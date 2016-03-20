package fr.vmarchaud.shareeat.adapters;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import fr.vmarchaud.shareeat.objects.Invitation;

public class InvitationAdapter implements JsonDeserializer<Invitation>, JsonSerializer<Invitation> {

    @Override
    public Invitation deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return null;
    }

    @Override
    public JsonElement serialize(Invitation src, Type typeOfSrc, JsonSerializationContext context) {
    	JsonObject obj = new JsonObject();
    	obj.addProperty("requester", src.getRequester().getId().toString());
    	obj.addProperty("receiver", src.getRequester().getId().toString());
    	obj.addProperty("type", src.getType().toString());
    	obj.addProperty("meetup", src.getMeetup() != null ? src.getMeetup().getId().toString() : null);
    	obj.addProperty("state", src.getState().toString());
        return obj;
    }
}