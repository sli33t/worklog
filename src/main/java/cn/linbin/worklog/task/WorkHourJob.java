package cn.linbin.worklog.task;

import cn.linbin.worklog.service.analysis.AnalysisService;
import cn.linbin.worklog.utils.LbMap;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;


@EnableScheduling
@Component
public class WorkHourJob {

    @Autowired
    private AnalysisService analysisService;

    protected void executeWorkHourJob() throws Exception {
        LbMap result = new LbMap();
        try {
            result = analysisService.workHourDoJob();
            System.out.println("======================================="+result.toString());

            if (!result.getBoolean("result")){
                System.out.println(result.getString("msg"));
            }else {
                System.out.println("自动任务执行成功！");
            }
        } catch (JobExecutionException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
