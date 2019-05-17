package my.test.input_cxf_bundle;

public class QueueResponseException extends Exception {

  public QueueResponseException(String message) {
    super(message);
  }

  public QueueResponseException(String message, Throwable cause) {
    super(message, cause);
  }
}
