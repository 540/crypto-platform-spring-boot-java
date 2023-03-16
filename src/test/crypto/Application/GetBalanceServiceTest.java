package crypto.Application;

import crypto.ApiErrorException;
import crypto.Domain.Coin;
import crypto.Domain.WalletCoin;
import crypto.Repositories.CryptoRepository;
import crypto.Repositories.DBWalletCoinRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetBalanceServiceTest {
    private DBWalletCoinRepository dbWalletCoinRepository;
    private CryptoRepository cryptoRepository;
    private GetBalanceService getCoinsService;

    @BeforeEach
    void setUp() {
        dbWalletCoinRepository = mock(DBWalletCoinRepository.class);
        cryptoRepository = mock(CryptoRepository.class);
        getCoinsService = new GetBalanceService(dbWalletCoinRepository, cryptoRepository);
    }

    @Test
    void getsNoCoins() throws ApiErrorException {
        List<WalletCoin> walletCoins = new ArrayList<>();
        walletCoins.add(new WalletCoin(1, "name", 1, 1));
        List<Coin> coins = new ArrayList<>();
        coins.add(new Coin("another_name", 50));

        when(dbWalletCoinRepository.getPurchasedCoins()).thenReturn(walletCoins);
        when(cryptoRepository.getCoins()).thenReturn(coins);

        assertThrowsExactly(CoinNotFoundException.class, () -> getCoinsService.execute());
    }

    @Test
    void errorIfThereAreNoPurchasedCoins() {
        List<WalletCoin> walletCoins = new ArrayList<>();

        when(dbWalletCoinRepository.getPurchasedCoins()).thenReturn(walletCoins);

        assertThrowsExactly(NoPurchasedCoinsException.class, () -> getCoinsService.execute());
    }

    @Test
    void errorIfApiNotWorking() throws ApiErrorException {
        List<WalletCoin> walletCoins = new ArrayList<>();
        walletCoins.add(new WalletCoin(1, "name", 1, 1));

        when(dbWalletCoinRepository.getPurchasedCoins()).thenReturn(walletCoins);
        when(cryptoRepository.getCoins()).thenThrow(new ApiErrorException("Service not available"));

        assertThrowsExactly(ApiErrorException.class, () -> getCoinsService.execute());
    }

    @Test
    void calculatesBalance() throws ApiErrorException, CoinNotFoundException, NoPurchasedCoinsException {
        List<WalletCoin> walletCoins = new ArrayList<>();
        walletCoins.add(new WalletCoin(90, "Bitcoin", 20, 3));
        walletCoins.add(new WalletCoin(80, "Ethereum", 5, 5));

        List<Coin> coins = new ArrayList<>();
        coins.add(new Coin( "Bitcoin", 50));
        coins.add(new Coin("Ethereum", 10));

        when(dbWalletCoinRepository.getPurchasedCoins()).thenReturn(walletCoins);
        when(cryptoRepository.getCoins()).thenReturn(coins);

        float balance = getCoinsService.execute();

        assertEquals(115, balance);
    }

}
