package crypto.Controllers;

import crypto.ApiErrorException;
import crypto.Application.CoinNotFoundException;
import crypto.Application.GetBalanceService;
import crypto.Application.NoPurchasedCoinsException;
import crypto.Repositories.CryptoRepository;
import crypto.Repositories.DBWalletCoinRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetBalanceController {
    private final CryptoRepository cryptoRepository;
    private DBWalletCoinRepository dbWalletCoinRepository;
    private GetBalanceService getBalanceService;

    public GetBalanceController(CryptoRepository cryptoRepository, DBWalletCoinRepository dbWalletCoinRepository, GetBalanceService getBalanceService) {
        this.cryptoRepository = cryptoRepository;
        this.dbWalletCoinRepository = dbWalletCoinRepository;
        this.getBalanceService = getBalanceService;
    }

    @GetMapping("/balance")
    public String getCoins() {
        try {
            return getBalanceService.execute() + " usd";
        } catch (CoinNotFoundException coinNotFoundException) {
            return "Balance no disponible, una de las monedas no existe";
        } catch (ApiErrorException apiErrorException) {
            return "Servicio no disponible";
        } catch (NoPurchasedCoinsException noPurchasedCoinsException) {
            return "No hay monedas en la cartera";
        }
    }
}