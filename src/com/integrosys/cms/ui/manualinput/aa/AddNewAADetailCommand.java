/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.manualinput.aa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.OBBankingMethod;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.bus.OBLimitProfile;
import com.integrosys.cms.app.limit.bus.OBOtherCovenant;
import com.integrosys.cms.app.limit.bus.OBTradingAgreement;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.limit.othercovenantsdetails.IOtherCovenantDetailsDAO;

/**
 * Describe this class. Purpose: for maker to add new AA Description: command
 * that let the maker to add new AA Value to the database
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date:$ Tag: $Name$
 */

public class AddNewAADetailCommand extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Defines an two dimensional array with 	the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "InitialLimitProfile", "com.integrosys.cms.app.limit.bus.OBLimitProfile", FORM_SCOPE },
				{ "limitProfileTrxVal", "com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue", SERVICE_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ "otherCovenantDetailsList", "java.util.List", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "preEvent", "java.lang.String", REQUEST_SCOPE }, });
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
				{ "InitialLimitProfile", "com.integrosys.cms.app.limit.bus.OBLimitProfile", FORM_SCOPE },
				{ "request.ITrxResult", "com.integrosys.cms.app.transaction.ICMSTrxResult", REQUEST_SCOPE },
				{ "preEvent", "java.lang.String", REQUEST_SCOPE }, { "countryList", "java.util.List", REQUEST_SCOPE },
				{ "otherCovenantDetailsList", "java.util.List", REQUEST_SCOPE },
				{ "otherCovenantDetailsList", "java.util.List", SERVICE_SCOPE },
				{ "orgList", "java.util.List", REQUEST_SCOPE },
				{ "sourceSystemList", "java.util.List", REQUEST_SCOPE },
				{ "agreementNull", "java.lang.String", REQUEST_SCOPE },
				{ "duplicateAANo", "java.lang.String", REQUEST_SCOPE } });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		OBLimitProfileTrxValue obj = null;
		
		HashMap resultMap = new HashMap();
		AAUIHelper helper = new AAUIHelper();
		List otherCovenantDetailsList;
		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");
		OBLimitProfile obLimitProfile = (OBLimitProfile) map.get("InitialLimitProfile");
		OBTradingAgreement tradingAgreement = (OBTradingAgreement) obLimitProfile.getTradingAgreement();
		ILimitProfileTrxValue limitProfileTrxVal = (ILimitProfileTrxValue) map.get("limitProfileTrxVal");
		
		// todo for --> Move to transaction Creation
		if (limitProfileTrxVal != null) {
			limitProfileTrxVal.setTransactionSubType(ICMSConstant.MANUAL_INPUT_TRX_TYPE);
			System.out.println("Staging Reference id-------------------"+limitProfileTrxVal.getStagingReferenceID());
		}

		DefaultLogger.debug(this, "Inside doExecute()  before PROXY CALL the busValue 'obLimitProfile' = "
				+ obLimitProfile);
		DefaultLogger.debug(this, "Inside doExecute()  before PROXY CALL the busValue 'limitProfileTrxVal' = "
				+ limitProfileTrxVal);
		
		try {
			ILimitProxy proxy = LimitProxyFactory.getProxy();

			if ((tradingAgreement == null) && obLimitProfile.getAAType().equals(ICMSConstant.AA_TYPE_TRADE)) {
				resultMap.put("agreementNull", "agreementNull");
				helper.refreshListing(map, resultMap, obLimitProfile);
			}
			else {
				ICMSTrxResult res = proxy.makerCreateLimitProfile(trxContext, limitProfileTrxVal, obLimitProfile);
				resultMap.put("request.ITrxResult", res);
				obj= (OBLimitProfileTrxValue) res.getTrxValue();
				System.out.print(obj.getStagingReferenceID());
			}

		}
		catch (LimitException e) {
			if ((e.getErrorCode() != null) && e.getErrorCode().equals(LimitException.ERR_DUPLICATE_AA_NUM)) {
				resultMap.put("duplicateAANo", "duplicateAANo");
				helper.refreshListing(map, resultMap, obLimitProfile);
			}
			else {

				DefaultLogger.debug(this, "got exception in doExecute" + e);
				e.printStackTrace();
				throw (new CommandProcessingException(e.getMessage()));
			}
		}
		IOtherCovenantDetailsDAO othercovenantdetailsdaoimpl = (IOtherCovenantDetailsDAO)BeanHouse.get("otherCoveantDeatilsDAO");
		OBOtherCovenant ob;
		
		
		otherCovenantDetailsList = (List)map.get("otherCovenantDetailsList");
		
		if(otherCovenantDetailsList !=null)
		{	
			for(int i=0;i<otherCovenantDetailsList.size();i++) 
			{	
				
				ob=(OBOtherCovenant)otherCovenantDetailsList.get(i);
				try
				{
				ob.setCustRef(obLimitProfile.getLEReference());
				ob.setStagingRefid(obj.getStagingReferenceID());
				ob.setIsUpdate("N");
				ob.setStatus("ACTIVE");
				othercovenantdetailsdaoimpl.insertOtherCovenantDetailsStage(ob);
				String monResp=ob.getMonitoringResponsibilityList1();
				String[] MRArr = monResp.split(",");
				ArrayList monRespList = new ArrayList();
				for(int m=0; m<MRArr.length;m++) {
					String[] MRArr1 =MRArr[m].split("-");
					monRespList.add(MRArr1[0]);
				}
				for(int j=0;j<monRespList.size();j++) {
					OBOtherCovenant ob1 = new OBOtherCovenant();
					ob1.setMonitoringResponsibiltyValue((String)monRespList.get(j));
					ob1.setStagingRefid(ob.getOtherCovenantId()+"");
					ob1.setCustRef(obLimitProfile.getLEReference());
					ob1.setPreviousStagingId(ob.getPreviousStagingId());
					ob1.setStatus("ACTIVE");
					if(ob1.getMonitoringResponsibiltyValue() != null && !ob1.getMonitoringResponsibiltyValue().equals("") )
					othercovenantdetailsdaoimpl.insertStageOtherCovenantDetailsValues(ob1);;
				}
				String facilityName=ob.getFinalfaciltyName();
				String[] FNLArr = facilityName.split(",");
				ArrayList facNameList = new ArrayList();
				for(int p=0; p<FNLArr.length;p++) {
					String[] FNLArr1 =FNLArr[p].split("-");
					facNameList.add(FNLArr1[0]);
				}
				for(int n=0;n<facNameList.size();n++) {
					OBOtherCovenant ob1 = new OBOtherCovenant();
					ob1.setFacilityNameValue((String)facNameList.get(n));
					ob1.setStagingRefid(ob.getOtherCovenantId()+"");
					ob1.setPreviousStagingId(ob.getPreviousStagingId());
					ob1.setStatus("ACTIVE");
					ob1.setCustRef(obLimitProfile.getLEReference());
					if(ob1.getFacilityNameValue() != null && !ob1.getFacilityNameValue().equals(""))
					othercovenantdetailsdaoimpl.insertStageOtherCovenantDetailsValues(ob1);
				}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
					
		}
		}
		DefaultLogger.debug(this, "Skipping ...");
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
