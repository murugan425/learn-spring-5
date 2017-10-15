package tamil.learn.springframework.learnspringrecipeapp.utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jodd.bean.BeanUtil;

import org.apache.poi.hssf.eventusermodel.FormatTrackingHSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.eventusermodel.MissingRecordAwareHSSFListener;
import org.apache.poi.hssf.eventusermodel.EventWorkbookBuilder.SheetRecordCollectingListener;
import org.apache.poi.hssf.eventusermodel.dummyrecord.LastCellOfRowDummyRecord;
import org.apache.poi.hssf.eventusermodel.dummyrecord.MissingCellDummyRecord;
import org.apache.poi.hssf.model.HSSFFormulaParser;
import org.apache.poi.hssf.record.BOFRecord;
import org.apache.poi.hssf.record.BlankRecord;
import org.apache.poi.hssf.record.BoolErrRecord;
import org.apache.poi.hssf.record.BoundSheetRecord;
import org.apache.poi.hssf.record.FormulaRecord;
import org.apache.poi.hssf.record.LabelRecord;
import org.apache.poi.hssf.record.LabelSSTRecord;
import org.apache.poi.hssf.record.NoteRecord;
import org.apache.poi.hssf.record.NumberRecord;
import org.apache.poi.hssf.record.RKRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.SSTRecord;
import org.apache.poi.hssf.record.StringRecord;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 *<pre>
 * 功   能: EXCEL工具类
 * 创建者: 陈    林(Vickey)
 * 日   期: 2014-7-3上午10:39:01
 * Q  Q: 
 *</pre>
 */
public class Hxls2List implements HSSFListener {
	private int minColumns;
	private POIFSFileSystem fs;

	private int lastRowNumber;
	private int lastColumnNumber;
	

	/** Should we output the formula, or the value it has? */
	private boolean outputFormulaValues = true;

	/** For parsing Formulas */
	private SheetRecordCollectingListener workbookBuildingListener;
	private HSSFWorkbook stubWorkbook;

	// Records we pick up as we process
	private SSTRecord sstRecord;
	private FormatTrackingHSSFListener formatListener;

	/** So we known which sheet we're on */
	private int sheetIndex = -1;
	private BoundSheetRecord[] orderedBSRs;
	@SuppressWarnings("rawtypes")
	private ArrayList boundSheetRecords = new ArrayList();

	// For handling formulas with string results
	private int nextRow;
	private int nextColumn;
	private boolean outputNextStringRecord;

	private int curRow;
	private Map<String, Object> rowMap;
	@SuppressWarnings( "unused")
	private String sheetName;
	
	int test= 0;
	
	protected String insertErrorMsg;
	
	//头部信息（列索引，字段实体类）
	private Map<Integer, ExcelColumn> excelHeadMap;
	//需要转换的列
	private Map<String, Map> columnsConvertMap;
	// 根据excel生成list类型的数据
	private List<Object> contents;
	private Class cls;

	private Hxls2List(POIFSFileSystem fs){
		this.fs = fs;
		this.minColumns = -1;
		this.curRow = 0;
		this.rowMap = new HashMap<String, Object>();
	}

	public Hxls2List(File file,Map<Integer, ExcelColumn> excelHeadMap,Map<String, Map> columnsConvertMap,Class cls,ArrayList<Object> contents) throws FileNotFoundException, IOException{
		this(new POIFSFileSystem(new FileInputStream(file)));
		this.excelHeadMap = excelHeadMap;
		this.columnsConvertMap = columnsConvertMap;
		this.cls = cls;
		this.contents = contents;
	}
	
	//excel记录行操作方法，以sheet索引，行索引和行元素列表为参数，对sheet的一行元素进行操作，元素为String类型
	public void optRows(int sheetIndex,int curRow, Map<String, Object> rowMap2){
		if (curRow >= 1) {
			Object objClass;
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
	 * 遍历 excel 文件
	 */
	public void process() throws IOException {
		MissingRecordAwareHSSFListener listener = new MissingRecordAwareHSSFListener(
				this);
		formatListener = new FormatTrackingHSSFListener(listener);

		HSSFEventFactory factory = new HSSFEventFactory();
		HSSFRequest request = new HSSFRequest();

		if (outputFormulaValues) {
			request.addListenerForAllRecords(formatListener);
		} else {
			workbookBuildingListener = new SheetRecordCollectingListener(formatListener);
			request.addListenerForAllRecords(workbookBuildingListener);
		}

		factory.processWorkbookEvents(request, fs);
	}
	
	/**
	 * HSSFListener 监听方法，处理 Record
	 */
	@SuppressWarnings("unchecked")
	public void processRecord(Record record) {
		test ++;
		int thisRow = -1;
		int thisColumn = -1;
		Object thisStr = null;
		Object value = null;
		
		switch (record.getSid()) {
		case BoundSheetRecord.sid:
			boundSheetRecords.add(record);
			break;
		case BOFRecord.sid:
			BOFRecord br = (BOFRecord) record;
			if (br.getType() == BOFRecord.TYPE_WORKSHEET) {
				// Create sub workbook if required
				if (workbookBuildingListener != null && stubWorkbook == null) {
					stubWorkbook = workbookBuildingListener
							.getStubHSSFWorkbook();
				}
				// Works by ordering the BSRs by the location of
				// their BOFRecords, and then knowing that we
				// process BOFRecords in byte offset order
				sheetIndex++;
				if (orderedBSRs == null) {
					orderedBSRs = BoundSheetRecord
							.orderByBofPosition(boundSheetRecords);
				}
				sheetName = orderedBSRs[sheetIndex].getSheetname();
			}
			break;

		case SSTRecord.sid:
			sstRecord = (SSTRecord) record;
			break;

		case BlankRecord.sid:
			BlankRecord brec = (BlankRecord) record;
			thisRow = brec.getRow();
			thisColumn = brec.getColumn();
//			thisStr = "";
			rowMap.put(excelHeadMap.get(thisColumn).getFieldName(),null);
			break;
		case BoolErrRecord.sid:
			BoolErrRecord berec = (BoolErrRecord) record;

			thisRow = berec.getRow();
			thisColumn = berec.getColumn();
			thisStr = "";
			rowMap.put(excelHeadMap.get(thisColumn).getFieldName(),null);
			break;

		case FormulaRecord.sid:
			FormulaRecord frec = (FormulaRecord) record;

			thisRow = frec.getRow();
			thisColumn = frec.getColumn();

			if (outputFormulaValues) {
				if (Double.isNaN(frec.getValue())) {
					// Formula result is a string
					// This is stored in the next record
					outputNextStringRecord = true;
					nextRow = frec.getRow();
					nextColumn = frec.getColumn();
				} else {
					thisStr = formatListener.formatNumberDateCell(frec);
				}
			} else {
				thisStr = '"' + HSSFFormulaParser.toFormulaString(stubWorkbook,
						frec.getParsedExpression()) + '"';
			}
			if (excelHeadMap.containsKey(thisColumn)) {
				if (columnsConvertMap.containsKey(excelHeadMap.get(thisColumn).getFieldName())) {
					rowMap.put(excelHeadMap.get(thisColumn).getFieldName(), columnsConvertMap.get(excelHeadMap.get(thisColumn).getFieldName()).get(thisStr));
				}else {
					rowMap.put(excelHeadMap.get(thisColumn).getFieldName(),thisStr);
				}
			}
			break;
		case StringRecord.sid:
			if (outputNextStringRecord) {
				// String for formula
				StringRecord srec = (StringRecord) record;
				thisStr = srec.getString();
				thisRow = nextRow;
				thisColumn = nextColumn;
				outputNextStringRecord = false;
			}
			break;

		case LabelRecord.sid:
			LabelRecord lrec = (LabelRecord) record;

			curRow = thisRow = lrec.getRow();
			thisColumn = lrec.getColumn();
			value = lrec.getValue().trim();
//			value = value.equals(null)?" ":value;
			if (excelHeadMap.containsKey(thisColumn)) {
				if (columnsConvertMap.containsKey(excelHeadMap.get(thisColumn).getFieldName())) {
					rowMap.put(excelHeadMap.get(thisColumn).getFieldName(), columnsConvertMap.get(excelHeadMap.get(thisColumn).getFieldName()).get(thisStr));
				}else {
					rowMap.put(excelHeadMap.get(thisColumn).getFieldName(),thisStr);
				}
			}
			break;
		case LabelSSTRecord.sid:
			LabelSSTRecord lsrec = (LabelSSTRecord) record;

			curRow = thisRow = lsrec.getRow();
			thisColumn = lsrec.getColumn();
			if (sstRecord == null) {
				rowMap.put(excelHeadMap.get(thisColumn).getFieldName(),null);
			} else {
				value =  sstRecord.getString(lsrec.getSSTIndex()).toString().trim();
//				value = value.equals(null)?" ":value;
				if (excelHeadMap.containsKey(thisColumn)) {
					if (columnsConvertMap.containsKey(excelHeadMap.get(thisColumn).getFieldName())) {
						rowMap.put(excelHeadMap.get(thisColumn).getFieldName(), columnsConvertMap.get(excelHeadMap.get(thisColumn).getFieldName()).get(value));
					}else {
						rowMap.put(excelHeadMap.get(thisColumn).getFieldName(),value);
					}
				}
			}
			break;
		case NoteRecord.sid:
			NoteRecord nrec = (NoteRecord) record;

			thisRow = nrec.getRow();
			thisColumn = nrec.getColumn();
			//Find object to match nrec.getShapeId()
			thisStr = '"' + "()" + '"';
//			rowlist.add(thisColumn,thisStr);
			break;
		case NumberRecord.sid:
			NumberRecord numrec = (NumberRecord) record;
//			if(HSSFDateUtil.isInternalDateFormat(numrec.getXFIndex())){ 
			curRow = thisRow = numrec.getRow();
			thisColumn = numrec.getColumn();
			if (excelHeadMap.containsKey(thisColumn)) {
				if(excelHeadMap.get(thisColumn).getType() instanceof Date){	
					value = new SimpleDateFormat("yyyy-MM-dd").format(HSSFDateUtil.getJavaDate(numrec.getValue()));     
				}else{
					value = (Double)numrec.getValue();
				}
				if (columnsConvertMap.containsKey(excelHeadMap.get(thisColumn).getFieldName())) {
					rowMap.put(excelHeadMap.get(thisColumn).getFieldName(), columnsConvertMap.get(excelHeadMap.get(thisColumn).getFieldName()).get(value));
				}else {
					rowMap.put(excelHeadMap.get(thisColumn).getFieldName(),value);
				}
			}
			break;
		case RKRecord.sid:
			RKRecord rkrec = (RKRecord) record;

			thisRow = rkrec.getRow();
			thisColumn = rkrec.getColumn();
			thisStr = '"' + "()" + '"';
//			rowlist.add(thisColumn,thisStr);
			break;
		default:
			break;
		}

		// 遇到新行的操作
		if (thisRow != -1 && thisRow != lastRowNumber) {
			lastColumnNumber = -1;
		}

		// 空值的操作
		if (record instanceof MissingCellDummyRecord) {
			MissingCellDummyRecord mc = (MissingCellDummyRecord) record;
			curRow = thisRow = mc.getRow();
			thisColumn = mc.getColumn();
			rowMap.put(excelHeadMap.get(thisColumn).getFieldName(),null);
		}

		// 更新行和列的值
		if (thisRow > -1)
			lastRowNumber = thisRow;
		if (thisColumn > -1)
			lastColumnNumber = thisColumn;

		// 行结束时的操作
		if (record instanceof LastCellOfRowDummyRecord) {
			if (minColumns > 0) {
				// 列值重新置空
				if (lastColumnNumber == -1) {
					lastColumnNumber = 0;
				}
			}
			// 行结束时， 调用 optRows() 方法
			lastColumnNumber = -1;
			optRows(sheetIndex,curRow, rowMap);
			rowMap.clear();
		}
	}
}
