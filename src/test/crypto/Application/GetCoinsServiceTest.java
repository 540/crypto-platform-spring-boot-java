package crypto.Application;

import crypto.Domain.Coin;
import crypto.Infrastructure.Repositories.CryptoApiRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GetCoinsServiceTest {
    private CryptoApiRepository cryptoApiRepository;
    private GetCoinsService getCoinsService;

    @BeforeEach
    void setUp() {
    cryptoApiRepository = mock(CryptoApiRepository.class);
    getCoinsService = new GetCoinsService(cryptoApiRepository);
    }

    @Test
    void getsNoCoins() {
        List<String> coins = getCoinsService.execute();

        when(cryptoApiRepository.getCoins()).thenReturn(new ArrayList<>());

        verify(cryptoApiRepository, times(1)).getCoins();
        assertEquals(new ArrayList<String>(), coins);
    }

    @Test
    void getsCoins() {
        List<Coin> remoteCoins = new ArrayList<>();
        remoteCoins.add(new Coin("Coin1", 5, 5));
        remoteCoins.add(new Coin("Coin2", 5, 5));
        List<String> expectedCoinsNames = new ArrayList<>();
        expectedCoinsNames.add("Coin1");
        expectedCoinsNames.add("Coin2");

        when(cryptoApiRepository.getCoins()).thenReturn(remoteCoins);

        List<String> coins = getCoinsService.execute();

        verify(cryptoApiRepository, times(1)).getCoins();
        assertEquals(expectedCoinsNames, coins);
    }
}
