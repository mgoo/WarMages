package main.exceptions;

public class ItemNotInRangeException extends RuntimeException {

  public ItemNotInRangeException(String message) {
    super(message);
  }
}
