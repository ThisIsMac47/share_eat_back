package fr.vmarchaud.croixrouge.dbs.impls;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import fr.vmarchaud.croixrouge.Core;
import fr.vmarchaud.croixrouge.dbs.IDatabase;
import fr.vmarchaud.croixrouge.entities.DegreeEntity;
import fr.vmarchaud.croixrouge.entities.UserEntity;

public class DBStandalone implements IDatabase{

	private String	dataFolder;
	
	public DBStandalone(String dataFolder) {
		this.dataFolder = dataFolder;
	}
	
	@Override
	public List<UserEntity> queryUsers() {
		try {
			TypeToken<List<UserEntity>> userType = new TypeToken<List<UserEntity>>(){};
			return Core.getInstance().gson.fromJson(new String(Files.readAllBytes(Paths.get(dataFolder + File.pathSeparator + "users.json")), StandardCharsets.UTF_8), userType.getType());
		} catch (JsonSyntaxException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<DegreeEntity> queryDegrees() {
		try {
			TypeToken<List<DegreeEntity>> degreeType = new TypeToken<List<DegreeEntity>>(){};
			return Core.getInstance().gson.fromJson(new String(Files.readAllBytes(Paths.get(dataFolder + File.pathSeparator + "degrees.json")), StandardCharsets.UTF_8), degreeType.getType());
		} catch (JsonSyntaxException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
