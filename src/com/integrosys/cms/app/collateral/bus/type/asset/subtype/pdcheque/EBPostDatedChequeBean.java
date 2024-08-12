/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/pdcheque/EBPostDatedChequeBean.java,v 1.12 2004/02/10 08:28:27 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque;

import java.math.BigDecimal;
import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Entity bean implementation for post dated cheque.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.12 $
 * @since $Date: 2004/02/10 08:28:27 $ Tag: $Name: $
 */
public abstract class EBPostDatedChequeBean implements IPostDatedCheque, EntityBean {
	/** The container assigned reference to the entity. */
	private EntityContext context;

	/** A list of methods to be excluded during update to the equity. */
	private static final String[] EXCLUDE_METHOD = new String[] { "getPostDatedChequeID", "getRefID" };

	/**
	 * Get post dated cheque id.
	 * 
	 * @return long
	 */
	public long getPostDatedChequeID() {
		return getEBPostDatedChequeID().longValue();
	}

	/**
	 * Set post dated cheque id.
	 * 
	 * @param postDatedChequeID of type long
	 */
	public void setPostDatedChequeID(long postDatedChequeID) {
		setEBPostDatedChequeID(new Long(postDatedChequeID));
	}

	/**
	 * Get margin.
	 * 
	 * @return double
	 */
	public double getMargin() {
		if (getEBMargin() == null) {
			return ICMSConstant.DOUBLE_INVALID_VALUE;
		}
		else {
			return getEBMargin().doubleValue();
		}
	}

	/**
	 * Set margin.
	 * 
	 * @param margin of type double
	 */
	public void setMargin(double margin) {
		if (margin == ICMSConstant.DOUBLE_INVALID_VALUE) {
			setEBMargin(null);
		}
		else {
			setEBMargin(new Double(margin));
		}
	}

	/**
	 * Get amount of cheque.
	 * 
	 * @return Amount
	 */
	public Amount getChequeAmount() {
		return new Amount(getEBChequeAmount(),new CurrencyCode("INR"));
	}

	/**
	 * Set amount of cheque.
	 * 
	 * @param chequeAmount is of type Amount
	 */
	public void setChequeAmount(Amount chequeAmount) {
		if (chequeAmount != null) {
			setEBChequeAmount(chequeAmount.getAmountAsBigDecimal());
		}
	}

	/**
	 * Get proceeds of receivables controlled by own bank.
	 * 
	 * @return boolean
	 */
	public boolean getIsOwnProceedsOfReceivables() {
		String isOwn = getEBIsOwnProceedsOfReceivables();
		if ((isOwn != null) && isOwn.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	/**
	 * Set proceeds of receivables controlled by own bank.
	 * 
	 * @param isOwnProceedsOfReceivables is of type boolean
	 */
	public void setIsOwnProceedsOfReceivables(boolean isOwnProceedsOfReceivables) {
		if (isOwnProceedsOfReceivables) {
			setEBIsOwnProceedsOfReceivables(ICMSConstant.TRUE_VALUE);
		}
		else {
			setEBIsOwnProceedsOfReceivables(ICMSConstant.FALSE_VALUE);
		}
	}

	/**
	 * Get value before margin.
	 * 
	 * @return Amount
	 */
	public Amount getBeforeMarginValue() {
		if (getEBBeforeMarginValue() != null) {
			return new Amount(getEBBeforeMarginValue().doubleValue(), getValuationCcyCode());
		}
		else {
			return null;
		}
	}

	/**
	 * Set value before margin.
	 * 
	 * @param beforeMarginValue of type Amount
	 */
	public void setBeforeMarginValue(Amount beforeMarginValue) {
		if (beforeMarginValue != null) {
			setEBBeforeMarginValue(new Double(beforeMarginValue.getAmountAsDouble()));
		}
		else {
			setEBBeforeMarginValue(null);
		}
	}

	/**
	 * Get value before margin.
	 * 
	 * @return Amount
	 */
	public Amount getAfterMarginValue() {
		if (getEBAfterMarginValue() != null) {
			return new Amount(getEBAfterMarginValue().doubleValue(), getValuationCcyCode());
		}
		else {
			return null;
		}
	}

	/**
	 * Set value before margin.
	 * 
	 * @param beforeMarginValue of type Amount
	 */
	public void setAfterMarginValue(Amount afterMarginValue) {
		if (afterMarginValue != null) {
			setEBAfterMarginValue(new Double(afterMarginValue.getAmountAsDouble()));
		}
		else {
			setEBAfterMarginValue(null);
		}
	}

	public abstract Long getEBPostDatedChequeID();

	public abstract void setEBPostDatedChequeID(Long eBPostDatedChequeID);

	public abstract Double getEBMargin();

	public abstract void setEBMargin(Double eBMargin);

	public abstract BigDecimal getEBChequeAmount();

	public abstract void setEBChequeAmount(BigDecimal eBChequeAmount);

	public abstract String getEBIsOwnProceedsOfReceivables();

	public abstract void setEBIsOwnProceedsOfReceivables(String eBIsOwnProceedsOfReceivables);

	public abstract Double getEBBeforeMarginValue();

	public abstract void setEBBeforeMarginValue(Double eBBeforeMarginValue);

	public abstract Double getEBAfterMarginValue();

	public abstract void setEBAfterMarginValue(Double eBAfterMarginValue);

	public abstract void setStatus(String status);

	/**
	 * Get post dated cheque.
	 * 
	 * @return IPostDatedCheque
	 */
	public IPostDatedCheque getValue() {
		OBPostDatedCheque ob = new OBPostDatedCheque();
		AccessorUtil.copyValue(this, ob);
		return ob;
	}

	/**
	 * Set post dated cheque.
	 * 
	 * @param cheque of type IPostDatedCheque
	 */
	public void setValue(IPostDatedCheque cheque) {
		AccessorUtil.copyValue(cheque, this, EXCLUDE_METHOD);
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param cheque of type IPostDatedCheque
	 * @return primary key
	 * @throws CreateException on error creating the entity object
	 */
	public Long ejbCreate(IPostDatedCheque cheque) throws CreateException {
		try {
			String chequeID = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_ASSET_PDC, true);
			AccessorUtil.copyValue(cheque, this, EXCLUDE_METHOD);
			this.setEBPostDatedChequeID(new Long(chequeID));
			if (cheque.getRefID() == ICMSConstant.LONG_MIN_VALUE) {
				setRefID(getPostDatedChequeID());
			}
			else {
				// else maintain this reference id.
				setRefID(cheque.getRefID());
			}
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
	 * @param cheque of type IPostDatedCheque
	 */
	public void ejbPostCreate(IPostDatedCheque cheque) {
	}

	/**
	 * EJB callback method to set the context of the bean.
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
	public void ejbRemove() {
	}

	public abstract long getRefID();

	public abstract void setRefID(long refID);

	public abstract String getChequeCcyCode();

	public abstract void setChequeCcyCode(String chequeCcyCode);

	public abstract String getIssuerName();

	public abstract void setIssuerName(String issuerName);

	public abstract String getDraweeBank();

	public abstract void setDraweeBank(String draweeBank);

	public abstract String getCountry();

	public abstract void setCountry(String country);

	public abstract Date getIssueDate();

	public abstract void setIssueDate(Date issueDate);

	public abstract Date getExpiryDate();

	public abstract void setExpiryDate(Date expiryDate);

	public abstract String getChequeType();

	public abstract void setChequeType(String chequeType);

	public abstract String getOwnAccNo();

	public abstract void setOwnAccNo(String ownAccNo);

	public abstract String getOwnAccNoLocation();

	public abstract void setOwnAccNoLocation(String ownAccNoLocation);

	public abstract String getCollateralCustodian();

	public abstract void setCollateralCustodian(String collateralCustodian);

	public abstract String getCollateralCustodianType();

	public abstract void setCollateralCustodianType(String collateralCustodianType);

	public abstract String getIsExchangeCtrlObtained();

	public abstract void setIsExchangeCtrlObtained(String isExchangeCtrlObtained);

	public abstract Date getValuationDate();

	public abstract void setValuationDate(Date valuationDate);

	public abstract String getValuationCcyCode();

	public abstract void setValuationCcyCode(String valuationCcyCode);

	public abstract String getStatus();

	public abstract Date getExchangeCtrlDate();

	public abstract void setExchangeCtrlDate(Date exchangeCtrlDate);
	
	
	
	public abstract String getChequeNumber();

	public abstract void setChequeNumber(String chequeNumber);
	public abstract String getReturnStatus();
	public abstract void setReturnStatus(String returnStatus);
	public abstract Date getReturnDate();

	public abstract void setReturnDate(Date returnDate);
	
	public abstract String getBankCode();

	public abstract void setBankCode(String bankCode);
	public abstract String getPacketNumber();

	public abstract void setPacketNumber(String packetNumber) ;

	public abstract String getLoanable();

	public abstract void setLoanable(String loanable) ;
	
	public abstract String getRemarks();
	public abstract void setRemarks(String remarks) ;
	
	public abstract long getChequeNoFrom() ;

	public abstract void setChequeNoFrom(long chequeNoFrom);

	public abstract long getChequeNoTo() ;

	public abstract void setChequeNoTo(long chequeNoTo);

	public abstract String getBulkSingle();

	public abstract void setBulkSingle(String bulkSingle) ;
	
	public abstract String getBankName();
	public abstract void setBankName(String bankName);
	
	public abstract Date getStartDate();

	public abstract void setStartDate(Date startDate);
	public abstract Date getMaturityDate();
	public abstract void setMaturityDate(Date maturityDate);
	public abstract String getRamId();

	public abstract void setRamId(String ramId);
	
	
	public abstract String getBranchName();

	public abstract void setBranchName(String branchName);

	public abstract String getBranchCode();

	public abstract void setBranchCode(String branchCode);
	

}