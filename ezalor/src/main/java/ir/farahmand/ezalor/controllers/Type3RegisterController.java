package ir.farahmand.ezalor.controllers;

import javax.validation.Valid;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ir.farahmand.ezalor.DAOs.T3RegisterDAO;
import ir.farahmand.ezalor.models.Target.Type;
import ir.farahmand.ezalor.services.DockerClientService;
import ir.farahmand.ezalor.services.FunctionStoreService;
import ir.farahmand.ezalor.services.HaskellAPIService;
import ir.farahmand.ezalor.services.ImageStoreService;
import ir.farahmand.ezalor.services.TargetStoreService;
import ir.farahmand.ezalor.services.URLValidatorService;

@Profile("t3")
@RestController
@RequestMapping("/t3/register")
public class Type3RegisterController {
	private HaskellAPIService haskellAPIService;
	private TargetStoreService targetStoreService;
	private FunctionStoreService functionStoreService;
	private DockerClientService dockerClientService;
	private URLValidatorService urlValidatorService;
	private ImageStoreService imageStoreService;

	public Type3RegisterController(TargetStoreService targetStoreService,
			HaskellAPIService haskellAPIService,
			FunctionStoreService functionStoreService,
			DockerClientService dockerClientService,
			URLValidatorService urlValidatorService,
			ImageStoreService imageStoreService) {
		this.targetStoreService = targetStoreService;
		this.haskellAPIService = haskellAPIService;
		this.functionStoreService = functionStoreService;
		this.dockerClientService = dockerClientService;
		this.urlValidatorService = urlValidatorService;
		this.imageStoreService = imageStoreService;
	}
	@PostMapping
	public ResponseEntity<String> registerTarget(@Valid @RequestBody T3RegisterDAO input ) {
		if(!this.urlValidatorService.areTargetsValid(input.getTargets()))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		if(input.getFunction() !=null && !this.haskellAPIService.checkFunction(input.getFunction()))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		
		if(this.dockerClientService.getDockerClient().listImagesCmd().withImageNameFilter(input.getImage()).exec().size()==0 &&
				this.dockerClientService.getDockerClient().searchImagesCmd(input.getImage()).exec().size()==0) 
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		String uuid = this.targetStoreService.createTarget(Type.TYPE3, input.getTargets());
		this.functionStoreService.addFunction(uuid, input.getFunction());
		this.imageStoreService.addImage(uuid, input.getImage());
		
		return new ResponseEntity<>(uuid,HttpStatus.OK);
	}
}
