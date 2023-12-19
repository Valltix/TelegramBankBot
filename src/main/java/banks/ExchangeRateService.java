package banks;


import settings.UserSettings;
import utils.Utils;
import java.math.BigDecimal;
import java.util.List;

public class ExchangeRateService {

    private static List<ExchangeRate> getExchangeRate(CurrencyName ratedCurrency, UserSettings settings) {
        return settings.getCurrencies().stream()
                .map(currency -> getRates(settings, ratedCurrency, currency))
                .toList();
    }

    private static ExchangeRate getRates(UserSettings settings, CurrencyName ratedCurrency, CurrencyName baseCurrency) {

        // TODO: connect to BankApi according to the settings.getBankName() and get current rate according to the currency.
        //  buy and sell prices should be returned as BigDecimal (e.g. Pair<BigDecimal, BigDecimal> can be used or object).

        BigDecimal buyRate = new BigDecimal("37.364948736"); // example of received exchange rate from bank
        BigDecimal sellRate = new BigDecimal("37.574378679");
        var ap = settings.getAfterPoint();
        return new ExchangeRate(baseCurrency, ratedCurrency, Utils.getScaledBigDecimal(buyRate, ap), Utils.getScaledBigDecimal(sellRate, ap));
    }

    public static String getExchangeRateMessage(CurrencyName currency, UserSettings settings) {

        StringBuilder sb = new StringBuilder("Курс у *" + settings.getBankName() + "*: ");
        List<ExchangeRate> rates = getExchangeRate(currency, settings);

        for (var rate : rates) {
            sb.append(rate.getBaseCurrency()).append(" / ").append(currency);
            sb.append("\nПокупка: *").append(rate.getBuy()).append("*\nПродаж: *").append(rate.getSell()).append("*");
            sb.append("\n\n");
        }

        return sb.toString();
    }
}
