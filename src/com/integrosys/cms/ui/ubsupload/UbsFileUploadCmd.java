/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.ubsupload;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.batch.common.filereader.CSVReader;
import com.integrosys.cms.batch.common.filereader.ProcessDataFile;
import com.integrosys.cms.batch.ubs.IUbsErrDetLog;
import com.integrosys.cms.batch.ubs.IUbsErrorLog;
import com.integrosys.cms.batch.ubs.OBUbsErrDetLog;
import com.integrosys.cms.batch.ubs.OBUbsErrorLog;
import com.integrosys.cms.ui.ubsupload.proxy.IUbsUploadProxyManager;

/**
 * @author $Author: Abhijeett J$ Command for UBS Upload
 */
public class UbsFileUploadCmd extends AbstractCommand implements ICommonEventConstant {
	public static final String UBS_UPLOAD = "UbsUpload";
	private IUbsUploadProxyManager ubsuploadProxy;

	public IUbsUploadProxyManager getUbsuploadProxy() {
		return ubsuploadProxy;
	}

	public void setUbsuploadProxy(IUbsUploadProxyManager ubsuploadProxy) {
		this.ubsuploadProxy = ubsuploadProxy;
	}

	/**
	 * Default Constructor
	 */

	public UbsFileUploadCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "remarks", "java.lang.String", REQUEST_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE },
				{ "ubsuploadObj", "com.integrosys.cms.ui.ubsupload.OBUbsUpload", FORM_SCOPE } });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE },
				{ "ubsuploadObj", "com.integrosys.cms.ui.ubsupload.OBUbsUpload", FORM_SCOPE },
				{ "errorList", "java.util.HashMap", REQUEST_SCOPE },
				{ "rowCount", "java.lang.Integer", REQUEST_SCOPE },
				{ "fileUploadPending", "java.lang.String", REQUEST_SCOPE },
				{ "fileCheckSum", "java.lang.String", REQUEST_SCOPE },
				{ "fileType", "java.lang.String", REQUEST_SCOPE },
				{ "objUbsErrorLog", "com.integrosys.cms.batch.ubs.IUbsErrorLog", REQUEST_SCOPE },
				{ "objUbsErrorLogService", "com.integrosys.cms.batch.ubs.IUbsErrorLog", SERVICE_SCOPE },
				{ "ubsErrorLogList", "java.util.List", SERVICE_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		String strError = "";
		String fileType = "";
		String uploadId = "";
		Set errMsg = null;
		IUbsErrorLog objUbsErrorLog = new OBUbsErrorLog();
		;
		Locale local = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		/* set local */
		CSVReader.setLocale(local);
		Set stErrorDet = null;
		int count = 0;
		IUbsErrDetLog obUbsErrDetLog[] = null;
		try {

			OBUbsUpload ubsupload = (OBUbsUpload) map.get("ubsuploadObj");
			if (!ubsupload.getFileUpload().getFileName().endsWith(".csv")
					&& !ubsupload.getFileUpload().getFileName().endsWith(".CSV")) {
				fileType = "NOT_CSV";
				strError = "errorEveList";
			} else {
				ProcessDataFile dataFile = new ProcessDataFile();
				ArrayList resultList = dataFile.processFile(ubsupload.getFileUpload(), UBS_UPLOAD);
				try {
					uploadId = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_LIMIT_UPLOAD, true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				obUbsErrDetLog = new IUbsErrDetLog[dataFile.getErrorList().size()];
				for (int j = 0; j < dataFile.getMaxCount(); j++) {

					String[][] errorData = new String[50][2];
					errorData = (String[][]) dataFile.getErrorList().get(new Integer(j));

					if (!(errorData == null)) {
						errMsg = new HashSet();
						for (int k = 0; k <= errorData.length - 1; k++) {
							if (errorData[k][0] != null) {
//								DefaultLogger.debug(this, errorData[k][0] + " Value: " + errorData[k][1]);
								errMsg.add(errorData[k][0]);
							}
						}
						obUbsErrDetLog[count] = new OBUbsErrDetLog();
						obUbsErrDetLog[count].setPtId(uploadId);
						obUbsErrDetLog[count].setRecordNo(j + 1 + "");
						obUbsErrDetLog[count].setErrorMsg("Validation Error in " + errMsg.toString());
						obUbsErrDetLog[count].setTime(new Date());
						count++;
					}

				}
				objUbsErrorLog = getUbsuploadProxy().insertUbsfile(resultList, ubsupload.getFileUpload().getFileName(),
						uploadId, obUbsErrDetLog);
				stErrorDet = objUbsErrorLog.getErrEntriesSet();
				/*
				 * while(itemIter.hasNext()){ obUbsErrDetLog =
				 * (IUbsErrDetLog)itemIter.next(); }
				 */
			}

			resultMap.put("fileType", fileType);
			resultMap.put("errorEveList", strError);
			resultMap.put("objUbsErrorLog", objUbsErrorLog);
			// Added BY Anil for Pagination
			List ubsErrorLogList = new ArrayList();
			if (objUbsErrorLog.getErrEntriesSet() != null && objUbsErrorLog.getErrEntriesSet().size() > 0)
				ubsErrorLogList.addAll(objUbsErrorLog.getErrEntriesSet());

			DefaultLogger.debug(this, "-------UbsErrorLog size------" + ubsErrorLogList.size());
			resultMap.put("objUbsErrorLogService", objUbsErrorLog);
			resultMap.put("ubsErrorLogList", ubsErrorLogList);
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);

			return returnMap;

		} catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}

	}

}
