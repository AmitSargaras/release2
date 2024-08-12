package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.vo.ILeadBankStock;

public interface IGeneralChargeDetails extends Serializable {

	public static final String STATUS_APPROVED = "APPROVED";
	public static final String STATUS_PENDING = "PENDING";
	public static final String STATUS_INSURANCE_UPDATED = "INS_UPDATED";
	
	//PK
	public long getGeneralChargeDetailsID();
	public void setGeneralChargeDetailsID(long generalChargeDetailsID) ;
	
	//FK
	public long getCmsCollatralId();
	public void setCmsCollatralId(long cmsCollatralId);
	
	public Date getDueDate() ;
	public void setDueDate(Date dueDate);

	public String getDocCode() ;
	public void setDocCode(String docCode);


	public String getStatus();
	public void setStatus(String status);

	//Added only for display,this will be saved at child level
	
	public String getLocation() ;
	public void setLocation(String location) ;
	
//	public String getFundedShare() ;
//	public void setFundedShare(String fundedShare) ;
	
	public String getCalculatedDP() ;
	public void setCalculatedDP(String calculatedDP) ;
	
	public String getLocationDetail() ;
	public void setLocationDetail(String locationDetail);

	public IGeneralChargeStockDetails[] getGeneralChargeStockDetails();
	public void setGeneralChargeStockDetails(IGeneralChargeStockDetails[] generalChargeStockDetails) ;
	
	public String getLastUpdatedBy() ;
	public void setLastUpdatedBy(String lastUpdatedBy) ;
	
	public Date getLastUpdatedOn();
	public void setLastUpdatedOn(Date lastUpdatedOn) ;
	
	public String getLastApprovedBy() ;
	public void setLastApprovedBy(String lastApprovedBy);
	
	public Date getLastApprovedOn() ;
	public void setLastApprovedOn(Date lastApprovedOn) ;
	
	//Uma Khot:Cam upload and Dp field calculation CR
	public String getDpShare();
	public void setDpShare(String dpShare);
	
	public BigDecimal getTotalLoanableAmount();
	public void setTotalLoanableAmount(BigDecimal totalLoanableAmount);
	
	public String getDpCalculateManually();
	public void setDpCalculateManually(String dpCalculateManually);
	//End Santosh
	
	//Start Prachit 
	
	public String getStockdocMonth();
	public void setStockdocMonth(String stockdocMonth);

	public String getStockdocYear();
	public void setStockdocYear(String stockdocYear);
	
	//End Prachit
	
	public List<ILeadBankStock> getLeadBankStock();	
	public void setLeadBankStock(List<ILeadBankStock> leadNodaBankStock);

	public BigDecimal getDpForCashFlowOrBudget();
	public void setDpForCashFlowOrBudget(BigDecimal dpForCashFlowOrBudget);
	
	public BigDecimal getActualDp();
	public void setActualDp(BigDecimal actualDp);

	public BigDecimal getCoverageAmount();
	public void setCoverageAmount(BigDecimal coverageAmount);
	
	public BigDecimal getAdHocCoverageAmount();
	public void setAdHocCoverageAmount(BigDecimal adHocCoverageAmount);
	
	public Double getCoveragePercentage();
	public void setCoveragePercentage(Double coveragePercentage);
	
	public String getRemarkByMaker();
	public void setRemarkByMaker(String remarkByMaker);
	
	public String getTotalLoanable();
	public void setTotalLoanable(String totalLoanable);
	
	public String getMigrationFlag_DP_CR();
	public void setMigrationFlag_DP_CR(String migrationFlag_DP_CR);
}
