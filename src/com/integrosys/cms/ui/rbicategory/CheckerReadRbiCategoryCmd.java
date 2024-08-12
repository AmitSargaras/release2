/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.rbicategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.rbicategory.bus.IRbiCategory;
import com.integrosys.cms.app.rbicategory.bus.OBIndustryCodeCategory;
import com.integrosys.cms.app.rbicategory.bus.OBRbiCategory;
import com.integrosys.cms.app.rbicategory.bus.RbiCategoryException;
import com.integrosys.cms.app.rbicategory.proxy.IRbiCategoryProxyManager;
import com.integrosys.cms.app.rbicategory.trx.IRbiCategoryTrxValue;
import com.integrosys.cms.app.rbicategory.trx.OBRbiCategoryTrxValue;

/**
 * @author $Govind.Sahu$<br>
 *Command for checker to read Rbi Category Trx value
 */
public class CheckerReadRbiCategoryCmd extends AbstractCommand implements ICommonEventConstant {
	
	
	private IRbiCategoryProxyManager rbiCategoryProxy;

	
	
	
	/**
	 * @return the rbiCategoryProxy
	 */
	public IRbiCategoryProxyManager getRbiCategoryProxy() {
		return rbiCategoryProxy;
	}

	/**
	 * @param rbiCategoryProxy the rbiCategoryProxy to set
	 */
	public void setRbiCategoryProxy(IRbiCategoryProxyManager rbiCategoryProxy) {
		this.rbiCategoryProxy = rbiCategoryProxy;
	}

	/**
	 * Default Constructor
	 */
	public CheckerReadRbiCategoryCmd() {
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
				{ "TrxId", "java.lang.String", REQUEST_SCOPE },
				{"event", "java.lang.String", REQUEST_SCOPE},
				
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
				{"iRbiCategory", "com.integrosys.cms.app.rbicategory.bus.OBRbiCategory", FORM_SCOPE },
				{"rbiCategoryTrxValue", "com.integrosys.cms.app.rbicategory.trx.IRbiCategoryTrxValue", REQUEST_SCOPE},
				{"rbiCategoryTrxValue", "com.integrosys.cms.app.rbicategory.trx.IRbiCategoryTrxValue", SERVICE_SCOPE},
				{"event", "java.lang.String", REQUEST_SCOPE},
				{ "actualRbiCodeCatIds", "java.util.ArrayList", REQUEST_SCOPE },
				
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,RbiCategoryException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			IRbiCategory iRbiCategory;
			IRbiCategoryTrxValue trxValue=null;
			String trxId=(String) (map.get("TrxId"));
			String event = (String) map.get("event");
			List rbiCategoryList = new ArrayList();

			trxValue = (OBRbiCategoryTrxValue) getRbiCategoryProxy().getRbiCategoryByTrxID(trxId);

			iRbiCategory = (OBRbiCategory) trxValue.getStagingRbiCategory();
			
			 rbiCategoryList= (List)  getRbiCategoryProxy().getAllRbiCategoryList();
			  Iterator itRbiCategory = rbiCategoryList.iterator();
			  IRbiCategory obRbicat;
			  Set rbiCode;
			  OBIndustryCodeCategory oBIndustryNameStg = new OBIndustryCodeCategory();
			  ArrayList actualRbiCodeCatIds = new ArrayList();
			  while(itRbiCategory.hasNext())
			  {
				  obRbicat = (IRbiCategory)itRbiCategory.next();
				  rbiCode = obRbicat.getStageIndustryNameSet();
				  Iterator itCodeSet = rbiCode.iterator();
					while(itCodeSet.hasNext())
					{
				 	oBIndustryNameStg = (OBIndustryCodeCategory)itCodeSet.next();
				 	actualRbiCodeCatIds.add(oBIndustryNameStg.getRbiCodeCategoryId());
				 	
					}
			  }
			  resultMap.put("actualRbiCodeCatIds", actualRbiCodeCatIds);
			
			resultMap.put("rbiCategoryTrxValue", trxValue);
			resultMap.put("iRbiCategory", iRbiCategory);
			resultMap.put("event", event);
		} catch (RbiCategoryException e) {
		
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		} catch (TransactionException e) {
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
