package fr.vmarchaud.shareeat.objects;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data @RequiredArgsConstructor 
public class Meetup {
	@DatabaseField(columnName = "id")
	public UUID 					id;
	
	@DatabaseField(columnName = "master", foreign = true)
	public transient User			master;
	
	@DatabaseField(columnName = "done")
	public boolean					done;
	
	@DatabaseField(columnName = "tags")
	public String					tags;
	
	@DatabaseField(columnName = "date",	dataType = DataType.DATE_TIME)
	public Date						date;
	
	@DatabaseField(columnName = "location", foreign = true)
	public Location					location;
	
	@DatabaseField(columnName = "master", foreign = true)
	public Collection<Invitation>	users;
}
