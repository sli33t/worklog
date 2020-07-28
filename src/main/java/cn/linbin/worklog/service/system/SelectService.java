package cn.linbin.worklog.service.system;

import cn.linbin.worklog.domain.po.Version;
import cn.linbin.worklog.utils.LbMap;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface SelectService {

    PageInfo<LbMap> findArea(int page, int limit);

    PageInfo<LbMap> findCustomer(int page, int limit);

    List<Version> findVersion();

    PageInfo<LbMap> findUser(int page, int limit);
}
