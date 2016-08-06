package cn.rangers.develop.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 从excel中读取数据，插入到excel中，通用类 兼容HSSF和XSSF格式 支持excel中读取指定的列，支持对列的条件操作
 * 支持对列指定不同的字段名，插入不同的数据库
 * 
 * @version 1.0
 * @since JDK1.7
 * @author fuhw
 * @date 2016年4月7日 下午2:54:54
 */
public class ExcelOperaterUtils {

	private static Workbook wb;// 获取excel工作簿
	private static Sheet sheet;// 获取excel中的表格
	private static Row row;// 获取excel中的行，列
	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelOperaterUtils.class);

	/**
	 * 根据传入数据条件，从excel中读取数据
	 * 
	 * @param excelPath excel的文件路径
	 * @param excelName excel的文件名称
	 * @param params 参数集合<excel中的列名，条件名称>
	 * @param conditionMap 条件集合<数据库中的字段名，条件名称>
	 * @return
	 * 
	 * @author fuhw
	 * @date 2016年4月6日 上午11:32:14
	 */
	public Map<String, Object> readFromExcel(String excelPath, String excelName, LinkedHashMap<String, String> params,
												LinkedHashMap<String, String> conditionMap) {
		String filePath = excelPath + "\\" + excelName;
		return this.readFromExcel(filePath, params, conditionMap);
	}

	public Map<String, Object> readFromExcel(String filePath, LinkedHashMap<String, String> params,
												LinkedHashMap<String, String> conditionMap){
		/**
		 * 1.加载文件
		 */
		File file = new File(filePath);
		String excelName = file.getName();
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			System.out.println("文件加载异常，没有找到指定目录的文件！！");
			e.printStackTrace();
		}
		return readFromExcel(inputStream, excelName,params, conditionMap);
	}
	
	
	/**
	 * 根据传入数据条件，从excel中读取数据
	 * 
	 * @param filePath 带文件名的全路径
	 * @param excelName 表格名称
	 * @param params 条件集合<excel中的列名，条件名称> 保证集合的有序性
	 * @param conditionMap 条件集合<数据库中的字段名，条件名称> 保证集合的有序性
	 * @return
	 * 
	 * @author fuhw
	 * @date 2016年4月6日 上午11:32:14
	 */
	public Map<String, Object> readFromExcel(InputStream inputStream,String excelName, LinkedHashMap<String, String> params,
												LinkedHashMap<String, String> conditionMap) {
		
		
		if(inputStream == null) {
			LOGGER.info("传入的文件流为 空！");
			return null;
		}
		
		
		/**
		 * 2.读取文件内容
		 */
		LOGGER.info("========start read content from excel=========");
		Map<String, Object> result = new LinkedHashMap<>();
		result.put("excel_name", excelName);

		int rowNum = 0;
		int colNum = 0;

		if (params == null) {
			System.out.println("参数有误，你没有指定要读取的列名！");
			return null;
		}
		try {
			wb = WorkbookFactory.create(inputStream);// 工作簿
			inputStream.close();
			sheet = wb.getSheetAt(0);// 表格
			rowNum = sheet.getLastRowNum();// 行数
			row = sheet.getRow(0);
			colNum = row.getLastCellNum();// 列数

		} catch (Exception e) {
			System.out.println(excelName + "文件类型不正确，不是纯正的格式！可能是临时文件，或者是直接更改的后缀或是其他导出的结果！");
			e.printStackTrace();
		}
		/**
		 * 遍历列
		 */

		for (int i = 0; i < colNum; i++) {
			String title = getCellFormatValue(row.getCell((short) i)).trim();// sheet.getRow(0);表格第一行为标题
			if (!params.containsKey(title)) {// 如果要读取的列集合中不包含该列不进行读取
				continue;
			}
			String field = params.get(title);// 获取标题对应的字段
			List<String> fieldList = new ArrayList<>();

			// 遍历行,小于等于poi获取到的列名
			for (int j = 0; j <= rowNum; j++) {
				row = sheet.getRow(j);// 类似行数++
				if (row == null) {// 行为空，终止循环
					break;
				}
				String temp = getCellFormatValue(row.getCell((short) i)).trim();
				if (row.getCell((short) i) == null || temp.equals("")) {// 行的值为空，终止循环
					break;
				}
				/**
				 * 判断条件集合是否为空
				 */
				if (conditionMap != null && j > 0) {// 大于0，过滤掉标题
					if (conditionMap.containsKey(title)) {
						try {
							String key = conditionMap.get(title);
							if (key.contains(ExcelReadConditions.SUB_STRING)) {
								if (!temp.contains(".")) {
									continue;
								}
								temp = temp.split("\\.")[0];// 截取掉后缀,注意要转译
							}
							if (key.contains(ExcelReadConditions.FILTER_STRING)) {
								// 操作 -- operate (需要转换 证券买入0，证券卖出 1，融资买入3，融券卖出4)
								/**
								 * 普通的方式
								 */
								switch (temp) {
									case "证券买入":
										temp = "0";
										break;
									case "证券卖出":
										temp = "1";
										break;
									case "融资买入":
										temp = "3";
										break;
									case "还款卖出":
										temp = "4";
										break;
									default:
										temp = null;
										break;
								}
								/**
								 * 使用配置文件读取(字符间的转换)
								 */
								// ResourceBundle bundle =
								// ResourceBundle.getBundle("condition");
								// boolean isContains = false;
								// for(String
								// str:bundle.getString("statusName").split(",")){
								// if(temp.equalsIgnoreCase(str)){
								// temp = bundle.getString(str);
								// isContains = true;
								// break;
								// }
								// }
								// if(!isContains){
								// temp = null;
								// }
							}
							if (key.contains(ExcelReadConditions.FORMAT_STRING)) {// 格式化操作，6位不足补零
								temp = String.format("%6s", temp).replace(" ", "0");
							}
						} catch (Exception e) {
							System.out.println("没有匹配的条件，检查条件输入是否有误！");
							e.printStackTrace();
						}
					}
				}

				if (temp != null) {
					fieldList.add(temp);
				}
			}
			result.put(field, fieldList);
			row = sheet.getRow(0);
		}
		LOGGER.info("========end read content from excel=========");
		return result;
	}

	/**
	 * 
	 * 把数据导出到excel中
	 * 
	 * @param results 参数map集合<表中字段名，对应的excel中列List集合>
	 * @param params 参数map集合<表中字段名，excel中的列名>
	 * 
	 * @author fuhw
	 * @date 2016年4月11日 下午1:44:40
	 */
	@SuppressWarnings("unchecked")
	public void exportToExcel(Map<String, Object> result, LinkedHashMap<String, String> params, String filePath) {
		// 1.创建Excel工作薄对象
		HSSFWorkbook wb = new HSSFWorkbook();
		// 2.创建Excel工作表对象
		HSSFSheet sheet = wb.createSheet("工作表");
		// 3.创建Excel工作表的行
		HSSFRow row = null;
		// 4.创建单元格样式
		CellStyle cellStyle = wb.createCellStyle();
		// 设置这些样式
		cellStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		String excelName = (String) result.get("excel_name");

		List<String> fieldList = new ArrayList<>();// 获取字段名
		List<List<String>> fields = new ArrayList<>();// 存所有列的集合
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(filePath + "\\" + excelName);
			/**
			 * 字段名列表
			 */
			for (Entry<String, String> entry : params.entrySet()) {
				fieldList.add(entry.getKey());
			}
			/**
			 * 解析封装数据：所有列的集合
			 */
			for (int i = 0; i < fieldList.size(); i++) {
				if (result.get(fieldList.get(i)) == null) {
					continue;
				}
				fields.add((List<String>) result.get(fieldList.get(i)));
			}

			for (int j = 0; j < fields.get(0).size(); j++) {// 每列多少个值（行数）
				row = sheet.createRow(j);// 每一行，创建一个excel表格的行
				for (int i = 0; i < fields.size(); i++) {// 几列属性（列数）
					if (fields.get(i).get(j) == null) {
						break;
					}
					// 在该行创建一个cell格子，存放的值，这一列i，这一行j的值
					row.createCell(i).setCellStyle(cellStyle);
					row.createCell(i).setCellValue(fields.get(i).get(j));
				}
			}
			wb.write(fout);
			fout.close();
			System.out.println("excel表格创建成功！");
		} catch (IOException e1) {
			System.out.println("excel表格创建出现异常！");
			e1.printStackTrace();
		}
	}

	/**
	 * 根据XSSFCell类型设置数据
	 * 
	 * @param cell
	 * @return
	 */
	private String getCellFormatValue(Cell cell) {
		String cellvalue = "";
		if (cell != null) {
			// 判断当前Cell的Type
			switch (cell.getCellType()) {

				case Cell.CELL_TYPE_NUMERIC: // 数值型
					if (HSSFDateUtil.isCellDateFormatted(cell)) {
						// 如果是date类型则 ，获取该cell的date值
						Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
						SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
						cellvalue = format.format(date);
						;
					} else {// 纯数字
					// BigDecimal big=new
					// BigDecimal(cell.getNumericCellValue());
					// cellvalue = big.toString();
						cellvalue = String.valueOf(cell.getNumericCellValue());
						// 解决1234.0 去掉后面的.0
						if (null != cellvalue && !"".equals(cellvalue.trim())) {
							String[] item = cellvalue.split("[.]");
							if (1 < item.length && "0".equals(item[1])) {
								cellvalue = item[0];
							}
						}
					}
					break;
				// 字符串类型
				case Cell.CELL_TYPE_STRING:
					cellvalue = cell.getStringCellValue().toString();
					break;
				// 公式类型
				case Cell.CELL_TYPE_FORMULA:
					// 读公式计算值
					cellvalue = String.valueOf(cell.getNumericCellValue());
					if (cellvalue.equals("NaN")) {// 如果获取的数据值为非法值,则转换为获取字符串
						cellvalue = cell.getStringCellValue().toString();
					}
					break;
				// 布尔类型
				case Cell.CELL_TYPE_BOOLEAN:
					cellvalue = " " + cell.getBooleanCellValue();
					break;
				// 空值
				case Cell.CELL_TYPE_BLANK:
					cellvalue = "";
					System.out.println("excel表格中出现空值");
					break;
				// 故障
				case Cell.CELL_TYPE_ERROR:
					cellvalue = "";
					System.out.println("excel出现故障");
					break;
				default:
					cellvalue = cell.getStringCellValue().toString();
			}
		}
		if ("null".endsWith(cellvalue.trim())) {
			cellvalue = "";
		}
		return cellvalue;
	}

}
