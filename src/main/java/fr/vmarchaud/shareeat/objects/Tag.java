package fr.vmarchaud.shareeat.objects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @DatabaseTable(tableName = "tags")
public class Tag {

	@DatabaseField(columnName = "value")
	public String value;
}
