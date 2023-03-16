package crypto.Application;

import crypto.ApiErrorException;
import crypto.Domain.Coin;
import crypto.Domain.WalletCoin;
import crypto.Repositories.CryptoRepository;
import crypto.Repositories.DBWalletCoinRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetBalanceService {
    private final DBWalletCoinRepository dbWalletCoinRepository;
    private final CryptoRepository cryptoRepository;

    public GetBalanceService(DBWalletCoinRepository dbWalletCoinRepository, CryptoRepository cryptoRepository) {
        this.dbWalletCoinRepository = dbWalletCoinRepository;
        this.cryptoRepository = cryptoRepository;
    }

    public float execute() throws CoinNotFoundException, ApiErrorException, NoPurchasedCoinsException {
        List<WalletCoin> walletCoins = dbWalletCoinRepository.getPurchasedCoins();
        if (walletCoins.isEmpty()) {
            throw new NoPurchasedCoinsException();
        }
        List<Coin> coins = cryptoRepository.getCoins();

        if (!walletCoins
                .stream()
                .allMatch(walletCoin -> coins
                        .stream()
                        .map(Coin::getName)
                        .toList()
                        .contains(walletCoin.getName()))) {
            throw new CoinNotFoundException();
        }

        float balance = 0;
        for (WalletCoin walletCoin : walletCoins) {
            balance += coins
                    .stream()
                    .filter(coin -> coin
                            .getName()
                            .equals(walletCoin.getName()))
                    .findAny()
                    .get().getUsdValue() * walletCoin.getCryptoQuantity() - walletCoin.getValue() * walletCoin.getCryptoQuantity();
        }

        return balance;
    }
}
