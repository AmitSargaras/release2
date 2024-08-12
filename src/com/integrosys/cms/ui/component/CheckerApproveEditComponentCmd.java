package com.integrosys.cms.ui.component;

import java.util.HashMap;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.holiday.bus.HolidayException;
import com.integrosys.cms.app.holiday.proxy.IHolidayProxyManager;
import com.integrosys.cms.app.holiday.trx.IHolidayTrxValue;
import com.integrosys.cms.app.holiday.trx.OBHolidayTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.component.proxy.IComponentProxyManager;
import com.integrosys.cms.app.component.trx.IComponentTrxValue;
import com.integrosys.cms.app.component.trx.OBComponentTrxValue;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.component.bus.IComponentDao;
import com.integrosys.cms.app.component.bus.OBComponent;
import com.integrosys.cms.app.discrepency.bus.IDiscrepencyDAO;

public class CheckerApproveEditComponentCmd extends AbstractCommand implements ICommonEventConstant {
	
	private IComponentProxyManager componentProxy;



	public IComponentProxyManager getComponentProxy() {
		return componentProxy;
	}

	public void setComponentProxy(IComponentProxyManager componentProxy) {
		this.componentProxy = componentProxy;
	}

	/**
	 * Default Constructor
	 */
	public CheckerApproveEditComponentCmd() {
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
				{"IComponentTrxValue", "com.integrosys.cms.app.component.trx.IComponentTrxValue", SERVICE_SCOPE},
				{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
				{"remarks", "java.lang.String", REQUEST_SCOPE}
		}
		);
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
				{"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE}
		}
		);
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
		try {
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			// Component Trx value
			IComponentTrxValue trxValueIn = (OBComponentTrxValue) map.get("IComponentTrxValue");
			if(trxValueIn.getStatus().equalsIgnoreCase("PENDING_DELETE")){
				trxValueIn.getStagingComponent().setDeprecated("Y");
			}
			OBComponent actualClone = new OBComponent();
			OBComponent stagingClone = new OBComponent();
			OBComponent actual = (OBComponent)trxValueIn.getComponent();
			OBComponent staging = (OBComponent)trxValueIn.getStagingComponent();
			if(actual!=null){
			actualClone = (OBComponent)actual.clone();
			}
			stagingClone = (OBComponent)staging.clone();
			String remarks = (String) map.get("remarks");
			ctx.setRemarks(remarks);
			// Function  to approve updated Component Trx
			IComponentTrxValue trxValueOut = getComponentProxy().checkerApproveComponent(ctx, trxValueIn);
			resultMap.put("request.ITrxValue", trxValueOut);
            OBComponent obj = (OBComponent)trxValueIn.getStagingComponent();
			
			IComponentDao componentDAO=(IComponentDao)BeanHouse.get("componentDao");
			
			if(trxValueIn!=null && (trxValueIn.getOperationDescField().equals("APPROVE_CREATE_COMPONENT") || trxValueIn.getOperationDescField().equals("APPROVE_COMPONENT")))
			{
				componentDAO.insertComponentinCommonCode(obj);
			}
			if(trxValueIn!=null && trxValueIn.getOperationDescField().equals("CHECKER_APPROVE_UPDATE"))
			{
				boolean isComponentNameChanged = false; 
				boolean isComponentDeleted = false; 
				boolean isComponentTypeChanged = false; 
				
				if(!stagingClone.getComponentName().equalsIgnoreCase(actualClone.getComponentName())){
					isComponentNameChanged = true;
				}
	            if(!stagingClone.getDeprecated().equalsIgnoreCase(actualClone.getDeprecated())&& "Y".equals(staging.getDeprecated())){
	            	isComponentDeleted = true;
				}
	            if(!stagingClone.getComponentType().equalsIgnoreCase(actualClone.getComponentType())){
	            	isComponentTypeChanged = true;
				}
				componentDAO.updateComponentinCommonCode(actualClone,stagingClone,isComponentNameChanged,isComponentDeleted,isComponentTypeChanged);
			}
					
			
		}catch (ComponentException ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		} catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
