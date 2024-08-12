package com.integrosys.cms.ui.manualinput.limit.covenant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.ILimitCovenant;
import com.integrosys.cms.app.limit.bus.OBLimitCovenant;

public class DeleteGoodsRestrictionCommand extends AbstractCommand implements ILmtCovenantConstants {
	
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ COVENANT_DETAIL_FORM, ILimitCovenant.class.getName(), FORM_SCOPE },
				{ SESSION_COVENANT_GOODS_RESTRICTION_LIST, List.class.getName() , SERVICE_SCOPE },
				{ "goodsCode", String.class.getName() , REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, 
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
			{ "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
			{ COVENANT_DETAIL_FORM,ILimitCovenant.class.getName(), FORM_SCOPE },
			{ SESSION_COVENANT_GOODS_RESTRICTION_LIST, List.class.getName() , SERVICE_SCOPE },
			{ "event", "java.lang.String", REQUEST_SCOPE }, 
			});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here get data from database for Interest
	 * Rate is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
	
	HashMap resultMap = new HashMap();
	HashMap returnMap = new HashMap();
	String event = (String) map.get("event");
	List<OBLimitCovenant> list = (List)map.get(SESSION_COVENANT_GOODS_RESTRICTION_LIST);
	HashMap exceptionMap = new HashMap();
	String goodsCode = (String)map.get("goodsCode");
	
	ILimitCovenant covenantDetail = (ILimitCovenant) map.get(COVENANT_DETAIL_FORM);
	
	
	if(StringUtils.isNotBlank(goodsCode)) {
		List<String> goodsCodeComboList = Arrays.asList(goodsCode.split(","));
		
		List<String> subChildComboList = new ArrayList<String>();
		List<String> childComboList = new ArrayList<String>();
		List<String> parentComboList = new ArrayList<String>();
		
		for(String goodsCodeCombo : goodsCodeComboList) {
			if(isSubChildGoodsCode(goodsCodeCombo)) {
				subChildComboList.add(goodsCodeCombo);
			}
			else if(isParentGoodsCode(goodsCodeCombo)) {
				parentComboList.add(goodsCodeCombo);
			}
			else {
				childComboList.add(goodsCodeCombo);
			}
		}
		
		list = deleteGoodsRestrictionItems(list,subChildComboList);
		
		for(String childCombo : childComboList) {
			List<String> subChildComboList1 = getSubChildGoodsCodeComboByChild(list, childCombo);
			list = deleteGoodsRestrictionItems(list,subChildComboList1);
			list = deleteGoodsRestrictionItems(list,Arrays.asList(childCombo));
		}
		
		for(String parentCombo : parentComboList) {
			List<String> childComboList1 = getChildGoodsCodeComboByParent(list, parentCombo);
			
			for(String child : childComboList1) {
				List<String> subChildComboList1 = getSubChildGoodsCodeComboByChild(list, child);
				list = deleteGoodsRestrictionItems(list,subChildComboList1);
			}
			
			list = deleteGoodsRestrictionItems(list,childComboList1);
			list = deleteGoodsRestrictionItems(list,Arrays.asList(parentCombo));
		}
		
	}
	
	
	
	
	resultMap.put("event",event);
	resultMap.put(SESSION_COVENANT_GOODS_RESTRICTION_LIST, list);
	resultMap.put(COVENANT_DETAIL_FORM, covenantDetail);
	
	DefaultLogger.debug(this, " -------- Delete Successfull -----------");
	returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
	return returnMap;		
}

	private List<OBLimitCovenant> deleteGoodsRestrictionItems(List<OBLimitCovenant> list, List<String> comboList) {
		if(!CollectionUtils.isEmpty(list)) {
			Iterator<OBLimitCovenant> itr = list.iterator();
			while(itr.hasNext()) {
				if(comboList.contains(itr.next().getGoodsRestrictionComboCode()))
					itr.remove();
			}
		}
		return list;
	}

	private List<String> getChildGoodsCodeComboByParent(List<OBLimitCovenant> list, String goodsCodeParentCombo) {
		String targetParent = goodsCodeParentCombo!=null?goodsCodeParentCombo.split("\\|")[0]:null;
		List<String> childComboList = new ArrayList<String>();
		for(OBLimitCovenant cov : list) {
			if(StringUtils.isNotBlank(cov.getGoodsRestrictionParentCode()) && cov.getGoodsRestrictionParentCode().equals(targetParent) && !cov.getGoodsRestrictionComboCode().equals(goodsCodeParentCombo)) {
				childComboList.add(cov.getGoodsRestrictionComboCode());
			}
		}
		
		return childComboList;
	}
	
	private List<String> getSubChildGoodsCodeComboByChild(List<OBLimitCovenant> list, String goodsCodeChildCombo) {
		String targetChild = goodsCodeChildCombo!=null && goodsCodeChildCombo.split("\\|").length==3 ?goodsCodeChildCombo.split("\\|")[1]:null;
		List<String> subChildComboList = new ArrayList<String>();
		for(OBLimitCovenant cov : list) {
			if(StringUtils.isNotBlank(cov.getGoodsRestrictionChildCode()) && cov.getGoodsRestrictionChildCode().equals(targetChild) && !cov.getGoodsRestrictionComboCode().equals(goodsCodeChildCombo)) {
				subChildComboList.add(cov.getGoodsRestrictionComboCode());
			}
		}
		
		return subChildComboList;
	}

	private boolean isParentGoodsCode(String goodsCodeCombo) {
		boolean flag = false;
		if(StringUtils.isNotBlank(goodsCodeCombo)) {
			String[] goodsCodeComboArr = goodsCodeCombo.split("\\|") ;
			if(goodsCodeComboArr.length == 3) {
				String childCode = goodsCodeComboArr[1];
				String subChildCode = goodsCodeComboArr[2];
				flag = (childCode.equals(subChildCode) && childCode.equals("0")); 
			}
			
		}
		return flag;
	}
	
	private boolean isSubChildGoodsCode(String goodsCodeCombo) {
		boolean flag = false;
		if(StringUtils.isNotBlank(goodsCodeCombo)) {
			String[] goodsCodeComboArr = goodsCodeCombo.split("\\|") ;
			if(goodsCodeComboArr.length == 3) {
				String lastIndex = goodsCodeComboArr[2];
				flag = !"0".equals(lastIndex);
			}
		}
		return flag;
	}

}
