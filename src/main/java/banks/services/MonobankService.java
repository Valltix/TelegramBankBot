package banks.services;

import banks.BankService;
import banks.CurrencyName;
import banks.monobank_data_classes.MBCurrency;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MonobankService implements BankService {
    private static final String URL = "https://api.monobank.ua/bank/currency";
    private final HttpClient httpClient;
    private final Gson gson;

    public MonobankService() {
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    private List<MBCurrency> getAllExchangeRate(){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .GET()
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            JsonArray jsonArray = JsonParser.parseString(responseBody).getAsJsonArray();

            List<MBCurrency> currencies = new ArrayList<>();

            for (var jsonElement : jsonArray) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();

                if (jsonObject.has("rateCross")) {
                    MBCurrency currency = new MBCurrency();
                    currency.setCurrencyCodeA(jsonObject.get("currencyCodeA").getAsInt());
                    currency.setCurrencyCodeB(jsonObject.get("currencyCodeB").getAsInt());
                    currency.setDate(jsonObject.get("date").getAsLong());
                    currency.setRateBuy(jsonObject.get("rateCross").getAsDouble());
                    currencies.add(currency);
                } else {
                    currencies.add(gson.fromJson(jsonObject, MBCurrency.class));
                }
            }

            return currencies;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public BigDecimal getRate(CurrencyName currencyName, int scale) {
        List<MBCurrency> currencies = getAllExchangeRate();
        Optional<MBCurrency> cur = currencies.stream().filter(c -> c.getCurrencyCodeA() == currencyName.getIso4217Code()).findFirst();
        if(cur.isPresent()){
            BigDecimal saleRate = new BigDecimal(cur.get().getRateBuy());
            return new BigDecimal(cur.get().getRateBuy()).setScale(scale, RoundingMode.HALF_UP);
        }
        return BigDecimal.ZERO;
    }
}
