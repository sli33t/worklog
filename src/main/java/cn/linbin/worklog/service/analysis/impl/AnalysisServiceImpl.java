package cn.linbin.worklog.service.analysis.impl;

import cn.linbin.worklog.dao.AnalysisDao;
import cn.linbin.worklog.dao.WorkHourDao;
import cn.linbin.worklog.domain.WorkHour;
import cn.linbin.worklog.service.analysis.AnalysisService;
import cn.linbin.worklog.utils.LbMap;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class AnalysisServiceImpl implements AnalysisService{

    @Autowired
    private AnalysisDao analysisDao;

    @Autowired
    private WorkHourDao workHourDao;

    /**
     * 查询工时
     * @param page
     * @param limit
     * @param param
     * @return
     */
    @Override
    public PageInfo<LbMap> workHourList(int page, int limit, LbMap param) {
        PageHelper.startPage(page, limit);
        List<LbMap> list = analysisDao.workHourList(param);
        return new PageInfo<>(list);
    }

    /**
     * 查询明细工时
     * @param page
     * @param limit
     * @param param
     * @return
     */
    @Override
    public PageInfo<LbMap> workDetailList(int page, int limit, LbMap param) {
        PageHelper.startPage(page, limit);
        List<LbMap> list = analysisDao.workDetailList(param);
        return new PageInfo<>(list);
    }

    /**
     * 查询开发总数及开发完成数
     * @return
     */
    @Override
    public LbMap queryDevCount(LbMap param) {
        LbMap devMap = analysisDao.queryDevCount(param);
        if (devMap==null){
            devMap = new LbMap();
            devMap.put("devCount", 0);
            devMap.put("devFinishCount", 0);
            devMap.put("devRate", 0);
        }
        return devMap;
    }

    /**
     * 查询测试总数及测试完成数
     * @param param
     * @return
     */
    @Override
    public LbMap queryTestCount(LbMap param) {
        LbMap testMap = analysisDao.queryTestCount(param);
        if (testMap==null){
            testMap = new LbMap();
            testMap.put("testCount", 0);
            testMap.put("testFinishCount", 0);
            testMap.put("testRate", 0);
        }
        return testMap;
    }

    /**
     * 查询总数及完成数
     * @param param
     * @return
     */
    @Override
    public LbMap queryAllCount(LbMap param) {
        LbMap allMap = analysisDao.queryAllCount(param);
        if (allMap==null){
            allMap = new LbMap();
            allMap.put("allCount", 0);
            allMap.put("allFinishCount", 0);
            allMap.put("allRate", 0);
        }
        return allMap;
    }

    @Override
    public LbMap queryFeedbackList(LbMap param) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 0);
        Date year = calendar.getTime();
        String thisYear = dateFormat.format(year);

        //去年
        calendar.add(Calendar.YEAR, -1);
        year = calendar.getTime();
        String lastYear = dateFormat.format(year);

        //前年，由于Calendar.YEAR此时已经是去年了，所以-1就是前年
        calendar.add(Calendar.YEAR, -1);
        year = calendar.getTime();
        String beforeLastYear = dateFormat.format(year);

        param.put("thisYear", thisYear);
        param.put("lastYear", lastYear);
        param.put("beforeLastYear", beforeLastYear);

        LbMap resultMap = analysisDao.queryFeedbackList(param);
        if (resultMap==null){
            resultMap = new LbMap();
            //0代表需求，1代表bug
            resultMap.put("thisYear0", thisYear);
            resultMap.put("lastYear0", lastYear);
            resultMap.put("beforeLastYear0", beforeLastYear);
            resultMap.put("thisYear1", thisYear);
            resultMap.put("lastYear1", lastYear);
            resultMap.put("beforeLastYear1", beforeLastYear);
        }

        //需要将对比的年度传给前台
        resultMap.put("thisYear", thisYear);
        resultMap.put("lastYear", lastYear);
        resultMap.put("beforeLastYear", beforeLastYear);

        return resultMap;
    }

    @Override
    public LbMap queryFeedbackTypeList(LbMap param) {
        LbMap resultMap = analysisDao.queryFeedbackTypeList(param);
        if (resultMap==null){
            resultMap = new LbMap();
            //0代表需求，1代表bug
            resultMap.put("NEED0", 0);
            resultMap.put("BUG0", 0);
            resultMap.put("NEED1", 0);
            resultMap.put("BUG1", 0);
        }
        return resultMap;
    }

    @Override
    public LbMap queryPriority(LbMap param) {
        LbMap resultMap = analysisDao.queryPriority(param);
        if (resultMap==null){
            resultMap = new LbMap();
            //0代表需求，1代表bug
            resultMap.put("PRIORITY0", 0);
            resultMap.put("PRIORITY1", 0);
            resultMap.put("PRIORITY2", 0);
            resultMap.put("PRIORITY3", 0);
        }
        return resultMap;
    }

    @Override
    public List<LbMap> queryVersionList(LbMap param) {
        return analysisDao.queryVersionList(param);
    }

    @Override
    public LbMap workHourDoJob() throws Exception {
        try {
            List<LbMap> list = analysisDao.workHourList(new LbMap());
            for (LbMap map : list) {
                WorkHour workHour = new WorkHour();
                workHour.setUserId(map.getString("userId"));
                workHour.setDevelopUser(map.getString("developUser"));
                workHour.setWorkCount(map.getDouble("workCount"));
                workHour.setPlanHour(map.getDouble("planHour"));
                workHour.setRealHour(map.getDouble("realHour"));
                workHour.setDelayHour(map.getDouble("delayHour"));
                if (workHourDao.insert(workHour)!=1){
                    return LbMap.failResult("写入临时表时出错");
                }
            }
            return LbMap.successResult("自动任务执行成功");
        }catch (Exception e){
            return LbMap.failResult("自动任务执行失败"+e.getMessage());
        }
    }
}
