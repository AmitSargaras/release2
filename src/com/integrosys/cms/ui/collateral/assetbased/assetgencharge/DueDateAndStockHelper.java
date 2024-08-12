package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import static com.integrosys.cms.ui.collateral.CollateralConstant.STOCK_DOC_MONTH_LIST;
import static com.integrosys.cms.ui.collateral.CollateralConstant.STOCK_DOC_YEAR_LIST;
import static com.integrosys.cms.ui.collateral.assetbased.assetgencharge.AssetGenChargeAction.EVENT_ADD_DUE_DATE_AND_STOCK;
import static com.integrosys.cms.ui.collateral.assetbased.assetgencharge.AssetGenChargeAction.EVENT_EDIT_DUE_DATE_AND_STOCK;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeDetails;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeStockDetails;
import com.integrosys.cms.businfra.LabelValue;

public class DueDateAndStockHelper {

	public static enum LeadBankStockBankingArrangement {
		
		CONSORTIUM, MULTIPLE;

		public static boolean contains(String input) {
			return StringUtils.isNotEmpty(input) && CONSORTIUM.name().equals(input) || MULTIPLE.name().equals(input);
		}
	}
    
	public static void loadStockDocMonthsandYears(String dueDateStr, HashMap<String, Object> result) {
		String[] splittArray = null;
		String[] splittString = null;
		String selectedDueDate = "";
		String selectedMonth = "";
		String selectedYear = "";
	    List<LabelValue> stockDocMonthList = new ArrayList<LabelValue>();
	    List<LabelValue> stockDocYearList = new ArrayList<LabelValue>();
		boolean flag = false;
		boolean flagCondition = false;

		if (null != dueDateStr && !"".equalsIgnoreCase(dueDateStr) && !"null".equalsIgnoreCase(dueDateStr)) {
			splittArray = dueDateStr.split(",");
			selectedDueDate = (String) splittArray[0];
		}

		if (null != selectedDueDate && !"".equalsIgnoreCase(selectedDueDate)
				&& !"null".equalsIgnoreCase(selectedDueDate)) {
			splittString = selectedDueDate.split("/");
			if (splittString.length == 3) {
				selectedMonth = (String) splittString[1];
				selectedYear = (String) splittString[2];
				flagCondition = true;
			}
		}
		List<String> months = Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov",
				"Dec");

		if (flagCondition == true) {
			int index = months.indexOf(selectedMonth);
			DefaultLogger.debug(DueDateAndStockHelper.class,"Selected months index=>" + index);
			int checkIndex = index + 6;
			int cnt = 0;
			
			if (checkIndex > 11) {
				cnt = checkIndex - 11;
				cnt = cnt - 1;
			} else {
				cnt = checkIndex;
			}
			
			for (int i = 0; i < 6; i++) {

				String month = months.get(cnt);
				stockDocMonthList.add(new LabelValue(month, month));
				cnt++;
				if (cnt == 12) {
					cnt = 0;
					flag = true;
				}
			}

			int year = Integer.parseInt(selectedYear);
			if (index == 0) {
				year = year - 1;
				String yearStr = String.valueOf(year);
				stockDocYearList.add(new LabelValue(yearStr, yearStr));
			} else if (flag == true) {
				String yearStr = String.valueOf(year);
				stockDocYearList.add(new LabelValue(yearStr, yearStr));
				year = year - 1;
				yearStr = String.valueOf(year);
				stockDocYearList.add(new LabelValue(yearStr, yearStr));
			} else {
				stockDocYearList.add(new LabelValue(selectedYear, selectedYear));
			}
		}

		result.put(STOCK_DOC_MONTH_LIST, stockDocMonthList);
		result.put(STOCK_DOC_YEAR_LIST, stockDocYearList);
	}	
	
	public static void setStockDetailsForSelectedLocation(IGeneralChargeDetails chargeDetail, IGeneralCharge stagingCol) {
		List<IGeneralChargeStockDetails> stockDetailsList = new ArrayList<IGeneralChargeStockDetails>();
		BigDecimal totalLoanableAsset = BigDecimal.ZERO;
		BigDecimal totalLoanableLiability = BigDecimal.ZERO;
		if(chargeDetail.getLocation()!=null && stagingCol!=null) {
			IGeneralChargeDetails[] chargeDetailArr= stagingCol.getGeneralChargeDetails();
			if(chargeDetailArr != null) {
				for(IGeneralChargeDetails gcDetails : chargeDetailArr) {
					if(gcDetails.getDocCode() != null && gcDetails.getDocCode().equals(chargeDetail.getDocCode())) {
						if(!ArrayUtils.isEmpty(gcDetails.getGeneralChargeStockDetails())) {
							for(IGeneralChargeStockDetails stockDetails : gcDetails.getGeneralChargeStockDetails()) {
								
								stockDetailsList.add(stockDetails);
								
								if("CurrentAsset".equals(stockDetails.getStockType()) && ("YES".equals(stockDetails.getApplicableForDp()))){
									totalLoanableAsset=totalLoanableAsset.add(new BigDecimal(stockDetails.getLonable()));
								}else if("CurrentLiabilities".equals(stockDetails.getStockType()) && ("YES".equals(stockDetails.getApplicableForDp()))){
									totalLoanableLiability=totalLoanableLiability.add(new BigDecimal(stockDetails.getLonable()));
								}
							}
						}
					}	
				}
			}
		}
		IGeneralChargeStockDetails[] stockDeatilsArr = stockDetailsList.toArray(new IGeneralChargeStockDetails[stockDetailsList.size()]);
		chargeDetail.setGeneralChargeStockDetails(stockDeatilsArr);
		chargeDetail.setTotalLoanableAmount(totalLoanableAsset.subtract(totalLoanableLiability));
		
		if(StringUtils.isBlank(chargeDetail.getCalculatedDP())) {
			if(StringUtils.isNotBlank(chargeDetail.getDpShare()) && chargeDetail.getTotalLoanableAmount()!=null) {
				Integer dpShare = Integer.valueOf(chargeDetail.getDpShare()) / 100;			
				BigDecimal calculatedDp = chargeDetail.getTotalLoanableAmount().multiply(new BigDecimal(dpShare)); 
				chargeDetail.setCalculatedDP(calculatedDp.toString());
			}
		}
	}
	
	public static Boolean isSecurityViewMode(String event) {
		if (EVENT_ADD_DUE_DATE_AND_STOCK.equals(event) || EVENT_EDIT_DUE_DATE_AND_STOCK.equals(event)) {
			return false;
		}
		return true;
	}
	
	public static void calculateStockDetailsLoanable(IGeneralChargeDetails[] chargeDetailsArray, IGeneralChargeDetails currentChargeDetails) {
		if (chargeDetailsArray != null) {
			for (int i = 0; i < chargeDetailsArray.length; i++) {
				IGeneralChargeDetails chargeDetails = chargeDetailsArray[i];
				if (chargeDetails != null && chargeDetails.getDocCode() != null && chargeDetails.getDocCode().equals(currentChargeDetails.getDocCode())) {
					IGeneralChargeStockDetails[] stockDetails = chargeDetails.getGeneralChargeStockDetails();
					if (stockDetails != null) {
						for (int j = 0; j < stockDetails.length; j++) {
							IGeneralChargeStockDetails existingStockDetails = stockDetails[j];

							double componentAmount = Double.parseDouble(existingStockDetails.getComponentAmount());
							double margin = Double.parseDouble(existingStockDetails.getMargin());
							double loanable = 0.00;
							loanable = componentAmount - ((componentAmount * margin) / 100);
							DecimalFormat df = new DecimalFormat("##0.00");
							df.format(loanable);
							existingStockDetails.setLonable(BigDecimal.valueOf(loanable).toPlainString());
						}
					}
					chargeDetails.setStatus(IGeneralChargeDetails.STATUS_PENDING);
					break;
				}

			}
		}
	}

	public static String getIsLinePendingErrorMsg(Long collateralId) {
		
		if(collateralId == null)
			return null;
		
		
		Map<String, String> facilitiesMap = CollateralDAOFactory.getDAO()
				.getNonApprovedLinkedFacilities(collateralId);
		
		if(facilitiesMap.isEmpty())
			return null;
		
		StringBuffer errorMsg = new StringBuffer() 
				.append("Below facility lines are pending for checker authorization. : ");
		
		for(Map.Entry<String, String> entry : facilitiesMap.entrySet() ) {
			errorMsg.append(entry.getValue())
				.append(" / ")
				.append(entry.getKey())
				.append(" ");
		}
		return String.valueOf(errorMsg);
	}
	
}
