package com.example.employee_management.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SystemHealthScheduler {
    private static final Logger logger = LoggerFactory.getLogger(SystemHealthScheduler.class);

    @Scheduled(fixedRate = 30000)
    public void logSystemStatus(){
        logger.info("System running");
    }

//    @Scheduled(fixedRate = 30000)      // Chạy lặp mỗi 30s, tính từ lúc BẮT ĐẦU lần chạy trước
//    @Scheduled(fixedDelay = 30000)     // Chạy lặp mỗi 30s, tính từ lúc KẾT THÚC lần chạy trước
//    @Scheduled(cron = "0 0 0 * * ?")   // Chạy theo biểu thức cron — ví dụ này là "mỗi ngày lúc 00:00"
}
