package cn.linbin.worklog.service.analysis.impl;

import cn.linbin.worklog.dao.AnalysisDao;
import cn.linbin.worklog.service.analysis.AnalysisService;
import cn.linbin.worklog.utils.LbMap;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalysisServiceImpl implements AnalysisService{

    @Autowired
    private AnalysisDao analysisDao;

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
}
