package crypto.Application;

import crypto.Domain.Coin;
import crypto.Infrastructure.Repositories.CryptoRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetCoinsService {
    public List<String> execute(CryptoRepository cryptoRepository) {
        return cryptoRepository.getCoins()
                .stream()
                .map(Coin::getName)
                .collect(Collectors.toList());
    }
}
