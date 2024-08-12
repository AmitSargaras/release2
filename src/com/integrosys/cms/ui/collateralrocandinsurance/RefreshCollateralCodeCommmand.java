package com.integrosys.cms.ui.collateralrocandinsurance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateralrocandinsurance.bus.ICollateralRocDao;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;

public class RefreshCollateralCodeCommmand extends AbstractCommand{

public String[][] getParameterDescriptor() {
		
		return (new String[][]{
	            {"collateralCategoryValue", "java.lang.String", REQUEST_SCOPE},
	            {"event", "java.lang.String", REQUEST_SCOPE},
	            //{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE }
			});
	    }
	
	public String[][] getResultDescriptor() {
	
		return (new String[][]{
	            {"event", "java.lang.String", REQUEST_SCOPE},
	            { "collateralRocCodeList", "java.util.List",REQUEST_SCOPE },
	            {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
	            //{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE }
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
        try {
        	String collateralCategoryValue = (String) map.get("collateralCategoryValue");
        	
        	List collateralRocCodeList =new ArrayList();
        	ICollateralRocDao collateralRoc=(ICollateralRocDao)BeanHouse.get("collateralRocDao");
        	if(collateralCategoryValue.equalsIgnoreCase("COLLATERAL")){
        		
        		List collateralNewMasterList = collateralRoc.getCollateralCodeList();
        		for (int i = 0; i < collateralNewMasterList.size(); i++) {
        			Object[] ob=(Object[])collateralNewMasterList.get(i);
						String val = String.valueOf(ob[0].toString());
						String id = String.valueOf(ob[1].toString());
						//LabelValueBean lvBean = new LabelValueBean(val,id);
						LabelValueBean lvBean = new LabelValueBean(replaceSpecialCharForXml1(val),replaceSpecialCharForXml1(id));
						collateralRocCodeList.add(lvBean);
				}
        	}else if(collateralCategoryValue.equalsIgnoreCase("COMPONENT")){
        		
        		List componentList = collateralRoc.getComponentCodeList();
        		for (int i = 0; i < componentList.size(); i++) {
        			Object[] ob=(Object[])componentList.get(i);
						String val = String.valueOf(ob[0].toString());
						String id = String.valueOf(ob[1].toString());
						//LabelValueBean lvBean = new LabelValueBean(val,id);
						LabelValueBean lvBean = new LabelValueBean(replaceSpecialCharForXml1(val),replaceSpecialCharForXml1(id));
						collateralRocCodeList.add(lvBean);
				}
        	}else if(collateralCategoryValue.equalsIgnoreCase("PROPERTY_TYPE")){
        		
        		List propertyTypeList = collateralRoc.getPropertyTypeList();
        		for(int i=0;i<propertyTypeList.size();i++){
    				Object[] ob=(Object[])propertyTypeList.get(i);
    				String val=String.valueOf(ob[0].toString());
    				String id=String.valueOf(ob[1].toString());
    				//LabelValueBean lvBean = new LabelValueBean(val,id);
    				LabelValueBean lvBean = new LabelValueBean(replaceSpecialCharForXml1(val),replaceSpecialCharForXml1(id));
    				collateralRocCodeList.add(lvBean);
    			}
        	}
        	
        	resultMap.put("collateralRocCodeList", collateralRocCodeList);
        } catch (NoSuchGeographyException nsge) {
        	CommandProcessingException cpe = new CommandProcessingException(nsge.getMessage());
			cpe.initCause(nsge);
			throw cpe;
		} catch (Exception e) {
			CommandProcessingException cpe = new CommandProcessingException("Internal Error While Processing ");
			cpe.initCause(e);
			throw cpe;
		}

        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        return returnMap;
    }
    
    public static String replaceSpecialCharForXml1(String input) {
		Map toReplace = new HashMap();
		toReplace.put("<", "&lt;");
		toReplace.put(">", "&gt;");
		toReplace.put("&", "&amp;");
		toReplace.put("\"", "&quot;");
		toReplace.put("\'", "&apos;");
		String temp = input;

		int index = 0;
		while (index < temp.length()) {
			String curChar = temp.substring(index, index + 1);
			if (toReplace.containsKey(curChar)) {
				String afterReplace = (String) (toReplace.get(curChar));
				temp = temp.substring(0, index) + afterReplace + temp.substring(index + 1);
				index = index + afterReplace.length();
			}
			else {
				index = index + 1;
			}
		}
		return temp;
	}
}
