package exception;

public class BusinessLogicException extends Exception{
  public BusinessLogicException() {
  }
  
  public BusinessLogicException(String message) {
    super(message);
  }
}
