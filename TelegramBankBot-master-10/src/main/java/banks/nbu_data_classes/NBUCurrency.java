package banks.nbu_data_classes;

import banks.CurrencyName;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;

public class NBUCurrency {
    private int r030;
    private String txt;
    private double rate;
    private CurrencyName cc;
    @SerializedName("exchangedate")
    private String exchangeDate;

    public int getR030() {
        return r030;
    }

    public String getTxt() {
        return txt;
    }

    public double getRate() {
        return rate;
    }

    public CurrencyName getCc() {
        return cc;
    }

    public String getExchangeDate() {
        return exchangeDate;
    }

    @Override
    public String toString() {
        return "NBUCurrency{" +
                "r030=" + r030 +
                ", txt='" + txt + '\'' +
                ", rate=" + rate +
                ", cc=" + cc +
                ", exchangeDate='" + exchangeDate + '\'' +
                '}';
    }
}
