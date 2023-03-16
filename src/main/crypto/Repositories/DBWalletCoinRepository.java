package crypto.Repositories;

import crypto.Domain.Coin;
import crypto.Domain.WalletCoin;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DBWalletCoinRepository {
    public List<WalletCoin> getPurchasedCoins() {
        List<WalletCoin> walletCoin = new ArrayList<>();

        walletCoin.add(new WalletCoin(90,"Bitcoin", 20, 3));
        walletCoin.add(new WalletCoin(80, "Ethereum", 5, 5));

        return walletCoin;
    }
}