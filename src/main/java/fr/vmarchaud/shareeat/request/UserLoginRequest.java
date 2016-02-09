package fr.vmarchaud.shareeat.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class UserLoginRequest {
	public String	username;
	public String	accessToken;
	
	public boolean isValid() {
		return username != null && accessToken != null && username.length() > 0 && accessToken.length() > 0 ? true : false;
	}
}
