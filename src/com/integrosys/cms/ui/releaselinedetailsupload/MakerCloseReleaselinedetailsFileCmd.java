
package com.integrosys.cms.ui.releaselinedetailsupload;

import java.util.HashMap;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.fileUpload.bus.FileUploadException;
import com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue;
import com.integrosys.cms.app.fileUpload.trx.OBFileUploadTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.releaselinedetailsupload.proxy.IReleaselinedetailsUploadProxyManager;

public class MakerCloseReleaselinedetailsFileCmd extends AbstractCommand implements ICommonEventConstant{
	public MakerCloseReleaselinedetailsFileCmd(){		
			}
	private IReleaselinedetailsUploadProxyManager releaselinedetailsuploadProxy;
	
	public IReleaselinedetailsUploadProxyManager getReleaselinedetailsuploadProxy() {
		return releaselinedetailsuploadProxy;
	}

	public void setReleaselinedetailsuploadProxy(IReleaselinedetailsUploadProxyManager releaselinedetailsuploadProxy) {
		this.releaselinedetailsuploadProxy = releaselinedetailsuploadProxy;
	} 
	
	public String[][] getParameterDescriptor() {
        return (new String[][]{
        		{"IFileUploadTrxValue", "com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue", SERVICE_SCOPE},
                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
        }
        );
    }

    /**
     * Defines an two dimensional array with the result list to be
     * expected as a result from the doExecute method using a HashMap
     * syntax for the array is (HashMapkey,classname,scope)
     * The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getResultDescriptor() {
        return (new String[][]{
                {"trxValueOut", "com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue", SERVICE_SCOPE}
        }
        );
    }

    /**
     * This method does the Business operations  with the HashMap and put the results back into
     * the HashMap.
     *
     * @param map is of type HashMap
     * @return HashMap with the Result
     */
    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,FileUploadException {
        HashMap returnMap = new HashMap();
        HashMap resultMap = new HashMap();
        try {
        	IFileUploadTrxValue trxValueIn = (OBFileUploadTrxValue) map.get("IFileUploadTrxValue");            
            OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
            String event = (String) map.get("event");
            
            IFileUploadTrxValue trxValueOut = getReleaselinedetailsuploadProxy().makerCloseRejectUbsFile(ctx, trxValueIn);
            resultMap.put("trxValueOut", trxValueOut);
         
           

        }catch(FileUploadException ex){
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
		} catch (TrxParameterException e) {
			throw (new CommandProcessingException(e.getMessage()));
		} catch (Exception e) { 
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        return returnMap;
    }


}
