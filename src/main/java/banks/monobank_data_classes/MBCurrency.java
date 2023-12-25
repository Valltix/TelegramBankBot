package banks.monobank_data_classes;

public class MBCurrency {
    private int currencyCodeA;
    private int currencyCodeB;
    private long date;
    private double rateBuy;
    private double rateSell;

    public int getCurrencyCodeA() {
        return currencyCodeA;
    }
    public double getRateBuy() {
        return rateBuy;
    }
    public double getRateSell() {
        return rateSell;
    }

    public void setCurrencyCodeA(int currencyCodeA) {
        this.currencyCodeA = currencyCodeA;
    }

    public void setCurrencyCodeB(int currencyCodeB) {
        this.currencyCodeB = currencyCodeB;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setRateBuy(double rateBuy) {
        this.rateBuy = rateBuy;
    }
    public void setRateSell(double rateSell) {
        this.rateSell = rateSell;
    }
    @Override
    public String toString() {
        return "MBCurrency{" +
                "currencyCodeA=" + currencyCodeA +
                ", currencyCodeB=" + currencyCodeB +
                ", date=" + date +
                ", rateBuy=" + rateBuy +
                ", rateSell=" + rateSell +
                '}';
    }
}
