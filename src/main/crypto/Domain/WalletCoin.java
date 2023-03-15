package crypto.Domain;

public class WalletCoin {
    private int id;
    private final String name;
    private int usdValue;
    private int cryptoQuantity;

    public WalletCoin(int id, String name, int usdValue, int cryptoQuantity) {
        this.id = id;
        this.name = name;
        this.usdValue = usdValue;
        this.cryptoQuantity = cryptoQuantity;
    }

    public String getName() {
        return name;
    }
}
