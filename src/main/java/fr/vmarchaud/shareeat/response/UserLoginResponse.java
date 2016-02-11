package fr.vmarchaud.shareeat.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class UserLoginResponse {
	public String	accessToken;
	public long		timestamp;
}
