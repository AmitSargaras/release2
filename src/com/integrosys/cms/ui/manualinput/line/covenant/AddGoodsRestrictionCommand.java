package com.integrosys.cms.ui.manualinput.line.covenant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMessage;
import org.springframework.util.CollectionUtils;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ILineCovenant;
import com.integrosys.cms.app.customer.bus.OBLineCovenant;
import com.integrosys.cms.app.goodsMaster.bus.IGoodsMaster;
import com.integrosys.cms.app.goodsMaster.bus.IGoodsMasterJdbc;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.ui.manualinput.limit.covenant.ILmtCovenantConstants;

public class AddGoodsRestrictionCommand extends AbstractCommand implements ILineCovenantConstants,ILmtCovenantConstants {
	
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ COVENANT_LINE_DETAIL_FORM, ILineCovenant.class.getName(), FORM_SCOPE },
				{ SESSION_COVENANT_GOODS_RESTRICTION_LIST_LINE, List.class.getName() , SERVICE_SCOPE },
				{ "goodsParentCode", "java.lang.String", REQUEST_SCOPE },
				{ "goodsChildCode", "java.lang.String", REQUEST_SCOPE },
				{ "goodsSubChildCode", "java.lang.String", REQUEST_SCOPE },
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
			{ COVENANT_LINE_DETAIL_FORM,ILineCovenant.class.getName(), FORM_SCOPE },
			{ SESSION_COVENANT_GOODS_RESTRICTION_LIST_LINE, List.class.getName() , SERVICE_SCOPE },
			
			{ "goodsParentCode", "java.lang.String", REQUEST_SCOPE },
			{ "goodsChildCode", "java.lang.String", REQUEST_SCOPE },
			{ "goodsSubChildCode", "java.lang.String", REQUEST_SCOPE },
			
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
	if(AbstractCommonMapper.isEmptyOrNull(event)) {
		event="add_goods_restriction";
	}
	List<OBLineCovenant> list = (List)map.get(SESSION_COVENANT_GOODS_RESTRICTION_LIST_LINE);
	 HashMap exceptionMap = new HashMap();
	String goodsParentCode = (String)map.get("goodsParentCode");
	String goodsChildCode = (String)map.get("goodsChildCode");
	String goodsSubChildCode = (String)map.get("goodsSubChildCode");
	
	
	ILimitTrxValue lmtTrxObj = (ILimitTrxValue) map.get("lmtTrxObj");
	DefaultLogger.debug(this, "in AddGoodsRestrictionCommand.java ==>> lmtTrxObj.getReferenceID();"+ lmtTrxObj.getReferenceID()); 
	DefaultLogger.debug(this, "in AddGoodsRestrictionCommand.java ==>> event "+ event); 

	if(list==null){
		list = new ArrayList();
	}
	boolean flag = false;
	ILimitDAO lmtDao = LimitDAOFactory.getDAO();
	if(lmtTrxObj.getReferenceID()!=null) {
    
	}
	
	IGoodsMasterJdbc goodsMasterJdbc = (IGoodsMasterJdbc)BeanHouse.get("goodsMasterJdbc");
	
	ILineCovenant covenantDetail = (ILineCovenant) map.get(COVENANT_LINE_DETAIL_FORM);
	
	if(list!=null && list.size()!=0)
	{	
		if(StringUtils.isNotBlank(goodsParentCode) && (StringUtils.isBlank(goodsChildCode) || !ArrayUtils.isEmpty(goodsChildCode.split(","))) && StringUtils.isBlank(goodsSubChildCode)) {
			if(isChildCodeExistsByParent(list, goodsParentCode)){
				exceptionMap.put("goodsRestrictionParentCode", new ActionMessage("error.goods.code.selected",goodsParentCode));
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			}
		}
		
		if(isChildSubChildGoodsCodeExistsInList(list, goodsChildCode, true)) {
			exceptionMap.put("goodsRestrictionParentCode", new ActionMessage("error.goods.code.selected",goodsChildCode));
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		}
		
		if(isChildSubChildGoodsCodeExistsInList(list, goodsSubChildCode, false)) {
			exceptionMap.put("goodsRestrictionParentCode", new ActionMessage("error.goods.code.selected",goodsSubChildCode));
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		}
	}
	
	
	if(exceptionMap.size()>0) {
		return returnMap;
	}
	

	
	//create main parent
	if(!isParentCodeExists(list,goodsParentCode)) {
		OBLineCovenant value = getLimitCovenant(goodsParentCode,null,null);
		list.add(value);
	}
	//parent selected, child selected, sub child selected/not selected
	if(StringUtils.isNotBlank(goodsParentCode) && StringUtils.isNotBlank(goodsChildCode) && StringUtils.isNotBlank(goodsSubChildCode)) {
		
		//child
		list.add(getLimitCovenant(goodsParentCode,goodsChildCode,null));
		
		String[] goodsSubChildArr = goodsSubChildCode.split(",");
		//sub child
		if(!ArrayUtils.isEmpty(goodsSubChildArr)) {
			for(String goodsSubChild : goodsSubChildArr) {
				list.add(getLimitCovenant(goodsParentCode,goodsChildCode,goodsSubChild));
			}
		}	
		
	} 
	
	//Child is blank and subchild is blank
	if(StringUtils.isNotBlank(goodsParentCode) && StringUtils.isBlank(goodsChildCode) && StringUtils.isBlank(goodsSubChildCode)) {
		
		List<IGoodsMaster> childList = goodsMasterJdbc.getAllNonProhibitedGoodsMasterListByParent(goodsParentCode);
		for(IGoodsMaster child : childList) {
			
			list.add(getLimitCovenant(goodsParentCode,child.getGoodsParentCode(),child.getGoodsCode()));
		}
	}
	//Parent code selected, comma separated child, sub child blank
	if(StringUtils.isNotBlank(goodsParentCode) && StringUtils.isNotBlank(goodsChildCode) && StringUtils.isBlank(goodsSubChildCode)) {
		
		String[] goodsChildArr = goodsChildCode.split(",");
		
		if(!ArrayUtils.isEmpty(goodsChildArr)) {
			for(String goodsChild : goodsChildArr) {
				
				list.add(getLimitCovenant(goodsParentCode,goodsChild,null));
				
				List<IGoodsMaster> childList = goodsMasterJdbc.getAllNonProhibitedGoodsMasterListByParent(goodsChild);
				for(IGoodsMaster subChild : childList) {
					list.add(getLimitCovenant(goodsParentCode,subChild.getGoodsParentCode(),subChild.getGoodsCode()));
				}
			}
		}
		
		
	}
	
	Collections.sort(list, OBLineCovenant.Comparators.GOODS_RESTRICTION_COMPARATOR);
	
	resultMap.put("event",event);
	resultMap.put(SESSION_COVENANT_GOODS_RESTRICTION_LIST_LINE, list);
	resultMap.put("lmtTrxObj", lmtTrxObj);
	resultMap.put(COVENANT_LINE_DETAIL_FORM, covenantDetail);
	
	DefaultLogger.debug(this, " -------- Create Successfull -----------");
	returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
	return returnMap;		
}

	private boolean isChildSubChildGoodsCodeExistsInList(List<OBLineCovenant> list, String goodsCode, boolean isChild) {
		boolean isExists = false;
		
		if(StringUtils.isNotBlank(goodsCode)){
			if(isChild) {
				for(OBLineCovenant cov : list) {
					if(goodsCode.equals(cov.getGoodsRestrictionChildCode())) {
						isExists = true;
						break;
					}
				}		
			}
			else {
				for(OBLineCovenant cov : list) {
					if(goodsCode.equals(cov.getGoodsRestrictionSubChildCode())) {
						isExists = true;
						break;
					}
						
				}
			}
		}
		
		return isExists;
	}

	private boolean isParentCodeExists(List<OBLineCovenant> list, String goodsParentCode) {
		
		boolean isExists = false;
		if(StringUtils.isNotBlank(goodsParentCode)) {
			for(OBLineCovenant cov : list) {
				if(StringUtils.isNotBlank(cov.getGoodsRestrictionComboCode())) {
					String targetComboCode = goodsParentCode+"|0|0";
					if(cov.getGoodsRestrictionComboCode().equals(targetComboCode)) {
						isExists = true;
						break;						
					}
				}
			}
		}
		return isExists;
	}
	
	private boolean isChildCodeExistsByParent(List<OBLineCovenant> list, String goodsParentCode) {
		
		boolean isExists = false;
		if(StringUtils.isNotBlank(goodsParentCode)) {
			String parentComboCode = goodsParentCode+"|0|0";
			for(OBLineCovenant cov : list) {
				if(goodsParentCode.equals(cov.getGoodsRestrictionParentCode()) && parentComboCode.equals(cov.getGoodsRestrictionComboCode())) {
					isExists = true;
					break;					
				}
			}
			
			if(isExists) {
				List<OBLineCovenant> childGoodsCodes = getChildGoodsCodeByParent(list,parentComboCode);
				isExists = !CollectionUtils.isEmpty(childGoodsCodes);
			}
		}
		return isExists;
	}
	
	private List<OBLineCovenant> getChildGoodsCodeByParent(List<OBLineCovenant> list, String goodsCodeParentCombo) {
		String targetParent = goodsCodeParentCombo!=null && goodsCodeParentCombo.split("\\|").length ==3 ?goodsCodeParentCombo.split("\\|")[0]:null;
		List<OBLineCovenant> childList = new ArrayList<OBLineCovenant>();
		for(OBLineCovenant cov : list) {
			if(StringUtils.isNotBlank(cov.getGoodsRestrictionParentCode()) && cov.getGoodsRestrictionParentCode().equals(targetParent) && !cov.getGoodsRestrictionComboCode().equals(goodsCodeParentCombo)) {
				childList.add(cov);
			}
		}
		
		return childList;
	}

	private OBLineCovenant getLimitCovenant(String goodsParentCode, String goodsChildCode, String goodsSubChildCode) {
		OBLineCovenant value = new OBLineCovenant();
		value.setGoodsRestrictionParentCode(goodsParentCode);
		value.setGoodsRestrictionChildCode(goodsChildCode);
		value.setGoodsRestrictionSubChildCode(goodsSubChildCode);
		
		StringBuffer combo = new StringBuffer();
		combo.append(getDefaultValueForCombo(goodsParentCode))
			.append("|")
			.append(getDefaultValueForCombo(goodsChildCode))
			.append("|")
			.append(getDefaultValueForCombo(goodsSubChildCode));
		
		value.setGoodsRestrictionComboCode(String.valueOf(combo));
		value.setGoodsRestrictionInd(ICMSConstant.YES);
		return value;
	}
	
	private String getDefaultValueForCombo(String input) {
		return StringUtils.isBlank(input)?"0":input;
	}

}
