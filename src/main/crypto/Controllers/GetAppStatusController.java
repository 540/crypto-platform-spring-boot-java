package crypto.Controllers;

import crypto.ApiErrorException;
import crypto.Repositories.CryptoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetAppStatusController {
    private final CryptoRepository cryptoRepository;

    public GetAppStatusController(CryptoRepository cryptoRepository) {
        this.cryptoRepository = cryptoRepository;
    }

    @GetMapping("/status")
    public String index() {
        try{
            cryptoRepository.getCoins();
        } catch (ApiErrorException apiErrorException){
            return "We are down!";
        }

        return "We are alive!";
    }
}