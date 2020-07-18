package cn.linbin.worklog.service.system;

import com.github.pagehelper.PageInfo;

public interface SelectService {

    PageInfo findArea(int pageIndex, int pageSize);
}
