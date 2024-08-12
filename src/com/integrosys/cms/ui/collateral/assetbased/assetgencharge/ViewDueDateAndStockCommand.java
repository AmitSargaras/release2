package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import static com.integrosys.cms.ui.collateral.CollateralConstant.ACTUAL_DUE_DATE_AND_STOCK_DETAILS_KEY;
import static com.integrosys.cms.ui.collateral.CollateralConstant.COLLATERAL_ID;
import static com.integrosys.cms.ui.collateral.CollateralConstant.DUE_DATE_AND_STOCK_DETAILS_KEY;
import static com.integrosys.cms.ui.collateral.CollateralConstant.DUE_DATE_LIST;
import static com.integrosys.cms.ui.collateral.CollateralConstant.DUE_DATE_VALUE;
import static com.integrosys.cms.ui.collateral.CollateralConstant.GC_DETAIL_ID;
import static com.integrosys.cms.ui.collateral.CollateralConstant.LOCATION_LIST;
import static com.integrosys.cms.ui.collateral.CollateralConstant.PARENT_PAGE;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SERVICE_COLLATERAL_OBJ;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SESSION_DUE_DATE_AND_STOCK_DETAILS;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SESSION_LOCATION_LIST;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SESSION_PARENT_PAGE;
import static com.integrosys.cms.ui.collateral.CollateralConstant.STOCK_DOC_MONTH_LIST;
import static com.integrosys.cms.ui.collateral.CollateralConstant.STOCK_DOC_YEAR_LIST;
import static com.integrosys.cms.ui.collateral.CollateralConstant.TRX_ID;
import static com.integrosys.cms.ui.collateral.assetbased.assetgencharge.AssetGenChargeAction.EVENT_CHECKER_DUE_DATE_AND_STOCK;
import static com.integrosys.cms.ui.common.constant.IGlobalConstant.GLOBAL_CUSTOMER_OBJ;
import static com.integrosys.cms.ui.common.constant.IGlobalConstant.INDEX;
import static com.integrosys.cms.ui.common.constant.IGlobalConstant.SELECTED_INDEX;
import static com.integrosys.cms.ui.common.constant.IGlobalConstant.SUB_EVENT;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IDueDateAndStockBO;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeDetails;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.OBGeneralChargeDetails;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;

public class ViewDueDateAndStockCommand extends AbstractCommand {
	
	private IDueDateAndStockBO dueDateAndStockBO = (IDueDateAndStockBO) BeanHouse.get("dueDateAndStockBO");
	
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ DUE_DATE_AND_STOCK_DETAILS_KEY, IGeneralChargeDetails.class.getName(), FORM_SCOPE},
				{ SESSION_DUE_DATE_AND_STOCK_DETAILS, IGeneralChargeDetails.class.getName(), SERVICE_SCOPE },
				{ GLOBAL_CUSTOMER_OBJ, ICMSCustomer.class.getName(), GLOBAL_SCOPE },
				{ PARENT_PAGE, String.class.getName(), REQUEST_SCOPE },
				{ EVENT, String.class.getName(), REQUEST_SCOPE },
				{ SUB_EVENT, String.class.getName(), REQUEST_SCOPE },
				{ GC_DETAIL_ID, String.class.getName(), REQUEST_SCOPE },
				{ SERVICE_COLLATERAL_OBJ, ICollateralTrxValue.class.getName(), SERVICE_SCOPE },
				{ DUE_DATE_VALUE, String.class.getName(), REQUEST_SCOPE },
				{ INDEX, String.class.getName(), REQUEST_SCOPE },
				{ "docDescriptionName", String.class.getName(), REQUEST_SCOPE },
				{ "duedate1", String.class.getName(), REQUEST_SCOPE },
				

		});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { 
				{ SESSION_DUE_DATE_AND_STOCK_DETAILS, IGeneralChargeDetails.class.getName(), SERVICE_SCOPE},
				{ ACTUAL_DUE_DATE_AND_STOCK_DETAILS_KEY, IGeneralChargeDetails.class.getName(), SERVICE_SCOPE},
				{ DUE_DATE_LIST, List.class.getName(), REQUEST_SCOPE},
				{ LOCATION_LIST,  List.class.getName(), REQUEST_SCOPE },
				{ SESSION_LOCATION_LIST,  List.class.getName(), SERVICE_SCOPE },				
				{ STOCK_DOC_MONTH_LIST, List.class.getName(), REQUEST_SCOPE},
				{ STOCK_DOC_YEAR_LIST, List.class.getName(), REQUEST_SCOPE},
				{ SESSION_PARENT_PAGE, String.class.getName(), SERVICE_SCOPE },
				{ EVENT, String.class.getName(), REQUEST_SCOPE },
				{ DUE_DATE_VALUE, String.class.getName(), REQUEST_SCOPE },
				{ SELECTED_INDEX, String.class.getName(), SERVICE_SCOPE },
				{ COLLATERAL_ID, String.class.getName(), SERVICE_SCOPE},
				{ TRX_ID, String.class.getName(), REQUEST_SCOPE},
				{ DUE_DATE_AND_STOCK_DETAILS_KEY, IGeneralChargeDetails.class.getName(), FORM_SCOPE},
				
		});
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public HashMap doExecute(HashMap inputMap) throws CommandProcessingException, CommandValidationException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		String event = (String) inputMap.get(EVENT);
		String subEvent = (String) inputMap.get(SUB_EVENT);
		ICollateralTrxValue collateralTrx = (ICollateralTrxValue) inputMap.get(SERVICE_COLLATERAL_OBJ);
		IGeneralCharge stagingCollateral = (IGeneralCharge) collateralTrx.getStagingCollateral();
		IGeneralChargeDetails[] stagingGenCharge = stagingCollateral.getGeneralChargeDetails();
		
		IGeneralCharge collateral = (IGeneralCharge) collateralTrx.getCollateral();
		IGeneralChargeDetails[] actualGenCharge = collateral.getGeneralChargeDetails();
		IGeneralChargeDetails actualChargeDetail = null;
		
		String index = (String) inputMap.get(INDEX);
		IGeneralChargeDetails chargeDetail = null;		
		
		int idx = -1;
		String sessionParentPage = (String) inputMap.get(PARENT_PAGE);
		
		if((event.equalsIgnoreCase("checker_due_date_and_stock") || event.equalsIgnoreCase("view_due_date_and_stock")) && subEvent==null) {
			subEvent = "view";
			index="1";
			if(sessionParentPage==null && event.equalsIgnoreCase("checker_due_date_and_stock")) {
				sessionParentPage = "ASSET_PROCESS";
			}
		}
		
		if(StringUtils.isNotEmpty(index)) {
			idx = Integer.parseInt(index)-1;
		}

		IGeneralChargeDetails sessionChargeDetail = (IGeneralChargeDetails) inputMap.get(SESSION_DUE_DATE_AND_STOCK_DETAILS);
		if ("view".equals(subEvent)) {
			if(idx>-1) {
				/*String docDescriptionName1 = (String) inputMap.get("docDescriptionName");
				if(null == docDescriptionName1) {
					docDescriptionName1 ="";
				}*/
				DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
				String duedateTest = "";
				String duedatess1 = (String) inputMap.get("duedate1");
				if(sessionChargeDetail!=null && sessionChargeDetail.getDueDate()!=null) {
				if(duedatess1==null || duedatess1.isEmpty()) {	
					duedatess1 =df.format(sessionChargeDetail.getDueDate());	
					System.out.println("sessionChargeDetail.getDueDate() : "+sessionChargeDetail.getDueDate()+" duedatess1 : "+duedatess1);
					}}
				
				if(null == duedatess1) {
					duedatess1 ="";
				}
				for(int i=0;i<stagingGenCharge.length;i++) {
					duedateTest =df.format(stagingGenCharge[i].getDueDate());
					System.out.println("ViewDueDateAndStockCommand.java=>duedateTest=>"+duedateTest+"***duedatess1=>"+duedatess1);
					if(duedatess1.equals(duedateTest)) {
						idx = i;
					}
				}
				chargeDetail = (OBGeneralChargeDetails) stagingGenCharge[idx];
			}
			
		} else if ("refreshDueDate".equals(subEvent)) {
			chargeDetail = (IGeneralChargeDetails)inputMap.get(DUE_DATE_AND_STOCK_DETAILS_KEY);
			dueDateAndStockBO.prepareDataForRefreshDueDate(inputMap, resultMap, chargeDetail);
		} else if ("viewStocks".equals(subEvent)) {
			chargeDetail = (IGeneralChargeDetails)inputMap.get(DUE_DATE_AND_STOCK_DETAILS_KEY);
			dueDateAndStockBO.prepareDataForViewStock(inputMap, resultMap, chargeDetail);
		} else if (subEvent==null || "return".equals(subEvent)) {
			chargeDetail = (IGeneralChargeDetails) inputMap.get(SESSION_DUE_DATE_AND_STOCK_DETAILS);
			dueDateAndStockBO.prepareDataForViewStock(inputMap, resultMap, chargeDetail);
		}
		dueDateAndStockBO.prepareDataForView(inputMap, resultMap, chargeDetail);
		
		if(actualGenCharge != null) {
			
			for (int i =0; i<actualGenCharge.length; i++)
			{
				IGeneralChargeDetails actualCharge = actualGenCharge[i];
				System.out.println("actualCharge : "+actualCharge.getDocCode());
				if(StringUtils.isNotBlank(actualCharge.getDocCode()) && actualCharge.getDocCode().equals(chargeDetail.getDocCode())) {
					actualChargeDetail = actualCharge;
					break;
				}
		}
		/*	for(IGeneralChargeDetails actualCharge : actualGenCharge) {
				if(StringUtils.isNotBlank(actualCharge.getDocCode()) && actualCharge.getDocCode().equals(chargeDetail.getDocCode())) {
					actualChargeDetail = actualCharge;
					break;
				}
			}
			*/
		}
		
		if(EVENT_CHECKER_DUE_DATE_AND_STOCK.equals(event)) {
			resultMap.put(ACTUAL_DUE_DATE_AND_STOCK_DETAILS_KEY, actualChargeDetail);
		}
	    resultMap.put(DUE_DATE_AND_STOCK_DETAILS_KEY, chargeDetail);
	    resultMap.put(SESSION_DUE_DATE_AND_STOCK_DETAILS, chargeDetail);
		resultMap.put(EVENT, event);
		resultMap.put(SELECTED_INDEX, index);
		resultMap.put(COLLATERAL_ID, collateralTrx.getReferenceID());
		resultMap.put(TRX_ID, collateralTrx.getTransactionID());
		resultMap.put(SESSION_PARENT_PAGE, sessionParentPage);
		
		HashMap<String, Map<String, ?>> returnMap = new HashMap<String, Map<String, ?>>();
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);

		return returnMap;
	}

}
