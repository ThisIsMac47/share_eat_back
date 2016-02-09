package fr.vmarchaud.croixrouge.objects;

import java.util.List;
import java.util.UUID;

import fr.vmarchaud.croixrouge.enums.EnumDegrees;
import fr.vmarchaud.croixrouge.enums.EnumStatus;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data @RequiredArgsConstructor 
public class User {
	
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
	public List<EnumDegrees>				degrees;
	public String 							accessToken;
}

