package com.integrosys.cms.app.ws.dto;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.collateralNewMaster.bus.ICollateralNewMaster;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.SQLParameter;
import com.integrosys.cms.app.commoncodeentry.bus.ICommonCodeEntry;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.CustomerException;
import com.integrosys.cms.app.customer.bus.IBankingMethod;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICMSCustomerUdf;
import com.integrosys.cms.app.customer.bus.ICMSLegalEntity;
import com.integrosys.cms.app.customer.bus.ICoBorrowerDetails;
import com.integrosys.cms.app.customer.bus.IContact;
import com.integrosys.cms.app.customer.bus.ICriInfo;
import com.integrosys.cms.app.customer.bus.ICustomerDAO;
import com.integrosys.cms.app.customer.bus.IDirector;
import com.integrosys.cms.app.customer.bus.ISystem;
import com.integrosys.cms.app.customer.bus.OBBankingMethod;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.customer.bus.OBCMSCustomerUdf;
import com.integrosys.cms.app.customer.bus.OBCMSLegalEntity;
import com.integrosys.cms.app.customer.bus.OBCoBorrowerDetails;
import com.integrosys.cms.app.customer.bus.OBContact;
import com.integrosys.cms.app.customer.bus.OBCriInfo;
import com.integrosys.cms.app.customer.bus.OBDirector;
import com.integrosys.cms.app.customer.bus.OBSystem;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.facilityNewMaster.bus.IFacilityNewMaster;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedEntry;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.region.bus.IRegionDAO;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.partygroup.bus.IPartyGroup;
import com.integrosys.cms.app.relationshipmgr.bus.IRelationshipMgrDAO;
import com.integrosys.cms.app.systemBank.bus.ISystemBank;
import com.integrosys.cms.app.systemBank.bus.OBSystemBank;
import com.integrosys.cms.app.systemBank.proxy.ISystemBankProxyManager;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.app.udf.bus.OBUdf;
import com.integrosys.cms.app.ws.jax.common.CMSException;
import com.integrosys.cms.app.ws.jax.common.CMSValidationFault;
import com.integrosys.cms.app.ws.jax.common.MasterAccessUtility;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.manualinput.customer.CoBorrowerDetailsForm;
import com.integrosys.cms.ui.manualinput.customer.ManualInputCustomerInfoForm;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;
import com.integrosys.cms.ui.relationshipmgr.IRelationshipMgr;

import com.integrosys.base.techinfra.validation.Validator;
import java.util.Locale;
import java.util.Calendar;


@Service
public class PartyDetailsDTOMapper {
	private static String WS_CALL_CREATE_PARTY;
	public PartyDetailsDTOMapper() {
	
		WS_CALL_CREATE_PARTY = PropertyManager.getValue("ws.call.createParty");

	}
	public static String DEFAULT_VALUE_FOR_CRI_INFO = "No";
	public static String DEFAULT_STATUS = "ACTIVE";
	public static String DEFAULT_CYCLE = "LAD";
	public static String DEFAULT_WILLFUL_STATUS = "0";
	public static String DEFAULT_SUBLINE = "CLOSE";
	public static String DEFAULT_UDF_VALUE = "REGULAR";
	public static String DEFAULT_NON_FUNDED_SHARE_PERCENT = "0";

	String errorCode = null;
	
	public ICMSCustomer getActualFromDTO(PartyDetailsRequestDTO requestDTO,ICMSCustomer customerInstance,String addOrUpdate) throws CMSValidationFault {
		
		SimpleDateFormat relationshipDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		relationshipDateFormat.setLenient(false);
		SimpleDateFormat relationshipDateFormatForLeiDate = new SimpleDateFormat("dd-MM-yyyy");
		relationshipDateFormatForLeiDate.setLenient(false);
		SimpleDateFormat cautionlistDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		cautionlistDateFormat.setLenient(false);
		SimpleDateFormat cautionDateFormat = new SimpleDateFormat("dd/MMM/yyyy");
		cautionDateFormat.setLenient(false);
		
		ICMSCustomer obCMSCustomerIntance = null;
		if(customerInstance!=null){
			obCMSCustomerIntance = customerInstance;
		}else{
			obCMSCustomerIntance = new OBCMSCustomer();
		}
		
		ICMSLegalEntity cmsLegalEntity = new OBCMSLegalEntity();
		
		if(requestDTO.getBusinessGroup()!=null && !requestDTO.getBusinessGroup().trim().isEmpty()){
			obCMSCustomerIntance.setPartyGroupName(requestDTO.getBusinessGroup().trim());	
		}else{
			obCMSCustomerIntance.setPartyGroupName(requestDTO.getBusinessGroup());	
		}
		
		if(requestDTO.getMainBranch()!=null && !requestDTO.getMainBranch().trim().isEmpty()){
			obCMSCustomerIntance.setMainBranch(requestDTO.getMainBranch().trim());
		}else{
			obCMSCustomerIntance.setMainBranch(requestDTO.getMainBranch());
		}
		
		if(requestDTO.getMainBranch()!=null && !requestDTO.getMainBranch().trim().isEmpty() && requestDTO.getMainBranch().trim().contains("-")){
			obCMSCustomerIntance.setBranchCode(requestDTO.getMainBranch().split("-")[0]);
		}else{
			obCMSCustomerIntance.setBranchCode("");
		}
		
		/*if(requestDTO.getRelationshipManager()!=null && !requestDTO.getRelationshipManager().trim().isEmpty()){
			obCMSCustomerIntance.setRelationshipMgr(requestDTO.getRelationshipManager().trim());
		}else{
			obCMSCustomerIntance.setRelationshipMgr(requestDTO.getRelationshipManager());
		}
		
		String rmRegion = "-";
		if(requestDTO.getRelationshipManager()!=null && !requestDTO.getRelationshipManager().trim().isEmpty()){
			try{
				Object relshpObj = masterAccessUtilityObj.getMaster("actualRelationshipMgr", new Long(requestDTO.getRelationshipManager().trim()));
				if(relshpObj!=null && !"".equals(relshpObj)){
					rmRegion = Long.toString(((IRelationshipMgr)relshpObj).getRegion().getIdRegion());
				}else{
					errors.add("relationshipMgrError",new ActionMessage("error.invalid.field.value"));
				}
			}catch (Exception e) {
				DefaultLogger.error(this, e.getMessage());
				errors.add("relationshipMgrError",new ActionMessage("error.invalid.field.value"));
			}
		}
		
		if(rmRegion!=null && !rmRegion.trim().isEmpty()){
			obCMSCustomerIntance.setRmRegion(rmRegion.trim());
		}else{
			obCMSCustomerIntance.setRmRegion(rmRegion);
		}*/
//		String rmMgrCode = requestDTO.getRelationshipManager();
//		String cpsId = requestDTO.getRelationshipManager();
//		String rmMgrCode = fetchRMDataRmMgrCode(cpsId);
		System.out.println("PartyDetailsDTOMapper.java=>Line 175=>requestDTO.getRelationshipMgrCode()=>"+requestDTO.getRelationshipMgrCode()+"*****");
		obCMSCustomerIntance.setRelationshipMgrEmpCode(requestDTO.getRelationshipMgrCode());
		
		List data =  fetchRMData(requestDTO.getRelationshipMgrCode());

		String relationshipMgr = "-";
		String rmRegion = "-";

		if(!data.isEmpty()) {
			relationshipMgr = (String) data.get(0);
			rmRegion = (String)data.get(1);
		}
		
	//	requestDTO.setRelationshipManager(relationshipMgr);
		
		IRelationshipMgrDAO relationshipMgrDAOImpl = (IRelationshipMgrDAO)BeanHouse.get("relationshipMgrDAO");
		if(null != requestDTO.getRelationshipManager() && !"".equals(requestDTO.getRelationshipManager())) {
//			IRelationshipMgr relationshipMgrObj = relationshipMgrDAOImpl.getRelationshipMgrByName(requestDTO.getRelationshipManager());
			IRelationshipMgr relationshipMgrObj = relationshipMgrDAOImpl.getRelationshipMgrByNameAndRMCode(relationshipMgr,requestDTO.getRelationshipMgrCode());
			if(relationshipMgrObj != null) {
				obCMSCustomerIntance.setRelationshipMgr(relationshipMgrObj.getId()+"");
			}else {
				obCMSCustomerIntance.setRelationshipMgr("");
			}
		}
		if(null != rmRegion && !"".equals(rmRegion)) {
			IRegion region = relationshipMgrDAOImpl.getRegionByRegionName(rmRegion);
			if(region != null) {
				obCMSCustomerIntance.setRmRegion(region.getIdRegion()+"");
			}else {
				obCMSCustomerIntance.setRmRegion("");
			}
		}		
		
		
		if(requestDTO.getEntity()!=null && !requestDTO.getEntity().trim().isEmpty()){
			obCMSCustomerIntance.setEntity(requestDTO.getEntity().trim());
		}else{
			obCMSCustomerIntance.setEntity(requestDTO.getEntity());
		}
		
		if(requestDTO.getPAN()!=null && !requestDTO.getPAN().trim().isEmpty()){
			obCMSCustomerIntance.setPan(requestDTO.getPAN().trim());
		}else{
			obCMSCustomerIntance.setPan(requestDTO.getPAN());
		}
		
		if(requestDTO.getRBIIndustryCode()!=null && !requestDTO.getRBIIndustryCode().trim().isEmpty()){
			obCMSCustomerIntance.setRbiIndustryCode(requestDTO.getRBIIndustryCode().trim());
		}else{
			obCMSCustomerIntance.setRbiIndustryCode(requestDTO.getRBIIndustryCode());
		}
		
		if(requestDTO.getIndustryName()!=null && !requestDTO.getIndustryName().trim().isEmpty()){
			obCMSCustomerIntance.setIndustryName(requestDTO.getIndustryName().trim());
		}else{
			obCMSCustomerIntance.setIndustryName(requestDTO.getIndustryName());
		}
		
		//Financial Details
		for(int i=0;i<requestDTO.getBankingMethodComboBoxList().size();i++) {
			if(requestDTO.getBankingMethodComboBoxList().get(i).getBankingMethod()!=null && !requestDTO.getBankingMethodComboBoxList().get(i).getBankingMethod().trim().isEmpty()){
				obCMSCustomerIntance.setBankingMethod(requestDTO.getBankingMethodComboBoxList().get(i).getBankingMethod().trim());
				obCMSCustomerIntance.setFinalBankMethodList(requestDTO.getBankingMethodComboBoxList().get(i).getBankingMethod().trim());
			}else{
				obCMSCustomerIntance.setBankingMethod("");
				obCMSCustomerIntance.setFinalBankMethodList("");
			}
		}
		if(requestDTO.getTotalFundedLimit()!=null && !requestDTO.getTotalFundedLimit().trim().isEmpty()){
			obCMSCustomerIntance.setTotalFundedLimit(requestDTO.getTotalFundedLimit().trim());
		}else{
			obCMSCustomerIntance.setTotalFundedLimit(requestDTO.getTotalFundedLimit());
		}
		
		if(requestDTO.getTotalNonFundedLimit()!=null && !requestDTO.getTotalNonFundedLimit().trim().isEmpty()){
			obCMSCustomerIntance.setTotalNonFundedLimit(requestDTO.getTotalNonFundedLimit().trim());
		}else{
			obCMSCustomerIntance.setTotalNonFundedLimit(requestDTO.getTotalNonFundedLimit());
		}
		
		if(requestDTO.getFundedSharePercent()!=null && !requestDTO.getFundedSharePercent().trim().isEmpty()){
			obCMSCustomerIntance.setFundedSharePercent(requestDTO.getFundedSharePercent().trim());
		}else{
			obCMSCustomerIntance.setFundedSharePercent(requestDTO.getFundedSharePercent());
		}
		
		if(requestDTO.getMemoExposure()!=null && !requestDTO.getMemoExposure().trim().isEmpty()){
			obCMSCustomerIntance.setMemoExposure(requestDTO.getMemoExposure().trim());
		}else{
			obCMSCustomerIntance.setMemoExposure(requestDTO.getMemoExposure());
		}
		
		if(requestDTO.getMpbf()!=null && !requestDTO.getMpbf().trim().isEmpty()){
			obCMSCustomerIntance.setMpbf(requestDTO.getMpbf().trim());
		}else{
			obCMSCustomerIntance.setMpbf(requestDTO.getMpbf());
		}
		
		if(requestDTO.getPartyName()!=null && !requestDTO.getPartyName().trim().isEmpty()){
			obCMSCustomerIntance.setCustomerNameUpper(requestDTO.getPartyName().trim());
			obCMSCustomerIntance.setCustomerName(requestDTO.getPartyName().trim());
		}else{
			obCMSCustomerIntance.setCustomerNameUpper(requestDTO.getPartyName());
			obCMSCustomerIntance.setCustomerName(requestDTO.getPartyName());
		}
		
		if(requestDTO.getSegment()!=null && !requestDTO.getSegment().trim().isEmpty()){
			obCMSCustomerIntance.setCustomerSegment(requestDTO.getSegment().trim());
		}else{
			obCMSCustomerIntance.setCustomerSegment(requestDTO.getSegment());
		}
		
		obCMSCustomerIntance.setStatus(DEFAULT_STATUS);
		obCMSCustomerIntance.setCycle(DEFAULT_CYCLE);
		obCMSCustomerIntance.setNonFundedSharePercent(DEFAULT_NON_FUNDED_SHARE_PERCENT);
		if("WS_create_customer".equals(requestDTO.getEvent())){
			obCMSCustomerIntance.setWillfulDefaultStatus(DEFAULT_WILLFUL_STATUS);
		}else{
			obCMSCustomerIntance.setWillfulDefaultStatus(obCMSCustomerIntance.getWillfulDefaultStatus());
		}
		obCMSCustomerIntance.setSubLine(DEFAULT_SUBLINE);
		
		Double totFundedLimit =(requestDTO.getTotalFundedLimit()!=null && !"".equals(requestDTO.getTotalFundedLimit().trim()) )?Double.parseDouble(requestDTO.getTotalFundedLimit().trim()):0d; 
		Double totNonFundedLimit =(requestDTO.getTotalNonFundedLimit()!=null && !"".equals(requestDTO.getTotalNonFundedLimit().trim()))?Double.parseDouble(requestDTO.getTotalNonFundedLimit().trim()):0d; 
		Double memoExposure =(requestDTO.getMemoExposure()!=null && !"".equals(requestDTO.getMemoExposure().trim())) ?Double.parseDouble(requestDTO.getMemoExposure().trim()):0d; 
		
		Double sancLimit = totFundedLimit + totNonFundedLimit + memoExposure; 
		
		obCMSCustomerIntance.setTotalSanctionedLimit(sancLimit.toString());
		
		if(totFundedLimit!=null && !"".equals(totFundedLimit)
				&& requestDTO.getFundedSharePercent()!=null && !"".equals(requestDTO.getFundedSharePercent().trim())){
			Double fundedShareLimit = totFundedLimit * Double.parseDouble(requestDTO.getFundedSharePercent().trim())/100;
			obCMSCustomerIntance.setFundedShareLimit(fundedShareLimit.toString());
		}else{
			obCMSCustomerIntance.setFundedShareLimit("0");
		}
		
		if(totNonFundedLimit!=null && !"".equals(totNonFundedLimit)){
			Double nonFundedShareLimit = totNonFundedLimit * Double.parseDouble(DEFAULT_NON_FUNDED_SHARE_PERCENT)/100;
			obCMSCustomerIntance.setNonFundedShareLimit(nonFundedShareLimit.toString());
		}else{
			obCMSCustomerIntance.setNonFundedShareLimit("0");
		}
		
		obCMSCustomerIntance.setDomicileCountry("IN");
		
		try {
			if (requestDTO.getRelationshipStartDate()!=null && !requestDTO.getRelationshipStartDate().trim().isEmpty()) {
				obCMSCustomerIntance.setCustomerRelationshipStartDate(relationshipDateFormat.parse(requestDTO.getRelationshipStartDate().trim()));
			}
		}catch (ParseException e) {
			e.printStackTrace();
		}
		//Santosh LEI CR 
		try {
			if (requestDTO.getLeiExpDate()!=null && !requestDTO.getLeiExpDate().trim().isEmpty()) {
				obCMSCustomerIntance.setLeiExpDate(relationshipDateFormat.parse(requestDTO.getLeiExpDate().trim()));
			}else{
				obCMSCustomerIntance.setLeiExpDate(null);
			}
		}catch (ParseException e) {
			e.printStackTrace();
		}
		
		if(requestDTO.getLeiCode()!=null && !requestDTO.getLeiCode().trim().isEmpty()){
			obCMSCustomerIntance.setLeiCode(requestDTO.getLeiCode().trim());
		}else{
			obCMSCustomerIntance.setLeiCode(requestDTO.getLeiCode());
		}
		
		obCMSCustomerIntance.setIsRBIWilfulDefaultersList(requestDTO.getIsRBIWilfulDefaultersList());
		 if("Yes".equals(requestDTO.getIsRBIWilfulDefaultersList())) {			 
			 obCMSCustomerIntance.setNameOfBank(requestDTO.getNameOfBank());
			 obCMSCustomerIntance.setIsDirectorMoreThanOne(requestDTO.getIsDirectorMoreThanOne());
			 if("Yes".equals(requestDTO.getIsDirectorMoreThanOne())) {
				 obCMSCustomerIntance.setNameOfDirectorsAndCompany(requestDTO.getNameOfDirectorsAndCompany());
			 }else {
				 obCMSCustomerIntance.setNameOfDirectorsAndCompany("");
			 }
		 }else {
			 obCMSCustomerIntance.setIsRBIWilfulDefaultersList(DEFAULT_VALUE_FOR_CRI_INFO);
			 obCMSCustomerIntance.setNameOfBank("");
			 obCMSCustomerIntance.setIsDirectorMoreThanOne("");
			 obCMSCustomerIntance.setNameOfDirectorsAndCompany("");
		 }
		 obCMSCustomerIntance.setIsCibilStatusClean(requestDTO.getIsCibilStatusClean());		 
		 if(requestDTO.getIsCibilStatusClean()!=null && "No".equals(requestDTO.getIsCibilStatusClean())) {
			 obCMSCustomerIntance.setDetailsOfCleanCibil(requestDTO.getDetailsOfCleanCibil());
		 }else {
			 obCMSCustomerIntance.setIsCibilStatusClean("Yes");
			 obCMSCustomerIntance.setDetailsOfCleanCibil("");
		 }	
		
		//cmsLegalEntity
		try {
			if (requestDTO.getLeiExpDate()!=null && !requestDTO.getLeiExpDate().trim().isEmpty()) {
				cmsLegalEntity.setLeiExpDate(relationshipDateFormat.parse(requestDTO.getLeiExpDate().trim()));
			}else{
				cmsLegalEntity.setLeiExpDate(null);
			}
		}catch (ParseException e) {
			e.printStackTrace();
		}
		
		if(requestDTO.getLeiCode()!=null && !requestDTO.getLeiCode().trim().isEmpty()){
			cmsLegalEntity.setLeiCode(requestDTO.getLeiCode().trim());
		}else{
			cmsLegalEntity.setLeiCode(requestDTO.getLeiCode());
		}
		//LEI CR
		if(requestDTO.getBusinessGroup()!=null && !requestDTO.getBusinessGroup().trim().isEmpty()){
			cmsLegalEntity.setPartyGroupName(requestDTO.getBusinessGroup().trim());	
		}else{
			cmsLegalEntity.setPartyGroupName(requestDTO.getBusinessGroup());	
		}
		
		if(requestDTO.getMainBranch()!=null && !requestDTO.getMainBranch().trim().isEmpty()){
			cmsLegalEntity.setMainBranch(requestDTO.getMainBranch().trim());
		}else{
			cmsLegalEntity.setMainBranch(requestDTO.getMainBranch());
		}
		
		if(requestDTO.getMainBranch()!=null && !requestDTO.getMainBranch().trim().isEmpty() && requestDTO.getMainBranch().trim().contains("-")){
			cmsLegalEntity.setBranchCode(requestDTO.getMainBranch().trim().split("-")[0]);
		}else{
			cmsLegalEntity.setBranchCode("");
		}
		
		if(requestDTO.getRelationshipManager()!=null && !requestDTO.getRelationshipManager().trim().isEmpty()){
			cmsLegalEntity.setRelationshipMgr(requestDTO.getRelationshipManager().trim());
		}else{
			cmsLegalEntity.setRelationshipMgr(requestDTO.getRelationshipManager());
		}
		
		if(rmRegion!=null && !rmRegion.trim().isEmpty()){
			cmsLegalEntity.setRmRegion(rmRegion.trim());
		}else{
			cmsLegalEntity.setRmRegion(rmRegion);
		}
		
		if(requestDTO.getEntity()!=null && !requestDTO.getEntity().trim().isEmpty()){
			cmsLegalEntity.setEntity(requestDTO.getEntity().trim());
		}else{
			cmsLegalEntity.setEntity(requestDTO.getEntity());
		}
		
		if(requestDTO.getPAN()!=null && !requestDTO.getPAN().trim().isEmpty()){
			cmsLegalEntity.setPan(requestDTO.getPAN().trim());
		}else{
			cmsLegalEntity.setPan(requestDTO.getPAN());
		}
		
		if(requestDTO.getRBIIndustryCode()!=null && !requestDTO.getRBIIndustryCode().trim().isEmpty()){
			cmsLegalEntity.setRbiIndustryCode(requestDTO.getRBIIndustryCode().trim());
		}else{
			cmsLegalEntity.setRbiIndustryCode(requestDTO.getRBIIndustryCode());
		}
		
		if(requestDTO.getIndustryName()!=null && !requestDTO.getIndustryName().trim().isEmpty()){
			cmsLegalEntity.setIndustryName(requestDTO.getIndustryName().trim());
		}else{
			cmsLegalEntity.setIndustryName(requestDTO.getIndustryName());
		}
		
		for(int i=0;i<requestDTO.getBankingMethodComboBoxList().size();i++) {
			if(requestDTO.getBankingMethodComboBoxList().get(i).getBankingMethod()!=null && !requestDTO.getBankingMethodComboBoxList().get(i).getBankingMethod().trim().isEmpty()){
				cmsLegalEntity.setBankingMethod(requestDTO.getBankingMethodComboBoxList().get(i).getBankingMethod().trim());
				cmsLegalEntity.setFinalBankMethodList(requestDTO.getBankingMethodComboBoxList().get(i).getBankingMethod().trim());
			}else{
				cmsLegalEntity.setBankingMethod("");
				cmsLegalEntity.setFinalBankMethodList("");
			}
		}
		
		if(requestDTO.getTotalFundedLimit()!=null && !requestDTO.getTotalFundedLimit().trim().isEmpty()){
			cmsLegalEntity.setTotalFundedLimit(requestDTO.getTotalFundedLimit().trim());
		}else{
			cmsLegalEntity.setTotalFundedLimit(requestDTO.getTotalFundedLimit());
		}
		
		if(requestDTO.getTotalNonFundedLimit()!=null && !requestDTO.getTotalNonFundedLimit().trim().isEmpty()){
			cmsLegalEntity.setTotalNonFundedLimit(requestDTO.getTotalNonFundedLimit().trim());
		}else{
			cmsLegalEntity.setTotalNonFundedLimit(requestDTO.getTotalNonFundedLimit());
		}
		
		if(requestDTO.getFundedSharePercent()!=null && !requestDTO.getFundedSharePercent().trim().isEmpty()){
			cmsLegalEntity.setFundedSharePercent(requestDTO.getFundedSharePercent().trim());
		}else{
			cmsLegalEntity.setFundedSharePercent(requestDTO.getFundedSharePercent());
		}
		
		if(requestDTO.getMemoExposure()!=null && !requestDTO.getMemoExposure().trim().isEmpty()){
			cmsLegalEntity.setMemoExposure(requestDTO.getMemoExposure().trim());
		}else{
			cmsLegalEntity.setMemoExposure(requestDTO.getMemoExposure());
		}
		
		if(requestDTO.getMpbf()!=null && !requestDTO.getMpbf().trim().isEmpty()){
			cmsLegalEntity.setMpbf(requestDTO.getMpbf().trim());
		}else{
			cmsLegalEntity.setMpbf(requestDTO.getMpbf());
		}
		
		if(requestDTO.getSegment()!=null && !requestDTO.getSegment().trim().isEmpty()){
			cmsLegalEntity.setCustomerSegment(requestDTO.getSegment().trim());
		}else{
			cmsLegalEntity.setCustomerSegment(requestDTO.getSegment());
		}
		
		cmsLegalEntity.setCycle(DEFAULT_CYCLE);
		cmsLegalEntity.setNonFundedSharePercent(DEFAULT_NON_FUNDED_SHARE_PERCENT);
		if("WS_create_customer".equals(requestDTO.getEvent())){
			cmsLegalEntity.setWillfulDefaultStatus(DEFAULT_WILLFUL_STATUS);
		}else{
			if(obCMSCustomerIntance.getCMSLegalEntity()!=null){
				cmsLegalEntity.setWillfulDefaultStatus(obCMSCustomerIntance.getCMSLegalEntity().getWillfulDefaultStatus());
			}
		}
		cmsLegalEntity.setSubLine(DEFAULT_SUBLINE);
		
		if(requestDTO.getPartyName()!=null && !requestDTO.getPartyName().trim().isEmpty()){
			cmsLegalEntity.setShortName(requestDTO.getPartyName().trim());
			cmsLegalEntity.setLegalName(requestDTO.getPartyName().trim());
		}else{
			cmsLegalEntity.setShortName(requestDTO.getPartyName());
			cmsLegalEntity.setLegalName(requestDTO.getPartyName());
		}
		
		try {
			if (requestDTO.getRelationshipStartDate()!=null && !requestDTO.getRelationshipStartDate().trim().isEmpty()) {
				cmsLegalEntity.setRelationshipStartDate(relationshipDateFormat.parse(requestDTO.getRelationshipStartDate().trim()));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		ICriInfo[] criInfoArr = new OBCriInfo[1];
		OBCriInfo obCriInfoInstance = new OBCriInfo();
		
		if(requestDTO.getCustomerRAMId()!=null && !requestDTO.getCustomerRAMId().trim().isEmpty()){
			obCriInfoInstance.setCustomerRamID(requestDTO.getCustomerRAMId().trim());
		}else{
			obCriInfoInstance.setCustomerRamID(requestDTO.getCustomerRAMId());
		}
		
		if(requestDTO.getCustomerAPRCode()!=null && !requestDTO.getCustomerAPRCode().trim().isEmpty()){
			obCriInfoInstance.setCustomerAprCode(requestDTO.getCustomerAPRCode().trim());
		}else{
			obCriInfoInstance.setCustomerAprCode(requestDTO.getCustomerAPRCode());
		}
		
		if(requestDTO.getCustomerExtRating()!=null && !requestDTO.getCustomerExtRating().trim().isEmpty()){
			obCriInfoInstance.setCustomerExtRating(requestDTO.getCustomerExtRating().trim());
		}else{
			obCriInfoInstance.setCustomerExtRating(requestDTO.getCustomerExtRating());
		}
		
		obCriInfoInstance.setIsNbfs(requestDTO.getNbfcFlag());
		if(requestDTO.getNbfcFlag()!=null && "Yes".equals(requestDTO.getNbfcFlag())){
			obCriInfoInstance.setNbfsA(requestDTO.getNbfcA());
			obCriInfoInstance.setNbfsB(requestDTO.getNbfcB());
		}
		
		obCriInfoInstance.setIsPrioritySector(requestDTO.getPrioritySector());
		obCriInfoInstance.setMsmeClassification(requestDTO.getMsmeClassification());
		obCriInfoInstance.setIsPermenentSsiCert(requestDTO.getPermSSICert());
		obCriInfoInstance.setIsWeakerSection(requestDTO.getWeakerSection());
		if(requestDTO.getWeakerSection()!=null && "Yes".equals(requestDTO.getWeakerSection())){
			obCriInfoInstance.setWeakerSection(requestDTO.getWeakerSectionType());
//			obCriInfoInstance.setGovtSchemeType(requestDTO.getWeakerSectionValue());
		}
		obCriInfoInstance.setIsKisanCreditCard(requestDTO.getKisanCreditCard());
		obCriInfoInstance.setIsMinorityCommunity(requestDTO.getMinorityCommunity());
		if(requestDTO.getMinorityCommunity()!=null && "Yes".equals(requestDTO.getMinorityCommunity())){
			obCriInfoInstance.setMinorityCommunity(requestDTO.getMinorityCommunityType());
		}
		obCriInfoInstance.setIsCapitalMarketExpos(requestDTO.getCapitalMarketExposure());
		obCriInfoInstance.setIsRealEstateExpos(requestDTO.getRealEstateExposure());
		obCriInfoInstance.setIsCommoditiesExposer(requestDTO.getCommodityExposure());
		obCriInfoInstance.setIsSensitive(requestDTO.getSensitive());
		obCriInfoInstance.setCommoditiesName(requestDTO.getCommodityName());
		if(requestDTO.getGrossInvestmentPM()!=null && !requestDTO.getGrossInvestmentPM().trim().isEmpty()){
			obCriInfoInstance.setGrossInvsInPM(requestDTO.getGrossInvestmentPM().trim());
		}else{
			obCriInfoInstance.setGrossInvsInPM("0");
		}
		if(requestDTO.getGrossInvestmentEquip()!=null && !requestDTO.getGrossInvestmentEquip().trim().isEmpty()){
			obCriInfoInstance.setGrossInvsInEquip(requestDTO.getGrossInvestmentEquip().trim());
		}else{
			obCriInfoInstance.setGrossInvsInEquip("0");
		}
		obCriInfoInstance.setPsu(requestDTO.getPsu());
		obCriInfoInstance.setPsuPercentage(requestDTO.getPercentageOfShareholding().trim());
		obCriInfoInstance.setGovtUnderTaking(requestDTO.getGovtUndertaking());
		obCriInfoInstance.setIsProjectFinance(requestDTO.getProjectFinance());
		obCriInfoInstance.setIsInfrastructureFinanace(requestDTO.getInfraFinance());
		obCriInfoInstance.setInfrastructureFinanaceType(requestDTO.getInfraFinanceType().trim());
		obCriInfoInstance.setIsSemsGuideApplicable(requestDTO.getSEMSGuideApplicable());
		obCriInfoInstance.setIsFailIfcExcluList(requestDTO.getFailsUnderIFCExclusion());
		obCriInfoInstance.setIsTufs(requestDTO.getTufs());
		obCriInfoInstance.setIsRbiDefaulter(requestDTO.getRBIDefaulterList());
		if(requestDTO.getRBIDefaulterList()!=null && "Yes".equals(requestDTO.getRBIDefaulterList())){
		//obCriInfoInstance.setRbiDefaulter(requestDTO.getRbiDefaulterListType());
			//Change for RBI defaulter Type i.e Company ,Director and Group Companies
			if(null!=requestDTO.getRbiDefaulterListTypeCompany() && !requestDTO.getRbiDefaulterListTypeCompany().trim().isEmpty()){
			obCriInfoInstance.setRbiDefaulter(requestDTO.getRbiDefaulterListTypeCompany());
			}
			else if(null!=requestDTO.getRbiDefaulterListTypeDirectors() && !requestDTO.getRbiDefaulterListTypeDirectors().trim().isEmpty()){
				obCriInfoInstance.setRbiDefaulter(requestDTO.getRbiDefaulterListTypeDirectors());
				}
			else if(null!=requestDTO.getRbiDefaulterListTypeGroupCompanies() && !requestDTO.getRbiDefaulterListTypeGroupCompanies().trim().isEmpty()){
				obCriInfoInstance.setRbiDefaulter(requestDTO.getRbiDefaulterListTypeGroupCompanies());
				}
		}
		obCriInfoInstance.setIsLitigation(requestDTO.getLitigationPending());
		if(requestDTO.getLitigationPending()!=null && "Yes".equals(requestDTO.getLitigationPending())){
			obCriInfoInstance.setLitigationBy(requestDTO.getLitigationPendingBy());
		}
		obCriInfoInstance.setIsInterestOfBank(requestDTO.getInterestOfDirectors());
		if(requestDTO.getInterestOfDirectors()!=null && "Yes".equals(requestDTO.getInterestOfDirectors())){
			obCriInfoInstance.setInterestOfBank(requestDTO.getInterestOfDirectorsType());
		}
		obCriInfoInstance.setIsAdverseRemark(requestDTO.getAdverseRemark());
		if(requestDTO.getAdverseRemark()!=null && "Yes".equals(requestDTO.getAdverseRemark())){
			obCriInfoInstance.setAdverseRemark(requestDTO.getAdverseRemarkValue());
		}
		
		if(requestDTO.getAudit()!=null && !requestDTO.getAudit().trim().isEmpty()){
			obCriInfoInstance.setAuditType(requestDTO.getAudit().trim());
		}else{
			obCriInfoInstance.setAuditType(requestDTO.getAudit());
		}
		
		if(requestDTO.getAvgAnnualTurnover()!=null && !requestDTO.getAvgAnnualTurnover().trim().isEmpty()){
			obCriInfoInstance.setAvgAnnualTurnover(requestDTO.getAvgAnnualTurnover().trim());
		}else{
			obCriInfoInstance.setAvgAnnualTurnover(requestDTO.getAvgAnnualTurnover());
		}
		
		if(requestDTO.getIndustryExposurePercent()!=null && !requestDTO.getIndustryExposurePercent().trim().isEmpty()){
			obCriInfoInstance.setIndustryExposer(requestDTO.getIndustryExposurePercent().trim());
		}else{
			obCriInfoInstance.setIndustryExposer("");
		}
//		obCriInfoInstance.setIndustryExposer(requestDTO.getBusinessGroupExposureLimit());
		
		obCriInfoInstance.setIsDirecOtherBank(requestDTO.getIsBorrowerDirector());
		obCriInfoInstance.setIsPartnerOtherBank(requestDTO.getIsBorrowerPartner());
		obCriInfoInstance.setIsSubstantialOtherBank(requestDTO.getIsDirectorOfOtherBank());
		obCriInfoInstance.setIsHdfcDirecRltv(requestDTO.getIsRelativeOfHDFCBank());
		obCriInfoInstance.setIsObkDirecRltv(requestDTO.getIsRelativeOfChairman());
		obCriInfoInstance.setIsPartnerDirecRltv(requestDTO.getIsPartnerRelativeOfBanks());
		obCriInfoInstance.setIsSubstantialRltvHdfcOther(requestDTO.getIsShareholderRelativeOfBank());
		
		if(requestDTO.getIsBorrowerDirector()!=null && "Yes".equals(requestDTO.getIsBorrowerDirector())){
			obCriInfoInstance.setDirecOtherBank(requestDTO.getBorrowerDirectorValue());
		}
		if(requestDTO.getIsBorrowerPartner()!=null && "Yes".equals(requestDTO.getIsBorrowerPartner())){
			obCriInfoInstance.setPartnerOtherBank(requestDTO.getBorrowerPartnerValue());
		}
		if(requestDTO.getIsDirectorOfOtherBank()!=null && "Yes".equals(requestDTO.getIsDirectorOfOtherBank())){
			obCriInfoInstance.setSubstantialOtherBank(requestDTO.getDirectorOfOtherBankValue());
		}
		if(requestDTO.getIsRelativeOfHDFCBank()!=null && "Yes".equals(requestDTO.getIsRelativeOfHDFCBank())){
			obCriInfoInstance.setHdfcDirecRltv(requestDTO.getRelativeOfHDFCBankValue());
		}
		if(requestDTO.getIsRelativeOfChairman()!=null && "Yes".equals(requestDTO.getIsRelativeOfChairman())){
			obCriInfoInstance.setObkDirecRltv(requestDTO.getRelativeOfChairmanValue());
		}
		if(requestDTO.getIsPartnerRelativeOfBanks()!=null && "Yes".equals(requestDTO.getIsPartnerRelativeOfBanks())){
			obCriInfoInstance.setPartnerDirecRltv(requestDTO.getPartnerRelativeOfBanksValue());
		}
		if(requestDTO.getIsShareholderRelativeOfBank()!=null && "Yes".equals(requestDTO.getIsShareholderRelativeOfBank())){
			obCriInfoInstance.setSubstantialRltvHdfcOther(requestDTO.getShareholderRelativeOfBankValue());
		}
		
		obCriInfoInstance.setIsBackedByGovt(requestDTO.getIsBackedByGovt());
		
		if(requestDTO.getFirstYear()!=null && !requestDTO.getFirstYear().trim().isEmpty()){
			obCriInfoInstance.setFirstYear(requestDTO.getFirstYear().trim());
		}else{
			obCriInfoInstance.setFirstYear(requestDTO.getFirstYear());
		}
		
		if(requestDTO.getFirstYearTurnover()!=null && !requestDTO.getFirstYearTurnover().trim().isEmpty()){
			obCriInfoInstance.setFirstYearTurnover(requestDTO.getFirstYearTurnover().trim());
		}else{
			obCriInfoInstance.setFirstYearTurnover(requestDTO.getFirstYearTurnover());
		}
		
		if(requestDTO.getTurnoverCurrency()!=null && !requestDTO.getTurnoverCurrency().trim().isEmpty()){
			obCriInfoInstance.setFirstYearTurnoverCurr(requestDTO.getTurnoverCurrency().trim());
			obCriInfoInstance.setSecondYearTurnoverCurr(requestDTO.getTurnoverCurrency().trim());
			obCriInfoInstance.setThirdYearTurnoverCurr(requestDTO.getTurnoverCurrency().trim());
		}else{
			obCriInfoInstance.setFirstYearTurnoverCurr(requestDTO.getTurnoverCurrency());
			obCriInfoInstance.setSecondYearTurnoverCurr(requestDTO.getTurnoverCurrency());
			obCriInfoInstance.setThirdYearTurnoverCurr(requestDTO.getTurnoverCurrency());
		}
		
		
		obCriInfoInstance.setIsSPVFunding(requestDTO.getIsSPVFunding());
		obCriInfoInstance.setIndirectCountryRiskExposure(requestDTO.getIndirectCountryRiskExposure());
		if(requestDTO.getIndirectCountryRiskExposure()!=null && "Yes".equals(requestDTO.getIndirectCountryRiskExposure())) {
			obCriInfoInstance.setCriCountryName(requestDTO.getCriCountryName());
			obCriInfoInstance.setSalesPercentage(requestDTO.getSalesPercentage());			
		}
		obCriInfoInstance.setIsIPRE(requestDTO.getIsIPRE());
		obCriInfoInstance.setFinanceForAccquisition(requestDTO.getFinanceForAccquisition());
		if(requestDTO.getFinanceForAccquisition()!=null && "Yes".equals(requestDTO.getFinanceForAccquisition())) {
			obCriInfoInstance.setFacilityApproved(requestDTO.getFacilityApproved());
			obCriInfoInstance.setFacilityAmount(requestDTO.getFacilityAmount());
			obCriInfoInstance.setSecurityName(requestDTO.getSecurityName());
			obCriInfoInstance.setSecurityType(requestDTO.getSecurityType());
			obCriInfoInstance.setSecurityValue(requestDTO.getSecurityValue());
		}
		obCriInfoInstance.setCompanyType(requestDTO.getCompanyType());
		if(requestDTO.getCompanyType()!=null && "Yes".equals(requestDTO.getCompanyType())) {
			obCriInfoInstance.setNameOfHoldingCompany(requestDTO.getNameOfHoldingCompany());
			obCriInfoInstance.setType(requestDTO.getType());
		}
		obCriInfoInstance.setCategoryOfFarmer(requestDTO.getCategoryOfFarmer());
		if(requestDTO.getCategoryOfFarmer()!=null && !requestDTO.getCategoryOfFarmer().trim().isEmpty()) {
			obCriInfoInstance.setLandHolding(requestDTO.getLandHolding());
		}
		obCriInfoInstance.setCountryOfGuarantor(requestDTO.getCountryOfGuarantor());
		obCriInfoInstance.setIsAffordableHousingFinance(requestDTO.getIsAffordableHousingFinance());
		obCriInfoInstance.setIsInRestrictedList(requestDTO.getIsInRestrictedList());
		if(requestDTO.getIsInRestrictedList()!=null && "Yes".equals(requestDTO.getIsInRestrictedList())) {
			obCriInfoInstance.setRestrictedFinancing(requestDTO.getRestrictedFinancing());	
		}
		obCriInfoInstance.setRestrictedIndustries(requestDTO.getRestrictedIndustries());
		if(requestDTO.getRestrictedIndustries()!=null && "Yes".equals(requestDTO.getRestrictedIndustries())) {
			obCriInfoInstance.setRestrictedListIndustries(requestDTO.getRestrictedListIndustries());
		}			
		obCriInfoInstance.setIsBorrowerInRejectDatabase(requestDTO.getIsBorrowerInRejectDatabase());
		if(requestDTO.getIsBorrowerInRejectDatabase()!=null && "Yes".equals(requestDTO.getIsBorrowerInRejectDatabase())) {
			obCriInfoInstance.setRejectHistoryReason(requestDTO.getRejectHistoryReason());
		}		
		if(requestDTO.getCapitalForCommodityAndExchange()!=null && !requestDTO.getCapitalForCommodityAndExchange().trim().isEmpty()) {
			obCriInfoInstance.setCapitalForCommodityAndExchange(requestDTO.getCapitalForCommodityAndExchange());
			if("Broker".equals(requestDTO.getCapitalForCommodityAndExchange()) && 
					(requestDTO.getIsBrokerTypeShare()!=null && !requestDTO.getIsBrokerTypeShare().trim().isEmpty()) &&
					(requestDTO.getIsBrokerTypeCommodity()==null || requestDTO.getIsBrokerTypeCommodity().trim().isEmpty())) {
				
				obCriInfoInstance.setIsBrokerTypeShare(requestDTO.getIsBrokerTypeShare().trim());
			
			}else if("Broker".equals(requestDTO.getCapitalForCommodityAndExchange()) && 
					(requestDTO.getIsBrokerTypeShare()==null || requestDTO.getIsBrokerTypeShare().trim().isEmpty()) &&
					(requestDTO.getIsBrokerTypeCommodity()!=null && !requestDTO.getIsBrokerTypeCommodity().trim().isEmpty())) {
			
				obCriInfoInstance.setIsBrokerTypeCommodity(requestDTO.getIsBrokerTypeCommodity().trim());
			
			}else if("Broker".equals(requestDTO.getCapitalForCommodityAndExchange()) && 
					(requestDTO.getIsBrokerTypeShare()!=null && !requestDTO.getIsBrokerTypeShare().trim().isEmpty()) &&
					(requestDTO.getIsBrokerTypeCommodity()!=null && !requestDTO.getIsBrokerTypeCommodity().trim().isEmpty())) {
				obCriInfoInstance.setIsBrokerTypeShare(requestDTO.getIsBrokerTypeShare().trim());
				obCriInfoInstance.setIsBrokerTypeCommodity(requestDTO.getIsBrokerTypeCommodity().trim());
			}
		}		
		obCriInfoInstance.setObjectFinance(requestDTO.getObjectFinance());
		obCriInfoInstance.setIsCommodityFinanceCustomer(requestDTO.getIsCommodityFinanceCustomer());
		obCriInfoInstance.setRestructuedBorrowerOrFacility(requestDTO.getRestructuedBorrowerOrFacility());
		if(requestDTO.getRestructuedBorrowerOrFacility()!=null && "Yes".equals(requestDTO.getRestructuedBorrowerOrFacility())) {
			obCriInfoInstance.setFacility(requestDTO.getFacility());
			obCriInfoInstance.setLimitAmount(requestDTO.getLimitAmount());
		}
		if("Yes".equals(requestDTO.getTufs().trim())){
			obCriInfoInstance.setSubsidyFlag(requestDTO.getSubsidyFlag());
			if(requestDTO.getSubsidyFlag()!=null && "Yes".equals(requestDTO.getSubsidyFlag())) {
				obCriInfoInstance.setHoldingCompnay(requestDTO.getHoldingCompnay());
			}
		}
		obCriInfoInstance.setCautionList(requestDTO.getCautionList());
		if(requestDTO.getCautionList()!=null && "Yes".equals(requestDTO.getCautionList())) {
			obCriInfoInstance.setDateOfCautionList(requestDTO.getDateOfCautionList());
			obCriInfoInstance.setCompany(requestDTO.getCompany());
			obCriInfoInstance.setDirectors(requestDTO.getDirectors());
			obCriInfoInstance.setGroupCompanies(requestDTO.getGroupCompanies());
		}
		obCriInfoInstance.setDefaultersList(requestDTO.getDefaultersList());
		if(requestDTO.getDefaultersList()!=null && "Yes".equals(requestDTO.getDefaultersList())) {
			obCriInfoInstance.setRbiDateOfCautionList(requestDTO.getRbiDateOfCautionList());
			obCriInfoInstance.setRbiCompany(requestDTO.getRbiCompany());
			obCriInfoInstance.setRbiDirectors(requestDTO.getRbiDirectors());
			obCriInfoInstance.setRbiGroupCompanies(requestDTO.getRbiGroupCompanies());
		}
		
		obCriInfoInstance.setCommericialRealEstate(requestDTO.getCommericialRealEstate());
		if (requestDTO.getCommericialRealEstate() != null
				&& "Yes".equals(requestDTO.getCommericialRealEstate())) {
			obCriInfoInstance.setCommericialRealEstateValue(requestDTO.getCommericialRealEstateValue());
			obCriInfoInstance.setCommericialRealEstateResidentialHousing("No");
			obCriInfoInstance.setResidentialRealEstate("No");
			obCriInfoInstance.setIndirectRealEstate("No");
		} else {
			if (requestDTO.getCommericialRealEstateResidentialHousing() != null
					&& "Yes".equals(requestDTO.getCommericialRealEstateResidentialHousing())) {
				obCriInfoInstance.setCommericialRealEstateResidentialHousing(
						requestDTO.getCommericialRealEstateResidentialHousing());
				obCriInfoInstance.setResidentialRealEstate("No");
				obCriInfoInstance.setIndirectRealEstate("No");
				obCriInfoInstance.setCommericialRealEstate("No");
			} else if (requestDTO.getResidentialRealEstate() != null
					&& "Yes".equals(requestDTO.getResidentialRealEstate())) {
				obCriInfoInstance
						.setResidentialRealEstate(requestDTO.getResidentialRealEstate());
				obCriInfoInstance.setCommericialRealEstateResidentialHousing("No");
				obCriInfoInstance.setIndirectRealEstate("No");
				obCriInfoInstance.setCommericialRealEstate("No");
			} else if (requestDTO.getIndirectRealEstate() != null
					&& "Yes".equals(requestDTO.getIndirectRealEstate())) {
				obCriInfoInstance.setIndirectRealEstate(requestDTO.getIndirectRealEstate());
				obCriInfoInstance.setCommericialRealEstateResidentialHousing("No");
				obCriInfoInstance.setResidentialRealEstate("No");
				obCriInfoInstance.setCommericialRealEstate("No");
			}
		}

		
		obCriInfoInstance.setConductOfAccountWithOtherBanks(requestDTO.getConductOfAccountWithOtherBanks());
		 if(requestDTO.getConductOfAccountWithOtherBanks()!=null && "classified".equals(requestDTO.getConductOfAccountWithOtherBanks())) {
			obCriInfoInstance.setCrilicStatus(requestDTO.getCrilicStatus());
			if(requestDTO.getCrilicStatus()!=null && !requestDTO.getCrilicStatus().trim().isEmpty())
				obCriInfoInstance.setComment(requestDTO.getComment());
		 }else {
			 obCriInfoInstance.setConductOfAccountWithOtherBanks("Satisfactory");
		 }
		 
		 cmsLegalEntity.setIsRBIWilfulDefaultersList(requestDTO.getIsRBIWilfulDefaultersList());
		 if("Yes".equals(requestDTO.getIsRBIWilfulDefaultersList())) {			 
			 cmsLegalEntity.setNameOfBank(requestDTO.getNameOfBank());
			 cmsLegalEntity.setIsDirectorMoreThanOne(requestDTO.getIsDirectorMoreThanOne());
			 if("Yes".equals(requestDTO.getIsDirectorMoreThanOne())) {
				 cmsLegalEntity.setNameOfDirectorsAndCompany(requestDTO.getNameOfDirectorsAndCompany());
			 }else {
				 cmsLegalEntity.setNameOfDirectorsAndCompany("");
			 }
		 }else {
			 cmsLegalEntity.setIsRBIWilfulDefaultersList(DEFAULT_VALUE_FOR_CRI_INFO);
			 cmsLegalEntity.setNameOfBank("");
			 cmsLegalEntity.setIsDirectorMoreThanOne("");
			 cmsLegalEntity.setNameOfDirectorsAndCompany("");
		 }
		 cmsLegalEntity.setIsCibilStatusClean(requestDTO.getIsCibilStatusClean());		 
		 if(requestDTO.getIsCibilStatusClean()!=null && "No".equals(requestDTO.getIsCibilStatusClean())) {
			 cmsLegalEntity.setDetailsOfCleanCibil(requestDTO.getDetailsOfCleanCibil());
		 }else {
			 cmsLegalEntity.setIsCibilStatusClean("Yes");
			 cmsLegalEntity.setDetailsOfCleanCibil("");
		 }		 
		 
		 criInfoArr[0] = obCriInfoInstance;

		 ICMSCustomerUdf udfList[] = new ICMSCustomerUdf[10];

		 //Udf Method Info(one-to-many)
		 if(requestDTO.getUdfMethodDetailList()!=null && !requestDTO.getUdfMethodDetailList().isEmpty()){
			 for(int i=0;i<requestDTO.getUdfMethodDetailList().size();i++){
				 ICMSCustomerUdf udf = new OBCMSCustomerUdf();	

				 udf.setUdf50(DEFAULT_UDF_VALUE);

				 if(requestDTO.getUdfMethodDetailList().get(i).getUdf63()!=null && 
						 !requestDTO.getUdfMethodDetailList().get(i).getUdf63().trim().isEmpty()){
					 udf.setUdf63(requestDTO.getUdfMethodDetailList().get(i).getUdf63());
				 }

				 if(requestDTO.getUdfMethodDetailList().get(i).getUdf78()!=null &&
						 !requestDTO.getUdfMethodDetailList().get(i).getUdf78().trim().isEmpty()){
					 if(requestDTO.getUdfMethodDetailList().get(i).getUdf78().equals("YES")) {
						 udf.setUdf78("YES");
					 }else {
						 udf.setUdf78("NO");
					 }
				 }else{
					 udf.setUdf78("NO");
				 }

				 if(requestDTO.getUdfMethodDetailList().get(i).getUdf79()!=null && 
						 !requestDTO.getUdfMethodDetailList().get(i).getUdf79().trim().isEmpty()){
					 if(requestDTO.getUdfMethodDetailList().get(i).getUdf79().equals("YES")) {
						 udf.setUdf79("YES");
					 }else {
						 udf.setUdf79("NO");
					 }
				 }else{
					 udf.setUdf79("NO");
				 }

				 udfList[i] = udf;
			 }
		 }
		 
		 
		
		if("WS_create_customer".equals(requestDTO.getEvent())){
			ISystem[] sysArray = new OBSystem[50];
			
			if(requestDTO.getSystemDetReqList()!=null && !requestDTO.getSystemDetReqList().isEmpty()){
				for(int i=0;i<requestDTO.getSystemDetReqList().size();i++){
					
					OBSystem obsystemInstance = new OBSystem();
					if(requestDTO.getSystemDetReqList().get(i).getSystem()!=null 
							&& !requestDTO.getSystemDetReqList().get(i).getSystem().trim().isEmpty()){
						obsystemInstance.setSystem(requestDTO.getSystemDetReqList().get(i).getSystem().trim());
					}else{
						obsystemInstance.setSystem(requestDTO.getSystemDetReqList().get(i).getSystem());
					}
					
					if(requestDTO.getSystemDetReqList().get(i).getSystemId()!=null && 
							!requestDTO.getSystemDetReqList().get(i).getSystemId().trim().isEmpty()){
						obsystemInstance.setSystemCustomerId(requestDTO.getSystemDetReqList().get(i).getSystemId().trim());
					}else{
						obsystemInstance.setSystemCustomerId(requestDTO.getSystemDetReqList().get(i).getSystemId());
					}
					
					sysArray[i] = obsystemInstance;
				}
			}
			
			cmsLegalEntity.setOtherSystem(sysArray);
		}else{
			if(obCMSCustomerIntance.getCMSLegalEntity()!=null){
				cmsLegalEntity.setOtherSystem(obCMSCustomerIntance.getCMSLegalEntity().getOtherSystem());
			}
		}
		
		IBankingMethod[] bankingMthdArray = new OBBankingMethod[50];
		
		//Banking Method Info(one-to-many)
		if(!"UPDATE".equals(addOrUpdate)) {
		if(requestDTO.getBankingMethodDetailList()!=null && !requestDTO.getBankingMethodDetailList().isEmpty()){
			for(int i=0;i<requestDTO.getBankingMethodDetailList().size();i++){
				
				IBankingMethod bankingMthd = new OBBankingMethod();

				if(requestDTO.getBankingMethodDetailList().get(i).getLeadNodalFlag()!=null && 
						!requestDTO.getBankingMethodDetailList().get(i).getLeadNodalFlag().trim().isEmpty()){
					bankingMthd.setNodal(requestDTO.getBankingMethodDetailList().get(i).getLeadNodalFlag().trim());
				}else{
					bankingMthd.setNodal(requestDTO.getBankingMethodDetailList().get(i).getLeadNodalFlag());
				}
				
				if(requestDTO.getBankingMethodDetailList().get(i).getBankType()!=null &&
						!requestDTO.getBankingMethodDetailList().get(i).getBankType().trim().isEmpty()){
					bankingMthd.setBankType(requestDTO.getBankingMethodDetailList().get(i).getBankType().trim());
				}else{
					bankingMthd.setBankType(requestDTO.getBankingMethodDetailList().get(i).getBankType());
				}
				
				if(requestDTO.getBankingMethodDetailList().get(i).getBranchId()!=null && 
						!requestDTO.getBankingMethodDetailList().get(i).getBranchId().trim().isEmpty()){
					bankingMthd.setBankId(Long.parseLong(requestDTO.getBankingMethodDetailList().get(i).getBranchId().trim()));
				}else{
					bankingMthd.setBankId(0L);
				}
				
				bankingMthdArray[i] = bankingMthd;
			}
		}
		}
		
		IDirector[] directorArr = new OBDirector[10];
		
		//Director info(one-to-many)
		if(requestDTO.getDirectorDetailList()!=null && !requestDTO.getDirectorDetailList().isEmpty()){
			for(int i=0;i<requestDTO.getDirectorDetailList().size();i++){
//				
				IDirector director = new OBDirector();
				PartyDirectorDetailsRequestDTO partyDirectorDetailsRequestDTO = requestDTO.getDirectorDetailList().get(i);
				
				if(partyDirectorDetailsRequestDTO.getRelatedType()!=null &&
						!partyDirectorDetailsRequestDTO.getRelatedType().trim().isEmpty()){
					director.setRelatedType(partyDirectorDetailsRequestDTO.getRelatedType().trim());
				}else{
					director.setRelatedType(partyDirectorDetailsRequestDTO.getRelatedType());
				}
				
				if(partyDirectorDetailsRequestDTO.getRelationship()!=null &&
						!partyDirectorDetailsRequestDTO.getRelationship().trim().isEmpty()){
					director.setRelationship(partyDirectorDetailsRequestDTO.getRelationship().trim());
				}else{
					director.setRelationship(partyDirectorDetailsRequestDTO.getRelationship());
				}
				
				if(partyDirectorDetailsRequestDTO.getDirectorEmailId()!=null 
						&& !partyDirectorDetailsRequestDTO.getDirectorEmailId().trim().isEmpty()){
					director.setDirectorEmail(partyDirectorDetailsRequestDTO.getDirectorEmailId().trim());
				}else{
					director.setDirectorEmail(partyDirectorDetailsRequestDTO.getDirectorEmailId());
				}
				
				if(partyDirectorDetailsRequestDTO.getDirectorFaxStdCode()!=null &&
						!partyDirectorDetailsRequestDTO.getDirectorFaxStdCode().trim().isEmpty()){
					director.setDirStdCodeTelex(partyDirectorDetailsRequestDTO.getDirectorFaxStdCode().trim());
				}else{
					director.setDirStdCodeTelex(partyDirectorDetailsRequestDTO.getDirectorFaxStdCode());
				}
				
				if(partyDirectorDetailsRequestDTO.getDirectorFaxNo()!=null && 
						!partyDirectorDetailsRequestDTO.getDirectorFaxNo().trim().isEmpty()){
					director.setDirectorFax(partyDirectorDetailsRequestDTO.getDirectorFaxNo().trim());
				}else{
					director.setDirectorFax(partyDirectorDetailsRequestDTO.getDirectorFaxNo());
				}
				
				if(partyDirectorDetailsRequestDTO.getDirectorTelephoneStdCode()!=null 
					&& !partyDirectorDetailsRequestDTO.getDirectorTelephoneStdCode().trim().isEmpty()){
					director.setDirStdCodeTelNo(partyDirectorDetailsRequestDTO.getDirectorTelephoneStdCode().trim());
				}else{
					director.setDirStdCodeTelNo(partyDirectorDetailsRequestDTO.getDirectorTelephoneStdCode());
				}
				
				if(partyDirectorDetailsRequestDTO.getDirectorTelNo()!=null 
						&& !partyDirectorDetailsRequestDTO.getDirectorTelNo().trim().isEmpty()){
					director.setDirectorTelNo(partyDirectorDetailsRequestDTO.getDirectorTelNo().trim());
				}else{
					director.setDirectorTelNo(partyDirectorDetailsRequestDTO.getDirectorTelNo());
				}
				
				if(partyDirectorDetailsRequestDTO.getDirectorCountry()!=null && 
						!partyDirectorDetailsRequestDTO.getDirectorCountry().trim().isEmpty()){
					director.setDirectorCountry(partyDirectorDetailsRequestDTO.getDirectorCountry().trim());
				}else{
					director.setDirectorCountry(partyDirectorDetailsRequestDTO.getDirectorCountry());
				}
				
				if(partyDirectorDetailsRequestDTO.getDirectorState()!=null && 
						!partyDirectorDetailsRequestDTO.getDirectorState().trim().isEmpty()){
					director.setDirectorState(partyDirectorDetailsRequestDTO.getDirectorState().trim());
				}else{
					director.setDirectorState(partyDirectorDetailsRequestDTO.getDirectorState());
				}
				
				if(partyDirectorDetailsRequestDTO.getDirectorCity()!=null &&
						!partyDirectorDetailsRequestDTO.getDirectorCity().trim().isEmpty()){
					director.setDirectorCity(partyDirectorDetailsRequestDTO.getDirectorCity().trim());
				}else{
					director.setDirectorCity(partyDirectorDetailsRequestDTO.getDirectorCity());
				}
				
				if(partyDirectorDetailsRequestDTO.getDirectorRegion()!=null &&
						!partyDirectorDetailsRequestDTO.getDirectorRegion().trim().isEmpty()){
					director.setDirectorRegion(partyDirectorDetailsRequestDTO.getDirectorRegion().trim());
				}else{
					director.setDirectorRegion(partyDirectorDetailsRequestDTO.getDirectorRegion());
				}
				
				if(partyDirectorDetailsRequestDTO.getDirectorPincode()!=null &&
						!partyDirectorDetailsRequestDTO.getDirectorPincode().trim().isEmpty()){
					director.setDirectorPostCode(partyDirectorDetailsRequestDTO.getDirectorPincode().trim());
				}else{
					director.setDirectorPostCode(partyDirectorDetailsRequestDTO.getDirectorPincode());
				}
				
				if(partyDirectorDetailsRequestDTO.getDirectorAddr3()!=null && 
						!partyDirectorDetailsRequestDTO.getDirectorAddr3().trim().isEmpty()){
					director.setDirectorAddress3(partyDirectorDetailsRequestDTO.getDirectorAddr3().trim());
				}else{
					director.setDirectorAddress3(partyDirectorDetailsRequestDTO.getDirectorAddr3());
				}
				
				if(partyDirectorDetailsRequestDTO.getDirectorAddr2()!=null &&
						!partyDirectorDetailsRequestDTO.getDirectorAddr2().trim().isEmpty()){
					director.setDirectorAddress2(partyDirectorDetailsRequestDTO.getDirectorAddr2().trim());
				}else{
					director.setDirectorAddress2(partyDirectorDetailsRequestDTO.getDirectorAddr2());
				}
				
				if(partyDirectorDetailsRequestDTO.getDirectorAddr1()!=null &&
						!partyDirectorDetailsRequestDTO.getDirectorAddr1().trim().isEmpty()){
					director.setDirectorAddress1(partyDirectorDetailsRequestDTO.getDirectorAddr1().trim());
				}else{
					director.setDirectorAddress1(partyDirectorDetailsRequestDTO.getDirectorAddr1());
				}
				
				if(partyDirectorDetailsRequestDTO.getPercentageOfControl()!=null && 
						!partyDirectorDetailsRequestDTO.getPercentageOfControl().trim().isEmpty()){
					director.setPercentageOfControl(partyDirectorDetailsRequestDTO.getPercentageOfControl().trim());
				}else{
					director.setPercentageOfControl(partyDirectorDetailsRequestDTO.getPercentageOfControl());
				}
				
				if(partyDirectorDetailsRequestDTO.getFullName()!=null &&
						 !partyDirectorDetailsRequestDTO.getFullName().trim().isEmpty()){
					director.setFullName(partyDirectorDetailsRequestDTO.getFullName().trim());
				}else{
					director.setFullName(partyDirectorDetailsRequestDTO.getFullName());
				}
				
				if(partyDirectorDetailsRequestDTO.getNamePrefix()!=null &&
						!partyDirectorDetailsRequestDTO.getNamePrefix().trim().isEmpty()){
					director.setNamePrefix(partyDirectorDetailsRequestDTO.getNamePrefix().trim());
				}else{
					director.setNamePrefix(partyDirectorDetailsRequestDTO.getNamePrefix());
				}
				
				if(partyDirectorDetailsRequestDTO.getBusinessEntityName()!=null && 
						!partyDirectorDetailsRequestDTO.getBusinessEntityName().trim().isEmpty()){
					director.setBusinessEntityName(partyDirectorDetailsRequestDTO.getBusinessEntityName().trim());
				}else{
					director.setBusinessEntityName(partyDirectorDetailsRequestDTO.getBusinessEntityName());
				}
				
				if(partyDirectorDetailsRequestDTO.getDirectorPAN()!=null &&
						!partyDirectorDetailsRequestDTO.getDirectorPAN().trim().isEmpty()){
					director.setDirectorPan(partyDirectorDetailsRequestDTO.getDirectorPAN().trim());
				}else{
					director.setDirectorPan(partyDirectorDetailsRequestDTO.getDirectorPAN());
				}
				
			/*	if(partyDirectorDetailsRequestDTO.getDirectorAADHAR()!=null &&
						!partyDirectorDetailsRequestDTO.getDirectorAADHAR().trim().isEmpty()){
					director.setDirectorAadhar(partyDirectorDetailsRequestDTO.getDirectorAADHAR().trim());
				}else{
					director.setDirectorAadhar(partyDirectorDetailsRequestDTO.getDirectorAADHAR());
				}
				*/
				directorArr[i]=director;
			}
		}
		
		IContact officialAddressContact = new OBContact();
		IContact regOfficialAddressContact = new OBContact();
		IContact officeAddrContact = new OBContact();
		IContact[] address = {officialAddressContact, officeAddrContact ,regOfficialAddressContact};
		
		officialAddressContact.setContactType("CORPORATE");
		if(requestDTO.getAddress1()!=null && !requestDTO.getAddress1().trim().isEmpty()){
			officialAddressContact.setAddressLine1(requestDTO.getAddress1().trim());
		}else{
			officialAddressContact.setAddressLine1(requestDTO.getAddress1());
		}
		if(requestDTO.getAddress2()!=null && !requestDTO.getAddress2().trim().isEmpty()){
			officialAddressContact.setAddressLine2(requestDTO.getAddress2().trim());
		}else{
			officialAddressContact.setAddressLine2(requestDTO.getAddress2());
		}
		if(requestDTO.getAddress3()!=null && !requestDTO.getAddress3().trim().isEmpty()){
			officialAddressContact.setAddressLine3(requestDTO.getAddress3().trim());
		}else{
			officialAddressContact.setAddressLine3(requestDTO.getAddress3());
		}
		
		if(requestDTO.getRegion()!=null && !requestDTO.getRegion().trim().isEmpty()){
			officialAddressContact.setRegion(requestDTO.getRegion().trim());
			
			IRegionDAO regionDao = (IRegionDAO)BeanHouse.get("regionDAO");
			try {
				officialAddressContact.setCountryCode(Long.toString((regionDao.getRegionById(Long.parseLong(requestDTO.getRegion().trim()))).getCountryId().getIdCountry()));
			} catch (NoSuchGeographyException e) {
				e.printStackTrace();
				throw new CMSException(e);
			} catch (TrxParameterException e) {
				e.printStackTrace();
				throw new CMSException(e);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new CMSException(e);
			} catch (TransactionException e) {
				e.printStackTrace();
				throw new CMSException(e);
			}
		}else{
			officialAddressContact.setCountryCode(requestDTO.getRegion());
			officialAddressContact.setRegion(requestDTO.getRegion());
		}
		
		if(requestDTO.getState()!=null && !requestDTO.getState().trim().isEmpty()){
			officialAddressContact.setState(requestDTO.getState().trim());
		}else{
			officialAddressContact.setState(requestDTO.getState());
		}
		if(requestDTO.getCity()!=null && !requestDTO.getCity().trim().isEmpty()){
			officialAddressContact.setCity(requestDTO.getCity().trim());
		}else{
			officialAddressContact.setCity(requestDTO.getCity());
		}
		
		if(requestDTO.getPincode()!=null && !requestDTO.getPincode().trim().isEmpty()){
			officialAddressContact.setPostalCode(requestDTO.getPincode().trim());
		}else{
			officialAddressContact.setPostalCode(requestDTO.getPincode());
		}
		if(requestDTO.getEmailId()!=null && !requestDTO.getEmailId().trim().isEmpty()){
			officialAddressContact.setEmailAddress(requestDTO.getEmailId().trim());
		}else{
			officialAddressContact.setEmailAddress(requestDTO.getEmailId());
		}
		if(requestDTO.getFaxStdCode()!=null && !requestDTO.getFaxStdCode().trim().isEmpty()){
			officialAddressContact.setStdCodeTelex(requestDTO.getFaxStdCode().trim());
		}else{
			officialAddressContact.setStdCodeTelex(requestDTO.getFaxStdCode());
		}
		if(requestDTO.getFaxNumber()!=null && !requestDTO.getFaxNumber().trim().isEmpty()){
			officialAddressContact.setTelex(requestDTO.getFaxNumber().trim());
		}else{
			officialAddressContact.setTelex(requestDTO.getFaxNumber());
		}
		if(requestDTO.getTelephoneStdCode()!=null && !requestDTO.getTelephoneStdCode().trim().isEmpty()){
			officialAddressContact.setStdCodeTelNo(requestDTO.getTelephoneStdCode().trim());
		}else{
			officialAddressContact.setStdCodeTelNo(requestDTO.getTelephoneStdCode());
		}
		if(requestDTO.getTelephoneNo()!=null && !requestDTO.getTelephoneNo().trim().isEmpty()){
			officialAddressContact.setTelephoneNumer(requestDTO.getTelephoneNo().trim());
		}else{
			officialAddressContact.setTelephoneNumer(requestDTO.getTelephoneNo());
		}
		
		
		officeAddrContact.setContactType("OFFICE");
		regOfficialAddressContact.setContactType("REGISTERED");
		
		if(requestDTO.getRegisteredAddr1()!=null && !requestDTO.getRegisteredAddr1().trim().isEmpty()){
			regOfficialAddressContact.setAddressLine1(requestDTO.getRegisteredAddr1().trim());
		}else{
			regOfficialAddressContact.setAddressLine1(requestDTO.getRegisteredAddr1());
		}
		
		if(requestDTO.getRegisteredAddr2()!=null && !requestDTO.getRegisteredAddr2().trim().isEmpty()){
			regOfficialAddressContact.setAddressLine2(requestDTO.getRegisteredAddr2().trim());
		}else{
			regOfficialAddressContact.setAddressLine2(requestDTO.getRegisteredAddr2());
		}
		
		if(requestDTO.getRegisteredAddr3()!=null && !requestDTO.getRegisteredAddr3().trim().isEmpty()){
			regOfficialAddressContact.setAddressLine3(requestDTO.getRegisteredAddr3().trim());
		}else{
			regOfficialAddressContact.setAddressLine3(requestDTO.getRegisteredAddr3());
		}
		
		if(requestDTO.getRegisteredCountry()!=null && !requestDTO.getRegisteredCountry().trim().isEmpty()){
			regOfficialAddressContact.setCountryCode(requestDTO.getRegisteredCountry().trim());
		}else{
			regOfficialAddressContact.setCountryCode(requestDTO.getRegisteredCountry());
		}
		
		if(requestDTO.getRegisteredRegion()!=null && !requestDTO.getRegisteredRegion().trim().isEmpty()){
			regOfficialAddressContact.setRegion(requestDTO.getRegisteredRegion().trim());
		}else{
			regOfficialAddressContact.setRegion(requestDTO.getRegisteredRegion());
		}
		
		if(requestDTO.getRegisteredState()!=null && !requestDTO.getRegisteredState().trim().isEmpty()){
			regOfficialAddressContact.setState(requestDTO.getRegisteredState().trim());
		}else{
			regOfficialAddressContact.setState(requestDTO.getRegisteredState());
		}
		
		if(requestDTO.getRegisteredCity()!=null && !requestDTO.getRegisteredCity().trim().isEmpty()){
			regOfficialAddressContact.setCity(requestDTO.getRegisteredCity().trim());
		}else{
			regOfficialAddressContact.setCity(requestDTO.getRegisteredCity());
		}
		
		if(requestDTO.getRegisteredPincode()!=null && !requestDTO.getRegisteredPincode().trim().isEmpty()){
			regOfficialAddressContact.setPostalCode(requestDTO.getRegisteredPincode().trim());
		}else{
			regOfficialAddressContact.setPostalCode(requestDTO.getRegisteredPincode());
		}
		
		if(requestDTO.getRegisteredTelephoneStdCode()!=null && !requestDTO.getRegisteredTelephoneStdCode().trim().isEmpty()){
			regOfficialAddressContact.setStdCodeTelNo(requestDTO.getRegisteredTelephoneStdCode().trim());
		}else{
			regOfficialAddressContact.setStdCodeTelNo(requestDTO.getRegisteredTelephoneStdCode());
		}
		
		if(requestDTO.getRegisteredTelNo()!=null && !requestDTO.getRegisteredTelNo().trim().isEmpty()){
			regOfficialAddressContact.setTelephoneNumer(requestDTO.getRegisteredTelNo().trim());
		}else{
			regOfficialAddressContact.setTelephoneNumer(requestDTO.getRegisteredTelNo());
		}
		
		if(requestDTO.getRegisteredFaxStdCode()!=null && !requestDTO.getRegisteredFaxStdCode().trim().isEmpty()){
			regOfficialAddressContact.setStdCodeTelex(requestDTO.getRegisteredFaxStdCode().trim());
		}else{
			regOfficialAddressContact.setStdCodeTelex(requestDTO.getRegisteredFaxStdCode());
		}
		
		if(requestDTO.getRegisteredFaxNumber()!=null && !requestDTO.getRegisteredFaxNumber().trim().isEmpty()){
			regOfficialAddressContact.setTelex(requestDTO.getRegisteredFaxNumber().trim());
		}else{
			regOfficialAddressContact.setTelex(requestDTO.getRegisteredFaxNumber());
		}
		
		cmsLegalEntity.setUdfData(udfList);		
		

		cmsLegalEntity.setRegisteredAddress(address);
		if(!"UPDATE".equals(addOrUpdate)) {
		cmsLegalEntity.setBankList(bankingMthdArray);
		}
		cmsLegalEntity.setCriList(criInfoArr);
		cmsLegalEntity.setDirector(directorArr);

	
		getCoBorrowerDetailsActualFromDTO(requestDTO, cmsLegalEntity);

		obCMSCustomerIntance.setCMSLegalEntity(cmsLegalEntity);
		
		//New Online CAM CR Start
		if(requestDTO.getListedCompany()!=null && !requestDTO.getListedCompany().trim().isEmpty()){
			obCMSCustomerIntance.setListedCompany(requestDTO.getListedCompany().trim());
		}else{
			obCMSCustomerIntance.setListedCompany(requestDTO.getListedCompany());
		}
		
		if(requestDTO.getIsinNo()!=null && !requestDTO.getIsinNo().trim().isEmpty()){
			obCMSCustomerIntance.setIsinNo(requestDTO.getIsinNo().trim());
		}else{
			obCMSCustomerIntance.setIsinNo(requestDTO.getIsinNo());
		}
		
		if(requestDTO.getRaroc()!=null && !requestDTO.getRaroc().trim().isEmpty()){
			obCMSCustomerIntance.setRaroc(requestDTO.getRaroc().trim());
		}else{
			obCMSCustomerIntance.setRaroc(requestDTO.getRaroc());
		}
		try {
			if (requestDTO.getRaraocPeriod()!=null && !requestDTO.getRaraocPeriod().trim().isEmpty()) {
				obCMSCustomerIntance.setRaraocPeriod(relationshipDateFormat.parse(requestDTO.getRaraocPeriod().trim()));
			}else{
				obCMSCustomerIntance.setRaraocPeriod(null);
			}
		}catch (ParseException e) {
			e.printStackTrace();
		}
		if(requestDTO.getYearEndPeriod()!=null && !requestDTO.getYearEndPeriod().trim().isEmpty()){
			obCMSCustomerIntance.setYearEndPeriod(requestDTO.getYearEndPeriod().trim());
		}else{
			obCMSCustomerIntance.setYearEndPeriod(requestDTO.getYearEndPeriod());
		}
		if(requestDTO.getCreditMgrEmpId()!=null && !requestDTO.getCreditMgrEmpId().trim().isEmpty()){
			obCMSCustomerIntance.setCreditMgrEmpId(requestDTO.getCreditMgrEmpId().trim());
		}else{
			obCMSCustomerIntance.setCreditMgrEmpId(requestDTO.getCreditMgrEmpId());
		}
		if(requestDTO.getPfLrdCreditMgrEmpId()!=null && !requestDTO.getPfLrdCreditMgrEmpId().trim().isEmpty()){
			obCMSCustomerIntance.setPfLrdCreditMgrEmpId(requestDTO.getPfLrdCreditMgrEmpId().trim());
		}else{
			obCMSCustomerIntance.setPfLrdCreditMgrEmpId(requestDTO.getPfLrdCreditMgrEmpId());
		}
		
		if(requestDTO.getCreditMgrSegment()!=null && !requestDTO.getCreditMgrSegment().trim().isEmpty()){
			obCMSCustomerIntance.setCreditMgrSegment(requestDTO.getCreditMgrSegment().trim());
		}else{
			obCMSCustomerIntance.setCreditMgrSegment(requestDTO.getCreditMgrSegment());
		}
		if(requestDTO.getMultBankFundBasedHdfcBankPer()!=null && !requestDTO.getMultBankFundBasedHdfcBankPer().trim().isEmpty()){
			obCMSCustomerIntance.setMultBankFundBasedHdfcBankPer(requestDTO.getMultBankFundBasedHdfcBankPer().trim());
		}else{
			obCMSCustomerIntance.setMultBankFundBasedHdfcBankPer(requestDTO.getMultBankFundBasedHdfcBankPer());
		}
		if(requestDTO.getMultBankFundBasedLeadBankPer()!=null && !requestDTO.getMultBankFundBasedLeadBankPer().trim().isEmpty()){
			obCMSCustomerIntance.setMultBankFundBasedLeadBankPer(requestDTO.getMultBankFundBasedLeadBankPer().trim());
		}else{
			obCMSCustomerIntance.setMultBankFundBasedLeadBankPer(requestDTO.getMultBankFundBasedLeadBankPer());
		}
		if(requestDTO.getMultBankNonFundBasedHdfcBankPer()!=null && !requestDTO.getMultBankNonFundBasedHdfcBankPer().trim().isEmpty()){
			obCMSCustomerIntance.setMultBankNonFundBasedHdfcBankPer(requestDTO.getMultBankNonFundBasedHdfcBankPer().trim());
		}else{
			obCMSCustomerIntance.setMultBankNonFundBasedHdfcBankPer(requestDTO.getMultBankNonFundBasedHdfcBankPer());
		}
		if(requestDTO.getMultBankNonFundBasedLeadBankPer()!=null && !requestDTO.getMultBankNonFundBasedLeadBankPer().trim().isEmpty()){
			obCMSCustomerIntance.setMultBankNonFundBasedLeadBankPer(requestDTO.getMultBankNonFundBasedLeadBankPer().trim());
		}else{
			obCMSCustomerIntance.setMultBankNonFundBasedLeadBankPer(requestDTO.getMultBankNonFundBasedLeadBankPer());
		}
		if(requestDTO.getConsBankFundBasedHdfcBankPer()!=null && !requestDTO.getConsBankFundBasedHdfcBankPer().trim().isEmpty()){
			obCMSCustomerIntance.setConsBankFundBasedHdfcBankPer(requestDTO.getConsBankFundBasedHdfcBankPer().trim());
		}else{
			obCMSCustomerIntance.setConsBankFundBasedHdfcBankPer(requestDTO.getConsBankFundBasedHdfcBankPer());
		}
		if(requestDTO.getConsBankFundBasedLeadBankPer()!=null && !requestDTO.getConsBankFundBasedLeadBankPer().trim().isEmpty()){
			obCMSCustomerIntance.setConsBankFundBasedLeadBankPer(requestDTO.getConsBankFundBasedLeadBankPer().trim());
		}else{
			obCMSCustomerIntance.setConsBankFundBasedLeadBankPer(requestDTO.getConsBankFundBasedLeadBankPer());
		}
		if(requestDTO.getConsBankNonFundBasedHdfcBankPer()!=null && !requestDTO.getConsBankNonFundBasedHdfcBankPer().trim().isEmpty()){
			obCMSCustomerIntance.setConsBankNonFundBasedHdfcBankPer(requestDTO.getConsBankNonFundBasedHdfcBankPer().trim());
		}else{
			obCMSCustomerIntance.setConsBankNonFundBasedHdfcBankPer(requestDTO.getConsBankNonFundBasedHdfcBankPer());
		}
		if(requestDTO.getConsBankNonFundBasedLeadBankPer()!=null && !requestDTO.getConsBankNonFundBasedLeadBankPer().trim().isEmpty()){
			obCMSCustomerIntance.setConsBankNonFundBasedLeadBankPer(requestDTO.getConsBankNonFundBasedLeadBankPer().trim());
		}else{
			obCMSCustomerIntance.setConsBankNonFundBasedLeadBankPer(requestDTO.getConsBankNonFundBasedLeadBankPer());
		}
		//New Online CAM CR End
		return obCMSCustomerIntance;
	}
	
	private static void getCoBorrowerDetailsActualFromDTO(PartyDetailsRequestDTO request, ICMSLegalEntity cmsLegalEntity){
		if(request==null || request.getCoBorrowerDetailsInd()==null)
			return;
		ICustomerDAO custDAO = CustomerDAOFactory.getDAO();

		if(!WS_CALL_CREATE_PARTY.equals(request.getEvent())) {
			List<CoBorrowerDetailsRequestDTO> coBorrowerList1 = custDAO.getCoBorrowerListWS(request.getClimsPartyId());

			if(coBorrowerList1!=null) {
				List<ICoBorrowerDetails> coBorrowerDetailsNew = new ArrayList<ICoBorrowerDetails>();
				for(CoBorrowerDetailsRequestDTO coBorrower1 : coBorrowerList1) {
					
					ICoBorrowerDetails actual = new OBCoBorrowerDetails();
					System.out.println("coBorrower1.getCoBorrowerLiabId()"+coBorrower1.getCoBorrowerLiabId());

					System.out.println("coBorrower1.getCoBorrowerName()"+coBorrower1.getCoBorrowerName());

					if(StringUtils.isNotBlank(coBorrower1.getCoBorrowerLiabId()))
						actual.setCoBorrowerLiabId(coBorrower1.getCoBorrowerLiabId());
					if(StringUtils.isNotBlank(coBorrower1.getCoBorrowerName()))
						actual.setCoBorrowerName(coBorrower1.getCoBorrowerName());
				
						actual.setIsInterfaced("Y");

					coBorrowerDetailsNew.add(actual);
				}
				cmsLegalEntity.setCoBorrowerDetails(coBorrowerDetailsNew);

			}	
			
			}else {
		
		if( ICMSConstant.YES.equals(request.getCoBorrowerDetailsInd())) {
			PartyCoBorrowerDetailsRequestDTO coBorrowerRequest = request.getCoBorrowerDetailsList();
			
		
				List<CoBorrowerDetailsRequestDTO> coBorrowerList = coBorrowerRequest.getCoBorrowerDetails();

				if(coBorrowerList!=null) {
					List<ICoBorrowerDetails> coBorrowerDetails = new ArrayList<ICoBorrowerDetails>();
					for(CoBorrowerDetailsRequestDTO coBorrower : coBorrowerList) {
						
						ICoBorrowerDetails actual = new OBCoBorrowerDetails();
						
						if(StringUtils.isNotBlank(coBorrower.getCoBorrowerLiabId()))
							actual.setCoBorrowerLiabId(coBorrower.getCoBorrowerLiabId());
						if(StringUtils.isNotBlank(coBorrower.getCoBorrowerName()))
							actual.setCoBorrowerName(coBorrower.getCoBorrowerName());
						coBorrowerDetails.add(actual);
					}
				cmsLegalEntity.setCoBorrowerDetails(coBorrowerDetails);
				}
			}
		}
	}
	
	public ManualInputCustomerInfoForm getFormFromDTO(PartyDetailsRequestDTO requestDTO,String addOrUpdate ) {
		
		SimpleDateFormat relationshipDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		relationshipDateFormat.setLenient(false);
		SimpleDateFormat relationshipDateFormatForLeiDate = new SimpleDateFormat("dd-MM-yyyy");
		relationshipDateFormatForLeiDate.setLenient(false);
		SimpleDateFormat cautionlistDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		cautionlistDateFormat.setLenient(false);
		SimpleDateFormat cautionDateFormat = new SimpleDateFormat("dd/MMM/yyyy");
		cautionDateFormat.setLenient(false);
		
		MasterAccessUtility masterAccessUtilityObj = (MasterAccessUtility)BeanHouse.get("masterAccessUtility");
		//CMSCustomer info
		ManualInputCustomerInfoForm form = new ManualInputCustomerInfoForm();
		
		//Default Values
		form.setStatus(DEFAULT_STATUS);
		form.setCycle(DEFAULT_CYCLE);
		form.setNonFundedSharePercent(DEFAULT_NON_FUNDED_SHARE_PERCENT);
		if("WS_create_customer".equals(requestDTO.getEvent())){
			form.setWillfulDefaultStatus(DEFAULT_WILLFUL_STATUS);
		}
		form.setSubLine(DEFAULT_SUBLINE);
		 
		if(requestDTO.getBusinessGroup()!=null && !requestDTO.getBusinessGroup().trim().isEmpty()){
			form.setBusinessGroup(requestDTO.getBusinessGroup().trim());
		}else{
			form.setBusinessGroup(requestDTO.getBusinessGroup());
		}
		
		if(requestDTO.getMainBranch()!=null && !requestDTO.getMainBranch().trim().isEmpty()){
			form.setMainBranch(requestDTO.getMainBranch().trim());
		}else{
			form.setMainBranch(requestDTO.getMainBranch());
		}
		
		if(requestDTO.getMainBranch()!=null && !requestDTO.getMainBranch().trim().isEmpty() && requestDTO.getMainBranch().trim().contains("-")){
			form.setBranchCode(requestDTO.getMainBranch().trim().split("-")[0]);
		}else{
			form.setBranchCode("");
		}
		
		String rmRegion = "-";

		if(requestDTO.getRelationshipManager()!=null && !requestDTO.getRelationshipManager().trim().isEmpty()){
			
			form.setRelationshipMgr(requestDTO.getRelationshipManager().trim());

			try{
				Object relshpObj = masterAccessUtilityObj.getMaster("actualRelationshipMgr", new Long(requestDTO.getRelationshipManager().trim()));
				if(relshpObj!=null && !"".equals(relshpObj)){
					rmRegion = Long.toString(((IRelationshipMgr)relshpObj).getRegion().getIdRegion());
				}
			}catch (Exception e) {
				DefaultLogger.error(this, e.getMessage());
			}
			
		}else{
			form.setRelationshipMgr(requestDTO.getRelationshipManager());
		}
		System.out.println("PartyDetailsDTOMapper.java=>Line 1479=>requestDTO.getRelationshipMgrCode()=>"+requestDTO.getRelationshipMgrCode()+"*****");
		if(requestDTO.getRelationshipMgrCode()!=null && !requestDTO.getRelationshipMgrCode().trim().isEmpty()){
			form.setRelationshipMgrEmpCode(requestDTO.getRelationshipMgrCode());
		}
		
		if(rmRegion!=null && !rmRegion.trim().isEmpty()){
			form.setRmRegion(rmRegion.trim());
		}else{
			form.setRmRegion(rmRegion);
		}
		
		if(requestDTO.getEntity()!=null && !requestDTO.getEntity().trim().isEmpty()){
			form.setEntity(requestDTO.getEntity().trim());
		}else{
			form.setEntity(requestDTO.getEntity());
		}
		
		if(requestDTO.getPAN()!=null && !requestDTO.getPAN().trim().isEmpty()){
			form.setPan(requestDTO.getPAN().trim());
		}else{
			form.setPan(requestDTO.getPAN());
		}
		
		if(requestDTO.getRBIIndustryCode()!=null && !requestDTO.getRBIIndustryCode().trim().isEmpty()){
			form.setRbiIndustryCode(requestDTO.getRBIIndustryCode().trim());
		}else{
			form.setRbiIndustryCode(requestDTO.getRBIIndustryCode());
		}
		
		if(requestDTO.getIndustryName()!=null && !requestDTO.getIndustryName().trim().isEmpty()){
			form.setIndustryName(requestDTO.getIndustryName().trim());
		}else{
			form.setIndustryName(requestDTO.getIndustryName());
		}
		
		for(int i=0;i<requestDTO.getBankingMethodComboBoxList().size();i++) {
			if(requestDTO.getBankingMethodComboBoxList().get(i).getBankingMethod()!=null && !requestDTO.getBankingMethodComboBoxList().get(i).getBankingMethod().trim().isEmpty()){
				form.setBankingMethod(requestDTO.getBankingMethodComboBoxList().get(i).getBankingMethod().trim());
				form.setFinalBankMethodList(requestDTO.getBankingMethodComboBoxList().get(i).getBankingMethod().trim());
			}else{
				form.setBankingMethod("");
				form.setFinalBankMethodList("");
			}
		}
		
		if(requestDTO.getTotalFundedLimit()!=null && !requestDTO.getTotalFundedLimit().trim().isEmpty()){
			form.setTotalFundedLimit(requestDTO.getTotalFundedLimit().trim());
		}else{
			form.setTotalFundedLimit(requestDTO.getTotalFundedLimit());
		}
		
		if(requestDTO.getTotalNonFundedLimit()!=null && !requestDTO.getTotalNonFundedLimit().trim().isEmpty()){
			form.setTotalNonFundedLimit(requestDTO.getTotalNonFundedLimit().trim());
		}else{
			form.setTotalNonFundedLimit(requestDTO.getTotalNonFundedLimit());
		}
		
		if(requestDTO.getFundedSharePercent()!=null && !requestDTO.getFundedSharePercent().trim().isEmpty()){
			form.setFundedSharePercent(requestDTO.getFundedSharePercent().trim());
		}else{
			form.setFundedSharePercent(requestDTO.getFundedSharePercent());
		}
		
		if(requestDTO.getMemoExposure()!=null && !requestDTO.getMemoExposure().trim().isEmpty()){
			form.setMemoExposure(requestDTO.getMemoExposure().trim());
		}else{
			form.setMemoExposure(requestDTO.getMemoExposure());
		}
		
		if(requestDTO.getMpbf()!=null && !requestDTO.getMpbf().trim().isEmpty()){
			form.setMpbf(requestDTO.getMpbf().trim());
		}else{
			form.setMpbf(requestDTO.getMpbf());
		}
		
		if(requestDTO.getPartyName()!=null && !requestDTO.getPartyName().trim().isEmpty()){
			form.setPartyName(requestDTO.getPartyName().trim());
		}else{
			form.setPartyName(requestDTO.getPartyName());
		}
		
		if(requestDTO.getSegment()!=null && !requestDTO.getSegment().trim().isEmpty()){
			form.setCustomerSegment(requestDTO.getSegment().trim());
		}else{
			form.setCustomerSegment(requestDTO.getSegment());
		}
		
		if(requestDTO.getRelationshipStartDate()!=null && !requestDTO.getRelationshipStartDate().trim().isEmpty()){
			form.setRelationshipStartDate(requestDTO.getRelationshipStartDate().trim());
		}else{
			form.setRelationshipStartDate(requestDTO.getRelationshipStartDate());
		}
		
		//Santosh LEI CR
		if(requestDTO.getLeiExpDate()!=null && !requestDTO.getLeiExpDate().toString().trim().isEmpty()){
			form.setLeiExpDate(requestDTO.getLeiExpDate().trim());
		}else{
			form.setLeiExpDate(requestDTO.getLeiExpDate());
		}
				
		if(requestDTO.getLeiCode()!=null && !requestDTO.getLeiCode().toString().trim().isEmpty()){
			form.setLeiCode(requestDTO.getLeiCode().trim());
		}else{
			form.setLeiCode(requestDTO.getLeiCode());
		}
		//End LEI CR
		
		if(requestDTO.getPartyName()!=null && !requestDTO.getPartyName().trim().isEmpty()){
			form.setCustomerNameLong(requestDTO.getPartyName().trim());
			form.setCustomerNameShort(requestDTO.getPartyName().trim());
		}else{
			form.setCustomerNameLong(requestDTO.getPartyName());
			form.setCustomerNameShort(requestDTO.getPartyName());
		}
		
		Double totFundedLimit =(requestDTO.getTotalFundedLimit()!=null && !"".equals(requestDTO.getTotalFundedLimit().trim()))?Double.parseDouble(requestDTO.getTotalFundedLimit().trim()):0d; 
		Double totNonFundedLimit =(requestDTO.getTotalNonFundedLimit()!=null && !"".equals(requestDTO.getTotalNonFundedLimit().trim()))?Double.parseDouble(requestDTO.getTotalNonFundedLimit().trim()):0d; 
		Double memoExposure =(requestDTO.getMemoExposure()!=null && !"".equals(requestDTO.getMemoExposure().trim())) ?Double.parseDouble(requestDTO.getMemoExposure().trim()):0d; 
		
		Double sancLimit = totFundedLimit + totNonFundedLimit + memoExposure; 
		
		form.setTotalSanctionedLimit(sancLimit.toString().trim());
		
		if(totFundedLimit!=null && !"".equals(totFundedLimit)
				&& requestDTO.getFundedSharePercent()!=null && !"".equals(requestDTO.getFundedSharePercent().trim())){
			Double fundedShareLimit = totFundedLimit * Double.parseDouble(requestDTO.getFundedSharePercent().trim())/100;
			form.setFundedShareLimit(fundedShareLimit.toString().trim());
		}else{
			form.setFundedShareLimit("0");
		}
		
		if(totNonFundedLimit!=null && !"".equals(totNonFundedLimit)){
			Double nonFundedShareLimit = totNonFundedLimit * Double.parseDouble(DEFAULT_NON_FUNDED_SHARE_PERCENT)/100;
			form.setNonFundedShareLimit(nonFundedShareLimit.toString().trim());
		}else{
			form.setNonFundedShareLimit("0");
		}
		
		
		//*********************CRI Info (One-to-one)****************************
		
		if(requestDTO.getCustomerRAMId()!=null && !requestDTO.getCustomerRAMId().trim().isEmpty()){
			form.setCustomerRamID(requestDTO.getCustomerRAMId().trim());
		}else{
			form.setCustomerRamID(requestDTO.getCustomerRAMId());
		}

		if(requestDTO.getCustomerAPRCode()!=null && !requestDTO.getCustomerAPRCode().trim().isEmpty()){
			form.setCustomerAprCode(requestDTO.getCustomerAPRCode().trim());
		}else{
			form.setCustomerAprCode(requestDTO.getCustomerAPRCode());
		}
		
		if(requestDTO.getCustomerExtRating()!=null && !requestDTO.getCustomerExtRating().trim().isEmpty()){
			form.setCustomerExtRating(requestDTO.getCustomerExtRating().trim());
		}else{
			form.setCustomerExtRating(requestDTO.getCustomerExtRating());
		}
		
		if(requestDTO.getNbfcFlag()!=null && !requestDTO.getNbfcFlag().trim().isEmpty()){
			form.setIsNbfs(requestDTO.getNbfcFlag().trim());
			
			if("Yes".equals(requestDTO.getNbfcFlag().trim())){
				if(requestDTO.getNbfcA()!=null && !requestDTO.getNbfcA().trim().isEmpty()){
					form.setNbfsA(requestDTO.getNbfcA().trim());
				}else{
					form.setNbfsA(requestDTO.getNbfcA());
				}
				if(requestDTO.getNbfcB()!=null && !requestDTO.getNbfcB().trim().isEmpty()){
					form.setNbfsB(requestDTO.getNbfcB().trim());
				}else{
					form.setNbfsB(requestDTO.getNbfcB());
				}
			}
		}else{
			form.setIsNbfs(requestDTO.getNbfcFlag());
		}

		if(requestDTO.getIsBackedByGovt()!=null && !requestDTO.getIsBackedByGovt().trim().isEmpty()){
			form.setIsBackedByGovt(requestDTO.getIsBackedByGovt().trim());
		}else{
			form.setIsBackedByGovt(requestDTO.getIsBackedByGovt());
		}
		if(requestDTO.getPrioritySector()!=null && !requestDTO.getPrioritySector().trim().isEmpty()){
			form.setPrioritySector(requestDTO.getPrioritySector().trim());
		}else{
			form.setPrioritySector(requestDTO.getPrioritySector());
		}
		
		if(requestDTO.getMsmeClassification()!=null && !requestDTO.getMsmeClassification().trim().isEmpty()){
			form.setMsmeClassification(requestDTO.getMsmeClassification().trim());
		}else{
			form.setMsmeClassification(requestDTO.getMsmeClassification());
		}
		
		if(requestDTO.getPermSSICert()!=null && !requestDTO.getPermSSICert().trim().isEmpty()){
			form.setIsPermenentSsiCert(requestDTO.getPermSSICert().trim());
		}else{
			form.setIsPermenentSsiCert(requestDTO.getPermSSICert());
		}
		
		if(requestDTO.getWeakerSection()!=null && !requestDTO.getWeakerSection().trim().isEmpty()){

			form.setIsWeakerSection(requestDTO.getWeakerSection().trim());
			if("Yes".equals(requestDTO.getWeakerSection().trim())){
				if(requestDTO.getWeakerSectionType()!=null && !requestDTO.getWeakerSectionType().trim().isEmpty()){
					form.setWeakerSection(requestDTO.getWeakerSectionType().trim());
				}else{
					form.setWeakerSection(requestDTO.getWeakerSectionType());
				}

				/*if(requestDTO.getWeakerSectionValue()!=null && !requestDTO.getWeakerSectionValue().trim().isEmpty()){
					form.setGovtSchemeType(requestDTO.getWeakerSectionValue().trim());
				}else{
					form.setMINORITY_COMMU(requestDTO.getWeakerSectionValue());
				}*/
			}
		}else{
			form.setIsWeakerSection(requestDTO.getWeakerSection());
		}
		
		if(requestDTO.getKisanCreditCard()!=null && !requestDTO.getKisanCreditCard().trim().isEmpty()){
			form.setIsKisanCreditCard(requestDTO.getKisanCreditCard().trim());
		}else{
			form.setIsKisanCreditCard(requestDTO.getKisanCreditCard());
		}
		
		if(requestDTO.getMinorityCommunity()!=null && !requestDTO.getMinorityCommunity().trim().isEmpty()){
			form.setIsMinorityCommunity(requestDTO.getMinorityCommunity().trim());
			if("Yes".equals(requestDTO.getMinorityCommunity().trim())){
				if(requestDTO.getMinorityCommunityType()!=null && !requestDTO.getMinorityCommunityType().trim().isEmpty()){
					form.setMinorityCommunity(requestDTO.getMinorityCommunityType().trim());
				}else{
					form.setMinorityCommunity(requestDTO.getMinorityCommunityType());
				}
			}
		}else{
			form.setIsMinorityCommunity(requestDTO.getMinorityCommunity());
		}

		if(requestDTO.getCapitalMarketExposure()!=null && !requestDTO.getCapitalMarketExposure().trim().isEmpty()){
			form.setIsCapitalMarketExpos(requestDTO.getCapitalMarketExposure().trim());
		}else{
			form.setIsCapitalMarketExpos(requestDTO.getCapitalMarketExposure());
		}
		
		if(requestDTO.getRealEstateExposure()!=null && !requestDTO.getRealEstateExposure().trim().isEmpty()){
			form.setIsRealEstateExpos(requestDTO.getRealEstateExposure().trim());
		}else{
			form.setIsRealEstateExpos(requestDTO.getRealEstateExposure());
		}
		
		if(requestDTO.getCommodityExposure()!=null && !requestDTO.getCommodityExposure().trim().isEmpty()){
			form.setIsCommoditiesExposer(requestDTO.getCommodityExposure().trim());
		}else{
			form.setIsCommoditiesExposer(requestDTO.getCommodityExposure());
		}
		
		if(requestDTO.getSensitive()!=null && !requestDTO.getSensitive().trim().isEmpty()){
			form.setIsSensitive(requestDTO.getSensitive().trim());
		}else{
			form.setIsSensitive(requestDTO.getSensitive());
		}
		
		if(requestDTO.getCommodityName()!=null && !requestDTO.getCommodityName().trim().isEmpty()){
			form.setCommoditiesName(requestDTO.getCommodityName().trim());
		}else{
			form.setCommoditiesName(requestDTO.getCommodityName());
		}
		
		if(requestDTO.getGrossInvestmentPM()!=null && !requestDTO.getGrossInvestmentPM().trim().isEmpty()){
			form.setGrossInvsInPM(requestDTO.getGrossInvestmentPM().trim());
		}else{
			form.setGrossInvsInPM("0");
		}
		if(requestDTO.getGrossInvestmentEquip()!=null && !requestDTO.getGrossInvestmentEquip().trim().isEmpty()){
			form.setGrossInvsInEquip(requestDTO.getGrossInvestmentEquip().trim());
		}else{
			form.setGrossInvsInEquip("0");
		}
		
		if(requestDTO.getPsu()!=null && !requestDTO.getPsu().trim().isEmpty()){
			form.setPsu(requestDTO.getPsu().trim());
		}else{
			form.setPsu(requestDTO.getPsu());
		}
		
		if(requestDTO.getPercentageOfShareholding()!=null && !requestDTO.getPercentageOfShareholding().trim().isEmpty()){
			form.setPsuPercentage(requestDTO.getPercentageOfShareholding().trim());
		}else{
			form.setPsuPercentage(requestDTO.getPercentageOfShareholding());
		}
		
		if(requestDTO.getGovtUndertaking()!=null && !requestDTO.getGovtUndertaking().trim().isEmpty()){
			form.setGovtUnderTaking(requestDTO.getGovtUndertaking().trim());
		}else{
			form.setGovtUnderTaking(requestDTO.getGovtUndertaking());
		}
		
		if(requestDTO.getProjectFinance()!=null && !requestDTO.getProjectFinance().trim().isEmpty()){
			form.setIsProjectFinance(requestDTO.getProjectFinance().trim());
		}else{
			form.setIsProjectFinance(requestDTO.getProjectFinance());
		}
		
		if(requestDTO.getInfraFinance()!=null && !requestDTO.getInfraFinance().trim().isEmpty()){
			form.setIsInfrastructureFinanace(requestDTO.getInfraFinance().trim());
		}else{
			form.setIsInfrastructureFinanace(requestDTO.getInfraFinance());
		}
		
		if(requestDTO.getInfraFinanceType()!=null && !requestDTO.getInfraFinanceType().trim().isEmpty()){
			form.setInfrastructureFinanaceType(requestDTO.getInfraFinanceType().trim());
		}else{
			form.setInfrastructureFinanaceType(requestDTO.getInfraFinanceType());
		}
		
		if(requestDTO.getSEMSGuideApplicable()!=null && !requestDTO.getSEMSGuideApplicable().trim().isEmpty()){
			form.setIsSemsGuideApplicable(requestDTO.getSEMSGuideApplicable().trim());
		}else{
			form.setIsSemsGuideApplicable(requestDTO.getSEMSGuideApplicable());
		}
		
		if(requestDTO.getFailsUnderIFCExclusion()!=null && !requestDTO.getFailsUnderIFCExclusion().trim().isEmpty()){
			form.setIsFailIfcExcluList(requestDTO.getFailsUnderIFCExclusion().trim());
		}else{
			form.setIsFailIfcExcluList(requestDTO.getFailsUnderIFCExclusion());
		}
		
		if(requestDTO.getTufs()!=null && !requestDTO.getTufs().trim().isEmpty()){
			form.setIsTufs(requestDTO.getTufs().trim());
		}else{
			form.setIsTufs(requestDTO.getTufs());
		}
		
		if(requestDTO.getRBIDefaulterList()!=null && !requestDTO.getRBIDefaulterList().trim().isEmpty()){
			form.setIsRbiDefaulter(requestDTO.getRBIDefaulterList().trim());
			if("Yes".equals(requestDTO.getRBIDefaulterList().trim())){
				//Change for RBI defaulter Type i.e Company ,Director and Group Companies
				if(null!=requestDTO.getRbiDefaulterListTypeCompany() && !requestDTO.getRbiDefaulterListTypeCompany().trim().isEmpty()){
					form.setRbiDefaulter(requestDTO.getRbiDefaulterListTypeCompany().trim());
					}
					else if(null!=requestDTO.getRbiDefaulterListTypeDirectors() && !requestDTO.getRbiDefaulterListTypeDirectors().trim().isEmpty()){
						form.setRbiDefaulter(requestDTO.getRbiDefaulterListTypeDirectors().trim());
						}
					else if(null!=requestDTO.getRbiDefaulterListTypeGroupCompanies() && !requestDTO.getRbiDefaulterListTypeGroupCompanies().trim().isEmpty()){
						form.setRbiDefaulter(requestDTO.getRbiDefaulterListTypeGroupCompanies().trim());
						}
				/* else{
					form.setRbiDefaulter(requestDTO.getRbiDefaulterListType());
				} */
			}
		}else{
			form.setIsRbiDefaulter(requestDTO.getRBIDefaulterList());
		}

		if(requestDTO.getLitigationPending()!=null && !requestDTO.getLitigationPending().trim().isEmpty()){
			form.setIsLitigation(requestDTO.getLitigationPending().trim());
			if("Yes".equals(requestDTO.getLitigationPending().trim())){
				if(requestDTO.getLitigationPendingBy()!=null && !requestDTO.getLitigationPendingBy().trim().isEmpty()){
					form.setLitigationBy(requestDTO.getLitigationPendingBy().trim());
				}else{
					form.setLitigationBy(requestDTO.getLitigationPendingBy());
				}
			}
		}else{
			form.setIsLitigation(requestDTO.getLitigationPending());
		}
		
		if(requestDTO.getInterestOfDirectors()!=null && !requestDTO.getInterestOfDirectors().trim().isEmpty()){
			form.setIsInterestOfBank(requestDTO.getInterestOfDirectors().trim());
			if("Yes".equals(requestDTO.getInterestOfDirectors().trim())){
				if(requestDTO.getInterestOfDirectorsType()!=null && !requestDTO.getInterestOfDirectorsType().trim().isEmpty()){
					form.setInterestOfBank(requestDTO.getInterestOfDirectorsType().trim());
				}else{
					form.setInterestOfBank(requestDTO.getInterestOfDirectorsType());
				}
			}
		}else{
			form.setIsInterestOfBank(requestDTO.getInterestOfDirectors());
		}

		if(requestDTO.getAdverseRemark()!=null && !requestDTO.getAdverseRemark().trim().isEmpty()){
			form.setIsAdverseRemark(requestDTO.getAdverseRemark().trim());
			if("Yes".equals(requestDTO.getAdverseRemark().trim())){
				if(requestDTO.getAdverseRemarkValue()!=null && !requestDTO.getAdverseRemarkValue().trim().isEmpty()){
					form.setAdverseRemark(requestDTO.getAdverseRemarkValue().trim());
				}else{
					form.setAdverseRemark(requestDTO.getAdverseRemarkValue());
				}
			}
		}else{
			form.setIsAdverseRemark(requestDTO.getAdverseRemark());
		}

		if(requestDTO.getAudit()!=null && !requestDTO.getAudit().trim().isEmpty()){
			form.setAuditType(requestDTO.getAudit().trim());
		}else{
			form.setAuditType(requestDTO.getAudit());
		}
		
		if(requestDTO.getAvgAnnualTurnover()!=null && !requestDTO.getAvgAnnualTurnover().trim().isEmpty()){
			form.setAvgAnnualTurnover(requestDTO.getAvgAnnualTurnover().trim());
		}else{
			form.setAvgAnnualTurnover(requestDTO.getAvgAnnualTurnover());
		}
		
		if(requestDTO.getIndustryExposurePercent()!=null && !requestDTO.getIndustryExposurePercent().trim().isEmpty()){
			form.setIndustryExposer(requestDTO.getIndustryExposurePercent().trim());
		}else{
			form.setIndustryExposer(requestDTO.getIndustryExposurePercent());
		}
		
		form.setIsDirecOtherBank(requestDTO.getIsBorrowerDirector());
		form.setIsPartnerOtherBank(requestDTO.getIsBorrowerPartner());
		form.setIsSubstantialOtherBank(requestDTO.getIsDirectorOfOtherBank());
		form.setIsHdfcDirecRltv(requestDTO.getIsRelativeOfHDFCBank());
		form.setIsObkDirecRltv(requestDTO.getIsRelativeOfChairman());
		form.setIsPartnerDirecRltv(requestDTO.getIsPartnerRelativeOfBanks());
		form.setIsSubstantialRltvHdfcOther(requestDTO.getIsShareholderRelativeOfBank());
		
		if(requestDTO.getIsBorrowerDirector()!=null && "Yes".equals(requestDTO.getIsBorrowerDirector())){
			form.setDirecOtherBank(requestDTO.getBorrowerDirectorValue());
		}
		if(requestDTO.getIsBorrowerPartner()!=null && "Yes".equals(requestDTO.getIsBorrowerPartner())){
			form.setPartnerOtherBank(requestDTO.getBorrowerPartnerValue());
		}
		if(requestDTO.getIsDirectorOfOtherBank()!=null && "Yes".equals(requestDTO.getIsDirectorOfOtherBank())){
			form.setSubstantialOtherBank(requestDTO.getDirectorOfOtherBankValue());
		}
		if(requestDTO.getIsRelativeOfHDFCBank()!=null && "Yes".equals(requestDTO.getIsRelativeOfHDFCBank())){
			form.setHdfcDirecRltv(requestDTO.getRelativeOfHDFCBankValue());
		}
		if(requestDTO.getIsRelativeOfChairman()!=null && "Yes".equals(requestDTO.getIsRelativeOfChairman())){
			form.setObkDirecRltv(requestDTO.getRelativeOfChairmanValue());
		}
		if(requestDTO.getIsPartnerRelativeOfBanks()!=null && "Yes".equals(requestDTO.getIsPartnerRelativeOfBanks())){
			form.setPartnerDirecRltv(requestDTO.getPartnerRelativeOfBanksValue());
		}
		if(requestDTO.getIsShareholderRelativeOfBank()!=null && "Yes".equals(requestDTO.getIsShareholderRelativeOfBank())){
			form.setSubstantialRltvHdfcOther(requestDTO.getShareholderRelativeOfBankValue());
		}
		
		if(requestDTO.getIsBackedByGovt()!=null && !requestDTO.getIsBackedByGovt().trim().isEmpty()){
			form.setIsBackedByGovt(requestDTO.getIsBackedByGovt().trim());
		}else{
			form.setIsBackedByGovt(requestDTO.getIsBackedByGovt());
		}
		
		if(requestDTO.getFirstYear()!=null && requestDTO.getFirstYear().trim().isEmpty()){
			form.setFirstYear(requestDTO.getFirstYear().trim());
		}else{
			form.setFirstYear(requestDTO.getFirstYear());
		}
		
		if(requestDTO.getFirstYearTurnover()!=null && !requestDTO.getFirstYearTurnover().trim().isEmpty()){
			form.setFirstYearTurnover(requestDTO.getFirstYearTurnover().trim());
		}else{
			form.setFirstYearTurnover(requestDTO.getFirstYearTurnover());
		}
		
		if(requestDTO.getTurnoverCurrency()!=null && !requestDTO.getTurnoverCurrency().trim().isEmpty()){
			form.setFirstYearTurnoverCurr(requestDTO.getTurnoverCurrency().trim());
			form.setSecondYearTurnoverCurr(requestDTO.getTurnoverCurrency().trim());
			form.setThirdYearTurnoverCurr(requestDTO.getTurnoverCurrency().trim());
		}else{
			form.setFirstYearTurnoverCurr(requestDTO.getTurnoverCurrency());
			form.setSecondYearTurnoverCurr(requestDTO.getTurnoverCurrency());
			form.setThirdYearTurnoverCurr(requestDTO.getTurnoverCurrency());
		}

		if(requestDTO.getIsSPVFunding()!=null && !requestDTO.getIsSPVFunding().trim().isEmpty()) {
			form.setIsSPVFunding(requestDTO.getIsSPVFunding().trim());
		}
		if(requestDTO.getIndirectCountryRiskExposure()!=null && !requestDTO.getIndirectCountryRiskExposure().trim().isEmpty()) {
			form.setIndirectCountryRiskExposure(requestDTO.getIndirectCountryRiskExposure());
			if("Yes".equals(requestDTO.getIndirectCountryRiskExposure())) {
				form.setCriCountryName(requestDTO.getCriCountryName().trim());
				form.setSalesPercentage(requestDTO.getSalesPercentage().trim());			
			}
		}
		if(requestDTO.getIsIPRE()!=null && !requestDTO.getIsIPRE().trim().isEmpty()) {
			form.setIsIPRE(requestDTO.getIsIPRE().trim());
		}
		if(requestDTO.getFinanceForAccquisition()!=null && !requestDTO.getFinanceForAccquisition().trim().isEmpty()) {
			form.setFinanceForAccquisition(requestDTO.getFinanceForAccquisition());
			if("Yes".equals(requestDTO.getFinanceForAccquisition())) {
				form.setFacilityApproved(requestDTO.getFacilityApproved().trim());
				form.setFacilityAmount(requestDTO.getFacilityAmount().trim());
				form.setSecurityName(requestDTO.getSecurityName().trim());
				form.setSecurityType(requestDTO.getSecurityType().trim());
				form.setSecurityValue(requestDTO.getSecurityValue().trim());
			}
		}
		if(requestDTO.getCompanyType()!=null && !requestDTO.getCompanyType().trim().isEmpty()) {
			form.setCompanyType(requestDTO.getCompanyType());
			if("Yes".equals(requestDTO.getCompanyType())) {
				form.setNameOfHoldingCompany(requestDTO.getNameOfHoldingCompany().trim());
				form.setType(requestDTO.getType().trim());
			}
		}
		if(requestDTO.getCategoryOfFarmer()!=null && !requestDTO.getCategoryOfFarmer().trim().isEmpty()) {
			form.setCategoryOfFarmer(requestDTO.getCategoryOfFarmer().trim());
			if(requestDTO.getLandHolding()!=null && !requestDTO.getLandHolding().trim().isEmpty()) {
				form.setLandHolding(requestDTO.getLandHolding().trim());
			}
		}
		if(requestDTO.getCountryOfGuarantor()!=null && !requestDTO.getCountryOfGuarantor().trim().isEmpty()) {
			form.setCountryOfGuarantor(requestDTO.getCountryOfGuarantor().trim());
		}
		if(requestDTO.getIsAffordableHousingFinance()!=null && !requestDTO.getIsAffordableHousingFinance().trim().isEmpty()) {
			form.setIsAffordableHousingFinance(requestDTO.getIsAffordableHousingFinance().trim());
		}
		if(requestDTO.getIsInRestrictedList()!=null && !requestDTO.getIsInRestrictedList().trim().isEmpty()) {
			form.setIsInRestrictedList(requestDTO.getIsInRestrictedList());
			if("Yes".equals(requestDTO.getIsInRestrictedList())) {
				form.setRestrictedFinancing(requestDTO.getRestrictedFinancing().trim());
			}
		}
		if(requestDTO.getRestrictedIndustries()!=null && !requestDTO.getRestrictedIndustries().trim().isEmpty()) {
			form.setRestrictedIndustries(requestDTO.getRestrictedIndustries());
			if("Yes".equals(requestDTO.getRestrictedIndustries())) {
				form.setRestrictedListIndustries(requestDTO.getRestrictedListIndustries().trim());
			}
		}
		if(requestDTO.getIsBorrowerInRejectDatabase()!=null && !requestDTO.getIsBorrowerInRejectDatabase().trim().isEmpty()) {
			form.setIsBorrowerInRejectDatabase(requestDTO.getIsBorrowerInRejectDatabase());
			if("Yes".equals(requestDTO.getIsBorrowerInRejectDatabase())) {
				form.setRejectHistoryReason(requestDTO.getRejectHistoryReason().trim());
			}
		}	
		if(requestDTO.getCapitalForCommodityAndExchange()!=null && !requestDTO.getCapitalForCommodityAndExchange().trim().isEmpty()) {
			form.setCapitalForCommodityAndExchange(requestDTO.getCapitalForCommodityAndExchange());
			if("Broker".equals(requestDTO.getCapitalForCommodityAndExchange()) && 
					(requestDTO.getIsBrokerTypeShare()!=null && !requestDTO.getIsBrokerTypeShare().trim().isEmpty()) &&
					(requestDTO.getIsBrokerTypeCommodity()==null || requestDTO.getIsBrokerTypeCommodity().trim().isEmpty())) {
				form.setIsBrokerTypeShare(requestDTO.getIsBrokerTypeShare().trim());
			}else if("Broker".equals(requestDTO.getCapitalForCommodityAndExchange()) && 
					(requestDTO.getIsBrokerTypeShare()==null || requestDTO.getIsBrokerTypeShare().trim().isEmpty()) &&
					(requestDTO.getIsBrokerTypeCommodity()!=null && !requestDTO.getIsBrokerTypeCommodity().trim().isEmpty())) {
				form.setIsBrokerTypeCommodity(requestDTO.getIsBrokerTypeCommodity().trim());
			}else if("Broker".equals(requestDTO.getCapitalForCommodityAndExchange()) && 
					(requestDTO.getIsBrokerTypeShare()!=null && !requestDTO.getIsBrokerTypeShare().trim().isEmpty()) &&
					(requestDTO.getIsBrokerTypeCommodity()!=null && !requestDTO.getIsBrokerTypeCommodity().trim().isEmpty())) {
				form.setIsBrokerTypeShare(requestDTO.getIsBrokerTypeShare().trim());
				form.setIsBrokerTypeCommodity(requestDTO.getIsBrokerTypeCommodity().trim());
			}
		}
		if(requestDTO.getObjectFinance()!=null && !requestDTO.getObjectFinance().trim().isEmpty()) {
			form.setObjectFinance(requestDTO.getObjectFinance().trim());
		}
		if(requestDTO.getIsCommodityFinanceCustomer()!=null && !requestDTO.getIsCommodityFinanceCustomer().trim().isEmpty()) {
			form.setIsCommodityFinanceCustomer(requestDTO.getIsCommodityFinanceCustomer().trim());
		}
		if(requestDTO.getRestructuedBorrowerOrFacility()!=null && !requestDTO.getRestructuedBorrowerOrFacility().trim().isEmpty()) {
			form.setRestructuedBorrowerOrFacility(requestDTO.getRestructuedBorrowerOrFacility());
			if("Yes".equals(requestDTO.getRestructuedBorrowerOrFacility())) {
				form.setFacility(requestDTO.getFacility().trim());
				form.setLimitAmount(requestDTO.getLimitAmount().trim());
			}
		}
		if("Yes".equals(requestDTO.getTufs().trim())){
			if(requestDTO.getSubsidyFlag()!=null && !requestDTO.getSubsidyFlag().trim().isEmpty()) {
				form.setSubsidyFlag(requestDTO.getSubsidyFlag());
				if("Yes".equals(requestDTO.getSubsidyFlag())) {
					form.setHoldingCompnay(requestDTO.getHoldingCompnay().trim());
				}
			}
		}
		if(requestDTO.getCautionList()!=null && !requestDTO.getCautionList().trim().isEmpty()) {
			form.setCautionList(requestDTO.getCautionList());
			if("Yes".equals(requestDTO.getCautionList())) {
				
//				try {
//					if (requestDTO.getDateOfCautionList()!=null && !requestDTO.getDateOfCautionList().trim().isEmpty()) {
//						form.setDateOfCautionList(relationshipDateFormat.parse(requestDTO.getDateOfCautionList().trim()));
//					}
//				}catch (ParseException e) {
//					e.printStackTrace();
//				}
				
				form.setDateOfCautionList(requestDTO.getDateOfCautionList().trim());
				form.setCompany(requestDTO.getCompany().trim());
				form.setDirectors(requestDTO.getDirectors().trim());
				form.setGroupCompanies(requestDTO.getGroupCompanies().trim());
			}
		}
		if(requestDTO.getDefaultersList()!=null && !requestDTO.getDefaultersList().trim().isEmpty()) {
			form.setDefaultersList(requestDTO.getDefaultersList());
			if("Yes".equals(requestDTO.getDefaultersList().trim())) {
				form.setRbiDateOfCautionList(requestDTO.getRbiDateOfCautionList().trim());
				form.setRbiCompany(requestDTO.getRbiCompany().trim());
				form.setRbiDirectors(requestDTO.getRbiDirectors().trim());
				form.setRbiGroupCompanies(requestDTO.getRbiGroupCompanies().trim());
			}
		}
		
		if (requestDTO.getCommericialRealEstate() != null
				&& !requestDTO.getCommericialRealEstate().trim().isEmpty()) {
			form.setCommericialRealEstate(requestDTO.getCommericialRealEstate());
			if ("Yes".equals(requestDTO.getCommericialRealEstate())) {
				form.setCommericialRealEstateValue(requestDTO.getCommericialRealEstateValue());
				form.setCommericialRealEstateResidentialHousing("No");
				form.setResidentialRealEstate("No");
				form.setIndirectRealEstate("No");

			} else {
				if (requestDTO.getCommericialRealEstateResidentialHousing() != null
						&& "Yes".equals(
								requestDTO.getCommericialRealEstateResidentialHousing())) {
					form.setCommericialRealEstateResidentialHousing(
							requestDTO.getCommericialRealEstateResidentialHousing());
					form.setResidentialRealEstate("No");
					form.setIndirectRealEstate("No");
					form.setCommericialRealEstate("No");
				} else if (requestDTO.getResidentialRealEstate() != null
						&& "Yes".equals(requestDTO.getResidentialRealEstate())) {
					form.setResidentialRealEstate(requestDTO.getResidentialRealEstate());
					form.setCommericialRealEstateResidentialHousing("No");
					form.setIndirectRealEstate("No");
					form.setCommericialRealEstate("No");
				} else if (requestDTO.getIndirectRealEstate() != null
						&& "Yes".equals(requestDTO.getIndirectRealEstate())) {
					form.setIndirectRealEstate(requestDTO.getIndirectRealEstate());
					form.setCommericialRealEstateResidentialHousing("No");
					form.setResidentialRealEstate("No");
					form.setCommericialRealEstate("No");
				}
			}
		}

		if(requestDTO.getConductOfAccountWithOtherBanks()!=null && !requestDTO.getConductOfAccountWithOtherBanks().trim().isEmpty()) {
			form.setConductOfAccountWithOtherBanks(requestDTO.getConductOfAccountWithOtherBanks());
			if("classified".equals(requestDTO.getConductOfAccountWithOtherBanks())) {
				form.setCrilicStatus(requestDTO.getCrilicStatus());
				if(requestDTO.getCrilicStatus()!=null && !requestDTO.getCrilicStatus().trim().isEmpty()) {
					form.setComment(requestDTO.getComment());
				}	
			}	
		}else {
			form.setConductOfAccountWithOtherBanks("Satisfactory");
		}
		
		if(requestDTO.getIsRBIWilfulDefaultersList()!=null && !requestDTO.getIsRBIWilfulDefaultersList().trim().isEmpty()) {
			form.setIsRBIWilfulDefaultersList(requestDTO.getIsRBIWilfulDefaultersList());
			if("Yes".equals(requestDTO.getIsRBIWilfulDefaultersList())) {
				form.setNameOfBank(requestDTO.getNameOfBank());
				form.setIsDirectorMoreThanOne(requestDTO.getIsDirectorMoreThanOne());
				if("Yes".equals(requestDTO.getIsDirectorMoreThanOne())) {
					form.setNameOfDirectorsAndCompany(requestDTO.getNameOfDirectorsAndCompany());
				}else {
					form.setNameOfDirectorsAndCompany("");
				}
			}else {
				form.setIsRBIWilfulDefaultersList(DEFAULT_VALUE_FOR_CRI_INFO);
				form.setNameOfBank("");
			}
		}else {
			form.setIsRBIWilfulDefaultersList(DEFAULT_VALUE_FOR_CRI_INFO);
			form.setNameOfBank("");
			form.setIsDirectorMoreThanOne("");
			form.setNameOfDirectorsAndCompany("");
		}
		
		if(requestDTO.getIsCibilStatusClean()!=null && !requestDTO.getIsCibilStatusClean().trim().isEmpty()) {
			form.setIsCibilStatusClean(requestDTO.getIsCibilStatusClean());		 
			if("No".equals(requestDTO.getIsCibilStatusClean())) {
				form.setDetailsOfCleanCibil(requestDTO.getDetailsOfCleanCibil());
			}else {
				form.setIsCibilStatusClean("Yes");
				form.setDetailsOfCleanCibil("");
			}
		}else {
			form.setDetailsOfCleanCibil("Yes");
		}
		
		//UDF Method Info(one-to-many)
		if(requestDTO.getUdfMethodDetailList()!=null && !requestDTO.getUdfMethodDetailList().isEmpty()){
			for(PartyUdfMethodDetailsRequestDTO udfMthdDetReqDTO: requestDTO.getUdfMethodDetailList()){
				
				if(udfMthdDetReqDTO.getUdf63()!=null && !udfMthdDetReqDTO.getUdf63().trim().isEmpty()){
					form.setUdf63(udfMthdDetReqDTO.getUdf63());
				}
				
				if(udfMthdDetReqDTO.getUdf78()!=null && !udfMthdDetReqDTO.getUdf78().trim().isEmpty()){
					if(udfMthdDetReqDTO.getUdf78().equals("YES")) {
						form.setUdf78("YES");						
					}else {
						form.setUdf78("NO");
					}
				}else{
					form.setUdf78("NO");
				}
				
				if(udfMthdDetReqDTO.getUdf79()!=null && !udfMthdDetReqDTO.getUdf79().trim().isEmpty()){
					if(udfMthdDetReqDTO.getUdf79().equals("YES")) {
						form.setUdf79("YES");
					}else {
						form.setUdf79("NO");
					}
				}else{
					form.setUdf79("NO");
				}
			}
		}

		
		// System info (one-to-many)
		if("WS_create_customer".equals(requestDTO.getEvent())){
			List sysList = new ArrayList();
			if(requestDTO.getSystemDetReqList()!=null && !requestDTO.getSystemDetReqList().isEmpty()){
				for(PartySystemDetailsRequestDTO systemDetReqDTO : requestDTO.getSystemDetReqList()){
					
					if(systemDetReqDTO.getSystem()!=null && !systemDetReqDTO.getSystem().trim().isEmpty()){
						form.setSystem(systemDetReqDTO.getSystem().trim());
					}else{
						form.setSystem(systemDetReqDTO.getSystem());
					}
					
					if(systemDetReqDTO.getSystemId()!=null && !systemDetReqDTO.getSystemId().trim().isEmpty()){
						form.setSystemId(systemDetReqDTO.getSystemId().trim());
						form.setSystemCustomerId(systemDetReqDTO.getSystemId().trim());
					}else{
						form.setSystemId(systemDetReqDTO.getSystemId());
						form.setSystemCustomerId(systemDetReqDTO.getSystemId());
					}
					
					sysList.add(systemDetReqDTO);
				}
			}
			form.setOtherSystem(sysList);
		}
		
		//Banking Method Info(one-to-many)
		List bankList = new ArrayList();
		if(!"UPDATE".equals(addOrUpdate)) {
		if(requestDTO.getBankingMethodDetailList()!=null && !requestDTO.getBankingMethodDetailList().isEmpty()){
			for(PartyBankingMethodDetailsRequestDTO bankMthdDetReqDTO: requestDTO.getBankingMethodDetailList()){
				
				if(bankMthdDetReqDTO.getLeadNodalFlag()!=null && !bankMthdDetReqDTO.getLeadNodalFlag().trim().isEmpty()){
					form.setNodalLead(bankMthdDetReqDTO.getLeadNodalFlag().trim());
				}else{
					form.setNodalLead(bankMthdDetReqDTO.getLeadNodalFlag());
				}
				
				if(bankMthdDetReqDTO.getBankType()!=null && !bankMthdDetReqDTO.getBankType().trim().isEmpty()){
					form.setBankName(bankMthdDetReqDTO.getBankType().trim());
				}else{
					form.setBankName(bankMthdDetReqDTO.getBankType());
				}
				
				if(bankMthdDetReqDTO.getBranchId()!=null && !bankMthdDetReqDTO.getBranchId().trim().isEmpty()){
					form.setBankBranchId(bankMthdDetReqDTO.getBranchId().trim());
				}else{
					form.setBankBranchId(bankMthdDetReqDTO.getBranchId());
				}

				bankList.add(bankMthdDetReqDTO);
			}
		}
		
		form.setOtherBank(bankList);
		}
		//Director info(one-to-many)
		if(requestDTO.getDirectorDetailList()!=null && !requestDTO.getDirectorDetailList().isEmpty()){
			for(PartyDirectorDetailsRequestDTO directorDetReqDTO: requestDTO.getDirectorDetailList()){
				
				if(directorDetReqDTO.getRelatedType()!=null && !directorDetReqDTO.getRelatedType().trim().isEmpty()){
					form.setRelatedType(directorDetReqDTO.getRelatedType().trim());
				}else{
					form.setRelatedType(directorDetReqDTO.getRelatedType());
				}
				
				if(directorDetReqDTO.getRelationship()!=null && !directorDetReqDTO.getRelationship().trim().isEmpty()){
					form.setRelationship(directorDetReqDTO.getRelationship().trim());
				}else{
					form.setRelationship(directorDetReqDTO.getRelationship());
				}
			
				if(directorDetReqDTO.getDirectorEmailId()!=null && !directorDetReqDTO.getDirectorEmailId().trim().isEmpty()){
					form.setDirectorEmail(directorDetReqDTO.getDirectorEmailId().trim());
				}else{
					form.setDirectorEmail(directorDetReqDTO.getDirectorEmailId());
				}
				
				if(directorDetReqDTO.getDirectorFaxNo()!=null && !directorDetReqDTO.getDirectorFaxNo().trim().isEmpty()){
					form.setDirectorFax(directorDetReqDTO.getDirectorFaxNo().trim());
				}else{
					form.setDirectorFax(directorDetReqDTO.getDirectorFaxNo());
				}
				
				if(directorDetReqDTO.getDirectorTelNo()!=null && !directorDetReqDTO.getDirectorTelNo().trim().isEmpty()){
					form.setDirectorTelNo(directorDetReqDTO.getDirectorTelNo().trim());
				}else{
					form.setDirectorTelNo(directorDetReqDTO.getDirectorTelNo());
				}
				
				if(directorDetReqDTO.getDirectorTelephoneStdCode()!=null && !directorDetReqDTO.getDirectorTelephoneStdCode().trim().isEmpty()){
					form.setDirStdCodeTelNo(directorDetReqDTO.getDirectorTelephoneStdCode().trim());
				}else{
					form.setDirStdCodeTelNo(directorDetReqDTO.getDirectorTelephoneStdCode());
				}
				
				if(directorDetReqDTO.getDirectorFaxStdCode()!=null && !directorDetReqDTO.getDirectorFaxStdCode().trim().isEmpty()){
					form.setDirStdCodeTelex(directorDetReqDTO.getDirectorFaxStdCode().trim());
				}else{
					form.setDirStdCodeTelex(directorDetReqDTO.getDirectorFaxStdCode());
				}
			
				if(directorDetReqDTO.getDirectorCountry()!=null && !directorDetReqDTO.getDirectorCountry().trim().isEmpty()){
					form.setDirectorCountry(directorDetReqDTO.getDirectorCountry().trim());
				}else{
					form.setDirectorCountry(directorDetReqDTO.getDirectorCountry());
				}
				
				if(directorDetReqDTO.getDirectorState()!=null && !directorDetReqDTO.getDirectorState().trim().isEmpty()){
					form.setDirectorState(directorDetReqDTO.getDirectorState().trim());
				}else{
					form.setDirectorState(directorDetReqDTO.getDirectorState());
				}
				
				if(directorDetReqDTO.getDirectorCity()!=null && !directorDetReqDTO.getDirectorCity().trim().isEmpty()){
					form.setDirectorCity(directorDetReqDTO.getDirectorCity().trim());
				}else{
					form.setDirectorCity(directorDetReqDTO.getDirectorCity());
				}
				
				if(directorDetReqDTO.getDirectorRegion()!=null && !directorDetReqDTO.getDirectorRegion().trim().isEmpty()){
					form.setDirectorRegion(directorDetReqDTO.getDirectorRegion().trim());
				}else{
					form.setDirectorRegion(directorDetReqDTO.getDirectorRegion());
				}
				
				if(directorDetReqDTO.getDirectorPincode()!=null && !directorDetReqDTO.getDirectorPincode().trim().isEmpty()){
					form.setDirectorPostCode(directorDetReqDTO.getDirectorPincode().trim());
				}else{
					form.setDirectorPostCode(directorDetReqDTO.getDirectorPincode());
				}
				
				if(directorDetReqDTO.getDirectorAddr3()!=null && !directorDetReqDTO.getDirectorAddr3().trim().isEmpty()){
					form.setDirectorAddress3(directorDetReqDTO.getDirectorAddr3().trim());
				}else{
					form.setDirectorAddress3(directorDetReqDTO.getDirectorAddr3());
				}
				
				if(directorDetReqDTO.getDirectorAddr2()!=null && !directorDetReqDTO.getDirectorAddr2().trim().isEmpty()){
					form.setDirectorAddress2(directorDetReqDTO.getDirectorAddr2().trim());
				}else{
					form.setDirectorAddress1(directorDetReqDTO.getDirectorAddr2());
				}
				
				if(directorDetReqDTO.getDirectorAddr1()!=null && !directorDetReqDTO.getDirectorAddr1().trim().isEmpty()){
					form.setDirectorAddress1(directorDetReqDTO.getDirectorAddr1().trim());
				}else{
					form.setDirectorAddress1(directorDetReqDTO.getDirectorAddr1());
				}
				
				if(directorDetReqDTO.getPercentageOfControl()!=null && !directorDetReqDTO.getPercentageOfControl().trim().isEmpty()){
					form.setPercentageOfControl(directorDetReqDTO.getPercentageOfControl().trim());
				}else{
					form.setPercentageOfControl(directorDetReqDTO.getPercentageOfControl());
				}
				
				if(directorDetReqDTO.getFullName()!=null && !directorDetReqDTO.getFullName().trim().isEmpty()){
					form.setFullName(directorDetReqDTO.getFullName().trim());
				}else{
					form.setFullName(directorDetReqDTO.getFullName());
				}
				
				if(directorDetReqDTO.getNamePrefix()!=null && !directorDetReqDTO.getNamePrefix().trim().isEmpty()){
					form.setNamePrefix(directorDetReqDTO.getNamePrefix().trim());
				}else{
					form.setNamePrefix(directorDetReqDTO.getNamePrefix());
				}
				
				if(directorDetReqDTO.getDirectorPAN()!=null && !directorDetReqDTO.getDirectorPAN().trim().isEmpty()){
					form.setDirectorPan(directorDetReqDTO.getDirectorPAN().trim());
				}else{
					form.setDirectorPan(directorDetReqDTO.getDirectorPAN());
				}
				
				if(directorDetReqDTO.getBusinessEntityName()!=null && !directorDetReqDTO.getBusinessEntityName().trim().isEmpty()){
					form.setBusinessEntityName(directorDetReqDTO.getBusinessEntityName().trim());
				}else{
					form.setBusinessEntityName(directorDetReqDTO.getBusinessEntityName());
				}
				
			/*	if(directorDetReqDTO.getDirectorAADHAR()!=null && !directorDetReqDTO.getDirectorAADHAR().trim().isEmpty()){
					form.setDirectorAadhar(directorDetReqDTO.getDirectorAADHAR().trim());
				}else{
					form.setDirectorAadhar(directorDetReqDTO.getDirectorAADHAR());
				}*/
			}
		}
		
		//contact info 
		form.setContactType("CORPORATE");
		if(requestDTO.getAddress1()!=null && !requestDTO.getAddress1().trim().isEmpty()){
			form.setAddress1(requestDTO.getAddress1().trim());
		}else{
			form.setAddress1(requestDTO.getAddress1());
		}
		
		if(requestDTO.getAddress2()!=null && !requestDTO.getAddress2().trim().isEmpty()){
			form.setAddress2(requestDTO.getAddress2().trim());
		}else{
			form.setAddress2(requestDTO.getAddress2());
		}
		
		if(requestDTO.getAddress3()!=null && !requestDTO.getAddress3().trim().isEmpty()){
			form.setAddress3(requestDTO.getAddress3().trim());
		}else{
			form.setAddress3(requestDTO.getAddress3());
		}

		if(requestDTO.getRegion()!=null && !requestDTO.getRegion().trim().isEmpty()){
			
			form.setRegion(requestDTO.getRegion().trim());
			
			//To Do :Get Region Object through DAO and get country
			IRegionDAO regionDao = (IRegionDAO)BeanHouse.get("regionDAO");
			try {
				form.setCountry(Long.toString((regionDao.getRegionById(Long.parseLong(requestDTO.getRegion().trim()))).getCountryId().getIdCountry()));
			} catch (NoSuchGeographyException e) {
				e.printStackTrace();
				throw new CMSException(e);
			} catch (TrxParameterException e) {
				e.printStackTrace();
				throw new CMSException(e);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new CMSException(e);
			} catch (TransactionException e) {
				e.printStackTrace();
				throw new CMSException(e);
			}
		}else{
			form.setCountry(requestDTO.getRegion());
			form.setRegion(requestDTO.getRegion());
		}
		
		if(requestDTO.getState()!=null && !requestDTO.getState().trim().isEmpty()){
			form.setState(requestDTO.getState().trim());
		}else{
			form.setState(requestDTO.getState());
		}
		
		if(requestDTO.getCity()!=null && !requestDTO.getCity().trim().isEmpty()){
			form.setCity(requestDTO.getCity().trim());
		}else{
			form.setCity(requestDTO.getCity());
		}
		
		if(requestDTO.getPincode()!=null && !requestDTO.getPincode().trim().isEmpty()){
			form.setPostcode(requestDTO.getPincode().trim());
		}else{
			form.setPostcode(requestDTO.getPincode());
		}
		
		if(requestDTO.getEmailId()!=null && !requestDTO.getEmailId().trim().isEmpty()){
			form.setEmail(requestDTO.getEmailId().trim());
		}else{
			form.setEmail(requestDTO.getEmailId());
		}
		
		if(requestDTO.getFaxNumber()!=null && !requestDTO.getFaxNumber().trim().isEmpty()){
			form.setTelex(requestDTO.getFaxNumber().trim());
		}else{
			form.setTelex(requestDTO.getFaxNumber());
		}
		
		if(requestDTO.getTelephoneNo()!=null && !requestDTO.getTelephoneNo().trim().isEmpty()){
			form.setTelephoneNo(requestDTO.getTelephoneNo().trim());
		}else{
			form.setTelephoneNo(requestDTO.getTelephoneNo().trim());
		}
		
		if(requestDTO.getTelephoneStdCode()!=null && !requestDTO.getTelephoneStdCode().trim().isEmpty()){
			form.setStdCodeTelNo(requestDTO.getTelephoneStdCode().trim());
		}else{
			form.setStdCodeTelNo(requestDTO.getTelephoneStdCode());
		}
		
		if(requestDTO.getFaxStdCode()!=null && !requestDTO.getFaxStdCode().trim().isEmpty()){
			form.setStdCodeTelex(requestDTO.getFaxStdCode().trim());
		}else{
			form.setStdCodeTelex(requestDTO.getFaxStdCode());
		}
		
		if(requestDTO.getRegisteredAddr1()!=null && !requestDTO.getRegisteredAddr1().trim().isEmpty()){
			form.setRegOfficeAddress1(requestDTO.getRegisteredAddr1().trim());
		}else{
			form.setRegOfficeAddress1(requestDTO.getRegisteredAddr1());
		}
		
		if(requestDTO.getRegisteredAddr2()!=null && !requestDTO.getRegisteredAddr2().trim().isEmpty()){
			form.setRegOfficeAddress2(requestDTO.getRegisteredAddr2().trim());
		}else{
			form.setRegOfficeAddress2(requestDTO.getRegisteredAddr2());
		}
		
		if(requestDTO.getRegisteredAddr3()!=null && !requestDTO.getRegisteredAddr3().trim().isEmpty()){
			form.setRegOfficeAddress3(requestDTO.getRegisteredAddr3().trim());
		}else{
			form.setRegOfficeAddress3(requestDTO.getRegisteredAddr3());
		}
		
		if(requestDTO.getRegisteredCountry()!=null && !requestDTO.getRegisteredCountry().trim().isEmpty()){
			form.setRegOfficeCountry(requestDTO.getRegisteredCountry().trim());
		}else{
			form.setRegOfficeCountry(requestDTO.getRegisteredCountry());
		}
		
		if(requestDTO.getRegisteredRegion()!=null && !requestDTO.getRegisteredRegion().trim().isEmpty()){
			form.setRegOfficeRegion(requestDTO.getRegisteredRegion().trim());
		}else{
			form.setRegOfficeRegion(requestDTO.getRegisteredRegion());
		}
		
		if(requestDTO.getRegisteredState()!=null && !requestDTO.getRegisteredState().trim().isEmpty()){
			form.setRegOfficeState(requestDTO.getRegisteredState().trim());
		}else{
			form.setRegOfficeState(requestDTO.getRegisteredState());
		}
		
		if(requestDTO.getRegisteredCity()!=null && !requestDTO.getRegisteredCity().trim().isEmpty()){
			form.setRegOfficeCity(requestDTO.getRegisteredCity().trim());
		}else{
			form.setRegOfficeCity(requestDTO.getRegisteredCity());
		}
		
		if(requestDTO.getRegisteredPincode()!=null && !requestDTO.getRegisteredPincode().trim().isEmpty()){
			form.setRegOfficePostCode(requestDTO.getRegisteredPincode().trim());
		}else{
			form.setRegOfficePostCode(requestDTO.getRegisteredPincode());
		}
		
		if(requestDTO.getRegisteredTelNo()!=null && !requestDTO.getRegisteredTelNo().trim().isEmpty()){
			form.setRegOfficeTelephoneNo(requestDTO.getRegisteredTelNo().trim());
		}else{
			form.setRegOfficeTelephoneNo(requestDTO.getRegisteredTelNo());
		}
		
		if(requestDTO.getRegisteredFaxNumber()!=null && !requestDTO.getRegisteredFaxNumber().trim().isEmpty()){
			form.setRegOfficeTelex(requestDTO.getRegisteredFaxNumber().trim());
		}else{
			form.setRegOfficeTelex(requestDTO.getRegisteredFaxNumber());
		}
		
		if(requestDTO.getRegisteredTelephoneStdCode()!=null && !requestDTO.getRegisteredTelephoneStdCode().trim().isEmpty()){
			form.setStdCodeOfficeTelNo(requestDTO.getRegisteredTelephoneStdCode().trim());
		}else{
			form.setStdCodeOfficeTelNo(requestDTO.getRegisteredTelephoneStdCode());
		}
		
		if(requestDTO.getRegisteredFaxStdCode()!=null && !requestDTO.getRegisteredFaxStdCode().trim().isEmpty()){
			form.setStdCodeOfficeTelex(requestDTO.getRegisteredFaxStdCode().trim());
		}else{
			form.setStdCodeOfficeTelex(requestDTO.getRegisteredFaxStdCode());
		}

		getCoBorrowerDetailsFormFromDTO(requestDTO, form);
		
		//New Online CAM CR Start
		if (requestDTO.getListedCompany() != null) {
			form.setListedCompany(requestDTO.getListedCompany().trim());
		}else {
			form.setListedCompany(requestDTO.getListedCompany());
		}
		
		if (requestDTO.getIsinNo() != null) {
			form.setIsinNo(requestDTO.getIsinNo().trim());
		}else {
			form.setIsinNo(requestDTO.getIsinNo());
		}
		if (requestDTO.getRaroc() != null) {
			form.setRaroc(requestDTO.getRaroc().trim());
		}else {
			form.setRaroc(requestDTO.getRaroc());
		}
		if (requestDTO.getRaraocPeriod() != null) {
			form.setRaraocPeriod(requestDTO.getRaraocPeriod().trim());
		}else {
			form.setRaraocPeriod(requestDTO.getRaraocPeriod());
		}
		if (requestDTO.getYearEndPeriod() != null) {
			form.setYearEndPeriod(requestDTO.getYearEndPeriod().trim());
		}else {
			form.setYearEndPeriod(requestDTO.getYearEndPeriod());
		}
		
		if (requestDTO.getCreditMgrEmpId() != null) {
			form.setCreditMgrEmpId(requestDTO.getCreditMgrEmpId().trim());
		}else {
			form.setCreditMgrEmpId(requestDTO.getCreditMgrEmpId());
		}
		
		if (requestDTO.getPfLrdCreditMgrEmpId() != null) {
			form.setPfLrdCreditMgrEmpId(requestDTO.getPfLrdCreditMgrEmpId().trim());
		}else {
			form.setPfLrdCreditMgrEmpId(requestDTO.getPfLrdCreditMgrEmpId());
		}
		
		if(requestDTO.getCreditMgrSegment()!=null && !requestDTO.getCreditMgrSegment().trim().isEmpty()){
			form.setCreditMgrSegment(requestDTO.getCreditMgrSegment().trim());
		}else{
			form.setCreditMgrSegment(requestDTO.getCreditMgrSegment());
		}
		if(requestDTO.getMultBankFundBasedHdfcBankPer()!=null && !requestDTO.getMultBankFundBasedHdfcBankPer().trim().isEmpty()){
			form.setMultBankFundBasedHdfcBankPer(requestDTO.getMultBankFundBasedHdfcBankPer().trim());
		}else{
			form.setMultBankFundBasedHdfcBankPer(requestDTO.getMultBankFundBasedHdfcBankPer());
		}
		if(requestDTO.getMultBankFundBasedLeadBankPer()!=null && !requestDTO.getMultBankFundBasedLeadBankPer().trim().isEmpty()){
			form.setMultBankFundBasedLeadBankPer(requestDTO.getMultBankFundBasedLeadBankPer().trim());
		}else{
			form.setMultBankFundBasedLeadBankPer(requestDTO.getMultBankFundBasedLeadBankPer());
		}
		if(requestDTO.getMultBankNonFundBasedHdfcBankPer()!=null && !requestDTO.getMultBankNonFundBasedHdfcBankPer().trim().isEmpty()){
			form.setMultBankNonFundBasedHdfcBankPer(requestDTO.getMultBankNonFundBasedHdfcBankPer().trim());
		}else{
			form.setMultBankNonFundBasedHdfcBankPer(requestDTO.getMultBankNonFundBasedHdfcBankPer());
		}
		if(requestDTO.getMultBankNonFundBasedLeadBankPer()!=null && !requestDTO.getMultBankNonFundBasedLeadBankPer().trim().isEmpty()){
			form.setMultBankNonFundBasedLeadBankPer(requestDTO.getMultBankNonFundBasedLeadBankPer().trim());
		}else{
			form.setMultBankNonFundBasedLeadBankPer(requestDTO.getMultBankNonFundBasedLeadBankPer());
		}
		if(requestDTO.getConsBankFundBasedHdfcBankPer()!=null && !requestDTO.getConsBankFundBasedHdfcBankPer().trim().isEmpty()){
			form.setConsBankFundBasedHdfcBankPer(requestDTO.getConsBankFundBasedHdfcBankPer().trim());
		}else{
			form.setConsBankFundBasedHdfcBankPer(requestDTO.getConsBankFundBasedHdfcBankPer());
		}
		if(requestDTO.getConsBankFundBasedLeadBankPer()!=null && !requestDTO.getConsBankFundBasedLeadBankPer().trim().isEmpty()){
			form.setConsBankFundBasedLeadBankPer(requestDTO.getConsBankFundBasedLeadBankPer().trim());
		}else{
			form.setConsBankFundBasedLeadBankPer(requestDTO.getConsBankFundBasedLeadBankPer());
		}
		if(requestDTO.getConsBankNonFundBasedHdfcBankPer()!=null && !requestDTO.getConsBankNonFundBasedHdfcBankPer().trim().isEmpty()){
			form.setConsBankNonFundBasedHdfcBankPer(requestDTO.getConsBankNonFundBasedHdfcBankPer().trim());
		}else{
			form.setConsBankNonFundBasedHdfcBankPer(requestDTO.getConsBankNonFundBasedHdfcBankPer());
		}
		if(requestDTO.getConsBankNonFundBasedLeadBankPer()!=null && !requestDTO.getConsBankNonFundBasedLeadBankPer().trim().isEmpty()){
			form.setConsBankNonFundBasedLeadBankPer(requestDTO.getConsBankNonFundBasedLeadBankPer().trim());
		}else{
			form.setConsBankNonFundBasedLeadBankPer(requestDTO.getConsBankNonFundBasedLeadBankPer());
		}
		
		//New Online CAM CR End

		return form;
	}
	
	private static void getCoBorrowerDetailsFormFromDTO(PartyDetailsRequestDTO request, ManualInputCustomerInfoForm form){
		if(request==null || request.getCoBorrowerDetailsList()==null)
			return;
		
		form.setCoBorrowerDetailsInd(request.getCoBorrowerDetailsInd());
		
		PartyCoBorrowerDetailsRequestDTO coBorrowerRequest = request.getCoBorrowerDetailsList();
		ICustomerDAO custDAO = CustomerDAOFactory.getDAO();
		if(!WS_CALL_CREATE_PARTY.equals(request.getEvent())) {
			List<CoBorrowerDetailsRequestDTO> coBorrowerList1 = custDAO.getCoBorrowerListWS(request.getClimsPartyId());

			if(coBorrowerList1!=null) {
				
				List<CoBorrowerDetailsForm> coBorrowerDetails = new ArrayList<CoBorrowerDetailsForm>();
				for(CoBorrowerDetailsRequestDTO coBorrower : coBorrowerList1) {
					
					CoBorrowerDetailsForm coBorrowerForm = new CoBorrowerDetailsForm();
					
					if(StringUtils.isNotBlank(coBorrower.getCoBorrowerLiabId()))
						coBorrowerForm.setCoBorrowerLiabId(coBorrower.getCoBorrowerLiabId());
					if(StringUtils.isNotBlank(coBorrower.getCoBorrowerName()))
						coBorrowerForm.setCoBorrowerName(coBorrower.getCoBorrowerName());
					coBorrowerDetails.add(coBorrowerForm);
				}
				form.setCoBorrowerDetails(coBorrowerDetails);

			}	
			
			}else {	
				List<CoBorrowerDetailsRequestDTO> coBorrowerList = coBorrowerRequest.getCoBorrowerDetails();
			

		if(coBorrowerList!=null) {
			List<CoBorrowerDetailsForm> coBorrowerDetails = new ArrayList<CoBorrowerDetailsForm>();
			for(CoBorrowerDetailsRequestDTO coBorrower : coBorrowerList) {
				
				CoBorrowerDetailsForm coBorrowerForm = new CoBorrowerDetailsForm();
				
				if(StringUtils.isNotBlank(coBorrower.getCoBorrowerLiabId()))
					coBorrowerForm.setCoBorrowerLiabId(coBorrower.getCoBorrowerLiabId());
				if(StringUtils.isNotBlank(coBorrower.getCoBorrowerName()))
					coBorrowerForm.setCoBorrowerName(coBorrower.getCoBorrowerName());
			
				coBorrowerForm.setIsInterfaced("Y");

				coBorrowerDetails.add(coBorrowerForm);
			}
			form.setCoBorrowerDetails(coBorrowerDetails);
		}
	}
	}
	
	public PartyDetailsRequestDTO getRequestDTOWithActualValues(PartyDetailsRequestDTO requestDTO,String addOrUpdate) {
		
		SimpleDateFormat relationshipDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		relationshipDateFormat.setLenient(false);
		SimpleDateFormat relationshipDateFormatForLeiDate = new SimpleDateFormat("dd-MM-yyyy");
		relationshipDateFormatForLeiDate.setLenient(false);
		SimpleDateFormat cautionlistDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		cautionlistDateFormat.setLenient(false);
		SimpleDateFormat cautionDateFormat = new SimpleDateFormat("dd/MMM/yyyy");
		cautionDateFormat.setLenient(false);
		
		MasterAccessUtility masterObj = (MasterAccessUtility)BeanHouse.get("masterAccessUtility");
		ActionErrors errors = new ActionErrors();
		
		if(requestDTO.getWsConsumer()!=null && !requestDTO.getWsConsumer().trim().isEmpty()){
			requestDTO.setWsConsumer(requestDTO.getWsConsumer().trim());
		}else{
			errors.add("wsConsumerError",new ActionMessage("error.string.mandatory"));
		}
		
		//CMSCustomer info
		
		if(requestDTO.getBusinessGroup()!=null && !requestDTO.getBusinessGroup().trim().isEmpty()){
			Object obj = masterObj.getObjectByEntityNameAndCPSId("actualOBPartyGroup", requestDTO.getBusinessGroup().trim(),"businessGroup",errors);
			if(!(obj instanceof ActionErrors)){
				requestDTO.setBusinessGroup(Long.toString(((IPartyGroup)obj).getId()));
			}
		}else{
			requestDTO.setBusinessGroup("");
		}
		
		if(requestDTO.getMainBranch()!=null && !requestDTO.getMainBranch().trim().isEmpty()){
			Object obj = masterObj.getObjectByEntityNameAndCPSId("actualSystemBankBranch", requestDTO.getMainBranch().trim(),"mainBranchError",errors);
			if(!(obj instanceof ActionErrors)){
				requestDTO.setMainBranch(((ISystemBankBranch)obj).getSystemBankBranchCode()
						+"-"+((ISystemBankBranch)obj).getSystemBankBranchName());
			}
		}else{
			requestDTO.setMainBranch("");
		}
		
		/*boolean flagRM = true;
		if(requestDTO.getRelationshipManager()!=null && !requestDTO.getRelationshipManager().trim().isEmpty()){
			
			List data = fetchRMData(requestDTO.getRelationshipManager());
			if (data.isEmpty()) {
				errors.add("relationshipMgrError", new ActionMessage("error.string.disable.emp.code"));
				flagRM = false;
			}
			
		boolean isAlphaNumeric = ASSTValidator.isValidAlphaNumStringWithoutSpace(requestDTO.getRelationshipManager());
		
		if (isAlphaNumeric){
			errors.add("relationshipMgrError", new ActionMessage("error.string.specialcharacter"));
			flagRM = false;
		}
		}else{
			errors.add("relationshipMgrError",new ActionMessage("error.string.mandatory"));
			flagRM = false;
		}
		
		if(flagRM == true) {
			requestDTO.setRelationshipManager(requestDTO.getRelationshipManager().trim());
		}*/
		System.out.println("PartyDetailsDTOMapper.java=>Line 2744=>List fetchRMDataRmMgrCode(requestDTO.getRelationshipManager())..");
		if(requestDTO.getRelationshipManager()!=null && !requestDTO.getRelationshipManager().trim().isEmpty()){
			  requestDTO.setRelationshipMgrCode(requestDTO.getRelationshipManager());
		}else{
			requestDTO.setRelationshipMgrCode("");
		}
		System.out.println("PartyDetailsDTOMapper.java=>Line 2751=>After List fetchRMDataRmMgrCode(requestDTO.getRelationshipManager())..requestDTO.getRelationshipMgrCode()=>"+requestDTO.getRelationshipMgrCode()+"***");
		if(requestDTO.getRelationshipManager()!=null && !requestDTO.getRelationshipManager().trim().isEmpty()){
			int counts = fetchRMCodeCountWithActiveStatus(requestDTO.getRelationshipManager());
		     System.out.println("===================================================counts for fetchRMCodeCountWithActiveStatus =>" + counts);
			if(counts == 0) {
				errors.add("relationshipMgrError",new ActionMessage("error.string.disable.relationship.mgr"));
			}
			Object obj = masterObj.getObjectByEntityNameAndRMCode("actualRelationshipMgr", requestDTO.getRelationshipMgrCode().trim(),"relationshipMgrError",errors);
			if(!(obj instanceof ActionErrors)){
				requestDTO.setRelationshipManager(Long.toString(((IRelationshipMgr)obj).getId()));
			}
		}else{
//			requestDTO.setRelationshipManager("");
			errors.add("relationshipMgrError",new ActionMessage("error.string.mandatory"));
		}
		
		/*if(requestDTO.getRMRegion()!=null && !requestDTO.getRMRegion().isEmpty()){
			Object obj = masterObj.getObjectByEntityNameAndCPSId("actualRegion",requestDTO.getRMRegion(),"rmRegionError",errors);
			if(!(obj instanceof ActionErrors)){
				requestDTO.setRMRegion(Long.toString(((IRegion)obj).getIdRegion()));
			}
		}else{
			requestDTO.setRMRegion(requestDTO.getRMRegion());
		}*/
		
		if(requestDTO.getEntity()!=null && !requestDTO.getEntity().trim().isEmpty()){
			//Category Code == Entity
			Object obj = masterObj.getObjectByEntityNameAndCPSId("entryCode", requestDTO.getEntity().trim(),"entityError",errors,"Entity");
			if(!(obj instanceof ActionErrors)){
				requestDTO.setEntity(((ICommonCodeEntry)obj).getEntryCode());
			}
		}else{
			requestDTO.setEntity("");
		}
		
		if(requestDTO.getPAN()!=null && !requestDTO.getPAN().trim().isEmpty()){
			ICustomerDAO custDAO = CustomerDAOFactory.getDAO();
			List<String> partyListWithSamePAN = new ArrayList<String>();
			try {
				if("WS_update_customer".equals(requestDTO.getEvent())){
					partyListWithSamePAN = custDAO.checkIfPANExistsInOtherParty(requestDTO.getClimsPartyId(), requestDTO.getPAN().trim());
				}else{
					partyListWithSamePAN = custDAO.checkIfPANExistsInOtherParty(null, requestDTO.getPAN().trim());
				}
				
				if(partyListWithSamePAN!=null && partyListWithSamePAN.size()>0){
					errors.add("invalidPanError",new ActionMessage("error.string.panAlreadyExists",Arrays.toString(partyListWithSamePAN.toArray())));
				}else{
					requestDTO.setPAN(requestDTO.getPAN().trim());	
				}
			} catch (SearchDAOException e) {
				e.printStackTrace();
			} catch (DBConnectionException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			requestDTO.setPAN("");
		}
		
		if(requestDTO.getRBIIndustryCode()!=null && !requestDTO.getRBIIndustryCode().trim().isEmpty()){
			//Category Code == HDFC_RBI_CODE
			Object obj = masterObj.getObjectByEntityNameAndCPSId("entryCode", requestDTO.getRBIIndustryCode().trim(),"rbiIndustryCodeError",errors,"HDFC_RBI_CODE");
			if(!(obj instanceof ActionErrors)){
				requestDTO.setRBIIndustryCode(((ICommonCodeEntry)obj).getEntryCode());
			}
		}else{
			requestDTO.setRBIIndustryCode("");
		}
		
		if(requestDTO.getIndustryName()!=null && !requestDTO.getIndustryName().trim().isEmpty()){
			//Category Code= HDFC_INDUSTRY
			Object obj = masterObj.getObjectByEntityNameAndCPSId("entryCode", requestDTO.getIndustryName().trim(),"industryNameError",errors,"HDFC_INDUSTRY");
			if(!(obj instanceof ActionErrors)){
				requestDTO.setIndustryName(((ICommonCodeEntry)obj).getEntryCode());
			}
		}else{
			requestDTO.setIndustryName("");
		}
		
		// New CAM interface CR : ON/OFF Feature
		if(PropertyManager.getValue("new.cam.webservice.mandatory.field.flag").equals("OFF")
			|| PropertyManager.getValue("new.cam.webservice.mandatory.field.flag").equals("ON")) {
			List<BankingMethodRequestDTO> bankList1 = new LinkedList<BankingMethodRequestDTO>();		
			BankingMethodRequestDTO partyBankDTO = new BankingMethodRequestDTO();							
			if(requestDTO.getBankingMethod()!=null && !requestDTO.getBankingMethod().trim().isEmpty()){
				partyBankDTO.setBankingMethod(requestDTO.getBankingMethod().trim());
			}else{
				partyBankDTO.setBankingMethod("");
			}
			bankList1.add(partyBankDTO);
			requestDTO.setBankingMethodComboBoxList(bankList1);
		}
		//End
		
		String bankListBox = "";		
		for(BankingMethodRequestDTO bankMtdReqDTO : requestDTO.getBankingMethodComboBoxList()){			
			if(bankMtdReqDTO.getBankingMethod()!=null && !bankMtdReqDTO.getBankingMethod().trim().isEmpty()) {
				Object obj = masterObj.getObjectByEntityNameAndCPSId("entryCode", bankMtdReqDTO.getBankingMethod().trim(),"bankingMethodError",errors,"BANKING_METHOD");
				if(!(obj instanceof ActionErrors)){
					bankListBox += ((ICommonCodeEntry)obj).getEntryCode()+"-"+((ICommonCodeEntry)obj).getEntryName()+",";
					bankMtdReqDTO.setBankingMethod(bankListBox);				
				}else {
					bankMtdReqDTO.setBankingMethod("");
				}				
			}else {
				errors.add("bankingMethod",new ActionMessage("error.string.mandatory"));				
			}
		}
		
		if(requestDTO.getTotalFundedLimit()!=null && !requestDTO.getTotalFundedLimit().trim().isEmpty()){
			requestDTO.setTotalFundedLimit(requestDTO.getTotalFundedLimit().trim());
		}else{
			requestDTO.setTotalFundedLimit("");
		}
		
		if(requestDTO.getTotalNonFundedLimit()!=null && !requestDTO.getTotalNonFundedLimit().trim().isEmpty()){
			requestDTO.setTotalNonFundedLimit(requestDTO.getTotalNonFundedLimit().trim());
		}else{
			requestDTO.setTotalNonFundedLimit("");
		}
		
		if(requestDTO.getFundedSharePercent()!=null && !requestDTO.getFundedSharePercent().trim().isEmpty()){
			requestDTO.setFundedSharePercent(requestDTO.getFundedSharePercent().trim());
		}else{
			requestDTO.setFundedSharePercent("");
		}
		
		if(requestDTO.getMemoExposure()!=null && !requestDTO.getMemoExposure().trim().isEmpty()){
			requestDTO.setMemoExposure(requestDTO.getMemoExposure().trim());
		}else{
			requestDTO.setMemoExposure("");
		}
		
		if(requestDTO.getMpbf()!=null && !requestDTO.getMpbf().trim().isEmpty()){
			//For CR : MPBF Field value should get converted to INR from Millions - Received in WSDL Request
			Double mpbfValueinMillions = Double.parseDouble(requestDTO.getMpbf().trim());
			BigDecimal mpbfValueinMillionsBD = BigDecimal.valueOf(mpbfValueinMillions);
			BigDecimal millionsBD = new BigDecimal(1000000);
			BigDecimal mpbfValueinINR = mpbfValueinMillionsBD.multiply(millionsBD);
			requestDTO.setMpbf(mpbfValueinINR.toBigInteger().toString());
		}else{
			requestDTO.setMpbf("");
		}
		
		if(requestDTO.getPartyName()!=null && !requestDTO.getPartyName().trim().isEmpty()){
			requestDTO.setPartyName(requestDTO.getPartyName().trim());
		}else{
			requestDTO.setPartyName("");
		}
		
		if(requestDTO.getSegment()!=null && !requestDTO.getSegment().trim().isEmpty()){
			//Category Code= HDFC_SEGMENT 
			Object obj = masterObj.getObjectByEntityNameAndCPSId("entryCode", requestDTO.getSegment().trim(),"customerSegmentError",errors,"HDFC_SEGMENT");
			if(!(obj instanceof ActionErrors)){
				requestDTO.setSegment(((ICommonCodeEntry)obj).getEntryCode());
			}
		}else{
			requestDTO.setSegment("");
		}
		
		if(requestDTO.getRelationshipStartDate()!=null && !requestDTO.getRelationshipStartDate().toString().trim().isEmpty()){
			try {
				relationshipDateFormat.parse(requestDTO.getRelationshipStartDate().toString().trim());
				requestDTO.setRelationshipStartDate(requestDTO.getRelationshipStartDate().trim());
			} catch (ParseException e) {
				errors.add("relationshipDateError",new ActionMessage("error.wsdl.relationshipDate.invalid.format"));
			}
		}else{
			requestDTO.setRelationshipStartDate("");
		}
		//Santosh LEI CR
		if(requestDTO.getLeiExpDate()!=null && !requestDTO.getLeiExpDate().toString().trim().isEmpty()){
			try {
				relationshipDateFormat.parse(requestDTO.getLeiExpDate().toString().trim());
				requestDTO.setLeiExpDate(requestDTO.getLeiExpDate().trim());
			} catch (ParseException e) {
				errors.add("leiExpDateError",new ActionMessage("error.wsdl.relationshipDate.invalid.format"));
			}
			if(requestDTO.getLeiCode()==null || requestDTO.getLeiCode().toString().trim().isEmpty()) {
				errors.add("leiCodeRequiredError",new ActionMessage("error.wsdl.leiCode.mandatory"));
			}
		}else{
			requestDTO.setLeiExpDate("");
		}
		
		if(requestDTO.getLeiCode()!=null && !requestDTO.getLeiCode().toString().trim().isEmpty()){
			boolean flag = ASSTValidator.isValidAlphaNumStringWithoutSpace(requestDTO.getLeiCode());
			if (flag == true)
				errors.add("invalidLeiCodeError",new ActionMessage("error.wsdl.leiCode.string.invalidCharacter"));
			else if(requestDTO.getLeiCode().trim().length()!=20)
				errors.add("lengthExceededLeiCodeError",new ActionMessage("error.wsdl.leiCode.length.exceeded"));
			else {
				requestDTO.setLeiCode(requestDTO.getLeiCode().trim());
			}
			if(requestDTO.getLeiExpDate()==null || requestDTO.getLeiExpDate().toString().trim().isEmpty()) {
				errors.add("leiExpDateRequiredError",new ActionMessage("error.wsdl.leiExpDate.mandatory"));
			}
		}else{
			requestDTO.setLeiCode("");
		}
		//End LEI CR
		//*********************CRI Info (One-to-one)****************************
		
		if(requestDTO.getCustomerRAMId()!=null && !requestDTO.getCustomerRAMId().trim().isEmpty()){
			requestDTO.setCustomerRAMId(requestDTO.getCustomerRAMId().trim());
		}else{
			requestDTO.setCustomerRAMId("");
		}
		
		if(requestDTO.getCustomerAPRCode()!=null && !requestDTO.getCustomerAPRCode().trim().isEmpty()){
			if(requestDTO.getCustomerAPRCode().length() > 10) {
				errors.add("customerAPRCode",new ActionMessage("length is exceeded"));
			}else {
				requestDTO.setCustomerAPRCode(requestDTO.getCustomerAPRCode().trim());
			}
		}else{
			requestDTO.setCustomerAPRCode("");
		}
		
		if(requestDTO.getCustomerExtRating()!=null && !requestDTO.getCustomerExtRating().trim().isEmpty()){
			requestDTO.setCustomerExtRating(requestDTO.getCustomerExtRating().trim());
		}else{
			requestDTO.setCustomerExtRating("");
		}
		
		if(requestDTO.getNbfcFlag()!=null && !requestDTO.getNbfcFlag().trim().isEmpty()){
			if(requestDTO.getNbfcFlag().trim().equals("Yes")){
				
				requestDTO.setNbfcFlag("Yes");
				
				if(requestDTO.getNbfcA()!=null && !requestDTO.getNbfcA().trim().isEmpty()){
					try{
						Object obj = masterObj.getMaster("entryCode", Long.parseLong(requestDTO.getNbfcA().trim()));
						if(obj!=null){
							ICommonCodeEntry codeEntry = (ICommonCodeEntry)obj;
							if("NBFC_A".equals(codeEntry.getCategoryCode())){
								requestDTO.setNbfcA((codeEntry).getEntryCode());
							}else{
								errors.add("nbfcAError",new ActionMessage("error.invalid.field.value"));
							}
						}
						else{
							errors.add("nbfcAError",new ActionMessage("error.invalid.field.value"));
						}
					}
					catch(Exception e){
						errors.add("nbfcAError",new ActionMessage("error.invalid.field.value"));
					}
				}else{
					errors.add("nbfcAError",new ActionMessage("error.string.mandatory"));
				}
				
				if(requestDTO.getNbfcB()!=null && !requestDTO.getNbfcB().trim().isEmpty()){
					try{
						Object obj = masterObj.getMaster("entryCode", Long.parseLong(requestDTO.getNbfcB().trim()));
						if(obj!=null){
							ICommonCodeEntry codeEntry = (ICommonCodeEntry)obj;
							if("NBFC_B".equals(codeEntry.getCategoryCode())){
								requestDTO.setNbfcB((codeEntry).getEntryCode());
							}else{
								errors.add("nbfcBError",new ActionMessage("error.invalid.field.value"));
							}
						}
						else{
							errors.add("nbfcBError",new ActionMessage("error.invalid.field.value"));
						}
					}
					catch(Exception e){
						errors.add("nbfcBError",new ActionMessage("error.invalid.field.value"));
					}
				
				}else{
					errors.add("nbfcBError",new ActionMessage("error.string.mandatory"));
				}
				
			}else if(requestDTO.getNbfcFlag().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)){
				requestDTO.setNbfcFlag(DEFAULT_VALUE_FOR_CRI_INFO);
			}else{
				errors.add("isNbfs",new ActionMessage("error.string.cri.default.values"));
			}
		}else{
			requestDTO.setNbfcFlag(DEFAULT_VALUE_FOR_CRI_INFO);
		}
		
		if(requestDTO.getMsmeClassification()!=null && !requestDTO.getMsmeClassification().trim().isEmpty()){
			//Category Code == MSME_CLASSIC
			Object obj = masterObj.getObjectByEntityNameAndCPSId("entryCode", requestDTO.getMsmeClassification().trim(),"msmeClassification",errors,"MSME_CLASSIC");
			if(!(obj instanceof ActionErrors)){
				requestDTO.setMsmeClassification(((ICommonCodeEntry)obj).getEntryCode());
			}
		}else{
			requestDTO.setMsmeClassification("");
		}
		
		
		// Added-----------
		if(requestDTO.getWeakerSection()!=null && !requestDTO.getWeakerSection().trim().isEmpty()){
			if(requestDTO.getWeakerSection().trim().equals("Yes")){
				
				requestDTO.setWeakerSection("Yes");
				
				if(requestDTO.getWeakerSectionType()!=null && !requestDTO.getWeakerSectionType().trim().isEmpty()){
					try{
						Object obj = masterObj.getMaster("entryCode", Long.parseLong(requestDTO.getWeakerSectionType().trim()));
						if(obj!=null){
							ICommonCodeEntry codeEntry = (ICommonCodeEntry)obj;
							if("WEAKER_SEC".equals(codeEntry.getCategoryCode())){
								requestDTO.setWeakerSectionType((codeEntry).getEntryCode());
							}else{
								errors.add("weakerSectionTypeError",new ActionMessage("error.invalid.field.value"));
							}
						}
						else{
							errors.add("weakerSectionTypeError",new ActionMessage("error.invalid.field.value"));
						}
					}
					catch(Exception e){
						errors.add("weakerSectionTypeError",new ActionMessage("error.invalid.field.value"));
					}
				}else{
					errors.add("weakerSectionTypeError",new ActionMessage("error.string.mandatory"));
				}
				
				/*if(requestDTO.getWeakerSectionValue()!= null && !requestDTO.getWeakerSectionValue().trim().isEmpty()){
					requestDTO.setWeakerSectionValue(requestDTO.getWeakerSectionValue().trim());
				}else{
					errors.add("weakerSectionTypeValueError",new ActionMessage("error.string.mandatory"));
				}*/
				
			}else if(requestDTO.getWeakerSection().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)){
				requestDTO.setWeakerSection(DEFAULT_VALUE_FOR_CRI_INFO);
			}else{
				errors.add("isWeakerSection",new ActionMessage("error.string.cri.default.values"));
			}
		}else{
			requestDTO.setWeakerSection(DEFAULT_VALUE_FOR_CRI_INFO);
		}
		
		if(requestDTO.getMinorityCommunity()!=null && !requestDTO.getMinorityCommunity().trim().isEmpty()){
			if(requestDTO.getMinorityCommunity().trim().equals("Yes")){ 
				requestDTO.setMinorityCommunity("Yes");
				if(requestDTO.getMinorityCommunityType()!=null && !requestDTO.getMinorityCommunityType().trim().isEmpty()){
					try{
						Object obj = masterObj.getMaster("entryCode", Long.parseLong(requestDTO.getMinorityCommunityType().trim()));
						if(obj!=null){
							ICommonCodeEntry codeEntry = (ICommonCodeEntry)obj;
							if("MINORITY_COMMU".equals(codeEntry.getCategoryCode())){
								requestDTO.setMinorityCommunityType((codeEntry).getEntryCode());
							}else{
								errors.add("minorityCommunityTypeError",new ActionMessage("error.invalid.field.value"));
							}
						}
						else{
							errors.add("minorityCommunityTypeError",new ActionMessage("error.invalid.field.value"));
						}
					}
					catch(Exception e){
						errors.add("minorityCommunityTypeError",new ActionMessage("error.invalid.field.value"));
					}
				}else{
					errors.add("minorityCommunityTypeError",new ActionMessage("error.string.mandatory"));
				}
			}else if(requestDTO.getMinorityCommunity().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)){
				requestDTO.setMinorityCommunity(DEFAULT_VALUE_FOR_CRI_INFO);
			}else{
				errors.add("isMinorityCommunity",new ActionMessage("error.string.cri.default.values"));
			}
		}else{
			requestDTO.setMinorityCommunity(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if(requestDTO.getCommodityExposure()!=null && !requestDTO.getCommodityExposure().trim().isEmpty()){
			if(requestDTO.getCommodityExposure().trim().equals("Yes") || requestDTO.getCommodityExposure().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)){
				requestDTO.setCommodityExposure(requestDTO.getCommodityExposure().trim());
			}else{
				errors.add("commodityExposure",new ActionMessage("error.string.cri.default.values"));
			}
		}else{
			requestDTO.setCommodityExposure(DEFAULT_VALUE_FOR_CRI_INFO);
		}
		
		
		if(requestDTO.getSensitive()!=null && !requestDTO.getSensitive().trim().isEmpty()){
			if(requestDTO.getSensitive().trim().equals("Yes") || requestDTO.getSensitive().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)){
				requestDTO.setSensitive(requestDTO.getSensitive().trim());
			}else{
				errors.add("isSensitive",new ActionMessage("error.string.cri.default.values"));
			}
		}else{
			if(requestDTO.getCommodityExposure()!=null && !requestDTO.getCommodityExposure().trim().isEmpty()
					&& requestDTO.getCommodityExposure().trim().equals("Yes")){
				errors.add("isSensitive",new ActionMessage("error.string.cri.default.values"));
			}else{
				requestDTO.setSensitive("");
			}
		}

		if(requestDTO.getCommodityName()!=null && !requestDTO.getCommodityName().trim().isEmpty()){
			//Category Code == COMMODITY_NAMES
			Object obj = masterObj.getObjectByEntityNameAndCPSId("entryCode", requestDTO.getCommodityName().trim(),"commoditiesName",errors,"COMMODITY_NAMES");
			if(!(obj instanceof ActionErrors)){
				requestDTO.setCommodityName(((ICommonCodeEntry)obj).getEntryCode());
			}
		}else{
			if(requestDTO.getSensitive()!=null && !requestDTO.getSensitive().trim().isEmpty()
					&& requestDTO.getSensitive().trim().equals("Yes")){
				errors.add("commoditiesName",new ActionMessage("error.string.mandatory"));
			}else{
				requestDTO.setCommodityName("");
			}
		}
		
		if(requestDTO.getPsu()!=null && !requestDTO.getPsu().trim().isEmpty()){
			if(requestDTO.getPsu().trim().equals("State") 
					|| requestDTO.getPsu().trim().equals("Central")
					|| requestDTO.getPsu().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)){
				requestDTO.setPsu(requestDTO.getPsu().trim());
			}else{
				errors.add("psu",new ActionMessage("error.string.cri.psu.default.values"));
			}
		}else{
			requestDTO.setPsu(DEFAULT_VALUE_FOR_CRI_INFO);
		}
		
		if(requestDTO.getPercentageOfShareholding()!=null && !requestDTO.getPercentageOfShareholding().trim().isEmpty()){
			requestDTO.setPercentageOfShareholding(requestDTO.getPercentageOfShareholding().trim());
		}else{
			if(requestDTO.getPsu()!=null && !requestDTO.getPsu().trim().isEmpty() 
					&& !requestDTO.getPsu().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)){
				errors.add("psuPercentage",new ActionMessage("error.string.cri.psuPercentage.default.values"));
			}else{
				requestDTO.setPercentageOfShareholding("");
			}
		}

		if(requestDTO.getGovtUndertaking()!=null && !requestDTO.getGovtUndertaking().trim().isEmpty()){
			if(requestDTO.getGovtUndertaking().trim().equals("State") 
					|| requestDTO.getGovtUndertaking().trim().equals("Central")
					|| requestDTO.getGovtUndertaking().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)){
				requestDTO.setGovtUndertaking(requestDTO.getGovtUndertaking().trim());
			}else{
				errors.add("govtUnderTaking",new ActionMessage("error.string.cri.govUndertaking.default.values"));
			}
		}else{
			requestDTO.setGovtUndertaking(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if(requestDTO.getSEMSGuideApplicable()!=null && !requestDTO.getSEMSGuideApplicable().trim().isEmpty()){
			if(requestDTO.getSEMSGuideApplicable().trim().equals("Yes") || requestDTO.getSEMSGuideApplicable().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)){
				requestDTO.setSEMSGuideApplicable(requestDTO.getSEMSGuideApplicable().trim());
			}else{
				errors.add("isSemsGuideApplicable",new ActionMessage("error.string.cri.default.values"));
			}
		}else{
			requestDTO.setSEMSGuideApplicable(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if(requestDTO.getFailsUnderIFCExclusion()!=null && !requestDTO.getFailsUnderIFCExclusion().trim().isEmpty()){
			if(requestDTO.getFailsUnderIFCExclusion().trim().equals("Yes") || requestDTO.getFailsUnderIFCExclusion().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)){
				requestDTO.setFailsUnderIFCExclusion(requestDTO.getFailsUnderIFCExclusion().trim());
			}else{
				errors.add("isFailIfcExcluList",new ActionMessage("error.string.cri.default.values"));
			}
		}else{
			requestDTO.setFailsUnderIFCExclusion(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if(requestDTO.getTufs()!=null && !requestDTO.getTufs().trim().isEmpty()){
			if(requestDTO.getTufs().trim().equals("Yes") || requestDTO.getTufs().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)){
				requestDTO.setTufs(requestDTO.getTufs().trim());
			}else{
				errors.add("isTufs",new ActionMessage("error.string.cri.default.values"));
			}
		}else{
			requestDTO.setTufs(DEFAULT_VALUE_FOR_CRI_INFO);
		}
		
		if(requestDTO.getRBIDefaulterList()!=null && !requestDTO.getRBIDefaulterList().trim().isEmpty()){
			if(requestDTO.getRBIDefaulterList().trim().equals("Yes")){
				requestDTO.setRBIDefaulterList("Yes");
				/*	if(requestDTO.getRbiDefaulterListType()!=null && !requestDTO.getRbiDefaulterListType().trim().isEmpty()){
					if("Company".equals(requestDTO.getRbiDefaulterListType().trim())
							|| "Directors".equals(requestDTO.getRbiDefaulterListType().trim())
							|| "Group Companies".equals(requestDTO.getRbiDefaulterListType().trim())){
						requestDTO.setRbiDefaulterListType(requestDTO.getRbiDefaulterListType().trim());
					}else{
						errors.add("rbiDefaulterListTypeError",new ActionMessage("error.wsdl.rbiDefaulterListType.valid.values"));
					}
				}else{
					errors.add("rbiDefaulterListTypeError",new ActionMessage("error.string.mandatory"));
				} */
				
				//Change for RBI defaulter Type i.e Company ,Director and Group Companies
				if(null!=requestDTO.getRbiDefaulterListTypeCompany() && !requestDTO.getRbiDefaulterListTypeCompany().trim().isEmpty()){
						requestDTO.setRbiDefaulterListTypeCompany("Company");
					}
				else if(null!=requestDTO.getRbiDefaulterListTypeDirectors() && !requestDTO.getRbiDefaulterListTypeDirectors().trim().isEmpty()){
						requestDTO.setRbiDefaulterListTypeDirectors("Directors");
					}
				else if(null!=requestDTO.getRbiDefaulterListTypeGroupCompanies() && !requestDTO.getRbiDefaulterListTypeGroupCompanies().trim().isEmpty()){
					requestDTO.setRbiDefaulterListTypeGroupCompanies("Group Companies");
				}
				else{
						errors.add("rbiDefaulterListTypeCompanyError",new ActionMessage("error.wsdl.rbiDefaulterListTypeCDG.valid.values"));
						errors.add("rbiDefaulterListTypeDirectorsError",new ActionMessage("error.wsdl.rbiDefaulterListTypeCDG.valid.values"));
						errors.add("rbiDefaulterListTypeGroupCompaniesError",new ActionMessage("error.wsdl.rbiDefaulterListTypeCDG.valid.values"));
					}
			}else if(requestDTO.getRBIDefaulterList().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)){
				requestDTO.setRBIDefaulterList(DEFAULT_VALUE_FOR_CRI_INFO);
			}else{
				errors.add("isRbiDefaulter",new ActionMessage("error.string.cri.default.values"));
			}
		}else{
			requestDTO.setRBIDefaulterList(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if(requestDTO.getLitigationPending()!=null && !requestDTO.getLitigationPending().trim().isEmpty()){
			if(requestDTO.getLitigationPending().trim().equals("Yes")){
				requestDTO.setLitigationPending("Yes");
				if(requestDTO.getLitigationPendingBy()!=null && !requestDTO.getLitigationPendingBy().trim().isEmpty()){
					if(requestDTO.getLitigationPendingBy().length() > 20) {
						errors.add("litigationPendingByError",new ActionMessage("length is exceeded"));
					} else {
						requestDTO.setLitigationPendingBy(requestDTO.getLitigationPendingBy().trim());	
					}
				}else{
					errors.add("litigationPendingByError",new ActionMessage("error.string.mandatory"));
				}					
			}else if(requestDTO.getLitigationPending().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)){
				requestDTO.setLitigationPending(DEFAULT_VALUE_FOR_CRI_INFO);
			}else{
				errors.add("isLitigation",new ActionMessage("error.string.cri.default.values"));
			}
		}else{
			requestDTO.setLitigationPending(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if(requestDTO.getInterestOfDirectors()!=null && !requestDTO.getInterestOfDirectors().trim().isEmpty()){
			if(requestDTO.getInterestOfDirectors().trim().equals("Yes")){
				requestDTO.setInterestOfDirectors("Yes");
				if(requestDTO.getInterestOfDirectorsType()!=null && !requestDTO.getInterestOfDirectorsType().trim().isEmpty()){
					requestDTO.setInterestOfDirectorsType(requestDTO.getInterestOfDirectorsType().trim());
				}else{
					errors.add("interestOfDirectorsTypeError",new ActionMessage("error.string.mandatory"));
				}	
			}else if(requestDTO.getInterestOfDirectors().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)){
				requestDTO.setInterestOfDirectors(DEFAULT_VALUE_FOR_CRI_INFO);
			}else{
				errors.add("isInterestOfBank",new ActionMessage("error.string.cri.default.values"));
			}
		}else{
			requestDTO.setInterestOfDirectors(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if(requestDTO.getAdverseRemark()!=null && !requestDTO.getAdverseRemark().trim().isEmpty()){
			if(requestDTO.getAdverseRemark().trim().equals("Yes")){
				requestDTO.setAdverseRemark("Yes");
				if(requestDTO.getAdverseRemarkValue()!=null && !requestDTO.getAdverseRemarkValue().trim().isEmpty()){
					requestDTO.setAdverseRemarkValue(requestDTO.getAdverseRemarkValue().trim());
				}else{
					errors.add("adverseRemarkValueError",new ActionMessage("error.string.mandatory"));
				}
			}else if(requestDTO.getAdverseRemark().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)){
				requestDTO.setAdverseRemark(DEFAULT_VALUE_FOR_CRI_INFO);
			}else{
				errors.add("isAdverseRemark",new ActionMessage("error.string.cri.default.values"));
			}
		}else{
			requestDTO.setAdverseRemark(DEFAULT_VALUE_FOR_CRI_INFO);
		}
		
		if(requestDTO.getAudit()!=null && !requestDTO.getAudit().trim().isEmpty()){
			if(requestDTO.getAudit().trim().equals("RBI Audit") 
					|| requestDTO.getAudit().trim().equals("External Audit")
					|| requestDTO.getAudit().trim().equals("Internal Audit")){
				requestDTO.setAudit(requestDTO.getAudit().trim());
			}else{
				errors.add("auditType",new ActionMessage("error.string.cri.audit.default.values"));
			}
		}else{
			requestDTO.setAudit("");
		}
		
		if(requestDTO.getIsBorrowerDirector()!=null && !requestDTO.getIsBorrowerDirector().trim().isEmpty()){
			if(requestDTO.getIsBorrowerDirector().trim().equals("Yes")){
				requestDTO.setIsBorrowerDirector("Yes");
				if(requestDTO.getBorrowerDirectorValue()!=null && !requestDTO.getBorrowerDirectorValue().trim().isEmpty()){
					requestDTO.setBorrowerDirectorValue(requestDTO.getBorrowerDirectorValue().trim());
				}else{
					errors.add("borrowerDirectorValueError",new ActionMessage("error.string.mandatory"));
				}
			}else if(requestDTO.getIsBorrowerDirector().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)){
				requestDTO.setIsBorrowerDirector(DEFAULT_VALUE_FOR_CRI_INFO);
			}else{
				errors.add("isBorrowerDirector",new ActionMessage("error.string.cri.default.values"));
			}
		}else{
			requestDTO.setIsBorrowerDirector(DEFAULT_VALUE_FOR_CRI_INFO);
		}
		
		if(requestDTO.getIsBorrowerPartner()!=null && !requestDTO.getIsBorrowerPartner().trim().isEmpty()){
			if(requestDTO.getIsBorrowerPartner().trim().equals("Yes")){
				requestDTO.setIsBorrowerPartner("Yes");
				if(requestDTO.getBorrowerPartnerValue()!=null && !requestDTO.getBorrowerPartnerValue().trim().isEmpty()){
					requestDTO.setBorrowerPartnerValue(requestDTO.getBorrowerPartnerValue().trim());
				}else{
					errors.add("borrowerPartnerValueError",new ActionMessage("error.string.mandatory"));
				}
			}else if(requestDTO.getIsBorrowerPartner().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)){
				requestDTO.setIsBorrowerPartner(DEFAULT_VALUE_FOR_CRI_INFO);
			}else{
				errors.add("isBorrowerPartner",new ActionMessage("error.string.cri.default.values"));
			}
		}else{
			requestDTO.setIsBorrowerPartner(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if(requestDTO.getIsDirectorOfOtherBank()!=null && !requestDTO.getIsDirectorOfOtherBank().trim().isEmpty()){
			if(requestDTO.getIsDirectorOfOtherBank().trim().equals("Yes")){
				requestDTO.setIsDirectorOfOtherBank("Yes");
				if(requestDTO.getDirectorOfOtherBankValue()!=null && !requestDTO.getDirectorOfOtherBankValue().trim().isEmpty()){
					requestDTO.setDirectorOfOtherBankValue(requestDTO.getDirectorOfOtherBankValue().trim());
				}else{
					errors.add("directorOfOtherBankValueError",new ActionMessage("error.string.mandatory"));
				}
			}else if(requestDTO.getIsDirectorOfOtherBank().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)){
				requestDTO.setIsDirectorOfOtherBank(DEFAULT_VALUE_FOR_CRI_INFO);
			}else{
				errors.add("isDirecOtherBank",new ActionMessage("error.string.cri.default.values"));
			}
		}else{
			requestDTO.setIsDirectorOfOtherBank(DEFAULT_VALUE_FOR_CRI_INFO);
		}
		
		if(requestDTO.getIsRelativeOfHDFCBank()!=null && !requestDTO.getIsRelativeOfHDFCBank().trim().isEmpty()){
			if(requestDTO.getIsRelativeOfHDFCBank().trim().equals("Yes")){
				requestDTO.setIsRelativeOfHDFCBank("Yes");
				if(requestDTO.getRelativeOfHDFCBankValue()!=null && !requestDTO.getRelativeOfHDFCBankValue().trim().isEmpty()){
					requestDTO.setRelativeOfHDFCBankValue(requestDTO.getRelativeOfHDFCBankValue().trim());
				}else{
					errors.add("relativeOfHDFCBankValueError",new ActionMessage("error.string.mandatory"));
				}
			}else if(requestDTO.getIsRelativeOfHDFCBank().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)){
				requestDTO.setIsRelativeOfHDFCBank(DEFAULT_VALUE_FOR_CRI_INFO);
			}else{
				errors.add("isHdfcDirecRltv",new ActionMessage("error.string.cri.default.values"));
			}
		}else{
			requestDTO.setIsRelativeOfHDFCBank(DEFAULT_VALUE_FOR_CRI_INFO);
		}
		
		if(requestDTO.getIsRelativeOfChairman()!=null && !requestDTO.getIsRelativeOfChairman().trim().isEmpty()){
			if(requestDTO.getIsRelativeOfChairman().trim().equals("Yes")){
				requestDTO.setIsRelativeOfChairman("Yes");
				if(requestDTO.getRelativeOfChairmanValue()!=null && !requestDTO.getRelativeOfChairmanValue().trim().isEmpty()){
					requestDTO.setRelativeOfChairmanValue(requestDTO.getRelativeOfChairmanValue().trim());
				}else{
					errors.add("relativeOfChairmanValueError",new ActionMessage("error.string.mandatory"));
				}
			}else if(requestDTO.getIsRelativeOfChairman().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)){
				requestDTO.setIsRelativeOfChairman(DEFAULT_VALUE_FOR_CRI_INFO);
			}else{
				errors.add("isRelativeOfChairman",new ActionMessage("error.string.cri.default.values"));
			}
		}else{
			requestDTO.setIsRelativeOfChairman(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if(requestDTO.getIsPartnerRelativeOfBanks()!=null && !requestDTO.getIsPartnerRelativeOfBanks().trim().isEmpty()){
			if(requestDTO.getIsPartnerRelativeOfBanks().trim().equals("Yes")){
				requestDTO.setIsPartnerRelativeOfBanks("Yes");
				if(requestDTO.getPartnerRelativeOfBanksValue()!=null && !requestDTO.getPartnerRelativeOfBanksValue().trim().isEmpty()){
					requestDTO.setPartnerRelativeOfBanksValue(requestDTO.getPartnerRelativeOfBanksValue().trim());
				}else{
					errors.add("partnerRelativeOfBanksValueError",new ActionMessage("error.string.mandatory"));
				}
			}else if(requestDTO.getIsPartnerRelativeOfBanks().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)){
				requestDTO.setIsPartnerRelativeOfBanks(DEFAULT_VALUE_FOR_CRI_INFO);
			}else{
				errors.add("isPartnerOtherBank",new ActionMessage("error.string.cri.default.values"));
			}
		}else{
			requestDTO.setIsPartnerRelativeOfBanks(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if(requestDTO.getIsShareholderRelativeOfBank()!=null && !requestDTO.getIsShareholderRelativeOfBank().trim().isEmpty()){
			if(requestDTO.getIsShareholderRelativeOfBank().trim().equals("Yes")){
				requestDTO.setIsShareholderRelativeOfBank("Yes");
				if(requestDTO.getShareholderRelativeOfBankValue()!=null && !requestDTO.getShareholderRelativeOfBankValue().trim().isEmpty()){
					requestDTO.setShareholderRelativeOfBankValue(requestDTO.getShareholderRelativeOfBankValue().trim());
				}else{
					errors.add("shareholderRelativeOfBankValueError",new ActionMessage("error.string.mandatory"));
				}
			}else if(requestDTO.getIsShareholderRelativeOfBank().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)){
				requestDTO.setIsShareholderRelativeOfBank(DEFAULT_VALUE_FOR_CRI_INFO);
			}else{
				errors.add("isSubstantialOtherBank",new ActionMessage("error.string.cri.default.values"));
			}
		}else{
			requestDTO.setIsShareholderRelativeOfBank(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if(requestDTO.getIsShareholderRelativeOfDirector()!=null && !requestDTO.getIsShareholderRelativeOfDirector().trim().isEmpty()){
			if(requestDTO.getIsShareholderRelativeOfDirector().trim().equals("Yes") || requestDTO.getIsShareholderRelativeOfDirector().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)){
				requestDTO.setIsShareholderRelativeOfDirector(DEFAULT_VALUE_FOR_CRI_INFO);
			}else{
				errors.add("isSubstantialRltvHdfcOther",new ActionMessage("error.string.cri.default.values"));
			}
		}else{
			requestDTO.setIsShareholderRelativeOfDirector(DEFAULT_VALUE_FOR_CRI_INFO);
		}
		
		if(requestDTO.getInfraFinance()!=null && !requestDTO.getInfraFinance().trim().isEmpty()){
			if(requestDTO.getInfraFinance().trim().equals("Yes") || requestDTO.getInfraFinance().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)){
				//Changed on 10-MAY-2016 : FOR CRI Fields related CR
				requestDTO.setInfraFinance(requestDTO.getInfraFinance().trim());
			}else{
				errors.add("isInfrastructureFinanace",new ActionMessage("error.string.cri.default.values"));
			}
		}else{
			requestDTO.setInfraFinance(DEFAULT_VALUE_FOR_CRI_INFO);
		}
		
		if(requestDTO.getProjectFinance()!=null && !requestDTO.getProjectFinance().trim().isEmpty()){
			if(requestDTO.getProjectFinance().trim().equals("Yes") || requestDTO.getProjectFinance().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)){
				//Changed on 10-MAY-2016 : FOR CRI Fields related CR
				requestDTO.setProjectFinance(requestDTO.getProjectFinance().trim());
			}else{
				errors.add("isProjectFinance",new ActionMessage("error.string.cri.default.values"));
			}
		}else{
			requestDTO.setProjectFinance(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if(requestDTO.getKisanCreditCard()!=null && !requestDTO.getKisanCreditCard().trim().isEmpty()){
			if(requestDTO.getKisanCreditCard().trim().equals("Yes") || requestDTO.getKisanCreditCard().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)){
				//Changed on 10-MAY-2016 : FOR CRI Fields related CR
				requestDTO.setKisanCreditCard(requestDTO.getKisanCreditCard().trim());
			}else{
				errors.add("isKisanCreditCard",new ActionMessage("error.string.cri.default.values"));
			}
		}else{
			requestDTO.setKisanCreditCard(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if(requestDTO.getPermSSICert()!=null && !requestDTO.getPermSSICert().trim().isEmpty()){
			if(requestDTO.getPermSSICert().trim().equals("Yes") || requestDTO.getPermSSICert().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)){
				//Changed on 10-MAY-2016 : FOR CRI Fields related CR
				requestDTO.setPermSSICert(requestDTO.getPermSSICert().trim());
			}else{
				errors.add("isPermenentSsiCert",new ActionMessage("error.string.cri.default.values"));
			}
		}else{
			requestDTO.setPermSSICert(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if(requestDTO.getIsBackedByGovt()!=null && !requestDTO.getIsBackedByGovt().trim().isEmpty()){
			if(requestDTO.getIsBackedByGovt().trim().equals("State")
					|| requestDTO.getIsBackedByGovt().trim().equals("Central")
					|| requestDTO.getIsBackedByGovt().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)){
				//Changed on 10-MAY-2016 : FOR CRI Fields related CR
				requestDTO.setIsBackedByGovt(requestDTO.getIsBackedByGovt().trim());
			}else{
				errors.add("isBackedByGovt",new ActionMessage("error.string.cri.backedByGov.default.values"));
			}
		}else{
			requestDTO.setIsBackedByGovt(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if(requestDTO.getPrioritySector()!=null && !requestDTO.getPrioritySector().trim().isEmpty()){
			if(requestDTO.getPrioritySector().trim().equals("Yes") || requestDTO.getPrioritySector().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)){
				//Changed on 10-MAY-2016 : FOR CRI Fields related CR
				requestDTO.setPrioritySector(requestDTO.getPrioritySector().trim());
			}else{
				errors.add("isPrioritySector",new ActionMessage("error.string.cri.default.values"));
			}
		}else{
			requestDTO.setPrioritySector(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if(requestDTO.getCapitalMarketExposure()!=null && !requestDTO.getCapitalMarketExposure().trim().isEmpty()){
			if(requestDTO.getCapitalMarketExposure().trim().equals("Yes") || requestDTO.getCapitalMarketExposure().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)){
				//Changed on 10-MAY-2016 : FOR CRI Fields related CR
				requestDTO.setCapitalMarketExposure(requestDTO.getCapitalMarketExposure().trim());
			}else{
				errors.add("isCapitalMarketExpos",new ActionMessage("error.string.cri.default.values"));
			}
		}else{
			requestDTO.setCapitalMarketExposure(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if(requestDTO.getRealEstateExposure()!=null && !requestDTO.getRealEstateExposure().trim().isEmpty()){
			if(requestDTO.getRealEstateExposure().trim().equals("Yes") || requestDTO.getRealEstateExposure().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)){
				//Changed on 10-MAY-2016 : FOR CRI Fields related CR
				requestDTO.setRealEstateExposure(requestDTO.getRealEstateExposure().trim());
			}else{
				errors.add("isRealEstateExpos",new ActionMessage("error.string.cri.default.values"));
			}
		}else{
			requestDTO.setRealEstateExposure(DEFAULT_VALUE_FOR_CRI_INFO);
		}
		
		//-------- END ------------

		
		if(requestDTO.getIsSPVFunding()!=null && !requestDTO.getIsSPVFunding().trim().isEmpty()) {
			if(requestDTO.getIsSPVFunding().trim().equals("Yes") || requestDTO.getIsSPVFunding().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)) {
				requestDTO.setIsSPVFunding(requestDTO.getIsSPVFunding().trim());
			}else {
				errors.add("isSPVFunding",new ActionMessage("error.string.cri.default.values"));
			}
		}else {
			requestDTO.setIsSPVFunding(DEFAULT_VALUE_FOR_CRI_INFO);
		}
		if(requestDTO.getIndirectCountryRiskExposure()!=null && !requestDTO.getIndirectCountryRiskExposure().trim().isEmpty()) {
			if(requestDTO.getIndirectCountryRiskExposure().trim().equals("Yes") || requestDTO.getIndirectCountryRiskExposure().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)) {
				requestDTO.setIndirectCountryRiskExposure(requestDTO.getIndirectCountryRiskExposure().trim());
			}else {
				errors.add("indirectCountryRiskExposure",new ActionMessage("error.string.cri.default.values"));
			}			
			if("Yes".equals(requestDTO.getIndirectCountryRiskExposure())) {
				requestDTO.setIndirectCountryRiskExposure("Yes");
				if(requestDTO.getCriCountryName()!=null && !requestDTO.getCriCountryName().trim().isEmpty()) {	
					Object obj = masterObj.getObjectByEntityNameAndCPSId("actualCountry", requestDTO.getCriCountryName().trim(),"criCountryName",errors);
					if(!(obj instanceof ActionErrors)){
						requestDTO.setCriCountryName(Long.toString(((ICountry)obj).getIdCountry()));
					}
				}else {
					errors.add("criCountryName", new ActionMessage("error.string.mandatory"));
				}
				if(requestDTO.getSalesPercentage()!=null && !requestDTO.getSalesPercentage().trim().isEmpty()) {
					if (ASSTValidator.isNumeric(requestDTO.getSalesPercentage().trim()))
						requestDTO.setSalesPercentage(requestDTO.getSalesPercentage().trim());			
					else {
						errors.add("salesPercentage", new ActionMessage("only numeric value allowed"));
					}
				}else {
					errors.add("salesPercentage", new ActionMessage("error.string.mandatory"));
				}
			}
		}else {
			requestDTO.setIndirectCountryRiskExposure(DEFAULT_VALUE_FOR_CRI_INFO);
		}
		if(requestDTO.getIsIPRE()!=null && !requestDTO.getIsIPRE().trim().isEmpty()) {
			if(requestDTO.getIsIPRE().trim().equals("Yes") || requestDTO.getIsIPRE().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)) {
				requestDTO.setIsIPRE(requestDTO.getIsIPRE().trim());
			}else {
				errors.add("isIPRE",new ActionMessage("error.string.cri.default.values"));
			}
		}else {
			requestDTO.setIsIPRE(DEFAULT_VALUE_FOR_CRI_INFO);
		}		

		if(requestDTO.getFinanceForAccquisition()!=null && !requestDTO.getFinanceForAccquisition().trim().isEmpty()) {
			if(requestDTO.getFinanceForAccquisition().trim().equals("Yes") || requestDTO.getFinanceForAccquisition().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)) {
				requestDTO.setFinanceForAccquisition(requestDTO.getFinanceForAccquisition());
			}else {
				errors.add("financeForAccquisition",new ActionMessage("error.string.cri.default.values"));
			}
			if("Yes".equals(requestDTO.getFinanceForAccquisition())) {
				requestDTO.setFinanceForAccquisition("Yes");
				if(requestDTO.getFacilityApproved()!=null && !requestDTO.getFacilityApproved().trim().isEmpty()) {					
					Object obj=masterObj.getObjectByEntityNameAndCPSId("actualFacilityNewMaster", requestDTO.getFacilityApproved().trim(),"facilityApproved",errors);
					if(!(obj instanceof ActionErrors)){
						requestDTO.setFacilityApproved(((IFacilityNewMaster)obj).getNewFacilityCode());
					}			
				}else {
					requestDTO.setFacilityApproved("");
				}
				if(requestDTO.getFacilityAmount()!=null && !requestDTO.getFacilityAmount().trim().isEmpty()) {
					if (ASSTValidator.isNumeric(requestDTO.getFacilityAmount().trim())){
						requestDTO.setFacilityAmount(requestDTO.getFacilityAmount().trim());
					}else {
						errors.add("facilityAmount",new ActionMessage("Only numeric is allowed"));
					}
				}else {
					requestDTO.setFacilityAmount("");
				}
				
				if(requestDTO.getSecurityName()!=null && !requestDTO.getSecurityName().trim().isEmpty()) {
					Object obj = masterObj.getObjectByEntityNameAndCPSId("actualCollateralNewMaster", requestDTO.getSecurityName().trim(),"securityName",errors);
					if(!(obj instanceof ActionErrors)){
						requestDTO.setSecurityName(((ICollateralNewMaster)obj).getNewCollateralCode());
					}					
				}else {
					requestDTO.setSecurityName("");
				}
				
							
				if(requestDTO.getSecurityType()!=null && !requestDTO.getSecurityType().trim().isEmpty()) {
					try{
						Object obj = masterObj.getMaster("entryCode", Long.parseLong(requestDTO.getSecurityType().trim()));
						if(obj!=null){
							ICommonCodeEntry codeEntry = (ICommonCodeEntry)obj;
							if("31".equals(codeEntry.getCategoryCode())){
								requestDTO.setSecurityType((codeEntry).getEntryCode());
							}else{
								errors.add("securityType",new ActionMessage("error.invalid.field.value"));
							}
						}
						else{
							errors.add("securityType",new ActionMessage("error.invalid.field.value"));
						}
					}
					catch(Exception e){
						DefaultLogger.error(this, e.getMessage());
						errors.add("securityType",new ActionMessage("error.invalid.field.value"));
					}
				}else {
					requestDTO.setSecurityType("");
				}

				if(requestDTO.getSecurityValue() != null && !requestDTO.getSecurityValue().trim().isEmpty()) {
					if (ASSTValidator.isNumeric(requestDTO.getSecurityValue().trim())){
						requestDTO.setSecurityValue(requestDTO.getSecurityValue().trim());
					}else {
						errors.add("securityValue",new ActionMessage("Only numeric is allowed"));
					}					
				}else {
					requestDTO.setSecurityValue("");
				}
				
			}
		}else {
			requestDTO.setFinanceForAccquisition(DEFAULT_VALUE_FOR_CRI_INFO);
		}
		if(requestDTO.getCompanyType()!=null && !requestDTO.getCompanyType().trim().isEmpty()) {
			if(requestDTO.getCompanyType().trim().equals("Yes") || requestDTO.getCompanyType().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)) {
				requestDTO.setCompanyType(requestDTO.getCompanyType());
			}else {
				errors.add("companyType",new ActionMessage("error.string.cri.default.values"));
			}
			if("Yes".equals(requestDTO.getCompanyType())) {
				if(requestDTO.getNameOfHoldingCompany() != null && !requestDTO.getNameOfHoldingCompany().isEmpty()) {
					if(requestDTO.getNameOfHoldingCompany().length() > 255) {
						errors.add("nameOfHoldingCompany",new ActionMessage("length is exceeded"));
					}else {
						requestDTO.setNameOfHoldingCompany(requestDTO.getNameOfHoldingCompany().trim());						
					}
				}else {
					requestDTO.setNameOfHoldingCompany("");
				}
				if(requestDTO.getType()!=null && !requestDTO.getType().trim().isEmpty()) {	
					try{
						Object obj = masterObj.getMaster("entryCode", Long.parseLong(requestDTO.getType().trim()));
						if(obj!=null){
							ICommonCodeEntry codeEntry = (ICommonCodeEntry)obj;
							if("TYPE_OF_COMPANY".equals(codeEntry.getCategoryCode())){
								requestDTO.setType((codeEntry).getEntryCode());
							}else{
								errors.add("type",new ActionMessage("error.party.type"));
							}
						}
						else{
							errors.add("type",new ActionMessage("error.party.type"));
						}
					}
					catch(Exception e){
						DefaultLogger.error(this, e.getMessage());
						errors.add("type",new ActionMessage("error.party.type"));
					}
				}else {
					requestDTO.setType("");
				}	
			}else {
				requestDTO.setNameOfHoldingCompany("");
				requestDTO.setType("");
			}
		}else {
			requestDTO.setCompanyType(DEFAULT_VALUE_FOR_CRI_INFO);
		}
		if(requestDTO.getCategoryOfFarmer()!=null && !requestDTO.getCategoryOfFarmer().trim().isEmpty()) {	
			try{
				Object obj = masterObj.getMaster("entryCode", Long.parseLong(requestDTO.getCategoryOfFarmer().trim()));
				if(obj!=null){
					ICommonCodeEntry codeEntry = (ICommonCodeEntry)obj;
					if("CATEGORY_OF_FARMER".equals(codeEntry.getCategoryCode())){
						requestDTO.setCategoryOfFarmer((codeEntry).getEntryCode());
					}else{
						errors.add("categoryOfFarmer",new ActionMessage("error.party.categoryOfFarmer"));
					}
				}
				else{
					errors.add("categoryOfFarmer",new ActionMessage("error.party.categoryOfFarmer"));
				}
			}
			catch(Exception e){
				DefaultLogger.error(this, e.getMessage());
				errors.add("categoryOfFarmer",new ActionMessage("error.party.categoryOfFarmer"));
			}
			if(requestDTO.getLandHolding()!=null && !requestDTO.getLandHolding().trim().isEmpty()) {
				if(requestDTO.getLandHolding().length() > 255) {
					errors.add("landHolding",new ActionMessage("length is exceeded"));
				}else {
					requestDTO.setLandHolding(requestDTO.getLandHolding().trim());
				}
			}
		}
		if(requestDTO.getCountryOfGuarantor()!=null && !requestDTO.getCountryOfGuarantor().trim().isEmpty()) {						
			Object obj = masterObj.getObjectByEntityNameAndCPSId("actualCountry", requestDTO.getCountryOfGuarantor().trim(),"countryOfGuarantor",errors);
			if(!(obj instanceof ActionErrors)){
				requestDTO.setCountryOfGuarantor(Long.toString(((ICountry)obj).getIdCountry()));
			}
		}	
		if(requestDTO.getIsAffordableHousingFinance()!=null && !requestDTO.getIsAffordableHousingFinance().trim().isEmpty()) {
		
			if(requestDTO.getIsAffordableHousingFinance().trim().equals("Yes") || requestDTO.getIsAffordableHousingFinance().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)) {
					requestDTO.setIsAffordableHousingFinance(requestDTO.getIsAffordableHousingFinance().trim());
				}else {
					errors.add("isAffordableHousingFinance",new ActionMessage("error.string.cri.default.values"));
				}
			}else {
				requestDTO.setIsAffordableHousingFinance(DEFAULT_VALUE_FOR_CRI_INFO);
			}

		if(requestDTO.getIsInRestrictedList()!=null && !requestDTO.getIsInRestrictedList().trim().isEmpty()) {
			
			if(requestDTO.getIsInRestrictedList().trim().equals("Yes") || requestDTO.getIsInRestrictedList().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)) {
				requestDTO.setIsInRestrictedList(requestDTO.getIsInRestrictedList());
			}else {
				errors.add("isInRestrictedList",new ActionMessage("error.string.cri.default.values"));
			}
			
			if("Yes".equals(requestDTO.getIsInRestrictedList())) {
				
				if(requestDTO.getRestrictedFinancing()!=null && !requestDTO.getRestrictedFinancing().trim().isEmpty()) {	
					try{
							Object obj = masterObj.getMaster("entryCode", Long.parseLong(requestDTO.getRestrictedFinancing().trim()));
							if(obj!=null){
								ICommonCodeEntry codeEntry = (ICommonCodeEntry)obj;
								if("RESTRICTED_FINANCING".equals(codeEntry.getCategoryCode())){
									requestDTO.setRestrictedFinancing((codeEntry).getEntryCode());
								}else{
									errors.add("restrictedFinancing",new ActionMessage("error.party.restrictedFinancing"));
								}
							}
							else{
								errors.add("restrictedFinancing",new ActionMessage("error.party.restrictedFinancing"));
							}
						}
						catch(Exception e){
							DefaultLogger.error(this, e.getMessage());
							errors.add("restrictedFinancing",new ActionMessage("error.party.restrictedFinancing"));
						}
				}else {
					errors.add("restrictedFinancing",new ActionMessage("error.string.mandatory"));
				}
			}
		}else {
			requestDTO.setIsInRestrictedList(DEFAULT_VALUE_FOR_CRI_INFO);
		}
		
		
		
		if(requestDTO.getRestrictedIndustries()!=null && !requestDTO.getRestrictedIndustries().trim().isEmpty()) {
			
			if(requestDTO.getRestrictedIndustries().trim().equals("Yes") || requestDTO.getRestrictedIndustries().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)) {
				requestDTO.setRestrictedIndustries(requestDTO.getRestrictedIndustries());
			}else {
				errors.add("restrictedIndustries",new ActionMessage("error.string.cri.default.values"));
			}
			
			if("Yes".equals(requestDTO.getRestrictedIndustries())) {			
				if(requestDTO.getRestrictedListIndustries()!=null && !requestDTO.getRestrictedListIndustries().trim().isEmpty()) {	
					try{
							Object obj = masterObj.getMaster("entryCode", Long.parseLong(requestDTO.getRestrictedListIndustries().trim()));
							if(obj!=null){
								ICommonCodeEntry codeEntry = (ICommonCodeEntry)obj;
								if("CREDIT_LIST_RESTRICTED_INDUSTRIES".equals(codeEntry.getCategoryCode())){
									requestDTO.setRestrictedListIndustries((codeEntry).getEntryCode());
								}else{
									errors.add("restrictedListIndustries",new ActionMessage("error.party.restrictedListIndustries"));
								}
							}
							else{
								errors.add("restrictedListIndustries",new ActionMessage("error.party.restrictedListIndustries"));
							}
						}
						catch(Exception e){
							DefaultLogger.error(this, e.getMessage());
							errors.add("restrictedListIndustries",new ActionMessage("error.party.restrictedListIndustries"));
						}
				}else {
					errors.add("restrictedListIndustries",new ActionMessage("error.string.mandatory"));
				}
			}
		}else {
			requestDTO.setRestrictedIndustries(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if(requestDTO.getIsBorrowerInRejectDatabase()!=null && !requestDTO.getIsBorrowerInRejectDatabase().trim().isEmpty()) {
			if(requestDTO.getIsBorrowerInRejectDatabase().trim().equals("Yes") || requestDTO.getIsBorrowerInRejectDatabase().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)) {
				requestDTO.setIsBorrowerInRejectDatabase(requestDTO.getIsBorrowerInRejectDatabase().trim());
				if(requestDTO.getRejectHistoryReason()!=null && !requestDTO.getRejectHistoryReason().trim().isEmpty()) {
					if(requestDTO.getRejectHistoryReason().length() > 4000 ) {
						errors.add("rejectHistoryReason",new ActionMessage("length is exceeded"));
					}else {
						requestDTO.setRejectHistoryReason(requestDTO.getRejectHistoryReason().trim());
					}
				}else {
					requestDTO.setRejectHistoryReason("");
				}
			}else {
				errors.add("isBorrowerInRejectDatabase",new ActionMessage("error.string.cri.default.values"));
			}
		}else {
			requestDTO.setIsBorrowerInRejectDatabase(DEFAULT_VALUE_FOR_CRI_INFO);
		}
		
		if (null != requestDTO.getCapitalForCommodityAndExchange()
				&& !requestDTO.getCapitalForCommodityAndExchange().trim().isEmpty()) {
			if (!("Broker"
					.equals(requestDTO.getCapitalForCommodityAndExchange().trim())
					|| "Non Broker".equals(
							requestDTO.getCapitalForCommodityAndExchange().trim()))) {
				errors.add("capitalForCommodityAndExchange", new ActionMessage("invalid field value"));
			} else {
				if ("Broker".equals(
						requestDTO.getCapitalForCommodityAndExchange().trim())) {
					requestDTO.setCapitalForCommodityAndExchange("Broker");
				} else {
					requestDTO.setCapitalForCommodityAndExchange("Non Broker");
				}
			}
		} else {
			requestDTO.setCapitalForCommodityAndExchange("Non Broker");
		}

		if (null != requestDTO.getCapitalForCommodityAndExchange()
				&& !requestDTO.getCapitalForCommodityAndExchange().trim().isEmpty()) {
			if ("Broker"
					.equals(requestDTO.getCapitalForCommodityAndExchange().trim())) {
				if (null != requestDTO.getIsBrokerTypeShare()
						&& !requestDTO.getIsBrokerTypeShare().trim().isEmpty()) {
					if (requestDTO.getIsBrokerTypeShare().trim().equals("Y")
							|| requestDTO.getIsBrokerTypeShare().trim().equals("N")) {
						requestDTO.setIsBrokerTypeShare(requestDTO.getIsBrokerTypeShare().trim());
					} else {
						errors.add("isBrokerTypeShare", new ActionMessage("error.invalid.field.checkbox.value"));
					}
				} else {
					requestDTO.setIsBrokerTypeShare("");
				}

				if (null != requestDTO.getIsBrokerTypeCommodity()
						&& !requestDTO.getIsBrokerTypeCommodity().trim().isEmpty()) {
					if (requestDTO.getIsBrokerTypeCommodity().trim().equals("Y")
							|| requestDTO.getIsBrokerTypeCommodity().trim().equals("N")) {
						requestDTO.setIsBrokerTypeCommodity(
								requestDTO.getIsBrokerTypeCommodity().trim());
					} else {
						errors.add("isBrokerTypeCommodity", new ActionMessage("error.invalid.field.checkbox.value"));
					}
				} else {
					requestDTO.setIsBrokerTypeCommodity("");
				}
			}
		}
	
		if(requestDTO.getObjectFinance()!=null && !requestDTO.getObjectFinance().trim().isEmpty()) {
			if(requestDTO.getObjectFinance().trim().equals("Yes") || requestDTO.getObjectFinance().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)) {
				requestDTO.setObjectFinance(requestDTO.getObjectFinance().trim());
			}else {
				errors.add("objectFinance",new ActionMessage("error.string.cri.default.values"));
			}
		}else {
			requestDTO.setObjectFinance(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if(requestDTO.getIsCommodityFinanceCustomer()!=null && !requestDTO.getIsCommodityFinanceCustomer().trim().isEmpty()) {
			if(requestDTO.getIsCommodityFinanceCustomer().trim().equals("Yes") || requestDTO.getIsCommodityFinanceCustomer().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)) {
				requestDTO.setIsCommodityFinanceCustomer(requestDTO.getIsCommodityFinanceCustomer().trim());
			}else {
				errors.add("isCommodityFinanceCustomer",new ActionMessage("error.string.cri.default.values"));
			}
		}else {
			requestDTO.setIsCommodityFinanceCustomer(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if(requestDTO.getRestructuedBorrowerOrFacility()!=null && !requestDTO.getRestructuedBorrowerOrFacility().trim().isEmpty()) {
			if(requestDTO.getRestructuedBorrowerOrFacility().trim().equals("Yes") || requestDTO.getRestructuedBorrowerOrFacility().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)) {
				requestDTO.setRestructuedBorrowerOrFacility(requestDTO.getRestructuedBorrowerOrFacility().trim());
			}else {
				errors.add("restructuedBorrowerOrFacility",new ActionMessage("error.string.cri.default.values"));
			}
			if("Yes".equals(requestDTO.getRestructuedBorrowerOrFacility().trim())) {
				if(requestDTO.getFacility()!=null && !requestDTO.getFacility().trim().isEmpty()) {
					Object obj=masterObj.getObjectByEntityNameAndCPSId("actualFacilityNewMaster", requestDTO.getFacility().trim(),"facility",errors);
					if(!(obj instanceof ActionErrors)){
						requestDTO.setFacility(((IFacilityNewMaster)obj).getNewFacilityCode());
					}
				}
				
				if(requestDTO.getLimitAmount()!=null && !requestDTO.getLimitAmount().trim().isEmpty()) {
					if (ASSTValidator.isNumeric(requestDTO.getLimitAmount().trim())){
						requestDTO.setLimitAmount(requestDTO.getLimitAmount().trim());						
					}else {
						errors.add("limitAmount",new ActionMessage("Only numeric is allowed"));
					}
				}else {
					requestDTO.setLimitAmount("");
				}
			}
		}else{
			requestDTO.setRestructuedBorrowerOrFacility(DEFAULT_VALUE_FOR_CRI_INFO);
			requestDTO.setLimitAmount("");
			requestDTO.setFacility("");
		}

		if("Yes".equals(requestDTO.getTufs().trim())){
			if(requestDTO.getSubsidyFlag()!=null && !requestDTO.getSubsidyFlag().trim().isEmpty()) {
				if(requestDTO.getSubsidyFlag().trim().equals("Yes") || requestDTO.getSubsidyFlag().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)) {
					requestDTO.setSubsidyFlag(requestDTO.getSubsidyFlag());
				}else {
					errors.add("subsidyFlag",new ActionMessage("error.string.cri.default.values"));
				}
				if("Yes".equals(requestDTO.getSubsidyFlag())) {
					if(requestDTO.getHoldingCompnay()!=null && !requestDTO.getHoldingCompnay().trim().isEmpty()) {
						if(requestDTO.getHoldingCompnay().length() > 255 ) {
							errors.add("holdingCompnay",new ActionMessage("length is exceeded"));
						}else {
							requestDTO.setHoldingCompnay(requestDTO.getHoldingCompnay().trim());
						}
					}else {
						requestDTO.setHoldingCompnay("");
					}
				}
			}else {
				requestDTO.setSubsidyFlag(DEFAULT_VALUE_FOR_CRI_INFO);
			}
		}
		if(requestDTO.getCautionList()!=null && !requestDTO.getCautionList().trim().isEmpty()) {
			if(requestDTO.getCautionList().trim().equals("Yes") || requestDTO.getCautionList().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)) {
				requestDTO.setCautionList(requestDTO.getCautionList().trim());
			}else {
				errors.add("cautionList",new ActionMessage("error.string.cri.default.values"));
			}
			if("Yes".equals(requestDTO.getCautionList())) {
				if(requestDTO.getDateOfCautionList()!=null && !requestDTO.getDateOfCautionList().trim().isEmpty()) {
					try {
						Date dt=cautionlistDateFormat.parse(requestDTO.getDateOfCautionList().trim());
						requestDTO.setDateOfCautionList(cautionDateFormat.format(dt));		
					} catch (ParseException e) {		
						errors.add("dateOfCautionList",new ActionMessage("error.party.invalid.format"));	
					}
				}else{
					  requestDTO.setDateOfCautionList("");
				}
				if(requestDTO.getCompany()!=null && !requestDTO.getCompany().trim().isEmpty()) {
					if(requestDTO.getCompany().length() > 255 ) {
						errors.add("company",new ActionMessage("length is exceeded"));
					}else {
						requestDTO.setCompany(requestDTO.getCompany().trim());
					}
				}else {
					requestDTO.setCompany("");
				}
				if(requestDTO.getDirectors()!=null && !requestDTO.getDirectors().trim().isEmpty()) {
					if(requestDTO.getDirectors().length() > 100 ) {
						errors.add("directors",new ActionMessage("length is exceeded"));
					}else {
						requestDTO.setDirectors(requestDTO.getDirectors().trim());
					}
				}else {
					requestDTO.setDirectors("");
				}
				if(requestDTO.getGroupCompanies()!=null && !requestDTO.getGroupCompanies().trim().isEmpty()) { 
					if(requestDTO.getGroupCompanies().length() > 100 ) {
						errors.add("groupCompanies",new ActionMessage("length is exceeded"));
					}else {
						requestDTO.setGroupCompanies(requestDTO.getGroupCompanies().trim());
					}
				}else {
					requestDTO.setGroupCompanies("");
				}
			}
		}else {
			requestDTO.setCautionList(DEFAULT_VALUE_FOR_CRI_INFO);
		}
		
		
		if(requestDTO.getDefaultersList()!=null && !requestDTO.getDefaultersList().trim().isEmpty()) {
			if(requestDTO.getDefaultersList().trim().equals("Yes") || requestDTO.getDefaultersList().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)) {
				requestDTO.setDefaultersList(requestDTO.getDefaultersList().trim());
			}else {
				errors.add("defaultersList",new ActionMessage("error.string.cri.default.values"));
			}
			if("Yes".equals(requestDTO.getDefaultersList())) {
				
				if(requestDTO.getRbiDateOfCautionList()!=null && !requestDTO.getRbiDateOfCautionList().trim().isEmpty()) {
					
					try {
						Date dt=cautionlistDateFormat.parse(requestDTO.getRbiDateOfCautionList().trim());
						requestDTO.setRbiDateOfCautionList(cautionDateFormat.format(dt));
					} catch (ParseException e) {		
						errors.add("rbiDateOfCautionList",new ActionMessage("error.party.invalid.format"));	
					}
				}else{
					requestDTO.setRbiDateOfCautionList("");
				}
				if(requestDTO.getRbiCompany()!=null && !requestDTO.getRbiCompany().trim().isEmpty()) {
					if(requestDTO.getRbiCompany().length() > 100)
						errors.add("rbiCompany",new ActionMessage("length is exceeded"));
					else
						requestDTO.setRbiCompany(requestDTO.getRbiCompany().trim());
				}else {
					requestDTO.setRbiCompany("");
				}
				if(requestDTO.getRbiDirectors()!=null && !requestDTO.getRbiDirectors().trim().isEmpty()) {
					if(requestDTO.getRbiDirectors().length() > 100)
						errors.add("rbiDirectors",new ActionMessage("length is exceeded"));
					else
						requestDTO.setRbiDirectors(requestDTO.getRbiDirectors().trim());
				}else {
					requestDTO.setRbiDirectors("");
				}
				if(requestDTO.getRbiGroupCompanies()!=null && !requestDTO.getRbiGroupCompanies().trim().isEmpty()) { 
					if(requestDTO.getRbiGroupCompanies().length() > 100 )
						errors.add("rbiGroupCompanies",new ActionMessage("length is exceeded"));
					else
						requestDTO.setRbiGroupCompanies(requestDTO.getRbiGroupCompanies().trim());
				}else {
					requestDTO.setRbiGroupCompanies("");
				}
			}
		}else {
			requestDTO.setDefaultersList(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if (requestDTO.getCommericialRealEstate() != null
				&& !requestDTO.getCommericialRealEstate().trim().isEmpty()) {
			if (requestDTO.getCommericialRealEstate().trim().equals("Yes")
					|| requestDTO.getCommericialRealEstate().trim()
							.equals(DEFAULT_VALUE_FOR_CRI_INFO)) {
				requestDTO.setCommericialRealEstate(requestDTO.getCommericialRealEstate().trim());
			} else {
				errors.add("commericialRealEstate", new ActionMessage("error.string.cri.default.values"));
			}

			if ("Yes".equals(requestDTO.getCommericialRealEstate())) {
				if (requestDTO.getCommericialRealEstateValue() != null && !requestDTO.getCommericialRealEstateValue().trim().isEmpty()) {
					if(!(requestDTO.getCommericialRealEstateValue().trim().length()>19)) {
						try {
							if (ASSTValidator.isNumeric(requestDTO.getCommericialRealEstateValue().trim())){
								Object obj = masterObj.getMaster("entryCode", Long.parseLong(requestDTO.getCommericialRealEstateValue().trim()));
								if (obj != null) {
									ICommonCodeEntry codeEntry = (ICommonCodeEntry) obj;
									if ("COMMERCIAL_REAL_ESTATE".equals(codeEntry.getCategoryCode())) {
										requestDTO.setCommericialRealEstateValue((codeEntry).getEntryCode());
									} else {
										errors.add("commericialRealEstateValue", new ActionMessage("error.party.commericialRealEstateValue"));
									}
								} else {
									errors.add("commericialRealEstateValue", new ActionMessage("error.party.commericialRealEstateValue"));
								}
							}else {
								errors.add("commericialRealEstateValue", new ActionMessage("error.numbers.format.only"));
							}
						} catch (Exception e) {
							DefaultLogger.error(this, e.getMessage());
							errors.add("commericialRealEstateValue", new ActionMessage("error.party.commericialRealEstateValue"));
						}
					}else{
						errors.add("commericialRealEstateValue", new ActionMessage("error.string.field.length.exceeded"));
					}
				}else {
					errors.add("commericialRealEstateValue", new ActionMessage("error.string.mandatory"));
				}

				if (requestDTO.getCommericialRealEstateResidentialHousing().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)) {
					requestDTO.setCommericialRealEstateResidentialHousing("No");
				} else {
					errors.add("commericialRealEstateResidentialHousing", new ActionMessage("error.string.cri.default.value"));
				}

				if (requestDTO.getResidentialRealEstate().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)) {
					requestDTO.setResidentialRealEstate("No");
				} else {
					errors.add("residentialRealEstate", new ActionMessage("error.string.cri.default.value"));
				}

				if (requestDTO.getIndirectRealEstate().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)) {
					requestDTO.setIndirectRealEstate("No");
				} else {
					errors.add("indirectRealEstate", new ActionMessage("error.string.cri.default.value"));
				}

			} else {
				if (requestDTO.getCommericialRealEstateResidentialHousing() != null
						&& "Yes".equals(requestDTO.getCommericialRealEstateResidentialHousing())) {

					requestDTO.setCommericialRealEstateResidentialHousing(requestDTO.getCommericialRealEstateResidentialHousing().trim());

					if (requestDTO.getCommericialRealEstate().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)) {
						requestDTO.setCommericialRealEstate("No");
					} else {
						errors.add("commericialRealEstate", new ActionMessage("error.string.cri.default.value"));
					}

					if (requestDTO.getResidentialRealEstate().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)) {
						requestDTO.setResidentialRealEstate("No");
					} else {
						errors.add("residentialRealEstate", new ActionMessage("error.string.cri.default.value"));
					}

					if (requestDTO.getIndirectRealEstate().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)) {
						requestDTO.setIndirectRealEstate("No");
					} else {
						errors.add("indirectRealEstate", new ActionMessage("error.string.cri.default.value"));
					}

				} else if (requestDTO.getResidentialRealEstate() != null
						&& "Yes".equals(requestDTO.getResidentialRealEstate())) {

					requestDTO.setResidentialRealEstate(
							requestDTO.getResidentialRealEstate().trim());

					if (requestDTO.getCommericialRealEstate().trim()
							.equals(DEFAULT_VALUE_FOR_CRI_INFO)) {
						requestDTO.setCommericialRealEstate("No");
					} else {
						errors.add("commericialRealEstate", new ActionMessage("error.string.cri.default.value"));
					}

					if (requestDTO.getCommericialRealEstateResidentialHousing().trim()
							.equals(DEFAULT_VALUE_FOR_CRI_INFO)) {
						requestDTO.setCommericialRealEstateResidentialHousing("No");
					} else {
						errors.add("commericialRealEstateResidentialHousing",
								new ActionMessage("error.string.cri.default.value"));
					}

					if (requestDTO.getIndirectRealEstate().trim()
							.equals(DEFAULT_VALUE_FOR_CRI_INFO)) {
						requestDTO.setIndirectRealEstate("No");
					} else {
						errors.add("indirectRealEstate", new ActionMessage("error.string.cri.default.value"));
					}

				} else if (requestDTO.getIndirectRealEstate() != null
						&& "Yes".equals(requestDTO.getIndirectRealEstate())) {

					requestDTO.setIndirectRealEstate(requestDTO.getIndirectRealEstate().trim());

					if (requestDTO.getCommericialRealEstate().trim()
							.equals(DEFAULT_VALUE_FOR_CRI_INFO)) {
						requestDTO.setCommericialRealEstate("No");
					} else {
						errors.add("commericialRealEstate", new ActionMessage("error.string.cri.default.value"));
					}

					if (requestDTO.getCommericialRealEstateResidentialHousing().trim()
							.equals(DEFAULT_VALUE_FOR_CRI_INFO)) {
						requestDTO.setCommericialRealEstateResidentialHousing("No");
					} else {
						errors.add("commericialRealEstateResidentialHousing",
								new ActionMessage("error.string.cri.default.value"));
					}

					if (requestDTO.getResidentialRealEstate().trim()
							.equals(DEFAULT_VALUE_FOR_CRI_INFO)) {
						requestDTO.setResidentialRealEstate("No");
					} else {
						errors.add("residentialRealEstate", new ActionMessage("error.string.cri.default.value"));
					}
				}
			}
		} else {
			requestDTO.setCommericialRealEstate(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if(requestDTO.getConductOfAccountWithOtherBanks()!=null && !requestDTO.getConductOfAccountWithOtherBanks().trim().isEmpty()){
			if(requestDTO.getConductOfAccountWithOtherBanks().trim().equals("classified") || requestDTO.getConductOfAccountWithOtherBanks().trim().equals("Satisfactory")) {
				requestDTO.setConductOfAccountWithOtherBanks(requestDTO.getConductOfAccountWithOtherBanks().trim());
			}else {
				errors.add("conductOfAccountWithOtherBanks",new ActionMessage("error.string.default.values"));
			}
			if(requestDTO.getConductOfAccountWithOtherBanks().trim().equals("classified")){
				requestDTO.setConductOfAccountWithOtherBanks("classified");
				if(requestDTO.getCrilicStatus()!=null && !requestDTO.getCrilicStatus().trim().isEmpty()){
					try{
						Object obj = masterObj.getMaster("entryCode", Long.parseLong(requestDTO.getCrilicStatus().trim()));
						if(obj!=null){
							ICommonCodeEntry codeEntry = (ICommonCodeEntry)obj;
							if("CRILIC_STATUS".equals(codeEntry.getCategoryCode())){
								requestDTO.setCrilicStatus((codeEntry).getEntryCode());
							}else{
								errors.add("crilicStatus",new ActionMessage("error.invalid.field.value"));
							}
						}
						else{
							errors.add("crilicStatus",new ActionMessage("error.invalid.field.value"));
						}
					}
					catch(Exception e){
						DefaultLogger.error(this, e.getMessage());
						errors.add("crilicStatus",new ActionMessage("error.invalid.field.value"));
					}
				}else{
					errors.add("crilicStatus",new ActionMessage("error.string.mandatory"));
				}		
			}
		}else{
			requestDTO.setConductOfAccountWithOtherBanks("Satisfactory");
		}
		
		if(requestDTO.getConductOfAccountWithOtherBanks()!=null && "classified".equals(requestDTO.getConductOfAccountWithOtherBanks())) {
			if(requestDTO.getCrilicStatus()!=null && !requestDTO.getCrilicStatus().trim().isEmpty()) {
				if(requestDTO.getComment()!=null && !requestDTO.getComment().trim().isEmpty()) {					
					if(requestDTO.getComment().length()>250){
						errors.add("comment",new ActionMessage("length is exceeded"));
					}else {
						requestDTO.setComment(requestDTO.getComment().trim());						
					}
				}else {
					requestDTO.setComment("");
				}
			}else {
				requestDTO.setComment("");
			}
		}else {
			requestDTO.setComment("");
		}
		
		if(requestDTO.getIsRBIWilfulDefaultersList()!=null && !requestDTO.getIsRBIWilfulDefaultersList().trim().isEmpty()) {
			if(requestDTO.getIsRBIWilfulDefaultersList().trim().equals("Yes") || requestDTO.getIsRBIWilfulDefaultersList().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)) {
				requestDTO.setIsRBIWilfulDefaultersList(requestDTO.getIsRBIWilfulDefaultersList());
			}else {
				errors.add("isRBIWilfulDefaultersList",new ActionMessage("error.string.cri.default.values"));
			}			
			if("Yes".equals(requestDTO.getIsRBIWilfulDefaultersList())) {			
				if(requestDTO.getNameOfBank()!=null && !requestDTO.getNameOfBank().trim().isEmpty()) {
					Object obj=masterObj.getObjectByEntityNameAndId("actualOtherBank", requestDTO.getNameOfBank().trim(),"nameOfBank",errors);
					if(!(obj instanceof ActionErrors)){
						requestDTO.setNameOfBank(((IOtherBank)obj).getOtherBankCode());
					}else {
						errors.add("nameOfBank",new ActionMessage("error.invalid.field.value"));
					}
				}else {
					requestDTO.setNameOfBank("");
				}	
				if(requestDTO.getIsDirectorMoreThanOne() != null && !requestDTO.getIsDirectorMoreThanOne().trim().isEmpty()) {
					if ("Yes".equals(requestDTO.getIsDirectorMoreThanOne().trim()) || "No".equals(requestDTO.getIsDirectorMoreThanOne().trim())) {
						requestDTO.setIsDirectorMoreThanOne(requestDTO.getIsDirectorMoreThanOne().trim());
					} else {
						errors.add("isDirectorMoreThanOne", new ActionMessage("error.invalid.field.value"));
					}
				}else {
					requestDTO.setIsDirectorMoreThanOne("No");
				}
				if(requestDTO.getIsDirectorMoreThanOne()!=null && "Yes".equals(requestDTO.getIsDirectorMoreThanOne())) {
					if(requestDTO.getNameOfDirectorsAndCompany() != null && !requestDTO.getNameOfDirectorsAndCompany().trim().isEmpty()) {
						if(requestDTO.getNameOfDirectorsAndCompany().length() > 150) {
							errors.add("nameOfDirectorsAndCompany", new ActionMessage("length is exceeded"));
						} else {
							requestDTO.setNameOfDirectorsAndCompany(requestDTO.getNameOfDirectorsAndCompany());
						}
					}else {
						requestDTO.setNameOfDirectorsAndCompany("");
					}
				}else {
					requestDTO.setNameOfDirectorsAndCompany("");
				}
			
			}else {
				requestDTO.setIsRBIWilfulDefaultersList(DEFAULT_VALUE_FOR_CRI_INFO);
				requestDTO.setNameOfBank("");
				requestDTO.setIsDirectorMoreThanOne("No");
				requestDTO.setNameOfDirectorsAndCompany("");
			}
		}else {
			requestDTO.setIsRBIWilfulDefaultersList(DEFAULT_VALUE_FOR_CRI_INFO);
			requestDTO.setNameOfBank("");
			requestDTO.setIsDirectorMoreThanOne("No");
			requestDTO.setNameOfDirectorsAndCompany("");
		}
		
		if(requestDTO.getIsCibilStatusClean()!=null && !requestDTO.getIsCibilStatusClean().trim().isEmpty()) {
			if(requestDTO.getIsCibilStatusClean().trim().equals("Yes") || requestDTO.getIsCibilStatusClean().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)) {
				requestDTO.setIsCibilStatusClean(requestDTO.getIsCibilStatusClean());	
			}else{
				errors.add("isCibilStatusClean",new ActionMessage("error.string.cri.default.values"));
			}				
			if("No".equals(requestDTO.getIsCibilStatusClean())) {
				if(requestDTO.getDetailsOfCleanCibil()!=null && requestDTO.getDetailsOfCleanCibil().length() > 150) {
					errors.add("detailsOfCleanCibil",new ActionMessage("length is exceeded"));
				}else {
					requestDTO.setDetailsOfCleanCibil(requestDTO.getDetailsOfCleanCibil());					
				}
			}else {
				requestDTO.setIsCibilStatusClean("Yes");
				requestDTO.setDetailsOfCleanCibil("");
			}
			
		}else {
			requestDTO.setIsCibilStatusClean("Yes");
		}
		
		
		if(requestDTO.getGrossInvestmentPM()!=null && !requestDTO.getGrossInvestmentPM().trim().isEmpty()){
			requestDTO.setGrossInvestmentPM(requestDTO.getGrossInvestmentPM().trim());
		}else{
			requestDTO.setGrossInvestmentPM("0");
		}
		if(requestDTO.getGrossInvestmentEquip()!=null && !requestDTO.getGrossInvestmentEquip().trim().isEmpty()){
			requestDTO.setGrossInvestmentEquip(requestDTO.getGrossInvestmentEquip().trim());
		}else{
			requestDTO.setGrossInvestmentEquip("0");
		}
		
		/*
		 * Infrastructure Finance Field value will be set as 'No' by default.
		 * Hence, Infra Finance Type field value will not be validated. So commented below code 
		 * 
		 * 
		 * -------------------------- 10-MAY-2016 -------------
		 * As per the change request raised by Bank, CRI fields value should get fetched as it is in WSDL file. 
		 * Ignore validation part related to 'Infrastructure Type' and 'Add Facility Details'.
		 * 
		 * Following field values to be set as received in WSDL request.
		 * 1. Infrastructure Finance
		 * 2. Infra Type
		 * 3. Project Finance
		 * 4. Kisan Credit Card
		 * 5. Permanent SSI cert
		 * 6. Backed by Govt.
		 * 7. Priority/Non priority sector
		 * 8. Capital market exposure
		 * 9. Real Estate Exposure
		 * 
		 */ 
		if(requestDTO.getInfraFinanceType()!=null && !requestDTO.getInfraFinanceType().trim().isEmpty()){
			Object obj = masterObj.getObjectByEntityNameAndCPSId("entryCode", requestDTO.getInfraFinanceType().trim(),"infrastructFinTypeError",errors,"INFRA_FINANACE_TYPE");
			if(!(obj instanceof ActionErrors)){
				requestDTO.setInfraFinanceType(((ICommonCodeEntry)obj).getEntryCode());
			}
		}
		
		if(requestDTO.getAvgAnnualTurnover()!=null && !requestDTO.getAvgAnnualTurnover().trim().isEmpty()){
			requestDTO.setAvgAnnualTurnover(requestDTO.getAvgAnnualTurnover().trim());
		}else{
			requestDTO.setAvgAnnualTurnover("");
		}
		
//		requestDTO.setBusinessGroupExposureLimit(requestDTO.getBusinessGroupExposureLimit());
		if(requestDTO.getFirstYear()!=null && !requestDTO.getFirstYear().trim().isEmpty()){
			/*Object obj = masterObj.getObjectByEntityNameAndCPSId("entryCode", requestDTO.getFirstYear(),"firstYear",errors);
			if(!(obj instanceof ActionErrors)){
				requestDTO.setFirstYear(((ICommonCodeEntry)obj).getEntryCode().trim());
			}*/
			//Catgory Code=CRI_TENURE_YEAR
			try{
				Criterion c1 = Property.forName("entryName").eq(requestDTO.getFirstYear().trim());
				Criterion c2 = Property.forName("categoryCode").eq("CRI_TENURE_YEAR");
				DetachedCriteria criteria = DetachedCriteria.forEntityName("entryCode").add(c1).add(c2);
				List<Object> objectList = masterObj.getObjectListBySpecification(criteria);
				if(objectList!=null && objectList.size()>0){
					String entryCode = ((ICommonCodeEntry)objectList.get(0)).getEntryCode();
					requestDTO.setFirstYear(entryCode);
				}else{
					errors.add("firstYear",new ActionMessage("error.invalid.field.value"));
				}
			}
			catch(Exception e){
				errors.add("firstYear",new ActionMessage("error.invalid.field.value"));
			}
		}else{
			requestDTO.setFirstYear("");
		}
		
		if(requestDTO.getFirstYearTurnover()!=null && !requestDTO.getFirstYearTurnover().trim().isEmpty()){
			requestDTO.setFirstYearTurnover(requestDTO.getFirstYearTurnover().trim());
		}else{
			requestDTO.setFirstYearTurnover("");
		}
		
		if(requestDTO.getTurnoverCurrency()!=null && !requestDTO.getTurnoverCurrency().trim().isEmpty()){
			Object obj = masterObj.getObjectByEntityNameAndCPSId("actualForexFeedEntry", requestDTO.getTurnoverCurrency().trim(),"turnoverCurrency",errors);
			if(!(obj instanceof ActionErrors)){
				requestDTO.setTurnoverCurrency(((IForexFeedEntry)obj).getCurrencyIsoCode());
			}
		}else{
			requestDTO.setTurnoverCurrency("");
		}

		//Udf Method Info(one-to-many)
		List<PartyUdfMethodDetailsRequestDTO> udfList = new LinkedList<PartyUdfMethodDetailsRequestDTO>();
					
			if(requestDTO.getUdfMethodDetailList()!=null && !requestDTO.getUdfMethodDetailList().isEmpty()){
								
				for(PartyUdfMethodDetailsRequestDTO udfMthdDetReqDTO: requestDTO.getUdfMethodDetailList()){
					
					PartyUdfMethodDetailsRequestDTO partyUdfDtlsRequestDTO = new PartyUdfMethodDetailsRequestDTO();
					
					if(udfMthdDetReqDTO.getUdf63()!=null && !udfMthdDetReqDTO.getUdf63().trim().isEmpty()){							
						Object obj = masterObj.getObjectByEntityNameAndSequence("actualUdf",63,"udf63",errors);						  
						List<String> al = new ArrayList<String>();
						OBUdf obudf = (OBUdf) obj;
						String str = obudf.getOptions();
						String strr[] = str.split(",");
						al = Arrays.asList(strr);
						List<String> newList = new ArrayList<String>();
						for (String item : al) {
				            newList.add(item.trim());
				        }
						if(newList.contains(udfMthdDetReqDTO.getUdf63().toUpperCase().trim())) {
							if(!(obj instanceof ActionErrors)){
								partyUdfDtlsRequestDTO.setUdf63(udfMthdDetReqDTO.getUdf63().toUpperCase().trim());
							}	
						}else {
							errors.add("63udfError",new ActionMessage("error.invalid.field.value"));
						}
					}
				
					if(udfMthdDetReqDTO.getUdf78()!=null && !udfMthdDetReqDTO.getUdf78().trim().isEmpty()) {
						Object obj = masterObj.getObjectByEntityNameAndSequence("actualUdf",78,"udf78",errors);
						List<String> al = new ArrayList<String>();
						OBUdf obudf = (OBUdf) obj;
						String str = obudf.getOptions();
						String strr[] = str.split(",");
						al = Arrays.asList(strr);
						List<String> newList = new ArrayList<String>();
						for (String item : al) {
				            newList.add(item.trim());
				        }
						if(newList.contains(udfMthdDetReqDTO.getUdf78().toUpperCase().trim())) {
							if(!(obj instanceof ActionErrors)){
								partyUdfDtlsRequestDTO.setUdf78(udfMthdDetReqDTO.getUdf78().toUpperCase().trim());
							}
						}else {
							errors.add("78udfError",new ActionMessage("error.invalid.field.value"));
						}
					} else{
						partyUdfDtlsRequestDTO.setUdf78("NO");
					}
					
					if(udfMthdDetReqDTO.getUdf79()!=null && !udfMthdDetReqDTO.getUdf79().trim().isEmpty()) {
						Object obj = masterObj.getObjectByEntityNameAndSequence("actualUdf",79,"udf79",errors);
						List<String> al = new ArrayList<String>();
						OBUdf obudf = (OBUdf) obj;
						String str = obudf.getOptions();
						String strr[] = str.split(",");
						al = Arrays.asList(strr);
						List<String> newList = new ArrayList<String>();
						for (String item : al) {
				            newList.add(item.trim());
				        }
						if(newList.contains(udfMthdDetReqDTO.getUdf79().toUpperCase().trim())) {
							if(!(obj instanceof ActionErrors)){
								partyUdfDtlsRequestDTO.setUdf79(udfMthdDetReqDTO.getUdf79().toUpperCase().trim());
							}
						}else {
							errors.add("79udfError",new ActionMessage("error.invalid.field.value"));
						}
					} else{
						partyUdfDtlsRequestDTO.setUdf79("NO");
					}
					
					udfList.add(partyUdfDtlsRequestDTO);
				}
			}	

			requestDTO.setUdfMethodDetailList(udfList);		 

		
			if(WS_CALL_CREATE_PARTY.equals(requestDTO.getEvent())) {			

				if (requestDTO.getCoBorrowerDetailsList() != null
						&& requestDTO.getCoBorrowerDetailsList().getCoBorrowerDetails() != null) {

					if(ICMSConstant.YES.equals(requestDTO.getCoBorrowerDetailsInd())) {
						requestDTO.setCoBorrowerDetailsInd(ICMSConstant.YES);
					}else {
						if(StringUtils.isBlank(requestDTO.getCoBorrowerDetailsInd())) {
							errors.add("coBorrowerDetailsIndError", new ActionMessage("error.coborrower.ind.mandatory"));
						}
						requestDTO.setCoBorrowerDetailsInd(ICMSConstant.NO);
					}

					PartyCoBorrowerDetailsRequestDTO coBorrowerDetails = requestDTO.getCoBorrowerDetailsList();
					List<CoBorrowerDetailsRequestDTO> coBorrowerDetailsList = coBorrowerDetails.getCoBorrowerDetails();

					StringBuilder liabIds = new StringBuilder();
					String coBorrowerLiabIdsNew="";

					for(CoBorrowerDetailsRequestDTO coBorrower : coBorrowerDetailsList) {

						if(StringUtils.isNotBlank(coBorrower.getCoBorrowerLiabId())) {

							List<String> coBorrowerLiabIdList = UIUtil.getListFromDelimitedString(
									coBorrowerLiabIdsNew, ",");

							if(coBorrower.getCoBorrowerLiabId().trim().length()>16){
								errors.add("coBorrowerLiabIdError", new ActionMessage("error.coborrower.size.coBorrowerLiabId",coBorrower.getCoBorrowerLiabId()));
							}

							if (!CollectionUtils.isEmpty(coBorrowerLiabIdList)) {
								if(coBorrowerLiabIdList.contains(coBorrower.getCoBorrowerLiabId()))
									errors.add("coBorrowerLiabIdError", new ActionMessage("error.coborrower.duplicate.coBorrowerLiabId",coBorrower.getCoBorrowerLiabId()));
							}
							else{
								coBorrower.setCoBorrowerLiabId(coBorrower.getCoBorrowerLiabId().trim());
								liabIds.append(coBorrower.getCoBorrowerLiabId().trim()).append(",");

								coBorrowerLiabIdsNew = liabIds.toString();

							}


						}else {
							coBorrower.setCoBorrowerLiabId("");
						}

						if(StringUtils.isNotBlank(coBorrower.getCoBorrowerName())) {

							if(coBorrower.getCoBorrowerName().trim().length()>2000){
								errors.add("coBorrowerNameError", new ActionMessage("error.coborrower.size.coBorrowerName",coBorrower.getCoBorrowerName()));
							}else{
								coBorrower.setCoBorrowerName(coBorrower.getCoBorrowerName().trim());
							}

						}else {
							coBorrower.setCoBorrowerName("");
						}	

					}
				}
			}//End NPA COBORROWER
		
		// System info (one-to-many)
		if("WS_create_customer".equals(requestDTO.getEvent())){
			List<PartySystemDetailsRequestDTO> sysList = new LinkedList<PartySystemDetailsRequestDTO>();
			
			if(requestDTO.getSystemDetReqList()!=null && !requestDTO.getSystemDetReqList().isEmpty()){
				
				List<String> systemList = new LinkedList<String>();
				
				for(PartySystemDetailsRequestDTO systemDetReqDTO : requestDTO.getSystemDetReqList()){
					
					PartySystemDetailsRequestDTO partySysDTO = new PartySystemDetailsRequestDTO();
					
					//systemId value should be unique for each system Name. Below code added to validate accordingly. 
					if(systemDetReqDTO.getSystem()!=null && !systemDetReqDTO.getSystem().trim().isEmpty()
							&& systemDetReqDTO.getSystemId()!=null && !systemDetReqDTO.getSystemId().trim().isEmpty()){
						
						ICustomerDAO customerDAO = CustomerDAOFactory.getDAO();
						try {
							if(systemList!=null && systemList.contains(systemDetReqDTO.getSystem().trim()+"::"+systemDetReqDTO.getSystemId().trim())){
								errors.add("systemError",new ActionMessage("error.wsdl.duplicatesystem.inlist",systemDetReqDTO.getSystemId().trim()));
							}else{
								
								if("WS_update_customer".equals(requestDTO.getEvent())){
									if(customerDAO.checkSystemExistsInOtherParty(systemDetReqDTO.getSystem().trim(), systemDetReqDTO.getSystemId().trim(),requestDTO.getClimsPartyId())){
										errors.add("systemError",new ActionMessage("error.wsdl.duplicatesystem.id",systemDetReqDTO.getSystemId().trim()));
									}else{
										systemList.add(systemDetReqDTO.getSystem().trim()+"::"+systemDetReqDTO.getSystemId().trim());
									}
								}else{
									if(customerDAO.checkSystemExists(systemDetReqDTO.getSystem().trim(), systemDetReqDTO.getSystemId().trim())){
										errors.add("systemError",new ActionMessage("error.wsdl.duplicatesystem.id",systemDetReqDTO.getSystemId().trim()));
									}else{
										systemList.add(systemDetReqDTO.getSystem().trim()+"::"+systemDetReqDTO.getSystemId().trim());
									}
								}
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
	
					if(systemDetReqDTO.getSystem()!=null && !systemDetReqDTO.getSystem().trim().isEmpty()){
						try{
							Criterion c1 = Property.forName("entryCode").eq(systemDetReqDTO.getSystem().trim());
							Criterion c2 = Property.forName("categoryCode").eq("SYSTEM");
							DetachedCriteria criteria = DetachedCriteria.forEntityName("entryCode").add(c1).add(c2);
							List<Object> objectList = masterObj.getObjectListBySpecification(criteria);
							if(objectList!=null && !objectList.isEmpty() && objectList.size()==1){
								partySysDTO.setSystem(systemDetReqDTO.getSystem().trim());
							}else{
								errors.add("systemError", new ActionMessage("error.invalid.field.value"));
							}
						}catch (Exception e) {
							errors.add("systemError", new ActionMessage("error.invalid.field.value"));
						}
					}else{
						errors.add("systemError", new ActionMessage("error.string.mandatory"));
					}
					
					if(systemDetReqDTO.getSystemId()!=null && !systemDetReqDTO.getSystemId().trim().isEmpty()){
						partySysDTO.setSystemId(systemDetReqDTO.getSystemId().trim());
					}else{
						errors.add("systemCustomerIdError", new ActionMessage("error.string.mandatory"));
					}
					sysList.add(partySysDTO);
				}
			}else{
				errors.add("systemListEmptyError", new ActionMessage("error.string.system.empty"));
			}
			requestDTO.setSystemDetReqList(sysList);
		}
		
		 
		ISystemBankProxyManager systemBankProxy = (ISystemBankProxyManager) BeanHouse.get("systemBankProxy");

		
		List<PartyBankingMethodDetailsRequestDTO> bankList = new LinkedList<PartyBankingMethodDetailsRequestDTO>();
		if(!"UPDATE".equals(addOrUpdate)) {
		for(int i=0;i<requestDTO.getBankingMethodComboBoxList().size();i++) {	
		if(requestDTO.getBankingMethodComboBoxList().get(i).getBankingMethod()!=null && !requestDTO.getBankingMethodComboBoxList().get(i).getBankingMethod().trim().isEmpty()){
			String[] bankList2 = requestDTO.getBankingMethodComboBoxList().get(i).getBankingMethod().trim().split(",");
			String[] bankList3 = null;
			for(int j=0;j<bankList2.length;j++) {
				bankList3 = bankList2[j].split("-");
			if("OUTSIDECONSORTIUM".equals(bankList3[j]) 
					|| "OUTSIDEMULTIPLE".equals(bankList3[j]) 
					||  "MULTIPLE".equals(bankList3[j]) 
					|| "CONSORTIUM".equals(bankList3[j]) ){
				//Banking Method Info(one-to-many)
				if(requestDTO.getBankingMethodDetailList()!=null && !requestDTO.getBankingMethodDetailList().isEmpty()){
					
					Boolean defaultLeadNodalFlag = true;
					PartyBankingMethodDetailsRequestDTO systemBankDTO = new PartyBankingMethodDetailsRequestDTO();
					List systemBankList = new ArrayList();
					try {
						systemBankList = (ArrayList) systemBankProxy.getAllActual();
						OBSystemBank hdfc = (OBSystemBank) systemBankList.get(j);
						systemBankDTO.setBankType("S");			
						systemBankDTO.setBranchId(Long.toString(hdfc.getId()));
						
						//If Banking method = Consortium/Multiple >> For LeadNodalflag, If value is Blank 
						// then HDFC bank will be selected by default. ------- START
						
						for(PartyBankingMethodDetailsRequestDTO bankMthdDetReqDTO: requestDTO.getBankingMethodDetailList()){
							DefaultLogger.debug(this, "bankMthdDetReqDTO.getLeadNodalFlag():::"+bankMthdDetReqDTO.getLeadNodalFlag());
							if(null != bankMthdDetReqDTO.getLeadNodalFlag() && !bankMthdDetReqDTO.getLeadNodalFlag().trim().isEmpty()){
								if("Y".equals(bankMthdDetReqDTO.getLeadNodalFlag().trim())){
									defaultLeadNodalFlag = false;
									break;
								}
							}
						}
						if(defaultLeadNodalFlag){
							systemBankDTO.setLeadNodalFlag("Y");
						}else{
							systemBankDTO.setLeadNodalFlag(null);
						}
						//END
						
						//bankList.add(systemBankDTO);
					} catch (TrxParameterException e) {
						e.printStackTrace();
					} catch (TransactionException e) {
						e.printStackTrace();
					}
					
					for(PartyBankingMethodDetailsRequestDTO bankMthdDetReqDTO: requestDTO.getBankingMethodDetailList()){
						
						PartyBankingMethodDetailsRequestDTO partyBankDtlsRequestDTO = new PartyBankingMethodDetailsRequestDTO();
						
						if(bankMthdDetReqDTO.getLeadNodalFlag()!=null && !bankMthdDetReqDTO.getLeadNodalFlag().trim().isEmpty()){
							partyBankDtlsRequestDTO.setLeadNodalFlag(bankMthdDetReqDTO.getLeadNodalFlag().trim());
						}else{
							partyBankDtlsRequestDTO.setLeadNodalFlag("");
						}
						
						if(bankMthdDetReqDTO.getBranchId()!=null && !bankMthdDetReqDTO.getBranchId().trim().isEmpty()){

							if(bankMthdDetReqDTO.getBankType()!=null && !bankMthdDetReqDTO.getBankType().trim().isEmpty()
									&& (bankMthdDetReqDTO.getBankType().trim().equals("S") 
									|| bankMthdDetReqDTO.getBankType().trim().equals("O")) ){
								
								partyBankDtlsRequestDTO.setBankType(bankMthdDetReqDTO.getBankType().trim());
								
								if(bankMthdDetReqDTO.getBankType().trim().equals("S")){
									Object obj = masterObj.getObjectByEntityNameAndCPSId("actualOBSystemBank",bankMthdDetReqDTO.getBranchId().trim(),"BranchId",errors);
									if(!(obj instanceof ActionErrors)){
										partyBankDtlsRequestDTO.setBranchId(((Long)((ISystemBank)obj).getId()).toString());
										bankList.add(partyBankDtlsRequestDTO);
										bankList.add(systemBankDTO);
									}
								}else{
									try{
										Object obj = masterObj.getMaster("actualOtherBranch", Long.parseLong(bankMthdDetReqDTO.getBranchId().trim()));
										if(obj!=null){
											partyBankDtlsRequestDTO.setBranchId(bankMthdDetReqDTO.getBranchId().trim());
											bankList.add(partyBankDtlsRequestDTO);
											bankList.add(systemBankDTO);
										
										}else{
											errors.add("otherBranchError",new ActionMessage("error.invalid.field.value"));
										}
									}catch (Exception e) {
										DefaultLogger.error(this, e.getMessage());
										errors.add("otherBranchError",new ActionMessage("error.invalid.field.value"));
									}
								}
							} else{
								errors.add("bankTypeError", new ActionMessage("error.string.banking.banktype.empty"));
							}
						}/*else{
							errors.add("branchIdError", new ActionMessage("error.string.mandatory"));
						}*/
					}
					
				}else{
					errors.add("bankListEmptyError", new ActionMessage("error.string.banking.empty"));
				}
				}
			}
		  }
		}
		requestDTO.setBankingMethodDetailList(bankList);		
		}
		
		//---------------------to do 
		List<PartyDirectorDetailsRequestDTO> directorList = new LinkedList<PartyDirectorDetailsRequestDTO>();
		//Director info(one-to-many)
		if(requestDTO.getDirectorDetailList()!=null && !requestDTO.getDirectorDetailList().isEmpty()){
			for(PartyDirectorDetailsRequestDTO directorDetReqDTO: requestDTO.getDirectorDetailList()){
				
				PartyDirectorDetailsRequestDTO partyDirectorDetReqDTO = new PartyDirectorDetailsRequestDTO();
				
				if(directorDetReqDTO.getRelatedType()!=null && !directorDetReqDTO.getRelatedType().trim().isEmpty()){
					//Category Code == RELATED_TYPE
					Object obj = masterObj.getObjectByEntityNameAndCPSId("entryCode", directorDetReqDTO.getRelatedType().trim(),"relatedTypeError",errors,"RELATED_TYPE");
					if(!(obj instanceof ActionErrors)){
						partyDirectorDetReqDTO.setRelatedType(((ICommonCodeEntry)obj).getEntryCode());
					}
				}else{
					errors.add("relatedTypeError", new ActionMessage("error.string.mandatory"));
				}
				
				if(directorDetReqDTO.getRelationship()!=null && !directorDetReqDTO.getRelationship().trim().isEmpty()){
					//Category == RELATIONSHIP_TYPE 
					/*Object obj = masterObj.getObjectByEntityNameAndCPSId("entryCode", directorDetReqDTO.getRelationship(),"relationshipError",errors);
					if(!(obj instanceof ActionErrors)){
						partyDirectorDetReqDTO.setRelationship(((ICommonCodeEntry)obj).getEntryCode());
					}*/
					try{
						Object obj = masterObj.getMaster("entryCode", Long.parseLong(directorDetReqDTO.getRelationship().trim()));
						if(obj!=null){
							ICommonCodeEntry codeEntry = (ICommonCodeEntry)obj;
							if("RELATIONSHIP_TYPE".equals(codeEntry.getCategoryCode())){
								partyDirectorDetReqDTO.setRelationship((codeEntry).getEntryCode());
							}else{
								errors.add("relationshipError",new ActionMessage("error.invalid.field.value"));
							}
						}
						else{
							errors.add("relationshipError",new ActionMessage("error.invalid.field.value"));
						}
						}
						catch(Exception e){
							errors.add("relationshipError",new ActionMessage("error.invalid.field.value"));
						}
				}else{
					partyDirectorDetReqDTO.setRelationship("");
					errors.add("relationshipError", new ActionMessage("error.string.mandatory"));
				}
				
				if(directorDetReqDTO.getDirectorEmailId()!=null && !directorDetReqDTO.getDirectorEmailId().trim().isEmpty()){
					partyDirectorDetReqDTO.setDirectorEmailId(directorDetReqDTO.getDirectorEmailId().trim());
				}else{
					partyDirectorDetReqDTO.setDirectorEmailId("");
				}
				
				if(directorDetReqDTO.getDirectorFaxNo()!=null && !directorDetReqDTO.getDirectorFaxNo().trim().isEmpty()){
					partyDirectorDetReqDTO.setDirectorFaxNo(directorDetReqDTO.getDirectorFaxNo().trim());
				}else{
					partyDirectorDetReqDTO.setDirectorFaxNo("");
				}
				
				if(directorDetReqDTO.getDirectorTelNo()!=null && !directorDetReqDTO.getDirectorTelNo().trim().isEmpty()){
					partyDirectorDetReqDTO.setDirectorTelNo(directorDetReqDTO.getDirectorTelNo().trim());
				}else{
					partyDirectorDetReqDTO.setDirectorTelNo("");
				}
				
				if(directorDetReqDTO.getDirectorTelephoneStdCode()!=null && !directorDetReqDTO.getDirectorTelephoneStdCode().trim().isEmpty()){
					if(directorDetReqDTO.getDirectorTelephoneStdCode().trim().length() > 5){
						errors.add("telephoneNoSTDCodeLengthError", new ActionMessage("error.wsdl.directorTelephoneStdCode.length.exceeded"));
					}else{
						partyDirectorDetReqDTO.setDirectorTelephoneStdCode(directorDetReqDTO.getDirectorTelephoneStdCode().trim());
					}
				}else{
					partyDirectorDetReqDTO.setDirectorTelephoneStdCode("");
				}

				if(directorDetReqDTO.getDirectorFaxStdCode()!=null && !directorDetReqDTO.getDirectorFaxStdCode().trim().isEmpty()){
					if(directorDetReqDTO.getDirectorFaxStdCode().trim().length() > 5){
						errors.add("faxNoSTDCodeLengthError", new ActionMessage("error.wsdl.directorFaxStdCode.length.exceeded"));
					}else{
						partyDirectorDetReqDTO.setDirectorFaxStdCode(directorDetReqDTO.getDirectorFaxStdCode().trim());
					}
				}else{
					partyDirectorDetReqDTO.setDirectorFaxStdCode("");
				}
				
				if(directorDetReqDTO.getDirectorCountry()!=null && !directorDetReqDTO.getDirectorCountry().trim().isEmpty()){
						Object obj = masterObj.getObjectByEntityNameAndCPSId("actualCountry", directorDetReqDTO.getDirectorCountry().trim(),"directorCountryError",errors);
						if(!(obj instanceof ActionErrors)){
							partyDirectorDetReqDTO.setDirectorCountry(Long.toString(((ICountry)obj).getIdCountry()));
						}
				}else{
					errors.add("directorCountryError", new ActionMessage("error.string.mandatory"));
				}
				
				if(directorDetReqDTO.getDirectorState()!=null && !directorDetReqDTO.getDirectorState().trim().isEmpty()){
					try{
						Object objState = masterObj.getMaster("actualState", new Long(directorDetReqDTO.getDirectorState().trim()));
						if(objState!=null){
							partyDirectorDetReqDTO.setDirectorState(directorDetReqDTO.getDirectorState().trim());
							partyDirectorDetReqDTO.setDirectorRegion(Long.toString(((IState)objState).getRegionId().getIdRegion()));
						}else{
							errors.add("directorStateError",new ActionMessage("error.invalid.field.value"));
						}
					}catch (Exception e) {
						DefaultLogger.error(this, e.getMessage());
						errors.add("directorStateError",new ActionMessage("error.invalid.field.value"));
					}
				}else{
					errors.add("directorStateError", new ActionMessage("error.string.mandatory"));
				}
				
				if(directorDetReqDTO.getDirectorCity()!=null && !directorDetReqDTO.getDirectorCity().trim().isEmpty()){
					try{
						Object objCity = masterObj.getMaster("actualCity", new Long(directorDetReqDTO.getDirectorCity().trim()));
						if(objCity!=null){
							partyDirectorDetReqDTO.setDirectorCity(directorDetReqDTO.getDirectorCity().trim());
						}else{
							errors.add("directorCityError",new ActionMessage("error.invalid.field.value"));
						}
					}catch (Exception e) {
						DefaultLogger.error(this, e.getMessage());
						errors.add("directorCityError",new ActionMessage("error.invalid.field.value"));
					}
				}else{
					errors.add("directorCityError", new ActionMessage("error.string.mandatory"));
				}

				if(directorDetReqDTO.getDirectorAddr1()!=null && !directorDetReqDTO.getDirectorAddr1().trim().isEmpty()){
					if(directorDetReqDTO.getDirectorAddr1().trim().length()>75){
						errors.add("directorAddress1LengthError", new ActionMessage("error.wsdl.directorAddress1.length.exceeded"));
					}else{
						partyDirectorDetReqDTO.setDirectorAddr1(directorDetReqDTO.getDirectorAddr1().trim());
					}
				}else{
					errors.add("directorAddress1Error", new ActionMessage("error.string.mandatory"));
				}
				
				if(directorDetReqDTO.getDirectorAddr2()!=null && !directorDetReqDTO.getDirectorAddr2().trim().isEmpty()){
					if(directorDetReqDTO.getDirectorAddr2().trim().length()>75){
						errors.add("directorAddress2LengthError", new ActionMessage("error.wsdl.directorAddress2.length.exceeded"));
					}else{
						partyDirectorDetReqDTO.setDirectorAddr2(directorDetReqDTO.getDirectorAddr2().trim());
					}
				}else{
					errors.add("directorAddress2Error", new ActionMessage("error.string.mandatory"));
				}
				
				if(directorDetReqDTO.getDirectorAddr3()!=null && !directorDetReqDTO.getDirectorAddr3().trim().isEmpty()){
					if(directorDetReqDTO.getDirectorAddr3().trim().length()>75){
						errors.add("directorAddress3LengthError", new ActionMessage("error.wsdl.directorAddress3.length.exceeded"));
					}else{
						partyDirectorDetReqDTO.setDirectorAddr3(directorDetReqDTO.getDirectorAddr3().trim());
					}
				}else{
					partyDirectorDetReqDTO.setDirectorAddr3("");
				}
				
				/*if(directorDetReqDTO.getDirectorPincode()!=null && !directorDetReqDTO.getDirectorPincode().isEmpty()){
					if(directorDetReqDTO.getDirectorPincode().length() > 6){
						errors.add("directorpincodeError", new ActionMessage("error.wsdl.directorPincode.length.exceeded"));
					}else{
						partyDirectorDetReqDTO.setDirectorPincode(directorDetReqDTO.getDirectorPincode());
					}
				}*/
				
				
				// As per the discussion opn date : 11-Nov-2014, pincode value will be first 6 characters of given value in WSDL file
				// If First 6 characters contain any alphabet/ special characters, System should validate value
				// An appropriate validation message will be displayed in response.
				
				if(directorDetReqDTO.getDirectorPincode()!=null && !directorDetReqDTO.getDirectorPincode().trim().isEmpty()){
					
					if(directorDetReqDTO.getDirectorPincode().trim().length()>6){
						partyDirectorDetReqDTO.setDirectorPincode(directorDetReqDTO.getDirectorPincode().trim().substring(0, 6));
					}else{
						partyDirectorDetReqDTO.setDirectorPincode(directorDetReqDTO.getDirectorPincode().trim());
					}
					
					Pattern p = Pattern.compile(".*\\D.*");
					Matcher m = p.matcher(partyDirectorDetReqDTO.getDirectorPincode().trim());
					boolean isAlphaNumeric = m.matches();
					if (isAlphaNumeric) {
						errors.add("directorpincodeError", new ActionMessage("error.pincode.numeric.format"));
					}
				}else{
					partyDirectorDetReqDTO.setDirectorPincode("");
				}
				
				if(directorDetReqDTO.getPercentageOfControl()!=null && !directorDetReqDTO.getPercentageOfControl().trim().isEmpty()){
					partyDirectorDetReqDTO.setPercentageOfControl(directorDetReqDTO.getPercentageOfControl().trim());
				}else{
					partyDirectorDetReqDTO.setPercentageOfControl("");
				}
				if(directorDetReqDTO.getFullName()!=null && !directorDetReqDTO.getFullName().trim().isEmpty()){
					partyDirectorDetReqDTO.setFullName(directorDetReqDTO.getFullName().trim());
				}else{
					partyDirectorDetReqDTO.setFullName("");
				}
				
				if(directorDetReqDTO.getNamePrefix()!=null && !directorDetReqDTO.getNamePrefix().trim().isEmpty()){
					partyDirectorDetReqDTO.setNamePrefix(directorDetReqDTO.getNamePrefix().trim());
				}else{
					partyDirectorDetReqDTO.setNamePrefix("");
				}
				
				if(directorDetReqDTO.getBusinessEntityName()!=null && !directorDetReqDTO.getBusinessEntityName().trim().isEmpty()){
					partyDirectorDetReqDTO.setBusinessEntityName(directorDetReqDTO.getBusinessEntityName().trim());
				}else{
					partyDirectorDetReqDTO.setBusinessEntityName("");
				}
				
				if(directorDetReqDTO.getDirectorPAN()!=null  && !directorDetReqDTO.getDirectorPAN().trim().isEmpty()){
					partyDirectorDetReqDTO.setDirectorPAN(directorDetReqDTO.getDirectorPAN().trim());
				}else{
					partyDirectorDetReqDTO.setDirectorPAN("");
				}
				
			/*	if(directorDetReqDTO.getDirectorAADHAR()!=null  && !directorDetReqDTO.getDirectorAADHAR().trim().isEmpty()){
					partyDirectorDetReqDTO.setDirectorAADHAR(directorDetReqDTO.getDirectorAADHAR().trim());
				}else{
					partyDirectorDetReqDTO.setDirectorAADHAR("");
				}*/
				
				directorList.add(partyDirectorDetReqDTO);
			}
		}else{
			errors.add("directorListEmptyError", new ActionMessage("error.string.director.empty"));
		}
		requestDTO.setDirectorDetailList(directorList);
		
		//contact info 
		if(requestDTO.getAddress1()!=null && !requestDTO.getAddress1().trim().isEmpty()){
			if(requestDTO.getAddress1().length() > 75) {
				errors.add("address1",new ActionMessage("length is exceeded"));
			}else {
				requestDTO.setAddress1(requestDTO.getAddress1().trim());
			}
		}else{
			requestDTO.setAddress1("");
		}
		
		if(requestDTO.getAddress2()!=null && !requestDTO.getAddress2().trim().isEmpty()){
			if(requestDTO.getAddress2().length() > 75) {
				errors.add("address2",new ActionMessage("length is exceeded"));
			}else {
				requestDTO.setAddress2(requestDTO.getAddress2().trim());
			}
		}else{
			requestDTO.setAddress2("");
		}
		
		if(requestDTO.getAddress3()!=null && !requestDTO.getAddress3().trim().isEmpty()){
			if(requestDTO.getAddress3().length() > 75) {
				errors.add("address3",new ActionMessage("length is exceeded"));
			}else {
				requestDTO.setAddress3(requestDTO.getAddress3().trim());
			}
		}else{
			requestDTO.setAddress3("");
		}
		
		if(requestDTO.getRegion()!=null && !requestDTO.getRegion().trim().isEmpty()){
			Object obj = masterObj.getObjectByEntityNameAndCPSId("actualRegion", requestDTO.getRegion().trim(),"regionError",errors);
			if(!(obj instanceof ActionErrors)){
				requestDTO.setRegion(Long.toString(((IRegion)obj).getIdRegion()));
			}
		}else{
			requestDTO.setRegion("");
		}
		
		if(requestDTO.getState()!=null && !requestDTO.getState().trim().isEmpty()){
			try{
				Object objState = masterObj.getMaster("actualState", new Long(requestDTO.getState().trim()));
				if(objState!=null){
					requestDTO.setState(requestDTO.getState().trim());
				}else{
					errors.add("stateError",new ActionMessage("error.invalid.field.value"));
				}
			}catch (Exception e) {
				DefaultLogger.error(this, e.getMessage());
				errors.add("stateError",new ActionMessage("error.invalid.field.value"));
			}
		}else{
			requestDTO.setState("");
		}
		
		if(requestDTO.getCity()!=null && !requestDTO.getCity().trim().isEmpty()){
			try{
				Object objCity = masterObj.getMaster("actualCity", new Long(requestDTO.getCity().trim()));
				if(objCity!=null){
					requestDTO.setCity(requestDTO.getCity().trim());
				}else{
					errors.add("cityError",new ActionMessage("error.invalid.field.value"));
				}
			}catch (Exception e) {
				DefaultLogger.error(this, e.getMessage());
				errors.add("cityError",new ActionMessage("error.invalid.field.value"));
			}
		}else{
			requestDTO.setCity("");
		}
		
		if(requestDTO.getPincode()!=null && !requestDTO.getPincode().trim().isEmpty()){
			
			if(requestDTO.getPincode().trim().length()>6){
				requestDTO.setPincode(requestDTO.getPincode().trim().substring(0, 6));
			}else{
				requestDTO.setPincode(requestDTO.getPincode().trim());
			}
			
			Pattern p = Pattern.compile(".*\\D.*");
			Matcher m = p.matcher(requestDTO.getPincode().trim());
			boolean isAlphaNumeric = m.matches();
			if (isAlphaNumeric) {
				errors.add("specialCharacterPostcodeError", new ActionMessage("error.pincode.numeric.format"));
			}
		}else{
			requestDTO.setPincode("");
		}
		
		if(requestDTO.getEmailId()!=null && !requestDTO.getEmailId().trim().isEmpty()){
			requestDTO.setEmailId(requestDTO.getEmailId().trim());
		}else{
			requestDTO.setEmailId("");
		}
		
		if(requestDTO.getFaxStdCode()!=null && !requestDTO.getFaxStdCode().trim().isEmpty()){
			requestDTO.setFaxStdCode(requestDTO.getFaxStdCode().trim());
		}else{
			requestDTO.setFaxStdCode("");
		}
		
		if(requestDTO.getFaxNumber()!=null && !requestDTO.getFaxNumber().trim().isEmpty()){
			requestDTO.setFaxNumber(requestDTO.getFaxNumber().trim());
		}else{
			requestDTO.setFaxNumber("");
		}
		
		if(requestDTO.getTelephoneStdCode()!=null && !requestDTO.getTelephoneStdCode().trim().isEmpty()){
			requestDTO.setTelephoneStdCode(requestDTO.getTelephoneStdCode().trim());
		}else{
			requestDTO.setTelephoneStdCode("");
		}
		
		if(requestDTO.getTelephoneNo()!=null && !requestDTO.getTelephoneNo().trim().isEmpty()){
			requestDTO.setTelephoneNo(requestDTO.getTelephoneNo().trim());
		}else{
			requestDTO.setTelephoneNo("");
		}

		if(requestDTO.getRegisteredAddr1()!=null && !requestDTO.getRegisteredAddr1().trim().isEmpty()){
			if(requestDTO.getRegisteredAddr1().length() > 75 ) {
				errors.add("registeredAddr1",new ActionMessage("length is exceeded"));
			}else {
				requestDTO.setRegisteredAddr1(requestDTO.getRegisteredAddr1().trim());
			}
		}else{
			requestDTO.setRegisteredAddr1("");
		}
		
		if(requestDTO.getRegisteredAddr2()!=null && !requestDTO.getRegisteredAddr2().trim().isEmpty()){
			if(requestDTO.getRegisteredAddr2().length() > 75 ) {
				errors.add("registeredAddr2",new ActionMessage("length is exceeded"));
			}else {
				requestDTO.setRegisteredAddr2(requestDTO.getRegisteredAddr2().trim());
			}
		}else{
			requestDTO.setRegisteredAddr2("");
		}
		
		if(requestDTO.getRegisteredAddr3()!=null && !requestDTO.getRegisteredAddr3().trim().isEmpty()){
			if(requestDTO.getRegisteredAddr3().length() > 75 ) {
				errors.add("registeredAddr3",new ActionMessage("length is exceeded"));
			}else {
				requestDTO.setRegisteredAddr3(requestDTO.getRegisteredAddr3().trim());
			}
		}else{
			requestDTO.setRegisteredAddr3("");
		}
		
		if(requestDTO.getRegisteredCountry()!=null && !requestDTO.getRegisteredCountry().trim().isEmpty()){
			Object obj = masterObj.getObjectByEntityNameAndCPSId("actualCountry", requestDTO.getRegisteredCountry().trim(),"regOfficeCountryError",errors);
			if(!(obj instanceof ActionErrors)){
				requestDTO.setRegisteredCountry(Long.toString(((ICountry)obj).getIdCountry()));
			}
		}else{
			requestDTO.setRegisteredCountry("");
		}
		
		if(requestDTO.getRegisteredState()!=null && !requestDTO.getRegisteredState().trim().isEmpty()){
			try{
				Object objState = masterObj.getMaster("actualState", new Long(requestDTO.getRegisteredState().trim()));
				if(objState!=null){
					requestDTO.setRegisteredState(requestDTO.getRegisteredState().trim());
					requestDTO.setRegisteredRegion(Long.toString(((IState)objState).getRegionId().getIdRegion()));
				}else{
					errors.add("regOfficeStateError",new ActionMessage("error.invalid.field.value"));
				}
			}catch (Exception e) {
				DefaultLogger.error(this, e.getMessage());
				errors.add("regOfficeStateError",new ActionMessage("error.invalid.field.value"));
			}
		}else{
			requestDTO.setRegisteredState("");
			requestDTO.setRegisteredRegion("");
		}
		
		if(requestDTO.getRegisteredCity()!=null && !requestDTO.getRegisteredCity().trim().isEmpty()){
			try{
				Object objCity = masterObj.getMaster("actualCity", new Long(requestDTO.getRegisteredCity().trim()));
				if(objCity!=null){
					requestDTO.setRegisteredCity(requestDTO.getRegisteredCity().trim());
				}else{
					errors.add("regOfficeCityError",new ActionMessage("error.invalid.field.value"));
				}
			}catch (Exception e) {
				DefaultLogger.error(this, e.getMessage());
				errors.add("regOfficeCityError",new ActionMessage("error.invalid.field.value"));
			}
		}else{
			requestDTO.setRegisteredCity("");
		}
		
		if(requestDTO.getRegisteredPincode()!=null && !requestDTO.getRegisteredPincode().trim().isEmpty()){
			
			if(requestDTO.getRegisteredPincode().trim().length()>6){
				requestDTO.setRegisteredPincode(requestDTO.getRegisteredPincode().trim().substring(0, 6));
			}else{
				requestDTO.setRegisteredPincode(requestDTO.getRegisteredPincode().trim());
			}
			
			Pattern p = Pattern.compile(".*\\D.*");
			Matcher m = p.matcher(requestDTO.getRegisteredPincode().trim());
			boolean isAlphaNumeric = m.matches();
			if (isAlphaNumeric) {
				errors.add("specialCharacterRegOfficePostCodeError", new ActionMessage("error.pincode.numeric.format"));
			}
		}else{
			requestDTO.setRegisteredPincode("");
		}
		
		if(requestDTO.getRegisteredTelephoneStdCode()!=null && !requestDTO.getRegisteredTelephoneStdCode().trim().isEmpty()){
			requestDTO.setRegisteredTelephoneStdCode(requestDTO.getRegisteredTelephoneStdCode().trim());
		}else{
			requestDTO.setRegisteredTelephoneStdCode("");
		}
		
		if(requestDTO.getRegisteredTelNo()!=null && !requestDTO.getRegisteredTelNo().trim().isEmpty()){
			requestDTO.setRegisteredTelNo(requestDTO.getRegisteredTelNo().trim());
		}else{
			requestDTO.setRegisteredTelNo("");
		}
		
		if(requestDTO.getRegisteredFaxStdCode()!=null && !requestDTO.getRegisteredFaxStdCode().trim().isEmpty()){
			requestDTO.setRegisteredFaxStdCode(requestDTO.getRegisteredFaxStdCode().trim());
		}else{
			requestDTO.setRegisteredFaxStdCode("");
		}
		
		if(requestDTO.getRegisteredFaxNumber()!=null && !requestDTO.getRegisteredFaxNumber().trim().isEmpty()){
			requestDTO.setRegisteredFaxNumber(requestDTO.getRegisteredFaxNumber().trim());
		}else{
			requestDTO.setRegisteredFaxNumber("");
		}
		
		
		if("WS_update_customer".equals(requestDTO.getEvent())) 
		{
			if(StringUtils.isBlank(requestDTO.getClimsPartyId().trim())){
				errors.add("climsPartyIdError",new ActionMessage("error.string.mandatory"));
			}else{
				try{
					ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
					//Fetching Party Details and set to the context 
					ICMSCustomer cust = custProxy.getCustomerByCIFSource(requestDTO.getClimsPartyId().trim(),null);
					if(cust==null || "".equals(cust)){
						errors.add("climsPartyIdError",new ActionMessage("error.invalid.field.value"));
					}
				}catch(CustomerException e){
					errors.add("climsPartyIdError",new ActionMessage("error.invalid.field.value"));
				}
			}
		}
		
		
		//New Online CAM CR Start

		if(requestDTO.getListedCompany()!=null && !requestDTO.getListedCompany().trim().isEmpty()) {
			if(requestDTO.getListedCompany().trim().equals("Yes") || requestDTO.getListedCompany().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)) {
				requestDTO.setListedCompany(requestDTO.getListedCompany().trim());	
			}else{
				errors.add("listedCompany",new ActionMessage("error.string.cri.default.values"));
			}	
		}else {
			requestDTO.setListedCompany("No");
		}
		
		if (requestDTO.getListedCompany()!=null && "Yes".equals(requestDTO.getListedCompany().trim())  ) {
			if(requestDTO.getIsinNo()!=null && !requestDTO.getIsinNo().trim().isEmpty()) {
				if (!(errorCode = Validator.checkString(requestDTO.getIsinNo(), true, 1, 20))
						.equals(Validator.ERROR_NONE)) {
					errors.add("isinNoError", new ActionMessage("error.string.length.error", "1", "20"));
				}
			}else {
				errors.add("isinNoError", new ActionMessage("error.string.mandatory"));
			}
		}else {
			requestDTO.setIsinNo("");
		}
		
		if(PropertyManager.getValue("new.cam.webservice.mandatory.field.flag").equals("OFF") && ("WS_create_customer".equals(requestDTO.getEvent()) || "WS_update_customer".equals(requestDTO.getEvent()))) {
			// bypass mandatory validation
		}else {
			if (requestDTO.getYearEndPeriod() == null || "".equals(requestDTO.getYearEndPeriod()))
			{
				errors.add("yearEndPeriodError", new ActionMessage("error.string.mandatory"));

			}
		}
		
		Calendar currentDate = Calendar.getInstance();
		int  year = currentDate.get(Calendar.YEAR);
		String dt= requestDTO.getYearEndPeriod()+"-"+String.valueOf(year);

		if(requestDTO.getYearEndPeriod() != null && !requestDTO.getYearEndPeriod().isEmpty()) {
			if (! (errorCode = Validator.checkDate(dt, true, Locale.getDefault())).equals(Validator.ERROR_NONE) || dt.contains("/")) {
				errors.add("yearEndPeriodFormatError", new ActionMessage("error.yearEndFormat.invalid.format"));
			}
		}

		if (requestDTO.getCreditMgrEmpId() != null  && !"".equals(requestDTO.getCreditMgrEmpId())) {

			if (!(errorCode = Validator.checkString(requestDTO.getCreditMgrEmpId(), true, 5, 100))
					.equals(Validator.ERROR_NONE)) {
				errors.add("creditMgrEmpIdError", new ActionMessage("error.creditMgrEmpId.length.error"));
			}
		}

		if (requestDTO.getPfLrdCreditMgrEmpId() != null && !"".equals(requestDTO.getPfLrdCreditMgrEmpId())) {

			if (!(errorCode = Validator.checkString(requestDTO.getPfLrdCreditMgrEmpId(), true, 5, 100))
					.equals(Validator.ERROR_NONE)) {
				errors.add("pfLrdCreditMgrEmpIdError", new ActionMessage("error.pfLrdCreditMgrEmpId.length.error"));
			}
		}

		if(requestDTO.getIsinNo()!=null && !requestDTO.getIsinNo().trim().isEmpty()){
			requestDTO.setIsinNo(requestDTO.getIsinNo().trim());
		}else {
			requestDTO.setIsinNo("");
		}
		
		if(requestDTO.getRaroc()!=null && !requestDTO.getRaroc().trim().isEmpty()){
			if (ASSTValidator.isNumeric(requestDTO.getRaroc().trim())){		
				requestDTO.setRaroc(requestDTO.getRaroc().trim());
			}else {
				errors.add("rarocError",new ActionMessage("Only numeric is allowed"));
			}
		}else {
			requestDTO.setRaroc("");
		}
		
		if(requestDTO.getRaraocPeriod()!=null && !requestDTO.getRaraocPeriod().toString().trim().isEmpty()){
			try {
				relationshipDateFormat.parse(requestDTO.getRaraocPeriod().toString().trim());
				requestDTO.setRaraocPeriod(requestDTO.getRaraocPeriod().trim());
			} catch (ParseException e) {
				errors.add("raraocPeriodError",new ActionMessage("error.wsdl.relationshipDate.invalid.format"));
			}
			
		}else{
			requestDTO.setRaraocPeriod("");
		}
		if(requestDTO.getYearEndPeriod()!=null && !requestDTO.getYearEndPeriod().trim().isEmpty()){
		requestDTO.setYearEndPeriod(requestDTO.getYearEndPeriod());
		}else {
			requestDTO.setYearEndPeriod("");
		}
		if(requestDTO.getCreditMgrEmpId()!=null && !requestDTO.getCreditMgrEmpId().trim().isEmpty()){
			requestDTO.setCreditMgrEmpId(requestDTO.getCreditMgrEmpId());
		}else {
			requestDTO.setCreditMgrEmpId("");
		}
		if(requestDTO.getPfLrdCreditMgrEmpId()!=null && !requestDTO.getPfLrdCreditMgrEmpId().trim().isEmpty()){
			requestDTO.setPfLrdCreditMgrEmpId(requestDTO.getPfLrdCreditMgrEmpId());
		}else {
			requestDTO.setPfLrdCreditMgrEmpId("");
		}		
		
		if(requestDTO.getCreditMgrSegment()!=null && !requestDTO.getCreditMgrSegment().trim().isEmpty()){
			//Category Code= HDFC_SEGMENT 
			Object obj = masterObj.getObjectByEntityNameAndCPSId("entryCode", requestDTO.getCreditMgrSegment().trim(),"creditMgrSegment",errors,"HDFC_SEGMENT");
			if(!(obj instanceof ActionErrors)){
				requestDTO.setCreditMgrSegment(((ICommonCodeEntry)obj).getEntryCode());
			}
		}else{
			requestDTO.setCreditMgrSegment("");
		}
		
				
		if(requestDTO.getMultBankFundBasedHdfcBankPer()!=null && !requestDTO.getMultBankFundBasedHdfcBankPer().trim().isEmpty()){
			if (ASSTValidator.isNumeric(requestDTO.getMultBankFundBasedHdfcBankPer().trim())) {
				requestDTO.setMultBankFundBasedHdfcBankPer(requestDTO.getMultBankFundBasedHdfcBankPer().trim());
			}else {
				errors.add("multBankFundBasedHdfcBankPer", new ActionMessage("only numeric value allowed"));
			}	
		}else{
			requestDTO.setMultBankFundBasedHdfcBankPer("");
		}
		if(requestDTO.getMultBankFundBasedLeadBankPer()!=null && !requestDTO.getMultBankFundBasedLeadBankPer().trim().isEmpty()){
			if (ASSTValidator.isNumeric(requestDTO.getMultBankFundBasedLeadBankPer().trim())) {
				requestDTO.setMultBankFundBasedLeadBankPer(requestDTO.getMultBankFundBasedLeadBankPer().trim());
			}else { 
				errors.add("multBankFundBasedLeadBankPer", new ActionMessage("only numeric value allowed"));
			}	
		}else{
			requestDTO.setMultBankFundBasedLeadBankPer("");
		}
		if(requestDTO.getMultBankNonFundBasedHdfcBankPer()!=null && !requestDTO.getMultBankNonFundBasedHdfcBankPer().trim().isEmpty()){
			if (ASSTValidator.isNumeric(requestDTO.getMultBankNonFundBasedHdfcBankPer().trim())) {
				requestDTO.setMultBankNonFundBasedHdfcBankPer(requestDTO.getMultBankNonFundBasedHdfcBankPer().trim());
			}else { 
				errors.add("multBankNonFundBasedHdfcBankPer", new ActionMessage("only numeric value allowed"));
			}
		}else{
			requestDTO.setMultBankNonFundBasedHdfcBankPer("");
		}
		if(requestDTO.getMultBankNonFundBasedLeadBankPer()!=null && !requestDTO.getMultBankNonFundBasedLeadBankPer().trim().isEmpty()){
			if (ASSTValidator.isNumeric(requestDTO.getMultBankNonFundBasedLeadBankPer().trim())) {
				requestDTO.setMultBankNonFundBasedLeadBankPer(requestDTO.getMultBankNonFundBasedLeadBankPer().trim());
			}else {
				errors.add("multBankNonFundBasedLeadBankPer", new ActionMessage("only numeric value allowed"));
			}	
		}else{
			requestDTO.setMultBankNonFundBasedLeadBankPer("");
		}
		if(requestDTO.getConsBankFundBasedHdfcBankPer()!=null && !requestDTO.getConsBankFundBasedHdfcBankPer().trim().isEmpty()){
			if (ASSTValidator.isNumeric(requestDTO.getConsBankFundBasedHdfcBankPer().trim())) {
				requestDTO.setConsBankFundBasedHdfcBankPer(requestDTO.getConsBankFundBasedHdfcBankPer().trim());
			}else { 
				errors.add("consBankFundBasedHdfcBankPer", new ActionMessage("only numeric value allowed"));
			}	
		}else{
			requestDTO.setConsBankFundBasedHdfcBankPer("");
		}
		if(requestDTO.getConsBankFundBasedLeadBankPer()!=null && !requestDTO.getConsBankFundBasedLeadBankPer().trim().isEmpty()){
			if (ASSTValidator.isNumeric(requestDTO.getConsBankFundBasedLeadBankPer().trim())) {
				requestDTO.setConsBankFundBasedLeadBankPer(requestDTO.getConsBankFundBasedLeadBankPer().trim());
			}else {
				errors.add("consBankFundBasedLeadBankPer", new ActionMessage("only numeric value allowed"));
			}
		}else{
			requestDTO.setConsBankFundBasedLeadBankPer("");
		}
		if(requestDTO.getConsBankNonFundBasedHdfcBankPer()!=null && !requestDTO.getConsBankNonFundBasedHdfcBankPer().trim().isEmpty()){
			if (ASSTValidator.isNumeric(requestDTO.getConsBankNonFundBasedHdfcBankPer().trim())) {
				requestDTO.setConsBankNonFundBasedHdfcBankPer(requestDTO.getConsBankNonFundBasedHdfcBankPer().trim());
			}else { 
				errors.add("consBankNonFundBasedHdfcBankPer", new ActionMessage("only numeric value allowed"));
			}
		}else{
			requestDTO.setConsBankNonFundBasedHdfcBankPer("");
		}
		if(requestDTO.getConsBankNonFundBasedLeadBankPer()!=null && !requestDTO.getConsBankNonFundBasedLeadBankPer().trim().isEmpty()){
			if (ASSTValidator.isNumeric(requestDTO.getConsBankNonFundBasedLeadBankPer().trim())) {
				requestDTO.setConsBankNonFundBasedLeadBankPer(requestDTO.getConsBankNonFundBasedLeadBankPer().trim());
			}else { 
				errors.add("consBankNonFundBasedLeadBankPer", new ActionMessage("only numeric value allowed"));
			}
		}else{
			requestDTO.setConsBankNonFundBasedLeadBankPer("");
		}
		//New Online CAM CR End

		requestDTO.setErrors(errors);
		return requestDTO;
	
	}
	
	private static List fetchRMData(String rmEmpCode){
		List data = new ArrayList();	
		
		String sql="select RM_MGR_NAME,(select reg.REGION_NAME from cms_region reg where reg.id= REGION) as region from CMS_RELATIONSHIP_MGR where RM_MGR_CODE = '"+rmEmpCode+"' and STATUS = 'ACTIVE'";
		System.out.println("PartyDetailsDTOMapper.java=>Line 5436=>List fetchRMData(String rmEmpCode)=>sql=>"+sql);
//	        SQLParameter params = SQLParameter.getInstance();
	        DBUtil dbUtil = null;
	        ResultSet rs = null;

	        try {
	            dbUtil = new DBUtil();
	            dbUtil.setSQL(sql);
	            rs = dbUtil.executeQuery();
	           
	            while (rs.next()) {	               
	               data.add(rs.getString("RM_MGR_NAME"));
	               data.add(rs.getString("REGION"));
	            }
	        } catch (SQLException ex) {
	            throw new SearchDAOException("SQLException in StagingCustGrpIdentifierDAO", ex);
	        } catch (Exception ex) {
	            throw new SearchDAOException("Exception in StagingCustGrpIdentifierDAO", ex);
	        }finally{
	        	try {
					dbUtil.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        }
		return data;
	}

	private static String fetchRMDataRmMgrCode(String cpsId){
//		List data = new ArrayList();	
		String data = "";
		String sql="SELECT RM_MGR_CODE FROM CMS_RELATIONSHIP_MGR WHERE CPS_ID='"+cpsId+"' and STATUS = 'ACTIVE'";
		System.out.println("PartyDetailsDTOMapper.java=>Line 5468=>String fetchRMDataRmMgrCode(String cpsId)..sql=>"+sql);
//	        SQLParameter params = SQLParameter.getInstance();
	        DBUtil dbUtil = null;
	        ResultSet rs = null;

	        try {
	            dbUtil = new DBUtil();
	            dbUtil.setSQL(sql);
	            rs = dbUtil.executeQuery();
	           
	            while (rs.next()) {	               
	               data = (rs.getString("RM_MGR_CODE"));
//	               data.add(rs.getString("REGION"));
	            }
	        } catch (SQLException ex) {
	            throw new SearchDAOException("SQLException in fetchRMDataRmMgrCode", ex);
	        } catch (Exception ex) {
	            throw new SearchDAOException("Exception in fetchRMDataRmMgrCode", ex);
	        }finally{
	        	try {
					dbUtil.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        }
		return data;
	}
	
	private static int fetchCpsIdCountWithActiveStatus(String cpsId){
//		List data = new ArrayList();	
		int data = 0;
		String sql="SELECT COUNT(1) AS CNT FROM CMS_RELATIONSHIP_MGR WHERE CPS_ID='"+cpsId+"' and STATUS = 'ACTIVE'";
		System.out.println("PartyDetailsDTOMapper.java=>Line 5500=>int fetchCpsIdCountWithActiveStatus(String cpsId)..sql=>"+sql);
//	        SQLParameter params = SQLParameter.getInstance();
	        DBUtil dbUtil = null;
	        ResultSet rs = null;

	        try {
	            dbUtil = new DBUtil();
	            dbUtil.setSQL(sql);
	            rs = dbUtil.executeQuery();
	           
	            while (rs.next()) {	               
	               data = (rs.getInt("CNT"));
//	               data.add(rs.getString("REGION"));
	            }
	        } catch (SQLException ex) {
	            throw new SearchDAOException("SQLException in fetchCpsIdCountWithActiveStatus", ex);
	        } catch (Exception ex) {
	            throw new SearchDAOException("Exception in fetchCpsIdCountWithActiveStatus", ex);
	        }finally{
	        	try {
					dbUtil.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        }
		return data;
	}
	
	
	private static int fetchRMCodeCountWithActiveStatus(String RMCode)
	  {
	   int data = 0;
	    String sql = "SELECT COUNT(1) AS CNT FROM CMS_RELATIONSHIP_MGR WHERE RM_MGR_CODE='" + RMCode + "' and STATUS = 'ACTIVE'";
	    
	    SQLParameter params = SQLParameter.getInstance();
	    DBUtil dbUtil = null;
	    ResultSet rs = null;
	    try
	    {
	      dbUtil = new DBUtil();
	      dbUtil.setSQL(sql);
	      rs = dbUtil.executeQuery();
	      while (rs.next()) {
	        data = rs.getInt("CNT");
	      }
	      return data;
	    }
	    catch (SQLException ex)
	    {
	      System.out.println("Exception in fetchRMCodeCountWithActiveStatus line 4998=>ex=>" + ex);
	      throw new SearchDAOException("SQLException in fetchRMCodeCountWithActiveStatus", ex);
	    }
	    catch (Exception ex)
	    {
	      System.out.println("Exception in fetchRMCodeCountWithActiveStatus line 5001=>ex=>" + ex);
	      throw new SearchDAOException("Exception in fetchRMCodeCountWithActiveStatus", ex);
	    }
	    finally
	    {
	      try
	      {
	        dbUtil.close();
	      }
	      catch (SQLException e)
	      {
	        e.printStackTrace();
	      }
	    }
	  }
}