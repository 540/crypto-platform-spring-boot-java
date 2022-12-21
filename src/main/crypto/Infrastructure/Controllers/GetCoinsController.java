package crypto.Infrastructure.Controllers;

import crypto.Application.GetCoinsService;
import crypto.Infrastructure.Repositories.CryptoApiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetCoinsController {
    @Autowired
    private GetCoinsService getCoinsService;

    @GetMapping("/coins")
    public String index() {
        return getCoinsService.execute().toString();
    }
}