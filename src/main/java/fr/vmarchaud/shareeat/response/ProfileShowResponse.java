package fr.vmarchaud.shareeat.response;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class ProfileShowResponse {
	public Map<String, String>	datas;
}
