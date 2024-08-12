package com.integrosys.cms.ui.goodsMaster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateralDAO;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.goodsMaster.bus.IGoodsMasterDao;
import com.integrosys.cms.app.goodsMaster.bus.IGoodsMasterJdbc;
import com.integrosys.cms.app.goodsMaster.bus.GoodsMasterDaoImpl;
import com.integrosys.cms.app.goodsMaster.proxy.IGoodsMasterProxyManager;
import com.integrosys.cms.app.goodsMaster.trx.OBGoodsMasterTrxValue;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.manualinput.CommonUtil;

public class MakerPrepareCreateGoodsMasterCmd extends AbstractCommand implements
ICommonEventConstant{

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
	public MakerPrepareCreateGoodsMasterCmd() {
	}
	
	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "theOBTrxContext",
				"com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE }
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
			{"IgoodsMasterTrxValue", "com.integrosys.cms.app.goodsMaster.trx.OBGoodsMasterTrxValue", SERVICE_SCOPE},
			{ "goodsParentCodeList", "java.util.List", SERVICE_SCOPE },
			{ "prohibitedGoodsCodeStr", String.class.getName(), SERVICE_SCOPE },
		});
	}
	
	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {

		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();

		OBGoodsMasterTrxValue goodsMasterTrxValue = new OBGoodsMasterTrxValue();
		
		//ICollateralDAO collateralDAO = (ICollateralDAO)BeanHouse.get("collateralDao");
		IGoodsMasterJdbc goodsMasterJdbc = (IGoodsMasterJdbc)BeanHouse.get("goodsMasterJdbc");
		
		List<String> prohibitedGoodsCodeList = goodsMasterJdbc.getProhibitedGoodsCode();
		resultMap.put("prohibitedGoodsCodeStr", StringUtils.join(prohibitedGoodsCodeList.toArray(),","));

		//collateralDAO.getFacilityList();
		resultMap.put("goodsParentCodeList", getGoodsParentCodeList());

		resultMap.put("IgoodsMasterTrxValue", goodsMasterTrxValue);
		
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
					//IRegion region = (IRegion)idList.get(i);
					//	String id = Long.toString(region.getIdRegion());
						//String val = region.getRegionName();						
						String id =(String) idList.get(i);
						String val = (String) idList.get(i);
						LabelValueBean lvBean = new LabelValueBean(val, id);
						lbValList.add(id);
						
						//lbValList.add(lvBean);
				}
			} catch (Exception ex) {
			}
			//return CommonUtil.sortDropdown(lbValList);
			return lbValList;
		}
	/*private List getGoodsParentCodeList() {
		List lbValList= new ArrayList();
		CommonCodeList  commonCode = CommonCodeList.getInstance(CategoryCodeConstant.HDFC_DEPARTMENT);
		Map labelValueMap = commonCode.getLabelValueMap();			
		Iterator iterator = labelValueMap.entrySet().iterator();
		String label;
		String value;
		while (iterator.hasNext()) {
	        Map.Entry pairs = (Map.Entry)iterator.next();
	        value=pairs.getKey().toString();
	        label=pairs.getKey()+" ("+pairs.getValue()+")";
			LabelValueBean lvBean = new LabelValueBean(label,value);
			lbValList.add(lvBean);
	    }
		return CommonUtil.sortDropdown(lbValList);
	}*/
}
