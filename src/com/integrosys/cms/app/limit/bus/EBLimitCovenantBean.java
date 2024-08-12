package com.integrosys.cms.app.limit.bus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

public abstract class EBLimitCovenantBean implements EntityBean, ILimitCovenant {
	
	private static final long serialVersionUID = 8157311227383296759L;

	private static final String SEQUENCE_NAME = ICMSConstant.SEQUENCE_LIMIT_COVENANT;
	

	private static final String[] EXCLUDE_METHOD = new String[] { "getCovenantId", "getFacilityFK" };
	
	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	/**
	 * Default Constructor
	 */
	public EBLimitCovenantBean() {
	}
	

	
		


	// ************ Non-persistence method *************
	// Getters
	/**
	 * Get Limit System XRef ID
	 * 
	 * @return long
	 */
		
		
		
		
	public long getCovenantId() {
		if (null != getCovenantPK()) {
			return getCovenantPK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	
	// Setters
	/**
	 * Set Limit system xref ID
	 * 
	 * @param value is of type long
	 */
	public void setCovenantId(long value) {
		setCovenantPK(new Long(value));
	}

	public void setFacilityFK(long facilityId) {
		if (facilityId != ICMSConstant.LONG_INVALID_VALUE) {
			setFacilityId(new Long(facilityId));
		}
	}

	public long getFacilityFK() {
		Long val = getFacilityId();
		if (val != null) {
			return val.longValue();
		}
		else {
			return ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	
	// ********************** Abstract Methods **********************
	
	public abstract Long getFacilityId();

	public abstract void setFacilityId(Long facilityId);
	
	/*public abstract String getLineId() ;

	public abstract void setLineId(String lineId);*/
	
	public abstract String getCovenantReqd();
	
	public abstract void setCovenantReqd(String covenantReqd);

	public abstract String getCountryRestrictionReqd() ;

	public abstract void setCountryRestrictionReqd(String countryRestrictionReqd);

	public abstract String getDrawerReqd();

	public abstract void setDrawerReqd(String drawerReqd) ;

	public abstract String getDraweeReqd() ;

	public abstract void setDraweeReqd(String draweeReqd);

	public abstract String getBeneficiaryReqd() ;

	public abstract void setBeneficiaryReqd(String beneficiaryReqd);

	public abstract String getCombinedTenorReqd() ;

	public abstract void setCombinedTenorReqd(String combinedTenorReqd) ;

	public abstract String getRunningAccountReqd() ;

	public abstract void setRunningAccountReqd(String runningAccountReqd);

	public abstract String getSellDownReqd() ;

	public abstract void setSellDownReqd(String sellDownReqd) ;

	public abstract String getLastAvailableDateReqd() ;

	public abstract void setLastAvailableDateReqd(String lastAvailableDateReqd);

	public abstract String getMoratoriumReqd();

	public abstract void setMoratoriumReqd(String moratoriumReqd);

	public abstract String getGoodsRestrictionReqd() ;

	public abstract void setGoodsRestrictionReqd(String goodsRestrictionReqd) ;

	public abstract String getCurrencyRestrictionReqd();

	public abstract void setCurrencyRestrictionReqd(String currencyRestrictionReqd);

	public abstract String getBankRestrictionReqd() ;

	public abstract void setBankRestrictionReqd(String bankRestrictionReqd);

	public abstract String getBuyersRatingReqd() ;

	public abstract void setBuyersRatingReqd(String buyersRatingReqd);

	public abstract String getEcgcCoverReqd() ;

	public abstract void setEcgcCoverReqd(String ecgcCoverReqd);
	
	public abstract Long getCovenantPK();
	
	public abstract void setCovenantPK(Long value);
	
	public abstract String getRestrictedCountryname() ;

	public abstract void setRestrictedCountryname(String restrictedCountryname);

	public abstract String getRestrictedAmount() ;

	public abstract void setRestrictedAmount(String restrictedAmount);

	public abstract String getDrawerName() ;

	public abstract void setDrawerName(String drawerName) ;

	public abstract String getDrawerAmount() ;

	public abstract void setDrawerAmount(String drawerAmount);

	public abstract String getDrawerCustId();

	public abstract void setDrawerCustId(String drawerCustId) ;

	public abstract String getDrawerCustName();

	public abstract void setDrawerCustName(String drawerCustName);

	public abstract String getDraweeName();

	public abstract void setDraweeName(String draweeName) ;

	public abstract String getDraweeAmount() ;

	public abstract void setDraweeAmount(String draweeAmount);

	public abstract String getDraweeCustId() ;

	public abstract void setDraweeCustId(String draweeCustId) ;

	public abstract String getDraweeCustName();

	public abstract void setDraweeCustName(String draweeCustName);

	public abstract String getBeneName() ;

	public abstract void setBeneName(String beneName);

	public abstract String getBeneAmount();

	public abstract void setBeneAmount(String beneAmount);

	public abstract String getBeneCustId() ;

	public abstract void setBeneCustId(String beneCustId);

	public abstract String getBeneCustName() ;

	public abstract void setBeneCustName(String beneCustName) ;

	public abstract String getMaxCombinedTenor();

	public abstract void setMaxCombinedTenor(String maxCombinedTenor);

	public abstract String getPreshipmentLinkage() ;

	public abstract void setPreshipmentLinkage(String preshipmentLinkage) ;

	public abstract String getPostShipmentLinkage() ;

	public abstract void setPostShipmentLinkage(String postShipmentLinkage) ;

	public abstract String getRunningAccount() ;

	public abstract void setRunningAccount(String runningAccount);

	public abstract String getOrderBackedbylc() ;

	public abstract void setOrderBackedbylc(String orderBackedbylc);

	public abstract String getIncoTerm();

	public abstract void setIncoTerm(String incoTerm);

	public abstract String getIncoTermMarginPercent() ;

	public abstract void setIncoTermMarginPercent(String incoTermMarginPercent);

	public abstract String getIncoTermDesc();

	public abstract void setIncoTermDesc(String incoTermDesc);

	public abstract String getModuleCode() ;

	public abstract void setModuleCode(String moduleCode) ;

	public abstract String getCommitmentTenor() ;

	public abstract void setCommitmentTenor(String commitmentTenor) ;

	public abstract String getSellDown() ;

	public abstract void setSellDown(String sellDown);

	public abstract Date getLastAvailableDate() ;

	public abstract void setLastAvailableDate(Date lastAvailableDate);

	public abstract String getMoratoriumPeriod() ;

	public abstract void setMoratoriumPeriod(String moratoriumPeriod);

	public abstract String getEmiFrequency() ;

	public abstract void setEmiFrequency(String emiFrequency) ;

	public abstract String getNoOfInstallments() ;

	public abstract void setNoOfInstallments(String noOfInstallments) ;

	public abstract String getRestrictedCurrency() ;

	public abstract void setRestrictedCurrency(String restrictedCurrency) ;

	public abstract String getRestrictedBank() ;

	public abstract void setRestrictedBank(String restrictedBank);

	public abstract String getBuyersRating();

	public abstract void setBuyersRating(String buyersRating);

	public abstract String getAgencyMaster() ;

	public abstract void setAgencyMaster(String agencyMaster);

	public abstract String getRatingMaster();

	public abstract void setRatingMaster(String ratingMaster);

	public abstract String getEcgcCover() ;

	public abstract void setEcgcCover(String ecgcCover);
	
	public abstract String getRestrictedCurrencyAmount();
	
	public abstract void setRestrictedCurrencyAmount(String restrictedCurrencyAmount) ;
	
	public abstract String getRestrictedBankAmount();
	
	public abstract void setRestrictedBankAmount(String restrictedBankAmount) ;
	
	public abstract String getRestrictedCountryInd();

	public abstract void setRestrictedCountryInd(String restrictedCountryInd);

	public abstract String getRestrictedBankInd();

	public abstract void setRestrictedBankInd(String restrictedBankInd);

	public abstract String getRestrictedCurrencyInd();

	public abstract void setRestrictedCurrencyInd(String restrictedCurrencyInd);

	public abstract String getDrawerInd();

	public abstract void setDrawerInd(String drawerInd);

	public abstract String getDraweeInd();

	public abstract void setDraweeInd(String draweeInd);

	public abstract String getBeneInd();

	public abstract void setBeneInd(String beneInd);

	public abstract String getGoodsRestrictionInd();

	public abstract void setGoodsRestrictionInd(String goodsRestrictionInd);

	public abstract String getGoodsRestrictionCode();

	public abstract void setGoodsRestrictionCode(String goodsRestrictionCode);

	public abstract String getGoodsRestrictionParentCode();

	public abstract void setGoodsRestrictionParentCode(String goodsRestrictionParentCode);
	
	public abstract String getSingleCovenantInd();
	public abstract void setSingleCovenantInd(String singleCovenantInd);
	
	public abstract String getGoodsRestrictionChildCode();
	public abstract void setGoodsRestrictionChildCode(String goodsRestrictionChildCode);

	public abstract String getGoodsRestrictionSubChildCode();
	public abstract void setGoodsRestrictionSubChildCode(String goodsRestrictionSubChildCode);

	public abstract String getGoodsRestrictionComboCode();
	public abstract void setGoodsRestrictionComboCode(String goodsRestrictionComboCode);
	
	/*public abstract String getIsNewEntry();

	public abstract void setIsNewEntry(String isNewEntry);
*/
	
	public  String getIsNewEntry()
	{
		return null;
	}
	public  void setIsNewEntry(String isNewEntry) {}

	// ************************ ejbCreate methods ********************

	/**
	 * Create a co borrower limit
	 * 
	 * @param value is the ILimitCovenant object
	 * @return Long the primary key
	 */
	public Long ejbCreate(ILimitCovenant value) throws CreateException {
		if (null == value) {
			throw new CreateException("ILimitCovenant is null!");
		}
		try {
			long pk = Long.parseLong((new SequenceManager()).getSeqNum(SEQUENCE_NAME, true));

			DefaultLogger.debug(this, "Creating Limit Sys XRef with ID: " + pk);

			setCovenantId(pk);

			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);

			return new Long(pk);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CreateException("Unknown Exception Caught: " + e.toString());
		}
	}

	/**
	 * Create a Co Borrower Limit
	 * 
	 * @param value is the ILimitCovenant object
	 */
	public void ejbPostCreate(ILimitCovenant value) throws CreateException {
		// do nothing
	}

	/**
	 * Method to get an object representation from persistance
	 * 
	 * @return ILimitCovenant
	 * @throws LimitException on error
	 */
	public ILimitCovenant getValue() throws LimitException {
		OBLimitCovenant value = new OBLimitCovenant();
		AccessorUtil.copyValue(this, value);
		return value;
	}

	/**
	 * Method to set an object representation into persistance
	 * 
	 * @param value is of type ILimitCovenant
	 * @throws LimitException on error
	 */
	public void setValue(ILimitCovenant value) throws LimitException {
		if (null != value) {
			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
			//value = updateDependants(value);
		}
		else {
			throw new LimitException("ILimitCovenant is null!");
		}
	}

	
	// ************************************************************************
	/**
	 * EJB callback method
	 */
	public void ejbActivate() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbPassivate() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbLoad() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbStore() {
	}

	/**
	 * EJB callback method
	 */
	
	

	public void ejbRemove() throws RemoveException, EJBException {
		
	}

	/**
	 * EJB Callback Method
	 */
	public void setEntityContext(EntityContext ctx) {
		_context = ctx;
	}

	/**
	 * EJB Callback Method
	 */
	public void unsetEntityContext() {
		_context = null;
	}

}
