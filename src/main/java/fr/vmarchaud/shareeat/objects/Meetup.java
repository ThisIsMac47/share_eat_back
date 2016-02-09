package fr.vmarchaud.shareeat.objects;

import java.util.List;
import java.util.UUID;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data @RequiredArgsConstructor 
public class Meetup {
	public UUID 		id;
	public List<UUID>	users;
	public Location		location;
	public boolean		finished;
	public List<String>	tags;
}
