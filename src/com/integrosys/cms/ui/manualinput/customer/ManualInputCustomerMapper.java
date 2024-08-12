package com.integrosys.cms.ui.manualinput.customer;

import static com.integrosys.cms.app.common.constant.ICMSConstant.NO;
import static com.integrosys.cms.app.common.constant.ICMSConstant.YES;
import static com.integrosys.cms.ui.manualinput.IManualInputConstants.SESSION_CO_BORROWER_DETAILS_KEY;
import static com.integrosys.cms.ui.manualinput.IManualInputConstants.SESSION_CUSTOMER;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.springframework.util.CollectionUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.customer.ILegalEntity;
import com.integrosys.base.businfra.customer.OBLegalEntity;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.CustomerDAO;
import com.integrosys.cms.app.customer.bus.IBankingMethod;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICMSCustomerUdf;
import com.integrosys.cms.app.customer.bus.ICMSLegalEntity;
import com.integrosys.cms.app.customer.bus.ICoBorrowerDetails;
import com.integrosys.cms.app.customer.bus.IContact;
import com.integrosys.cms.app.customer.bus.ICriFac;
import com.integrosys.cms.app.customer.bus.ICriInfo;
import com.integrosys.cms.app.customer.bus.IDirector;
import com.integrosys.cms.app.customer.bus.IISICCode;
import com.integrosys.cms.app.customer.bus.ISubline;
import com.integrosys.cms.app.customer.bus.ISystem;
import com.integrosys.cms.app.customer.bus.IVendor;
import com.integrosys.cms.app.customer.bus.OBBankingMethod;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.customer.bus.OBCMSCustomerUdf;
import com.integrosys.cms.app.customer.bus.OBCMSLegalEntity;
import com.integrosys.cms.app.customer.bus.OBContact;
import com.integrosys.cms.app.customer.bus.OBCriFac;
import com.integrosys.cms.app.customer.bus.OBCriInfo;
import com.integrosys.cms.app.customer.bus.OBDirector;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.region.bus.IRegionDAO;
import com.integrosys.cms.app.relationshipmgr.bus.IRelationshipMgrDAO;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.customer.CategoryCodeConstant;
import com.integrosys.cms.ui.relationshipmgr.IRelationshipMgr;
import com.integrosys.component.commondata.app.CommonDataSingleton;
/**
 * class added by
 * 
 * @author sandiip.shinde
 * 
 */

public class ManualInputCustomerMapper extends AbstractCommonMapper {

	public ManualInputCustomerMapper() {
	}

	public Object mapFormToOB(CommonForm cForm, HashMap inputs)
			throws MapperException {
		// cmsLegalEntity MainProfile

		DefaultLogger.debug(this,
				"Entering method mapFormToOB : ManualInputCustomerMapper");
		ManualInputCustomerInfoForm form = (ManualInputCustomerInfoForm) cForm;
		ICMSCustomer customerInfo = new OBCMSCustomer();
		ICMSLegalEntity cmsLegalEntity = new OBCMSLegalEntity();
		ICMSCustomerUdf cmsUdf = new OBCMSCustomerUdf();
		ILegalEntity legalEntity = new OBLegalEntity();
		IContact officialAddressContact = new OBContact();
		IContact registerAddressContact = new OBContact();
		IContact RegOfficialAddressContact = new OBContact();
		ISystem[] otherSystem = new ISystem[50];
		IVendor[] vendorDtls = new IVendor[50];
		IBankingMethod[] banking = new IBankingMethod[10];
		IContact[] address = { registerAddressContact, officialAddressContact ,RegOfficialAddressContact };
		/*
		 * Will be used when 2 tables will be used for officeial address and
		 * registered address
		 */
		/*
		 * IContact[] officialAddressContactArr = {officialAddressContact};
		 * IContact[] registerAddressContactArr = {registerAddressContact};
		 */
        IDirector[] dir = new OBDirector[10];
        IDirector director = new OBDirector();
        if(form.getDirectorCountry()!=null && !"".equals(form.getDirectorCountry()))
        {
        director.setDirectorCountry(form.getDirectorCountry());
        }
        if(form.getDirectorRegion()!=null && !"".equals(form.getDirectorRegion()))
        {
        director.setDirectorRegion(form.getDirectorRegion());
        }
        if(form.getDirectorCity()!=null && !"".equals(form.getDirectorCity()))
        {
        director.setDirectorCity(form.getDirectorCity());
        }
        if(form.getDirectorState()!=null && !"".equals(form.getDirectorState()))
        {
        director.setDirectorState(form.getDirectorState());
        }
        

      //Tighteing property CR
        if(form.getDirectorPostCode()!=null && !"".equals(form.getDirectorPostCode()))
        {
        director.setDirectorPostCode(form.getDirectorPostCode());
        }     
       //Tighteing property CR
        
        dir[0] = director;
        
        
        
        
        
		String nodalLead = form.getNodalLead();
		String leadVal = form.getLeadValue();
		IBankingMethod bank = new OBBankingMethod();
		bank.setNodal(nodalLead);
		bank.setLead(leadVal);
		banking[0] = bank;
		
		try{

		customerInfo.setDomicileCountry(form.getDomicileCountry());
		customerInfo.setCustomerName(form.getCustomerNameShort());
		
		// customerInfo.setCustomerReference(form.getCifId());
		customerInfo.setCifId(form.getCifId());
		if( !(form.getCustomerNameShort()== null || form.getCustomerNameShort().equals("")))
		{
			customerInfo.setCustomerNameUpper(form.getCustomerNameShort()
					.toUpperCase());
		}
        customerInfo.setMainBranch(form.getMainBranch());
        customerInfo.setBranchCode(form.getBranchCode());
		customerInfo.setCycle(form.getCycle());
		customerInfo.setPartyGroupName(form.getPartyGroupName());
		
		customerInfo.setRelationshipMgrEmpCode(form.getRelationshipMgrEmpCode());
		
		
		IRelationshipMgrDAO relationshipMgrDAOImpl = (IRelationshipMgrDAO)BeanHouse.get("relationshipMgrDAO");
		if(null != form.getRelationshipMgr() && !"".equals(form.getRelationshipMgr())) {
			IRelationshipMgr relationshipMgr = relationshipMgrDAOImpl.getRelationshipMgrByName(form.getRelationshipMgr(),form.getRelationshipMgrEmpCode());
			if(relationshipMgr != null) {
			customerInfo.setRelationshipMgr(relationshipMgr.getId()+"");
			}else {
				customerInfo.setRelationshipMgr("");
			}
		}
		if(null != form.getRmRegion() && !"".equals(form.getRmRegion())) {
			IRegion region = relationshipMgrDAOImpl.getRegionByRegionName(form.getRmRegion());
			if(region != null) {
				customerInfo.setRmRegion(region.getIdRegion()+"");
			}else {
				customerInfo.setRmRegion("");
			}
		}		
		
		customerInfo.setEntity(form.getEntity());
		customerInfo.setAadharNumber(form.getAadharNumber());
		customerInfo.setCinLlpin(form.getCinLlpin());
		customerInfo.setPartyNameAsPerPan(form.getPartyNameAsPerPan());
		customerInfo.setRbiIndustryCode(form.getRbiIndustryCode());
		customerInfo.setIndustryName(form.getIndustryName());
		customerInfo.setPan(form.getPan());
		customerInfo.setListedCompany(form.getListedCompany()); 
		customerInfo.setIsinNo(form.getIsinNo()); 
		customerInfo.setRaroc(form.getRaroc()); 
		//customerInfo.setRaraocPeriod(form.getRaraocPeriod()); 
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MMM/yyyy");
		if (!isEmptyOrNull(form.getRaraocPeriod()))
		{customerInfo.setRaraocPeriod(sdf1.parse(form
				.getRaraocPeriod()));
		
		}
		ActionErrors errors = new ActionErrors();
		Calendar currentDate = Calendar.getInstance();
        int  year = currentDate.get(Calendar.YEAR);
		String dt= form.getYearEndPeriod()+"-"+String.valueOf(year);
		if (Validator.checkDate(dt, true, Locale.getDefault()).equals(Validator.ERROR_NONE) || dt.contains("/")) {				
		if(form.getYearEndPeriod() != null && !"".equals(form.getYearEndPeriod())){
		String yearEndPeriodValue = form.getYearEndPeriod();
		String value1 = "";
		String value2 = "";
		String value3 = "";
		String[] yearEndPeriodSplit = yearEndPeriodValue.split("-");
		if(yearEndPeriodSplit[0].length() == 1) {
		 value1 = "0" + yearEndPeriodSplit[0];
		}else {
			 value1 = yearEndPeriodSplit[0];
		}
		
		if(yearEndPeriodSplit[1].length() == 3) {
			 value2 =  yearEndPeriodSplit[1].toUpperCase();
			 value3 = value1+"-"+value2;
			form.setYearEndPeriod(value3);
		}
		}
		}else {
			errors.add("yearEndPeriodFormatError", new ActionMessage("error.yearEndFormat.invalid.format"));
		}
		customerInfo.setYearEndPeriod(form.getYearEndPeriod()); 
		customerInfo.setCreditMgrEmpId(form.getCreditMgrEmpId());
		customerInfo.setPfLrdCreditMgrEmpId(form.getPfLrdCreditMgrEmpId());
		customerInfo.setCreditMgrSegment(form.getCreditMgrSegment());

		customerInfo.setConsBankFundBasedHdfcBankPer(form.getConsBankFundBasedHdfcBankPer());
		customerInfo.setConsBankFundBasedLeadBankPer(form.getConsBankFundBasedLeadBankPer());
		customerInfo.setConsBankNonFundBasedHdfcBankPer(form.getConsBankNonFundBasedHdfcBankPer());
		customerInfo.setConsBankNonFundBasedLeadBankPer(form.getConsBankNonFundBasedLeadBankPer());
		
		customerInfo.setMultBankFundBasedHdfcBankPer(form.getMultBankFundBasedHdfcBankPer());
		customerInfo.setMultBankFundBasedLeadBankPer(form.getMultBankFundBasedLeadBankPer());
		customerInfo.setMultBankNonFundBasedHdfcBankPer(form.getMultBankNonFundBasedHdfcBankPer());
		customerInfo.setMultBankNonFundBasedLeadBankPer(form.getMultBankNonFundBasedLeadBankPer());
		
		customerInfo.setRevisedEmailIdsArrayList(form.getRevisedEmailIdsArray());
		
		ArrayList arrs = new ArrayList();
		
		if(form.getRevisedEmailIdsArray() != null) {
			for(int i=0;i<form.getRevisedEmailIdsArray().length - 1;i++) {
				arrs.add(form.getRevisedEmailIdsArray()[i].toString());
			}
		}
		
		form.setRevisedArrayListN(arrs);
		customerInfo.setLeiCode(form.getLeiCode());
		//customerInfo.setRegion(form.getRegion());
		// customerInfo.setCountry(form.getCountry);
		// customerInfo.setState(form.getState);
		// customerInfo.setState(form.getState);
		// customerInfo.setTelephoneNo(form.getTelephoneNo);
		customerInfo.setSubLine(form.getSubLine());
		customerInfo.setBankingMethod(form.getBankingMethod());
		customerInfo.setFinalBankMethodList(form.getFinalBankMethodList());
		//form.setBankingMethod(customerInfo.getBankingMethod());
		
		customerInfo.setExceptionalCases(form.getExceptionalCases());
	/*	customerInfo.setTotalFundedLimit(form.getTotalFundedLimit());
		customerInfo.setTotalNonFundedLimit(form.getTotalNonFundedLimit()); */
		customerInfo.setFundedSharePercent(form.getFundedSharePercent());
		customerInfo.setNonFundedSharePercent(form.getNonFundedSharePercent());

//		customerInfo.setMemoExposure(form.getMemoExposure());
		
		//Uma Khot:Cam upload and Dp field calculation CR
		customerInfo.setDpSharePercent(form.getDpSharePercent());
		
		//Phase 3 CR:input field to be comma seperated
		String funded=form.getTotalFundedLimit();
		funded=UIUtil.removeComma(funded);
		String nonFunded=form.getTotalNonFundedLimit();
		nonFunded=UIUtil.removeComma(nonFunded);
		String memoExposure1=form.getMemoExposure();
		memoExposure1=UIUtil.removeComma(memoExposure1);
		
		customerInfo.setTotalFundedLimit(funded);
		customerInfo.setTotalNonFundedLimit(nonFunded);
		customerInfo.setMemoExposure(memoExposure1);
		
		customerInfo.setIsRBIWilfulDefaultersList(form.getIsRBIWilfulDefaultersList());
		customerInfo.setNameOfBank(form.getNameOfBank());
		customerInfo.setIsDirectorMoreThanOne(form.getIsDirectorMoreThanOne());
		customerInfo.setNameOfDirectorsAndCompany(form.getNameOfDirectorsAndCompany());
		customerInfo.setIsBorrDefaulterWithBank(form.getIsBorrDefaulterWithBank());
		customerInfo.setDetailsOfDefault(form.getDetailsOfDefault());
		customerInfo.setExtOfCompromiseAndWriteoff(form.getExtOfCompromiseAndWriteoff());
		customerInfo.setIsCibilStatusClean(form.getIsCibilStatusClean());
		customerInfo.setDetailsOfCleanCibil(form.getDetailsOfCleanCibil());
	
		
		/**
		 * add code for AJAX slow response issue for calculation of sectioned amount
		 * code updated By Sachin patil on 31-Jan-2014.  
		 * */
		
		/** Start code  */
		
		String totalNonFundedLimits = (String) (form.getTotalNonFundedLimit());
		if(totalNonFundedLimits == null || "".equals(totalNonFundedLimits))
		{
			totalNonFundedLimits = "0.00";
		}
		String nonFundedSharePercents = (String) (form.getNonFundedSharePercent());
		if(nonFundedSharePercents == null || "".equals(nonFundedSharePercents))
		{
			nonFundedSharePercents = "0.00";
		}
		String memoExposures = (String) (form.getMemoExposure());
		if(memoExposures == null || "".equals(memoExposures))
		{
			memoExposures = "0.00";
		}
		String totalFundedLimits = (String) (form.getTotalFundedLimit());
		if(totalFundedLimits == null || "".equals(totalFundedLimits))
		{
			totalFundedLimits = "0.00";
		}
		String fundedSharePercents = (String) (form.getFundedSharePercent());
		if(fundedSharePercents == null || "".equals(fundedSharePercents))
		{
			fundedSharePercents = "0.00";
		}
		
	BigDecimal fundedShareLimits =new BigDecimal("0.00");
	BigDecimal nonFundedShareLimits = new BigDecimal("0.00");
	BigDecimal totalSanctionedLimits =new BigDecimal("0.00");
 int roundingMode =  BigDecimal.ROUND_FLOOR;
	BigDecimal totalNonFundedLimit = new BigDecimal(totalNonFundedLimits.replaceAll(",", ""));
	BigDecimal nonFundedSharePercent  =new BigDecimal(nonFundedSharePercents.replaceAll(",", ""));
	BigDecimal memoExposure = new BigDecimal(memoExposures.replaceAll(",", ""));
	BigDecimal totalFundedLimit = new BigDecimal(totalFundedLimits.replaceAll(",", ""));
	BigDecimal fundedSharePercent =  new BigDecimal(fundedSharePercents);
	BigDecimal bigDecimalZero =  new BigDecimal("0.00") ;	
	 BigDecimal hundred = BigDecimal.valueOf(100);
	    BigDecimal percentageFactor = null;
	if(fundedSharePercent != null && !(bigDecimalZero.equals(fundedSharePercent))){
		    BigDecimal percentage = fundedSharePercent;
		    percentageFactor = percentage.divide(hundred, 4,roundingMode);
	fundedShareLimits = totalFundedLimit.multiply(percentageFactor);
	}
	if(nonFundedSharePercent != null && !(bigDecimalZero.equals(nonFundedSharePercent))){
		 BigDecimal percentage = nonFundedSharePercent;
		  percentageFactor = percentage.divide(hundred,4, roundingMode);
    nonFundedShareLimits= totalNonFundedLimit.multiply(percentageFactor);
	}
	totalSanctionedLimits = totalNonFundedLimit.add(memoExposure);
	totalSanctionedLimits = totalSanctionedLimits.add(totalFundedLimit);
	
	customerInfo.setTotalSanctionedLimit(String.valueOf(totalSanctionedLimits));
	customerInfo.setFundedShareLimit(String.valueOf(fundedShareLimits));
	customerInfo.setNonFundedShareLimit(String.valueOf(nonFundedShareLimits));
	
	/*result.put("fundedShareLimit",String.valueOf(fundedShareLimits));
	result.put("nonFundedShareLimit", String.valueOf(nonFundedShareLimits));
	result.put("totalSanctionedLimit", String.valueOf(totalSanctionedLimits));*/
		
	/** End code on 31-01-2014 By Sachin Patil  */
		
	// commented By sachin Patil On 31-Jan-2014
	/*
	 customerInfo.setTotalSanctionedLimit(form.getTotalSanctionedLimit());
	customerInfo.setMpbf(form.getMpbf());
	customerInfo.setFundedShareLimit(form.getFundedShareLimit());
	customerInfo.setNonFundedShareLimit(form.getNonFundedShareLimit());	*/		
	
		customerInfo.setMpbf(UIUtil.removeComma(form.getMpbf()));		
		customerInfo.setStatus(form.getStatus());
		DefaultLogger.debug(this,"----------on 11 May 2012--------------");
		
		if (form.getCIFId() != null && form.getCIFId() != "") {
			customerInfo.setCustomerID(Long.parseLong(form.getCIFId()));
			cmsLegalEntity.setLEID(Long.parseLong(form.getCIFId()));
			legalEntity.setLEID(Long.parseLong(form.getCIFId()));
		}

		legalEntity.setShortName(form.getCustomerNameShort());
		legalEntity.setLegalName(form.getCustomerNameShort());
		legalEntity.setLegalConstitution(form.getLegalConstitution());
		legalEntity.setLegalRegCountry(form.getLegalConstitution());

		cmsLegalEntity.setLegalConstitution(form.getLegalConstitution());
		cmsLegalEntity.setShortName(form.getCustomerNameShort());
		cmsLegalEntity.setLegalName(form.getCustomerNameShort());
		cmsLegalEntity.setMainBranch(form.getMainBranch());
		cmsLegalEntity.setBranchCode(form.getBranchCode());
		cmsLegalEntity.setLEReference(form.getCifId());
		cmsLegalEntity.setSourceID(form.getSource());
		cmsLegalEntity.setLegalConstitution(form.getLegalConstitution());
		cmsLegalEntity.setCountryPR(form.getLegalConstitution());
		cmsLegalEntity.setCustomerSegment(form.getCustomerSegment());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
		try {
			if (!isEmptyOrNull(form.getRelationshipStartDate())) {
				cmsLegalEntity.setRelationshipStartDate(sdf.parse(form
						.getRelationshipStartDate()));
				customerInfo.setCustomerRelationshipStartDate(sdf.parse(form
						.getRelationshipStartDate()));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if (!isEmptyOrNull(form.getLeiExpDate())) {
				cmsLegalEntity.setLeiExpDate(sdf.parse(form.getLeiExpDate()));
				customerInfo.setLeiExpDate(sdf.parse(form.getLeiExpDate()));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cmsLegalEntity.setCycle(form.getCycle());
		cmsLegalEntity.setPartyGroupName(form.getPartyGroupName());
		cmsLegalEntity.setRelationshipMgrEmpCode(form.getRelationshipMgrEmpCode());
		
//		cmsLegalEntity.setRelationshipMgr(form.getRelationshipMgr());
//		cmsLegalEntity.setRmRegion(form.getRmRegion());
		
//		IRelationshipMgrDAO relationshipMgrDAOImpl = (IRelationshipMgrDAO)BeanHouse.get("relationshipMgrDAO");
		if(null != form.getRelationshipMgr() && !"".equals(form.getRelationshipMgr())) {
			IRelationshipMgr relationshipMgr = relationshipMgrDAOImpl.getRelationshipMgrByName(form.getRelationshipMgr(),form.getRelationshipMgrEmpCode());
			if(relationshipMgr != null) {
				cmsLegalEntity.setRelationshipMgr(relationshipMgr.getId()+"");
			}else {
				cmsLegalEntity.setRelationshipMgr("");
			}
		}
		if(null != form.getRmRegion() && !"".equals(form.getRmRegion())) {
			IRegion region = relationshipMgrDAOImpl.getRegionByRegionName(form.getRmRegion());
			if(region != null) {
				cmsLegalEntity.setRmRegion(region.getIdRegion()+"");
			}else {
				cmsLegalEntity.setRmRegion("");
			}
		}
		
		cmsLegalEntity.setEntity(form.getEntity());
		try {
			if (!isEmptyOrNull(form.getDateOfIncorporation())) {
				cmsLegalEntity.setDateOfIncorporation(sdf.parse(form
						.getDateOfIncorporation()));
				customerInfo.setDateOfIncorporation(sdf.parse(form
						.getDateOfIncorporation()));
			}
			
			if (!isEmptyOrNull(form.getRaraocPeriod()))
			{customerInfo.setRaraocPeriod(sdf.parse(form
					.getRaraocPeriod()));
				
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cmsLegalEntity.setAadharNumber(form.getAadharNumber());
		cmsLegalEntity.setListedCompany(form.getListedCompany()); 
		cmsLegalEntity.setIsinNo(form.getIsinNo()); 
		cmsLegalEntity.setRaroc(form.getRaroc()); 
		//customerInfo.setRaraocPeriod(form.getRaraocPeriod()); 
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MMM/yyyy");
		if (!isEmptyOrNull(form.getRaraocPeriod()))
		{cmsLegalEntity.setRaraocPeriod(sdf2.parse(form
				.getRaraocPeriod()));
			
		}
		
		cmsLegalEntity.setYearEndPeriod(form.getYearEndPeriod()); 
		cmsLegalEntity.setCreditMgrEmpId(form.getCreditMgrEmpId());
		cmsLegalEntity.setPfLrdCreditMgrEmpId(form.getPfLrdCreditMgrEmpId());
		cmsLegalEntity.setCreditMgrSegment(form.getCreditMgrSegment());
		
		cmsLegalEntity.setConsBankFundBasedHdfcBankPer(form.getConsBankFundBasedHdfcBankPer());
		cmsLegalEntity.setConsBankFundBasedLeadBankPer(form.getConsBankFundBasedLeadBankPer());
		cmsLegalEntity.setConsBankNonFundBasedHdfcBankPer(form.getConsBankNonFundBasedHdfcBankPer());
		cmsLegalEntity.setConsBankNonFundBasedLeadBankPer(form.getConsBankNonFundBasedLeadBankPer());
		
		cmsLegalEntity.setMultBankFundBasedHdfcBankPer(form.getMultBankFundBasedHdfcBankPer());
		cmsLegalEntity.setMultBankFundBasedLeadBankPer(form.getMultBankFundBasedLeadBankPer());
		cmsLegalEntity.setMultBankNonFundBasedHdfcBankPer(form.getMultBankNonFundBasedHdfcBankPer());
		cmsLegalEntity.setMultBankNonFundBasedLeadBankPer(form.getMultBankNonFundBasedLeadBankPer());
		
		cmsLegalEntity.setCinLlpin(form.getCinLlpin());
		cmsLegalEntity.setPartyNameAsPerPan(form.getPartyNameAsPerPan());
		cmsLegalEntity.setRbiIndustryCode(form.getRbiIndustryCode());
		cmsLegalEntity.setIndustryName(form.getIndustryName());
		cmsLegalEntity.setPan(form.getPan());
		//cmsLegalEntity.setRegion(form.getRegion());
		cmsLegalEntity.setPartyGroupName(form.getPartyGroupName());
		// cmsLegalEntity.setCountry(form.getCountry);
		// cmsLegalEntity.setState(form.getState);
		// cmsLegalEntity.setState(form.getState);
		// cmsLegalEntity.setTelephoneNo(form.getTelephoneNo);
		cmsLegalEntity.setSubLine(form.getSubLine());
		cmsLegalEntity.setBankingMethod(form.getBankingMethod());
		cmsLegalEntity.setFinalBankMethodList(form.getFinalBankMethodList());
		cmsLegalEntity.setExceptionalCases(form.getExceptionalCases());
//		cmsLegalEntity.setTotalFundedLimit(form.getTotalFundedLimit());
//		cmsLegalEntity.setTotalNonFundedLimit(form.getTotalNonFundedLimit());
		
		cmsLegalEntity.setTotalFundedLimit(funded);
		cmsLegalEntity.setTotalNonFundedLimit(nonFunded);
		cmsLegalEntity.setMemoExposure(memoExposure1);
		
		cmsLegalEntity.setFundedSharePercent(form.getFundedSharePercent());
		cmsLegalEntity
				.setNonFundedSharePercent(form.getNonFundedSharePercent());
//		cmsLegalEntity.setMemoExposure(form.getMemoExposure());
//		cmsLegalEntity.setTotalSanctionedLimit(form.getTotalSanctionedLimit());
		
		//Uma Khot:Cam upload and Dp field calculation CR
		cmsLegalEntity.setDpSharePercent(form.getDpSharePercent());
		cmsLegalEntity.setLeiCode(form.getLeiCode());
		//Phase 3 CR;Comma separated
		String totalSanctionedLimit = form.getTotalSanctionedLimit();
		 totalSanctionedLimit = UIUtil.removeComma(totalSanctionedLimit);
		 cmsLegalEntity.setTotalSanctionedLimit(totalSanctionedLimit);
		
		cmsLegalEntity.setMpbf(UIUtil.removeComma(form.getMpbf()));
		
	/*	cmsLegalEntity.setFundedShareLimit(form.getFundedShareLimit());
		cmsLegalEntity.setNonFundedShareLimit(form.getNonFundedShareLimit()); */

		
		customerInfo.setBorrowerDUNSNo(form.getBorrowerDUNSNo());
		customerInfo.setClassActivity1(form.getClassActivity1());
		customerInfo.setClassActivity2(form.getClassActivity2());
		customerInfo.setClassActivity3(form.getClassActivity3());
		customerInfo.setWillfulDefaultStatus(form.getWillfulDefaultStatus());
		customerInfo.setSuitFilledStatus(form.getSuitFilledStatus());
	
		
		customerInfo.setIsRBIWilfulDefaultersList(form.getIsRBIWilfulDefaultersList());
		customerInfo.setNameOfBank(form.getNameOfBank());
		customerInfo.setIsDirectorMoreThanOne(form.getIsDirectorMoreThanOne());
		customerInfo.setNameOfDirectorsAndCompany(form.getNameOfDirectorsAndCompany());
		customerInfo.setIsBorrDefaulterWithBank(form.getIsBorrDefaulterWithBank());
		customerInfo.setDetailsOfDefault(form.getDetailsOfDefault());
		customerInfo.setExtOfCompromiseAndWriteoff(form.getExtOfCompromiseAndWriteoff());
		customerInfo.setIsCibilStatusClean(form.getIsCibilStatusClean());
		customerInfo.setDetailsOfCleanCibil(form.getDetailsOfCleanCibil());
		try {
			if (!isEmptyOrNull(form.getDateOfSuit()) ) {
				customerInfo.setDateOfSuit(sdf.parse(form
						.getDateOfSuit()));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Phase 3 CR:comma separated
		String suitAmount = form.getSuitAmount();
		suitAmount=UIUtil.removeComma(suitAmount);
		customerInfo.setSuitAmount(suitAmount);
		
		//customerInfo.setSuitAmount(form.getSuitAmount());
		customerInfo.setCurrency(form.getCurrency());
		customerInfo.setPartyConsent(form.getPartyConsent());
		customerInfo.setCustomerSegment(form.getCustomerSegment());
		customerInfo.setRegOffice(form.getRegOffice());
		customerInfo.setSuitReferenceNo(form.getSuitReferenceNo());
		try {
			if (!isEmptyOrNull(form.getDateWillfulDefault())) {
				customerInfo.setDateWillfulDefault(sdf.parse(form
						.getDateWillfulDefault()));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		customerInfo.setRegOfficeDUNSNo(form.getRegOfficeDUNSNo());
		
		//Added by Ankit on dt 18-FEB-2016 : PAN VALIDATION WITH NSDL
		customerInfo.setLastModifiedOn(Calendar.getInstance().getTime());
		customerInfo.setPanValGenParamFlagValue(form.getPanValGenParamFlag()!=null?form.getPanValGenParamFlag():'N');
		customerInfo.setForm6061checked((form.getForm6061Checked()!=null && !"".equalsIgnoreCase(form.getForm6061Checked()))?form.getForm6061Checked().charAt(0):'N');
		customerInfo.setIsPanValidated(form.getIsPanValidated()!=null?form.getIsPanValidated():'N');
		//end
		
		//Added by Nilkanth on dt 29-JULY-2022 : LEI VALIDATION WITH CCIL
		customerInfo.setLastModifiedLei(Calendar.getInstance().getTime());
		customerInfo.setLeiValGenParamFlag(form.getLeiValGenParamFlag()!=null?form.getLeiValGenParamFlag():'N');
		customerInfo.setDeferLEI(StringUtils.isBlank(form.getDeferLEI())?ICMSConstant.NO:form.getDeferLEI());
		customerInfo.setIsLeiValidated(form.getIsLeiValidated()!=null?form.getIsLeiValidated():'N');

		cmsLegalEntity.setLastModifiedLei(Calendar.getInstance().getTime());
		cmsLegalEntity.setLeiValGenParamFlag(form.getLeiValGenParamFlag()!=null?form.getLeiValGenParamFlag():'N');
		cmsLegalEntity.setDeferLEI(StringUtils.isBlank(form.getDeferLEI())?ICMSConstant.NO:form.getDeferLEI());
//		cmsLegalEntity.setIsLeiValidated(form.getIsLeiValidated()!=null?form.getIsLeiValidated():'N');
		//end
		
		cmsLegalEntity.setBorrowerDUNSNo(form.getBorrowerDUNSNo());
		cmsLegalEntity.setClassActivity1(form.getClassActivity1());
		cmsLegalEntity.setClassActivity2(form.getClassActivity2());
		cmsLegalEntity.setClassActivity3(form.getClassActivity3());
		cmsLegalEntity.setWillfulDefaultStatus(form.getWillfulDefaultStatus());
		cmsLegalEntity.setSuitFilledStatus(form.getSuitFilledStatus());
		
		cmsLegalEntity.setIsRBIWilfulDefaultersList(form.getIsRBIWilfulDefaultersList());
		cmsLegalEntity.setNameOfBank(form.getNameOfBank());
		cmsLegalEntity.setIsDirectorMoreThanOne(form.getIsDirectorMoreThanOne());
		cmsLegalEntity.setNameOfDirectorsAndCompany(form.getNameOfDirectorsAndCompany());
		cmsLegalEntity.setIsBorrDefaulterWithBank(form.getIsBorrDefaulterWithBank());
		cmsLegalEntity.setDetailsOfDefault(form.getDetailsOfDefault());
		cmsLegalEntity.setExtOfCompromiseAndWriteoff(form.getExtOfCompromiseAndWriteoff());
		cmsLegalEntity.setIsCibilStatusClean(form.getIsCibilStatusClean());
		cmsLegalEntity.setDetailsOfCleanCibil(form.getDetailsOfCleanCibil());
		try {
			if (!isEmptyOrNull(form.getDateOfSuit())) {
				cmsLegalEntity.setDateOfSuit(sdf.parse(form.getDateOfSuit()));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Phase 3 CR:comma separated
		cmsLegalEntity.setSuitAmount(suitAmount);
		
		//cmsLegalEntity.setSuitAmount(form.getSuitAmount());
		cmsLegalEntity.setCurrency(form.getCurrency());
		cmsLegalEntity.setPartyConsent(form.getPartyConsent());
		cmsLegalEntity.setRegOffice(form.getRegOffice());
		cmsLegalEntity.setSuitReferenceNo(form.getSuitReferenceNo());
		try {
			if (!isEmptyOrNull(form.getDateWillfulDefault())) {
				cmsLegalEntity.setDateWillfulDefault(sdf.parse(form.getDateWillfulDefault()));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cmsLegalEntity.setRegOfficeDUNSNo(form.getRegOfficeDUNSNo());
		// cmsLegalEntity.setCustomerSubSegment(form.getCustomerSubSegment());

		/*
		 * commented by bharat if( form.getBlacklisted().equals("No"))
		 * cmsLegalEntity.setBlackListedInd(false); else
		 * cmsLegalEntity.setBlackListedInd(true);
		 */

		/*
		 * otherSystem.setSystem(form.getSystem());
		 * otherSystem.setSystemCustomerId
		 * (Long.parseLong(form.getSystemCustomerId()));
		 */

		registerAddressContact.setContactType("CORPORATE");
		// registerAddressContact.setContactReference(form.getCifId());
		// registerAddressContact.setLraLeId(form.getCifId());
		registerAddressContact.setAddressLine1(form.getAddress1());
		registerAddressContact.setAddressLine2(form.getAddress2());
		registerAddressContact.setAddressLine3(form.getAddress3());
		registerAddressContact.setAddressLine4(form.getAddress4());
		registerAddressContact.setAddressLine5(form.getAddress5());
		registerAddressContact.setTelephoneNumer(form.getTelephoneNo());
		registerAddressContact.setTelex(form.getTelex());
		registerAddressContact.setStdCodeTelex(form.getStdCodeTelex());
		registerAddressContact.setStdCodeTelNo(form.getStdCodeTelNo());
		registerAddressContact.setEmailAddress(form.getEmail());
		registerAddressContact.setCountryCode(form.getCountry());
		registerAddressContact.setState(form.getState());
		registerAddressContact.setRegion(form.getRegion());
		registerAddressContact.setCity(form.getCity());
		registerAddressContact.setPostalCode(form.getPostcode());
		registerAddressContact.setTelephoneNumer(form.getTelephoneNo());
		registerAddressContact.setEmailAddress(form.getEmail());

		officialAddressContact.setContactType("OFFICE");
		// officialAddressContact.setContactReference(form.getCifId());
		// officialAddressContact.setLraLeId(form.getCifId());
		officialAddressContact.setAddressLine1(form.getOfficeAddress1());
		officialAddressContact.setAddressLine2(form.getOfficeAddress2());
		officialAddressContact.setAddressLine3(form.getOfficeAddress3());
		officialAddressContact.setAddressLine4(form.getOfficeAddress4());
		officialAddressContact.setAddressLine5(form.getOfficeAddress5());
		officialAddressContact.setTelephoneNumer(form.getOfficeTelephoneNo());
		officialAddressContact.setTelex(form.getOfficeTelex());
		/*officialAddressContact.setStdCodeTelex(form.getStdCodeTelex());
		officialAddressContact.setStdCodeTelNo(form.getStdCodeTelNo());*/
		officialAddressContact.setEmailAddress(form.getOfficeEmail());
		officialAddressContact.setCountryCode(form.getOfficeCountry());
		officialAddressContact.setState(form.getOfficeState());
		officialAddressContact.setCity(form.getOfficeCity());
		officialAddressContact.setRegion(form.getOfficeRegion());
		officialAddressContact.setPostalCode(form.getOfficePostCode());
		officialAddressContact.setTelephoneNumer(form.getOfficeTelephoneNo());
		officialAddressContact.setEmailAddress(form.getOfficeEmail());
		
		RegOfficialAddressContact.setContactType("REGISTERED");
		// officialAddressContact.setContactReference(form.getCifId());
		// officialAddressContact.setLraLeId(form.getCifId());
		RegOfficialAddressContact.setAddressLine1(form.getRegOfficeAddress1());
		RegOfficialAddressContact.setAddressLine2(form.getRegOfficeAddress2());
		RegOfficialAddressContact.setAddressLine3(form.getRegOfficeAddress3());
		RegOfficialAddressContact.setAddressLine4(form.getRegOfficeAddress4());
		RegOfficialAddressContact.setAddressLine5(form.getRegOfficeAddress5());
		RegOfficialAddressContact.setTelephoneNumer(form.getRegOfficeTelephoneNo());
		RegOfficialAddressContact.setTelex(form.getRegOfficeTelex());
		RegOfficialAddressContact.setEmailAddress(form.getRegOfficeEmail());
		RegOfficialAddressContact.setCountryCode(form.getRegOfficeCountry());
		RegOfficialAddressContact.setStdCodeTelex(form.getStdCodeOfficeTelex());
		RegOfficialAddressContact.setStdCodeTelNo(form.getStdCodeOfficeTelNo());
		RegOfficialAddressContact.setState(form.getRegOfficeState());
		RegOfficialAddressContact.setCity(form.getRegOfficeCity());
		RegOfficialAddressContact.setRegion(form.getRegOfficeRegion());
		RegOfficialAddressContact.setPostalCode(form.getRegOfficePostCode());
		RegOfficialAddressContact.setTelephoneNumer(form.getRegOfficeTelephoneNo());
		RegOfficialAddressContact.setEmailAddress(form.getRegOfficeEmail());
		
		//Add by Shiv 200811
		ICriInfo criInfo = new OBCriInfo();

	    criInfo.setCustomerRamID(form.getCustomerRamID());
		
		criInfo.setCustomerAprCode(form.getCustomerAprCode());
		
		criInfo.setCustomerExtRating(form.getCustomerExtRating());
		
		criInfo.setIsNbfs(form.getIsNbfs());
		
		criInfo.setNbfsA(form.getNbfsA());
		
		criInfo.setNbfsB(form.getNbfsB());
		
		criInfo.setIsPrioritySector(form.getIsPrioritySector());
		
		criInfo.setMsmeClassification(form.getMsmeClassification());
		
		criInfo.setIsPermenentSsiCert(form.getIsPermenentSsiCert());
		
		criInfo.setIsWeakerSection(form.getIsWeakerSection());
		
		criInfo.setWeakerSection(form.getWeakerSection());
		
		criInfo.setGovtSchemeType(form.getGovtSchemeType());
		
		criInfo.setIsKisanCreditCard(form.getIsKisanCreditCard());
		
		criInfo.setIsMinorityCommunity(form.getIsMinorityCommunity());
		
		criInfo.setMinorityCommunity(form.getMinorityCommunity());
		
		criInfo.setIsCapitalMarketExpos(form.getIsCapitalMarketExpos());
		
		criInfo.setIsRealEstateExpos(form.getIsRealEstateExpos());
		
		criInfo.setRealEstateExposType(form.getRealEstateExposType());
		
		criInfo.setRealEstateExposComm(form.getRealEstateExposComm());
		
		criInfo.setIsCommoditiesExposer(form.getIsCommoditiesExposer());
		
		criInfo.setIsSensitive(form.getIsSensitive());
		
		criInfo.setCommoditiesName(form.getCommoditiesName());
		
		criInfo.setIsBackedByGovt(form.getIsBackedByGovt());

		//phase 3 CR:
		String grossInvsInPM = form.getGrossInvsInPM();
		String grossInvsInEquip = form.getGrossInvsInEquip();
		criInfo.setGrossInvsInPM(UIUtil.removeComma(grossInvsInPM));
		
		criInfo.setGrossInvsInEquip(UIUtil.removeComma(grossInvsInEquip));
		
		criInfo.setPsu(form.getPsu());
		
		criInfo.setPsuPercentage(form.getPsuPercentage());
		
		/*criInfo.setGroupExposer(form.getGroupExposer());*/
		
		criInfo.setGovtUnderTaking(form.getGovtUnderTaking());
		
		criInfo.setIsProjectFinance(form.getIsProjectFinance());
		
		criInfo.setIsInfrastructureFinanace(form.getIsInfrastructureFinanace());
		
		criInfo.setInfrastructureFinanaceType(form.getInfrastructureFinanaceType());
		
		criInfo.setIsSemsGuideApplicable(form.getIsSemsGuideApplicable());
		
		criInfo.setIsFailIfcExcluList(form.getIsFailIfcExcluList());
		
		criInfo.setIsTufs(form.getIsTufs());
		
		criInfo.setIsRbiDefaulter(form.getIsRbiDefaulter());
		
		criInfo.setRbiDefaulter(form.getRbiDefaulter());
		
		criInfo.setIsLitigation(form.getIsLitigation());
		
		criInfo.setLitigationBy(form.getLitigationBy());
		
		criInfo.setIsInterestOfBank(form.getIsInterestOfBank());
		
		criInfo.setInterestOfBank(form.getInterestOfBank());
		
		criInfo.setIsAdverseRemark(form.getIsAdverseRemark());
		
		criInfo.setAdverseRemark(form.getAdverseRemark());
		
		criInfo.setAuditType(form.getAuditType());
		
		
	//	criInfo.setAvgAnnualTurnover(form.getAvgAnnualTurnover());
		
		criInfo.setIndustryExposer(form.getIndustryExposer());
		
		
		criInfo.setIsDirecOtherBank(form.getIsDirecOtherBank());
		
		criInfo.setDirecOtherBank(form.getDirecOtherBank());
		
		criInfo.setIsPartnerOtherBank(form.getIsPartnerOtherBank());
		
		criInfo.setPartnerOtherBank(form.getPartnerOtherBank());
		
		criInfo.setIsSubstantialOtherBank(form.getIsSubstantialOtherBank());
		
		criInfo.setSubstantialOtherBank(form.getSubstantialOtherBank());
		
		criInfo.setIsHdfcDirecRltv(form.getIsHdfcDirecRltv());
		
		criInfo.setHdfcDirecRltv(form.getHdfcDirecRltv());
		
		criInfo.setIsObkDirecRltv(form.getIsObkDirecRltv());
		
		criInfo.setObkDirecRltv(form.getObkDirecRltv());
		
		criInfo.setIsPartnerDirecRltv(form.getIsPartnerDirecRltv());
		
		criInfo.setPartnerDirecRltv(form.getPartnerDirecRltv());
		
		criInfo.setIsSubstantialRltvHdfcOther(form.getIsSubstantialRltvHdfcOther());
		
		criInfo.setSubstantialRltvHdfcOther(form.getSubstantialRltvHdfcOther());
		
		criInfo.setDirecHdfcOther(form.getDirecHdfcOther());
		
		criInfo.setFirstYear(form.getFirstYear());
		
		//criInfo.setFirstYearTurnover(form.getFirstYearTurnover()) ;
		
		criInfo.setFirstYearTurnoverCurr(form.getFirstYearTurnoverCurr());
		
        criInfo.setSecondYear(form.getSecondYear());
		
	//	criInfo.setSecondYearTurnover(form.getSecondYearTurnover()) ;
		
		criInfo.setSecondYearTurnoverCurr(form.getSecondYearTurnoverCurr());
		
        criInfo.setThirdYear(form.getThirdYear());
		
	//	criInfo.setThirdYearTurnover(form.getThirdYearTurnover()) ;
		
		criInfo.setThirdYearTurnoverCurr(form.getThirdYearTurnoverCurr());
		
		//Phase 3 CR:comma separated
		String avgAnnualTurnover = form.getAvgAnnualTurnover();
		avgAnnualTurnover=UIUtil.removeComma(avgAnnualTurnover);
		criInfo.setAvgAnnualTurnover(avgAnnualTurnover);
		
		String firstYrTurnover=form.getFirstYearTurnover();
		String secondYrTurnover=form.getSecondYearTurnover();
		String thirdYrTurnover=form.getThirdYearTurnover();
		
		criInfo.setFirstYearTurnover(UIUtil.removeComma(firstYrTurnover)) ;
		criInfo.setSecondYearTurnover(UIUtil.removeComma(secondYrTurnover)) ;
		criInfo.setThirdYearTurnover(UIUtil.removeComma(thirdYrTurnover)) ;
		
		//START : CRI Fields
		criInfo.setCategoryOfFarmer(form.getCategoryOfFarmer());
		criInfo.setEntityType(form.getEntityType());
		criInfo.setIsSPVFunding(form.getIsSPVFunding());
		criInfo.setIndirectCountryRiskExposure(form.getIndirectCountryRiskExposure());
		criInfo.setSalesPercentage(form.getSalesPercentage());
		criInfo.setIsCGTMSE(form.getIsCGTMSE());
		criInfo.setIsIPRE(form.getIsIPRE());
		criInfo.setFinanceForAccquisition(form.getFinanceForAccquisition());
		criInfo.setFacilityApproved(form.getFacilityApproved());
		criInfo.setFacilityAmount(form.getFacilityAmount());
		criInfo.setSecurityName(form.getSecurityName());
		criInfo.setSecurityType(form.getSecurityType());
		criInfo.setSecurityValue(form.getSecurityValue());
		criInfo.setCompany(form.getCompany());
		criInfo.setNameOfHoldingCompany(form.getNameOfHoldingCompany());
		criInfo.setType(form.getType());
		criInfo.setCompanyType(form.getCompanyType());
		criInfo.setIfBreachWithPrescriptions(form.getIfBreachWithPrescriptions());
		criInfo.setComments(form.getComments());
		criInfo.setLandHolding(form.getLandHolding());
		criInfo.setCountryOfGuarantor(form.getCountryOfGuarantor());
		criInfo.setIsAffordableHousingFinance(form.getIsAffordableHousingFinance());
				
		criInfo.setIsInRestrictedList(form.getIsInRestrictedList());
		criInfo.setRestrictedFinancing(form.getRestrictedFinancing());
		criInfo.setRestrictedIndustries(form.getRestrictedIndustries());
		criInfo.setIsQualifyingNotesPresent(form.getIsQualifyingNotesPresent());
		criInfo.setStateImplications(form.getStateImplications());
		criInfo.setIsBorrowerInRejectDatabase(form.getIsBorrowerInRejectDatabase());
		criInfo.setRejectHistoryReason(form.getRejectHistoryReason());
		criInfo.setCapitalForCommodityAndExchange(form.getCapitalForCommodityAndExchange());
		
		if("Broker".equals(form.getCapitalForCommodityAndExchange())){
			criInfo.setIsBrokerTypeShare(StringUtils.isBlank(form.getIsBrokerTypeShare())?ICMSConstant.NO:form.getIsBrokerTypeShare());
			criInfo.setIsBrokerTypeCommodity(StringUtils.isBlank(form.getIsBrokerTypeCommodity())?ICMSConstant.NO:form.getIsBrokerTypeCommodity());
		}
		else{
			criInfo.setIsBrokerTypeShare(null);
			criInfo.setIsBrokerTypeCommodity(null);
		}
		
		/*criInfo.setOdfdCategory(form.getOdfdCategory());*/
		criInfo.setObjectFinance(form.getObjectFinance());
		
		criInfo.setIsCommodityFinanceCustomer(form.getIsCommodityFinanceCustomer());
		criInfo.setRestructuedBorrowerOrFacility(form.getRestructuedBorrowerOrFacility());
		criInfo.setFacility(form.getFacility());
		criInfo.setCriCountryName(form.getCriCountryName());
		criInfo.setLimitAmount(form.getLimitAmount());
		criInfo.setConductOfAccountWithOtherBanks(form.getConductOfAccountWithOtherBanks());
		criInfo.setCrilicStatus(form.getCrilicStatus());
		criInfo.setComment(form.getComment());
		criInfo.setSubsidyFlag(form.getSubsidyFlag());
		criInfo.setHoldingCompnay(form.getHoldingCompnay());
		
		criInfo.setCautionList(form.getCautionList());
		criInfo.setDateOfCautionList(form.getDateOfCautionList());
		criInfo.setDirectors(form.getDirectors());
		criInfo.setGroupCompanies(form.getGroupCompanies());
		
		criInfo.setDefaultersList(form.getDefaultersList());
		criInfo.setRbiCompany(form.getRbiCompany());
		criInfo.setRbiDirectors(form.getRbiDirectors());
		criInfo.setRbiGroupCompanies(form.getRbiGroupCompanies());
		criInfo.setRbiDateOfCautionList(form.getRbiDateOfCautionList());
		
		criInfo.setCommericialRealEstate(form.getCommericialRealEstate());
		criInfo.setCommericialRealEstateValue(form.getCommericialRealEstateValue());
		criInfo.setCommericialRealEstateResidentialHousing(form.getCommericialRealEstateResidentialHousing());
		criInfo.setResidentialRealEstate(form.getResidentialRealEstate());
		criInfo.setIndirectRealEstate(form.getIndirectRealEstate());
		criInfo.setCommericialRealEstateValue(form.getCommericialRealEstateValue());
		criInfo.setCommericialRealEstate(form.getCommericialRealEstate());
		criInfo.setRestrictedListIndustries(form.getRestrictedListIndustries());

		//Ram rating CR
		criInfo.setCustomerFyClouser(form.getCustomerFyClouser());
		
		ICriInfo criList[] = new ICriInfo[1];
		criList[0] = criInfo;
		
		if(form.getCriFacility() == null) {
			ICriFac criFacList[] = new ICriFac[1];
			ICriFac criFac = new OBCriFac();
			criFac.setFacilityFor(form.getFacilityFor());
			
			if(form.getFacilityFor() != null && !form.getFacilityFor().equals("")) {
				if(form.getFacilityFor().equals("Real Estate Exposure")) {
					form.setPrioritySector("");
				}
				if(form.getFacilityFor().equals("Priority/Non priority Sector")) {
					form.setEstateType("");
					form.setCommRealEstateType("");
				}
				if(form.getFacilityFor().equals("Capital Market Exposure")) {
					form.setEstateType("");
					form.setCommRealEstateType("");
					form.setPrioritySector("");
				}
				if(!form.getFacilityFor().equals("Priority/Non priority Sector") && !form.getFacilityFor().equals("Capital Market Exposure")
						&& !form.getFacilityFor().equals("Real Estate Exposure")) {
					form.setSerialNo("");
					form.setEstateType("");
					form.setCommRealEstateType("");
					form.setPrioritySector("");
				}
			}
				
			criFac.setFacilityName(form.getFacilityName());
			criFac.setLineNo(form.getLineNo());
			criFac.setSerialNo(form.getSerialNo());
			criFac.setEstateType(form.getEstateType());
			criFac.setCommRealEstateType(form.getCommRealEstateType());
			criFac.setPrioritySector(form.getPrioritySector());
			if(form.getFacilityAmount() == null){
				form.setFacilityAmount("");
			}
			
		//	criFac.setFacilityAmount(form.getFacilityAmount());
			
			//Phase 3 CR:comma separated
			String facilityAmount = form.getFacilityAmount();
			criFac.setFacilityAmount(UIUtil.removeComma(facilityAmount));
			
			criFacList[0] = criFac;
			cmsLegalEntity.setCriFacList(criFacList);
		}/*else{
			ICriFac criFacList[] = cmsLegalEntity.getCriFacList();
			ICriFac criFac = new OBCriFac();
			criFac.setFacilityFor(form.getFacilityFor());
			criFac.setFacilityName(form.getFacilityName());
			criFac.setFacilityAmount(Double.parseDouble(form.getFacilityAmount()));
			
			criFacList[cmsLegalEntity.getCriFacList().length+1] = criFac;
			cmsLegalEntity.setCriFacList(criFacList);
		}*/
		
		/*
		 * Will be used when 2 tables will be used for officeial address and
		 * registered address
		 */
		/*
		 * cmsLegalEntity.setRegisteredAddress(registerAddressContactArr);
		 * customerInfo.setOfficialAddresses(officialAddressContactArr);
		 */

		/* cmsLegalEntity.setOtherSystem(form.getOtherSystem()); */
		//cmsLegalEntity.setOtherSystem(otherSystem);
		
//		for(int i = 0; i <= form.getCriFacility().size(); i++){
//			ICriFac criFac = new OBCriFac();
//			//criFac.setFacilityFor(form.getCriFacility().get(i).getCriFacilityFor());
//		}
		
		cmsLegalEntity.setRegisteredAddress(address);
		cmsLegalEntity.setBankList(banking);
		cmsLegalEntity.setCriList(criList);
		cmsLegalEntity.setDirector(dir);
		//cmsLegalEntity.setCriFacList();
	
		cmsUdf.setId(form.getUdfId());
        cmsUdf.setUdf1(form.getUdf1());
        cmsUdf.setUdf2(form.getUdf2());
        cmsUdf.setUdf3(form.getUdf3());
        cmsUdf.setUdf4(form.getUdf4());
        cmsUdf.setUdf5(form.getUdf5());
        cmsUdf.setUdf6(form.getUdf6());
        cmsUdf.setUdf7(form.getUdf7());
        cmsUdf.setUdf8(form.getUdf8());
        cmsUdf.setUdf9(form.getUdf9());
        cmsUdf.setUdf10(form.getUdf10());
        cmsUdf.setUdf11(form.getUdf11());
        cmsUdf.setUdf12(form.getUdf12());
        cmsUdf.setUdf13(form.getUdf13());
        cmsUdf.setUdf14(form.getUdf14());
        cmsUdf.setUdf15(form.getUdf15());
        cmsUdf.setUdf16(form.getUdf16());
        cmsUdf.setUdf17(form.getUdf17());
        cmsUdf.setUdf18(form.getUdf18());
        cmsUdf.setUdf19(form.getUdf19());
        cmsUdf.setUdf20(form.getUdf20());
        cmsUdf.setUdf21(form.getUdf21());
        cmsUdf.setUdf22(form.getUdf22());
        cmsUdf.setUdf23(form.getUdf23());
        cmsUdf.setUdf24(form.getUdf24());
        cmsUdf.setUdf25(form.getUdf25());
        cmsUdf.setUdf26(form.getUdf26());
        cmsUdf.setUdf27(form.getUdf27());
        cmsUdf.setUdf28(form.getUdf28());
        cmsUdf.setUdf29(form.getUdf29());
        cmsUdf.setUdf30(form.getUdf30());
        cmsUdf.setUdf31(form.getUdf31());
        cmsUdf.setUdf32(form.getUdf32());
        cmsUdf.setUdf33(form.getUdf33());
        cmsUdf.setUdf34(form.getUdf34());
        cmsUdf.setUdf35(form.getUdf35());
        cmsUdf.setUdf36(form.getUdf36());
        cmsUdf.setUdf37(form.getUdf37());
        cmsUdf.setUdf38(form.getUdf38());
        cmsUdf.setUdf39(form.getUdf39());
        cmsUdf.setUdf40(form.getUdf40());
        cmsUdf.setUdf41(form.getUdf41());
        cmsUdf.setUdf42(form.getUdf42());
        cmsUdf.setUdf43(form.getUdf43());
        cmsUdf.setUdf44(form.getUdf44());
        cmsUdf.setUdf45(form.getUdf45());
        cmsUdf.setUdf46(form.getUdf46());
        cmsUdf.setUdf47(form.getUdf47());
        cmsUdf.setUdf48(form.getUdf48());
        cmsUdf.setUdf49(form.getUdf49());
        cmsUdf.setUdf50(form.getUdf50());
    	cmsUdf.setUdf51(form.getUdf51());
        cmsUdf.setUdf52(form.getUdf52());
        cmsUdf.setUdf53(form.getUdf53());
        cmsUdf.setUdf54(form.getUdf54());
        cmsUdf.setUdf55(form.getUdf55());
        cmsUdf.setUdf56(form.getUdf56());
        cmsUdf.setUdf57(form.getUdf57());
        cmsUdf.setUdf58(form.getUdf58());
        cmsUdf.setUdf59(form.getUdf59());
        cmsUdf.setUdf60(form.getUdf60());
        cmsUdf.setUdf61(form.getUdf61());
        cmsUdf.setUdf62(form.getUdf62());
        cmsUdf.setUdf63(form.getUdf63());
        cmsUdf.setUdf64(form.getUdf64());
        cmsUdf.setUdf65(form.getUdf65());
        cmsUdf.setUdf66(form.getUdf66());
        cmsUdf.setUdf67(form.getUdf67());
        cmsUdf.setUdf68(form.getUdf68());
        cmsUdf.setUdf69(form.getUdf69());
        cmsUdf.setUdf70(form.getUdf70());
        cmsUdf.setUdf71(form.getUdf71());
        cmsUdf.setUdf72(form.getUdf72());
        cmsUdf.setUdf73(form.getUdf73());
        cmsUdf.setUdf74(form.getUdf74());
        cmsUdf.setUdf75(form.getUdf75());
        cmsUdf.setUdf76(form.getUdf76());
        cmsUdf.setUdf77(form.getUdf77());
        cmsUdf.setUdf78(form.getUdf78());
        cmsUdf.setUdf79(form.getUdf79());
        cmsUdf.setUdf80(form.getUdf80());
        cmsUdf.setUdf81(form.getUdf81());
        cmsUdf.setUdf82(form.getUdf82());
        cmsUdf.setUdf83(form.getUdf83());
        cmsUdf.setUdf84(form.getUdf84());
        cmsUdf.setUdf85(form.getUdf85());
        cmsUdf.setUdf86(form.getUdf86());
        cmsUdf.setUdf87(form.getUdf87());
        cmsUdf.setUdf88(form.getUdf88());
        cmsUdf.setUdf89(form.getUdf89());
        cmsUdf.setUdf90(form.getUdf90());
        cmsUdf.setUdf91(form.getUdf91());
        cmsUdf.setUdf92(form.getUdf92());
        cmsUdf.setUdf93(form.getUdf93());
        cmsUdf.setUdf94(form.getUdf94());
        cmsUdf.setUdf95(form.getUdf95());
        cmsUdf.setUdf96(form.getUdf96());
        cmsUdf.setUdf97(form.getUdf97());
        cmsUdf.setUdf98(form.getUdf98());
        cmsUdf.setUdf99(form.getUdf99());
        cmsUdf.setUdf100(form.getUdf100());
        cmsUdf.setUdf101(form.getUdf101());
        cmsUdf.setUdf102(form.getUdf102());
        cmsUdf.setUdf103(form.getUdf103());
        cmsUdf.setUdf104(form.getUdf104());
        cmsUdf.setUdf105(form.getUdf105());
        cmsUdf.setUdf106(form.getUdf106());
        cmsUdf.setUdf107(form.getUdf107());
        cmsUdf.setUdf108(form.getUdf108());
        cmsUdf.setUdf109(form.getUdf109());
        cmsUdf.setUdf110(form.getUdf110());
        cmsUdf.setUdf111(form.getUdf111());
        cmsUdf.setUdf112(form.getUdf112());
        cmsUdf.setUdf113(form.getUdf113());
        cmsUdf.setUdf114(form.getUdf114());
        cmsUdf.setUdf115(form.getUdf115());
        cmsUdf.setUdf116(form.getUdf116());
        cmsUdf.setUdf117(form.getUdf117());
        cmsUdf.setUdf118(form.getUdf118());
        cmsUdf.setUdf119(form.getUdf119());
        cmsUdf.setUdf120(form.getUdf120());
        ICMSCustomerUdf udfLists[] = new ICMSCustomerUdf[1];
        udfLists[0] = cmsUdf;
		
        cmsLegalEntity.setUdfData(udfLists);
        
        //Start:Uma Khot:CRI Field addition enhancement
        cmsLegalEntity.setIsRBIWilfulDefaultersList(form.getIsRBIWilfulDefaultersList());
        cmsLegalEntity.setNameOfBank(form.getNameOfBank());
        cmsLegalEntity.setIsDirectorMoreThanOne(form.getIsDirectorMoreThanOne());
        cmsLegalEntity.setNameOfDirectorsAndCompany(form.getNameOfDirectorsAndCompany());
        cmsLegalEntity.setIsBorrDefaulterWithBank(form.getIsBorrDefaulterWithBank());
        cmsLegalEntity.setDetailsOfDefault(form.getDetailsOfDefault());
        cmsLegalEntity.setExtOfCompromiseAndWriteoff(form.getExtOfCompromiseAndWriteoff());
        cmsLegalEntity.setIsCibilStatusClean(form.getIsCibilStatusClean());
        cmsLegalEntity.setDetailsOfCleanCibil(form.getDetailsOfCleanCibil());
		  //End:Uma Khot:CRI Field addition enhancement
    	customerInfo.setCMSLegalEntity(cmsLegalEntity);
    	
		 //Start:Uma Khot:CRI Field addition enhancement
    	customerInfo.setIsRBIWilfulDefaultersList(form.getIsRBIWilfulDefaultersList());
    	customerInfo.setNameOfBank(form.getNameOfBank());
    	customerInfo.setIsDirectorMoreThanOne(form.getIsDirectorMoreThanOne());
    	customerInfo.setNameOfDirectorsAndCompany(form.getNameOfDirectorsAndCompany());
    	customerInfo.setIsBorrDefaulterWithBank(form.getIsBorrDefaulterWithBank());
    	customerInfo.setDetailsOfDefault(form.getDetailsOfDefault());
    	customerInfo.setExtOfCompromiseAndWriteoff(form.getExtOfCompromiseAndWriteoff());
    	customerInfo.setIsCibilStatusClean(form.getIsCibilStatusClean());
    	customerInfo.setDetailsOfCleanCibil(form.getDetailsOfCleanCibil());
		  //End:Uma Khot:CRI Field addition enhancement
		
		DefaultLogger.debug(this, "Values Set ");
		DefaultLogger.debug(this, "lcustomerInfo.getDomicileCountry : "
				+ customerInfo.getDomicileCountry());
		DefaultLogger.debug(this,
				"customerInfo.getLegalEntity().getLegalConstitution : "
						+ customerInfo.getCMSLegalEntity()
								.getLegalConstitution());
		
		//		DefaultLogger.debug(this,
//				"customerInfo.getLegalEntity().getShortName : "
//						+ customerInfo.getCMSLegalEntity().getShortName());
//		DefaultLogger.debug(this, "customerInfo.getLegalEntity().getLEID : "
//				+ customerInfo.getCMSLegalEntity().getLEID());

		mapCoBorrowerDetailsFormToOB(form, customerInfo, inputs);
		
		}catch (Exception e){
			DefaultLogger.error(this, e.getMessage(), e);
			e.printStackTrace();
		}
		
		return customerInfo;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs)
			throws MapperException {
		DefaultLogger.debug(this,
				"Entering method mapOBToForm : ManualInputCustomerMapper");
		DefaultLogger.debug(this, "::::" + cForm);

		// CustomerInfoForm form = (CustomerInfoForm)cForm;
		ManualInputCustomerInfoForm form = (ManualInputCustomerInfoForm) cForm;

		ICMSCustomer customerInfo = (OBCMSCustomer) obj;

		form.setCIFId(customerInfo.getLegalEntity().getLEID() + "");
		form.setCmsId(customerInfo.getCustomerID());
		form.setLegalConstitution(customerInfo.getLegalEntity()
				.getLegalConstitution());
		form.setLegalName(customerInfo.getCustomerName());

		
		form.setCustomerNameShort(customerInfo.getCustomerName());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
		if (customerInfo.getCustomerRelationshipStartDate() != null) {
			form.setRelationshipStartDate(sdf.format(customerInfo.getCustomerRelationshipStartDate()));
		}
		//added by santosh for LEI CR
		form.setLeiCode(customerInfo.getLeiCode());
		if (customerInfo.getLeiExpDate() != null) {
			form.setLeiExpDate(sdf.format(customerInfo.getLeiExpDate()));
		}
		
		form.setLeiCode(customerInfo.getCMSLegalEntity().getLeiCode());
		if (customerInfo.getCMSLegalEntity().getLeiExpDate() != null) {
			form.setLeiExpDate(sdf.format(customerInfo.getCMSLegalEntity().getLeiExpDate()));
		}
		//End Santosh
		form.setDomicileCountry(customerInfo.getDomicileCountry());
	//	form.setCountry(customerInfo.getCMSLegalEntity().getCountryPR());
		form.setSubProfileId(customerInfo.getCustomerID());

		form.setCycle(customerInfo.getCycle());
	// form.setCustomerBranch();
		form.setPartyGroupName(customerInfo.getPartyGroupName());
		form.setRelationshipMgrEmpCode(customerInfo.getRelationshipMgrEmpCode());
		
		IRelationshipMgrDAO relationshipMgrDAOImpl = (IRelationshipMgrDAO)BeanHouse.get("relationshipMgrDAO");
		if(null != customerInfo.getRelationshipMgr() && !"".equals(customerInfo.getRelationshipMgr())) {
			IRelationshipMgr relationshipMgr = relationshipMgrDAOImpl.getRelationshipMgrById(Long.parseLong(customerInfo.getRelationshipMgr()));
			form.setRelationshipMgr(relationshipMgr.getRelationshipMgrName());
		}
		if(null != customerInfo.getRmRegion() && !"".equals(customerInfo.getRmRegion())) {
			IRegionDAO iRegion =  (IRegionDAO) BeanHouse.get("regionDAO");
			IRegion reg;
			try {
				reg = iRegion.getRegionById(Long.parseLong(customerInfo.getRmRegion()));
				form.setRmRegion(reg.getRegionName());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}		
		
//		form.setRelationshipMgr(customerInfo.getRelationshipMgr());
//		form.setRmRegion(customerInfo.getRmRegion());
//		
		form.setMainBranch(customerInfo.getMainBranch());
		form.setBranchCode(customerInfo.getBranchCode());
		if (customerInfo.getDateOfIncorporation() != null) {
			form.setDateOfIncorporation(sdf.format(customerInfo.getDateOfIncorporation()));
		}
		form.setAadharNumber(customerInfo.getAadharNumber());
		
		form.setListedCompany(customerInfo.getListedCompany());
		form.setIsinNo(customerInfo.getIsinNo());
		form.setRaroc(customerInfo.getRaroc());
		
		form.setYearEndPeriod(customerInfo.getYearEndPeriod());
		form.setCreditMgrEmpId(customerInfo.getCreditMgrEmpId());
		form.setPfLrdCreditMgrEmpId(customerInfo.getPfLrdCreditMgrEmpId());
		form.setCreditMgrSegment(customerInfo.getCreditMgrSegment());

		form.setConsBankFundBasedHdfcBankPer(customerInfo.getConsBankFundBasedHdfcBankPer());
		form.setConsBankFundBasedLeadBankPer(customerInfo.getConsBankFundBasedLeadBankPer());
		form.setConsBankNonFundBasedHdfcBankPer(customerInfo.getConsBankNonFundBasedHdfcBankPer());
		form.setConsBankNonFundBasedLeadBankPer(customerInfo.getConsBankNonFundBasedLeadBankPer());
		
		form.setMultBankFundBasedHdfcBankPer(customerInfo.getMultBankFundBasedHdfcBankPer());
		form.setMultBankFundBasedLeadBankPer(customerInfo.getMultBankFundBasedLeadBankPer());
		form.setMultBankNonFundBasedHdfcBankPer(customerInfo.getMultBankNonFundBasedHdfcBankPer());
		form.setMultBankNonFundBasedLeadBankPer(customerInfo.getMultBankNonFundBasedLeadBankPer());
		
		form.setRevisedEmailIdsArray(customerInfo.getRevisedEmailIdsArrayList());
		
		ArrayList arrs = new ArrayList();
		
		if(form.getRevisedEmailIdsArray() != null) {
			for(int i=0;i<form.getRevisedEmailIdsArray().length - 1;i++) {
				arrs.add(form.getRevisedEmailIdsArray()[i].toString());
			}
		}
		
		form.setRevisedArrayListN(arrs);

		if (customerInfo.getRaraocPeriod() != null) {
			form.setRaraocPeriod(sdf.format(customerInfo.getRaraocPeriod()));
		}
		form.setCinLlpin(customerInfo.getCinLlpin());
		form.setPartyNameAsPerPan(customerInfo.getPartyNameAsPerPan());
		form.setEntity(customerInfo.getEntity());
		form.setRbiIndustryCode(customerInfo.getCMSLegalEntity()
				.getRbiIndustryCode());
		form
				.setIndustryName(customerInfo.getCMSLegalEntity()
						.getIndustryName());
		form.setPan(customerInfo.getCMSLegalEntity().getPan());
		//form.setRegion(customerInfo.getRegion());
		// form.setCountry(customerInfo.getCountry());
		// form.setState(customerInfo.getState());
		// form.setState(customerInfo.getState());
		// form.setTelephoneNo(customerInfo.getTelephoneNo());
		form.setSubLine(customerInfo.getSubLine());
		form.setBankingMethod(customerInfo.getBankingMethod());
		form.setFinalBankMethodList(customerInfo.getFinalBankMethodList());
		
		form.setExceptionalCases(customerInfo.getExceptionalCases());
	//	form.setTotalFundedLimit(customerInfo.getTotalFundedLimit());
	//	form.setTotalNonFundedLimit(customerInfo.getTotalNonFundedLimit());
		form.setFundedSharePercent(customerInfo.getFundedSharePercent());
		form.setNonFundedSharePercent(customerInfo.getNonFundedSharePercent());
		
		//Uma Khot:Cam upload and Dp field calculation CR
		form.setDpSharePercent(customerInfo.getDpSharePercent());
		
	//	form.setMemoExposure(customerInfo.getMemoExposure());
	//	form.setTotalSanctionedLimit(customerInfo.getTotalSanctionedLimit());
		form.setMpbf(UIUtil.formatWithCommaAndDecimal(customerInfo.getMpbf()));
		/*if(customerInfo.getStatus()== null || "".equals(customerInfo.getStatus().trim()))
		{
			form.setStatus("ACTIVE");
		}
		else{*/
			form.setStatus(customerInfo.getStatus());
		
		//phase 3 CR : comma seperated
			String totalFundedLimit = customerInfo.getTotalFundedLimit();
			String totalFundedLimit1=UIUtil.formatWithCommaAndDecimalNew(totalFundedLimit);
			form.setTotalFundedLimit(totalFundedLimit1);
			
			String totalNonFundedLimit = customerInfo.getTotalNonFundedLimit();
			String totalNonFundedLimit1=UIUtil.formatWithCommaAndDecimalNew(totalNonFundedLimit);
			form.setTotalNonFundedLimit(totalNonFundedLimit1);
			
			String memoExposure = customerInfo.getMemoExposure();
			String memoExposure1=UIUtil.formatWithCommaAndDecimalNew(memoExposure);
			form.setMemoExposure(memoExposure1);
			
			String totalSanctLimit= customerInfo.getTotalSanctionedLimit();
			String totalSanctLimit1=UIUtil.formatWithCommaAndDecimalNew(totalSanctLimit);
			form.setTotalSanctionedLimit(totalSanctLimit1);
			
			String fundedShareLimit = customerInfo.getFundedShareLimit();
			String fundedShareLimit1=UIUtil.formatWithCommaAndDecimalNew(fundedShareLimit);
			form.setFundedShareLimit(fundedShareLimit1);
			
			String nonFundedShareLimit = customerInfo.getNonFundedShareLimit();
			String nonFundedShareLimit1=UIUtil.formatWithCommaAndDecimalNew(nonFundedShareLimit);
			form.setNonFundedShareLimit(nonFundedShareLimit1);

//		form.setFundedShareLimit(customerInfo.getFundedShareLimit());
//		form.setNonFundedShareLimit(customerInfo.getNonFundedShareLimit());
		
		form.setBorrowerDUNSNo(customerInfo.getBorrowerDUNSNo());
		form.setClassActivity1(customerInfo.getClassActivity1());
		form.setClassActivity2(customerInfo.getClassActivity2());
		form.setClassActivity3(customerInfo.getClassActivity3());
		form.setWillfulDefaultStatus(customerInfo.getWillfulDefaultStatus());
		
		form.setIsRBIWilfulDefaultersList(customerInfo.getIsRBIWilfulDefaultersList());
		form.setNameOfBank(customerInfo.getNameOfBank());
		form.setIsDirectorMoreThanOne(customerInfo.getIsDirectorMoreThanOne());
		form.setNameOfDirectorsAndCompany(customerInfo.getNameOfDirectorsAndCompany());
		form.setIsBorrDefaulterWithBank(customerInfo.getIsBorrDefaulterWithBank());
		form.setDetailsOfDefault(customerInfo.getDetailsOfDefault());
		form.setExtOfCompromiseAndWriteoff(customerInfo.getExtOfCompromiseAndWriteoff());
		form.setIsCibilStatusClean(customerInfo.getIsCibilStatusClean());
		form.setDetailsOfCleanCibil(customerInfo.getDetailsOfCleanCibil());
		
		
		
		if (customerInfo.getDateOfSuit() == null) {
		} else {
			form.setDateOfSuit(sdf.format(customerInfo.getDateOfSuit()));
		}
		if (customerInfo.getDateWillfulDefault() == null) {
		} else {
			form.setDateWillfulDefault(sdf.format(customerInfo.getDateWillfulDefault()));
		}
		form.setSuitFilledStatus(customerInfo.getSuitFilledStatus());
		
		//Phase 3 CR:comma separated
		form.setSuitAmount(UIUtil.formatWithCommaAndDecimal(customerInfo.getSuitAmount()));
		
		//form.setSuitAmount(customerInfo.getSuitAmount());
		form.setCurrency(customerInfo.getCurrency());
		form.setPartyConsent(customerInfo.getPartyConsent());
		form.setRegOffice(customerInfo.getRegOffice());
		
		form.setSuitReferenceNo(customerInfo.getSuitReferenceNo());
		
		form.setRegOfficeDUNSNo(customerInfo.getRegOfficeDUNSNo());
		
		if (customerInfo.getCMSLegalEntity().getBlackListedInd() == true)
			form.setBlacklisted("Yes");
		else
			form.setBlacklisted("No");

		IISICCode[] isicCode = customerInfo.getCMSLegalEntity().getISICCode();
		if (isicCode != null && isicCode.length > 0) {
			if (!(isicCode[0].getISICCode().equals(null) || isicCode[0]
					.getISICCode().equals(""))) {
				form.setIsicCode(isicCode[0].getISICCode().toString());
			}
		}

		String isicType = null;
		if (isicCode != null && isicCode.length > 0) {
			// hm =
			// CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.ISIC_CODE);

			if (!(isicCode[0].getISICType() == null || isicCode[0]
					.getISICType().equals(""))) {
				// isicType = (String) hm.get(isicCode[0].getISICCode());
			}
		}

		String custSeg = null;
		HashMap hm = new HashMap();
		hm = CommonDataSingleton
				.getCodeCategoryValueLabelMap(CategoryCodeConstant.CUSTOMER_SEGMENT);
		if (!(customerInfo.getCMSLegalEntity().getCustomerSegment() == null || customerInfo
				.getCMSLegalEntity().getCustomerSegment().equals(""))) {
			// custSeg =
			// (String)form.setCustomerSegment(hm.get(customerInfo.getCMSLegalEntity().getCustomerSegment()));
		}

		// String custType = null;
		// hm =
		// CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.CUSTOMER_TYPE);
		// if(! (legal.getCustomerType() == null ||
		// legal.getCustomerType().equals("")) ){
		// custType = (String) hm.get(legal.getCustomerType());
		// }

		/*
		 * Will be used when 2 tables will be used for officeial address and
		 * registered address
		 */

		/*
		 * IContact regAddress[] =
		 * customerInfo.getCMSLegalEntity().getRegisteredAddress(); IContact
		 * obContact = regAddress[0]; IContact officeAddress[] =
		 * customerInfo.getOfficialAddresses(); IContact obContactOff =
		 * officeAddress[0];
		 */

		IContact obContact = null;
		ISystem obSystem = null;
		IVendor obVendor = null;
		IContact obContactOff = null;
		IContact obContactRegOff = null;
		IContact address[] = customerInfo.getCMSLegalEntity()
				.getRegisteredAddress();
		ISystem system[] = customerInfo.getCMSLegalEntity().getOtherSystem();
		IVendor vendor[] = customerInfo.getCMSLegalEntity().getVendor();
		IBankingMethod bankingMethod[] = customerInfo.getCMSLegalEntity()
				.getBankList();
		ISubline subline[] = customerInfo.getCMSLegalEntity().getSublineParty();
		IDirector[] director = customerInfo.getCMSLegalEntity().getDirector();
		IDirector dir = null;
		if(director != null){
		dir = director[0];
		}	
		if(dir!= null){
		 if(dir.getDirectorCountry()!=null && !"".equals(dir.getDirectorCountry()))
	        {
	        form.setDirectorCountry(dir.getDirectorCountry());
	        }
		 if(dir.getDirectorState()!=null && !"".equals(dir.getDirectorState()))
	        {
	        form.setDirectorState(dir.getDirectorState());
	        }
		 if(dir.getDirectorRegion()!=null && !"".equals(dir.getDirectorRegion()))
	        {
	        form.setDirectorRegion(dir.getDirectorRegion());
	        }
		 if(dir.getDirectorCity()!=null && !"".equals(dir.getDirectorCity()))
	        {
	        form.setDirectorCity(dir.getDirectorCity());
	        }
		 
		 //Tightening property CR
		 if(dir.getDirectorPostCode()!=null && !"".equals(dir.getDirectorPostCode()))
	        {
	        form.setDirectorPostCode(dir.getDirectorPostCode());
	        }
		 
		}
		
		if (address != null) {
			for (int i = 0; i < address.length; i++) {
				if (address[i].getContactType() != null
						&& address[i].getContactType().equals(
								ICMSConstant.REGISTERED)) {
					obContactRegOff = address[i];
				}
				else if (address[i].getContactType() != null
						&& address[i].getContactType().equals(
								ICMSConstant.OFFICE)) {
					obContactOff = address[i];
				}
				else if (address[i].getContactType() != null
						&& address[i].getContactType().equals(
								ICMSConstant.CORPORATE)) {
					obContact = address[i];
				}
			}
		}
		List list = new ArrayList();
		if (system != null) {
			for (int i = 0; i < system.length; i++) {
				list.add(system[i]);

			}
		}
		
		List name = new ArrayList();
		if (vendor != null) {
			for (int i = 0; i < vendor.length; i++) {
				name.add(vendor[i]);

			}
		}

		List bank = new ArrayList();
		if (bankingMethod != null) {
			for (int i = 0; i < bankingMethod.length; i++) {
				bank.add(bankingMethod[i]);

			}
		}

		List subParty = new ArrayList();
		if (subline != null) {
			for (int i = 0; i < subline.length; i++) {
				subParty.add(subline[i]);

			}
		}
		boolean nodalValueFlag = false;
		boolean leadValueFlag = false;
		for (int i = 0; bank.size() > i; i++) {
			OBBankingMethod nodal = (OBBankingMethod) bank.get(i);
			if(nodal != null && !nodal.equals("")){
			if (nodal.getNodal() != null && !nodal.getNodal().equals("") && nodalValueFlag == false ) {
				if (nodal.getBankType()!=null && nodal.getBankType().equals("O")) {
					if (nodal.getNodal()!= null && (nodal.getNodal().equals("Y") || nodal.getNodal().equals("Yes"))) {
						form.setNodalLead("o,"+(String.valueOf(nodal.getBankId()).trim()));
						nodalValueFlag = true;
					}
				} else if (nodal.getBankType()!=null && nodal.getBankType().equals("S")) {
					if (nodal.getNodal()!= null && nodal.getNodal().equals("Y") || nodal.getNodal().equals("Yes")) {
						form.setNodalLead("s,"+(String.valueOf(nodal.getBankId()).trim()));
						nodalValueFlag = true;
					}
				}
			}
			if (nodal.getLead() != null &&  !nodal.getLead().equals("") && leadValueFlag == false ) {
				if (nodal.getBankType()!=null && nodal.getBankType().equals("O") ) {
					if (nodal.getLead()!= null && nodal.getLead().equals("Y")) {
//						form.setNodalLead("o,"+(String.valueOf(nodal.getBankId()).trim()));
						form.setLeadValue("o,"+(String.valueOf(nodal.getBankId()).trim()));
						leadValueFlag = true;
					}
				} else if (nodal.getBankType()!=null && nodal.getBankType().equals("S") ) {
					if (nodal.getLead()!= null && nodal.getLead().equals("Y")) {
//						form.setNodalLead("s,"+(String.valueOf(nodal.getBankId()).trim()));
						form.setLeadValue("s,"+(String.valueOf(nodal.getBankId()).trim()));
						leadValueFlag = true;
					}
				}
			}
			}
		}

		
		form.setOtherSystem(list);
		form.setOtherBank(bank);
		form.setVendor(name);
		form.setSublineParty(subParty);
		if (obContact == null) {
			obContact = new OBContact();
		} else {
			form.setAddress1(obContact.getAddressLine1());
			form.setAddress2(obContact.getAddressLine2());
			form.setAddress3(obContact.getAddressLine3());
			form.setAddress4(obContact.getAddressLine4());
			form.setContactType(obContact.getContactType());
			form.setTelephoneNo(obContact.getTelephoneNumer());
			form.setTelex(obContact.getTelex());
			form.setEmail(obContact.getEmailAddress());
			form.setState(obContact.getState());
			form.setCity(obContact.getCity());
			form.setRegion(obContact.getRegion());
			form.setPostcode(obContact.getPostalCode());
			form.setStdCodeTelNo(obContact.getStdCodeTelNo());
			form.setStdCodeTelex(obContact.getStdCodeTelex());
			// form.setLraLeId(obContact.getLraLeId());
			//form.setCountry(obContact.getCountryCode());
			form.setCountry(obContact.getCountryCode());
		}

		if (obContactOff == null) {
			obContactOff = new OBContact();
		} else {
			form.setOfficeAddress1(obContactOff.getAddressLine1());
			form.setOfficeAddress2(obContactOff.getAddressLine2());
			form.setOfficeAddress3(obContactOff.getAddressLine3());
			form.setOfficeAddress4(obContactOff.getAddressLine4());
			form.setContactType(obContactOff.getContactType());
			form.setOfficeTelephoneNo(obContactOff.getTelephoneNumer());
			form.setOfficeTelex(obContactOff.getTelex());
			form.setOfficeEmail(obContactOff.getEmailAddress());
			form.setOfficeState(obContactOff.getState());
			form.setOfficeCity(obContactOff.getCity());
			form.setOfficeRegion(obContactOff.getRegion());
			form.setOfficePostCode(obContactOff.getPostalCode());
			form.setOfficeCountry(obContactOff.getCountryCode());
		}
		
		if (obContactRegOff == null) {
			obContactRegOff = new OBContact();
		} else {
			form.setRegOfficeAddress1(obContactRegOff.getAddressLine1());
			form.setRegOfficeAddress2(obContactRegOff.getAddressLine2());
			form.setRegOfficeAddress3(obContactRegOff.getAddressLine3());
			form.setRegOfficeAddress4(obContactRegOff.getAddressLine4());
			form.setContactType(obContactRegOff.getContactType());
			form.setRegOfficeTelephoneNo(obContactRegOff.getTelephoneNumer());
			form.setRegOfficeTelex(obContactRegOff.getTelex());
			form.setRegOfficeEmail(obContactRegOff.getEmailAddress());
			form.setRegOfficeState(obContactRegOff.getState());
			form.setRegOfficeCity(obContactRegOff.getCity());
			form.setStdCodeOfficeTelNo(obContactRegOff.getStdCodeTelNo());
			form.setStdCodeOfficeTelex(obContactRegOff.getStdCodeTelex());
			form.setRegOfficeRegion(obContactRegOff.getRegion());
			form.setRegOfficePostCode(obContactRegOff.getPostalCode());
			form.setRegOfficeCountry(obContactRegOff.getCountryCode());
		}
		form.setPartyGroupName(customerInfo.getPartyGroupName());
		
		form.setEntity(customerInfo.getEntity());
		form.setBusinessSector(CommonDataSingleton.getCodeCategoryLabelByValue(
				CategoryCodeConstant.BUSSINESS_SECTOR, customerInfo
						.getCMSLegalEntity().getBusinessSector()));
		form.setCifId(customerInfo.getCMSLegalEntity().getLEReference());
		form.setSource(customerInfo.getCMSLegalEntity().getSourceID());
		form.setIncorporationDate(customerInfo.getCMSLegalEntity()
				.getIncorporateDate());
		form.setAccountOfficerID(customerInfo.getCMSLegalEntity()
				.getAccountOfficerID());
		form.setAccountOfficerName(customerInfo.getCMSLegalEntity()
				.getAccountOfficerName());
		// form.setBaselCustomerSegment(customerInfo.getCMSLegalEntity().getBaselSegment());
		// form.setBaselCustomerSubSegment(customerInfo.getCMSLegalEntity().getBaselSubSegment());
		form.setCustomerSegment(customerInfo.getCustomerSegment());
		form.setBusinessGroup(customerInfo.getCMSLegalEntity()
				.getBusinessGroup());
		form.setBusinessSector(customerInfo.getCMSLegalEntity()
				.getBusinessSector());
		// form.setCountryPermanent(customerInfo.getCMSLegalEntity().getCountryPR());
		form
				.setCustomerType(customerInfo.getCMSLegalEntity()
						.getCustomerType());
		// form.set(customerInfo.getCMSLegalEntity().getCreditGrades());
		form.setLegalId(customerInfo.getCMSLegalEntity().getLegalIDSource());

		form.setCustomerType(customerInfo.getCustomerOriginType());
		form.setIncorporatedCountry(customerInfo.getLegalEntity()
				.getLegalRegCountry());
		// form.setSwiftCode(customerInfo.getSwiftCode());
		// form.set(customerInfo.getCMSLegalEntity().getVersionTime());

		form.setCycle(customerInfo.getCycle());
		// form.setCustomerBranch();
		form.setPartyGroupName(customerInfo.getPartyGroupName());
		form.setRelationshipMgrEmpCode(customerInfo.getRelationshipMgrEmpCode());
//		form.setRelationshipMgr(customerInfo.getRelationshipMgr());
//		form.setRmRegion(customerInfo.getRmRegion());
		
//		IRelationshipMgrDAO relationshipMgrDAOImpl = (IRelationshipMgrDAO)BeanHouse.get("relationshipMgrDAO");
		if(null != customerInfo.getRelationshipMgr() && !"".equals(customerInfo.getRelationshipMgr())) {
			IRelationshipMgr relationshipMgr = relationshipMgrDAOImpl.getRelationshipMgrById(Long.parseLong(customerInfo.getRelationshipMgr()));
			form.setRelationshipMgr(relationshipMgr.getRelationshipMgrName());
		}
		if(null != customerInfo.getRmRegion() && !"".equals(customerInfo.getRmRegion())) {
			IRegionDAO iRegion =  (IRegionDAO) BeanHouse.get("regionDAO");
			IRegion reg;
			try {
				reg = iRegion.getRegionById(Long.parseLong(customerInfo.getRmRegion()));
				form.setRmRegion(reg.getRegionName());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}		
		
		form.setEntity(customerInfo.getEntity());
		form.setRbiIndustryCode(customerInfo
				.getRbiIndustryCode());
		form.setAadharNumber(customerInfo.getAadharNumber());
		form.setListedCompany(customerInfo.getListedCompany());
		form.setIsinNo(customerInfo.getIsinNo());
		form.setRaroc(customerInfo.getRaroc());
		
		form.setYearEndPeriod(customerInfo.getYearEndPeriod());
		form.setCreditMgrEmpId(customerInfo.getCreditMgrEmpId());
		form.setPfLrdCreditMgrEmpId(customerInfo.getPfLrdCreditMgrEmpId());
		form.setCreditMgrSegment(customerInfo.getCreditMgrSegment());
		
		form.setConsBankFundBasedHdfcBankPer(customerInfo.getConsBankFundBasedHdfcBankPer());
		form.setConsBankFundBasedLeadBankPer(customerInfo.getConsBankFundBasedLeadBankPer());
		form.setConsBankNonFundBasedHdfcBankPer(customerInfo.getConsBankNonFundBasedHdfcBankPer());
		form.setConsBankNonFundBasedLeadBankPer(customerInfo.getConsBankNonFundBasedLeadBankPer());
		
		form.setMultBankFundBasedHdfcBankPer(customerInfo.getMultBankFundBasedHdfcBankPer());
		form.setMultBankFundBasedLeadBankPer(customerInfo.getMultBankFundBasedLeadBankPer());
		form.setMultBankNonFundBasedHdfcBankPer(customerInfo.getMultBankNonFundBasedHdfcBankPer());
		form.setMultBankNonFundBasedLeadBankPer(customerInfo.getMultBankNonFundBasedLeadBankPer());
		
		form.setRevisedEmailIdsArray(customerInfo.getRevisedEmailIdsArrayList());
		
		if (customerInfo.getRaraocPeriod() != null) {
			form.setRaraocPeriod(sdf.format(customerInfo.getRaraocPeriod()));
		}
		form.setCinLlpin(customerInfo.getCinLlpin());
		form.setPartyNameAsPerPan(customerInfo.getPartyNameAsPerPan());
		form
				.setIndustryName(customerInfo
						.getIndustryName());
		form.setPan(customerInfo.getPan());
		
		//Added by Ankit on dt 18-FEB-2016 : PAN VALIDATION WITH NSDL
		form.setForm6061Checked(String.valueOf(customerInfo.getForm6061checked()));
		form.setIsPanValidated(customerInfo.getIsPanValidated());
		form.setPanValGenParamFlag(customerInfo.getPanValGenParamFlagValue());
		//END
		
		//Added by Nilkanth on dt 29-JULY-2022 : LEI VALIDATION WITH CCIL
		form.setIsLeiValidated(customerInfo.getIsLeiValidated());
		form.setLeiValGenParamFlag(customerInfo.getLeiValGenParamFlag());
		
		form.setDeferLEI(StringUtils.isBlank(customerInfo.getCMSLegalEntity().getDeferLEI())?ICMSConstant.NO:customerInfo.getCMSLegalEntity().getDeferLEI());
//		form.setIsLeiValidated(customerInfo.getCMSLegalEntity().getIsLeiValidated());
		form.setLeiValGenParamFlag(customerInfo.getCMSLegalEntity().getLeiValGenParamFlag());
		//END
		
		// form.setRegion(customerInfo.getCMSLegalEntity().getRegion());
		// form.setCountry(customerInfo.getCMSLegalEntity().getCountry());
		// form.setState(customerInfo.getCMSLegalEntity().getState());
		// form.setState(customerInfo.getCMSLegalEntity().getState());
		// form.setTelephoneNo(customerInfo.getCMSLegalEntity().getTelephoneNo());
		form.setSubLine(customerInfo.getSubLine());
		form.setExceptionalCases(customerInfo.getExceptionalCases());
		form.setBankingMethod(customerInfo
				.getBankingMethod());
		form.setFinalBankMethodList(customerInfo
				.getFinalBankMethodList());
		if(customerInfo.getTotalFundedLimit()==null || "".equals(customerInfo.getTotalFundedLimit()))
		{
			form.setTotalFundedLimit("0.00");
		}else{
//		form.setTotalFundedLimit(customerInfo
//				.getTotalFundedLimit());
			
			form.setTotalFundedLimit(totalFundedLimit1);
		}
		if(customerInfo.getTotalNonFundedLimit()==null || "".equals(customerInfo.getTotalNonFundedLimit()))
		{
			form.setTotalNonFundedLimit("0.00");
		}
		else{
//		form.setTotalNonFundedLimit(customerInfo
//				.getTotalNonFundedLimit());
			form.setTotalNonFundedLimit(totalNonFundedLimit1);
		}
		form.setFundedSharePercent(customerInfo
				.getFundedSharePercent());
		
		form.setNonFundedSharePercent(customerInfo
				.getNonFundedSharePercent());
		if(customerInfo.getMemoExposure()==null || "".equals(customerInfo.getMemoExposure()))
		{
			form.setMemoExposure("0.00");
		}
		else 
		{
//		form.setMemoExposure(customerInfo
//						.getMemoExposure());
			form.setMemoExposure(memoExposure1);
		}
//		form.setTotalSanctionedLimit(customerInfo
//				.getTotalSanctionedLimit());
		form.setMpbf(UIUtil.formatWithCommaAndDecimal(customerInfo.getMpbf()));
//		form.setFundedShareLimit(customerInfo
//				.getFundedShareLimit());
//		form.setNonFundedShareLimit(customerInfo
//				.getNonFundedShareLimit());
		
		form.setTotalSanctionedLimit(totalSanctLimit1);
		form.setFundedShareLimit(fundedShareLimit1);
		form.setNonFundedShareLimit(nonFundedShareLimit1);
		
		
		//Add by Shiv 200811
		
		ICriInfo criInfo[] = customerInfo.getCMSLegalEntity().getCriList();
		
		
		if(criInfo != null) {
			form.setCustomerRamID(criInfo[0].getCustomerRamID());                               
			form.setCustomerAprCode(criInfo[0].getCustomerAprCode());                           
			form.setCustomerExtRating(criInfo[0].getCustomerExtRating());                       
			form.setIsNbfs(	criInfo[0].getIsNbfs());                                            
			form.setNbfsA(criInfo[0].getNbfsA());                                               
			form.setNbfsB(criInfo[0].getNbfsB());                                               
			form.setIsPrioritySector(criInfo[0].getIsPrioritySector());                         
			form.setMsmeClassification(criInfo[0].getMsmeClassification());                     
			form.setIsPermenentSsiCert(criInfo[0].getIsPermenentSsiCert());                     
			form.setIsWeakerSection(criInfo[0].getIsWeakerSection());                           
			form.setWeakerSection(criInfo[0].getWeakerSection()); 
			form.setGovtSchemeType(criInfo[0].getGovtSchemeType()); 
			form.setIsKisanCreditCard(criInfo[0].getIsKisanCreditCard());                       
			form.setIsMinorityCommunity(criInfo[0].getIsMinorityCommunity());                   
			form.setMinorityCommunity(criInfo[0].getMinorityCommunity());                       
			form.setIsCapitalMarketExpos(criInfo[0].getIsCapitalMarketExpos());                 
			form.setIsRealEstateExpos(criInfo[0].getIsRealEstateExpos());                       
			form.setRealEstateExposType(criInfo[0].getRealEstateExposType());                   
			form.setRealEstateExposComm(criInfo[0].getRealEstateExposComm());                   
			form.setIsCommoditiesExposer(criInfo[0].getIsCommoditiesExposer());                 
			form.setIsSensitive(criInfo[0].getIsSensitive());                                   
			form.setCommoditiesName(criInfo[0].getCommoditiesName());                           
		//	form.setGrossInvsInPM(criInfo[0].getGrossInvsInPM());                               
		//	form.setGrossInvsInEquip(criInfo[0].getGrossInvsInEquip());                         
			form.setPsu(criInfo[0].getPsu()); 
			/*form.setGroupExposer(criInfo[0].getGroupExposer());*/
			form.setPsuPercentage(criInfo[0].getPsuPercentage());                               
			form.setGovtUnderTaking(criInfo[0].getGovtUnderTaking());                           
			form.setIsProjectFinance(criInfo[0].getIsProjectFinance());                         
			form.setIsInfrastructureFinanace(criInfo[0].getIsInfrastructureFinanace());         
			form.setInfrastructureFinanaceType(criInfo[0].getInfrastructureFinanaceType());     
			form.setIsSemsGuideApplicable(criInfo[0].getIsSemsGuideApplicable());               
			form.setIsFailIfcExcluList(criInfo[0].getIsFailIfcExcluList());                     
			form.setIsTufs(criInfo[0].getIsTufs());                                             
			form.setIsRbiDefaulter(criInfo[0].getIsRbiDefaulter());                             
			form.setRbiDefaulter(criInfo[0].getRbiDefaulter());                                 
			form.setIsLitigation(criInfo[0].getIsLitigation());                                 
			form.setLitigationBy(criInfo[0].getLitigationBy());                                 
			form.setIsInterestOfBank(criInfo[0].getIsInterestOfBank());                         
			form.setInterestOfBank(criInfo[0].getInterestOfBank());                             
			form.setIsAdverseRemark(criInfo[0].getIsAdverseRemark());                           
			form.setAdverseRemark(criInfo[0].getAdverseRemark());                               
			form.setAuditType(criInfo[0].getAuditType());                                       
		//	form.setAvgAnnualTurnover(criInfo[0].getAvgAnnualTurnover());         //              
			form.setIndustryExposer(criInfo[0].getIndustryExposer());          //                 
			form.setIsDirecOtherBank(criInfo[0].getIsDirecOtherBank());                         
			form.setDirecOtherBank(criInfo[0].getDirecOtherBank());                             
			form.setIsPartnerOtherBank(criInfo[0].getIsPartnerOtherBank());                     
			form.setPartnerOtherBank(criInfo[0].getPartnerOtherBank());                         
			form.setIsSubstantialOtherBank(criInfo[0].getIsSubstantialOtherBank());             
			form.setSubstantialOtherBank(criInfo[0].getSubstantialOtherBank());                 
			form.setIsHdfcDirecRltv(criInfo[0].getIsHdfcDirecRltv());                           
			form.setHdfcDirecRltv(criInfo[0].getHdfcDirecRltv());                               
			form.setIsObkDirecRltv(criInfo[0].getIsObkDirecRltv());                             
			form.setObkDirecRltv(criInfo[0].getObkDirecRltv());                                 
			form.setIsPartnerDirecRltv(criInfo[0].getIsPartnerDirecRltv());                     
			form.setPartnerDirecRltv(criInfo[0].getPartnerDirecRltv());                         
			form.setIsSubstantialRltvHdfcOther(criInfo[0].getIsSubstantialRltvHdfcOther());     
			form.setSubstantialRltvHdfcOther(criInfo[0].getSubstantialRltvHdfcOther());         
			form.setDirecHdfcOther(criInfo[0].getDirecHdfcOther());
			form.setIsBackedByGovt(criInfo[0].getIsBackedByGovt());
			form.setFirstYear(criInfo[0].getFirstYear());
//			form.setFirstYearTurnover(criInfo[0].getFirstYearTurnover());
			form.setFirstYearTurnoverCurr(criInfo[0].getFirstYearTurnoverCurr());
			form.setSecondYear(criInfo[0].getSecondYear());
			//form.setSecondYearTurnover(criInfo[0].getSecondYearTurnover());
			form.setSecondYearTurnoverCurr(criInfo[0].getSecondYearTurnoverCurr());
			form.setThirdYear(criInfo[0].getThirdYear());
			//form.setThirdYearTurnover(criInfo[0].getThirdYearTurnover());
			form.setThirdYearTurnoverCurr(criInfo[0].getThirdYearTurnoverCurr());
			// CRI Fields  - start
			form.setCategoryOfFarmer(criInfo[0].getCategoryOfFarmer());
			form.setEntityType(criInfo[0].getEntityType());
			form.setIsSPVFunding(criInfo[0].getIsSPVFunding());
			form.setIndirectCountryRiskExposure(criInfo[0].getIndirectCountryRiskExposure());
			form.setSalesPercentage(criInfo[0].getSalesPercentage());
			
			form.setIsCGTMSE(criInfo[0].getIsCGTMSE());
			form.setIsIPRE(criInfo[0].getIsIPRE());
			form.setFinanceForAccquisition(criInfo[0].getFinanceForAccquisition());
			
			form.setFacilityApproved(criInfo[0].getFacilityApproved());
			
			form.setFacilityAmount(criInfo[0].getFacilityAmount());
			
			form.setSecurityName(criInfo[0].getSecurityName());
			
			
			form.setRestrictedListIndustries(criInfo[0].getRestrictedListIndustries());
			
			
			
			
			form.setSecurityValue(criInfo[0].getSecurityValue());
			
			form.setCompany(criInfo[0].getCompany());
			
			form.setNameOfHoldingCompany(criInfo[0].getNameOfHoldingCompany());
		
			form.setType(criInfo[0].getType());
			
			form.setCompanyType(criInfo[0].getCompanyType());
			
			form.setIfBreachWithPrescriptions(criInfo[0].getIfBreachWithPrescriptions());
			
			form.setComments(criInfo[0].getComments());
			form.setLandHolding(criInfo[0].getLandHolding());
			
			form.setCountryOfGuarantor(criInfo[0].getCountryOfGuarantor());
			
			
			form.setIsAffordableHousingFinance(criInfo[0].getIsAffordableHousingFinance());
			
			form.setIsInRestrictedList(criInfo[0].getIsInRestrictedList());
			
			form.setRestrictedFinancing(criInfo[0].getRestrictedFinancing());
			
			form.setRestrictedIndustries(criInfo[0].getRestrictedIndustries());
			
			form.setIsQualifyingNotesPresent(criInfo[0].getIsQualifyingNotesPresent());
			
			form.setStateImplications(criInfo[0].getStateImplications());
			
			form.setIsBorrowerInRejectDatabase(criInfo[0].getIsBorrowerInRejectDatabase());
			
			form.setRejectHistoryReason(criInfo[0].getRejectHistoryReason());
			
			form.setCapitalForCommodityAndExchange(criInfo[0].getCapitalForCommodityAndExchange());
			//form.setBrokerType(criInfo[0].getBrokerType());
			
			/*form.setOdfdCategory(criInfo[0].getOdfdCategory());*/
			
			if("Broker".equals(criInfo[0].getCapitalForCommodityAndExchange())){
				form.setIsBrokerTypeShare(StringUtils.isBlank(criInfo[0].getIsBrokerTypeShare())?ICMSConstant.NO:criInfo[0].getIsBrokerTypeShare());
				form.setIsBrokerTypeCommodity(StringUtils.isBlank(criInfo[0].getIsBrokerTypeCommodity())?ICMSConstant.NO:criInfo[0].getIsBrokerTypeCommodity());
			}
			
			form.setObjectFinance(criInfo[0].getObjectFinance());
			
			form.setIsCommodityFinanceCustomer(criInfo[0].getIsCommodityFinanceCustomer());
			
			form.setRestructuedBorrowerOrFacility(criInfo[0].getRestructuedBorrowerOrFacility());
			
			form.setCriCountryName(criInfo[0].getCriCountryName());
			
			form.setFacility(criInfo[0].getFacility());
			
			form.setLimitAmount(criInfo[0].getLimitAmount());
			
			form.setConductOfAccountWithOtherBanks(criInfo[0].getConductOfAccountWithOtherBanks());
			
			form.setCrilicStatus(criInfo[0].getCrilicStatus());
			
			form.setComment(criInfo[0].getComment());
			form.setSubsidyFlag(criInfo[0].getSubsidyFlag());
			
			form.setHoldingCompnay(criInfo[0].getHoldingCompnay());
			
			
			form.setCautionList(criInfo[0].getCautionList());
			
			
			form.setDateOfCautionList(criInfo[0].getDateOfCautionList());
			
			form.setDirectors(criInfo[0].getDirectors());
			
			form.setGroupCompanies(criInfo[0].getGroupCompanies());
			
			
			form.setDefaultersList(criInfo[0].getDefaultersList());
			
			
			form.setRbiCompany(criInfo[0].getRbiCompany());
			
			
			form.setRestrictedListIndustries(criInfo[0].getRestrictedListIndustries());
			
			form.setRbiDirectors(criInfo[0].getRbiDirectors());

			form.setRbiGroupCompanies(criInfo[0].getRbiGroupCompanies());
			
			form.setRbiDateOfCautionList(criInfo[0].getRbiDateOfCautionList());
			
			
			form.setSecurityType(criInfo[0].getSecurityType());
			
			
			form.setCommericialRealEstate(criInfo[0].getCommericialRealEstate());
			
			form.setCommericialRealEstateValue(criInfo[0].getCommericialRealEstateValue());
			
			
			
	
			
			form.setCommericialRealEstateResidentialHousing(criInfo[0].getCommericialRealEstateResidentialHousing());
			
			form.setResidentialRealEstate(criInfo[0].getResidentialRealEstate());
			
			form.setIndirectRealEstate(criInfo[0].getIndirectRealEstate());
			
			form.setCommericialRealEstateValue(criInfo[0].getCommericialRealEstateValue());
		
		
			form.setCommericialRealEstate(criInfo[0].getCommericialRealEstate());
			
			
			//Phase 3 CR:comma separated
			String grossInvsInPM = criInfo[0].getGrossInvsInPM();
			String grossInvsInPM1=UIUtil.formatWithCommaAndDecimal(grossInvsInPM);
			form.setGrossInvsInPM(grossInvsInPM1);    
			
			String grossInvsInEquip = criInfo[0].getGrossInvsInEquip();
			String grossInvsInEquip1=UIUtil.formatWithCommaAndDecimal(grossInvsInEquip);
			form.setGrossInvsInEquip(grossInvsInEquip1);   
			
			String avgAnnualTurnover = criInfo[0].getAvgAnnualTurnover();
			String avgAnnualTurnover1=UIUtil.formatWithCommaAndDecimal(avgAnnualTurnover);
			form.setAvgAnnualTurnover(avgAnnualTurnover1);  
			
			String firstYearTurnover = criInfo[0].getFirstYearTurnover();
			String firstYearTurnover1=UIUtil.formatWithCommaAndDecimal(firstYearTurnover);
			form.setFirstYearTurnover(firstYearTurnover1);
			
			String secondYearTurnover = criInfo[0].getSecondYearTurnover();
			String secondYearTurnover1=UIUtil.formatWithCommaAndDecimal(secondYearTurnover);
			form.setSecondYearTurnover(secondYearTurnover1);
			
			String thirdYearTurnover = criInfo[0].getThirdYearTurnover();
			String thirdYearTurnover1=UIUtil.formatWithCommaAndDecimal(thirdYearTurnover);
			form.setThirdYearTurnover(thirdYearTurnover1);
			

			//Ram ratig CR.
			form.setCustomerFyClouser(criInfo[0].getCustomerFyClouser());
			
			CustomerDAO  customerDao = new CustomerDAO(); 
			String camType = customerDao.getCamtypeForParty(form.getLegalName());
			form.setCamTypePartyLevel(camType);
		}
		
		
		ICMSCustomerUdf[] udfDataList = customerInfo.getCMSLegalEntity().getUdfData();
		ICMSCustomerUdf udfData;
		if (udfDataList != null && udfDataList.length > 0) {
			udfData = udfDataList[0];
            form.setUdf1(udfData.getUdf1());
            form.setUdf2(udfData.getUdf2());
            form.setUdf3(udfData.getUdf3());
            form.setUdf4(udfData.getUdf4());
            form.setUdf5(udfData.getUdf5());
            form.setUdf6(udfData.getUdf6());
            form.setUdf7(udfData.getUdf7());
            form.setUdf8(udfData.getUdf8());
            form.setUdf9(udfData.getUdf9());
            form.setUdf10(udfData.getUdf10());
            form.setUdf11(udfData.getUdf11());
            form.setUdf12(udfData.getUdf12());
            form.setUdf13(udfData.getUdf13());
            form.setUdf14(udfData.getUdf14());
            form.setUdf15(udfData.getUdf15());
            form.setUdf16(udfData.getUdf16());
            form.setUdf17(udfData.getUdf17());
            form.setUdf18(udfData.getUdf18());
            form.setUdf19(udfData.getUdf19());
            form.setUdf20(udfData.getUdf20());
            form.setUdf21(udfData.getUdf21());
            form.setUdf22(udfData.getUdf22());
            form.setUdf23(udfData.getUdf23());
            form.setUdf24(udfData.getUdf24());
            form.setUdf25(udfData.getUdf25());
            form.setUdf26(udfData.getUdf26());
            form.setUdf27(udfData.getUdf27());
            form.setUdf28(udfData.getUdf28());
            form.setUdf29(udfData.getUdf29());
            form.setUdf30(udfData.getUdf30());
            form.setUdf31(udfData.getUdf31());
            form.setUdf32(udfData.getUdf32());
            form.setUdf33(udfData.getUdf33());
            form.setUdf34(udfData.getUdf34());
            form.setUdf35(udfData.getUdf35());
            form.setUdf36(udfData.getUdf36());
            form.setUdf37(udfData.getUdf37());
            form.setUdf38(udfData.getUdf38());
            form.setUdf39(udfData.getUdf39());
            form.setUdf40(udfData.getUdf40());
            form.setUdf41(udfData.getUdf41());
            form.setUdf42(udfData.getUdf42());
            form.setUdf43(udfData.getUdf43());
            form.setUdf44(udfData.getUdf44());
            form.setUdf45(udfData.getUdf45());
            form.setUdf46(udfData.getUdf46());
            form.setUdf47(udfData.getUdf47());
            form.setUdf48(udfData.getUdf48());
            form.setUdf49(udfData.getUdf49());
            form.setUdf50(udfData.getUdf50());
		    form.setUdf51(udfData.getUdf51());
            form.setUdf52(udfData.getUdf52());
            form.setUdf53(udfData.getUdf53());
            form.setUdf54(udfData.getUdf54());
            form.setUdf55(udfData.getUdf55());
            form.setUdf56(udfData.getUdf56());
            form.setUdf57(udfData.getUdf57());
            form.setUdf58(udfData.getUdf58());
            form.setUdf59(udfData.getUdf59());
            form.setUdf60(udfData.getUdf60());
            form.setUdf61(udfData.getUdf61());
            form.setUdf62(udfData.getUdf62());
            form.setUdf63(udfData.getUdf63());
            form.setUdf64(udfData.getUdf64());
            form.setUdf65(udfData.getUdf65());
            form.setUdf66(udfData.getUdf66());
            form.setUdf67(udfData.getUdf67());
            form.setUdf68(udfData.getUdf68());
            form.setUdf69(udfData.getUdf69());
            form.setUdf70(udfData.getUdf70());
            form.setUdf71(udfData.getUdf71());
            form.setUdf72(udfData.getUdf72());
            form.setUdf73(udfData.getUdf73());
            form.setUdf74(udfData.getUdf74());
            form.setUdf75(udfData.getUdf75());
            form.setUdf76(udfData.getUdf76());
            form.setUdf77(udfData.getUdf77());
            form.setUdf78(udfData.getUdf78());
            form.setUdf79(udfData.getUdf79());
            form.setUdf80(udfData.getUdf80());
            form.setUdf81(udfData.getUdf81());
            form.setUdf82(udfData.getUdf82());
            form.setUdf83(udfData.getUdf83());
            form.setUdf84(udfData.getUdf84());
            form.setUdf85(udfData.getUdf85());
            form.setUdf86(udfData.getUdf86());
            form.setUdf87(udfData.getUdf87());
            form.setUdf88(udfData.getUdf88());
            form.setUdf89(udfData.getUdf89());
            form.setUdf90(udfData.getUdf90());
            form.setUdf91(udfData.getUdf91());
            form.setUdf92(udfData.getUdf92());
            form.setUdf93(udfData.getUdf93());
            form.setUdf94(udfData.getUdf94());
            form.setUdf95(udfData.getUdf95());
            form.setUdf96(udfData.getUdf96());
            form.setUdf97(udfData.getUdf97());
            form.setUdf98(udfData.getUdf98());
            form.setUdf99(udfData.getUdf99());
            form.setUdf100(udfData.getUdf100());
            form.setUdfId(udfData.getId());
		}
		
		/*//Shiv 290811
		ICriFac facilityList[] = customerInfo.getCMSLegalEntity().getCriFacList();
		List facList = new ArrayList();
		if (facilityList != null) {
			for (int i = 0; i < facilityList.length; i++) {
				facList.add(facilityList[i]);

			}
		}
		
		form.setCriFacility(facList);*/

		/*
		 * form.setOtherSystem(customerInfo.getCMSLegalEntity().getOtherSystem())
		 * ;
		 */


		
		
		 //Start:Uma Khot:CRI Field addition enhancement
		form.setIsRBIWilfulDefaultersList(customerInfo.getIsRBIWilfulDefaultersList());
		form.setNameOfBank(customerInfo.getNameOfBank());
		form.setIsDirectorMoreThanOne(customerInfo.getIsDirectorMoreThanOne());
		form.setNameOfDirectorsAndCompany(customerInfo.getNameOfDirectorsAndCompany());
		form.setIsBorrDefaulterWithBank(customerInfo.getIsBorrDefaulterWithBank());
		form.setDetailsOfDefault(customerInfo.getDetailsOfDefault());
		form.setExtOfCompromiseAndWriteoff(customerInfo.getExtOfCompromiseAndWriteoff());
		form.setIsCibilStatusClean(customerInfo.getIsCibilStatusClean());
		form.setDetailsOfCleanCibil(customerInfo.getDetailsOfCleanCibil());
		  //End:Uma Khot:CRI Field addition enhancement
		
		form.setIncomeRange(CommonDataSingleton.getCodeCategoryLabelByValue(
				CategoryCodeConstant.INCOME_RANGE, customerInfo
						.getCMSLegalEntity().getIncomeRange()));
		DefaultLogger.debug(this, "Values Set ");
//		DefaultLogger.debug(this, "getCustomerNameShort:: "
//				+ form.getCustomerNameShort());
//		DefaultLogger.debug(this, "getCifId:: " + form.getCifId());
//		DefaultLogger.debug(this, "getCustomerNameLong:: "
//				+ form.getCustomerNameLong());
//		DefaultLogger.debug(this, "getShortName:: "
//				+ form.getCustomerNameShort());
		DefaultLogger.debug(this, "getCountryOfInCorporation:: "
				+ form.getIncorporatedCountry());
		DefaultLogger
				.debug(this, "getCustomerType:: " + form.getCustomerType());
		DefaultLogger.debug(this, "getDomicileCountry:: "
				+ form.getDomicileCountry());
		DefaultLogger.debug(this, "getBlackListedInd:: "
				+ form.getBlacklisted());
		DefaultLogger.debug(this, "getRelationshipStartDate:: "
				+ form.getRelationshipStartDate());
		DefaultLogger.debug(this, "getLegalConstitution:: "
				+ form.getLegalConstitution());
		DefaultLogger
				.debug(this, "getCustomerType:: " + form.getCustomerType());

	mapCoBorrowerDetailsOBToForm(form, customerInfo, inputs);
		return form;
	}

	private static void mapCoBorrowerDetailsFormToOB(ManualInputCustomerInfoForm form, ICMSCustomer customerInfo, HashMap map) throws MapperException {
		List<ICoBorrowerDetails> coBorrowerDetailsList =  (List<ICoBorrowerDetails>) map.get(SESSION_CO_BORROWER_DETAILS_KEY);
		if(customerInfo.getCMSLegalEntity() == null)
			return;
		
		if(YES.equals(form.getCoBorrowerDetailsInd())){
			customerInfo.getCMSLegalEntity().setCoBorrowerDetailsInd(YES);
		}else {
			customerInfo.getCMSLegalEntity().setCoBorrowerDetailsInd(NO);
		}

		if(!CollectionUtils.isEmpty(coBorrowerDetailsList)) {
			customerInfo.getCMSLegalEntity().setCoBorrowerDetails(coBorrowerDetailsList);
		}
	}

	private static void mapCoBorrowerDetailsOBToForm(ManualInputCustomerInfoForm form, ICMSCustomer customerInfo, HashMap map) throws MapperException {
		if(customerInfo!=null && customerInfo.getCMSLegalEntity()!=null ) {
			String coBorrowerDetailsInd = customerInfo.getCMSLegalEntity().getCoBorrowerDetailsInd();
			form.setCoBorrowerDetailsInd(coBorrowerDetailsInd==null ? NO : coBorrowerDetailsInd);
			
			if(!CollectionUtils.isEmpty(customerInfo.getCMSLegalEntity().getCoBorrowerDetails())) {
				List<ICoBorrowerDetails> coBorrowerDetailsList = customerInfo.getCMSLegalEntity().getCoBorrowerDetails();
				List<CoBorrowerDetailsForm> formList = new ArrayList<CoBorrowerDetailsForm>();
				StringBuilder coBorrowerLiabIdList = new StringBuilder();
				
				for(ICoBorrowerDetails coBorrowerDetails : coBorrowerDetailsList) {
					CoBorrowerDetailsForm coBorrowerDetailform = new CoBorrowerDetailsForm();
					
					coBorrowerDetailform.setCoBorrowerLiabId(coBorrowerDetails.getCoBorrowerLiabId());
					coBorrowerDetailform.setCoBorrowerName(coBorrowerDetails.getCoBorrowerName());

					coBorrowerDetailform.setIsInterfaced("N");
					formList.add(coBorrowerDetailform);
					coBorrowerLiabIdList.append(coBorrowerDetails.getCoBorrowerLiabId()).append(",");
				}
				form.setCoBorrowerDetails(formList);
				form.setCoBorrowerLiabIdList(coBorrowerLiabIdList.toString());
				form.setCoBorrowerDetailsInd(YES);
			}else {
				form.setCoBorrowerDetails(null);
				form.setCoBorrowerLiabIdList("");
			}
		}
	}

	public String[][] getParameterDescriptor() {
		DefaultLogger.debug(this,
				"Entering getParameterDescriptor of ManualInputCustomerMapper");
		return (new String[][] {
	// { IGlobalConstant.USER_TEAM,
				// "com.integrosys.component.bizstructure.app.bus.ITeam",
				// GLOBAL_SCOPE },
				// { IGlobalConstant.USER,
				// "com.integrosys.component.user.app.bus.ICommonUser",
				// GLOBAL_SCOPE },

				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "OBCMSCustomer", "com.integrosys.cms.app.customer.bus.ICMSCustomer", SERVICE_SCOPE },
				{ SESSION_CUSTOMER, ICMSCustomer.class.getName(), SERVICE_SCOPE},
				{ SESSION_CO_BORROWER_DETAILS_KEY, List.class.getName(), SERVICE_SCOPE}
		});
	}

}
