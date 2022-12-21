package crypto.Domain;

public class Coin {
    private final String name;
    private int usdValue;
    private int cryptoQuantity;

    public Coin(String name, int usdValue, int cryptoQuantity) {
        this.name = name;
        this.usdValue = usdValue;
        this.cryptoQuantity = cryptoQuantity;
    }

    public String getName() {
        return name;
    }
}
