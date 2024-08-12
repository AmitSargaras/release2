package com.integrosys.cms.ui.genlad;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.UnhandledException;
import org.apache.commons.lang.time.DateFormatUtils;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.poi.report.IReportService;
import com.integrosys.cms.app.poi.report.OBFilter;
import com.integrosys.cms.app.poi.report.xml.schema.IReportConstants;

/**
 * @author cyliew
 * @author Chong Jun Yong
 * @since 2006/10/27
 */
public class DownloadReportCmd extends AbstractCommand implements ICommonEventConstant {

	private IReportService reportService;
	
	public IReportService getReportService() {
		return reportService;
	}

	public void setReportService(IReportService reportService) {
		this.reportService = reportService;
	}

	/**
	 * Default Constructor
	 */
	public DownloadReportCmd() {

	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "reportId", "java.lang.String", REQUEST_SCOPE },
				{ "reportFormObj","com.integrosys.cms.app.poi.report.OBFilter",FORM_SCOPE },
				{ "fileName", "java.lang.String", REQUEST_SCOPE },
				{ "reportfile","java.lang.String",SERVICE_SCOPE },
				{ "count", "java.lang.String", REQUEST_SCOPE }
				});
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "output", "java.io.ByteArrayOutputStream", REQUEST_SCOPE },
				{ "reportfile", "java.lang.String", REQUEST_SCOPE },
				{ "fileName", "java.lang.String", REQUEST_SCOPE },
				{ "count", "java.lang.String", REQUEST_SCOPE }				
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();

		DefaultLogger.debug(this, "Inside doExecute()");
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] fileData;
		try {
			
			String count = (String) map.get("count");
			String reportfile = (String) map.get("reportfile");
			String fileName = (String) map.get("fileName");
			
			OBFilter filter = (OBFilter) map.get("reportFormObj");
			
			File file = new File(reportfile);
			
			fileData = IOUtils.toByteArray(new FileInputStream(file));
			output.write(fileData);
			resultMap.put("fileName", fileName);
			resultMap.put("count", count);
		}
		catch (Exception e) {
			throw new UnhandledException(e);
			// todo
		}
		finally {
			if (!resultMap.containsKey("fileName")) {
				resultMap.put("fileName", "");
			}
		}

		resultMap.put("output", output);
		try {
			output.flush();
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
	/**
	 * This method will return the basepath
	 * 
	 * @return String
	 */
	public String getBasePath() {
		return PropertyManager.getValue(IReportConstants.BASE_PATH);
	}
}
