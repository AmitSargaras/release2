package com.integrosys.cms.ui.feed.bond.list;

import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.common.RemarksValidatorUtil;
import com.integrosys.cms.ui.feed.FeedConstants;

/**
 * This class implements validation
 */
public class BondListFormValidator implements java.io.Serializable {

	private static String LOGOBJ = BondListFormValidator.class.getName();

	public static ActionErrors validateInput(BondListForm form, Locale locale) {

		String event = form.getEvent();
		ActionErrors errors = new ActionErrors();
		String errorCode = "";

		if (BondListAction.EVENT_SAVE.equals(event) || BondListAction.EVENT_SUBMIT.equals(event)
				|| BondListAction.EVENT_PAGINATE.equals(event) || BondListAction.EVENT_ADD.equals(event)
				|| BondListAction.EVENT_REMOVE.equals(event)) {
			// Check that the updated unit prices fall in range.
			String[] bondName = form.getBondNames();
			String[] updatedUnitPricesArr = form.getUpdatedUnitPrices();
			//FeedUIValidator.validateUnitPricesArr(form.getUpdatedUnitPrices(), errors);
			String[] issueDate = form.getIssueDateArr();
			String[] maturityDate = form.getMaturityDateArr();
			
			String[] rating = form.getRatingArr();
			String[] couponRate = form.getCouponRate();
			
			if (bondName != null) {
				for (int i = 0; i < bondName.length; i++) {
					if(bondName[i]==null || bondName[i].equals("")){
						errors.add("bondNameError"+i,new ActionMessage("error.string.mandatory"));
					}else{
						if (!(errorCode = Validator.checkString(bondName[i], true, 1, 30)).equals(Validator.ERROR_NONE)) {
							errors.add("bondNameError"+i, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), new Integer(1),
									new Integer(30)));
						}
						else{
							boolean nameFlag = ASSTValidator.isValidPercentRoundBrackets(bondName[i]);
							if( nameFlag == true)
								errors.add("bondNameError"+i, new ActionMessage("error.string.invalidCharacter"));
						}
					}	
				}
			}
			
			if (updatedUnitPricesArr != null) {
				for (int i = 0; i < updatedUnitPricesArr.length; i++) {
					if(updatedUnitPricesArr[i]==null || updatedUnitPricesArr[i].equals("")){
						errors.add("currentUnitPriceError"+i,new ActionMessage("error.string.mandatory"));
					}else{
						if (!(errorCode = Validator.checkNumber(updatedUnitPricesArr[i], true, 0, 99999.99,3,locale)).equals(Validator.ERROR_NONE)) {
							errors.add("currentUnitPriceError"+i , new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,errorCode), "0", "99999.99"));
							DefaultLogger.debug(LOGOBJ, "currentUnitPriceError= "+ updatedUnitPricesArr[i]);
						}
					}	
				}
			}
			
			if (rating != null) {
				
				for (int i = 0; i < rating.length; i++) {
					if(rating[i]==null || rating[i].equals("")){
						errors.add("ratingError"+i,new ActionMessage("error.string.mandatory"));
					}else{
						if(!Validator.ERROR_NONE.equals(errorCode = Validator.checkString(rating[i], true, 1, 30))){
							errors.add("ratingError"+i, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), new Integer(1),
									new Integer(30)));
						}
					}	
				}
			}
			
			if ( couponRate != null) {				
				for (int i = 0; i < couponRate.length; i++) {
					if(couponRate[i]==null || couponRate[i].equals("")){
						errors.add("couponRate"+i,new ActionMessage("error.string.mandatory"));
					}else if (!(errorCode = Validator.checkNumber(couponRate[i], true, 0, 999.99,3,locale)).equals(Validator.ERROR_NONE)) {
						errors.add("couponRate"+i , new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,errorCode), "0", "999.99"));
						DefaultLogger.debug(LOGOBJ, "couponRate= "+ updatedUnitPricesArr[i]);
					}			
				}
			}
			
			if( maturityDate != null ){
				for (int i = 0; i < maturityDate.length; i++) {
					if( maturityDate[i]==null || maturityDate[i].equals("") ){
						errors.add("maturityDate"+i,new ActionMessage("error.string.mandatory"));
					}else if (!(errorCode = Validator.checkDate(maturityDate[i], false, locale)).equals(Validator.ERROR_NONE)) {
						errors.add("maturityDate"+i, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode)));
					}
/*					else if ((maturityDate != null) && !maturityDate.equals("")) {
						if (DateUtil.convertDate(locale, maturityDate[i]).before(new Date())) {
							errors.add("maturityDate"+i, new ActionMessage(FeedConstants.ERROR_PAST_DATE));
						}
					}*/
				}
			}
			
			if( issueDate != null ){
				for (int i = 0; i < issueDate.length; i++) {
					if( issueDate[i]==null || issueDate[i].equals("") ){
						errors.add("issueDate"+i,new ActionMessage("error.string.mandatory"));
					}else if (!(errorCode = Validator.checkDate(issueDate[i], false, locale)).equals(Validator.ERROR_NONE)) {
						errors.add("issueDate"+i, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode)));
					}else if ((issueDate != null) && !issueDate.equals("")) {
						if (DateUtil.convertDate(locale, issueDate[i]).before(new Date())) {
							errors.add("issueDate"+i, new ActionMessage(FeedConstants.ERROR_FUTURE_DATE));
						}
					}
				}
			}
			
		}

		// Check that there is at least one selected checkbox.
		if (BondListAction.EVENT_REMOVE.equals(event)) {
			if ((form.getChkDeletes() == null) || (form.getChkDeletes().length == 0)) {
				errors.add("chkDeletes", new ActionMessage(FeedConstants.ERROR_CHKBOX_MANDATORY));
			}
		}

		if (BondListAction.EVENT_APPROVE.equals(event) || BondListAction.EVENT_REJECT.equals(event)
				|| BondListAction.EVENT_CLOSE.equals(event)) {
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
