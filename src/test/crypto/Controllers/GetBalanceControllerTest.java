package crypto.Controllers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import crypto.ApiErrorException;
import crypto.Client;
import crypto.Domain.WalletCoin;
import crypto.Repositories.DBWalletCoinRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockReset;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GetBalanceControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean(reset= MockReset.AFTER)
    private Client client;

    @MockBean
    private DBWalletCoinRepository dbWalletCoinRepository;

    @Test
    public void serviceNotAvailable() throws Exception, ApiErrorException {
        List<WalletCoin> coinsWallet = new ArrayList<>();
        coinsWallet.add(new WalletCoin(1, "name", 1, 1));

        when(dbWalletCoinRepository.getPurchasedCoins()).thenReturn(coinsWallet);
        when(client.send(getRequest())).thenThrow(new ApiErrorException("Service not available"));

        mvc.perform(MockMvcRequestBuilders.get("/balance").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Servicio no disponible")));
    }

    @Test
    public void walletIsEmpty() throws Exception, ApiErrorException {
        when(dbWalletCoinRepository.getPurchasedCoins()).thenReturn(new ArrayList<>());

        mvc.perform(MockMvcRequestBuilders.get("/balance").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("No hay monedas en la cartera")));

        verify(client, never()).send(any());
    }

    @Test
    public void coinDoesNotExistInAPI() throws Exception, ApiErrorException {
        List<WalletCoin> coinsWallet = new ArrayList<>();
        coinsWallet.add(new WalletCoin(1, "name", 1, 1));
        String jsonString = "{\"data\":[{\"id\":\"90\",\"symbol\":\"BTC\",\"name\":\"Bitcoin\",\"nameid\":\"bitcoin\",\"rank\":1,\"price_usd\":\"24608.13\",\"percent_change_24h\":\"1.25\",\"percent_change_1h\":\"-0.67\",\"percent_change_7d\":\"11.70\",\"price_btc\":\"1.00\",\"market_cap_usd\":\"474644026521.14\",\"volume24\":53985070328.96783,\"volume24a\":48465224708.67262,\"csupply\":\"19288102.00\",\"tsupply\":\"19288102\",\"msupply\":\"21000000\"},{\"id\":\"80\",\"symbol\":\"ETH\",\"name\":\"Ethereum\",\"nameid\":\"ethereum\",\"rank\":2,\"price_usd\":\"1690.74\",\"percent_change_24h\":\"1.36\",\"percent_change_1h\":\"-0.33\",\"percent_change_7d\":\"9.18\",\"price_btc\":\"0.068748\",\"market_cap_usd\":\"206902165202.09\",\"volume24\":14424034025.272745,\"volume24a\":12833058289.63249,\"csupply\":\"122374074.00\",\"tsupply\":\"122374074\",\"msupply\":\"\"}]}";
        JsonObject jsonResponse = (JsonObject) JsonParser.parseString(jsonString);

        when(dbWalletCoinRepository.getPurchasedCoins()).thenReturn(coinsWallet);
        when(client.send(getRequest())).thenReturn(jsonResponse);

        mvc.perform(MockMvcRequestBuilders.get("/balance").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Balance no disponible, una de las monedas no existe")));
    }

    @Test
    public void getsBalance() throws Exception, ApiErrorException {
        List<WalletCoin> coinsWallet = new ArrayList<>();
        coinsWallet.add(new WalletCoin(90, "Bitcoin", 20, 3));
        coinsWallet.add(new WalletCoin(80, "Ethereum", 5, 5));
        String jsonString = "{\"data\":[{\"id\":\"90\",\"symbol\":\"BTC\",\"name\":\"Bitcoin\",\"nameid\":\"bitcoin\",\"rank\":1,\"price_usd\":\"50\",\"percent_change_24h\":\"1.25\",\"percent_change_1h\":\"-0.67\",\"percent_change_7d\":\"11.70\",\"price_btc\":\"1.00\",\"market_cap_usd\":\"474644026521.14\",\"volume24\":53985070328.96783,\"volume24a\":48465224708.67262,\"csupply\":\"19288102.00\",\"tsupply\":\"19288102\",\"msupply\":\"21000000\"},{\"id\":\"80\",\"symbol\":\"ETH\",\"name\":\"Ethereum\",\"nameid\":\"ethereum\",\"rank\":2,\"price_usd\":\"10\",\"percent_change_24h\":\"1.36\",\"percent_change_1h\":\"-0.33\",\"percent_change_7d\":\"9.18\",\"price_btc\":\"0.068748\",\"market_cap_usd\":\"206902165202.09\",\"volume24\":14424034025.272745,\"volume24a\":12833058289.63249,\"csupply\":\"122374074.00\",\"tsupply\":\"122374074\",\"msupply\":\"\"}]}";
        JsonObject jsonResponse = (JsonObject) JsonParser.parseString(jsonString);

        when(dbWalletCoinRepository.getPurchasedCoins()).thenReturn(coinsWallet);
        when(client.send(getRequest())).thenReturn(jsonResponse);

        mvc.perform(MockMvcRequestBuilders.get("/balance").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("115.0 usd")));
    }

    private static HttpRequest getRequest() {
        return HttpRequest
                .newBuilder(URI.create("https://api.coinlore.net/api/tickers/"))
                .header("accept", "application/json")
                .build();
    }
}
