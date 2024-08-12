package com.integrosys.cms.ui.goodsMaster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.goodsMaster.bus.OBGoodsMaster;
import com.integrosys.cms.app.collateral.bus.ICollateralDAO;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.goodsMaster.bus.GoodsMasterException;
import com.integrosys.cms.app.goodsMaster.proxy.IGoodsMasterProxyManager;
import com.integrosys.cms.app.goodsMaster.trx.IGoodsMasterTrxValue;
import com.integrosys.cms.app.goodsMaster.trx.OBGoodsMasterTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.manualinput.CommonUtil;

public class MakerEditGoodsMasterCmd extends AbstractCommand implements ICommonEventConstant{

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
	public MakerEditGoodsMasterCmd() {
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
				{ "remarks", "java.lang.String", REQUEST_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE },
				{"event", "java.lang.String", REQUEST_SCOPE},
				{ "goodsMasterObj", "com.integrosys.cms.app.goodsMaster.bus.OBGoodsMaster", FORM_SCOPE }

		});
	}
	
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE },
				{ "goodsParentCodeList", "java.util.List", SERVICE_SCOPE } });
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
			if(trxValueIn.getFromState().equals("PENDING_PERFECTION")){
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
				trxValueOut = getGoodsMasterProxy().makerUpdateSaveCreateGoodsMaster(ctx, trxValueIn,goodsMaster);
			}
			else{
				if( event.equals("maker_edit_goods_master") ||event.equals("maker_save_update") ){
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
					trxValueOut = getGoodsMasterProxy().makerUpdateGoodsMaster(ctx, trxValueIn,goodsMaster);
				}
				// event is  maker_confirm_resubmit_edit
			}
			ICollateralDAO collateralDAO = (ICollateralDAO)BeanHouse.get("collateralDao");
			if(event.equals("maker_delete_goods_master"))
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
						trxValueOut = getGoodsMasterProxy().makerUpdateGoodsMaster(ctx, trxValueIn,goodsMaster);
					}
					}
				}
			}
			
			OBGoodsMasterTrxValue goodsMasterTrxValue = new OBGoodsMasterTrxValue();
			resultMap.put("goodsParentCodeList", getGoodsParentCodeList());
			
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
	
	private List getGoodsParentCodeList() {
		 List lbValList = new ArrayList();
			try {
				DefaultLogger.debug(this, "inside getGoodsParentCodeList() method");
				ICollateralDAO collateralDAO = (ICollateralDAO)BeanHouse.get("collateralDao");
				List idList = (List) collateralDAO.getGoodsParentCodeList();
				for (int i = 0; i < idList.size(); i++) {
					String id =(String) idList.get(i);
					String val = (String) idList.get(i);
						LabelValueBean lvBean = new LabelValueBean(val, id);
						lbValList.add(lvBean);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return CommonUtil.sortDropdown(lbValList);
		}
}
