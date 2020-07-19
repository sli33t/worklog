package cn.linbin.worklog.controller.customer;

import cn.linbin.worklog.controller.BaseController;
import cn.linbin.worklog.domain.Customer;
import cn.linbin.worklog.domain.Version;
import cn.linbin.worklog.service.customer.CustomerService;
import cn.linbin.worklog.service.system.SelectService;
import cn.linbin.worklog.utils.LbMap;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/customer")
public class CustomerController extends BaseController {

    private final static Logger logger = (Logger) LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    private SelectService selectService;

    /**
     * 跳转列表页面
     * @return
     */
    @GetMapping(value = "/toCustomerList")
    public ModelAndView toCustomerList(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("customer/customer-list");
        return mv;
    }

    /**
     * 查询所有客户信息
     * @return
     */
    @GetMapping(value = "/findAll")
    public LbMap findAll(@RequestParam(defaultValue = "1") int pageIndex, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "") String jsonStr){
        try {
            LbMap map = LbMap.fromObject(jsonStr);
            PageInfo<LbMap> pages = customerService.findAll(pageIndex, pageSize, map);
            logger.info("查询成功");
            return LbMap.successResult("客户查询成功", pages.getList(), pages.getSize());
        }catch (Exception e){
            return LbMap.failResult("客户查询失败，"+e.getMessage());
        }
    }


    /**
     * 跳转新增页面
     * @return
     */
    @GetMapping(value = "/toAdd")
    public ModelAndView toAdd(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("customer/customer-add");
        List<Version> versionList = selectService.findVersion();
        mv.addObject("versionList", versionList);
        return mv;
    }

    /**
     * 新增和修改
     * @param customer
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public LbMap edit(Customer customer) {
        try {
            if (customer.getCustomerName().equals("")){
                LbMap.failResult("客户编辑失败，客户名称不能为空！");
            }else if (customer.getTelNo().equals("")){
                LbMap.failResult("客户编辑失败，客户电话不能为空！");
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            customer.setCreateTime(dateFormat.parse(dateFormat.format(new Date())));

            //新增
            if (StringUtils.isEmpty(customer.getCustomerId())){
                List<Customer> customerList = customerService.checkCustomerInfo(customer.getCustomerName());

                if (customerList!=null&&customerList.size()>0){
                    for (Customer oldCustomer : customerList) {
                        if (oldCustomer.getCustomerName().equals(customer.getCustomerName())){
                            return LbMap.failResult("客户名称已经存在");
                        }
                    }
                }

                //初始化的行版本号为0
                customer.setRowVersion(0);
                customer.setDeleteFlag(0);
                //ID为空的为新增
                customerService.save(customer);
            }else {
                //ID不为空的为修改
                customerService.update(customer);
            }

            return LbMap.successResult("客户编辑成功");
        }catch (Exception e){
            logger.info("客户编辑失败："+e.getMessage());
            return LbMap.failResult("客户编辑失败，"+e.getMessage());
        }
    }

    /**
     * 通过编号查询客户信息
     * @param customerId
     * @return
     */
    @GetMapping(value = "/toUpdate")
    public ModelAndView toUpdate(String customerId){
        ModelAndView mv = new ModelAndView();
        try {
            if (customerId.equals("")){
                throw new Exception("没有找到客户编号");
            }

            mv.setViewName("customer/customer-update");

            Customer customer = customerService.findById(customerId);
            mv.addObject("customer", customer);

            List<Version> versionList = selectService.findVersion();
            mv.addObject("versionList", versionList);

            logger.info("查询成功");
            return mv;
        }catch (Exception e){
            mv.setViewName("system/error");
            mv.addObject("errorMsg", e.getMessage());
            logger.info("查询成功");
            return mv;
        }
    }



    /**
     * 删除
     * @param customerId
     * @return
     */
    @PostMapping(value = "/delete")
    public LbMap delete(String customerId, String rowVersion){
        try {
            if (customerId.equals("")){
                return LbMap.failResult("客户删除失败，没有找到客户编号！");
            }
            customerService.delete(customerId, rowVersion);
            return LbMap.successResult("客户删除成功");
        }catch (Exception e){
            return LbMap.failResult("客户删除失败，"+e.getMessage());
        }
    }
}
