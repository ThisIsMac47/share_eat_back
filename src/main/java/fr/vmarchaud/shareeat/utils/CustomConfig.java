package fr.vmarchaud.shareeat.utils;

import fr.vmarchaud.shareeat.enums.EnumEnv;

public class CustomConfig {
	// PROD

	/*
	public EnumEnv	ENV 		=	EnumEnv.PROD;
	public String 	BASE_URL	=	"http://164.132.103.92:8080";
	public String	HOSTNAME	=	"jdbc:mysql://localhost:3306/" + ENV.toString().toLowerCase();
	public String	USERNAME	=	"prod_user";
	public String	PASSWORD	=	"";
	// DEV */
	public static EnumEnv	ENV 		=	EnumEnv.DEV;
	//public static String 	BASE_URL	=	"http://localhost:4242";
	public static String 	BASE_URL	=	"http://164.132.103.92:8080";
	public static String	HOSTNAME	=	"jdbc:mysql://shareeat.vmarchaud.fr:3306/" + ENV.toString().toLowerCase();
	public static String	USERNAME	=	"dev_user";
	public static String	PASSWORD	=	"Nc6tqcEy2ZcYBrQP"; // "HMPTEWYSJG";
	
	
}
