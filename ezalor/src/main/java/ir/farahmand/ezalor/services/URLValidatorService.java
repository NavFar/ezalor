package ir.farahmand.ezalor.services;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Service;

@Service
public class URLValidatorService {
	public boolean areTargetsValid(String[] targets) {
		String[] schemes = {"http", "https"};
		UrlValidator validator = new  UrlValidator(schemes);
		for(String  targetURL: targets) {
			if(!validator.isValid(targetURL)) {
				return false;
			}
		}
		return true;
	}
}
