package info.albertcode.utils.exception;

/**
 * @Description:
 * @Author: Albert Shen
 */

public class CustomException extends Exception{
    String message;

    public CustomException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
