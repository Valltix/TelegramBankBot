package banks;


import banks.repositories.MonobankRepository;
import banks.repositories.NBURepository;
import banks.repositories.PrivatBankRepository;
import settings.UserSettings;
import utils.Utils;
import java.math.BigDecimal;
import java.util.List;

public class ExchangeRateService {

    // Бере з налаштувань список валют і для кожної з них викликає метод getRate, отримуючи об'єкт ExchangeRate.
    // Зберігає ці об'єкти у список і повертає.
    private static List<ExchangeRate> getExchangeRate(CurrencyName ratedCurrency, UserSettings settings) {
        return settings.getCurrencies().stream()
                .map(currency -> getRates(settings, ratedCurrency, currency))
                .toList();
    }

    // Створює новий об'єкт ExchangeRate з данними отриманими з банку.
    private static ExchangeRate getRates(UserSettings settings, CurrencyName ratedCurrency, CurrencyName baseCurrency) {
        BankService bankService;
        BigDecimal buyRate;
        BigDecimal sellRate;
        switch (settings.getBankName()){
            case "NBU":
                bankService = new NBURepository();
                break;
            case "Privat":
                bankService = new PrivatBankRepository();
                break;
            case "Monobank":
                bankService = new MonobankRepository();
                break;
            default:
                throw new IllegalArgumentException("Unknown bank: " + settings.getBankName());
        }

        buyRate = new BigDecimal(bankService.getBuyRate(baseCurrency, settings.getAfterPoint()));
        sellRate = new BigDecimal(bankService.getSellRate(baseCurrency, settings.getAfterPoint()));


//        BigDecimal buyRate = new BigDecimal("37.364948736"); // example of received exchange rate from bank
//        BigDecimal sellRate = new BigDecimal("37.574378679");
        var ap = settings.getAfterPoint();
        return new ExchangeRate(baseCurrency, ratedCurrency, Utils.getScaledBigDecimal(buyRate, ap), Utils.getScaledBigDecimal(sellRate, ap));
    }

    // цей метод проходить по всім рейтам, що отримані методом getExchangeRate() і створює красиве повідомлення для чату.
    public static String getExchangeRateMessage(CurrencyName currency, UserSettings settings) {

        StringBuilder sb = new StringBuilder("Курс на сьогодні у *" + settings.getBankName() + "* : \n\n");
        List<ExchangeRate> rates = getExchangeRate(currency, settings);

        for (var rate : rates) {
            sb.append(rate.getBaseCurrency()).append(" / ").append(currency);
            sb.append("\nКупівля: *").append(rate.getBuy()).append("* \nПродаж: *").append(rate.getSell()).append("*");
            sb.append("\n\n");
        }
        return sb.toString();
    }
}
