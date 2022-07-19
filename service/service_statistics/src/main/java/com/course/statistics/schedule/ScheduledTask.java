package com.course.statistics.schedule;


import com.course.statistics.service.StatisticsDailyService;
import com.course.statistics.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class ScheduledTask {

    @Autowired
    private StatisticsDailyService statisticsDailyService;



    /**
     * 每隔5秒执行一次这个方法
     */
    // @Scheduled(cron = "0/5 * * * * ?")
    public void task(){
        System.out.println("task执行");
    }

    /**
     * 在每天凌晨一点，把前一天的数据进行数据查询添加
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void registerCountTask(){
        statisticsDailyService.countRegister(DateUtil.formatDate(DateUtil.addDays(new Date(),-1)));
    }
}
