package ir.farahmand.ezalor.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import ir.farahmand.ezalor.models.Target;

@Service
public class TargetStoreService {

	private Map<String, List<Target>> targets;
	
	public TargetStoreService() {
		this.targets = new HashMap<>();
	}
	
	public void addTarget(String key, Target target) {
		List<Target> selectedTarget =this.targets.get(key);
		if(selectedTarget==null)
			selectedTarget = new ArrayList<Target>();
		selectedTarget.add(target);
		this.targets.put(key, selectedTarget);
	}
}
