/**
 * 
 */
package com.integrosys.cms.batch.sibs.creditapplication;

import java.util.Date;

/**
 * @author gp loh
 * @date 10 oct 08, 1938hr
 *
 */
public interface ICreditApplicationNplClosedAcc extends ICreditApplication {

	/* Account Status */
	public void setAccountStatus(String accSts);
	public String getAccountStatus();

	/* NPL Status */
	public void setNplStatus(String nplSts);
	public String getNplStatus();

	/** Date Settled of outstanding amount **/
	public void setDateSettled(Date dtStld);
	public Date   getDateSettled();

	/** Amount partially written off **/
	public void setAmtPartiallyWrittenOff(double amtPartWrtOff);
	public double getAmtPartiallyWrittenOff();

	/** Date of NPL **/
	public void setDateNpl(Date dtNPL);
	public Date   getDateNpl();

	/** Interest in suspense **/
	public void setInterestInSuspense(double intrstInSusp);
	public double getInterestInSuspense();

	/** Specific Provision Charge to Account  **/
	public void setSpecProvisionChrgToAccount(double spcProvsnChrg2Acc);
	public double getSpecProvisionChrgToAccount();

	/** Partial payment received **/
	public void setPartPaymentReceived(double partPaymtRx);
	public double getPartPaymentReceived();

	/** Date Latest Doubtful **/
    public void setDateLatestDoubtful(Date dtLtstDbtfl);
    public Date   getDateLatestDoubtful();

    /** Date of Judgement **/
    public void    setDateJudgement(Date dtJdgmt);
    public Date   getDateJudgement();

    /** Judgement Sum  **/
    public void setJudgementSum(double jdgmtSum);
    public double getJudgementSum();

    /** Date Write Off **/
    public void setDateWriteOff(Date dtWrtOff);
    public Date   getDateWriteOff();

    /** Amount Written Off **/
    public void setAmountWrittenOff(double amtWrtnOff);
    public double getAmountWrittenOff();

    /** Months INstallment Arrears ***/
    public void setMonthsInstallmentsArrears(double mthInstlmtArrs);
    public double getMonthsInstallmentsArrears();

    /*** MOnths INterest Arrears ***/
    public void setMonthsInterestArrears(double mthsIntrstArrs);
    public double getMonthsInterestArrears();

}
