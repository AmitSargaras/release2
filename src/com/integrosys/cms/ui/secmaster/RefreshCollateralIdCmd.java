/*
 * Created on Apr 5, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.secmaster;

/**
 * 
 * 
 * @author abhijit.rudrakshawar
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.chktemplate.proxy.CheckListTemplateProxyManagerFactory;
import com.integrosys.cms.app.chktemplate.proxy.ICheckListTemplateProxyManager;
import com.integrosys.cms.app.collateral.bus.CollateralComparator;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralSubType;
import com.integrosys.cms.app.collateral.bus.OBCollateral;
import com.integrosys.cms.app.collateralNewMaster.bus.OBCollateralNewMaster;
import com.integrosys.cms.app.customer.bus.CustomerSearchCriteria;
import com.integrosys.cms.app.imageTag.proxy.IImageTagProxyManager;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.ui.common.SecurityTypeList;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.security.MISecurityUIHelper;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class RefreshCollateralIdCmd extends AbstractCommand {

	
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				
				{ "secSubType", "java.lang.String", REQUEST_SCOPE },
				 });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
				
				{ "collateralIdList", "java.util.List", REQUEST_SCOPE },
				

		});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException, AccessDeniedException {
		HashMap lmtcolmap = new HashMap();
		HashMap returnMap = new HashMap();

		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		OBCollateralNewMaster obcol= new OBCollateralNewMaster();
		List collateralIdList = new ArrayList();
		List collateralOBList = new ArrayList();
		String secSubTypeString = (String) map.get("secSubType");
		String[] secSubTypeTokens= secSubTypeString.split(",");
		String secSubType= secSubTypeTokens[2];
		ICheckListTemplateProxyManager proxy = CheckListTemplateProxyManagerFactory.getCheckListTemplateProxyManager();
		SearchResult collateralObjects=new SearchResult();
		try {
			collateralObjects = proxy.getCollateralList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		collateralOBList=(List) collateralObjects.getResultList();
		Iterator i = collateralOBList.iterator();
		while (i.hasNext()) {
			obcol = ((OBCollateralNewMaster) i.next());

			if (obcol.getNewCollateralSubType().equalsIgnoreCase(secSubType)) {
				LabelValueBean lvBean = new LabelValueBean(String.valueOf(obcol.getNewCollateralDescription()), String.valueOf(obcol.getNewCollateralCode()));
				collateralIdList.add(lvBean);
			}

		}

		
		result.put("collateralIdList", collateralIdList);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}

	

}
