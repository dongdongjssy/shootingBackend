package io.goose.quartz.task;

import org.springframework.stereotype.Component;

/**
 * 定时任务调度测试
 * 
 * @author goose
 */
@Component("gooseTask")
public class GooseTask
{
    public void gooseParams(String params)
    {
        System.out.println("执行有参方法：" + params);
    }

    public void gooseNoParams()
    {
        System.out.println("执行无参方法");
    }
}
