package ir.farahmand.ezalor.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.LogContainerCmd;
import com.github.dockerjava.api.command.WaitContainerResultCallback;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.LogContainerResultCallback;




@Profile("t3")
@SuppressWarnings("deprecation")
@Service
public class DockerClientService {
	DockerClient dockerClient;
	public DockerClientService() {
		//		DefaultDockerClientConfig.Builder config = DefaultDockerClientConfig.createDefaultConfigBuilder();
		//		DockerClient dockerClient = DockerClientBuilder.getInstance(config).build();
		DockerClient dockerClient = DockerClientBuilder.getInstance().build();
		this.dockerClient = dockerClient;
	}
	public DockerClient getDockerClient() {
		return dockerClient;
	}

	public ContainerOutput runDocker(String image, String input) {
		ContainerOutput result = new ContainerOutput();
		ObjectMapper mapper = new ObjectMapper();
		String sanitizedInputsString = "";
		CreateContainerResponse container;
		try {
			sanitizedInputsString = mapper.writeValueAsString(input);

			container =
					this.getDockerClient().createContainerCmd(image)
					.withEnv("INPUT="+ sanitizedInputsString)
					.exec();
			this.getDockerClient().startContainerCmd(container.getId()).exec();


			WaitContainerResultCallback resultCallback = new WaitContainerResultCallback();
			dockerClient.waitContainerCmd(container.getId()).exec(resultCallback);
			resultCallback.awaitCompletion(5,TimeUnit.MINUTES);
		}catch(Exception e) {
			result.setHasError(true);
			return result;
		}
		String errors = this.getContainerError(container.getId());
		if(errors !=null && !errors.isBlank())
		{
			result.setHasError(true);
			result.setMessage(errors);
			return result;
		}

		result.setHasOutput(true);
		result.setMessage(this.getContainerResult(container.getId()));
		this.deleteContainer(container.getId());
		return result;
	}
	public String getContainerError(String containerId) {
		if(this.getDockerClient().listContainersCmd().withShowAll(true).withIdFilter(Arrays.asList(containerId)).exec().size()==0) {
			return "NOT_FOUND";
		}
		if(this.getDockerClient().listContainersCmd().withShowAll(true).withIdFilter(Arrays.asList(containerId)).exec().size()!=1) {
			return "CONFLICT";
		}
		return this.getResult(containerId, false, true);
	}

	public String getContainerResult(String containerId) {
		if(this.getDockerClient().listContainersCmd().withShowAll(true).withIdFilter(Arrays.asList(containerId)).exec().size()==0) {
			return "NOT_FOUND";
		}
		if(this.getDockerClient().listContainersCmd().withShowAll(true).withIdFilter(Arrays.asList(containerId)).exec().size()!=1) {
			return "CONFLICT";
		}
		return this.getResult(containerId, true, false);
	}

	public void deleteContainer(String containerId) {
		this.getDockerClient().removeContainerCmd(containerId).withForce(true).exec();
	}
	private String getResult(String containerId,Boolean stdOut,Boolean stdErr) {
		final List<String> logs = new ArrayList<String>();
		LogContainerCmd logContainerCmd = this.getDockerClient().logContainerCmd(containerId);
		logContainerCmd.withStdOut(stdOut).withStdErr(stdErr);
		//		logContainerCmd.withTimestamps(true);
		try {
			logContainerCmd.exec(new LogContainerResultCallback() {
				@Override
				public void onNext(Frame item) {
					String str = item.toString();
					if(str.indexOf("STDOUT:")!=-1)
						str = str.substring(str.indexOf("STDOUT:")+"STDOUT:".length()).trim();
					if(str.indexOf("STDERR:")!=-1)
						str = str.substring(str.indexOf("STDOUT:")+"STDOUT:".length()).trim();
					logs.add(str);
				}
			})
			.awaitCompletion(30,TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			return "";
		}
		return String.join("", logs);
	}
}
