package cn.linbin.worklog.service.customer.impl;

import cn.linbin.worklog.constant.FeedbackConstant;
import cn.linbin.worklog.dao.FeedbackDao;
import cn.linbin.worklog.domain.po.Feedback;
import cn.linbin.worklog.service.customer.CustomerService;
import cn.linbin.worklog.service.customer.FeedbackService;
import cn.linbin.worklog.utils.DBUtil;
import cn.linbin.worklog.utils.ExcelExportUtil;
import cn.linbin.worklog.utils.LbMap;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService{

    @Autowired
    private FeedbackDao feedbackDao;

    @Autowired
    private CustomerService customerService;

    /**
     * 编辑客户反馈单
     * @param feedback
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Feedback feedback) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        feedback.setCreateTime(dateFormat.parse(dateFormat.format(new Date())));

        //初始化的行版本号为0
        feedback.setRowVersion(0);
        feedback.setStatus(FeedbackConstant.FEEDBACK_STATUS_1);

        int count = feedbackDao.insert(feedback);
        if (count<=0){
            throw new Exception("客户反馈单写入失败！");
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Feedback feedback) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        feedback.setModifyTime(dateFormat.parse(dateFormat.format(new Date())));

        QueryWrapper<Feedback> wrapper = new QueryWrapper<>();
        wrapper.eq("FEEDBACK_ID", feedback.getFeedbackId());
        wrapper.eq("ROW_VERSION", feedback.getRowVersion());
        feedback.setRowVersion(feedback.getRowVersion()+1);
        int count = feedbackDao.update(feedback, wrapper);
        if (count<=0){
            throw new Exception("客户反馈单写入失败！");
        }
    }

    /**
     * 查询客户反馈单列表
     * @param page
     * @param limit
     * @param param
     * @return
     */
    @Override
    public PageInfo<LbMap> findAll(int page, int limit, LbMap param) {
        PageHelper.startPage(page, limit);
        List<LbMap> list = feedbackDao.findAll(param);
        return new PageInfo(list);
    }

    @Override
    public Feedback findById(Integer feedbackId) throws Exception {
        Feedback feedback = feedbackDao.findById(feedbackId);
        if (feedback==null){
            throw new Exception("没有找到该客户反馈单，请刷新重试");
        }
        return feedback;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateStatus(Integer feedbackId, Integer status) {
        return feedbackDao.updateStatus(feedbackId, status);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LbMap excelToFeedback(MultipartFile file, String userId) throws Exception {
        Workbook wb = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = wb.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        if (lastRowNum<=0){
            return LbMap.failResult("不能导入空的表格");
        }

        String[] titles = {"客户名称", "问题类型", "反馈类型", "优先级别", "要求完成日期", "反馈内容"};
        String msg = "";

        for(int i=2; i<=lastRowNum; i++){
            Row row = sheet.getRow(i);
            Feedback feedback = new Feedback();
            feedback.setCreateUserId(userId);

            for(int j=0; j<titles.length; j++){
                Cell cell = row.getCell(j);

                String cellValue = ExcelExportUtil.getCellValue(cell);
                String cellCode = "";

                if (titles[j].equals("客户名称")){
                    cellCode = customerService.findIdByName(cellValue);

                    if (cellCode.equals("")){
                        msg = DBUtil.StringAdd(msg, "没有找到客户编号", "<br>");
                    }

                    feedback.setCustomerId(cellCode);
                }else if (titles[j].equals("问题类型")){
                    if (cellValue.equals("需求")){
                        cellCode = "0";
                    }else if (cellValue.toLowerCase().equals("bug")) {
                        cellCode = "1";
                    }

                    feedback.setProblemType(Integer.valueOf(cellCode));
                }else if (titles[j].equals("反馈类型")){
                    if (cellValue.equals("内部反馈")){
                        cellCode = "0";
                    }else if (cellValue.equals("客户反馈")) {
                        cellCode = "1";
                    }

                    feedback.setFeedbackType(Integer.valueOf(cellCode));
                }else if (titles[j].equals("优先级别")){
                    if (cellValue.equals("紧急")){
                        cellCode = "0";
                    }else if (cellValue.equals("高")) {
                        cellCode = "1";
                    }else if (cellValue.equals("中")) {
                        cellCode = "2";
                    }else if (cellValue.equals("低")) {
                        cellCode = "3";
                    }

                    feedback.setPriority(Integer.valueOf(cellCode));
                }else if (titles[j].equals("要求完成日期")){
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = dateFormat.parse(cellValue);
                    feedback.setRequireDate(date);
                }else if (titles[j].equals("反馈内容")){
                    feedback.setProblemText(cellValue);
                }
            }

            save(feedback);
        }

        if (!msg.equals("")){
            return LbMap.failResult("导入客户反馈单时失败，<br>" + msg);
        }

        return LbMap.successResult("");
    }
}
