package pro.sky.telegrambot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class NotificationTask {
    @Id
    @GeneratedValue
    private Long id;
    private Long chatId;
    private String NotificationText;
    private String NotificationDateTime;

    @Override
    public String toString() {
        return "NotificationTask{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", NotificationText='" + NotificationText + '\'' +
                ", NotificationDateTime='" + NotificationDateTime + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NotificationTask)) return false;
        NotificationTask that = (NotificationTask) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getChatId(), that.getChatId()) && Objects.equals(getNotificationText(), that.getNotificationText()) && Objects.equals(getNotificationDateTime(), that.getNotificationDateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getChatId(), getNotificationText(), getNotificationDateTime());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getNotificationText() {
        return NotificationText;
    }

    public void setNotificationText(String notificationText) {
        NotificationText = notificationText;
    }

    public String getNotificationDateTime() {
        return NotificationDateTime;
    }

    public void setNotificationDateTime(String notificationDateTime) {
        NotificationDateTime = notificationDateTime;
    }


}
