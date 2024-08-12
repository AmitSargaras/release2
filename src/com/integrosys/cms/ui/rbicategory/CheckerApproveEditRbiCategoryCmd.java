package com.integrosys.cms.ui.rbicategory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.rbicategory.bus.OBRbiCategory;
import com.integrosys.cms.app.rbicategory.bus.RbiCategoryException;
import com.integrosys.cms.app.rbicategory.proxy.IRbiCategoryProxyManager;
import com.integrosys.cms.app.rbicategory.trx.IRbiCategoryTrxValue;
import com.integrosys.cms.app.rbicategory.trx.OBRbiCategoryTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * @author $Govind.Sahu$<br>
 * Command for checker to approve edit .
 */

public class CheckerApproveEditRbiCategoryCmd extends AbstractCommand implements ICommonEventConstant {


	private IRbiCategoryProxyManager rbiCategoryProxy;




	/**
	 * Default Constructor
	 */
	public CheckerApproveEditRbiCategoryCmd() {
	}

	/**
	 * Defines an two dimensional array with the result list to be
	 * expected as a result from the doExecute method using a HashMap
	 * syntax for the array is (HashMapkey,classname,scope)
	 * The scope may be request,form or service
	 *
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][]{
				{"rbiCategoryTrxValue", "com.integrosys.cms.app.rbicategory.trx.IRbiCategoryTrxValue", SERVICE_SCOPE},
				{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
				{"remarks", "java.lang.String", REQUEST_SCOPE}
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
				{"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE}
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
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		String strIndustryNameId;
		boolean isIndustryNameApprove = false;
		OBRbiCategory stgObRbiCategory = new OBRbiCategory();
		OBRbiCategory actObRbiCategory = new OBRbiCategory();
		
		List isRbiCodeCategoryApproveList = null;
		
		
		try {
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			IRbiCategoryTrxValue trxValueIn = (OBRbiCategoryTrxValue) map.get("rbiCategoryTrxValue");
			String remarks = (String) map.get("remarks");
			ctx.setRemarks(remarks);
			stgObRbiCategory = (OBRbiCategory)trxValueIn.getStagingRbiCategory();
			actObRbiCategory = (OBRbiCategory)trxValueIn.getRbiCategory();
				if ( trxValueIn != null && (trxValueIn.getFromState().equals("ND") || trxValueIn.getStatus().equals("PENDING_CREATE"))) {
						if(stgObRbiCategory!=null)
						{
				
							strIndustryNameId = stgObRbiCategory.getIndustryNameId();
							if(strIndustryNameId!=null)
							{
								isIndustryNameApprove  = getRbiCategoryProxy().isIndustryNameApprove(strIndustryNameId);
							}
							else
							{
								throw new RbiCategoryException("ERROR-- Industry Name Id is null");
							}
						}
						else
						{
							throw new RbiCategoryException("ERROR-- obRbiCategory is null");
						}
						if(isIndustryNameApprove==true){
							exceptionMap.put("rbiIndustryNameError", new ActionMessage("error.string.rbicode.industry.name.exist"));
						}
					
					isRbiCodeCategoryApproveList = getRbiCategoryProxy().isRbiCodeCategoryApprove(stgObRbiCategory,false,null);
					Iterator it = isRbiCodeCategoryApproveList.iterator();
					String codeCatList = "";
					while(it.hasNext())
					{
						codeCatList = codeCatList+(String)it.next();	
					}
					if(isRbiCodeCategoryApproveList.size()>0)
					{
						exceptionMap.put("rbiCodeCategoryError", new ActionMessage("error.string.rbicode.code.category.exist"));
					}
			
				}else
				{
					isRbiCodeCategoryApproveList = getRbiCategoryProxy().isRbiCodeCategoryApprove(stgObRbiCategory,true,actObRbiCategory);
					Iterator it = isRbiCodeCategoryApproveList.iterator();
					String codeCatList = "";
					while(it.hasNext())
					{
						codeCatList = codeCatList+(String)it.next();	
					}
					if(isRbiCodeCategoryApproveList.size()>0)
					{
						exceptionMap.put("rbiCodeCategoryError", new ActionMessage("error.string.rbicode.code.category.exist"));
					}
				}

			if(isIndustryNameApprove==true || (isRbiCodeCategoryApproveList!=null && isRbiCodeCategoryApproveList.size()>0) ){
				IRbiCategoryTrxValue trxValueOut = null;
				resultMap.put("request.ITrxValue", trxValueOut);
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				return returnMap;
			}
			// Function  to approve updated Rbi Category Trx
			IRbiCategoryTrxValue trxValueOut = getRbiCategoryProxy().checkerApproveRbiCategory(ctx, trxValueIn);
			resultMap.put("request.ITrxValue", trxValueOut);
		}catch (RbiCategoryException ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		} catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

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
}



