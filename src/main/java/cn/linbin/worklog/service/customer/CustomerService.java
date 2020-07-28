package cn.linbin.worklog.service.customer;

import cn.linbin.worklog.domain.po.Customer;
import cn.linbin.worklog.utils.LbMap;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CustomerService {

    /**
     * 查询所有客户
     * @return
     */
    PageInfo<LbMap> findAll(int page, int limit, LbMap param);

    /**
     * 通过ID查找客户信息
     * @param customerId
     * @return
     */
    Customer findById(String customerId);

    /**
     * 保存客户信息
     * @param customer
     * @return
     */
    void save(Customer customer) throws Exception;

    /**
     * 更新客户信息
     * @param customer
     * @return
     */
    void update(Customer customer) throws Exception;

    /**
     * 删除客户信息
     * @param customerId
     * @return
     */
    void delete(String customerId, String rowVersion) throws Exception;

    /**
     * 检查客户信息
     * @param customerName
     * @return
     */
    List<Customer> checkCustomerInfo(String customerName);

    /**
     * 查询所有
     */
    List<Customer> findAll();

    /**
     * 根据客户名称查询客户编号
     * @param customerName
     * @return
     */
    String findIdByName(String customerName);
}
