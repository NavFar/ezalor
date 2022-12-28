package ir.farahmand.ezalor.controllers;


import java.util.List;

import javax.validation.Valid;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import ir.farahmand.ezalor.services.URLValidatorService;
import reactor.core.publisher.Flux;

@Profile({"t2","t3"})
@RestController
@RequestMapping("/t2")
public class Type2Controller {
	
	private HaskellAPIService haskellAPIService;
	private TargetStoreService targetStoreService;
	private FunctionStoreService functionStoreService;
	private URLValidatorService urlValidatorService;
	public Type2Controller(TargetStoreService targetStoreService, HaskellAPIService haskellAPIService, FunctionStoreService functionStoreService, URLValidatorService urlValidatorService) {
		this.targetStoreService = targetStoreService;
		this.haskellAPIService = haskellAPIService;
		this.functionStoreService = functionStoreService;
		this.urlValidatorService = urlValidatorService;
	}
	@PostMapping("/register")
	public ResponseEntity<String> registerTarget(@Valid @RequestBody T2RegisterDAO input ) {
		if(!this.urlValidatorService.areTargetsValid(input.getTargets()))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		if(!this.haskellAPIService.checkFunction(input.getFunction()))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		String uuid = this.targetStoreService.createTargets(Type.TYPE2, input.getTargets());
		this.functionStoreService.addFunction(uuid, input.getFunction());
		return new ResponseEntity<>(uuid,HttpStatus.OK);
	}
	
	@GetMapping("/execute/{id}")
	public ResponseEntity<String> executeTarget(@PathVariable String id) {
		List<Target> targets = this.targetStoreService.getTargets(id);
		if (targets == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		List<String> responses = Flux.fromIterable(targets).flatMap(target -> this.targetStoreService.executeTarget(target)).collectList().block();
		
		String response = "[";
		for(int i=0;i <responses.size();i++) {
			response += this.haskellAPIService.runFunction(this.functionStoreService.getFunction(id), response) ;
			if(i < responses.size() -1 )
				response += ",";
		}
		response += " ]";
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
}
