package cn.linbin.worklog.service.system.impl;

import cn.linbin.worklog.dao.AreaDao;
import cn.linbin.worklog.dao.CustomerDao;
import cn.linbin.worklog.dao.UserDao;
import cn.linbin.worklog.dao.VersionDao;
import cn.linbin.worklog.domain.Version;
import cn.linbin.worklog.service.system.SelectService;
import cn.linbin.worklog.utils.LbMap;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SelectServiceImpl implements SelectService {

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private VersionDao versionDao;

    @Autowired
    private UserDao userDao;

    @Override
    public PageInfo findArea(int page, int limit) {
        PageHelper.startPage(page, limit);
        List<LbMap> list = areaDao.findArea();
        return new PageInfo(list);
    }

    @Override
    public PageInfo<LbMap> findCustomer(int page, int limit) {
        PageHelper.startPage(page, limit);
        List<LbMap> list = customerDao.findCustomer();
        return new PageInfo(list);
    }

    @Override
    public List<Version> findVersion() {
        QueryWrapper<Version> wrapper = new QueryWrapper<>();
        wrapper.eq("DELETE_FLAG", 0);
        List<Version> list = versionDao.selectList(wrapper);
        return list;
    }

    @Override
    public PageInfo<LbMap> findUser(int page, int limit) {
        PageHelper.startPage(page, limit);
        List<LbMap> list = areaDao.findUser();
        return new PageInfo(list);
    }
}
