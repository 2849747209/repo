package utils.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import utils.excel.type.*;
import utils.excel.validate.mappers.RuleMapper;
import utils.excel.validate.rules.And;
import utils.excel.validate.rules.Gt;
import utils.excel.validate.rules.Lt;
import utils.excel.validate.rules.Rule;

public class ExcelUtils {

	private String workBookName;
	private XSSFWorkbook workBook;

	private List<SheetWapper> sheets;
	
	public ExcelUtils() {
		this.workBook = new XSSFWorkbook();
		this.sheets = new ArrayList<SheetWapper>();
	}

	public XSSFWorkbook getWorkBook() {
		return workBook;
	}

	public String getWorkBookName() {
		return this.workBookName;
	}
	
	public void createWorkBook(String name) {
		this.workBookName = name;
	}
	
	public void createSheet(String name, List<Title> titles, List<?> datas) {
		XSSFSheet sheet = this.workBook.createSheet(name);
		Title[] titleArr = new Title[titles.size()];
		SheetWapper wapper = new SheetWapper(sheet, titles.toArray(titleArr), datas);
		sheets.add(wapper);
	}
	
	public void out(OutputStream os) {
		for (SheetWapper sheetWapper : sheets) {
			sheetWapper.init();
		}
		
		try {
			workBook.write(os);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static class SheetWapper {

		private final XSSFSheet sheet;
		
		private Title[] titles;
		
		private List<?> data;

		public SheetWapper(XSSFSheet sheet, Title[] titles, List<?> data) {
			this.sheet = sheet;
			this.titles = titles;
			this.data = data;
		}
		
		public void init() {
			initTitle();
			//initData();
			//统计
			//statistics();
		}
		
		private void initTitle() {
			XSSFRow row = sheet.createRow(0);

			for (int i=0; i<titles.length; i++) {
				XSSFCell cell = row.createCell(i);
				XSSFRichTextString text = new XSSFRichTextString(titles[i].getName());
				cell.setCellValue(text);

				if (null != titles[i].getTitleStyle()) {
					Style style = titles[i].getTitleStyle();
					setCellColorAndFont(cell, style);
				}
			}
		}
		
		private void initData() {
			Map<String, Method> methodMapper = getMethodMapper();
			
			int lastRowNum = sheet.getLastRowNum() + 1;
			for (Object obj : data) {
				XSSFRow row = sheet.createRow(lastRowNum);

				for (int i=0; i<titles.length; i++) {
					XSSFCell cell = row.createCell(i);
					Title title = titles[i];
					
					Method method = methodMapper.get(title.getFieldName());
					final Object value;
					try {
						value = method.invoke(obj);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}

					Type type = title.getToType();
					if (Str.class.isInstance(type)) {
						Str toType = (Str) type;
						if (null != value)
							cell.setCellValue(toType.getVal((String) value));
						else
							cell.setCellValue("");
					} else if (Num.class.isInstance(type)) {
						Num toType = (Num) type;
						if (null != value) {
							cell.setCellValue(new BigDecimal(value.toString()).setScale(toType.getVal()).doubleValue());
						} else
							cell.setCellValue(0);
					} else if (Cur.class.isInstance(type)) {
						Cur toType = (Cur) type;
						if (null != value)
							cell.setCellValue(new BigDecimal(value.toString()).doubleValue());
						else
							cell.setCellValue(BigDecimal.ZERO.doubleValue());

						setDataFormat(cell, toType.getVal());
					} else if (Dat.class.isInstance(type)) {
						Dat toType = (Dat) type;
						if (null != value)
							cell.setCellValue((Date) value);
						else
							cell.setCellValue("");

						setDataFormat(cell, toType.getVal());
					} else if (utils.excel.type.Map.class.isInstance(type)) {
						utils.excel.type.Map toType = (utils.excel.type.Map) type;
						if (null != value) {
							String val = toType.getVal(value);
							cell.setCellValue(val);
						} else
							cell.setCellValue("");
					} else {
						cell.setCellValue("");
					}

					Style style = title.mapper(value);
					if (null != style) {
						setCellColorAndFont(cell, style);
					}
				}
				
				lastRowNum++;
			}
		}

		private Map<String, Method> getMethodMapper() {
			Map<String, Method> methodMap = new HashMap<String, Method>();
			
			for (Object obj : data) {
				if (null != obj) {
					Class<?> cls = obj.getClass();

					Method[] methods = cls.getMethods();
					for (Method method : methods) {
						String methodName = method.getName();
						if (methodName.startsWith("get")) {
							if (methodName.length() > 3) {
								StringBuilder builder = new StringBuilder(methodName.substring(3));
								char chr = Character.toLowerCase(builder.charAt(0));
								builder.setCharAt(0, chr);
								methodMap.put(builder.toString(), method);
							} else {
								methodMap.put("", method);
							}
						}
					}

					break;
				}
			}
			
			return methodMap;
		}
		
		private void setCellColorAndFont(XSSFCell cell, Style style) {
			XSSFWorkbook workbook = sheet.getWorkbook();

			XSSFCellStyle cellStyle = cell.getCellStyle();
			if (null == cellStyle) {
				cellStyle = workbook.createCellStyle();
			}
			
			XSSFFont font = workbook.createFont();
			font.setColor(style.getFontColor());
			cellStyle.setFont(font);
			
			cellStyle.setFillForegroundColor(style.getBackgroundColor());
			cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

			cell.setCellStyle(cellStyle);
		}

		private void setDataFormat(XSSFCell cell, String format) {
			XSSFWorkbook workbook = sheet.getWorkbook();

			if (null != format && !"".equals(format)) {
				short fmt = workbook.createDataFormat().getFormat(format);

				XSSFCellStyle cellStyle = cell.getCellStyle();
				if (null == cellStyle) {
					cellStyle = workbook.createCellStyle();
				}

				cellStyle.setDataFormat(fmt);

				//cell.setCellStyle(cellStyle);
			}
		}
		
		private void statistics() {
			int size = data.size();
			if (size > 0) {
				int dataLastRow = size + 1;
				for (int i = 0; i < titles.length; i++) {
					Title title = titles[i];
					Formula[] formulas = title.getFormulas();
					if (formulas != null) {
						int rowNum = dataLastRow + 1;
						for (Formula formula : formulas) {
							XSSFRow row = sheet.getRow(rowNum);
							if (null == row)
								row = sheet.createRow(rowNum);

							XSSFCell createCell = row.createCell(i);
							String expression = formula.getVal(i, 2, i, dataLastRow);
							createCell.setCellFormula(expression);

							rowNum++;
						}
					}
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		ExcelUtils xlsxUtil = new ExcelUtils();
		
		xlsxUtil.createWorkBook("test.xlsx");
		
		List<Title> titles = new ArrayList<Title>();

		Title title1 = new Title("stringValue", "name", new Str(null, null), new Style(IndexedColors.RED.getIndex(), IndexedColors.GREY_50_PERCENT.getIndex()), null);
		titles.add(title1);

		Rule<Double> gt = new Gt(100.00);
		Rule<Double> lt = new Lt(2000.00);
		Rule<Double> and = new And(gt, lt);
		RuleMapper<Double, Style> ruleStyleMapper = new RuleMapper<Double, Style>(and, new Style(IndexedColors.RED.getIndex(), IndexedColors.GREY_50_PERCENT.getIndex()), new Style(IndexedColors.BLUE.getIndex(), IndexedColors.LIGHT_ORANGE.getIndex()));
		Title title2 = new Title("numberValue", "number", new Num(6), new Style(IndexedColors.RED.getIndex(), IndexedColors.GREY_50_PERCENT.getIndex()), ruleStyleMapper, Formula.AVERAGE);
		titles.add(title2);

		Title title3 = new Title("currencyValue", "currency", new Cur("###,##0.00"), new Style(IndexedColors.RED.getIndex(), IndexedColors.GREY_50_PERCENT.getIndex()), null, Formula.MAX);
		titles.add(title3);

		Title title4 = new Title("dateValue", "date", new Dat("yyyy-MM-dd HH:mm:ss"), new Style(IndexedColors.RED.getIndex(), IndexedColors.GREY_50_PERCENT.getIndex()), null, null);
		titles.add(title4);
		
		List<Bean> datas = new ArrayList<Bean>();
		Bean bean1 = new Bean();
		bean1.setName("bean1Name");
		bean1.setDate(new Date());
		bean1.setCurrency(new BigDecimal("1876.53"));
		bean1.setNumber(234234.999);
		datas.add(bean1);
		Bean bean2 = new Bean();
		bean2.setName("bean2Name");
		bean2.setDate(new Date());
		bean2.setCurrency(new BigDecimal("1236.332"));
		bean2.setNumber(1563.969);
		datas.add(bean2);
		Bean bean3 = new Bean();
		bean3.setName("黄业鹏");
		bean3.setDate(new Date());
		bean3.setCurrency(new BigDecimal("157636.31"));
		bean3.setNumber(35635.969);
		datas.add(bean3);
		Bean bean4 = new Bean();
		bean4.setName(null);
		bean4.setDate(new Date());
		bean4.setCurrency(null);
		bean4.setNumber(24.0);
		datas.add(bean4);
		
		xlsxUtil.createSheet("testSheet1", titles, datas);
		//xlsxUtil.createSheet("testSheet2", titles, datas);
		OutputStream os = new FileOutputStream(new File("D://" + xlsxUtil.workBookName));
		xlsxUtil.out(os);
		os.close();
	}
}
