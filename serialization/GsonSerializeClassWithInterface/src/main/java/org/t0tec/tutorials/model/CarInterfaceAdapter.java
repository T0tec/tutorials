package org.t0tec.tutorials.model;
import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

    public class CarInterfaceAdapter implements JsonSerializer<Car>, JsonDeserializer<Car> {    	

        public JsonElement serialize(final Car car, final Type type, final JsonSerializationContext context) {
            final JsonObject wrapper = new JsonObject();
            wrapper.addProperty("type", car.getCarHandling().getClass().getName());
            wrapper.add("data", context.serialize(car.getCarHandling()));
            wrapper.add("carImageLocation", new JsonPrimitive(car.getCarImageLocation()));
            return wrapper;
        }

		public Car deserialize(JsonElement jsonElem, Type type, JsonDeserializationContext context) throws JsonParseException {
	        final JsonObject wrapper = (JsonObject) jsonElem;
	        final JsonElement typeName = get(wrapper, "type");
	        final JsonElement data = get(wrapper, "data");
	        final Type actualType = typeForName(typeName); 
	        
	        JsonElement element = wrapper.get("carImageLocation");
	        
	        Car car = new Car((ICarHandling) context.deserialize(data, actualType), element.getAsString());
	        
	        return car;
		}
    	
	    private Type typeForName(final JsonElement typeElem) {
	        try {
	            return Class.forName(typeElem.getAsString());
	        } catch (ClassNotFoundException e) {
	            throw new JsonParseException(e);
	        }
	    }
	    
	    private JsonElement get(final JsonObject wrapper, String memberName) {
	        final JsonElement elem = wrapper.get(memberName);
	        if (elem == null) throw new JsonParseException("no '" + memberName + "' member found in what was expected to be an interface wrapper");
	        return elem;
	    }

    }