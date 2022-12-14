package crypto.Application;

import crypto.Domain.Coin;
import crypto.Infrastructure.Repositories.CryptoRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GetCoinsServiceTest {
    @Test
    void getsNoCoins() {
        GetCoinsService getCoinsService = new GetCoinsService();
        CryptoRepository cryptoRepository = mock(CryptoRepository.class);
        List<String> coins = getCoinsService.execute(cryptoRepository);

        when(cryptoRepository.getCoins()).thenReturn(new ArrayList<>());

        verify(cryptoRepository, times(1)).getCoins();
        assertEquals(new ArrayList<String>(), coins);
    }

    @Test
    void getsCoins() {
        GetCoinsService getCoinsService = new GetCoinsService();
        CryptoRepository cryptoRepository = mock(CryptoRepository.class);
        List<Coin> remoteCoins = new ArrayList<>();
        remoteCoins.add(new Coin("Coin1"));
        remoteCoins.add(new Coin("Coin2"));
        List<String> expectedCoinsNames = new ArrayList<>();
        expectedCoinsNames.add("Coin1");
        expectedCoinsNames.add("Coin2");

        when(cryptoRepository.getCoins()).thenReturn(remoteCoins);

        List<String> coins = getCoinsService.execute(cryptoRepository);

        verify(cryptoRepository, times(1)).getCoins();
        assertEquals(expectedCoinsNames, coins);
    }
}
