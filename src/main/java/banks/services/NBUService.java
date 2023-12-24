package banks.services;

import banks.BankService;
import banks.CurrencyName;
import banks.nbu_data_classes.NBUCurrency;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

public class NBUService implements BankService {
    private static final String URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";
    private final HttpClient httpClient;
    private final Gson gson;

    public NBUService() {
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    private List<NBUCurrency> getAllExchangeRate(){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .GET()
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            Type nbuListType = new TypeToken<List<NBUCurrency>>(){}.getType();
            return gson.fromJson(responseBody, nbuListType);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public BigDecimal getRate(CurrencyName currencyName, int scale) {
        List<NBUCurrency> currencies = getAllExchangeRate();
        Optional<NBUCurrency> cur = currencies.stream().filter(c -> c.getCc() == currencyName).findFirst();
        if(cur.isPresent()){
            BigDecimal saleRate = new BigDecimal(cur.get().getRate());
            return saleRate.setScale(scale, RoundingMode.HALF_UP);
        }
        return BigDecimal.ZERO;
    }
}
