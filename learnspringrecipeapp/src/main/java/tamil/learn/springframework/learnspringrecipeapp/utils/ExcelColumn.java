package tamil.learn.springframework.learnspringrecipeapp.utils;

/**
 *<pre>
 * 功   能: EXCEL工具类
 * 创建者: 陈    林(Vickey)
 * 日   期: 2014-7-3上午10:38:24
 * Q  Q: 
 *</pre>
 */
public class ExcelColumn {
	/**
	 * 索引[0、1、2、3...]
	 */
	private int index;
	
	/**
	 * 字段名称
	 */
	private String fieldName;
	
	/**
	 * 字段显示名称
	 */
	private String fieldDispName;
	
	/**
	 * 单元格宽度
	 * 默认3500
	 */
	private int width;
	
	/**
	 * 导出日期格式
	 * 默认：yyyy-MM-dd
	 */
	private String dateFormat;
	
	/**
	 * 数据类型
	 */
	private Object type;
	
	/**
	 * 是否垂直合并此列（true:上下单元格值若一样则合并）
	 */
	private boolean verticalMerge = false;
	

    int megreStartRow=0;
    boolean first = true,megre=false;
	
	public ExcelColumn() {
		
	}
	
	/**
	 * @param index 索引
	 * @param fieldName 字段名称
	 * @param fieldDispName 字段显示名称
	 */
	public ExcelColumn(int index, String fieldName, String fieldDispName) {
		super();
		this.index = index;
		this.fieldName = fieldName;
		this.fieldDispName = fieldDispName;
	}
	
	/**
	 * @param index 索引
	 * @param fieldName 字段名称
	 * @param fieldDispName 字段显示名称
	 * @param width 单元格宽度
	 */
	public ExcelColumn(int index, String fieldName, String fieldDispName, int width) {
		super();
		this.index = index;
		this.fieldName = fieldName;
		this.fieldDispName = fieldDispName;
		this.width = width;
	}
	/**
	 * @param index 索引
	 * @param fieldName 字段名称
	 * @param fieldDispName 字段显示名称
	 * @param verticalMerge 是否垂直合并此列（true:上下单元格值若一样则合并）
	 */
	public ExcelColumn(int index, String fieldName, String fieldDispName, boolean verticalMerge) {
		super();
		this.index = index;
		this.fieldName = fieldName;
		this.fieldDispName = fieldDispName;
		this.verticalMerge = verticalMerge;
	}
	
	/**
	 * @param index 索引
	 * @param fieldName 字段名称
	 * @param type 数据类型
	 */
	public ExcelColumn(int index, String fieldName,Object type) {
		super();
		this.index = index;
		this.fieldName = fieldName;
		this.type = type;
	}
	
	/**
	 * @param index 索引
	 * @param fieldName 字段名称
	 * @param fieldDispName 字段显示名称
	 * @param width 单元格宽度
	 */
	public ExcelColumn(int index, String fieldName, String fieldDispName, int width, boolean verticalMerge) {
		super();
		this.index = index;
		this.fieldName = fieldName;
		this.fieldDispName = fieldDispName;
		this.width = width;
		this.verticalMerge = verticalMerge;
	}
	
	/**
	 * @param index 索引
	 * @param fieldName 字段名称
	 * @param fieldDispName 字段显示名称
	 */
	public ExcelColumn(int index, String fieldName, String fieldDispName, String dateFormat, boolean verticalMerge) {
		super();
		this.index = index;
		this.fieldName = fieldName;
		this.fieldDispName = fieldDispName;
		this.setDateFormat(dateFormat);
		this.verticalMerge = verticalMerge;
	}
	/**
	 * @param index 索引
	 * @param fieldName 字段名称
	 * @param fieldDispName 字段显示名称
	 * @param width 单元格宽度
	 */
	public ExcelColumn(int index, String fieldName, String fieldDispName, int width,String dateFormat, boolean verticalMerge) {
		super();
		this.index = index;
		this.fieldName = fieldName;
		this.fieldDispName = fieldDispName;
		this.width = width;
		this.setDateFormat(dateFormat);
		this.verticalMerge = verticalMerge;
	}

	public int getIndex() {
		return index;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldDispName() {
		return fieldDispName;
	}

	public void setFieldDispName(String fieldDispName) {
		this.fieldDispName = fieldDispName;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getWidth() {
		return width;
	}
	
	@Override
	public String toString() {
		return "ExcelColumn [fieldDispName=" + fieldDispName + ", fieldName="
		+ fieldName + ", index=" + index + ", width=" + width + "]";
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public Object getType() {
		return type;
	}

	public void setType(Object type) {
		this.type = type;
	}

	public void setVerticalMerge(boolean verticalMerge) {
		this.verticalMerge = verticalMerge;
	}

	public boolean isVerticalMerge() {
		return verticalMerge;
	}

	public int getMegreStartRow() {
		return megreStartRow;
	}

	public void setMegreStartRow(int megreStartRow) {
		this.megreStartRow = megreStartRow;
	}

	public boolean isFirst() {
		return first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}

	public boolean isMegre() {
		return megre;
	}

	public void setMegre(boolean megre) {
		this.megre = megre;
	}

}
