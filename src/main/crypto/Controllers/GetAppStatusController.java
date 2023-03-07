package crypto.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetAppStatusController {
    @GetMapping("/status")
    public String index() {
        return "Make this work :)";
    }
}