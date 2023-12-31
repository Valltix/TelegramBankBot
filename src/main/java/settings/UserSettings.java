package settings;

import banks.CurrencyName;
import notification.NotificationScheduler;
import org.quartz.SchedulerException;

import java.time.LocalTime;
import java.util.List;

public class UserSettings {

    private Long chatId;

    private String bankName; // can be Bank object instead (Bank: id, name, etc)

    private Integer afterPoint;

    private List<CurrencyName> currencies;

    private LocalTime notification;

    private Boolean notificationOn;

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

    public List<CurrencyName> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<CurrencyName> currencies) {
        this.currencies = currencies;
    }

    public LocalTime getNotification() {
        return notification;
    }
    public void setNotification(LocalTime notification) {
        this.notification = notification;
    }



    public Boolean getNotificationOn() {
        return notificationOn;
    }
    public void setNotificationOn(Boolean notificationOn) {
        this.notificationOn = notificationOn;
    }
    @Override
    public String toString() {
        return "Банк: *" + bankName + "*\nЗнаків після коми: *" + afterPoint + "*\nВалюта: *" + String.join(", ", currencies.stream().map(Enum::name).toList()) + "*" + "\nОповіщення: *" + notification + "*";
    }

}