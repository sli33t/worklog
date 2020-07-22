package cn.linbin.worklog.dao;

import cn.linbin.worklog.domain.TestTask;
import cn.linbin.worklog.utils.LbMap;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TestTaskDao extends BaseMapper<TestTask>{


    @Select("<script>"+
            "SELECT TB_FEEDBACK.FEEDBACK_ID, TB_TESTTASK.DEVTASK_ID, TB_TESTTASK.TESTTASK_ID, TB_CUSTOMER.CUSTOMER_NAME, TB_VERSION.VERSION_NAME," +
            "TB_FEEDBACK.PRIORITY, TB_FEEDBACK.STATUS, TB_FEEDBACK.FEEDBACK_TYPE, TB_FEEDBACK.PROBLEM_TYPE, DEV_USER.USER_NAME AS DEVELOPER,  " +
            "TB_TESTTASK.RECEIVED, TB_TESTTASK.FINISHED, TEST_USER.USER_NAME AS TEST_USER, TB_TESTTASK.TEST_ARRANGE, " +

            "CASE WHEN COALESCE(TB_TESTTASK.TEST_ARRANGE, 0) = 0 AND COALESCE(TB_TESTTASK.FINISHED, 0) = 0 THEN '未分配' " +
                "WHEN COALESCE(TB_TESTTASK.TEST_ARRANGE, 0) = 0 AND COALESCE(TB_TESTTASK.FINISHED, 0) = 2 THEN '退回未分配' " +
                "WHEN COALESCE(TB_TESTTASK.RECEIVED, 0) = 0 AND TB_TESTTASK.TEST_ARRANGE = 1 THEN '已分配未接收'" +
                "WHEN COALESCE(TB_TESTTASK.FINISHED, 0) = 0 AND TB_TESTTASK.TEST_ARRANGE = 1 THEN '未测试完成'" +
                "WHEN TB_TESTTASK.TEST_ARRANGE = 1 AND TB_TESTTASK.FINISHED = 1 THEN '测试完成'" +
            "END AS TEST_STATUS, " +

            "TB_FEEDBACK.PROBLEM_TEXT, TB_DEVTASK.FINISH_TEXT, CREATE_USER.USER_NAME AS CREATE_USER, " +
            "TB_TESTTASK.PLAN_HOUR, TB_TESTTASK.REAL_HOUR, TB_TESTTASK.FINISH_TIME, TB_FEEDBACK.REQUIRE_DATE " +
            "FROM TB_TESTTASK " +
            "INNER JOIN TB_FEEDBACK ON TB_TESTTASK.FEEDBACK_ID = TB_FEEDBACK.FEEDBACK_ID " +
            "INNER JOIN TB_CUSTOMER ON TB_FEEDBACK.CUSTOMER_ID = TB_CUSTOMER.CUSTOMER_ID " +
            "INNER JOIN TB_VERSION ON TB_VERSION.VERSION_ID = TB_CUSTOMER.VERSION_ID " +
            "INNER JOIN TB_DEVTASK ON TB_TESTTASK.DEVTASK_ID = TB_DEVTASK.DEVTASK_ID " +
            "INNER JOIN TB_USER AS DEV_USER ON TB_DEVTASK.DEVELOP_USER_ID = DEV_USER.USER_ID " +
            "INNER JOIN TB_USER AS CREATE_USER ON TB_FEEDBACK.CREATE_USER_ID = CREATE_USER.USER_ID " +
            "LEFT JOIN TB_USER AS TEST_USER ON TB_TESTTASK.TEST_USER_ID = TEST_USER.USER_ID " +
            "WHERE 1=1 " +

            "<if test='param.problemType != null and param.problemType != &quot;&quot;'>" +
                " AND TB_FEEDBACK.PROBLEM_TYPE = #{param.problemType}"+
            "</if>" +

            "<if test='param.feedbackId != null and param.feedbackId != &quot;&quot;'>" +
                " AND TB_FEEDBACK.FEEDBACK_ID like CONCAT('%',#{param.feedbackId},'%') " +
            "</if>" +

            "<if test='param.customerName != null and param.customerName != &quot;&quot;'>" +
                " AND TB_CUSTOMER.CUSTOMER_NAME like CONCAT('%',#{param.customerName},'%') " +
            "</if>" +

            "<if test='param.feedbackType != null and param.feedbackType != &quot;&quot;'>" +
                " AND TB_FEEDBACK.FEEDBACK_TYPE = #{param.feedbackType} " +
            "</if>" +

            "<if test='param.priority != null and param.priority != &quot;&quot;'>" +
                " AND TB_FEEDBACK.PRIORITY = #{param.priority} " +
            "</if>" +

            "<if test='param.status != null and param.status != &quot;&quot;'>" +
                " AND TB_FEEDBACK.STATUS = #{param.status} " +
            "</if>" +

            "<if test='param.testStatus != null and param.testStatus != &quot;&quot;'>" +
                "<if test='param.testStatus==0'>" +
                    " AND COALESCE(TB_TESTTASK.TEST_ARRANGE, 0) = 0 " +
                "</if>" +
                "<if test='param.testStatus==1'>" +
                    " AND COALESCE(TB_TESTTASK.RECEIVED, 0) = 0 AND TB_TESTTASK.TEST_ARRANGE = 1 " +
                "</if>" +
                "<if test='param.testStatus==2'>" +
                    " AND COALESCE(TB_TESTTASK.FINISHED, 0) = 0 AND TB_TESTTASK.TEST_ARRANGE = 1 " +
                "</if>" +
                "<if test='param.testStatus==3'>" +
                    " AND TB_TESTTASK.TEST_ARRANGE = 1 AND TB_TESTTASK.FINISHED = 1 " +
                "</if>" +
            "</if>" +

            "ORDER BY TB_FEEDBACK.CREATE_TIME DESC"+

            "</script>")
    List<LbMap> findAll(@Param("param") LbMap param);

    @Select("SELECT COALESCE(COUNT(TESTTASK_ID), 0) AS COUNT FROM TB_TESTTASK WHERE TB_TESTTASK.TEST_USER_ID = #{userId} AND COALESCE(TB_TESTTASK.FINISHED, 0) = 0")
    int findTestFinishCount(String userId);

    @Select("SELECT TB_FEEDBACK.FEEDBACK_ID, TB_TESTTASK.TESTTASK_ID, TB_FEEDBACK.REQUIRE_DATE, TB_FEEDBACK.PROBLEM_TEXT, TB_CUSTOMER.CUSTOMER_NAME, " +
            "TB_DEVTASK.FINISH_TEXT, TB_USER.USER_NAME, DEV_USER.USER_NAME AS DEVELOPER " +
            "FROM TB_FEEDBACK INNER JOIN TB_CUSTOMER ON TB_FEEDBACK.CUSTOMER_ID = TB_CUSTOMER.CUSTOMER_ID " +
            "INNER JOIN TB_USER ON TB_FEEDBACK.CREATE_USER_ID = TB_USER.USER_ID "+
            "INNER JOIN TB_TESTTASK ON TB_TESTTASK.FEEDBACK_ID = TB_FEEDBACK.FEEDBACK_ID " +
            "INNER JOIN TB_DEVTASK ON TB_DEVTASK.DEVTASK_ID = TB_TESTTASK.DEVTASK_ID " +
            "INNER JOIN TB_USER DEV_USER ON TB_DEVTASK.DEVELOP_USER_ID = DEV_USER.USER_ID "+
            "WHERE TB_TESTTASK.TEST_USER_ID = #{param.testUserId} AND COALESCE(TB_TESTTASK.FINISHED, 0) = 0 ")
    List<LbMap> findDevFinish(@Param("param") LbMap param);
}
