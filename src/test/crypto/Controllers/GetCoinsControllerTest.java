package crypto.Controllers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import crypto.ApiErrorException;
import crypto.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.URI;
import java.net.http.HttpRequest;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GetCoinsControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private Client client;

    @Test
    public void getsNoCoins() throws Exception, ApiErrorException {
        String jsonString = "{data: []}";
        JsonObject jsonResponse = (JsonObject) JsonParser.parseString(jsonString);

        when(client.send(getRequest())).thenReturn(jsonResponse);

        mvc.perform(MockMvcRequestBuilders.get("/coins").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[]")));
    }

    @Test
    public void getsCoins() throws Exception, ApiErrorException {
        String jsonString = "{\"data\":[{\"id\":\"90\",\"symbol\":\"BTC\",\"name\":\"Bitcoin\",\"nameid\":\"bitcoin\",\"rank\":1,\"price_usd\":\"24608.13\",\"percent_change_24h\":\"1.25\",\"percent_change_1h\":\"-0.67\",\"percent_change_7d\":\"11.70\",\"price_btc\":\"1.00\",\"market_cap_usd\":\"474644026521.14\",\"volume24\":53985070328.96783,\"volume24a\":48465224708.67262,\"csupply\":\"19288102.00\",\"tsupply\":\"19288102\",\"msupply\":\"21000000\"},{\"id\":\"80\",\"symbol\":\"ETH\",\"name\":\"Ethereum\",\"nameid\":\"ethereum\",\"rank\":2,\"price_usd\":\"1690.74\",\"percent_change_24h\":\"1.36\",\"percent_change_1h\":\"-0.33\",\"percent_change_7d\":\"9.18\",\"price_btc\":\"0.068748\",\"market_cap_usd\":\"206902165202.09\",\"volume24\":14424034025.272745,\"volume24a\":12833058289.63249,\"csupply\":\"122374074.00\",\"tsupply\":\"122374074\",\"msupply\":\"\"}]}";
        JsonObject jsonResponse = (JsonObject) JsonParser.parseString(jsonString);

        when(client.send(getRequest())).thenReturn(jsonResponse);

        mvc.perform(MockMvcRequestBuilders.get("/coins").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[Bitcoin, Ethereum]")));
    }

    private static HttpRequest getRequest() {
        return HttpRequest
                .newBuilder(URI.create("https://api.coinlore.net/api/tickers/"))
                .header("accept", "application/json")
                .build();
    }
}
