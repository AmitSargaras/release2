/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/bus/OBSCCertificate.java,v 1.7 2006/05/30 10:19:38 czhou Exp $
 */
package com.integrosys.cms.app.sccertificate.bus;

//java
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.bus.IBookingLocation;

/**
 * This class the that provides the implementation for ISCCertificate
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2006/05/30 10:19:38 $ Tag: $Name: $
 */
public class OBSCCertificate implements ISCCertificate {
	private long scCertID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String scCertRef = null;

	private long limitProfileID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private Date dateGenerated = null;

	private Amount cleanApprovalAmount = null;

	private Amount approvalAmount = null;

	private Amount totalApprovalAmount = null;

	private Amount cleanActivatedAmount = null;

	private Amount activatedAmount = null;

	private Amount totalActivatedAmount = null;

	private String creditOfficerName = null;

	private String creditOfficerSignNo = null;

	private IBookingLocation creditOfficerLocation = null;

	private String seniorOfficerName = null;

	private String seniorOfficerSignNo = null;

	private IBookingLocation seniorOfficerLocation = null;

	private String remarks = null;

	private ISCCertificateItem[] itemList = null;

	private long versionTime = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private ISCCertificateCustomerDetail custDetails;

    private char hasCheck1;

    private char hasCheck2;

    private char hasCheck3;

    private char hasCheck4;

    private String insuredWith;

    private BigDecimal insuredWithAmt;

    private String ambm;

    private Date expiry;

    private BigDecimal sinkFundAmt;

    private String pmForPeriodOf;

    private String commencingFrom;

    private BigDecimal fundReach;

    private BigDecimal feesAmt;

    private String others;

	/**
	 * Get the SCC ID
	 * @return long - the SCC ID
	 */
	public long getSCCertID() {
		return this.scCertID;
	}

	/**
	 * Get the SC Certificate Reference
	 * @return String - the SC certificate reference
	 */
	public String getSCCertRef() {
		return this.scCertRef;
	}

	/**
	 * Get the limit profile ID
	 * @return long - the limit profile ID
	 */
	public long getLimitProfileID() {
		return this.limitProfileID;
	}

	/**
	 * Get the date generated
	 * @return Date - the date of SCC generation
	 */
	public Date getDateGenerated() {
		return this.dateGenerated;
	}

	/**
	 * Get the approval amount for clean limits
	 * @return Amount - the clean approval amount
	 */
	public Amount getCleanApprovalAmount() {
		return this.cleanApprovalAmount;
	}

	/**
	 * Get the approval amount (limit with security)
	 * @return Amount - the approval amount
	 */
	public Amount getApprovalAmount() {
		return this.approvalAmount;
	}

	/**
	 * Get the total approval amount. This amount is calculated based on the
	 * base currency of the bank
	 * @return Amount - the total approval amount calculated
	 */
	public Amount getTotalApprovalAmount() {
		return this.totalApprovalAmount;
	}

	/**
	 * Get the clean activated amount for clean limits
	 * @return Amount - the activated amount for clean limits
	 */
	public Amount getCleanActivatedAmount() {
		return this.cleanActivatedAmount;
	}

	/**
	 * Get the activated amount (limit with security)
	 * @return Amount - the activated amount
	 */
	public Amount getActivatedAmount() {
		return this.activatedAmount;
	}

	/**
	 * Get the total activated amount. This amount is calculated based on the
	 * base currency of the bank
	 * @return Amount - the total activated amount calculated
	 */
	public Amount getTotalActivatedAmount() {
		return this.totalActivatedAmount;
	}

	/**
	 * Get the credit officer name
	 * @return String - the credit officer name
	 */
	public String getCreditOfficerName() {
		return this.creditOfficerName;
	}

	/**
	 * Get the credit officer signing number
	 * @return String - the credit officer signing number
	 */
	public String getCreditOfficerSignNo() {
		return this.creditOfficerSignNo;
	}

	/**
	 * Get the credit officer booking location
	 * @return IBookingLocation - the credit officer location
	 */
	public IBookingLocation getCreditOfficerLocation() {
		return this.creditOfficerLocation;
	}

	/**
	 * Get the senior officer name
	 * @return String - the senior officer name
	 */
	public String getSeniorOfficerName() {
		return this.seniorOfficerName;
	}

	/**
	 * Get the senior officer signing number
	 * @return String - the senior officer signing number
	 */
	public String getSeniorOfficerSignNo() {
		return this.seniorOfficerSignNo;
	}

	/**
	 * Get the senior officer location
	 * @return IBookingLocation - the senior officer location
	 */
	public IBookingLocation getSeniorOfficerLocation() {
		return this.seniorOfficerLocation;
	}

	/**
	 * Get the remarks
	 * @return String - the remarks
	 */
	public String getRemarks() {
		return this.remarks;
	}

	/**
	 * Get the list of SCC items
	 * @return ISCCertificateItem[] - the list of scc items
	 */
	public ISCCertificateItem[] getSCCItemList() {
		return this.itemList;
	}

	/**
	 * Get the version time
	 * @return long - the version time
	 */
	public long getVersionTime() {
		return this.versionTime;
	}

    public char getHasCheck1() {
        return hasCheck1;
    }

    public char getHasCheck2() {
        return hasCheck2;
    }

    public char getHasCheck3() {
        return hasCheck3;
    }

    public char getHasCheck4() {
        return hasCheck4;
    }

    public String getInsuredWith() {
        return insuredWith;
    }

    public BigDecimal getInsuredWithAmt() {
        return insuredWithAmt;
    }

    public String getAmbm() {
        return ambm;
    }

    public Date getExpiry() {
        return expiry;
    }

    public BigDecimal getSinkFundAmt() {
        return sinkFundAmt;
    }

    public String getPmForPeriodOf() {
        return pmForPeriodOf;
    }

    public String getCommencingFrom() {
        return commencingFrom;
    }

    public BigDecimal getFundReach() {
        return fundReach;
    }

    public BigDecimal getFeesAmt() {
        return feesAmt;
    }

    public String getOthers() {
        return others;
    }

    /**
	 * Set the SCC ID
	 * @param aSCCertID of long type
	 */
	public void setSCCertID(long aSCCertID) {
		this.scCertID = aSCCertID;
	}

	/**
	 * Set the SCCert Reference
	 * @param aSCCertRef of String type
	 */
	public void setSCCertRef(String aSCCertRef) {
		this.scCertRef = aSCCertRef;
	}

	/**
	 * Set the limit profile ID
	 * @param aLimitProfileID of long type
	 */
	public void setLimitProfileID(long aLimitProfileID) {
		this.limitProfileID = aLimitProfileID;
	}

	/**
	 * Set the date generated
	 * @param aDateGenerated of Date type
	 */
	public void setDateGenerated(Date aDateGenerated) {
		this.dateGenerated = aDateGenerated;
	}

	/**
	 * Get the approval amount for clean limits
	 */
	public void setCleanApprovalAmount(Amount aCleanApprovalAmount) {
		this.cleanApprovalAmount = aCleanApprovalAmount;
	}

	/**
	 * Get the approval amount (limit with security)
	 */
	public void setApprovalAmount(Amount anApprovalAmount) {
		this.approvalAmount = anApprovalAmount;
	}

	/**
	 * Set the total approval amount
	 * @param aTotalApprovalAmount of Amount type
	 */
	public void setTotalApprovalAmount(Amount aTotalApprovalAmount) {
		this.totalApprovalAmount = aTotalApprovalAmount;
	}

	/**
	 * Get the clean activated amount for clean limits
	 */
	public void setCleanActivatedAmount(Amount aCleanActivatedAmount) {
		this.cleanActivatedAmount = aCleanActivatedAmount;
	}

	/**
	 * Get the activated amount (limit with security)
	 */
	public void setActivatedAmount(Amount anActivatedAmount) {
		this.activatedAmount = anActivatedAmount;
	}

	/**
	 * Set the total activated amount
	 * @param aTotalActivatedAmount of Amount type
	 */
	public void setTotalActivatedAmount(Amount aTotalActivatedAmount) {
		this.totalActivatedAmount = aTotalActivatedAmount;
	}

	/**
	 * Set the credit officer name
	 * @param aCreditOfficerName of String type
	 */
	public void setCreditOfficerName(String aCreditOfficerName) {
		this.creditOfficerName = aCreditOfficerName;
	}

	/**
	 * Set the credit officer signing number
	 * @param aCreditOfficerSignNo of String type
	 */
	public void setCreditOfficerSignNo(String aCreditOfficerSignNo) {
		this.creditOfficerSignNo = aCreditOfficerSignNo;
	}

	/**
	 * Set the credit officer location
	 * @param anIBookingLocation of IBookingLocation type
	 */
	public void setCreditOfficerLocation(IBookingLocation anIBookingLocation) {
		this.creditOfficerLocation = anIBookingLocation;
	}

	/**
	 * Set the senior officer name
	 * @param aSeniorOfficerName of String type
	 */
	public void setSeniorOfficerName(String aSeniorOfficerName) {
		this.seniorOfficerName = aSeniorOfficerName;
	}

	/**
	 * Set the senior officer signing number
	 * @param aSeniorOfficerSignNo of String type
	 */
	public void setSeniorOfficerSignNo(String aSeniorOfficerSignNo) {
		this.seniorOfficerSignNo = aSeniorOfficerSignNo;
	}

	/**
	 * Set the senior officer location
	 * @param anIBookingLocation of IBookingLocation type
	 */
	public void setSeniorOfficerLocation(IBookingLocation anIBookingLocation) {
		this.seniorOfficerLocation = anIBookingLocation;
	}

	/**
	 * Set the remarks
	 * @param aRemarks of String type
	 */
	public void setRemarks(String aRemarks) {
		this.remarks = aRemarks;
	}

	/**
	 * Set the list of SCC items
	 * @param anISCCertificateItemList of ISCCertificateItem[] type
	 */
	public void setSCCItemList(ISCCertificateItem[] anISCCertificateItemList) {
		this.itemList = anISCCertificateItemList;
	}

	/**
	 * Set the version time
	 * @param aVersionTime of long type
	 */
	public void setVersionTime(long aVersionTime) {
		this.versionTime = aVersionTime;
	}

    public void setHasCheck1(char hasCheck1) {
        this.hasCheck1 = hasCheck1;
    }

    public void setHasCheck2(char hasCheck2) {
        this.hasCheck2 = hasCheck2;
    }

    public void setHasCheck3(char hasCheck3) {
        this.hasCheck3 = hasCheck3;
    }

    public void setHasCheck4(char hasCheck4) {
        this.hasCheck4 = hasCheck4;
    }

    public void setInsuredWith(String insuredWith) {
        this.insuredWith = insuredWith;
    }

    public void setInsuredWithAmt(BigDecimal insuredWithAmt) {
        this.insuredWithAmt = insuredWithAmt;
    }

    public void setAmbm(String ambm) {
        this.ambm = ambm;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }

    public void setSinkFundAmt(BigDecimal sinkFundAmt) {
        this.sinkFundAmt = sinkFundAmt;
    }

    public void setPmForPeriodOf(String pmForPeriodOf) {
        this.pmForPeriodOf = pmForPeriodOf;
    }

    public void setCommencingFrom(String commencingFrom) {
        this.commencingFrom = commencingFrom;
    }

    public void setFundReach(BigDecimal fundReach) {
        this.fundReach = fundReach;
    }

    public void setFeesAmt(BigDecimal feesAmt) {
        this.feesAmt = feesAmt;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    /**
	 * Prints a String representation of this object
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	/****************************** CMS-1366********Starts ******************/

	private Date creditOfficerDt = null;

	public Date getCreditOfficerDt() {

		return this.creditOfficerDt;
	}

	public void setCreditOfficerDt(Date aCreditOfficerDt) {

		this.creditOfficerDt = aCreditOfficerDt;
	}

	private Date seniorOfficerDt = null;

	public Date getSeniorOfficerDt() {

		return this.seniorOfficerDt;
	}

	public void setSeniorOfficerDt(Date aSeniorOfficerDt) {

		this.seniorOfficerDt = aSeniorOfficerDt;
	}

	/****************************** CMS-1366********Ends ********************/

	/**
	 * Get certificate customer info.
	 * 
	 * @return ISCCertificateCustomerDetail
	 */
	public ISCCertificateCustomerDetail getCustDetails() {
		return custDetails;
	}

	/**
	 * Set certificate customer info.
	 * 
	 * @param custDetails of type ISCCertificateCustomerDetail
	 */
	public void setCustDetails(ISCCertificateCustomerDetail custDetails) {
		this.custDetails = custDetails;
	}

	/****************************** R1.5-CR146 ********Starts ******************/

	/**
	 * Get the list of SCCertificate items for clean limit
	 * @return ISCCertificateItem[] - the list of SCCertificate items for clean
	 *         limits
	 */
	public ISCCertificateItem[] getCleanSCCertificateItemList() {
		DefaultLogger.debug(this, ">>>>>>>> In getCleanSCCertificateItemList()");
		ISCCertificateItem[] fullList = getSCCItemList();
		if (fullList != null) {
			ArrayList cleanList = new ArrayList();
			for (int ii = 0; ii < fullList.length; ii++) {
				if (fullList[ii].isCleanType()) {
					if (!fullList[ii].getIsDeletedInd()) {
						cleanList.add(fullList[ii]);
					}
				}
			}
			if (cleanList.size() > 0) {
				return (ISCCertificateItem[]) cleanList.toArray(new ISCCertificateItem[0]);
			}
		}
		return null;
	}

	/**
	 * Get the list of SCCertificate items (limit with securities)
	 * @return ISCCertificateItem[] - the list of SCCertificate item
	 */
	public ISCCertificateItem[] getNotCleanSCCertificateItemList() {
		ISCCertificateItem[] fullList = getSCCItemList();
		if (fullList != null) {
			ArrayList notCleanList = new ArrayList();
			for (int ii = 0; ii < fullList.length; ii++) {
				if (!fullList[ii].isCleanType()) {
					notCleanList.add(fullList[ii]);
				}
			}
			if (notCleanList.size() > 0) {
				return (ISCCertificateItem[]) notCleanList.toArray(new ISCCertificateItem[0]);
			}
		}
		return null;
	}
	/****************************** R1.5-CR146 ********Ends ******************/

}
