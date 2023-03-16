package crypto.Domain;

public class WalletCoin {
    private int id;
    private final String name;
    private float usdValue;
    private int cryptoQuantity;

    public WalletCoin(int id, String name, float usdValue, int cryptoQuantity) {
        this.id = id;
        this.name = name;
        this.usdValue = usdValue;
        this.cryptoQuantity = cryptoQuantity;
    }

    public String getName() {
        return name;
    }

    public float getCryptoQuantity() {
        return cryptoQuantity;
    }

    public float getValue() {
        return usdValue;
    }
}