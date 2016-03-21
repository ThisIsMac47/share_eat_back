package fr.vmarchaud.shareeat.request;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class MeetupCreateRequest {
	public List<UUID> 	invited;
	public UUID			location;
	public String[]		tags;
	public String		date;
	public String		name;
	
	public boolean isValid() {
		if (invited == null || invited.size() == 0 || location == null || tags == null || tags.length == 0 || date == null || name == null)
			return false;
		else
			return true;
	}
}
