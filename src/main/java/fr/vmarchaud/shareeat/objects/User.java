package fr.vmarchaud.shareeat.objects;

import java.util.List;
import java.util.UUID;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import fr.vmarchaud.shareeat.enums.EnumRole;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @DatabaseTable(tableName = "users")
public class User {
	
	// Internal info
	@DatabaseField(columnName = "id", id = true)
	public UUID								id;
	
	@DatabaseField(columnName = "name")
	public String 							name;
	
	@DatabaseField(columnName = "accessToken")
	public String 							accessToken;
	
	
	// Data used for profile
	@DatabaseField(columnName = "age")
	public short							age;
	@DatabaseField(columnName = "phone")
	public String							phone;
	@DatabaseField(columnName = "mail")
	public String							mail;
	@DatabaseField(columnName = "avatar")
	public String							avatar;
	@DatabaseField(columnName = "school")
	public String							school;
	@DatabaseField(columnName = "description")
	public String							desc;
	@DatabaseField(columnName = "tags")
	public String							tags;
	@DatabaseField(columnName = "job")
	public String							job;
	@DatabaseField(columnName = "password")
	public String							password;
	
	public EnumRole							role;

	
	public List<UUID>						friends;

	//@ForeignCollectionField
	//public Collection<Meetup>				meetups;
}

