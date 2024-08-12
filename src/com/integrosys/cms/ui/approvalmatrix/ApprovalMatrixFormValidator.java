package com.integrosys.cms.ui.approvalmatrix;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.RemarksValidatorUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.feed.FeedConstants;

/**
 * This class implements validation
 */
public class ApprovalMatrixFormValidator implements java.io.Serializable {

	private static String LOGOBJ = ApprovalMatrixFormValidator.class.getName();

	public static ActionErrors validateInput(ApprovalMatrixForm form, Locale locale) {

		String event = form.getEvent();
		ActionErrors errors = new ActionErrors();
		String errorCode = "";
		String maximumAmt = new BigDecimal(IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT).toString();
		if (ApprovalMatrixAction.EVENT_SAVE.equals(event) || ApprovalMatrixAction.EVENT_SUBMIT.equals(event)
				|| ApprovalMatrixAction.EVENT_PAGINATE.equals(event) || ApprovalMatrixAction.EVENT_ADD.equals(event)
				|| ApprovalMatrixAction.EVENT_REMOVE.equals(event)) {
			// Check that the updated unit prices fall in range.
			//String[] updatedUnitPricesArr = form.getUpdatedUnitPrices();
			//FeedUIValidator.validateUnitPricesArr(form.getUpdatedUnitPrices(), errors);
			String[] level1 = form.getLevel1();
			String[] level2 = form.getLevel2();
			String[] level3 = form.getLevel3();
			String[] level4 = form.getLevel4();
			
			if (level1 != null) {
				for (int i = 0; i < level1.length; i++) {
						
					  if (!(errorCode = Validator.checkNumber(level1[i], false, 0, 99999999999999999999.99, 3,
			  					locale)).equals(Validator.ERROR_NONE)) {
			  				errors.add("level1" + i, new ActionMessage(
			  						ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
			  						"0", "99999999999999999999.99"));
			  			}
			   
					  else if (!(errorCode = Validator.checkAmount(level1[i], false, 0,
						      IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
						    .equals(Validator.ERROR_NONE)) {
					      errors.add("level1" + i, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
							"0", maximumAmt));
			        	}
					}
			}	
			
			if (level2 != null) {
				for (int i = 0; i < level2.length; i++) {
						
					  if (!(errorCode = Validator.checkNumber(level2[i], false, 0, 99999999999999999999.99, 3,
			  					locale)).equals(Validator.ERROR_NONE)) {
			  				errors.add("level2" + i, new ActionMessage(
			  						ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
			  						"0", "99999999999999999999.99"));
			  			}
			   
					  else if (!(errorCode = Validator.checkAmount(level2[i], false, 0,
						      IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
						    .equals(Validator.ERROR_NONE)) {
					      errors.add("level2" + i, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
							"0", maximumAmt));
			        	}	
				
				}
			}	
			
			if (level3 != null) {
				for (int i = 0; i < level3.length; i++) {
					 if (!(errorCode = Validator.checkNumber(level3[i], false, 0, 99999999999999999999.99, 3,
			  					locale)).equals(Validator.ERROR_NONE)) {
			  				errors.add("level3" + i, new ActionMessage(
			  						ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
			  						"0", "99999999999999999999.99"));
			  			}
			   
					 else if (!(errorCode = Validator.checkAmount(level3[i], false, 0,
						      IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
						    .equals(Validator.ERROR_NONE)) {
					      errors.add("level3" + i, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
							"0", maximumAmt));
			        	}
					
				}
			}	
			
			
			if (level4 != null) {
				for (int i = 0; i < level4.length; i++) {
					
					 if (!(errorCode = Validator.checkNumber(level4[i], false, 0, 99999999999999999999.99, 3,
			  					locale)).equals(Validator.ERROR_NONE)) {
			  				errors.add("level4" + i, new ActionMessage(
			  						ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
			  						"0", "99999999999999999999.99"));
			  			}
			   
					 else if (!(errorCode = Validator.checkAmount(level4[i], false, 0,
						      IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
						    .equals(Validator.ERROR_NONE)) {
					      errors.add("level4" + i, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
							"0", maximumAmt));
			        	}
					
					}
			}	
		}

		if (ApprovalMatrixAction.EVENT_APPROVE.equals(event) || ApprovalMatrixAction.EVENT_REJECT.equals(event)
				|| ApprovalMatrixAction.EVENT_CLOSE.equals(event)) {
			errorCode = RemarksValidatorUtil.checkRemarks(form.getRemarks(), false);
			if (!Validator.ERROR_NONE.equals(errorCode)) {
				errors.add("remarks", RemarksValidatorUtil.getErrorMessage(errorCode));
			}

		}
		DefaultLogger.debug(LOGOBJ, "number of errors = " + errors.size());
		return errors;
	}
		
		private static boolean checkString(String str) {
			boolean check = false;
			if (str != null) {
				check = str.indexOf(".") > 0;
			}
			return check;

		}	

}
