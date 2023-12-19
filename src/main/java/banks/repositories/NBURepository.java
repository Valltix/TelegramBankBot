package banks.repositories;

import banks.BankService;
import banks.CurrencyName;
import banks.monobank_data_classes.MBCurrency;
import banks.nbu_data_classes.NBUCurrency;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

public class NBURepository implements BankService {
    private static final String URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";
    private final HttpClient httpClient;
    private final Gson gson;

    public NBURepository() {
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
    public double getRate(CurrencyName currencyName, int scale) {
        List<NBUCurrency> currencies = getAllExchangeRate();
        Optional<NBUCurrency> cur = currencies.stream().filter(c -> c.getCc() == currencyName).findFirst();
        if(cur.isPresent()){
            return Math.round(cur.get().getRate() * Math.pow(10, scale)) / Math.pow(10, scale);
        }
        return 0;
    }
}
