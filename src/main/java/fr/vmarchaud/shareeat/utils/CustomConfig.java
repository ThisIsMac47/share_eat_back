package fr.vmarchaud.shareeat.utils;

import fr.vmarchaud.shareeat.enums.EnumEnv;

public class CustomConfig {
	
	// Actually it only use to choose the db (dev or prod)
	public static EnumEnv	ENV 		=	EnumEnv.DEV;
	
	// The url that the http server will match for any request.
	public static String 	BASE_URL	=	"http://localhost:4242";
	
	// The DSN string with driver type / db url / db name
	public static String	HOSTNAME	=	"jdbc:mysql://localhost:3306/" + ENV.toString().toLowerCase();
	
	// Username used to login into the database
	public static String	USERNAME	=	"user";
	
	// Password used to login into the database
	public static String	PASSWORD	=	"pwd";
}
