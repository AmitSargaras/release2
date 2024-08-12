package com.integrosys.cms.ui.collateral.property;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.IPin;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.collateral.ActualListCmd;
import com.integrosys.cms.ui.collateral.ApproveCollateralCommand;
import com.integrosys.cms.ui.collateral.ApproveInsuranceCommand;
import com.integrosys.cms.ui.collateral.CollateralAction;
import com.integrosys.cms.ui.collateral.ReadReturnCollateralCommand;
import com.integrosys.cms.ui.collateral.ReturnCollateralCommand;
import com.integrosys.cms.ui.collateral.ValidateStpCollateralCommand;
import com.integrosys.cms.ui.collateral.property.propcommgeneral.PreparePropCommGeneralCommand;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jul 25, 2003 Time: 6:20:00 PM
 * To change this template use Options | File Templates.
 */
public class PropertyAction extends CollateralAction implements IPin {

	public static final String VIEW_PREV_VALUATION1="viewPreviousValuation1";
	public static final String RETURN_PREV_VALUATION1="returnPreviousValuation1";
	
	
	public static final String VIEW_PREV_VALUATION2="viewPreviousValuation2";
	public static final String RETURN_PREV_VALUATION2="returnPreviousValuation2";
	
	public static final String VIEW_PREV_VALUATION3="viewPreviousValuation3";
	public static final String RETURN_PREV_VALUATION3="returnPreviousValuation3";
	
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if (EVENT_PRINT_REMINDER.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrintReminderCommand();
		}
		else if ("refresh_dropdown".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RefreshDropDownCommand();
		}
		else if (EVENT_APPROVE.equals(event)) {
			objArray = new ICommand[8];
			objArray[0] = new ValidateStpCollateralCommand("HP", "AB102");
			objArray[1] = new ApproveCollateralCommand();
			objArray[2] = new ActualListCmd();
			objArray[3] = new ApproveInsuranceCommand();
			objArray[4] = new MaintainOtherCheckListCommandProp();
			objArray[5] = new SubmitOtherCheckListCommandIPProp();
			objArray[6] = new SubmitOtherReceiptCommandIPProp();
			objArray[7] = new ApproveOtherReceiptCommandProp();
		}	else if ("refreshPreMortgage".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RefreshPreMortgageCmd();
			
		}else if (VIEW_PREV_VALUATION1.equals(event)) {
			objArray = new ICommand[1];			
			//objArray[0] = new ProcessingCollateralCommand();
			objArray[0] = new ViewPreviousValuationCmd();
			
		}else if (VIEW_PREV_VALUATION2.equals(event)) {
			objArray = new ICommand[1];			
			objArray[0] = new ViewPreviousValuation2Cmd();
		}else if (RETURN_PREV_VALUATION1.equals(event) || RETURN_PREV_VALUATION2.equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new ReadReturnCollateralCommand();
			objArray[1] = new PreparePropCommGeneralCommand();
			objArray[2] = new ReturnCollateralCommand();
		}else if (VIEW_PREV_VALUATION3.equals(event)) {
			objArray = new ICommand[1];			
			//objArray[0] = new ProcessingCollateralCommand();
			objArray[0] = new ViewPreviousValuation3Cmd();
			
		}else if (RETURN_PREV_VALUATION3.equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new ReadReturnCollateralCommand();
			objArray[1] = new PreparePropCommGeneralCommand();
			objArray[2] = new ReturnCollateralCommand();
		}
		else {
			objArray = super.getCommandChain(event);
		}
		return objArray;
	}

	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();
		if ("refresh_dropdown".equals(event)) {
			aPage.setPageReference("dropdown_list");
		}else if("refreshPreMortgage".equals(event)) {
			aPage.setPageReference("refreshPreMortgage");
		}else if (VIEW_PREV_VALUATION1.equals(event)) {
			aPage.setPageReference(VIEW_PREV_VALUATION1);
		}else if (RETURN_PREV_VALUATION1.equals(event)) {
			aPage.setPageReference(RETURN_PREV_VALUATION1);
		}
		else {
			aPage = (Page)super.getNextPage(event, resultMap, exceptionMap);
		}
		return aPage;
	}
}
