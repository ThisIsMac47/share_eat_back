package fr.vmarchaud.shareeat.utils;

import fr.vmarchaud.shareeat.enums.EnumEnv;

public class CustomConfig {
	public EnumEnv	ENV 		=	EnumEnv.DEV;
	public String 	BASE_URL	=	"http://localhost:4242";
	public String	HOSTNAME	=	"jdbc:mysql://shareeat.vmarchaud.fr:3306/" + ENV.toString().toLowerCase();
	public String	USERNAME	=	"dev_user";
	public String	PASSWORD	=	"Nc6tqcEy2ZcYBrQP"; // "HMPTEWYSJG";
}
