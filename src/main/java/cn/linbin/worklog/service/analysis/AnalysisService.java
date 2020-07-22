package cn.linbin.worklog.service.analysis;

import cn.linbin.worklog.utils.LbMap;
import com.github.pagehelper.PageInfo;

public interface AnalysisService {

    PageInfo<LbMap> workHourList(int page, int limit, LbMap param);

    PageInfo<LbMap> workDetailList(int page, int limit, LbMap param);
}
