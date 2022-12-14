package crypto.Infrastructure.Repositories;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import crypto.Domain.Coin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.List;

@Repository
public class CryptoRepository {
    @Autowired
    private final Client client;

    public CryptoRepository(Client client) {
        this.client = client;
    }

    public List<Coin> getCoins() {
        HttpRequest request = HttpRequest
                .newBuilder(URI.create("https://api.coinlore.net/api/tickers/"))
                .header("accept", "application/json")
                .build();

        Coin[] coins = new Coin[0];
        JsonObject jsonObject = client.send(request);
        if (jsonObject != null){
            JsonElement data = jsonObject.get("data");
            coins = new Gson().fromJson(data, Coin[].class);
        }

        return List.of(coins);
    }
}
