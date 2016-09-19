package com.rjkj.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

public class ObjectDeserializer implements JsonDeserializer{

	@Override
	public Object deserialize(JsonElement arg0, Type arg1,
			JsonDeserializationContext arg2) throws JsonParseException {
		List<Object> list = new ArrayList<Object>();
		if(arg0.isJsonPrimitive()){
			JsonPrimitive jp = arg0.getAsJsonPrimitive();
			if(jp.isBoolean()){
				return jp.getAsBoolean();
			}else if(jp.isJsonNull()){
				return null;
			}else if(jp.isString()){
				return jp.getAsString();
			}else if(jp.isNumber()){
				return jp.getAsNumber();
			}else{
				return jp.toString();
			}
		}
		if(arg0.isJsonArray()){
			for(JsonElement je : arg0.getAsJsonArray()){
				Object o = arg2.deserialize(je, arg1);
				list.add(o);
			}
			return list;
		}
		if(arg0.isJsonObject()){
			Set<Entry<String,JsonElement>> set = arg0.getAsJsonObject().entrySet();
			LinkedHashMap<String,Object> lhm = new LinkedHashMap<String, Object>();
			for(Entry<String,JsonElement> entry : set){
				String key = entry.getKey();
				JsonElement je = entry.getValue();
				Object o = arg2.deserialize(je, arg1);
//				Object o2 = arg0.getAsJsonObject().get(key);
				lhm.put(key, o);
			}
			return lhm;
		}
//		if(arg0.isJsonNull()){
		return null;
//		}
	}
	
}
