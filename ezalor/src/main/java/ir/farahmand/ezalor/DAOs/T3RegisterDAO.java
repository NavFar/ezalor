package ir.farahmand.ezalor.DAOs;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class T3RegisterDAO {
	@NotNull
	private String[] targets;
	@NotBlank
	private String function;
	@NotNull
	@NotBlank
	private String image;
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
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
}
