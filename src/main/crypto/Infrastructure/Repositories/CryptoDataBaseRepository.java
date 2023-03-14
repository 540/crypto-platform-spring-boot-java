package crypto.Infrastructure.Repositories;

import crypto.Domain.Coin;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DBCoinRepository {
    public List<Coin> getPurchasedCoins() {
        List<Coin> coins = new ArrayList<>();

        coins.add(new Coin("Bitcoin", 20, 3));
        coins.add(new Coin("Ethereum", 5, 5));

        return coins;
    }
}
