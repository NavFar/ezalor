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

import ir.farahmand.ezalor.DAOs.T2RegisterDAO;
import ir.farahmand.ezalor.models.Target;
import ir.farahmand.ezalor.models.Target.Type;
import ir.farahmand.ezalor.services.FunctionStoreService;
import ir.farahmand.ezalor.services.HaskellAPIService;
import ir.farahmand.ezalor.services.TargetStoreService;

@Profile({"t2","t3"})
@RestController
@RequestMapping("/t2/register")
public class Type2RegisterController {
	
	private HaskellAPIService haskellAPIService;
	private TargetStoreService targetStoreService;
	private FunctionStoreService functionStoreService;
	public Type2RegisterController(TargetStoreService targetStoreService, HaskellAPIService haskellAPIService, FunctionStoreService functionStoreService) {
		this.targetStoreService = targetStoreService;
		this.haskellAPIService = haskellAPIService;
		this.functionStoreService = functionStoreService;
	}
	@PostMapping
	public ResponseEntity<String> registerTarget(@Valid @RequestBody T2RegisterDAO input ) {
		String[] schemes = {"http", "https"};
		UrlValidator validator = new  UrlValidator(schemes);
		for(String  targetURL: input.getTargets()) {
			if(!validator.isValid(targetURL)) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		
		if(!this.haskellAPIService.checkFunction(input.getFunction()))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		String uuid = UUID.randomUUID().toString();
		this.functionStoreService.addFunction(uuid, input.getFunction());
		for(String targetURL: input.getTargets()) {
			Target target = new Target();
			target.setUrl(targetURL);
			target.setType(Type.TYPE2);
			this.targetStoreService.addTarget(uuid, target);
		}
		return new ResponseEntity<>(uuid,HttpStatus.OK);
	}
}
