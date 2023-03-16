package crypto.Domain;

public class Coin {
    private final String name;
    private final float price_usd;

    public Coin(String name, float price_usd) {
        this.name = name;
        this.price_usd = price_usd;
    }

    public String getName() {
        return name;
    }

    public float getUsdValue() {
        return price_usd;
    }
}
