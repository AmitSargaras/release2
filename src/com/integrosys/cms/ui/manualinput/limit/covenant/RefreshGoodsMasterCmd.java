package com.integrosys.cms.ui.manualinput.limit.covenant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.goodsMaster.bus.IGoodsMasterJdbc;
import com.integrosys.cms.ui.manualinput.CommonUtil;

public class RefreshGoodsMasterCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
			{ "goodsType", String.class.getName() , REQUEST_SCOPE },
			{ "goodsCode", String.class.getName() , REQUEST_SCOPE }
		});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
			{ "goodsChildList", List.class.getName(), REQUEST_SCOPE },
			{ "goodsSubChildList", List.class.getName(), REQUEST_SCOPE },
			{ "dropdown_name", String.class.getName(), REQUEST_SCOPE },
			});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		List childGoodsList = Collections.emptyList();
		
		String goodsType = (String) map.get("goodsType");
		String goodsCode = (String) map.get("goodsCode");
		
		IGoodsMasterJdbc goodsMasterJdbc = (IGoodsMasterJdbc)BeanHouse.get("goodsMasterJdbc");
		List<String> goodsChildList = goodsMasterJdbc.getNonProhibitedChildGoodsCodeListByParent(goodsCode);
		
		
		if("parentCode".equals(goodsType)) {
			result.put("goodsChildList", getGoodsParentList(goodsChildList));
			result.put("dropdown_name", "goodsChildList");
		}
		else if("childCode".equals(goodsType)) {
			result.put("goodsSubChildList", getGoodsParentList(goodsChildList));
			result.put("dropdown_name", "goodsSubChildList");
		}
		
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	private List getGoodsParentList(List<String> list) {
		if(list != null && !list.isEmpty()) {
			List lbValList = new ArrayList();
			try {
				for(String goodsItem : list) {
					LabelValueBean lvBean = new LabelValueBean(goodsItem,goodsItem);
					lbValList.add(lvBean);
				}
				
			} catch (Exception ex) {
				DefaultLogger.error(this, "Exception caught in getting list :: ", ex);
			}
			return CommonUtil.sortDropdown(lbValList);
		}
		return list;
	}
}
