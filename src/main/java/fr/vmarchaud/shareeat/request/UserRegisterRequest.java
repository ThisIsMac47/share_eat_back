package fr.vmarchaud.shareeat.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class UserRegisterRequest {
	public String mail;
	public String password;
	
	public boolean isValid() {
		return mail != null && password != null && !mail.isEmpty() && !password.isEmpty() ? true : false;
	}
}
