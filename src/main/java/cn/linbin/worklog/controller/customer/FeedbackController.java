package cn.linbin.worklog.controller.customer;

import cn.linbin.worklog.controller.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "/feedback")
public class FeedbackController extends BaseController {

    /**
     * 跳转列表页面
     * @return
     */
    @GetMapping(value = "/toFeedback")
    public ModelAndView toCustomerList(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("customer/customer-feedback");
        return mv;
    }

}
