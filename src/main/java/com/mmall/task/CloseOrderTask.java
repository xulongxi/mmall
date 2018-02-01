package com.mmall.task;

import com.mmall.common.Const;
import com.mmall.service.IOrderService;
import com.mmall.util.PropertiesUtil;
import com.mmall.util.RedisShardedPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 项目名：   mmall
 * 包名：     com.mmall.task
 * 文件名：   CloseOrderTask
 * 创建时间： 2018/1/29 15:07
 *
 * @author Longxi XU
 * 描述：     定时关单
 */
@Component
@Slf4j
public class CloseOrderTask {
    @Autowired
    IOrderService iOrderService;

//    @Scheduled(cron = "0 */1 * * * ?")//每个分钟整数倍启动
    public void closeOrderTaskV1() {
        int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.time.hour", "2"));
        log.info("定时任务V1执行");
//        iOrderService.closeOrder(hour);
    }

    @Scheduled(cron = "0 */1 * * * ?")//每个分钟整数倍启动
    public void closeOrderTaskV2() {
        log.info("定时任务V2执行");
        Long lockTimeout = Long.parseLong(PropertiesUtil.getProperty("lock.timeout", "5000"));
        Long setnxResult = RedisShardedPoolUtil.setnx(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, String.valueOf(System.currentTimeMillis() + lockTimeout));
        if (setnxResult != null && setnxResult.intValue() == 1) {
            closeOrder(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        } else {
            log.info("没有获得分布式锁:{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);

        }
        log.info("关闭订单定时任务结束");

    }


    private void closeOrder(String lockName) {
        RedisShardedPoolUtil.expire(lockName, 5);//有效期50秒，防止死锁
        log.info("获取{},ThreadName:{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, Thread.currentThread().getName());
        int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.time.hour", "2"));
        iOrderService.closeOrder(hour);
        RedisShardedPoolUtil.del(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        log.info("释放{},ThreadName:{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, Thread.currentThread().getName());
        log.info("===============================");
    }

}
