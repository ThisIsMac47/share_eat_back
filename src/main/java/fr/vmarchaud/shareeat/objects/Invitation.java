package fr.vmarchaud.shareeat.objects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import fr.vmarchaud.shareeat.enums.EnumInvitation;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data @RequiredArgsConstructor @DatabaseTable(tableName = "invitations")
public class Invitation {

	@DatabaseField(columnName = "requester", foreign = true)
	public transient User			requester;
	
	@DatabaseField(columnName = "receiver", foreign = true)
	public transient User			receiver;
	
	@DatabaseField(columnName = "type")
	public transient EnumInvitation			type;
	
	@DatabaseField(columnName = "meetup", foreign = true)
	public transient Meetup			meetup;
}
