package com.integrosys.cms.ui.productMaster;

import java.util.HashMap;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.productMaster.bus.IProductMaster;
import com.integrosys.cms.app.productMaster.bus.OBProductMaster;
import com.integrosys.cms.app.productMaster.bus.ProductMasterException;
import com.integrosys.cms.app.productMaster.proxy.IProductMasterProxyManager;
import com.integrosys.cms.app.productMaster.trx.IProductMasterTrxValue;
import com.integrosys.cms.app.productMaster.trx.OBProductMasterTrxValue;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;

public class MakerReadProductMasterCmd extends AbstractCommand implements ICommonEventConstant{

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
	public MakerReadProductMasterCmd() {
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
			 {"productCode", "java.lang.String", REQUEST_SCOPE},
			 { "startIndex", "java.lang.String", REQUEST_SCOPE },
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
				{ "productMasterObj", "com.integrosys.cms.app.productMaster.bus.OBProductMaster", FORM_SCOPE },
				{"IProductMasterTrxValue", "com.integrosys.cms.app.productMaster.trx.IProductMasterTrxValue", SERVICE_SCOPE},
				{"event", "java.lang.String", REQUEST_SCOPE}
				
		});
	}
	
	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,SystemBankException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			IProductMaster productMaster;
			IProductMasterTrxValue trxValue=null;
			String productCode=(String) (map.get("productCode"));
			String startIdx = (String) map.get("startIndex");
			String event = (String) map.get("event");
			trxValue = (OBProductMasterTrxValue) getProductMasterProxy().getProductMasterTrxValue(Long.parseLong(productCode));
			productMaster = (OBProductMaster) trxValue.getStagingProductMaster();
			DefaultLogger.debug(this, "startIdx: " + startIdx);
			if((trxValue.getStatus().equals("PENDING_CREATE"))||(trxValue.getStatus().equals("PENDING_UPDATE"))||(trxValue.getStatus().equals("PENDING_DELETE"))||trxValue.getStatus().equals("REJECTED")||trxValue.getStatus().equals("DRAFT"))
			{
				resultMap.put("wip", "wip");
			}
			resultMap.put("IProductMasterTrxValue", trxValue);
			resultMap.put("productMasterObj", productMaster);
			resultMap.put("event", event);
			resultMap.put("startIndex",startIdx);
		} catch (ProductMasterException e) {
		
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
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
