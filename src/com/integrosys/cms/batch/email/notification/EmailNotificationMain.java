package com.integrosys.cms.batch.email.notification;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.email.notification.bus.ICaseCreationNotificationDetail;
import com.integrosys.cms.app.email.notification.bus.ICustomerNotificationDetail;
import com.integrosys.cms.app.email.notification.bus.IEmailNotificationJdbc;
import com.integrosys.cms.app.email.notification.bus.IEmailNotificationService;
import com.integrosys.cms.app.email.notification.bus.INotificationQuery;
import com.integrosys.cms.app.email.notification.bus.OBCustomerNotificationDetail;
import com.integrosys.cms.batch.BatchJobException;
import com.integrosys.cms.batch.InvalidParameterBatchJobException;
import com.integrosys.cms.batch.factory.BatchJob;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.component.commondata.app.bus.CommonDataDAO;
import com.integrosys.component.commondata.app.bus.OBCodeCategoryEntry;

/**
 * <p>
 * Batch program to perform the computation of due date of recurrent and
 * covenant item, subsequently create sub items
 * 
 * Required parameter:
 * <ul>
 * <li>country
 * </ul>
 * 
 * @author hmbao
 * @author Chong Jun Yong
 * @since 2006/10/09 06:10:07
 */
public class EmailNotificationMain implements BatchJob {

	private final Logger logger = LoggerFactory.getLogger(EmailNotificationMain.class);

	// private IEmailNotificationDao emailNotificationDao;
	private IEmailNotificationJdbc emailNotificationJdbc;
	private IEmailNotificationService emailNotificationService;

	public IEmailNotificationJdbc getEmailNotificationJdbc() {
		return emailNotificationJdbc;
	}

	public void setEmailNotificationJdbc(IEmailNotificationJdbc emailNotificationJdbc) {
		this.emailNotificationJdbc = emailNotificationJdbc;
	}

	public IEmailNotificationService getEmailNotificationService() {
		return emailNotificationService;
	}

	public void setEmailNotificationService(IEmailNotificationService emailNotificationService) {
		this.emailNotificationService = emailNotificationService;
	}

	/**
	 * Default Constructor
	 */
	public EmailNotificationMain() {
	}

	public void execute(Map context) throws BatchJobException {
		/* Commentted for HDFC requirement */
		String countryCode = "IN";

		if (countryCode == null) {
			throw new InvalidParameterBatchJobException("missing parameter 'country' that required for the jdbc retrieval.");
		}

		executeInternal(countryCode);
	}

	/**
	 * Process recurrent due date computation
	 */
	private void executeInternal(String countryCode) {
		// Batch Job processing goes here.
		DefaultLogger.error(this,"Inisde EmailNotificationMain");
		DefaultLogger.error(this,"Notification Generation Started.........");
		/*generateCAMExpiryNotification();
		generatePartiesWithoutCAMNotification();
		generateLADExpiredPartiesNotification();
		generateLADDuePartiesNotification();
		generateImageNotAttachNotification();
		generateStaleChequesNotification();
		generateSecurityMaturityNotification();
		generateInsuranceMaturityNotification();
		generateDocumentDueNotification();
		generateInsuranceMaturityNotificationAll();  // Shiv 131212
		generateCaseCreationNotificationCPUTRequested();
		generateCaseCreationNotificationBranchSent();
		generateCaseCreationNotificationCPUTReceived();
		generateCaseCreationNotificationCPUTNotReceived();
		generateCaseCreationNotificationBranchNotSent();
		generateCaseCreationNotificationCPUTWrongRequest();*/
		generateDiaryNotification();
		DefaultLogger.error(this,"Notification Generation Ends.........");
	}

	private void generateDiaryNotification() {
		DefaultLogger.error(this,"Generating DiaryNotification starts");
		try {
			List diaryList = getEmailNotificationJdbc().getTodaysDiaryList();
			DefaultLogger.error(this,"Total Diary Notification found :" + diaryList.size());
			for (Iterator iterator = diaryList.iterator(); iterator.hasNext();) {
				ICustomerNotificationDetail notificationDetail = (ICustomerNotificationDetail) iterator.next();
				getEmailNotificationService().createNotification("NOT00023", notificationDetail);
			}
		} catch (Exception e) {
			logger.debug("got error in generateDiaryNotification()", e.getMessage());
			logger.debug("" + e.getStackTrace());
			e.printStackTrace();

		}
		DefaultLogger.error(this,"Generating DiaryNotification ends");
	}

	private void generateLADDuePartiesNotification() {
		DefaultLogger.error(this,"Generating LADDuePartiesNotification starts");
		try {
			List ladDuePartiesList = getEmailNotificationJdbc().getLADDueParties();
			DefaultLogger.error(this,"Total LAD Due Parties found :" + ladDuePartiesList.size());
			for (Iterator iterator = ladDuePartiesList.iterator(); iterator.hasNext();) {
				ICustomerNotificationDetail notificationDetail = (ICustomerNotificationDetail) iterator.next();
				getEmailNotificationService().createNotification("NOT0007", notificationDetail);
			}
		} catch (Exception e) {
			logger.debug("got error in generateLADDuePartiesNotification()", e.getMessage());
			logger.debug("" + e.getStackTrace());
			e.printStackTrace();

		}
		DefaultLogger.error(this,"Generating LADDuePartiesNotification ends");
	}

	private void generateLADExpiredPartiesNotification() {
		DefaultLogger.error(this,"Generating LADExpiredPartiesNotification starts");
		try {
			List ladExpiredPartiesList = getEmailNotificationJdbc().getLADExpiredParties();
			DefaultLogger.error(this,"Total Parties with expired LAD found :" + ladExpiredPartiesList.size());
			for (Iterator iterator = ladExpiredPartiesList.iterator(); iterator.hasNext();) {
				ICustomerNotificationDetail notificationDetail = (ICustomerNotificationDetail) iterator.next();
				getEmailNotificationService().createNotification("NOT0008", notificationDetail);
			}
		} catch (Exception e) {
			logger.debug("got error in generateLADExpiredPartiesNotification()", e.getMessage());
			logger.debug("" + e.getStackTrace());
			e.printStackTrace();

		}
		DefaultLogger.error(this,"Generating LADExpiredPartiesNotification ends");
	}

	private void generatePartiesWithoutCAMNotification() {
		DefaultLogger.error(this,"Generating PartiesWithoutCAMNotification starts");
		try {
			List partiesWithoutCAMList = getEmailNotificationJdbc().getPartiesWithoutCAM();
			DefaultLogger.error(this,"Total Parties without CAM found :" + partiesWithoutCAMList.size());
			for (Iterator iterator = partiesWithoutCAMList.iterator(); iterator.hasNext();) {
				ICustomerNotificationDetail notificationDetail = (ICustomerNotificationDetail) iterator.next();
				getEmailNotificationService().createNotification("NOT0002", notificationDetail);
			}
		} catch (Exception e) {
			logger.debug("got error in generatePartiesWithoutCAMNotification()", e.getMessage());
			logger.debug("" + e.getStackTrace());
			e.printStackTrace();

		}
		DefaultLogger.error(this,"Generating PartiesWithoutCAMNotification ends");
	}

	private void generateCAMExpiryNotification() {
		DefaultLogger.error(this,"Generating CAMExpiryNotification starts");
		try {
			List expiredCAMParties = getEmailNotificationJdbc().getExpiredCAMParties();
			DefaultLogger.error(this,"Total Parties with Expired CAM found :" + expiredCAMParties.size());
			for (Iterator iterator = expiredCAMParties.iterator(); iterator.hasNext();) {
				ICustomerNotificationDetail notificationDetail = (ICustomerNotificationDetail) iterator.next();
				getEmailNotificationService().createNotification("NOT0003", notificationDetail);
			}
		} catch (Exception e) {
			logger.debug("got error in generateCAMExpiryNotification()", e.getMessage());
			e.printStackTrace();
			
		}
		DefaultLogger.error(this,"Generating CAMExpiryNotification ends");
	}
	private void generateImageNotAttachNotification() {
		DefaultLogger.error(this,"Generating ImageNotAttachNotification starts");
		try {
			 List allPartiesWithoutTagImageList = getEmailNotificationJdbc().getAllPartiesWithoutTagImage();
			DefaultLogger.error(this,"Total Parties witthout Tag Images found :" + allPartiesWithoutTagImageList.size());
			for (Iterator iterator = allPartiesWithoutTagImageList.iterator(); iterator.hasNext();) {
				ICustomerNotificationDetail notificationDetail = (ICustomerNotificationDetail) iterator.next();
				getEmailNotificationService().createNotification("NOT0012", notificationDetail);
			}
		} catch (Exception e) {
			logger.debug("got error in generateImageNotAttachNotification()", e.getMessage());
			e.printStackTrace();

		}
		DefaultLogger.error(this,"Generating ImageNotAttachNotification ends");
	}
	private void generateStaleChequesNotification() {
		DefaultLogger.error(this,"Generating StaleChequesNotification starts");
		try {
			List partiesWithStaleChequesList = getEmailNotificationJdbc().getPartiesWithStaleCheques();
			DefaultLogger.error(this,"Total Parties with stale cheque found :" + partiesWithStaleChequesList.size());
			for (Iterator iterator = partiesWithStaleChequesList.iterator(); iterator.hasNext();) {
				ICustomerNotificationDetail notificationDetail = (ICustomerNotificationDetail) iterator.next();
				getEmailNotificationService().createNotification("NOT0009", notificationDetail);
			}
		} catch (Exception e) {
			logger.debug("got error in generateStaleChequesNotification()", e.getMessage());
			e.printStackTrace();
			
		}
		DefaultLogger.error(this,"Generating StaleChequesNotification ends");
	}
	private void generateSecurityMaturityNotification() {
		DefaultLogger.error(this,"Generating SecurityMaturityNotification starts");
		try {
			List partiesWithMatureCollateralsList = getEmailNotificationJdbc().getPartiesWithMatureCollaterals();
			DefaultLogger.error(this,"Total Parties with mature collaterals found :" + partiesWithMatureCollateralsList.size());
			for (Iterator iterator = partiesWithMatureCollateralsList.iterator(); iterator.hasNext();) {
				ICustomerNotificationDetail notificationDetail = (ICustomerNotificationDetail) iterator.next();
				getEmailNotificationService().createNotification("NOT0004", notificationDetail);
			}
		} catch (Exception e) {
			logger.debug("got error in generateSecurityMaturityNotification()", e.getMessage());
			e.printStackTrace();
			
		}
		DefaultLogger.error(this,"Generating SecurityMaturityNotification ends.");
	}
	
	private void generateInsuranceMaturityNotification() {
		DefaultLogger.error(this,"Generating InsuranceMaturityNotification starts");
		try {
			List partiesWithMatureCollateralInsuranceList = getEmailNotificationJdbc().getPartiesWithMatureCollateralInsurance();
			DefaultLogger.error(this,"Total Parties with mature Insurance found :" + partiesWithMatureCollateralInsuranceList.size());
			for (Iterator iterator = partiesWithMatureCollateralInsuranceList.iterator(); iterator.hasNext();) {
				ICustomerNotificationDetail notificationDetail = (ICustomerNotificationDetail) iterator.next();
				getEmailNotificationService().createNotification("NOT0005", notificationDetail);
			}
		} catch (Exception e) {
			logger.debug("got error in generateInsuranceMaturityNotification()", e.getMessage());
			e.printStackTrace();
		}
		DefaultLogger.error(this,"Generating InsuranceMaturityNotification ends.");
	}
	
	private void generateDocumentDueNotification() {
		DefaultLogger.error(this,"Generating DocumentDueNotification starts");
		try {
			List partiesHavingDocumentDueList = getEmailNotificationJdbc().getPartiesHavingDocumentDue();
			String statementType="";
			for (Iterator iterator = partiesHavingDocumentDueList.iterator(); iterator.hasNext();) {
				ICustomerNotificationDetail notificationDetail = (ICustomerNotificationDetail) iterator.next();
				statementType =notificationDetail.getStatementType();
				if("FORM8".equals(statementType)){
					getEmailNotificationService().createNotification("NOT0013", notificationDetail);
				}else if(INotificationQuery.STATEMENT_TYPE_CALL_MEMO.equals(statementType)){
					getEmailNotificationService().createNotification("NOT0014", notificationDetail);
				}else if(INotificationQuery.STATEMENT_TYPE_PLANT_VISIT.equals(statementType)){
					getEmailNotificationService().createNotification("NOT0015", notificationDetail);
				}
			}
		} catch (Exception e) {
			logger.debug("got error in generateDocumentDueNotification()", e.getMessage());
			e.printStackTrace();
		}
		DefaultLogger.error(this,"Generating DocumentDueNotification ends.");
	}
	
	//Shiv 131212
	
	private void generateInsuranceMaturityNotificationAll() {
		DefaultLogger.error(this,"Generating InsuranceMaturityNotification starts");
		StringBuffer msgString = new StringBuffer("");
		boolean flag = false;
		try {
			List allPartiesWithMatureCollateralInsuranceList = getEmailNotificationJdbc().getAllPartiesWithMatureCollateralInsurance();
			DefaultLogger.error(this,"Total Parties with mature Insurance found :" + allPartiesWithMatureCollateralInsuranceList.size());
			for (Iterator iterator = allPartiesWithMatureCollateralInsuranceList.iterator(); iterator.hasNext();) {
				ICustomerNotificationDetail notificationDetail = (ICustomerNotificationDetail) iterator.next();
				msgString.append(notificationDetail.getPartyName()==null?"":notificationDetail.getPartyName());
				msgString.append(" - ");
				msgString.append(notificationDetail.getSecuritySubType()==null?"":notificationDetail.getSecuritySubType());
				msgString.append(" \n\r");
				flag = true;
				//getEmailNotificationService().createNotification("NOT0005", notificationDetail);
			}
		} catch (Exception e) {
			logger.debug("got error in generateInsuranceMaturityNotification()", e.getMessage());
			e.printStackTrace();
		}
		if(flag) {
			ICustomerNotificationDetail notificationDetail = new OBCustomerNotificationDetail(); 
			notificationDetail.setInsMsgString(msgString.toString());
			getEmailNotificationService().createNotification("NOT0016", notificationDetail);
		}
		DefaultLogger.error(this,"Generating InsuranceMaturityNotification ends.");
	}
	
	private void generateCaseCreationNotificationCPUTRequested() {
		DefaultLogger.error(this,"Generating generateCaseCreationNotificationCPUTRequested starts");
		try {
			List segmentList = getEmailNotificationJdbc().getCaseCreationDistinctSegment();
			
			List caseCreationBranchList = getEmailNotificationJdbc().getCaseCreationDistinctBranch();
			
			List caseCreationList = getEmailNotificationJdbc().getCPUTRequestedCaseCreation();
			
			
			DefaultLogger.error(this,"Case Creation caseCreationBranchList :" + caseCreationBranchList.size());
			
			for (Iterator iteratorSeg = segmentList.iterator(); iteratorSeg.hasNext();) {
			String segment =(String) iteratorSeg.next();
			ArrayList caseCreationNotificationDetailsSortedSegment= new ArrayList();
			for(Iterator iterator1 = caseCreationList.iterator(); iterator1.hasNext();){
				ICaseCreationNotificationDetail caseCreationNotificationDetail= (ICaseCreationNotificationDetail)iterator1.next();
			if(segment.equalsIgnoreCase(caseCreationNotificationDetail.getSegment())){
				caseCreationNotificationDetailsSortedSegment.add(caseCreationNotificationDetail);
			}
			}
			for (Iterator iterator = caseCreationBranchList.iterator(); iterator.hasNext();) {
				String branch = (String) iterator.next();
				ArrayList caseCreationNotificationDetails= new ArrayList();
				for(Iterator iterator2 = caseCreationNotificationDetailsSortedSegment.iterator(); iterator2.hasNext();){
					ICaseCreationNotificationDetail caseCreationNotificationDetail2= (ICaseCreationNotificationDetail)iterator2.next();
					if(branch.equalsIgnoreCase(caseCreationNotificationDetail2.getBranch())){
					caseCreationNotificationDetails.add(caseCreationNotificationDetail2);
					}
				}
				if(caseCreationNotificationDetails!=null && caseCreationNotificationDetails.size()>0){
				getEmailNotificationService().createNotificationCaseCreation("NOT0017", caseCreationNotificationDetails,branch,segment);
				}
			}
			
			
			}
		} catch (Exception e) {
			logger.debug("got error in generateCaseCreationNotificationCPUTRequested()", e.getMessage());
			e.printStackTrace();
			
		}
		DefaultLogger.error(this,"Generating generateCaseCreationNotificationCPUTRequested ends");
	}
	
	private void generateCaseCreationNotificationBranchSent() {
		DefaultLogger.error(this,"Generating generateCaseCreationNotificationBranchSent starts");
		try {
			List segmentList = getEmailNotificationJdbc().getCaseCreationDistinctSegment();
			
			List caseCreationBranchList = getEmailNotificationJdbc().getCaseCreationDistinctBranch();
			
			List caseCreationList = getEmailNotificationJdbc().getBranchSentCaseCreation();
			
			
			DefaultLogger.error(this,"Case Creation caseCreationBranchList :" + caseCreationBranchList.size());
			
			for (Iterator iteratorSeg = segmentList.iterator(); iteratorSeg.hasNext();) {
			String segment =(String) iteratorSeg.next();
			ArrayList caseCreationNotificationDetailsSortedSegment= new ArrayList();
			for(Iterator iterator1 = caseCreationList.iterator(); iterator1.hasNext();){
				ICaseCreationNotificationDetail caseCreationNotificationDetail= (ICaseCreationNotificationDetail)iterator1.next();
			if(segment.equalsIgnoreCase(caseCreationNotificationDetail.getSegment())){
				caseCreationNotificationDetailsSortedSegment.add(caseCreationNotificationDetail);
			}
			}
			for (Iterator iterator = caseCreationBranchList.iterator(); iterator.hasNext();) {
				String branch = (String) iterator.next();
				ArrayList caseCreationNotificationDetails= new ArrayList();
				for(Iterator iterator2 = caseCreationNotificationDetailsSortedSegment.iterator(); iterator2.hasNext();){
					ICaseCreationNotificationDetail caseCreationNotificationDetail2= (ICaseCreationNotificationDetail)iterator2.next();
					if(branch.equalsIgnoreCase(caseCreationNotificationDetail2.getBranch())){
					caseCreationNotificationDetails.add(caseCreationNotificationDetail2);
					}
				}
				if(caseCreationNotificationDetails!=null && caseCreationNotificationDetails.size()>0){
				getEmailNotificationService().createNotificationCaseCreation("NOT0018", caseCreationNotificationDetails,branch,segment);
				}
			}
			
			
			}
		} catch (Exception e) {
			logger.debug("got error in generateCaseCreationNotificationBranchSent()", e.getMessage());
			e.printStackTrace();
			
		}
		DefaultLogger.error(this,"Generating generateCaseCreationNotificationBranchSent ends");
	}
	
	
	private void generateCaseCreationNotificationCPUTReceived() {
		DefaultLogger.error(this,"Generating generateCaseCreationNotificationCPUTReceived starts");
		try {
			List segmentList = getEmailNotificationJdbc().getCaseCreationDistinctSegment();
			
			List caseCreationBranchList = getEmailNotificationJdbc().getCaseCreationDistinctBranch();
			
			List caseCreationList = getEmailNotificationJdbc().getCPUTReceivedCaseCreation();
			
			
			DefaultLogger.error(this,"Case Creation caseCreationBranchList :" + caseCreationBranchList.size());
			
			for (Iterator iteratorSeg = segmentList.iterator(); iteratorSeg.hasNext();) {
			String segment =(String) iteratorSeg.next();
			ArrayList caseCreationNotificationDetailsSortedSegment= new ArrayList();
			for(Iterator iterator1 = caseCreationList.iterator(); iterator1.hasNext();){
				ICaseCreationNotificationDetail caseCreationNotificationDetail= (ICaseCreationNotificationDetail)iterator1.next();
			if(segment.equalsIgnoreCase(caseCreationNotificationDetail.getSegment())){
				caseCreationNotificationDetailsSortedSegment.add(caseCreationNotificationDetail);
			}
			}
			for (Iterator iterator = caseCreationBranchList.iterator(); iterator.hasNext();) {
				String branch = (String) iterator.next();
				ArrayList caseCreationNotificationDetails= new ArrayList();
				for(Iterator iterator2 = caseCreationNotificationDetailsSortedSegment.iterator(); iterator2.hasNext();){
					ICaseCreationNotificationDetail caseCreationNotificationDetail2= (ICaseCreationNotificationDetail)iterator2.next();
					if(branch.equalsIgnoreCase(caseCreationNotificationDetail2.getBranch())){
					caseCreationNotificationDetails.add(caseCreationNotificationDetail2);
					}
				}
				if(caseCreationNotificationDetails!=null && caseCreationNotificationDetails.size()>0){
				getEmailNotificationService().createNotificationCaseCreation("NOT0019", caseCreationNotificationDetails,branch,segment);
				}
			}
			
			
			}
		} catch (Exception e) {
			logger.debug("got error in generateCaseCreationNotificationCPUTReceived()", e.getMessage());
			e.printStackTrace();
			
		}
		DefaultLogger.error(this,"Generating generateCaseCreationNotificationCPUTReceived ends");
	}
	
	private void generateCaseCreationNotificationCPUTNotReceived() {
		DefaultLogger.error(this,"Generating generateCaseCreationNotificationCPUTNotReceived starts");
		try {
			List segmentList = getEmailNotificationJdbc().getCaseCreationDistinctSegment();
			
			List caseCreationBranchList = getEmailNotificationJdbc().getCaseCreationDistinctBranch();
			
			List caseCreationList = getEmailNotificationJdbc().getCPUTNotReceivedCaseCreation();
			
			
			DefaultLogger.error(this,"Case Creation caseCreationBranchList :" + caseCreationBranchList.size());
			
			for (Iterator iteratorSeg = segmentList.iterator(); iteratorSeg.hasNext();) {
			String segment =(String) iteratorSeg.next();
			ArrayList caseCreationNotificationDetailsSortedSegment= new ArrayList();
			for(Iterator iterator1 = caseCreationList.iterator(); iterator1.hasNext();){
				ICaseCreationNotificationDetail caseCreationNotificationDetail= (ICaseCreationNotificationDetail)iterator1.next();
			if(segment.equalsIgnoreCase(caseCreationNotificationDetail.getSegment())){
				caseCreationNotificationDetailsSortedSegment.add(caseCreationNotificationDetail);
			}
			}
			for (Iterator iterator = caseCreationBranchList.iterator(); iterator.hasNext();) {
				String branch = (String) iterator.next();
				ArrayList caseCreationNotificationDetails= new ArrayList();
				for(Iterator iterator2 = caseCreationNotificationDetailsSortedSegment.iterator(); iterator2.hasNext();){
					ICaseCreationNotificationDetail caseCreationNotificationDetail2= (ICaseCreationNotificationDetail)iterator2.next();
					if(branch.equalsIgnoreCase(caseCreationNotificationDetail2.getBranch())){
					caseCreationNotificationDetails.add(caseCreationNotificationDetail2);
					}
				}
				if(caseCreationNotificationDetails!=null && caseCreationNotificationDetails.size()>0){
				getEmailNotificationService().createNotificationCaseCreation("NOT0020", caseCreationNotificationDetails,branch,segment);
				}
			}
			
			
			}
		} catch (Exception e) {
			logger.debug("got error in generateCaseCreationNotificationCPUTNotReceived()", e.getMessage());
			e.printStackTrace();
			
		}
		DefaultLogger.error(this,"Generating generateCaseCreationNotificationCPUTNotReceived ends");
	}
	
	
	
	private void generateCaseCreationNotificationBranchNotSent() {
		DefaultLogger.error(this,"Generating generateCaseCreationNotificationBranchNotSent starts");
		try {
			List segmentList = getEmailNotificationJdbc().getCaseCreationDistinctSegment();
			
			List caseCreationBranchList = getEmailNotificationJdbc().getCaseCreationDistinctBranch();
			
			List caseCreationList = getEmailNotificationJdbc().getBranchNotSentCaseCreation();
			
			
			DefaultLogger.error(this,"Case Creation caseCreationBranchList :" + caseCreationBranchList.size());
			
			for (Iterator iteratorSeg = segmentList.iterator(); iteratorSeg.hasNext();) {
			String segment =(String) iteratorSeg.next();
			ArrayList caseCreationNotificationDetailsSortedSegment= new ArrayList();
			for(Iterator iterator1 = caseCreationList.iterator(); iterator1.hasNext();){
				ICaseCreationNotificationDetail caseCreationNotificationDetail= (ICaseCreationNotificationDetail)iterator1.next();
			if(segment.equalsIgnoreCase(caseCreationNotificationDetail.getSegment())){
				caseCreationNotificationDetailsSortedSegment.add(caseCreationNotificationDetail);
			}
			}
			for (Iterator iterator = caseCreationBranchList.iterator(); iterator.hasNext();) {
				String branch = (String) iterator.next();
				ArrayList caseCreationNotificationDetails= new ArrayList();
				for(Iterator iterator2 = caseCreationNotificationDetailsSortedSegment.iterator(); iterator2.hasNext();){
					ICaseCreationNotificationDetail caseCreationNotificationDetail2= (ICaseCreationNotificationDetail)iterator2.next();
					if(branch.equalsIgnoreCase(caseCreationNotificationDetail2.getBranch())){
					caseCreationNotificationDetails.add(caseCreationNotificationDetail2);
					}
				}
				if(caseCreationNotificationDetails!=null && caseCreationNotificationDetails.size()>0){
				getEmailNotificationService().createNotificationCaseCreation("NOT0021", caseCreationNotificationDetails,branch,segment);
				}
			}
			
			
			}
		} catch (Exception e) {
			logger.debug("got error in generateCaseCreationNotificationBranchNotSent()", e.getMessage());
			e.printStackTrace();
			
		}
		DefaultLogger.error(this,"Generating generateCaseCreationNotificationBranchNotSent ends");
	}
	
	
	
	
	
	private void generateCaseCreationNotificationCPUTWrongRequest() {
		DefaultLogger.error(this,"Generating generateCaseCreationNotificationCPUTWrongRequest starts");
		try {
			List segmentList = getEmailNotificationJdbc().getCaseCreationDistinctSegment();
			
			List caseCreationBranchList = getEmailNotificationJdbc().getCaseCreationDistinctBranch();
			
			List caseCreationList = getEmailNotificationJdbc().getCPUTWrongRequestCaseCreation();
			
			
			DefaultLogger.error(this,"Case Creation caseCreationBranchList :" + caseCreationBranchList.size());
			
			for (Iterator iteratorSeg = segmentList.iterator(); iteratorSeg.hasNext();) {
			String segment =(String) iteratorSeg.next();
			ArrayList caseCreationNotificationDetailsSortedSegment= new ArrayList();
			for(Iterator iterator1 = caseCreationList.iterator(); iterator1.hasNext();){
				ICaseCreationNotificationDetail caseCreationNotificationDetail= (ICaseCreationNotificationDetail)iterator1.next();
			if(segment.equalsIgnoreCase(caseCreationNotificationDetail.getSegment())){
				caseCreationNotificationDetailsSortedSegment.add(caseCreationNotificationDetail);
			}
			}
			for (Iterator iterator = caseCreationBranchList.iterator(); iterator.hasNext();) {
				String branch = (String) iterator.next();
				ArrayList caseCreationNotificationDetails= new ArrayList();
				for(Iterator iterator2 = caseCreationNotificationDetailsSortedSegment.iterator(); iterator2.hasNext();){
					ICaseCreationNotificationDetail caseCreationNotificationDetail2= (ICaseCreationNotificationDetail)iterator2.next();
					if(branch.equalsIgnoreCase(caseCreationNotificationDetail2.getBranch())){
					caseCreationNotificationDetails.add(caseCreationNotificationDetail2);
					}
				}
				if(caseCreationNotificationDetails!=null && caseCreationNotificationDetails.size()>0){
				getEmailNotificationService().createNotificationCaseCreation("NOT0022", caseCreationNotificationDetails,branch,segment);
				}
			}
			
			
			}
		} catch (Exception e) {
			logger.debug("got error in generateCaseCreationNotificationCPUTWrongRequest()", e.getMessage());
			e.printStackTrace();
			
		}
		DefaultLogger.error(this,"Generating generateCaseCreationNotificationCPUTWrongRequest ends");
	}

}