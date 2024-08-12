/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/OBValuation.java,v 1.22 2006/06/23 01:54:16 hmbao Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents valuation details of the collateral.
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.22 $
 * @since $Date: 2006/06/23 01:54:16 $ Tag: $Name: $
 */
public class OBValuation implements IValuation {

	private static final long serialVersionUID = -2154544894001201940L;

	private long valuationID = ICMSConstant.LONG_MIN_VALUE;

	private long collateralID = ICMSConstant.LONG_MIN_VALUE;

	private String valuerName;

	private Date valuationDate;

	private Date updateDate;

	private String currencyCode;

	private Amount cMV;

	private Amount fSV;

	private Amount beforeMarginValue;

	private Amount afterMarginValue;

	private int revaluationFreq;

	private String revaluationFreqUnit;

	private Date fSVEvaluationDate;

	private Date revaluationDate;

	private Date lastEvaluationDate;

	private Date nextRevaluationDate;

	private int nonRevaluationFreq = ICMSConstant.INT_INVALID_VALUE;

	private String nonRevaluationFreqUnit;

	private Amount olv;

	private Double remainusefullife;

	private String valuationbasis;

	private String comments;

	private String sourceId;

	private String sourceType;

	private String valuationType;

	private Amount reservePrice;

	private Date reservePriceDate;

	private Date evaluationDateFSV;

	private Long losValuationId;

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getValuationType() {
		return valuationType;
	}

	public void setValuationType(String valuationType) {
		this.valuationType = valuationType;
	}

	public Amount getReservePrice() {
		return reservePrice;
	}

	public void setReservePrice(Amount reservePrice) {
		this.reservePrice = reservePrice;
	}

	public Date getReservePriceDate() {
		return reservePriceDate;
	}

	public void setReservePriceDate(Date reservePriceDate) {
		this.reservePriceDate = reservePriceDate;
	}

	public Date getEvaluationDateFSV() {
		return evaluationDateFSV;
	}

	public void setEvaluationDateFSV(Date evaluationDateFSV) {
		this.evaluationDateFSV = evaluationDateFSV;
	}

	/**
	 * Default Constructor.
	 */
	public OBValuation() {
	}

	/**
	 * Construct the object from its interface.
	 * @param obj is of type IValuation
	 */
	public OBValuation(IValuation obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Get valuation id.
	 * 
	 * @return long
	 */
	public long getValuationID() {
		return valuationID;
	}

	/**
	 * Set valuation id.
	 * 
	 * @param valuationID is of type long
	 */
	public void setValuationID(long valuationID) {
		this.valuationID = valuationID;
	}

	/**
	 * Get collateral id.
	 * 
	 * @return long
	 */
	public long getCollateralID() {
		return collateralID;
	}

	/**
	 * Set collateral id.
	 * 
	 * @param collateralID of type long
	 */
	public void setCollateralID(long collateralID) {
		this.collateralID = collateralID;
	}

	/**
	 * Get valuer name.
	 * 
	 * @return String
	 */
	public String getValuerName() {
		return valuerName;
	}

	/**
	 * Set valuer name.
	 * 
	 * @param valuerName is of type String
	 */
	public void setValuerName(String valuerName) {
		this.valuerName = valuerName;
	}

	/**
	 * Get the valuation date.
	 * 
	 * @return Date
	 */
	public Date getValuationDate() {
		return valuationDate;
	}

	/**
	 * Set valuation date.
	 * 
	 * @param valuationDate is of type Date
	 */
	public void setValuationDate(Date valuationDate) {
		this.valuationDate = valuationDate;
	}

	/**
	 * Get the update date.
	 * 
	 * @return Date
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * Set update date.
	 * 
	 * @param updateDate is of type Date
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * Get currency used for the valuation.
	 * 
	 * @return String
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * Set currency used for the valuation.
	 * 
	 * @param currencyCode is of type String
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * Get current market value (CMV).
	 * 
	 * @return Amount
	 */
	public Amount getCMV() {
		return cMV;
	}

	/**
	 * Set current market value.
	 * 
	 * @param cMV is of type Amount
	 */
	public void setCMV(Amount cMV) {
		this.cMV = cMV;
	}

	/**
	 * Get Forced Sale Value (FSV).
	 * 
	 * @return Amount
	 */
	public Amount getFSV() {
		return fSV;
	}

	/**
	 * set forced sale value.
	 * 
	 * @param fSV is of type Amount
	 */
	public void setFSV(Amount fSV) {
		this.fSV = fSV;
	}

	/**
	 * Get value before margin.
	 * 
	 * @return Amount
	 */

	public Amount getOlv() {
		return olv;
	}

	public void setOlv(Amount olv) {
		this.olv = olv;
	}

	public Double getRemainusefullife() {
		return remainusefullife;
	}

	public void setRemainusefullife(Double remainusefullife) {
		this.remainusefullife = remainusefullife;
	}

	public String getValuationbasis() {
		return valuationbasis;
	}

	public void setValuationbasis(String valuationbasis) {
		this.valuationbasis = valuationbasis;
	}

	public Amount getBeforeMarginValue() {
		return beforeMarginValue;
	}

	/**
	 * Set value before margin.
	 * 
	 * @param beforeMarginValue of type Amount
	 */
	public void setBeforeMarginValue(Amount beforeMarginValue) {
		this.beforeMarginValue = beforeMarginValue;
	}

	/**
	 * Get revaluation frequency.
	 * 
	 * @return int
	 */
	public int getRevaluationFreq() {
		return revaluationFreq;
	}

	/**
	 * Set revaluation frequency.
	 * 
	 * @param revaluationFreq of type int
	 */
	public void setRevaluationFreq(int revaluationFreq) {
		this.revaluationFreq = revaluationFreq;
	}

	/**
	 * Get unit of measurement of revaluation frequency.
	 * 
	 * @return String
	 */
	public String getRevaluationFreqUnit() {
		return revaluationFreqUnit;
	}

	/**
	 * Set unit of measurement of revaluation frequency.
	 * 
	 * @param revaluationFreqUnit of type String
	 */
	public void setRevaluationFreqUnit(String revaluationFreqUnit) {
		this.revaluationFreqUnit = revaluationFreqUnit;
	}

	/**
	 * Get evaluation date of FSV.
	 * 
	 * @return Date
	 */
	public Date getFSVEvaluationDate() {
		return fSVEvaluationDate;
	}

	/**
	 * Set evaluation date of FSV.
	 * 
	 * @param fSVEvaluationDate of type Date
	 */
	public void setFSVEvaluationDate(Date fSVEvaluationDate) {
		this.fSVEvaluationDate = fSVEvaluationDate;
	}

	/**
	 * Get revaluation date.
	 * 
	 * @return Date
	 */
	public Date getRevaluationDate() {
		return revaluationDate;
	}

	/**
	 * Set revaluation date.
	 * 
	 * @param revaluationDate of type Date
	 */
	public void setRevaluationDate(Date revaluationDate) {
		this.revaluationDate = revaluationDate;
	}

	/**
	 * Get value after margin.
	 * 
	 * @return Amount
	 */
	public Amount getAfterMarginValue() {
		return afterMarginValue;
	}

	/**
	 * Set value after margin.
	 * 
	 * @param afterMarginValue of type Amount
	 */
	public void setAfterMarginValue(Amount afterMarginValue) {
		this.afterMarginValue = afterMarginValue;
	}

	/**
	 * Get last evaluation date.
	 * 
	 * @return Date
	 */
	public Date getLastEvaluationDate() {
		return lastEvaluationDate;
	}

	/**
	 * Set last evaluation date.
	 * 
	 * @param lastEvaluationDate of type Date
	 */
	public void setLastEvaluationDate(Date lastEvaluationDate) {
		this.lastEvaluationDate = lastEvaluationDate;
	}

	/**
	 * Get next revaluation date.
	 * 
	 * @return Date
	 */
	public Date getNextRevaluationDate() {
		return nextRevaluationDate;
	}

	/**
	 * Set next revaluation date.
	 * 
	 * @param nextRevaluationDate of type Date
	 */
	public void setNextRevaluationDate(Date nextRevaluationDate) {
		this.nextRevaluationDate = nextRevaluationDate;
	}

	/**
	 * Get non revaluation frequency.
	 * 
	 * @return int
	 */
	public int getNonRevaluationFreq() {
		return nonRevaluationFreq;
	}

	/**
	 * Set non revaluation frequency.
	 * 
	 * @param nonRevaluationFreq of type int
	 */
	public void setNonRevaluationFreq(int nonRevaluationFreq) {
		this.nonRevaluationFreq = nonRevaluationFreq;
	}

	/**
	 * Get unit of measurement of non revaluation frequency.
	 * 
	 * @return String
	 */
	public String getNonRevaluationFreqUnit() {
		return nonRevaluationFreqUnit;
	}

	/**
	 * Set unit of measurement of non revaluation frequency.
	 * 
	 * @param nonRevaluationFreqUnit of type String
	 */
	public void setNonRevaluationFreqUnit(String nonRevaluationFreqUnit) {
		this.nonRevaluationFreqUnit = nonRevaluationFreqUnit;
	}

	// add by zhan jia
	/**
	 * Get remarks
	 * 
	 * @return String
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * Set remarks
	 * 
	 * @param comments of type String
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * Return a String representation of this object.
	 * 
	 * @return String
	 */
	public String toString() {
		StringBuffer buf = new StringBuffer(getClass().getName());
		buf.append("@").append(System.identityHashCode(this)).append("; ");
		buf.append("CMS Collateral Id [").append(collateralID).append("], ");
		buf.append("Valuation Type [").append(valuationType).append("], ");
		buf.append("Source Type [").append(sourceType).append("], ");
		buf.append("CMV [").append(cMV).append("], ");
		buf.append("FSV [").append(fSV).append("], ");
		buf.append("Valuation Date [").append(valuationDate).append("]");

		return buf.toString();
	}

	/**
	 * Return the hash code
	 * 
	 * @return int
	 */
	public int hashCode() {
		String hash = String.valueOf(valuationID);
		return hash.hashCode();
	}

	/**
	 * Test for equality.
	 * 
	 * @param obj is of type Object
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		else if (!(obj instanceof OBValuation)) {
			return false;
		}
		else {
			IValuation that = (IValuation) obj;
			String thisValuerName = this.getValuerName() == null ? "" : this.getValuerName();
			String thatValuerName = that.getValuerName() == null ? "" : that.getValuerName();
			String thisCcyCode = this.getCurrencyCode() == null ? "" : this.getCurrencyCode();
			String thatCcyCode = that.getCurrencyCode() == null ? "" : that.getCurrencyCode();
			String thisReUnit = this.getRevaluationFreqUnit() == null ? "" : this.getRevaluationFreqUnit();
			String thatReUnit = that.getRevaluationFreqUnit() == null ? "" : that.getRevaluationFreqUnit();
			String thisNonReUnit = this.getNonRevaluationFreqUnit() == null ? "" : this.getNonRevaluationFreqUnit();
			String thatNonReUnit = that.getNonRevaluationFreqUnit() == null ? "" : that.getNonRevaluationFreqUnit();
			String thisValuationbasis = this.getValuationbasis() == null ? "" : this.getValuationbasis();
			String thatValuationbasis = that.getValuationbasis() == null ? "" : that.getValuationbasis();
			Double thisRemainusefullife = new Double(0.0);
			Double thatRemainusefullife = new Double(0.0);
			if (this.getRemainusefullife() != null) {
				thisRemainusefullife = this.getRemainusefullife();
			}

			if (that.getRemainusefullife() != null) {
				thatRemainusefullife = that.getRemainusefullife();
			}

			/*
			 * Amount thisFsv = this.getFSV() == null ? new Amount (0, "") :
			 * this.getFSV(); Amount thatFsv = that.getFSV() == null ? new
			 * Amount (0, "") : that.getFSV(); Amount thisCmv = this.getCMV() ==
			 * null ? new Amount (0, "") : this.getCMV(); Amount thatCmv =
			 * that.getCMV() == null ? new Amount (0, "") : that.getCMV();
			 * Amount thisBeforeM = this.getBeforeMarginValue() == null ? new
			 * Amount (0, "") : this.getBeforeMarginValue(); Amount thatBeforeM
			 * = that.getBeforeMarginValue() == null ? new Amount (0, "") :
			 * that.getBeforeMarginValue();
			 */
			Amount thisFsv = !isValidAmt(this.getFSV()) ? new Amount(this.getFSV() != null ? this.getFSV()
					.getAmountAsDouble() : 0, "") : this.getFSV();
			Amount thatFsv = !isValidAmt(that.getFSV()) ? new Amount(that.getFSV() != null ? that.getFSV()
					.getAmountAsDouble() : 0, "") : that.getFSV();
			Amount thisCmv = !isValidAmt(this.getCMV()) ? new Amount(this.getCMV() != null ? this.getCMV()
					.getAmountAsDouble() : 0, "") : this.getCMV();
			Amount thatCmv = !isValidAmt(that.getCMV()) ? new Amount(that.getCMV() != null ? that.getCMV()
					.getAmountAsDouble() : 0, "") : that.getCMV();
			Amount thisBeforeM = !isValidAmt(this.getBeforeMarginValue()) ? new Amount(
					this.getBeforeMarginValue() != null ? this.getBeforeMarginValue().getAmountAsDouble() : 0, "")
					: this.getBeforeMarginValue();
			Amount thatBeforeM = !isValidAmt(that.getBeforeMarginValue()) ? new Amount(
					that.getBeforeMarginValue() != null ? that.getBeforeMarginValue().getAmountAsDouble() : 0, "")
					: that.getBeforeMarginValue();
			Amount thisOlv = !isValidAmt(this.getOlv()) ? new Amount(this.getOlv() != null ? this.getOlv()
					.getAmountAsDouble() : 0, "") : this.getOlv();
			Amount thatOlv = !isValidAmt(that.getOlv()) ? new Amount(that.getOlv() != null ? that.getOlv()
					.getAmountAsDouble() : 0, "") : that.getOlv();

			Date date = new Date();
			Date thisValDate = this.getValuationDate() == null ? date : this.getValuationDate();
			Date thatValDate = that.getValuationDate() == null ? date : that.getValuationDate();

			String thisResvPrice = this.getReservePrice() == null ? "" : this.getReservePrice().toString();
			String thatResvPrice = that.getReservePrice() == null ? "" : that.getReservePrice().toString();

			String thisResvPriceDate = this.getReservePriceDate() == null ? "" : String.valueOf(this
					.getReservePriceDate().getTime());
			String thatResvPriceDate = that.getReservePriceDate() == null ? "" : String.valueOf(that
					.getReservePriceDate().getTime());

			String thisComments = "";
			String thatComments = "";
			if (this.getComments() == null) {
				if (that.getComments() != null) {
					return false;
				}
			}
			else {
				thisComments = this.getComments();
			}
			if (that.getComments() != null) {
				thatComments = that.getComments();
			}
			if ( // this.hashCode() == that.hashCode() &&
			// this.getValuationID() == that.getValuationID() &&
			thisValuerName.equals(thatValuerName) && thisValDate.equals(thatValDate) && thisCcyCode.equals(thatCcyCode)
					&& thisCmv.equals(thatCmv) && thisFsv.equals(thatFsv) && thisResvPrice.equals(thatResvPrice)
					&& thisResvPriceDate.equals(thatResvPriceDate) && thisOlv.equals(thatOlv)
					&& (thisRemainusefullife == thatRemainusefullife) && (thisValuationbasis == thatValuationbasis)) {
				return true;
			}
			else {
				return false;
			}
		}
	}

	private boolean isValidAmt(Amount amt) {
		if (amt == null) {
			return false;
		}

		if ((amt.getCurrencyCode() == null) || (amt.getCurrencyCode().length() == 0)) {
			return false;
		}

		return true;
	}

	public boolean isEmpty() {

		if (((getValuerName() != null) && (getValuerName().trim().length() > 0))
				|| (getValuationDate() != null)
				|| ((getCurrencyCode() != null) && (getCurrencyCode().trim().length() > 0))
				|| ((getCMV() != null) && (getCMV().getAmountAsBigDecimal().longValue() != ICMSConstant.LONG_INVALID_VALUE))
				|| ((getFSV() != null) && (getFSV().getAmountAsBigDecimal().longValue() != ICMSConstant.LONG_INVALID_VALUE))
				|| ((getValuationType() != null) && (getValuationType().trim().length() > 0))
				|| ((getReservePrice() != null) && (getReservePrice().getAmountAsBigDecimal().longValue() != ICMSConstant.LONG_INVALID_VALUE))
				|| (getReservePriceDate() != null)) {
			return false;
		}
		else {
			return true;
		}
	}

	public Long getLosValuationId() {
		return this.losValuationId;
	}

	public void setLosValuationId(Long losValuationId) {
		this.losValuationId = losValuationId;
	}
}