package fr.vmarchaud.shareeat.objects;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data @RequiredArgsConstructor 
public class Meetup {
	public UUID 		id;
	public UUID			master;
	public List<UUID>	users;
	public boolean		finished;
	public List<String>	tags;
	public Date			date;
}
