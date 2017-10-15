package tamil.learn.springframework.learnspringrecipeapp.utils;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 *<pre>
 * 功       能: 电子表格工具
 * 涉及版本: 
 * 创  建  者: 陈林林(Vickey)
 * 日       期: 2015-9-23下午2:26:55
 * Q    Q: 308053847
 *</pre>
 */
public class ExcelUtil {
	
	private static Logger log = Logger.getLogger(ExcelUtil.class);
	
	/**
	 *<pre>
	 * 说       明: 导出2010EXCEL
	 * @param outputStream
	 * @param sheetName
	 * @return
	 * @throws Exception
	 * 涉及版本: 
	 * 创  建  者: 陈林林(Vickey)
	 * 日       期: 2015-9-23下午2:43:50
	 *</pre>
	 */
	public static boolean excelExp2007(OutputStream outputStream,
			String sheetName) throws Exception {
		try {
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
			XSSFSheet xssfSheet = xssfWorkbook.createSheet(sheetName);
			XSSFRow xssfRow = xssfSheet.createRow(0);
			XSSFCell xssfCell = xssfRow.createCell(0, 0);
			xssfCell.setCellValue("中文测试");
			xssfCell.setCellType(XSSFCell.CELL_TYPE_STRING);
			xssfWorkbook.write(outputStream);
		} catch (Exception e) {
			throw new Exception(e);
		}
		return false;
	}
	
	/**
	 * <pre>
	 * 说   明:  导出EXCEL文件
	 * 
	 * @param workbook
	 * @param filename
	 * @param request
	 * @param response
	 * </pre>
	 */
	public static void exportExcelResponse(SXSSFWorkbook workbook,
			String filename, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

//		filename = FileUtil.encodeFileName(filename, request);// 处理中文文件名
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-disposition", "attachment;filename="
				+ filename);
		OutputStream ouputStream = response.getOutputStream();
		workbook.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();
	}

}
