package cn.linbin.worklog.controller.analysis;

import cn.linbin.worklog.controller.BaseController;
import cn.linbin.worklog.service.analysis.AnalysisService;
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
@RequestMapping(value = "/analysis")
public class AnalysisController extends BaseController {

    private final static Logger logger = (Logger) LoggerFactory.getLogger(AnalysisController.class);

    @Autowired
    private AnalysisService analysisService;

    /**
     * 跳转列表页面
     * @return
     */
    @GetMapping(value = "/toWorkHourList")
    public ModelAndView toDevTaskList(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("analysis/work-hour-list");
        return mv;
    }


    /**
     * 查询所有工时
     * @param pageIndex
     * @param pageSize
     * @param jsonStr
     * @return
     */
    @GetMapping(value = "/workHourList")
    public LbMap workHourList(@RequestParam(defaultValue = "1") int pageIndex, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "") String jsonStr){
        try {
            LbMap param = LbMap.fromObject(jsonStr);
            PageInfo<LbMap> pages = analysisService.workHourList(pageIndex, pageSize, param);
            logger.info("查询成功");
            return LbMap.successResult("工时查询成功", pages.getList(), pages.getSize());
        }catch (Exception e){
            return LbMap.failResult("工时查询失败，"+e.getMessage());
        }
    }


    /**
     * 查询明细工时
     * @param pageIndex
     * @param pageSize
     * @param jsonStr
     * @return
     */
    @GetMapping(value = "/workDetailList")
    public LbMap workDetailList(@RequestParam(defaultValue = "1") int pageIndex, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "") String jsonStr){
        try {
            LbMap param = LbMap.fromObject(jsonStr);
            PageInfo<LbMap> pages = analysisService.workDetailList(pageIndex, pageSize, param);
            logger.info("查询成功");
            return LbMap.successResult("工时查询成功", pages.getList(), pages.getSize());
        }catch (Exception e){
            return LbMap.failResult("工时查询失败，"+e.getMessage());
        }
    }
}
