package utils;

public class InputCleanerUtil {
  public static String cleanWhitespacesAround(String name) {
    return name != null ? name.trim() : null;
  }
  
  public static Float cleanFloatFromCharacters(String input) {
    if (input != null) {
      String cleaned = input.replaceAll("[^\\d.]", "");
      return cleaned.isEmpty() ? null : Float.parseFloat(cleaned);
    }
    return null;
  }
  
  public static Double cleanDoubleFromCharacters(String input) {
    if (input != null) {
      String cleaned = input.replaceAll("[^\\d.]", "");
      return cleaned.isEmpty() ? null : Double.parseDouble(cleaned);
    }
    return null;
  }
  
  public static Integer cleanIntegerFromCharacters(String input) {
    if (input != null) {
      String cleaned = input.replaceAll("[^\\d.]", "");
      return cleaned.isEmpty() ? null : Integer.parseInt(cleaned);
    }
    return null;
  }
  
  public static Boolean formatYesOrNoToBoolean(String input) {
    if (input != null) {
      String cleaned = input.trim().toLowerCase();
      switch (cleaned) {
        case "y":
        case "yes":
        case "ja":
          return true;
        case "n":
        case "no":
        case "nein":
          return false;
        default:
          return null;
      }
    }
    return null;
  }
  
}
