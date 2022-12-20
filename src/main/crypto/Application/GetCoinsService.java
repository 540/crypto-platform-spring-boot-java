package crypto.Application;

import crypto.Domain.Coin;
import crypto.Infrastructure.Repositories.CryptoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetCoinsService {
    CryptoRepository cryptoRepository;

    public GetCoinsService(CryptoRepository cryptoRepository) {
        this.cryptoRepository = cryptoRepository;
    }

    public List<String> execute() {
        return cryptoRepository.getCoins()
                .stream()
                .map(Coin::getName)
                .collect(Collectors.toList());
    }
}
