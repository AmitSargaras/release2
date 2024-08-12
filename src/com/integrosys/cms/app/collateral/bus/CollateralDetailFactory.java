/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/CollateralDetailFactory.java,v 1.27 2005/03/17 10:02:45 hshii Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.collateral.bus.type.asset.OBAssetBasedCollateral;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.IChargeCommon;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.IReceivableCommon;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.OBGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque.IAssetPostDatedCheque;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque.OBAssetPostDatedCheque;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.recgenagent.OBReceivableGeneralAgent;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.recopen.OBReceivableOpen;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.recspecagent.OBReceivableSpecificAgent;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.recspecnoa.OBReceivableSpecificNoAgent;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargeaircraft.ISpecificChargeAircraft;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargeaircraft.OBSpecificChargeAircraft;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargegold.ISpecificChargeGold;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargegold.OBSpecificChargeGold;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargeothers.OBSpecificChargeOthers;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargeplant.ISpecificChargePlant;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargeplant.OBSpecificChargePlant;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargevehicle.ISpecificChargeVehicle;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargevehicle.OBSpecificChargeVehicle;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.vessel.IVessel;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.vessel.OBVessel;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashCollateral;
import com.integrosys.cms.app.collateral.bus.type.cash.OBCashCollateral;
import com.integrosys.cms.app.collateral.bus.type.cash.subtype.cashcash.OBCashCash;
import com.integrosys.cms.app.collateral.bus.type.cash.subtype.cashfd.OBCashFd;
import com.integrosys.cms.app.collateral.bus.type.cash.subtype.diffcurrency.OBDiffCurrency;
import com.integrosys.cms.app.collateral.bus.type.cash.subtype.hkdusd.OBHKDUSD;
import com.integrosys.cms.app.collateral.bus.type.cash.subtype.repo.OBRepo;
import com.integrosys.cms.app.collateral.bus.type.cash.subtype.samecurrency.OBSameCurrency;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.collateral.bus.type.commodity.OBCommodityCollateral;
import com.integrosys.cms.app.collateral.bus.type.commodity.subtype.chemical.OBChemical;
import com.integrosys.cms.app.collateral.bus.type.commodity.subtype.energy.OBEnergy;
import com.integrosys.cms.app.collateral.bus.type.commodity.subtype.ferrousmetal.OBFerrousMetal;
import com.integrosys.cms.app.collateral.bus.type.commodity.subtype.nonferrousmetal.OBNonFerrousMetal;
import com.integrosys.cms.app.collateral.bus.type.commodity.subtype.nonferrousothers.OBNonFerrousOthers;
import com.integrosys.cms.app.collateral.bus.type.commodity.subtype.softbulk.OBSoftBulkCommodity;
import com.integrosys.cms.app.collateral.bus.type.document.IDocumentCollateral;
import com.integrosys.cms.app.collateral.bus.type.document.OBDocumentCollateral;
import com.integrosys.cms.app.collateral.bus.type.document.subtype.comfort.OBComfort;
import com.integrosys.cms.app.collateral.bus.type.document.subtype.creditagreement.OBCreditAgreement;
import com.integrosys.cms.app.collateral.bus.type.document.subtype.deedassignment.OBDeedAssignment;
import com.integrosys.cms.app.collateral.bus.type.document.subtype.deedsub.OBDeedSub;
import com.integrosys.cms.app.collateral.bus.type.document.subtype.fxisda.OBFXISDA;
import com.integrosys.cms.app.collateral.bus.type.document.subtype.fxnetting.OBFXNetting;
import com.integrosys.cms.app.collateral.bus.type.document.subtype.leaseagreement.OBLeaseAgreement;
import com.integrosys.cms.app.collateral.bus.type.document.subtype.letterindemnity.OBLetterIndemnity;
import com.integrosys.cms.app.collateral.bus.type.document.subtype.pledge.OBPledge;
import com.integrosys.cms.app.collateral.bus.type.document.subtype.undertaking.OBUndertaking;
import com.integrosys.cms.app.collateral.bus.type.guarantee.IGuaranteeCollateral;
import com.integrosys.cms.app.collateral.bus.type.guarantee.OBGuaranteeCollateral;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.bankdiffccy.OBBankDifferentCurrency;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.banksameccy.IBankSameCurrency;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.banksameccy.OBBankSameCurrency;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.corprelated.OBCorporateRelated;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.corpthirdparty.OBCorporateThirdParty;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.government.OBGovernment;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.gtegovtlink.OBGteGovtLink;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.gteinwardlc.OBGteinwardLC;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.interbrindem.OBInterBranchIndemnity;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.personal.OBPersonal;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.sblcdiffccy.OBSBLCDifferentCurrency;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.sblcsameccy.ISBLCSameCurrency;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.sblcsameccy.OBSBLCSameCurrency;
import com.integrosys.cms.app.collateral.bus.type.insurance.IInsuranceCollateral;
import com.integrosys.cms.app.collateral.bus.type.insurance.OBInsuranceCollateral;
import com.integrosys.cms.app.collateral.bus.type.insurance.subtype.creditderivative.OBCreditDerivative;
import com.integrosys.cms.app.collateral.bus.type.insurance.subtype.creditinsurance.OBCreditInsurance;
import com.integrosys.cms.app.collateral.bus.type.insurance.subtype.creditswaps.OBCreditDefaultSwaps;
import com.integrosys.cms.app.collateral.bus.type.insurance.subtype.keymaninsurance.OBKeymanInsurance;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableCollateral;
import com.integrosys.cms.app.collateral.bus.type.marketable.OBMarketableCollateral;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.bondsforeign.OBBondsForeign;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.bondslocal.OBBondsLocal;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.govtforeigndiffccy.OBGovtForeignDiffCurrency;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.govtforeignsameccy.OBGovtForeignSameCurrency;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.govtlocal.OBGovtLocal;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.mainindexforeign.OBMainIndexForeign;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.mainindexlocal.OBMainIndexLocal;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.nonlistedlocal.INonListedLocal;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.nonlistedlocal.OBNonListedLocal;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.otherlistedforeign.OBOtherListedForeign;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.otherlistedlocal.OBOtherListedLocal;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.portfolioothers.OBPortfolioOthers;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.portfolioown.OBPortfolioOwn;
import com.integrosys.cms.app.collateral.bus.type.nocollateral.INoCollateral;
import com.integrosys.cms.app.collateral.bus.type.nocollateral.OBNoCollateral;
import com.integrosys.cms.app.collateral.bus.type.nocollateral.subtype.OBNoCollaterala;
import com.integrosys.cms.app.collateral.bus.type.others.IOthersCollateral;
import com.integrosys.cms.app.collateral.bus.type.others.OBOthersCollateral;
import com.integrosys.cms.app.collateral.bus.type.others.subtype.othersa.OBOthersa;
import com.integrosys.cms.app.collateral.bus.type.property.IPropertyCollateral;
import com.integrosys.cms.app.collateral.bus.type.property.OBPropertyCollateral;
import com.integrosys.cms.app.collateral.bus.type.property.subtype.agricultural.OBAgricultural;
import com.integrosys.cms.app.collateral.bus.type.property.subtype.comgeneral.OBCommercialGeneral;
import com.integrosys.cms.app.collateral.bus.type.property.subtype.comshophouse.OBCommercialShopHouse;
import com.integrosys.cms.app.collateral.bus.type.property.subtype.industrial.OBIndustrial;
import com.integrosys.cms.app.collateral.bus.type.property.subtype.industrialspecial.OBIndustrialSpecial;
import com.integrosys.cms.app.collateral.bus.type.property.subtype.landurban.OBLandUrban;
import com.integrosys.cms.app.collateral.bus.type.property.subtype.landvacantrural.OBLandVacantRural;
import com.integrosys.cms.app.collateral.bus.type.property.subtype.resluxury.OBResidentialLuxury;
import com.integrosys.cms.app.collateral.bus.type.property.subtype.resstandard.OBResidentialStandard;
import com.integrosys.cms.app.collateral.bus.type.property.subtype.serviceapartment.OBCommercialServiceApartment;
import com.integrosys.cms.app.collateral.bus.type.property.subtype.specialhotel.OBSpecialPurposeHotel;
import com.integrosys.cms.app.collateral.bus.type.property.subtype.specialothers.OBSpecialPurposeOthers;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * A factory class to factorize collateral into its type such as property,
 * asset, etc.
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.27 $
 * @since $Date: 2005/03/17 10:02:45 $ Tag: $Name: $
 */
public class CollateralDetailFactory {
	/**
	 * Get home interface to collateral detail entity bean based on the
	 * collateral instance.
	 * 
	 * @param col of type ICollateral
	 * @return collateral detail home interface
	 * @throws CollateralException if the collateral is invalid
	 */
	public static EBCollateralDetailHome getEBHome(ICollateral col) throws CollateralException {
		if (col instanceof IGuaranteeCollateral) {
			return (EBCollateralDetailHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_GUARANTEE_COLLATERAL_JNDI,
					EBCollateralDetailHome.class.getName());
		}
		else if (col instanceof ISpecificChargeAircraft) {
			return (EBCollateralDetailHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_ASSET_AIRCRAFT_JNDI,
					EBCollateralDetailHome.class.getName());
		}
		else if (col instanceof ISpecificChargeGold) {
			return (EBCollateralDetailHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_ASSET_GOLD_JNDI,
					EBCollateralDetailHome.class.getName());
		}
		else if (col instanceof ISpecificChargePlant) {
			return (EBCollateralDetailHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_ASSET_PLANT_JNDI,
					EBCollateralDetailHome.class.getName());
		}
		else if (col instanceof ISpecificChargeVehicle) {
			return (EBCollateralDetailHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_ASSET_VEHICLE_JNDI,
					EBCollateralDetailHome.class.getName());
		}
		else if (col instanceof IVessel) {
			return (EBCollateralDetailHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_ASSET_VESSEL_JNDI,
					EBCollateralDetailHome.class.getName());
		}
		else if (col instanceof IChargeCommon) {
			return (EBCollateralDetailHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_ASSET_CHARGE_JNDI,
					EBCollateralDetailHome.class.getName());
		}
		else if (col instanceof IReceivableCommon) {
			return (EBCollateralDetailHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_ASSET_RECEIVABLE_JNDI,
					EBCollateralDetailHome.class.getName());
		}
		else if (col instanceof IAssetPostDatedCheque) {
			return (EBCollateralDetailHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_ASSET_PDT_CHEQUE_JNDI,
					EBCollateralDetailHome.class.getName());
		}
		else if (col instanceof ICashCollateral) {
			return (EBCollateralDetailHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_CASH_COLLATERAL_JNDI,
					EBCollateralDetailHome.class.getName());
		}
		else if (col instanceof IDocumentCollateral) {
			return (EBCollateralDetailHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_DOCUMENT_COLLATERAL_JNDI,
					EBCollateralDetailHome.class.getName());
		}
		else if (col instanceof IInsuranceCollateral) {
			return (EBCollateralDetailHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_INSURANCE_COLLATERAL_JNDI,
					EBCollateralDetailHome.class.getName());
		}
		else if (col instanceof IPropertyCollateral) {
			return (EBCollateralDetailHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_PROPERTY_COLLATERAL_JNDI,
					EBCollateralDetailHome.class.getName());
		}
		else if (col instanceof IMarketableCollateral) {
			return (EBCollateralDetailHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_MARKETABLE_COLLATERAL_JNDI,
					EBCollateralDetailHome.class.getName());
		}
		else if (col instanceof ICommodityCollateral) {
			return (EBCollateralDetailHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_COMMODITY_COLLATERAL_JNDI,
					EBCollateralDetailHome.class.getName());
		}
		else if (col instanceof IOthersCollateral) {
			return (EBCollateralDetailHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_OTHERS_COLLATERAL_JNDI,
					EBCollateralDetailHome.class.getName());
		}
		else if (col instanceof IGeneralCharge) {
			return (EBCollateralDetailHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_ASSET_GEN_CHARGE_JNDI,
					EBCollateralDetailHome.class.getName());
		}
		else if (col instanceof INoCollateral) {
			return (EBCollateralDetailHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_COLLATERAL_JNDI,
					EBCollateralDetailHome.class.getName());
		}
		else {
			throw new CollateralException("Invalid collateral instance: " + col.getClass().getName());
		}
	}

	/**
	 * Get home interface to collateral detail staging entity bean based on the
	 * collateral instance.
	 * 
	 * @param col of type ICollateral
	 * @return collateral detail home interface
	 * @throws CollateralException if the collateral is invalid
	 */
	public static EBCollateralDetailHome getEBStagingHome(ICollateral col) throws CollateralException {
		if (col instanceof IGuaranteeCollateral) {
			return (EBCollateralDetailHome) BeanController.getEJBHome(
					ICMSJNDIConstant.EB_GUARANTEE_COLLATERAL_STAGING_JNDI, EBCollateralDetailHome.class.getName());
		}
		else if (col instanceof ISpecificChargeAircraft) {
			return (EBCollateralDetailHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_ASSET_AIRCRAFT_STAGING_JNDI,
					EBCollateralDetailHome.class.getName());
		}
		else if (col instanceof ISpecificChargeGold) {
			return (EBCollateralDetailHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_ASSET_GOLD_STAGING_JNDI,
					EBCollateralDetailHome.class.getName());
		}
		else if (col instanceof ISpecificChargePlant) {
			return (EBCollateralDetailHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_ASSET_PLANT_STAGING_JNDI,
					EBCollateralDetailHome.class.getName());
		}
		else if (col instanceof ISpecificChargeVehicle) {
			return (EBCollateralDetailHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_ASSET_VEHICLE_STAGING_JNDI,
					EBCollateralDetailHome.class.getName());
		}
		else if (col instanceof IVessel) {
			return (EBCollateralDetailHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_ASSET_VESSEL_STAGING_JNDI,
					EBCollateralDetailHome.class.getName());
		}
		else if (col instanceof IChargeCommon) {
			return (EBCollateralDetailHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_ASSET_CHARGE_STAGING_JNDI,
					EBCollateralDetailHome.class.getName());
		}
		else if (col instanceof IReceivableCommon) {
			return (EBCollateralDetailHome) BeanController.getEJBHome(
					ICMSJNDIConstant.EB_ASSET_RECEIVABLE_STAGING_JNDI, EBCollateralDetailHome.class.getName());
		}
		else if (col instanceof IAssetPostDatedCheque) {
			return (EBCollateralDetailHome) BeanController.getEJBHome(
					ICMSJNDIConstant.EB_ASSET_PDT_CHEQUE_STAGING_JNDI, EBCollateralDetailHome.class.getName());
		}
		else if (col instanceof ICashCollateral) {
			return (EBCollateralDetailHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_CASH_COLLATERAL_STAGING_JNDI,
					EBCollateralDetailHome.class.getName());
		}
		else if (col instanceof IDocumentCollateral) {
			return (EBCollateralDetailHome) BeanController.getEJBHome(
					ICMSJNDIConstant.EB_DOCUMENT_COLLATERAL_STAGING_JNDI, EBCollateralDetailHome.class.getName());
		}
		else if (col instanceof IInsuranceCollateral) {
			return (EBCollateralDetailHome) BeanController.getEJBHome(
					ICMSJNDIConstant.EB_INSURANCE_COLLATERAL_STAGING_JNDI, EBCollateralDetailHome.class.getName());
		}
		else if (col instanceof IPropertyCollateral) {
			return (EBCollateralDetailHome) BeanController.getEJBHome(
					ICMSJNDIConstant.EB_PROPERTY_COLLATERAL_STAGING_JNDI, EBCollateralDetailHome.class.getName());
		}
		else if (col instanceof IMarketableCollateral) {
			return (EBCollateralDetailHome) BeanController.getEJBHome(
					ICMSJNDIConstant.EB_MARKETABLE_COLLATERAL_STAGING_JNDI, EBCollateralDetailHome.class.getName());
		}
		else if (col instanceof ICommodityCollateral) {
			return (EBCollateralDetailHome) BeanController.getEJBHome(
					ICMSJNDIConstant.EB_COMMODITY_COLLATERAL_STAGING_JNDI, EBCollateralDetailHome.class.getName());
		}
		else if (col instanceof IOthersCollateral) {
			return (EBCollateralDetailHome) BeanController.getEJBHome(
					ICMSJNDIConstant.EB_OTHERS_COLLATERAL_STAGING_JNDI, EBCollateralDetailHome.class.getName());
		}
		else if (col instanceof IGeneralCharge) {
			return (EBCollateralDetailHome) BeanController.getEJBHome(
					ICMSJNDIConstant.EB_ASSET_GEN_CHARGE_STAGING_JNDI, EBCollateralDetailHome.class.getName());
		}
		else if (col instanceof INoCollateral) {
			return (EBCollateralDetailHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_COLLATERAL_STAGING_JNDI,
					EBCollateralDetailHome.class.getName());
		}
		else {
			throw new CollateralException("Invalid collateral instance: " + col.getClass().getName());
		}
	}

	/**
	 * Get business object of collateral detail based on the collateral subtype.
	 * 
	 * @param subtype is of type ICollateralSubType
	 * @return collateral business object
	 * @throws CollateralException if the subtype is invalid
	 */
	public static ICollateral getOB(ICollateralSubType subtype) throws CollateralException {
		String code = subtype.getSubTypeCode();

		// DefaultLogger.debug ( CollateralDetailFactory.class.getName () ,
		// " CALLLLLLLLEDDDDDD : " + code );

		if ((code == null) || (code.length() == 0)) {
			throw new CollateralException("Invalid Collateral subtype: " + code);
		}

		if (code.equals(ICMSConstant.COLTYPE_GUARANTEE_BANK_DIFFCCY)) {
			return new OBBankDifferentCurrency();
		}
		else if (code.equals(ICMSConstant.COLTYPE_GUARANTEE_BANK_SAMECCY)) {
			return new OBBankSameCurrency();
		}
		else if (code.equals(ICMSConstant.COLTYPE_GUARANTEE_CORP_RELATED)) {
			return new OBCorporateRelated();
		}
		else if (code.equals(ICMSConstant.COLTYPE_GUARANTEE_CORP_3RDPARTY)) {
			return new OBCorporateThirdParty();
		}
		else if (code.equals(ICMSConstant.COLTYPE_GUARANTEE_GOVERNMENT)) {
			return new OBGovernment();
		}
		else if (code.equals(ICMSConstant.COLTYPE_GUARANTEE_INTBR_INDEMNITY)) {
			return new OBInterBranchIndemnity();
		}
		else if (code.equals(ICMSConstant.COLTYPE_GUARANTEE_PERSONAL)) {
			return new OBPersonal();
		}
		else if (code.equals(ICMSConstant.COLTYPE_GUARANTEE_SBLC_DIFFCCY)) {
			return new OBSBLCDifferentCurrency();
		}
		else if (code.equals(ICMSConstant.COLTYPE_GUARANTEE_SBLC_SAMECCY)) {
			return new OBSBLCSameCurrency();
		}
		else if (code.equals(ICMSConstant.COLTYPE_ASSET_GENERAL_CHARGE)) {
			return new OBGeneralCharge();
		}
		else if (code.equals(ICMSConstant.COLTYPE_ASSET_PDT_CHEQUE)) {
			return new OBAssetPostDatedCheque();
		}
		else if (code.equals(ICMSConstant.COLTYPE_ASSET_RECV_GEN_AGENT)) {
			return new OBReceivableGeneralAgent();
		}
		else if (code.equals(ICMSConstant.COLTYPE_ASSET_RECV_OPEN)) {
			return new OBReceivableOpen();
		}
		else if (code.equals(ICMSConstant.COLTYPE_ASSET_RECV_SPEC_AGENT)) {
			return new OBReceivableSpecificAgent();
		}
		else if (code.equals(ICMSConstant.COLTYPE_ASSET_RECV_SPEC_NOAGENT)) {
			return new OBReceivableSpecificNoAgent();
		}
		else if (code.equals(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_OTHERS)) {
			return new OBSpecificChargeOthers();
		}
		else if (code.equals(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_PLANT)) {
			return new OBSpecificChargePlant();
		}
		else if (code.equals(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_VEH)) {
			return new OBSpecificChargeVehicle();
		}
		else if (code.equals(ICMSConstant.COLTYPE_ASSET_VESSEL)) {
			return new OBVessel();
		}
		else if (code.equals(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_AIRCRAFT)) {
			return new OBSpecificChargeAircraft();
		}
		else if (code.equals(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_GOLD)) {
			return new OBSpecificChargeGold();
		}
		else if (code.equals(ICMSConstant.COLTYPE_CASH_CASH)) {
			return new OBCashCash();
		}
		else if (code.equals(ICMSConstant.COLTYPE_CASH_FD)) {
			return new OBCashFd();
		}
		else if (code.equals(ICMSConstant.COLTYPE_CASH_REPO)) {
			return new OBRepo();
		}
		else if (code.equals(ICMSConstant.COLTYPE_CASH_DIFFCCY)) {
			return new OBDiffCurrency();
		}
		else if (code.equals(ICMSConstant.COLTYPE_CASH_HKDUSD)) {
			return new OBHKDUSD();
		}
		else if (code.equals(ICMSConstant.COLTYPE_CASH_SAMECCY)) {
			return new OBSameCurrency();
		}
		else if (code.equals(ICMSConstant.COLTYPE_DOC_CR_AGREEMENT)) {
			return new OBCreditAgreement();
		}
		else if (code.equals(ICMSConstant.COLTYPE_DOC_FX_ISDA)) {
			return new OBFXISDA();
		}
		else if (code.equals(ICMSConstant.COLTYPE_DOC_FX_NETTING)) {
			return new OBFXNetting();
		}
		else if (code.equals(ICMSConstant.COLTYPE_DOC_LOU)) {
			return new OBUndertaking();
		}
		else if (code.equals(ICMSConstant.COLTYPE_DOC_COMFORT)) {
			return new OBComfort();
		}
		else if (code.equals(ICMSConstant.COLTYPE_DOC_PLEDGE)) {
			return new OBPledge();
		}
		else if (code.equals(ICMSConstant.COLTYPE_DOC_LETTER_INDEMNITY)) {
			return new OBLetterIndemnity();
		}
		else if (code.equals(ICMSConstant.COLTYPE_DOC_DEED_ASSIGNMENT)) {
			return new OBDeedAssignment();
		}
		else if (code.equals(ICMSConstant.COLTYPE_DOC_DEED_SUB)) {
			return new OBDeedSub();
		}
		else if (code.equals(ICMSConstant.COLTYPE_DOC_LEASE_AGREEMENT)) {
			return new OBLeaseAgreement();
		}
		else if (code.equals(ICMSConstant.COLTYPE_INS_CR_DERIVATIVE)) {
			return new OBCreditDerivative();
		}
		else if (code.equals(ICMSConstant.COLTYPE_INS_CR_INS)) {
			return new OBCreditInsurance();
		}
		else if (code.equals(ICMSConstant.COLTYPE_INS_CR_DEFAULT_SWAPS)) {
			return new OBCreditDefaultSwaps();
		}
		else if (code.equals(ICMSConstant.COLTYPE_INS_KEYMAN_INS)) {
			return new OBKeymanInsurance();
		}
		else if (code.equals(ICMSConstant.COLTYPE_PROP_AGRICULTURAL)) {
			return new OBAgricultural();
		}
		else if (code.equals(ICMSConstant.COLTYPE_PROP_COM_GENERAL)) {
			return new OBCommercialGeneral();
		}
		else if (code.equals(ICMSConstant.COLTYPE_PROP_COM_SHOP_HOUSE)) {
			return new OBCommercialShopHouse();
		}
		else if (code.equals(ICMSConstant.COLTYPE_PROP_INDUSTRIAL)) {
			return new OBIndustrial();
		}
		else if (code.equals(ICMSConstant.COLTYPE_PROP_SPEC_SERVICE_APT)) {
			return new OBCommercialServiceApartment();
		}
		else if (code.equals(ICMSConstant.COLTYPE_PROP_SPEC_INDUSTRIAL)) {
			return new OBIndustrialSpecial();
		}
		else if (code.equals(ICMSConstant.COLTYPE_PROP_LAND_URBAN)) {
			return new OBLandUrban();
		}
		else if (code.equals(ICMSConstant.COLTYPE_PROP_LAND_VACANT)) {
			return new OBLandVacantRural();
		}
		else if (code.equals(ICMSConstant.COLTYPE_PROP_RES_LUXURY)) {
			return new OBResidentialLuxury();
		}
		else if (code.equals(ICMSConstant.COLTYPE_PROP_RES_STANDARD)) {
			return new OBResidentialStandard();
		}
		else if (code.equals(ICMSConstant.COLTYPE_PROP_SPEC_HOTEL)) {
			return new OBSpecialPurposeHotel();
		}
		else if (code.equals(ICMSConstant.COLTYPE_PROP_SPEC_OTHERS)) {
			return new OBSpecialPurposeOthers();
		}
		else if (code.equals(ICMSConstant.COLTYPE_MAR_BONDS_FOREIGN)) {
			return new OBBondsForeign();
		}
		else if (code.equals(ICMSConstant.COLTYPE_MAR_BONDS_LOCAL)) {
			return new OBBondsLocal();
		}
		else if (code.equals(ICMSConstant.COLTYPE_MAR_GOVT_FOREIGN_DIFFCCY)) {
			return new OBGovtForeignDiffCurrency();
		}
		else if (code.equals(ICMSConstant.COLTYPE_MAR_GOVT_FOREIGN_SAMECCY)) {
			return new OBGovtForeignSameCurrency();
		}
		else if (code.equals(ICMSConstant.COLTYPE_MAR_GOVT_LOCAL)) {
			return new OBGovtLocal();
		}
		else if (code.equals(ICMSConstant.COLTYPE_MAR_MAIN_IDX_FOREIGN)) {
			return new OBMainIndexForeign();
		}
		else if (code.equals(ICMSConstant.COLTYPE_MAR_MAIN_IDX_LOCAL)) {
			return new OBMainIndexLocal();
		}
		else if (code.equals(ICMSConstant.COLTYPE_MAR_NONLISTED_LOCAL)) {
			return new OBNonListedLocal();
		}
		else if (code.equals(ICMSConstant.COLTYPE_MAR_OTHERLISTED_FOREIGN)) {
			return new OBOtherListedForeign();
		}
		else if (code.equals(ICMSConstant.COLTYPE_MAR_OTHERLISTED_LOCAL)) {
			return new OBOtherListedLocal();
		}
		else if (code.equals(ICMSConstant.COLTYPE_MAR_PORTFOLIO_OTHERS)) {
			return new OBPortfolioOthers();
		}
		else if (code.equals(ICMSConstant.COLTYPE_MAR_PORTFOLIO_OWN)) {
			return new OBPortfolioOwn();
		}
		else if (code.equals(ICMSConstant.COLTYPE_COMMODITY_CHEMICAL)) {
			return new OBChemical();
		}
		else if (code.equals(ICMSConstant.COLTYPE_COMMODITY_ENERGY)) {
			return new OBEnergy();
		}
		else if (code.equals(ICMSConstant.COLTYPE_COMMODITY_FERROUS_METAL)) {
			return new OBFerrousMetal();
		}
		else if (code.equals(ICMSConstant.COLTYPE_COMMODITY_NON_FERROUS_METAL)) {
			return new OBNonFerrousMetal();
		}
		else if (code.equals(ICMSConstant.COLTYPE_COMMODITY_NON_FERROUS_OTHERS)) {
			return new OBNonFerrousOthers();
		}
		else if (code.equals(ICMSConstant.COLTYPE_COMMODITY_SOFT_BULK)) {
			return new OBSoftBulkCommodity();
		}
		else if (code.equals(ICMSConstant.COLTYPE_NA)) {
			return getOBByType(subtype);
		}
		else if (code.equals(ICMSConstant.COLTYPE_OTHERS_OTHERSA)) {
			return new OBOthersa();
		}
		else if (code.equals(ICMSConstant.COLTYPE_NOCOLLATERAL)) {
			return new OBNoCollaterala();
		}
		else if (code.equals(ICMSConstant.COLTYPE_GUARANTEE_BANK_INWARDLC)) {
			return new OBGteinwardLC();
		}
		else if (code.equals(ICMSConstant.COLTYPE_GUARANTEE_GOVT_LINK)) {
			return new OBGteGovtLink();
		}
		else {
			String actualCode = CommonDataSingleton.getCodeCategoryLabelByValue(CategoryCodeConstant.SEC_SUBTYP, code);
			if (actualCode != null) {
				subtype.setSubTypeCode(actualCode);
				ICollateral newCollateral = getOB(subtype);
				ICollateralSubType newCollateralSubType = newCollateral.getCollateralSubType();
				newCollateralSubType.setSubTypeCode(code);
				newCollateral.setCollateralSubType(newCollateralSubType);
				return newCollateral;
			}
			else {
				throw new CollateralException("Invalid Collateral subtype: " + subtype.getSubTypeCode());
			}
		}
	}

	private static ICollateral getOBByType(ICollateralSubType subtype) {
		String code = subtype.getTypeCode();

		if ((code == null) || (code.length() == 0)) {
			return new OBCollateral();
			// throw new CollateralException ("Invalid Collateral type: " +
			// code);
		}

		if (code.equals(ICMSConstant.SECURITY_TYPE_ASSET)) {
			return new OBAssetBasedCollateral();
		}
		else if (code.equals(ICMSConstant.SECURITY_TYPE_CASH)) {
			return new OBCashCollateral();
		}
		else if (code.equals(ICMSConstant.SECURITY_TYPE_DOCUMENT)) {
			return new OBDocumentCollateral();
		}
		else if (code.equals(ICMSConstant.SECURITY_TYPE_GUARANTEE)) {
			return new OBGuaranteeCollateral();
		}
		else if (code.equals(ICMSConstant.SECURITY_TYPE_INSURANCE)) {
			return new OBInsuranceCollateral();
		}
		else if (code.equals(ICMSConstant.SECURITY_TYPE_MARKETABLE)) {
			return new OBMarketableCollateral();
		}
		else if (code.equals(ICMSConstant.SECURITY_TYPE_PROPERTY)) {
			return new OBPropertyCollateral();
		}
		else if (code.equals(ICMSConstant.SECURITY_TYPE_COMMODITY)) {
			return new OBCommodityCollateral();
		}
		else if (code.equals(ICMSConstant.SECURITY_TYPE_OTHERS)) {
			return new OBOthersCollateral();
		}
		else if (code.equals(ICMSConstant.COLTYPE_NOCOLLATERAL)) {
			return new OBNoCollateral();
		}
		else {
			return new OBCollateral();
			// throw new CollateralException ("Invalid Collateral type: " +
			// code);
		}
	}

	/**
	 * <p>
	 * Whether a collateral can maintain multiple charge info.
	 * <p>
	 * Currently, Property, Others all subtype and Asset Based - Vehicles, Plant
	 * &amp; Equipment, Others, Gold, Aircraft, Vessel and General Charge can
	 * maintain multiple charge.
	 * @param collateral an collateral instance to be checked against
	 * @return whether a collateral can maintain multiple charge info
	 */
	public static boolean canCollateralMaintainMultipleCharge(ICollateral collateral) {
		return (collateral instanceof IPropertyCollateral) || (collateral instanceof IOthersCollateral)
				|| (collateral instanceof IChargeCommon) || (collateral instanceof IGeneralCharge);
	}

	/**
	 * <p>
	 * For a collateral, to check whether it's required for online valuation.
	 * <p>
	 * Applicable for Marketable securities, cash, guarantee, insurance and
	 * asset based - gold.
	 * @param col the collateral to be checked against
	 * @return whether the collateral required online valuation
	 */
	public static boolean isCollateralOnlineValuationRequired(ICollateral col) {
		if ((col instanceof IMarketableCollateral) || (col instanceof ICashCollateral)
				|| (col instanceof IGuaranteeCollateral) || (col instanceof IInsuranceCollateral)
				|| (col instanceof ISpecificChargeGold)) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * For a collateral, whether it's manual valuation section should have
	 * reserved price input date.
	 * <p>
	 * Applicable for Property and Others collateral
	 * @param collateral collateral to be checked against
	 * @return whether the collateral manual valuation require reserved price
	 *         input date
	 */
	public static boolean isCollateralManualValuationRequireReservedPriceInputDate(ICollateral collateral) {
		return (collateral instanceof IPropertyCollateral) || (collateral instanceof IOthersCollateral);
	}

	/**
	 * Check if the collateral requires fully manual revaluation based on the
	 * collateral type.
	 * 
	 * @param col of type ICollateral
	 * @return boolean
	 */
	public static boolean isFullyManualRevaluation(ICollateral col) {
		if ((col instanceof IMarketableCollateral) && !(col instanceof INonListedLocal)) {
			return false;
		}
		else if (col instanceof IAssetPostDatedCheque) {
			return false;
		}
		else if (col instanceof IGuaranteeCollateral) {
			if ((col instanceof IBankSameCurrency) || (col instanceof ISBLCSameCurrency)) {
				return true;
			}
			else {
				return false;
			}
		}
		else if (col instanceof IInsuranceCollateral) {
			return false;
		}
		else {
			return true;
		}
	}
}
