package fr.vmarchaud.shareeat.objects;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data @RequiredArgsConstructor 
public class Meetup {
	// Key
	public UUID 					id;
	public transient User			master;
	
	// Data
	public List<UUID>	users;
	public boolean		finished;
	public List<String>	tags;
	public Date			date;
	public Location		locaion;
}
