package fr.vmarchaud.croixrouge.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class UserLoginRequest {
	public String	username;
	public String	password;
	
	public boolean isValid() {
		return username != null && password != null && username.length() > 0 && password.length() > 0 ? true : false;
	}
}
