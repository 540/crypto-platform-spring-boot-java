package crypto.Repositories;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import crypto.ApiErrorException;
import crypto.Client;
import crypto.Domain.Coin;
import crypto.Repositories.CryptoRepository;
import org.junit.jupiter.api.Test;

import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CryptoRepositoryTest {
    @Test
    void getsNoCoins() throws ApiErrorException {
        Client client = mock(Client.class);
        CryptoRepository cryptoRepository = new CryptoRepository(client);

        when(client.send(any(HttpRequest.class))).thenReturn(null);

        List<Coin> coins = cryptoRepository.getCoins();

        assertEquals(new ArrayList<Coin>(), coins);
    }

    @Test
    void getsCoins() throws ApiErrorException {
        Client client = mock(Client.class);
        CryptoRepository cryptoRepository = new CryptoRepository(client);
        List<Coin> expectedCoins = new ArrayList<>();
        expectedCoins.add(new Coin("Bitcoin", 50));
        String jsonString = "{data: [{name: Bitcoin}]}";
        JsonObject jsonResponse = (JsonObject) JsonParser.parseString(jsonString);

        when(client.send(any(HttpRequest.class))).thenReturn(jsonResponse);
        List<Coin> coins = cryptoRepository.getCoins();

        assertEquals(expectedCoins.get(0).getName(), coins.get(0).getName());
    }
}
