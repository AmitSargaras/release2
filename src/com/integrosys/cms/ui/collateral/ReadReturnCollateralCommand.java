/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/ReadReturnCollateralCommand.java,v 1.4 2003/08/30 04:13:39 hshii Exp $
 */

package com.integrosys.cms.ui.collateral;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableCollateral;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableEquity;
import com.integrosys.cms.app.collateral.bus.type.marketable.OBMarketableEquity;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.bondslocal.OBBondsLocal;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.mainindexlocal.OBMainIndexLocal;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.otherlistedlocal.OBOtherListedLocal;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.ui.collateral.marketablesec.linedetail.IMarketableEquityLineDetailConstants;

/**
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/08/30 04:13:39 $ Tag: $Name: $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jul 2, 2003 Time: 12:13:00 PM
 * To change this template use Options | File Templates.
 */
public class ReadReturnCollateralCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "subtype", "java.lang.String", REQUEST_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "from_event", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
			//	{ "form.PortItemObject", "java.lang.Object", FORM_SCOPE },
				
				{ "insuranceStatusRadio", "java.lang.String", REQUEST_SCOPE },
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
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "form.collateralObject", "com.integrosys.cms.app.collateral.bus.ICollateral", FORM_SCOPE },
				{ "from_event", "java.lang.String", REQUEST_SCOPE }, { "subtype", "java.lang.String", REQUEST_SCOPE },
				{ "collateralID", "java.lang.String", REQUEST_SCOPE },
				{IMarketableEquityLineDetailConstants.SESSION_LINE_DETAIL_LIST, List.class.getName(), SERVICE_SCOPE},
				{IMarketableEquityLineDetailConstants.SESSION_EQUITY_EVENT, String.class.getName(), SERVICE_SCOPE },
				{ "insuranceStatusRadio", "java.lang.String", REQUEST_SCOPE },
				{ "session.PortItemObject", "java.lang.Object", SERVICE_SCOPE }
				});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap result = new HashMap();
		HashMap temp = new HashMap();
		String event = (String) map.get("event");
		String indexID = (String) map.get("indexID");
		
		
		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
		
		if(itrxValue !=null)
		{
			
			if((itrxValue.getCollateral() instanceof OBBondsLocal ) || (itrxValue.getCollateral() instanceof OBMainIndexLocal ) || (itrxValue.getCollateral() instanceof OBOtherListedLocal ))
			{
				
			

		if ((event != null) && event.equals("cancel") ) {
			if(Integer.parseInt(indexID) >=0){
			IMarketableEquity iMarketEquity ;	
			
			IMarketableCollateral iMarket = (IMarketableCollateral) itrxValue
			.getStagingCollateral();
  if(iMarket.getEquityList().length > Integer.parseInt(indexID)  )
  {
	IMarketableEquity equityArray[] = iMarket.getEquityList();
	iMarketEquity = equityArray[Integer.parseInt(indexID)] ;	
	

	IMarketableCollateral iMarketS = (IMarketableCollateral) itrxValue
	.getStagingCollateral();
	boolean flag = false;
	for(int i = 0;i<iMarketS.getEquityList().length ;i++){
	IMarketableEquity equityArrayS[] = iMarketS.getEquityList();
	if(equityArray[Integer.parseInt(indexID)].getEquityID() == equityArrayS[i].getEquityID())
	{
	 equityArrayS[i] =iMarketEquity ;
	 flag = true;
	}
	
	}
	if(flag==false)
	{
		IMarketableCollateral iMarketS1 = (IMarketableCollateral) itrxValue
		.getStagingCollateral();
		
		IMarketableEquity equityArrayS[] = iMarketS1.getEquityList();
		//IMarketableEquity equityArrayS[] = iMarketS.getEquityList();
		if(!"cancel".equalsIgnoreCase(event)){
		for(int k = 0 ;k< iMarketS1.getEquityList().length ;k++)
		{
			if(iMarketS1.getEquityList().length == k+1)
			{
				equityArrayS[k] = null;
				break;
				
			}
			else if(k>=Integer.parseInt(indexID))
			{
				equityArrayS[k] = equityArrayS[k+1];
			}
		}
		}
		// equityArrayS[Integer.parseInt(indexID)] =null ;  
	}
  }
  else
  {	  
		IMarketableCollateral iMarketS = (IMarketableCollateral) itrxValue
		.getStagingCollateral();
		
		IMarketableEquity equityArrayS[] = iMarketS.getEquityList();
		if(!"cancel".equalsIgnoreCase(event)){
		for(int k = 0 ;k< iMarketS.getEquityList().length ;k++)
		{
			if(iMarketS.getEquityList().length == k+1)
			{
				equityArrayS[k] = null;
				break;
				
			}
			else if(k>=Integer.parseInt(indexID))
			{
				equityArrayS[k] = equityArrayS[k+1];
			}
		}
		}
		// equityArrayS[Integer.parseInt(indexID)] =null ;  
  }
  
		}
			
			}
}
			
		}
		
		if (itrxValue != null) {

			result.put("serviceColObj", itrxValue);
			String from_event = (String) map.get("from_event");
			if ((from_event != null) && from_event.equals("read") ) {
				result.put("form.collateralObject", itrxValue.getCollateral());
			} else {
				result.put("form.collateralObject", itrxValue.getStagingCollateral());
			}
			if (itrxValue.getCollateral() != null) {
				result.put("collateralID", String.valueOf(itrxValue.getCollateral().getCollateralID()));
			}
		}
		
		 //Uma Khot::Insurance Deferral maintainance
		result.put("insuranceStatusRadio", map.get("insuranceStatusRadio"));
		
		result.put(IMarketableEquityLineDetailConstants.SESSION_LINE_DETAIL_LIST, null);
		result.put(IMarketableEquityLineDetailConstants.SESSION_EQUITY_EVENT, null);
		result.put("session.PortItemObject",null);
		
		result.put("from_event", map.get("from_event"));
		result.put("subtype", map.get("subtype"));

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		return temp;
	}
}
