package com.integrosys.cms.ui.collateral;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.app.collateral.bus.ICollateralPledgor;
import com.integrosys.cms.app.collateral.bus.IInsurancePolicy;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargeaircraft.OBSpecificChargeAircraft;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargeplant.OBSpecificChargePlant;
import com.integrosys.cms.app.collateral.bus.type.property.OBPropertyCollateral;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.PropertiesConstantHelper;

/**
 * Abstract implementation of <code>CollateralStpValidator</code> to validate
 * common fields for all type of collateral.
 * 
 * @author Roy Krisnadi
 * @author Chong Jun Yong
 * @author Andy Wong
 */
public abstract class AbstractCollateralStpValidator implements CollateralStpValidator {

	/**
	 * logger available for subclasses
	 */
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private ICollateralProxy collateralProxy;

	/**
	 * @return the collateralProxy
	 */
	public ICollateralProxy getCollateralProxy() {
		return collateralProxy;
	}

	/**
	 * @param collateralProxy the collateralProxy to set
	 */
	public void setCollateralProxy(ICollateralProxy collateralProxy) {
		this.collateralProxy = collateralProxy;
	}

	public abstract boolean validate(Map context);

	protected boolean validateCommonCollateral(Map context) {
		if (validateAndAccumulateCommonCollateral(context).size() <= 0) {
			// proceed to next check
		}
		else {
			return false;
		}

		ICollateral collateral = (ICollateral) context.get(COL_OB);
		ICollateralPledgor[] collateralPledgors = collateral.getPledgors();
		if (collateralPledgors == null || collateralPledgors.length == 0) {
			return false;
		}

		ICollateralTrxValue trxValue = (ICollateralTrxValue) context.get(COL_TRX_VALUE);
		ICollateralLimitMap[] nonDeletedCollateralLimitMap = SecuritySubTypeUtil
				.retrieveNonDeletedCollateralLimitMap(collateral);
		if (nonDeletedCollateralLimitMap == null || nonDeletedCollateralLimitMap.length == 0) {
			if (trxValue != null && trxValue.getCollateral() == null){
				return false;
			}
		}

		return true;
	}

	protected ActionErrors validateAndAccumulateCommonCollateral(Map context) {
		ActionErrors errorMessages = new ActionErrors();
		ICollateral collateral = (ICollateral) context.get(COL_OB);
		ICollateralTrxValue trxValue = (ICollateralTrxValue) context.get(COL_TRX_VALUE);

		boolean isSTPMandatory = PropertiesConstantHelper.isSTPRequired()
				&& PropertiesConstantHelper.isValidSTPSystem(collateral.getSourceId());

		// Collateral Name
		/**Govind S:Comment For HDFC 01/07/2011**/
		/*
		if (StringUtils.isBlank(collateral.getSCIReferenceNote())) {
			errorMessages.add("collateralName", new ActionMessage("error.mandatory"));
		}
		else {
			// validate duplicated Collateral Name
			long cmsCollateralId = ICMSConstant.LONG_INVALID_VALUE;
			if (trxValue != null && trxValue.getCollateral() != null
					&& trxValue.getCollateral().getCollateralID() != ICMSConstant.LONG_INVALID_VALUE) {
				cmsCollateralId = trxValue.getCollateral().getCollateralID();
			}

			boolean collateralNameExist = isCollateralNameDuplicated(collateral.getSCIReferenceNote(), cmsCollateralId);
			if (collateralNameExist) {
				errorMessages.add("collateralName", new ActionMessage("error.collateral.collateralName.exist"));
			}
		}

		
		// Collateral status
		/*if (StringUtils.isBlank(collateral.getCollateralStatus())) {
			errorMessages.add("collateralStatus", new ActionMessage("error.mandatory"));
		}*/
	
		// Currency // need to check with sachin and dattatray. 
		//TODO if collateral currency is mandatory accross the security, need to put this check
		/*if (StringUtils.isBlank(collateral.getCurrencyCode())) {
			errorMessages.add("collateralCurrency", new ActionMessage("error.mandatory"));
		}*/
		// Collateral code

		if (isSTPMandatory && StringUtils.isBlank(collateral.getSourceSecuritySubType())) {
			errorMessages.add("sourceSecuritySubType", new ActionMessage("error.mandatory"));
		}
		/*// Branch Number
		if (StringUtils.isBlank(collateral.getSecurityOrganization())) {
			errorMessages.add("securityOrganization", new ActionMessage("error.mandatory"));
		}
		// Pledgor
		ICollateralPledgor[] collateralPledgors = collateral.getPledgors();
		if (collateralPledgors == null || collateralPledgors.length == 0) {
			errorMessages.add("collateralPledgor", new ActionMessage("error.collateral.pledgor.info.missing"));
		}*/

		ICollateralLimitMap[] nonDeletedCollateralLimitMap = SecuritySubTypeUtil
				.retrieveNonDeletedCollateralLimitMap(collateral);
		if (nonDeletedCollateralLimitMap == null || nonDeletedCollateralLimitMap.length == 0) {
			if (trxValue != null && trxValue.getCollateral() == null){
				errorMessages.add("collateralPledge", new ActionMessage("error.collateral.pledge.info.missing"));
			}
		}
//Sachin P:Start Here 26/07/2011  This fields is not in mandatory
		// Andy Wong, 10 Feb 2009: manual valuation input validation
//		if (collateral.getValuationIntoCMS() != null) {
//			// validate CMV must > FSV
//			if (collateral.getValuationIntoCMS().getFSV() != null
//					&& (collateral.getValuationIntoCMS().getCMV() == null || collateral.getValuationIntoCMS().getFSV()
//							.getAmount() > collateral.getValuationIntoCMS().getCMV().getAmount())) {
//				errorMessages.add("amountCMV", new ActionMessage("error.val.fsv.greater.cmv"));
//			}
//		}
//Sachin P:End Here 26/07/2011  This fields is not in mandatory
		return errorMessages;
	}

	protected ActionErrors validateAndAccumulateInsurancePolicies(Map context) {
		ICollateral collateral = (ICollateral) context.get(COL_OB);
		ActionErrors errorMessages = (ActionErrors) context.get(ERRORS);
		if (errorMessages == null) {
			errorMessages = new ActionErrors();
			logger.warn("ActionErrors must not be empty when validateAndAccumulateInsurancePolicies.");
		}

		IInsurancePolicy[] insurancePolicies = collateral.getInsurancePolicies();
		boolean insErr = false;
		
		boolean checkOnlyReceivedIns = false;
		if(collateral instanceof OBSpecificChargeAircraft || collateral instanceof OBSpecificChargePlant
		   || collateral instanceof OBPropertyCollateral
				){
			checkOnlyReceivedIns=true;
		}
		if (insurancePolicies != null && insurancePolicies.length > 0) {
			for (int i = 0; i < insurancePolicies.length; i++) {
				
				if(checkOnlyReceivedIns){
					// Collateral ID
					// Insurance Company
					if("PENDING_RECEIVED".equals(insurancePolicies[i].getInsuranceStatus()) || "RECEIVED".equals(insurancePolicies[i].getInsuranceStatus()) || "UPDATE_RECEIVED".equals(insurancePolicies[i].getInsuranceStatus())){
					if (StringUtils.isBlank(insurancePolicies[i].getInsurerName())
							&& StringUtils.isBlank(insurancePolicies[i].getInsuranceCompanyName())) {
						errorMessages.add("insurerName", new ActionMessage("error.mandatory"));
						insErr = true;
					}
					// Amount Insured
					if (insurancePolicies[i].getInsuredAmount() == null) {
						errorMessages.add("insuredAmt", new ActionMessage("error.mandatory"));
						insErr = true;
					}
					
					//********** Lines Commented by Dattatray Thorat for Insurance as Collateral on 28-07-2011
					
					// Type Of Coverage
					/*if (StringUtils.isBlank(insurancePolicies[i].getInsuranceType())) {
						errorMessages.add("insuranceType", new ActionMessage("error.mandatory"));
						insErr = true;
					}
					// Bank Or Cust Arrange Ins
					if (StringUtils.isBlank(insurancePolicies[i].getBankCustomerArrange())) {
						errorMessages.add("bankCustomerArrange", new ActionMessage("error.mandatory"));
						insErr = true;
					}
					// Ins Issue date DDMMYYYY
					if (insurancePolicies[i].getInsIssueDate() == null) {
						errorMessages.add("insIssueDate", new ActionMessage("error.mandatory"));
						insErr = true;
					}*/
					
					//********** Lines Commented by Dattatray Thorat for Insurance as Collateral on 28-07-2011
					
					// Effective Date DDMMYYYY
					if (insurancePolicies[i].getEffectiveDate() == null) {
						errorMessages.add("effectiveDateIns", new ActionMessage("error.mandatory"));
						insErr = true;
					}

					try {
						// Andy Wong, 25 March 2009: check not policy no not blank
						// with StringUtils
						if (StringUtils.isNotBlank(insurancePolicies[i].getPolicyNo())) {
							boolean policyNoExist = getCollateralProxy().getPolicyNumber(
									insurancePolicies[i].getPolicyNo(), insurancePolicies[i].getRefID());
							/*if (policyNoExist) {
								errorMessages.add("insPolicyNum", new ActionMessage(
										"error.collateral.insurance.policyNo.exist"));
								errorMessages.add("insPolicyErr", new ActionMessage(
										"error.collateral.insurance.policyNo.exist.withNo", insurancePolicies[i]
												.getPolicyNo()));
								insErr = true;
							}*/
						}
					}
					catch (CollateralException ex) {
						throw new CommandProcessingException(
								"failed to retrieve whether policy number is exists for policy no ["
										+ insurancePolicies[i].getPolicyNo() + "], policy id ["
										+ insurancePolicies[i].getInsurancePolicyID() + "]", ex);
					}
					}
				}else{
				// Collateral ID
				// Insurance Company
				if (StringUtils.isBlank(insurancePolicies[i].getInsurerName())
						&& StringUtils.isBlank(insurancePolicies[i].getInsuranceCompanyName())) {
					errorMessages.add("insurerName", new ActionMessage("error.mandatory"));
					insErr = true;
				}
				// Amount Insured
				if (insurancePolicies[i].getInsuredAmount() == null) {
					errorMessages.add("insuredAmt", new ActionMessage("error.mandatory"));
					insErr = true;
				}
				
				//********** Lines Commented by Dattatray Thorat for Insurance as Collateral on 28-07-2011
				
				// Type Of Coverage
				/*if (StringUtils.isBlank(insurancePolicies[i].getInsuranceType())) {
					errorMessages.add("insuranceType", new ActionMessage("error.mandatory"));
					insErr = true;
				}
				// Bank Or Cust Arrange Ins
				if (StringUtils.isBlank(insurancePolicies[i].getBankCustomerArrange())) {
					errorMessages.add("bankCustomerArrange", new ActionMessage("error.mandatory"));
					insErr = true;
				}
				// Ins Issue date DDMMYYYY
				if (insurancePolicies[i].getInsIssueDate() == null) {
					errorMessages.add("insIssueDate", new ActionMessage("error.mandatory"));
					insErr = true;
				}*/
				
				//********** Lines Commented by Dattatray Thorat for Insurance as Collateral on 28-07-2011
				
				// Effective Date DDMMYYYY
				if (insurancePolicies[i].getEffectiveDate() == null) {
					errorMessages.add("effectiveDateIns", new ActionMessage("error.mandatory"));
					insErr = true;
				}

				try {
					// Andy Wong, 25 March 2009: check not policy no not blank
					// with StringUtils
					if (StringUtils.isNotBlank(insurancePolicies[i].getPolicyNo())) {
						boolean policyNoExist = getCollateralProxy().getPolicyNumber(
								insurancePolicies[i].getPolicyNo(), insurancePolicies[i].getRefID());
						/*if (policyNoExist) {
							errorMessages.add("insPolicyNum", new ActionMessage(
									"error.collateral.insurance.policyNo.exist"));
							errorMessages.add("insPolicyErr", new ActionMessage(
									"error.collateral.insurance.policyNo.exist.withNo", insurancePolicies[i]
											.getPolicyNo()));
							insErr = true;
						}*/
					}
				}
				catch (CollateralException ex) {
					throw new CommandProcessingException(
							"failed to retrieve whether policy number is exists for policy no ["
									+ insurancePolicies[i].getPolicyNo() + "], policy id ["
									+ insurancePolicies[i].getInsurancePolicyID() + "]", ex);
				}
			}
			}

			// do nothing
		}/*else{
			errorMessages.add("insuranceError", new ActionMessage("Please add insurance"));
		}*/
		
		
		
		if (insErr) {
			errorMessages.add("insPolicyErr", new ActionMessage("error.ins.pol.perfected"));
		}
		return errorMessages;
	}

	public boolean isPreStpValidationRequired(Map context) {
		String status = (String) context.get(TRX_STATUS);
		if (StringUtils.equals(ICMSConstant.STATE_PENDING_PERFECTION, status)
				|| StringUtils.equals(ICMSConstant.STATE_PENDING_UPDATE, status)
				|| StringUtils.equals(ICMSConstant.STATE_PENDING_CREATE, status)) {
			return true;
		}
		return false;
	}

	protected boolean isCollateralNameDuplicated(String collateralName, long cmsCollateralId) {
		boolean collateralNameExist = false;
		try {
			collateralNameExist = getCollateralProxy().getCollateralName(collateralName, cmsCollateralId);
		}
		catch (CollateralException ex) {
			throw new CommandProcessingException(
					"failed to retrieve whether collateral name is exists for cms collateral id [" + cmsCollateralId
							+ "], collateral name [" + collateralName + "]", ex);
		}
		return collateralNameExist;
	}
}
