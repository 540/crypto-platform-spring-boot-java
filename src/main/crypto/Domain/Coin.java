package crypto.Domain;

public class Coin {
    private final String name;
    private final int id;
    private int usdValue;

    public Coin(int id, String name, int usdValue) {
        this.id = id;
        this.name = name;
        this.usdValue = usdValue;
    }

    public String getName() {
        return name;
    }
}
