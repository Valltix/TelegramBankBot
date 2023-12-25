package banks;

public interface BankService {
    public double getBuyRate(CurrencyName currencyName, int scale);
    public double getSellRate(CurrencyName currencyName, int scale);
}
