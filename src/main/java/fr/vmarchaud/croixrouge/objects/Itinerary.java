package fr.vmarchaud.croixrouge.objects;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data @RequiredArgsConstructor 
public class Itinerary {
	public Location start;
	public Location end;
	public int		distance;
	public int		time;
}
