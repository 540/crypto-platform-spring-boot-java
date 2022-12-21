package crypto.Infrastructure.Repositories;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import crypto.Domain.Coin;
import org.junit.jupiter.api.Test;

import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CryptoApiRepositoryTest {
    @Test
    void getsNoCoins() {
        Client client = mock(Client.class);
        CryptoApiRepository cryptoApiRepository = new CryptoApiRepository(client);

        when(client.send(any(HttpRequest.class))).thenReturn(null);

        List<Coin> coins = cryptoApiRepository.getCoins();

        assertEquals(new ArrayList<Coin>(), coins);
    }

    @Test
    void getsCoins() {
        Client client = mock(Client.class);
        CryptoApiRepository cryptoApiRepository = new CryptoApiRepository(client);
        List<Coin> expectedCoins = new ArrayList<>();
        expectedCoins.add(new Coin("Bitcoin", 5, 5));
        String jsonString = "{data: [{name: Bitcoin}]}";
        JsonObject jsonResponse = (JsonObject) JsonParser.parseString(jsonString);

        when(client.send(any(HttpRequest.class))).thenReturn(jsonResponse);
        List<Coin> coins = cryptoApiRepository.getCoins();

        assertEquals(expectedCoins.get(0).getName(), coins.get(0).getName());
    }
}
