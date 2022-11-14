package ir.farahmand.ezalor.DAOs;

import javax.validation.constraints.NotNull;

public class T1RegisterDAO {
	@NotNull
	private String[] targets;

	public String[] getTargets() {
		return targets;
	}

	public void setTargets(String[] targets) {
		this.targets = targets;
	}
	
	
}
