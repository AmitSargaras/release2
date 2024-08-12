/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/message/castor/sci/security/SCISecurityHelper.java,v 1.20 2004/07/05 03:14:45 lyng Exp $
 */
package com.integrosys.cms.host.eai.security;

import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.security.bus.ApprovedSecurity;
import com.integrosys.cms.host.eai.security.bus.SecurityCharge;
import com.integrosys.cms.host.eai.security.bus.SecurityInsurancePolicy;
import com.integrosys.cms.host.eai.security.bus.SecurityPledgorMap;
import com.integrosys.cms.host.eai.security.bus.SecurityValuation;
import com.integrosys.cms.host.eai.security.bus.asset.AssetSecurity;
import com.integrosys.cms.host.eai.security.bus.asset.ChequeDetail;
import com.integrosys.cms.host.eai.security.bus.asset.StageAssetSecurity;
import com.integrosys.cms.host.eai.security.bus.cash.CashDeposit;
import com.integrosys.cms.host.eai.security.bus.cash.CashSecurity;
import com.integrosys.cms.host.eai.security.bus.cash.StageCashSecurity;
import com.integrosys.cms.host.eai.security.bus.clean.CleanSecurity;
import com.integrosys.cms.host.eai.security.bus.clean.StageCleanSecurity;
import com.integrosys.cms.host.eai.security.bus.document.DocumentSecurity;
import com.integrosys.cms.host.eai.security.bus.document.StageDocumentSecurity;
import com.integrosys.cms.host.eai.security.bus.guarantee.GuaranteeSecurity;
import com.integrosys.cms.host.eai.security.bus.guarantee.StageGuaranteeSecurity;
import com.integrosys.cms.host.eai.security.bus.insurance.CreditDefaultSwapsDetail;
import com.integrosys.cms.host.eai.security.bus.insurance.InsuranceSecurity;
import com.integrosys.cms.host.eai.security.bus.insurance.StageInsuranceSecurity;
import com.integrosys.cms.host.eai.security.bus.marketable.MarketableSecurity;
import com.integrosys.cms.host.eai.security.bus.marketable.PortfolioItem;
import com.integrosys.cms.host.eai.security.bus.marketable.StageMarketableSecurity;
import com.integrosys.cms.host.eai.security.bus.others.OthersSecurity;
import com.integrosys.cms.host.eai.security.bus.others.StageOthersSecurity;
import com.integrosys.cms.host.eai.security.bus.property.PropertySecurity;
import com.integrosys.cms.host.eai.security.bus.property.StagePropertySecurity;

/**
 * Helper class for Security in SCI context.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.20 $
 * @since $Date: 2004/07/05 03:14:45 $ Tag: $Name: $
 */
public class SCISecurityHelper {
	private static SCISecurityHelper instance = null;

	private static Properties secProps = null;

	/**
	 * Default constructor.
	 */
	private SCISecurityHelper() {
	}

	/**
	 * Create handler to the SCISecurityHelper.
	 * 
	 * @return SCISecurityHelper instance
	 */
	public static SCISecurityHelper getInstance() {
		if (instance == null) {
			synchronized (SCISecurityHelper.class) {
				if (instance == null) {
					instance = new SCISecurityHelper();
				}
			}
		}
		return instance;
	}

	/**
	 * Get security sub type code in CMS given its SCI security subtype.
	 * 
	 * @param sciSecuritySubType security subtype code from SCI
	 * @return cms security subtype code
	 */
	public String getCMSSecuritySubType(String sciSecuritySubType) {
		Properties prop = getSecuritySubTypeProperties();
		return prop.getProperty(sciSecuritySubType);
	}

	/**
	 * Helper method to get properties for security subtypes.
	 * 
	 * @return Properties
	 */
	private Properties getSecuritySubTypeProperties() {
		if (secProps == null) {
			secProps = new Properties();
			try {
				// TODO: get the property value
				FileInputStream is = new FileInputStream(PropertyManager.getValue(""));
				secProps.load(is);
			}
			catch (Exception e) {
				// do nothing here.
			}
		}
		return secProps;
	}

	/**
	 * Get approved security based on its type code.
	 * 
	 * @param typeCode of type String
	 * @return approved security
	 */
	public ApprovedSecurity getSecurityBasedOnType(String typeCode) {
		if (typeCode == null) {
			return null;
		}

		if (typeCode.equals(ICMSConstant.SECURITY_TYPE_PROPERTY)) {
			return new PropertySecurity();
		}
		else if (typeCode.equals(ICMSConstant.SECURITY_TYPE_CASH)) {
			return new CashSecurity();
		}
		else if (typeCode.equals(ICMSConstant.SECURITY_TYPE_ASSET)) {
			return new AssetSecurity();
		}
		else if (typeCode.equals(ICMSConstant.SECURITY_TYPE_DOCUMENT)) {
			return new DocumentSecurity();
		}
		else if (typeCode.equals(ICMSConstant.SECURITY_TYPE_GUARANTEE)) {
			return new GuaranteeSecurity();
		}
		else if (typeCode.equals(ICMSConstant.SECURITY_TYPE_INSURANCE)) {
			return new InsuranceSecurity();
		}
		else if (typeCode.equals(ICMSConstant.SECURITY_TYPE_MARKETABLE)) {
			return new MarketableSecurity();
		}
		else if (typeCode.equals(ICMSConstant.SECURITY_TYPE_CLEAN)) {
			return new CleanSecurity();
		}
		else if (typeCode.equals(ICMSConstant.SECURITY_TYPE_OTHERS)) {
			return new OthersSecurity();
		}
		else {
			return null;
		}
	}

	/**
	 * Get approved security for staging based on its type code.
	 * 
	 * @param typeCode of type String
	 * @return approved security
	 */
	public ApprovedSecurity getStageSecurityBasedOnType(String typeCode) {
		if (typeCode == null) {
			return null;
		}

		if (typeCode.equals(ICMSConstant.SECURITY_TYPE_PROPERTY)) {
			return new StagePropertySecurity();
		}
		else if (typeCode.equals(ICMSConstant.SECURITY_TYPE_CASH)) {
			return new StageCashSecurity();
		}
		else if (typeCode.equals(ICMSConstant.SECURITY_TYPE_ASSET)) {
			return new StageAssetSecurity();
		}
		else if (typeCode.equals(ICMSConstant.SECURITY_TYPE_DOCUMENT)) {
			return new StageDocumentSecurity();
		}
		else if (typeCode.equals(ICMSConstant.SECURITY_TYPE_GUARANTEE)) {
			return new StageGuaranteeSecurity();
		}
		else if (typeCode.equals(ICMSConstant.SECURITY_TYPE_INSURANCE)) {
			return new StageInsuranceSecurity();
		}
		else if (typeCode.equals(ICMSConstant.SECURITY_TYPE_MARKETABLE)) {
			return new StageMarketableSecurity();
		}
		else if (typeCode.equals(ICMSConstant.SECURITY_TYPE_CLEAN)) {
			return new StageCleanSecurity();
		}
		else if (typeCode.equals(ICMSConstant.SECURITY_TYPE_OTHERS)) {
			return new StageOthersSecurity();
		}
		else {
			return null;
		}
	}

	/**
	 * Check if the approved security is changed.
	 * 
	 * @param sec of type ApprovedSecurity
	 * @return boolean
	 */
	public boolean isSecurityChanged(ApprovedSecurity sec) {
		return sec.getChangeIndicator().equals(String.valueOf(IEaiConstant.CHANGEINDICATOR));
	}

	/**
	 * Check if it is to create the approved security.
	 * 
	 * @param sec of type ApprovedSecurity
	 * @return boolean
	 */
	public boolean isCreate(ApprovedSecurity sec) {
		return sec.getUpdateStatusIndicator().equals(String.valueOf(IEaiConstant.CREATEINDICATOR));
	}

	/**
	 * Check if it is to update the approved security.
	 * 
	 * @param sec of type ApprovedSecurity
	 * @return boolean
	 */
	public boolean isUpdate(ApprovedSecurity sec) {
		return sec.getUpdateStatusIndicator().equals(String.valueOf(IEaiConstant.UPDATEINDICATOR));
	}

	/**
	 * Check if it is to delete the approved security.
	 * 
	 * @param sec of type ApprovedSecurity
	 * @return boolean
	 */
	public boolean isDelete(ApprovedSecurity sec) {
		return sec.getUpdateStatusIndicator().equals(String.valueOf(IEaiConstant.DELETEINDICATOR));
	}

	public boolean isShareSecurity(ApprovedSecurity sec) {
		return (sec.getSharedSecurityId() != ICMSConstant.LONG_INVALID_VALUE) && (sec.getSharedSecurityId() != 0);
	}

	/**
	 * Check if the security pledgor map is changed.
	 * 
	 * @param map of type SecurityPledgorMap
	 * @return boolean
	 */
	public boolean isPledgorMapChanged(SecurityPledgorMap map) {
		return map.getChangeIndicator().equals(String.valueOf(IEaiConstant.CHANGEINDICATOR));
	}

	/**
	 * Check if it is to create the security pledgor map.
	 * 
	 * @param map of type SecurityPledgorMap
	 * @return boolean
	 */
	public boolean isCreatePledgorMap(SecurityPledgorMap map) {
		return map.getUpdateStatusIndicator().equals(String.valueOf(IEaiConstant.CREATEINDICATOR));
	}

	/**
	 * Check if it is to update the security pledgor map.
	 * 
	 * @param map of type SecurityPledgorMap
	 * @return boolean
	 */
	public boolean isUpdatePledgorMap(SecurityPledgorMap map) {
		return map.getUpdateStatusIndicator().equals(String.valueOf(IEaiConstant.UPDATEINDICATOR));
	}

	/**
	 * Check if it is to delete the security pledgor map.
	 * 
	 * @param map of type SecurityPledgorMap
	 * @return boolean
	 */
	public boolean isDeletePledgorMap(SecurityPledgorMap map) {
		return map.getUpdateStatusIndicator().equals(String.valueOf(IEaiConstant.DELETEINDICATOR));
	}

	/**
	 * Method to check if the string is empty or null.
	 * 
	 * @param str of type String
	 * @return true if the string is empty or null, otherwise return false
	 */
	public boolean isEmptyString(String str) {
		if (str == null) {
			return true;
		}

		String tmpstr = str.trim();

		if (tmpstr.length() == 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Get security pledgor map for processing.
	 * 
	 * @param mapVec of type Vector
	 * @return a Vector of SecurityPledgorMap
	 */
	public Vector getPledgorMapForProcess(Vector mapVec) {
		int size = 0;
		if (mapVec != null) {
			size = mapVec.size();
		}
		Vector vec = new Vector();

		for (int i = 0; i < size; i++) {
			SecurityPledgorMap sec = (SecurityPledgorMap) mapVec.elementAt(i);

			if (sec.getChangeIndicator() != null && sec.getChangeIndicator().toString().trim().length() > 0) {
				vec.add(sec);
			}
		}
		return vec;
	}

	/**
	 * Get valuation detail for processing.
	 * 
	 * @param valuationDetailVec of type Vector
	 * @return a Vector of ValuationDetail
	 */
	public Vector getValuationDetailForProcess(Vector valuationDetailVec) {
		int size = 0;
		if (valuationDetailVec != null) {
			size = valuationDetailVec.size();
		}

		Vector vec = new Vector();
		for (int i = 0; i < size; i++) {
			SecurityValuation sec = (SecurityValuation) valuationDetailVec.elementAt(i);

			if (sec.getChangeIndicator() != null && StringUtils.isNotBlank(sec.getChangeIndicator().toString())) {
				vec.add(sec);
			}
		}
		return vec;
	}

	/**
	 * Get insurance policy detail for processing.
	 * 
	 * @param insPolicyDetailVec of type Vector
	 * @return a Vector of InsurancePolicyDetail
	 */
	public Vector getInsurancePolicyDetailForProcess(Vector insPolicyDetailVec) {
		int size = 0;
		if (insPolicyDetailVec != null) {
			size = insPolicyDetailVec.size();
		}

		Vector vec = new Vector();
		for (int i = 0; i < size; i++) {
			SecurityInsurancePolicy sec = (SecurityInsurancePolicy) insPolicyDetailVec.elementAt(i);

			if (sec.getChangeIndicator() != null && StringUtils.isNotBlank(sec.getChangeIndicator().toString())) {
				vec.add(sec);
			}
		}
		return vec;
	}

	/**
	 * Get charge detail for processing.
	 * 
	 * @param chargeDetailVec of type Vector
	 * @return a Vector of ChargeDetail
	 */
	public Vector getChargeDetailForProcess(Vector chargeDetailVec) {
		int size = 0;
		if (chargeDetailVec != null) {
			size = chargeDetailVec.size();
		}

		Vector vec = new Vector();
		for (int i = 0; i < size; i++) {
			SecurityCharge sec = (SecurityCharge) chargeDetailVec.elementAt(i);

			if (!isEmptyString(sec.getChangeIndicator())) {
				vec.add(sec);
			}
		}
		return vec;
	}

	/**
	 * Get deposit detail for processing.
	 * 
	 * @param depositDetailVec of type Vector
	 * @return a Vector of DepositDetail
	 */
	public Vector getDepositDetailForProcess(Vector depositDetailVec) {
		int size = 0;
		if (depositDetailVec != null) {
			size = depositDetailVec.size();
		}

		Vector vec = new Vector();
		for (int i = 0; i < size; i++) {
			CashDeposit sec = (CashDeposit) depositDetailVec.elementAt(i);

			if (!isEmptyString(sec.getChangeIndicator())) {
				vec.add(sec);
			}
		}
		return vec;
	}

	/**
	 * Get cheque detail for processing.
	 * 
	 * @param chequeDetailVec of type Vector
	 * @return a Vector of ChequeDetail
	 */
	public Vector getChequeDetailForProcess(Vector chequeDetailVec) {
		int size = 0;
		if (chequeDetailVec != null) {
			size = chequeDetailVec.size();
		}

		Vector vec = new Vector();
		for (int i = 0; i < size; i++) {
			ChequeDetail sec = (ChequeDetail) chequeDetailVec.elementAt(i);

			if (!isEmptyString(sec.getChangeIndicator())) {
				vec.add(sec);
			}
		}
		return vec;
	}

	/**
	 * Get credit default swaps detail for processing.
	 * 
	 * @param creditDefaultSwapsDetailVec of type Vector
	 * @return a Vector of CreditDefaultSwapsDetail
	 */
	public Vector getCreditDefaultSwapsDetailForProcess(Vector creditDefaultSwapsDetailVec) {
		int size = 0;
		if (creditDefaultSwapsDetailVec != null) {
			size = creditDefaultSwapsDetailVec.size();
		}

		Vector vec = new Vector();
		for (int i = 0; i < size; i++) {
			CreditDefaultSwapsDetail sec = (CreditDefaultSwapsDetail) creditDefaultSwapsDetailVec.elementAt(i);

			if (!isEmptyString(sec.getChangeIndicator())) {
				vec.add(sec);
			}
		}
		return vec;
	}

	/**
	 * Get port folio items for processing.
	 * 
	 * @param portfolioItemsVec of type Vector
	 * @return a Vector of PortfolioItems
	 */
	public Vector getPortfolioItemsForProcess(Vector portfolioItemsVec) {
		int size = 0;
		if (portfolioItemsVec != null) {
			size = portfolioItemsVec.size();
		}

		Vector vec = new Vector();
		for (int i = 0; i < size; i++) {
			PortfolioItem sec = (PortfolioItem) portfolioItemsVec.elementAt(i);

			if (!isEmptyString(sec.getChangeIndicator())) {
				vec.add(sec);
			}
		}
		return vec;
	}

	/** To fix the indicators **/
	public AssetSecurity fixAssetDetailInd(AssetSecurity assetSecurity, ApprovedSecurity security) {
		if (isCreate(security)) {

			assetSecurity.setUpdateStatusIndicator(String.valueOf(IEaiConstant.CREATEINDICATOR));
			assetSecurity.setChangeIndicator(String.valueOf(IEaiConstant.CHANGEINDICATOR));
		}
		else if ((isUpdate(security))) {
			assetSecurity.setUpdateStatusIndicator(String.valueOf(IEaiConstant.UPDATEINDICATOR));
			assetSecurity.setChangeIndicator(String.valueOf(IEaiConstant.CHANGEINDICATOR));
		}
		return assetSecurity;
	}

	public Vector fixChequeDetailInd(Vector cheques, ApprovedSecurity security) {
		if (cheques == null) {
			cheques = new Vector();
		}
		Vector indFixedChequeDetail = new Vector();
		if (isCreate(security)) {
			Iterator iter = cheques.iterator();
			while (iter.hasNext()) {
				ChequeDetail chequeDetail = (ChequeDetail) iter.next();

				chequeDetail.setUpdateStatusIndicator(String.valueOf(IEaiConstant.CREATEINDICATOR));
				chequeDetail.setChangeIndicator(String.valueOf(IEaiConstant.CHANGEINDICATOR));
				indFixedChequeDetail.add(chequeDetail);
			}
		}
		else if ((isUpdate(security))) {
			Iterator iter = cheques.iterator();
			while (iter.hasNext()) {
				ChequeDetail chequeDetail = (ChequeDetail) iter.next();

				chequeDetail.setUpdateStatusIndicator(String.valueOf(IEaiConstant.UPDATEINDICATOR));
				chequeDetail.setChangeIndicator(String.valueOf(IEaiConstant.CHANGEINDICATOR));
				indFixedChequeDetail.add(chequeDetail);
			}
		}
		return indFixedChequeDetail;
	}

	public CashSecurity fixCashDetailInd(CashSecurity cashSecurity, ApprovedSecurity security) {
		if (isCreate(security)) {

			cashSecurity.setUpdateStatusIndicator(String.valueOf(IEaiConstant.CREATEINDICATOR));
			cashSecurity.setChangeIndicator(String.valueOf(IEaiConstant.CHANGEINDICATOR));
		}
		else if ((isUpdate(security))) {
			cashSecurity.setUpdateStatusIndicator(String.valueOf(IEaiConstant.UPDATEINDICATOR));
			cashSecurity.setChangeIndicator(String.valueOf(IEaiConstant.CHANGEINDICATOR));
		}
		return cashSecurity;
	}

	public Vector fixCashDepositInd(Vector cashDeposits, ApprovedSecurity security) {

		if (cashDeposits == null) {
			cashDeposits = new Vector();
		}
		Vector indFixedCashDeposits = new Vector();
		if (isCreate(security)) {
			Iterator iter = cashDeposits.iterator();
			while (iter.hasNext()) {
				CashDeposit cashDeposit = (CashDeposit) iter.next();

				cashDeposit.setUpdateStatusIndicator(String.valueOf(IEaiConstant.CREATEINDICATOR));
				cashDeposit.setChangeIndicator(String.valueOf(IEaiConstant.CHANGEINDICATOR));
				indFixedCashDeposits.add(cashDeposit);
			}
		}
		else if ((isUpdate(security))) {
			Iterator iter = cashDeposits.iterator();
			while (iter.hasNext()) {
				CashDeposit cashDeposit = (CashDeposit) iter.next();

				cashDeposit.setUpdateStatusIndicator(String.valueOf(IEaiConstant.UPDATEINDICATOR));
				cashDeposit.setChangeIndicator(String.valueOf(IEaiConstant.CHANGEINDICATOR));
				indFixedCashDeposits.add(cashDeposit);
			}
		}
		return indFixedCashDeposits;
	}

	public InsuranceSecurity fixInsuranceDetailInd(InsuranceSecurity insuranceSecurity, ApprovedSecurity security) {
		if (isCreate(security)) {

			insuranceSecurity.setUpdateStatusIndicator(String.valueOf(IEaiConstant.CREATEINDICATOR));
			insuranceSecurity.setChangeIndicator(String.valueOf(IEaiConstant.CHANGEINDICATOR));
		}
		else if ((isUpdate(security))) {
			insuranceSecurity.setUpdateStatusIndicator(String.valueOf(IEaiConstant.UPDATEINDICATOR));
			insuranceSecurity.setChangeIndicator(String.valueOf(IEaiConstant.CHANGEINDICATOR));
		}
		return insuranceSecurity;
	}

	public Vector fixCreditDefaultSwapsDetailInd(Vector creditDefaultSwapsDetails, ApprovedSecurity security) {

		if (creditDefaultSwapsDetails == null) {
			creditDefaultSwapsDetails = new Vector();
		}
		Vector indFixedcreditSwapsDetails = new Vector();
		if (isCreate(security)) {
			Iterator iter = creditDefaultSwapsDetails.iterator();
			while (iter.hasNext()) {
				CreditDefaultSwapsDetail creditDefaultSwapsDetail = (CreditDefaultSwapsDetail) iter.next();

				creditDefaultSwapsDetail.setUpdateStatusIndicator(String.valueOf(IEaiConstant.CREATEINDICATOR));
				creditDefaultSwapsDetail.setChangeIndicator(String.valueOf(IEaiConstant.CHANGEINDICATOR));
				indFixedcreditSwapsDetails.add(creditDefaultSwapsDetail);
			}
		}
		else if ((isUpdate(security))) {
			Iterator iter = creditDefaultSwapsDetails.iterator();
			while (iter.hasNext()) {
				CreditDefaultSwapsDetail creditDefaultSwapsDetail = (CreditDefaultSwapsDetail) iter.next();

				creditDefaultSwapsDetail.setUpdateStatusIndicator(String.valueOf(IEaiConstant.UPDATEINDICATOR));
				creditDefaultSwapsDetail.setChangeIndicator(String.valueOf(IEaiConstant.CHANGEINDICATOR));
				indFixedcreditSwapsDetails.add(creditDefaultSwapsDetail);
			}
		}
		return indFixedcreditSwapsDetails;
	}

	public MarketableSecurity fixMarketableSecurityInd(MarketableSecurity marketableSecurity, ApprovedSecurity security) {
		if (isCreate(security)) {

			marketableSecurity.setUpdateStatusIndicator(String.valueOf(IEaiConstant.CREATEINDICATOR));
			marketableSecurity.setChangeIndicator(String.valueOf(IEaiConstant.CHANGEINDICATOR));
		}
		else if ((isUpdate(security))) {
			marketableSecurity.setUpdateStatusIndicator(String.valueOf(IEaiConstant.UPDATEINDICATOR));
			marketableSecurity.setChangeIndicator(String.valueOf(IEaiConstant.CHANGEINDICATOR));
		}
		return marketableSecurity;
	}

	public Vector fixPortFolioItemsInd(Vector portFolioItems, ApprovedSecurity security) {
		if (portFolioItems == null) {
			portFolioItems = new Vector();
		}
		Vector indFixedportFolioItems = new Vector();
		if (isCreate(security)) {
			Iterator iter = portFolioItems.iterator();
			while (iter.hasNext()) {
				PortfolioItem portfolioItem = (PortfolioItem) iter.next();

				portfolioItem.setUpdateStatusIndicator(String.valueOf(IEaiConstant.CREATEINDICATOR));
				portfolioItem.setChangeIndicator(String.valueOf(IEaiConstant.CHANGEINDICATOR));
				indFixedportFolioItems.add(portfolioItem);
			}
		}
		else if ((isUpdate(security))) {
			Iterator iter = portFolioItems.iterator();
			while (iter.hasNext()) {
				PortfolioItem portfolioItem = (PortfolioItem) iter.next();

				portfolioItem.setUpdateStatusIndicator(String.valueOf(IEaiConstant.UPDATEINDICATOR));
				portfolioItem.setChangeIndicator(String.valueOf(IEaiConstant.CHANGEINDICATOR));
				indFixedportFolioItems.add(portfolioItem);
			}
		}
		return indFixedportFolioItems;
	}

	public DocumentSecurity fixDocumentDetailInd(DocumentSecurity documentSecurity, ApprovedSecurity security) {
		if (isCreate(security)) {

			documentSecurity.setUpdateStatusIndicator(String.valueOf(IEaiConstant.CREATEINDICATOR));
			documentSecurity.setChangeIndicator(String.valueOf(IEaiConstant.CHANGEINDICATOR));
		}
		else if ((isUpdate(security))) {
			documentSecurity.setUpdateStatusIndicator(String.valueOf(IEaiConstant.UPDATEINDICATOR));
			documentSecurity.setChangeIndicator(String.valueOf(IEaiConstant.CHANGEINDICATOR));
		}
		return documentSecurity;
	}

	public OthersSecurity fixOthersDetailInd(OthersSecurity othersSecurity, ApprovedSecurity security) {
		if (isCreate(security)) {

			othersSecurity.setUpdateStatusIndicator(String.valueOf(IEaiConstant.CREATEINDICATOR));
			othersSecurity.setChangeIndicator(String.valueOf(IEaiConstant.CHANGEINDICATOR));
		}
		else if ((isUpdate(security))) {
			othersSecurity.setUpdateStatusIndicator(String.valueOf(IEaiConstant.UPDATEINDICATOR));
			othersSecurity.setChangeIndicator(String.valueOf(IEaiConstant.CHANGEINDICATOR));
		}
		return othersSecurity;
	}

	public GuaranteeSecurity fixGuaranteeDetailInd(GuaranteeSecurity guaranteeSecurity, ApprovedSecurity security) {
		if (isCreate(security)) {

			guaranteeSecurity.setUpdateStatusIndicator(String.valueOf(IEaiConstant.CREATEINDICATOR));
			guaranteeSecurity.setChangeIndicator(String.valueOf(IEaiConstant.CHANGEINDICATOR));
		}
		else if ((isUpdate(security))) {
			guaranteeSecurity.setUpdateStatusIndicator(String.valueOf(IEaiConstant.UPDATEINDICATOR));
			guaranteeSecurity.setChangeIndicator(String.valueOf(IEaiConstant.CHANGEINDICATOR));
		}
		return guaranteeSecurity;
	}

	public PropertySecurity fixPropertyDetailInd(PropertySecurity propertySecurity, ApprovedSecurity security) {
		if (isCreate(security)) {

			propertySecurity.setUpdateStatusIndicator(String.valueOf(IEaiConstant.CREATEINDICATOR));
			propertySecurity.setChangeIndicator(String.valueOf(IEaiConstant.CHANGEINDICATOR));
		}
		else if ((isUpdate(security))) {
			propertySecurity.setUpdateStatusIndicator(String.valueOf(IEaiConstant.UPDATEINDICATOR));
			propertySecurity.setChangeIndicator(String.valueOf(IEaiConstant.CHANGEINDICATOR));
		}
		return propertySecurity;
	}

}
