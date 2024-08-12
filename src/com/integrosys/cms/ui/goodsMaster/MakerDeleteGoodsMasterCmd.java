package com.integrosys.cms.ui.goodsMaster;

import java.util.HashMap;
import java.util.List;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.goodsMaster.bus.OBGoodsMaster;
import com.integrosys.cms.app.collateral.bus.ICollateralDAO;
import com.integrosys.cms.app.goodsMaster.bus.GoodsMasterException;
import com.integrosys.cms.app.goodsMaster.proxy.IGoodsMasterProxyManager;
import com.integrosys.cms.app.goodsMaster.trx.IGoodsMasterTrxValue;
import com.integrosys.cms.app.goodsMaster.trx.OBGoodsMasterTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class MakerDeleteGoodsMasterCmd extends AbstractCommand implements ICommonEventConstant {

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
	public MakerDeleteGoodsMasterCmd() {
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
				{"IgoodsMasterTrxValue", "com.integrosys.cms.app.goodsMaster.trx.IGoodsMasterTrxValue", SERVICE_SCOPE},
				{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
				{"event", "java.lang.String", REQUEST_SCOPE},
				{ "goodsMasterObj", "com.integrosys.cms.app.goodsMaster.bus.OBGoodsMaster", FORM_SCOPE },
				{"remarks", "java.lang.String", REQUEST_SCOPE}
		});
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
				{"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE},
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
		try{
		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		IGoodsMasterTrxValue trxValueIn = (OBGoodsMasterTrxValue) map.get("IgoodsMasterTrxValue");
		OBGoodsMaster goodsMaster = (OBGoodsMaster) map.get("goodsMasterObj");
		String event = (String) map.get("event");
		String remarks = (String) map.get("remarks");
		IGoodsMasterTrxValue trxValueOut = new OBGoodsMasterTrxValue();
		ICollateralDAO collateralDAO = (ICollateralDAO)BeanHouse.get("collateralDao");
		boolean isGoodsCodeUnique = false;
		
		if( event.equals("maker_confirm_resubmit_delete") ){
			if(goodsMaster.getStatus().equalsIgnoreCase("ACTIVE"))
			{
			String goodsCode=goodsMaster.getGoodsCode();
			String goodsCodeActual=trxValueIn.getStagingGoodsMaster().getGoodsCode();
			if(! goodsCode.equals(goodsCodeActual)) {
				isGoodsCodeUnique = getGoodsMasterProxy().isGoodsCodeUnique(goodsCode);				
				if(isGoodsCodeUnique != false){
					exceptionMap.put("goodsCodeError", new ActionMessage("error.string.exist","This Goods Code "));
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
				}
			}
			ctx.setRemarks(remarks);
			trxValueOut = getGoodsMasterProxy().makerEditRejectedGoodsMaster(ctx, trxValueIn,goodsMaster);
		
		}
			else 
			{
				if(goodsMaster.getGoodsCode() != null)
				{
					String goodsCode=goodsMaster.getGoodsCode();
					List childGoodsCodeList = collateralDAO.getChildsAgaintParentGoodsCode(goodsCode);
					if(null != childGoodsCodeList)
					{
					if(!childGoodsCodeList.isEmpty() && childGoodsCodeList.size() != 0)
					{
						exceptionMap.put("goodsParentCodeError", new ActionMessage("error.goods.code.parent.delete"));
						//exceptionMap.put("goodsParentCodeError", new ActionMessage("error.string.exist","Cannot delete !!!!!!- Child goodcodes against this goodcode "));
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						return returnMap;
					}
					else
					{
						ctx.setRemarks(remarks);
						trxValueOut = getGoodsMasterProxy().makerEditRejectedGoodsMaster(ctx, trxValueIn,goodsMaster);
					}
					}
				}
			}
		}
		
		
		resultMap.put("request.ITrxValue", trxValueOut);
			
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
