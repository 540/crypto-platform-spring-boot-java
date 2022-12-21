package crypto.Controllers;

import crypto.Client;
import crypto.Repositories.CryptoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class GetAppStatusController {
    @Autowired
    CryptoRepository cryptoRepository;

    @GetMapping("/status")
    public String index() {
        try {
            cryptoRepository.getCoins();
        } catch (IOException | InterruptedException e) {
            return "We are down!";
        }

        return "We are alive!";
    }
}