package com.integrosys.cms.ui.goodsMaster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateralDAO;
import com.integrosys.cms.app.excludedfacility.bus.ExcludedFacilityException;
import com.integrosys.cms.app.excludedfacility.bus.IExcludedFacility;
import com.integrosys.cms.app.excludedfacility.bus.OBExcludedFacility;
import com.integrosys.cms.app.excludedfacility.trx.IExcludedFacilityTrxValue;
import com.integrosys.cms.app.excludedfacility.trx.OBExcludedFacilityTrxValue;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.goodsMaster.bus.IGoodsMaster;
import com.integrosys.cms.app.goodsMaster.bus.IGoodsMasterJdbc;
import com.integrosys.cms.app.goodsMaster.bus.OBGoodsMaster;
import com.integrosys.cms.app.goodsMaster.bus.GoodsMasterException;
import com.integrosys.cms.app.goodsMaster.proxy.IGoodsMasterProxyManager;
import com.integrosys.cms.app.goodsMaster.trx.IGoodsMasterTrxValue;
import com.integrosys.cms.app.goodsMaster.trx.OBGoodsMasterTrxValue;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.ui.manualinput.CommonUtil;

public class CheckerReadGoodsMasterCmd extends AbstractCommand implements ICommonEventConstant {

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
	public CheckerReadGoodsMasterCmd() {
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
				{ "goodsMasterObj", "com.integrosys.cms.app.goodsMaster.bus.OBGoodsMaster", FORM_SCOPE },
				{"IgoodsMasterTrxValue", "com.integrosys.cms.app.goodsMaster.trx.IGoodsMasterTrxValue", SERVICE_SCOPE},
				{"event", "java.lang.String", REQUEST_SCOPE},
				{ "goodsParentCodeList", "java.util.List", SERVICE_SCOPE },
				{ "prohibitedGoodsCodeStr", String.class.getName(), SERVICE_SCOPE },
				
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
			IGoodsMaster goodsMaster;
			IGoodsMasterTrxValue trxValue=null;
			String branchCode=(String) (map.get("TrxId"));
			String event = (String) map.get("event");
			// function to get goodsMaster Trx value
			trxValue = (OBGoodsMasterTrxValue) getGoodsMasterProxy().getGoodsMasterByTrxID(branchCode);
			
			goodsMaster = (OBGoodsMaster) trxValue.getStagingGoodsMaster();
			
			OBGoodsMasterTrxValue goodsMasterTrxValue = new OBGoodsMasterTrxValue();
			
			IGoodsMasterJdbc goodsMasterJdbc = (IGoodsMasterJdbc)BeanHouse.get("goodsMasterJdbc");
			
			List<String> prohibitedGoodsCodeList = goodsMasterJdbc.getProhibitedGoodsCode();
			resultMap.put("prohibitedGoodsCodeStr", StringUtils.join(prohibitedGoodsCodeList.toArray(),","));
			
			resultMap.put("goodsParentCodeList", getGoodsParentCodeList());
			
			resultMap.put("IgoodsMasterTrxValue", trxValue);
			resultMap.put("goodsMasterObj", goodsMaster);
			resultMap.put("event", event);
		} catch (GoodsMasterException e) {
		
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		} catch (TransactionException e) {
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}catch (Exception e) {
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
				//IGoodsMasterDao collateralDAO = (IGoodsMasterDao)BeanHouse.get("collateralDao");
				//List idList = (List) collateralDAO.getGoodsParentCodeList();	
				//List<LabelValueBean> idList=new ArrayList<LabelValueBean>();
				ICollateralDAO collateralDAO = (ICollateralDAO)BeanHouse.get("collateralDao");
				List idList = (List) collateralDAO.getGoodsParentCodeList();
				for (int i = 0; i < idList.size(); i++) {
					String id =(String) idList.get(i);
					String val = (String) idList.get(i);
						LabelValueBean lvBean = new LabelValueBean(val, id);
						lbValList.add(id);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return lbValList;
		}
}
