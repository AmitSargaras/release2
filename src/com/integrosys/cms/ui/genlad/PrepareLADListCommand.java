/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.genlad;

import java.util.HashMap;

import com.crystaldecisions.sdk.occa.report.document.IDocument;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.lad.bus.ILAD;
import com.integrosys.cms.app.lad.proxy.ILADProxyManager;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.login.CMSGlobalSessionConstant;

/**
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2005/12/12 10:03:30 $ Tag: $Name: $
 */
public class PrepareLADListCommand extends AbstractCommand implements ICommonEventConstant {
	
	private ILADProxyManager ladProxy;
	
	
	public ILADProxyManager getLadProxy() {
		return ladProxy;
	}

	public void setLadProxy(ILADProxyManager ladProxy) {
		this.ladProxy = ladProxy;
	}

	/**
	 * Default Constructor
	 */
	public PrepareLADListCommand() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID, "java.lang.String", GLOBAL_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE },
				{ "operation", "java.lang.String", REQUEST_SCOPE },
				
				
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
				{ "ilads", "java.util.ArrayList", SERVICE_SCOPE }
				
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
		DefaultLogger.debug(this, "Inside doExecute()");
		try{
			ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
	        long limitProfileID = limit.getLimitProfileID();
			String teamTypeMemID = (String) map.get(CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID);
			boolean isUserCpcMaker = ICMSConstant.TEAM_TYPE_SSC_MAKER == Long.parseLong(teamTypeMemID)||ICMSConstant.TEAM_TYPE_SSC_MAKER_WFH == Long.parseLong(teamTypeMemID);
			if(!isUserCpcMaker ){
				resultMap.put("isChecker", "true");
			}else{
				resultMap.put("isChecker", "false");
			}
//			System.out.println("$$$$$$$$$$$$$$isUserCpcMaker$$$$$$$$$$$$$$$$$$$$$$$$$$"+isUserCpcMaker);
		String operation=(String) map.get("operation");
//		System.out.println("$$$$$$$$$$$$$$operation$$$$$$$$$$$$$$$$$$$$$$$$$$"+operation);
		if(operation!=null){
			if(operation.trim().equals("LADDUE")){
				resultMap.put("ilads", getLadProxy().getLAD(limitProfileID,"GEN"));
			}
			if(operation.trim().equals("LADGBNR")){
				resultMap.put("ilads", getLadProxy().getLAD(limitProfileID,"GEN"));
			}
			if(operation.trim().equals("LADREC")){
				resultMap.put("ilads", getLadProxy().getLAD(limitProfileID,"REC"));
			}
			resultMap.put("redirect", operation);
//			System.out.println("$$$$$$$$$$$$$$operation$$$$$$$$$$$$$$$$$$$$$$$$$$"+operation);
		}
		}catch (Exception e) {
		}
			
			
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

	
}