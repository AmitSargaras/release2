/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.manualinput.aa;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.bus.OBLimitProfile;
import com.integrosys.cms.app.limit.bus.OBOtherCovenant;
import com.integrosys.cms.app.limit.bus.OBTradingAgreement;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue;
import com.integrosys.cms.app.manualinput.aa.proxy.SBMIAAProxy;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.limit.othercovenantsdetails.IOtherCovenantDetailsDAO;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Describe this class. Purpose: for Maker to edit the value Description:
 * command that let the maker to save the value that being edited to the
 * database
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: $
 * @since $Date:$ Tag: $Name$
 */

public class EditAADetailCommand extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "InitialLimitProfile", "com.integrosys.cms.app.limit.bus.OBLimitProfile", FORM_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ "limitProfileTrxVal", "com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "preEvent", "java.lang.String", REQUEST_SCOPE },
				{ "otherCovenantDetailsList", "java.util.List", SERVICE_SCOPE }
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
				{ "InitialLimitProfile", "com.integrosys.cms.app.limit.bus.OBLimitProfile", FORM_SCOPE },
				{ "request.ITrxResult", "com.integrosys.cms.app.transaction.ICMSTrxResult", REQUEST_SCOPE },
				{ "preEvent", "java.lang.String", REQUEST_SCOPE },
				{ "agreementNull", "java.lang.String", REQUEST_SCOPE },
				{ "countryList", "java.util.List", REQUEST_SCOPE }, { "orgList", "java.util.List", REQUEST_SCOPE },
				{ "sourceSystemList", "java.util.List", REQUEST_SCOPE },
				{ "duplicateAANo", "java.lang.String", REQUEST_SCOPE },
				{ "riskGradeList", "java.util.List", REQUEST_SCOPE },
				{ "otherCovenantDetailsList", "java.util.List", SERVICE_SCOPE },
				{ "creditAprrovalList", "java.util.List", REQUEST_SCOPE }});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		AAUIHelper helper = new AAUIHelper();
		OBLimitProfileTrxValue obj = null;
		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");
		OBLimitProfile obLimitProfile = (OBLimitProfile) map.get("InitialLimitProfile");
		OBTradingAgreement tradingAgreement = (OBTradingAgreement) obLimitProfile.getTradingAgreement();

		ILimitProfileTrxValue limitProfileTrxVal = (ILimitProfileTrxValue) map.get("limitProfileTrxVal");
		limitProfileTrxVal.setTransactionSubType(ICMSConstant.MANUAL_INPUT_TRX_TYPE);

//		DefaultLogger.debug(this, "Inside doExecute()  before PROXY CALL the busValue 'obLimitProfile' = "
//				+ obLimitProfile);
		DefaultLogger.debug(this, "Inside doExecute() trxContext  = " + trxContext);
		ICMSTrxResult res = null;

		try {
			ILimitProxy proxy = LimitProxyFactory.getProxy();
			
			if(obLimitProfile.getBCAReference()!=null && !obLimitProfile.getBCAReference().equals("")){
				limitProfileTrxVal.setLimitProfileReferenceNumber(obLimitProfile.getBCAReference());
			}	
			
			if ((tradingAgreement == null) && obLimitProfile.getAAType().equals(ICMSConstant.AA_TYPE_TRADE)) {
				resultMap.put("agreementNull", "agreementNull");
				helper.refreshListing(map, resultMap, obLimitProfile);
			}
			else {
				if(limitProfileTrxVal.getStagingLimitProfile().getExtendedNextReviewDate()!= null && !limitProfileTrxVal.getStagingLimitProfile().getExtendedNextReviewDate().equals(obLimitProfile.getExtendedNextReviewDate())){
					//obLimitProfile.setNextAnnualReviewDate(obLimitProfile.getExtendedNextReviewDate()); //Shiv 160911
					obLimitProfile.setNoOfTimesExtended(obLimitProfile.getNoOfTimesExtended()+1);
				}
				//in case cam number change (from initial to annual or Interim ) and cam date is changed count need to reset to 0
				if((!limitProfileTrxVal.getStagingLimitProfile().getBCAReference().equalsIgnoreCase(obLimitProfile.getBCAReference())) && 
						(!limitProfileTrxVal.getStagingLimitProfile().getApprovalDate().equals(obLimitProfile.getApprovalDate()))){
					obLimitProfile.setNoOfTimesExtended(0);
				}
				
				if (limitProfileTrxVal.getStatus().equals(ICMSConstant.STATE_REJECTED_CREATE)) {
					res = proxy.makerCreateLimitProfile(trxContext, limitProfileTrxVal, obLimitProfile);
				}
				else {
					res = proxy.makerUpdateLimitProfile(trxContext, limitProfileTrxVal, obLimitProfile);
				}
				obj= (OBLimitProfileTrxValue) res.getTrxValue();
				resultMap.put("request.ITrxResult", res);
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
		resultMap.put("riskGradeList",getGradeList());  //Shiv
		
		SBMIAAProxy proxy1 = helper.getSBMIAAProxy();
		try {
			//BigDecimal sanctLmt = new BigDecimal(limitProfileTrxVal.getLimitProfile().getTotalSactionedAmount());
			resultMap.put("creditAprrovalList", getCreditApproverList(proxy1.getCreditApproverList())); //shiv
			//resultMap.put("creditAprrovalList", getCreditApproverList(proxy1.getCreditApproverListWithLimit(sanctLmt)));
			
		} catch (SearchDAOException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} //shiv
		
		IOtherCovenantDetailsDAO othercovenantdetailsdaoimpl = (IOtherCovenantDetailsDAO)BeanHouse.get("otherCoveantDeatilsDAO");
		OBOtherCovenant ob;
		List otherCovenantDetailsList = (List)map.get("otherCovenantDetailsList");
		if(otherCovenantDetailsList !=null)
		{	
			for(int i=0;i<otherCovenantDetailsList.size();i++) 
			{	
				
				ob=(OBOtherCovenant)otherCovenantDetailsList.get(i);
				
				/*if(ob.getStatus().equalsIgnoreCase("INACTIVE"))
				{*/
					ob.setOtherCovenantId(Long.parseLong(othercovenantdetailsdaoimpl.getOtherCovenantDetailsStagingIdFromSeq()));
				/*}*/
				//othercovenantdetailsdaoimpl.deleteOtherCovenantStagingValues(ob.getPreviousStagingId());
				String monResp=ob.getMonitoringResponsibilityList1();
				//List OtherCovenantDetailsValuesStaging = othercovenantdetailsdaoimpl.getOtherCovenantDetailsValuesStaging(ob.getPreviousStagingId());
				if(null != monResp)
				{
					/*for(int u=0;u<OtherCovenantDetailsValuesStaging.size();u++)
					{
						OBOtherCovenant ob2 = new OBOtherCovenant();
						ob2= (OBOtherCovenant) OtherCovenantDetailsValuesStaging.get(u);
						if(ob2.getMonitoringResponsibiltyValue() != null)
						{
						ob2.setStatus("INACTIVE");
						othercovenantdetailsdaoimpl.updateOtherCovenantDetailsStaging(ob2);
						}
					}*/
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
					ob1.setPreviousStagingId(ob.getPreviousStagingId());
					ob1.setCustRef(obLimitProfile.getLEReference());
					if(ob.getStatus().equalsIgnoreCase("INACTIVE"))
					{	
						ob1.setStatus("INACTIVE");
					}
					else
					{
						ob1.setStatus("ACTIVE");
					}
					//othercovenantdetailsdaoimpl.deleteOtherCovenantStagingValues(ob.getPreviousStagingId());
						if(ob1.getMonitoringResponsibiltyValue() != null && !ob1.getMonitoringResponsibiltyValue().equals("") )
					othercovenantdetailsdaoimpl.insertStageOtherCovenantDetailsValues(ob1);
				}
				}
				String facilityName=ob.getFinalfaciltyName();
				if(null != facilityName)
				{
					/*for(int u=0;u<OtherCovenantDetailsValuesStaging.size();u++)
					{
						OBOtherCovenant ob2 = new OBOtherCovenant();
						ob2= (OBOtherCovenant) OtherCovenantDetailsValuesStaging.get(u);
						if(ob2.getFacilityNameValue() != null)
						{
						ob2.setStatus("INACTIVE");
						othercovenantdetailsdaoimpl.updateOtherCovenantDetailsStaging(ob2);
						}
					}*/
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
					ob1.setCustRef(obLimitProfile.getLEReference());
					ob1.setPreviousStagingId(ob.getPreviousStagingId());
					if(ob.getStatus().equalsIgnoreCase("INACTIVE"))
					{	
						ob1.setStatus("INACTIVE");
					}
					else
					{
						ob1.setStatus("ACTIVE");
					}
					//othercovenantdetailsdaoimpl.deleteOtherCovenantStagingValues(ob.getPreviousStagingId());
					//if(ob1.getFacilityNameValue() != null || ob1.getMonitoringResponsibiltyValue() != null)
						if(ob1.getFacilityNameValue() != null && !ob1.getFacilityNameValue().equals(""))
					othercovenantdetailsdaoimpl.insertStageOtherCovenantDetailsValues(ob1);;
				}
				}
			
				try
				{
					if(null != ob.getStagingRefid())
					{
						ob.setIsUpdate("Y");
					}
					else
					{
						ob.setIsUpdate("N");
					}
					ob.setCustRef(obLimitProfile.getLEReference());
					ob.setStagingRefid(obj.getStagingReferenceID());
				
				othercovenantdetailsdaoimpl.insertOtherCovenantDetailsStage(ob);
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

	//Shiv
	private List getCreditApproverList(List lst) {
		List lbValList = new ArrayList();
	try {
			
		for (int i = 0; i < lst.size(); i++) {
			String [] creditApprover = (String[])lst.get(i);
				String val = creditApprover[0];
				String name = creditApprover[1];
				if(creditApprover[2].equals("Y")){
					name = name+ " (Senior)";
				}
				LabelValueBean lvBean = new LabelValueBean(name,val );
				lbValList.add(lvBean);
		}
	} catch (Exception ex) {
	}
	return CommonUtil.sortDropdown(lbValList);
}

	//Shiv
	private List getGradeList() {
		List lbValList = new ArrayList();
		List values = new ArrayList();
		TreeSet ts = new TreeSet();
		try {
		values.addAll(CommonDataSingleton.getCodeCategoryLabels(CategoryCodeConstant.CommonCode_RISK_GRADE));
		
		for (int i = 0; i < values.size(); i++) {
			ts.add(new Integer(values.get(i).toString()));
		}
		Iterator itr = ts.iterator();
		
		while (itr.hasNext()) {
				String val = itr.next().toString();
				LabelValueBean lvBean = new LabelValueBean(val,val );
				lbValList.add(lvBean);
		}
	} catch (Exception ex) {
	}
	return lbValList;
}
	
}
