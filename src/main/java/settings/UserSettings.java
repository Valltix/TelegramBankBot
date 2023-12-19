package settings;

import banks.Currency;
import org.telegram.telegrambots.meta.api.objects.User;

import java.time.LocalTime;
import java.util.List;

public class UserSettings {

    private Long chatId;

    private String bankName; // can be Bank object instead (Bank: id, name, etc)

    private Integer afterPoint;

    private List<Currency> currencies;

    private LocalTime notification;

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Integer getAfterPoint() {
        return afterPoint;
    }

    public void setAfterPoint(Integer afterPoint) {
        this.afterPoint = afterPoint;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public LocalTime getNotification() {
        return notification;
    }

    public void setNotification(LocalTime notification) {
        this.notification = notification;
    }

    @Override
    public String toString() {
        return "Банк: *" + bankName + "*\nЗнаків після коми: *" + afterPoint + "*\nВалюти: *" + String.join(", ", currencies.stream().map(Enum::name).toList()) + "*" + "\nОповіщення: *" + notification + "*";
    }

}
