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
	public EnumEnv	ENV 		=	EnumEnv.DEV;
	public String 	BASE_URL	=	"http://localhost:4242";
	public String	HOSTNAME	=	"jdbc:mysql://shareeat.vmarchaud.fr:3306/" + ENV.toString().toLowerCase();
	public String	USERNAME	=	"dev_user";
	public String	PASSWORD	=	"Nc6tqcEy2ZcYBrQP"; // "HMPTEWYSJG";
}
