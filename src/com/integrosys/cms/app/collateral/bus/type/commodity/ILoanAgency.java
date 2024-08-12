/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/ILoanAgency.java,v 1.20 2004/08/10 07:25:27 hshii Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;

/**
 * Created by IntelliJ IDEA. User: Administrator Date: May 3, 2004 Time:
 * 10:30:46 AM To change this template use File | Settings | File Templates.
 */
public interface ILoanAgency extends java.io.Serializable {

	/**
	 * The key to get list of PaymentFrequencies from CommonCode list
	 */
	public static final String PAYMENT_FREQUENCY_KEY = "PAYMENT_FREQ";

	public static final String CURRENCY_DELIMITER = ";";

	public long getLoanAgencyID();

	public ILoanLimit[] getLimitIDs();

	public String getAdministrativeAgentName();

	public String getCollateralAgentName();

	public String getDocumentAgent();

	public String getLCIssuingBank();

	public IBorrower[] getBorrowers();

	public IGuarantor[] getGuarantors();

	public ISubLimit[] getSubLimits();

	public ILoanSchedule[] getLoanSchedules();

	public Amount getGlobalAmount();

	public boolean getIsMultipleCurrencies();

	public String[] getGlobalCurrencies();

	public Date getFacilityCommitmentDate();

	public Date getFacilityEffectiveDate();

	public Date getFacilityMaturityDate();

	public Date getFacilityTerminationDate();

	public Date getLastDateToIssueLC();

	public Date getFinalLCMaturityDate();

	public String getTypeOfTransaction(); // commoncode - list char(1)

	public String getAgentBankCounselName();

	public IParticipant[] getParticipants();

	// Term Loan Amortization Schedules

	// public boolean isEqualInstalments();
	public boolean getIsEqualInstalments();

	public Amount getPrincipleAmount();

	public int getNumberOfInstalments();

	public String getFrequencyOfPayment();

	public Date getDateOfPayment();

	public double getDebtRate();

	public int getCalculationBaseNumberOfDays();

	// public boolean isTermOutOption();
	public boolean getIsTermOutOption();

	public boolean getIsPrepaymentOption();

	public Amount getPrepaymentMinAmount();

	public boolean getIsPrepaymentPenalty();

	public int getNumberOfNoticeDays();

	public String getGoverningLaw();

	public String getInterestPeriodDurationUnit(); // commoncode - list char(1)

	public int getInterestPeriodDuration();

	public int getMaxNumberOfLoanOutstanding();

	public Amount getMinDrawdownAmountAllowed();

	public int getMinNumberOfDrawdownsAllowed();

	public Amount getMinAssignmentFees();

	public Amount getMaxDrawdownAmountAllowed();

	public int getMaxNumberOfDrawdownsAllowed();

	public Amount getMaxAssignmentFees();

	public boolean getIsConsentFromBorrower();

	public double getMajorityLendersConsent();

	public double getDefaultRate();

	// Fees
	public Amount getAgencyFees();

	public Amount getClosingFees();

	public Amount getCommitmentFees();

	public Amount getFacilityFees();

	public Amount getUpfrontFees();

	public Amount getLCFees();

	public Amount getAmendmentFees();

	public Amount getExtensionFees();

	public Amount getArrangementFees();

	public Amount getOtherFees();

	public String getFacilityType(); // commoncode - list

	public String getStatus();

	public long getCommonRef();
}
