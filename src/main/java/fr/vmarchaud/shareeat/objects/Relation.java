package fr.vmarchaud.shareeat.objects;

import java.util.UUID;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor @DatabaseTable(tableName = "relations")
public class Relation {
	
	@DatabaseField(columnName = "user", foreign = true)
	public transient User			user;
	
	@DatabaseField(columnName = "friend")
	public  UUID			friend;
	
}
