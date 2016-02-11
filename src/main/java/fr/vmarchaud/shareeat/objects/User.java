package fr.vmarchaud.shareeat.objects;

import java.util.List;
import java.util.UUID;

import javax.management.relation.Role;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import fr.vmarchaud.shareeat.enums.EnumRole;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data @RequiredArgsConstructor @DatabaseTable(tableName = "users")
public class User {
	
	// Internal info
	@DatabaseField(columnName = "id", id = true)
	public UUID								id;
	
	@DatabaseField(columnName = "name")
	public String 							name;
	
	@DatabaseField(columnName = "accessToken")
	public String 							accessToken;
	
	//@ForeignCollectionField
	//public Collection<Meetup>				meetups;
	
	// Data used for profile
	@DatabaseField(columnName = "age")
	public short							age;
	@DatabaseField(columnName = "phone")
	public String							phone;
	@DatabaseField(columnName = "mail")
	public String							mail;
	@DatabaseField(columnName = "avatar")
	public String							avatar;
	@DatabaseField(columnName = "background")
	public String							background;
	@DatabaseField(columnName = "description")
	public String							desc;
	@DatabaseField(columnName = "tags")
	public String							tags;
	@DatabaseField(columnName = "job")
	public String							job;
	
	public EnumRole							role;
	
	public List<UUID>						friends;
	
}

