/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.genlad;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.lad.bus.ILAD;
import com.integrosys.cms.app.lad.proxy.ILADProxyManager;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: jychong $<br>
 * @version $Revision: 1.15 $
 * @since $Date: 2006/11/15 12:50:06 $ Tag: $Name: $
 */
public class UpdateLADCommand extends AbstractCommand implements ICommonEventConstant {
	
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
	public UpdateLADCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "ladId", "java.lang.String", SERVICE_SCOPE },
				{ "ladReceiveDate", "java.lang.String", REQUEST_SCOPE },
               
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
		return (new String[][] { { "custDetail", "com.integrosys.cms.app.ddn.bus.IDDNCustomerDetail", SERVICE_SCOPE },
				{ "certTrxVal", "com.integrosys.cms.app.ddn.trx.IDDNTrxValue", SERVICE_SCOPE },
				{ "cert", "com.integrosys.cms.app.ddn.bus.IDDN", SERVICE_SCOPE },
				{ "cert", "com.integrosys.cms.app.ddn.bus.IDDN", FORM_SCOPE },
				{ "wip", "java.lang.String", REQUEST_SCOPE }, { "frame", "java.lang.String", SERVICE_SCOPE },
				{ "error", "java.lang.String", REQUEST_SCOPE },
				{ "ladId", "java.lang.String", SERVICE_SCOPE },
				{ "bflFinalIssueDate", "java.lang.String", REQUEST_SCOPE },
                { "docsHeldMap", "java.util.HashMap", REQUEST_SCOPE}});
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
		DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
	        long limitProfileID = limit.getLimitProfileID();
			boolean error= false;
			String ladId=(String)map.get("ladId");
			resultMap.put("ladId", ladId);
			long id=Long.parseLong(ladId);
			String ladRecDate=(String)map.get("ladReceiveDate");
			if(ladRecDate.trim().equals("")){
				error=true;
				exceptionMap.put("ladRecDateError", new ActionMessage("error.string.mandatory"));
				
			}else {
				Date recDate= df.parse(ladRecDate);
				List list=getLadProxy().getLAD(limitProfileID,"GEN");
				if(list!=null){
					for(int i=0;i<list.size();i++){
					ILAD ilad=(ILAD)list.get(i);
					if(ilad.getId()==id){
						Date docDate=ilad.getGeneration_date(); 
						
					
					if (recDate.after(
							DateUtil.clearTime(DateUtil.getDate()))) {
						error=true;
						exceptionMap.put("ladRecDateError", new ActionMessage("error.date.compareDate.cannot.date", "Future date"));
					}else 
						if(recDate.before(docDate)){
							error=true;
							exceptionMap.put("ladRecDateError", new ActionMessage("error.date.compareDate", "Receive Date",
									"LAD Generation Date"));
						}
					
				
					}
					}
				}
			
			
			}
			
			if(error){
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				return returnMap;
			}else{
		
			List listDate=new ArrayList();
			List list=getLadProxy().getLAD(limitProfileID,"GEN");
			if(list!=null){
				for(int i=0;i<list.size();i++){
				ILAD ilad=(ILAD)list.get(i);
				if(ilad.getId()==id){
					ilad.setStatus("REC");
					Date expDate = new Date();
					expDate.setDate(df.parse(ladRecDate).getDate());
					expDate.setMonth(df.parse(ladRecDate).getMonth());
					expDate.setYear(df.parse(ladRecDate).getYear()+3);
					
					ilad.setReceive_date(df.parse(ladRecDate));
					ilad.setExpiry_date(expDate);
					getLadProxy().updateLAD(ilad);
					
				}
				}
			}
			}
		}
		catch (Exception e) {
			DefaultLogger.error(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
