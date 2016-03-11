package fr.vmarchaud.shareeat.request;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class MeetupCreateRequest {
	public UUID 		creator;
	public List<UUID> 	invited;
	public UUID			location;
	public List<String>	tags;
	public String		date;
}
