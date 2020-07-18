package cn.linbin.worklog.service.system.impl;

import cn.linbin.worklog.dao.AreaDao;
import cn.linbin.worklog.service.system.SelectService;
import cn.linbin.worklog.utils.LbMap;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SelectServiceImpl implements SelectService {

    @Autowired
    private AreaDao areaDao;

    @Override
    public PageInfo findArea(int pageIndex, int pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        List<LbMap> list = areaDao.findArea();
        return new PageInfo(list);
    }
}
