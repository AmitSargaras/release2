/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.manualinput.aa;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.common.bus.BaseCurrency;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.bus.OBBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomerUdf;
import com.integrosys.cms.app.customer.bus.OBCMSCustomerUdf;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.ILimitProfileUdf;
import com.integrosys.cms.app.limit.bus.OBLimitProfile;
import com.integrosys.cms.app.limit.bus.OBLimitProfileUdf;
import com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;

/**
 * Describe this class. Purpose: Map the form to OB or OB to form for Manual
 * Input AA Detail Description: Map the value from database to the screen or
 * from the screen that user key in to database
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date: $ Tag: $Name$
 */

public class AADetailMapper extends AbstractCommonMapper {

	public String[][] getParameterDescriptor() {
		return (new String[][] { { ICommonEventConstant.EVENT, "java.lang.String", REQUEST_SCOPE },
				{ "limitProfileTrxVal", "com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue", SERVICE_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ "InitialLimitProfile", "java.lang.Object", FORM_SCOPE },
				{ "indexChange", "java.lang.String", REQUEST_SCOPE }, { "preEvent", "java.lang.String", REQUEST_SCOPE } });

	}

	/**
	 * This method is used to map the Form values into Corresponding OB Values
	 * and returns the same.
	 * 
	 * @param cForm is of type CommonForm
	 * @return Object
	 */

	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		DefaultLogger.debug(this, "Inside Map Form to OB ");
		String event = (String) map.get(ICommonEventConstant.EVENT);
		String ind = (String) map.get("indexChange");
		int indexChange = 0;

		if (ind != null) {
			indexChange = Integer.parseInt(ind);
		}

		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		AADetailForm aForm = (AADetailForm) cForm;
		Double haircut;
		Double dealRate;

		try {
			if (AADetailAction.EVENT_VIEW.equals(event)) {

				OBLimitProfile obLimitProfile = new OBLimitProfile();

				return obLimitProfile;

			}
			else if (event.equals("maker_edit_aadetail") || event.equals("refresh_maker_edit_aadetail")
					|| event.equals("delete_aadetail_confirm") || event.equals("maker_delete_aadetail")
					|| event.equals("maker_delete_aadetail")
					|| event.equals("refresh_maker_edit_aadetail_reject")
					|| event.equals("maker_delete_aadetail_confirm")) {

				OBLimitProfile obLimitProfile = new OBLimitProfile();

				if (indexChange == 0) {
					obLimitProfile.setLimitProfileID(Long.parseLong(aForm.getAaID()));

					// aForm.getLeID not storing LEID , but CMS_CUSTOMER_ID
					// instead
					obLimitProfile.setCustomerID(Long.parseLong(aForm.getLeID()));
				}
				else {
					OBLimitProfileTrxValue oldTrxValue = (OBLimitProfileTrxValue) map.get("limitProfileTrxVal");
					obLimitProfile = (OBLimitProfile) oldTrxValue.getStagingLimitProfile();

					if (obLimitProfile != null) {

						// todo
						// aForm.getLeID not storing LEID , but CMS_CUSTOMER_ID
						// instead
						obLimitProfile.setCustomerID(Long.parseLong(aForm.getLeID()));
						obLimitProfile.setLEReference(aForm.getLeReference());

						obLimitProfile.setBCAReference(aForm.getAaNum());
						obLimitProfile.setSourceID(aForm.getAaSource());
						obLimitProfile.setAAType(aForm.getAaType());

						if (!AbstractCommonMapper.isEmptyOrNull(aForm.getAaApprovalDate())) {
							Date stageDate = compareDate(locale, obLimitProfile.getApprovalDate(), aForm
									.getAaApprovalDate());
							obLimitProfile.setApprovalDate(stageDate);
						}
						else {
							obLimitProfile.setApprovalDate(null);
						}
						
						//Uma Khot:Added for Valid Rating CR
						if (!AbstractCommonMapper.isEmptyOrNull(aForm.getRamRatingFinalizationDate())) {
							Date stageDate = compareDate(locale, obLimitProfile.getRamRatingFinalizationDate(), aForm
									.getRamRatingFinalizationDate());
							obLimitProfile.setRamRatingFinalizationDate(stageDate);
						}
						else {
							obLimitProfile.setRamRatingFinalizationDate(null);
						}
						
						IBookingLocation orgLocation = null;
						if (obLimitProfile.getOriginatingLocation() != null) {
							orgLocation = obLimitProfile.getOriginatingLocation();
						}
						else {
							orgLocation = new OBBookingLocation();
						}

						if (!(aForm.getBookingCty().equals(null) || aForm.getBookingCty().equals(""))
								|| !(aForm.getBookingOrg().equals(null) || aForm.getBookingOrg().equals(""))) {
							orgLocation.setCountryCode(aForm.getBookingCty());
							orgLocation.setOrganisationCode(aForm.getBookingOrg());
							obLimitProfile.setOriginatingLocation(orgLocation);
						}
						else {
							obLimitProfile.setOriginatingLocation(null);
						}

						if (!AbstractCommonMapper.isEmptyOrNull(aForm.getInterimReviewDate())) {
							Date stageDate = compareDate(locale, obLimitProfile.getNextInterimReviewDate(), aForm
									.getInterimReviewDate());
							obLimitProfile.setNextInterimReviewDate(stageDate);
						}
						else {
							obLimitProfile.setNextInterimReviewDate(null);
						}

						if (!AbstractCommonMapper.isEmptyOrNull(aForm.getAnnualReviewDate())) {
							Date stageDate = compareDate(locale, obLimitProfile.getNextAnnualReviewDate(), aForm
									.getAnnualReviewDate());
							obLimitProfile.setNextAnnualReviewDate(stageDate);
						}
						else {
							obLimitProfile.setNextAnnualReviewDate(null);
						}
						
						this.addUdfToOb(aForm, obLimitProfile);
					}
					
					 //Start:Uma Khot:CRI Field addition enhancement
					obLimitProfile.setIsSpecialApprGridForCustBelowHDB8(aForm.getIsSpecialApprGridForCustBelowHDB8());
					obLimitProfile.setIsSingleBorrowerPrudCeiling(aForm.getIsSingleBorrowerPrudCeiling());
					obLimitProfile.setDetailsOfRbiApprovalForSingle(aForm.getDetailsOfRbiApprovalForSingle());
					obLimitProfile.setIsGroupBorrowerPrudCeiling(aForm.getIsGroupBorrowerPrudCeiling());
					obLimitProfile.setDetailsOfRbiApprovalForGroup(aForm.getDetailsOfRbiApprovalForGroup());
					obLimitProfile.setIsNonCooperativeBorrowers(aForm.getIsNonCooperativeBorrowers());
					obLimitProfile.setIsDirectorAsNonCooperativeForOther(aForm.getIsDirectorAsNonCooperativeForOther());
					obLimitProfile.setNameOfDirectorsAndCompany(aForm.getNameOfDirectorsAndCompany());
					 //End:Uma Khot:CRI Field addition enhancement
					
					obLimitProfile.setRbiApprovalForSingle(aForm.getRbiApprovalForSingle());
					obLimitProfile.setRbiApprovalForGroup(aForm.getRbiApprovalForGroup());
					
				}

				return obLimitProfile;

			}
			else if (event.equals("maker_close_aadetail_confirm")) {

				OBLimitProfile obLimitProfile = new OBLimitProfile();
				return obLimitProfile;

			}
			else if (event.equals("maker_edit_aadetail_confirm") || event.equals("maker_edit_aadetail_reject_confirm") ||  event.equals("return_edit_Other_Covenant_List_to_AA_confirm")) {

				OBLimitProfileTrxValue oldTrxValue = (OBLimitProfileTrxValue) map.get("limitProfileTrxVal");

				// copy all old values from ORIGINAL value int newBusinessValue.
				OBLimitProfile newLimitProfile = null;

			if (event.equals("maker_edit_aadetail_confirm") || event.equals("return_edit_Other_Covenant_List_to_AA_confirm") ) {
					// copy all old values from ORIGINAL value int
					// newBusinessValue.
					if (oldTrxValue.getStatus().equals(ICMSConstant.STATE_REJECTED_CREATE) || (indexChange > 0)) {

						newLimitProfile = (OBLimitProfile) oldTrxValue.getStagingLimitProfile();
					}
					else {
						newLimitProfile = (OBLimitProfile) oldTrxValue.getLimitProfile();

					}

				}
				else if ("maker_edit_aadetail_reject_confirm".equals(event)) {
					// copy all old values from STAGING value int
					// newBusinessValue.
					newLimitProfile = (OBLimitProfile) oldTrxValue.getStagingLimitProfile();
				}

				if (newLimitProfile != null) {
					// todo
					newLimitProfile.setCustomerID(Long.parseLong(aForm.getLeID()));
					newLimitProfile.setLEReference(aForm.getLeReference());
					//newLimitProfile.setCamType(aForm.getCamType());
					newLimitProfile.setBCAReference(aForm.getAaNum());
					newLimitProfile.setSourceID(aForm.getAaSource());
					newLimitProfile.setAAType(aForm.getAaType());

					if (!AbstractCommonMapper.isEmptyOrNull(aForm.getAaApprovalDate())) {
						Date stageDate = compareDate(locale, newLimitProfile.getApprovalDate(), aForm
								.getAaApprovalDate());
						newLimitProfile.setApprovalDate(stageDate);
					}
					else {
						newLimitProfile.setApprovalDate(null);
					}
					
					//Uma Khot:Added for Valid Rating CR
					if (!AbstractCommonMapper.isEmptyOrNull(aForm.getRamRatingFinalizationDate())) {
						Date stageDate = compareDate(locale, newLimitProfile.getRamRatingFinalizationDate(), aForm
								.getRamRatingFinalizationDate());
						newLimitProfile.setRamRatingFinalizationDate(stageDate);
					}
					else {
						newLimitProfile.setRamRatingFinalizationDate(null);
					}
					
					IBookingLocation orgLocation = newLimitProfile.getOriginatingLocation();
					orgLocation.setCountryCode(aForm.getBookingCty());
					orgLocation.setOrganisationCode(aForm.getBookingOrg());
					newLimitProfile.setOriginatingLocation(orgLocation);

					if (!AbstractCommonMapper.isEmptyOrNull(aForm.getInterimReviewDate())) {
						Date stageDate = compareDate(locale, newLimitProfile.getNextInterimReviewDate(), aForm
								.getInterimReviewDate());
						newLimitProfile.setNextInterimReviewDate(stageDate);
					}
					else {
						newLimitProfile.setNextInterimReviewDate(null);
					}

					if (!AbstractCommonMapper.isEmptyOrNull(aForm.getAnnualReviewDate())) {
						Date stageDate = compareDate(locale, newLimitProfile.getNextAnnualReviewDate(), aForm
								.getAnnualReviewDate());
						newLimitProfile.setNextAnnualReviewDate(stageDate);
					}
					else {
						newLimitProfile.setNextAnnualReviewDate(null);
					}
					//Shiv
					newLimitProfile.setCamType(aForm.getCamType());  //CAM Type
					if (!AbstractCommonMapper.isEmptyOrNull(aForm.getCamLoginDate())) {
						Date stageDate = compareDate(locale, newLimitProfile.getCamLoginDate(), aForm
								.getCamLoginDate());
						newLimitProfile.setCamLoginDate(stageDate);
					}
					else {
						newLimitProfile.setCamLoginDate(null);
					}
					//newLimitProfile.setApprovingOfficerGrade(aForm.getApprovingOfficerGrade());  //Risk Grade
					if (!AbstractCommonMapper.isEmptyOrNull(aForm.getExtendedNextReviewDate())) {
						Date stageDate = compareDate(locale, newLimitProfile.getExtendedNextReviewDate(), aForm
								.getExtendedNextReviewDate());
						newLimitProfile.setExtendedNextReviewDate(stageDate);
					}
					else {
						newLimitProfile.setExtendedNextReviewDate(null);
					}
					if(aForm.getNoOfTimesExtended() != null && !aForm.getNoOfTimesExtended().trim().equals("")) {
						newLimitProfile.setNoOfTimesExtended(Long.parseLong(aForm.getNoOfTimesExtended()));  //no. of times extended
					}else{
						newLimitProfile.setNoOfTimesExtended(0);  //no. of times extended
					}
					if(!aForm.getTotalSactionedAmount().equals("")&& !(aForm.getTotalSactionedAmount() == null)) {
						double d = Double.parseDouble(aForm.getTotalSactionedAmount().replaceAll(",", ""));
						newLimitProfile.setTotalSactionedAmount(d); //Tot Sanction amount
					}else{
						newLimitProfile.setTotalSactionedAmount(0);
					}
					
					//Start:Code added for Total Sanctioned Amount For Facility
					if(!aForm.getTotalSanctionedAmountFacLevel().equals("")&& !(aForm.getTotalSanctionedAmountFacLevel() == null)) {
						newLimitProfile.setTotalSanctionedAmountFacLevel(aForm.getTotalSanctionedAmountFacLevel().replaceAll(",", ""));  //Total Sanction amount for Facility
					}else{
						newLimitProfile.setTotalSanctionedAmountFacLevel("0");
					}
					//End :Code added for Total Sanctioned Amount For Facility
					
					newLimitProfile.setRelationshipManager(aForm.getRelationshipManager());  //Relationship Manager
					newLimitProfile.setControllingBranch(aForm.getControllingBranch());  //Controlling Branch
					if(aForm.getCommitteApproval() == null || aForm.getCommitteApproval().equals("")){
						newLimitProfile.setCommitteApproval("N");   //Committee Approval
					}else{
						newLimitProfile.setCommitteApproval("Y");   //Committee Approval
					}
					
					//Start:Code added for Fully Cash Collateral
					if(aForm.getFullyCashCollateral() == null || aForm.getFullyCashCollateral().equals("")){
						newLimitProfile.setFullyCashCollateral("N");   //Fully Cash Collateral
					}else{
						newLimitProfile.setFullyCashCollateral("Y");   //Fully Cash Collateral
					}
					//End  :Code added for Fully Cash Collateral
					if(aForm.getBoardApproval() == null || aForm.getBoardApproval().equals("")){
						newLimitProfile.setBoardApproval("N");  //BoardApproval
					}else{
						newLimitProfile.setBoardApproval("Y");  //BoardApproval
					}
					newLimitProfile.setApproverEmployeeName1(aForm.getCreditApproval1());  //Credit Approval 1
					newLimitProfile.setApproverEmployeeName2(aForm.getCreditApproval2()); //Credit Approval 2
					newLimitProfile.setApproverEmployeeName3(aForm.getCreditApproval3()); //Credit Approval 3
					newLimitProfile.setApproverEmployeeName4(aForm.getCreditApproval4()); //Credit Approval 4
					newLimitProfile.setApproverEmployeeName5(aForm.getCreditApproval5()); //Credit Approval 5
					newLimitProfile.setAssetClassification(aForm.getAssetClassification()); //Assert Classification
					newLimitProfile.setRbiAssetClassification(aForm.getRbiAssetClassification()); //RBI Assert Classification
					newLimitProfile.setDocRemarks(aForm.getDocRemarks()); 
					//Added by Pramod Katkar for New Filed CR on 20-08-2013
					newLimitProfile.setRamRating(aForm.getRamRating());
					newLimitProfile.setRamRatingType(aForm.getRamRatingType());
					newLimitProfile.setRamRatingYear(aForm.getRamRatingYear());
					//End by Pramod Katkar
					
					 //Start:Uma Khot:CRI Field addition enhancement
					newLimitProfile.setIsSpecialApprGridForCustBelowHDB8(aForm.getIsSpecialApprGridForCustBelowHDB8());
					newLimitProfile.setIsSingleBorrowerPrudCeiling(aForm.getIsSingleBorrowerPrudCeiling());
					newLimitProfile.setDetailsOfRbiApprovalForSingle(aForm.getDetailsOfRbiApprovalForSingle());
					newLimitProfile.setIsGroupBorrowerPrudCeiling(aForm.getIsGroupBorrowerPrudCeiling());
					newLimitProfile.setDetailsOfRbiApprovalForGroup(aForm.getDetailsOfRbiApprovalForGroup());
					newLimitProfile.setIsNonCooperativeBorrowers(aForm.getIsNonCooperativeBorrowers());
					newLimitProfile.setIsDirectorAsNonCooperativeForOther(aForm.getIsDirectorAsNonCooperativeForOther());
					newLimitProfile.setNameOfDirectorsAndCompany(aForm.getNameOfDirectorsAndCompany());
					 //End:Uma Khot:CRI Field addition enhancement
					
					newLimitProfile.setRbiApprovalForSingle(aForm.getRbiApprovalForSingle());
					newLimitProfile.setRbiApprovalForGroup(aForm.getRbiApprovalForGroup());
					
					if (!AbstractCommonMapper.isEmptyOrNull(aForm.getAaApprovalDate())) {
						Date stageDate = compareDate(locale, newLimitProfile.getApprovalDate(), aForm.getAaApprovalDate());
						newLimitProfile.setApprovalDate(stageDate);
					}
					else {
						newLimitProfile.setApprovalDate(null);
					}
					
					//Uma Khot:Added for Valid Rating CR
					if (!AbstractCommonMapper.isEmptyOrNull(aForm.getRamRatingFinalizationDate())) {
						Date stageDate = compareDate(locale, newLimitProfile.getRamRatingFinalizationDate(), aForm.getRamRatingFinalizationDate());
						newLimitProfile.setRamRatingFinalizationDate(stageDate);
					}
					else {
						newLimitProfile.setRamRatingFinalizationDate(null);
					}
					
					this.addUdfToOb(aForm, newLimitProfile);
					return newLimitProfile;
				}
			}
			else if (event.equals("maker_add_aadetail_confirm") || "return_Other_Covenant_List_to_AA_confirm".equals(event)|| "refresh_maker_add_aadetail".equals(event)) {

				OBLimitProfileTrxValue oldTrxValue = (OBLimitProfileTrxValue) map.get("limitProfileTrxVal");

				// copy all old values from ORIGINAL value int newBusinessValue.
				OBLimitProfile newLimitProfile = null;

				if ((indexChange > 0) && (oldTrxValue != null)) {
					newLimitProfile = (OBLimitProfile) oldTrxValue.getStagingLimitProfile();
				}
				else {
					newLimitProfile = new OBLimitProfile();
				}

				// todo
				newLimitProfile.setCustomerID(Long.parseLong(aForm.getLeID()));
				newLimitProfile.setLEReference(aForm.getLeReference());
				newLimitProfile.setBCAReference(aForm.getAaNum());
				newLimitProfile.setSourceID(aForm.getAaSource());
				newLimitProfile.setAAType(aForm.getAaType());
				
				newLimitProfile.setCamType(aForm.getCamType());  //CAM Type
				if (!AbstractCommonMapper.isEmptyOrNull(aForm.getCamLoginDate())) {
					Date stageDate = compareDate(locale, newLimitProfile.getCamLoginDate(), aForm
							.getCamLoginDate());
					newLimitProfile.setCamLoginDate(stageDate);
				}
				else {
					newLimitProfile.setCamLoginDate(null);
				}
				//newLimitProfile.setApprovingOfficerGrade(aForm.getApprovingOfficerGrade());  //Risk Grade
				if (!AbstractCommonMapper.isEmptyOrNull(aForm.getExtendedNextReviewDate())) {
					Date stageDate = compareDate(locale, newLimitProfile.getExtendedNextReviewDate(), aForm
							.getExtendedNextReviewDate());
					newLimitProfile.setExtendedNextReviewDate(stageDate);
				}
				else {
					newLimitProfile.setExtendedNextReviewDate(null);
				}
				if(!aForm.getNoOfTimesExtended().equals("")&&!(aForm.getNoOfTimesExtended() == null)) {
					newLimitProfile.setNoOfTimesExtended(Long.parseLong(aForm.getNoOfTimesExtended()));  //no. of times extended
				}else{
					newLimitProfile.setNoOfTimesExtended(0);  //no. of times extended
				}
				if(!aForm.getTotalSactionedAmount().equals("")&& !(aForm.getTotalSactionedAmount() == null)) {
					newLimitProfile.setTotalSactionedAmount(Double.parseDouble(aForm.getTotalSactionedAmount().replaceAll(",", "")));  //Tot Sanction amount
				}else{
					newLimitProfile.setTotalSactionedAmount(0);
				}
				
				//Start:Code added for Total Sanctioned Amount For Facility
				if(!aForm.getTotalSanctionedAmountFacLevel().equals("")&& !(aForm.getTotalSanctionedAmountFacLevel() == null)) {
					newLimitProfile.setTotalSanctionedAmountFacLevel(aForm.getTotalSanctionedAmountFacLevel().replaceAll(",", ""));  //Total Sanction amount for Facility
				}else{
					newLimitProfile.setTotalSanctionedAmountFacLevel("0");
				}
				//End :Code added for Total Sanctioned Amount For Facility
				
				newLimitProfile.setRelationshipManager(aForm.getRelationshipManager());  //Relationship Manager
				newLimitProfile.setControllingBranch(aForm.getControllingBranch());  //Controlling Branch
				if(aForm.getCommitteApproval() == null || aForm.getCommitteApproval().equals("")){
					newLimitProfile.setCommitteApproval("N");   //Committee Approval
				}else{
					newLimitProfile.setCommitteApproval("Y");   //Committee Approval
				}
				//Start:Code added for Fully Cash Collateral
				if(aForm.getFullyCashCollateral() == null || aForm.getFullyCashCollateral().equals("")){
					newLimitProfile.setFullyCashCollateral("N");   //Fully Cash Collateral
				}else{
					newLimitProfile.setFullyCashCollateral("Y");   //Fully Cash Collateral
				}
				//End:Code added for Fully Cash Collateral
				
				if(aForm.getBoardApproval() == null || aForm.getBoardApproval().equals("")){
					newLimitProfile.setBoardApproval("N");  //BoardApproval
				}else{
					newLimitProfile.setBoardApproval("Y");  //BoardApproval
				}
				newLimitProfile.setApproverEmployeeName1(aForm.getCreditApproval1());  //Credit Approval 1
				newLimitProfile.setApproverEmployeeName2(aForm.getCreditApproval2()); //Credit Approval 2
				newLimitProfile.setApproverEmployeeName3(aForm.getCreditApproval3()); //Credit Approval 3
				newLimitProfile.setApproverEmployeeName4(aForm.getCreditApproval4()); //Credit Approval 4
				newLimitProfile.setApproverEmployeeName5(aForm.getCreditApproval5()); //Credit Approval 5
				newLimitProfile.setAssetClassification(aForm.getAssetClassification()); //Assert Classification
				newLimitProfile.setRbiAssetClassification(aForm.getRbiAssetClassification()); //RBI Assert Classification
				newLimitProfile.setDocRemarks(aForm.getDocRemarks()); 
				//Added by Pramod Katkar for New Filed CR on 20-08-2013
				newLimitProfile.setRamRating(aForm.getRamRating());
				newLimitProfile.setRamRatingType(aForm.getRamRatingType());
				newLimitProfile.setRamRatingYear(aForm.getRamRatingYear());
				//End by Pramod Katkar
				
				 //Start:Uma Khot:CRI Field addition enhancement
				newLimitProfile.setIsSpecialApprGridForCustBelowHDB8(aForm.getIsSpecialApprGridForCustBelowHDB8());
				newLimitProfile.setIsSingleBorrowerPrudCeiling(aForm.getIsSingleBorrowerPrudCeiling());
				newLimitProfile.setDetailsOfRbiApprovalForSingle(aForm.getDetailsOfRbiApprovalForSingle());
				newLimitProfile.setIsGroupBorrowerPrudCeiling(aForm.getIsGroupBorrowerPrudCeiling());
				newLimitProfile.setDetailsOfRbiApprovalForGroup(aForm.getDetailsOfRbiApprovalForGroup());
				newLimitProfile.setIsNonCooperativeBorrowers(aForm.getIsNonCooperativeBorrowers());
				newLimitProfile.setIsDirectorAsNonCooperativeForOther(aForm.getIsDirectorAsNonCooperativeForOther());
				newLimitProfile.setNameOfDirectorsAndCompany(aForm.getNameOfDirectorsAndCompany());
				 //End:Uma Khot:CRI Field addition enhancement
				
				newLimitProfile.setRbiApprovalForSingle(aForm.getRbiApprovalForSingle());
				newLimitProfile.setRbiApprovalForGroup(aForm.getRbiApprovalForGroup());
				
				if (!AbstractCommonMapper.isEmptyOrNull(aForm.getAaApprovalDate())) {
					Date stageDate = compareDate(locale, newLimitProfile.getApprovalDate(), aForm.getAaApprovalDate());
					newLimitProfile.setApprovalDate(stageDate);
				}
				else {
					newLimitProfile.setApprovalDate(null);
				}

				IBookingLocation orgLocation = null;
				if (newLimitProfile.getOriginatingLocation() != null) {
					orgLocation = newLimitProfile.getOriginatingLocation();
				}
				else {
					orgLocation = new OBBookingLocation();
				}

				if (!(aForm.getBookingCty().equals(null) || aForm.getBookingCty().equals(""))
						|| !(aForm.getBookingOrg().equals(null) || aForm.getBookingOrg().equals(""))) {
					orgLocation.setCountryCode(aForm.getBookingCty());
					orgLocation.setOrganisationCode(aForm.getBookingOrg());
					newLimitProfile.setOriginatingLocation(orgLocation);
				}
				else {
					newLimitProfile.setOriginatingLocation(null);
				}

				if (!AbstractCommonMapper.isEmptyOrNull(aForm.getInterimReviewDate())) {
					Date stageDate = compareDate(locale, newLimitProfile.getNextInterimReviewDate(), aForm
							.getInterimReviewDate());
					newLimitProfile.setNextInterimReviewDate(stageDate);
				}
				else {
					newLimitProfile.setNextInterimReviewDate(null);
				}

				if (!AbstractCommonMapper.isEmptyOrNull(aForm.getAnnualReviewDate())) {
					Date stageDate = compareDate(locale, newLimitProfile.getNextAnnualReviewDate(), aForm
							.getAnnualReviewDate());
					newLimitProfile.setNextAnnualReviewDate(stageDate);
				}
				else {
					newLimitProfile.setNextAnnualReviewDate(null);
				}
				
				if (!AbstractCommonMapper.isEmptyOrNull(aForm.getRamRatingFinalizationDate())) {
					Date stageDate = compareDate(locale, newLimitProfile.getRamRatingFinalizationDate(), aForm
							.getRamRatingFinalizationDate());
					newLimitProfile.setRamRatingFinalizationDate(stageDate);
				}
				else {
					newLimitProfile.setRamRatingFinalizationDate(null);
				}
				
				this.addUdfToOb(aForm, newLimitProfile);
				return newLimitProfile;

			}
			else if ("remove_agreement".equals(event)) {

				OBLimitProfileTrxValue oldTrxValue = (OBLimitProfileTrxValue) map.get("limitProfileTrxVal");

				OBLimitProfileTrxValue newTrxValue = oldTrxValue;

				// copy all old values from ORIGINAL value int newBusinessValue.
				OBLimitProfile oldLimitProfile = null;
				OBLimitProfile newLimitProfile = null;

				oldLimitProfile = (OBLimitProfile) oldTrxValue.getStagingLimitProfile();

				if (oldLimitProfile != null) {
					// todo
					oldLimitProfile.setCustomerID(Long.parseLong(aForm.getLeID()));
					oldLimitProfile.setLEReference(aForm.getLeReference());
					oldLimitProfile.setBCAReference(aForm.getAaNum());
					oldLimitProfile.setSourceID(aForm.getAaSource());
					oldLimitProfile.setAAType(aForm.getAaType());

					if (!AbstractCommonMapper.isEmptyOrNull(aForm.getAaApprovalDate())) {
						Date stageDate = compareDate(locale, oldLimitProfile.getApprovalDate(), aForm
								.getAaApprovalDate());
						oldLimitProfile.setApprovalDate(stageDate);
					}
					else {
						oldLimitProfile.setApprovalDate(null);
					}

					IBookingLocation orgLocation = oldLimitProfile.getOriginatingLocation();
					orgLocation.setCountryCode(aForm.getBookingCty());
					orgLocation.setOrganisationCode(aForm.getBookingOrg());
					oldLimitProfile.setOriginatingLocation(orgLocation);

					if (!AbstractCommonMapper.isEmptyOrNull(aForm.getInterimReviewDate())) {
						Date stageDate = compareDate(locale, oldLimitProfile.getNextInterimReviewDate(), aForm
								.getInterimReviewDate());
						oldLimitProfile.setNextInterimReviewDate(stageDate);
					}
					else {
						oldLimitProfile.setNextInterimReviewDate(null);
					}

					if (!AbstractCommonMapper.isEmptyOrNull(aForm.getAnnualReviewDate())) {
						Date stageDate = compareDate(locale, oldLimitProfile.getNextAnnualReviewDate(), aForm
								.getAnnualReviewDate());
						oldLimitProfile.setNextAnnualReviewDate(stageDate);
					}
					else {
						oldLimitProfile.setNextAnnualReviewDate(null);
					}

					if (aForm.getDeletedAgreement() != null) {
						newLimitProfile = new OBLimitProfile();
						newLimitProfile = oldLimitProfile;
						newLimitProfile.setTradingAgreement(null);

					}
					
					this.addUdfToOb(aForm, newLimitProfile);
				}

				return newLimitProfile;

			}
			else if ("edit_agreement".equals(event) || "add_agreement".equals(event)) {

				String preEvent = (String) map.get("preEvent");
				OBLimitProfileTrxValue oldTrxValue = (OBLimitProfileTrxValue) map.get("limitProfileTrxVal");

				// copy all old values from ORIGINAL value int newBusinessValue.
				OBLimitProfile newLimitProfile = null;

				if (preEvent.equals("maker_edit_aadetail") || preEvent.equals("refresh_maker_edit_aadetail")) {
					// copy all old values from ORIGINAL value int
					// newBusinessValue.
					if (oldTrxValue.getStatus().equals(ICMSConstant.STATE_REJECTED_CREATE) || (indexChange > 0)) {
						newLimitProfile = (OBLimitProfile) oldTrxValue.getStagingLimitProfile();

					}
					else {
						newLimitProfile = (OBLimitProfile) oldTrxValue.getLimitProfile();

					}

				}
				else if ("maker_add_aadetail".equals(preEvent) || preEvent.equals("refresh_maker_add_aadetail")) {
					if ((indexChange > 0) && (oldTrxValue != null)) {
						newLimitProfile = (OBLimitProfile) oldTrxValue.getStagingLimitProfile();

					}
					else {
						newLimitProfile = new OBLimitProfile();
					}

				}
				else if ("maker_edit_aadetail_reject".equals(preEvent)
						|| "refresh_maker_edit_aadetail_reject".equals(event)) {
					// copy all old values from STAGING value int
					// newBusinessValue.
					newLimitProfile = (OBLimitProfile) oldTrxValue.getStagingLimitProfile();
				}

				// todo
				newLimitProfile.setCustomerID(Long.parseLong(aForm.getLeID()));
				newLimitProfile.setLEReference(aForm.getLeReference());

				if (!(aForm.getAaNum().equals(null) || aForm.getAaNum().equals(""))) {
					newLimitProfile.setBCAReference(aForm.getAaNum());
				}
				if (!(aForm.getAaSource().equals(null) || aForm.getAaSource().equals(""))) {
					newLimitProfile.setSourceID(aForm.getAaSource());
				}
				if (!(aForm.getAaType().equals(null) || aForm.getAaType().equals(""))) {
					newLimitProfile.setAAType(aForm.getAaType());
				}
				
			
				

				if (!AbstractCommonMapper.isEmptyOrNull(aForm.getAaApprovalDate())) {
					Date stageDate = compareDate(locale, newLimitProfile.getApprovalDate(), aForm.getAaApprovalDate());
					newLimitProfile.setApprovalDate(stageDate);
				}
				else {
					newLimitProfile.setApprovalDate(null);
				}

				IBookingLocation orgLocation = null;
				if (newLimitProfile.getOriginatingLocation() != null) {
					orgLocation = newLimitProfile.getOriginatingLocation();
				}
				else {
					orgLocation = new OBBookingLocation();
				}
				if (!(aForm.getBookingCty().equals(null) || aForm.getBookingCty().equals(""))
						|| !(aForm.getBookingOrg().equals(null) || aForm.getBookingOrg().equals(""))) {
					orgLocation.setCountryCode(aForm.getBookingCty());
					orgLocation.setOrganisationCode(aForm.getBookingOrg());
					newLimitProfile.setOriginatingLocation(orgLocation);
				}
				else {
					newLimitProfile.setOriginatingLocation(null);
				}

				if (!AbstractCommonMapper.isEmptyOrNull(aForm.getInterimReviewDate())) {
					Date stageDate = compareDate(locale, newLimitProfile.getNextInterimReviewDate(), aForm
							.getInterimReviewDate());
					newLimitProfile.setNextInterimReviewDate(stageDate);
				}
				else {
					newLimitProfile.setNextInterimReviewDate(null);
				}

				if (!AbstractCommonMapper.isEmptyOrNull(aForm.getAnnualReviewDate())) {
					Date stageDate = compareDate(locale, newLimitProfile.getNextAnnualReviewDate(), aForm
							.getAnnualReviewDate());
					newLimitProfile.setNextAnnualReviewDate(stageDate);
				}
				else {
					newLimitProfile.setNextAnnualReviewDate(null);
				}
				this.addUdfToOb(aForm, newLimitProfile);
				return newLimitProfile;

			}
			else if ("view_edit_cam_image".equals(event)||"view_edit_cam_image_reject".equals(event)) {

				String preEvent = (String) map.get("preEvent");
				OBLimitProfileTrxValue oldTrxValue = (OBLimitProfileTrxValue) map.get("limitProfileTrxVal");

				// copy all old values from ORIGINAL value int newBusinessValue.
				OBLimitProfile newLimitProfile = new OBLimitProfile();				


				// todo
				newLimitProfile.setCustomerID(Long.parseLong(aForm.getLeID()));
				newLimitProfile.setLEReference(aForm.getLeReference());
				newLimitProfile.setCamType(aForm.getCamType());
				newLimitProfile.setBCAReference(aForm.getAaNum());
				newLimitProfile.setSourceID(aForm.getAaSource());
				newLimitProfile.setAAType(aForm.getAaType());

				if (!AbstractCommonMapper.isEmptyOrNull(aForm.getAaApprovalDate())) {
					Date stageDate = compareDate(locale, newLimitProfile.getApprovalDate(), aForm
							.getAaApprovalDate());
					newLimitProfile.setApprovalDate(stageDate);
				}
				else {
					newLimitProfile.setApprovalDate(null);
				}

				/*IBookingLocation orgLocation = newLimitProfile.getOriginatingLocation();
				orgLocation.setCountryCode(aForm.getBookingCty());
				orgLocation.setOrganisationCode(aForm.getBookingOrg());
				newLimitProfile.setOriginatingLocation(orgLocation);*/

				if (!AbstractCommonMapper.isEmptyOrNull(aForm.getInterimReviewDate())) {
					Date stageDate = compareDate(locale, newLimitProfile.getNextInterimReviewDate(), aForm
							.getInterimReviewDate());
					newLimitProfile.setNextInterimReviewDate(stageDate);
				}
				else {
					newLimitProfile.setNextInterimReviewDate(null);
				}

				if (!AbstractCommonMapper.isEmptyOrNull(aForm.getAnnualReviewDate())) {
					Date stageDate = compareDate(locale, newLimitProfile.getNextAnnualReviewDate(), aForm
							.getAnnualReviewDate());
					newLimitProfile.setNextAnnualReviewDate(stageDate);
				}
				else {
					newLimitProfile.setNextAnnualReviewDate(null);
				}
				//Shiv
				//newLimitProfile.setCamType(aForm.getCamType());  //CAM Type
				if (!AbstractCommonMapper.isEmptyOrNull(aForm.getCamLoginDate())) {
					Date stageDate = compareDate(locale, newLimitProfile.getCamLoginDate(), aForm
							.getCamLoginDate());
					newLimitProfile.setCamLoginDate(stageDate);
				}
				else {
					newLimitProfile.setCamLoginDate(null);
				}
//				newLimitProfile.setApprovingOfficerGrade(aForm.getApprovingOfficerGrade());  //Risk Grade
				if (!AbstractCommonMapper.isEmptyOrNull(aForm.getExtendedNextReviewDate())) {
					Date stageDate = compareDate(locale, newLimitProfile.getExtendedNextReviewDate(), aForm
							.getExtendedNextReviewDate());
					newLimitProfile.setExtendedNextReviewDate(stageDate);
				}
				else {
					newLimitProfile.setExtendedNextReviewDate(null);
				}
				if(aForm.getNoOfTimesExtended() != null && !aForm.getNoOfTimesExtended().trim().equals("")) {
					newLimitProfile.setNoOfTimesExtended(Long.parseLong(aForm.getNoOfTimesExtended()));  //no. of times extended
				}else{
					newLimitProfile.setNoOfTimesExtended(0);  //no. of times extended
				}
				if(!aForm.getTotalSactionedAmount().equals("")&& !(aForm.getTotalSactionedAmount() == null)) {
					double d = Double.parseDouble(aForm.getTotalSactionedAmount().replaceAll(",", ""));
					newLimitProfile.setTotalSactionedAmount(d); //Tot Sanction amount
				}else{
					newLimitProfile.setTotalSactionedAmount(0);
				}
				
				//Start:Code added for Total Sanctioned Amount For Facility
				if(!aForm.getTotalSanctionedAmountFacLevel().equals("")&& !(aForm.getTotalSanctionedAmountFacLevel() == null)) {
					newLimitProfile.setTotalSanctionedAmountFacLevel(aForm.getTotalSanctionedAmountFacLevel().replaceAll(",", ""));  //Total Sanction amount for Facility
				}else{
					newLimitProfile.setTotalSanctionedAmountFacLevel("0");
				}
				//End :Code added for Total Sanctioned Amount For Facility
				
				newLimitProfile.setRelationshipManager(aForm.getRelationshipManager());  //Relationship Manager
				newLimitProfile.setControllingBranch(aForm.getControllingBranch());  //Controlling Branch
				if(aForm.getCommitteApproval() == null || aForm.getCommitteApproval().equals("")){
					newLimitProfile.setCommitteApproval("N");   //Committee Approval
				}else{
					newLimitProfile.setCommitteApproval("Y");   //Committee Approval
				}
				
				//Start:Code added for Fully Cash Collateral
				if(aForm.getFullyCashCollateral() == null || aForm.getFullyCashCollateral().equals("")){
					newLimitProfile.setFullyCashCollateral("N");   //Fully Cash Collateral
				}else{
					newLimitProfile.setFullyCashCollateral("Y");   //Fully Cash Collateral
				}
				//End  :Code added for Fully Cash Collateral
				if(aForm.getBoardApproval() == null || aForm.getBoardApproval().equals("")){
					newLimitProfile.setBoardApproval("N");  //BoardApproval
				}else{
					newLimitProfile.setBoardApproval("Y");  //BoardApproval
				}
				newLimitProfile.setApproverEmployeeName1(aForm.getCreditApproval1());  //Credit Approval 1
				newLimitProfile.setApproverEmployeeName2(aForm.getCreditApproval2()); //Credit Approval 2
				newLimitProfile.setApproverEmployeeName3(aForm.getCreditApproval3()); //Credit Approval 3
				newLimitProfile.setApproverEmployeeName4(aForm.getCreditApproval4()); //Credit Approval 4
				newLimitProfile.setApproverEmployeeName5(aForm.getCreditApproval5()); //Credit Approval 5
				newLimitProfile.setAssetClassification(aForm.getAssetClassification()); //Assert Classification
				newLimitProfile.setRbiAssetClassification(aForm.getRbiAssetClassification()); //RBI Assert Classification
				newLimitProfile.setDocRemarks(aForm.getDocRemarks()); 
				
				 //Start:Uma Khot:CRI Field addition enhancement
				newLimitProfile.setIsSpecialApprGridForCustBelowHDB8(aForm.getIsSpecialApprGridForCustBelowHDB8());
				newLimitProfile.setIsSingleBorrowerPrudCeiling(aForm.getIsSingleBorrowerPrudCeiling());
				newLimitProfile.setDetailsOfRbiApprovalForSingle(aForm.getDetailsOfRbiApprovalForSingle());
				newLimitProfile.setIsGroupBorrowerPrudCeiling(aForm.getIsGroupBorrowerPrudCeiling());
				newLimitProfile.setDetailsOfRbiApprovalForGroup(aForm.getDetailsOfRbiApprovalForGroup());
				newLimitProfile.setIsNonCooperativeBorrowers(aForm.getIsNonCooperativeBorrowers());
				newLimitProfile.setIsDirectorAsNonCooperativeForOther(aForm.getIsDirectorAsNonCooperativeForOther());
				newLimitProfile.setNameOfDirectorsAndCompany(aForm.getNameOfDirectorsAndCompany());
				 //End:Uma Khot:CRI Field addition enhancement
				
				newLimitProfile.setRbiApprovalForSingle(aForm.getRbiApprovalForSingle());
				newLimitProfile.setRbiApprovalForGroup(aForm.getRbiApprovalForGroup());
				
				if (!AbstractCommonMapper.isEmptyOrNull(aForm.getAaApprovalDate())) {
					Date stageDate = compareDate(locale, newLimitProfile.getApprovalDate(), aForm.getAaApprovalDate());
					newLimitProfile.setApprovalDate(stageDate);
				}
				else {
					newLimitProfile.setApprovalDate(null);
				}
				this.addUdfToOb(aForm, newLimitProfile);
				return newLimitProfile;
			

			}
		}
		catch (Exception e) {
			DefaultLogger.error(this, "error in SRPGlobalMapper is" + e);
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * This method is used to map data from OB to the form and to return the
	 * form.
	 * 
	 * @param cForm is of type CommonForm
	 * @param obj is of type Object
	 * @return Object
	 */

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
		DefaultLogger.debug(this, "inside mapOb to form ");
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		ITeam team = (ITeam) (map.get(IGlobalConstant.USER_TEAM));
		String type = ICMSConstant.AA_TYPE_BANK;
		long teamTypeID = team.getTeamType().getTeamTypeID();
		if (teamTypeID == ICMSConstant.TEAM_TYPE_MR) {
			type = ICMSConstant.AA_TYPE_TRADE;
		}

		try {

			AADetailForm aForm = (AADetailForm) cForm;
			if (obj != null) {
				ILimitProfile sr = (ILimitProfile) obj;
				if (!sr.getCMSCreateInd()) {
					type = ICMSConstant.BATCH_INPUT_TRX_TYPE;
				}

				aForm.setAaNum(sr.getBCAReference());
				aForm.setAaSource(sr.getSourceID());
				aForm.setAaType(sr.getAAType());
				aForm.setAaApprovalDate(DateUtil.formatDate(locale, sr.getApprovalDate()));
				//Added by Pramod Katkar for New Filed CR on 20-08-2013
				aForm.setRamRating(sr.getRamRating());
				aForm.setRamRatingType(sr.getRamRatingType());
				aForm.setRamRatingYear(sr.getRamRatingYear());
				//End by Pramod Katkar
				//Shiv
				
				 //Start:Uma Khot:CRI Field addition enhancement
				aForm.setIsSpecialApprGridForCustBelowHDB8(sr.getIsSpecialApprGridForCustBelowHDB8());
				aForm.setIsSingleBorrowerPrudCeiling(sr.getIsSingleBorrowerPrudCeiling());
				aForm.setDetailsOfRbiApprovalForSingle(sr.getDetailsOfRbiApprovalForSingle());
				aForm.setIsGroupBorrowerPrudCeiling(sr.getIsGroupBorrowerPrudCeiling());
				aForm.setDetailsOfRbiApprovalForGroup(sr.getDetailsOfRbiApprovalForGroup());
				aForm.setIsNonCooperativeBorrowers(sr.getIsNonCooperativeBorrowers());
				aForm.setIsDirectorAsNonCooperativeForOther(sr.getIsDirectorAsNonCooperativeForOther());
				aForm.setNameOfDirectorsAndCompany(sr.getNameOfDirectorsAndCompany());
				 //End:Uma Khot:CRI Field addition enhancement
				
				aForm.setRbiApprovalForSingle(sr.getRbiApprovalForSingle());
				aForm.setRbiApprovalForGroup(sr.getRbiApprovalForGroup());
				
				 	aForm.setCamType(sr.getCamType());
					aForm.setCamLoginDate(DateUtil.formatDate(locale,sr.getCamLoginDate()));
					aForm.setAnnualReviewDate(DateUtil.formatDate(locale,sr.getNextAnnualReviewDate()));
					aForm.setExtendedNextReviewDate(DateUtil.formatDate(locale,sr.getExtendedNextReviewDate()));
					//aForm.setApprovingOfficerGrade(sr.getApprovingOfficerGrade());
				    aForm.setTotalSactionedAmount(CurrencyManager.convertToString(locale,convertToAmount(new BigDecimal(sr.getTotalSactionedAmount()))));
				    
					//Uma Khot:Added for Valid Rating CR
				    if(null!=sr.getRamRatingFinalizationDate() || !"".equals(sr.getRamRatingFinalizationDate()))
				    aForm.setRamRatingFinalizationDate(DateUtil.formatDate(locale,sr.getRamRatingFinalizationDate()));
				    
				    //Start:code added for Total Sanctioned Amount for Facility
				    if(sr.getTotalSanctionedAmountFacLevel() == null){
				    	sr.setTotalSanctionedAmountFacLevel("0");		
				    }	
				    aForm.setTotalSanctionedAmountFacLevel(CurrencyManager.convertToString(locale,convertToAmount(new BigDecimal(sr.getTotalSanctionedAmountFacLevel()))));
				    //End  :code added for Total Sanctioned Amount for Facility
				    
				    aForm.setNoOfTimesExtended(String.valueOf(sr.getNoOfTimesExtended()));
				    aForm.setRelationshipManager(sr.getRelationshipManager());
				    aForm.setControllingBranch(sr.getControllingBranch());
				    
				    if(sr.getCommitteApproval()!=null && sr.getCommitteApproval().equals("Y")){
				    	aForm.setCommitteApproval("on");
				    }else{
				    	aForm.setCommitteApproval("");
				    }
				    if(sr.getBoardApproval()!=null && sr.getBoardApproval().equals("Y")){
				    	aForm.setBoardApproval("on");
				    }else{
				    	aForm.setBoardApproval("");
				    }
				    
				    //Start:Code added for Fully Cash Collateral
				    if(sr.getFullyCashCollateral()!=null&&sr.getFullyCashCollateral().equals("Y")){
				    	aForm.setFullyCashCollateral("on");
				    }else{
				    	aForm.setFullyCashCollateral("");
				    }
				    //End  :Code added for Fully Cash Collateral
				    
				    
				    aForm.setCreditApproval1(sr.getApproverEmployeeName1());
				    aForm.setCreditApproval2(sr.getApproverEmployeeName2());
				    aForm.setCreditApproval3(sr.getApproverEmployeeName3());
				    aForm.setCreditApproval4(sr.getApproverEmployeeName4());
				    aForm.setCreditApproval5(sr.getApproverEmployeeName5());
				    //aForm.setCreditApproval6(sr.getApproverEmployeeName6());
				    aForm.setAssetClassification(sr.getAssetClassification());
				    aForm.setRbiAssetClassification(sr.getRbiAssetClassification());
				    aForm.setDocRemarks(sr.getDocRemarks());
				if (sr.getOriginatingLocation() != null) {
					IBookingLocation orgLocation = sr.getOriginatingLocation();
					aForm.setBookingCty(orgLocation.getCountryCode());
					String bookingCtyDesc = CountryList.getInstance().getCountryName(orgLocation.getCountryCode());
					aForm.setBookingCtyDesc(bookingCtyDesc);

					aForm.setBookingOrg(orgLocation.getOrganisationCode());
					String bookingOrgDesc = CommonCodeList.getInstance(orgLocation.getCountryCode(),
							ICMSConstant.CATEGORY_CODE_BKGLOC, true).getCommonCodeLabel(
							orgLocation.getOrganisationCode());
					bookingOrgDesc = bookingOrgDesc + " (" + orgLocation.getOrganisationCode() + ")";
					aForm.setBookingOrgDesc(bookingOrgDesc);

					String sourceIdDesc = CommonCodeList.getInstance(orgLocation.getCountryCode(), null,
							ICMSUIConstant.AA_SOURCE_CODE, true, type).getCommonCodeLabel(sr.getSourceID());
					aForm.setAaSourceDesc(sourceIdDesc);

				}
				else {
					aForm.setBookingCty(null);
					aForm.setBookingCtyDesc(null);
					aForm.setBookingOrg(null);
					aForm.setBookingOrgDesc(null);
					aForm.setAaSourceDesc(null);
				}

				this.addUdfToForm(aForm, sr);
				aForm.setInterimReviewDate(DateUtil.formatDate(locale, sr.getNextInterimReviewDate()));
				aForm.setAnnualReviewDate(DateUtil.formatDate(locale, sr.getNextAnnualReviewDate()));

				DefaultLogger.debug(this, "Before putting vector result");
			}
			else {

				aForm.setAaNum(null);
				aForm.setAaSource(null);
				aForm.setAaType(null);
				aForm.setAaApprovalDate(null);
				aForm.setBookingCty(null);
				aForm.setBookingCtyDesc(null);
				aForm.setBookingOrg(null);
				aForm.setBookingOrgDesc(null);
				aForm.setInterimReviewDate(null);
				aForm.setAnnualReviewDate(null);

			}
			DefaultLogger.debug(this, "Going out of mapOb to form ");
			return aForm;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "error in AADetailMapper is" + e);
		}
		return null;
	}

	public static Date compareDate(Locale locale, Date dateOrigin, String dateStage) {
		Date returnDate = DateUtil.convertDate(locale, dateStage);

		if (dateOrigin != null) {
			String originalDate = DateUtil.formatDate(locale, dateOrigin);
			if (originalDate.equals(dateStage)) {
				returnDate = dateOrigin;
			}
		}
		return returnDate;
	}

	private void  addUdfToOb(AADetailForm form, ILimitProfile ob) {
		ILimitProfileUdf udf = new OBLimitProfileUdf();
		udf.setId(form.getUdfId());
        udf.setUdf1(form.getUdf1());
        udf.setUdf2(form.getUdf2());
        udf.setUdf3(form.getUdf3());
        udf.setUdf4(form.getUdf4());
        udf.setUdf5(form.getUdf5());
        udf.setUdf6(form.getUdf6());
        udf.setUdf7(form.getUdf7());
        udf.setUdf8(form.getUdf8());
        udf.setUdf9(form.getUdf9());
        udf.setUdf10(form.getUdf10());
        udf.setUdf11(form.getUdf11());
        udf.setUdf12(form.getUdf12());
        udf.setUdf13(form.getUdf13());
        udf.setUdf14(form.getUdf14());
        udf.setUdf15(form.getUdf15());
        udf.setUdf16(form.getUdf16());
        udf.setUdf17(form.getUdf17());
        udf.setUdf18(form.getUdf18());
        udf.setUdf19(form.getUdf19());
        udf.setUdf20(form.getUdf20());
        udf.setUdf21(form.getUdf21());
        udf.setUdf22(form.getUdf22());
        udf.setUdf23(form.getUdf23());
        udf.setUdf24(form.getUdf24());
        udf.setUdf25(form.getUdf25());
        udf.setUdf26(form.getUdf26());
        udf.setUdf27(form.getUdf27());
        udf.setUdf28(form.getUdf28());
        udf.setUdf29(form.getUdf29());
        udf.setUdf30(form.getUdf30());
        udf.setUdf31(form.getUdf31());
        udf.setUdf32(form.getUdf32());
        udf.setUdf33(form.getUdf33());
        udf.setUdf34(form.getUdf34());
        udf.setUdf35(form.getUdf35());
        udf.setUdf36(form.getUdf36());
        udf.setUdf37(form.getUdf37());
        udf.setUdf38(form.getUdf38());
        udf.setUdf39(form.getUdf39());
        udf.setUdf40(form.getUdf40());
        udf.setUdf41(form.getUdf41());
        udf.setUdf42(form.getUdf42());
        udf.setUdf43(form.getUdf43());
        udf.setUdf44(form.getUdf44());
        udf.setUdf45(form.getUdf45());
        udf.setUdf46(form.getUdf46());
        udf.setUdf47(form.getUdf47());
        udf.setUdf48(form.getUdf48());
        udf.setUdf49(form.getUdf49());
        udf.setUdf50(form.getUdf50());
    	udf.setUdf51(form.getUdf51());
        udf.setUdf52(form.getUdf52());
        udf.setUdf53(form.getUdf53());
        udf.setUdf54(form.getUdf54());
        udf.setUdf55(form.getUdf55());
        udf.setUdf56(form.getUdf56());
        udf.setUdf57(form.getUdf57());
        udf.setUdf58(form.getUdf58());
        udf.setUdf59(form.getUdf59());
        udf.setUdf60(form.getUdf60());
        udf.setUdf61(form.getUdf61());
        udf.setUdf62(form.getUdf62());
        udf.setUdf63(form.getUdf63());
        udf.setUdf64(form.getUdf64());
        udf.setUdf65(form.getUdf65());
        udf.setUdf66(form.getUdf66());
        udf.setUdf67(form.getUdf67());
        udf.setUdf68(form.getUdf68());
        udf.setUdf69(form.getUdf69());
        udf.setUdf70(form.getUdf70());
        udf.setUdf71(form.getUdf71());
        udf.setUdf72(form.getUdf72());
        udf.setUdf73(form.getUdf73());
        udf.setUdf74(form.getUdf74());
        udf.setUdf75(form.getUdf75());
        udf.setUdf76(form.getUdf76());
        udf.setUdf77(form.getUdf77());
        udf.setUdf78(form.getUdf78());
        udf.setUdf79(form.getUdf79());
        udf.setUdf80(form.getUdf80());
        udf.setUdf81(form.getUdf81());
        udf.setUdf82(form.getUdf82());
        udf.setUdf83(form.getUdf83());
        udf.setUdf84(form.getUdf84());
        udf.setUdf85(form.getUdf85());
        udf.setUdf86(form.getUdf86());
        udf.setUdf87(form.getUdf87());
        udf.setUdf88(form.getUdf88());
        udf.setUdf89(form.getUdf89());
        udf.setUdf90(form.getUdf90());
        udf.setUdf91(form.getUdf91());
        udf.setUdf92(form.getUdf92());
        udf.setUdf93(form.getUdf93());
        udf.setUdf94(form.getUdf94());
        udf.setUdf95(form.getUdf95());
        udf.setUdf96(form.getUdf96());
        udf.setUdf97(form.getUdf97());
        udf.setUdf98(form.getUdf98());
        udf.setUdf99(form.getUdf99());
        udf.setUdf100(form.getUdf100());
        ILimitProfileUdf udfList[] = new ILimitProfileUdf[1];
        udfList[0] = udf;
        ob.setUdfData(udfList);
	}
	
	private void  addUdfToForm(AADetailForm form, ILimitProfile ob) {
		if (ob != null && ob.getUdfData() != null && ob.getUdfData().length > 0) {
			ILimitProfileUdf udf = ob.getUdfData()[0];	
				form.setUdf1(udf.getUdf1());
	            form.setUdf2(udf.getUdf2());
	            form.setUdf3(udf.getUdf3());
	            form.setUdf4(udf.getUdf4());
	            form.setUdf5(udf.getUdf5());
	            form.setUdf6(udf.getUdf6());
	            form.setUdf7(udf.getUdf7());
	            form.setUdf8(udf.getUdf8());
	            form.setUdf9(udf.getUdf9());
	            form.setUdf10(udf.getUdf10());
	            form.setUdf11(udf.getUdf11());
	            form.setUdf12(udf.getUdf12());
	            form.setUdf13(udf.getUdf13());
	            form.setUdf14(udf.getUdf14());
	            form.setUdf15(udf.getUdf15());
	            form.setUdf16(udf.getUdf16());
	            form.setUdf17(udf.getUdf17());
	            form.setUdf18(udf.getUdf18());
	            form.setUdf19(udf.getUdf19());
	            form.setUdf20(udf.getUdf20());
	            form.setUdf21(udf.getUdf21());
	            form.setUdf22(udf.getUdf22());
	            form.setUdf23(udf.getUdf23());
	            form.setUdf24(udf.getUdf24());
	            form.setUdf25(udf.getUdf25());
	            form.setUdf26(udf.getUdf26());
	            form.setUdf27(udf.getUdf27());
	            form.setUdf28(udf.getUdf28());
	            form.setUdf29(udf.getUdf29());
	            form.setUdf30(udf.getUdf30());
	            form.setUdf31(udf.getUdf31());
	            form.setUdf32(udf.getUdf32());
	            form.setUdf33(udf.getUdf33());
	            form.setUdf34(udf.getUdf34());
	            form.setUdf35(udf.getUdf35());
	            form.setUdf36(udf.getUdf36());
	            form.setUdf37(udf.getUdf37());
	            form.setUdf38(udf.getUdf38());
	            form.setUdf39(udf.getUdf39());
	            form.setUdf40(udf.getUdf40());
	            form.setUdf41(udf.getUdf41());
	            form.setUdf42(udf.getUdf42());
	            form.setUdf43(udf.getUdf43());
	            form.setUdf44(udf.getUdf44());
	            form.setUdf45(udf.getUdf45());
	            form.setUdf46(udf.getUdf46());
	            form.setUdf47(udf.getUdf47());
	            form.setUdf48(udf.getUdf48());
	            form.setUdf49(udf.getUdf49());
	            form.setUdf50(udf.getUdf50());
			    form.setUdf51(udf.getUdf51());
	            form.setUdf52(udf.getUdf52());
	            form.setUdf53(udf.getUdf53());
	            form.setUdf54(udf.getUdf54());
	            form.setUdf55(udf.getUdf55());
	            form.setUdf56(udf.getUdf56());
	            form.setUdf57(udf.getUdf57());
	            form.setUdf58(udf.getUdf58());
	            form.setUdf59(udf.getUdf59());
	            form.setUdf60(udf.getUdf60());
	            form.setUdf61(udf.getUdf61());
	            form.setUdf62(udf.getUdf62());
	            form.setUdf63(udf.getUdf63());
	            form.setUdf64(udf.getUdf64());
	            form.setUdf65(udf.getUdf65());
	            form.setUdf66(udf.getUdf66());
	            form.setUdf67(udf.getUdf67());
	            form.setUdf68(udf.getUdf68());
	            form.setUdf69(udf.getUdf69());
	            form.setUdf70(udf.getUdf70());
	            form.setUdf71(udf.getUdf71());
	            form.setUdf72(udf.getUdf72());
	            form.setUdf73(udf.getUdf73());
	            form.setUdf74(udf.getUdf74());
	            form.setUdf75(udf.getUdf75());
	            form.setUdf76(udf.getUdf76());
	            form.setUdf77(udf.getUdf77());
	            form.setUdf78(udf.getUdf78());
	            form.setUdf79(udf.getUdf79());
	            form.setUdf80(udf.getUdf80());
	            form.setUdf81(udf.getUdf81());
	            form.setUdf82(udf.getUdf82());
	            form.setUdf83(udf.getUdf83());
	            form.setUdf84(udf.getUdf84());
	            form.setUdf85(udf.getUdf85());
	            form.setUdf86(udf.getUdf86());
	            form.setUdf87(udf.getUdf87());
	            form.setUdf88(udf.getUdf88());
	            form.setUdf89(udf.getUdf89());
	            form.setUdf90(udf.getUdf90());
	            form.setUdf91(udf.getUdf91());
	            form.setUdf92(udf.getUdf92());
	            form.setUdf93(udf.getUdf93());
	            form.setUdf94(udf.getUdf94());
	            form.setUdf95(udf.getUdf95());
	            form.setUdf96(udf.getUdf96());
	            form.setUdf97(udf.getUdf97());
	            form.setUdf98(udf.getUdf98());
	            form.setUdf99(udf.getUdf99());
	            form.setUdf100(udf.getUdf100());
	            form.setUdfId(udf.getId());
		}
	}
	
	public Amount convertToAmount(long val){
		Amount amt = new Amount(val,BaseCurrency.getCurrencyCode());
		return amt;
	}
	
	public Amount convertToAmount(BigDecimal val){
		Amount amt = new Amount(val,BaseCurrency.getCurrencyCode());
		return amt;
	}
}
