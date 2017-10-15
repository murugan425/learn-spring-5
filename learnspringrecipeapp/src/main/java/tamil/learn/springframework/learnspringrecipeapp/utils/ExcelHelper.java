package tamil.learn.springframework.learnspringrecipeapp.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jodd.bean.BeanUtil;
import jodd.datetime.JDateTime;
import jodd.util.StringUtil;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFFont;

/**
 *<pre>
 * 功   能: Excel助手类
 * 创建者: 陈    林(Vickey)
 * 日   期: 2014-7-3上午10:38:54
 * Q  Q: 
 *</pre>
 */
public class ExcelHelper {
	private static ExcelHelper helper = null;
	private static final int defaultColumnWidth = 3500;
	
	private DataFormat format;
	
	/**
	 * Excel样式
	 */
	private CellStyle headerStyle,commonStyle;
	/**
	 * Excel日期格式
	 */
	private CellStyle defaultDateCellStyle;
	
	/**
	 * 自定义的日期格式（索引，对应的格式）
	 */
	private Map<Integer, CellStyle> mapDateCellStyle;
	
	private ExcelHelper() {
		
	}
	
	public static synchronized ExcelHelper getInstanse() {
		if(helper == null) {
			helper = new ExcelHelper();
		}
		return helper;
	}
	
	/**
	 * 将Excel文件导入到list对象
	 * @param head	文件头信息
	 * @param file	导入的数据源
	 * @param cls	保存当前数据的对象
	 * @return List
	 */
	public List importExcelToObjectList(ExcelHead head, File file, Class cls) {
		if (null == head || null == file || null == cls || !file.exists()) {
			return null;
		}
		InputStream is;
		ArrayList<Object> contents = new ArrayList<Object>();
		try {
			is = new BufferedInputStream(new FileInputStream(file));
			List<ExcelColumn> excelColumns = head.getColumns();
			Map<String, Map> columnsConvertMap = head.getColumnsConvertMap();//需要转换的列
			if (!is.markSupported()) {
				is = new PushbackInputStream(is);
			}
			if (POIFSFileSystem.hasPOIFSHeader(is)) {// EXCEL2003使用的是微软的文件系统
				Hxls2List hxls2List = new Hxls2List(file, convertExcelHeadToMap(excelColumns), columnsConvertMap, cls, contents);
				hxls2List.process();
			}else if (POIXMLDocument.hasOOXMLHeader(is)){//EXCEL2007使用的是OOM文件格式
				Xxlsx2List xxlsx2List = new Xxlsx2List(convertExcelHeadToMap(excelColumns), columnsConvertMap, contents, cls);
				xxlsx2List.process(is);
			}else {
				throw new IOException("不能解析的Excel版本");
			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return contents;
	}
	
	/**
	 * 将数据导入Excel，返回文件流
	 * @author 白国庆      2013-8-20
	 * @param head 报表头信息
	 * @param fileName Excel文件名
	 * @param dataList 导入excel报表的数据来源
	 * 
	 * @return InputStream
	 */
	public InputStream exportExcelInputStream(ExcelHead head, String fileName,
			List<?> dataList) {
		// 读取导出excel模板
		InputStream is = null;
		SXSSFWorkbook wb = new SXSSFWorkbook(100);
		// 生成导出数据
		buildExcelData(wb, head, fileName, dataList);

		// 导出到文件中
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			wb.write(out);
			out.flush();
			byte[] xlsxData = out.toByteArray();
			is = new ByteArrayInputStream(xlsxData, 0, xlsxData.length);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return is;
	}
	
	/**
	 * 将报表结构转换成Map
	 * @param excelColumns
	 * @auther <a href="mailto:hubo@feinno.com">hubo</a>
	 * @return void
	 * 2012-4-18 下午01:31:12
	 */
	private Map<Integer, ExcelColumn> convertExcelHeadToMap(List<ExcelColumn> excelColumns) {
		Map<Integer, ExcelColumn> excelHeadMap = new HashMap<Integer, ExcelColumn>();
		for (ExcelColumn excelColumn : excelColumns) {
			if(StringUtil.isEmpty(excelColumn.getFieldName())) {
				continue;
			} else {
				excelHeadMap.put(excelColumn.getIndex(), excelColumn);
			}
		}
		return excelHeadMap;
	}
	
	/**
	 * 生成导出至Excel文件的数据
	 * @param sheet	工作区间
	 * @param excelColumns	excel表头
	 * @param excelHeadMap	excel表头对应实体属性
	 * @param excelHeadConvertMap  需要对数据进行特殊转换的列
	 * @param dataList	导入excel报表的数据来源
	 * @return 
	 * @return void
	 * 2012-4-19 上午09:36:37
	 */
	public SXSSFWorkbook buildExcelData(SXSSFWorkbook wb, ExcelHead head,String fileName, List<?> dataList) {

		Sheet sheet = wb.createSheet(fileName);
		List<ExcelColumn> excelColumns = head.getColumns(); 
		Map<String, Map> excelHeadConvertMap = head.getColumnsConvertMap();
		
		// 从第几行开始插入数据
	    int startRow = 0;
	    initExcelStyle(wb,excelColumns);
	    //写表头
    	Row row = sheet.createRow(startRow++);
    	Cell cell = null;
    	ExcelColumn excelColumn;
	    for (int j = 0; j < excelColumns.size(); j++) {
	    	excelColumn = excelColumns.get(j);
	    	cell = row.createCell(j);
	    	cell.setCellValue(excelColumn.getFieldDispName());
	    	cell.setCellStyle(headerStyle);
			sheet.setColumnWidth(j, ((excelColumn.getWidth() <= 0 || excelColumn.getWidth()  > '\uff00') ? defaultColumnWidth : excelColumn.getWidth()));
	    }
	    //写数据
	    Cell upCell;
	    for (Object obj : dataList) {
	    	row = sheet.createRow(startRow++);
	    	for (int j = 0; j < excelColumns.size(); j++) {
	    		cell = row.createCell(j);
	    		cell.setCellStyle(commonStyle);
	    		excelColumn = excelColumns.get(j);
	    		String fieldName = excelColumn.getFieldName();
	    		if(fieldName != null) {
	    			upCell = sheet.getRow(startRow-2).getCell(j);
	    			Object valueObject = BeanUtil.getProperty(obj, fieldName);
	    			// 如果存在需要转换的字段信息，则进行转换
	    			if(excelHeadConvertMap != null && excelHeadConvertMap.get(fieldName) != null) {
	    				valueObject = excelHeadConvertMap.get(fieldName).get(valueObject);
	    			}
	    			if(valueObject == null) {
	    				megreCell(excelColumn,valueObject,upCell,sheet,startRow,j,1);
	    				cell.setCellValue("");
	    			} else if (valueObject instanceof Integer) {
    					megreCell(excelColumn,valueObject,upCell,sheet,startRow,j,2);
	    				cell.setCellValue((Integer)valueObject);
					} else if (valueObject instanceof String) {
    					megreCell(excelColumn,valueObject,upCell,sheet,startRow,j,3);
						cell.setCellValue((String)valueObject);
					} else if (valueObject instanceof Date) {
    					megreCell(excelColumn,valueObject,upCell,sheet,startRow,j,4);
						cell.setCellValue((Date)valueObject);
						if (excelColumns.get(j).getDateFormat() != null) {
							cell.setCellStyle(mapDateCellStyle.get(excelColumn.getIndex()));
						}else {
							cell.setCellStyle(defaultDateCellStyle);
						}
					} else if (valueObject instanceof Calendar) {
    					megreCell(excelColumn,valueObject,upCell,sheet,startRow,j,5);
						if (excelColumns.get(j).getDateFormat() != null) {
							cell.setCellStyle(mapDateCellStyle.get(excelColumn.getIndex()));
						}else {
							cell.setCellStyle(defaultDateCellStyle);
						}
						cell.setCellValue((Calendar)valueObject);
					} else if (valueObject instanceof Double) {
    					megreCell(excelColumn,valueObject,upCell,sheet,startRow,j,6);
						cell.setCellValue((Double)valueObject);
					} else if (valueObject instanceof Boolean) {
    					megreCell(excelColumn,valueObject,upCell,sheet,startRow,j,7);
						cell.setCellValue((Boolean)valueObject);
					} else {
    					megreCell(excelColumn,valueObject,upCell,sheet,startRow,j,8);
						cell.setCellValue(valueObject.toString());
					}
	    		}
			}
		}
		for (int i = 0; i < excelColumns.size(); i++) {
			excelColumn = excelColumns.get(i);
			if (excelColumn.isMegre()) {
				sheet.addMergedRegion(new CellRangeAddress(excelColumn
						.getMegreStartRow(), startRow - 1, i, i));
				excelColumn.setFirst(true);
				excelColumn.setMegre(false);
			}
		}
		
		return wb;
	}
	
	/**
	 * @功能描述 合并单元格
	 * @author 白国庆 
	 * @since 2013-12-25
	 * @param excelColumn 当前列的定义
	 * @param valueObject 当前单元格的值
	 * @param upCell 垂直上面的单元格
	 * @param sheet 
	 * @param startRow 写到的第n行号
	 * @param j 列数
	 * @param type 单元格值的类型
	 * void
	 */
	private void megreCell(ExcelColumn excelColumn, Object valueObject,
			Cell upCell, Sheet sheet, int startRow, int j, int type) {
		if (!excelColumn.isVerticalMerge() || upCell ==null || startRow==2) {
			return;
		}
		boolean flag=false;
		switch (type) {
		case 1:
			if (upCell.getStringCellValue().trim().equals("")) {
				flag = true;
			}
			break;
		case 2:
			if ((Integer)valueObject == upCell.getNumericCellValue()) {
				flag = true;
			}
			break;
		case 3:
			if (((String)valueObject).equals(upCell.getStringCellValue())) {
				flag = true;
			}
			break;
		case 4:
			if (((Date)valueObject).compareTo(upCell.getDateCellValue()) == 0) {
				flag = true;
			}
			break;
		case 5:
			if (((Calendar)valueObject).getTimeInMillis() == upCell.getDateCellValue().getTime()) {
				flag = true;
			}
			break;
		case 6:
			if ((Double)valueObject == upCell.getNumericCellValue()) {
				flag = true;
			}
			break;
		case 7:
			if ((Boolean)valueObject == upCell.getBooleanCellValue()) {
				flag = true;
			}
			break;
		case 8:
			if (((String)valueObject).equals(upCell.getStringCellValue())) {
				flag = true;
			}
			break;
		default:
			return;
		}
		if (flag) {
			if (excelColumn.isFirst()) {
				excelColumn.setMegreStartRow(startRow - 2);
				excelColumn.setFirst(false);
			}
			excelColumn.setMegre(true);
		} else {
			if (excelColumn.isMegre()) {
				sheet.addMergedRegion(new CellRangeAddress(excelColumn
						.getMegreStartRow(), startRow - 2, j, j));
				excelColumn.setFirst(true);
				excelColumn.setMegre(false);
			}
		}
	}

	/**
	 * 初始化excel样式
	 * @author 白国庆      2013-8-20 void
	 * @param wb 
	 * @param excelColumns 
	 */
	private void initExcelStyle(SXSSFWorkbook wb, List<ExcelColumn> excelColumns) {
		// 创建帮助类
		CreationHelper helper = wb.getCreationHelper();

		// 创建字体
		Font commonFont = createFonts(wb, (short) 200, false);
		Font headerFont = createFonts(wb, (short) 250, false);

		// 一般样式
		commonStyle = wb.createCellStyle();

		// 表头样式
		headerStyle = wb.createCellStyle();

		format = helper.createDataFormat();
		//默认日期格式
		defaultDateCellStyle = wb.createCellStyle();
		defaultDateCellStyle.setFont(commonFont);
		defaultDateCellStyle.setDataFormat(format.getFormat("yyyy-MM-dd"));
		
		commonStyle.setFont(commonFont);
		headerStyle.setFont(headerFont);
		
		//设置背景颜色
		setFillBackgroundColors(headerStyle, IndexedColors.AQUA.getIndex(),CellStyle.SOLID_FOREGROUND);
		
		// 框线显示
		setCellBorderAndColorStyle(commonStyle);
		setCellBorderAndColorStyle(headerStyle);
		setCellBorderAndColorStyle(defaultDateCellStyle);
		
		// 居中显示
		setCellStyleAlignment(commonStyle);
		setCellStyleAlignment(headerStyle);
		setCellStyleAlignment(defaultDateCellStyle);
		
		//自定义日期格式
		mapDateCellStyle = new HashMap<Integer, CellStyle>();
		ExcelColumn excelColumn;
		for (int i = 0; i < excelColumns.size(); i++) {
			excelColumn = excelColumns.get(i);
			if (excelColumn.getDateFormat() != null) {
				CellStyle dateCellStyle = wb.createCellStyle();
				dateCellStyle.setFont(commonFont);
				dateCellStyle.setDataFormat(format.getFormat(excelColumn.getDateFormat()));
				setCellBorderAndColorStyle(dateCellStyle);// 框线显示
				setCellStyleAlignment(dateCellStyle);// 居中显示
				mapDateCellStyle.put(excelColumn.getIndex(), dateCellStyle);
			}
		}

	}
	
	/**
	 * 设置背景色
	 * @author 白国庆      2013-8-20
	 * @param cellStyle
	 * @param fg
	 * @param fp
	 */
	public void setFillBackgroundColors(CellStyle cellStyle, short fg, short fp)
	{
		cellStyle.setFillForegroundColor(fg);
		cellStyle.setFillPattern(fp);
	}
	
	/**
	 *  框线显示
	 * @author 白国庆      2013-8-20
	 * @param cellStyle
	 */
	private void setCellBorderAndColorStyle(CellStyle cellStyle)
	{
		// 设置一个单元格边框
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);

		// 设置一个单元格边框颜色
		cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
	}

	/**
	 * 居中显示
	 * @author 白国庆      2013-8-20
	 * @param cellStyle 
	 */
	private void setCellStyleAlignment(CellStyle cellStyle)
	{
		// 设置左右
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		// 设置上下
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	}

	/**
	 * 创建字体样式
	 * @author 白国庆      2013-8-21
	 * @param wb
	 * @param fontSize 大小
	 * @param isBlod 是否加粗
	 * @return Font
	 */
	private Font createFonts(Workbook wb, short fontSize, boolean isBlod)
	{

		// 创建Font对象
		Font font = wb.createFont();
		// 设置字体
		font.setFontName("微软雅黑");
		// 斜体
		font.setItalic(false);
		// 字体大小
		font.setFontHeight(fontSize);
		//是否加粗
		if (isBlod) {
			font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		}

		return font;
	}
}

