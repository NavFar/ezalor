package ir.farahmand.ezalor.controllers;


import javax.validation.Valid;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ir.farahmand.ezalor.DAOs.T2RegisterDAO;
import ir.farahmand.ezalor.models.Target.Type;
import ir.farahmand.ezalor.services.FunctionStoreService;
import ir.farahmand.ezalor.services.HaskellAPIService;
import ir.farahmand.ezalor.services.TargetStoreService;
import ir.farahmand.ezalor.services.URLValidatorService;

@Profile({"t2","t3"})
@RestController
@RequestMapping("/t2/register")
public class Type2RegisterController {
	
	private HaskellAPIService haskellAPIService;
	private TargetStoreService targetStoreService;
	private FunctionStoreService functionStoreService;
	private URLValidatorService urlValidatorService;
	public Type2RegisterController(TargetStoreService targetStoreService, HaskellAPIService haskellAPIService, FunctionStoreService functionStoreService, URLValidatorService urlValidatorService) {
		this.targetStoreService = targetStoreService;
		this.haskellAPIService = haskellAPIService;
		this.functionStoreService = functionStoreService;
		this.urlValidatorService = urlValidatorService;
	}
	@PostMapping
	public ResponseEntity<String> registerTarget(@Valid @RequestBody T2RegisterDAO input ) {
		if(!this.urlValidatorService.areTargetsValid(input.getTargets()))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		if(!this.haskellAPIService.checkFunction(input.getFunction()))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		String uuid = this.targetStoreService.createTarget(Type.TYPE2, input.getTargets());
		this.functionStoreService.addFunction(uuid, input.getFunction());
		return new ResponseEntity<>(uuid,HttpStatus.OK);
	}
}
