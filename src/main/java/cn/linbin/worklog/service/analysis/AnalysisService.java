package cn.linbin.worklog.service.analysis;

import cn.linbin.worklog.utils.LbMap;
import com.github.pagehelper.PageInfo;

public interface AnalysisService {

    PageInfo<LbMap> workHourList(int pageIndex, int pageSize, LbMap param);

    PageInfo<LbMap> workDetailList(int pageIndex, int pageSize, LbMap param);
}
