package fr.vmarchaud.shareeat.adapters;

import java.lang.reflect.Type;
import java.util.Collection;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import fr.vmarchaud.shareeat.objects.Relation;

public class RelationCollectionAdapter implements JsonDeserializer<Collection<Relation>>, JsonSerializer<Collection<Relation>> {

    @Override
    public Collection<Relation> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return null;
    }

    @Override
    public JsonElement serialize(Collection<Relation> src, Type typeOfSrc, JsonSerializationContext context) {
    	JsonArray array = new JsonArray();
    	for(Relation rel : src) {
    		array.add(rel.getFriend().toString());
    	}
        return array;
    }
}
