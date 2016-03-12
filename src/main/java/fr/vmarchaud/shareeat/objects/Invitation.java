package fr.vmarchaud.shareeat.objects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import fr.vmarchaud.shareeat.enums.EnumInvitation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data @RequiredArgsConstructor @AllArgsConstructor @DatabaseTable(tableName = "invitations")
public class Invitation {

	@DatabaseField(columnName = "requester", foreign = true)
	public transient User			requester;
	
	@DatabaseField(columnName = "receiver", foreign = true)
	public transient User			receiver;
	
	@DatabaseField(columnName = "type")
	public EnumInvitation			type;
	
	@DatabaseField(columnName = "meetup", foreign = true)
	public Meetup					meetup;
	
	@DatabaseField(columnName = "state")
	public boolean					state;
}
