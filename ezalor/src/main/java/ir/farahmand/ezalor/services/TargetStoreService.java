package ir.farahmand.ezalor.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import ir.farahmand.ezalor.models.Target;
import ir.farahmand.ezalor.models.Target.Type;

@Service
public class TargetStoreService {

	private Map<String, List<Target>> targets;
	
	public TargetStoreService() {
		this.targets = new HashMap<>();
	}
	
	public String createTarget(Type type, String[] targets) {
		String uuid = UUID.randomUUID().toString();
		for(String targetURL: targets) {
			Target target = new Target();
			target.setUrl(targetURL);
			target.setType(type);
			this.saveTarget(uuid, target);
		}
		return uuid;
	}
	
	public void saveTarget(String key, Target target) {
		List<Target> selectedTarget =this.targets.get(key);
		if(selectedTarget==null)
			selectedTarget = new ArrayList<Target>();
		selectedTarget.add(target);
		this.targets.put(key, selectedTarget);
	}
}
