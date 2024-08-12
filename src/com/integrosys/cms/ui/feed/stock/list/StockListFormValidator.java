package com.integrosys.cms.ui.feed.stock.list;

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
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.feed.FeedConstants;
import com.integrosys.cms.ui.feed.FeedUIValidator;

/**
 * This class implements validation
 */
public class StockListFormValidator implements java.io.Serializable {

	private static String LOGOBJ = StockListFormValidator.class.getName();

	public static ActionErrors validateInput(StockListForm form, Locale locale) {

		String event = form.getEvent();
		ActionErrors errors = new ActionErrors();
		String errorCode = "";

		Date d = DateUtil.clearTime(DateUtil.getDate());

		if (StockListAction.EVENT_ADD.equals(event) || StockListAction.EVENT_REMOVE.equals(event)
				|| StockListAction.EVENT_SAVE.equals(event) || StockListAction.EVENT_SUBMIT.equals(event)
				|| StockListAction.EVENT_PAGINATE.equals(event)) {

			// Check that the updated unit prices fall in range.
			FeedUIValidator.validateUnitPricesArr(form.getUpdatedUnitPrices(), errors);

			String[] expiryDateArr = form.getExpiryDate();
			String[] scriptNameArr = form.getScriptNameArr();
			String[] scriptValueArr = form.getScriptValueArr();
			String[] faceValueArr = form.getFaceValueArr();
			String[] stockExchangeArr = form.getExchangeNameArr();
			String stockType = form.getStockType();
			
			if(scriptNameArr!=null){
				for(int i = 0; i < scriptNameArr.length; i++){
					
					if(scriptNameArr[i] == null && scriptNameArr[i].equals("")){
						errors.add("scriptNameError"+i, new ActionMessage("error.string.mandatory"));
					}else{
						if (!(errorCode = Validator.checkString(scriptNameArr[i], true, 1, 30)).equals(Validator.ERROR_NONE)) {
							errors.add("scriptNameError"+i, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), new Integer(1),
									new Integer(30)));
						}
						else{
							boolean nameFlag = ASSTValidator.isValidAndRoundBrackets(scriptNameArr[i]);
							if( nameFlag == true)
								errors.add("scriptNameError"+i, new ActionMessage("error.string.invalidCharacter"));
						}			
					}
				}
			}
			
			if(scriptValueArr!=null){
				for(int i = 0; i < scriptValueArr.length; i++){	
					
					//Phase 3 CR:comma separated
					scriptValueArr[i]=UIUtil.removeComma(scriptValueArr[i]);
					
					if (!(errorCode = Validator.checkNumber(scriptValueArr[i], true, 0, 9999999.99,3,locale)).equals(Validator.ERROR_NONE)) {
						errors.add("scriptValueError"+i , new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,errorCode), "0", "9999999.99"));
						DefaultLogger.debug(LOGOBJ, "scriptValueError= "+ scriptValueArr[i]);
					}
					else if (!"".equals(scriptValueArr[i]) && checkString(scriptValueArr[i])
							&& !Validator.checkPattern(scriptValueArr[i], FeedConstants.FOREX_PRICE_FORMAT)) {
						errors.add("scriptValueError"+i, new ActionMessage(FeedConstants.ERROR_INVALID));
						DefaultLogger.debug(LOGOBJ, " scriptValueError= "+ scriptValueArr[i]);
					}
				}
			}	
			
			if(faceValueArr!=null){
				for(int i = 0; i < faceValueArr.length; i++){	
					
					//Phase 3 CR:comma separated
					faceValueArr[i]=UIUtil.removeComma(faceValueArr[i]);
					
					if (!(errorCode = Validator.checkNumber(faceValueArr[i], true, 0, 99999.99,3,locale)).equals(Validator.ERROR_NONE)) {
						errors.add("faceValueError"+i , new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,errorCode), "0", "99999.99"));
						DefaultLogger.debug(LOGOBJ, "faceValueError= "+ faceValueArr[i]);
					}
					else if (!"".equals(faceValueArr[i]) && checkString(faceValueArr[i])
							&& !Validator.checkPattern(faceValueArr[i], FeedConstants.FOREX_PRICE_FORMAT)) {
						errors.add("faceValueError"+i, new ActionMessage(FeedConstants.ERROR_INVALID));
						DefaultLogger.debug(LOGOBJ, " faceValueError= "
								+ faceValueArr[i]);
					}
				}
			}	
			
			if (expiryDateArr != null) {
				for (int i = 0; i < expiryDateArr.length; i++) {

					if (!(errorCode = Validator.checkDate(expiryDateArr[i], false, locale))
							.equals(Validator.ERROR_NONE)) {
						errors.add("expiryDate." + i, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE,
								errorCode)));
					}
				}
			}
			
			if((stockType!=null && !stockType.equals("")) && stockType.equals("003")){
				if(stockExchangeArr != null){
					for(int i=0;i<stockExchangeArr.length;i++){
						
						if(stockExchangeArr[i] == null && stockExchangeArr[i].equals("")){
							errors.add("stockExchangeNameError"+i, new ActionMessage("error.string.mandatory"));
						}else{
							
							if (!(errorCode = Validator.checkString(stockExchangeArr[i], true, 1, 30)).equals(Validator.ERROR_NONE)) {
								errors.add("stockExchangeNameError"+i, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), new Integer(1),
										new Integer(30)));
							}
						}
					}	
				}	
			}	
		}

		if (StockListAction.EVENT_REMOVE.equals(event)) {
			// Check that there is at least one selected checkbox.
			if ((form.getChkDeletes() == null) || (form.getChkDeletes().length == 0)) {
				errors.add("chkDeletes", new ActionMessage(CHKBOX_MANDATORY));
			}
		}

		if (StockListAction.EVENT_APPROVE.equals(event) || StockListAction.EVENT_REJECT.equals(event)
				|| StockListAction.EVENT_CLOSE.equals(event)) {
			errorCode = RemarksValidatorUtil.checkRemarks(form.getRemarks(), false);
			
			if((StockListAction.EVENT_REJECT.equals(event)) && (form.getRemarks()==null || form.getRemarks().trim().equals(""))){
				errors.add("remarks",new ActionMessage("error.string.mandatory"));
			}else{
				if (!Validator.ERROR_NONE.equals(errorCode)) {
					errors.add("remarks", RemarksValidatorUtil.getErrorMessage(errorCode));
				}
			}	

		}

		if (StockListAction.EVENT_PREPARE.equals(event) || StockListAction.EVENT_CHECKER_PREPARE.equals(event)) {
			if (!(errorCode = Validator.checkString(form.getSubType(), true, 1, 20)).equals(Validator.ERROR_NONE)) {
				errors.add("subType", new ActionMessage("error.string.mandatory", "1", "20"));
			}
			if (!(errorCode = Validator.checkString(form.getStockType(), true, 1, 20)).equals(Validator.ERROR_NONE)) {
				errors.add("stockType", new ActionMessage("error.string.mandatory", "1", "20"));
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

	private static final String NUM_LT = "error.number.lessthan";

	private static final String NUM_MANDATORY = "error.number.mandatory";

	private static final String CHKBOX_MANDATORY = "error.checkbox.mandatory";
}
