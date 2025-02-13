package utils.generalUtils;

public enum AnsiColorsUtil {
  RED("\u001B[31m"),
  RED_BOLD("\u001B[1;31m"),
  GREEN("\u001B[32m"),
  YELLOW("\u001B[33m"),
  BLUE("\u001B[34m"),
  MAGENTA("\u001B[35m"),
  CYAN("\u001B[36m"),
  TURQUOISE("\u001B[38;5;48m"),
  BLACK("\u001B[30m"),
  DARK_GRAY("\u001B[38;5;242m"),
  MEDIUM_GRAY("\u001B[38;5;250m"),
  BOLD_WHITE("\u001B[1;97m"),
  WHITE("\u001B[38;5;254m"),
  RESET("\u001B[0m");
  
  private final String colorCode;
  
  AnsiColorsUtil(String code) {
    this.colorCode = code;
  }
  
  public String getCode() {
    return colorCode;
  }
}
