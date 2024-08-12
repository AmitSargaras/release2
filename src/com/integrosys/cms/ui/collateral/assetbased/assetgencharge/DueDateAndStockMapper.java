package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import static com.integrosys.cms.ui.collateral.CollateralConstant.ACTUAL_DUE_DATE_AND_STOCK_DETAILS_KEY;
import static com.integrosys.cms.ui.collateral.CollateralConstant.DP_SHARE;
import static com.integrosys.cms.ui.collateral.CollateralConstant.DUE_DATE_AND_STOCK_DETAILS_KEY;
import static com.integrosys.cms.ui.collateral.CollateralConstant.DUE_DATE_MAP;
import static com.integrosys.cms.ui.collateral.CollateralConstant.GC_DETAIL_ID;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SERVICE_COLLATERAL_OBJ;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SESSION_DUE_DATE_AND_STOCK_DETAILS;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SESSION_LOCATION_LIST;
import static com.integrosys.cms.ui.common.constant.IGlobalConstant.SELECTED_INDEX;
import static com.integrosys.cms.ui.common.constant.IGlobalConstant.SUB_EVENT;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.LabelValueBean;
import org.springframework.util.CollectionUtils;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeDetails;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeStockDetails;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.OBGeneralChargeDetails;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.MapperUtil;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.DueDateAndStockHelper.LeadBankStockBankingArrangement;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.vo.ILeadBankStock;
import com.integrosys.cms.ui.common.UIUtil;

public class DueDateAndStockMapper extends AbstractCommonMapper {
	
	@Override
	public Object mapFormToOB(CommonForm cForm, HashMap inputMap) throws MapperException {
		DueDateAndStockForm form = (DueDateAndStockForm) cForm;
		
		
		IGeneralChargeDetails chargeDetail = (IGeneralChargeDetails) inputMap.get(SESSION_DUE_DATE_AND_STOCK_DETAILS);
		if(chargeDetail==null)	{	
			chargeDetail = new OBGeneralChargeDetails();
		}
		
		ICollateralTrxValue itrxValue = (ICollateralTrxValue) inputMap.get(SERVICE_COLLATERAL_OBJ);
		IGeneralCharge stagingCollateral = (IGeneralCharge) itrxValue.getStagingCollateral();
		IGeneralChargeDetails[] stocksAndDueDateDetails = stagingCollateral.getGeneralChargeDetails();
		form.setBankingArrangementVal(stagingCollateral.getBankingArrangement());
		if(form.getDocCode()==null || AssetGenChargeAction.EVENT_SAVE_DUE_DATE_AND_STOCK_ERROR.equals(form.getEvent())) {
			return null;
		}

		String selectedIndex = (String) inputMap.get(SELECTED_INDEX);
		
		if(StringUtils.isNotEmpty(selectedIndex)) {
			int idx = Integer.parseInt(selectedIndex)-1;
			chargeDetail = (OBGeneralChargeDetails) stocksAndDueDateDetails[idx];
		}
		
		chargeDetail.setStatus(IGeneralChargeDetails.STATUS_PENDING);
		
		if(StringUtils.isNotBlank(form.getDocCode())) {
			chargeDetail.setDocCode(form.getDocCode());
			Map<String,String> dueDateMap = (Map<String,String>)inputMap.get(DUE_DATE_MAP);
			if(dueDateMap !=null ) {
				Date dueDate = MapperUtil.stringToDate(dueDateMap.get(form.getDocCode()));
				chargeDetail.setDueDate(dueDate);
				
				DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
				String toDate = df.format(dueDate);
				form.setDueDate(toDate);
			}
			Set<String> docCodesSet = Collections.emptySet();
			
			if(!ArrayUtils.isEmpty(stocksAndDueDateDetails)) {
				docCodesSet = new HashSet<String>();
				for(IGeneralChargeDetails genChargeDetail : stocksAndDueDateDetails) {
					docCodesSet.add(genChargeDetail.getDocCode());
				}
			}
			
			if(docCodesSet!=null && docCodesSet.contains(form.getDocCode())){
				form.setDueDateAlreadyReceived("Y");
			}else {
				form.setDueDateAlreadyReceived("N");
			}
		}
		
		if(StringUtils.isNotBlank(form.getLocation()))
			chargeDetail.setLocation(form.getLocation());
		
		if(StringUtils.isNotBlank(form.getStockDocMonth()))
			chargeDetail.setStockdocMonth(form.getStockDocMonth());
		
		if(StringUtils.isNotBlank(form.getStockDocYear()))
			chargeDetail.setStockdocYear(form.getStockDocYear());
		
		String dpShare = MapperUtil.emptyIfNull(form.getDpShare());
		chargeDetail.setDpShare(dpShare);
		
		chargeDetail.setRemarkByMaker(form.getRemarkByMaker());
		if(null != form.getTotalLoanable()) {
		BigDecimal totalLoanableAmount = MapperUtil.stringToBigDecimal(UIUtil.removeComma(form.getTotalLoanable()));
		if(null == totalLoanableAmount) {
			chargeDetail.setTotalLoanable("");
		}else {
			chargeDetail.setTotalLoanable(totalLoanableAmount.toString());
			form.setTotalLoanable(totalLoanableAmount.toString());
		}
		
		
		}
		
		String dpAsPerStockStatement = MapperUtil.emptyIfNull(form.getDpAsPerStockStatement());
		chargeDetail.setCalculatedDP(dpAsPerStockStatement);

		BigDecimal dpForCashFlowOrBudget = MapperUtil.stringToBigDecimal(UIUtil.removeComma(form.getDpForCashFlowOrBudget()));
		chargeDetail.setDpForCashFlowOrBudget(dpForCashFlowOrBudget);
		
		chargeDetail.setDpCalculateManually(form.getDpCalcManually());
		
		BigDecimal actualDp = MapperUtil.stringToBigDecimal(form.getActualDp());
		chargeDetail.setActualDp(actualDp);
		
		chargeDetail.setAdHocCoverageAmount(MapperUtil.stringToBigDecimal(form.getAdHocCoverageAmount()));
		chargeDetail.setCoverageAmount(MapperUtil.stringToBigDecimal(form.getCoverageAmount()));
		chargeDetail.setCoveragePercentage(MapperUtil.stringToDouble(form.getCoveragePercentage()));

		return chargeDetail;
	}

	@Override
	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputMap) throws MapperException {
		
		DueDateAndStockForm form = (DueDateAndStockForm) cForm;
		IGeneralChargeDetails chargeDetail = (IGeneralChargeDetails) obj;	
		IGeneralChargeDetails actualChargeDetail = (IGeneralChargeDetails) inputMap
				.get(ACTUAL_DUE_DATE_AND_STOCK_DETAILS_KEY);
		ICollateralTrxValue collateralTrx = (ICollateralTrxValue) inputMap.get(SERVICE_COLLATERAL_OBJ);
		
		IGeneralCharge sessionStageCollateral = (IGeneralCharge) collateralTrx.getStagingCollateral();
		
		Map<String, String> statements = sessionStageCollateral.getDueDateAndStockStatements();
		if(statements==null)
			statements = Collections.emptyMap();

		String dueDate = MapperUtil.dateToString(chargeDetail.getDueDate(), null);
		form.setDueDate(dueDate);
		
		String docCode = MapperUtil.emptyIfNull(chargeDetail.getDocCode());
		form.setDocCode(docCode);
		
		String location = MapperUtil.emptyIfNull(chargeDetail.getLocation());
		form.setLocation(location);

		String stockDocMonth = MapperUtil.emptyIfNull(chargeDetail.getStockdocMonth());
		form.setStockDocMonth(stockDocMonth);
		
		String stockDocYear = MapperUtil.emptyIfNull(chargeDetail.getStockdocYear());
		form.setStockDocYear(stockDocYear);
		
		
		
		String statement = statements.get(chargeDetail.getDocCode());
		form.setStatementName(MapperUtil.emptyIfNull(statement));
		System.out.println("DueDateAndStockMapper.java=>statement=>"+statement);
		if("".equals(statement) || null == statement ) {
		String statementName ="";
		
		if(docCode!=null&& !"".equals(docCode.trim())){
			ICollateralProxy colProxy = CollateralProxyFactory.getProxy();
			try {
				statementName = colProxy.getStatementNameByDocCode(docCode);
				form.setStatementName(statementName);
				System.out.println("statementName in Mapper:"+statementName);
			} catch (SearchDAOException e1) {
				e1.printStackTrace();
			} catch (CollateralException e1) {
				e1.printStackTrace();
			}
		}
		}
		String dpToBeCalcManually = chargeDetail.getDpCalculateManually();
		dpToBeCalcManually = dpToBeCalcManually==null ? "NO" : dpToBeCalcManually;
		form.setDpCalcManually(dpToBeCalcManually);
		
		String dpShare = MapperUtil.emptyIfNull(chargeDetail.getDpShare());
		form.setDpShare(dpShare);
		
		String remarkByMaker = chargeDetail.getRemarkByMaker();
		form.setRemarkByMaker(remarkByMaker);
		

		List stockDetailsList = new ArrayList();
		IGeneralChargeDetails existingChargeDetails;
		IGeneralChargeDetails sessionDueDateAndStocks = (IGeneralChargeDetails) inputMap.get(SESSION_DUE_DATE_AND_STOCK_DETAILS); //null 
		IGeneralChargeStockDetails[] existingGeneralChargeStockDetails; 
		existingChargeDetails = sessionDueDateAndStocks;

		if (null != existingChargeDetails  && null != existingChargeDetails.getGeneralChargeStockDetails() ) {
			existingGeneralChargeStockDetails = existingChargeDetails.getGeneralChargeStockDetails();
			for (int j = 0; j < existingGeneralChargeStockDetails.length; j++) {
				IGeneralChargeStockDetails existingStockDetails = existingGeneralChargeStockDetails[j];
				stockDetailsList.add(existingStockDetails);
			}
		}
		
		String totalLonable ;
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
				chargeDetail.setTotalLoanable(totalLonable);
			} 

		}
	
		String totalLoanable = chargeDetail.getTotalLoanable();
		form.setTotalLoanable(totalLoanable);
		
		String dpAsPerStockStatement = MapperUtil.emptyIfNull(chargeDetail.getCalculatedDP());
		form.setDpAsPerStockStatement(dpAsPerStockStatement);
		
		String dpForCashFlow = MapperUtil.bigDecimalToString(chargeDetail.getDpForCashFlowOrBudget());
		form.setDpForCashFlowOrBudget(dpForCashFlow);
		
		String actualDp = MapperUtil.bigDecimalToString(chargeDetail.getActualDp());
		form.setActualDp(actualDp);
		
		String totalLoanableAmount = MapperUtil.bigDecimalToString(chargeDetail.getTotalLoanableAmount());
		form.setTotalLoanableAmount(totalLoanableAmount);
		
		
		
		//Actual charge detail for Checker Screen
		if(actualChargeDetail!=null) {
			String dueDateActual = MapperUtil.dateToString(actualChargeDetail.getDueDate(), null);
			form.setDueDateActual(dueDateActual);
			
			String stockDocMonthActual = MapperUtil.emptyIfNull(actualChargeDetail.getStockdocMonth());
			form.setStockDocMonthActual(stockDocMonthActual);
			
			String stockDocYearActual = MapperUtil.emptyIfNull(actualChargeDetail.getStockdocYear());
			form.setStockDocYearActual(stockDocYearActual);
			
			String statementActual = statements.get(actualChargeDetail.getDocCode());
			form.setStatementNameActual(MapperUtil.emptyIfNull(statementActual));
			
			String dpToBeCalcManuallyActual = actualChargeDetail.getDpCalculateManually();
			dpToBeCalcManuallyActual = dpToBeCalcManuallyActual ==null ? "NO" : dpToBeCalcManuallyActual;
			form.setDpCalcManuallyActual(dpToBeCalcManuallyActual);
			
			String dpShareActual = MapperUtil.emptyIfNull(actualChargeDetail.getDpShare());
			form.setDpShareActual(dpShareActual);
			
			String dpAsPerStockStatementActual = MapperUtil.emptyIfNull(actualChargeDetail.getCalculatedDP());
			form.setDpAsPerStockStatementActual(dpAsPerStockStatementActual);
		}
		
		if (!ArrayUtils.isEmpty(chargeDetail.getGeneralChargeStockDetails())) {
			mapStockOBToForm(form, chargeDetail, inputMap);
		}

		form.setLeadBankStockInd(ICMSConstant.YES);
		mapLeadBankStockOBToForm(form, chargeDetail);
		
		form.setAdHocCoverageAmount(MapperUtil.bigDecimalToString(chargeDetail.getAdHocCoverageAmount()));
		form.setCoverageAmount(MapperUtil.bigDecimalToString(chargeDetail.getCoverageAmount()));
		form.setCoveragePercentage(MapperUtil.doubleToString(chargeDetail.getCoveragePercentage()));
		
		return form;
	}

	protected static void mapStockOBToForm(DueDateAndStockForm form, IGeneralChargeDetails chargeDetail, HashMap inputMap) {
		if(chargeDetail==null || ArrayUtils.isEmpty(chargeDetail.getGeneralChargeStockDetails()))
			return;
		
		List<AssetGenChargeStockDetailsHelperForm> stockFormList = new ArrayList<AssetGenChargeStockDetailsHelperForm>();
		IGeneralChargeStockDetails[] stockArr = chargeDetail.getGeneralChargeStockDetails();
		//List<LabelValueBean> allLocations = AssetGenChargeHelper.getLocationList();
		//Map<String, String> locationMap = UIUtil.convertLabelValueBeanListToMap(allLocations);
		
		BigDecimal totalLonableAsset = BigDecimal.ZERO;
		BigDecimal totalLonableLiability = BigDecimal.ZERO;
		BigDecimal totalLonableForDP = BigDecimal.ZERO;
		BigDecimal calculatedDP = BigDecimal.ZERO;
		
		String dpShare = (String) inputMap.get(DP_SHARE);
		BigDecimal fundedShare = StringUtils.isBlank(dpShare) ? BigDecimal.ZERO : new BigDecimal(dpShare);
		
		AssetGenChargeStockDetailsHelperForm stockForm = new AssetGenChargeStockDetailsHelperForm();

		String dueDate = MapperUtil.dateToString(chargeDetail.getDueDate(), null);
		stockForm.setDueDate(dueDate+","+form.getDocCode());
		
		
		for(IGeneralChargeStockDetails stock : stockArr) {
			stockForm.setLocationID(String.valueOf(stock.getLocationId()));
			stockForm.setLocationName(MapperUtil.emptyIfNull(AssetGenChargeHelper.getLocationNameById(stock.getLocationId())));
			
			//form.getLocation() != null && form.getLocation().equals(String.valueOf(stock.getLocationId()))
			if ("YES".equals(stock.getApplicableForDp()) && StringUtils.isNotBlank(stock.getLonable())) {
				BigDecimal loanable = new BigDecimal(stock.getLonable());
				
				
				if ("CurrentAsset".equals(stock.getStockType())) {
					totalLonableAsset = totalLonableAsset.add(loanable);
				} else if ("CurrentLiabilities".equals(stock.getStockType())) {
					totalLonableLiability = totalLonableLiability.add(loanable);
				}
				if(form.getEvent().equalsIgnoreCase("checker_due_date_and_stock") || "view_due_date_and_stock".equals(form.getEvent())) {
					System.out.println("inside if for event=checker_due_date_and_stock TotalLoanable : "+chargeDetail.getTotalLoanable());
					stockForm.setTotalLonable(chargeDetail.getTotalLoanable());
				}
				else {
					
				stockForm.setTotalLonable((totalLonableAsset.subtract(totalLonableLiability)).toString());
				System.out.println("inside else TotalLoanable : "+stockForm.getTotalLonable());
				}
				
//					System.out.println("inside if for event=checker_due_date_and_stock TotalLoanable : "+chargeDetail.getTotalLoanable()+"****form.getEvent()=>"+form.getEvent());
//					stockForm.setTotalLonable(chargeDetail.getTotalLoanable());
				System.out.println("DueDateAndStockMapper.java=>stockForm.getTotalLonable()====>"+stockForm.getTotalLonable());
				if(null != stockForm.getTotalLonable()) {
					totalLonableForDP=totalLonableForDP.add(new BigDecimal(stockForm.getTotalLonable()));
				}
				calculatedDP=totalLonableForDP.multiply(fundedShare);
				calculatedDP=calculatedDP.divide(new BigDecimal(100),BigDecimal.ROUND_FLOOR);
				stockForm.setCalculatedDP(calculatedDP.toString());

			}
		}
		stockFormList.add(stockForm);
		form.setStockSummaryForm(stockFormList);
	}
	
	protected static void mapLeadBankStockOBToForm(DueDateAndStockForm form, IGeneralChargeDetails chargeDetail) {
		if(chargeDetail==null || CollectionUtils.isEmpty(chargeDetail.getLeadBankStock()))
			return;

		List<LeadBankStockSummaryForm> lnbStockFormList = new ArrayList<LeadBankStockSummaryForm>();
		List<ILeadBankStock> lnbStockObj = chargeDetail.getLeadBankStock();
		BigDecimal totalLeadBankDp = BigDecimal.ZERO;
		Double totalbankSharePct = 0d;
		Double avgbankSharePct = 0d;
		int numOfBankShare = 0;
		for(ILeadBankStock stock : lnbStockObj) {
			LeadBankStockSummaryForm stockForm = new LeadBankStockSummaryForm();
			
			String dpAsPerLeadBank = MapperUtil.bigDecimalToString(stock.getDrawingPowerAsPerLeadBank());
			stockForm.setDrawingPowerAsPerLeadBank(dpAsPerLeadBank);
			
			if(stock.getDrawingPowerAsPerLeadBank() != null)
				totalLeadBankDp = totalLeadBankDp.add(stock.getDrawingPowerAsPerLeadBank());
			
			String bankSharePct = MapperUtil.doubleToString(stock.getBankSharePercentage());
			stockForm.setBankSharePercentage(bankSharePct);
			
			if(stock.getBankSharePercentage() != null) {
				numOfBankShare++;
				totalbankSharePct = totalbankSharePct + stock.getBankSharePercentage();
			}
			
			String stockAmt = MapperUtil.bigDecimalToString(stock.getStockAmount());
			stockForm.setStockAmount(stockAmt);
			
			lnbStockFormList.add(stockForm);
		}
		form.setLeadBankStockSummary(lnbStockFormList);
				
		BigDecimal stockDp = BigDecimal.ZERO;
		if(null != chargeDetail.getCalculatedDP() && !"".equals(chargeDetail.getCalculatedDP()) ) {
		 stockDp = MapperUtil.stringToBigDecimal(UIUtil.removeComma(chargeDetail.getCalculatedDP()));
		}
		
		if(0 != numOfBankShare) {
		avgbankSharePct = totalbankSharePct/numOfBankShare;
		}
		BigDecimal bigAvgbankSharePct = BigDecimal.valueOf(avgbankSharePct)
											.setScale(2, RoundingMode.HALF_UP);
		
		calculateActualStockDp(form, stockDp, totalLeadBankDp, bigAvgbankSharePct);
	}
	
	private static void calculateActualStockDp(DueDateAndStockForm form, BigDecimal stockDp, 
			BigDecimal totalLeadBankDp, BigDecimal bigAvgbankSharePct) {
		
		totalLeadBankDp = totalLeadBankDp.setScale(2, RoundingMode.HALF_UP);		

		BigDecimal avgbankShare = bigAvgbankSharePct.divide(BigDecimal.valueOf(100));
		BigDecimal leadBankDp  =  totalLeadBankDp.multiply(avgbankShare);
		
		BigDecimal actualDp = BigDecimal.ZERO;
		if(BigDecimal.ZERO.compareTo(stockDp) != 0 && BigDecimal.ZERO.compareTo(leadBankDp) != 0)
			actualDp = leadBankDp.min(stockDp);
		else if(BigDecimal.ZERO.compareTo(stockDp)!=0)
			actualDp = stockDp;
		else
			actualDp = leadBankDp;
		
		form.setActualDp(MapperUtil.bigDecimalToString(actualDp.setScale(2, RoundingMode.HALF_UP)));
		form.setLeadBankDp(MapperUtil.bigDecimalToString(leadBankDp));
	}
	
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ SESSION_DUE_DATE_AND_STOCK_DETAILS, IGeneralChargeDetails.class.getName(), SERVICE_SCOPE },
				{ ACTUAL_DUE_DATE_AND_STOCK_DETAILS_KEY, IGeneralChargeDetails.class.getName(), SERVICE_SCOPE },
				{ GC_DETAIL_ID, String.class.getName(), REQUEST_SCOPE },
				{ SERVICE_COLLATERAL_OBJ, ICollateralTrxValue.class.getName(), SERVICE_SCOPE },
				{ SESSION_LOCATION_LIST,  List.class.getName(), SERVICE_SCOPE },
				{ DUE_DATE_MAP, Map.class.getName(), SERVICE_SCOPE },
				{ SELECTED_INDEX, String.class.getName(), REQUEST_SCOPE },
				{ DP_SHARE, String.class.getName(), SERVICE_SCOPE },
				{ "displayList", List.class.getName(), SERVICE_SCOPE },
				{ EVENT, String.class.getName(), REQUEST_SCOPE },
				{ SUB_EVENT, String.class.getName(), REQUEST_SCOPE },
				{ DUE_DATE_AND_STOCK_DETAILS_KEY, IGeneralChargeDetails.class.getName(), FORM_SCOPE},
		});
	}
}
