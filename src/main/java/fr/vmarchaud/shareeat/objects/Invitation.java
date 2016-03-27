package fr.vmarchaud.shareeat.objects;

import java.util.UUID;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import fr.vmarchaud.shareeat.enums.EnumInvitation;
import fr.vmarchaud.shareeat.enums.EnumState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data @RequiredArgsConstructor @AllArgsConstructor @DatabaseTable(tableName = "invitations")
public class Invitation {
	
	@DatabaseField(columnName = "id", id = true)
	public UUID						id;
	
	@DatabaseField(columnName = "requester", foreign = true)
	public transient User			requester;
	
	@DatabaseField(columnName = "receiver", foreign = true)
	public transient User			receiver;
	
	@DatabaseField(columnName = "type")
	public EnumInvitation			type;
	
	@DatabaseField(foreignAutoRefresh = true, columnName = "meetup", foreign = true)
	public transient Meetup			meetup;
	
	@DatabaseField(columnName = "state")
	public EnumState				state;
}
