package banks;

import java.math.BigDecimal;

public class ExchangeRate {

    private Currency baseCurrency;

    private Currency ratedCurrency;

    private BigDecimal buy;

    private BigDecimal sell;

    public ExchangeRate(Currency baseCurrency, Currency ratedCurrency, BigDecimal buy, BigDecimal sell) {
        this.baseCurrency = baseCurrency;
        this.ratedCurrency = ratedCurrency;
        this.buy = buy;
        this.sell = sell;
    }

    public ExchangeRate() {
    }

    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(Currency baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public Currency getRatedCurrency() {
        return ratedCurrency;
    }

    public void setRatedCurrency(Currency ratedCurrency) {
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
