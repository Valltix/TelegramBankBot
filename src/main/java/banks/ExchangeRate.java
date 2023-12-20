package banks;

import java.math.BigDecimal;

public class ExchangeRate {

    private CurrencyName baseCurrency;

    private CurrencyName ratedCurrency;

    private BigDecimal buy;

    private BigDecimal sell;

    public ExchangeRate(CurrencyName baseCurrency, CurrencyName ratedCurrency, BigDecimal buy, BigDecimal sell) {
        this.baseCurrency = baseCurrency;
        this.ratedCurrency = ratedCurrency;
        this.buy = buy;
        this.sell = sell;
    }

    public ExchangeRate() {
    }

    public CurrencyName getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(CurrencyName baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public CurrencyName getRatedCurrency() {
        return ratedCurrency;
    }

    public void setRatedCurrency(CurrencyName ratedCurrency) {
        this.ratedCurrency = ratedCurrency;
    }

    public BigDecimal getBuy() {
        return buy;
    }

    public void setBuy(BigDecimal buy) {
        this.buy = buy;
    }

    public BigDecimal getSell() {
        return sell;
    }

    public void setSell(BigDecimal sell) {
        this.sell = sell;
    }
}
