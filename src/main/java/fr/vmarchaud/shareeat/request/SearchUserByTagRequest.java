package fr.vmarchaud.shareeat.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class SearchUserByTagRequest {
	private List<String>	tags;
	
	public boolean isValid() {
		if (tags == null || tags.size() == 0)
			return false;
		else
			return true;
	}
}
