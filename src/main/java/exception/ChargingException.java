package exception;

public class ChargingException extends Exception{
  public ChargingException() {
  }
  
  public ChargingException(String message){
    super(message);
  }
}
