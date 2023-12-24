package banks;

import java.math.BigDecimal;

public interface BankService {
    public BigDecimal getRate(CurrencyName currencyName, int scale);
}
