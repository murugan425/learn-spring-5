package tamil.learn.springframework.learnspringrecipeapp.utils;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jodd.bean.BeanUtil;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 *<pre>
 * 功   能: EXCEL工具类
 * 创建者: 陈    林(Vickey)
 * 日   期: 2014-7-3上午10:39:13
 * Q  Q: 
 *</pre>
 */
public class Xxlsx2List extends DefaultHandler {
	private SharedStringsTable sst;
	private String lastContents;
	private boolean nextIsString;

	private int sheetIndex = -1;
	private Map<String, Object> rowMap;
	
	//excel数据插入数据库是是否出异常,true插入失败
//	protected boolean isImportException = false;
	
	protected String insertErrorMsg;
	
	//头部信息（列索引，类的属性实体类）
	private Map<Integer, ExcelColumn> excelHeadMap;
	//需要转换的列
	private Map<String, Map> columnsConvertMap;
	// 根据excel生成list类型的数据
	private List<Object> contents;
	private Class cls; //实体类
	
	/**
	 * 行标识值
	 * */
	private int curRow = 0;
	
	/**
	 * 列标识值
	 * */
	private int curCol = 0;

	/**
	 * @param columnsConvertMap 需要转换的列
	 * @param contents 根据excel生成list类型的数据
	 * @param cls 实体类
	 */
	public Xxlsx2List(Map<Integer, ExcelColumn> excelHeadMap,
			Map<String, Map> columnsConvertMap, List<Object> contents, Class cls) {
		super();
		this.excelHeadMap = excelHeadMap;
		this.columnsConvertMap = columnsConvertMap;
		this.contents = contents;
		this.cls = cls;
		this.rowMap = new HashMap<String, Object>();
	}



	//excel记录行操作方法，以sheet索引，行索引和行元素列表为参数，对sheet的一行元素进行操作，元素为String类型
	public void optRows(int sheetIndex,int curRow, Map<String, Object> rowMap2){
		if (curRow >= 1) {
			Object objClass;
//			System.out.println(curRow+"==="+rowMap2);
			try {
				objClass = cls.newInstance();
				BeanUtil.populateBean(objClass, rowMap2);
				contents.add(objClass);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 只遍历一个sheet，其中sheetId为要遍历的sheet索引，从1开始，1-3
	 * @author 白国庆      2013-8-23
	 * @param fileIS
	 * @param sheetId
	 * @throws Exception void
	 */
	public void processOneSheet(InputStream fileIS, int sheetId) throws Exception {
		OPCPackage pkg = OPCPackage.open(fileIS);
		XSSFReader r = new XSSFReader(pkg);
		SharedStringsTable sst = r.getSharedStringsTable();
		XMLReader parser = fetchSheetParser(sst);
		// rId2 found by processing the Workbook
		// 根据 rId# 或 rSheet# 查找sheet
		InputStream sheetIS = r.getSheet("rId" + sheetId);
		sheetIndex++;
		InputSource sheetSource = new InputSource(sheetIS);
		parser.parse(sheetSource);
		sheetIS.close();
	}

	/**
	 * 遍历 excel 文件
	 */
	public void process(String filename) throws Exception {
		OPCPackage pkg = OPCPackage.open(filename);
		XSSFReader r = new XSSFReader(pkg);
		SharedStringsTable sst = r.getSharedStringsTable();
		XMLReader parser = fetchSheetParser(sst);
		Iterator<InputStream> sheets = r.getSheetsData();
		while (sheets.hasNext()) {
			curRow = 0;
			sheetIndex++;
			InputStream sheet = sheets.next();
			InputSource sheetSource = new InputSource(sheet);
			parser.parse(sheetSource);
			sheet.close();
		}
	}
	
	/**
	 * @功能描述:解析传入的文件流
	 * */
	public void process(InputStream fileIS) throws Exception {
	try{
		OPCPackage pkg = OPCPackage.open(fileIS);
		XSSFReader r = new XSSFReader(pkg);
		SharedStringsTable sst = r.getSharedStringsTable();

		XMLReader parser = fetchSheetParser(sst);
		
		Iterator<InputStream> sheets = r.getSheetsData();
		while (sheets.hasNext()) {
			curRow = 0;
			sheetIndex++;
			InputStream sheet = sheets.next();
			InputSource sheetSource = new InputSource(sheet);
			parser.parse(sheetSource);
			sheet.close();
		}
	}catch (Exception e) {
		e.printStackTrace();
		insertErrorMsg = "要导入的文件解析出错，请确认导入文件是否正确!";
	}finally {
		
	}
	}

	public XMLReader fetchSheetParser(SharedStringsTable sst)
			throws SAXException {
		XMLReader parser = XMLReaderFactory
				.createXMLReader("org.apache.xerces.parsers.SAXParser");
		this.sst = sst;
		parser.setContentHandler(this);
		return parser;
	}

	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {
		
			// c => 单元格
			if (name.equals("c")) {
				// 如果下一个元素是 SST 的索引，则将nextIsString标记为true
				String cellType = attributes.getValue("t");
	//			System.out.println("sst的值: "+cellType);
				if (cellType != null && cellType.equals("s")) {
					nextIsString = true;
				} else {
					nextIsString = false;
				}
			}
			// 置空
			lastContents = "";
		
	}

	/**
	 * 重写父类的方法
	 */
	public void endElement(String uri, String localName, String name)
			throws SAXException {
		//如果前面的数据插入未出错,则接着插入
//		if(!isImportException){
			// 根据SST的索引值的到单元格的真正要存储的字符串
			// 这时characters()方法可能会被调用多次
			if (nextIsString) {
				try {
					int idx = Integer.parseInt(lastContents);
					lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
				} catch (Exception e) {
	
				}
			}
	
			// v => 单元格的值，如果单元格是字符串则v标签的值为该字符串在SST中的索引
			// 将单元格内容加入rowMap中，在这之前先去掉字符串前后的空白符
	//		System.out.println("name"+name);
			if (name.equals("c")) {
				Object value = lastContents.trim();
				value = value.equals("")?null:value;
//				rowlist.add(curCol, value);
				if (excelHeadMap.containsKey(curCol)) {
					if (columnsConvertMap != null && columnsConvertMap.containsKey(excelHeadMap.get(curCol).getFieldName())) {
						value = columnsConvertMap.get(excelHeadMap.get(curCol).getFieldName()).get(value);
					}
					value = transformType(excelHeadMap.get(curCol).getType(), value);
					rowMap.put(excelHeadMap.get(curCol).getFieldName(), value);
				}
				curCol++;
			}else {
				//如果标签名称为 row ，这说明已到行尾，调用 optRows() 方法
				if (name.equals("row")) {
					optRows(sheetIndex,curRow,rowMap);
					rowMap.clear();
					curRow++;
					curCol = 0;
				}
			}
//		}
	}
	
	private Object transformType(Object excelColumnType,Object value){
		if (excelColumnType instanceof String) {
			return value;
		}else if (excelColumnType instanceof Integer) {
			return (Integer)value;
		}else if (excelColumnType instanceof Double) {
			return (Double)value;
		}else if (excelColumnType instanceof Long) {
			return (Long)value;
		}else if (excelColumnType instanceof Date) {
			return (Date)value;
		}
		return value;
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		//得到单元格内容的值
		lastContents += new String(ch, start, length);
	}
}
