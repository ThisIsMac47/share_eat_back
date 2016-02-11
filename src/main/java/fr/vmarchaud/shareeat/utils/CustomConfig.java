package fr.vmarchaud.shareeat.utils;

import fr.vmarchaud.shareeat.enums.EnumEnv;

public class CustomConfig {
	public EnumEnv	ENV 		=	EnumEnv.DEV;
	public String 	BASE_URL	=	"http://localhost:4242";
	public String	HOSTNAME	=	"jdbc:postgresql://shareeat.vmarchaud.fr:5432/" + 
										ENV.toString().toLowerCase() + "?currentSchema=public";
	public String	USERNAME	=	"postgres";
	public String	PASSWORD	=	"HMPTEWYSJG";
}
