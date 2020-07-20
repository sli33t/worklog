package cn.linbin.worklog.service.system;

import cn.linbin.worklog.domain.Version;
import cn.linbin.worklog.utils.LbMap;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface SelectService {

    PageInfo<LbMap> findArea(int pageIndex, int pageSize);

    PageInfo<LbMap> findCustomer(int pageIndex, int pageSize);

    List<Version> findVersion();

    PageInfo<LbMap> findUser(int pageIndex, int pageSize);
}
