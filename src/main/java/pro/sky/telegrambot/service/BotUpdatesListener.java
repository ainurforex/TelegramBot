package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repoitory.NotificationTaskRepository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class BotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(BotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;

    @Autowired
    private NotificationTaskRepository notificationTaskRepository;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            long chatId = (update.message().chat().id());
            logger.info("Processing update: {}", update);
            String updateMassage = update.message().text();

            if (updateMassage != null) {
                if (updateMassage.equals("/start")) {
                    sendStartMasage(chatId);
                } else {
                    sendTrueMassage(notificationSave(chatId, updateMassage),chatId);
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private boolean notificationSave(long chatId, String updateMassage) {
        Pattern pattern = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W+\\w+]+)");
        Matcher matcher = pattern.matcher(updateMassage);
        boolean mattchOk = false;
        if (matcher.matches()) {
            String date = matcher.group(1);
            String massage = matcher.group(3);

            NotificationTask notificationTask = new NotificationTask();
            notificationTask.setNotificationText(massage);
            notificationTask.setChatId(chatId);
            notificationTask.setNotificationDateTime(date);
            notificationTaskRepository.save(notificationTask);
            mattchOk = true;
            logger.info("Save notification: {}", notificationTask);
        }
        return mattchOk;
    }

    private void sendStartMasage(long chatId) {
        String massage = "Написать в чат задачу в формате:\n" +
                "01.01.2022 20:00 Купить торт\n" +
                "в указанное время бот напишет сообщение \n" +
                "Купить торт";
        SendMessage message = new SendMessage(chatId, massage);
        telegramBot.execute(message);
        logger.info("Send Start massage to chatId: {}", chatId);
    }

    private void sendTrueMassage(boolean result, long chatId) {
        SendMessage message;
        if (result) {
            message = new SendMessage(chatId, "Задача принята");
        } else {
            message = new SendMessage(chatId, "Неверный формат");
        }
        telegramBot.execute(message);
    }


}
