/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/UpdateCollateralCommand.java,v 1.41.10.1 2006/12/14 12:19:04 jychong Exp $
 */

package com.integrosys.cms.ui.collateral;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeDetails;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeStockDetails;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.collateral.trx.OBCollateralTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.limitsOfAuthorityMaster.LimitsOfAuthorityMasterHelper;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * @author $Author: jychong $<br>
 * @version $Revision: 1.41.10.1 $
 * @since $Date: 2006/12/14 12:19:04 $ Tag: $Name: DEV_20060126_B286V1 $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jul 2, 2003 Time: 1:26:03 PM
 * To change this template use Options | File Templates.
 */
public class UpdateCollateralCommand extends AbstractCommand {

    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"form.collateralObject", "com.integrosys.cms.app.collateral.bus.ICollateral", FORM_SCOPE},
                {"serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE},
                {"flag1", "java.lang.String", SERVICE_SCOPE},
                { "dueDate", "java.lang.String", SERVICE_SCOPE },
                { "stockdocMonth", "java.lang.String", REQUEST_SCOPE },
                { "stockdocYear", "java.lang.String", REQUEST_SCOPE },
                { "calculatedDP", "java.lang.String", REQUEST_SCOPE },
//				{ "fundedShare", "java.lang.String", REQUEST_SCOPE },
                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
                {"isSSC", "java.lang.String", REQUEST_SCOPE},
                { "dpCalculateManually", "java.lang.String", REQUEST_SCOPE },
                {"remarkByMaker", "java.lang.String", REQUEST_SCOPE},
                {"totalLonable", "java.lang.String", REQUEST_SCOPE},
                
                {"migrationFlag", "java.lang.String", REQUEST_SCOPE},

                { "dpShare", "java.lang.String", REQUEST_SCOPE },});
    }

    /**
     * Defines an two dimensional array with the result list to be expected as a
     * result from the doExecute method using a HashMap syntax for the array is
     * (HashMapkey,classname,scope) The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getResultDescriptor() {
        return (new String[][]{{"request.ITrxValue", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue",
                REQUEST_SCOPE},
        	{ "stockdocMonth", "java.lang.String", SERVICE_SCOPE },
            { "stockdocYear", "java.lang.String", SERVICE_SCOPE },
            { "remarkByMaker", "java.lang.String", SERVICE_SCOPE },
            { "totalLonable", "java.lang.String", SERVICE_SCOPE },
            { "dpShare", "java.lang.String", SERVICE_SCOPE },
            { "migrationFlag", "java.lang.String", SERVICE_SCOPE },
            { "dpCalculateManually", "java.lang.String", SERVICE_SCOPE },
            });
    }

    /**
     * This method does the Business operations with the HashMap and put the
     * results back into the HashMap.Here reading for Company Borrower is done.
     *
     * @param map is of type HashMap
     * @return HashMap with the Result
     * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
     *          on errors
     * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
     *          on errors
     */
    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

        HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();

        ICollateral iColObj = (ICollateral) map.get("form.collateralObject");
        ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
        OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
        ICommonUser curUser =  (ICommonUser)map.get(IGlobalConstant.USER);
        String calculatedDP = null;
        
        Date date = new Date();
      //==================================================added by bharat===========================================
        if (iColObj instanceof IGeneralCharge)
    	{
		IGeneralCharge newCollateral = (IGeneralCharge) itrxValue.getStagingCollateral();
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
		System.out.println("UpdateCollateral = >stockdocMonth=>"+stockdocMonth+" stockdocYear=>"+stockdocYear);
		result.put("stockdocMonth", stockdocMonth);
		result.put("stockdocYear", stockdocYear);
		//String fundedShare = (String) map.get("fundedShare");
		
		//Uma Khot:Cam upload and Dp field calculation CR
		String fundedShare = (String) map.get("dpShare");

		String remarkByMaker = (String) map.get("remarkByMaker");
		
		String totalLonable = (String) map.get("totalLonable");
		
		String migrationFlag = (String) map.get("migrationFlag");
		
		if(null==totalLonable ||  "".equals(totalLonable)) {
			totalLonable="0";
		}
		calculatedDP = (String) map.get("calculatedDP");

		String dpCalculateManually=(String) map.get("dpCalculateManually");
		
		String selectedDueDate = "";
		 String selectedDocCode = "";
		
		 if (dueDate != null && !"".equalsIgnoreCase(dueDate)){
	         splittArray = dueDate.split(",");
	          selectedDueDate = (String) splittArray[0];
	          selectedDocCode = (String) splittArray[1];
	    }
		 if(existingGeneralChargeDetails!=null){
				for (int i = 0; i < existingGeneralChargeDetails.length; i++) {
					 existingChargeDetails = existingGeneralChargeDetails[i];
//					if(existingChargeDetails!=null && existingChargeDetails.getDueDate().equals(dueDate)){
					if(existingChargeDetails!=null && null != existingChargeDetails.getDocCode()  && existingChargeDetails.getDocCode().equals(selectedDocCode)){
						existingGeneralChargeStockDetails = existingChargeDetails.getGeneralChargeStockDetails();
						for (int j = 0; j < existingGeneralChargeStockDetails.length; j++) {
							IGeneralChargeStockDetails existingStockDetails = existingGeneralChargeStockDetails[j];
							if(!distinctLocation.containsKey(Long.toString(existingStockDetails.getLocationId()))){
								distinctLocation.put(Long.toString(existingStockDetails.getLocationId()),Long.toString(existingStockDetails.getLocationId()));
							}
						}
						existingChargeDetails.setStatus(IGeneralChargeDetails.STATUS_PENDING);
//						existingChargeDetails.setFundedShare(fundedShare);
						
						//Uma Khot:Cam upload and Dp field calculation CR
						existingChargeDetails.setDpShare(fundedShare);
						existingChargeDetails.setCalculatedDP(calculatedDP);
						existingChargeDetails.setDpCalculateManually(dpCalculateManually);
						existingChargeDetails.setStockdocMonth(stockdocMonth);
						existingChargeDetails.setStockdocYear(stockdocYear);
						existingChargeDetails.setRemarkByMaker(remarkByMaker);
						existingChargeDetails.setTotalLoanable(totalLonable);
						
						existingChargeDetails.setMigrationFlag_DP_CR(migrationFlag);
						
						if(curUser!=null){
						existingChargeDetails.setLastUpdatedBy(curUser.getLoginID());
						}
						existingChargeDetails.setLastUpdatedOn(date);
						break;
					}
				}
			}
    	}
		 //===========================================================================
        
        
        
        
        
        
        
        
        if (itrxValue.getCollateral() != null) {
            DefaultLogger.debug(this, "original collateral location is: "
                    + itrxValue.getCollateral().getCollateralLocation());
        }
        DefaultLogger.debug(this, "staging collateral location is: " + iColObj.getCollateralLocation());

        boolean isSSC = Boolean.valueOf((String) map.get("isSSC")).booleanValue();
        DefaultLogger.debug(this, "isSSc is: " + map.get("isSSC") + "\tboolean: " + isSSC);
        try {
            boolean isCheckListCompleted = false;
            boolean isSecurityNoChecklist = true;
            boolean isSecurityLocationChange = false;
            if (itrxValue.getCollateral() != null) {
                String originalLocation = itrxValue.getCollateral().getCollateralLocation();
                if (!iColObj.getCollateralLocation().equals(originalLocation)) {
                    isSecurityLocationChange = true;
                }
            }

            try {
                isCheckListCompleted = CollateralProxyFactory.getProxy().isCollateralCheckListCompleted(
                        itrxValue.getCollateral());
            }
            catch (Exception e) {
                DefaultLogger.warn(this, "Failed to get collateral check complete status" + e);
            }

            try {
                if (isSecurityLocationChange) {
                    isSecurityNoChecklist = CollateralProxyFactory.getProxy().isCollateralNoChecklist(
                            itrxValue.getCollateral().getCollateralID());
                    DefaultLogger.debug(this, "isSecurityNoChecklist: " + isSecurityNoChecklist);
                }
            }
            catch (Exception e) {
                DefaultLogger.warn(this, "Failed to validate the collateral checklist existence" + e);
            }

            /*
            if (!isSecurityNoChecklist) {
                exceptionMap.put("collateralLoc", new ActionMessage("error.collateral.securityLocation"));
            }
             */
            
            if (!isSSC && (iColObj.getLimitCharges() != null) && (iColObj.getLimitCharges().length > 0)) {
                SecuritySubTypeUtil.validateSetLimitChargeDetails(iColObj, isCheckListCompleted, exceptionMap);
            }

            SecuritySubTypeUtil.validateChargeType(iColObj, exceptionMap);
            SecuritySubTypeUtil.validateLegalEnforce(iColObj, isCheckListCompleted, exceptionMap);
            // set default for currency code to cms security currency
            if (!isSSC) {
                SecuritySubTypeUtil.setCMSCurrency(iColObj);
            }

            if (iColObj instanceof IGeneralCharge) {
                SecuritySubTypeUtil.setAssetGenChargeInfo((IGeneralCharge) iColObj);
            }

            if (exceptionMap.size() == 0) {
                CollateralUiUtil.setTrxLocation(ctx, iColObj);

                String minGrade = LimitsOfAuthorityMasterHelper.getMinimumEmployeeGradeForLOA(itrxValue, null, calculatedDP);
        		itrxValue.setMinEmployeeGrade(minGrade);
                
                ICollateralTrxValue returnValue = new OBCollateralTrxValue();
                SecuritySubTypeUtil.setCollateralSubTypeCode(itrxValue, iColObj);
                
                iColObj.setCollateralLimits(null);
                
                String flag1 = (String) map.get("flag1");
                if (flag1 == null) {
                    // existing rejected/draft trx context
                    ITrxContext oldCtx = itrxValue.getTrxContext();
                    if (oldCtx != null) {
                        ctx.setCustomer(oldCtx.getCustomer());
                        ctx.setLimitProfile(oldCtx.getLimitProfile());
                    }
                    returnValue = CollateralProxyFactory.getProxy().makerSaveCollateral(ctx, itrxValue, iColObj);
                } else {
                    if (flag1.equals(CategoryCodeConstant.SEARCH_BY_COLLATERAL)) {
                        // no trx context if search by collateral id
                        ctx.setCollateralID(itrxValue.getCollateral().getCollateralID());
                        returnValue = CollateralProxyFactory.getProxy().makerSaveCollateral(ctx, itrxValue, iColObj);
                    } else {
                        // use global trx context when it is search by customer
                        returnValue = CollateralProxyFactory.getProxy().makerSaveCollateral(ctx, itrxValue, iColObj);
                    }
                }

                CollateralStpValidatorFactory stpValidatorFactory = (CollateralStpValidatorFactory) BeanHouse
                        .get("collateralStpValidatorFactory");
                CollateralStpValidator stpValidator = stpValidatorFactory.getCollateralStpValidator(iColObj);
                Map context = new HashMap();
                //Andy Wong: set CMV to staging if actual got value but staging blank, used for pre Stp valuation validation
                if(itrxValue.getCollateral()!=null && itrxValue.getCollateral().getCMV()!=null
                        && (itrxValue.getStagingCollateral().getCMV()==null || itrxValue.getStagingCollateral().getCMV().getAmount() <= 0)){
                    itrxValue.getStagingCollateral().setCMV(itrxValue.getCollateral().getCMV());
                }
                context.put(CollateralStpValidator.COL_OB, returnValue.getStagingCollateral());
                context.put(CollateralStpValidator.TRX_STATUS, returnValue.getStatus());
                context.put(CollateralStpValidator.COL_TRX_VALUE, returnValue);
                boolean isStpReady = stpValidator.validate(context);
                CollateralProxyFactory.getProxy().updateOrInsertStpAllowedIndicator(returnValue, isStpReady);

                result.put("request.ITrxValue", returnValue);
            }
        }
        catch (CollateralException e) {
            throw new CommandProcessingException("failed to update collateral", e);
        }

        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

}
