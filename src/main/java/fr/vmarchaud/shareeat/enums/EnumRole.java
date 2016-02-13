package fr.vmarchaud.shareeat.enums;

public enum EnumRole {
	NONE(0), USER(1), ADMIN(2);
	
	private int weight;
	
	private EnumRole(int weight) {
		this.weight = weight;
	}
	
	/**
	 * Get EnumRole from string value
	 * @param String value of the EnumRole
	 * @return EnumRole corresponding to the request OR null if not found
	 */
	public static EnumRole fromName(String name) {
		if (name.equals("USER"))
			return USER;
		else if (name.equals("ADMIN"))
			return ADMIN;
		else if (name.equals("NONE"))
			return NONE;
		return null;
	}
	
	/**
	 * Compare the first role to the second role
	 * @param EnumRole first
	 * @param EnumRole second
	 * @return the difference of ordinal
	 */
	public static int		compareWeight(EnumRole first, EnumRole second) {
		return first.weight - first.weight;
	}
	
}
