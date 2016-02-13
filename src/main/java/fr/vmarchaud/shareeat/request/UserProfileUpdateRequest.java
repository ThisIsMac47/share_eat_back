package fr.vmarchaud.shareeat.request;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class UserProfileUpdateRequest {
	public Map<String, String>	datas;
	
	public boolean isValid() {
		return datas != null && datas.size() > 0 && datas.size() < 10 ? true : false;
	}
}
