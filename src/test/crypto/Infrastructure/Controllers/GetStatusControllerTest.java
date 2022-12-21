package crypto.Infrastructure.Controllers;

import crypto.Domain.Coin;
import crypto.Infrastructure.Repositories.CryptoApiRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GetStatusControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CryptoApiRepository client;

    @Test
    public void systemIsDown() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/status").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("We are down!")));
    }

    @Test
    public void systemIsUp() throws Exception {
        List<Coin> coins = new ArrayList<>();
        coins.add(new Coin("coin", 5, 5));
        when(client.getCoins()).thenReturn(coins);

        mvc.perform(MockMvcRequestBuilders.get("/status").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("We are alive!")));
    }
}