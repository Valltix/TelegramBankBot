package banks;

public enum CurrencyName {
    UAH(980),
    EUR(978),
    USD(840),
    PLN(985);

    private final int iso4217Code;

    CurrencyName(int iso4217Code) {
        this.iso4217Code = iso4217Code;
    }
    public int getIso4217Code() {
        return iso4217Code;
    }
    public static CurrencyName getByIso4217Code(int iso4217Code) {
        for (CurrencyName currency : values()) {
            if (currency.getIso4217Code() == iso4217Code) {
                return currency;
            }
        }
        return null;
    }
}

