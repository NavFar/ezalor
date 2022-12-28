package ir.farahmand.ezalor.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile({"t3"})
@Service
public class ImageStoreService {
	private Map<String, String> images;

	public ImageStoreService() {
		this.images = new HashMap<>();
	}
	
	public void addImage(String key, String image) {
		this.images.put(key, image);
	}
	
	public String getImage(String key) {
		return this.images.get(key);
	}
}
