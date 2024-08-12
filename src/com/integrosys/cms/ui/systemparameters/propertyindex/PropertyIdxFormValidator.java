package com.integrosys.cms.ui.systemparameters.propertyindex;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.RemarksValidatorUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;

import java.util.Locale;

/**
 * Describe this class.
 * Purpose: Validation for property valuation by index
 * Description: Validate the value that user key in
 *
 * @author $Author$
 * @version $Revision$
 * @since $Date$
 *        Tag: $Name$
 */

public class PropertyIdxFormValidator {

    public static ActionErrors validatePropertyIdxForm(ActionForm aForm, Locale locale) {

        String errorCode = null;
        ActionErrors errors = new ActionErrors();

        try {
            PropertyIdxForm propertyForm = (PropertyIdxForm) aForm;

            String event = propertyForm.getEvent();

            if (event.equals("checker_confirm_approve_create") || event.equals("checker_confirm_approve_edit")
                    || event.equals("checker_confirm_approve_delete") || event.equals("checker_confirm_reject_create")
                    || event.equals("checker_confirm_reject_edit") || event.equals("checker_confirm_reject_delete")
                    || event.equals("maker_confirm_resubmit_edit")) {

                if (!(errorCode = RemarksValidatorUtil.checkRemarks(propertyForm.getRemarks(), false)).equals(Validator.ERROR_NONE)) {
                    errors.add("remarks", RemarksValidatorUtil.getErrorMessage(errorCode));

                }
            } else {
                if (propertyForm.getSelectedPropertySubtype() == null || propertyForm.getSelectedPropertySubtype().length <= 0) {
                    errors.add("propertySubtypeError", new ActionMessage("error.property.param.propertySubtypeList"));
                } else {
                    if (propertyForm.getValuationDescription().equals("IRH")) {
                        String[] propertySubType = propertyForm.getSelectedPropertySubtype();
                        boolean isExists = false;
                        for (int i = 0; i < propertySubType.length; i++) {
                            if (propertySubType[i].equals("PT704")) {
                                isExists = true;
                                break;
                            }
                        }

                        if (!isExists) {
                            errors.add("residentialHousesError", new ActionMessage("error.property.param.subtype.residentialHouses"));
                        }
                    }
                }

                if (!(errorCode = Validator.checkString(propertyForm.getValuationDescription(), true, 1, 10)).equals(Validator.ERROR_NONE)) {
                    errors.add("valuationDescriptionError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "10"));
                }
            }

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return errors;
    }

    public static ActionErrors validatePropertyIdxItemForm(ActionForm aForm, Locale locale) {
        String errorCode = null;
        ActionErrors errors = new ActionErrors();
        DefaultLogger.debug(PropertyIdxFormValidator.class, "locale from common action >>>>>>>>>>" + locale);
        try {
            PropertyIdxItemForm itemForm = (PropertyIdxItemForm) aForm;

            String idxValueRegex = "[\\.0-9]*";

            if (itemForm.getValDesc().equals("ISTP")) {

                if (itemForm.getSelectedPropertyType() == null || itemForm.getSelectedPropertyType().length <= 0) {
                    errors.add("propertyTypeError", new ActionMessage("error.property.param.propertyTypeList"));
                }

                if (!(errorCode = Validator.checkString(itemForm.getState(), true, 1, 10)).equals(Validator.ERROR_NONE)) {
                    errors.add("stateError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "10"));
                }

                if (AbstractCommonMapper.isEmptyOrNull(itemForm.getIdxYear())) {
                    errors.add("yearError", new ActionMessage("error.string.mandatory"));
                }

                if (AbstractCommonMapper.isEmptyOrNull(itemForm.getIdxType())) {
                    errors.add("typeError", new ActionMessage("error.string.mandatory"));
                }

                if (AbstractCommonMapper.isEmptyOrNull(itemForm.getIdxValue())) {
                    errors.add("valueError", new ActionMessage("error.string.mandatory"));
                } else {

                    if (!Validator.checkPattern(itemForm.getIdxValue(), idxValueRegex)) {
                        errors.add("valueError", new ActionMessage("error.property.item.numeric"));
                    } else
                    if (!(errorCode = Validator.checkNumber(itemForm.getIdxValue(), true, 1, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_4, 5, locale)).equals(Validator.ERROR_NONE)) {
                        if (errorCode.equals("greaterthan") || errorCode.equals("lessthan")) {
                            errors.add("valueError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, "heightlessthan"), "1", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_4_STR));

                        } else if (errorCode.equals("decimalexceeded")) {
                            errors.add("valueError", new ActionMessage("error.number.moredecimalexceeded", "", "", "4"));

                        } else if (!errorCode.equals("mandatory")) {
                            errors.add("valueError", new ActionMessage("error.number." + errorCode));
                        }
                    }
                }

            } else if (itemForm.getValDesc().equals("ITP")) {

                if (itemForm.getSelectedPropertyType() == null || itemForm.getSelectedPropertyType().length <= 0) {
                    errors.add("propertyTypeError", new ActionMessage("error.property.param.propertyTypeList"));
                }

                if (AbstractCommonMapper.isEmptyOrNull(itemForm.getIdxYear())) {
                    errors.add("yearError", new ActionMessage("error.string.mandatory"));
                }

                if (AbstractCommonMapper.isEmptyOrNull(itemForm.getIdxType())) {
                    errors.add("typeError", new ActionMessage("error.string.mandatory"));
                }

                if (AbstractCommonMapper.isEmptyOrNull(itemForm.getIdxValue())) {
                    errors.add("valueError", new ActionMessage("error.string.mandatory"));
                } else {

                    if (!Validator.checkPattern(itemForm.getIdxValue(), idxValueRegex)) {
                        errors.add("valueError", new ActionMessage("error.property.item.numeric"));
                    } else
                    if (!(errorCode = Validator.checkNumber(itemForm.getIdxValue(), true, 1, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_4, 5, locale)).equals(Validator.ERROR_NONE)) {
                        if (errorCode.equals("greaterthan") || errorCode.equals("lessthan")) {
                            errors.add("valueError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, "heightlessthan"), "1", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_4_STR));

                        } else if (errorCode.equals("decimalexceeded")) {
                            errors.add("valueError", new ActionMessage("error.number.moredecimalexceeded", "", "", "4"));

                        } else if (!errorCode.equals("mandatory")) {
                            errors.add("valueError", new ActionMessage("error.number." + errorCode));
                        }
                    }
                }

            } else if (itemForm.getValDesc().equals("ID")) {
                if (!(errorCode = Validator.checkString(itemForm.getState(), true, 1, 10)).equals(Validator.ERROR_NONE)) {
                    errors.add("stateError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "10"));
                }

                if (itemForm.getSelectedDistrict() == null || itemForm.getSelectedDistrict().length <= 0) {
                    errors.add("districtError", new ActionMessage("error.property.param.districtList"));
                }

                if (AbstractCommonMapper.isEmptyOrNull(itemForm.getIdxYear())) {
                    errors.add("yearError", new ActionMessage("error.string.mandatory"));
                }

                if (AbstractCommonMapper.isEmptyOrNull(itemForm.getIdxType())) {
                    errors.add("typeError", new ActionMessage("error.string.mandatory"));
                }

                if (AbstractCommonMapper.isEmptyOrNull(itemForm.getIdxValue())) {
                    errors.add("valueError", new ActionMessage("error.string.mandatory"));
                } else {

                    if (!Validator.checkPattern(itemForm.getIdxValue(), idxValueRegex)) {
                        errors.add("valueError", new ActionMessage("error.property.item.numeric"));
                    } else
                    if (!(errorCode = Validator.checkNumber(itemForm.getIdxValue(), true, 1, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_4, 5, locale)).equals(Validator.ERROR_NONE)) {
                        if (errorCode.equals("greaterthan") || errorCode.equals("lessthan")) {
                            errors.add("valueError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, "heightlessthan"), "1", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_4_STR));

                        } else if (errorCode.equals("decimalexceeded")) {
                            errors.add("valueError", new ActionMessage("error.number.moredecimalexceeded", "", "", "4"));

                        } else if (!errorCode.equals("mandatory")) {
                            errors.add("valueError", new ActionMessage("error.number." + errorCode));
                        }
                    }
                }

            } else if (itemForm.getValDesc().equals("IS")) {

                if (!(errorCode = Validator.checkString(itemForm.getState(), true, 1, 10)).equals(Validator.ERROR_NONE)) {
                    errors.add("stateError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "10"));
                }

                if (AbstractCommonMapper.isEmptyOrNull(itemForm.getIdxType())) {
                    errors.add("typeError", new ActionMessage("error.string.mandatory"));
                }

                if (AbstractCommonMapper.isEmptyOrNull(itemForm.getIdxYear())) {
                    errors.add("yearError", new ActionMessage("error.string.mandatory"));
                }

                if (AbstractCommonMapper.isEmptyOrNull(itemForm.getIdxValue())) {
                    errors.add("valueError", new ActionMessage("error.string.mandatory"));
                } else {

                    if (!Validator.checkPattern(itemForm.getIdxValue(), idxValueRegex)) {
                        errors.add("valueError", new ActionMessage("error.property.item.numeric"));
                    } else
                    if (!(errorCode = Validator.checkNumber(itemForm.getIdxValue(), true, 1, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_4, 5, locale)).equals(Validator.ERROR_NONE)) {
                        if (errorCode.equals("greaterthan") || errorCode.equals("lessthan")) {
                            errors.add("valueError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, "heightlessthan"), "1", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_4_STR));

                        } else if (errorCode.equals("decimalexceeded")) {
                            errors.add("valueError", new ActionMessage("error.number.moredecimalexceeded", "", "", "4"));

                        } else if (!errorCode.equals("mandatory")) {
                            errors.add("valueError", new ActionMessage("error.number." + errorCode));
                        }
                    }
                }

            } else if (itemForm.getValDesc().equals("IRH")) {

                if (AbstractCommonMapper.isEmptyOrNull(itemForm.getIdxYear())) {
                    errors.add("yearError", new ActionMessage("error.string.mandatory"));
                }

                if (AbstractCommonMapper.isEmptyOrNull(itemForm.getIdxType())) {
                    errors.add("typeError", new ActionMessage("error.string.mandatory"));
                }

                if (AbstractCommonMapper.isEmptyOrNull(itemForm.getIdxValue())) {
                    errors.add("valueError", new ActionMessage("error.string.mandatory"));
                } else {

                    if (!Validator.checkPattern(itemForm.getIdxValue(), idxValueRegex)) {
                        errors.add("valueError", new ActionMessage("error.property.item.numeric"));
                    } else
                    if (!(errorCode = Validator.checkNumber(itemForm.getIdxValue(), true, 1, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_4, 5, locale)).equals(Validator.ERROR_NONE)) {
                        if (errorCode.equals("greaterthan") || errorCode.equals("lessthan")) {
                            errors.add("valueError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, "heightlessthan"), "1", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_4_STR));

                        } else if (errorCode.equals("decimalexceeded")) {
                            errors.add("valueError", new ActionMessage("error.number.moredecimalexceeded", "", "", "4"));

                        } else if (!errorCode.equals("mandatory")) {
                            errors.add("valueError", new ActionMessage("error.number." + errorCode));
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return errors;
    }
}
