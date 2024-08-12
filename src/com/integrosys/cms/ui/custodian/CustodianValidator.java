package com.integrosys.cms.ui.custodian;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.cms.app.custodian.proxy.ICustodianProxyManager;
import com.integrosys.cms.app.custodian.proxy.CustodianProxyManagerFactory;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Command class to add the new bizstructure by admin maker on the corresponding
 * event...
 * @author $Author: hltan $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/07/03 03:50:04 $ Tag: $Name: $
 */
public class CustodianValidator extends AbstractCommand implements ICommonEventConstant {

	public static ActionErrors validateInput(CustodianForm form, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errorCode = "";
                                                        
		if ((form.getDocReasons() != null) && (form.getDocReasons().length() > 0)) {
			if (!(errorCode = Validator.checkString(form.getDocReasons(), false, 1, 250)).equals(Validator.ERROR_NONE)) {
				errors.add("reasonError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
						"250"));
			}
		}
		if ((form.getRemarks() != null) && (form.getRemarks().length() > 0)) {
			if (!(errorCode = Validator.checkString(form.getRemarks(), false, 1, 250)).equals(Validator.ERROR_NONE)) {
				errors.add("remarksError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
						"250"));
			}
		}
		if (form.getEvent().equals("print_lodgement_memo") || form.getEvent().equals("print_withdrawl_memo") || form.getEvent().equals("print_reversal_memo")) {
			if ((form.getCheckListItemRef() == null) || (form.getCheckListItemRef().length < 1)) {
				errors.add("selectIdError", new ActionMessage("error.custid.sel"));
			}
			else {
				if (!(errorCode = Validator.checkString(form.getAuthzName1(), true, 1, 30))
						.equals(Validator.ERROR_NONE)) {
					errors.add("authzName1Error", new ActionMessage(ErrorKeyMapper
							.map(ErrorKeyMapper.STRING, errorCode), "1", "30"));
				}
				if (!(errorCode = Validator.checkString(form.getSignNum1(), true, 1, 10)).equals(Validator.ERROR_NONE)) {
					errors.add("signNum1Error", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
							"1", "10"));
				}
				if (!(errorCode = Validator.checkString(form.getAuthzName2(), false, 1, 30))
						.equals(Validator.ERROR_NONE)) {
					errors.add("authzName2Error", new ActionMessage(ErrorKeyMapper
							.map(ErrorKeyMapper.STRING, errorCode), "1", "30"));
				}
				if (!(errorCode = Validator.checkString(form.getSignNum2(), false, 1, 10)).equals(Validator.ERROR_NONE)) {
					errors.add("signNum2Error", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
							"1", "10"));
				}
				if ((((form.getAuthzName2() != null) && (form.getAuthzName2().length() > 0)) && !((form.getSignNum2() != null) && (form
						.getSignNum2().length() > 0)))
						|| (!((form.getAuthzName2() != null) && (form.getAuthzName2().length() > 0)) && ((form
								.getSignNum2() != null) && (form.getSignNum2().length() > 0)))) {
					errors.add("printErrror", new ActionMessage("error.authz2"));
				}
			}
		}
        if (form.getEvent().equals("lodge_custodian_maker") || form.getEvent().equals("relodge_custodian_maker")) {
            if (AbstractCommonMapper.isEmptyOrNull(form.getCustDocItemBarcode())) {
                errors.add("custDocItemBarcodeError", new ActionMessage("error.string.mandatory"));
            }else if(checkDocItemBarcodeExist(form.getCustDocItemBarcode(), Long.valueOf(form.getCheckListItemRef()[0]).longValue())){//only have one checkListItemRefID for Lodge and Relodge
                errors.add("custDocItemBarcodeError", new ActionMessage("error.ccdoc.docitem.barcodeIsExist"));
            }

            if (AbstractCommonMapper.isEmptyOrNull(form.getSecEnvelopeBarcode())) {
                errors.add("secEnvBarcodeError", new ActionMessage("error.string.mandatory"));
            }else if(!checkSecEnvelopeBarcodeExist(form.getLimitProfile(),form.getSecEnvelopeBarcode())){
                errors.add("secEnvBarcodeError", new ActionMessage("error.ccdoc.envelope.barcodeNotExist"));    
            }
        }else if (form.getEvent().equals("tempuplift_custodian_maker") || form.getEvent().equals("permuplift_custodian_maker") || form.getEvent().equals("lodgereversal_custodian_maker")){
            if (!form.getCustDocItemBarcodeTmp().equals(form.getCustDocItemBarcode())) {

                errors.add("custDocItemBarcodeError", new ActionMessage("error.ccdoc.docitem.barcodeInvalid"));
            }
            if (!form.getSecEnvelopeBarcodeTmp().equals(form.getSecEnvelopeBarcode())) {
                errors.add("secEnvBarcodeError", new ActionMessage("error.ccdoc.envelope.barcodeInvalid"));
            }
        }
		DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		return errors;
	}

    public static boolean checkDocItemBarcodeExist(String currDocItemBarcode, long checkListItemRefID){
        boolean isUniqueDocItemBarcode = false;
        ICustodianProxyManager proxy = CustodianProxyManagerFactory.getCustodianProxyManager();
        isUniqueDocItemBarcode = proxy.getCheckDocItemBarcodeExist(currDocItemBarcode, checkListItemRefID);
        return isUniqueDocItemBarcode;
    }

    public static boolean checkSecEnvelopeBarcodeExist(String limitprofile, String currSecEnvBarcode){
        boolean isValidEnvBarcode = false;
        ICustodianProxyManager proxy = CustodianProxyManagerFactory.getCustodianProxyManager();
        isValidEnvBarcode = proxy.getCheckEnvelopeBarcodeExist(Long.parseLong(limitprofile), currSecEnvBarcode);
        return isValidEnvBarcode;
    }

}