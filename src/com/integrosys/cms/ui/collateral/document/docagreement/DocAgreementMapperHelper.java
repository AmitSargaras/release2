/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/document/doclou/DocLoUMapperHelper.java,v 1.10 2005/09/30 10:03:33 vishal Exp $
 */
package com.integrosys.cms.ui.collateral.document.docagreement;

import java.util.HashMap;
import java.util.Locale;

//import org.hibernate.hql.ast.tree.IsNotNullLogicOperatorNode;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.type.document.subtype.leaseagreement.ILeaseAgreement;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;

/**
 * 
 * @author Thurein
 * @since  2/Sep/2008	
 *
 */
public class DocAgreementMapperHelper {	

	public static Object mapFormToOB(CommonForm cForm, HashMap inputs, Object obj) throws MapperException {
		try
		{
			DefaultLogger.debug("DocAgreementMapperHelper", "Mapping the form to Object");
			DocAgreementForm aForm = (DocAgreementForm) cForm;
			ILeaseAgreement iObj = (ILeaseAgreement) obj;
			Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
			
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getCollateralName()))
				iObj.setSCIReferenceNote(aForm.getCollateralName());
			
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getIssuer()))
				iObj.setIssuer(aForm.getIssuer());
			
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getDateOfLeaseAggrement()))
				iObj.setDateOfLeaseAgreement(DateUtil.convertDate( aForm.getDateOfLeaseAggrement()));
			
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getDateExchangeControlApprovalObtained()))
				iObj.setExchangeCtrlDate(DateUtil.convertDate(aForm.getDateExchangeControlApprovalObtained()));
			
			if (aForm.getLeaseRentalAgreement().equals("Yes"))
				iObj.setLeaseRentalAgreement(true);
			else
				iObj.setLeaseRentalAgreement(false);
			
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getLimitationOfLease()))
				iObj.setLeaseLimitation(aForm.getLimitationOfLease());
			
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getPropertyType()))
				iObj.setPropertyType(aForm.getPropertyType());
			
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getLocationOfLots()))
				iObj.setLotsLocation(aForm.getLocationOfLots());
			
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getTitleNumber()))
				iObj.setTitleNumberType(aForm.getTitleNumber());
			
			iObj.setBuybackValue(CurrencyManager.convertToAmount(locale, iObj.getCurrencyCode(), aForm.getBuybackValue()));
			
			if(aForm.getBorrowerDependencyOnCollateral().equals("Yes"))
				iObj.setIsBorrowerDependency(true);
			else
				iObj.setIsBorrowerDependency(false);
			
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getGuranteeAmount()))
				iObj.setGuranteeAmount(CurrencyManager.convertToAmount(locale, iObj.getCurrencyCode(), aForm.getGuranteeAmount()));
			
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getLeaseType()))
				iObj.setLeaseType(aForm.getLeaseType());
			
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getDescription()))
				iObj.setDocumentDesc(aForm.getDescription());
			
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getSecurityPerfectionDate()))
				iObj.setPerfectionDate(DateUtil.convertDate( aForm.getSecurityPerfectionDate()));

			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getTitleNumberValue()))
				iObj.setTitleNumberValue(aForm.getTitleNumberValue());

			DefaultLogger.debug("DocAgreementMapperHelper", "Finished mapping");
			return iObj;
		}
		catch(Exception ex)
		{
			throw new MapperException(ex.getMessage());
		}
	}

	public static CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		
		try
		{
			DocAgreementForm aForm = (DocAgreementForm) cForm;
			ILeaseAgreement iObj = (ILeaseAgreement) obj;
			Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
			
			if(iObj.getDocumentDate() !=null)
				aForm.setDocumentDate(DateUtil.formatDate(locale, iObj.getDocumentDate()));
			
			if(iObj.getDocumentDesc() !=null)
				aForm.setDescription(iObj.getDocumentDesc());
			
			if(iObj.getSCIReferenceNote() !=null)
				aForm.setCollateralName(iObj.getSCIReferenceNote());
			
			if(iObj.getIssuer() !=null)
				aForm.setIssuer(iObj.getIssuer());
			
			if(iObj.getDateOfLeaseAgreement() !=null)
				aForm.setDateOfLeaseAggrement( String.valueOf(iObj.getDateOfLeaseAgreement()));
			
			if(iObj.getExchangeCtrlDate() !=null)
				aForm.setDateExchangeControlApprovalObtained(DateUtil.formatDate("dd/MMM/yyyy", iObj.getExchangeCtrlDate()));
			
			if(iObj.getLeaseRentalAgreement())
				aForm.setLeaseRentalAgreement("Yes");
			else
				aForm.setLeaseRentalAgreement("No");	
			
			if(iObj.getLeaseLimitation() !=null)
				aForm.setLimitationOfLease(String.valueOf( iObj.getLeaseLimitation()));
			
			if(iObj.getPropertyType() !=null)
				aForm.setPropertyType(iObj.getPropertyType());
			
			if(iObj.getLotsLocation() !=null)
				aForm.setLocationOfLots(iObj.getLotsLocation());
			
			if(iObj.getTitleNumberType() !=null)
				aForm.setTitleNumber(iObj.getTitleNumberType());
			
			if (iObj.getBuybackValue() != null)
				aForm.setBuybackValue(String.valueOf( iObj.getBuybackValue().getAmount()));
			
			if(iObj.getIsBorrowerDependency())
				aForm.setBorrowerDependencyOnCollateral("Yes");
			else
				aForm.setBorrowerDependencyOnCollateral("No");
			
			if(iObj.getGuranteeAmount() != null &&
					iObj.getGuranteeAmount().getCurrencyCode() == null)
			{
				iObj.getGuranteeAmount().setCurrencyCode(iObj.getCurrencyCode());
			}
			
			aForm.setGuranteeAmount(iObj.getGuranteeAmount() != null ? CurrencyManager.convertToString(locale, iObj.getGuranteeAmount()):"");
			
			if(iObj.getLeaseType() !=null)
				aForm.setLeaseType(iObj.getLeaseType());
			
			if(iObj.getPerfectionDate()!=null)
				aForm.setSecurityPerfectionDate(DateUtil.formatDate(locale, iObj.getPerfectionDate()));
			
			if (iObj.getTitleNumberValue() !=null)
				aForm.setTitleNumberValue(iObj.getTitleNumberValue());
	
			return aForm;
		}
		catch(Exception ex)
		{
			throw new MapperException(ex.getMessage());
		}
	}

	public static Object getObject(HashMap inputs) {
		return ((ILeaseAgreement) ((ICollateralTrxValue) inputs.get("serviceColObj")).getStagingCollateral());
	}
}