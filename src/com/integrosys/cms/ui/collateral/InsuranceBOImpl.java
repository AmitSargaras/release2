package com.integrosys.cms.ui.collateral;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.common.util.MapperUtil;
import com.integrosys.cms.ui.collateral.bus.IInsuranceHistoryReport;

public class InsuranceBOImpl implements IInsuranceBO {

	private InsuranceGCJdbcImpl insuranceGcJdbc;
	
	public InsuranceGCJdbcImpl getInsuranceGcJdbc() {
		return insuranceGcJdbc;
	}
	public void setInsuranceGcJdbc(InsuranceGCJdbcImpl insuranceGcJdbc) {
		this.insuranceGcJdbc = insuranceGcJdbc;
	}

	@Override
	public List<IInsuranceHistoryReport> getFullInsuranceHistory(InsuranceHistorySearchCriteria criteria) {
		return insuranceGcJdbc.getFullInsuranceHistory(criteria);
	}
	
	@Override
	public String createInsuranceHistoryReport(InsuranceHistorySearchCriteria criteria, Map<String, String> basicDataMap, Map insuranceCompanyNameMap) {
		if (criteria.getCollateralId() == null)
			return StringUtils.EMPTY;
		List<IInsuranceHistoryReport> history = insuranceGcJdbc.getFullInsuranceHistory(criteria);
		
		Workbook book = createWorkBookFromTemplate();
		
		writeDataToWorkBook(book, history, basicDataMap, insuranceCompanyNameMap);
		
		String fileName = saveWorkbook(book);
		
		return fileName;
	}

	
	private static Workbook createWorkBookFromTemplate() {
		String template = PropertyManager.getValue("insurance.history.report.template");
		Workbook book = new XSSFWorkbook();
		if (StringUtils.isNotEmpty(template)) {
			try {
				book = new XSSFWorkbook(OPCPackage.open(template));
			} catch (InvalidFormatException e) { 
				DefaultLogger.error(InsuranceBOImpl.class.getName(),
						"Exception while creating copy of Insurance History template", e);
			} catch(IOException e) {
				DefaultLogger.error(InsuranceBOImpl.class.getName(),
						"Exception while creating copy of Insurance History template", e);
			} catch(Exception e) {
				DefaultLogger.error(InsuranceBOImpl.class.getName(),
						"Exception while creating copy of Insurance History template", e);
			}
		}	
		return book;
	}
	
	private static void writeDataToWorkBook(Workbook book, List<IInsuranceHistoryReport> history, Map<String, String> basicDataMap, Map insuranceCompanyNameMap) {
		Sheet sheet = book.getSheetAt(0);
		int index = PropertyManager.getInt("insurance.history.report.row.start",6);
		
		for(IInsuranceHistoryReport insurance : history) {		
			Row row = sheet.createRow(index);
			Cell cell = row.createCell(0);
			cell.setCellValue(insurance.getIndex());			
			cell = row.createCell(1);
			cell.setCellValue(basicDataMap.get("customerName"));
			cell = row.createCell(2);
			cell.setCellValue(basicDataMap.get("customerId"));
			cell = row.createCell(3);
			cell.setCellValue(basicDataMap.get("collaetralId"));
			cell = row.createCell(4);
			cell.setCellValue(basicDataMap.get("collateralType"));
			cell = row.createCell(5);
			cell.setCellValue(basicDataMap.get("collateralSubType"));
			cell = row.createCell(6);
			cell.setCellValue(insurance.getStatus());
			cell = row.createCell(7);
			cell.setCellValue(MapperUtil.dateToString(insurance.getDueDate(), null));
			cell = row.createCell(8);
			cell.setCellValue(insurance.getInsurancePolicyNo());
			cell = row.createCell(9);
//			cell.setCellValue((String)insuranceCompanyNameMap.get(insurance.getInsuranceCompanyName()));
			cell.setCellValue(insurance.getInsuranceCompanyName());
			cell = row.createCell(10);
			cell.setCellValue(MapperUtil.bigDecimalToString(insurance.getInsuredAmount()));			
			cell = row.createCell(11);
			cell.setCellValue(MapperUtil.dateToString(insurance.getExpiryDate(), null));
			cell = row.createCell(12);
			cell.setCellValue(MapperUtil.dateToString(insurance.getReceivedDate(), null));
			cell = row.createCell(13);
			cell.setCellValue(insurance.getOldInsurancePolicyNo());
			cell = row.createCell(14);
//			cell.setCellValue(insurance.getOldInsuranceCompanyName());
			cell.setCellValue((String)insuranceCompanyNameMap.get(insurance.getOldInsuranceCompanyName()));
			cell = row.createCell(15);
			cell.setCellValue(MapperUtil.bigDecimalToString(insurance.getOldInsuredAmount()));
			cell = row.createCell(16);
			cell.setCellValue(MapperUtil.dateToString(insurance.getOldExpiryDate(), null));
			cell = row.createCell(17);
			cell.setCellValue(MapperUtil.dateToString(insurance.getOldReceivedDate(), null));
			index++;
		}
	}
	
	private static String saveWorkbook(Workbook book) {
		FileOutputStream fos = null;
		String fileName = generateFileName();
		String path = PropertyManager.getValue("insurance.history.report.path")+fileName;
		try {
			fos = new FileOutputStream(path);
			book.write(fos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				fos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return fileName;
	}
	
	private static String generateFileName(){
		return "InsuranceHistory_"+CommonUtil.getCurrentDateTime("yyyMMddHHmmss")+".xls";
	}
	
	
}
