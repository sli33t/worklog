package cn.linbin.worklog.dao;

import cn.linbin.worklog.domain.Area;
import cn.linbin.worklog.utils.LbMap;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AreaDao extends BaseMapper<Area> {

    @Select("SELECT AREA_NAME as name, AREA_ID as id, CREATE_TIME " +
            "FROM TB_AREA WHERE COALESCE(DELETE_FLAG, 0) = 0 ORDER BY AREA_NAME")
    List<LbMap> findArea();
}
