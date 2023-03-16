package crypto.Application;

public class NoPurchasedCoinsException extends Throwable {
    public NoPurchasedCoinsException() {
        super("Not purchased coins exception");
    }
}