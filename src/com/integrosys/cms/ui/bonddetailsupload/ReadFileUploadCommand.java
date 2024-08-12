package com.integrosys.cms.ui.bonddetailsupload;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.fileUpload.bus.FileUploadException;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadJdbc;
import com.integrosys.cms.app.fileUpload.bus.OBBondDetailsFile;
import com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue;
import com.integrosys.cms.ui.bonddetailsupload.proxy.IBondDetailsUploadProxyManager;

public class ReadFileUploadCommand extends AbstractCommand implements IBondDetailsUploadConstants{

	private IBondDetailsUploadProxyManager bondDetailsuploadProxy;
	
	public IBondDetailsUploadProxyManager getBondDetailsuploadProxy() {
		return bondDetailsuploadProxy;
	}

	public void setBondDetailsuploadProxy(IBondDetailsUploadProxyManager bondDetailsuploadProxy) {
		this.bondDetailsuploadProxy = bondDetailsuploadProxy;
	}
	
	public String[][] getParameterDescriptor() {
		return new String[][] { 
				{ "TrxId", String.class.getName(), REQUEST_SCOPE },
				{ "event", String.class.getName(), REQUEST_SCOPE },
		};
	}
	
	public String[][] getResultDescriptor() {
		return new String[][] { 
				{ SESSION_TRX_VALUE_OUT, IFileUploadTrxValue.class.getName(),SERVICE_SCOPE },
				{ "event", String.class.getName(), REQUEST_SCOPE },
				{ "total", String.class.getName(), REQUEST_SCOPE },
				{ "correct", String.class.getName(), REQUEST_SCOPE },
				{ "fail", String.class.getName(), REQUEST_SCOPE },
				{ SESSION_TOTAL_UPLOADED_LIST, List.class.getName(), SERVICE_SCOPE },
				
		};
	}
	
	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException, AccessDeniedException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		
		String fileId = (String) map.get("TrxId");
		
		try {
				DefaultLogger.info(this, "Fetching file upload transaction for transaction id: " + fileId);
				IFileUploadTrxValue trxValue = getBondDetailsuploadProxy().getFileUploadByTrxID(fileId);
				
				IFileUploadJdbc jdbc = (IFileUploadJdbc) BeanHouse.get("fileUploadJdbc");
				List<OBBondDetailsFile> totalFileList = jdbc.getAllBondDetailsFile(Long.valueOf(trxValue.getStagingReferenceID()));
				
				long countPass = 0;
				long countFail = 0;
				for(OBBondDetailsFile file : totalFileList) {
					if (ICMSConstant.PASS.equals(file.getStatus()) && ICMSConstant.YES.equals(file.getUploadStatus())) {
						countPass++;
					}
				}
				countFail = totalFileList.size() - countPass;
				
				DefaultLogger.info(this, "Total record successfully uploaded : " + countPass + ", total record failed: " + countFail);
				
				resultMap.put("total", String.valueOf(totalFileList.size()));
				resultMap.put("correct", String.valueOf(countPass));
				resultMap.put("fail", String.valueOf(countFail));
				resultMap.put(SESSION_TRX_VALUE_OUT, trxValue);
				resultMap.put(SESSION_TOTAL_UPLOADED_LIST, totalFileList);
			
		} catch (FileUploadException e) {
			e.printStackTrace();
			DefaultLogger.error(this, "Exception occurred while fetching record for file upload of type " + ICMSConstant.BOND_DETAILS_UPLOAD);
			throw new CommandProcessingException("Exception occurred while fetching record for file upload of type " + ICMSConstant.BOND_DETAILS_UPLOAD, e);
		} catch (TransactionException e) {
			e.printStackTrace();
			DefaultLogger.error(this, "Exception occurred while fetching record for file upload of type " + ICMSConstant.BOND_DETAILS_UPLOAD);
			throw new CommandProcessingException("Exception occurred while fetching record for file upload of type " + ICMSConstant.BOND_DETAILS_UPLOAD, e);
		}
		
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		
		return returnMap;
	}
	
}