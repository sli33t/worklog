package cn.linbin.worklog.service.customer;

import cn.linbin.worklog.domain.Customer;
import cn.linbin.worklog.utils.LbMap;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CustomerService {

    /**
     * 查询所有客户
     * @return
     */
    PageInfo<LbMap> findAll(int pageIndex, int pageSize, LbMap param);

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
    
}
