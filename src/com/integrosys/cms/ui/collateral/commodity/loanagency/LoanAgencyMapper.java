/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/loanagency/LoanAgencyMapper.java,v 1.41 2006/09/15 12:42:16 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.loanagency;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.base.uiinfra.mapper.MapperUtil;
import com.integrosys.cms.app.collateral.bus.type.commodity.IBorrower;
import com.integrosys.cms.app.collateral.bus.type.commodity.IGuarantor;
import com.integrosys.cms.app.collateral.bus.type.commodity.ILoanAgency;
import com.integrosys.cms.app.collateral.bus.type.commodity.ILoanLimit;
import com.integrosys.cms.app.collateral.bus.type.commodity.ILoanSchedule;
import com.integrosys.cms.app.collateral.bus.type.commodity.IParticipant;
import com.integrosys.cms.app.collateral.bus.type.commodity.ISubLimit;
import com.integrosys.cms.app.collateral.bus.type.commodity.OBBorrower;
import com.integrosys.cms.app.collateral.bus.type.commodity.OBGuarantor;
import com.integrosys.cms.app.collateral.bus.type.commodity.OBLoanAgency;
import com.integrosys.cms.app.collateral.bus.type.commodity.OBLoanLimit;
import com.integrosys.cms.app.collateral.bus.type.commodity.OBLoanSchedule;
import com.integrosys.cms.app.collateral.bus.type.commodity.OBParticipant;
import com.integrosys.cms.app.collateral.bus.type.commodity.OBSubLimit;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.limit.bus.ICoBorrowerLimit;
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.ui.collateral.CollateralMapper;
import com.integrosys.cms.ui.collateral.commodity.CommodityMainAction;
import com.integrosys.cms.ui.collateral.commodity.CommodityMainConstant;
import com.integrosys.cms.ui.collateral.commodity.CommodityMainUtil;
import com.integrosys.cms.ui.common.CurrencyList;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.41 $
 * @since $Date: 2006/09/15 12:42:16 $ Tag: $Name: $
 */
public class LoanAgencyMapper extends AbstractCommonMapper {
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		LoanAgencyForm aForm = (LoanAgencyForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		String from_event = (String) inputs.get("from_page");
		OBLoanAgency obToChange = (OBLoanAgency) inputs.get("serviceLoanAgency");
		if (from_event.equals(LoanAgencyAction.EVENT_PREPARE_UPDATE)) {
			if (aForm.getEvent().equals(LoanAgencyAction.EVENT_REMOVE_SUBLIMIT)) {
				ISubLimit[] limitList = obToChange.getSubLimits();
				String[] deleteList = aForm.getDeleteSubLimit();
				Object[] objArr = deleteArr(limitList, deleteList);
				if (objArr != null) {
					limitList = new ISubLimit[objArr.length];
					for (int i = 0; i < objArr.length; i++) {
						limitList[i] = (ISubLimit) objArr[i];
					}
				}
				obToChange.setSubLimits(limitList);
			}
			if (aForm.getEvent().equals(LoanAgencyAction.EVENT_REMOVE_PARTICIPANT)) {
				IParticipant[] participantList = obToChange.getParticipants();
				String[] deleteList = aForm.getDeleteParticipant();
				Object[] objArr = deleteArr(participantList, deleteList);
				if (objArr != null) {
					participantList = new IParticipant[objArr.length];
					for (int i = 0; i < objArr.length; i++) {
						participantList[i] = (IParticipant) objArr[i];
					}
				}
				obToChange.setParticipants(participantList);
			}

			HashMap loanLimitMap = new HashMap();
			if (obToChange.getLimitIDs() != null) {
				ILoanLimit[] limitList = obToChange.getLimitIDs();
				for (int i = 0; i < limitList.length; i++) {
					loanLimitMap.put(CommodityMainUtil.getLoanLimitLimitID(limitList[i]), limitList[i]);
				}
			}
			if (aForm.getSelectedLimitID() != null) {
				ILoanLimit[] newLimitList = new ILoanLimit[aForm.getSelectedLimitID().length];
				for (int i = 0; i < newLimitList.length; i++) {
					ILoanLimit limit = (ILoanLimit) loanLimitMap.get(aForm.getSelectedLimitID()[i]);
					if (limit == null) {
						limit = new OBLoanLimit();
						String strLmtID = aForm.getSelectedLimitID()[i];
						long lmtID = Long.parseLong(strLmtID.substring(2, strLmtID.length()));
						if (strLmtID.substring(0, 2).equals(ICMSConstant.CUSTOMER_CATEGORY_MAIN_BORROWER)) {
							limit.setLimitID(lmtID);
							limit.setCustomerCategory(ICMSConstant.CUSTOMER_CATEGORY_MAIN_BORROWER);
						}
						else if (strLmtID.substring(0, 2).equals(ICMSConstant.CUSTOMER_CATEGORY_CO_BORROWER)) {
							limit.setCoBorrowerLimitID(lmtID);
							limit.setCustomerCategory(ICMSConstant.CUSTOMER_CATEGORY_CO_BORROWER);
						}
					}
					newLimitList[i] = limit;
				}
				obToChange.setLimitIDs(newLimitList);
			}
			else {
				obToChange.setLimitIDs(null);
			}

			if (isEmptyOrNull(aForm.getAdminAgentName())) {
				obToChange.setAdministrativeAgentName(null);
			}
			else {
				obToChange.setAdministrativeAgentName(aForm.getAdminAgentName());
			}
			if (isEmptyOrNull(aForm.getColAgentName())) {
				obToChange.setCollateralAgentName(null);
			}
			else {
				obToChange.setCollateralAgentName(aForm.getColAgentName());
			}
			if (isEmptyOrNull(aForm.getDocumentAgent())) {
				obToChange.setDocumentAgent(null);
			}
			else {
				obToChange.setDocumentAgent(aForm.getDocumentAgent());
			}
			obToChange.setLCIssuingBank(aForm.getLcIssuingBank());

			String[] borrowerList = aForm.getBorrowerList();
			ArrayList borrowerArray = new ArrayList();
			if (borrowerList != null) {
				IBorrower[] borrowerArr = obToChange.getBorrowers();
				HashMap borrowerMap = new HashMap();
				if (borrowerArr != null) {
					for (int i = 0; i < borrowerArr.length; i++) {
						borrowerMap.put(borrowerArr[i].getName(), borrowerArr[i]);
					}
				}
				for (int i = 0; i < borrowerList.length; i++) {
					if (borrowerMap.get(borrowerList[i]) != null) {
						borrowerArray.add(borrowerMap.get(borrowerList[i]));
					}
					else {
						OBBorrower tempBorrower = new OBBorrower();
						tempBorrower.setName(borrowerList[i]);
						borrowerArray.add(tempBorrower);
					}
				}
			}
			obToChange.setBorrowers((IBorrower[]) borrowerArray.toArray(new IBorrower[0]));

			String[] guarantorList = aForm.getGuarantorList();
			ArrayList guarantorArray = new ArrayList();
			if (guarantorList != null) {
				HashMap guarantorMap = new HashMap();
				IGuarantor[] guarantorArr = obToChange.getGuarantors();
				if (guarantorArr != null) {
					for (int i = 0; i < guarantorArr.length; i++) {
						guarantorMap.put(guarantorArr[i].getName(), guarantorArr[i]);
					}
				}
				for (int i = 0; i < guarantorList.length; i++) {
					if (guarantorMap.get(guarantorList[i]) != null) {
						guarantorArray.add(guarantorMap.get(guarantorList[i]));
					}
					else {
						OBGuarantor tempGuarantor = new OBGuarantor();
						tempGuarantor.setName(guarantorList[i]);
						guarantorArray.add(tempGuarantor);
					}
				}
			}
			obToChange.setGuarantors((IGuarantor[]) guarantorArray.toArray(new IGuarantor[0]));
			if (!isEmptyOrNull(aForm.getAllowMultipleCurr())
					&& ICMSConstant.TRUE_VALUE.equals(aForm.getAllowMultipleCurr())) {
				obToChange.setIsMultipleCurrencies(true);
			}
			else {
				obToChange.setIsMultipleCurrencies(false);
			}
			obToChange.setGlobalCurrencies(aForm.getSelectedCurrency());
			obToChange.setGlobalAmount(UIUtil.convertToAmount(locale, aForm.getGlobalAmountCcy(), aForm
					.getGlobalAmountAmt()));
			if (isEmptyOrNull(aForm.getFacilityType())) {
				obToChange.setFacilityType(null);
			}
			else {
				obToChange.setFacilityType(aForm.getFacilityType());
			}
			obToChange.setFacilityCommitmentDate(CollateralMapper.compareDate(locale, obToChange
					.getFacilityCommitmentDate(), aForm.getFacCommitmentDate()));
			obToChange.setFacilityEffectiveDate(CollateralMapper.compareDate(locale, obToChange
					.getFacilityEffectiveDate(), aForm.getFacEffectiveDate()));
			obToChange.setFacilityMaturityDate(CollateralMapper.compareDate(locale, obToChange
					.getFacilityMaturityDate(), aForm.getFacMaturityDate()));
			obToChange.setFacilityTerminationDate(CollateralMapper.compareDate(locale, obToChange
					.getFacilityTerminationDate(), aForm.getFacTerminationDate()));
			obToChange.setLastDateToIssueLC(CollateralMapper.compareDate(locale, obToChange.getLastDateToIssueLC(),
					aForm.getLastDateIssueLC()));
			obToChange.setFinalLCMaturityDate(CollateralMapper.compareDate(locale, obToChange.getFinalLCMaturityDate(),
					aForm.getFinalLCMaturityDate()));
			obToChange.setTypeOfTransaction(aForm.getTransactionType());
			obToChange.setAgentBankCounselName(aForm.getCounselNameAgentBank());

			if (!aForm.getEvent().equals(LoanAgencyAction.EVENT_REMOVE_SUBLIMIT)) {
				if (aForm.getSubLimitID() != null) {
					String[] subLimitID = aForm.getSubLimitID();
					String[] subLimitCcy = aForm.getSubLimitCcy();
					String[] subLimitAmt = aForm.getSubLimitAmt();
					String[] subLimitType = aForm.getSubLimitFacType();
					HashMap subLimitMap = new HashMap();
					ISubLimit[] subLimitList = obToChange.getSubLimits();
					if (subLimitList != null) {
						for (int i = 0; i < subLimitList.length; i++) {
							subLimitMap.put(String.valueOf(subLimitList[i].getSubLimitID()), subLimitList[i]);
						}
					}
					ISubLimit[] newList = new ISubLimit[subLimitID.length];
					for (int i = 0; i < subLimitID.length; i++) {
						OBSubLimit temp;
						if ((subLimitID[i] != null)
								&& !subLimitID[i].equals(String.valueOf(ICMSConstant.LONG_INVALID_VALUE))) {
							temp = (OBSubLimit) subLimitMap.get(subLimitID[i]);
						}
						else {
							temp = new OBSubLimit();
							temp.setSubLimitID(ICMSConstant.LONG_INVALID_VALUE);
						}
						temp.setAmount(UIUtil.convertToAmount(locale, subLimitCcy[i], subLimitAmt[i]));
						temp.setFacilityType(subLimitType[i]);
						newList[i] = temp;
					}
					obToChange.setSubLimits(newList);
				}
				else {
					obToChange.setSubLimits(null);
				}
			}

			if (!aForm.getEvent().equals(LoanAgencyAction.EVENT_REMOVE_PARTICIPANT)) {
				if (aForm.getParticipantID() != null) {
					String[] participantID = aForm.getParticipantID();
					String[] name = aForm.getParticipant();
					String[] allocatedAmt = aForm.getAllocatedAmt();
					String[] pricing = aForm.getPricing();
					HashMap participantMap = new HashMap();
					IParticipant[] participantList = obToChange.getParticipants();
					if (participantList != null) {
						for (int i = 0; i < participantList.length; i++) {
							participantMap.put(String.valueOf(participantList[i].getParticipantID()),
									participantList[i]);
						}
					}
					IParticipant[] newList = new IParticipant[participantID.length];
					String ccyCode = null;
					if (obToChange.getGlobalAmount() != null) {
						ccyCode = obToChange.getGlobalAmount().getCurrencyCode();
					}
					for (int i = 0; i < participantID.length; i++) {
						OBParticipant temp;
						if ((participantID[i] != null)
								&& !participantID[i].equals(String.valueOf(ICMSConstant.LONG_INVALID_VALUE))) {
							temp = (OBParticipant) participantMap.get(participantID[i]);
						}
						else {
							temp = new OBParticipant();
							temp.setParticipantID(ICMSConstant.LONG_INVALID_VALUE);
						}
						temp.setName(name[i]);
						temp.setAllocatedAmount(UIUtil.convertToAmount(locale, ccyCode, allocatedAmt[i]));
						temp.setRemarks(pricing[i]);
						newList[i] = temp;
					}
					obToChange.setParticipants(newList);
				}
				else {
					obToChange.setParticipants(null);
				}
			}
			boolean isDebtRateChange = false;
			if (isEmptyOrNull(aForm.getDebtRate())) {
				if (obToChange.getDebtRate() != ICMSConstant.DOUBLE_INVALID_VALUE) {
					isDebtRateChange = true;
				}
				obToChange.setDebtRate(ICMSConstant.DOUBLE_INVALID_VALUE);
			}
			else {
				try {
					double debtRate = MapperUtil.mapStringToDouble(aForm.getDebtRate(), locale);
					if (debtRate != obToChange.getDebtRate()) {
						isDebtRateChange = true;
					}
					obToChange.setDebtRate(debtRate);
				}
				catch (Exception e) {
					throw new MapperException(e.getMessage());
				}
			}

			if (!isEmptyOrNull(aForm.getInstalmentEqualType())
					&& aForm.getInstalmentEqualType().equals(ICMSConstant.TRUE_VALUE)) {
				boolean isChanged = false;
				if (!obToChange.getIsEqualInstalments()) {
					isChanged = true;
				}
				if (!isChanged) {
					try {
						isChanged = isPrincipleAmountChanged(obToChange.getPrincipleAmount(), aForm
								.getEqualPrinAmtCcy(), aForm.getEqualPrinAmtVal(), locale);
					}
					catch (Exception e) {
						throw new MapperException(e.getMessage());
					}
				}
				obToChange.setPrincipleAmount(UIUtil.convertToAmount(locale, aForm.getEqualPrinAmtCcy(), aForm
						.getEqualPrinAmtVal()));
				int numInstal = -1;
				if (isEmptyOrNull(aForm.getEqualNoInstalments())) {
					if (!isChanged && (obToChange.getNumberOfInstalments() != 0)) {
						isChanged = true;
					}
					obToChange.setNumberOfInstalments(0);
				}
				else {
					numInstal = Integer.parseInt(aForm.getEqualNoInstalments());
					DefaultLogger.debug(this, "old num of instalments: " + obToChange.getNumberOfInstalments()
							+ " \t new : " + numInstal);
					if (!isChanged && (obToChange.getNumberOfInstalments() != numInstal)) {
						isChanged = true;
					}
					obToChange.setNumberOfInstalments(numInstal);
				}
				if (!isChanged
						&& (((obToChange.getFrequencyOfPayment() == null) && !isEmptyOrNull(aForm.getEqualPaymentFreq())) || ((obToChange
								.getFrequencyOfPayment() != null) && !obToChange.getFrequencyOfPayment().equals(
								aForm.getEqualPaymentFreq())))) {
					isChanged = true;
				}
				obToChange.setFrequencyOfPayment(aForm.getEqualPaymentFreq());
				Date paymentDate = CollateralMapper.compareDate(locale, obToChange.getDateOfPayment(), aForm
						.getEqualFirstPaymentDate());
				if (!isChanged
						&& (((obToChange.getDateOfPayment() == null) && !isEmptyOrNull(aForm.getEqualFirstPaymentDate())) || ((obToChange
								.getDateOfPayment() != null) && !obToChange.getDateOfPayment().equals(paymentDate)))) {
					isChanged = true;
				}
				obToChange.setDateOfPayment(paymentDate);
				isChanged = (isChanged || isDebtRateChange);
				DefaultLogger.debug(this, "<<<<<<<<<<<<<<<<<<<<<< HSHII: no of instalment: "
						+ obToChange.getNumberOfInstalments() + "\tpayment date: " + obToChange.getDateOfPayment());
				if (isChanged && (numInstal > 0) && (obToChange.getDateOfPayment() != null)
						&& !isEmptyOrNull(obToChange.getFrequencyOfPayment())
						&& (obToChange.getPrincipleAmount() != null)
						&& (obToChange.getPrincipleAmount().getAmount() > 0)) {
					ILoanSchedule[] scheduleList = setEqualInstalmentLoanSchedule(obToChange.getLoanSchedules(),
							obToChange.getDateOfPayment(), obToChange.getPrincipleAmount(), numInstal, obToChange
									.getFrequencyOfPayment(), obToChange.getDebtRate());
					obToChange.setLoanSchedules(scheduleList);
				}
				else if (numInstal <= 0) {
					obToChange.setLoanSchedules(null);
				}
				obToChange.setIsEqualInstalments(true);
			}
			else {
				int numInstal = -1;
				boolean isChanged = false;
				if (obToChange.getIsEqualInstalments()) {
					isChanged = true;
				}
				obToChange.setIsEqualInstalments(false);
				if (!isChanged) {
					try {
						isChanged = isPrincipleAmountChanged(obToChange.getPrincipleAmount(), aForm
								.getNonEqPrinAmtCcy(), aForm.getNonEqPrinAmtVal(), locale);
					}
					catch (Exception e) {
						throw new MapperException(e.getMessage());
					}
				}
				obToChange.setPrincipleAmount(UIUtil.convertToAmount(locale, aForm.getNonEqPrinAmtCcy(), aForm
						.getNonEqPrinAmtVal()));
				if (isEmptyOrNull(aForm.getNonEqNoInstalments())) {
					if (!isChanged && (obToChange.getNumberOfInstalments() > 0)) {
						isChanged = true;
					}
					obToChange.setNumberOfInstalments(0);
				}
				else {
					numInstal = Integer.parseInt(aForm.getNonEqNoInstalments());
					if (!isChanged && (obToChange.getNumberOfInstalments() != numInstal)) {
						isChanged = true;
					}
					obToChange.setNumberOfInstalments(numInstal);
				}
				if (!isChanged
						&& (((obToChange.getFrequencyOfPayment() == null) && !isEmptyOrNull(aForm.getNonEqPaymentFreq())) || ((obToChange
								.getFrequencyOfPayment() != null) && !obToChange.getFrequencyOfPayment().equals(
								aForm.getNonEqPaymentFreq())))) {
					isChanged = true;
				}
				obToChange.setFrequencyOfPayment(aForm.getNonEqPaymentFreq());
				Date paymentDate = CollateralMapper.compareDate(locale, obToChange.getDateOfPayment(), aForm
						.getNonEqFirstPaymentDate());
				if (!isChanged
						&& (((paymentDate == null) && (obToChange.getDateOfPayment() != null)) || ((paymentDate != null) && !paymentDate
								.equals(obToChange.getDateOfPayment())))) {
					isChanged = true;
				}
				obToChange.setDateOfPayment(paymentDate);

				if (isChanged && (numInstal > 0) && (obToChange.getDateOfPayment() != null)
						&& (obToChange.getPrincipleAmount() != null)
						&& (obToChange.getPrincipleAmount().getAmount() > 0)
						&& (obToChange.getFrequencyOfPayment() != null)) {
					ILoanSchedule[] scheduleList = obToChange.getLoanSchedules();
					ILoanSchedule[] newList = new ILoanSchedule[numInstal];
					int numMonth = getNumMonthByPaymentFreq(obToChange.getFrequencyOfPayment());
					GregorianCalendar cal = new GregorianCalendar();
					cal.setTime(obToChange.getDateOfPayment());
					for (int i = 0; i < numInstal; i++) {
						OBLoanSchedule obj = new OBLoanSchedule();
						if ((scheduleList != null) && (i < scheduleList.length)) {
							obj = (OBLoanSchedule) scheduleList[i];
						}
						obj.setPaymentDate(cal.getTime());
						cal.add(Calendar.MONTH, numMonth);
						obj.setPrincipalDueAmount(null);
						obj.setInterestDueAmount(null);

						newList[i] = obj;
					}
					obToChange.setLoanSchedules(newList);
				}
				else if (numInstal <= 0) {
					obToChange.setLoanSchedules(null);
				}
			}

			if (isEmptyOrNull(aForm.getCalculateBase())) {
				obToChange.setCalculationBaseNumberOfDays(0);
			}
			else {
				obToChange.setCalculationBaseNumberOfDays(Integer.parseInt(aForm.getCalculateBase()));
			}

			if (!isEmptyOrNull(aForm.getTermOutOption()) && aForm.getTermOutOption().equals(ICMSConstant.TRUE_VALUE)) {
				obToChange.setIsTermOutOption(true);
			}
			else {
				obToChange.setIsTermOutOption(false);
			}
			if (!isEmptyOrNull(aForm.getPrePaymentOption())
					&& aForm.getPrePaymentOption().equals(ICMSConstant.TRUE_VALUE)) {
				obToChange.setIsPrepaymentOption(true);
			}
			else {
				obToChange.setIsPrepaymentOption(false);
			}
			obToChange.setPrepaymentMinAmount(UIUtil.convertToAmount(locale, aForm.getPrePaymentMinCcy(), aForm
					.getPrePaymentMinAmt()));
			if (!isEmptyOrNull(aForm.getPrePaymentPenalty())
					&& aForm.getPrePaymentPenalty().equals(ICMSConstant.TRUE_VALUE)) {
				obToChange.setIsPrepaymentPenalty(true);
			}
			else {
				obToChange.setIsPrepaymentPenalty(false);
			}

			if (isEmptyOrNull(aForm.getNumDayNotice())) {
				obToChange.setNumberOfNoticeDays(0);
			}
			else {
				obToChange.setNumberOfNoticeDays(Integer.parseInt(aForm.getNumDayNotice()));
			}

			obToChange.setGoverningLaw(aForm.getGoverningLaw());
			if (isEmptyOrNull(aForm.getInterestDuration())) {
				obToChange.setInterestPeriodDuration(0);
			}
			else {
				obToChange.setInterestPeriodDuration(Integer.parseInt(aForm.getInterestDuration()));
			}
			obToChange.setInterestPeriodDurationUnit(aForm.getInterestDurationUnit());

			if (isEmptyOrNull(aForm.getMaxLoanOutstanding())) {
				obToChange.setMaxNumberOfLoanOutstanding(0);
			}
			else {
				obToChange.setMaxNumberOfLoanOutstanding(Integer.parseInt(aForm.getMaxLoanOutstanding()));
			}
			obToChange.setMinDrawdownAmountAllowed(UIUtil.convertToAmount(locale, aForm.getMinDrawdownAllowCcy(), aForm
					.getMinDrawdownAllowAmt()));
			obToChange.setMaxDrawdownAmountAllowed(UIUtil.convertToAmount(locale, aForm.getMaxDrawdownAllowCcy(), aForm
					.getMaxDrawdownAllowAmt()));
			if (isEmptyOrNull(aForm.getMinNumDrawdownAllow())) {
				obToChange.setMinNumberOfDrawdownsAllowed(0);
			}
			else {
				obToChange.setMinNumberOfDrawdownsAllowed(Integer.parseInt(aForm.getMinNumDrawdownAllow()));
			}

			if (isEmptyOrNull(aForm.getMaxNumDrawdownAllow())) {
				obToChange.setMaxNumberOfDrawdownsAllowed(0);
			}
			else {
				obToChange.setMaxNumberOfDrawdownsAllowed(Integer.parseInt(aForm.getMaxNumDrawdownAllow()));
			}
			obToChange.setMinAssignmentFees(UIUtil.convertToAmount(locale, aForm.getMinAssignmentFeeCcy(), aForm
					.getMinAssignmentFeeAmt()));
			obToChange.setMaxAssignmentFees(UIUtil.convertToAmount(locale, aForm.getMaxAssignmentFeeCcy(), aForm
					.getMaxAssignmentFeeAmt()));
			if (!isEmptyOrNull(aForm.getBorrowerConsent())
					&& aForm.getBorrowerConsent().equals(ICMSConstant.TRUE_VALUE)) {
				obToChange.setIsConsentFromBorrower(true);
			}
			else {
				obToChange.setIsConsentFromBorrower(false);
			}
			if (isEmptyOrNull(aForm.getMajorityLendConsent())) {
				obToChange.setMajorityLendersConsent(ICMSConstant.DOUBLE_INVALID_VALUE);
			}
			else {
				try {
					obToChange.setMajorityLendersConsent(MapperUtil.mapStringToDouble(aForm.getMajorityLendConsent(),
							locale));
				}
				catch (Exception e) {
					throw new MapperException(e.getMessage());
				}
			}

			if (isEmptyOrNull(aForm.getDefaultRate())) {
				obToChange.setDefaultRate(ICMSConstant.DOUBLE_INVALID_VALUE);
			}
			else {
				try {
					obToChange.setDefaultRate(MapperUtil.mapStringToDouble(aForm.getDefaultRate(), locale));
				}
				catch (Exception e) {
					throw new MapperException(e.getMessage());
				}
			}
			obToChange.setAgencyFees(UIUtil.convertToAmount(locale, aForm.getAgencyFeeCcy(), aForm.getAgencyFeeAmt()));
			obToChange.setClosingFees(UIUtil
					.convertToAmount(locale, aForm.getClosingFeeCcy(), aForm.getClosingFeeAmt()));
			obToChange.setCommitmentFees(UIUtil.convertToAmount(locale, aForm.getCommitmentFeeCcy(), aForm
					.getCommitmentFeeAmt()));
			obToChange.setFacilityFees(UIUtil.convertToAmount(locale, aForm.getFacilityFeeCcy(), aForm
					.getFacilityFeeAmt()));
			obToChange.setUpfrontFees(UIUtil
					.convertToAmount(locale, aForm.getUpfrontFeeCcy(), aForm.getUpfrontFeeAmt()));
			obToChange.setLCFees(UIUtil.convertToAmount(locale, aForm.getLcFeeCcy(), aForm.getLcFeeAmt()));
			obToChange.setAmendmentFees(UIUtil.convertToAmount(locale, aForm.getAmendmentFeeCcy(), aForm
					.getAmendmentFeeAmt()));
			obToChange.setExtensionFees(UIUtil.convertToAmount(locale, aForm.getExtensionFeeCcy(), aForm
					.getExtensionFeeAmt()));
			obToChange.setArrangementFees(UIUtil.convertToAmount(locale, aForm.getArrangementFeeCcy(), aForm
					.getArrangementFeeAmt()));
			obToChange.setOtherFees(UIUtil.convertToAmount(locale, aForm.getOtherFeeCcy(), aForm.getOtherFeeAmt()));
		}
		// DefaultLogger.debug(this,
		// "<<<<<<<<<<<<<<<<<<<< HSHII: obToChange: "+obToChange);
		return obToChange;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		LoanAgencyForm aForm = (LoanAgencyForm) cForm;
		HashMap loanAgencyMap = (HashMap) obj;
		ILoanAgency loanAgencyObj = (ILoanAgency) loanAgencyMap.get("obj");
		String securityID = (String) loanAgencyMap.get("securityID");

		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		HashMap trxValueMap = (HashMap) inputs.get("commodityMainTrxValue");
		String from_event = (String) inputs.get("from_page");

		aForm.setSecurityID(securityID);
		HashMap limitList = (HashMap) trxValueMap.get("limitList");
		HashMap limitMap = new HashMap();
		if ((from_event != null) && from_event.equals(CommodityMainAction.EVENT_READ)) {
			limitMap = (HashMap) trxValueMap.get("actualLimit");
		}
		else {
			limitMap = (HashMap) trxValueMap.get("stageLimit");
		}

		Collection limitID = new ArrayList();
		try {
			Collection tmp = limitMap.keySet();
			if (tmp != null) {
				DefaultLogger.debug(this, "keyset length: " + tmp.size());
			}
			Iterator itr = tmp.iterator();
			while (itr.hasNext()) {
				String tmpID = (String) itr.next();
				if (tmpID.substring(0, 2).equals(ICMSConstant.CUSTOMER_CATEGORY_MAIN_BORROWER)) {
					ILimit limit = (ILimit) limitList.get(tmpID);
					if (!limit.getLimitStatus().equals(ICMSConstant.STATE_DELETED) && hasLimitSecurityLinkage(limit)) {
						limitID.add(tmpID);
					}
				}
				else if (tmpID.substring(0, 2).equals(ICMSConstant.CUSTOMER_CATEGORY_CO_BORROWER)) {
					ICoBorrowerLimit colmt = (ICoBorrowerLimit) limitList.get(tmpID);
					if (!colmt.getStatus().equals(ICMSConstant.STATE_DELETED) && hasLimitSecurityLinkage(colmt)) {
						limitID.add(tmpID);
					}
				}
			}

		}
		catch (Exception e) {
			new MapperException(e.getMessage());
		}

		Collection selectedLimitID = new ArrayList();
		if (loanAgencyObj.getLimitIDs() != null) {
			ILoanLimit[] limitIDList = loanAgencyObj.getLimitIDs();
			for (int i = 0; i < limitIDList.length; i++) {
				selectedLimitID.add(CommodityMainUtil.getLoanLimitLimitID(limitIDList[i]));
			}
		}

		limitID.removeAll(selectedLimitID);
		String[] limit = (String[]) limitID.toArray(new String[0]);
		String[] selectedLimit = (String[]) selectedLimitID.toArray(new String[0]);
		aForm.setLimitID(limit);
		aForm.setSelectedLimitID(selectedLimit);

		aForm.setAdminAgentName(loanAgencyObj.getAdministrativeAgentName());
		aForm.setColAgentName(loanAgencyObj.getCollateralAgentName());
		aForm.setDocumentAgent(loanAgencyObj.getDocumentAgent());
		aForm.setLcIssuingBank(loanAgencyObj.getLCIssuingBank());

		String[] borrower = null;
		if (loanAgencyObj.getBorrowers() != null) {
			IBorrower[] borrowerList = loanAgencyObj.getBorrowers();
			borrower = new String[borrowerList.length];
			for (int i = 0; i < borrowerList.length; i++) {
				borrower[i] = borrowerList[i].getName();
			}
		}
		aForm.setBorrowerList(borrower);

		String[] guarantor = null;
		if (loanAgencyObj.getGuarantors() != null) {
			IGuarantor[] guarantorList = loanAgencyObj.getGuarantors();
			guarantor = new String[guarantorList.length];
			for (int i = 0; i < guarantorList.length; i++) {
				guarantor[i] = guarantorList[i].getName();
			}
		}
		aForm.setGuarantorList(guarantor);

		Collection tempCurr = CurrencyList.getInstance().getCurrencyLabels();
		Collection currencyList = new ArrayList();
		try {
			currencyList = (Collection) AccessorUtil.deepClone(tempCurr);
		}
		catch (Exception e) {
			throw new MapperException(e.getMessage());
		}
		if (loanAgencyObj.getIsMultipleCurrencies()) {
			aForm.setAllowMultipleCurr(ICMSConstant.TRUE_VALUE);
		}
		else {
			aForm.setAllowMultipleCurr(ICMSConstant.FALSE_VALUE);
		}
		if ((loanAgencyObj.getGlobalCurrencies() != null) && (loanAgencyObj.getGlobalCurrencies().length > 0)) {
			Collection selectedCurrList = Arrays.asList(loanAgencyObj.getGlobalCurrencies());
			currencyList.removeAll(selectedCurrList);
			aForm.setSelectedCurrency(loanAgencyObj.getGlobalCurrencies());
		}
		aForm.setCurrency((String[]) currencyList.toArray(new String[0]));
		if (loanAgencyObj.getGlobalAmount() != null) {
			try {
				aForm.setGlobalAmountAmt(UIUtil.formatNumber(loanAgencyObj.getGlobalAmount().getAmountAsBigDecimal(),
						0, locale));
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
			aForm.setGlobalAmountCcy(loanAgencyObj.getGlobalAmount().getCurrencyCode());
		}
		else {
			aForm.setGlobalAmountAmt("");
			aForm.setGlobalAmountCcy("");
		}

		aForm.setFacilityType(loanAgencyObj.getFacilityType());
		aForm.setFacCommitmentDate(DateUtil.formatDate(locale, loanAgencyObj.getFacilityCommitmentDate()));
		aForm.setFacEffectiveDate(DateUtil.formatDate(locale, loanAgencyObj.getFacilityEffectiveDate()));
		aForm.setFacMaturityDate(DateUtil.formatDate(locale, loanAgencyObj.getFacilityMaturityDate()));
		aForm.setFacTerminationDate(DateUtil.formatDate(locale, loanAgencyObj.getFacilityTerminationDate()));
		aForm.setLastDateIssueLC(DateUtil.formatDate(locale, loanAgencyObj.getLastDateToIssueLC()));
		aForm.setFinalLCMaturityDate(DateUtil.formatDate(locale, loanAgencyObj.getFinalLCMaturityDate()));
		aForm.setTransactionType(loanAgencyObj.getTypeOfTransaction());
		aForm.setCounselNameAgentBank(loanAgencyObj.getAgentBankCounselName());

		// set sub-limit
		ISubLimit[] subLimitList = loanAgencyObj.getSubLimits();
		String[] subLimitID = null;
		String[] subLimitCcy = null;
		String[] subLimitAmt = null;
		String[] subLimitFacType = null;
		if (subLimitList != null) {
			subLimitID = new String[subLimitList.length];
			subLimitCcy = new String[subLimitList.length];
			subLimitAmt = new String[subLimitList.length];
			subLimitFacType = new String[subLimitList.length];
			for (int i = 0; i < subLimitList.length; i++) {
				subLimitID[i] = String.valueOf(subLimitList[i].getSubLimitID());
				if (subLimitList[i].getAmount() != null) {
					subLimitCcy[i] = subLimitList[i].getAmount().getCurrencyCode();
					try {
						subLimitAmt[i] = UIUtil.formatNumber(subLimitList[i].getAmount().getAmountAsBigDecimal(), 2,
								locale);
					}
					catch (Exception e) {
						throw new MapperException(e.getMessage());
					}
				}
				else {
					subLimitCcy[i] = "";
					subLimitAmt[i] = "";
				}
				subLimitFacType[i] = subLimitList[i].getFacilityType();
			}
			try {
				Amount totalSubLimitAmt = ((OBLoanAgency) loanAgencyObj).getTotalSubLimitAmount();
				if ((totalSubLimitAmt != null) && (totalSubLimitAmt.getAmount() > 0)) {
					aForm.setTotalSubLimit(UIUtil.formatNumber(totalSubLimitAmt.getAmountAsBigDecimal(), 2, locale));
				}
			}
			catch (Exception e) {
				aForm.setTotalSubLimit("Forex Error");
			}
		}
		aForm.setSubLimitID(subLimitID);
		aForm.setSubLimitCcy(subLimitCcy);
		aForm.setSubLimitAmt(subLimitAmt);
		aForm.setSubLimitFacType(subLimitFacType);
		aForm.setDeleteSubLimit(new String[0]);

		// set participant
		IParticipant[] participantList = loanAgencyObj.getParticipants();
		String[] participantID = null;
		String[] participantName = null;
		String[] allocatedAmt = null;
		String[] percentageAmt = null;
		String[] pricing = null;
		if (participantList != null) {
			boolean isValidAmt = loanAgencyObj.getGlobalAmount() != null;
			participantID = new String[participantList.length];
			participantName = new String[participantList.length];
			allocatedAmt = new String[participantList.length];
			percentageAmt = new String[participantList.length];
			pricing = new String[participantList.length];
			BigDecimal totalAllocatedAmt = null;
			for (int i = 0; i < participantList.length; i++) {
				participantID[i] = String.valueOf(participantList[i].getParticipantID());
				participantName[i] = participantList[i].getName();
				if (participantList[i].getAllocatedAmount() != null) {
					try {
						allocatedAmt[i] = UIUtil.formatNumber(participantList[i].getAllocatedAmount()
								.getAmountAsBigDecimal(), 2, locale);
						if (totalAllocatedAmt != null) {
							totalAllocatedAmt = totalAllocatedAmt.add(participantList[i].getAllocatedAmount()
									.getAmountAsBigDecimal());
						}
						else {
							totalAllocatedAmt = new BigDecimal(participantList[i].getAllocatedAmount()
									.getAmountAsBigDecimal().toString());
						}
					}
					catch (Exception e) {
						throw new MapperException(e.getMessage());
					}
					if (isValidAmt && (totalAllocatedAmt != null)) {
						BigDecimal tempAmt = CommonUtil.calcPercentage(loanAgencyObj.getGlobalAmount()
								.getAmountAsBigDecimal(), totalAllocatedAmt);
						try {
							percentageAmt[i] = UIUtil.formatNumber(tempAmt, 2, locale);
						}
						catch (Exception e) {
							throw new MapperException(e.getMessage());
						}
					}
					else {
						percentageAmt[i] = "";
					}
				}
				else {
					allocatedAmt[i] = "";
					percentageAmt[i] = "";
				}
				DefaultLogger.debug(this, i + " <<<<<<<<<< participantList allocate amt: "
						+ participantList[i].getAllocatedAmount() + "\ttotal allocated amt: " + totalAllocatedAmt);
				pricing[i] = participantList[i].getRemarks();
			}
		}
		aForm.setParticipantID(participantID);
		aForm.setParticipant(participantName);
		aForm.setAllocatedAmt(allocatedAmt);
		aForm.setPercentageAmt(percentageAmt);
		aForm.setPricing(pricing);
		aForm.setDeleteParticipant(new String[0]);

		ILoanSchedule[] scheduleList = loanAgencyObj.getLoanSchedules();

		String[] paymentDate = null;
		String[] principalDue = null;
		String[] interestDue = null;
		String[] totalPayment = null;
		if (scheduleList != null) {
			paymentDate = new String[scheduleList.length];
			principalDue = new String[scheduleList.length];
			interestDue = new String[scheduleList.length];
			totalPayment = new String[scheduleList.length];
			for (int i = 0; i < scheduleList.length; i++) {
				ILoanSchedule tempObj = scheduleList[i];
				paymentDate[i] = DateUtil.formatDate(locale, tempObj.getPaymentDate());
				BigDecimal total = null;
				if (tempObj.getPrincipalDueAmount() != null) {
					try {
						principalDue[i] = UIUtil.formatNumber(tempObj.getPrincipalDueAmount().getAmountAsBigDecimal(),
								2, locale);
					}
					catch (Exception e) {
						throw new MapperException(e.getMessage());
					}
					total = tempObj.getPrincipalDueAmount().getAmountAsBigDecimal();
				}
				else {
					principalDue[i] = "";
				}
				if (tempObj.getInterestDueAmount() != null) {
					try {
						interestDue[i] = UIUtil.formatNumber(tempObj.getInterestDueAmount().getAmountAsBigDecimal(), 2,
								locale);
					}
					catch (Exception e) {
						throw new MapperException(e.getMessage());
					}
					if ((total != null) && (tempObj.getInterestDueAmount().getAmountAsBigDecimal() != null)) {
						total = total.add(tempObj.getInterestDueAmount().getAmountAsBigDecimal());
					}
					else if (total == null) {
						total = tempObj.getInterestDueAmount().getAmountAsBigDecimal();
					}
				}
				else {
					interestDue[i] = "";
				}
				if (total != null) {
					try {
						totalPayment[i] = UIUtil.formatNumber(total, 2, locale);
					}
					catch (Exception e) {
						throw new MapperException(e.getMessage());
					}
				}
				else {
					totalPayment[i] = "";
				}
			}
		}

		if (loanAgencyObj.getIsEqualInstalments()) {
			aForm.setEqualPaymentDate(paymentDate);
			aForm.setEqualPrincipalAmt(principalDue);
			aForm.setEqualInterestAmt(interestDue);
			aForm.setEqualTotalPayment(totalPayment);
			aForm.setNonEqPaymentDate(null);
			aForm.setNonEqPrincipalAmt(null);
			aForm.setNonEqInterestAmt(null);
			aForm.setNonEqTotalPayment(null);

			aForm.setInstalmentEqualType(ICMSConstant.TRUE_VALUE);
			if (loanAgencyObj.getPrincipleAmount() != null) {
				aForm.setEqualPrinAmtCcy(loanAgencyObj.getPrincipleAmount().getCurrencyCode());
				try {
					aForm.setEqualPrinAmtVal(UIUtil.formatNumber(loanAgencyObj.getPrincipleAmount()
							.getAmountAsBigDecimal(), 2, locale));
				}
				catch (Exception e) {
					throw new MapperException(e.getMessage());
				}
				if ((loanAgencyObj.getNumberOfInstalments() > 0)
						&& !isEmptyOrNull(loanAgencyObj.getFrequencyOfPayment())) {
					BigDecimal periodicRate = getPeriodicRate(loanAgencyObj.getDebtRate(), loanAgencyObj
							.getFrequencyOfPayment());
					BigDecimal amtPerInstal = calAmountPerInstalment(periodicRate, loanAgencyObj.getPrincipleAmount()
							.getAmountAsBigDecimal(), loanAgencyObj.getNumberOfInstalments());
					try {
						aForm.setEqualAmtPerInstalments(UIUtil.formatNumber(amtPerInstal, 2, locale));
					}
					catch (Exception e) {
						throw new MapperException(e.getMessage());
					}
				}
			}
			else {
				aForm.setEqualPrinAmtCcy("");
				aForm.setEqualPrinAmtVal("");
				aForm.setEqualAmtPerInstalments("");
			}
			if (loanAgencyObj.getNumberOfInstalments() > 0) {
				aForm.setEqualNoInstalments(String.valueOf(loanAgencyObj.getNumberOfInstalments()));
			}
			else {
				aForm.setEqualNoInstalments("");
			}
			aForm.setEqualPaymentFreq(loanAgencyObj.getFrequencyOfPayment());
			aForm.setEqualFirstPaymentDate(DateUtil.formatDate(locale, loanAgencyObj.getDateOfPayment()));
		}
		else {
			aForm.setNonEqPaymentDate(paymentDate);
			aForm.setNonEqPrincipalAmt(principalDue);
			aForm.setNonEqInterestAmt(interestDue);
			aForm.setNonEqTotalPayment(totalPayment);

			aForm.setEqualPaymentDate(null);
			aForm.setEqualAmtPerInstalments(null);
			aForm.setEqualPrincipalAmt(null);
			aForm.setEqualInterestAmt(null);
			aForm.setEqualTotalPayment(null);

			aForm.setInstalmentEqualType(ICMSConstant.FALSE_VALUE);
			if (loanAgencyObj.getPrincipleAmount() != null) {
				aForm.setNonEqPrinAmtCcy(loanAgencyObj.getPrincipleAmount().getCurrencyCode());
				try {
					aForm.setNonEqPrinAmtVal(UIUtil.formatNumber(loanAgencyObj.getPrincipleAmount()
							.getAmountAsBigDecimal(), 2, locale));
				}
				catch (Exception e) {
					throw new MapperException(e.getMessage());
				}
			}
			else {
				aForm.setNonEqPrinAmtCcy("");
				aForm.setNonEqPrinAmtVal("");
			}
			if (loanAgencyObj.getNumberOfInstalments() > 0) {
				aForm.setNonEqNoInstalments(String.valueOf(loanAgencyObj.getNumberOfInstalments()));
			}
			else {
				aForm.setNonEqNoInstalments("");
			}
			aForm.setNonEqPaymentFreq(loanAgencyObj.getFrequencyOfPayment());
			aForm.setNonEqFirstPaymentDate(DateUtil.formatDate(locale, loanAgencyObj.getDateOfPayment()));
		}

		if (loanAgencyObj.getDebtRate() != ICMSConstant.DOUBLE_INVALID_VALUE) {
			aForm.setDebtRate(MapperUtil.mapDoubleToString(loanAgencyObj.getDebtRate(), 2, locale));
		}
		aForm.setCalculateBase(String.valueOf(loanAgencyObj.getCalculationBaseNumberOfDays()));
		if ((loanAgencyObj.getPrincipleAmount() != null) && (loanAgencyObj.getDebtRate() > 0)
				&& (loanAgencyObj.getCalculationBaseNumberOfDays() > 0)) {
			BigDecimal temp = CommonUtil.calcAfterPercent(loanAgencyObj.getPrincipleAmount().getAmountAsBigDecimal(),
					loanAgencyObj.getDebtRate());
			BigDecimal paymentDuration = getPaymentDurationInDays(loanAgencyObj.getDateOfPayment(), loanAgencyObj
					.getFrequencyOfPayment(), loanAgencyObj.getNumberOfInstalments());
			if (paymentDuration != null) {
				BigDecimal totalInterest = temp.divide(new BigDecimal(loanAgencyObj.getCalculationBaseNumberOfDays()),
						6, BigDecimal.ROUND_HALF_UP);
				totalInterest = totalInterest.multiply(paymentDuration);
				totalInterest.setScale(2, BigDecimal.ROUND_HALF_UP);
				try {
					aForm.setTotalAmtInterest(UIUtil.formatNumber(totalInterest, 2, locale));
				}
				catch (Exception e) {
					throw new MapperException(e.getMessage());
				}
				BigDecimal totalPaymentAmt = loanAgencyObj.getPrincipleAmount().getAmountAsBigDecimal().add(
						totalInterest);
				try {
					aForm.setTotalPayment(UIUtil.formatNumber(totalPaymentAmt, 2, locale));
				}
				catch (Exception e) {
					throw new MapperException(e.getMessage());
				}
			}
		}

		if (loanAgencyObj.getIsTermOutOption()) {
			aForm.setTermOutOption(ICMSConstant.TRUE_VALUE);
		}
		else {
			aForm.setTermOutOption(ICMSConstant.FALSE_VALUE);
		}
		if (loanAgencyObj.getIsPrepaymentOption()) {
			aForm.setPrePaymentOption(ICMSConstant.TRUE_VALUE);
		}
		else {
			aForm.setPrePaymentOption(ICMSConstant.FALSE_VALUE);
		}
		if (loanAgencyObj.getPrepaymentMinAmount() != null) {
			aForm.setPrePaymentMinCcy(loanAgencyObj.getPrepaymentMinAmount().getCurrencyCode());
			try {
				aForm.setPrePaymentMinAmt(UIUtil.formatNumber(loanAgencyObj.getPrepaymentMinAmount()
						.getAmountAsBigDecimal(), 2, locale));
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}
		else {
			aForm.setPrePaymentMinCcy("");
			aForm.setPrePaymentMinAmt("");
		}
		if (loanAgencyObj.getIsPrepaymentPenalty()) {
			aForm.setPrePaymentPenalty(ICMSConstant.TRUE_VALUE);
		}
		else {
			aForm.setPrePaymentPenalty(ICMSConstant.FALSE_VALUE);
		}
		aForm.setNumDayNotice(String.valueOf(loanAgencyObj.getNumberOfNoticeDays()));
		aForm.setGoverningLaw(loanAgencyObj.getGoverningLaw());
		aForm.setInterestDuration(String.valueOf(loanAgencyObj.getInterestPeriodDuration()));
		aForm.setInterestDurationUnit(loanAgencyObj.getInterestPeriodDurationUnit());
		aForm.setMaxLoanOutstanding(String.valueOf(loanAgencyObj.getMaxNumberOfLoanOutstanding()));
		if (loanAgencyObj.getMinDrawdownAmountAllowed() != null) {
			aForm.setMinDrawdownAllowCcy(loanAgencyObj.getMinDrawdownAmountAllowed().getCurrencyCode());
			try {
				aForm.setMinDrawdownAllowAmt(UIUtil.formatNumber(loanAgencyObj.getMinDrawdownAmountAllowed()
						.getAmountAsBigDecimal(), 2, locale));
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}
		else {
			aForm.setMinDrawdownAllowCcy("");
			aForm.setMinDrawdownAllowAmt("");
		}
		if (loanAgencyObj.getMaxDrawdownAmountAllowed() != null) {
			aForm.setMaxDrawdownAllowCcy(loanAgencyObj.getMaxDrawdownAmountAllowed().getCurrencyCode());
			try {
				aForm.setMaxDrawdownAllowAmt(UIUtil.formatNumber(loanAgencyObj.getMaxDrawdownAmountAllowed()
						.getAmountAsBigDecimal(), 2, locale));
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}
		else {
			aForm.setMaxDrawdownAllowCcy("");
			aForm.setMaxDrawdownAllowAmt("");
		}
		if (loanAgencyObj.getMinNumberOfDrawdownsAllowed() != ICMSConstant.INT_INVALID_VALUE) {
			aForm.setMinNumDrawdownAllow(String.valueOf(loanAgencyObj.getMinNumberOfDrawdownsAllowed()));
		}
		else {
			aForm.setMinNumDrawdownAllow("");
		}
		if (loanAgencyObj.getMaxNumberOfDrawdownsAllowed() != ICMSConstant.INT_INVALID_VALUE) {
			aForm.setMaxNumDrawdownAllow(String.valueOf(loanAgencyObj.getMaxNumberOfDrawdownsAllowed()));
		}
		else {
			aForm.setMaxNumDrawdownAllow("");
		}
		if (loanAgencyObj.getMinAssignmentFees() != null) {
			aForm.setMinAssignmentFeeCcy(loanAgencyObj.getMinAssignmentFees().getCurrencyCode());
			try {
				aForm.setMinAssignmentFeeAmt(UIUtil.formatNumber(loanAgencyObj.getMinAssignmentFees()
						.getAmountAsBigDecimal(), 2, locale));
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}
		else {
			aForm.setMinAssignmentFeeCcy("");
			aForm.setMinAssignmentFeeAmt("");
		}
		if (loanAgencyObj.getMinNumberOfDrawdownsAllowed() >= 0) {
			aForm.setMinNumDrawdownAllow(String.valueOf(loanAgencyObj.getMinNumberOfDrawdownsAllowed()));
		}
		else {
			aForm.setMinNumDrawdownAllow("");
		}
		if (loanAgencyObj.getMaxNumberOfDrawdownsAllowed() >= 0) {
			aForm.setMaxNumDrawdownAllow(String.valueOf(loanAgencyObj.getMaxNumberOfDrawdownsAllowed()));
		}
		else {
			aForm.setMaxNumDrawdownAllow("");
		}
		if (loanAgencyObj.getMaxAssignmentFees() != null) {
			aForm.setMaxAssignmentFeeCcy(loanAgencyObj.getMaxAssignmentFees().getCurrencyCode());
			try {
				aForm.setMaxAssignmentFeeAmt(UIUtil.formatNumber(loanAgencyObj.getMaxAssignmentFees()
						.getAmountAsBigDecimal(), 2, locale));
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}
		else {
			aForm.setMaxAssignmentFeeCcy("");
			aForm.setMaxAssignmentFeeAmt("");
		}

		if (loanAgencyObj.getIsConsentFromBorrower()) {
			aForm.setBorrowerConsent(ICMSConstant.TRUE_VALUE);
		}
		else {
			aForm.setBorrowerConsent(ICMSConstant.FALSE_VALUE);
		}
		aForm.setMajorityLendConsent(String.valueOf(loanAgencyObj.getMajorityLendersConsent()));
		aForm.setDefaultRate(String.valueOf(loanAgencyObj.getDefaultRate()));

		// set fees
		if (loanAgencyObj.getAgencyFees() != null) {
			aForm.setAgencyFeeCcy(loanAgencyObj.getAgencyFees().getCurrencyCode());
			try {
				aForm.setAgencyFeeAmt(UIUtil.formatNumber(loanAgencyObj.getAgencyFees().getAmountAsBigDecimal(), 2,
						locale));
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}
		else {
			aForm.setAgencyFeeCcy("");
			aForm.setAgencyFeeAmt("");
		}
		if (loanAgencyObj.getClosingFees() != null) {
			aForm.setClosingFeeCcy(loanAgencyObj.getClosingFees().getCurrencyCode());
			try {
				aForm.setClosingFeeAmt(UIUtil.formatNumber(loanAgencyObj.getClosingFees().getAmountAsBigDecimal(), 2,
						locale));
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}
		else {
			aForm.setClosingFeeCcy("");
			aForm.setClosingFeeAmt("");
		}
		if (loanAgencyObj.getCommitmentFees() != null) {
			aForm.setCommitmentFeeCcy(loanAgencyObj.getCommitmentFees().getCurrencyCode());
			try {
				aForm.setCommitmentFeeAmt(UIUtil.formatNumber(
						loanAgencyObj.getCommitmentFees().getAmountAsBigDecimal(), 2, locale));
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}
		else {
			aForm.setCommitmentFeeCcy("");
			aForm.setCommitmentFeeAmt("");
		}
		if (loanAgencyObj.getFacilityFees() != null) {
			aForm.setFacilityFeeCcy(loanAgencyObj.getFacilityFees().getCurrencyCode());
			try {
				aForm.setFacilityFeeAmt(UIUtil.formatNumber(loanAgencyObj.getFacilityFees().getAmountAsBigDecimal(), 2,
						locale));
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}
		else {
			aForm.setFacilityFeeCcy("");
			aForm.setFacilityFeeAmt("");
		}
		if (loanAgencyObj.getUpfrontFees() != null) {
			aForm.setUpfrontFeeCcy(loanAgencyObj.getUpfrontFees().getCurrencyCode());
			try {
				aForm.setUpfrontFeeAmt(UIUtil.formatNumber(loanAgencyObj.getUpfrontFees().getAmountAsBigDecimal(), 2,
						locale));
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}
		else {
			aForm.setUpfrontFeeCcy("");
			aForm.setUpfrontFeeAmt("");
		}
		if (loanAgencyObj.getLCFees() != null) {
			aForm.setLcFeeCcy(loanAgencyObj.getLCFees().getCurrencyCode());
			try {
				aForm.setLcFeeAmt(UIUtil.formatNumber(loanAgencyObj.getLCFees().getAmountAsBigDecimal(), 2, locale));
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}
		else {
			aForm.setLcFeeCcy("");
			aForm.setLcFeeAmt("");
		}
		if (loanAgencyObj.getAmendmentFees() != null) {
			aForm.setAmendmentFeeCcy(loanAgencyObj.getAmendmentFees().getCurrencyCode());
			try {
				aForm.setAmendmentFeeAmt(UIUtil.formatNumber(loanAgencyObj.getAmendmentFees().getAmountAsBigDecimal(),
						2, locale));
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}
		else {
			aForm.setAmendmentFeeCcy("");
			aForm.setAmendmentFeeAmt("");
		}
		if (loanAgencyObj.getExtensionFees() != null) {
			aForm.setExtensionFeeCcy(loanAgencyObj.getExtensionFees().getCurrencyCode());
			try {
				aForm.setExtensionFeeAmt(UIUtil.formatNumber(loanAgencyObj.getExtensionFees().getAmountAsBigDecimal(),
						2, locale));
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}
		else {
			aForm.setExtensionFeeCcy("");
			aForm.setExtensionFeeAmt("");
		}
		if (loanAgencyObj.getArrangementFees() != null) {
			aForm.setArrangementFeeCcy(loanAgencyObj.getArrangementFees().getCurrencyCode());
			try {
				aForm.setArrangementFeeAmt(UIUtil.formatNumber(loanAgencyObj.getArrangementFees()
						.getAmountAsBigDecimal(), 2, locale));
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}
		else {
			aForm.setArrangementFeeCcy("");
			aForm.setArrangementFeeAmt("");
		}
		if (loanAgencyObj.getOtherFees() != null) {
			aForm.setOtherFeeCcy(loanAgencyObj.getOtherFees().getCurrencyCode());
			try {
				aForm.setOtherFeeAmt(UIUtil.formatNumber(loanAgencyObj.getOtherFees().getAmountAsBigDecimal(), 2,
						locale));
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}
		else {
			aForm.setOtherFeeCcy("");
			aForm.setOtherFeeAmt("");
		}

		return aForm;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "commodityMainTrxValue", "java.util.HashMap", SERVICE_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "serviceLoanAgency", "com.integrosys.cms.app.collateral.bus.type.commodity.ILoanAgency",
						SERVICE_SCOPE }, { "from_page", "java.lang.String", REQUEST_SCOPE }, });
	}

	private Object[] deleteArr(Object[] oldArr, String[] chkDelete) {
		Object[] newList = null;
		if (chkDelete != null) {
			if (chkDelete.length <= oldArr.length) {
				int numDelete = 0;
				for (int i = 0; i < chkDelete.length; i++) {
					if (Integer.parseInt(chkDelete[i]) < oldArr.length) {
						numDelete++;
					}
				}
				if (numDelete != 0) {
					newList = new Object[oldArr.length - numDelete];
					int i = 0, j = 0;
					while (i < oldArr.length) {
						if ((j < chkDelete.length) && (Integer.parseInt(chkDelete[j]) == i)) {
							j++;
						}
						else {
							newList[i - j] = oldArr[i];
						}
						i++;
					}
				}
			}
		}
		else {
			newList = oldArr;
		}
		return newList;
	}

	private boolean hasLimitSecurityLinkage(ILimit limit) {
		boolean returnValue = false;
		if (limit != null) {
			ICollateralAllocation[] allocation = limit.getCollateralAllocations();
			if (allocation != null) {
				for (int i = 0; i < allocation.length; i++) {
					if (!allocation[i].getHostStatus().equals(ICMSConstant.HOST_STATUS_DELETE)) {
						returnValue = true;
						return returnValue;
					}
				}
			}
		}
		return returnValue;
	}

	private boolean hasLimitSecurityLinkage(ICoBorrowerLimit limit) {
		boolean returnValue = false;
		if (limit != null) {
			ICollateralAllocation[] allocation = limit.getCollateralAllocations();
			if (allocation != null) {
				for (int i = 0; i < allocation.length; i++) {
					if (!allocation[i].getHostStatus().equals(ICMSConstant.HOST_STATUS_DELETE)) {
						returnValue = true;
						return returnValue;
					}
				}
			}
		}
		return returnValue;
	}

	private ILoanSchedule[] setEqualInstalmentLoanSchedule(ILoanSchedule[] schedule, Date paymentDate,
			Amount totalPrincipalAmt, int numberInstalment, String frequency, double interestRate)
			throws MapperException {
		Amount balancePrinAmt = null;
		try {
			balancePrinAmt = (Amount) AccessorUtil.deepClone(totalPrincipalAmt);
		}
		catch (Exception e) {
			throw new MapperException(e.getMessage());
		}
		OBLoanSchedule[] scheduleList = new OBLoanSchedule[0];
		if (numberInstalment > 0) {
			scheduleList = new OBLoanSchedule[numberInstalment];
			int numMonth = getNumMonthByPaymentFreq(frequency);

			BigDecimal periodicRate = getPeriodicRate(interestRate, frequency);
			BigDecimal perInstalmentAmt = calAmountPerInstalment(periodicRate, balancePrinAmt.getAmountAsBigDecimal(),
					numberInstalment);

			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(paymentDate);
			CurrencyCode ccy = null;
			if (totalPrincipalAmt != null) {
				ccy = totalPrincipalAmt.getCurrencyCodeAsObject();
			}
			for (int i = 0; i < numberInstalment; i++) {
				OBLoanSchedule obj = new OBLoanSchedule();
				if ((schedule != null) && (i < schedule.length)) {
					obj = (OBLoanSchedule) schedule[i];
				}
				BigDecimal interestAmt = null;
				BigDecimal principalAmt = null;
				if (periodicRate == null) {
					principalAmt = perInstalmentAmt;
				}
				else {
					interestAmt = balancePrinAmt.getAmountAsBigDecimal().multiply(periodicRate);
					interestAmt = roundBigDecimal(interestAmt);
					principalAmt = perInstalmentAmt.subtract(interestAmt);
				}

				obj.setPaymentDate(cal.getTime());
				cal.add(Calendar.MONTH, numMonth);
				obj.setPrincipalDueAmount(new Amount(principalAmt, ccy));
				obj.setInterestDueAmount(new Amount(interestAmt, ccy));
				obj.setPrincipalDueAmount(roundAmount(obj.getPrincipalDueAmount()));
				obj.setInterestDueAmount(roundAmount(obj.getInterestDueAmount()));
				scheduleList[i] = obj;
				BigDecimal balancePrincipalAmt = balancePrinAmt.getAmountAsBigDecimal().subtract(principalAmt);
				balancePrinAmt.setAmountAsBigDecimal(balancePrincipalAmt);
			}
		}

		return scheduleList;
	}

	/**
	 * Helper method to round the amount value.
	 * 
	 * @param amt of type Amount
	 * @return rounded amount
	 */
	public static Amount roundAmount(Amount amt) {
		if ((amt == null) || (amt.getAmountAsBigDecimal() == null)) {
			return amt;
		}

		BigDecimal bd = roundBigDecimal(amt.getAmountAsBigDecimal());
		return new Amount(bd, amt.getCurrencyCodeAsObject());
	}

	private static BigDecimal roundBigDecimal(BigDecimal bd) {
		if (bd != null) {
			bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		return bd;
	}

	private static boolean isPrincipleAmountChanged(Amount originalAmt, String ccyCode, String amt, Locale locale)
			throws Exception {
		if (originalAmt == null) {
			if (!isEmptyOrNull(ccyCode) || !isEmptyOrNull(amt)) {
				return true;
			}
		}
		else {
			if (!isEmptyOrNull(amt)) {
				BigDecimal temp = UIUtil.mapStringToBigDecimal(amt);
				try {
					String strInput = UIUtil.formatNumber(temp, 2, locale);
					String origAmt = UIUtil.formatNumber(originalAmt.getAmountAsBigDecimal(), 2, locale);
					if (!strInput.equals(origAmt)) {
						return true;
					}
				}
				catch (Exception e) {
					throw new MapperException(e.getMessage());
				}
			}
			else if (originalAmt.getAmountAsBigDecimal() != null) {
				return true;
			}
			else if (!isEmptyOrNull(ccyCode) && ccyCode.equals(originalAmt.getCurrencyCode())) {
				return true;
			}
			else if (isEmptyOrNull(ccyCode) && !isEmptyOrNull(originalAmt.getCurrencyCode())) {
				return true;
			}
		}
		return false;
	}

	private static BigDecimal calAmountPerInstalment(BigDecimal periodicRate, BigDecimal loanAmount, int numPayment) {
		if (periodicRate != null) {
			BigDecimal tempVal = new BigDecimal(Math.pow(1 + periodicRate.doubleValue(), numPayment) - 1);
			tempVal = loanAmount.divide(tempVal, 10, BigDecimal.ROUND_HALF_UP);
			tempVal = loanAmount.add(tempVal);
			tempVal = tempVal.multiply(periodicRate);
			tempVal.setScale(2, BigDecimal.ROUND_HALF_UP);
			return tempVal;
		}
		else {
			return loanAmount.divide(new BigDecimal(numPayment), 2, BigDecimal.ROUND_HALF_UP);
		}
	}

	private static int getNumMonthByPaymentFreq(String frequency) {
		int numMonth = 0;
		if (isEmptyOrNull(frequency)) {
			return numMonth;
		}
		if (frequency.equals(CommodityMainConstant.PAYMENT_FREQ_MONTHLY)) {
			numMonth = 1;
		}
		else if (frequency.equals(CommodityMainConstant.PAYMENT_FREQ_BIMONTHLY)) {
			numMonth = 2;
		}
		else if (frequency.equals(CommodityMainConstant.PAYMENT_FREQ_QUARTERLY)) {
			numMonth = 3;
		}
		else if (frequency.equals(CommodityMainConstant.PAYMENT_FREQ_SEMI_ANNUALLY)) {
			numMonth = 6;
		}
		else {
			numMonth = 12;
		}

		return numMonth;
	}

	private static BigDecimal getPeriodicRate(double interestRate, String frequency) {
		if ((interestRate > 0) && !isEmptyOrNull(frequency)) {
			int numMonth = getNumMonthByPaymentFreq(frequency);
			BigDecimal numPaymentPerYear = (new BigDecimal(12 / numMonth));
			return (new BigDecimal(String.valueOf(interestRate / 100))).divide(numPaymentPerYear, 6,
					BigDecimal.ROUND_HALF_UP);
		}
		return null;
	}

	private static BigDecimal getPaymentDurationInDays(Date startPaymentDate, String frequency, int numOfInstal) {
		if (startPaymentDate != null) {
			GregorianCalendar startCal = new GregorianCalendar();
			GregorianCalendar endCal = new GregorianCalendar();
			startCal.setTime(startPaymentDate);
			endCal.setTime(startPaymentDate);
			int numMonth = getNumMonthByPaymentFreq(frequency);
			int totalMonth = numMonth * numOfInstal;
			endCal.add(Calendar.MONTH, totalMonth);
			long start = startCal.getTime().getTime();
			long end = endCal.getTime().getTime();
			double difference = end - start;
			long numOfDays = Math.round((difference / (1000 * 60 * 60 * 24)));
			DefaultLogger.debug("LoanAgencyMapper - getPaymentDurationInDays", "<<<<<<<<<<<<<< number of days: "
					+ numOfDays);
			return new BigDecimal(numOfDays);
		}
		return null;
	}
}
