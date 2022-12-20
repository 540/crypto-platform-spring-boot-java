package crypto.Controllers;

import crypto.Repositories.CryptoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetStatusController {
    @Autowired
    private CryptoRepository cryptoRepository;

    @GetMapping("/status")
    public String index() {
        if (cryptoRepository.getCoins().isEmpty()){
            return "We are down!";
        }

        return "We are alive!";
    }
}