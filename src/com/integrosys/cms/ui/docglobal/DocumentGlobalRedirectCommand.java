package com.integrosys.cms.ui.docglobal;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.chktemplate.proxy.CheckListTemplateProxyManagerFactory;
import com.integrosys.cms.app.chktemplate.proxy.ICheckListTemplateProxyManager;
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Aug 8, 2008
 * Time: 10:29:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class DocumentGlobalRedirectCommand  extends AbstractCommand implements ICommonEventConstant {


    /**
     * Default Constructor
     */
    public DocumentGlobalRedirectCommand() {
    }

    /**
     * Defines an two dimensional array with the result list to be expected as a
     * result from the doExecute method using a HashMap syntax for the array is
     * (HashMapkey,classname,scope) The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "docTrxID", "java.lang.String", REQUEST_SCOPE }, });
	}

    /**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 *
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "trxSubType", "java.lang.String", REQUEST_SCOPE }, });
	}


    /**
     * This method does the Business operations with the HashMap and put the
     * results back into the HashMap. Gets the security sub-type ID for the
     * required transaction to find out which action to forward to.
     *
     * @param map is of type HashMap
     * @return HashMap with the Result
     * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException on errors
     * @throws com.integrosys.base.uiinfra.exception.CommandValidationException on errors
     */
    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();

        String trxIDStr = (String) map.get("docTrxID");
        String trxSubType = null;
        try {
            long trxID = Long.parseLong(trxIDStr);
            ICheckListTemplateProxyManager proxy = CheckListTemplateProxyManagerFactory.getCheckListTemplateProxyManager();
            trxSubType = proxy.getTrxSubTypeByTrxID(trxID);
            resultMap.put("trxSubType", trxSubType);

        }
        catch (Exception e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }

        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return returnMap;
    }



}
