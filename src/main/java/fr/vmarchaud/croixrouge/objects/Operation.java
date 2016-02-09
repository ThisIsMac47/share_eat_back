package fr.vmarchaud.croixrouge.objects;

import java.util.List;
import java.util.UUID;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data @RequiredArgsConstructor 
public class Operation {
	public UUID 		id;
	public String 		location;
	public List<User>	engaged;
	public boolean		finished;
}
