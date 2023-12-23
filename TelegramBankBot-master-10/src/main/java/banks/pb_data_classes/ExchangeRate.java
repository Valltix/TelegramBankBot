package banks.pb_data_classes;

import banks.CurrencyName;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExchangeRate {
    private String date;
    private String bank;
    @SerializedName("baseCurrency")
    private String baseCurrency;

    @SerializedName("baseCurrencyLit")
    private CurrencyName baseCurrencyLitCode;

    @SerializedName("exchangeRate")
    private List<PBCurrency> currencies;

    @Override
    public String toString() {
        return "currencies=" + currencies;
    }

    public List<PBCurrency> getCurrencies() {
        return currencies;
    }
}
