package com.integrosys.cms.ui.collateral.cash.cashfd;

import java.io.Serializable;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class FixedDepositForm extends CommonForm implements Serializable {

	public static final String FIXEDDEPOSITMAPPER = "com.integrosys.cms.ui.collateral.cash.cashfd.FixedDepositMapper";

	private String cashDepositID = "";

	// private String depRecpNo = "";
	private String depCurr = IGlobalConstant.DEFAULT_CURRENCY;

	private String depAmt = "";

	private String depMatDate = "";

	private String depNo = "";

	private String depositRefNo = "";

	private String depositReceiptNo;

	private String accountType = "";

	private String fdrRate = "";

	private String issueDate = "";

	private String thirdPartyBank = "";

	private String accountTypeValue = "";

	private String accountTypeNum;

	private String tenure = "";
	
	private String tenureUOM = "";
	
	private String ownBank = "";
	
	private String groupAccountNumber = "";

    private String holdStatus = "";
    
   // private String fdLinePercentage = "";
    
    private String depositeInterestRate = "";
    
    private String verificationDate = "";
    
    private String depositorName = "";
    
    private String index;
    
    private String lienTotal = "";
    
   
    /********** Start: add Sub Form for Lien details by Scahin Patil */
    private String event;
    
     private String lienNo = "";
    
    private String lienAmount = "";
    
    private String serialNo = "";
    
    

	private String remark = "";
	
	private String systemName = "";
	
	private String systemId = "";
	
	private String customerId = "";
	
	private String finwareId = "";
	
	private String active = "";
	
    private String maker_id = "";
    
    private String checker_id = "";
    
    private String maker_date = "";
	
	private String checker_date = "";
	
	//private int fdCount =0;
	
	 private String searchFlag = "";
	 
	 private String fdWebServiceFlag;
	 
	 private String depositRecNoInSearch;
	 
	 
	 private String facilityName;
	 
	 private String facilityId;
	 
	 private String lcnNo;
	 
	 
	public String getSearchFlag() {
		return searchFlag;
	}

	public void setSearchFlag(String searchFlag) {
		this.searchFlag = searchFlag;
	}
	
	 private String radioSelect ="";

	 public String getRadioSelect() {
			return radioSelect;
		}

		public void setRadioSelect(String radioSelect) {
			this.radioSelect = radioSelect;
		}
	 
	/*public int getFdCount() {
		return fdCount;
	}

	public void setFdCount(int fdCount) {
		this.fdCount = fdCount;
	}*/

	

	private List system;
	    private List systemIdList;
	

	

	/********** end: add Sub Form for Lien details by Scahin Patil */
    
    public String getHoldStatus() {
        return holdStatus;
    }

    public void setHoldStatus(String holdStatus) {
        this.holdStatus = holdStatus;
    }

    public String getTenure() {
		return tenure;
	}

	public void setTenure(String tenure) {
		this.tenure = tenure;
	}

	public String getTenureUOM() {
		return tenureUOM;
	}

	public void setTenureUOM(String tenureUOM) {
		this.tenureUOM = tenureUOM;
	}

	public String getOwnBank() {
		return ownBank;
	}

	public void setOwnBank(String ownBank) {
		this.ownBank = ownBank;
	}

	public String getGroupAccountNumber() {
		return groupAccountNumber;
	}

	public void setGroupAccountNumber(String groupAccountNumber) {
		this.groupAccountNumber = groupAccountNumber;
	}

	public String getDepositReceiptNo() {
		return depositReceiptNo;
	}

	public void setDepositReceiptNo(String depositReceiptNo) {
		this.depositReceiptNo = depositReceiptNo;
	}

	public String getCashDepositID() {
		return cashDepositID;
	}

	public void setCashDepositID(String cashDepositID) {
		this.cashDepositID = cashDepositID;
	}

	public String getDepCurr() {
		return depCurr;
	}

	public void setDepCurr(String depCurr) {
		this.depCurr = depCurr;
	}

	public String getDepAmt() {
		return depAmt;
	}

	public void setDepAmt(String depAmt) {
		this.depAmt = depAmt;
	}

	/*
	 * public String getDepRecpNo() { return this.depRecpNo; }
	 * 
	 * 
	 * public void setDepRecpNo(String depRecpNo) { this.depRecpNo = depRecpNo;
	 * }
	 */

	public String getDepMatDate() {
		return this.depMatDate;
	}

	public void setDepMatDate(String depMatDate) {
		this.depMatDate = depMatDate;
	}

	public String getDepNo() {
		return depNo;
	}

	public void setDepNo(String depNo) {
		this.depNo = depNo;
	}

	public String getDepositRefNo() {
		return depositRefNo;
	}

	public void setDepositRefNo(String depositRefNo) {
		this.depositRefNo = depositRefNo;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getFdrRate() {
		return fdrRate;
	}

	public void setFdrRate(String fdrRate) {
		this.fdrRate = fdrRate;
	}

	public String getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	public String getThirdPartyBank() {
		return thirdPartyBank;
	}

	public void setThirdPartyBank(String thirdPartyBank) {
		this.thirdPartyBank = thirdPartyBank;
	}

	public String getAccountTypeNum() {
		return accountTypeNum;
	}

	public void setAccountTypeNum(String accountTypeNum) {
		this.accountTypeNum = accountTypeNum;
	}

	public String getAccountTypeValue() {
		return accountTypeValue;
	}

	public void setAccountTypeValue(String accountTypeValue) {
		this.accountTypeValue = accountTypeValue;
	}

	
	public void reset() {
		depCurr = IGlobalConstant.DEFAULT_CURRENCY;
	}

	/*public String getFdLinePercentage() {
		return fdLinePercentage;
	}

	public void setFdLinePercentage(String fdLinePercentage) {
		this.fdLinePercentage = fdLinePercentage;
	}*/

	public String getDepositeInterestRate() {
		return depositeInterestRate;
	}

	public void setDepositeInterestRate(String depositeInterestRate) {
		this.depositeInterestRate = depositeInterestRate;
	}

	public String getVerificationDate() {
		return verificationDate;
	}

	public void setVerificationDate(String verificationDate) {
		this.verificationDate = verificationDate;
	}

	public String getDepositorName() {
		return depositorName;
	}

	public void setDepositorName(String depositorName) {
		this.depositorName = depositorName;
	}

private String rec;
	
	public String getRec() {
		return rec;
	}

	public void setRec(String rec) {
		this.rec = rec;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}
	
	// ------added by Sachin patil for
	// customers------------------------------end---------------------------------
	
	/*public String[][] getMapper() {
		String[][] input = { { "form.depositObject", FIXEDDEPOSITMAPPER }, };
		return input;

	}*/

	public String[][] getMapper() {
		DefaultLogger.debug(this, "Getting Mapper");
		String[][] input = {
				{ "form.depositObject", FIXEDDEPOSITMAPPER },
				{ "theOBTrxContext", TRX_MAPPER },
				{ "OBLien", LIEN_INFO_MAPPER } };
		return input;
	}

	public static final String LIEN_INFO_MAPPER = "com.integrosys.cms.ui.collateral.cash.cashfd.FixedDepositMapper";

	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";

	public String getLienNo() {
		return lienNo;
	}

	public void setLienNo(String lienNo) {
		this.lienNo = lienNo;
	}

	public String getLienAmount() {
		return lienAmount;
	}

	public void setLienAmount(String lienAmount) {
		this.lienAmount = lienAmount;
	}

	public String getLienTotal() {
		return lienTotal;
	}

	public void setLienTotal(String lienTotal) {
		this.lienTotal = lienTotal;
	}
	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	 public String getSystemName() {
			return systemName;
		}

		public void setSystemName(String systemName) {
			this.systemName = systemName;
		}

		public String getSystemId() {
			return systemId;
		}

		public void setSystemId(String systemId) {
			this.systemId = systemId;
		}

		public String getCustomerId() {
			return customerId;
		}

		public void setCustomerId(String customerId) {
			this.customerId = customerId;
		}

		public String getFinwareId() {
			return finwareId;
		}

		public void setFinwareId(String finwareId) {
			this.finwareId = finwareId;
		}
		
		public String getActive() {
			return active;
		}

		public void setActive(String active) {
			this.active = active;
		}
		public List getSystem() {
			return system;
		}

		public void setSystem(List system) {
			this.system = system;
		}

		public List getSystemIdList() {
			return systemIdList;
		}

		public void setSystemIdList(List systemIdList) {
			this.systemIdList = systemIdList;
		}

		public String getMaker_id() {
			return maker_id;
		}

		public void setMaker_id(String maker_id) {
			this.maker_id = maker_id;
		}

		public String getChecker_id() {
			return checker_id;
		}

		public void setChecker_id(String checker_id) {
			this.checker_id = checker_id;
		}

		public String getMaker_date() {
			return maker_date;
		}

		public void setMaker_date(String maker_date) {
			this.maker_date = maker_date;
		}

		public String getChecker_date() {
			return checker_date;
		}

		public void setChecker_date(String checker_date) {
			this.checker_date = checker_date;
		}

		public String getFdWebServiceFlag() {
			return fdWebServiceFlag;
		}

		public void setFdWebServiceFlag(String fdWebServiceFlag) {
			this.fdWebServiceFlag = fdWebServiceFlag;
		}
//Start:Uma Khot: Added to cpture FD details manully when fd no is other than FC.
		public String getDepositRecNoInSearch() {
			return depositRecNoInSearch;
		}

		public void setDepositRecNoInSearch(String depositRecNoInSearch) {
			this.depositRecNoInSearch = depositRecNoInSearch;

		}
//End:Uma Khot: Added to cpture FD details manully when fd no is other than FC.

		public String getFacilityName() {
			return facilityName;
		}

		public void setFacilityName(String facilityName) {
			this.facilityName = facilityName;
		}

		public String getFacilityId() {
			return facilityId;
		}

		public void setFacilityId(String facilityId) {
			this.facilityId = facilityId;
		}

		public String getLcnNo() {
			return lcnNo;
		}

		public void setLcnNo(String lcnNo) {
			this.lcnNo = lcnNo;
		}

}
