package com.integrosys.cms.ui.productMaster;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.productMaster.bus.OBProductMaster;
import com.integrosys.cms.app.productMaster.bus.ProductMasterException;
import com.integrosys.cms.app.productMaster.proxy.IProductMasterProxyManager;
import com.integrosys.cms.app.productMaster.trx.IProductMasterTrxValue;
import com.integrosys.cms.app.productMaster.trx.OBProductMasterTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class MakerEditProductMasterCmd extends AbstractCommand implements ICommonEventConstant{

	private IProductMasterProxyManager productMasterProxy;
	
	public IProductMasterProxyManager getProductMasterProxy() {
		return productMasterProxy;
	}

	public void setProductMasterProxy(IProductMasterProxyManager productMasterProxy) {
		this.productMasterProxy = productMasterProxy;
	}
	
	/**
	 * Default Constructor
	 */
	public MakerEditProductMasterCmd() {
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
				{ "IProductMasterTrxValue", "com.integrosys.cms.app.productMaster.trx.IProductMasterTrxValue",
						SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "remarks", "java.lang.String", REQUEST_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE },
				{"event", "java.lang.String", REQUEST_SCOPE},
				{ "productMasterObj", "com.integrosys.cms.app.productMaster.bus.OBProductMaster", FORM_SCOPE }

		});
	}
	
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.
	 *
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 */
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		try {
			String event = (String) map.get("event");
			OBProductMaster productMaster = (OBProductMaster) map.get("productMasterObj");
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			IProductMasterTrxValue trxValueIn = (OBProductMasterTrxValue) map.get("IProductMasterTrxValue");
			IProductMasterTrxValue trxValueOut = new OBProductMasterTrxValue();
			boolean isProductCodeUnique = false;
			if(trxValueIn.getFromState().equals("PENDING_PERFECTION")){
				String productCode=productMaster.getProductCode();
				String productCodeActual=trxValueIn.getStagingProductMaster().getProductCode();
				if(! productCode.equals(productCodeActual)) {
					isProductCodeUnique = getProductMasterProxy().isProductCodeUnique(productCode);				
					if(isProductCodeUnique != false){
						exceptionMap.put("productCodeError", new ActionMessage("error.string.exist","This Product Code "));
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						return returnMap;
					}
				}
				trxValueOut = getProductMasterProxy().makerUpdateSaveCreateProductMaster(ctx, trxValueIn,productMaster);
			}
			else{
				if( event.equals("maker_edit_product_master") ||event.equals("maker_save_update") ){
					String productCode=productMaster.getProductCode();
					String productCodeActual=trxValueIn.getStagingProductMaster().getProductCode();
					if(! productCode.equals(productCodeActual)) {
						isProductCodeUnique = getProductMasterProxy().isProductCodeUnique(productCode);				
						if(isProductCodeUnique != false){
							exceptionMap.put("productCodeError", new ActionMessage("error.string.exist","This Product Code "));
							returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
							returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
							return returnMap;
						}
					}
					trxValueOut = getProductMasterProxy().makerUpdateProductMaster(ctx, trxValueIn,productMaster);
				}
				// event is  maker_confirm_resubmit_edit
			}
			resultMap.put("request.ITrxValue", trxValueOut);
				
		} catch (ProductMasterException ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		} catch (TransactionException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			throw (new CommandProcessingException(e.getMessage()));
		} catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
