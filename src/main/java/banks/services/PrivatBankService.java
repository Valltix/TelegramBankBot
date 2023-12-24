package banks.services;

import banks.BankService;
import banks.CurrencyName;
import banks.pb_data_classes.PBCurrency;
import banks.pb_data_classes.ExchangeRate;
import com.google.gson.Gson;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class PrivatBankService implements BankService {
    private static final String URL = "https://api.privatbank.ua/p24api/exchange_rates?date=";
    private final HttpClient httpClient;
    private final Gson gson;
    private String date;

    public PrivatBankService() {
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();
        LocalDateTime lcd = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        this.date = lcd.format(formatter);
    }
    private ExchangeRate getAllExchangeRate(){
        String url = URL + date;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            ExchangeRate exchangeRate = gson.fromJson(responseBody, ExchangeRate.class);
            return exchangeRate;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public BigDecimal getRate(CurrencyName currencyName, int scale){
        Optional<ExchangeRate> exchangeRate = Optional.ofNullable(getAllExchangeRate());
        if(exchangeRate.isPresent()){
            Optional<PBCurrency> cur = exchangeRate.get().getCurrencies().stream()
                    .filter(c -> c.getCurrency() == currencyName).findFirst();
            if (cur.isPresent()) {
                BigDecimal saleRate = new BigDecimal(cur.get().getSaleRate());
                return saleRate.setScale(scale, RoundingMode.HALF_UP);
            }
        }
        return BigDecimal.ZERO;
    }
}
