package net.wintang.zooapp.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OrderCleanupScheduler {

    private final EntityManager entityManager;

    @Autowired
    public OrderCleanupScheduler(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Scheduled(cron = "00 45 16 * * *") // Run daily at 00:00:00 UTC if on Azure
    @Transactional
    public void removeExpiredOrders() {
        Query query = entityManager.createNativeQuery("EXEC RemoveExpiredOrders");
        query.executeUpdate();
    }

}
