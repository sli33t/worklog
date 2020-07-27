package cn.linbin.worklog.task;

import cn.linbin.worklog.service.analysis.AnalysisService;
import cn.linbin.worklog.utils.LbMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class WorkHourJob extends QuartzJobBean {

    @Autowired
    private AnalysisService analysisService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
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
