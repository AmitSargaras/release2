package com.integrosys.cms.ui.collateralrocandinsurance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateralrocandinsurance.bus.ICollateralRocDao;
import com.integrosys.cms.app.collateralrocandinsurance.bus.OBCollateralRoc;
import com.integrosys.cms.app.collateralrocandinsurance.proxy.ICollateralRocProxyManager;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;

public class MakerCreateErrorCmd extends AbstractCommand implements ICommonEventConstant {

	private ICollateralRocProxyManager collateralRocProxy;

	public ICollateralRocProxyManager getCollateralRocProxy() {
		return collateralRocProxy;
	}

	public void setCollateralRocProxy(ICollateralRocProxyManager collateralRocProxy) {
		this.collateralRocProxy = collateralRocProxy;
	}

	/**
	 * Defaulc Constructor
	 */
	public MakerCreateErrorCmd() {
		super();
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
				{ "ICollateralRocTrxValue",
						"com.integrosys.cms.app.collateralrocandinsurance.trx.ICollateralRocTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				/*{ "remarks", "java.lang.String", REQUEST_SCOPE }, */
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "collateralCategory", "java.lang.String", REQUEST_SCOPE },
				/*{ "collateralRocCode", "java.lang.String", REQUEST_SCOPE },*/
				{ "collateralRocActualCode", "java.lang.String", REQUEST_SCOPE },
				{ "collateralRocObj","com.integrosys.cms.app.collateralrocandinsurance.bus.OBCollateralRoc", FORM_SCOPE }
		});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE },
				{ "collateralCategory", "java.lang.String", REQUEST_SCOPE },
				{ "collateralRocCode", "java.lang.String", REQUEST_SCOPE },
				{ "collateralRocCodeList", "java.util.List", SERVICE_SCOPE },
				{ "collateralRocActualCode", "java.lang.String", REQUEST_SCOPE },
				{ "collateralRocObj","com.integrosys.cms.app.collateralrocandinsurance.bus.OBCollateralRoc", FORM_SCOPE }
		});
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
	        	String collateralCategoryValue = (String) map.get("collateralCategory");
	        	OBCollateralRoc collateralRocObj = (OBCollateralRoc) map.get("collateralRocObj");
	        	String collateralRocDescription = collateralRocObj.getCollateralRocDescription();
	        	String collateralRocActualCode = (String) map.get("collateralRocActualCode");
	        	//String collateralRocCode1 = (String) map.get("collateralRocCode");
	        	List collateralRocCodeList =new ArrayList();
	        	ICollateralRocDao collateralRoc=(ICollateralRocDao)BeanHouse.get("collateralRocDao");
	        	if(collateralCategoryValue.equalsIgnoreCase("COLLATERAL")){
	        		
	        		List collateralNewMasterList = collateralRoc.getCollateralCodeList();
	        		for (int i = 0; i < collateralNewMasterList.size(); i++) {
	        			Object[] ob=(Object[])collateralNewMasterList.get(i);
							String val = String.valueOf(ob[0].toString());
							String id = String.valueOf(ob[1].toString());
							LabelValueBean lvBean = new LabelValueBean(val, id);
							collateralRocCodeList.add(lvBean);
					}
	        	}else if(collateralCategoryValue.equalsIgnoreCase("COMPONENT")){
	        		
	        		List componentList = collateralRoc.getComponentCodeList();
	        		for (int i = 0; i < componentList.size(); i++) {
	        			Object[] ob=(Object[])componentList.get(i);
							String val = String.valueOf(ob[0].toString());
							String id = String.valueOf(ob[1].toString());
							LabelValueBean lvBean = new LabelValueBean(val, id);
							collateralRocCodeList.add(lvBean);
					}
	        	}else if(collateralCategoryValue.equalsIgnoreCase("PROPERTY_TYPE")){
	        		
	        		List propertyTypeList = collateralRoc.getPropertyTypeList();
	        		for(int i=0;i<propertyTypeList.size();i++){
	    				Object[] ob=(Object[])propertyTypeList.get(i);
	    				String val=String.valueOf(ob[0].toString());
	    				String id=String.valueOf(ob[1].toString());
	    				LabelValueBean lvBean = new LabelValueBean(val, id);
	    				collateralRocCodeList.add(lvBean);
	    			}
	        	}
	        	resultMap.put("collateralRocCodeList", collateralRocCodeList);
	        	resultMap.put("collateralRocActualCode", collateralRocActualCode);
	        	resultMap.put("collateralRocDescription", collateralRocDescription);
	        	//resultMap.put("collateralRocCode", collateralRocCode);
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
}
