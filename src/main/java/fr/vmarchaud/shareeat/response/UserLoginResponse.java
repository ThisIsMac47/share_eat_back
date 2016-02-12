package fr.vmarchaud.shareeat.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class UserLoginResponse {
	public String	accessToken;
	public UUID		id;
	public long		timestamp;
}
