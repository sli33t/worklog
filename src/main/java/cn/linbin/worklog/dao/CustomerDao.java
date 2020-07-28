package cn.linbin.worklog.dao;

import cn.linbin.worklog.domain.po.Customer;
import cn.linbin.worklog.utils.LbMap;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CustomerDao extends BaseMapper<Customer>{

    @Select("SELECT TB_CUSTOMER.CUSTOMER_ID, TB_CUSTOMER.CUSTOMER_NAME, TB_CUSTOMER.TEL_NO, TB_CUSTOMER.ADDRESS, " +
            "TB_CUSTOMER.DELETE_FLAG, TB_CUSTOMER.DELETE_DATE, TB_CUSTOMER.CREATE_TIME, TB_CUSTOMER.ROW_VERSION " +
            "FROM TB_CUSTOMER WHERE TB_CUSTOMER.CUSTOMER_NAME = #{customerName}")
    List<Customer> checkUserInfo(String customerName);

    @Select("<script>" +
            "SELECT TB_CUSTOMER.CUSTOMER_ID, TB_CUSTOMER.CUSTOMER_NAME, TB_CUSTOMER.TEL_NO, TB_CUSTOMER.ADDRESS, " +
            "TB_CUSTOMER.AREA_ID, TB_AREA.AREA_NAME, TB_CUSTOMER.EMAIL, TB_VERSION.VERSION_NAME, " +
            "TB_CUSTOMER.DELETE_FLAG, TB_CUSTOMER.DELETE_DATE, TB_CUSTOMER.CREATE_TIME, TB_CUSTOMER.ROW_VERSION " +
            "FROM TB_CUSTOMER INNER JOIN TB_AREA ON TB_CUSTOMER.AREA_ID = TB_AREA.AREA_ID " +
            "LEFT JOIN TB_VERSION ON TB_CUSTOMER.VERSION_ID = TB_VERSION.VERSION_ID " +
            "WHERE COALESCE(TB_CUSTOMER.DELETE_FLAG, 0) = 0 " +

            "<if test='param.customerName != null and param.customerName != &quot;&quot;'>" +
                " AND TB_CUSTOMER.CUSTOMER_NAME like CONCAT('%',#{param.customerName},'%') " +
            "</if>" +

            "<if test='param.telNo != null and param.telNo != &quot;&quot;'>" +
                " AND TB_CUSTOMER.TEL_NO like CONCAT('%',#{param.telNo},'%') " +
            "</if>" +

            "</script>")
    List<LbMap> findAll(@Param("param") LbMap param);

    @Select("SELECT TB_CUSTOMER.CUSTOMER_NAME as name, TB_CUSTOMER.CUSTOMER_ID as id, TB_CUSTOMER.TEL_NO, " +
            "TB_CUSTOMER.ADDRESS, TB_CUSTOMER.EMAIL, TB_CUSTOMER.CREATE_TIME, TB_AREA.AREA_NAME, TB_VERSION.VERSION_NAME " +
            "FROM TB_CUSTOMER INNER JOIN TB_AREA ON TB_CUSTOMER.AREA_ID = TB_AREA.AREA_ID " +
            "LEFT JOIN TB_VERSION ON TB_CUSTOMER.VERSION_ID = TB_VERSION.VERSION_ID " +
            "WHERE COALESCE(TB_CUSTOMER.DELETE_FLAG, 0) = 0 " +
            "ORDER BY TB_CUSTOMER.CUSTOMER_NAME")
    List<LbMap> findCustomer();
}
