package fr.vmarchaud.croixrouge.response;

import fr.vmarchaud.croixrouge.enums.EnumStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class UserLoginResponse {
	private String 		accessToken;
	private EnumStatus 	currentStatus;
	private long		timestamp;
}
