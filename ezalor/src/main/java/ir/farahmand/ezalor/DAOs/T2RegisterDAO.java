package ir.farahmand.ezalor.DAOs;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class T2RegisterDAO {
	@NotNull
	private String[] targets;
	@NotNull
	@NotBlank
	private String function;
	public String[] getTargets() {
		return targets;
	}

	public void setTargets(String[] targets) {
		this.targets = targets;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}
	
}
