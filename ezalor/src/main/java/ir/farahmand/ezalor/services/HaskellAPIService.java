package ir.farahmand.ezalor.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile({ "t2", "t3" })
@Service
public class HaskellAPIService {
	public boolean checkFunction(String function) {
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.directory(new File("/tmp"));
		processBuilder.command("ghci");
		try {
			Process process = processBuilder.start();
			OutputStream inputStream = process.getOutputStream();
			InputStream outputStream = process.getInputStream();
			InputStream errorStream = process.getErrorStream();
			inputStream.write(function.getBytes(Charset.forName("UTF-8")));
			inputStream.write("\n".getBytes(Charset.forName("UTF-8")));
			inputStream.write(":type convert ".getBytes(Charset.forName("UTF-8")));
			inputStream.flush();
			inputStream.close();
			boolean isFinished = process.waitFor(10, TimeUnit.SECONDS);
			String response = "";
			try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(outputStream))) {
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					response += line;
				}
			} catch (IOException e) {
				System.err.println("Can't get GHCI result");
			}
			if (errorStream.available() != 0)
				return false;
			if(response.contains("convert :: String -> Maybe String"))
				return true;

			if (!isFinished) {
				process.destroyForcibly();
			}
		} catch (IOException e) {
			System.err.println("Can't run GHCI");
		} catch (InterruptedException e) {
			System.err.println("Can't Stop GHCI");
		}

		return false;
	}
	
	public String runFunction(String function, String  input) {
		if(function==null || function.isEmpty())
			return input;
		String output = "";
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.directory(new File("/tmp"));
		processBuilder.command("ghci");
		try {
			Process process = processBuilder.start();
			OutputStream inputStream = process.getOutputStream();
			InputStream outputStream = process.getInputStream();
			InputStream errorStream = process.getErrorStream();
			inputStream.write(function.getBytes(Charset.forName("UTF-8")));
			inputStream.write("\n".getBytes(Charset.forName("UTF-8")));
			inputStream.write(("convert \""+input+"\"").getBytes(Charset.forName("UTF-8")));
			inputStream.flush();
			inputStream.close();
			boolean isFinished = process.waitFor(10, TimeUnit.SECONDS);
			String response = "";
			try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(outputStream))) {
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					response += line;
				}
			} catch (IOException e) {
				System.err.println("Can't get GHCI result");
			}
			if (errorStream.available() != 0)
				return output;
			if (!isFinished) {
				process.destroyForcibly();
			}
			return response;
		} catch (IOException e) {
			System.err.println("Can't run GHCI");
		} catch (InterruptedException e) {
			System.err.println("Can't Stop GHCI");
		}
		return output;
	}

}
