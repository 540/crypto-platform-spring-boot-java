package crypto.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetGreetingController {
    @GetMapping("/greeting")
    public String index() {
        return "Make this work :)";
    }
}