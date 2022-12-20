package crypto.Infrastructure.Repositories;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import crypto.Domain.Coin;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Component
public class MixedCryptoRepository {
    public List<Coin> getCoins() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder(URI.create("https://api.coinlore.net/api/tickers/"))
                .header("accept", "application/json")
                .build();

        Coin[] coins;
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonObject jsonObject = new Gson().fromJson(response.body(), JsonObject.class);
            JsonElement data = jsonObject.get("data");
            coins = new Gson().fromJson(data, Coin[].class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return List.of(coins);
    }
}
