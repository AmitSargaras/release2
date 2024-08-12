/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/EBLoanAgencyBean.java,v 1.34 2004/08/18 08:00:51 wltan Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Entity bean implementation for security parameter.
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.34 $
 * @since $Date: 2004/08/18 08:00:51 $ Tag: $Name: $
 */

public abstract class EBLoanAgencyBean implements ILoanAgency, EntityBean {

	protected boolean isStaging = false;

	public EBLoanAgencyBean() {
		isStaging = false;
	}

	/** The container assigned reference to the entity. */
	private EntityContext context;

	public abstract Long getLoanAgencyPK();

	public abstract void setLoanAgencyPK(Long pk);

	public long getLoanAgencyID() {
		return getLoanAgencyPK().longValue();
	}

	public void setLoanAgencyID(long id) {
		setLoanAgencyPK(new Long(id));
	}

	public int getNumberOfInstalments() {
		return -1;
	}

	public void setNumberOfInstalments(int numberOfInstalments) {
	}

	public Date getDateOfPayment() {
		return null;
	}

	public void setDateOfPayment(Date dateOfPayment) {
	}

	public abstract String getAdministrativeAgentName();

	public abstract void setAdministrativeAgentName(String value);

	public abstract String getCollateralAgentName();

	public abstract void setCollateralAgentName(String value);

	public abstract String getDocumentAgent();

	public abstract void setDocumentAgent(String value);

	public abstract String getLCIssuingBank();

	public abstract void setLCIssuingBank(String value);

	public abstract Collection getLimitsCMR();

	public abstract void setLimitsCMR(Collection value);

	public abstract Collection getBorrowersCMR();

	public abstract void setBorrowersCMR(Collection value);

	public abstract Collection getGuarantorsCMR();

	public abstract void setGuarantorsCMR(Collection value);

	public abstract Collection getSubLimitsCMR();

	public abstract void setSubLimitsCMR(Collection value);

	public abstract Collection getLoanSchedulesCMR();

	public abstract void setLoanSchedulesCMR(Collection value);

	public abstract Collection getParticipantsCMR();

	public abstract void setParticipantsCMR(Collection value);

	public boolean getIsMultipleCurrencies() {
		String isMulpleCurr = getEBIsMultipleCurrencies();
		return ((isMulpleCurr == null) || isMulpleCurr.equals(ICMSConstant.FALSE_VALUE)) ? false : true;
	}

	public void setIsMultipleCurrencies(boolean value) {
		setEBIsMultipleCurrencies((!value) ? ICMSConstant.FALSE_VALUE : ICMSConstant.TRUE_VALUE);
	}

	public abstract String getEBIsMultipleCurrencies();

	public abstract void setEBIsMultipleCurrencies(String value);

	public abstract String getGlobalCurrenciesStr();

	public abstract void setGlobalCurrenciesStr(String value);

	public Amount getGlobalAmount() {
		return (getEBGlobalAmount() == null) ? null : new Amount(getEBGlobalAmount(), new CurrencyCode(
				getGlobalAmountCurrency()));
	}

	public void setGlobalAmount(Amount value) {
		setEBGlobalAmount(value == null ? null : value.getAmountAsBigDecimal());
		setGlobalAmountCurrency(value == null ? null : value.getCurrencyCode());
	}

	public abstract BigDecimal getEBGlobalAmount();

	public abstract void setEBGlobalAmount(BigDecimal value);

	public abstract String getGlobalAmountCurrency();

	public abstract void setGlobalAmountCurrency(String value);

	public abstract Date getFacilityCommitmentDate();

	public abstract void setFacilityCommitmentDate(Date value);

	public abstract Date getFacilityEffectiveDate();

	public abstract void setFacilityEffectiveDate(Date value);

	public abstract Date getFacilityMaturityDate();

	public abstract void setFacilityMaturityDate(Date value);

	public abstract Date getFacilityTerminationDate();

	public abstract void setFacilityTerminationDate(Date value);

	public abstract Date getLastDateToIssueLC();

	public abstract void setLastDateToIssueLC(Date value);

	public abstract Date getFinalLCMaturityDate();

	public abstract void setFinalLCMaturityDate(Date value);

	public abstract String getTypeOfTransaction(); // commoncode - list

	public abstract void setTypeOfTransaction(String value);

	public abstract String getAgentBankCounselName();

	public abstract void setAgentBankCounselName(String value);

	// Term Loan Amortization Schedules
	public boolean getIsEqualInstalments() {
		String isEqual = getEBIsEqualInstalments();
		return ((isEqual == null) || isEqual.equals(ICMSConstant.FALSE_VALUE)) ? false : true;
	}

	public void setIsEqualInstalments(boolean value) {
		setEBIsEqualInstalments((!value) ? ICMSConstant.FALSE_VALUE : ICMSConstant.TRUE_VALUE);
	}

	public abstract String getEBIsEqualInstalments();

	public abstract void setEBIsEqualInstalments(String value);

	public Amount getPrincipleAmount() {
		return (getEBPrincipleAmount() == null) ? null : new Amount(getEBPrincipleAmount(), new CurrencyCode(
				getPrincipleAmountCurrency()));
	}

	public void setPrincipleAmount(Amount value) {
		setEBPrincipleAmount(value == null ? null : value.getAmountAsBigDecimal());
		setPrincipleAmountCurrency(value == null ? null : value.getCurrencyCode());
	}

	public abstract BigDecimal getEBPrincipleAmount();

	public abstract void setEBPrincipleAmount(BigDecimal value);

	public abstract String getPrincipleAmountCurrency();

	public abstract void setPrincipleAmountCurrency(String value);

	public abstract String getFrequencyOfPayment();

	public abstract void setFrequencyOfPayment(String value);

	public abstract int getCalculationBaseNumberOfDays();

	public abstract void setCalculationBaseNumberOfDays(int calculationBaseNumberOfDays);

	public double getDebtRate() {
		return (getEBDebtRate() == null) ? ICMSConstant.DOUBLE_INVALID_VALUE : getEBDebtRate().doubleValue();
	}

	public void setDebtRate(double debtRate) {
		setEBDebtRate((debtRate == ICMSConstant.DOUBLE_INVALID_VALUE) ? null : new Double(debtRate));
	}

	public abstract Double getEBDebtRate();

	public abstract void setEBDebtRate(Double Double);

	public boolean getIsTermOutOption() {
		String isTermOut = getEBIsTermOutOption();
		return ((isTermOut == null) || isTermOut.equals(ICMSConstant.FALSE_VALUE)) ? false : true;
	}

	public void setIsTermOutOption(boolean value) {
		setEBIsTermOutOption((!value) ? ICMSConstant.FALSE_VALUE : ICMSConstant.TRUE_VALUE);
	}

	public abstract String getEBIsTermOutOption();

	public abstract void setEBIsTermOutOption(String value);

	public boolean getIsPrepaymentOption() {
		String isPrePaymt = getEBIsPrepaymentOption();
		return ((isPrePaymt == null) || isPrePaymt.equals(ICMSConstant.FALSE_VALUE)) ? false : true;
	}

	public void setIsPrepaymentOption(boolean value) {
		setEBIsPrepaymentOption((!value) ? ICMSConstant.FALSE_VALUE : ICMSConstant.TRUE_VALUE);
	}

	public abstract String getEBIsPrepaymentOption();

	public abstract void setEBIsPrepaymentOption(String value);

	public Amount getPrepaymentMinAmount() {
		return (getEBPrepaymentMinAmount() == null) ? null : new Amount(getEBPrepaymentMinAmount(), new CurrencyCode(
				getPrepaymentMinAmountCurrency()));
	}

	public void setPrepaymentMinAmount(Amount value) {
		setEBPrepaymentMinAmount(value == null ? null : value.getAmountAsBigDecimal());
		setPrepaymentMinAmountCurrency(value == null ? null : value.getCurrencyCode());
	}

	public abstract BigDecimal getEBPrepaymentMinAmount();

	public abstract void setEBPrepaymentMinAmount(BigDecimal value);

	public abstract String getPrepaymentMinAmountCurrency();

	public abstract void setPrepaymentMinAmountCurrency(String value);

	public boolean getIsPrepaymentPenalty() {
		String isPenalty = getEBIsPrepaymentPenalty();
		return ((isPenalty == null) || isPenalty.equals(ICMSConstant.FALSE_VALUE)) ? false : true;
	}

	public void setIsPrepaymentPenalty(boolean value) {
		setEBIsPrepaymentPenalty((!value) ? ICMSConstant.FALSE_VALUE : ICMSConstant.TRUE_VALUE);
	}

	public abstract String getEBIsPrepaymentPenalty();

	public abstract void setEBIsPrepaymentPenalty(String value);

	public abstract int getNumberOfNoticeDays();

	public abstract void setNumberOfNoticeDays(int value);

	public abstract String getGoverningLaw();

	public abstract void setGoverningLaw(String value);

	public abstract String getInterestPeriodDurationUnit(); // commoncode - list

	public abstract void setInterestPeriodDurationUnit(String value); // commoncode
																		// -
																		// list

	public abstract int getInterestPeriodDuration();

	public abstract void setInterestPeriodDuration(int value);

	public abstract int getMaxNumberOfLoanOutstanding();

	public abstract void setMaxNumberOfLoanOutstanding(int value);

	public abstract String getMinDrawdownAmountAllowedCurrency();

	public abstract void setMinDrawdownAmountAllowedCurrency(String value);

	public Amount getMinDrawdownAmountAllowed() {
		return (getEBMinDrawdownAmountAllowed() == null) ? null : new Amount(getEBMinDrawdownAmountAllowed(),
				new CurrencyCode(getMinDrawdownAmountAllowedCurrency()));
	}

	public void setMinDrawdownAmountAllowed(Amount value) {
		setEBMinDrawdownAmountAllowed(value == null ? null : value.getAmountAsBigDecimal());
		setMinDrawdownAmountAllowedCurrency(value == null ? null : value.getCurrencyCode());
	}

	public abstract BigDecimal getEBMinDrawdownAmountAllowed();

	public abstract void setEBMinDrawdownAmountAllowed(BigDecimal value);

	public int getMinNumberOfDrawdownsAllowed() {
		if (getEBMinNumberOfDrawdownsAllowed() == null) {
			return ICMSConstant.INT_INVALID_VALUE;
		}
		return getEBMinNumberOfDrawdownsAllowed().intValue();
	}

	public void setMinNumberOfDrawdownsAllowed(int minNumberOfDrawdownsAllowed) {
		setEBMinNumberOfDrawdownsAllowed(minNumberOfDrawdownsAllowed == ICMSConstant.INT_INVALID_VALUE ? null
				: new Integer(minNumberOfDrawdownsAllowed));
	}

	public abstract Integer getEBMinNumberOfDrawdownsAllowed();

	public abstract void setEBMinNumberOfDrawdownsAllowed(Integer value);

	public abstract String getMinAssignmentFeesCurrency();

	public abstract void setMinAssignmentFeesCurrency(String value);

	public Amount getMinAssignmentFees() {
		return (getEBMinAssignmentFees() == null) ? null : new Amount(getEBMinAssignmentFees(), new CurrencyCode(
				getMinAssignmentFeesCurrency()));
	}

	public void setMinAssignmentFees(Amount value) {
		setEBMinAssignmentFees(value == null ? null : value.getAmountAsBigDecimal());
		setMinAssignmentFeesCurrency(value == null ? null : value.getCurrencyCode());
	}

	public abstract BigDecimal getEBMinAssignmentFees();

	public abstract void setEBMinAssignmentFees(BigDecimal value);

	public abstract String getMaxDrawdownAmountAllowedCurrency();

	public abstract void setMaxDrawdownAmountAllowedCurrency(String value);

	public Amount getMaxDrawdownAmountAllowed() {
		return (getEBMaxDrawdownAmountAllowed() == null) ? null : new Amount(getEBMaxDrawdownAmountAllowed(),
				new CurrencyCode(getMaxDrawdownAmountAllowedCurrency()));
	}

	public void setMaxDrawdownAmountAllowed(Amount value) {
		setEBMaxDrawdownAmountAllowed(value == null ? null : value.getAmountAsBigDecimal());
		setMaxDrawdownAmountAllowedCurrency(value == null ? null : value.getCurrencyCode());
	}

	public abstract BigDecimal getEBMaxDrawdownAmountAllowed();

	public abstract void setEBMaxDrawdownAmountAllowed(BigDecimal value);

	public int getMaxNumberOfDrawdownsAllowed() {
		if (getEBMaxNumberOfDrawdownsAllowed() == null) {
			return ICMSConstant.INT_INVALID_VALUE;
		}
		return getEBMaxNumberOfDrawdownsAllowed().intValue();
	}

	public void setMaxNumberOfDrawdownsAllowed(int maxNumberOfDrawdownsAllowed) {
		setEBMaxNumberOfDrawdownsAllowed(maxNumberOfDrawdownsAllowed == ICMSConstant.INT_INVALID_VALUE ? null
				: new Integer(maxNumberOfDrawdownsAllowed));
	}

	public abstract Integer getEBMaxNumberOfDrawdownsAllowed();

	public abstract void setEBMaxNumberOfDrawdownsAllowed(Integer value);

	public abstract String getMaxAssignmentFeesCurrency();

	public abstract void setMaxAssignmentFeesCurrency(String value);

	public Amount getMaxAssignmentFees() {
		return (getEBMaxAssignmentFees() == null) ? null : new Amount(getEBMaxAssignmentFees(), new CurrencyCode(
				getMaxAssignmentFeesCurrency()));
	}

	public void setMaxAssignmentFees(Amount value) {
		setEBMaxAssignmentFees(value == null ? null : value.getAmountAsBigDecimal());
		setMaxAssignmentFeesCurrency(value == null ? null : value.getCurrencyCode());
	}

	public abstract BigDecimal getEBMaxAssignmentFees();

	public abstract void setEBMaxAssignmentFees(BigDecimal value);

	public boolean getIsConsentFromBorrower() {
		String isConsent = getEBIsConsentFromBorrower();
		return ((isConsent == null) || isConsent.equals(ICMSConstant.FALSE_VALUE)) ? false : true;
	}

	public void setIsConsentFromBorrower(boolean value) {
		setEBIsConsentFromBorrower((!value) ? ICMSConstant.FALSE_VALUE : ICMSConstant.TRUE_VALUE);
	}

	public abstract String getEBIsConsentFromBorrower();

	public abstract void setEBIsConsentFromBorrower(String value);

	public double getMajorityLendersConsent() {
		return (getEBMajorityLendersConsent() == null) ? ICMSConstant.DOUBLE_INVALID_VALUE
				: getEBMajorityLendersConsent().doubleValue();
	}

	public void setMajorityLendersConsent(double value) {
		setEBMajorityLendersConsent((value == ICMSConstant.DOUBLE_INVALID_VALUE) ? null : new Double(value));
	}

	public abstract Double getEBMajorityLendersConsent();

	public abstract void setEBMajorityLendersConsent(Double value);

	public double getDefaultRate() {
		return (getEBDefaultRate() == null) ? ICMSConstant.DOUBLE_INVALID_VALUE : getEBDefaultRate().doubleValue();
	}

	public void setDefaultRate(double value) {
		setEBDefaultRate((value == ICMSConstant.DOUBLE_INVALID_VALUE) ? null : new Double(value));
	}

	public abstract Double getEBDefaultRate();

	public abstract void setEBDefaultRate(Double value);

	// Fees
	public abstract String getAgencyFeesCurrency();

	public abstract void setAgencyFeesCurrency(String value);

	public Amount getAgencyFees() {
		return (getEBAgencyFees() == null) ? null : new Amount(getEBAgencyFees(), new CurrencyCode(
				getAgencyFeesCurrency()));
	}

	public void setAgencyFees(Amount value) {
		setEBAgencyFees(value == null ? null : value.getAmountAsBigDecimal());
		setAgencyFeesCurrency(value == null ? null : value.getCurrencyCode());
	}

	public abstract BigDecimal getEBAgencyFees();

	public abstract void setEBAgencyFees(BigDecimal value);

	public abstract String getClosingFeesCurrency();

	public abstract void setClosingFeesCurrency(String value);

	public Amount getClosingFees() {
		return (getEBClosingFees() == null) ? null : new Amount(getEBClosingFees(), new CurrencyCode(
				getClosingFeesCurrency()));
	}

	public void setClosingFees(Amount value) {
		setEBClosingFees(value == null ? null : value.getAmountAsBigDecimal());
		setClosingFeesCurrency(value == null ? null : value.getCurrencyCode());
	}

	public abstract BigDecimal getEBClosingFees();

	public abstract void setEBClosingFees(BigDecimal value);

	public abstract String getCommitmentFeesCurrency();

	public abstract void setCommitmentFeesCurrency(String value);

	public Amount getCommitmentFees() {
		return (getEBCommitmentFees() == null) ? null : new Amount(getEBCommitmentFees(), new CurrencyCode(
				getCommitmentFeesCurrency()));
	}

	public void setCommitmentFees(Amount value) {
		setEBCommitmentFees(value == null ? null : value.getAmountAsBigDecimal());
		setCommitmentFeesCurrency(value == null ? null : value.getCurrencyCode());
	}

	public abstract BigDecimal getEBCommitmentFees();

	public abstract void setEBCommitmentFees(BigDecimal value);

	public abstract String getFacilityFeesCurrency();

	public abstract void setFacilityFeesCurrency(String value);

	public Amount getFacilityFees() {
		return (getEBFacilityFees() == null) ? null : new Amount(getEBFacilityFees(), new CurrencyCode(
				getFacilityFeesCurrency()));
	}

	public void setFacilityFees(Amount value) {
		setEBFacilityFees(value == null ? null : value.getAmountAsBigDecimal());
		setFacilityFeesCurrency(value == null ? null : value.getCurrencyCode());
	}

	public abstract BigDecimal getEBFacilityFees();

	public abstract void setEBFacilityFees(BigDecimal value);

	public abstract String getUpfrontFeesCurrency();

	public abstract void setUpfrontFeesCurrency(String value);

	public Amount getUpfrontFees() {
		return (getEBUpfrontFees() == null) ? null : new Amount(getEBUpfrontFees(), new CurrencyCode(
				getUpfrontFeesCurrency()));
	}

	public void setUpfrontFees(Amount value) {
		setEBUpfrontFees(value == null ? null : value.getAmountAsBigDecimal());
		setUpfrontFeesCurrency(value == null ? null : value.getCurrencyCode());
	}

	public abstract BigDecimal getEBUpfrontFees();

	public abstract void setEBUpfrontFees(BigDecimal value);

	public abstract String getLCFeesCurrency();

	public abstract void setLCFeesCurrency(String value);

	public Amount getLCFees() {
		return (getEBLCFees() == null) ? null : new Amount(getEBLCFees(), new CurrencyCode(getLCFeesCurrency()));
	}

	public void setLCFees(Amount value) {
		setEBLCFees(value == null ? null : value.getAmountAsBigDecimal());
		setLCFeesCurrency(value == null ? null : value.getCurrencyCode());
	}

	public abstract BigDecimal getEBLCFees();

	public abstract void setEBLCFees(BigDecimal value);

	public abstract String getAmendmentFeesCurrency();

	public abstract void setAmendmentFeesCurrency(String value);

	public Amount getAmendmentFees() {
		return (getEBAmendmentFees() == null) ? null : new Amount(getEBAmendmentFees(), new CurrencyCode(
				getAmendmentFeesCurrency()));
	}

	public void setAmendmentFees(Amount value) {
		setEBAmendmentFees(value == null ? null : value.getAmountAsBigDecimal());
		setAmendmentFeesCurrency(value == null ? null : value.getCurrencyCode());
	}

	public abstract BigDecimal getEBAmendmentFees();

	public abstract void setEBAmendmentFees(BigDecimal value);

	public abstract String getExtensionFeesCurrency();

	public abstract void setExtensionFeesCurrency(String value);

	public Amount getExtensionFees() {
		return (getEBExtensionFees() == null) ? null : new Amount(getEBExtensionFees(), new CurrencyCode(
				getExtensionFeesCurrency()));
	}

	public void setExtensionFees(Amount value) {
		setEBExtensionFees(value == null ? null : value.getAmountAsBigDecimal());
		setExtensionFeesCurrency(value == null ? null : value.getCurrencyCode());
	}

	public abstract BigDecimal getEBExtensionFees();

	public abstract void setEBExtensionFees(BigDecimal value);

	public abstract String getArrangementCurrency();

	public abstract void setArrangementCurrency(String value);

	public Amount getArrangementFees() {
		return (getEBArrangementFees() == null) ? null : new Amount(getEBArrangementFees(), new CurrencyCode(
				getArrangementCurrency()));
	}

	public void setArrangementFees(Amount value) {
		setEBArrangementFees(value == null ? null : value.getAmountAsBigDecimal());
		setArrangementCurrency(value == null ? null : value.getCurrencyCode());
	}

	public abstract BigDecimal getEBArrangementFees();

	public abstract void setEBArrangementFees(BigDecimal value);

	public abstract String getOtherFeesCurrency();

	public abstract void setOtherFeesCurrency(String value);

	public Amount getOtherFees() {
		return (getEBOtherFees() == null) ? null : new Amount(getEBOtherFees(),
				new CurrencyCode(getOtherFeesCurrency()));
	}

	public void setOtherFees(Amount value) {
		setEBOtherFees(value == null ? null : value.getAmountAsBigDecimal());
		setOtherFeesCurrency(value == null ? null : value.getCurrencyCode());
	}

	public abstract BigDecimal getEBOtherFees();

	public abstract void setEBOtherFees(BigDecimal value);

	public abstract String getFacilityType();

	public abstract void setFacilityType(String value);

	public abstract long getCommonRef();

	public abstract void setCommonRef(long x);

	public abstract String getStatus();

	public abstract void setStatus(String value);

	public ILoanLimit[] getLimitIDs() {
		Iterator i = getLimitsCMR().iterator();
		ArrayList arrayList = new ArrayList();

		while (i.hasNext()) {
			ILoanLimit loanLimit = ((EBLoanLimitLocal) i.next()).getValue();
			if ((loanLimit.getStatus() != null)
					&& loanLimit.getStatus().equals(ICMSConstant.SEQUENCE_COMMODITY_LOANLIMIT_AGENCY)) {
				continue;
			}

			arrayList.add(loanLimit);
		}

		return (ILoanLimit[]) arrayList.toArray(new ILoanLimit[arrayList.size()]);
	}

	public void setLimitIDs(ILoanLimit[] value) {
	}

	public IBorrower[] getBorrowers() {
		Iterator i = getBorrowersCMR().iterator();
		ArrayList arrayList = new ArrayList();

		while (i.hasNext()) {
			IBorrower borrower = ((EBBorrowerLocal) i.next()).getValue();
			if ((borrower.getStatus() != null) && borrower.getStatus().equals(ICMSConstant.STATE_DELETED)) {
				continue;
			}

			arrayList.add(borrower);
		}

		return (IBorrower[]) arrayList.toArray(new IBorrower[0]);
	}

	public void setBorrowers(IBorrower[] value) {
	}

	public IGuarantor[] getGuarantors() {
		Iterator i = getGuarantorsCMR().iterator();
		ArrayList arrayList = new ArrayList();

		while (i.hasNext()) {
			IGuarantor guarantor = ((EBGuarantorLocal) i.next()).getValue();
			if ((guarantor.getStatus() != null) && guarantor.getStatus().equals(ICMSConstant.STATE_DELETED)) {
				continue;
			}

			arrayList.add(guarantor);
		}

		return (IGuarantor[]) arrayList.toArray(new IGuarantor[0]);
	}

	public void setGuarantors(IGuarantor[] value) {
	}

	public ISubLimit[] getSubLimits() {
		Iterator i = getSubLimitsCMR().iterator();
		ArrayList arrayList = new ArrayList();

		while (i.hasNext()) {
			ISubLimit sublimit = ((EBSubLimitLocal) i.next()).getValue();
			if ((sublimit.getStatus() != null) && sublimit.getStatus().equals(ICMSConstant.STATE_DELETED)) {
				continue;
			}

			arrayList.add(sublimit);
		}

		return (ISubLimit[]) arrayList.toArray(new ISubLimit[0]);
	}

	public void setSubLimits(ISubLimit[] value) {
	}

	public ILoanSchedule[] getLoanSchedules() {
		Iterator i = getLoanSchedulesCMR().iterator();
		ArrayList arrayList = new ArrayList();

		while (i.hasNext()) {
			ILoanSchedule loanSchedule = ((EBLoanScheduleLocal) i.next()).getValue();
			if ((loanSchedule.getStatus() != null) && loanSchedule.getStatus().equals(ICMSConstant.STATE_DELETED)) {
				continue;
			}

			arrayList.add(loanSchedule);
		}

		ILoanSchedule[] schedules = (ILoanSchedule[]) arrayList.toArray(new ILoanSchedule[0]);
		Arrays.sort(schedules, new LoanScheduleComparator());

		return schedules;
	}

	public void setLoanSchedules(ILoanSchedule[] value) {
	}

	public String[] getGlobalCurrencies() {
		return decryptCurrencies(getGlobalCurrenciesStr());
	}

	public void setGlobalCurrencies(String[] value) {
		setGlobalCurrenciesStr(encryptCurrencies(value));
	}

	protected String encryptCurrencies(String[] currencies) {
		StringBuffer buffer = new StringBuffer("");
		if ((currencies != null) && (currencies.length > 0)) {
			for (int i = 0; i < currencies.length; i++) {
				buffer.append(currencies[i]);
				buffer.append(CURRENCY_DELIMITER);
			}
			return buffer.toString();
		}
		else {
			return null;
		}
	}

	protected String[] decryptCurrencies(String encryptedCurrencies) {
		String[] decryptedCuStrings = null;
		if (encryptedCurrencies != null) {
			StringTokenizer st = new StringTokenizer(encryptedCurrencies, CURRENCY_DELIMITER);
			ArrayList list = new ArrayList();
			while (st.hasMoreTokens()) {
				list.add(st.nextToken());
			}
			if (list.size() > 0) {
				decryptedCuStrings = new String[list.size()];
				decryptedCuStrings = (String[]) list.toArray(decryptedCuStrings);
			}
		}
		return decryptedCuStrings;
	}

	public IParticipant[] getParticipants() {
		Iterator i = getParticipantsCMR().iterator();
		ArrayList arrayList = new ArrayList();

		while (i.hasNext()) {
			IParticipant participant = ((EBParticipantLocal) i.next()).getValue();
			if ((participant.getStatus() != null) && participant.getStatus().equals(ICMSConstant.STATE_DELETED)) {
				continue;
			}

			arrayList.add(participant);
		}

		return (IParticipant[]) arrayList.toArray(new IParticipant[0]);
	}

	public void setParticipants(IParticipant[] value) {
	}

	public ILoanAgency getValue() {
		OBLoanAgency value = new OBLoanAgency();
		AccessorUtil.copyValue(this, value);
		return value;
	}

	public void setValue(ILoanAgency value) {
		AccessorUtil.copyValue(value, this, new String[] { "getLoanAgencyPK", "getLoanAgencyID" });
		setReferences(value, false);
	}

	/**
	 * Soft delete the loan and agency.
	 */
	public void softDelete() {
		this.setStatus(ICMSConstant.STATE_DELETED);
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param value - ILoanAgency object
	 * @throws CreateException on error creating the entity object
	 */
	public Long ejbCreate(ILoanAgency value) throws CreateException {
		try {

			AccessorUtil.copyValue(value, this, new String[] { "getLoanAgencyPK", "getLoanAgencyID" });

			if (isStaging) {
				String generatedPK = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_COMMODITY_LOAN_AGENCY,
						true);
				this.setLoanAgencyPK(new Long(generatedPK));
				if (!(value.getCommonRef() > 0)) {
					this.setCommonRef(Long.parseLong(generatedPK));
				}
			}
			else {
				this.setLoanAgencyPK(new Long(value.getLoanAgencyID()));
			}

			setStatus(ICMSConstant.STATE_ACTIVE);

			return null;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CreateException(e.toString());
		}
	}

	/**
	 * The container invokes this method after invoking the ejbCreate method
	 * with the same arguments.
	 * 
	 * @param value object of ILoanAgency
	 * @throws CreateException on error creating the references
	 */
	public void ejbPostCreate(ILoanAgency value) throws CreateException {
		setReferences(value, true);
	}

	/**
	 * EJB callback method to set the context of the bean.
	 * 
	 * @param context the entity context.
	 */
	public void setEntityContext(EntityContext context) {
		this.context = context;
	}

	/**
	 * EJB callback method to clears the context of the bean.
	 */
	public void unsetEntityContext() {
		this.context = null;
	}

	/**
	 * This method is called when the container picks this entity object and
	 * assigns it to a specific entity object. No implementation currently
	 * acquires any additional resources that it needs when it is in the ready
	 * state.
	 */
	public void ejbActivate() {
	}

	/**
	 * This method is called when the container diassociates the bean from the
	 * entity object identity and puts the instance back into the pool of
	 * available instances. No implementation is currently provided to release
	 * resources that should not be held while the instance is in the pool.
	 */
	public void ejbPassivate() {
	}

	/**
	 * The container invokes this method on the bean whenever it becomes
	 * necessary to synchronize the bean's state with the state in the database.
	 * This method is called after the container has loaded the bean's state
	 * from the database.
	 */
	public void ejbLoad() {
	}

	/**
	 * The container invokes this method on the bean whenever it becomes
	 * necessary to synchronize the state in the database with the state of the
	 * bean. This method is called before the container extracts the fields and
	 * writes them into the database.
	 */
	public void ejbStore() {
	}

	/**
	 * The container invokes this method in response to a client-invoked remove
	 * request. No implementation is currently provided for taking actions
	 * before the bean is removed from the database.
	 */
	public void ejbRemove() throws RemoveException {
		/*
		 * try { // remove Contracts removeBorrowers(); removeGuarantors();
		 * removeParticipants(); removeSubLimits();
		 * 
		 * }catch(RemoveException e) { throw new EJBException(e); }
		 */
	}

	/**
	 * Set the references to this loan agency.
	 * 
	 * @param loanAgency of type ILoanAgency
	 * @param isAdd true is to create new references, otherwise false
	 */
	private void setReferences(ILoanAgency loanAgency, boolean isAdd) {
		try {
			DefaultLogger.debug(this, "########## #### setting references for LoanAndAgency - ID : "
					+ getLoanAgencyID());
			setLimitsRef(loanAgency.getLimitIDs(), isAdd);
			setGuarantorsRef(loanAgency.getGuarantors(), isAdd);
			setBorrowersRef(loanAgency.getBorrowers(), isAdd);
			setParticipantsRef(loanAgency.getParticipants(), isAdd);
			setSubLimitsRef(loanAgency.getSubLimits(), isAdd);
			setLoanSchedulesRef(loanAgency.getLoanSchedules(), isAdd);
		}
		catch (Exception e) {
			throw new EJBException(e);
		}
	}

	/**
	 * Set limits for loan and agency.
	 * 
	 * @param limits of type ILoanLimit[]
	 * @param isAdd true if the caller is ejb post create, otherwise false
	 * @throws CreateException on error creating the limits
	 */
	private void setLimitsRef(ILoanLimit[] limits, boolean isAdd) throws CreateException// ,
																						// RemoveException
	{
		// get existing borrowers for the deal
		Collection existingList = getLimitsCMR();
		if (isAdd || existingList.isEmpty()) {
			// existing list is empty, add all in borrowers
			DefaultLogger.debug(this, "########## setLoanLimitsRef : existing list is empty");
			if ((limits != null) && (limits.length != 0)) {
				DefaultLogger.debug(this, "########## setLoanLimitsRef : new list is NOT empty");
				addLimit(Arrays.asList(limits), existingList);
				return;
			}
		}
		else {
			// existing list is NOT empty
			// if borrowers is empty , remove all borrowers
			DefaultLogger.debug(this, "########## setLoanLimitsRef : existing list is NOT empty");
			if ((limits == null) || (limits.length == 0)) {
				DefaultLogger.debug(this, "########## setLoanLimitsRef : new list is empty..remove all existing");
				removeLimit(existingList);
				return;
			}

			DefaultLogger.debug(this, "########## setLoanLimitsRef : new list not is empty..processing");
			// put existing list into hashmap - excludes deleted values
			HashMap existingListMap = new HashMap(existingList.size());
			Iterator existingListIterator = existingList.iterator();
			while (existingListIterator.hasNext()) {
				EBLoanLimitLocal limitLocal = (EBLoanLimitLocal) existingListIterator.next();
				ILoanLimit limit = limitLocal.getValue();
				if ((limit.getStatus() == null) || !limit.getStatus().equals(ICMSConstant.STATE_DELETED)) {
					existingListMap.put(new Long(limit.getCommonReferenceID()), limitLocal);
				}
			}

			ArrayList newList = new ArrayList();

			// compare borrowers with existing list
			for (int i = 0; i < limits.length; i++) {
				ILoanLimit limit = limits[i];
				Long commonRefID = new Long(limit.getCommonReferenceID());
				EBLoanLimitLocal theExistingEjb = (EBLoanLimitLocal) existingListMap.get(commonRefID);
				if (theExistingEjb != null) {
					theExistingEjb.setValue(limit);
					existingListMap.remove(commonRefID);
				}
				else {
					newList.add(limit);
				}
			}

			// add new borrowers
			addLimit(newList, existingList);

			// remove deleted borrowers
			EBLoanLimitLocal[] deletedList = (EBLoanLimitLocal[]) existingListMap.entrySet().toArray(
					new EBLoanLimitLocal[existingListMap.size()]);
			removeLimit(Arrays.asList(deletedList));
		}
	}

	/**
	 * Helper method to add all limits specified in the collection.
	 * 
	 * @param newLimits
	 * @param existingLimits
	 * @throws CreateException
	 */
	private void addLimit(Collection newLimits, Collection existingLimits) throws CreateException {
		EBLoanLimitLocalHome theEjbHome = _getLimitLocalHome();
		Iterator i = newLimits.iterator();
		while (i.hasNext()) {
			ILoanLimit limit = (ILoanLimit) i.next();
			existingLimits.add(theEjbHome.create(limit));
		}
	}

	/**
	 * Helper method to remove all limits specified in the collection.
	 * 
	 * @param limits - Collection of limits to be removed. Cannot be null.
	 */
	private void removeLimit(Collection limits) {
		Iterator i = limits.iterator();
		while (i.hasNext()) {
			EBLoanLimitLocal theEjb = (EBLoanLimitLocal) i.next();
			removeLimit(theEjb);
		}
	}

	/**
	 * Helper method to remove the said limits.
	 * 
	 * @param anEjb - EBLoanLimitLocal
	 */
	private void removeLimit(EBLoanLimitLocal anEjb) {
		anEjb.setStatus(ICMSConstant.STATE_DELETED);
	}

	/**
	 * Set borrowers for loan and agency.
	 * 
	 * @param borrowers of type IBorrower[]
	 * @param isAdd true if the caller is ejb post create, otherwise false
	 * @throws CreateException on error creating the borrowers
	 */
	private void setBorrowersRef(IBorrower[] borrowers, boolean isAdd) throws CreateException// ,
																								// RemoveException
	{
		// get existing borrowers for the deal
		Collection existingList = getBorrowersCMR();
		if (isAdd || existingList.isEmpty()) {
			// existing list is empty, add all in borrowers
			DefaultLogger.debug(this, "########## setBorrowersRef : existing list is empty");
			if ((borrowers != null) && (borrowers.length != 0)) {
				DefaultLogger.debug(this, "########## setBorrowersRef : new list is NOT empty");
				addBorrower(Arrays.asList(borrowers), existingList);
				return;
			}
		}
		else {
			// existing list is NOT empty
			// if borrowers is empty , remove all borrowers
			DefaultLogger.debug(this, "########## setBorrowersRef : existing list is NOT empty");
			if ((borrowers == null) || (borrowers.length == 0)) {
				DefaultLogger.debug(this, "########## setBorrowersRef : new list is empty..remove all existing");
				removeBorrower(existingList);
				return;
			}

			DefaultLogger.debug(this, "########## setBorrowersRef : new list not is empty..processing");
			// put existing list into hashmap - excludes deleted values
			HashMap existingListMap = new HashMap(existingList.size());
			Iterator existingListIterator = existingList.iterator();
			while (existingListIterator.hasNext()) {
				EBBorrowerLocal borrowerLocal = (EBBorrowerLocal) existingListIterator.next();
				IBorrower borrower = borrowerLocal.getValue();
				if ((borrower.getStatus() == null) || !borrower.getStatus().equals(ICMSConstant.STATE_DELETED)) {
					existingListMap.put(new Long(borrower.getCommonRef()), borrowerLocal);
				}
			}

			ArrayList newList = new ArrayList();

			// compare borrowers with existing list
			for (int i = 0; i < borrowers.length; i++) {
				IBorrower borrower = borrowers[i];
				Long commonRefID = new Long(borrower.getCommonRef());
				EBBorrowerLocal theExistingEjb = (EBBorrowerLocal) existingListMap.get(commonRefID);
				if (theExistingEjb != null) {
					theExistingEjb.setValue((OBBorrower) borrower);
					existingListMap.remove(commonRefID);
				}
				else {
					newList.add(borrower);
				}
			}

			// add new borrowers
			addBorrower(newList, existingList);

			// remove deleted borrowers
			EBBorrowerLocal[] deletedList = (EBBorrowerLocal[]) existingListMap.entrySet().toArray(
					new EBBorrowerLocal[existingListMap.size()]);
			removeBorrower(Arrays.asList(deletedList));
		}
	}

	/**
	 * Helper method to add all borrowers specified in the collection.
	 * 
	 * @param newBorrowers
	 * @param existingBorrowers
	 * @throws CreateException
	 */
	private void addBorrower(Collection newBorrowers, Collection existingBorrowers) throws CreateException {
		EBBorrowerLocalHome theEjbHome = _getBorrowerLocalHome();
		Iterator i = newBorrowers.iterator();
		while (i.hasNext()) {
			OBBorrower borrower = (OBBorrower) i.next();
			existingBorrowers.add(theEjbHome.create(borrower));
		}
	}

	/**
	 * Helper method to remove all borrowers specified in the collection.
	 * 
	 * @param borrowers - Collection of borrowers to be removed. Cannot be null.
	 */
	private void removeBorrower(Collection borrowers) {
		Iterator i = borrowers.iterator();
		while (i.hasNext()) {
			EBBorrowerLocal theEjb = (EBBorrowerLocal) i.next();
			removeBorrower(theEjb);
		}
	}

	/**
	 * Helper method to remove the said borrowers.
	 * 
	 * @param anEjb - EBBorrowerLocal
	 */
	private void removeBorrower(EBBorrowerLocal anEjb) {
		anEjb.setStatus(ICMSConstant.STATE_DELETED);
	}

	/**
	 * Set guarantors for loan and agency.
	 * 
	 * @param guarantors of type IGuarantor[]
	 * @param isAdd true if the caller is ejb post create, otherwise false
	 * @throws CreateException on error creating the guarantors
	 */
	private void setGuarantorsRef(IGuarantor[] guarantors, boolean isAdd) throws CreateException// ,
																								// RemoveException
	{
		// get existing cash guarantors for the deal
		Collection existingList = getGuarantorsCMR();
		if (isAdd || existingList.isEmpty()) {
			// existing list is empty, add all in guarantors
			DefaultLogger.debug(this, "########## setGuarantorsRef : existing list is empty");
			if ((guarantors != null) && (guarantors.length != 0)) {
				DefaultLogger.debug(this, "########## setGuarantorsRef : new list is NOT empty");
				addGuarantor(Arrays.asList(guarantors), existingList);
				return;
			}
		}
		else {
			// existing list is NOT empty
			// if deposits is empty , remove all cash deposits
			DefaultLogger.debug(this, "########## setGuarantorsRef : existing list is NOT empty");
			if ((guarantors == null) || (guarantors.length == 0)) {
				DefaultLogger.debug(this, "########## setGuarantorsRef : new list is empty..remove all existing");
				removeGuarantor(existingList);
				return;
			}

			DefaultLogger.debug(this, "########## setGuarantorsRef : new list not is empty..processing");
			// put existing list into hashmap - excludes deleted values
			HashMap existingListMap = new HashMap(existingList.size());
			Iterator existingListIterator = existingList.iterator();
			while (existingListIterator.hasNext()) {
				EBGuarantorLocal guarantorLocal = (EBGuarantorLocal) existingListIterator.next();
				IGuarantor guarantor = guarantorLocal.getValue();
				if ((guarantor.getStatus() == null) || !guarantor.getStatus().equals(ICMSConstant.STATE_DELETED)) {
					existingListMap.put(new Long(guarantor.getCommonRef()), guarantorLocal);
				}
			}

			ArrayList newList = new ArrayList();

			// compare guarantors with existing list
			for (int i = 0; i < guarantors.length; i++) {
				IGuarantor guarantor = guarantors[i];
				Long commonRefID = new Long(guarantor.getCommonRef());
				EBGuarantorLocal theExistingEjb = (EBGuarantorLocal) existingListMap.get(commonRefID);
				if (theExistingEjb != null) {
					theExistingEjb.setValue((OBGuarantor) guarantor);
					existingListMap.remove(commonRefID);
				}
				else {
					newList.add(guarantor);
				}
			}

			// add new guarantor
			addGuarantor(newList, existingList);

			// remove deleted guarantor
			EBGuarantorLocal[] deletedList = (EBGuarantorLocal[]) existingListMap.entrySet().toArray(
					new EBGuarantorLocal[existingListMap.size()]);
			removeGuarantor(Arrays.asList(deletedList));
		}
	}

	/**
	 * Helper method to add all guarantors specified in the collection.
	 * 
	 * @param newGuarantors
	 * @param existingGuarantors
	 * @throws CreateException
	 */
	private void addGuarantor(Collection newGuarantors, Collection existingGuarantors) throws CreateException {
		EBGuarantorLocalHome theEjbHome = _getGuarantorLocalHome();
		Iterator i = newGuarantors.iterator();
		while (i.hasNext()) {
			OBGuarantor guarantor = (OBGuarantor) i.next();
			existingGuarantors.add(theEjbHome.create(guarantor));
		}
	}

	/**
	 * Helper method to remove all guarantors specified in the collection.
	 * 
	 * @param guarantors - Collection of guarantors to be removed. Cannot be
	 *        null.
	 */
	private void removeGuarantor(Collection guarantors) {
		Iterator i = guarantors.iterator();
		while (i.hasNext()) {
			EBGuarantorLocal theEjb = (EBGuarantorLocal) i.next();
			removeGuarantor(theEjb);
		}
	}

	/**
	 * Helper method to remove the said guarantor.
	 * 
	 * @param anEjb - EBGuarantorLocal
	 */
	private void removeGuarantor(EBGuarantorLocal anEjb) {
		anEjb.setStatus(ICMSConstant.STATE_DELETED);
	}

	/**
	 * Set sub-limits for loan and agency.
	 * 
	 * @param sublimits of type ISubLimit[]
	 * @param isAdd true if the caller is ejb post create, otherwise false
	 * @throws CreateException on error creating the sublimits
	 */
	private void setSubLimitsRef(ISubLimit[] sublimits, boolean isAdd) throws CreateException// ,
																								// RemoveException
	{
		// get existing sublimits for the deal
		Collection existingList = getSubLimitsCMR();
		if (isAdd || existingList.isEmpty()) {
			// existing list is empty, add all in sublimits
			DefaultLogger.debug(this, "########## setSubLimitsRef : existing list is empty");
			if ((sublimits != null) && (sublimits.length != 0)) {
				DefaultLogger.debug(this, "########## setSubLimitsRef : new list is NOT empty");
				addSubLimit(Arrays.asList(sublimits), existingList);
				return;
			}
		}
		else {
			// existing list is NOT empty
			// if deposits is empty , remove all cash deposits
			DefaultLogger.debug(this, "########## setSubLimitsRef : existing list is NOT empty");
			if ((sublimits == null) || (sublimits.length == 0)) {
				DefaultLogger.debug(this, "########## setSubLimitsRef : new list is empty..remove all existing");
				removeSubLimit(existingList);
				return;
			}

			DefaultLogger.debug(this, "########## setSubLimitsRef : new list not is empty..processing");
			// put existing list into hashmap - excludes deleted values
			HashMap existingListMap = new HashMap(existingList.size());
			Iterator existingListIterator = existingList.iterator();
			while (existingListIterator.hasNext()) {
				EBSubLimitLocal sublimitLocal = (EBSubLimitLocal) existingListIterator.next();
				ISubLimit sublimit = sublimitLocal.getValue();
				if ((sublimit.getStatus() == null) || !sublimit.getStatus().equals(ICMSConstant.STATE_DELETED)) {
					existingListMap.put(new Long(sublimit.getCommonRef()), sublimitLocal);
				}
			}

			ArrayList newList = new ArrayList();

			// compare sublimits with existing list
			for (int i = 0; i < sublimits.length; i++) {
				ISubLimit sublimit = sublimits[i];
				Long commonRefID = new Long(sublimit.getCommonRef());
				EBSubLimitLocal theExistingEjb = (EBSubLimitLocal) existingListMap.get(commonRefID);
				if (theExistingEjb != null) {
					theExistingEjb.setValue(sublimit);
					existingListMap.remove(commonRefID);
				}
				else {
					newList.add(sublimit);
				}
			}

			// add new cash deposit
			addSubLimit(newList, existingList);

			// remove deleted cash deposit
			EBSubLimitLocal[] deletedList = (EBSubLimitLocal[]) existingListMap.entrySet().toArray(
					new EBSubLimitLocal[existingListMap.size()]);
			removeSubLimit(Arrays.asList(deletedList));
		}
	}

	/**
	 * Helper method to add all sublimits specified in the collection.
	 * 
	 * @param newSublimits
	 * @param existingSublimits
	 * @throws CreateException
	 */
	private void addSubLimit(Collection newSublimits, Collection existingSublimits) throws CreateException {
		EBSubLimitLocalHome theEjbHome = _getSubLimitLocalHome();
		Iterator i = newSublimits.iterator();
		while (i.hasNext()) {
			ISubLimit sublimit = (ISubLimit) i.next();
			existingSublimits.add(theEjbHome.create(sublimit));
		}
	}

	/**
	 * Helper method to remove all sublimits specified in the collection.
	 * 
	 * @param sublimits - Collection of sublimits to be removed. Cannot be null.
	 */
	private void removeSubLimit(Collection sublimits) {
		Iterator i = sublimits.iterator();
		while (i.hasNext()) {
			EBSubLimitLocal theEjb = (EBSubLimitLocal) i.next();
			removeSubLimit(theEjb);
		}
	}

	/**
	 * Helper method to remove the said sublimit.
	 * 
	 * @param anEjb - EBSubLimitLocal
	 */
	private void removeSubLimit(EBSubLimitLocal anEjb) {
		anEjb.setStatus(ICMSConstant.STATE_DELETED);
	}

	/**
	 * Set participants for loan and agency.
	 * 
	 * @param participants of type IParticipant[]
	 * @param isAdd true if the caller is ejb post create, otherwise false
	 * @throws CreateException on error creating the participants
	 */
	private void setParticipantsRef(IParticipant[] participants, boolean isAdd) throws CreateException// ,
																										// RemoveException
	{
		// get existing participants for the deal
		Collection existingList = getParticipantsCMR();
		if (isAdd || existingList.isEmpty()) {
			DefaultLogger.debug(this, "########## setParticipantsRef : existing list is empty");
			// existing list is empty, add all in participants
			if ((participants != null) && (participants.length != 0)) {
				DefaultLogger.debug(this, "########## setParticipantsRef : new list is NOT empty");
				addParticipant(Arrays.asList(participants), existingList);
				return;
			}
		}
		else {
			// existing list is NOT empty
			// if participants is empty , remove all participants
			DefaultLogger.debug(this, "########## setParticipantsRef : existing list is NOT empty");
			if ((participants == null) || (participants.length == 0)) {
				DefaultLogger.debug(this, "########## setParticipantsRef : new list is empty..remove all existing");
				removeParticipant(existingList);
				return;
			}

			DefaultLogger.debug(this, "########## setParticipantsRef : new list not is empty..processing");
			// put existing list into hashmap - excludes deleted values
			HashMap existingListMap = new HashMap(existingList.size());
			Iterator existingListIterator = existingList.iterator();
			while (existingListIterator.hasNext()) {
				EBParticipantLocal participantLocal = (EBParticipantLocal) existingListIterator.next();
				IParticipant participant = participantLocal.getValue();
				if ((participant.getStatus() == null) || !participant.getStatus().equals(ICMSConstant.STATE_DELETED)) {
					existingListMap.put(new Long(participant.getCommonRef()), participantLocal);
				}
			}

			ArrayList newList = new ArrayList();

			// compare participants with existing list
			for (int i = 0; i < participants.length; i++) {
				IParticipant paricipant = participants[i];
				Long commonRefID = new Long(paricipant.getCommonRef());
				EBParticipantLocal theExistingEjb = (EBParticipantLocal) existingListMap.get(commonRefID);
				if (theExistingEjb != null) {
					theExistingEjb.setValue(paricipant);
					existingListMap.remove(commonRefID);
				}
				else {
					newList.add(paricipant);
				}
			}

			// add new participants
			addParticipant(newList, existingList);

			// remove deleted participants
			EBParticipantLocal[] deletedList = (EBParticipantLocal[]) existingListMap.entrySet().toArray(
					new EBParticipantLocal[existingListMap.size()]);
			removeParticipant(Arrays.asList(deletedList));
		}
	}

	/**
	 * Helper method to add all participants specified in the collection.
	 * 
	 * @param newParticipants
	 * @param existingParticipants
	 * @throws CreateException
	 */
	private void addParticipant(Collection newParticipants, Collection existingParticipants) throws CreateException {
		EBParticipantLocalHome theEjbHome = _getParticipantLocalHome();
		Iterator i = newParticipants.iterator();
		while (i.hasNext()) {
			IParticipant participant = (IParticipant) i.next();
			existingParticipants.add(theEjbHome.create(participant));
		}
	}

	/**
	 * Helper method to remove all participants specified in the collection.
	 * 
	 * @param participants - Collection of participants to be removed. Cannot be
	 *        null.
	 */
	private void removeParticipant(Collection participants) {
		Iterator i = participants.iterator();
		while (i.hasNext()) {
			EBParticipantLocal theEjb = (EBParticipantLocal) i.next();
			removeParticipant(theEjb);
		}
	}

	/**
	 * Helper method to remove the said participant.
	 * 
	 * @param anEjb - EBParticipantLocal
	 */
	private void removeParticipant(EBParticipantLocal anEjb) {
		anEjb.setStatus(ICMSConstant.STATE_DELETED);
	}

	/**
	 * Set loan schedules for loan and agency.
	 * 
	 * @param schedules of type ILoanSchedule[]
	 * @param isAdd true if the caller is ejb post create, otherwise false
	 * @throws CreateException on error creating the schedules
	 */
	private void setLoanSchedulesRef(ILoanSchedule[] schedules, boolean isAdd) throws CreateException// ,
																										// RemoveException
	{
		// get existing schedules for the deal
		Collection existingList = getLoanSchedulesCMR();
		if (isAdd || existingList.isEmpty()) {
			// existing list is empty, add all in schedules
			DefaultLogger.debug(this, "########## setLoanSchedulesRef : existing list is empty");
			if ((schedules != null) && (schedules.length != 0)) {
				DefaultLogger.debug(this, "########## setLoanSchedulesRef : new list is NOT empty");
				addLoanSchedule(Arrays.asList(schedules), existingList);
				return;
			}
		}
		else {
			// existing list is NOT empty
			// if schedules is empty , remove all schedules
			DefaultLogger.debug(this, "########## setLoanSchedulesRef : existing list is NOT empty");
			if ((schedules == null) || (schedules.length == 0)) {
				DefaultLogger.debug(this, "########## setLoanSchedulesRef : new list is empty..remove all existing");
				removeLoanSchedule(existingList);
				return;
			}

			DefaultLogger.debug(this, "########## setLoanSchedulesRef : new list not is empty..processing");
			// put existing list into hashmap - excludes deleted values
			HashMap existingListMap = new HashMap(existingList.size());
			Iterator existingListIterator = existingList.iterator();
			while (existingListIterator.hasNext()) {
				EBLoanScheduleLocal scheduleLocal = (EBLoanScheduleLocal) existingListIterator.next();
				ILoanSchedule schedule = scheduleLocal.getValue();
				if ((schedule.getStatus() == null) || !schedule.getStatus().equals(ICMSConstant.STATE_DELETED)) {
					existingListMap.put(new Long(schedule.getCommonRef()), scheduleLocal);
				}
			}

			ArrayList newList = new ArrayList();

			// compare schedules with existing list
			for (int i = 0; i < schedules.length; i++) {
				ILoanSchedule schedule = schedules[i];
				Long commonRefID = new Long(schedule.getCommonRef());
				EBLoanScheduleLocal theExistingEjb = (EBLoanScheduleLocal) existingListMap.get(commonRefID);
				if (theExistingEjb != null) {
					theExistingEjb.setValue(schedule);
					existingListMap.remove(commonRefID);
				}
				else {
					newList.add(schedule);
				}
			}

			// add new schedules
			addLoanSchedule(newList, existingList);

			// remove deleted schedules
			EBLoanScheduleLocal[] deletedList = (EBLoanScheduleLocal[]) existingListMap.entrySet().toArray(
					new EBLoanScheduleLocal[existingListMap.size()]);
			removeLoanSchedule(Arrays.asList(deletedList));
		}
	}

	/**
	 * Helper method to add all loan schedules specified in the collection.
	 * 
	 * @param newSchedules
	 * @param existingSchedules
	 * @throws CreateException
	 */
	private void addLoanSchedule(Collection newSchedules, Collection existingSchedules) throws CreateException {
		EBLoanScheduleLocalHome theEjbHome = _getLoanScheduleLocalHome();
		Iterator i = newSchedules.iterator();
		while (i.hasNext()) {
			ILoanSchedule schedule = (ILoanSchedule) i.next();
			existingSchedules.add(theEjbHome.create(schedule));
		}
	}

	/**
	 * Helper method to remove all loan schedules specified in the collection.
	 * 
	 * @param schedules - Collection of loan schedules to be removed. Cannot be
	 *        null.
	 */
	private void removeLoanSchedule(Collection schedules) {
		Iterator i = schedules.iterator();
		while (i.hasNext()) {
			EBLoanScheduleLocal theEjb = (EBLoanScheduleLocal) i.next();
			removeLoanSchedule(theEjb);
		}
	}

	/**
	 * Helper method to remove the said loan schedule.
	 * 
	 * @param anEjb - EBLoanScheduleLocal
	 */
	private void removeLoanSchedule(EBLoanScheduleLocal anEjb) {
		anEjb.setStatus(ICMSConstant.STATE_DELETED);
	}

	private void removeBorrowers() throws RemoveException {
		Collection c = getBorrowersCMR();
		Iterator iterator = c.iterator();

		while (iterator.hasNext()) {
			EBBorrowerLocal theEjb = (EBBorrowerLocal) iterator.next();
			c.remove(theEjb);
			theEjb.remove();
			iterator = c.iterator();
		}
	}

	private void removeGuarantors() throws RemoveException {
		Collection c = getGuarantorsCMR();
		Iterator iterator = c.iterator();

		while (iterator.hasNext()) {
			EBGuarantorLocal theEjb = (EBGuarantorLocal) iterator.next();
			c.remove(theEjb);
			theEjb.remove();
			iterator = c.iterator();
		}
	}

	private void removeParticipants() throws RemoveException {
		Collection c = getParticipantsCMR();
		Iterator iterator = c.iterator();

		while (iterator.hasNext()) {
			EBParticipantLocal theEjb = (EBParticipantLocal) iterator.next();
			c.remove(theEjb);
			theEjb.remove();
			iterator = c.iterator();
		}
	}

	private void removeSubLimits() throws RemoveException {
		Collection c = getSubLimitsCMR();
		Iterator iterator = c.iterator();

		while (iterator.hasNext()) {
			EBSubLimitLocal theEjb = (EBSubLimitLocal) iterator.next();
			c.remove(theEjb);
			theEjb.remove();
			iterator = c.iterator();
		}
	}

	private void removeLoanSchedules() throws RemoveException {
		Collection c = getLoanSchedulesCMR();
		Iterator iterator = c.iterator();

		while (iterator.hasNext()) {
			EBLoanScheduleLocal theEjb = (EBLoanScheduleLocal) iterator.next();
			c.remove(theEjb);
			theEjb.remove();
			iterator = c.iterator();
		}
	}

	protected EBLoanLimitLocalHome _getLimitLocalHome() {
		EBLoanLimitLocalHome ejbHome = (EBLoanLimitLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_LOAN_LIMIT_LOCAL_JNDI, EBLoanLimitLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBLoanLimitLocalHome is Null!");
		}

		return ejbHome;
	}

	protected EBBorrowerLocalHome _getBorrowerLocalHome() {
		EBBorrowerLocalHome ejbHome = (EBBorrowerLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_BORROWER_LOCAL_JNDI, EBBorrowerLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBBorrowerLocalHome is Null!");
		}

		return ejbHome;
	}

	protected EBGuarantorLocalHome _getGuarantorLocalHome() {
		EBGuarantorLocalHome ejbHome = (EBGuarantorLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_GUARANTOR_LOCAL_JNDI, EBGuarantorLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBGuarantorLocalHome is Null!");
		}

		return ejbHome;
	}

	protected EBParticipantLocalHome _getParticipantLocalHome() {
		EBParticipantLocalHome ejbHome = (EBParticipantLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_PARTICIPANT_LOCAL_JNDI, EBParticipantLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBParticipantLocalHome is Null!");
		}

		return ejbHome;
	}

	protected EBSubLimitLocalHome _getSubLimitLocalHome() {
		EBSubLimitLocalHome ejbHome = (EBSubLimitLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_SUBLIMIT_LOCAL_JNDI, EBSubLimitLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBSubLimitLocalHome is Null!");
		}

		return ejbHome;
	}

	protected EBLoanScheduleLocalHome _getLoanScheduleLocalHome() {
		EBLoanScheduleLocalHome ejbHome = (EBLoanScheduleLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_LOANSCHEDULE_LOCAL_JNDI, EBLoanScheduleLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBLoanScheduleLocalHome is Null!");
		}

		return ejbHome;
	}
}
