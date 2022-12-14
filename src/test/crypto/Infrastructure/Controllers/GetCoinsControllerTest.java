package crypto.Infrastructure.Controllers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import crypto.Infrastructure.Repositories.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.http.HttpRequest;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
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
    public void getsEmptyCoinsList() throws Exception {
        String jsonString = "{data: []}";
        JsonObject jsonResponse = (JsonObject) JsonParser.parseString(jsonString);

        when(client.send(any(HttpRequest.class))).thenReturn(jsonResponse);

        mvc.perform(MockMvcRequestBuilders.get("/coins").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[]")));
        verify(client, times(1)).send(any());
    }

    @Test
    public void getsCoinsList() throws Exception {
        String jsonString = "{data: [{name: Bitcoin, price_usd: 6456.52}, {name: Ethereum, price_usd: 3500}]}";
        JsonObject jsonResponse = (JsonObject) JsonParser.parseString(jsonString);

        when(client.send(any(HttpRequest.class))).thenReturn(jsonResponse);

        mvc.perform(MockMvcRequestBuilders.get("/coins").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[Bitcoin, Ethereum]")));
        verify(client, times(1)).send(any());
    }
}
