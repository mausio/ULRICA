package utils.generalUtils;

import com.google.gson.Gson;

public class GsonUtil {
  public static String objectToGsonString(Object object, Gson gson) {
    return gson.toJson(object);
  }
}
