//GENERATED FILE... ANYMODIFICATION WILL BE LOST. ASK SATHISH FOR ANY CLARIFICATION
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import static com.integrosys.cms.ui.collateral.CollateralConstant.SERVICE_COLLATERAL_OBJ;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.GCStockDetComparator;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeDetails;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.util.MapperUtil;
import com.integrosys.cms.ui.collateral.assetbased.AssetBasedMapper;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 22, 2003 Time: 4:45:05 PM
 * To change this template use Options | File Templates.
 */
public class AssetGenChargeMapper extends AssetBasedMapper {

	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {

		Object obj = AssetGenChargeMapperHelper.getObject(inputs);
		AssetGenChargeForm aForm = (AssetGenChargeForm) cForm;
	
		if ("true".equals(aForm.getUserAccess())) {
			super.mapFormToOB(cForm, inputs, obj);
			return AssetGenChargeMapperHelper.mapFormToOB(cForm, inputs, obj);
		}
		return obj;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {

		super.mapOBToForm(cForm, obj, inputs);
		AssetGenChargeForm form = (AssetGenChargeForm)cForm;
		AssetGenChargeMapperHelper.mapOBToForm(form, obj, inputs);
		
		mapDueDateAndStockOBToForm(obj, form, inputs);
		
		return form;

	}
	
	public static void mapDueDateAndStockOBToForm(Object obj, AssetGenChargeForm form, HashMap inputMap) {
		if(obj==null)
			return;
		IGeneralCharge genCharge = (IGeneralCharge) obj;
		IGeneralChargeDetails[] genChargeDetailsObj = genCharge.getGeneralChargeDetails();
		List<DueDateAndStockSummaryForm> dueDateAndStockForm = new ArrayList<DueDateAndStockSummaryForm>();
		ICollateralTrxValue collateralTrx = (ICollateralTrxValue) inputMap.get(SERVICE_COLLATERAL_OBJ);
		IGeneralCharge sessionStageCollateral = (IGeneralCharge) collateralTrx.getStagingCollateral();
		
		Map<String, String> statements = sessionStageCollateral.getDueDateAndStockStatements();
		if(statements==null)
			statements = Collections.emptyMap();
			
		if(!ArrayUtils.isEmpty(genChargeDetailsObj)) {
			
		    	Arrays.sort(genChargeDetailsObj, new Comparator<IGeneralChargeDetails>() {
		    		public int compare(IGeneralChargeDetails o1, IGeneralChargeDetails o2) {
		    			return (o1.getDueDate() != null && o2.getDueDate()!= null)? o1.getDueDate().compareTo(o2.getDueDate()):0;
		    		}
		    	});
		    	
			
			for(IGeneralChargeDetails chargeDetail : genChargeDetailsObj) {
				DueDateAndStockSummaryForm itemForm = new DueDateAndStockSummaryForm();
				
				String dueDate = MapperUtil.dateToString(chargeDetail.getDueDate(), null);
				itemForm.setDueDate(dueDate);
				
				String statement = statements.get(chargeDetail.getDocCode());
				itemForm.setStatementName(MapperUtil.emptyIfNull(statement));
				
				
				System.out.println("DueDateAndStockMapper.java=>statement=>"+statement);
				if("".equals(statement) || null == statement ) {
				String statementName ="";
				
				if(null != chargeDetail.getDocCode() && !"".equals(chargeDetail.getDocCode())){
					ICollateralProxy colProxy = CollateralProxyFactory.getProxy();
					try {
						statementName = colProxy.getStatementNameByDocCode(chargeDetail.getDocCode());
						itemForm.setStatementName(MapperUtil.emptyIfNull(statementName));
						System.out.println("AssetBasedGeneralchargeMapper.java=>statementName in Mapper:"+statementName);
					} catch (SearchDAOException e1) {
						e1.printStackTrace();
					} catch (CollateralException e1) {
						e1.printStackTrace();
					}
				}
				}
				
				
				String dpToBeCalcManually = chargeDetail.getDpCalculateManually();
				dpToBeCalcManually = dpToBeCalcManually==null ? "NO" : dpToBeCalcManually;
				itemForm.setDpCalcManually(dpToBeCalcManually);
				
				String dpShare = MapperUtil.emptyIfNull(chargeDetail.getDpShare());
				itemForm.setDpShare(dpShare);
				
				String dpAsPerStockStatement = MapperUtil.emptyIfNull(chargeDetail.getCalculatedDP());
				itemForm.setDpAsPerStockStatement(dpAsPerStockStatement);
				
				String dpForCashFlow = MapperUtil.bigDecimalToString(chargeDetail.getDpForCashFlowOrBudget());
				itemForm.setDpForCashFlowOrBudget(dpForCashFlow);
				
				String chargeDetailId = String.valueOf(chargeDetail.getGeneralChargeDetailsID());
				itemForm.setGcDetailId(chargeDetailId);
				dueDateAndStockForm.add(itemForm);
			}
			form.setDueDateAndStock(dueDateAndStockForm);
		}
	}
	
}
