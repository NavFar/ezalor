package ir.farahmand.ezalor.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile({"t2","t3"})
@Service
public class FunctionStoreService {
	private Map<String, String> functions;
	public FunctionStoreService() {
		this.functions = new HashMap<>();
	}
	
	public void addFunction(String key, String function) {
		this.functions.put(key, function);
	}
	
	public String getFunction(String key) {
		return this.functions.get(key);
	}
}
