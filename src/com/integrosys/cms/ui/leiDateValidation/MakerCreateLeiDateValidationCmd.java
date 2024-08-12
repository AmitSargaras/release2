package com.integrosys.cms.ui.leiDateValidation;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.leiDateValidation.bus.OBLeiDateValidation;
import com.integrosys.cms.app.leiDateValidation.bus.LeiDateValidationException;
import com.integrosys.cms.app.leiDateValidation.proxy.ILeiDateValidationProxyManager;
import com.integrosys.cms.app.leiDateValidation.trx.ILeiDateValidationTrxValue;
import com.integrosys.cms.app.leiDateValidation.trx.OBLeiDateValidationTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class MakerCreateLeiDateValidationCmd extends AbstractCommand implements ICommonEventConstant {

	private ILeiDateValidationProxyManager leiDateValidationProxy;

	public ILeiDateValidationProxyManager getLeiDateValidationProxy() {
		return leiDateValidationProxy;
	}

	public void setLeiDateValidationProxy(ILeiDateValidationProxyManager leiDateValidationProxy) {
		this.leiDateValidationProxy = leiDateValidationProxy;
	}
	
	/**
	 * Default Constructor
	 */
	public MakerCreateLeiDateValidationCmd() {
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
	        		{"ILeiDateValidationTrxValue", "com.integrosys.cms.app.leiDateValidation.trx.ILeiDateValidationTrxValue", SERVICE_SCOPE},
	                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
	                {"remarks", "java.lang.String", REQUEST_SCOPE},
	                {"event", "java.lang.String", REQUEST_SCOPE},
	                {"partyID", "java.lang.String", REQUEST_SCOPE},
	        		{ "leiDateValidationObj", "com.integrosys.cms.app.leiDateValidation.bus.OBLeiDateValidation", FORM_SCOPE }
	               
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
			OBLeiDateValidation leiDateValidation = (OBLeiDateValidation) map.get("leiDateValidationObj");
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			
			String event = (String) map.get("event");
			String partyID = (String) map.get("partyID");
			boolean isPartyIDUnique = false;
			
			if( event.equals("maker_create_lei_date_validation") ){
				isPartyIDUnique = getLeiDateValidationProxy().isPartyIDUnique(partyID);				
				if(!isPartyIDUnique && leiDateValidation.getStatus().equals("ACTIVE")){
					exceptionMap.put("partyIDError", new ActionMessage("error.string.exist","This Party ID "));
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
				}
			}
			try {
				ILeiDateValidationTrxValue trxValueOut = new OBLeiDateValidationTrxValue();
//				ILeiDateValidationTrxValue trxValueIn = (OBLeiDateValidationTrxValue) map.get("ILeiDateValidationTrxValue");
//				if(null != trxValueIn.getTransactionID()) {
//					trxValueOut = getLeiDateValidationProxy().makerUpdateLeiDateValidation(ctx, trxValueIn, leiDateValidation);
//				}else {
					trxValueOut = getLeiDateValidationProxy().makerCreateLeiDateValidation(ctx,leiDateValidation);
//				}	
					resultMap.put("request.ITrxValue", trxValueOut);
				
			}catch (LeiDateValidationException ex) {
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
}
