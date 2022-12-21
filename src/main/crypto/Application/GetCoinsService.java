package crypto.Application;

import crypto.Domain.Coin;
import crypto.Infrastructure.Repositories.CryptoApiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetCoinsService {
    @Autowired
    CryptoApiRepository cryptoApiRepository;

    public GetCoinsService(CryptoApiRepository cryptoApiRepository) {
        this.cryptoApiRepository = cryptoApiRepository;
    }

    public List<String> execute() {
        return cryptoApiRepository.getCoins()
                .stream()
                .map(Coin::getName)
                .collect(Collectors.toList());
    }
}
