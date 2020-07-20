package cn.linbin.worklog.service.devTask.impl;

import cn.linbin.worklog.dao.DevTaskDao;
import cn.linbin.worklog.service.devTask.DevTaskService;
import cn.linbin.worklog.utils.LbMap;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DevTaskServiceImpl implements DevTaskService{

    @Autowired
    private DevTaskDao devTaskDao;

    @Override
    public PageInfo<LbMap> findAll(int pageIndex, int pageSize, LbMap param) {
        PageHelper.startPage(pageIndex, pageSize);
        List<LbMap> list = devTaskDao.findAll(param);
        return new PageInfo(list);
    }
}
