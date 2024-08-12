package com.integrosys.cms.ui.collateral;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.UnhandledException;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import static com.integrosys.cms.app.common.constant.ICMSConstant.FILE_NAME;
import static com.integrosys.cms.app.common.constant.ICMSConstant.REPORT_FILE;
import static com.integrosys.cms.app.common.constant.ICMSConstant.OUTPUT;

public class DownloadInsuranceHistoryReportCommand extends AbstractCommand implements ICommonEventConstant {

	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ EVENT, String.class.getName(), REQUEST_SCOPE },
				{ FILE_NAME, String.class.getName(), SERVICE_SCOPE },
				{ REPORT_FILE, String.class.getName(), SERVICE_SCOPE }, 
		});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { 
				{ OUTPUT, ByteArrayOutputStream.class.getName(), REQUEST_SCOPE },
				{ FILE_NAME, String.class.getName(), REQUEST_SCOPE } 
		});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		DefaultLogger.debug(this, "Inside doExecute()");
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] fileData;
		
		try {
			String reportfile = (String) map.get(REPORT_FILE);
			String fileName = (String) map.get(FILE_NAME);

			File file = new File(reportfile);

			fileData = IOUtils.toByteArray(new FileInputStream(file));
			output.write(fileData);
			resultMap.put(FILE_NAME, fileName);
		} catch (Exception e) {
			DefaultLogger.error(this, e.getMessage(), e);
			throw new UnhandledException(e);
		} finally {
			if (!resultMap.containsKey(FILE_NAME)) {
				resultMap.put(FILE_NAME, "");
			}
		}

		resultMap.put(OUTPUT, output);
		DefaultLogger.debug(this, "Going out of doExecute()");

		HashMap<String, Map<String, ?>> returnMap = new HashMap<String, Map<String, ?>>();
		returnMap.put(COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
