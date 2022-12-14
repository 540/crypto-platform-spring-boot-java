package crypto.Infrastructure.Controllers;

import crypto.Application.GetCoinsService;
import crypto.Infrastructure.Repositories.CryptoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetCoinsController {
    @Autowired
    private GetCoinsService getCoinsService;

    @Autowired
    private CryptoRepository cryptoRepository;

    @GetMapping("/coins")
    public String index() {
        return getCoinsService.execute(cryptoRepository).toString();
    }
}