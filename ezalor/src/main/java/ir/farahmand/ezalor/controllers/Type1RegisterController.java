package ir.farahmand.ezalor.controllers;

import java.util.UUID;

import javax.validation.Valid;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ir.farahmand.ezalor.DAOs.T1RegisterDAO;
import ir.farahmand.ezalor.models.Target;
import ir.farahmand.ezalor.models.Target.Type;
import ir.farahmand.ezalor.services.TargetStoreService;

@RestController
@RequestMapping("/t1/register")
public class Type1RegisterController {
	
	private TargetStoreService targetStoreService;
	public Type1RegisterController(TargetStoreService targetStoreService) {
		this.targetStoreService = targetStoreService;
	}

	@PostMapping
	public ResponseEntity<String> registerTarget(@Valid @RequestBody T1RegisterDAO input ) {
		String[] schemes = {"http", "https"};
		UrlValidator validator = new  UrlValidator(schemes);
		for(String  targetURL: input.getTargets()) {
			if(!validator.isValid(targetURL)) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		String uuid = UUID.randomUUID().toString();
		for(String targetURL: input.getTargets()) {
			Target target = new Target();
			target.setUrl(targetURL);
			target.setType(Type.TYPE1);
			this.targetStoreService.addTarget(uuid, target);
		}
		return new ResponseEntity<>(uuid,HttpStatus.OK);
	}
}
