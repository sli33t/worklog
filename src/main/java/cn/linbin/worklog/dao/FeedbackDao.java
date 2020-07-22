package cn.linbin.worklog.dao;

import cn.linbin.worklog.domain.Feedback;
import cn.linbin.worklog.utils.LbMap;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface FeedbackDao extends BaseMapper<Feedback>{

    @Select("<script>" +
            "SELECT TB_FEEDBACK.FEEDBACK_ID, TB_CUSTOMER.CUSTOMER_NAME, TB_VERSION.VERSION_NAME, TB_FEEDBACK.PROBLEM_TYPE, TB_FEEDBACK.FEEDBACK_TYPE, TB_FEEDBACK.PRIORITY, " +
            "CREATE_USER.USER_NAME AS CREATE_USER, TB_FEEDBACK.CREATE_TIME, TB_CUSTOMER.TEL_NO, TB_CUSTOMER.ADDRESS, TB_FEEDBACK.PROBLEM_TEXT, " +
            "TB_FEEDBACK.STATUS, TB_FEEDBACK.FINISHED, TB_FEEDBACK.FINISH_TIME, " +
            "MODIFY_USER.USER_NAME AS MODIFY_USER, TB_FEEDBACK.MODIFY_TIME, TB_AREA.AREA_NAME, TB_CUSTOMER.EMAIL, TB_FEEDBACK.ROW_VERSION, TB_FEEDBACK.REQUIRE_DATE " +
            "FROM TB_FEEDBACK  " +
            "INNER JOIN TB_USER CREATE_USER ON TB_FEEDBACK.CREATE_USER_ID = CREATE_USER.USER_ID " +
            "INNER JOIN TB_CUSTOMER ON TB_FEEDBACK.CUSTOMER_ID = TB_CUSTOMER.CUSTOMER_ID " +
            "INNER JOIN TB_AREA ON TB_CUSTOMER.AREA_ID = TB_AREA.AREA_ID " +
            "INNER JOIN TB_VERSION ON TB_CUSTOMER.VERSION_ID = TB_VERSION.VERSION_ID " +
            "LEFT JOIN TB_USER MODIFY_USER ON TB_FEEDBACK.MODIFY_USER_ID = MODIFY_USER.USER_ID " +

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

            "ORDER BY TB_FEEDBACK.CREATE_TIME DESC"+

            "</script>")
    List<LbMap> findAll(@Param("param") LbMap param);

    @Select("SELECT TB_FEEDBACK.FEEDBACK_ID, TB_CUSTOMER.CUSTOMER_NAME, TB_VERSION.VERSION_NAME, TB_FEEDBACK.PROBLEM_TYPE, TB_FEEDBACK.FEEDBACK_TYPE, TB_FEEDBACK.PRIORITY, " +
            "TB_CUSTOMER.TEL_NO, TB_CUSTOMER.ADDRESS, TB_FEEDBACK.PROBLEM_TEXT, TB_CUSTOMER.EMAIL, TB_AREA.AREA_NAME, " +
            "TB_FEEDBACK.STATUS, TB_FEEDBACK.FINISHED, TB_FEEDBACK.FINISH_TIME, TB_FEEDBACK.CREATE_TIME, " +
            "TB_FEEDBACK.ROW_VERSION, TB_FEEDBACK.CUSTOMER_ID, TB_FEEDBACK.REQUIRE_DATE, TB_DEVTASK.FINISH_TEXT, DEV_USER.USER_NAME AS DEVELOPER, CREATOR.USER_NAME AS CREATE_USER " +
            "FROM TB_FEEDBACK  " +
            "INNER JOIN TB_CUSTOMER ON TB_FEEDBACK.CUSTOMER_ID = TB_CUSTOMER.CUSTOMER_ID " +
            "INNER JOIN TB_AREA ON TB_CUSTOMER.AREA_ID = TB_AREA.AREA_ID " +
            "INNER JOIN TB_VERSION ON TB_CUSTOMER.VERSION_ID = TB_VERSION.VERSION_ID " +
            "INNER JOIN TB_USER CREATOR ON CREATOR.USER_ID = TB_FEEDBACK.CREATE_USER_ID " +
            "LEFT JOIN TB_DEVTASK ON TB_FEEDBACK.FEEDBACK_ID = TB_DEVTASK.FEEDBACK_ID "+
            "LEFT JOIN TB_USER DEV_USER ON DEV_USER.USER_ID = TB_DEVTASK.DEVELOP_USER_ID "+
            "WHERE TB_FEEDBACK.FEEDBACK_ID = #{feedbackId}")
    Feedback findById(Integer feedbackId);

    @Update("UPDATE TB_FEEDBACK SET STATUS = #{status} WHERE FEEDBACK_ID = #{feedbackId}")
    int updateStatus(@Param("feedbackId") Integer feedbackId, @Param("status") Integer status);

    @Select("SELECT FEEDBACK_ID FROM TB_DEVTASK WHERE FEEDBACK_ID = #{feedbackId}")
    List<LbMap> haveDevBack(Integer feedbackId);
}
