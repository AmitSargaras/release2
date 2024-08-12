package com.integrosys.cms.ui.collateralrocandinsurance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateralrocandinsurance.bus.CollateralRocException;
import com.integrosys.cms.app.collateralrocandinsurance.bus.ICollateralRoc;
import com.integrosys.cms.app.collateralrocandinsurance.bus.OBCollateralRoc;
import com.integrosys.cms.app.collateralrocandinsurance.proxy.ICollateralRocProxyManager;
import com.integrosys.cms.app.collateralrocandinsurance.trx.ICollateralRocTrxValue;
import com.integrosys.cms.app.collateralrocandinsurance.trx.OBCollateralRocTrxValue;

public class MakerReadCollateralRocCmd extends AbstractCommand implements ICommonEventConstant {

	private ICollateralRocProxyManager collateralRocProxy;

	public ICollateralRocProxyManager getCollateralRocProxy() {
		return collateralRocProxy;
	}

	public void setCollateralRocProxy(ICollateralRocProxyManager collateralRocProxy) {
		this.collateralRocProxy = collateralRocProxy;
	}
	/**
	 * Default Constructor
	 */
	public MakerReadCollateralRocCmd() {
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
			 	{"collateralCode", "java.lang.String", REQUEST_SCOPE},
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "collateralCategoryList", "java.util.List", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE } });
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
				{ "collateralRocObj", "com.integrosys.cms.app.collateralrocandinsurance.bus.OBCollateralRoc", FORM_SCOPE },
				{"event", "java.lang.String", REQUEST_SCOPE},
				 { "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "collateralRocList", "java.util.List", SERVICE_SCOPE },
				{ "collateralCategoryList", "java.util.List", SERVICE_SCOPE },
				{"ICollateralRocTrxValue", "com.integrosys.cms.app.collateralrocandinsurance.trx.ICollateralRocTrxValue", SERVICE_SCOPE}
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
		try {
			ICollateralRoc collateralRoc;
			ICollateralRocTrxValue trxValue=null;
			String collateralCode=(String) (map.get("collateralCode"));
			String event = (String) map.get("event");
			String startIdx = (String) map.get("startIndex");
			
			DefaultLogger.debug(this, "startIdx: " + startIdx);
			
			trxValue = (OBCollateralRocTrxValue) getCollateralRocProxy().getCollateralRocTrxValue(Long.parseLong(collateralCode));
			collateralRoc = (OBCollateralRoc) trxValue.getCollateralRoc();

			if((trxValue.getStatus().equals("PENDING_CREATE"))||(trxValue.getStatus().equals("PENDING_UPDATE"))||(trxValue.getStatus().equals("PENDING_DELETE"))||(trxValue.getStatus().equals("REJECTED"))||(trxValue.getStatus().equals("DRAFT")))
			{
				resultMap.put("wip", "wip");
			}
			List collateralCategoryList =new ArrayList();
			
			collateralCategoryList.add(new LabelValueBean("Collateral", "COLLATERAL"));
			collateralCategoryList.add(new LabelValueBean("Component", "COMPONENT"));
			collateralCategoryList.add(new LabelValueBean("Property Type", "PROPERTY_TYPE"));
			
			resultMap.put("collateralCategoryList", collateralCategoryList);
			resultMap.put("event", event);	
			resultMap.put("startIndex",startIdx);	
			resultMap.put("ICollateralRocTrxValue", trxValue);
			resultMap.put("collateralRocObj", collateralRoc);
			 
		}catch (CollateralRocException ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		}
		catch (TransactionException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}catch (Exception e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
