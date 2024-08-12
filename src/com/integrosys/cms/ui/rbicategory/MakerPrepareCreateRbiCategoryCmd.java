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

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.rbicategory.bus.IRbiCategory;
import com.integrosys.cms.app.rbicategory.bus.OBIndustryCodeCategory;
import com.integrosys.cms.app.rbicategory.bus.RbiCategoryException;
import com.integrosys.cms.app.rbicategory.proxy.IRbiCategoryProxyManager;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
$Govind.Sahu$
Command for list Rbi Category
*/
public class MakerPrepareCreateRbiCategoryCmd extends AbstractCommand implements ICommonEventConstant {
	
	
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
	public MakerPrepareCreateRbiCategoryCmd() {
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
				  {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
				  { IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				  
					 
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
				{"rbiCategoryTrxValue", "com.integrosys.cms.app.rbicategory.trx.OBRbiCategoryTrxValue", SERVICE_SCOPE},
				{ "commonUser", "com.integrosys.component.user.app.bus.ICommonUser", REQUEST_SCOPE },
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
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		List rbiCategoryList = new ArrayList();
		  try {
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
			  
			  
		  ICommonUser commonUser = (ICommonUser)map.get(IGlobalConstant.USER);	
		  resultMap.put("commonUser", commonUser);
		  }catch (RbiCategoryException ex) {
				DefaultLogger.debug(this, "got exception in doExecute" + ex);
				ex.printStackTrace();
				throw (new CommandProcessingException(ex.getMessage()));
		  }
		  catch (Exception e) {
	            DefaultLogger.debug(this, "got exception in doExecute" + e);
	            e.printStackTrace();
	            throw (new CommandProcessingException(e.getMessage()));
	      }
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
