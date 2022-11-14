package ir.farahmand.ezalor.controllers;

import java.util.UUID;

import javax.validation.Valid;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ir.farahmand.ezalor.DAOs.T3RegisterDAO;
import ir.farahmand.ezalor.models.Target;
import ir.farahmand.ezalor.models.Target.Type;
import ir.farahmand.ezalor.services.DockerClientService;
import ir.farahmand.ezalor.services.FunctionStoreService;
import ir.farahmand.ezalor.services.HaskellAPIService;
import ir.farahmand.ezalor.services.TargetStoreService;

@Profile("t3")
@RestController
@RequestMapping("/t3/register")
public class Type3RegisterController {
	private HaskellAPIService haskellAPIService;
	private TargetStoreService targetStoreService;
	private FunctionStoreService functionStoreService;
	private DockerClientService dockerClientService;
	public Type3RegisterController(TargetStoreService targetStoreService, HaskellAPIService haskellAPIService, FunctionStoreService functionStoreService, DockerClientService dockerClientService) {
		this.targetStoreService = targetStoreService;
		this.haskellAPIService = haskellAPIService;
		this.functionStoreService = functionStoreService;
		this.dockerClientService = dockerClientService;
	}
	@PostMapping
	public ResponseEntity<String> registerTarget(@Valid @RequestBody T3RegisterDAO input ) {
		String[] schemes = {"http", "https"};
		UrlValidator validator = new  UrlValidator(schemes);
		for(String  targetURL: input.getTargets()) {
			if(!validator.isValid(targetURL)) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		
		if(input.getFunction() !=null && !this.haskellAPIService.checkFunction(input.getFunction()))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		
		if(this.dockerClientService.getDockerClient().listImagesCmd().withImageNameFilter(input.getImage()).exec().size()==0 &&
				this.dockerClientService.getDockerClient().searchImagesCmd(input.getImage()).exec().size()==0) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		String uuid = UUID.randomUUID().toString();
		this.functionStoreService.addFunction(uuid, input.getFunction());
		for(String targetURL: input.getTargets()) {
			Target target = new Target();
			target.setUrl(targetURL);
			target.setImage(input.getImage());
			target.setType(Type.TYPE3);
			this.targetStoreService.addTarget(uuid, target);
		}
		return new ResponseEntity<>(uuid,HttpStatus.OK);
	}
}
