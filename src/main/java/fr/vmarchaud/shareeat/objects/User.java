package fr.vmarchaud.shareeat.objects;

import java.util.List;
import java.util.UUID;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data @RequiredArgsConstructor 
public class User {
	
	// Internal info
	public UUID								id;
	public String 							name;
	public String 							accessToken;
	
	
	
	// Data used for profile
	public short							age;
	public String							phone;
	public String							mail;
	public String							avatar;
	public String							background;
	public String							desc;
	public List<String>						tags;
	public String							job;
	
	
	public enum USR_KEY {
		NAME,
		ID;
	}
}

