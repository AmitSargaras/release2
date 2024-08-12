package com.integrosys.cms.ui.goodsMaster;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.excludedfacility.bus.ExcludedFacilityException;
import com.integrosys.cms.app.excludedfacility.bus.OBExcludedFacility;
import com.integrosys.cms.app.excludedfacility.trx.IExcludedFacilityTrxValue;
import com.integrosys.cms.app.excludedfacility.trx.OBExcludedFacilityTrxValue;
import com.integrosys.cms.app.goodsMaster.bus.OBGoodsMaster;
import com.integrosys.cms.app.goodsMaster.bus.GoodsMasterException;
import com.integrosys.cms.app.goodsMaster.proxy.IGoodsMasterProxyManager;
import com.integrosys.cms.app.goodsMaster.trx.IGoodsMasterTrxValue;
import com.integrosys.cms.app.goodsMaster.trx.OBGoodsMasterTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class MakerSaveGoodsMasterCmd extends AbstractCommand implements ICommonEventConstant {

	private IGoodsMasterProxyManager goodsMasterProxy;

	public IGoodsMasterProxyManager getGoodsMasterProxy() {
		return goodsMasterProxy;
	}

	public void setGoodsMasterProxy(IGoodsMasterProxyManager goodsMasterProxy) {
		this.goodsMasterProxy = goodsMasterProxy;
	}
	
	/**
	 * Default Constructor
	 */
	public MakerSaveGoodsMasterCmd() {
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
				{ "IgoodsMasterTrxValue", "com.integrosys.cms.app.goodsMaster.trx.IGoodsMasterTrxValue",
						SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "goodsMasterObj", "com.integrosys.cms.app.goodsMaster.bus.OBGoodsMaster", FORM_SCOPE }

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
			OBGoodsMaster goodsMaster = (OBGoodsMaster) map.get("goodsMasterObj");
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			IGoodsMasterTrxValue trxValueIn = (OBGoodsMasterTrxValue) map.get("IgoodsMasterTrxValue");
			IGoodsMasterTrxValue trxValueOut = new OBGoodsMasterTrxValue();

			boolean isGoodsCodeUnique = false;
			String goodsCode=goodsMaster.getGoodsCode();
			isGoodsCodeUnique = getGoodsMasterProxy().isGoodsCodeUnique(goodsCode);	
			if (event.equals("maker_update_draft_goods_master")) { 
			String goodsCodeActual=trxValueIn.getStagingGoodsMaster().getGoodsCode();
			if(goodsCode != null && !goodsCode.equals(goodsCodeActual) && isGoodsCodeUnique != false){
					exceptionMap.put("goodsCodeError", new ActionMessage("error.string.exist","This Goods Code "));
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
				}
			}
			if (event.equals("maker_update_draft_goods_master")) { 
				trxValueOut = getGoodsMasterProxy().makerUpdateSaveUpdateGoodsMaster(ctx, trxValueIn,goodsMaster);
				resultMap.put("request.ITrxValue", trxValueOut);
			} else {
				trxValueOut = getGoodsMasterProxy().makerSaveGoodsMaster(ctx, goodsMaster);
				resultMap.put("request.ITrxValue", trxValueOut);
			}
		} catch (GoodsMasterException ex) {
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
