package cn.linbin.worklog.task;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
public class TestJob {

    public void excuteTextJob(){
        System.out.println("=================================测试第二种自动任务");
    }
}
