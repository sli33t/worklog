package cn.linbin.worklog.controller.customer;

import cn.linbin.worklog.constant.FeedbackConstant;
import cn.linbin.worklog.controller.BaseController;
import cn.linbin.worklog.domain.Customer;
import cn.linbin.worklog.domain.Feedback;
import cn.linbin.worklog.service.customer.CustomerService;
import cn.linbin.worklog.service.customer.FeedbackService;
import cn.linbin.worklog.utils.DownloadUtil;
import cn.linbin.worklog.utils.ExcelExportUtil;
import cn.linbin.worklog.utils.LbMap;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/feedback")
public class FeedbackController extends BaseController {

    private final static Logger logger = (Logger) LoggerFactory.getLogger(FeedbackController.class);

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private CustomerService customerService;

    /**
     * 跳转列表页面
     * @return
     */
    @GetMapping(value = "/toFeedback")
    public ModelAndView toFeedback(){
        ModelAndView mv = new ModelAndView();
        Feedback feedback = new Feedback();
        mv.addObject("feedback", feedback);
        mv.setViewName("customer/customer-feedback");
        return mv;
    }


    /**
     * 新增和修改
     * @param feedback
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public LbMap edit(Feedback feedback) {
        try {
            if (feedback.getCustomerId().equals("")){
                return LbMap.failResult("客反编辑失败，客户名称不能为空");
            }else if (feedback.getProblemText().equals("")){
                return LbMap.failResult("客反编辑失败，反馈内容不能为空");
            }else if (feedback.getProblemType()==null){
                return LbMap.failResult("客反编辑失败，问题类型不能为空，请选择需求还是bug");
            }else if (feedback.getFeedbackType()==null){
                return LbMap.failResult("客反编辑失败，反馈类型不能为空，请选择内部反馈还是客户反馈");
            }else if (feedback.getRequireDate()==null){
                return LbMap.failResult("客反编辑失败，要求完成日期不能为空");
            }else if (feedback.getPriority()==null){
                return LbMap.failResult("客反编辑失败，优先级别不能为空");
            }

            int code;

            //新增
            if (StringUtils.isEmpty(feedback.getFeedbackId())){

                feedback.setCreateUserId(userId);
                //ID为空的为新增
                feedbackService.save(feedback);
                code = 100;
            }else {
                //ID不为空的为修改
                feedback.setModifyUserId(userId);

                feedbackService.update(feedback);
                code = 200;
            }

            return LbMap.successResult("客户编辑成功", code);
        }catch (Exception e){
            logger.info("客户编辑失败："+e.getMessage());
            return LbMap.failResult("客户编辑失败，"+e.getMessage());
        }
    }


    /**
     * 跳转列表页面
     * @return
     */
    @GetMapping(value = "/toFeedbackList")
    public ModelAndView toFeedbackList(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("customer/customer-feedback-list");
        return mv;
    }

    /**
     * 查询所有客户反馈单
     * @param page
     * @param limit
     * @param jsonStr
     * @return
     */
    @GetMapping(value = "/findAll")
    public LbMap findAll(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "") String jsonStr){
        try {
            LbMap map = LbMap.fromObject(jsonStr);
            PageInfo<LbMap> pages = feedbackService.findAll(page, limit, map);
            logger.info("查询成功");
            return LbMap.successResult("客户反馈单查询成功", pages.getList(), pages.getTotal());
        }catch (Exception e){
            return LbMap.failResult("客户反馈单查询失败，"+e.getMessage());
        }
    }


    /**
     * 跳转更新页面
     * @param feedbackId
     * @return
     */
    @GetMapping(value = "/toUpdate")
    public ModelAndView toUpdate(Integer feedbackId) throws Exception {
        ModelAndView mv = new ModelAndView();
        Feedback feedback = feedbackService.findById(feedbackId);
        logger.info(feedback.toString());
        mv.setViewName("customer/customer-feedback");
        mv.addObject("feedback", feedback);
        return mv;
    }

    /**
     * 导入数据
     * @return
     */
    @PostMapping(value = "/excelToFeedback")
    public LbMap excelToFeedback(@RequestParam("file") MultipartFile file){
        try {
            LbMap result = feedbackService.excelToFeedback(file, userId);
            return result;
        }catch (Exception e){
            return LbMap.failResult("导入失败");
        }
    }


    /**
     * 导出模板
     * @throws IOException
     */
    @PostMapping(value = "/feedbackToExcel")
    public void feedbackToExcel() throws IOException {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("客户反馈模板");

        //设置列宽
        sheet.setColumnWidth(0, 30*256);
        sheet.setColumnWidth(1, 15*256);
        sheet.setColumnWidth(2, 15*256);
        sheet.setColumnWidth(3, 15*256);
        sheet.setColumnWidth(4, 20*256);
        sheet.setColumnWidth(5, 60*256);

        //合并大标题
        CellRangeAddress cellAddresses = new CellRangeAddress(0,0,0,5);
        sheet.addMergedRegion(cellAddresses);

        //第一行，大标题
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        row.setHeightInPoints(36);
        cell.setCellStyle(ExcelExportUtil.bigTitle(wb));
        cell.setCellValue("客户反馈单导入模板");

        //第二行，小标题
        row = sheet.createRow(1);
        row.setHeightInPoints(26);

        String[] titles = {"客户名称", "问题类型", "反馈类型", "优先级别", "要求完成日期", "反馈内容"};

        int index = 0;
        for (String title : titles) {
            cell = row.createCell(index);
            cell.setCellValue(title);
            cell.setCellStyle(ExcelExportUtil.title(wb));

            if (title.equals("客户名称")){
                List<Customer> customerList = customerService.findAll();
                String[] strings = new String[customerList.size()];
                int i=0;
                for (Customer customer : customerList) {
                    strings[i] = customer.getCustomerName();
                    i++;
                }
                sheet.addValidationData(ExcelExportUtil.createDataValidation(wb, index, strings));
            }
            if (title.equals("问题类型")){
                String[] list = {"需求", "bug"};
                /*ExcelExportUtil.createDataValidation(sheet, list, index);*/
                sheet.addValidationData(ExcelExportUtil.createDataValidation(wb, index, list));
            }else if (title.equals("反馈类型")){
                String[] list = {"内部反馈", "客户反馈"};
                /*ExcelExportUtil.createDataValidation(sheet, list, index);*/
                sheet.addValidationData(ExcelExportUtil.createDataValidation(wb, index, list));
            }else if (title.equals("优先级别")){
                String[] list = {"紧急", "高", "中", "低"};
                /*ExcelExportUtil.createDataValidation(sheet, list, index);*/
                sheet.addValidationData(ExcelExportUtil.createDataValidation(wb, index, list));
            }

            index++;
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        wb.write(outputStream);
        DownloadUtil.download(outputStream, response, "客户反馈单导入模板.xlsx");
    }
}
