package com.aurionpro.clims.rest.mapper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.aurionpro.clims.rest.dto.ABEnquiryResponseDTO;
import com.aurionpro.clims.rest.dto.ABSpecEnqDetailsResponseDTO;
import com.aurionpro.clims.rest.dto.CollateralEnqiryDetailsResponseDTO;
import com.aurionpro.clims.rest.dto.InsuranceDetailRestResponseDTO;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.IInsurancePolicy;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargeaircraft.ISpecificChargeAircraft;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargeaircraft.OBSpecificChargeAircraft;

public class CollateralEnquiryDTOMapper {
	public List getInsuranceForColObj(ABSpecEnqDetailsResponseDTO res,
			ICollateral col, String ColSubTypeId) throws ParseException {
		List<InsuranceDetailRestResponseDTO> insuranceResponseList = new ArrayList<InsuranceDetailRestResponseDTO>();

		InsuranceDetailRestResponseDTO insRes = new InsuranceDetailRestResponseDTO();
		IInsurancePolicy iPol;
		IInsurancePolicy[] insurancePolicies = col.getInsurancePolicies();
		if (null != insurancePolicies && insurancePolicies.length != 0) {
			for (int i = 0; i < insurancePolicies.length; i++) {
				iPol = insurancePolicies[i];
				if ("ACTIVE".equalsIgnoreCase(iPol.getStatus())) {
					insRes.setInsurancePolicyID(
							Long.toString(iPol.getInsurancePolicyID()));
					if (null != iPol.getInsuranceStatus()
							&& !iPol.getInsuranceStatus().trim().isEmpty()) {
						insRes.setInsuranceStatus(iPol.getInsuranceStatus());
					}
					if ("RECIEVED"
							.equalsIgnoreCase(iPol.getInsuranceStatus())) {
						if (null != iPol.getPolicyNo()
								&& !iPol.getPolicyNo().trim().isEmpty()) {
							insRes.setPolicyNo(iPol.getPolicyNo());
						}
						if (null != iPol.getCoverNoteNumber() && !iPol
								.getCoverNoteNumber().trim().isEmpty()) {
							insRes.setCoverNoteNumber(
									iPol.getCoverNoteNumber());
						}
						if (null != iPol.getInsuranceCompanyName() && !iPol
								.getInsuranceCompanyName().trim().isEmpty()) {
							insRes.setInsuranceCompanyName(
									iPol.getInsuranceCompanyName());
						}
						if (null != iPol.getCurrencyCode()
								&& !iPol.getCurrencyCode().trim().isEmpty()) {
							insRes.setCurrencyCode(iPol.getCurrencyCode());
						}
						if (null != iPol.getTypeOfPerils1()
								&& !iPol.getTypeOfPerils1().trim().isEmpty()) {
							insRes.setTypeOfPerils1(iPol.getTypeOfPerils1());
						}
						if (null != iPol.getInsurableAmount()
								&& !iPol.getInsurableAmount().toString().trim()
										.isEmpty()) {
							insRes.setInsurableAmount(
									iPol.getInsurableAmount().toString());
						}
						if (null != iPol.getInsuredAmount()
								&& !iPol.getInsuredAmount().toString().trim()
										.isEmpty()) {
							insRes.setInsuredAmount(
									iPol.getInsuredAmount().toString());
						}
						if (null != iPol.getReceivedDate()
								&& !iPol.getReceivedDate().toString().trim()
										.isEmpty()) {
							insRes.setReceivedDate(
									iPol.getReceivedDate().toString());
						}
						if (null != iPol.getEffectiveDate()
								&& !iPol.getEffectiveDate().toString().trim()
										.isEmpty()) {
							insRes.setEffectiveDate(
									iPol.getEffectiveDate().toString());
						}
						if (null != iPol.getInsurancePremium()
								&& !iPol.getInsurancePremium().toString().trim()
										.isEmpty()) {
							insRes.setInsurancePremium(
									iPol.getInsurancePremium().toString());
						}
						if (null != iPol.getNonSchemeScheme() && !iPol
								.getNonSchemeScheme().trim().isEmpty()) {
							insRes.setNonScheme_Scheme(
									iPol.getNonSchemeScheme());
						}
						if (null != iPol.getAddress() && !iPol.getAddress()
								.toString().trim().isEmpty()) {
							insRes.setAddress(iPol.getAddress().toString());
						}
						if (null != iPol.getNonSchemeScheme() && !iPol
								.getNonSchemeScheme().trim().isEmpty()) {
							insRes.setNonScheme_Scheme(
									iPol.getNonSchemeScheme());
						}
						if (null != iPol.getRemark1() && !iPol.getRemark1()
								.toString().trim().isEmpty()) {
							insRes.setRemark1(iPol.getRemark1());
						}
						if (null != iPol.getRemark2()
								&& !iPol.getRemark2().trim().isEmpty()) {
							insRes.setRemark2(iPol.getRemark2());
						}
						if (null != iPol.getInsuredAgainst()
								&& !iPol.getInsuredAgainst().trim().isEmpty()) {
							insRes.setInsuredAgainst(iPol.getInsuredAgainst());
						}
					} else if ("WAIVED"
							.equalsIgnoreCase(iPol.getInsuranceStatus())) {
						if (null != iPol.getWaivedDate() && !iPol
								.getWaivedDate().toString().trim().isEmpty()) {
							insRes.setWaivedDate(
									iPol.getWaivedDate().toString());
						}
						if (null != iPol.getCreditApprover()
								&& !iPol.getCreditApprover().trim().isEmpty()) {
							insRes.setCreditApprover(
									iPol.getCreditApprover().toString());
						}
					} else if ("AWAITING"
							.equalsIgnoreCase(iPol.getInsuranceStatus())) {
						if (null != iPol.getOriginalTargetDate()
								&& !iPol.getOriginalTargetDate().toString()
										.trim().isEmpty()) {
							insRes.setOriginalTargetDate(
									iPol.getOriginalTargetDate().toString());
						}
					} else if ("DEFERRED"
							.equalsIgnoreCase(iPol.getInsuranceStatus())) {
						if (null != iPol.getOriginalTargetDate()
								&& !iPol.getOriginalTargetDate().toString()
										.trim().isEmpty()) {
							insRes.setOriginalTargetDate(
									iPol.getOriginalTargetDate().toString());
						}
						if (null != iPol.getDateDeferred()
								&& !iPol.getDateDeferred().toString().trim()
										.isEmpty()) {
							insRes.setDateDeferred(
									iPol.getDateDeferred().toString());
						}
						if (null != iPol.getNextDueDate() && !iPol
								.getNextDueDate().toString().trim().isEmpty()) {
							insRes.setNextDueDate(
									iPol.getNextDueDate().toString());
						}
						if (null != iPol.getCreditApprover()
								&& !iPol.getCreditApprover().trim().isEmpty()) {
							insRes.setCreditApprover(
									iPol.getCreditApprover().toString());
						}
					}
					insuranceResponseList.add(insRes);
				}
			}
		}
		return insuranceResponseList;
	}
	
	public CollateralEnqiryDetailsResponseDTO getCommonRespForColObj(
			CollateralEnqiryDetailsResponseDTO res, ICollateral col,
			String ColSubTypeId) throws ParseException {
		
		res.setSecurityId(Long.toString(col.getCollateralID()));
		if (null != col.getSCIReferenceNote()
				&& !col.getSCIReferenceNote().trim().isEmpty()) {
			res.setSciReferenceNote(col.getSCIReferenceNote());
		}
		if (null != col.getCollateralType().getTypeName()
				&& !col.getCollateralType().getTypeName().trim().isEmpty()) {
			res.setCollateralType(col.getCollateralType().getTypeName());
		}
		if (null != col.getCollateralSubType().getSubTypeDesc()
				&& !col.getCollateralSubType().getSubTypeDesc().trim().isEmpty()) {
			res.setSciReferenceNote(col.getCollateralSubType().getSubTypeDesc());
		}
		if (null != col.getCurrencyCode()
				&& !col.getCurrencyCode().trim().isEmpty()) {
			res.setCollateralCurrency(col.getCurrencyCode());
		}
		if (null != col.getSecPriority()
				&& !col.getSecPriority().trim().isEmpty()) {
			res.setSecPriority(col.getSecPriority());
		}
		if (null != col.getMonitorProcess()
				&& !col.getMonitorProcess().trim().isEmpty()) {
			res.setMonitorProcess(col.getMonitorProcess());
		}
		if (null != col.getMonitorFrequency()
				&& !col.getMonitorFrequency().trim().isEmpty()) {
			res.setMonitorFrequency(col.getMonitorFrequency());
		}
		//Custodian1 Custodian2 pending
		/*if (null != col.getCollateralCustodian()
				&& !col.getSecurityOwnership().trim().isEmpty()) {
			res.setSecurityOwnership(col.getSecurityOwnership());
		}
		if (null != col.getCollateralCustodianType()
				&& !col.getCollateralCustodianType().trim().isEmpty()) {
			res.setCollateralCustodianType(col.getCollateralCustodianType());
		}*/
		if (null != col.getCollateralLocation()
				&& !col.getCollateralLocation().trim().isEmpty()) {
			res.setCollateralLocation(col.getCollateralLocation());
		}
		if (null != col.getCollateralCode()
				&& !col.getCollateralCode().trim().isEmpty()) {
			res.setCollateralCode(col.getCollateralCode());
		}
		if (null != col.getSecurityOrganization()
				&& !col.getSecurityOrganization().trim().isEmpty()) {
			res.setSecurityOrganization(col.getSecurityOrganization());
		}
		if (null != col.getValuationAmount()
				&& !col.getValuationAmount().trim().isEmpty()) {
			res.setValuationAmount(col.getValuationAmount());
		}
		if (null != col.getValuationDate()) {
			res.setValuationDate(col.getValuationDate().toString());
		}
		if (null != col.getCommonRevalFreq()
				&& !col.getCommonRevalFreq().trim().isEmpty()) {
			res.setCommonRevalFreq(col.getCommonRevalFreq());
		}
		
		if (null != col.getNextValDate()) {
			res.setNextValDate(col.getNextValDate().toString());
		}
		if (null != col.getTypeOfChange()
				&& !col.getTypeOfChange().trim().isEmpty()) {
			res.setTypeOfChange(col.getTypeOfChange());
		}
		if (null != col.getOtherBankCharge()
				&& !col.getOtherBankCharge().trim().isEmpty()) {
			res.setOtherBankCharge(col.getOtherBankCharge());
		}
		if (null != col.getThirdPartyEntity()
				&& !col.getThirdPartyEntity().trim().isEmpty()) {
			res.setThirdPartyEntity(col.getThirdPartyEntity());
		}
		if (null != col.getCinForThirdParty()
				&& !col.getCinForThirdParty().trim().isEmpty()) {
			res.setCinForThirdParty(col.getCinForThirdParty());
		}
		if (null != col.getOwnerOfProperty()
				&& !col.getOwnerOfProperty().trim().isEmpty()) {
			res.setOwnerOfProperty(col.getOwnerOfProperty());
		}
		if (null != col.getOwnerOfProperty()
				&& !col.getOwnerOfProperty().trim().isEmpty()) {
			res.setOwnerOfProperty(col.getOwnerOfProperty());
		}
		if (null != col.getCersaiTransactionRefNumber()
				&& !col.getCersaiTransactionRefNumber().trim().isEmpty()) {
			res.setCersaiTransactionRefNumber(col.getCersaiTransactionRefNumber());
		}
		if (null != col.getCersaiSecurityInterestId()
				&& !col.getCersaiSecurityInterestId().trim().isEmpty()) {
			res.setCersaiSecurityInterestId(col.getCersaiSecurityInterestId());
		}
		if (null != col.getCersaiAssetId()
				&& !col.getCersaiAssetId().trim().isEmpty()) {
			res.setCersaiAssetId(col.getCersaiAssetId());
		}
		if (null != col.getDateOfCersaiRegisteration()){
			res.setDateOfCersaiRegisteration(col.getDateOfCersaiRegisteration().toString());
		}
		if (null != col.getCersaiId()
				&& !col.getCersaiId().trim().isEmpty()) {
			res.setCersaiId(col.getCersaiId());
		}
		if (null != col.getSaleDeedPurchaseDate()) {
			res.setSaleDeedPurchaseDate(col.getSaleDeedPurchaseDate().toString());
		}
		if (null != col.getThirdPartyAddress()
				&& !col.getThirdPartyAddress().trim().isEmpty()) {
			res.setThirdPartyAddress(col.getThirdPartyAddress());
		}
		if (null != col.getThirdPartyState()
				&& !col.getThirdPartyState().trim().isEmpty()) {
			res.setThirdPartyState(col.getThirdPartyState());
		}
		if (null != col.getThirdPartyCity()
				&& !col.getThirdPartyCity().trim().isEmpty()) {
			res.setThirdPartyCity(col.getThirdPartyCity());
		}
		if (null != col.getThirdPartyPincode()
				&& !col.getThirdPartyPincode().trim().isEmpty()) {
			res.setThirdPartyPincode(col.getThirdPartyPincode());
		}
		if (null != col.getThirdPartyAddress()
				&& !col.getThirdPartyAddress().trim().isEmpty()) {
			res.setThirdPartyAddress(col.getThirdPartyAddress());
		}
		res.setMargin(Double.toString(col.getMargin()));
		if (null != col.getCMV()) {
			res.setCmv(col.getCMV().toString());
		}
		return res;

	}
	
	public ABEnquiryResponseDTO getAbSpecificAssetRespForColObj(ABSpecEnqDetailsResponseDTO res, ICollateral col,String ColSubTypeId) throws ParseException {
		ABEnquiryResponseDTO colRes = new ABEnquiryResponseDTO();
		
		ISpecificChargeAircraft iObj = null;
		if(col instanceof OBSpecificChargeAircraft)
		{
			 iObj = (ISpecificChargeAircraft) col;
		}
		
		if (null != iObj.getRamId()
				&& !iObj.getRamId().trim().isEmpty()) {
			res.setRamId(iObj.getRamId());
		}
		if (null != iObj.getStartDate()
				&& !iObj.getStartDate().toString().trim().isEmpty()) {
			res.setStartDate(iObj.getStartDate().toString());
		}
		if (null != iObj.getMaturityDate()
				&& !iObj.getMaturityDate().toString().trim().isEmpty()) {
			res.setMaturityDate(iObj.getMaturityDate().toString());
		}
		if (null != iObj.getRamId()
				&& !iObj.getRamId().trim().isEmpty()) {
			res.setRamId(iObj.getRamId());
		}
		
		boolean phyIns = iObj.getIsPhysicalInspection();
		if(phyIns)
			res.setIsPhysicalInspection("Yes");
		else
			res.setIsPhysicalInspection("No");
		
		
		if (null != iObj.getPhysicalInspectionFreqUnit()
				&& !iObj.getPhysicalInspectionFreqUnit().trim().isEmpty()) {
			res.setPhysicalInspectionFreq(iObj.getPhysicalInspectionFreqUnit());
		}
		if (null != iObj.getLastPhysicalInspectDate()
				&& !iObj.getLastPhysicalInspectDate().toString().trim().isEmpty()) {
			res.setLastPhysicalInspectDate(iObj.getLastPhysicalInspectDate().toString());
		}
		if (null != iObj.getNextPhysicalInspectDate()
				&& !iObj.getNextPhysicalInspectDate().toString().trim().isEmpty()) {
			res.setNextPhysicalInspectDate(iObj.getNextPhysicalInspectDate().toString());
		}
		if (null != iObj.getGoodStatus()
				&& !iObj.getGoodStatus().trim().isEmpty()) {
			res.setGoodStatus(iObj.getGoodStatus());
		}
		if (null != iObj.getScrapValue()
				&& !iObj.getScrapValue().toString().trim().isEmpty()) {
			res.setScrapValue(iObj.getScrapValue().toString());
		}
		if (null != iObj.getEnvRiskyStatus()
				&& !iObj.getEnvRiskyStatus().trim().isEmpty()) {
			res.setEnvRiskyStatus(iObj.getEnvRiskyStatus());
		}
		if (null != iObj.getEnvRiskyDate()
				&& !iObj.getEnvRiskyDate().toString().trim().isEmpty()) {
			res.setEnvRiskyDate(iObj.getEnvRiskyDate().toString());
		}
		if (null != iObj.getEnvRiskyStatus()
				&& !iObj.getEnvRiskyStatus().trim().isEmpty()) {
			res.setEnvRiskyStatus(iObj.getEnvRiskyStatus());
		}
		if (null != iObj.getRemarks()
				&& !iObj.getRemarks().trim().isEmpty()) {
			res.setRemarks(iObj.getRemarks());
		}
		List<ABSpecEnqDetailsResponseDTO> bodyDetails = new ArrayList<ABSpecEnqDetailsResponseDTO>();
		bodyDetails.add(res);
		colRes.setBodyDetails(bodyDetails);
		return colRes;
	}
}
