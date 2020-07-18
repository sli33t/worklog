package cn.linbin.worklog.dao;

import cn.linbin.worklog.domain.File;
import cn.linbin.worklog.utils.LbMap;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FileDao extends BaseMapper<File>{

    @Select("<script>" +
            "SELECT TB_FILE.FILE_ID, TB_FILE.FILE_NAME, TB_FILE.FILE_URL, TB_FILE.CREATE_TIME, TB_FILE.DELETE_DATE, TB_FILE.DELETE_FLAG, " +
            "TB_FILE.USER_ID, TB_FILE.FILE_TYPE, TB_USER.USER_NAME " +
            "FROM TB_FILE INNER JOIN TB_USER ON TB_FILE.USER_ID = TB_USER.USER_ID " +
            "WHERE COALESCE(TB_FILE.DELETE_FLAG, 0) = 0 " +

            "<if test='param.fileName != null and param.fileName != &quot;&quot;'>" +
                " AND TB_FILE.FILE_NAME like CONCAT('%',#{param.fileName},'%') " +
            "</if>" +

            "<if test='param.fileType != null and param.fileType != &quot;&quot;'>" +
                "<if test='param.fileType == 1'>" +
                    /*员工*/
                    " AND TB_FILE.FILE_TYPE in (1, 2) "+
                "</if>"+
            "</if>" +

            "ORDER BY TB_FILE.CREATE_TIME DESC" +
            "</script>")
    List<LbMap> findAll(@Param("param") LbMap param);
}
