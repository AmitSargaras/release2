/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetpostdatedchqs/AddChequeCommand.java,v 1.2 2005/08/26 10:12:37 hshii Exp $
 */

package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateralDAO;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeDetails;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeStockDetails;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.OBGeneralChargeDetails;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.OBGeneralChargeStockDetails;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque.IAssetPostDatedCheque;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque.IPostDatedCheque;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.city.bus.OBCity;
import com.integrosys.cms.app.geography.city.proxy.ICityProxyManager;
import com.integrosys.cms.app.insurancecoverage.bus.IInsuranceCoverageDAO;
import com.integrosys.cms.app.insurancecoverage.bus.OBInsuranceCoverage;
import com.integrosys.cms.app.insurancecoverage.proxy.IInsuranceCoverageProxyManager;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.insurancecoverage.IInsuranceCoverage;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/08/26 10:12:37 $ Tag: $Name: $
 */

public class PrepareAssetsAndLiabilityInsurenceCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "form.stockDetailsObject", "java.lang.Object", FORM_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "serviceStockDetailsList",  "java.util.List", SERVICE_SCOPE },
				{ "subtype", "java.lang.String", REQUEST_SCOPE }, 
				{ "assetsMarginType", "java.lang.String", REQUEST_SCOPE }, 
				{ "assetsFixedMarginValue", "java.lang.String", REQUEST_SCOPE }, 
				
				{ "assetsMarginTypeNew", "java.lang.String", REQUEST_SCOPE }, 
				{ "assetsFixedMarginValueNew", "java.lang.String", REQUEST_SCOPE }, 
				
				{ "liabilityMarginTypeAdv", "java.lang.String", REQUEST_SCOPE }, 
				{ "liabilityFixedMarginValueAdv", "java.lang.String", REQUEST_SCOPE },
				
				{ "liabilityMarginType", "java.lang.String", REQUEST_SCOPE }, 
				{ "liabilityFixedMarginValue", "java.lang.String", REQUEST_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				
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
				{ "serviceStockDetailsList",  "java.util.List", SERVICE_SCOPE },
				{ "locationList", "java.util.List", ICommonEventConstant.REQUEST_SCOPE },
				{ "insuranceCompanyList", "java.util.List", ICommonEventConstant.REQUEST_SCOPE },
				{ "subtype", "java.lang.String", REQUEST_SCOPE },
				{ "assetsMarginType", "java.lang.String", REQUEST_SCOPE }, 
				{ "assetsFixedMarginValue", "java.lang.String", REQUEST_SCOPE }, 
				
				{ "assetsMarginTypeNew", "java.lang.String", REQUEST_SCOPE }, 
				{ "assetsFixedMarginValueNew", "java.lang.String", REQUEST_SCOPE }, 
				
				{ "liabilityMarginTypeAdv", "java.lang.String", REQUEST_SCOPE }, 
				{ "liabilityFixedMarginValueAdv", "java.lang.String", REQUEST_SCOPE },
				
				{ "liabilityMarginType", "java.lang.String", REQUEST_SCOPE }, 
				{ "liabilityFixedMarginValue", "java.lang.String", REQUEST_SCOPE }, 
				{ "form.stockDetailsObject", "java.lang.Object", FORM_SCOPE },
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
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		
		String assetsMarginType = (String) map.get("assetsMarginType");
		String assetsFixedMarginValue = (String) map.get("assetsFixedMarginValue");
		
		String assetsMarginTypeNew = (String) map.get("assetsMarginTypeNew");
		String assetsFixedMarginValueNew = (String) map.get("assetsFixedMarginValueNew");
		String liabilityMarginTypeAdv = (String) map.get("liabilityMarginTypeAdv");
		String liabilityFixedMarginValueAdv = (String) map.get("liabilityFixedMarginValueAdv");
		
		String liabilityMarginType = (String) map.get("liabilityMarginType");
		String liabilityFixedMarginValue = (String) map.get("liabilityFixedMarginValue");
		String errorCode="";
		if("FIXED".equals(assetsMarginType)){
			if (!(errorCode = Validator.checkNumber(assetsFixedMarginValue, true, 0.01, 100.00, 3,locale)).equals(Validator.ERROR_NONE)) {
				exceptionMap.put("marginErrorAsset",  new ActionMessage(
						ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0.01",
						"100.00" + ""));
			}
		}
		
		if("FIXED".equals(assetsMarginTypeNew)){
			if (!(errorCode = Validator.checkNumber(assetsFixedMarginValueNew, true, 0.01, 100.00, 3,locale)).equals(Validator.ERROR_NONE)) {
				exceptionMap.put("marginErrorAssetNew",  new ActionMessage(
						ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0.01",
						"100.00" + ""));
			}
		}
		
		
		if("FIXED".equals(liabilityMarginType)){
			if (!(errorCode = Validator.checkNumber(liabilityFixedMarginValue, true, 0.01, 100.00, 3,locale)).equals(Validator.ERROR_NONE)) {
				exceptionMap.put("marginErrorLiability",  new ActionMessage(
						ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0.01",
						"100.00" + ""));
			}
		}
		
		
		if("FIXED".equals(liabilityMarginTypeAdv)){
			if (!(errorCode = Validator.checkNumber(liabilityFixedMarginValueAdv, true, 0.01, 100.00, 3,locale)).equals(Validator.ERROR_NONE)) {
				exceptionMap.put("marginErrorLiabilityAdv",  new ActionMessage(
						ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0.01",
						"100.00" + ""));
			}
		}
		
     ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
     IGeneralChargeDetails obGeneralChargeDetails = (OBGeneralChargeDetails) map.get("form.stockDetailsObject");
		if(itrxValue !=null)
		{
			
			IGeneralChargeStockDetails[] obj = (IGeneralChargeStockDetails[])obGeneralChargeDetails.getGeneralChargeStockDetails();
		if(obj.length > 0)
			{
			IGeneralChargeStockDetails obGeneralChargeStockDetails = (OBGeneralChargeStockDetails)obj[0];
			IInsuranceCoverageDAO insuranceCoverageDAO =(IInsuranceCoverageDAO) BeanHouse.get("insuranceCoverageDAO");
			Long colId = new Long(itrxValue.getCollateral().getCollateralID());
			String compnent = obGeneralChargeStockDetails.getComponent();			
			ArrayList list = insuranceCoverageDAO.getInsuranceDetailList(colId, compnent);
			if(!list.isEmpty()){
			if (list.get(0)!= null) {
				obGeneralChargeStockDetails.setInsuranceCompanyName(list.get(0).toString());	
			}
			if (list.get(1)!= null) {
				obGeneralChargeStockDetails.setHasInsurance(list.get(1).toString());			
						}
			if (list.get(2)!= null) {
				obGeneralChargeStockDetails.setInsuranceCompanyCategory(list.get(2).toString());
			}
			if (list.get(3)!= null) {
				obGeneralChargeStockDetails.setInsuranceDescription(list.get(3).toString());
			}
			if (list.get(4)!= null) {
				obGeneralChargeStockDetails.setInsuredAmount(list.get(4).toString());	
			}	
		
		Date d = new Date();
		Date d1 = new Date();
		Date d2 = new Date();	
				if (list.get(5)!= null) {
					d= (Date) list.get(5);
					obGeneralChargeStockDetails.setEffectiveDateOfInsurance(d);
				}
				if (list.get(6)!= null) {
					d1= (Date) list.get(6);
					obGeneralChargeStockDetails.setExpiryDate(d1);
				}
				
			
            if (list.get(7)!= null) {
            	obGeneralChargeStockDetails.setTotalPolicyAmount(list.get(7).toString());
			}
			if (list.get(8)!= null) {
				obGeneralChargeStockDetails.setInsurancePolicyNo(list.get(8).toString());			
			}
			if (list.get(9)!= null) {
				obGeneralChargeStockDetails.setInsuranceCoverNote(list.get(9).toString());
			}
		if (list.get(10)!= null) {
					d2= (Date) list.get(10);
					obGeneralChargeStockDetails.setInsuranceRecivedDate(d2);
				}
		
		if (list.get(11)!= null) {
			obGeneralChargeStockDetails.setInsuranceDefaulted(list.get(11).toString());
		}
		if (list.get(12)!= null) {
			obGeneralChargeStockDetails.setInsurancePremium(list.get(12).toString());
		}
			
			IGeneralChargeStockDetails[] obj1 = new OBGeneralChargeStockDetails[1];
			obj1[0]=obGeneralChargeStockDetails;
			obGeneralChargeDetails.setGeneralChargeStockDetails(obj1);
		}
			else{				
					obGeneralChargeStockDetails.setInsuranceCompanyName(null);	
					obGeneralChargeStockDetails.setHasInsurance(null);							
					obGeneralChargeStockDetails.setInsuranceCompanyCategory(null);				
					obGeneralChargeStockDetails.setInsuranceDescription(null);				
					obGeneralChargeStockDetails.setInsuredAmount(null);	
					obGeneralChargeStockDetails.setEffectiveDateOfInsurance(null);
					obGeneralChargeStockDetails.setExpiryDate(null);
					obGeneralChargeStockDetails.setTotalPolicyAmount(null);
					obGeneralChargeStockDetails.setInsurancePolicyNo(null);	
					obGeneralChargeStockDetails.setInsuranceCoverNote(null);
					obGeneralChargeStockDetails.setInsuranceRecivedDate(null);
				    obGeneralChargeStockDetails.setInsuranceDefaulted(null);
				    obGeneralChargeStockDetails.setInsurancePremium(null);
				
				IGeneralChargeStockDetails[] obj1 = new OBGeneralChargeStockDetails[1];
				obj1[0]=obGeneralChargeStockDetails;
				obGeneralChargeDetails.setGeneralChargeStockDetails(obj1);
			}
			}
		
		}
		
		
		
		DefaultLogger.debug(this, "inside PrepareAssetsAndLiabilityCommand");
		
		
		result.put("assetsMarginType", assetsMarginType);
		result.put("assetsFixedMarginValue", assetsFixedMarginValue);
		
		result.put("assetsMarginTypeNew", assetsMarginTypeNew);
		result.put("assetsFixedMarginValueNew", assetsFixedMarginValueNew);
		
		result.put("liabilityMarginTypeAdv", liabilityMarginTypeAdv);
		result.put("liabilityFixedMarginValueAdv", liabilityFixedMarginValueAdv);
		result.put("locationList", AssetGenChargeHelper.getLocationList());

		result.put("liabilityMarginType", liabilityMarginType);
		result.put("liabilityFixedMarginValue", liabilityFixedMarginValue);
		result.put("insuranceCompanyList", getInsuranceCompanyList());
		result.put("form.stockDetailsObject", obGeneralChargeDetails);
		Object itrxValue1 = (Object) map.get("form.stockDetailsObject");
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
	
	private List getInsuranceCompanyList() {
		IInsuranceCoverageProxyManager proxy= (IInsuranceCoverageProxyManager)BeanHouse.get("insuranceCoverageProxyManager");
		SearchResult insuranceCoverageSR = proxy.getInsuranceCoverageList(null, null);

		List insuranceCoverageList=new ArrayList();
		insuranceCoverageList.addAll(insuranceCoverageSR.getResultList());
	    
	    
	    List insuranceCoverageLVBList= new ArrayList();
	    if(insuranceCoverageList!=null && insuranceCoverageList.size()>0){
	    	String label;
			String value;
	    for (Iterator iter = insuranceCoverageList.iterator(); iter.hasNext();) {
			IInsuranceCoverage coverage = (OBInsuranceCoverage) iter.next();
	    	label=coverage.getCompanyName();
			value= Long.toString(coverage.getId());
			LabelValueBean lvBean = new LabelValueBean(label,value);
			insuranceCoverageLVBList.add(lvBean);
	    }
	    }
		return insuranceCoverageLVBList;
	}
}
