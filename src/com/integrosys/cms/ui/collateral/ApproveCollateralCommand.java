/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/ApproveCollateralCommand.java,v 1.10.10.1 2006/12/14 12:19:04 jychong Exp $
 */

package com.integrosys.cms.ui.collateral;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.NoSQLStatementException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CheckListSearchResult;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.IAddtionalDocumentFacilityDetails;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralDAO;
import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.app.collateral.bus.IInsurancePolicy;
import com.integrosys.cms.app.collateral.bus.OBInsurancePolicyColl;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeDetails;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeStockDetails;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargeaircraft.OBSpecificChargeAircraft;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargeplant.OBSpecificChargePlant;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashDeposit;
import com.integrosys.cms.app.collateral.bus.type.cash.ILienMethod;
import com.integrosys.cms.app.collateral.bus.type.cash.subtype.cashfd.ICashFd;
import com.integrosys.cms.app.collateral.bus.type.cash.subtype.cashfd.OBCashFd;
import com.integrosys.cms.app.collateral.bus.type.property.IPropertyCollateral;
import com.integrosys.cms.app.collateral.bus.type.property.IPropertyValuation1;
import com.integrosys.cms.app.collateral.bus.type.property.IPropertyValuation1Dao;
import com.integrosys.cms.app.collateral.bus.type.property.IPropertyValuation2;
import com.integrosys.cms.app.collateral.bus.type.property.IPropertyValuation2Dao;
import com.integrosys.cms.app.collateral.bus.type.property.IPropertyValuation3;
import com.integrosys.cms.app.collateral.bus.type.property.IPropertyValuation3Dao;
import com.integrosys.cms.app.collateral.bus.type.property.PropertyValuation1;
import com.integrosys.cms.app.collateral.bus.type.property.PropertyValuation2;
import com.integrosys.cms.app.collateral.bus.type.property.PropertyValuation3;
import com.integrosys.cms.app.collateral.bus.type.property.subtype.comgeneral.OBCommercialGeneral;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.collateral.trx.OBCollateralTrxValue;
import com.integrosys.cms.app.common.DomainObjectStatusMapper;
import com.integrosys.cms.app.common.StpTrxStatusReadyIndicator;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.PropertiesConstantHelper;
import com.integrosys.cms.app.email.notification.bus.ICustomerNotificationDetail;
import com.integrosys.cms.app.email.notification.bus.IEmailNotification;
import com.integrosys.cms.app.email.notification.bus.IEmailNotificationService;
import com.integrosys.cms.app.email.notification.bus.OBCustomerNotificationDetail;
import com.integrosys.cms.app.fileUpload.bus.FileUploadJdbcImpl;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.limitsOfAuthorityMaster.trxlog.bus.ILimitsOfAuthorityMasterTrxLog;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.limitsOfAuthorityMaster.LimitsOfAuthorityMasterHelper;
import com.integrosys.cms.ui.login.CMSGlobalSessionConstant;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * <p>
 * A web command to approve a collateral from a transaction context point of
 * view.
 * <p>
 * Hence, prior to reach this command, a command should read a collateral via
 * transaction engine, and passed into SERVICE_SCOPE as 'serviceColObj', this
 * will store both actual and staging copy of collaterals plus workflow
 * information.
 * <p>
 * <b>Note: </b>This command will check against the facility that the collateral
 * pledged, whether the facility is in implementated status, only then approval
 * of collateral can be proceeded. Take note of <b>has.access.stp.system</b>
 * properties as to whether to do such checking.
 * 
 * @author Hii Hui Sieng
 * @author Chong Jun Yong
 * @author Andy Wong
 * @author Phoon Sai Heng
 * @author Jerlin Ong
 * @since 2006/12/14 12:19:04
 */
public class ApproveCollateralCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE }, 
				{ CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID, String.class.getName(), GLOBAL_SCOPE },
				{ "calculatedDP", "java.lang.String", REQUEST_SCOPE },
				});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue",
				REQUEST_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		DefaultLogger.debug(this, "===============================101=============== ");
		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		ICommonUser curUser =  (ICommonUser)map.get(IGlobalConstant.USER);
		IInsuranceGCDao insuranceGCDao = (IInsuranceGCDao) BeanHouse.get("insuranceGcDao");
		ICollateral iColObj = (ICollateral) map.get("form.collateralObject");
		
		String calculatedDPGeneralChargeStr = (String)map.get("calculatedDP");

		String teamTypeMembershipID = (String) map.get(CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID);
		
		boolean isLoaAuthorizer = String.valueOf(ICMSConstant.TEAM_TYPE_SSC_CHECKER).equals(teamTypeMembershipID) || 
				String.valueOf(ICMSConstant.CPU_MAKER_CHECKER).equals(teamTypeMembershipID);
		
		ILimitsOfAuthorityMasterTrxLog obLimitsOfAuthorityMasterTrxLog = null;
		
		if(isLoaAuthorizer) {
			Map loaValidationMap = LimitsOfAuthorityMasterHelper.validateLOAMasterFieldsCollateral(itrxValue, curUser, calculatedDPGeneralChargeStr, exceptionMap);
			exceptionMap = (HashMap) loaValidationMap.get("exceptionMap");
			obLimitsOfAuthorityMasterTrxLog = (ILimitsOfAuthorityMasterTrxLog) loaValidationMap.get("obLimitsOfAuthorityMasterTrxLog");
		}
		
		if(!exceptionMap.isEmpty()) {
			exceptionMap.put(STOP_COMMAND_CHAIN, new ActionMessage("stop"));
			temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
			return temp;
		}
		
		if (itrxValue.getCollateral() instanceof OBSpecificChargePlant || itrxValue.getCollateral() instanceof OBSpecificChargeAircraft
				|| itrxValue.getCollateral() instanceof OBCommercialGeneral) {
			IInsurancePolicy[] insurancePlantList = itrxValue.getStagingCollateral().getInsurancePolicies();
			
			//IInsurancePolicy[] insurancePlantList2=insurancePlantList;
			List<IInsurancePolicy> iInsurancePolicy=new ArrayList();
			
			
			for(int i=0;i<insurancePlantList.length;i++){
				
				if("PENDING_RECEIVED".equals(insurancePlantList[i].getInsuranceStatus())){
					//OBInsurancePolicyColl  existingOBInsurancePolicy=(OBInsurancePolicyColl) insurancePlantList[i];
					
					OBInsurancePolicyColl newOBInsurancePolicy= new OBInsurancePolicyColl();
					newOBInsurancePolicy.setOriginalTargetDate(insurancePlantList[i].getExpiryDate());
					newOBInsurancePolicy.setLastApproveBy(insurancePlantList[i].getLastApproveBy());
					newOBInsurancePolicy.setLastApproveOn(insurancePlantList[i].getLastApproveOn());
					newOBInsurancePolicy.setLastUpdatedBy(insurancePlantList[i].getLastUpdatedBy());
					newOBInsurancePolicy.setLastUpdatedOn(insurancePlantList[i].getLastUpdatedOn());
				/*	newOBInsurancePolicy.setParentId(insurancePlantList[i].getParentId());
					*/
					
					//newOBInsurancePolicy.set
					newOBInsurancePolicy.setStatus("ACTIVE");
					newOBInsurancePolicy.setCmsCollateralId(itrxValue.getStagingCollateral().getCollateralID());
					newOBInsurancePolicy.setInsuranceStatus(ICMSConstant.STATE_ITEM_AWAITING);
					
					IInsurancePolicy createInsurancePolicy = insuranceGCDao.createAndUpdateInsurancePolicy("stageInsurancepolicy",newOBInsurancePolicy);
					
					iInsurancePolicy.add(createInsurancePolicy);
					
				}				
				if("PENDING_RECEIVED".equals(insurancePlantList[i].getInsuranceStatus()) || "UPDATE_RECEIVED".equals(insurancePlantList[i].getInsuranceStatus())){
					insurancePlantList[i].setInsuranceStatus(ICMSConstant.STATE_ITEM_RECEIVED);
				}
				else if(ICMSConstant.STATE_ITEM_PENDING_WAIVER.equals(insurancePlantList[i].getInsuranceStatus())){
					insurancePlantList[i].setInsuranceStatus(ICMSConstant.STATE_ITEM_WAIVED);
				}
				else if(ICMSConstant.STATE_ITEM_PENDING_DEFERRAL.equals(insurancePlantList[i].getInsuranceStatus())){
					insurancePlantList[i].setInsuranceStatus(ICMSConstant.STATE_ITEM_DEFERRED);
				}
				else if(ICMSConstant.STATE_ITEM_PENDING_UPDATE.equals(insurancePlantList[i].getInsuranceStatus())){
					insurancePlantList[i].setInsuranceStatus(ICMSConstant.STATE_ITEM_AWAITING);
				}else{
					insurancePlantList[i].setInsuranceStatus(insurancePlantList[i].getInsuranceStatus());
				}
				iInsurancePolicy.add(insurancePlantList[i]);
			}
			//itrxValue.getStagingCollateral().setInsurancePolicies(insurancePlantList);
			
			IInsurancePolicy[] iInsurancePolicyTemp = new IInsurancePolicy[iInsurancePolicy.size()];
			itrxValue.getStagingCollateral().setInsurancePolicies(iInsurancePolicy.toArray(iInsurancePolicyTemp));
			
			
			
			IAddtionalDocumentFacilityDetails[] addDocFacDetPlantList = itrxValue.getStagingCollateral().getAdditonalDocFacDetails();
			
			List<IAddtionalDocumentFacilityDetails> iaddDocFacDetList=new ArrayList();
			
			
			for(int i=0;i<addDocFacDetPlantList.length;i++){
				if("PENDING_EDIT".equals(addDocFacDetPlantList[i].getAddFacDocStatus()) 
						|| "PENDING_UPDATE".equals(addDocFacDetPlantList[i].getAddFacDocStatus())){
					addDocFacDetPlantList[i].setAddFacDocStatus("SUCCESS");
				}
				iaddDocFacDetList.add(addDocFacDetPlantList[i]);
			}
			
			IAddtionalDocumentFacilityDetails[] iAddDocFacDetTemp = new IAddtionalDocumentFacilityDetails[iaddDocFacDetList.size()];
			itrxValue.getStagingCollateral().setAdditonalDocFacDetails(iaddDocFacDetList.toArray(iAddDocFacDetTemp));
		}
		
		if (itrxValue.getCollateral() instanceof OBSpecificChargePlant || itrxValue.getCollateral() instanceof OBSpecificChargeAircraft)
		{
			Date date = new Date();
			IInsurancePolicy[] policies = itrxValue.getStagingCollateral().getInsurancePolicies();
			for(int i = 0;i < policies.length;i++ ){
				policies[i].setLastApproveBy(curUser.getLoginID());
				policies[i].setLastApproveOn(date);
			}
			if(policies.length > 0){
				itrxValue.getStagingCollateral().setInsurancePolicies(policies);
			}
			
		}
		
//added by anup k
//validation of lien total not more than deposit amount.
		
		if(itrxValue.getCollateral() instanceof IGeneralCharge){
			IGeneralCharge newCollateral = (IGeneralCharge) itrxValue.getStagingCollateral();
			HashMap distinctLocation=new HashMap();
			ICommonUser user =  (ICommonUser)map.get(IGlobalConstant.USER);
			Date date = new Date();
			IGeneralChargeDetails existingChargeDetails;
			IGeneralChargeStockDetails[] existingGeneralChargeStockDetails;
			IGeneralChargeDetails[] existingGeneralChargeDetails = null;
			if(newCollateral!=null){
			 existingGeneralChargeDetails = newCollateral.getGeneralChargeDetails();
			}
			
			 if(existingGeneralChargeDetails!=null){
					for (int i = 0; i < existingGeneralChargeDetails.length; i++) {
						 existingChargeDetails = existingGeneralChargeDetails[i];
//						if(existingChargeDetails!=null && existingChargeDetails.getDueDate().equals(dueDate)){
						if(existingChargeDetails!=null && existingChargeDetails.getStatus().equals(IGeneralChargeDetails.STATUS_PENDING)){
							existingGeneralChargeStockDetails = existingChargeDetails.getGeneralChargeStockDetails();
							if(existingGeneralChargeStockDetails!=null) {
								for (int j = 0; j < existingGeneralChargeStockDetails.length; j++) {
									IGeneralChargeStockDetails existingStockDetails = existingGeneralChargeStockDetails[j];
									if(!distinctLocation.containsKey(Long.toString(existingStockDetails.getLocationId()))){
										distinctLocation.put(Long.toString(existingStockDetails.getLocationId()),Long.toString(existingStockDetails.getLocationId()));
									}
								}
							}
							existingChargeDetails.setLastApprovedBy(user.getLoginID());
							existingChargeDetails.setLastApprovedOn(date);
							//break;
						}
					}
				}
			
		}
		if(itrxValue.getCollateral() instanceof ICashFd){
			ICashFd cashfd=(OBCashFd)itrxValue.getStagingCollateral();
			ICashFd cashfdAtual=(OBCashFd)itrxValue.getCollateral();
			//code for validation
			ICashDeposit cashDep[]=cashfd.getDepositInfo();
			ICashDeposit cashDepAtual[]=cashfdAtual.getDepositInfo();
			if(cashDep!=null){
				for(int i=0;i<cashDep.length;i++){
					if(cashDep[i].getActive().equals("active")){
					double usedLienAmt=CollateralDAOFactory.getDAO().getAllTotalLienAmount(cashDep[i].getDepositRefNo());//actual lien total with new fd no
					/*String fdReceiptNo =  CollateralDAOFactory.getDAO().getStageReceiptNoByDepositID(String.valueOf(cashDep[i].getCashDepositID()));
					 if(!fdReceiptNo.equals(cashDep[i].getDepositRefNo())){
					 usedLienAmt=usedLienAmt + CollateralDAOFactory.getDAO().getAllTotalLienAmount(fdReceiptNo);
					 }*/
					double currentLienAmt = 0;
					double remainingLienAmt=cashDep[i].getDepositAmount().getAmount();
				//	double remainingLienAmt= 0;
					boolean flag = false;
                     for(int j = 0 ; j < cashDepAtual.length ;j++)
                     {
                    	 if(cashDep[i].getRefID() == cashDepAtual[j].getRefID())
                    	 {
                    		 if(!cashDep[i].getDepositRefNo().equals(cashDepAtual[j].getDepositRefNo()))
                    		 {
                    			// currentLienAmt=CollateralDAOFactory.getDAO().getNonDepositCurrentLienAmount(String.valueOf(cashDepAtual[j].getCashDepositID()) ,cashDepAtual[j].getDepositRefNo());
                    			/* currentLienAmt=CollateralDAOFactory.getDAO().getAllTotalLienAmount(cashDep[i].getDepositRefNo());;
                    			 remainingLienAmt=remainingLienAmt-currentLienAmt;*/
                    		//	 currentLienAmt=CollateralDAOFactory.getDAO().getNonDepositCurrentLienAmount(String.valueOf(cashDepAtual[j].getCashDepositID()) ,cashDepAtual[j].getDepositRefNo());
                    			// remainingLienAmt=remainingLienAmt-currentLienAmt;
                    			// flag = true;
                    			 //Added to set currentLienAmt as zero when fd no are not same.
                    			 currentLienAmt = 0;
                    		 }
                    		 else{
                    			 usedLienAmt = usedLienAmt - CollateralDAOFactory.getDAO().getCurrentLienAmount(String.valueOf(cashDepAtual[j].getCashDepositID()) ,cashDep[i].getDepositRefNo());
                    		 }
                    		 
                    	 }
                     }
                     /*if(flag == false)
            		 {
            			// currentLienAmt=CollateralDAOFactory.getDAO().getAllTotalLienAmount(cashDep[i].getDepositRefNo());
            			 currentLienAmt=CollateralDAOFactory.getDAO().getAllTotalLienAmount(cashDep[i].getDepositRefNo());;
            			  remainingLienAmt=remainingLienAmt-currentLienAmt;
            		 }
					*/
					 //currentLienAmt=CollateralDAOFactory.getDAO().getAllTotalLienAmount(cashDep[i].getDepositRefNo());
					
					/*double currentLienAmt=CollateralDAOFactory.getDAO().getNonStagingCurrentLienAmount(String.valueOf(cashDep[i].getCashDepositID()),cashDep[i].getDepositRefNo());
				   
					
					  String fdReceiptNo1 =  CollateralDAOFactory.getDAO().getStageReceiptNoByDepositID(String.valueOf(cashDep[i].getCashDepositID()));
						 if(!fdReceiptNo1.equals(cashDep[i].getDepositRefNo())){
							 currentLienAmt=CollateralDAOFactory.getDAO().getStagingCurrentLienAmount(String.valueOf(cashDep[i].getCashDepositID()),fdReceiptNo1);
						 }*/
					
					//get total lien amt for each FD
					double lienTot=0.0;
					ILienMethod lien=null;
					if(cashDep[i].getLien()!=null){
						for(int j=0;j<cashDep[i].getLien().length;j++){
							lien=cashDep[i].getLien()[j];
							lienTot=lienTot+lien.getLienAmount();
						                                              }
				   }//end of lien total calculation for each FD.
					if(lienTot!=0.0){
						if(lienTot + usedLienAmt + currentLienAmt >(remainingLienAmt)){
							//String lienTotal = String.valueOf(remainingLienAmt -(usedLienAmt + currentLienAmt)) + " for Deposit No " +cashDep[i].getDepositRefNo();
							String lienTotal = " for Deposit No " +cashDep[i].getDepositRefNo();
							exceptionMap.put("lienTotal", new ActionMessage("error.number.must.lessthan.lien.utilized",lienTotal));
						}
						}
					}//
				}
			}
									
			//-->end validation
			// set all FDs
			if(null == exceptionMap.get("lienTotal"))
			{
			ICashDeposit fd[]=cashfd.getDepositInfo();
			ICashDeposit fdActual[]=cashfdAtual.getDepositInfo();
			if(fd!=null||fd.length!=0){
				for(int i=0;i<fd.length;i++)
				{
					boolean flag = true;
					for(int j=0;j<fdActual.length;j++)
					{
					//String fdReceiptNo =  CollateralDAOFactory.getDAO().getStageReceiptNoByDepositID(String.valueOf(fd[i].getCashDepositID()));//get by stage
					
					 if(fd[i].getRefID() == fdActual[j].getRefID())
                	 {
                		 if(!fd[i].getDepositRefNo().equals(fdActual[j].getDepositRefNo()))
                		 {
                			setAllFixedFdByReceiptNo(fd[i],fd[i].getDepositRefNo());	//update new receipt with old
     						setAllFixedFdByReceiptNo(fd[i],fdActual[j].getDepositRefNo());//update old receipt data
     						flag = false;
     	                    
                		 }
                		/* else
                		 {
                			 CollateralDAOFactory.getDAO().setAllFixedFd(fd[i]);//if receipt no not changed 
                		 }*/
                	 }
					 
					/*if(fdReceiptNo.equals(fd[i].getDepositRefNo())){
					CollateralDAOFactory.getDAO().setAllFixedFd(fd[i]);//if receipt no not changed
					}
					else
					{
						CollateralDAOFactory.getDAO().setAllFixedFdByReceiptNo(fd[i],fdReceiptNo);	//update new receipt with old
						CollateralDAOFactory.getDAO().setAllFixedFdByReceiptNo(fd[i],fd[i].getDepositRefNo());//update old receipt data
					}*/
					
					}
					if(flag)
					{
					setAllFixedFd(fd[i]);
					}
				}
			}
		}
					}
//end by anup k
		ICollateralTrxValue returnValue = new OBCollateralTrxValue();
		ICollateralProxy securityproxy = CollateralProxyFactory.getProxy();
		
		// checker approve to get customer/lp from existing context
		ITrxContext oldCtx = itrxValue.getTrxContext();
		if (oldCtx != null) {
			if (oldCtx.getCustomer() != null) {
				ctx.setCustomer(oldCtx.getCustomer());
			}

			if (oldCtx.getLimitProfile() != null) {
				ctx.setLimitProfile(oldCtx.getLimitProfile());
			}
		}
		DefaultLogger.debug(this, "===============================102=============== ");
		if (itrxValue.getStatus().equals(ICMSConstant.STATE_PENDING_RETRY)) {
			ctx.setStpAllowed(true);
		}
		else {
			CollateralStpValidatorFactory factory = (CollateralStpValidatorFactory) BeanHouse
					.get("collateralStpValidatorFactory");
			Map context = new HashMap();

			// set CMV to staging if actual got value but staging blank,
			// used for pre Stp valuation validation
			if (itrxValue.getCollateral() != null
					&& itrxValue.getCollateral().getCMV() != null
					&& (itrxValue.getStagingCollateral().getCMV() == null || itrxValue.getStagingCollateral().getCMV()
							.getAmount() < 0)) {
							
				//changing condition itrxValue.getStagingCollateral().getCMV().getAmount() <= 0 as to update  CMV value to 0
				itrxValue.getStagingCollateral().setCMV(itrxValue.getCollateral().getCMV());
			}

			context.put(CollateralStpValidator.COL_OB, itrxValue.getStagingCollateral());
			context.put(CollateralStpValidator.TRX_STATUS, itrxValue.getStatus());
			context.put(CollateralStpValidator.COL_TRX_VALUE, itrxValue);
			CollateralStpValidator validator = factory.getCollateralStpValidator(itrxValue.getStagingCollateral());
			ctx.setStpAllowed(validator.validate(context));
		}

		// Validation only needed when trx status <> 'PENDING_RETRY' When a
		// collateral having duplicated collateral name in 'PENDING_RETRY'
		// status, system should skip this validation and let SIBS to prompt
		// error so that the status can set back as 'PENDING_PERFECTION'
		if (!StringUtils.equals(itrxValue.getStatus(), ICMSConstant.STATE_PENDING_RETRY)) {
			// check duplicated collateral name
			long cmsCollateralId = ICMSConstant.LONG_INVALID_VALUE;
			if (itrxValue.getCollateral() != null
					&& itrxValue.getCollateral().getCollateralID() != ICMSConstant.LONG_INVALID_VALUE) {
				cmsCollateralId = itrxValue.getCollateral().getCollateralID();
			}

			//TODO: Temp commented by Janki on 14th Sep, 2011. at the time of approver it was giving error for duplicate
			/*boolean isDuplicatedCollateralName;
			try {
				isDuplicatedCollateralName = securityproxy.getCollateralName(itrxValue.getStagingCollateral().getSCIReferenceNote(), cmsCollateralId);
			}
			catch (CollateralException ex) {
				throw new CommandProcessingException("failed to retrieve collateral name for cms collateral id ["
						+ cmsCollateralId + "]", ex);
			}

			if (isDuplicatedCollateralName) {
				exceptionMap.put("collateralName", new ActionMessage("error.collateral.collateralName.exist"));
			}*/

			boolean isStpMandatory = PropertyManager.getBoolean("has.access.stp.system", false)
					&& PropertiesConstantHelper.isValidSTPSystem(itrxValue.getStagingCollateral().getSourceId());

			// validate facility link to collateral have been stp, filter
			// deleted limit security map
			ICollateralLimitMap[] activeCollateralLimitMap = (ICollateralLimitMap[]) SecuritySubTypeUtil
					.retrieveNonDeletedCollateralLimitMap(itrxValue.getStagingCollateral());
			List limitIdList = new ArrayList();
			if (isStpMandatory && activeCollateralLimitMap != null) {
				for (int i = 0; i < activeCollateralLimitMap.length; i++) {
					ICollateralLimitMap iCollateralLimitMap = activeCollateralLimitMap[i];
					limitIdList.add(new Long(iCollateralLimitMap.getLimitID()));
				}

				DomainObjectStatusMapper statusMapper = (DomainObjectStatusMapper) BeanHouse
						.get("facilityTrxStatusMapper");
				Map limitIdStatusMap = statusMapper.mapStatus((Long[]) limitIdList.toArray(new Long[0]));
				// disallow checker to approve when there is facility not
				// stp completely
				for (Iterator iterator = limitIdList.iterator(); iterator.hasNext();) {
					Long limitId = (Long) iterator.next();
					StpTrxStatusReadyIndicator facStpTrxStatusReadyIndicator = (StpTrxStatusReadyIndicator) limitIdStatusMap
							.get(limitId);
					//Not required for HDFC
					/*if(facStpTrxStatusReadyIndicator!=null){
						if (!StringUtils.equals("Implemented", facStpTrxStatusReadyIndicator.getTrxStatus())) {
							exceptionMap.put("pendingPerfectError", new ActionMessage("error.collateral.facility.not.stp",
									limitId + ""));
							break;
						}
					}*/
				}
			}
		}	
		DefaultLogger.debug(this, "===============================202=============== ");
		if (exceptionMap.size() == 0) {
			
			try {
				/**
                 * Added by Anil
                 * Restoring the collateral limit mapping from actual to staging, so that while approving the collateral ,
                 * mapping defined at facility level should remain as it is.
                 */
				DefaultLogger.debug(this, "===============================211=========exceptionMap.size()====== "+exceptionMap.size());
                ICollateral actualCol = itrxValue.getCollateral();
                ICollateral actualColTemp = itrxValue.getCollateral();
                ICollateral stagingCol = itrxValue.getStagingCollateral();
                stagingCol.setCollateralLimits(actualCol.getCollateralLimits());
                itrxValue.setStagingCollateral(stagingCol);
                
                //========================================================
                String documentCode="";
                IGeneralChargeDetails iGeneralChargeDetailsStage = null;
                if (itrxValue.getCollateral() instanceof IGeneralCharge) {
                	DefaultLogger.debug(this, "===============================219=============instance of IGeneralCharge========= ");
                IGeneralCharge staging = (IGeneralCharge)itrxValue.getStagingCollateral();
				
				IGeneralChargeDetails[] generalChargeDetails = staging.getGeneralChargeDetails();
				if(generalChargeDetails!=null){
				for(int a=0;a<generalChargeDetails.length;a++){
					IGeneralChargeDetails iGeneralChargeDetails = generalChargeDetails[a];
					DefaultLogger.debug(this, "===============================220=============iGeneralChargeDetails.getStatus()========= " + iGeneralChargeDetails.getStatus());
					if(iGeneralChargeDetails.getStatus().equals("PENDING")){
						documentCode=iGeneralChargeDetails.getDocCode();
						DefaultLogger.debug(this, "===============================226=============Assigning Document Code========= " + documentCode);
						iGeneralChargeDetailsStage = iGeneralChargeDetails;
					}
				}
				}
							
                }
				returnValue = CollateralProxyFactory.getProxy().checkerVerifyCollateral(ctx, itrxValue);
				DefaultLogger.debug(this, "===============================103=======returnValue=============== "+returnValue);
				ICollateralDAO collateralDAO = (ICollateralDAO)BeanHouse.get("collateralDao");
				collateralDAO.updateActualCollateral(actualColTemp);
				
				if(obLimitsOfAuthorityMasterTrxLog != null) {
					obLimitsOfAuthorityMasterTrxLog = LimitsOfAuthorityMasterHelper.prepareObLimitsOfAuthorityMasterTrxLogCollateral(returnValue, obLimitsOfAuthorityMasterTrxLog);
					LimitsOfAuthorityMasterHelper.logLimitsOfAuthorityTrxData(obLimitsOfAuthorityMasterTrxLog);
				}
				
				//ALso find out the lowest valuation based on total property amount and update the same in actual columns
				
				//insert into valuation 1 table
				if (itrxValue.getCollateral() instanceof IPropertyCollateral) {
					IPropertyCollateral iPropertyCollateral=(IPropertyCollateral)itrxValue.getCollateral();
					
					IPropertyValuation1Dao iPropertyValuation1Dao = (IPropertyValuation1Dao)BeanHouse.get("propertyValuation1Dao");
					
					DefaultLogger.debug(this, "iPropertyValuation1Dao:"+iPropertyValuation1Dao);
					//Update or create valuation entry if exist
				//	if(!(null==iPropertyCollateral.getValuationDate_v1() || "".equals(iPropertyCollateral.getValuationDate_v1()))) {
					if( (null==iPropertyCollateral.getVal1_id() || "".equals(iPropertyCollateral.getVal1_id()))) {
						//If valuation1 not present then create
						PropertyValuation1 valuation1=setValuation1(iPropertyCollateral,"Create");
						IPropertyValuation1 createObj=iPropertyValuation1Dao.createPropertyValuation1(valuation1);
						//update id into main and stage table.
						collateralDAO.updateLatestValId(""+iPropertyCollateral.getCollateralID(), ""+createObj.getId(), "VAL1_ID");
						
					}else {
							//updating all property currently
						PropertyValuation1 valuation1=setValuation1(iPropertyCollateral,"Update");
						iPropertyValuation1Dao.updatePropertyValuation1(valuation1);	
					}
					
					IPropertyValuation2Dao iPropertyValuation2Dao = (IPropertyValuation2Dao)BeanHouse.get("propertyValuation2Dao");
					
					if(null==iPropertyCollateral.getVal2_id() || "".equals(iPropertyCollateral.getVal2_id())) {
						if (null!=iPropertyCollateral.getValuationDate_v2()) {
						PropertyValuation2 valuation2= setValuation2(iPropertyCollateral,"Create");
						IPropertyValuation2 createObj = iPropertyValuation2Dao.createPropertyValuation2(valuation2);
						collateralDAO.updateLatestValId(""+iPropertyCollateral.getCollateralID(), ""+createObj.getId(), "VAL2_ID");
						}
					}else {
						PropertyValuation2 valuation2=setValuation2(iPropertyCollateral,"Update");
						iPropertyValuation2Dao.updatePropertyValuation2(valuation2);	
					}
					
					//valuation 3 update or create start
					
					IPropertyValuation3Dao iPropertyValuation3Dao = (IPropertyValuation3Dao)BeanHouse.get("propertyValuation3Dao");
					
					DefaultLogger.debug(this, "iPropertyValuation3Dao:"+iPropertyValuation3Dao);
					//Update or create valuation entry if exist
				//	if(!(null==iPropertyCollateral.getValuationDate_v3() || "".equals(iPropertyCollateral.getValuationDate_v3()))) {
					if( (null==iPropertyCollateral.getVal3_id() || "".equals(iPropertyCollateral.getVal3_id()))) {
						if (null!=iPropertyCollateral.getValuationDate_v3()) {
						//If valuation3 not present then create
						PropertyValuation3 valuation3=setValuation3(iPropertyCollateral,"Create");
						IPropertyValuation3 createObj=iPropertyValuation3Dao.createPropertyValuation3(valuation3);
						//update id into main and stage table.
						collateralDAO.updateLatestValId(""+iPropertyCollateral.getCollateralID(), ""+createObj.getId(), "VAL3_ID");
						}
					}else {
							//updating all property currently
						PropertyValuation3 valuation3=setValuation3(iPropertyCollateral,"Update");
						iPropertyValuation3Dao.updatePropertyValuation3(valuation3);	
					}	
					
					//valuation 3 update or start end
					
					//String valuationNo = CollateralHelper.findCorrectValuation(iPropertyCollateral);
					//String valuationNo = CollateralHelper.findCorrectValuationNew(iPropertyCollateral);
					
					boolean isWaiverOrDeferral = (iPropertyCollateral.getWaiver() != null &&  iPropertyCollateral.getWaiver().trim().equals("on")) ||
							(iPropertyCollateral.getDeferral() != null &&  iPropertyCollateral.getDeferral().trim().equals("on"));
					String maxVersion="";
					if(isWaiverOrDeferral) {
						 maxVersion = collateralDAO.getVersion(iPropertyCollateral.getCollateralID(),13);	
					}else {
						 maxVersion = collateralDAO.getVersion(iPropertyCollateral.getCollateralID(),123);	
					}
					
					System.out.println("ApproveCollateralCommand.java=> Going for CollateralHelper.findCorrectValuationNew=> maxVersion=>"+maxVersion+"**isWaiverOrDeferral=>"+isWaiverOrDeferral);
					String valuationNo = CollateralHelper.findCorrectValuationNew(iPropertyCollateral,maxVersion,isWaiverOrDeferral);
					System.out.println("ApproveCollateralCommand.java=> After CollateralHelper.findCorrectValuationNew=> maxVersion=>"+maxVersion+"**isWaiverOrDeferral=>"+isWaiverOrDeferral+"**valuationNo=>"+valuationNo);
					
					if(!valuationNo.isEmpty()) {
					int ret = collateralDAO.syncPropertyValuation(iPropertyCollateral.getCollateralID(), valuationNo, true);
					if(ret > 0) {
						collateralDAO.syncPropertyValuation(iPropertyCollateral.getCollateralID(), valuationNo, false);
					}
					}
					//update/insert into table cms_property_mortgage_det for previous mortgage date.
					if(null!= iPropertyCollateral.getSalePurchaseDate()) {
					Date salePurchaseDate = iPropertyCollateral.getSalePurchaseDate();
					String str=DateUtil.formatDate("dd-MMM-yy",salePurchaseDate);
					DefaultLogger.debug(this,"str:"+str+" to caps:"+str.toUpperCase());
					
					int count=collateralDAO.CheckPreviousMortData(iPropertyCollateral.getCollateralID(),str.toUpperCase());
					if(count >0) {
						collateralDAO.updatePreviousMortagageData(iPropertyCollateral);
					}else {
						//if not present then only create the same.
						String id=new SimpleDateFormat("yyyyMMdd").format(new Date())+String.format("%09d", collateralDAO.getNextSequnceNumber("CMS_PROP_MORTGAGE_DET_SEQ"));
						collateralDAO.createPreviuosMortgageData(iPropertyCollateral, id);
			
					 }
					}
					//make the flag false
				/*	if("true".equals(iPropertyCollateral.getMortgageCreationAdd()) || "preMortDdTrue".equals(iPropertyCollateral.getMortgageCreationAdd())){*/
					if("preMortDdTrue".equals(iPropertyCollateral.getMortgageCreationAdd())){
					 collateralDAO.updateLatestValId(""+iPropertyCollateral.getCollateralID(), "true", "MORTGAGECREATIONADD");	
					}
				}
				generateInsuranceCoverageNotification(itrxValue.getCollateral(),(String)map.get("calculatedDP"));
				
				DefaultLogger.debug(this, "===============================104====================== ");
				//ICollateralDAO collateralDAO = (ICollateralDAO)BeanHouse.get("collateralDao");
				
				
				String partyId = collateralDAO.getCustomerCIFIDByCollateralID(actualCol.getCollateralID());
				
				collateralDAO.removeExtraEntryFromLSM(partyId);
				
				result.put("request.ITrxValue", returnValue);
				
				
				if (itrxValue.getCollateral() instanceof IGeneralCharge) {
					DefaultLogger.debug(this, "===============================245=============instance of IGeneralCharge========= ");
					String alimitProfileId=collateralDAO.getCustomerLimitProfileIDByCollateralID(actualCol.getCollateralID());
					
					
					
					
						ICheckListProxyManager checkListProxyManager = CheckListProxyManagerFactory.getCheckListProxyManager();
					  IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
			                IGeneralParamEntry generalParamEntries= generalParamDao.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
			    			Date  date=itrxValue.getTransactionDate();
			    			DateFormat df = new SimpleDateFormat("dd-MMM-yy");
			    			String stringDate=df.format(date);
			    		/*	if(generalParamEntries!=null){
		    					 date=generalParamEntries.getParamValue();
		    				}*/
			    			
			    			
			    			
			    			//NPA Stock FCUBS Start
			    			boolean flag = false;
			    			boolean flag1 = false;
			    			boolean documentCodeIsEmpty = false;
			    			String documentCodeActual = "";
			    			if(documentCode != null && !"".equals(documentCode)) {
//			    			 IGeneralCharge actuals = (IGeneralCharge)itrxValue.getCollateral();actualCol
			    				IGeneralCharge actuals = (IGeneralCharge) actualCol;
			 				
			 				IGeneralChargeDetails[] generalChargeDetails = actuals.getGeneralChargeDetails();
			 				if(generalChargeDetails!=null){
			 				for(int a=0;a<generalChargeDetails.length;a++){
			 					IGeneralChargeDetails iGeneralChargeDetails = generalChargeDetails[a];
			 					
			 					documentCodeActual=iGeneralChargeDetails.getDocCode();
			 						if(documentCode.equals(documentCodeActual)) {
			 							flag1 = true;
			 							if(iGeneralChargeDetails.getDueDate() != null && iGeneralChargeDetailsStage.getDueDate() != null) {
			 								
			 								if(iGeneralChargeDetails.getDueDate().compareTo(iGeneralChargeDetailsStage.getDueDate()) == 0) {
			 									
			 								}else {
			 									flag = true;
			 								}
			 							}else {
			 								
			 								if(iGeneralChargeDetails.getDueDate() != null) {
			 									if(iGeneralChargeDetails.getDueDate().compareTo(iGeneralChargeDetailsStage.getDueDate()) == 0) {
				 									
				 								}else {
				 									flag = true;
				 								}
			 								}else if(iGeneralChargeDetailsStage.getDueDate() != null) {
			 										if(iGeneralChargeDetailsStage.getDueDate().compareTo(iGeneralChargeDetails.getDueDate()) == 0) {
				 									
				 								}else {
				 									flag = true;
				 								}
			 								}
			 								
			 							}
			 							
			 							if(flag == false) {
			 								if(!iGeneralChargeDetailsStage.getStockdocMonth().equals(iGeneralChargeDetails.getStockdocMonth())) {
			 									flag = true;
			 								}
			 								else if(!iGeneralChargeDetailsStage.getStockdocYear().equals(iGeneralChargeDetails.getStockdocYear())) {
			 									flag = true;
			 								}
			 							}
			 							
			 						}
			 				}
			 				}else {
			 						flag = true;
			 					
			 				}
			    			
			    			}else {
			    				documentCodeIsEmpty = true;
			    			}
			    			String stockdocMonth = "";
			    			String stockdocYear = "";
			    			String colId = "";
			    			ILimitDAO daoLmt = LimitDAOFactory.getDAO();
			    			FileUploadJdbcImpl fileUpload=new FileUploadJdbcImpl();
			    			if((flag1 == false || flag == true) && documentCodeIsEmpty == false) {
							long collateralId = actualCol.getCollateralID();
							if(!"".equals(collateralId)) {
								System.out.println("Going for update status from SUCCESS to PENDING in table SCI_LSP_SYS_XREF.");
							ArrayList xrefIdList = collateralDAO.getXrefIdList(collateralId);
							ArrayList sourceRefNoList = new ArrayList();
							colId = Long.toString(collateralId);
							
							stockdocMonth = daoLmt.getStockDocMonthByColId(colId);
							stockdocYear = daoLmt.getStockDocYearByColId(colId);
							//For update status SUCCESS to PENDING Start
							//account.setAction(ICMSConstant.FCUBSLIMIT_ACTION_MODIFY);
							if(xrefIdList != null ) {
								
								for(int i=0;i<xrefIdList.size();i++) {
									String sourceRefNo=fileUpload.generateSourceRefNo();
									sourceRefNoList.add(sourceRefNo);
								}
								
							collateralDAO.updateStatusSuccessToPending(xrefIdList,sourceRefNoList,stockdocMonth,stockdocYear);
							collateralDAO.updateStagingStatusSuccessToPending(xrefIdList,sourceRefNoList,stockdocMonth,stockdocYear);
							
							System.out.println("Complete update status from SUCCESS to PENDING in table SCI_LSP_SYS_XREF.");
							}
							}
							
			    			}
							//NPA Stock FCUBS End
			    			
			    			
					if(alimitProfileId!=null ){
						long limitProfileId=Long.parseLong(alimitProfileId);
						DefaultLogger.debug(this, "===============================262=============getting recurrent checklist========= ");
						CheckListSearchResult checkListRECSummary = checkListProxyManager.getCAMCheckListByCategoryAndProfileID("REC",limitProfileId);
						
					if(checkListRECSummary!=null){
						DefaultLogger.debug(this, "===============================266=============updating checklist========= ");
						DefaultLogger.debug(this, "===============================267=============checkListRECSummary.getCheckListID()========= "+checkListRECSummary.getCheckListID());
						DefaultLogger.debug(this, "===============================268=============documentCode========= "+documentCode);
						DefaultLogger.debug(this, "===============================269=============stringDate========= "+stringDate);
						collateralDAO.updateStatusOfChecklistItemForGC(checkListRECSummary.getCheckListID(), documentCode, stringDate);
						DefaultLogger.debug(this, "===============================272=============executing procedure========= ");
						DefaultLogger.debug(this, "===============================272=============actualCol.getCollateralID()========= "+actualCol.getCollateralID());
						collateralDAO.executeReceivedStatementProc(actualCol.getCollateralID());
						
						
						DefaultLogger.debug(this, "===============================272=============updating checklist successfull========= ");
					}
					}
					
					
					
				}
				
				
			}
			catch (CollateralException ex) {
				throw new CommandProcessingException("failed to verify collateral, transaction id ["
						+ itrxValue.getTransactionID() + "]", ex);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	private PropertyValuation1 setValuation1(IPropertyCollateral iPropertyCollateral,String name) {
		PropertyValuation1 valuation1=new PropertyValuation1();
		if("Update".equals(name)){
			valuation1.setId(new Long(iPropertyCollateral.getVal1_id()));
		}
		valuation1.setPreviousMortCreationDate(iPropertyCollateral.getPreviousMortCreationDate());
		valuation1.setCollateralId(iPropertyCollateral.getCollateralID());
		valuation1.setSubTypeName(iPropertyCollateral.getCollateralSubType().getSubTypeName());
		valuation1.setTypeName(iPropertyCollateral.getCollateralType().getTypeName());
		valuation1.setSciRefNote(iPropertyCollateral.getSCIReferenceNote());
		valuation1.setSecurityCurrency(iPropertyCollateral.getCurrencyCode());
		valuation1.setSecPriority(iPropertyCollateral.getSecPriority());
		valuation1.setMonitorProcess(iPropertyCollateral.getMonitorProcess());
		valuation1.setMonitorFrequency(iPropertyCollateral.getMonitorFrequency());
		valuation1.setSecLocation(iPropertyCollateral.getCollateralLocation());
		valuation1.setSecOrganization(iPropertyCollateral.getSecurityOrganization());
		valuation1.setCollateralCode(iPropertyCollateral.getCollateralCode());
		valuation1.setRevalFrequency(iPropertyCollateral.getCommonRevalFreq());
		valuation1.setNextValuationDate(iPropertyCollateral.getNextValDate());
		valuation1.setChangeType(iPropertyCollateral.getTypeOfChange());
		valuation1.setOtherBankCharge(iPropertyCollateral.getOtherBankCharge());
		valuation1.setSecOwnership(iPropertyCollateral.getSecurityOwnership());
		valuation1.setOwnerOfProperty(iPropertyCollateral.getOwnerOfProperty());
		valuation1.setThirdPartyEntity(iPropertyCollateral.getThirdPartyEntity());
		valuation1.setCinThirdParty(iPropertyCollateral.getCinForThirdParty());
		valuation1.setCersaiTrxRefNo(iPropertyCollateral.getCersaiTransactionRefNumber());
		valuation1.setCersaiSecurityInterestId(iPropertyCollateral.getCersaiSecurityInterestId());
		valuation1.setCersaiAssetId(iPropertyCollateral.getCersaiAssetId());
		valuation1.setDateCersaiRegi(iPropertyCollateral.getDateOfCersaiRegisteration());
		valuation1.setCersaiRegDate(iPropertyCollateral.getCersiaRegistrationDate());
		valuation1.setCersaiId(iPropertyCollateral.getCersaiId());
		valuation1.setSaleDeedPurchaseDate(iPropertyCollateral.getSaleDeedPurchaseDate());
		valuation1.setThirdPartyAddress(iPropertyCollateral.getThirdPartyAddress());
		valuation1.setThirdPartyState(iPropertyCollateral.getThirdPartyState());
		valuation1.setThirdPartyCity(iPropertyCollateral.getThirdPartyCity());
		valuation1.setThirdPartyPincode(iPropertyCollateral.getThirdPartyPincode());
		valuation1.setPropertyId(iPropertyCollateral.getPropertyId());
		valuation1.setDescOfAsset(iPropertyCollateral.getDescription());
		valuation1.setPropertyType(iPropertyCollateral.getPropertyType());
		
		if(null!=iPropertyCollateral.getSalePurchaseValue())
		valuation1.setSalePurchaseValue(iPropertyCollateral.getSalePurchaseValue().getAmountAsBigDecimal().toString());
		
		valuation1.setSalePurchaseDate(iPropertyCollateral.getSalePurchaseDate());
		valuation1.setMortgageType(iPropertyCollateral.getTypeOfMargage());
		valuation1.setMortgagecretaedBy(iPropertyCollateral.getMorgageCreatedBy());
		valuation1.setDocumentReceived(iPropertyCollateral.getDocumentReceived());
		valuation1.setDocumentBlock(iPropertyCollateral.getDocumentReceived());
		valuation1.setBinNumber(iPropertyCollateral.getBinNumber());
		valuation1.setClaim(iPropertyCollateral.getClaim());
		valuation1.setClaimType(iPropertyCollateral.getClaimType());
		valuation1.setAdvocateLawyerName(iPropertyCollateral.getAdvocateLawyerName());
		valuation1.setDeveloperGroupCompany(iPropertyCollateral.getDevGrpCo());
		valuation1.setLotNo(iPropertyCollateral.getLotNo());
		valuation1.setLotNumberPrefix(iPropertyCollateral.getLotNumberPrefix());
		valuation1.setPropertyLotLocation(iPropertyCollateral.getPropertyLotLocation());
		valuation1.setOtherCity(iPropertyCollateral.getOtherCity());
		valuation1.setPropertyAddress(iPropertyCollateral.getPropertyAddress());
		valuation1.setPropertyAddress2(iPropertyCollateral.getPropertyAddress2());
		valuation1.setPropertyAddress3(iPropertyCollateral.getPropertyAddress3());
		valuation1.setPropertyAddress4(iPropertyCollateral.getPropertyAddress4());
		valuation1.setPropertyAddress5(iPropertyCollateral.getPropertyAddress5());
		valuation1.setPropertyAddress6(iPropertyCollateral.getPropertyAddress6());
		valuation1.setProjectName(iPropertyCollateral.getProjectName());
		valuation1.setValuationDateV1(iPropertyCollateral.getValuationDate_v1());
		valuation1.setValuatorCompanyV1(iPropertyCollateral.getValuatorCompany_v1());
		
		if(null!=iPropertyCollateral.getTotalPropertyAmount_v1())
		valuation1.setTotalPropertyAmontV1(iPropertyCollateral.getTotalPropertyAmount_v1().getAmountAsBigDecimal().toString());
		valuation1.setCategoryOfLandUseV1(iPropertyCollateral.getCategoryOfLandUse_v1());
		valuation1.setDeveloperNameV1(iPropertyCollateral.getDeveloperName_v1());
		valuation1.setCountryV1(iPropertyCollateral.getCountry_v1());
		valuation1.setRegionV1(iPropertyCollateral.getRegion_v1());
		valuation1.setStateV1(iPropertyCollateral.getLocationState_v1());
		valuation1.setNearestCityV1(iPropertyCollateral.getNearestCity_v1());
		valuation1.setPincodeV1(iPropertyCollateral.getPinCode_v1());
		valuation1.setLandAreaV1(iPropertyCollateral.getLandArea_v1());
		valuation1.setLandAreaUOMV1(iPropertyCollateral.getLandAreaUOM_v1());
		valuation1.setInSqfLandAreaV1(iPropertyCollateral.getInSqftLandArea_v1());
		valuation1.setBuildupAreaV1(iPropertyCollateral.getBuiltupArea_v1());
		valuation1.setBuildupAreaUOMV1(iPropertyCollateral.getBuiltupAreaUOM_v1());
		valuation1.setInSqfbuildupAreaV1(iPropertyCollateral.getInSqftBuiltupArea_v1());
		valuation1.setPropertyCompletionStatusV1(iPropertyCollateral.getPropertyCompletionStatus_v1());
		valuation1.setLandValueIRBV1(iPropertyCollateral.getLandValue_v1());
		valuation1.setBuildingValueIRBV1(iPropertyCollateral.getBuildingValue_v1());
		valuation1.setReconstructionValueIRBV1(iPropertyCollateral.getReconstructionValueOfTheBuilding_v1());
		valuation1.setIsPhyInspectionV1(iPropertyCollateral.getIsPhysicalInspection_v1());
		valuation1.setPhyInspectionFreqUnitV1(iPropertyCollateral.getPhysicalInspectionFreqUnit_v1());
		valuation1.setLastPhyInspectionDateV1(iPropertyCollateral.getLastPhysicalInspectDate_v1());
		valuation1.setNextPhyInspectionDateV1(iPropertyCollateral.getNextPhysicalInspectDate_v1());
		valuation1.setRemarksPropertyV1(iPropertyCollateral.getRemarksProperty_v1());
		valuation1.setEnvironmentRiskyStatus(iPropertyCollateral.getEnvRiskyStatus());
		valuation1.setEnvironmentRiskDate(iPropertyCollateral.getEnvRiskyDate());
		valuation1.setTsrDate(iPropertyCollateral.getTsrDate());
		valuation1.setNextTsrDate(iPropertyCollateral.getNextTsrDate());
		valuation1.setTsrFrequency(iPropertyCollateral.getTsrFrequency());
		valuation1.setConstitution(iPropertyCollateral.getConstitution());
		valuation1.setEnvironmentRiskRemark(iPropertyCollateral.getEnvRiskyRemarks());
		
		//set the new details legalAuditDate etc
		valuation1.setLegalAuditDate(iPropertyCollateral.getLegalAuditDate());
		valuation1.setInterveingPeriSerachDate(iPropertyCollateral.getInterveingPeriSearchDate());
		valuation1.setDateOfReceiptTitleDeed(iPropertyCollateral.getDateOfReceiptTitleDeed());
		valuation1.setValCreationDateV1(iPropertyCollateral.getValcreationdate_v1());
	
		valuation1.setPropertyUsage(iPropertyCollateral.getPropertyUsage());
		valuation1.setMortageRegisteredRef(iPropertyCollateral.getMortageRegisteredRef());
		valuation1.setRevalOverride(iPropertyCollateral.getRevalOverride());
		return valuation1; 
	}
	
	private PropertyValuation2 setValuation2(IPropertyCollateral iPropertyCollateral,String name) {
		PropertyValuation2 valuation2=new PropertyValuation2();
		if("Update".equals(name)){
			valuation2.setId(new Long(iPropertyCollateral.getVal2_id()));
		}
		valuation2.setPreviousMortCreationDate(iPropertyCollateral.getPreviousMortCreationDate());
		valuation2.setCollateralId(iPropertyCollateral.getCollateralID());
		valuation2.setSubTypeName(iPropertyCollateral.getCollateralSubType().getSubTypeName());
		valuation2.setTypeName(iPropertyCollateral.getCollateralType().getTypeName());
		valuation2.setSciRefNote(iPropertyCollateral.getSCIReferenceNote());
		valuation2.setSecurityCurrency(iPropertyCollateral.getCurrencyCode());
		valuation2.setSecPriority(iPropertyCollateral.getSecPriority());
		valuation2.setMonitorProcess(iPropertyCollateral.getMonitorProcess());
		valuation2.setMonitorFrequency(iPropertyCollateral.getMonitorFrequency());
		valuation2.setSecLocation(iPropertyCollateral.getCollateralLocation());
		valuation2.setSecOrganization(iPropertyCollateral.getSecurityOrganization());
		valuation2.setCollateralCode(iPropertyCollateral.getCollateralCode());
		valuation2.setRevalFrequency(iPropertyCollateral.getCommonRevalFreq());
		valuation2.setNextValuationDate(iPropertyCollateral.getNextValDate());
		valuation2.setChangeType(iPropertyCollateral.getTypeOfChange());
		valuation2.setOtherBankCharge(iPropertyCollateral.getOtherBankCharge());
		valuation2.setSecOwnership(iPropertyCollateral.getSecurityOwnership());
		valuation2.setOwnerOfProperty(iPropertyCollateral.getOwnerOfProperty());
		valuation2.setThirdPartyEntity(iPropertyCollateral.getThirdPartyEntity());
		valuation2.setCinThirdParty(iPropertyCollateral.getCinForThirdParty());
		valuation2.setCersaiTrxRefNo(iPropertyCollateral.getCersaiTransactionRefNumber());
		valuation2.setCersaiSecurityInterestId(iPropertyCollateral.getCersaiSecurityInterestId());
		valuation2.setCersaiAssetId(iPropertyCollateral.getCersaiAssetId());
		valuation2.setDateCersaiRegi(iPropertyCollateral.getDateOfCersaiRegisteration());
		valuation2.setCersaiRegDate(iPropertyCollateral.getCersiaRegistrationDate());
		valuation2.setCersaiId(iPropertyCollateral.getCersaiId());
		valuation2.setSaleDeedPurchaseDate(iPropertyCollateral.getSaleDeedPurchaseDate());
		valuation2.setThirdPartyAddress(iPropertyCollateral.getThirdPartyAddress());
		valuation2.setThirdPartyState(iPropertyCollateral.getThirdPartyState());
		valuation2.setThirdPartyCity(iPropertyCollateral.getThirdPartyCity());
		valuation2.setThirdPartyPincode(iPropertyCollateral.getThirdPartyPincode());
		valuation2.setPropertyId(iPropertyCollateral.getPropertyId());
		valuation2.setDescOfAsset(iPropertyCollateral.getDescription());
		valuation2.setPropertyType(iPropertyCollateral.getPropertyType());
		
		if(null!=iPropertyCollateral.getSalePurchaseValue())
		valuation2.setSalePurchaseValue(iPropertyCollateral.getSalePurchaseValue().getAmountAsBigDecimal().toString());
		
		valuation2.setSalePurchaseDate(iPropertyCollateral.getSalePurchaseDate());
		valuation2.setMortgageType(iPropertyCollateral.getTypeOfMargage());
		valuation2.setMortgagecretaedBy(iPropertyCollateral.getMorgageCreatedBy());
		valuation2.setDocumentReceived(iPropertyCollateral.getDocumentReceived());
		valuation2.setDocumentBlock(iPropertyCollateral.getDocumentReceived());
		valuation2.setBinNumber(iPropertyCollateral.getBinNumber());
		valuation2.setClaim(iPropertyCollateral.getClaim());
		valuation2.setClaimType(iPropertyCollateral.getClaimType());
		valuation2.setAdvocateLawyerName(iPropertyCollateral.getAdvocateLawyerName());
		valuation2.setDeveloperGroupCompany(iPropertyCollateral.getDevGrpCo());
		valuation2.setLotNo(iPropertyCollateral.getLotNo());
		valuation2.setLotNumberPrefix(iPropertyCollateral.getLotNumberPrefix());
		valuation2.setPropertyLotLocation(iPropertyCollateral.getPropertyLotLocation());
		valuation2.setOtherCity(iPropertyCollateral.getOtherCity());
		valuation2.setPropertyAddress(iPropertyCollateral.getPropertyAddress());
		valuation2.setPropertyAddress2(iPropertyCollateral.getPropertyAddress2());
		valuation2.setPropertyAddress3(iPropertyCollateral.getPropertyAddress3());
		valuation2.setPropertyAddress4(iPropertyCollateral.getPropertyAddress4());
		valuation2.setPropertyAddress5(iPropertyCollateral.getPropertyAddress5());
		valuation2.setPropertyAddress6(iPropertyCollateral.getPropertyAddress6());
		valuation2.setProjectName(iPropertyCollateral.getProjectName());
		valuation2.setValuationDateV2(iPropertyCollateral.getValuationDate_v2());
		valuation2.setValuatorCompanyV2(iPropertyCollateral.getValuatorCompany_v2());
		
		if(null!=iPropertyCollateral.getTotalPropertyAmount_v2())
		valuation2.setTotalPropertyAmontV2(iPropertyCollateral.getTotalPropertyAmount_v2().getAmountAsBigDecimal().toString());
		valuation2.setCategoryOfLandUseV2(iPropertyCollateral.getCategoryOfLandUse_v2());
		valuation2.setDeveloperNameV2(iPropertyCollateral.getDeveloperName_v2());
		valuation2.setCountryV2(iPropertyCollateral.getCountry_v2());
		valuation2.setRegionV2(iPropertyCollateral.getRegion_v2());
		valuation2.setStateV2(iPropertyCollateral.getLocationState_v2());
		valuation2.setNearestCityV2(iPropertyCollateral.getNearestCity_v2());
		valuation2.setPincodeV2(iPropertyCollateral.getPinCode_v2());
		valuation2.setLandAreaV2(iPropertyCollateral.getLandArea_v2());
		valuation2.setLandAreaUOMV2(iPropertyCollateral.getLandAreaUOM_v2());
		valuation2.setInSqfLandAreaV2(iPropertyCollateral.getInSqftLandArea_v2());
		valuation2.setBuildupAreaV2(iPropertyCollateral.getBuiltupArea_v2());
		valuation2.setBuildupAreaUOMV2(iPropertyCollateral.getBuiltupAreaUOM_v2());
		valuation2.setInSqfbuildupAreaV2(iPropertyCollateral.getInSqftBuiltupArea_v2());
		valuation2.setPropertyCompletionStatusV2(iPropertyCollateral.getPropertyCompletionStatus_v2());
		valuation2.setLandValueIRBV2(iPropertyCollateral.getLandValue_v2());
		valuation2.setBuildingValueIRBV2(iPropertyCollateral.getBuildingValue_v2());
		valuation2.setReconstructionValueIRBV2(iPropertyCollateral.getReconstructionValueOfTheBuilding_v2());
		valuation2.setIsPhyInspectionV2(iPropertyCollateral.getIsPhysicalInspection_v2());
		valuation2.setPhyInspectionFreqUnitV2(iPropertyCollateral.getPhysicalInspectionFreqUnit_v2());
		valuation2.setLastPhyInspectionDateV2(iPropertyCollateral.getLastPhysicalInspectDate_v2());
		valuation2.setNextPhyInspectionDateV2(iPropertyCollateral.getNextPhysicalInspectDate_v2());
		valuation2.setRemarksPropertyV2(iPropertyCollateral.getRemarksProperty_v2());
		valuation2.setEnvironmentRiskyStatus(iPropertyCollateral.getEnvRiskyStatus());
		valuation2.setEnvironmentRiskDate(iPropertyCollateral.getEnvRiskyDate());
		valuation2.setTsrDate(iPropertyCollateral.getTsrDate());
		valuation2.setNextTsrDate(iPropertyCollateral.getNextTsrDate());
		valuation2.setTsrFrequency(iPropertyCollateral.getTsrFrequency());
		valuation2.setConstitution(iPropertyCollateral.getConstitution());
		valuation2.setEnvironmentRiskRemark(iPropertyCollateral.getEnvRiskyRemarks());
		
		//set the new details legalAuditDate etc
		valuation2.setLegalAuditDate(iPropertyCollateral.getLegalAuditDate());
		valuation2.setInterveingPeriSerachDate(iPropertyCollateral.getInterveingPeriSearchDate());
		valuation2.setDateOfReceiptTitleDeed(iPropertyCollateral.getDateOfReceiptTitleDeed());
		valuation2.setValCreationDateV2(iPropertyCollateral.getValcreationdate_v2());
	
		valuation2.setPropertyUsage(iPropertyCollateral.getPropertyUsage());
		valuation2.setMortageRegisteredRef(iPropertyCollateral.getMortageRegisteredRef());
		valuation2.setDeferral(iPropertyCollateral.getDeferral() == null ? "off" : iPropertyCollateral.getDeferral().trim());
		valuation2.setDeferralId(iPropertyCollateral.getDeferralId());
		valuation2.setWaiver(iPropertyCollateral.getWaiver() == null ? "off" : iPropertyCollateral.getWaiver().trim());
		return valuation2; 
	}
	
	private PropertyValuation3 setValuation3(IPropertyCollateral iPropertyCollateral,String name) {
		PropertyValuation3 valuation3=new PropertyValuation3();
		if("Update".equals(name)){
			valuation3.setId(new Long(iPropertyCollateral.getVal3_id()));
		}
		valuation3.setPreviousMortCreationDate(iPropertyCollateral.getPreviousMortCreationDate());
		valuation3.setCollateralId(iPropertyCollateral.getCollateralID());
		valuation3.setSubTypeName(iPropertyCollateral.getCollateralSubType().getSubTypeName());
		valuation3.setTypeName(iPropertyCollateral.getCollateralType().getTypeName());
		valuation3.setSciRefNote(iPropertyCollateral.getSCIReferenceNote());
		valuation3.setSecurityCurrency(iPropertyCollateral.getCurrencyCode());
		valuation3.setSecPriority(iPropertyCollateral.getSecPriority());
		valuation3.setMonitorProcess(iPropertyCollateral.getMonitorProcess());
		valuation3.setMonitorFrequency(iPropertyCollateral.getMonitorFrequency());
		valuation3.setSecLocation(iPropertyCollateral.getCollateralLocation());
		valuation3.setSecOrganization(iPropertyCollateral.getSecurityOrganization());
		valuation3.setCollateralCode(iPropertyCollateral.getCollateralCode());
		valuation3.setRevalFrequency(iPropertyCollateral.getCommonRevalFreq());
		valuation3.setNextValuationDate(iPropertyCollateral.getNextValDate());
		valuation3.setChangeType(iPropertyCollateral.getTypeOfChange());
		valuation3.setOtherBankCharge(iPropertyCollateral.getOtherBankCharge());
		valuation3.setSecOwnership(iPropertyCollateral.getSecurityOwnership());
		valuation3.setOwnerOfProperty(iPropertyCollateral.getOwnerOfProperty());
		valuation3.setThirdPartyEntity(iPropertyCollateral.getThirdPartyEntity());
		valuation3.setCinThirdParty(iPropertyCollateral.getCinForThirdParty());
		valuation3.setCersaiTrxRefNo(iPropertyCollateral.getCersaiTransactionRefNumber());
		valuation3.setCersaiSecurityInterestId(iPropertyCollateral.getCersaiSecurityInterestId());
		valuation3.setCersaiAssetId(iPropertyCollateral.getCersaiAssetId());
		valuation3.setDateCersaiRegi(iPropertyCollateral.getDateOfCersaiRegisteration());
		valuation3.setCersaiRegDate(iPropertyCollateral.getCersiaRegistrationDate());
		valuation3.setCersaiId(iPropertyCollateral.getCersaiId());
		valuation3.setSaleDeedPurchaseDate(iPropertyCollateral.getSaleDeedPurchaseDate());
		valuation3.setThirdPartyAddress(iPropertyCollateral.getThirdPartyAddress());
		valuation3.setThirdPartyState(iPropertyCollateral.getThirdPartyState());
		valuation3.setThirdPartyCity(iPropertyCollateral.getThirdPartyCity());
		valuation3.setThirdPartyPincode(iPropertyCollateral.getThirdPartyPincode());
		valuation3.setPropertyId(iPropertyCollateral.getPropertyId());
		valuation3.setDescOfAsset(iPropertyCollateral.getDescription());
		valuation3.setPropertyType(iPropertyCollateral.getPropertyType());
		
		if(null!=iPropertyCollateral.getSalePurchaseValue())
		valuation3.setSalePurchaseValue(iPropertyCollateral.getSalePurchaseValue().getAmountAsBigDecimal().toString());
		
		valuation3.setSalePurchaseDate(iPropertyCollateral.getSalePurchaseDate());
		valuation3.setMortgageType(iPropertyCollateral.getTypeOfMargage());
		valuation3.setMortgagecretaedBy(iPropertyCollateral.getMorgageCreatedBy());
		valuation3.setDocumentReceived(iPropertyCollateral.getDocumentReceived());
		valuation3.setDocumentBlock(iPropertyCollateral.getDocumentReceived());
		valuation3.setBinNumber(iPropertyCollateral.getBinNumber());
		valuation3.setClaim(iPropertyCollateral.getClaim());
		valuation3.setClaimType(iPropertyCollateral.getClaimType());
		valuation3.setAdvocateLawyerName(iPropertyCollateral.getAdvocateLawyerName());
		valuation3.setDeveloperGroupCompany(iPropertyCollateral.getDevGrpCo());
		valuation3.setLotNo(iPropertyCollateral.getLotNo());
		valuation3.setLotNumberPrefix(iPropertyCollateral.getLotNumberPrefix());
		valuation3.setPropertyLotLocation(iPropertyCollateral.getPropertyLotLocation());
		valuation3.setOtherCity(iPropertyCollateral.getOtherCity());
		valuation3.setPropertyAddress(iPropertyCollateral.getPropertyAddress());
		valuation3.setPropertyAddress2(iPropertyCollateral.getPropertyAddress2());
		valuation3.setPropertyAddress3(iPropertyCollateral.getPropertyAddress3());
		valuation3.setPropertyAddress4(iPropertyCollateral.getPropertyAddress4());
		valuation3.setPropertyAddress5(iPropertyCollateral.getPropertyAddress5());
		valuation3.setPropertyAddress6(iPropertyCollateral.getPropertyAddress6());
		valuation3.setProjectName(iPropertyCollateral.getProjectName());
		valuation3.setValuationDateV3(iPropertyCollateral.getValuationDate_v3());
		valuation3.setValuatorCompanyV3(iPropertyCollateral.getValuatorCompany_v3());
		
		if(null!=iPropertyCollateral.getTotalPropertyAmount_v3())
		valuation3.setTotalPropertyAmontV3(iPropertyCollateral.getTotalPropertyAmount_v3().getAmountAsBigDecimal().toString());
		valuation3.setCategoryOfLandUseV3(iPropertyCollateral.getCategoryOfLandUse_v3());
		valuation3.setDeveloperNameV3(iPropertyCollateral.getDeveloperName_v3());
		valuation3.setCountryV3(iPropertyCollateral.getCountry_v3());
		valuation3.setRegionV3(iPropertyCollateral.getRegion_v3());
		valuation3.setStateV3(iPropertyCollateral.getLocationState_v3());
		valuation3.setNearestCityV3(iPropertyCollateral.getNearestCity_v3());
		valuation3.setPincodeV3(iPropertyCollateral.getPinCode_v3());
		valuation3.setLandAreaV3(iPropertyCollateral.getLandArea_v3());
		valuation3.setLandAreaUOMV3(iPropertyCollateral.getLandAreaUOM_v3());
		valuation3.setInSqfLandAreaV3(iPropertyCollateral.getInSqftLandArea_v3());
		valuation3.setBuildupAreaV3(iPropertyCollateral.getBuiltupArea_v3());
		valuation3.setBuildupAreaUOMV3(iPropertyCollateral.getBuiltupAreaUOM_v3());
		valuation3.setInSqfbuildupAreaV3(iPropertyCollateral.getInSqftBuiltupArea_v3());
		valuation3.setPropertyCompletionStatusV3(iPropertyCollateral.getPropertyCompletionStatus_v3());
		valuation3.setLandValueIRBV3(iPropertyCollateral.getLandValue_v3());
		valuation3.setBuildingValueIRBV3(iPropertyCollateral.getBuildingValue_v3());
		valuation3.setReconstructionValueIRBV3(iPropertyCollateral.getReconstructionValueOfTheBuilding_v3());
		valuation3.setIsPhyInspectionV3(iPropertyCollateral.getIsPhysicalInspection_v3());
		valuation3.setPhyInspectionFreqUnitV3(iPropertyCollateral.getPhysicalInspectionFreqUnit_v3());
		valuation3.setLastPhyInspectionDateV3(iPropertyCollateral.getLastPhysicalInspectDate_v3());
		valuation3.setNextPhyInspectionDateV3(iPropertyCollateral.getNextPhysicalInspectDate_v3());
		valuation3.setRemarksPropertyV3(iPropertyCollateral.getRemarksProperty_v3());
		valuation3.setEnvironmentRiskyStatus(iPropertyCollateral.getEnvRiskyStatus());
		valuation3.setEnvironmentRiskDate(iPropertyCollateral.getEnvRiskyDate());
		valuation3.setTsrDate(iPropertyCollateral.getTsrDate());
		valuation3.setNextTsrDate(iPropertyCollateral.getNextTsrDate());
		valuation3.setTsrFrequency(iPropertyCollateral.getTsrFrequency());
		valuation3.setConstitution(iPropertyCollateral.getConstitution());
		valuation3.setEnvironmentRiskRemark(iPropertyCollateral.getEnvRiskyRemarks());
		
		//set the new details legalAuditDate etc
		valuation3.setLegalAuditDate(iPropertyCollateral.getLegalAuditDate());
		valuation3.setInterveingPeriSerachDate(iPropertyCollateral.getInterveingPeriSearchDate());
		valuation3.setDateOfReceiptTitleDeed(iPropertyCollateral.getDateOfReceiptTitleDeed());
		valuation3.setValCreationDateV3(iPropertyCollateral.getValcreationdate_v3());
		valuation3.setPropertyUsage(iPropertyCollateral.getPropertyUsage());
		valuation3.setMortageRegisteredRef(iPropertyCollateral.getMortageRegisteredRef());
	
	
		return valuation3;
	}

	private void generateInsuranceCoverageNotification(ICollateral collateral, String calculatedDP) {
		BigDecimal totalInsurance= new BigDecimal(0);
		BigDecimal securityOMV= new BigDecimal(0);
		ICollateralProxy proxy = CollateralProxyFactory.getProxy();
		IEmailNotificationService service=(IEmailNotificationService)BeanHouse.get("emailNotificationService");
		ICustomerNotificationDetail noticationDetail=null;
		boolean isInsCovNotificationRequire=false;
		String customerName="";
		String customerID="";
		try {
			customerName = proxy.getCustomerNameByCollateralID(collateral.getCollateralID());
			customerID = proxy.getCustomerIDByCollateralID(collateral.getCollateralID());
		} catch (CollateralException e) {
			e.printStackTrace();
		}
		
		//Step1 : Retrieve the total insurance amount  
		if(collateral instanceof IGeneralCharge){
			IGeneralCharge gc= (IGeneralCharge)collateral;
			TreeMap sortedGCMap= new TreeMap();
			IGeneralChargeDetails[] gcDetList = gc.getGeneralChargeDetails();
			IGeneralChargeDetails gcDet=null;
			if(gcDetList!=null){
			for (int i = 0; i < gcDetList.length; i++) {
				gcDet = gcDetList[i];
				DefaultLogger.debug(this, "gcDet ================> "+gcDet);
				DefaultLogger.debug(this, "gcDet.getDueDate() ===> "+gcDet.getDueDate());
				sortedGCMap.put(gcDet.getDueDate(), gcDet);
			}
			}
			if(sortedGCMap.size()>0){
				IGeneralChargeDetails latestGCDet = (IGeneralChargeDetails)sortedGCMap.get(sortedGCMap.lastKey());
				IGeneralChargeStockDetails[] gcStockDetailsList = latestGCDet.getGeneralChargeStockDetails();
				IGeneralChargeStockDetails gcStockDet=null;
				if(gcStockDetailsList!=null) {
					for (int i = 0; i < gcStockDetailsList.length; i++) {
						gcStockDet = gcStockDetailsList[i];
						if("CurrentAsset".equals(gcStockDet.getStockType())&& "Y".equals(gcStockDet.getHasInsurance())){
							totalInsurance.add(new BigDecimal(gcStockDet.getInsuredAmount()));
							securityOMV.add(new BigDecimal(gcStockDet.getComponentAmount()));
							isInsCovNotificationRequire=true;
						}
					}
				}
			}
			//for Inadequate DP Notification
			BigDecimal releasedAmount=new BigDecimal(0);
			try {
				 ILimit limit = proxy.getReleasableAmountByCollateralID(collateral.getCollateralID());
				 if(limit!=null){
				 releasedAmount=new BigDecimal(limit.getTotalReleasedAmount());
				 }
			} catch (SearchDAOException e) {
				e.printStackTrace();
			} catch (CollateralException e) {
				e.printStackTrace();
			}
			if(calculatedDP!=null && releasedAmount.compareTo(new BigDecimal(calculatedDP))==1){
				noticationDetail= new OBCustomerNotificationDetail();
				noticationDetail.setPartyId(customerID);
				noticationDetail.setPartyName(customerName);
				noticationDetail.setDrawingPower(calculatedDP);
				noticationDetail.setReleasableAmount(releasedAmount.toString());
				DefaultLogger.debug(this, "Creating notification");
				IEmailNotification createNotification = service.createNotification("NOT0010", noticationDetail);
				DefaultLogger.debug(this, "Notification Created"+createNotification.getNotifcationId());
			}
			
		}else{
			if(collateral.getCMV()!=null){
				securityOMV=collateral.getCMV().getAmountAsBigDecimal();
				IInsurancePolicy[] insPolicyList = collateral.getInsurancePolicies();
				IInsurancePolicy insPolicy=null;
				for (int i = 0; i < insPolicyList.length; i++) {
					insPolicy = insPolicyList[i];
					if(null != insPolicy.getInsuredAmount()){
						totalInsurance=totalInsurance.add(insPolicy.getInsuredAmount().getAmountAsBigDecimal());
					}
					
					isInsCovNotificationRequire=true;
				}
			}
		}
		
		
		// Step3 Compare and generate notification
		if(isInsCovNotificationRequire){
			if(totalInsurance.compareTo(securityOMV)==-1){
					noticationDetail= new OBCustomerNotificationDetail();
					noticationDetail.setPartyId(customerID);
					noticationDetail.setPartyName(customerName);
					DefaultLogger.debug(this, "Creating notification");
					IEmailNotification createNotification = service.createNotification("NOT0006", noticationDetail);
					DefaultLogger.debug(this, "Notification Created"+createNotification.getNotifcationId());
			}
		}
	}
	
	
	public void setAllFixedFd(ICashDeposit fd) throws SearchDAOException { // New Receipt No
		String dep_amt=fd.getDepositAmount().getAmountAsBigDecimal().toString();
		String ver_date=null;
		String maker_date=null;
		String checker_date=null;
		String maker_id=null;
		String checker_id=null;
		String receiptNo = fd.getDepositRefNo(); 
		String SqlQuery = "select cd.CASH_DEPOSIT_ID "+
        " FROM CMS_LIMIT_SECURITY_MAP lsm,SCI_LSP_APPR_LMTS lmts,SCI_LSP_LMT_PROFILE pf,SCI_LE_SUB_PROFILE sp,CMS_CASH_DEPOSIT cd,cms_security sec "+      
        " where sp.CMS_LE_SUB_PROFILE_ID = pf.CMS_CUSTOMER_ID AND pf.CMS_LSP_LMT_PROFILE_ID  = lmts.CMS_LIMIT_PROFILE_ID "+
     				" AND lmts.CMS_LSP_APPR_LMTS_ID  = lsm.CMS_LSP_APPR_LMTS_ID	AND cd.CMS_COLLATERAL_ID (+)   = lsm.CMS_COLLATERAL_ID "+
     				" AND (lsm.UPDATE_STATUS_IND != 'D' OR lsm.UPDATE_STATUS_IND IS NULL)	AND (CD.STATUS = 'ACTIVE')	AND sp.status != 'INACTIVE' "+
     				" and lmts.cms_limit_status <> 'DELETED'and sec.cms_collateral_id = cd.cms_collateral_id "+
     				" AND lsm.CHARGE_ID            = (SELECT MAX(CHARGE_ID) FROM CMS_LIMIT_SECURITY_MAP MAPS1 WHERE MAPS1.CMS_COLLATERAL_ID=CD.CMS_COLLATERAL_ID and MAPS1.CMS_LSP_APPR_LMTS_ID = lsm.CMS_LSP_APPR_LMTS_ID) "+ 
     				" AND lsm.UPDATE_STATUS_IND = 'I' AND cd.status <> 'DELETED'	AND cd.ACTIVE ='active' and cd.deposit_reference_number = '"+receiptNo+"'";

		
		if(null!=fd.getVerificationDate())
			ver_date=formatDate(fd.getVerificationDate().toString());
		else
			ver_date = "";
		if(null!=fd.getMaker_date())
			maker_date=formatDate(fd.getMaker_date().toString());
		else
			maker_date = "";
		if(null!=fd.getChecker_date())
			checker_date=formatDate(fd.getChecker_date().toString());
		else
			checker_date = "";
		if(null!=fd.getMaker_id())
			maker_id=fd.getMaker_id().toString();
		else
			maker_id = "";
		if(null!=fd.getChecker_id())
			checker_id=fd.getChecker_id().toString();
		else
			checker_id = "";
		String sql="update CMS_CASH_DEPOSIT set deposit_amount = "+dep_amt+",issue_date = '"+formatDate(fd.getIssueDate().toString())+"',deposit_maturity_date = '"+formatDate(fd.getDepositMaturityDate().toString())+"',verification_date = '"+ver_date+"',deposit_interest_rate="+fd.getDepositeInterestRate()+", MAKER_ID = '"+maker_id+"'"+", CHECKER_ID = '"+checker_id+"', MAKER_DATE = '"+maker_date+"'"+", CHECKER_DATE = '"+checker_date+"'";
		sql=sql+" where deposit_reference_number = '"+fd.getDepositRefNo()+"' and status = 'ACTIVE'" + " and cash_deposit_id in("+SqlQuery+")";
		DBUtil dbUtil = null;
		
		try {
			dbUtil=new DBUtil();
			dbUtil.setSQL(sql);
			int rs=dbUtil.executeUpdate();	
			dbUtil.commit();
		} catch (DBConnectionException e) {
			e.printStackTrace();
		} catch (NoSQLStatementException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finalize(dbUtil);
	}
	public String formatDate(String d){
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss.S aa");
		SimpleDateFormat sdf2 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
		Date d1=null;
		try{
		d1=sdf2.parse(d);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return sdf1.format(d1);
	}
	private void finalize(DBUtil dbUtil) {
		try {
			
			if (dbUtil != null) {
				dbUtil.close();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void setAllFixedFdByReceiptNo(ICashDeposit fd,String receiptNo) throws SearchDAOException { //update existing Receipt no.
		String dep_amt=fd.getDepositAmount().getAmountAsBigDecimal().toString();
		String ver_date=null;
		String maker_date=null;
		String checker_date=null;
		String maker_id=null;
		String checker_id=null;
		//String receiptNo = fd.getDepositRefNo(); 
		String SqlQuery = "select cd.CASH_DEPOSIT_ID "+
        " FROM CMS_LIMIT_SECURITY_MAP lsm,SCI_LSP_APPR_LMTS lmts,SCI_LSP_LMT_PROFILE pf,SCI_LE_SUB_PROFILE sp,CMS_CASH_DEPOSIT cd,cms_security sec "+      
        " where sp.CMS_LE_SUB_PROFILE_ID = pf.CMS_CUSTOMER_ID AND pf.CMS_LSP_LMT_PROFILE_ID  = lmts.CMS_LIMIT_PROFILE_ID "+
     				" AND lmts.CMS_LSP_APPR_LMTS_ID  = lsm.CMS_LSP_APPR_LMTS_ID	AND cd.CMS_COLLATERAL_ID (+)   = lsm.CMS_COLLATERAL_ID "+
     				" AND (lsm.UPDATE_STATUS_IND != 'D' OR lsm.UPDATE_STATUS_IND IS NULL)	AND (CD.STATUS = 'ACTIVE')	AND sp.status != 'INACTIVE' "+
     				" and lmts.cms_limit_status <> 'DELETED'and sec.cms_collateral_id = cd.cms_collateral_id "+
     				" AND lsm.CHARGE_ID            = (SELECT MAX(CHARGE_ID) FROM CMS_LIMIT_SECURITY_MAP MAPS1 WHERE MAPS1.CMS_COLLATERAL_ID=CD.CMS_COLLATERAL_ID and MAPS1.CMS_LSP_APPR_LMTS_ID = lsm.CMS_LSP_APPR_LMTS_ID) "+ 
     				" AND lsm.UPDATE_STATUS_IND = 'I' AND cd.status <> 'DELETED'	AND cd.ACTIVE ='active' and cd.deposit_reference_number = '"+receiptNo+"'";

		
		if(null!=fd.getVerificationDate())
			ver_date=formatDate(fd.getVerificationDate().toString());
		else
			ver_date = "";
		if(null!=fd.getMaker_date())
			maker_date=formatDate(fd.getMaker_date().toString());
		else
			maker_date = "";
		if(null!=fd.getChecker_date())
			checker_date=formatDate(fd.getChecker_date().toString());
		else
			checker_date = "";
		if(null!=fd.getMaker_id())
			maker_id=fd.getMaker_id().toString();
		else
			maker_id = "";
		if(null!=fd.getChecker_id())
			checker_id=fd.getChecker_id().toString();
		else
			checker_id = "";
		String sql="update CMS_CASH_DEPOSIT set deposit_reference_number = '"+fd.getDepositRefNo()+"', deposit_amount = "+dep_amt
		+", issue_date = '"+formatDate(fd.getIssueDate().toString())+"',deposit_maturity_date = '"+formatDate(fd.getDepositMaturityDate().toString())+"',verification_date = '"+ver_date
		+"',deposit_interest_rate="+fd.getDepositeInterestRate()+", MAKER_ID = '"+maker_id+"'"+", CHECKER_ID = '"+checker_id+"', MAKER_DATE = '"+maker_date+"'"+", CHECKER_DATE = '"+checker_date+"'";
		sql=sql+" where deposit_reference_number = '"+receiptNo+"' and status = 'ACTIVE'" + " and cash_deposit_id in("+SqlQuery+")";
		DBUtil dbUtil = null;
//		ResultSet rs=null;
		try {
			dbUtil=new DBUtil();
			dbUtil.setSQL(sql);
			int rs=dbUtil.executeUpdate();	
			dbUtil.commit();	
		} catch (DBConnectionException e) {
			e.printStackTrace();
		} catch (NoSQLStatementException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finalize(dbUtil);
	}

}
