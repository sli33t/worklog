package cn.linbin.worklog.controller.analysis;

import cn.linbin.worklog.controller.BaseController;
import cn.linbin.worklog.domain.WorkHour;
import cn.linbin.worklog.service.analysis.AnalysisService;
import cn.linbin.worklog.utils.LbMap;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/pdf")
public class PdfController extends BaseController {

    @Autowired
    private AnalysisService analysisService;

    @GetMapping(value = "/workHourToPdf")
    public void workHourToPdf(@RequestParam(defaultValue = "") String beginDate, @RequestParam(defaultValue = "") String endDate,
                              @RequestParam(defaultValue = "") String developUser) throws Exception {
        /*LbMap lbMap = LbMap.fromObject(jsonStr);
        System.out.println(lbMap.toString());*/

        ClassPathResource resource = new ClassPathResource("jaspers" + File.separator + "workHourList.jasper");
        InputStream jasperStream = resource.getInputStream();
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);

        Map<String, Object> map = new HashMap<>();
        map.put("beginDate", beginDate);
        map.put("endDate", endDate);
        map.put("developUser", developUser);
        LbMap param = LbMap.mapToLbMap(map);

        List<WorkHour> list = analysisService.queryWorkHourList(param);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, dataSource);
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }

}
