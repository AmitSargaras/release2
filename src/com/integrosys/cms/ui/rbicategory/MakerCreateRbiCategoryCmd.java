/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.rbicategory;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditApproval.trx.ICreditApprovalTrxValue;
import com.integrosys.cms.app.rbicategory.bus.OBRbiCategory;
import com.integrosys.cms.app.rbicategory.bus.RbiCategoryException;
import com.integrosys.cms.app.rbicategory.proxy.IRbiCategoryProxyManager;
import com.integrosys.cms.app.rbicategory.trx.IRbiCategoryTrxValue;
import com.integrosys.cms.app.rbicategory.trx.OBRbiCategoryTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
$Govind.Sahu$
Command for list Rbi Category
*/
public class MakerCreateRbiCategoryCmd extends AbstractCommand implements ICommonEventConstant {
	
	
	private IRbiCategoryProxyManager rbiCategoryProxy;
	
	
	/**
	 * Default Constructor
	 */
	
	
	public MakerCreateRbiCategoryCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	 public String[][] getParameterDescriptor() {
	        return (new String[][]{
	        		{"rbiCategoryTrxValue", "com.integrosys.cms.app.rbicategory.trx.IRbiCategoryTrxValue", SERVICE_SCOPE},
	                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
	                {"remarks", "java.lang.String", REQUEST_SCOPE},
	                {"event", "java.lang.String", REQUEST_SCOPE},
	        		{ "rbiCategoryObj", "com.integrosys.cms.app.rbicategory.bus.OBRbiCategory", FORM_SCOPE }
	               
	        }
	        );
	    }

	 public String[][] getResultDescriptor() {
			return (new String[][] { 
					{"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE}
					   });
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
			try {
				OBRbiCategory oBRbiCategory = (OBRbiCategory) map.get("rbiCategoryObj");
				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
				
				boolean valiDateAppCode = (boolean)getRbiCategoryProxy().getCheckIndustryName(oBRbiCategory);
	    		if(valiDateAppCode)
	    		{
	    			IRbiCategoryTrxValue trxValueOut  = null;
	    			resultMap.put("trxValueOut", trxValueOut);
			        exceptionMap.put("industryNameId", new ActionMessage("error.string.industryNameId.exist"));
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
	    		}
				
				
				
				IRbiCategoryTrxValue trxValueOut = new OBRbiCategoryTrxValue();
				trxValueOut = getRbiCategoryProxy().makerCreateRbiCategory(ctx,oBRbiCategory);
					
					resultMap.put("request.ITrxValue", trxValueOut);
				
			}catch (RbiCategoryException ex) {
				DefaultLogger.debug(this, "got exception in doExecute" + ex);
				ex.printStackTrace();
				throw (new CommandProcessingException(ex.getMessage()));
			}
			catch (TransactionException e) {
				DefaultLogger.debug(this, "got exception in doExecute" + e);
				throw (new CommandProcessingException(e.getMessage()));
			}catch (Exception e) {
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
