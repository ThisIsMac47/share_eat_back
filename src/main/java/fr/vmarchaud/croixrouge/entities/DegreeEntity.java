package fr.vmarchaud.croixrouge.entities;

import java.util.UUID;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data @RequiredArgsConstructor 
public class DegreeEntity {

	public UUID				userId;
	public String 			name;
}
