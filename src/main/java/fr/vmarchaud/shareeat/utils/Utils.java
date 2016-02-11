package fr.vmarchaud.shareeat.utils;

public class Utils {
	
	public static boolean	isUUID(String uuid) {
		return uuid.matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}") ? true : false;
	}
	
	
}
