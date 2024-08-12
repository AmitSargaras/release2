package com.integrosys.cms.app.poi.report;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;

import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.poi.report.xml.schema.IReportConstants;
import com.integrosys.cms.app.poi.report.xml.schema.Reports;

public class ReportParser implements IReportConstants{
	public Reports getReportObject(String reportId, OBFilter filter) {
		
		if("RPT0041".equals(reportId)){
			if("ALL".equals(filter.getFilterUserMode())){
				reportId="RPT0041_A";
			}
		}
		if("RPT0047".equals(reportId)){
			if("RM".equals(filter.getProfile())){
				reportId="RPT0047_A";
			}
			else if("BRANCH".equals(filter.getProfile())){
				reportId="RPT0047_B";
			}
			else if ("CPU".equals(filter.getProfile())){
				reportId="RPT0047_C";
			}
		}
		String templateKey= REPORT_MAPPING_PREFIX+reportId+REPORT_MAPPING_TEMPLATE_SUFFIX;

		String templateBasePath=PropertyManager.getValue(REPORT_TEMPLATE_BASE_PATH);
		String templatePath=templateBasePath+PropertyManager.getValue(templateKey);
		
		Reports reports=null;
		try {
			/*InputStream inputStream = ReportParser.class.getResourceAsStream(templatePath);
			reports = (Reports) Unmarshaller.unmarshal(Reports.class, new InputStreamReader(inputStream));*/
			FileInputStream fis = new FileInputStream(templatePath);
			InputStreamReader read = new InputStreamReader(fis);
			reports = (Reports) Unmarshaller.unmarshal(Reports.class, read);
		} catch (MarshalException e) {
			e.printStackTrace();
			throw new ReportException("Error in marshaling report template.",e);
		} catch (ValidationException e) {
			e.printStackTrace();
			throw new ReportException("Validating report template failed.",e);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new ReportException("Template not found.",e);
		}
		return reports;
	}
	
	
	/*public static void main(String[] args) {
		try {
			//initializing properties
			String reportId="SYS001";
			String templateKey= REPORT_MAPPING_PREFIX+reportId+REPORT_MAPPING_TEMPLATE_SUFFIX;
			String templatePath=PropertyUtils.getProperties().getProperty(templateKey);
			
			InputStream istrm = ReportParser.class.getResourceAsStream(templatePath);
			InputStreamReader reader= new InputStreamReader(istrm);
			Reports reports = (Reports) Unmarshaller.unmarshal(Reports.class,reader);
			
		} catch (MarshalException e) {
			e.printStackTrace();
		} catch (ValidationException e) {
			e.printStackTrace();
		}
	}
*/	
}
