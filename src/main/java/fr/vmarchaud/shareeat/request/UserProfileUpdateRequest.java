package fr.vmarchaud.shareeat.request;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class UserProfileUpdateRequest {
	public String				accessToken;
	public String				id;
	
	public Map<String, String>	datas;
	
	public boolean isValid() {
		return accessToken != null && accessToken.length() > 0 && id != null && id.length() == 36 &&
				datas != null && datas.size() > 0 && datas.size() < 10 ? true : false;
	}
}
