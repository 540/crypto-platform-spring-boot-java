package crypto.Controllers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import crypto.Client;
import crypto.Repositories.MixedCryptoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.net.http.HttpRequest;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class GetAppStatusControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    Client client;

    @Test
    public void systemIsDown() throws Exception {
        when(client.send(any(HttpRequest.class))).thenThrow(IOException.class);

        mvc.perform(MockMvcRequestBuilders.get("/status").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("We are down!")));
    }

    @Test
    public void systemIsUp() throws Exception {
        String jsonString = "{data: [{name: Bitcoin}]}";
        JsonObject cryptoApiResponse = (JsonObject) JsonParser.parseString(jsonString);

        when(client.send(any(HttpRequest.class))).thenReturn(cryptoApiResponse);

        mvc.perform(MockMvcRequestBuilders.get("/status").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("We are alive!")));
    }
}
