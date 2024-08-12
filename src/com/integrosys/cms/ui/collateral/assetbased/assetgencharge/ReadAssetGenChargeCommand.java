/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/ReadAssetGenChargeCommand.java,v 1.2 2005/04/19 12:09:45 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import static com.integrosys.cms.ui.collateral.CollateralConstant.PARENT_PAGE;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SESSION_ASSET_GC_DRAWING_POWER_MSG;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SESSION_ASSET_GC_INSURED_AMT_WARNING;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SESSION_DUE_DATE_AND_STOCK_DETAILS;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SESSION_PARENT_PAGE;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SESSION_TOTAL_INSURANCE_POLICY_AMT;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;	

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IDueDateAndStockBO;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeDetails;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeStockDetails;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.MapperUtil;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.ui.collateral.CollateralHelper;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/04/19 12:09:45 $ Tag: $Name: $
 */
public class ReadAssetGenChargeCommand extends AbstractCommand {
	
	private IDueDateAndStockBO dueDateAndStockBO = (IDueDateAndStockBO) BeanHouse.get("dueDateAndStockBO");;
	
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "from_page", "java.lang.String", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE },
				{ "dpCalculateManually", "java.lang.String", REQUEST_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "typeOfCharge", "java.lang.String", REQUEST_SCOPE },
				{ "bankingMethod", "java.lang.String", REQUEST_SCOPE },
				{ "insuranceStatusRadio", "java.lang.String", REQUEST_SCOPE },
				{ "insuranceList", List.class.getName() , SERVICE_SCOPE },
				{ SESSION_DUE_DATE_AND_STOCK_DETAILS, IGeneralChargeDetails.class.getName(), SERVICE_SCOPE },
				{ SESSION_PARENT_PAGE, String.class.getName(), SERVICE_SCOPE },
				{ "remarkByMaker", "java.lang.String", REQUEST_SCOPE },
				{ "termLoanOutstdAmt", "java.lang.String", REQUEST_SCOPE },
				{ "marginAssetCover", "java.lang.String", REQUEST_SCOPE },
				{ "recvGivenByClient", "java.lang.String", REQUEST_SCOPE },
				
				{ "totalLonable", "java.lang.String", REQUEST_SCOPE },
				{ "migrationFlag", "java.lang.String", REQUEST_SCOPE },
				{ "hdnPreviousDueDate", "java.lang.String", REQUEST_SCOPE },
				
				{ "alertFlg", "java.lang.String", REQUEST_SCOPE },
				
				});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { 
				{ "from_page", "java.lang.String", SERVICE_SCOPE },
				{ "tab", "java.lang.String", SERVICE_SCOPE },
				{ "isEditable", "java.lang.String", SERVICE_SCOPE },
				{ "releasableAmount", "java.math.BigDecimal", SERVICE_SCOPE },
				{ "calculatedDP", "java.lang.String", SERVICE_SCOPE },
				{ "remarks", "java.lang.String", REQUEST_SCOPE },
				{ "dueDate", "java.lang.String", SERVICE_SCOPE },
				{ "displayList",  "java.util.List", SERVICE_SCOPE },
				{ "dueDateList",  "java.util.List", SERVICE_SCOPE},
				{ "filterLocationList",  "java.util.List", SERVICE_SCOPE },
				{ "statementName",  "java.util.List", SERVICE_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE },
				{ "alertRequired",  "java.util.List", SERVICE_SCOPE },
				{ "frequencyList", "java.util.List", REQUEST_SCOPE },
				{ "isStockDetailsAdded",  "java.util.List", SERVICE_SCOPE },
				{ "dpShare", "java.lang.String", SERVICE_SCOPE },
				{ "insuranceStatusRadio", "java.lang.String", SERVICE_SCOPE },
				{ "serviceColObj", ICollateralTrxValue.class.getName(), SERVICE_SCOPE },
				{ PARENT_PAGE, String.class.getName(), REQUEST_SCOPE },
				{ SESSION_ASSET_GC_INSURED_AMT_WARNING, String.class.getName(), SERVICE_SCOPE },
				{ SESSION_ASSET_GC_DRAWING_POWER_MSG, String.class.getName(), SERVICE_SCOPE },
				{ SESSION_TOTAL_INSURANCE_POLICY_AMT, String.class.getName() , SERVICE_SCOPE },
				
				{ "dpCalculateManually", "java.lang.String", SERVICE_SCOPE },
				{ "loanable", "java.lang.String", REQUEST_SCOPE },
				{ "finalYearList",  "java.util.List", SERVICE_SCOPE },
				{ "finalMonthList",  "java.util.List", SERVICE_SCOPE },
				{ "stockdocMonth", "java.lang.String", SERVICE_SCOPE },
				{ "stockdocYear", "java.lang.String", SERVICE_SCOPE },
				{ "remarkByMaker", "java.lang.String", SERVICE_SCOPE },
				{ "totalLonable", "java.lang.String", SERVICE_SCOPE },
				{ "migrationFlag", "java.lang.String", SERVICE_SCOPE },
				{ "hdnPreviousDueDate", "java.lang.String", SERVICE_SCOPE },
				
				{ "alertFlg", "java.lang.String", SERVICE_SCOPE },
				{ "totalLonable", "java.lang.String", REQUEST_SCOPE },
			
				});
	}

	@SuppressWarnings("unchecked")
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
	

		String event = (String) map.get("event");
		String typeOfCharge = (String) map.get("typeOfCharge");
		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
		List<OBInsuranceGC> insuranceList=(List)map.get("insuranceList");
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		
		IGeneralCharge stagingCollateral = (IGeneralCharge) itrxValue.getStagingCollateral();
		IGeneralChargeDetails[] stocksAndDueDateDetails = stagingCollateral.getGeneralChargeDetails();
		
		ICollateralProxy collateralProxy = CollateralProxyFactory.getProxy();
		String migratedFlagFromStaging = "";
		/*String termLoanOutstdAmt =(String) map.get("termLoanOutstdAmt");
		String marginAssetCover =(String) map.get("marginAssetCover");
		String recvGivenByClient =(String) map.get("recvGivenByClient");*/
		String totalLonable =(String) map.get("totalLonable");
		if(null==totalLonable || "".equals(totalLonable)) {
			totalLonable="0";
		}
		
		String alertFlg =(String) map.get("alertFlg");
		System.out.println("totalLonable in ReadAssetGenChargeCommand==="+totalLonable);
		if(typeOfCharge!= null && !"".equals(typeOfCharge)){
			itrxValue.getCollateral().setTypeOfCharge(typeOfCharge);
		}
		
		if ( event!=null && !event.endsWith("return")) {
			result.put("from_page", (String) map.get("event"));
		}
		else {
			DefaultLogger.debug(this, "<<<<<<<<<<< readAssetGenChargeCommand - event: " + event);
			ITrxContext ctx = itrxValue.getTrxContext();
            if(ctx!=null) {
			    DefaultLogger.debug(this, "<<<<<<<<<<< readAssetGenChargeCommand - remarks: " + ctx.getRemarks());
			    result.put("remarks", ctx.getRemarks());
            }
		}
		
	    IGeneralCharge newCollateral = (IGeneralCharge) itrxValue.getStagingCollateral();
	    
	    BigDecimal sumInsuredAmt = null;
	    if(insuranceList != null) {
	    	sumInsuredAmt = BigDecimal.ZERO;
	    	for(OBInsuranceGC insGc : insuranceList) {
	    		if(StringUtils.isNotBlank(insGc.getInsuredAmount())) {
	    			BigDecimal insuredAmt = MapperUtil.stringToBigDecimal(insGc.getInsuredAmount()); 
	    			sumInsuredAmt = sumInsuredAmt.add(insuredAmt);
	    		}
	    	}
	    }
	    
	    BigDecimal totalReleasedAmount = CollateralDAOFactory.getDAO()
				.getTotalLimitReleasedAmtForLinkedFacilities(Long.valueOf(itrxValue.getReferenceID()));
	    boolean isErrorDpLessThanReleasedAmt = Boolean.FALSE;
	    
	    System.out.println("ReadAssetGenChargeCommand.java=> Going to check Released amount and dp amount as per stock.=>itrxValue.getReferenceID()=>"+itrxValue.getReferenceID()+"***totalReleasedAmount=>"+totalReleasedAmount);
	    
	    boolean isWarningInsuredAmtLessThanDp = Boolean.FALSE;
	    if(!ArrayUtils.isEmpty(stocksAndDueDateDetails)) {
	    	Arrays.sort(stocksAndDueDateDetails, new Comparator<IGeneralChargeDetails>() {
	    		public int compare(IGeneralChargeDetails o1, IGeneralChargeDetails o2) {
	    			return (o1.getDueDate() != null && o2.getDueDate()!= null)? o1.getDueDate().compareTo(o2.getDueDate()):0;
	    		}
	    	});
	    	
	    	IGeneralChargeDetails recentGeneralChargeDetail = stocksAndDueDateDetails[stocksAndDueDateDetails.length-1];
	    	BigDecimal dpValue = recentGeneralChargeDetail.getActualDp();
	    	if(dpValue !=null && sumInsuredAmt !=null) {
	    		isWarningInsuredAmtLessThanDp = dpValue.compareTo(sumInsuredAmt)>0;
	    	}
	    	
	    	String dpAsPerStockStr = UIUtil.removeComma(recentGeneralChargeDetail.getCalculatedDP());
			BigDecimal dpAsPerStock = MapperUtil.stringToBigDecimal(dpAsPerStockStr);
			System.out.println("totalReleasedAmount=>"+totalReleasedAmount+"****dpAsPerStock=>"+dpAsPerStock+"**recentGeneralChargeDetail.getDueDate()=>"+recentGeneralChargeDetail.getDueDate());
	    	if(null != totalReleasedAmount && null != dpAsPerStock) {
	    		if(totalReleasedAmount.compareTo(dpAsPerStock) >0) {
				isErrorDpLessThanReleasedAmt = Boolean.TRUE;
				System.out.println("ReadAssetGenChargeCommand.java=>totalReleasedAmount=>"+totalReleasedAmount+"****dpAsPerStock=>"+dpAsPerStock+"**isErrorDpLessThanReleasedAmt=>"+isErrorDpLessThanReleasedAmt);
	    }}
	    }
	   
	    IGeneralChargeDetails[] generalChargeDetails = newCollateral.getGeneralChargeDetails();
	  /*  BigDecimal totalReleasedAmount = CollateralDAOFactory.getDAO()
				.getTotalLimitReleasedAmtForLinkedFacilities(Long.valueOf(newCollateral.getCollateralID()));*/
	    
	    /*BigDecimal totalReleasedAmount = CollateralDAOFactory.getDAO()
				.getTotalLimitReleasedAmtForLinkedFacilities(Long.valueOf(itrxValue.getReferenceID()));*/
	    
	   /* if(!ArrayUtils.isEmpty(generalChargeDetails)) {
	    	for(IGeneralChargeDetails genChargeDetail : generalChargeDetails) {
				String dpAsPerStockStr = UIUtil.removeComma(genChargeDetail.getCalculatedDP());
				BigDecimal dpAsPerStock = MapperUtil.stringToBigDecimal(dpAsPerStockStr);
			//	dpAsPerStock = BigDecimal.ZERO;
				System.out.println("totalReleasedAmount=>"+totalReleasedAmount+"****dpAsPerStock=>"+dpAsPerStock);
				if(totalReleasedAmount != null && totalReleasedAmount.compareTo(dpAsPerStock) >0) {
					isErrorDpLessThanReleasedAmt = Boolean.TRUE;
					System.out.println("ReadAssetGenChargeCommand.java=>totalReleasedAmount=>"+totalReleasedAmount+"****dpAsPerStock=>"+dpAsPerStock+"**isErrorDpLessThanReleasedAmt=>"+isErrorDpLessThanReleasedAmt);
					break;
				}
	    	}
	    }*/
	    System.out.println("ReadAssetGenChargeCommand.java=>");
		Long stageCollateralId = Long.parseLong(itrxValue.getStagingReferenceID());
		System.out.println("ReadAssetGenChargeCommand.java=>stageCollateralId=>"+stageCollateralId);
		Map<String, String> statements = dueDateAndStockBO.getStatementNames(stageCollateralId);
		newCollateral.setDueDateAndStockStatements(statements);
		
		List stockDetailsList = new ArrayList();

		IGeneralChargeDetails existingChargeDetails;
		IGeneralChargeDetails sessionDueDateAndStocks = (IGeneralChargeDetails) map.get(SESSION_DUE_DATE_AND_STOCK_DETAILS);
		IGeneralChargeStockDetails[] existingGeneralChargeStockDetails;
		existingChargeDetails = sessionDueDateAndStocks;

		if (null != existingChargeDetails && null != existingChargeDetails.getGeneralChargeStockDetails()) {
			existingGeneralChargeStockDetails = existingChargeDetails.getGeneralChargeStockDetails();
			for (int j = 0; j < existingGeneralChargeStockDetails.length; j++) {
				IGeneralChargeStockDetails existingStockDetails = existingGeneralChargeStockDetails[j];
				stockDetailsList.add(existingStockDetails);
			}
		}


		if(null != stocksAndDueDateDetails) {
		for (IGeneralChargeDetails chargeDetail : stocksAndDueDateDetails) {
			if (null != chargeDetail && null != chargeDetail.getMigrationFlag_DP_CR()) {
				String migrationFlag = chargeDetail.getMigrationFlag_DP_CR();

				System.out.println("migrationFlag_DP_CR: " + migrationFlag);

				if (null != migrationFlag && "Y".equalsIgnoreCase(migrationFlag.trim())) {

					BigDecimal totalLonableAsset = new BigDecimal(0);
					BigDecimal totalLonableLiability = new BigDecimal(0);
					
					if(null != stockDetailsList) {
					for (int j = 0; j < stockDetailsList.size(); j++) {

						IGeneralChargeStockDetails serviceStockDetailsList = (IGeneralChargeStockDetails) stockDetailsList.get(j);

						if (("CurrentAsset".equals(serviceStockDetailsList.getStockType())
								|| "ValueDebtors".equals(serviceStockDetailsList.getStockType()))
								&& ("YES".equals(serviceStockDetailsList.getApplicableForDp()))) {
							totalLonableAsset = totalLonableAsset.add(new BigDecimal(serviceStockDetailsList.getLonable()));
						} else if (("CurrentLiabilities".equals(serviceStockDetailsList.getStockType())
								|| "LessAdvances".equals(serviceStockDetailsList.getStockType()))
								&& ("YES".equals(serviceStockDetailsList.getApplicableForDp()))) {
							totalLonableLiability = totalLonableLiability.add(new BigDecimal(serviceStockDetailsList.getLonable()));
						}
					}}
					totalLonable = (totalLonableAsset.subtract(totalLonableLiability)).toString();
					result.put("totalLonable", totalLonable);
				} else {
					result.put("totalLonable", totalLonable);
				}

			}
		}}
		
		result.put("alertFlg", alertFlg);
		
		result.put("totalLonable", totalLonable);
		result.put(PARENT_PAGE, map.get(SESSION_PARENT_PAGE));
		result.put("serviceColObj", itrxValue);
		result.put(SESSION_ASSET_GC_INSURED_AMT_WARNING, isWarningInsuredAmtLessThanDp?ICMSConstant.YES:ICMSConstant.NO);
		result.put(SESSION_ASSET_GC_DRAWING_POWER_MSG, isErrorDpLessThanReleasedAmt?ICMSConstant.YES:ICMSConstant.NO);
		result.put(SESSION_TOTAL_INSURANCE_POLICY_AMT,CollateralHelper.getTotalInsurancePolicyAmount(stagingCollateral,insuranceList));
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
	
}
