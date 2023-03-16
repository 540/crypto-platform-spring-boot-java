package crypto.Application;

public class CoinNotFoundException extends Throwable {
    public CoinNotFoundException() {
        super("Coin not found exception");
    }
}