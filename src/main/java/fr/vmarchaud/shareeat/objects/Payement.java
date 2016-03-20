package fr.vmarchaud.shareeat.objects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data @AllArgsConstructor @RequiredArgsConstructor @DatabaseTable(tableName = "payements")
public class Payement {
	
	@DatabaseField(columnName = "user", foreign = true)
	public User		user;
	
	@DatabaseField(columnName = "live")
	public boolean 	live;

	@DatabaseField(columnName = "token")
	public String	token;

	@DatabaseField(columnName = "charge")
	public String	charge;

	@DatabaseField(columnName = "meetup", foreign = true)
	public Meetup	meetup;
}
