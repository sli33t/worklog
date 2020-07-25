package cn.linbin.worklog.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExcelExportUtil {

	//大标题的样式
	public static CellStyle bigTitle(XSSFWorkbook wb){
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short)16);
		font.setBold(true);//字体加粗
		style.setFont(font);
		style.setAlignment(HorizontalAlignment.CENTER);				//横向居中
		style.setVerticalAlignment(VerticalAlignment.CENTER);		//纵向居中
		style.setBorderTop(BorderStyle.THIN);						//上细线
		style.setBorderBottom(BorderStyle.THIN);					//下细线
		style.setBorderLeft(BorderStyle.THIN);						//左细线
		style.setBorderRight(BorderStyle.THIN);						//右细线
        style.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		return style;
	}

	//小标题的样式
	public static CellStyle title(XSSFWorkbook wb){
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		font.setFontName("黑体");
		font.setFontHeightInPoints((short)12);
		style.setFont(font);
		style.setAlignment(HorizontalAlignment.CENTER);				//横向居中
		style.setVerticalAlignment(VerticalAlignment.CENTER);		//纵向居中
		style.setBorderTop(BorderStyle.THIN);						//上细线
		style.setBorderBottom(BorderStyle.THIN);					//下细线
		style.setBorderLeft(BorderStyle.THIN);						//左细线
		style.setBorderRight(BorderStyle.THIN);						//右细线

        style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		return style;
	}

	//文字样式
	public static CellStyle text(XSSFWorkbook wb){
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		font.setFontName("Times New Roman");
		font.setFontHeightInPoints((short)10);

		style.setFont(font);

		style.setAlignment(HorizontalAlignment.LEFT);				//横向居左
		style.setVerticalAlignment(VerticalAlignment.CENTER);		//纵向居中
		style.setBorderTop(BorderStyle.THIN);						//上细线
		style.setBorderBottom(BorderStyle.THIN);					//下细线
		style.setBorderLeft(BorderStyle.THIN);						//左细线
		style.setBorderRight(BorderStyle.THIN);						//右细线

		return style;
	}

	public static DataValidation createDataValidation(XSSFWorkbook wb, int thisCol, String[] thisList){
	    if (thisList==null){
	        return null;
        }

        String uuid = IdWorker.getNum4();
        XSSFSheet sheet = wb.createSheet(uuid);

        /*int index = 0;
        for (String string : thisList) {
            Row row = sheet.createRow(index);
            Cell cell = row.createCell(0);
            cell.setCellValue(string);
            index++;
        }*/

		XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheet);
		XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper.createExplicitListConstraint(thisList);
		CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(2, 65535, thisCol, thisCol);
		XSSFDataValidation dataValidation = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, cellRangeAddressList);

        /*XSSFDataValidationConstraint constraint = new XSSFDataValidationConstraint(DataValidationConstraint.ValidationType.LIST, "!$A$1:$A$65535");
        CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(2, 65535, thisCol, thisCol);
        DataValidationHelper help = sheet.getDataValidationHelper();
        DataValidation dataValidation = help.createValidation(constraint, cellRangeAddressList);*/

        dataValidation.setShowErrorBox(true);
        dataValidation.setEmptyCellAllowed(false);
        dataValidation.setShowPromptBox(true);
        dataValidation.createPromptBox("提示", "只能选择下拉框里面的数据");

        //wb.setSheetHidden(wb.getSheetIndex(uuid), true);
        return dataValidation;
    }


    public static void createDataValidation(HSSFSheet sheet, String[] textList, int thisCol){
        setHSSFValidation(sheet, textList, 2, 65535, thisCol, thisCol);
        setHSSFPrompt(sheet, "提示", "只能选择下拉菜单里的内容", 2, 500, thisCol, thisCol);
    }


    /**
     * 设置某些列的值只能输入预制的数据,显示下拉框.
     * @param sheet 要设置的sheet.
     * @param textlist 下拉框显示的内容
     * @param firstRow 开始行
     * @param endRow 结束行
     * @param firstCol   开始列
     * @param endCol  结束列
     * @return 设置好的sheet.
     */
    public static Sheet setHSSFValidation(HSSFSheet sheet, String[] textlist, int firstRow, int endRow, int firstCol, int endCol) {
        // 加载下拉列表内容
        DVConstraint constraint = DVConstraint.createExplicitListConstraint(textlist);
        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(firstRow,endRow, firstCol, endCol);
        // 数据有效性对象
        HSSFDataValidation data_validation_list = new HSSFDataValidation(regions, constraint);
        sheet.addValidationData(data_validation_list);
        return sheet;
    }

    /**
     * 设置单元格上提示
     * @param sheet  要设置的sheet.
     * @param promptTitle 标题
     * @param promptContent 内容
     * @param firstRow 开始行
     * @param endRow  结束行
     * @param firstCol  开始列
     * @param endCol  结束列
     * @return 设置好的sheet.
     */
    public static Sheet setHSSFPrompt(HSSFSheet sheet, String promptTitle, String promptContent, int firstRow, int endRow ,int firstCol,int endCol) {
        // 构造constraint对象
        DVConstraint constraint = DVConstraint.createCustomFormulaConstraint("BB1");
        // 四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(firstRow,endRow,firstCol, endCol);
        // 数据有效性对象
        HSSFDataValidation data_validation_view = new HSSFDataValidation(regions,constraint);
        data_validation_view.createPromptBox(promptTitle, promptContent);
        sheet.addValidationData(data_validation_view);
        return sheet;
    }


    public static String getCellValue(Cell cell) {
        String value = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case NUMERIC: // 数字
                    //value = cell.getNumericCellValue() + "";
                    if (DateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        if (date != null) {
                            value = new SimpleDateFormat("yyyy-MM-dd").format(date); // 日期格式化
                        } else {
                            value = "";
                        }
                    } else {
                        // 解析cell时候 数字类型默认是double类型的 但是想要获取整数类型 需要格式化
                        value = new DecimalFormat("0.00").format(cell.getNumericCellValue());
                    }
                    break;
                case FORMULA: // 公式类型
                    try {
                        value = String.valueOf(cell.getNumericCellValue());
                    } catch (IllegalStateException e) {
                        value = String.valueOf(cell.getRichStringCellValue());
                    }
                    break;
                case STRING: // 字符串
                    value = cell.getStringCellValue();
                    break;
                case BOOLEAN: // Boolean类型
                    value = cell.getBooleanCellValue() + "";
                    break;
                case _NONE: // 空值
                    value = "";
                    break;
                case ERROR: // 错误类型
                    value = "非法字符";
                    break;
                default:
                    value = "未知类型";
                    break;
            }

        }
        return value.trim();
    }
}
