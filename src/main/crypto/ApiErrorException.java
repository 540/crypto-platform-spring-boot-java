package crypto;

public class ApiErrorException extends Throwable {
    public ApiErrorException(String errorMessage) {
        super(errorMessage);
    }
}
