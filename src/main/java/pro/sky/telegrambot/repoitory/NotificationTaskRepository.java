package pro.sky.telegrambot.repoitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.telegrambot.model.NotificationTask;

import java.util.Collection;


public interface NotificationTaskRepository extends JpaRepository<NotificationTask, Long> {

    @Query(value = "SELECT * from notification_task WHERE notification_date_time LIKE :DateTime", nativeQuery = true)
    Collection<NotificationTask> getNotificationTaskByDateTime(String DateTime);
}

