package crypto.Infrastructure.Controllers;

import crypto.Infrastructure.Repositories.CryptoApiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetStatusController {
    @Autowired
    private CryptoApiRepository cryptoApiRepository;

    @GetMapping("/status")
    public String index() {
        if (cryptoApiRepository.getCoins().isEmpty()) {
            return "We are down!";
        }

        return "We are alive!";
    }
}