package com.integrosys.cms.app.collateral.bus;

import java.math.BigDecimal;
import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.businfra.contact.IAddress;
import com.integrosys.base.businfra.contact.OBAddress;
import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public abstract class EBAddtionalDocumentFacilityDetailsBean implements IAddtionalDocumentFacilityDetails, EntityBean {

	private static final long serialVersionUID = -2636784418617758949L;

	/** The container assigned reference to the entity. */ 
	private EntityContext context;

	/** A list of methods to be excluded during update to the insurance. */
	private static final String[] EXCLUDE_METHOD = new String[] { "getAddDocFacDetID" };

	/**
	 * Get insurance policy ID, the primary key in CMS.
	 * 
	 * @return long
	 */
	public long getAddDocFacDetID() {
		return getEBAddDocFacDetID().longValue();
	}

	/**
	 * Set insurance policy ID, the primary key in CMS.
	 * 
	 * @param insurancePolicyID is of type long
	 */
	public void setAddDocFacDetID(long addDocFacDetID) {
		setEBAddDocFacDetID(new Long(addDocFacDetID));
	}

	public Long getLmtProfileId() {
		return getEBLmtProfileId();
	}

	public void setLmtProfileId(Long lmtProfileId) {
		setEBLmtProfileId(lmtProfileId);
	}

	/**
	 * Get insurance address.
	 * 
	 * @return IAddress
	 */
//	public IAddress getAddress() {
//		if (getEBAddress() == null) {
//			return null;
//		}
//		OBAddress add = new OBAddress();
//		add.setAddress(getEBAddress());
//		return add;
//	}

	/**
	 * Get insurable amount.
	 * 
	 * @return Amount
	 */
//	public Amount getInsurableAmount() {
//		return getEBInsurableAmount() == null ? null : new Amount(getEBInsurableAmount(), new CurrencyCode(
//				getCurrencyCode()));
//	}

	/**
	 * Set insurable amount.
	 * 
	 * @param insurableAmount of type Amount
	 */
//	public void setInsurableAmount(Amount insurableAmount) {
//		setEBInsurableAmount(insurableAmount == null ? null : insurableAmount.getAmountAsBigDecimal());
//	}

	/**
	 * Get insured amount.
	 * 
	 * @return Amount
	 */
//	public Amount getInsuredAmount() {
//		return getEBInsuredAmount() == null ? null : new Amount(getEBInsuredAmount(), new CurrencyCode(
//				getCurrencyCode()));
//	}
//
//	public Amount getNewAmtInsured() {
//		return getEBNewAmtInsured() == null ? null : new Amount(getEBNewAmtInsured(), new CurrencyCode(
//				getCurrencyCode()));
//	}
//
//	public void setNewAmtInsured(Amount newAmtInsured) {
//		setEBNewAmtInsured(newAmtInsured == null ? null : newAmtInsured.getAmountAsBigDecimal());
//	}

	/**
	 * Set insured amount.
	 * 
	 * @param insuredAmount of type Amount
	 */
//	public void setInsuredAmount(Amount insuredAmount) {
//		setEBInsuredAmount(insuredAmount == null ? null : insuredAmount.getAmountAsBigDecimal());
//	}

	/**
	 * Set insurance address.
	 * 
	 * @param address of type IAddress
	 */
//	public void setAddress(IAddress address) {
//		setEBAddress(address == null ? null : address.getAddress());
//	}

	/**
	 * Get insured premium amount.
	 * 
	 * @return Amount
	 */
//	public Amount getInsurancePremium() {
//		return getEBInsurancePremium() == null ? null : new Amount(getEBInsurancePremium(), new CurrencyCode(
//				getCurrencyCode()));
//	}

	/**
	 * Set insured amount.
	 * 
	 * @param insuredAmount of type Amount
	 */
//	public void setInsurancePremium(Amount insurancePremium) {
//		setEBInsurancePremium(insurancePremium == null ? null : insurancePremium.getAmountAsBigDecimal());
//	}

	/**
	 * Get insured takaful commission amount.
	 * 
	 * @return Amount
	 */
//	public Amount getTakafulCommission() {
//		return getEBTakafulCommission() == null ? null : new Amount(getEBTakafulCommission(), new CurrencyCode(
//				getCurrencyCode()));
//	}

	/**
	 * Set insured amount.
	 * 
	 * @param takafulCommission of type Amount
	 */
//	public void setTakafulCommission(Amount takafulCommission) {
//		setEBTakafulCommission(takafulCommission == null ? null : takafulCommission.getAmountAsBigDecimal());
//	}

	public abstract Long getEBAddDocFacDetID();
	public abstract void setEBAddDocFacDetID(Long eBAddDocFacDetID);

	public abstract Long getEBLmtProfileId();
	public abstract void setEBLmtProfileId(Long eBLmtProfileId);
	
	public abstract String getDocumentNo();
	public abstract void setDocumentNo(String documentNo);
	
//	public abstract BigDecimal getEBInsurableAmount();
//
//	public abstract void setEBInsurableAmount(BigDecimal eBInsurableAmount);
//
//	public abstract BigDecimal getEBInsuredAmount();
//
//	public abstract void setEBInsuredAmount(BigDecimal eBInsuredAmount);
//
//	public abstract BigDecimal getEBNewAmtInsured();
//
//	public abstract void setEBNewAmtInsured(BigDecimal eBNewAmtInsured);
//
//	public abstract String getEBAddress();
//
//	public abstract void setEBAddress(String eBAddress);

	

//	public abstract BigDecimal getEBInsurancePremium();
//
//	public abstract void setEBInsurancePremium(BigDecimal eBInsurancePremium);

//	public abstract BigDecimal getEBTakafulCommission();
//
//	public abstract void setEBTakafulCommission(BigDecimal eBTakafulCommission);

	/**
	 * Get the  of this entity.
	 * 
	 * @return an 
	 */
	public IAddtionalDocumentFacilityDetails getValue() {
		OBAddtionalDocumentFacilityDetails addtionalDocumentFacilityDetails = new OBAddtionalDocumentFacilityDetails();
		AccessorUtil.copyValue(this, addtionalDocumentFacilityDetails);
		return addtionalDocumentFacilityDetails;
	}

	/**
	 * Set the insurance policy.
	 * 
	 * @param insurance of type IInsurancePolicy
	 */
	public void setValue(IAddtionalDocumentFacilityDetails addtionalDocumentFacilityDetails) {
		AccessorUtil.copyValue(addtionalDocumentFacilityDetails, this, EXCLUDE_METHOD);
	}

	/**
	 * Delete this insurance policy.
	 */
	public void delete() {
		this.setStatus(ICMSConstant.STATE_DELETED);
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param insurance of type IInsurancePolicy
	 * @return insurance policy primary key
	 * @throws CreateException on error creating the entity object
	 */
	public Long ejbCreate(IAddtionalDocumentFacilityDetails addtionalDocumentFacilityDetails) throws CreateException {
		try {
			String addtionalDocumentFacilityDetailsID = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_ADD_FAC_DOC_DET, true);
			AccessorUtil.copyValue(addtionalDocumentFacilityDetails, this);
			setEBAddDocFacDetID(new Long(addtionalDocumentFacilityDetailsID));
			if (addtionalDocumentFacilityDetails.getRefID() == null) {
				setRefID(String.valueOf(getAddDocFacDetID()));
			}
			else {
				// else maintain this reference id.
				setRefID(addtionalDocumentFacilityDetails.getRefID());
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
	 * @param insurance of type IInsurancePolicy
	 */
	public void ejbPostCreate(IAddtionalDocumentFacilityDetails addtionalDocumentFacilityDetails) {
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
	public void ejbRemove() {
	}
	
	public abstract Date getReceivedDate() ;
	public abstract void setReceivedDate(Date receivedDate) ;
	
	public abstract String getLastUpdatedBy();
	public abstract void setLastUpdatedBy(String lastUpdatedBy);
	
	public abstract String getLastApproveBy();
	public abstract void setLastApproveBy(String lastApproveBy);
	
	public abstract Date getLastUpdatedOn();
	public abstract void setLastUpdatedOn(Date lastUpdatedOn);
	
	public abstract Date getLastApproveOn();
	public abstract void setLastApproveOn(Date lastApproveOn);
	
	public abstract String getAddFacDocStatus();
	public abstract void setAddFacDocStatus(String addFacDocStatus);
	
	public abstract String getDocFacilityAmount();
	public abstract void setDocFacilityAmount(String docFacilityAmount);
	
	public abstract String getUniqueAddDocFacDetID();
	public abstract void setUniqueAddDocFacDetID(String uniqueAddDocFacDetID);
	
	public abstract String getDocFacilityCategory();
	public abstract void setDocFacilityCategory(String docFacilityCategory);

	public abstract String getDocFacilityType();
	public abstract void setDocFacilityType(String docFacilityType);
	
	public abstract String getDocFacilityTotalAmount();
	public abstract void setDocFacilityTotalAmount(String docFacilityTotalAmount);
	
	public abstract String getRemark1();
	public abstract void setRemark1(String remark1);
	
	public abstract String getRefID();
	public abstract void setRefID(String refID);

	public abstract String getStatus();
	public abstract void setStatus(String status);

//	public abstract String getPolicyNo();
//
//	public abstract void setPolicyNo(String policyNo);

//	public abstract String getInsurerName();
//
//	public abstract void setInsurerName(String insurerName);

//	public abstract String getInsuranceType();
//
//	public abstract void setInsuranceType(String insuranceType);

	/*public abstract Date getExpiryDate();

	public abstract void setExpiryDate(Date expiryDate);

	public abstract String getCurrencyCode();

	public abstract void setCurrencyCode(String currencyCode);

	public abstract Date getEffectiveDate();

	public abstract void setEffectiveDate(Date effectiveDate);*/

//	public abstract String getInsuredAgainst();
//
//	public abstract void setInsuredAgainst(String insuredAgainst);

	/*public abstract String getCategory();

	public abstract void setCategory(String category);
*/

//	public abstract String getInsuredAddress1();
//
//	public abstract void setInsuredAddress1(String insuredAddress1);
//
//	public abstract String getInsuredAddress2();
//
//	public abstract void setInsuredAddress2(String insuredAddress2);
//
//	public abstract String getInsuredAddress3();
//
//	public abstract void setInsuredAddress3(String insuredAddress3);

//	public abstract String getInsuredAddress4();
//
//	public abstract void setInsuredAddress4(String insuredAddress4);

	/*public abstract String getConversionCurrency();

	public abstract void setConversionCurrency(String conversionCurrency);

	public abstract String getBankCustomerArrange();

	public abstract void setBankCustomerArrange(String bankCustomerArrange);

	public abstract String getCoverNoteNumber();

	public abstract void setCoverNoteNumber(String coverNoteNumber);*/

//	public abstract Date getInsIssueDate();
//
//	public abstract void setInsIssueDate(Date insIssueDate);
//
//	public abstract Double getInsuranceExchangeRate();
//
//	public abstract void setInsuranceExchangeRate(Double insuranceExchangeRate);
//
//	public abstract String getInsuranceCompanyName();
//
//	public abstract void setInsuranceCompanyName(String insuranceCompanyName);

//	public abstract String getDebitingACNo();
//
//	public abstract void setDebitingACNo(String debitingACNo);

	/*public abstract String getAcType();

	public abstract void setAcType(String acType);

	public abstract String getNonSchemeScheme();

	public abstract void setNonSchemeScheme(String nonSchemeScheme);

	public abstract String getAutoDebit();

	public abstract void setAutoDebit(String autoDebit);

	public abstract Date getEndorsementDate();

	public abstract void setEndorsementDate(Date endorsementDate);

	public abstract String getBuildingOccupation();

	public abstract void setBuildingOccupation(String buildingOccupation);

	public abstract String getNatureOfWork();

	public abstract void setNatureOfWork(String natureOfWork);

	public abstract String getBuildingType();

	public abstract void setBuildingType(String buildingType);

	public abstract Integer getNumberOfStorey();

	public abstract void setNumberOfStorey(Integer numberOfStorey);

	public abstract String getWall();

	public abstract void setWall(String wall);

	public abstract String getExtensionWalls();

	public abstract void setExtensionWalls(String extensionWalls);

	public abstract String getRoof();

	public abstract void setRoof(String roof);

	public abstract String getExtensionRoof();

	public abstract void setExtensionRoof(String extensionRoof);

	public abstract String getEndorsementCode();

	public abstract void setEndorsementCode(String endorsementCode);

	public abstract String getPolicyCustodian();

	public abstract void setPolicyCustodian(String policyCustodian);*/

//	public abstract Date getInsuranceClaimDate();
//
//	public abstract void setInsuranceClaimDate(Date insuranceClaimDate);

	/*public abstract String getTypeOfFloor();

	public abstract void setTypeOfFloor(String typeOfFloor);*/

//	public abstract String getTypeOfPerils1();
//
//	public abstract void setTypeOfPerils1(String typeOfPerils1);
//
//	public abstract String getTypeOfPerils2();
//
//	public abstract void setTypeOfPerils2(String typeOfPerils2);
//
//	public abstract String getTypeOfPerils3();
//
//	public abstract void setTypeOfPerils3(String typeOfPerils3);
//
//	public abstract String getTypeOfPerils4();
//
//	public abstract void setTypeOfPerils4(String typeOfPerils4);
//
//	public abstract String getTypeOfPerils5();
//
//	public abstract void setTypeOfPerils5(String typeOfPerils5);

//	public abstract String getRemark2();
//
//	public abstract void setRemark2(String remark2);
//
//	public abstract String getRemark3();
//
//	public abstract void setRemark3(String remark3);

	/*public abstract BigDecimal getNettPermByBorrower();

	public abstract void setNettPermByBorrower(BigDecimal nettPermByBorrower);

	public abstract BigDecimal getNettPermToInsCo();

	public abstract void setNettPermToInsCo(BigDecimal nettPermToInsCo);

	public abstract BigDecimal getCommissionEarned();

	public abstract void setCommissionEarned(BigDecimal commissionEarned);

	public abstract BigDecimal getStampDuty();

	public abstract void setStampDuty(BigDecimal stampDuty);

	public abstract BigDecimal getGrossPremium();

	public abstract void setGrossPremium(BigDecimal grossPremium);

	public abstract String getPolicySeq();

	public abstract void setPolicySeq(String policySeq);*/

//	public abstract Long getLosInsurancePolicyId();
//
//	public abstract void setLosInsurancePolicyId(Long losInsurancePolicyId);

	/*public abstract BigDecimal getRebateAmount();

	public abstract void setRebateAmount(BigDecimal rebateAmount);

	public abstract BigDecimal getServiceTaxPercentage();

	public abstract void setServiceTaxPercentage(BigDecimal serviceTaxPercentage);

	public abstract BigDecimal getServiceTaxAmount();

	public abstract void setServiceTaxAmount(BigDecimal serviceTaxAmount);*/
	
	/*public abstract Date getOriginalTargetDate();

	public abstract void setOriginalTargetDate(Date originalTargetDate);

	public abstract Date getNextDueDate();

	public abstract void setNextDueDate(Date nextDueDate);
	public abstract Date getDateDeferred();

	public abstract void setDateDeferred(Date dateDeferred);

	public abstract String getCreditApprover();

	public abstract void setCreditApprover(String creditApprover);

	public abstract Date getWaivedDate();

	public abstract void setWaivedDate(Date waivedDate);*/
}
