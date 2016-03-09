package fr.vmarchaud.shareeat.objects;

import java.util.Collection;
import java.util.UUID;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data @RequiredArgsConstructor @DatabaseTable(tableName = "locations")
public class Location {

	@DatabaseField(columnName = "id", id = true)
	public	UUID	id;
	
	@DatabaseField(columnName = "position")
	public  String	position;

	@DatabaseField(columnName = "name")
	public	String	name;

	@DatabaseField(columnName = "image")
	public	String	img;

	@DatabaseField(columnName = "type")
	public	String	type;

	@DatabaseField(columnName = "description")
	public	String	description;
	
	@ForeignCollectionField(eager = true)
	public Collection<Mealplan> plans;
}
