package test;

import com.google.gson.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonUtil {
    public JsonUtil() {
    }

    public static String beanToString(Object object) {
        return object == null ? null : getGson(false).toJson(object);
    }

    public static String beanToStringNulls(Object object) {
        return object == null ? null : getGson(true).toJson(object);
    }

    public static JsonObject stringToJson(String jsonString){
        if (jsonString == null) {
            return null;
        } else {
            try {
                return (new JsonParser()).parse(jsonString).getAsJsonObject();
            } catch (RuntimeException var2) {
            }
        }
        return null;
    }

    public static JsonObject beanToJson(Object object){
        if (object == null) {
            return null;
        } else {
            try {
                return (new JsonParser()).parse(getGson(true).toJson(object)).getAsJsonObject();
            } catch (RuntimeException var2) {
            }
        }
        return null;
    }

    public static JsonArray stringToJsonArray(String jsonString){
        if (jsonString == null) {
            return null;
        } else {
            try {
                return (new JsonParser()).parse(jsonString).getAsJsonArray();
            } catch (RuntimeException var2) {
            }
        }
        return null;
    }

    public static JsonArray listToJsonArray(List list) {
        return list == null ? null : (new JsonParser()).parse(getGson(true).toJson(list)).getAsJsonArray();
    }

    public static JsonObject getJsonObject() {
        return new JsonObject();
    }

    public static Gson getGson(boolean serializeNulls) {
        Gson gson = new Gson();
        return serializeNulls ? gson.newBuilder().serializeNulls().create() : gson;
    }

    public static <T> List<T> stringToList(String jsonString, Class<T[]> clazz) {
        if (jsonString == null) {
            return null;
        } else {
            try {
                return new ArrayList(Arrays.asList((Object[]) getGson(true).fromJson(jsonString, clazz)));
            } catch (RuntimeException var3) {
            }
        }
        return null;
    }


    public static <T> T stringToBean(String jsonString, Class<T> clazz){
        try {
            return getGson(true).fromJson(jsonString, clazz);
        } catch (RuntimeException var3) {
        }
        return null;
    }

    public static class Builder {
        private JsonObject json = JsonUtil.getJsonObject();

        public Builder() {
        }

        public Builder add(String key, Number number) {
            this.json.addProperty(key, number);
            return this;
        }

        public Builder add(String key, String string) {
            this.json.addProperty(key, string);
            return this;
        }

        public Builder add(String key, Character character) {
            this.json.addProperty(key, character);
            return this;
        }

        public Builder add(String key, Boolean bool) {
            this.json.addProperty(key, bool);
            return this;
        }

        public Builder add(String key, JsonElement jsonElement) {
            this.json.add(key, jsonElement);
            return this;
        }

        public Builder add(String key, List list) {
            this.json.add(key, JsonUtil.listToJsonArray(list));
            return this;
        }

        public Builder add(String key, Object value) {
            this.json.add(key, (new JsonParser()).parse(JsonUtil.getGson(true).toJson(value)).getAsJsonObject());
            return this;
        }

        public JsonObject build() {
            return this.json;
        }
    }
}
