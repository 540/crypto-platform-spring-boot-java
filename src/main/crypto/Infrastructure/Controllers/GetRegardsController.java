package crypto.Infrastructure.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetRegardsController {
    @GetMapping("/regards")
    public String index() {
        return "Make this work :)";
    }
}