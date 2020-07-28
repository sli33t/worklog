package cn.linbin.worklog.dao;

import cn.linbin.worklog.domain.vo.WorkHourDetailVo;
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
    List<WorkHourDetailVo> workDetailList(@Param("param") LbMap param);

    @Select("<script>" +
            "SELECT SUM(1) AS DEV_COUNT, SUM(CASE FINISHED WHEN 1 THEN 1 ELSE 0 END) AS DEV_FINISH_COUNT, " +
            "CASE WHEN COALESCE(SUM(1), 0) = 0 THEN 0 ELSE ROUND(SUM(CASE FINISHED WHEN 1 THEN 1 ELSE 0 END) / SUM(1), 2) * 100 END AS DEV_RATE " +
            "FROM TB_DEVTASK WHERE COALESCE(FINISHED, 0) != 2 "+
            "</script>")
    LbMap queryDevCount(@Param("param") LbMap param);

    @Select("<script>" +
            "SELECT SUM(1) AS TEST_COUNT, SUM(CASE FINISHED WHEN 1 THEN 1 ELSE 0 END) AS TEST_FINISH_COUNT, " +
            "CASE WHEN COALESCE(SUM(1), 0) = 0 THEN 0 ELSE ROUND(SUM(CASE FINISHED WHEN 1 THEN 1 ELSE 0 END) / SUM(1), 2) * 100 END AS TEST_RATE " +
            "FROM TB_TESTTASK WHERE COALESCE(FINISHED, 0) != 2 "+
            "</script>")
    LbMap queryTestCount(LbMap param);

    @Select("<script>" +
            "SELECT SUM(1) AS ALL_COUNT, SUM(CASE TB_TESTTASK.FINISHED WHEN 1 THEN 1 ELSE 0 END) AS ALL_FINISH_COUNT, " +
            "CASE WHEN COALESCE(SUM(1), 0) = 0 THEN 0 ELSE ROUND(SUM(CASE TB_TESTTASK.FINISHED WHEN 1 THEN 1 ELSE 0 END) / SUM(1), 2) * 100 END AS ALL_RATE " +
            "FROM TB_DEVTASK LEFT JOIN TB_TESTTASK ON TB_DEVTASK.DEVTASK_ID = TB_TESTTASK.DEVTASK_ID " +
            "WHERE TB_DEVTASK.FINISHED != 2 "+
            "</script>")
    LbMap queryAllCount(LbMap param);

    @Select("<script>" +
            "SELECT SUM(CASE WHEN DATE_FORMAT(create_time, '%Y') = #{param.thisYear} AND PROBLEM_TYPE = 0 THEN 1 ELSE 0 END) AS thisYear0," +
            "SUM(CASE WHEN DATE_FORMAT(create_time, '%Y') = #{param.lastYear} AND PROBLEM_TYPE = 0 THEN 1 ELSE 0 END) AS lastYear0," +
            "SUM(CASE WHEN DATE_FORMAT(create_time, '%Y') = #{param.beforeLastYear} AND PROBLEM_TYPE = 0 THEN 1 ELSE 0 END) AS beforeLastYear0," +
            "SUM(CASE WHEN DATE_FORMAT(create_time, '%Y') = #{param.thisYear} AND PROBLEM_TYPE = 1 THEN 1 ELSE 0 END) AS thisYear1," +
            "SUM(CASE WHEN DATE_FORMAT(create_time, '%Y') = #{param.lastYear} AND PROBLEM_TYPE = 1 THEN 1 ELSE 0 END) AS lastYear1," +
            "SUM(CASE WHEN DATE_FORMAT(create_time, '%Y') = #{param.beforeLastYear} AND PROBLEM_TYPE = 1 THEN 1 ELSE 0 END) AS beforeLastYear1 " +
            "FROM TB_FEEDBACK "+
            "</script>")
    LbMap queryFeedbackList(@Param("param") LbMap param);

    @Select("SELECT SUM(CASE WHEN PROBLEM_TYPE = 0 AND FEEDBACK_TYPE = 0 THEN 1 ELSE 0 END) AS NEED0," +
            "SUM(CASE WHEN PROBLEM_TYPE = 1 AND FEEDBACK_TYPE = 0 THEN 1 ELSE 0 END) AS BUG0," +
            "SUM(CASE WHEN PROBLEM_TYPE = 0 AND FEEDBACK_TYPE = 1 THEN 1 ELSE 0 END) AS NEED1," +
            "SUM(CASE WHEN PROBLEM_TYPE = 1 AND FEEDBACK_TYPE = 1 THEN 1 ELSE 0 END) AS BUG1 " +
            "FROM TB_FEEDBACK")
    LbMap queryFeedbackTypeList(LbMap param);

    @Select("SELECT SUM(CASE WHEN PRIORITY = 0 THEN 1 ELSE 0 END) AS PRIORITY0, " +
            "SUM(CASE WHEN PRIORITY = 1 THEN 1 ELSE 0 END) AS PRIORITY1, " +
            "SUM(CASE WHEN PRIORITY = 2 THEN 1 ELSE 0 END) AS PRIORITY2, " +
            "SUM(CASE WHEN PRIORITY = 3 THEN 1 ELSE 0 END) AS PRIORITY3 " +
            "FROM TB_FEEDBACK")
    LbMap queryPriority(LbMap param);

    @Select("SELECT CONCAT(TB_VERSION.VERSION_NAME, CASE WHEN TB_FEEDBACK.PROBLEM_TYPE = 0 THEN '需求' ELSE '问题' END) AS NAME,  SUM(1) AS VALUE " +
            "FROM TB_FEEDBACK INNER JOIN TB_CUSTOMER ON TB_FEEDBACK.CUSTOMER_ID = TB_CUSTOMER.CUSTOMER_ID " +
            "INNER JOIN TB_VERSION ON TB_CUSTOMER.VERSION_ID = TB_VERSION.VERSION_ID " +
            "WHERE COALESCE(TB_VERSION.DELETE_FLAG, 0) = 0 " +
            "GROUP BY TB_VERSION.VERSION_NAME, TB_FEEDBACK.PROBLEM_TYPE")
    List<LbMap> queryVersionList(LbMap param);
}
