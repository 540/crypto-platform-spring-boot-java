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
public class GetAppStatusControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private Client client;

    @Test
    public void systemIsDown() throws Exception, ApiErrorException {
        HttpRequest request = HttpRequest
                .newBuilder(URI.create("https://api.coinlore.net/api/tickers/"))
                .header("accept", "application/json")
                .build();

        when(client.send(request)).thenThrow(new ApiErrorException("Api error"));

        mvc.perform(MockMvcRequestBuilders.get("/status").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("We are down!")));
    }

    @Test
    public void systemIsUp() throws Exception, ApiErrorException {
        String jsonString = "{data: [{name: Bitcoin}]}";
        JsonObject jsonResponse = (JsonObject) JsonParser.parseString(jsonString);
        HttpRequest request = HttpRequest
                .newBuilder(URI.create("https://api.coinlore.net/api/tickers/"))
                .header("accept", "application/json")
                .build();

        when(client.send(request)).thenReturn(jsonResponse);

        mvc.perform(MockMvcRequestBuilders.get("/status").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("We are alive!")));
    }
}
