package fr.vmarchaud.shareeat.objects;

import java.util.Date;
import java.util.UUID;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data @RequiredArgsConstructor 
public class Location {
	public	UUID	id;
	public  String	position;
	public	String	name;
	public	Date	date;
}
