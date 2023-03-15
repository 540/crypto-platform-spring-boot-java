package crypto.Controllers;

import crypto.ApiErrorException;
import crypto.Domain.Coin;
import crypto.Repositories.CryptoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class GetCoinsController {
    private final CryptoRepository cryptoRepository;

    public GetCoinsController(CryptoRepository cryptoRepository) {
        this.cryptoRepository = cryptoRepository;
    }

    @GetMapping("/coins")
    public String getCoins() throws ApiErrorException {
        return cryptoRepository.getCoins()
                .stream()
                .map(Coin::getName)
                .toList()
                .toString();
    }
}