package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repoitory.NotificationTaskRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;


@Service
public class BotScheduledNotificator {
    @Autowired
    private TelegramBot telegramBot;

    @Autowired
    private NotificationTaskRepository notificationTaskRepository;

    private static final String DATE_FORMATTER = "dd.MM.yyyy HH:mm";

    private Logger logger = LoggerFactory.getLogger(BotUpdatesListener.class);

    @Scheduled(cron = "0 0/1 * * * *")
    public void everyMinuteScheduler() {

        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMATTER));
        Collection<NotificationTask> notificationTasks = notificationTaskRepository.getNotificationTaskByDateTime(dateTime);

        if (notificationTasks != null) {
            notificationTasks.stream().forEach(e -> {
                SendMessage message = new SendMessage(e.getChatId(), e.getNotificationText());
                telegramBot.execute(message);
                logger.info("Send notifications to chatId: {}", e.getChatId() + " " + e.getNotificationText());
            });
            logger.info("Send all notifications in Date&Time: {}", dateTime);
        }
    }
}
