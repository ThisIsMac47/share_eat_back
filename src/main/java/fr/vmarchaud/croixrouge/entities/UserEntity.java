package fr.vmarchaud.croixrouge.entities;

import java.util.UUID;

import fr.vmarchaud.croixrouge.enums.EnumStatus;
import fr.vmarchaud.croixrouge.objects.Location;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data @RequiredArgsConstructor 
public class UserEntity {
	
	public UUID								id;
	public String 							name;
	public short							age;
	public String							adress;
	public String							phone;
	public String							mail;
	public Location							last_geo;
	public String							password;
	public EnumStatus						status;
	public String							registerId;
}
