package banks.pb_data_classes;

import banks.CurrencyName;
import com.google.gson.annotations.SerializedName;

public class PBCurrency {
    @SerializedName("baseCurrency")
    private String baseCurrency;

    @SerializedName("currency")
    private CurrencyName currency;

    @SerializedName("saleRate")
    private double saleRate;

    @SerializedName("purchaseRate")
    private double purchaseRate;

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public CurrencyName getCurrency() {
        return currency;
    }

    public double getSaleRate() {
        return saleRate;
    }

    public double getPurchaseRate() {
        return purchaseRate;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "baseCurrency='" + baseCurrency + '\'' +
                ", currency='" + currency + '\'' +
                ", saleRate=" + saleRate +
                ", purchaseRate=" + purchaseRate +
                '}';
    }
}
