package fr.vmarchaud.shareeat.objects;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import fr.vmarchaud.shareeat.enums.EnumState;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data @RequiredArgsConstructor @DatabaseTable(tableName = "meetups")
public class Meetup {
	@DatabaseField(columnName = "id", id = true)
	public UUID 					id;
	
	@DatabaseField(columnName = "master", foreign = true)
	public transient User			master;

	@DatabaseField(columnName = "name")
	public String					name;
	
	@DatabaseField(columnName = "state")
	public EnumState				state;
	
	@DatabaseField(columnName = "tags")
	public String					tags;
	
	@DatabaseField(columnName = "date")
	public String					date;
	
	@DatabaseField(columnName = "location", foreign = true)
	public Location					location;

	@ForeignCollectionField(eager = true, foreignFieldName="meetup")
	public Collection<Invitation>	users;
}
