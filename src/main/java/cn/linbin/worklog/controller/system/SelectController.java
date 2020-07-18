package cn.linbin.worklog.controller.system;

import cn.linbin.worklog.controller.BaseController;
import cn.linbin.worklog.service.system.SelectService;
import cn.linbin.worklog.utils.LbMap;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "/select")
public class SelectController extends BaseController{

    @Autowired
    private SelectService selectService;

    private final static Logger logger = (Logger) LoggerFactory.getLogger(SelectController.class);

    @GetMapping(value = "/toSelectTable")
    public ModelAndView toSelectTable(String tableUrl){
        ModelAndView mv = new ModelAndView();
        try {
            mv.addObject("tableUrl", tableUrl);
            mv.setViewName("common/selectTable");
            logger.info("查询成功");
            return mv;
        }catch (Exception e){
            mv.setViewName("system/error");
            mv.addObject("errorMsg", e.getMessage());
            logger.info("查询失败");
            return mv;
        }
    }

    /**
     * 查询区域
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/findArea")
    public LbMap findArea(@RequestParam(defaultValue = "1") int pageIndex, @RequestParam(defaultValue = "10") int pageSize){
        try {
            PageInfo<LbMap> pages = selectService.findArea(pageIndex, pageSize);
            logger.info("findArea查询成功");
            return LbMap.successResult("findArea查询成功", pages.getList(), pages.getSize());
        }catch (Exception e){
            return LbMap.failResult("findArea查询失败，"+e.getMessage());
        }
    }
}
