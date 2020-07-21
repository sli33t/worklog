package cn.linbin.worklog.dao;

import cn.linbin.worklog.utils.LbMap;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AnalysisDao extends BaseMapper<LbMap> {

    @Select("<script>"+
            "SELECT TB_USER.USER_ID, TB_USER.USER_NAME AS DEVELOP_USER, COUNT(TB_DEVTASK.DEVTASK_ID) AS WORK_COUNT, " +
            "COALESCE(SUM(TB_DEVTASK.PLAN_HOUR), 0) AS PLAN_HOUR, COALESCE(SUM(TB_DEVTASK.REAL_HOUR), 0) AS REAL_HOUR, " +
            "COALESCE(SUM(TB_DEVTASK.REAL_HOUR), 0) - COALESCE(SUM(TB_DEVTASK.PLAN_HOUR), 0) AS DELAY_HOUR " +
            "FROM TB_DEVTASK INNER JOIN TB_USER ON TB_DEVTASK.DEVELOP_USER_ID = TB_USER.USER_ID " +
            "WHERE TB_DEVTASK.FINISHED = 1 " +

            "<if test='param.id != null and param.id != &quot;&quot;'>" +
                " AND TB_DEVTASK.DEVELOP_USER_ID = #{param.id} " +
            "</if>" +

            "<if test='param.beginDate != null and param.beginDate != &quot;&quot;'>" +
                " AND TB_DEVTASK.FINISH_DATE &gt;= #{param.beginDate} " +
            "</if>" +

            "<if test='param.endDate != null and param.endDate != &quot;&quot;'>" +
                " AND TB_DEVTASK.FINISH_DATE &lt;= #{param.endDate} " +
            "</if>" +

            " GROUP BY TB_DEVTASK.DEVELOP_USER_ID " +

            "</script>")
    List<LbMap> workHourList(@Param("param") LbMap param);

    @Select("<script>"+
            "SELECT TB_USER.USER_NAME AS DEVELOP_USER, TB_DEVTASK.FEEDBACK_ID, TB_DEVTASK.PLAN_HOUR, TB_DEVTASK.REAL_HOUR, TB_DEVTASK.FEEDBACK_TIME, " +
            "TB_DEVTASK.FINISH_TIME, TB_FEEDBACK.REQUIRE_DATE, TB_DEVTASK.FINISH_DATE - TB_FEEDBACK.REQUIRE_DATE AS DELAY_DAYS " +
            "FROM TB_DEVTASK INNER JOIN TB_USER ON TB_DEVTASK.DEVELOP_USER_ID = TB_USER.USER_ID " +
            "INNER JOIN TB_FEEDBACK ON TB_DEVTASK.FEEDBACK_ID = TB_FEEDBACK.FEEDBACK_ID " +
            "WHERE TB_DEVTASK.FINISHED = 1 " +

            "<if test='param.developUserId != null and param.developUserId != &quot;&quot;'>" +
                " AND TB_DEVTASK.DEVELOP_USER_ID = #{param.developUserId} " +
            "</if>" +

            "<if test='param.id != null and param.id != &quot;&quot;'>" +
                " AND TB_DEVTASK.DEVELOP_USER_ID = #{param.id} " +
            "</if>" +

            "<if test='param.beginDate != null and param.beginDate != &quot;&quot;'>" +
                " AND TB_DEVTASK.FINISH_DATE &gt;= #{param.beginDate} " +
            "</if>" +

            "<if test='param.endDate != null and param.endDate != &quot;&quot;'>" +
                " AND TB_DEVTASK.FINISH_DATE &lt;= #{param.endDate} " +
            "</if>" +

            "</script>")
    List<LbMap> workDetailList(@Param("param") LbMap param);
}
