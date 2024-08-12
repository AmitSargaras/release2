package com.integrosys.cms.app.email.notification.bus;

import java.util.List;


public interface IEmailNotificationJdbc {

	public List getExpiredCAMParties();
	public List getPartiesWithoutCAM();
	public List getLADDueParties();
	public List getLADExpiredParties();
	public List getAllPartiesWithoutTagImage();
	public List getPartiesWithStaleCheques();
	public List getPartiesWithMatureCollaterals();
	public List getPartiesWithMatureCollateralInsurance();
	public List getPartiesHavingDocumentDue();
	public String getRMAndRMHeadByCustomerId(String partyId);
	public String getBranchCoordinatorEmailId(String branchCode) ;
	public String getBranchName(String branchCode) ;
	public List getAllPartiesWithMatureCollateralInsurance(); // Shiv 131212
	
	
	public List getCaseCreationDistinctSegment();
	public List getCaseCreationDistinctBranch();
	
	
	public List getCPUTRequestedCaseCreation();
	public List getBranchSentCaseCreation();
	public List getBranchNotSentCaseCreation();
	public List getCPUTReceivedCaseCreation();
	public List getCPUTNotReceivedCaseCreation();
	public List getCPUTWrongRequestCaseCreation();
	public List getTodaysDiaryList();
	public List getTodaysDiaryEmailIdList(String segment);
	
}
