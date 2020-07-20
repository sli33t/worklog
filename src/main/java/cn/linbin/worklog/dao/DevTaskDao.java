package cn.linbin.worklog.dao;

import cn.linbin.worklog.domain.DevTask;
import cn.linbin.worklog.utils.LbMap;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DevTaskDao extends BaseMapper<DevTask>{

    @Select("<script>" +
            "SELECT TB_FEEDBACK.FEEDBACK_ID, TB_CUSTOMER.CUSTOMER_NAME, TB_VERSION.VERSION_NAME, TB_FEEDBACK.PROBLEM_TYPE, TB_FEEDBACK.FEEDBACK_TYPE, TB_FEEDBACK.PRIORITY, " +
            "TB_FEEDBACK.CREATE_TIME, TB_CUSTOMER.TEL_NO, TB_CUSTOMER.ADDRESS, TB_FEEDBACK.PROBLEM_TEXT, " +
            "TB_FEEDBACK.STATUS, TB_FEEDBACK.FINISHED, TB_FEEDBACK.FINISH_TIME, " +

            //开发任务
            "DEVELOPER.USER_NAME AS DEVELOPER, TB_DEVTASK.RECEIVED, TB_DEVTASK.RECEIVE_DATE, TB_DEVTASK.RECEIVE_TIME, TB_DEVTASK.FINISHED, TB_DEVTASK.FINISH_TIME, TB_DEVTASK.FINISH_DATE, " +
            "CREATE_USER.USER_NAME AS CREATOR_USER, TB_DEVTASK.TASK_TIME, TB_DEVTASK.FINISH_TEXT, TB_DEVTASK.DEVTASK_ID, TB_DEVTASK.PLAN_HOUR, TB_DEVTASK.REAL_HOUR, TB_DEVTASK.DEVTASK_ID, "+

            //开发状态
            " CASE WHEN TB_DEVTASK.DEVTASK_ID IS NULL OR TB_DEVTASK.FINISHED = 2 THEN '未分配' " +
                " WHEN TB_DEVTASK.DEVTASK_ID IS NOT NULL AND COALESCE(TB_DEVTASK.RECEIVED, 0) = 0 THEN '已分配未接收' " +
                " WHEN TB_DEVTASK.RECEIVED = 1 AND COALESCE(TB_DEVTASK.FINISHED, 0) = 0 THEN '已接收未开发完成' " +
                " WHEN TB_DEVTASK.FINISHED = 1 THEN '已开发完成' END AS DEV_STATUS, "+

            "TB_AREA.AREA_NAME, TB_CUSTOMER.EMAIL, TB_FEEDBACK.ROW_VERSION, TB_FEEDBACK.REQUIRE_DATE " +

            "FROM TB_FEEDBACK " +
            "INNER JOIN TB_CUSTOMER ON TB_FEEDBACK.CUSTOMER_ID = TB_CUSTOMER.CUSTOMER_ID " +
            "INNER JOIN TB_AREA ON TB_CUSTOMER.AREA_ID = TB_AREA.AREA_ID " +
            "INNER JOIN TB_VERSION ON TB_CUSTOMER.VERSION_ID = TB_VERSION.VERSION_ID " +
            "LEFT JOIN TB_DEVTASK ON TB_FEEDBACK.FEEDBACK_ID = TB_DEVTASK.FEEDBACK_ID " +
            "LEFT JOIN TB_USER DEVELOPER ON TB_DEVTASK.DEVELOP_USER_ID = DEVELOPER.USER_ID "+
            "LEFT JOIN TB_USER CREATE_USER ON TB_DEVTASK.CREATE_USER_ID = CREATE_USER.USER_ID "+

            "WHERE 1=1 " +

            "<if test='param.feedbackId != null and param.feedbackId != &quot;&quot;'>" +
                " AND TB_FEEDBACK.FEEDBACK_ID like CONCAT('%',#{param.feedbackId},'%') " +
            "</if>" +

            "<if test='param.customerName != null and param.customerName != &quot;&quot;'>" +
                " AND TB_CUSTOMER.CUSTOMER_NAME like CONCAT('%',#{param.customerName},'%') " +
            "</if>" +

            "<if test='param.problemType != null and param.problemType != &quot;&quot;'>" +
                " AND TB_FEEDBACK.PROBLEM_TYPE = #{param.problemType} " +
            "</if>" +

            "<if test='param.feedbackType != null and param.feedbackType != &quot;&quot;'>" +
                " AND TB_FEEDBACK.FEEDBACK_TYPE = #{param.feedbackType} " +
            "</if>" +

            "<if test='param.priority != null and param.priority != &quot;&quot;'>" +
                " AND TB_FEEDBACK.PRIORITY = #{param.priority} " +
            "</if>" +

            "<if test='param.devStatus != null and param.devStatus != &quot;&quot;'>" +
                "<if test='param.devStatus==0'>" +
                    " AND (TB_DEVTASK.DEVTASK_ID IS NULL OR TB_DEVTASK.FINISHED = 2) " +
                "</if>" +
                "<if test='param.devStatus==1'>" +
                    " AND TB_DEVTASK.DEVTASK_ID IS NOT NULL AND COALESCE(TB_DEVTASK.RECEIVED, 0) = 0 " +
                "</if>" +
                "<if test='param.devStatus==2'>" +
                    " AND TB_DEVTASK.RECEIVED = 1 AND COALESCE(TB_DEVTASK.FINISHED, 0) = 0 " +
                "</if>" +
                "<if test='param.devStatus==3'>" +
                    " AND TB_DEVTASK.FINISHED = 1 " +
                "</if>" +
            "</if>" +

            "ORDER BY TB_FEEDBACK.CREATE_TIME DESC"+

            "</script>")
    List<LbMap> findAll(@Param("param") LbMap param);
}
