package fr.vmarchaud.shareeat.objects;

import java.util.UUID;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data @RequiredArgsConstructor @DatabaseTable(tableName = "relations")
public class Relation {
	
	@DatabaseField(columnName = "user")
	public UUID			user;
	
	@DatabaseField(columnName = "friend")
	public UUID			friend;
}
