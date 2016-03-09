package fr.vmarchaud.shareeat.objects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import fr.vmarchaud.shareeat.enums.EnumMealplan;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data @RequiredArgsConstructor @DatabaseTable(tableName = "mealplans")
public class Mealplan {

	@DatabaseField(columnName = "location",  foreign = true)
	public transient Location	location;
	
	@DatabaseField(columnName = "price")
	public int 			price;

	@DatabaseField(columnName = "plan")
	public EnumMealplan	plan;
	
	@DatabaseField(columnName = "drink")
	public int			drink;
	
}
