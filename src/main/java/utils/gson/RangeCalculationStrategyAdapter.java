package utils.gson;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import model.strategy.RangeCalculationStrategy;
import model.strategy.SimpleFactorRangeStrategy;

public class RangeCalculationStrategyAdapter implements JsonSerializer<RangeCalculationStrategy>, JsonDeserializer<RangeCalculationStrategy> {
    
    @Override
    public JsonElement serialize(RangeCalculationStrategy src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("type", new JsonPrimitive(src.getClass().getSimpleName()));
        result.add("properties", context.serialize(src, src.getClass()));
        return result;
    }

    @Override
    public RangeCalculationStrategy deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        // For now, always deserialize to SimpleFactorRangeStrategy
        // This can be expanded later to handle different strategy types
        return new SimpleFactorRangeStrategy();
    }
} 