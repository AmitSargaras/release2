/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/SubmitCollateralCommand.java,v 1.47.4.1 2006/12/14 12:19:04 jychong Exp $
 */

package com.integrosys.cms.ui.collateral;



import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.IInsurancePolicy;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeDetails;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeStockDetails;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.OBGeneralChargeDetails;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargeaircraft.ISpecificChargeAircraft;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargeplant.ISpecificChargePlant;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.collateral.trx.OBCollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.collateral.secapportion.SecApportionmentValidator;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.limitsOfAuthorityMaster.LimitsOfAuthorityMasterHelper;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.user.app.bus.ICommonUser;



/**
 * @author jychong
 * @since 2006/12/14 12:19:04
 */
public class SubmitCollateralCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "form.collateralObject", "com.integrosys.cms.app.collateral.bus.ICollateral", FORM_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "flag1", "java.lang.String", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "isSSC", "java.lang.String", REQUEST_SCOPE },
				{ "dueDate", "java.lang.String", SERVICE_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ "limitMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "limitDtlList", "java.util.List", SERVICE_SCOPE },
				{ "skipPraChecking", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "totalLonable", "java.lang.String", REQUEST_SCOPE },
				{ "migrationFlag", "java.lang.String", REQUEST_SCOPE },
				

				{ "remarkByMaker", "java.lang.String", REQUEST_SCOPE },
				{ "totalLonable", "java.lang.String", REQUEST_SCOPE },
				{ "migrationFlag", "java.lang.String", REQUEST_SCOPE },
				

		});
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { 
			{ "request.ITrxValue", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue",REQUEST_SCOPE }, 
			{ "statementName",  "java.util.List", SERVICE_SCOPE },		
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		ICollateral iColObj = (ICollateral) map.get("form.collateralObject");
		ICollateralTrxValue trxValue = (ICollateralTrxValue) map.get("serviceColObj");
		ICommonUser curUser =  (ICommonUser)map.get(IGlobalConstant.USER);

		String calculatedDP = null;
		DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");




		
		if (iColObj instanceof IGeneralCharge)
		{
		IGeneralCharge newCollateral = (IGeneralCharge) trxValue.getStagingCollateral();
		HashMap distinctLocation=new HashMap();
		IGeneralChargeDetails[] existingGeneralChargeDetails = null;
		if(newCollateral!=null){
		 existingGeneralChargeDetails = newCollateral.getGeneralChargeDetails();
		}
		IGeneralChargeDetails existingChargeDetails;
		
		IGeneralChargeStockDetails[] existingGeneralChargeStockDetails;
		String[] splittArray = null;
		String dueDate = (String) map.get("dueDate");
		String stockdocMonth = (String) map.get("stockdocMonth");
		String stockdocYear = (String) map.get("stockdocYear");
		//String fundedShare = (String) map.get("fundedShare");
		
		String fundedShare = (String) map.get("dpShare");

		String remarkByMaker = (String) map.get("remarkByMaker");
		String totalLonable = (String) map.get("totalLonable");
		String migrationFlag = (String) map.get("migrationFlag");
		if(null==totalLonable ||  "".equals(totalLonable)) {
			totalLonable="0";
		}
		calculatedDP = (String) map.get("calculatedDP");
		//Start Santosh 
		String dpCalculateManually = (String) map.get("dpCalculateManually");
		//End Santosh
		//Phase 3 CR:comma separated
		calculatedDP=UIUtil.removeComma(calculatedDP);
		totalLonable=UIUtil.removeComma(totalLonable);
		String selectedDueDate = "";
		 String selectedDocCode = "";
		 Date date = new Date();
		 System.out.println("SubmitCollateralComand.java=>dueDate=>"+dueDate+"*****");
		 if (null!=dueDate && !"".equalsIgnoreCase(dueDate)){
			 if(dueDate.equalsIgnoreCase("null")) {
				 dueDate = "";
			 }
			 else {
				 if(dueDate.contains(",")){
	         splittArray = dueDate.split(",");
	          selectedDueDate = (String) splittArray[0];
	          selectedDocCode = (String) splittArray[1];
				 }
			 }
	    }
		 //Start IRB Santosh
		 String statementName ="";
		 ICollateralProxy collateralProxy = CollateralProxyFactory.getProxy();
			try {
				statementName = collateralProxy.getStatementNameByDocCode(selectedDocCode);
			} catch (SearchDAOException e1) {
				e1.printStackTrace();
			} catch (CollateralException e1) {
				e1.printStackTrace();
			}
			result.put("statementName", statementName);
			 //End IRB Santosh
			
		 if(null!=iColObj){
				DefaultLogger.debug(this, "Collateral ID:"+iColObj.getCollateralID());
			}
		 
		 
			IGeneralChargeDetails existingChargeDetails2=null;
			IGeneralChargeDetails[] existingGeneralChargeDetails2=null;
			if(existingGeneralChargeDetails!=null){
				existingGeneralChargeDetails2 = new OBGeneralChargeDetails[existingGeneralChargeDetails.length+1];
			}
			
		 if(existingGeneralChargeDetails!=null){
				for (int i = 0; i < existingGeneralChargeDetails.length; i++) {
					 existingChargeDetails = existingGeneralChargeDetails[i];
					  selectedDueDate = "";
			          selectedDocCode = "";
					 if(null != existingChargeDetails) {
						 if("PENDING".equals(existingChargeDetails.getStatus())) {
							 selectedDueDate = df.format(existingChargeDetails.getDueDate());
							 selectedDocCode = existingChargeDetails.getDocCode();
							 System.out.println("SubmitCollateralCommand.java=>existingChargeDetails.getStatus()=>"+existingChargeDetails.getStatus()+"**selectedDueDate=>"+selectedDueDate+"**selectedDocCode=>"+selectedDocCode);
						 }
					 }
					 
//					if(existingChargeDetails!=null && existingChargeDetails.getDueDate().equals(dueDate)){
					if(existingChargeDetails!=null && null!=  existingChargeDetails.getDocCode() && existingChargeDetails.getDocCode().equals(selectedDocCode)){
						existingGeneralChargeStockDetails = existingChargeDetails.getGeneralChargeStockDetails();
					if(existingGeneralChargeStockDetails!=null) {	
						for (int j = 0; j < existingGeneralChargeStockDetails.length; j++) {
							IGeneralChargeStockDetails existingStockDetails = existingGeneralChargeStockDetails[j];
							double componentAmount = 0;
							double margin = 0;
							//Uma: Prod: loanable amount issue
							System.out.println("Submitcollateralcommand.java=>existingStockDetails.getComponentAmount()=>"+existingStockDetails.getComponentAmount()+"****existingStockDetails.getMargin()=>"+existingStockDetails.getMargin());
							if(null != existingStockDetails.getComponentAmount() && !"".equals(existingStockDetails.getComponentAmount())) {
								componentAmount= Double.parseDouble(existingStockDetails.getComponentAmount());
							}
							if(null != existingStockDetails.getMargin() && !"".equals(existingStockDetails.getMargin())) {
								margin= Double.parseDouble(existingStockDetails.getMargin());
							}
							System.out.println("Submitcollateralcommand.java=>componentAmount)=>"+componentAmount+"****margin=>"+margin);
							double lonable=getLonableAmount(componentAmount,margin);
							existingStockDetails.setLonable(BigDecimal.valueOf(lonable).toPlainString());
							 
							//Uma: Prod: loanable amount issue
							
							if(!distinctLocation.containsKey(Long.toString(existingStockDetails.getLocationId()))){
								distinctLocation.put(Long.toString(existingStockDetails.getLocationId()),Long.toString(existingStockDetails.getLocationId()));
							}
						}
					}	
						existingChargeDetails.setStatus(IGeneralChargeDetails.STATUS_PENDING);
						//existingChargeDetails.setFundedShare(fundedShare);
						
						//Uma Khot:Cam upload and Dp field calculation CR
						/*existingChargeDetails.setDpShare(fundedShare);
						existingChargeDetails.setCalculatedDP(calculatedDP); 
						//Start Santosh 
						existingChargeDetails.setDpCalculateManually(dpCalculateManually);
						//End Santosh
						existingChargeDetails.setStockdocMonth(stockdocMonth);
						existingChargeDetails.setStockdocYear(stockdocYear);
						existingChargeDetails.setRemarkByMaker(remarkByMaker);
						existingChargeDetails.setTotalLoanable(totalLonable);*/
						existingChargeDetails.setLastUpdatedBy(curUser.getLoginID());
						existingChargeDetails.setLastUpdatedOn(date);
						existingChargeDetails.setMigrationFlag_DP_CR(migrationFlag);
						//break;
					}
					
					//IRb Santosh
					else if(i==(existingGeneralChargeDetails.length-1)&& null!=selectedDueDate&& !"".equals(selectedDueDate)){
						existingChargeDetails2 = new OBGeneralChargeDetails();
						existingChargeDetails2.setStatus(IGeneralChargeDetails.STATUS_PENDING);
						existingChargeDetails2.setDueDate(DateUtil.convertDate(selectedDueDate));
						existingChargeDetails2.setDpShare(fundedShare);
						existingChargeDetails2.setCalculatedDP(calculatedDP);
						existingChargeDetails2.setLastUpdatedBy(curUser.getLoginID());
						existingChargeDetails2.setLastUpdatedOn(date);
						existingChargeDetails2.setDpCalculateManually(dpCalculateManually);
						existingChargeDetails2.setDocCode(selectedDocCode);
						existingChargeDetails2.setStockdocMonth(stockdocMonth);
						existingChargeDetails2.setStockdocYear(stockdocYear);
						existingChargeDetails2.setRemarkByMaker(remarkByMaker);
						existingChargeDetails2.setTotalLoanable(totalLonable);
						existingChargeDetails2.setMigrationFlag_DP_CR(migrationFlag);
					}
				}
				
				if(null!=existingChargeDetails2){
				//existingGeneralChargeDetails[existingGeneralChargeDetails.length]=existingChargeDetails2;
				existingGeneralChargeDetails2[0]=existingChargeDetails2;	
				
				for (int i = 0; i < existingGeneralChargeDetails.length; i++) {
					 existingChargeDetails = existingGeneralChargeDetails[i];
					 existingGeneralChargeDetails2[i+1]=existingChargeDetails;	
				}
				
				newCollateral.setGeneralChargeDetails(existingGeneralChargeDetails2);
				}
				
			}
		 else {
			 
			 if(null != selectedDueDate && !"".equals(selectedDueDate) && null != selectedDocCode && !"".equals(selectedDocCode)){
				 
				//start santosh
				 existingGeneralChargeDetails=new OBGeneralChargeDetails[1];
					existingChargeDetails = new OBGeneralChargeDetails();
					
					existingChargeDetails.setDueDate(DateUtil.convertDate(selectedDueDate));
					existingChargeDetails.setStatus(IGeneralChargeDetails.STATUS_PENDING);
					existingChargeDetails.setDpShare(fundedShare);
					existingChargeDetails.setCalculatedDP(calculatedDP);
					existingChargeDetails.setDpCalculateManually(dpCalculateManually);
					existingChargeDetails.setLastUpdatedBy(curUser.getLoginID());
					existingChargeDetails.setLastUpdatedOn(date);
					existingChargeDetails.setDocCode(selectedDocCode);
					existingChargeDetails.setStockdocMonth(stockdocMonth);
					existingChargeDetails.setStockdocYear(stockdocYear);
					existingChargeDetails.setRemarkByMaker(remarkByMaker);
					existingChargeDetails.setTotalLoanable(totalLonable);
					
					existingChargeDetails.setMigrationFlag_DP_CR(migrationFlag);
					
					existingGeneralChargeDetails[0]=existingChargeDetails;
					newCollateral.setGeneralChargeDetails(existingGeneralChargeDetails);
			 }
			
			
		 }
		 
		}
		 //===========================================================================

		if (iColObj instanceof ISpecificChargePlant || iColObj instanceof ISpecificChargeAircraft)
		{
			Date date = new Date();
			IInsurancePolicy[] policies = trxValue.getStagingCollateral().getInsurancePolicies();
			for(int i = 0;i < policies.length;i++ ){
				policies[i].setLastUpdatedBy(curUser.getLoginID());
				policies[i].setLastUpdatedOn(date);
			}
			if(policies.length > 0){
				trxValue.getStagingCollateral().setInsurancePolicies(policies);
			}
			
		}
		
		ICollateralTrxValue returnValue = new OBCollateralTrxValue();
		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");

		if (iColObj.getInstrumentArray() != null) {
			DefaultLogger.debug(this, "No of instruments : " + iColObj.getInstrumentArray().length);
		}

		boolean isSSC = "true".equals((String) map.get("isSSC"));
		
		iColObj.setCollateralLimits(null);

		
		
		 if (trxValue != null) {
	          
			      // iColObj = trxValue.getStagingCollateral();
	            	
	            	iColObj.setCollateralLocation(trxValue.getCollateral().getCollateralLocation());
	            	trxValue.setStagingCollateral(iColObj); 
	                result.put("form.collateralObject", trxValue.getStagingCollateral());
	            
	        }
		
		
		
		
		
		try {
			boolean isCheckListCompleted = false;
			boolean isSecurityNoChecklist = true;
			boolean isSecurityLocationChange = false;
			boolean isSecurityOrganizationChange = false;

			if (trxValue.getCollateral() != null) {
				/*Not used below hence commenting
				 * Commented by Anil ============Start==================
				 * String originalLocation = trxValue.getCollateral().getCollateralLocation();
				if (!iColObj.getCollateralLocation().equals(originalLocation)) {
					isSecurityLocationChange = true;
				}

				String securityOrganization = trxValue.getCollateral().getSecurityOrganization();
				if ((securityOrganization != null) && (iColObj.getSecurityOrganization() != null)
						&& !iColObj.getSecurityOrganization().equals(securityOrganization)) {
					isSecurityOrganizationChange = true;
				}
				Commented by Anil ============Ends==================
				*/

				try {
					isSecurityNoChecklist = CollateralProxyFactory.getProxy().isCollateralNoChecklist(
							trxValue.getCollateral().getCollateralID());

					DefaultLogger.debug(this, "security location change - isSecurityNoChecklist: "
							+ isSecurityNoChecklist);

				}
				catch (CollateralException e) {
					DefaultLogger.warn(this, "Getting isSecurityNoChecklist throws Exception " + e);
				}

				try {
					isCheckListCompleted = CollateralProxyFactory.getProxy().isCollateralCheckListCompleted(
							trxValue.getCollateral());
				}
				catch (Exception e) {
					DefaultLogger.warn(this, "Failed to get collateral check complete status" + e);
				}

				/*
				if (isSecurityLocationChange && !isSecurityNoChecklist) {
					exceptionMap.put("collateralLoc", new ActionMessage("error.collateral.securityLocation"));
				}

				if (isSecurityOrganizationChange && !isSecurityNoChecklist) {
					exceptionMap
							.put("securityOrganization", new ActionMessage("error.collateral.securityOrganization"));
				}
				*/
			}

			if (!isSSC && (iColObj.getLimitCharges() != null) && (iColObj.getLimitCharges().length > 0)) {
				SecuritySubTypeUtil.validateSetLimitChargeDetails(iColObj, isCheckListCompleted, exceptionMap);
			}

			if (!isSSC) {
				SecuritySubTypeUtil.setCMSCurrency(iColObj);
			}

			List limitDtl = (List) map.get("limitDtlList");

			SecuritySubTypeUtil.validateLegalEnforce(iColObj, isCheckListCompleted, exceptionMap);

			if (!SecApportionmentValidator.validateApportionments(limitDtl, trxValue)
					&& ((map.get("skipPraChecking") == null) || "".equals(map.get("skipPraChecking")))) {
				exceptionMap.put("apportionmentError", new ActionMessage("error.apportionment.prAmountExceed"));

				if (iColObj instanceof IGeneralCharge) {
					boolean isCPC = false;
					ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
					ITeam userTeam = (ITeam) map.get(IGlobalConstant.USER_TEAM);
					TOP_LOOP: for (int i = 0; i < userTeam.getTeamMemberships().length; i++) {
						for (int j = 0; j < userTeam.getTeamMemberships()[i].getTeamMembers().length; j++) {
							if (userTeam.getTeamMemberships()[i].getTeamMembers()[j].getTeamMemberUser().getUserID() == user
									.getUserID()) {
								if (userTeam.getTeamMemberships()[i].getTeamTypeMembership().getMembershipID() == ICMSConstant.TEAM_TYPE_CPC_MAKER) {
									isCPC = true;
									DefaultLogger.debug("SubmitCollateralCommand.doExecute ", "User is cpc maker...");
									break TOP_LOOP;
								}
							}
						}
					}
					DefaultLogger.debug(this, "isCPC " + isCPC);
					if (!isCPC) {
						result.put("forwardPage", CollateralAction.EVENT_READ);
					}
					else {
						result.put("forwardPage", CollateralAction.EVENT_PREPARE_UPDATE);
					}
				}
			}

			CollateralStpValidatorFactory factory = (CollateralStpValidatorFactory) BeanHouse
					.get("collateralStpValidatorFactory");
			Map context = new HashMap();
			/*
			 * set CMV to staging if actual got value but staging blank, used
			 * for pre Stp valuation validation
			 */
			if (trxValue.getCollateral() != null
					&& trxValue.getCollateral().getCMV() != null
					&& (trxValue.getStagingCollateral().getCMV() == null || trxValue.getStagingCollateral().getCMV()
							.getAmount() < 0)) {
				//changing condition returnValue.getStagingCollateral().getCMV().getAmount() <= 0 as to update  CMV value to 0
				trxValue.getStagingCollateral().setCMV(trxValue.getCollateral().getCMV());
			}
			context.put(CollateralStpValidator.COL_OB, trxValue.getStagingCollateral());
			context.put(CollateralStpValidator.TRX_STATUS, trxValue.getStatus());
			context.put(CollateralStpValidator.COL_TRX_VALUE, trxValue);
			CollateralStpValidator validator = factory.getCollateralStpValidator(trxValue.getStagingCollateral());
			ActionErrors actionErrors = validator.validateAndAccumulate(context);

			/*if(iColObj.getInsurancePolicies().length == 0){
				exceptionMap.put("insuranceError", new ActionMessage("Please add insurance"));
			}*/
			
			if (!actionErrors.isEmpty()) {
				Iterator errKeySet = actionErrors.properties();

				while (errKeySet.hasNext()) {
					String errKey = (String) errKeySet.next();
					Iterator errMsgSet = actionErrors.get(errKey);

				//	 only retrieve the first action message 
					ActionMessage errMsg = (ActionMessage) errMsgSet.next();

					Object[] errMsgValue = null;
					if (errMsg.getValues() != null && errMsg.getValues().length > 0) {
						errMsgValue = (Object[]) errMsg.getValues();
						if (errMsgValue.length == 2) {
							exceptionMap.put(errKey, new ActionMessage(errMsg.getKey(), errMsgValue[0].toString(),
									errMsgValue[1].toString()));
						}
						else {
							exceptionMap.put(errKey, new ActionMessage(errMsg.getKey(), errMsgValue[0].toString()));
						}
					}
					else {
						exceptionMap.put(errKey, new ActionMessage(errMsg.getKey()));
					}
				}
				// temp.put(ICommonEventConstant.MESSAGE_LIST, actionErrors);
			}

			if (exceptionMap.size() == 0) {
				CollateralUiUtil.setTrxLocation(ctx, iColObj);
				
				String minGrade = LimitsOfAuthorityMasterHelper.getMinimumEmployeeGradeForLOA(trxValue, null, calculatedDP);
				trxValue.setMinEmployeeGrade(minGrade);

				DefaultLogger.debug(this, "============= transaction id ============> " + trxValue.getTransactionID());
				SecuritySubTypeUtil.setCollateralSubTypeCode(trxValue, iColObj);

				String flag1 = (String) map.get("flag1");
				if (flag1 == null) {
					// Existing rejected/draft transaction
					ITrxContext oldCtx = trxValue.getTrxContext();
					if (oldCtx != null) {
						ctx.setCustomer(oldCtx.getCustomer());
						ctx.setLimitProfile(oldCtx.getLimitProfile());
					}

					returnValue = CollateralProxyFactory.getProxy().makerUpdateCollateral(ctx, trxValue, iColObj);
				}
				else {
					if (flag1.equals(CategoryCodeConstant.SEARCH_BY_COLLATERAL)) {
						// search by collateral will not have customer trx
						// context
						ctx.setCollateralID(trxValue.getCollateral().getCollateralID());
						returnValue = CollateralProxyFactory.getProxy().makerUpdateCollateral(ctx, trxValue, iColObj);
					}
					else {
						// search by customer getting the customer trx context
						// we are setting collateral id in ctx for HDFC bank to show customer and security type and sub type 
						// in to do and to track pages.
						
						ctx.setCollateralID(trxValue.getCollateral().getCollateralID());
						returnValue = CollateralProxyFactory.getProxy().makerUpdateCollateral(ctx, trxValue, iColObj);
					}
				}

				CollateralStpValidatorFactory stpValidatorFactory = (CollateralStpValidatorFactory) BeanHouse
						.get("collateralStpValidatorFactory");
				CollateralStpValidator stpValidator = stpValidatorFactory.getCollateralStpValidator(iColObj);
				context = new HashMap();
				/*
				 * set CMV to staging if actual got value but staging blank,
				 * used for pre Stp valuation validation
				 */
				if (returnValue.getCollateral() != null
						&& returnValue.getCollateral().getCMV() != null
						&& (returnValue.getStagingCollateral().getCMV() == null || returnValue.getStagingCollateral()
								.getCMV().getAmount() <= 0)) {
					returnValue.getStagingCollateral().setCMV(returnValue.getCollateral().getCMV());
				}
				context.put(CollateralStpValidator.COL_OB, returnValue.getStagingCollateral());
				context.put(CollateralStpValidator.TRX_STATUS, returnValue.getStatus());
				context.put(CollateralStpValidator.COL_TRX_VALUE, returnValue);
				boolean isStpReady = stpValidator.validate(context);
				CollateralProxyFactory.getProxy().updateOrInsertStpAllowedIndicator(returnValue, isStpReady);

				result.put("request.ITrxValue", returnValue);
			}
		}
		catch (CollateralException ex) {
			throw new CommandProcessingException("failed to submit collateral", ex);
		}

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	//temp created
	private double getLonableAmount(double componentAmount, double margin) {
		double lonable=0.00;
	    lonable=componentAmount-((componentAmount * margin)/100);
	    DecimalFormat df =new DecimalFormat("##0.00");
		df.format(lonable);
	    DefaultLogger.debug(this,"componentAmount:"+BigDecimal.valueOf(componentAmount).toPlainString()+" margin:"+BigDecimal.valueOf(margin).toPlainString()+" lonable:"+BigDecimal.valueOf(lonable).toPlainString());
		return lonable;
	}
}
