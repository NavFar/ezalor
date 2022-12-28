package ir.farahmand.ezalor.controllers;


import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ir.farahmand.ezalor.DAOs.T1RegisterDAO;
import ir.farahmand.ezalor.models.Target.Type;
import ir.farahmand.ezalor.services.TargetStoreService;
import ir.farahmand.ezalor.services.URLValidatorService;

@RestController
@RequestMapping("/t1/register")
public class Type1RegisterController {
	
	private TargetStoreService targetStoreService;
	private URLValidatorService urlValidatorService;
	public Type1RegisterController(TargetStoreService targetStoreService, URLValidatorService urlValidatorService) {
		this.targetStoreService = targetStoreService;
		this.urlValidatorService = urlValidatorService;
	}

	@PostMapping
	public ResponseEntity<String> registerTarget(@Valid @RequestBody T1RegisterDAO input ) {
		
		
		if(!this.urlValidatorService.areTargetsValid(input.getTargets()))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		String uuid = this.targetStoreService.createTarget(Type.TYPE1, input.getTargets());
		return new ResponseEntity<>(uuid,HttpStatus.OK);
	}
}
