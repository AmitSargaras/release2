/**
 * Copyrfight Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/limit/CoBorrowerMapper.java,v 1.16 2006/09/27 06:09:07 hshii Exp $
 */

package com.integrosys.cms.ui.limit.limitaccount;

import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICustomerSysXRef;
import com.integrosys.cms.app.customer.bus.OBCustomerSysXRef;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.ui.manualinput.limit.MILimitUIHelper;

public class LimitAccountMapper extends AbstractCommonMapper {

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "service.account", "com.integrosys.cms.app.customer.bus.ICustomerSysXRef", SERVICE_SCOPE },
				{ "service.limitTrxValue", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE }, });
	}

	public Object mapFormToOB(CommonForm commonForm, HashMap inputs) throws MapperException {
		LimitAccountForm aForm = (LimitAccountForm) commonForm;
		int indexID = Integer.parseInt((String) inputs.get("indexID"));

		ICustomerSysXRef account = null;
		if (indexID == -1) {
			account = new OBCustomerSysXRef();
		}
		else {
			account = (ICustomerSysXRef) inputs.get("service.account");
		}

		account.setExternalSystemCodeNum(ICMSConstant.CATEGORY_SOURCE_SYSTEM);
		account.setExternalSysCountry(aForm.getHostSystemCountry());
		account.setExternalSystemCode(aForm.getHostSystemName());
		account.setExternalAccountNo(aForm.getAccountNo());
		account.setBookingLoctnCountry(aForm.getHostSystemCountry());
		return account;
	}

	public CommonForm mapOBToForm(CommonForm commonForm, Object obj, HashMap inputs) throws MapperException {
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		try {
			MILimitUIHelper helper = new MILimitUIHelper();
			ICustomerSysXRef account = (ICustomerSysXRef) obj;
			LimitAccountForm aForm = (LimitAccountForm) commonForm;
			aForm.setHostSystemCountry(account.getBookingLoctnCountry());
			aForm.setHostSystemName(account.getExternalSystemCode());
			aForm.setAccountNo(account.getExternalAccountNo());
			if (account.getAcctEffectiveDate() != null) {
				aForm.setEffectiveDate(DateUtil.formatDate(locale, account.getAcctEffectiveDate()));
			}
			if (account.getAccountStatus() != null) {
				aForm.setAcctClassification(helper.getAccountClassification(account.getAccountDelinq()));
				aForm.setAcctStatus(helper.getAccountStatusDisp(account.getAccountStatus()));
			}
			if (account.getRVForAccount() != null) {
				aForm.setRealizableVal(CurrencyManager.convertToString(locale, account.getRVForAccount()));
				aForm.setRvCcy(account.getRVForAccount().getCurrencyCode());
			}

			if (account.getXRefID() > 0) {
				ILimitTrxValue trxValue = (ILimitTrxValue) (inputs.get("service.limitTrxValue"));
				ILimit stgLmt = trxValue.getStagingLimit();
				if ((stgLmt != null) && (stgLmt.getApprovedLimitAmount() != null)
						&& (stgLmt.getApprovedLimitAmount().getCurrencyCode() != null)) {
					Amount amt = helper.getSBMILmtProxy().getNetoutStandingForAccount(
							String.valueOf(account.getXRefID()), stgLmt.getApprovedLimitAmount().getCurrencyCode());
					if (amt != null) {
						aForm.setNetOutstandingAmt(CurrencyManager.convertToString(locale, amt));
						aForm.setNetOutstandingCcy(amt.getCurrencyCode());
					}
				}
			}
			return aForm;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new MapperException();
		}
	}

}
