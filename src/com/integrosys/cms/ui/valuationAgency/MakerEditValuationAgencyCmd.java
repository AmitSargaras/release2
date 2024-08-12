package com.integrosys.cms.ui.valuationAgency;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.valuationAgency.bus.OBValuationAgency;
import com.integrosys.cms.app.valuationAgency.bus.ValuationAgencyException;
import com.integrosys.cms.app.valuationAgency.proxy.IValuationAgencyProxyManager;
import com.integrosys.cms.app.valuationAgency.trx.IValuationAgencyTrxValue;
import com.integrosys.cms.app.valuationAgency.trx.OBValuationAgencyTrxValue;

/**
 * @author rajib.aich For Valuation Agency Command for checker to approve edit .
 */

public class MakerEditValuationAgencyCmd extends AbstractCommand implements
		ICommonEventConstant {

	
private  static boolean isVACodeUnique = true;
	
	/**
	 * @return the isRMCodeUnique
	 */
	public static boolean isVACodeUnique() {
		return isVACodeUnique;
	}

	/**
	 * @param isRMCodeUnique the isRMCodeUnique to set
	 */
	public static void setVACodeUnique(boolean isVACodeUnique) {
		MakerEditValuationAgencyCmd.isVACodeUnique = isVACodeUnique;
	}

	
	
	
	private IValuationAgencyProxyManager valuationAgencyProxy;

	public IValuationAgencyProxyManager getValuationAgencyProxy() {
		return valuationAgencyProxy;
	}

	public void setValuationAgencyProxy(
			IValuationAgencyProxyManager valuationAgencyProxy) {
		this.valuationAgencyProxy = valuationAgencyProxy;
	}

	/**
	 * Default Constructor
	 */

	public MakerEditValuationAgencyCmd() {
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
				
				{"valuationAgencyCode", "java.lang.String", REQUEST_SCOPE },
				{
						"IValuationAgencyTrxValue",
						"com.integrosys.cms.app.valuationAgency.trx.IValuationAgencyTrxValue",
						SERVICE_SCOPE },
				{ "theOBTrxContext",
						"com.integrosys.cms.app.transaction.OBTrxContext",
						FORM_SCOPE },
				{ "remarks", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{
						"valuationObj",
						"com.integrosys.cms.app.valuationAgency.bus.OBValuationAgency",
						FORM_SCOPE }

		});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "request.ITrxValue",
				"com.integrosys.cms.app.transaction.ICMSTrxValue",
				REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		 HashMap exceptionMap = new HashMap();
		try {
			OBValuationAgency valuationAgency = (OBValuationAgency) map.get("valuationObj");
			String event = (String) map.get("event");
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			IValuationAgencyTrxValue trxValueIn = (OBValuationAgencyTrxValue) map.get("IValuationAgencyTrxValue");
			IValuationAgencyTrxValue trxValueOut = new OBValuationAgencyTrxValue();

			String newValuationName =  valuationAgency.getValuationAgencyName().trim();
			String oldValuationName = "";			
			boolean isValuationNameUnique = false;
			
			if (trxValueIn.getFromState().equals(ICMSConstant.STATE_PENDING_PERFECTION)) {
				
				/*boolean isvaCodeUnique = true;
				String vaCode = (String)map.get("valuationAgencyCode");
				if(vaCode!=null)
					isvaCodeUnique = getValuationAgencyProxy().isVACodeUnique(vaCode.trim());

				if(isvaCodeUnique != false){
					exceptionMap.put("valuationAgencyCodeError", new ActionMessage("error.string.valuationAgencycode.exist"));
					IValuationAgencyTrxValue valuationAgencyTrxValue = null;
					resultMap.put("request.ITrxValue", valuationAgencyTrxValue);
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;		
				}*/
				
				oldValuationName = trxValueIn.getStagingValuationAgency().getValuationAgencyName();
				
				if( ! oldValuationName.equalsIgnoreCase(newValuationName) )
					isValuationNameUnique = getValuationAgencyProxy().isValuationNameUnique(newValuationName);
				
				if( isValuationNameUnique ){
					exceptionMap.put("valuationAgencyNameError", new ActionMessage("error.string.exist","ValuationAgency Name"));
					IValuationAgencyTrxValue valuationAgencyTrxValue = null;
					resultMap.put("request.ITrxValue", valuationAgencyTrxValue);
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;		
				}
				
				trxValueOut = getValuationAgencyProxy().makerCreateValuationAgency(ctx, trxValueIn,valuationAgency);
			}

			else if ((event.equals("maker_edit_valuationAgency")) || event.equals("maker_delete_valuationAgency") || event.equals("maker_save_update")) {
				
				if ( event.equals("maker_edit_valuationAgency") ) 		// edit - submit
					oldValuationName = trxValueIn.getValuationAgency().getValuationAgencyName();
					
				else if ( event.equals("maker_save_update") )	 		// edit - save - Maker ToDo - Process - Submit
					oldValuationName = trxValueIn.getStagingValuationAgency().getValuationAgencyName();
						
				if ( event.equals("maker_edit_valuationAgency") || event.equals("maker_save_update") ){
					if( ! oldValuationName.equalsIgnoreCase(newValuationName) )
						isValuationNameUnique = getValuationAgencyProxy().isValuationNameUnique(newValuationName);
					
					if( isValuationNameUnique ){
						exceptionMap.put("valuationAgencyNameError", new ActionMessage("error.string.exist","ValuationAgency Name"));
						IValuationAgencyTrxValue valuationAgencyTrxValue = null;
						resultMap.put("request.ITrxValue", valuationAgencyTrxValue);
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						return returnMap;		
					}
				}
				trxValueOut = getValuationAgencyProxy().makerUpdateSaveCreateValuationAgency(ctx, trxValueIn,valuationAgency);
			} else {
				// event is maker_confirm_resubmit_edit
				
				if( trxValueIn.getFromState().equalsIgnoreCase("PENDING_CREATE") )
					oldValuationName = trxValueIn.getStagingValuationAgency().getValuationAgencyName();
				else
					oldValuationName = trxValueIn.getValuationAgency().getValuationAgencyName();
				
				if( ! oldValuationName.equalsIgnoreCase(newValuationName) )
					isValuationNameUnique = getValuationAgencyProxy().isValuationNameUnique(newValuationName);
				
				if( isValuationNameUnique ){
					exceptionMap.put("valuationAgencyNameError", new ActionMessage("error.string.exist","ValuationAgency Name"));
					IValuationAgencyTrxValue valuationAgencyTrxValue = null;
					resultMap.put("request.ITrxValue", valuationAgencyTrxValue);
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;		
				}
				
				String remarks = (String) map.get("remarks");
				ctx.setRemarks(remarks);
				trxValueOut = getValuationAgencyProxy().makerEditRejectedValuationAgency(ctx, trxValueIn,valuationAgency);
			}

			resultMap.put("request.ITrxValue", trxValueOut);

		} catch (ValuationAgencyException ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		} catch (TransactionException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			throw (new CommandProcessingException(e.getMessage()));
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
