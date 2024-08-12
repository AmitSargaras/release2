/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBCollateralBean.java,v 1.94 2006/09/26 03:11:32 hshii Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import org.springframework.util.CollectionUtils;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.GeneralChargeException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.sharesecurity.bus.EBShareSecurityLocal;
import com.integrosys.cms.app.sharesecurity.bus.EBShareSecurityLocalHome;
import com.integrosys.cms.app.sharesecurity.bus.IShareSecurity;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.batch.common.BatchResourceFactory;

/**
 * Entity bean implementation for Collateral entity.
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.94 $
 * @since $Date: 2006/09/26 03:11:32 $ Tag: $Name: $
 */
public abstract class EBCollateralBean implements ICollateral, EntityBean {

	private static final long serialVersionUID = 5547327996562878394L;

	/**
	 * The container assigned reference to the entity.
	 */
	private EntityContext context;

	/**
	 * A list of methods to be excluded during create collateral.
	 */
	private static final String[] EXCLUDE_METHOD_CREATE = new String[] { "getCollateralID", "getSourceValuation",
			"getValuationFromLOS", "getValuationIntoCMS" };

	/**
	 * A list of methods to be excluded during update of collateral. These
	 * include collateral primary key and information from SCI.
	 */
	private static final String[] EXCLUDE_METHOD_UPDATE = new String[] { "getCollateralID", "getSCISubTypeValue",
			"getSCICurrencyCode", "getSCIFSVDate", "getSCIFSV", "getSCIBookingLocationID", "getSCITypeValue",
			"getSourceValuation", "getValuationFromLOS", "getValuationIntoCMS" };

	/**
	 * A list of methods to be excluded when getting the collateral without
	 * child references.
	 */
	private static final String[] EXCLUDE_METHOD_GET = new String[] { "getValuation", "getValuationHistory",
			"getPledgors", "getSecApportionment", "getSourceValuation", "getValuationFromLOS", "getValuationIntoCMS" };


	
	public ISystemBankBranch getBranchName(){
				return	null;
	       }
	  
	public void setBranchName(ISystemBankBranch branchName) {
			
        
       	branchName=null;
     
      }

	
	
	
	/**
	 * Get collateral ID.
	 * 
	 * @return long
	 */
	public long getCollateralID() {
		return getEBCollateralID().longValue();
	}

	/**
	 * set collateral ID.
	 * 
	 * @param collateralID is of type long
	 */
	public void setCollateralID(long collateralID) {
		setEBCollateralID(new Long(collateralID));
	}

	/**
	 * Get margin value.
	 * 
	 * @return double
	 */
	public double getMargin() {
		if (getEBMargin() == null) {
			return ICMSConstant.DOUBLE_INVALID_VALUE;
		}
		else {
			return getEBMargin().doubleValue();
		}
	}

	/**
	 * Set margin value.
	 * 
	 * @param margin of type double
	 */
	public void setMargin(double margin) {
		// if (margin == ICMSConstant.DOUBLE_INVALID_VALUE)
		// to cater for both -1 and -0.01 invalid values
		if (margin < 0) {
			setEBMargin(null);
		}
		else {
			setEBMargin(new Double(margin));
		}
	}

	/**
	 * Get subtype of this collateral.
	 * 
	 * @return ICollateralSubType
	 */
	public ICollateralSubType getCollateralSubType() {
		return getCollateralSubTypeCMR().getValue();
	}

	/**
	 * Get type of this collateral.
	 * 
	 * @return ICollateralType
	 */
	public ICollateralType getCollateralType() {
		return getCollateralSubTypeCMR().getValue();
	}

	/**
	 * Get collateral valuation.
	 * 
	 * @return IValuation
	 */
	public IValuation getValuation() {
		IValuation[] valuationList = getAllValuationSortByValID();

		if ((valuationList == null) || (valuationList.length == 0)) {
			return null;
		}
		else {
			return valuationList[valuationList.length - 1];
		}
	}

	/**
	 * Get collateral's valuation history.
	 * 
	 * @return a list of valuation history
	 */
	public IValuation[] getValuationHistory() {
		IValuation[] valuationList = getAllValuationSortByValID();

		if ((valuationList == null) || (valuationList.length == 0)) {
			return null;
		}

		int totHistory = valuationList.length - 1;
		IValuation[] history = new OBValuation[totHistory];

		for (int i = 0; i < totHistory; i++) {
			history[i] = valuationList[i];
		}

		return history;
	}

	/**
	 * Get collateral limit charges.
	 * 
	 * @return a list of limit charges
	 */
	public ILimitCharge[] getLimitCharges() {
		Collection c = getLimitChargesCMR();

		if (c != null) {
			Iterator i = getLimitChargesCMR().iterator();
			ArrayList arrayList = new ArrayList();

			while (i.hasNext()) {
				EBLimitChargeLocal theEjb = (EBLimitChargeLocal) i.next();
				ILimitCharge charge = theEjb.getValue();
				if ((charge.getStatus() != null) && charge.getStatus().equals(ICMSConstant.STATE_DELETED)) {
					continue;
				}
				arrayList.add(charge);
			}

			return ((OBLimitCharge[]) arrayList.toArray(new OBLimitCharge[0]));
		}

		return null;
	}

	/**
	 * Get limits associated to this collateral.
	 * 
	 * @return ICollateralLimitMap[]
	 */
	public ICollateralLimitMap[] getCollateralLimits() {
		EBCollateralLimitMapLocalHome ejbHome = getEBCollateralLimitMapLocalHome();
		try {
			Iterator i = ejbHome.findByCollateralID(getCollateralID()).iterator();
			ArrayList arrList = new ArrayList();

			ArrayList limitIDList = new ArrayList();
			while (i.hasNext()) {
				ICollateralLimitMap obj = ((EBCollateralLimitMapLocal) i.next()).getValue();
				Long strLimitID = new Long(obj.getLimitID());
				if (!limitIDList.contains(strLimitID)) {
					limitIDList.add(strLimitID);
				}

				arrList.add(obj);
			}

			ILimitDAO limitDAO = LimitDAOFactory.getDAO();
			HashMap map = limitDAO.getLimitProductTypeByLimitIDList(limitIDList);

			for (int n = 0; n < arrList.size(); n++) {
				ICollateralLimitMap obj = (ICollateralLimitMap) arrList.get(n);
				String productType = (String) map.get(String.valueOf(obj.getLimitID()));
				// DefaultLogger.debug(this, "<<<<<< " +
				// String.valueOf(obj.getLimitID()) + "\t" + productType);
				obj.setLimitType(productType);
				arrList.set(n, obj);
			}

			return ((OBCollateralLimitMap[]) arrList.toArray(new OBCollateralLimitMap[0]));
		}
		catch (FinderException e) {
			return null;
		}
		catch (Exception e) {
			return null;
		}
	}

	/**
	 * Get filtered collateral limit maps.
	 * 
	 * @return ICollateralLimitMap[]
	 */
	public ICollateralLimitMap[] getCurrentCollateralLimits() {
		return null;
	}

	/**
	 * Get pledgors of the collateral.
	 * 
	 * @return a list of pledgors
	 */
	public ICollateralPledgor[] getPledgors() {
		Iterator i = getPledgorsCMR().iterator();
		ArrayList arrayList = new ArrayList();

		while (i.hasNext()) {
			EBCollateralPledgorLocal theEjb = (EBCollateralPledgorLocal) i.next();
			ICollateralPledgor item = theEjb.getValue();
			if (item.getSCIPledgorMapStatus() != null
					&& item.getSCIPledgorMapStatus().equals(ICMSConstant.HOST_STATUS_DELETE))
				continue;

			arrayList.add(item);
		}

		ICollateralPledgor[] pledgors = (OBCollateralPledgor[]) arrayList.toArray(new OBCollateralPledgor[0]);
		CollateralPledgorComparator comp = new CollateralPledgorComparator();
		Arrays.sort(pledgors, comp);

		return (OBCollateralPledgor[]) arrayList.toArray(new OBCollateralPledgor[0]);
	}

	/**
	 * Get a list of insurance policies of the collateral.
	 * 
	 * @return IInsurancePolicy[]
	 */
	public IInsurancePolicy[] getInsurancePolicies() {
		Iterator i = getInsurancePolicyCMR().iterator();
		ArrayList list = new ArrayList();

		while (i.hasNext()) {
			EBInsurancePolicyLocal theEjb = (EBInsurancePolicyLocal) i.next();
			IInsurancePolicy item = theEjb.getValue();
			if ((item.getStatus() != null) && item.getStatus().equals(ICMSConstant.STATE_DELETED)) {
				continue;
			}
			list.add(item);
		}
		return (OBInsurancePolicy[]) list.toArray(new OBInsurancePolicy[0]);
	}
	
	public IAddtionalDocumentFacilityDetails[] getAdditonalDocFacDetails() {
		Iterator i = getAddtionalDocumentFacilityDetailsCMR().iterator();
		ArrayList list = new ArrayList();

		while (i.hasNext()) {
			EBAddtionalDocumentFacilityDetailsLocal theEjb = (EBAddtionalDocumentFacilityDetailsLocal) i.next();
			IAddtionalDocumentFacilityDetails item = theEjb.getValue();
			if ((item.getStatus() != null) && item.getStatus().equals(ICMSConstant.STATE_DELETED)) {
				continue;
			} 
			list.add(item);
		}
		return (OBAddtionalDocumentFacilityDetails[]) list.toArray(new OBAddtionalDocumentFacilityDetails[0]);
	}

	public List getSecApportionment() {
		List lst = new ArrayList();
		try {
			lst = new SecApportionmentDAO().getApportionmentsForSecurity(getCollateralID());
		}
		catch (SearchDAOException ex) {
			// ignore
		}
		return lst;
	}

	// FOR SourceType == "A" . ie automatic for System Valuation Details
	public IValuation getSourceValuation() {
		IValuation iValuation = null;
		String sourceType = ICMSConstant.VALUATION_SOURCE_TYPE_A;
		ICollateralDAO dao = CollateralDAOFactory.getDAO();
		try {
			iValuation = dao.getSourceValuation(getCollateralID(), sourceType);
			// if (iValuation == null) {
			// DefaultLogger.debug(this,
			// ">>>>>>>>>>>>>>>>>>>>......**** in created new OBValuation");
			// iValuation = new OBValuation();
			// iValuation.setSourceType(sourceType);
			// iValuation.setValuationDate(new Date());
			// iValuation.setCurrencyCode(getCurrencyCode());
			// }
		}
		catch (SearchDAOException ex) {
			// ignore
		}
		return iValuation;

	}

	public void setSourceValuation(IValuation shareSecArray) {

	}

	// FOR SourceType == "S" . ie for from LOS/Source System
	public IValuation[] getValuationFromLOS() {
		IValuation[] iValuation = null;
		ICollateralDAO dao = CollateralDAOFactory.getDAO();
		try {
			iValuation = dao.getValuationFromLOS(getCollateralID());
		}
		catch (SearchDAOException ex) {
			// ignore
		}
		return iValuation;
	}

	public void setValuationFromLOS(IValuation[] shareSecArray) {

	}

	// FOR SourceType == "M" . ie manual for Input into GCMS
	public IValuation getValuationIntoCMS() {
		IValuation iValuation = null;
		String sourceType = ICMSConstant.VALUATION_SOURCE_TYPE_M;
		ICollateralDAO dao = CollateralDAOFactory.getDAO();
		try {
			iValuation = dao.getSourceValuation(getCollateralID(), sourceType);
		}
		catch (SearchDAOException ex) {
			// ignore
		}
		return iValuation;
	}

	public void setValuationIntoCMS(IValuation shareSecArray) {

	}

	/**
	 * Get if the Legal Enforceability .
	 * 
	 * @return String
	 */
	public String getIsLE() {
		String isByCR = getEBIsLE();
		/*
		 * if (isByCR != null && isByCR.equals (ICMSConstant.TRUE_VALUE)) {
		 * return true; } return false;
		 */
		return isByCR;
	}

	/**
	 * Set the legal enforceability .
	 * 
	 * @param isLE is of type String
	 */
	public void setIsLE(String isLE) {
		/*
		 * if (isLE) { setEBIsLE (ICMSConstant.TRUE_VALUE); } else { setEBIsLE
		 * (ICMSConstant.FALSE_VALUE); }
		 */
		setEBIsLE(isLE);
	}

	/**
	 * Get if the Legal Enforceability by charge ranking.
	 * 
	 * @return boolean
	 */
	public boolean getIsLEByChargeRanking() {
		String isByCR = getEBIsLEByChargeRanking();
		if ((isByCR != null) && isByCR.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	/**
	 * Set the legal enforceability by charge ranking.
	 * 
	 * @param isLEByChargeRanking is of type boolean
	 */
	public void setIsLEByChargeRanking(boolean isLEByChargeRanking) {
		if (isLEByChargeRanking) {
			setEBIsLEByChargeRanking(ICMSConstant.TRUE_VALUE);
		}
		else {
			setEBIsLEByChargeRanking(ICMSConstant.FALSE_VALUE);
		}
	}

	/**
	 * Get if the Legal Enforceability by jurisdiction.
	 * 
	 * @return boolean
	 */
	public boolean getIsLEByJurisdiction() {
		String isByJuris = getEBIsLEByJurisdiction();
		if ((isByJuris != null) && isByJuris.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	/**
	 * Set the legal enforceability by jurisdiction.
	 * 
	 * @param isLEByJurisdiction is of type boolean
	 */
	public void setIsLEByJurisdiction(boolean isLEByJurisdiction) {
		if (isLEByJurisdiction) {
			setEBIsLEByJurisdiction(ICMSConstant.TRUE_VALUE);
		}
		else {
			setEBIsLEByJurisdiction(ICMSConstant.FALSE_VALUE);
		}
	}

	/**
	 * Get if the Legal Enforceability by governing laws.
	 * 
	 * @return boolean
	 */
	public boolean getIsLEByGovernLaws() {
		String isByGov = getEBIsLEByGovernLaws();
		if ((isByGov != null) && isByGov.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	/**
	 * Set if the legal enforceability by governing laws.
	 * 
	 * @param isLEByGovernLaws is of type boolean
	 */
	public void setIsLEByGovernLaws(boolean isLEByGovernLaws) {
		if (isLEByGovernLaws) {
			setEBIsLEByGovernLaws(ICMSConstant.TRUE_VALUE);
		}
		else {
			setEBIsLEByGovernLaws(ICMSConstant.FALSE_VALUE);
		}
	}

	/**
	 * Get latest current market value.
	 * 
	 * @return Amount
	 */
	public Amount getCMV() {
		if (getEBCMV() != null) {
//			return new Amount(getEBCMV().doubleValue(), getCMVCcyCode());
			return new Amount(getEBCMV(),new CurrencyCode(getCMVCcyCode()));
		}
		else {
			return null;
		}
	}

	/**
	 * Set latest current market value.
	 * 
	 * @param cmv of type Amount
	 */
	public void setCMV(Amount cmv) {
		if (cmv != null) {
//			setEBCMV(new Double(cmv.getAmountAsDouble()));
			setEBCMV(cmv.getAmountAsBigDecimal());
		}
		else {
			setEBCMV(null);
		}
	}

	/**
	 * Get latest forced sale value.
	 * 
	 * @return Amount
	 */
	public Amount getFSV() {
		if (getEBFSV() != null) {
			return new Amount(getEBFSV().doubleValue(), getFSVCcyCode());
		}
		else {
			return null;
		}
	}

	/**
	 * Set latest forced sale value.
	 * 
	 * @param fsv of type Amount
	 */
	public void setFSV(Amount fsv) {
		if (fsv != null) {
			setEBFSV(new Double(fsv.getAmountAsDouble()));
		}
		else {
			setEBFSV(null);
		}
	}

	/**
	 * Get FSV balance after collateral allocation.
	 * 
	 * @return Amount
	 */
	public Amount getFSVBalance() {
		if (getEBFSVBalance() == null) {
			return null;
		}
		return new Amount(getEBFSVBalance(), new CurrencyCode(getEBFSVBalanceCcyCode()));
	}

	/**
	 * Set FSV balance after collateral allocation.
	 * 
	 * @param fsvBalance of type Amount
	 */
	public void setFSVBalance(Amount fsvBalance) {
		setEBFSVBalance(fsvBalance == null ? null : fsvBalance.getAmountAsBigDecimal());
		setEBFSVBalanceCcyCode(fsvBalance == null ? null : fsvBalance.getCurrencyCode());
	}

	/**
	 * Get if it is pari passu.
	 * 
	 * @return boolean
	 */
	public boolean getIsPariPassu() {
		String isPariPassu = getEBIsPariPassu();
		if ((isPariPassu != null) && isPariPassu.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	/**
	 * Set if it is pari passu.
	 * 
	 * @param isPariPassu of type boolean
	 */
	public void setIsPariPassu(boolean isPariPassu) {
		if (isPariPassu) {
			setEBIsPariPassu(ICMSConstant.TRUE_VALUE);
		}
		else {
			setEBIsPariPassu(ICMSConstant.FALSE_VALUE);
		}
	}

	/**
	 * Get if security is perfected.
	 * 
	 * @return boolean
	 */
	public boolean getIsPerfected() {
		String isPerfected = getEBIsPerfected();
		if ((isPerfected != null) && isPerfected.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	/**
	 * Set if the security is perfected.
	 * 
	 * @param isPerfected of type boolean
	 */
	public void setIsPerfected(boolean isPerfected) {
		if (isPerfected) {
			setEBIsPerfected(ICMSConstant.TRUE_VALUE);
		}
		else {
			setEBIsPerfected(ICMSConstant.FALSE_VALUE);
		}
	}

	/**
	 * Get force sale value from SCI.
	 * 
	 * @return Amount
	 */
	public Amount getSCIFSV() {
		return new Amount(getEBSCIFSV(), getCurrencyCode());
	}

	/**
	 * Set force sale value from SCI.
	 * 
	 * @param sciFSV of type Amount
	 */
	public void setSCIFSV(Amount sciFSV) {
		if (sciFSV != null) {
			setEBSCIFSV(sciFSV.getAmountAsDouble());
		}
	}

	public Amount getNetRealisableAmount() {
		BigDecimal ebAmt = getEBNetRealisableAmount();
		if (ebAmt != null) {
			return new Amount(ebAmt, new CurrencyCode(getSCICurrencyCode()));
		}
		else {
			return null;
		}
	}

	public void setNetRealisableAmount(Amount nrAmt) {
		if (nrAmt != null) {
			setEBNetRealisableAmount(nrAmt.getAmountAsBigDecimal());
		}
		else {
			setEBNetRealisableAmount(null);
		}
	}

	public Amount getReservePrice() {
		if (getEBReservePrice() != null) {
			return new Amount(getEBReservePrice().doubleValue(), getCurrencyCode());
		}
		else {
			return null;
		}
	}

	public void setReservePrice(Amount reservePrice) {
		if (reservePrice != null) {
			setEBReservePrice(new Double(reservePrice.getAmountAsDouble()));
		}
		else {
			setEBReservePrice(null);
		}
	}
	
	/**/
	public float getSpread() {
			return getEBSpread();
	}

	public void setSpread(float spread) {
			setEBSpread(spread);
	}
	
	/**/

	public boolean getIsBorrowerDependency() {
		if ((getIsBorrowerDependencyStr() != null) && (getIsBorrowerDependencyStr().equals(ICMSConstant.TRUE_VALUE)))
			return true;
		return false;
	}

	public void setIsBorrowerDependency(boolean isBorrowerDependency) {
		if (isBorrowerDependency) {
			setIsBorrowerDependencyStr(ICMSConstant.TRUE_VALUE);
			return;
		}
		setIsBorrowerDependencyStr(ICMSConstant.FALSE_VALUE);
	}

	public Map getInsurance() {
		return new HashMap();
	}

	public void setInsurance(Map insurance) {
	}

	public void setCollateralSubType(ICollateralSubType collateralSubType) {
	}

	public void setCollateralType(ICollateralType collateralType) {
	}

	public void setValuation(IValuation valuation) {
	}

	public void setValuationHistory(IValuation[] valuationHistory) {
	}

	public void setLimitCharges(ILimitCharge[] limitCharges) {
	}

	public void setCollateralLimits(ICollateralLimitMap[] collateralLimits) {
	}

	public void setPledgors(ICollateralPledgor[] pledgors) {
	}

	public void setInsurancePolicies(IInsurancePolicy[] insurancePolicies) {
	}
	
	public void setAdditonalDocFacDetails(IAddtionalDocumentFacilityDetails[] additonalDocFacDetails) {
	}

	public void setSecApportionment(List apportionments) {
	}

	public abstract Long getEBCollateralID();

	public abstract void setEBCollateralID(Long eBCollateralID);

	public abstract Double getEBMargin();

	public abstract void setEBMargin(Double eBMargin);

	/*public abstract Double getEBCMV();

	public abstract void setEBCMV(Double eBCMV);*/
	
	public abstract BigDecimal getEBCMV();

	public abstract void setEBCMV(BigDecimal eBCMV);

	public abstract Double getEBFSV();

	public abstract void setEBFSV(Double eBFSV);

	public abstract BigDecimal getEBFSVBalance();

	public abstract void setEBFSVBalance(BigDecimal eBFSVBalance);

	public abstract String getEBFSVBalanceCcyCode();

	public abstract void setEBFSVBalanceCcyCode(String eBFSVBalanceCcyCode);

	public abstract String getEBIsPariPassu();

	public abstract void setEBIsPariPassu(String eBIsPariPassu);

	public abstract String getEBIsPerfected();

	public abstract void setEBIsPerfected(String eBIsPerfected);

	public abstract String getEBIsLE();

	public abstract void setEBIsLE(String eBIsLE);

	public abstract String getEBIsLEByChargeRanking();

	public abstract void setEBIsLEByChargeRanking(String eBIsLEByChargeRanking);

	public abstract String getEBIsLEByJurisdiction();

	public abstract void setEBIsLEByJurisdiction(String eBIsLEByJurisdiction);

	public abstract String getEBIsLEByGovernLaws();

	public abstract void setEBIsLEByGovernLaws(String eBIsLEByGovernLaws);

	public abstract double getEBSCIFSV();

	public abstract void setEBSCIFSV(double eBSCIFSV);

	public abstract String getSubTypeName();

	public abstract void setSubTypeName(String subTypeName);

	public abstract String getTypeName();

	public abstract void setTypeName(String typeName);

	public abstract Collection getPledgorsCMR();

	public abstract void setPledgorsCMR(Collection pledgorsCMR);

	public abstract EBCollateralSubTypeLocal getCollateralSubTypeCMR();

	public abstract void setCollateralSubTypeCMR(EBCollateralSubTypeLocal collateralSubTypeCMR);

	public abstract Collection getLimitChargesCMR();

	public abstract void setLimitChargesCMR(Collection limitChargesCMR);

	public abstract Collection getValuationCMR();

	public abstract void setValuationCMR(Collection valuationCMR);

	public abstract Collection getInsurancePolicyCMR();

	public abstract void setInsurancePolicyCMR(Collection insurancePolicyCMR);
	
	public abstract Collection getAddtionalDocumentFacilityDetailsCMR();

	public abstract void setAddtionalDocumentFacilityDetailsCMR(Collection addtionalDocumentFacilityDetailsCMR);

	public abstract Collection getSecApportionmentCMR();

	public abstract void setSecApportionmentCMR(Collection secApportionmentCMR);

	// public abstract Collection getInstrumentCMR();

	// public abstract void setInstrumentCMR(Collection instrumentCMR);

	public abstract Collection getShareSecCMR();

	public abstract void setShareSecCMR(Collection shareSecCMR);
	
		public abstract String getEBSecurityOrganization();

	public abstract void setEBSecurityOrganization(String securityOrganization);

	public abstract BigDecimal getEBNetRealisableAmount();

	public abstract void setEBNetRealisableAmount(BigDecimal amt);

	public abstract Double getEBReservePrice();

	public abstract void setEBReservePrice(Double eBReservePrice);
	
	public abstract float getEBSpread();

	public abstract void setEBSpread(float eBSpread);
	
	public abstract String getIsBorrowerDependencyStr();

	public abstract void setIsBorrowerDependencyStr(String isBorrowerDependency);

	public abstract long getVersionTime();

	public String getSecurityOrganization() {
		return getEBSecurityOrganization();
	}

	public void setSecurityOrganization(String org) {
		setEBSecurityOrganization(org);
	}

	/**
	 * Get the collateral business object.
	 * 
	 * @return collateral based on its subtype
	 */
	public ICollateral getValue() throws CollateralException {
		return getValue(true);
	}

	/**
	 * Get the collateral business object. getValue (true) will return the same
	 * object as getValue(). getValue(false) will return collateral without
	 * child references.
	 * 
	 * @param withRef true if with child references, otherwise false
	 * @return collateral based on its type/subtype
	 * @throws CollateralException on error getting the value
	 */
	public ICollateral getValue(boolean withRef) throws CollateralException {
		ICollateral collateral = CollateralDetailFactory.getOB(getCollateralSubType());
		try {
			if (withRef) {
				AccessorUtil.copyValue(this, collateral);
			}
			else {
				AccessorUtil.copyValue(this, collateral, EXCLUDE_METHOD_GET);
			}
		}
		catch (Exception e) {
			throw new CollateralException("fail to copy existing copy value to a new instance, collateral id ["
					+ getCollateralID() + "]", e);
		}
		return collateral;
	}

	/**
	 * Copy data from this entity bean to the collateral given.
	 * 
	 * @param collateral any of collateral subtypes
	 * @return collateral
	 */
	public ICollateral getValue(ICollateral collateral) {
		try {
			ICollateral newCol = (ICollateral) AccessorUtil.deepClone(collateral);
			AccessorUtil.copyValue(this, newCol);

			return newCol;
		}
		catch (Exception e) {
			throw new EJBException("fail to clone a new collateral, collateral id [" + collateral.getCollateralID()
					+ "]", e);
		}
	}

	/**
	 * Set the collateral to this entity.
	 * 
	 * @param collateral is of type ICollateral
	 * @throws VersionMismatchException if the entity version is different from
	 *         backend
	 */
	public void setValue(ICollateral collateral) throws VersionMismatchException {
		checkVersionMismatch(collateral);
		AccessorUtil.copyValue(collateral, this, EXCLUDE_METHOD_UPDATE);
		setReferences(collateral, false, false);
		// setInstrumentArrayPersist(collateral.getInstrumentArray());
		this.setVersionTime(VersionGenerator.getVersionNumber());
		this.setTypeName(collateral.getCollateralSubType().getTypeName());
		this.setSubTypeName(collateral.getCollateralSubType().getSubTypeName());
	}

	/**
	 * Set new version for this entity.
	 * 
	 * @return newly generated version time
	 * @throws VersionMismatchException if the entity version is different from
	 *         backend
	 */
	public long updateVersionTime(ICollateral collateral) throws VersionMismatchException {
		checkVersionMismatch(collateral);
		this.setVersionTime(VersionGenerator.getVersionNumber());
		return this.getVersionTime();
	}

	/**
	 * Create references for the collateral.
	 * 
	 * @param collateral of type ICollateral
	 * @param versionTime of type long
	 * @throws VersionMismatchException if collateral version is different from
	 *         backend
	 */
	public void createDependants(ICollateral collateral, long versionTime, boolean createPledgor)
			throws VersionMismatchException {
		checkVersionMismatch(versionTime);
		this.setVersionTime(VersionGenerator.getVersionNumber());
		setReferences(collateral, true, createPledgor);
	}

	/**
	 * Revaluate this collateral. It will create new valuation for the
	 * collateral and the previous valuation will become history.
	 * 
	 * @param newValuation re-valuation value
	 */
	public IValuation createValuation(IValuation newValuation) {
		try {
			newValuation.setValuationID(ICMSConstant.LONG_MIN_VALUE);
			setValuationRef(newValuation);
			return getValuation();
		}
		catch (Exception e) {
			throw new EJBException("failed to create valuation, collateral id [" + newValuation.getCollateralID()
					+ "], valuation type [" + newValuation.getValuationType() + "]", e);
		}
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param collateral of type ICollateral
	 * @throws CreateException on error creating the entity object
	 */
	public Long ejbCreate(ICollateral collateral) throws CreateException {
		try {
			String colID = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_COLLATERAL, true);

			AccessorUtil.copyValue(collateral, this, EXCLUDE_METHOD_CREATE);

			Long collateralId = Long.valueOf(colID);
			setEBCollateralID(collateralId);
			setVersionTime(VersionGenerator.getVersionNumber());

			
			 if ((collateral.getSCISecurityID() == null) ||
			 collateral.getSCISecurityID().trim().equals("")) {
			 setSCISecurityID(colID);
			 }
			 
			if (collateral.getLosCollateralRef() == null || collateral.getLosCollateralRef().trim().length() == 0) {
				setLosCollateralRef(colID);
			}

			return collateralId;
		}
		catch (Exception e) {
			CreateException cex = new CreateException("fail to create collateral, host id ["
					+ collateral.getSCISecurityID() + "]");
			cex.initCause(e);
			throw cex;
		}
	}

	/**
	 * The container invokes this method after invoking the ejbCreate method
	 * with the same arguments.
	 * 
	 * @param collateral of type ICollateral
	 * @throws CreateException on error creating the references
	 */
	public void ejbPostCreate(ICollateral collateral) throws CreateException {
		try {
			setCollateralSubTypeRef(collateral.getCollateralSubType());
			setInstrumentArrayPersist(collateral.getInstrumentArray());
		}
		catch (Exception e) {
			throw new EJBException("fail to update collateral sub type ref and instrument, collateral id ["
					+ collateral.getCollateralID() + "]", e);
		}
	}

	/**
	 * Search collateral given the criteria.
	 * 
	 * @param criteria collateral search criteria
	 * @return search result
	 * @throws SearchDAOException on error searching the collateral
	 */
	public SearchResult ejbHomeSearchCollateral(CollateralSearchCriteria criteria) throws SearchDAOException {
		ICollateralDAO dao = CollateralDAOFactory.getDAO();
		return dao.searchCollateral(criteria);
	}

	public IInstrument[] getInstrumentArray() {
		try {
			EBInstrumentLocalHome ebInstrumentLocalHome = getEBInstrumentLocalHome();
			Iterator i = ebInstrumentLocalHome.findByCollateralID(getEBCollateralID()).iterator();
			// Iterator i = getInstrumentCMR().iterator();
			ArrayList arrayList = new ArrayList();
			while (i.hasNext()) {
				arrayList.add(((EBInstrumentLocal) i.next()).getValue());
			}
			IInstrument[] instumentArray = (IInstrument[]) arrayList.toArray(new OBInstrument[0]);
			InstrumentComparator comp = new InstrumentComparator();
			Arrays.sort(instumentArray, comp);
			return (IInstrument[]) arrayList.toArray(new IInstrument[0]);
		}
		catch (Exception e) {
			return null;
		}
	}

	public void setInstrumentArray(IInstrument[] instrumentArray) {
	}

	public void setInstrumentArrayPersist(IInstrument[] instrumentArray) {
		try {
			EBInstrumentLocalHome ebInstrumentLocalHome = getEBInstrumentLocalHome();
			Collection oldInstruments = ebInstrumentLocalHome.findByCollateralID(getEBCollateralID());
			if ((instrumentArray == null) || (instrumentArray.length == 0)) {
				removeInstruments(oldInstruments);
				return;
			}

			Map oldInstrumentMap = new HashMap();
			for (Iterator inter = oldInstruments.iterator(); inter.hasNext();) {
				EBInstrumentLocal theEjb = (EBInstrumentLocal) inter.next();
				IInstrument aInstrument = theEjb.getValue();
				oldInstrumentMap.put(aInstrument.getInstrumentCode(), aInstrument);
			}

			Map existInstrumentMap = new HashMap();
			for (int index = 0; index < instrumentArray.length; index++) {
				if (oldInstrumentMap.get(instrumentArray[index].getInstrumentCode()) == null) {
					instrumentArray[index].setCollateralID(getCollateralID());
					try {
						ebInstrumentLocalHome.create(instrumentArray[index]);
					}
					catch (CreateException e) {
						// ignore
					}
				}
				else {
					existInstrumentMap.put(instrumentArray[index].getInstrumentCode(), instrumentArray[index]);
				}
			}
			// DefaultLogger.debug(this,"Exist : "+existInstrumentMap.size());
			if (oldInstruments.size() != 0) {
				for (Iterator inter = oldInstruments.iterator(); inter.hasNext();) {
					EBInstrumentLocal theEjb = (EBInstrumentLocal) inter.next();
					IInstrument aInstrument = theEjb.getValue();
					if (existInstrumentMap.get(aInstrument.getInstrumentCode()) == null) {
						try {
							theEjb.remove();
						}
						catch (Exception e) {
							// ignore
						}
					}
				}
			}
		}
		catch (Exception e) {
			// ignore
		}
	}

	public IShareSecurity[] getShareSecArray() {
		Iterator i = getShareSecCMR().iterator();
		ArrayList arrayList = new ArrayList();
		while (i.hasNext()) {
			try {
				arrayList.add(((EBShareSecurityLocal) i.next()).getValue());
			}
			catch (Exception e) {
				// ignore
			}
		}
		return (IShareSecurity[]) arrayList.toArray(new IShareSecurity[0]);
	}

	public void setShareSecArray(IShareSecurity[] shareSecArray) {
	}

	public IShareSecurity createShareSecurity(IShareSecurity sec) throws CollateralException {
		try {
			EBShareSecurityLocal local = this.getEBShareSecurityLocalHome().create(sec);
			getShareSecCMR().add(local);
			return local.getValue();
		}
		catch (Exception ex) {
			throw new CollateralException("fail to create share security collateral id [" + sec.getCmsCollateralId()
					+ "]", ex);
		}
	}

	protected EBInstrumentLocalHome getEBInstrumentLocalHome() {
		EBInstrumentLocalHome ejbHome = (EBInstrumentLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_INSTRUMENT_JNDI, EBInstrumentLocalHome.class.getName());
		if (ejbHome == null) {
			throw new EJBException("EBInstrumentLocalHome is Null!");
		}
		return ejbHome;
	}

	private void removeInstruments(Collection instrumentColl) {
		if ((instrumentColl == null) || (instrumentColl.size() == 0)) {
			return;
		}
		for (Iterator inter = instrumentColl.iterator(); inter.hasNext();) {
			try {
				((EBInstrumentLocal) inter.next()).remove();
			}
			catch (Exception e) {
				// ignore
			}
		}
	}

	/**
	 * Get valuation local home object.
	 * 
	 * @return EBValuationLocalHome
	 */
	protected EBValuationLocalHome getEBValuationLocalHome() {
		EBValuationLocalHome ejbHome = (EBValuationLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_COL_VALUATION_LOCAL_JNDI, EBValuationLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBValuationLocalHome is Null!");
		}
		return ejbHome;
	}

	/**
	 * Get collateral pledgor local home.
	 * 
	 * @return EBCollateralPledgorLocalHome
	 */
	protected EBCollateralPledgorLocalHome getEBCollateralPledgorLocalHome() {
		EBCollateralPledgorLocalHome ejbHome = (EBCollateralPledgorLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_COL_PLEDGOR_LOCAL_JNDI, EBCollateralPledgorLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBCollateralPledgorLocalHome is Null!");
		}

		return ejbHome;
	}

	/**
	 * Get collateral limit charge local home.
	 * 
	 * @return EBLimitChargeLocalHome
	 */
	protected EBLimitChargeLocalHome getEBLimitChargeLocalHome() {
		EBLimitChargeLocalHome ejbHome = (EBLimitChargeLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_COL_LIMITCHARGE_LOCAL_JNDI, EBLimitChargeLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBLimitChargeLocalHome is Null!");
		}

		return ejbHome;
	}

	/**
	 * Get collateral insurance policy local home.
	 * 
	 * @return EBInsurancePolicyLocalHome
	 */
	protected EBInsurancePolicyLocalHome getEBInsurancePolicyLocalHome() {
		EBInsurancePolicyLocalHome ejbHome = (EBInsurancePolicyLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_INSURANCE_POLICY_LOCAL_JNDI, EBInsurancePolicyLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBInsurancePolicyLocalHome is Null!");
		}

		return ejbHome;
	}
	
	protected EBAddtionalDocumentFacilityDetailsLocalHome getEBAddtionalDocumentFacilityDetailsLocalHome() {
		EBAddtionalDocumentFacilityDetailsLocalHome ejbHome = (EBAddtionalDocumentFacilityDetailsLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_ADDTIONAL_DOCUMENT_FACILITY_DETAILS_LOCAL_JNDI, EBAddtionalDocumentFacilityDetailsLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBAddtionalDocumentFacilityDetailsLocalHome is Null!");
		}

		return ejbHome;
	}

	/**
	 * Get collateral limit map local home.
	 * 
	 * @return EBCollateralLimitMapLocalHome
	 */
	protected EBCollateralLimitMapLocalHome getEBCollateralLimitMapLocalHome() {
		EBCollateralLimitMapLocalHome ejbHome = (EBCollateralLimitMapLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_COL_LIMIT_MAP_LOCAL_JNDI, EBCollateralLimitMapLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBCollateralLimitMapLocalHome is Null!");
		}

		return ejbHome;
	}

	protected EBSecApportionmentLocalHome getEBSecApportionmentLocalHome() {
		EBSecApportionmentLocalHome ejbHome = (EBSecApportionmentLocalHome) (BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_COL_APPORTIONMENT_LOCAL_JNDI, EBSecApportionmentLocalHome.class.getName()));
		if (ejbHome == null) {
			throw new EJBException("EBSecApportionmentLocalHome is Null!");
		}
		return ejbHome;
	}

	protected EBShareSecurityLocalHome getEBShareSecurityLocalHome() throws Exception {
		return (EBShareSecurityLocalHome) BeanController.getEJBLocalHome(ICMSJNDIConstant.EB_SHARE_SECURITY_LOCAL_JNDI,
				EBShareSecurityLocalHome.class.getName());
	}

	/**
	 * Set the references to this collateral.
	 * 
	 * @param collateral of type ICollateral
	 * @param isAdd true is to create new references, otherwise false
	 */
	protected void setReferences(ICollateral collateral, boolean isAdd, boolean createPledgor) {
		// pledgors map and/or pledgor
		try {
			if (createPledgor) {
				setPledgorsRef(collateral.getPledgors(), isAdd);
			}
			else {
				setPledgorsRef(collateral.getPledgors(), false);
			}
		}
		catch (CreateException e) {
			throw new EJBException("failed to create/update collateral pledgor map or pledgor, collateral id ["
					+ collateral.getCollateralID() + "]", e);
		}

		// collateral limit map
		try {
			setCollateralLimitsRef(collateral.getCollateralLimits(), isAdd);
		}
		catch (CreateException e) {
			throw new EJBException("failed to create/update collateral limit map, collateral id ["
					+ collateral.getCollateralID() + "]", e);
		}

		// [28th Nov 2008]cz: getValuation() = store the latest valuation
		// record only;
		// for info purpose only - do NOT persists
		// OBValuation obVal = (OBValuation) collateral.getValuation();
		// if ((obVal != null) && !obVal.isEmpty()) {
		// setValuationRef(collateral.getValuation());
		// }

		// valuation into CMS
		try {
			setValuationRef(collateral.getValuationIntoCMS());
		}
		catch (CreateException e) {
			throw new EJBException("failed to create/update valuation into CMS, collateral id ["
					+ collateral.getCollateralID() + "]", e);
		}

		// source valuation
		IValuation actualPrevVal = getSourceValuation();
		IValuation stagingCurrVal = collateral.getSourceValuation();
		if ((stagingCurrVal != null) && (stagingCurrVal.getCMV() != null)) {
			if (actualPrevVal == null || actualPrevVal.getValuationDate().before(stagingCurrVal.getValuationDate())
					|| (actualPrevVal.getCMV() == null || !actualPrevVal.getCMV().equals(stagingCurrVal.getCMV()))) {
				try {
					setValuationRef(collateral.getSourceValuation());
				}
				catch (CreateException e) {
					throw new EJBException("failed to create/update source valuation, collateral id ["
							+ collateral.getCollateralID() + "]", e);
				}
			}
		}

		populateChargeIdFromLimitMapToChargeMap(collateral);

		// limit charge map and/or charge detail
		try {
			setLimitChargesRef(collateral.getLimitCharges(), isAdd);
		}
		catch (CreateException e) {
			throw new EJBException("failed to create/update limit charge map or charge detail, collateral id ["
					+ collateral.getCollateralID() + "]", e);
		}
		catch (RemoveException e) {
			throw new EJBException("failed to remove limit charge map or charge detail, collateral id ["
					+ collateral.getCollateralID() + "]", e);
		}

		// insurance policy
		try {
			setInsurancePolicyRef(collateral.getInsurancePolicies(), isAdd);
		}
		catch (CreateException e) {
			throw new EJBException("failed to create/update insurance policys, collateral id ["
					+ collateral.getCollateralID() + "]", e);
		}
		
		try {
			updateSecurityCoverage(collateral.getSecurityCoverage(), collateral.getCollateralID(), isAdd);
		} catch (CollateralException e) {
			DefaultLogger.error(this, e.getMessage(), e);
			throw new EJBException("failed to create/update security coverage, collateral id ["
					+ collateral.getCollateralID() + "]", e);
		} 
		
		try {
			setAddtionalDocumentFacilityDetailsRef(collateral.getAdditonalDocFacDetails(), isAdd);
		}
		catch (CreateException e) {
			throw new EJBException("failed to create/update Additional Facility Document Details, collateral id ["
					+ collateral.getCollateralID() + "]", e);
		}

		// security apportionment
		/*
		 * try { setSecApportionmentRef(collateral.getSecApportionment(),
		 * isAdd); } catch (CreateException e) { throw newEJBException(
		 * "failed to create/update security apportionment, collateral id [" +
		 * collateral.getCollateralID() + "]", e); } catch (RemoveException e) {
		 * throw new
		 * EJBException("failed to remove security apportionment, collateral id ["
		 * + collateral.getCollateralID() + "]", e); }
		 */
	}

	/**
	 * To populate the charge id of the limit map created into the limit charge
	 * map, based on the sys generated id in both map.
	 * 
	 * @param collateral the collateral having the collateral limit map and
	 *        limit charge map
	 */
	protected void populateChargeIdFromLimitMapToChargeMap(ICollateral collateral) {
		ILimitCharge[] limitCharges = collateral.getLimitCharges();
		if (limitCharges == null || limitCharges.length == 0) {
			return;
		}

		EBCollateralLimitMapLocalHome collateralLimitMapHome = getEBCollateralLimitMapLocalHome();
		Collection collateralLimitMapLocalCollection = Collections.EMPTY_SET;
		try {
			collateralLimitMapLocalCollection = collateralLimitMapHome.findByCollateralID(getCollateralID());
		}
		catch (FinderException e) {
			throw new EJBException("failed to find collateral limit map using collateral id [" + getCollateralID()
					+ "], is it possible after persistent ?", e);
		}

		for (int i = 0; i < limitCharges.length; i++) {
			ILimitCharge limitCharge = limitCharges[i];
			ICollateralLimitMap[] limitChargeMaps = limitCharge.getLimitMaps();
			if (limitChargeMaps == null || limitChargeMaps.length == 0) {
				continue;
			}

			for (int j = 0; j < limitChargeMaps.length; j++) {
				ICollateralLimitMap limitChargeMap = limitChargeMaps[j];

				for (Iterator itr = collateralLimitMapLocalCollection.iterator(); itr.hasNext();) {
					EBCollateralLimitMapLocal collateralLimitMapLocal = (EBCollateralLimitMapLocal) itr.next();
					ICollateralLimitMap limitMap = collateralLimitMapLocal.getValue();

					if (limitChargeMap.getSCISysGenID() == limitMap.getSCISysGenID()) {
						limitChargeMap.setChargeID(limitMap.getChargeID());
					}
				}
			}
		}
	}

	/**
	 * Check the version of this collateral against the backend version.
	 * 
	 * @param collateral collateral's version to be checked
	 * @throws VersionMismatchException if the entity version is different from
	 *         backend
	 */
	private void checkVersionMismatch(ICollateral collateral) throws VersionMismatchException {
		if (getVersionTime() != collateral.getVersionTime()) {
			throw new VersionMismatchException("Mismatch timestamp! " + collateral.getVersionTime());
		}
	}

	/**
	 * Check the version time against the backend version.
	 * 
	 * @param versionTime version time
	 * @throws VersionMismatchException if the version is different from
	 *         backend's
	 */
	private void checkVersionMismatch(long versionTime) throws VersionMismatchException {
		if (getVersionTime() != versionTime) {
			throw new VersionMismatchException("Mismatch timestamp! " + versionTime);
		}
	}

	/**
	 * Helper method to get a list of valuation sorted by valuation date.
	 * 
	 * @return a list of valuation sorted by date
	 */
	/*
	 * private IValuation[] getAllValuationSortByDate() { Iterator i =
	 * getValuationCMR().iterator(); ArrayList list = new ArrayList();
	 * 
	 * while (i.hasNext()) { EBValuationLocal theEjb = (EBValuationLocal)
	 * i.next(); list.add (theEjb.getValue()); } IValuation[] val =
	 * (OBValuation[]) list.toArray (new OBValuation[0]);
	 * 
	 * Arrays.sort (val, new ValuationComparator
	 * (ValuationComparator.COMPARE_BY_VAL_DATE)); return val; }
	 */

	/**
	 * Helper method to get a list of valuation sorted by valuation date.
	 * 
	 * @return a list of valuation sorted by date
	 */
	private IValuation[] getAllValuationSortByValID() {
		Iterator i = getValuationCMR().iterator();
		ArrayList list = new ArrayList();

		while (i.hasNext()) {
			EBValuationLocal theEjb = (EBValuationLocal) i.next();
			list.add(theEjb.getValue());
		}
		IValuation[] val = (OBValuation[]) list.toArray(new OBValuation[0]);

		Arrays.sort(val, new ValuationComparator(ValuationComparator.COMPARE_BY_VAL_ID));
		return val;
	}

	/**
	 * Set references to pledgors of the collateral.
	 * 
	 * @param pledgors a list of pledgors who pledge the collateral
	 * @param isAdd true is to create, false is to update
	 * @throws CreateException on error creating the references
	 */
	protected void setPledgorsRef(ICollateralPledgor[] pledgors, boolean isAdd) throws CreateException {
		if (pledgors == null) {
			return;
		}

		EBCollateralPledgorLocalHome ejbHome = getEBCollateralPledgorLocalHome();

		Collection c = getPledgorsCMR();

		if (isAdd || (c.size() == 0)) {
			for (int i = 0; i < pledgors.length; i++) {
				c.add(ejbHome.create(pledgors[i]));
			}
			return;
		}

		removeCollateralPledgors(c, pledgors);

		Iterator iterator = c.iterator();
		ArrayList newColPldg = new ArrayList();

		for (int i = 0; i < pledgors.length; i++) {
			ICollateralPledgor pledgor = new OBCollateralPledgor(pledgors[i]);

			boolean found = false;

			while (iterator.hasNext()) {
				EBCollateralPledgorLocal theEjb = (EBCollateralPledgorLocal) iterator.next();
				ICollateralPledgor value = theEjb.getValue();
				if (pledgor.getSPMID() == value.getSPMID()) {
					theEjb.setValue(pledgor);
					found = true;
					break;
				}
			}
			if (!found) {
				newColPldg.add(pledgor);
			}
			iterator = c.iterator();
		}

		iterator = newColPldg.iterator();

		while (iterator.hasNext()) {
			c.add(ejbHome.create((ICollateralPledgor) iterator.next()));
		}
	}

	/**
	 * Set collateral sub type reference.
	 * 
	 * @param colType of type ICollateralSubType
	 * @throws FinderException on error finding the collateral sub type
	 */
	private void setCollateralSubTypeRef(ICollateralSubType colType) throws FinderException {
		EBCollateralSubTypeLocalHome ejbHome = (EBCollateralSubTypeLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_COL_SUBTYPE_LOCAL_JNDI, EBCollateralSubTypeLocalHome.class.getName());

		EBCollateralSubTypeLocal theEjb = ejbHome.findByPrimaryKey(colType.getSubTypeCode());
		setCollateralSubTypeCMR(theEjb);
		setSubTypeName(getCollateralSubType().getSubTypeName());
		setTypeName(getCollateralSubType().getTypeName());
	}

	/**
	 * Set limits associated to this collateral.
	 * 
	 * @param maps of type ICollateralLimitMap[]
	 * @throws CreateException on error creating limit maps
	 */
	public void setCollateralLimitsRef(ICollateralLimitMap[] maps, boolean isAdd) throws CreateException {
		if ((maps == null) || (maps.length == 0)) {
			if (!isAdd) {
				removeAllLimitMaps();
			}
			return;
		}

		EBCollateralLimitMapLocalHome collateralLimitMapLocalHome = getEBCollateralLimitMapLocalHome();

		// Collection c = getLimitMapsCMR();
		Collection existingColLimitMaps = new ArrayList();

		try {
			existingColLimitMaps = collateralLimitMapLocalHome.findByCollateralID(getCollateralID());
		}
		catch (FinderException e) {
			DefaultLogger.warn(this, "failed to find existing collateral limit map for collateral id ["
					+ getCollateralID() + "], skipped", e);
		}

		if (isAdd || (existingColLimitMaps.size() == 0)) {
			for (int i = 0; i < maps.length; i++) {
				ICollateralLimitMap map = new OBCollateralLimitMap(maps[i]);
				map.setCollateralID(getCollateralID());
				collateralLimitMapLocalHome.create(map);
			}
			return;
		}

		removeLimitMaps(existingColLimitMaps, maps);

		/*
		 * KEY is the limit id (cms limit id + customer category), VALUE is the
		 * collateral limit map ejb instance
		 */
		Map limitColLimitMap = new HashMap();
		Iterator iterator = existingColLimitMaps.iterator();

		// a limit security map can link to multiple collateral limit map with
		// different charge ID
		// this is to set all the collateral limit map with the correct value
		while (iterator.hasNext()) {
			EBCollateralLimitMapLocal theEjb = (EBCollateralLimitMapLocal) iterator.next();
			ICollateralLimitMap existingColLimitMapValue = theEjb.getValue();

			String strLimitID = getColLimitMapLimitID(existingColLimitMapValue);
			List list = (ArrayList) limitColLimitMap.get(strLimitID);
			if (list == null) {
				list = new ArrayList();
			}
			list.add(theEjb);

			limitColLimitMap.put(strLimitID, list);
		}

		List newMaps = new ArrayList();

		for (int i = 0; i < maps.length; i++) {
			ICollateralLimitMap map = new OBCollateralLimitMap(maps[i]);
			map.setCollateralID(getCollateralID());

			boolean found = false;
			String strLimitID = getColLimitMapLimitID(map);

			List existingColLimitMapsForLimit = (ArrayList) limitColLimitMap.get(strLimitID);
			if ((existingColLimitMapsForLimit != null) && (existingColLimitMapsForLimit.size() > 0)) {
				found = true;
				for (int j = 0; j < existingColLimitMapsForLimit.size(); j++) {
					EBCollateralLimitMapLocal theEjb = (EBCollateralLimitMapLocal) existingColLimitMapsForLimit.get(j);
					theEjb.setValue(map);
				}
			}

			if (!found) {
				newMaps.add(map);
			}
		}

		for (Iterator itr = newMaps.iterator(); itr.hasNext();) {
			ICollateralLimitMap map = (ICollateralLimitMap) itr.next();
			collateralLimitMapLocalHome.create(map);
		}
	}

	private String getColLimitMapLimitID(ICollateralLimitMap colLmtMap) {
		String limitID = null;
		if (ICMSConstant.CUSTOMER_CATEGORY_MAIN_BORROWER.equals(colLmtMap.getCustomerCategory())) {
			limitID = ICMSConstant.CUSTOMER_CATEGORY_MAIN_BORROWER + String.valueOf(colLmtMap.getLimitID());
		}
		else if (ICMSConstant.CUSTOMER_CATEGORY_CO_BORROWER.equals(colLmtMap.getCustomerCategory())) {
			limitID = ICMSConstant.CUSTOMER_CATEGORY_CO_BORROWER + String.valueOf(colLmtMap.getCoBorrowerLimitID());
		}
		return limitID;
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.EBCollateral#setLimitChargeMapRef
	 */
	public void setLimitChargeMapRef(ILimitChargeMap lmtChargeMap, boolean isDelete) {
		Collection c = getLimitChargesCMR();

		Iterator iterator = c.iterator();

		while (iterator.hasNext()) {
			EBLimitChargeLocal theEjb = (EBLimitChargeLocal) iterator.next();
			ILimitCharge value = theEjb.getValue();
			if ((value.getStatus() != null) && value.getStatus().equals(ICMSConstant.STATE_DELETED)) {
				continue;
			}

			DefaultLogger.debug(this, "limit charge ref ID=" + value.getRefID());
			DefaultLogger.debug(this, "charge details ID from param=" + lmtChargeMap.getChargeDetailID());

			if (lmtChargeMap.getChargeDetailID() == value.getRefID()) {

				DefaultLogger.debug(this, "found limit charge by charge datails ID, lmtChargeMap=" + lmtChargeMap);
				if (isDelete) {
					theEjb.removeChargeMapsRef(lmtChargeMap);
				}
				else {
					theEjb.setChargeMapsRef(lmtChargeMap);
				}

				break;
			}
		}
	}

	public ICollateralLimitMap getCollateralLimitMap(long cmsLimitID) {

		EBCollateralLimitMapLocalHome ejbHome = getEBCollateralLimitMapLocalHome();
		try {
			Iterator i = ejbHome.findByCollateralID(getCollateralID()).iterator();

			ICollateralLimitMap seclimitMap = null;
			while (i.hasNext()) {

				ICollateralLimitMap obj = ((EBCollateralLimitMapLocal) i.next()).getValue();

				if ((obj.getSCIStatus() != null) && obj.getSCIStatus().equals(ICMSConstant.HOST_STATUS_DELETE)) {
					continue;
				}
				if (cmsLimitID == obj.getLimitID()) {
					seclimitMap = obj;
					break;
				}
			}

			return seclimitMap;

		}
		catch (FinderException e) {
			return null;
		}
		catch (Exception e) {
			return null;
		}
	}

	/**
	 * Set references to limit charge of the collateral.
	 * 
	 * @param charges a list of limit charges
	 * @param isAdd true is to create, false is to update
	 * @throws CreateException on error creating the references
	 * @throws RemoveException on error removing the references
	 */
	protected void setLimitChargesRef(ILimitCharge[] charges, boolean isAdd) throws CreateException, RemoveException {
		if ((charges == null) || (charges.length == 0)) {
			removeAllLimitCharges();
			return;
		}

		EBLimitChargeLocalHome ejbHome = getEBLimitChargeLocalHome();

		Collection c = getLimitChargesCMR();

		if (isAdd || (c.size() == 0)) {
			DefaultLogger.debug(this, "<<<<<<<<<<<<<<<<<<<<< setlimitChargesRef: length: " + charges.length);
			for (int i = 0; i < charges.length; i++) {
				ILimitCharge charge = new OBLimitCharge(charges[i]);
				charge.setCollateralID(getCollateralID());
				c.add(ejbHome.create(charge));
			}
			return;
		}

		removeLimitCharges(c, charges);

		Iterator iterator = c.iterator();
		ArrayList newCharges = new ArrayList();

		for (int i = 0; i < charges.length; i++) {
			ILimitCharge charge = new OBLimitCharge(charges[i]);
			charge.setCollateralID(getCollateralID());

			boolean found = false;

			while (iterator.hasNext()) {
				EBLimitChargeLocal theEjb = (EBLimitChargeLocal) iterator.next();
				ILimitCharge value = theEjb.getValue();
				if ((value.getStatus() != null) && value.getStatus().equals(ICMSConstant.STATE_DELETED)) {
					continue;
				}

				if (charge.getRefID() == value.getRefID()) {
					theEjb.setValue(charge);
					found = true;
					break;
				}
			}
			if (!found) {
				newCharges.add(charge);
			}
			iterator = c.iterator();
		}

		iterator = newCharges.iterator();

		while (iterator.hasNext()) {
			c.add(ejbHome.create((ILimitCharge) iterator.next()));
		}
	}

	/**
	 * Set insurance policy list.
	 * 
	 * @param policies of type IInsurancePolicy[]
	 * @throws CreateException on error creating reference to equity
	 */
	protected void setInsurancePolicyRef(IInsurancePolicy[] policies, boolean isAdd) throws CreateException {
		int policyCount = policies == null ? 0 : policies.length;

		if (policyCount == 0) {
			removeAllInsurancePolicies();
			return;
		}

		EBInsurancePolicyLocalHome ejbHome = getEBInsurancePolicyLocalHome();

		Collection c = getInsurancePolicyCMR();

		if (isAdd || (c.size() == 0)) {
			for (int i = 0; i < policyCount; i++) {
				c.add(ejbHome.create(policies[i]));
			}
			return;
		}

		removeInsurancePolicies(c, policies, policyCount);

		Iterator iterator = c.iterator();
		ArrayList newItems = new ArrayList();

		for (int i = 0; i < policyCount; i++) {
			boolean found = false;

			while (iterator.hasNext()) {
				EBInsurancePolicyLocal theEjb = (EBInsurancePolicyLocal) iterator.next();
				IInsurancePolicy value = theEjb.getValue();
				if ((value.getStatus() != null) && value.getStatus().equals(ICMSConstant.STATE_DELETED)) {
					continue;
				}

				if (policies[i].getRefID().equals(value.getRefID())) {
					theEjb.setValue(policies[i]);
					found = true;
					break;
				}
			}
			if (!found) {
				newItems.add(policies[i]);
			}
			iterator = c.iterator();
		}

		iterator = newItems.iterator();

		while (iterator.hasNext()) {
			c.add(ejbHome.create((IInsurancePolicy) iterator.next()));
		}
	}

	
	protected void setAddtionalDocumentFacilityDetailsRef(IAddtionalDocumentFacilityDetails[] policies, boolean isAdd) throws CreateException {
		int policyCount = policies == null ? 0 : policies.length;

		if (policyCount == 0) {
			removeAllAddtionalDocumentFacilityDetails();
			return;
		}

		EBAddtionalDocumentFacilityDetailsLocalHome ejbHome = getEBAddtionalDocumentFacilityDetailsLocalHome();

		Collection c = getAddtionalDocumentFacilityDetailsCMR();

		if (isAdd || (c.size() == 0)) {
			for (int i = 0; i < policyCount; i++) {
				c.add(ejbHome.create(policies[i]));
			}
			return;
		}

		removeAddtionalDocumentFacilityDetails(c, policies, policyCount);

		Iterator iterator = c.iterator();
		ArrayList newItems = new ArrayList();

		for (int i = 0; i < policyCount; i++) {
			boolean found = false;

			while (iterator.hasNext()) {
				EBAddtionalDocumentFacilityDetailsLocal theEjb = (EBAddtionalDocumentFacilityDetailsLocal) iterator.next();
				IAddtionalDocumentFacilityDetails value = theEjb.getValue();
				if ((value.getStatus() != null) && value.getStatus().equals(ICMSConstant.STATE_DELETED)) {
					continue;
				}

				if (policies[i].getRefID().equals(value.getRefID())) {
					theEjb.setValue(policies[i]);
					found = true;
					break;
				}
			}
			if (!found) {
				newItems.add(policies[i]);
			}
			iterator = c.iterator();
		}

		iterator = newItems.iterator();

		while (iterator.hasNext()) {
			c.add(ejbHome.create((IAddtionalDocumentFacilityDetails) iterator.next()));
		}
	}
	
	
	protected void setSecApportionmentRef(List secApportionments, boolean isAdd) throws RemoveException,
			CreateException {
		Collection c = getSecApportionmentCMR();
		List toRemoveList = new ArrayList();
		Iterator iterator = c.iterator();
		while (iterator.hasNext()) {
			EBSecApportionmentLocal theEjb = (EBSecApportionmentLocal) iterator.next();
			String curPk = theEjb.getPrimaryKey().toString();
			ISecApportionment curApportionment = findApportionmentById(curPk, secApportionments);
			if (curApportionment == null) {
				// the ejb is removed
				toRemoveList.add(theEjb);
			}
			else {
				// update the apportionment details value
				theEjb.setValue(curApportionment);
			}
		}
		for (int i = 0; i < toRemoveList.size(); i++) {
			EBSecApportionmentLocal theEjb = (EBSecApportionmentLocal) (toRemoveList.get(i));
			theEjb.remove();
		}
		// create newly added apportionments
		createNewlyAddedSecApportionments(c, secApportionments);
	}

	private ISecApportionment findApportionmentById(String secApportionmentId, List secApportionments) {
		if (secApportionments != null) {
			for (int i = 0; i < secApportionments.size(); i++) {
				ISecApportionment nextApportionment = (ISecApportionment) (secApportionments.get(i));
				if (secApportionmentId.equals("" + nextApportionment.getSecApportionmentID())) {
					return nextApportionment;
				}
			}
		}
		return null;
	}

	private void createNewlyAddedSecApportionments(Collection originalApportionments, List secApportionments)
			throws CreateException {
		if (secApportionments == null) {
			return;
		}
		outer: for (int i = 0; i < secApportionments.size(); i++) {
			ISecApportionment nextApportionment = (ISecApportionment) (secApportionments.get(i));
			String nextId = "" + nextApportionment.getSecApportionmentID();
			Iterator iterator = originalApportionments.iterator();
			while (iterator.hasNext()) {
				EBSecApportionmentLocal theEjb = (EBSecApportionmentLocal) iterator.next();
				if (theEjb.getPrimaryKey().toString().equals(nextId)) {
					continue outer;
				}
			}
			// id not found, this is a new apportionment
			originalApportionments.add(getEBSecApportionmentLocalHome().create(nextApportionment));
		}
	}

	/**
	 * Helper method to delete all the limit maps
	 */
	private void removeAllLimitMaps() {
		try {
			Collection existingColLimitMaps = getEBCollateralLimitMapLocalHome().findByCollateralID(getCollateralID());
			if (existingColLimitMaps != null && existingColLimitMaps.size() > 0) {
				for (Iterator itr = existingColLimitMaps.iterator(); itr.hasNext();) {
					EBCollateralLimitMapLocal limitMapEjb = (EBCollateralLimitMapLocal) itr.next();
					ICollateralLimitMap limitMapValue = limitMapEjb.getValue();
					limitMapValue.setSCIStatus(ICMSConstant.HOST_STATUS_DELETE);
					limitMapEjb.setValue(limitMapValue);
				}
			}
		}
		catch (FinderException e) {
			// ignored
		}
	}

	/**
	 * Helper method to delete limit maps in limitMapCol that are not contained
	 * in limitMapList.
	 * 
	 * @param limitMapCol a list of old limit maps
	 * @param limitMapList a list of newly updated limit maps
	 */
	private void removeLimitMaps(Collection limitMapCol, ICollateralLimitMap[] limitMapList) {

		Iterator iterator = limitMapCol.iterator();

		while (iterator.hasNext()) {
			EBCollateralLimitMapLocal theEjb = (EBCollateralLimitMapLocal) iterator.next();
			ICollateralLimitMap map = theEjb.getValue();

			boolean found = false;

			for (int i = 0; i < limitMapList.length; i++) {
				if (limitMapList[i].getLimitID() == map.getLimitID()) {
					found = true;
					break;
				}
			}

			if (!found) {
				map.setSCIStatus(ICMSConstant.HOST_STATUS_DELETE);
				theEjb.setValue(map);
			}
		}
	}

	/**
	 * Helper method to delete limit maps in limitMapCol that are not contained
	 * in limitMapList.
	 * 
	 * @param limitMapCol a list of old limit maps
	 * @param limitMapList a list of newly updated limit maps
	 */
	private void removeCollateralPledgors(Collection existingColPledgor, ICollateralPledgor[] newColPledgorList) {

		Iterator iterator = existingColPledgor.iterator();

		while (iterator.hasNext()) {
			EBCollateralPledgorLocal theEjb = (EBCollateralPledgorLocal) iterator.next();
			ICollateralPledgor map = theEjb.getValue();

			boolean found = false;

			for (int i = 0; i < newColPledgorList.length; i++) {
				if (newColPledgorList[i].getSPMID() == map.getSPMID()) {
					found = true;
					break;
				}
			}

			if (!found) {
				map.setSCIPledgorMapStatus(ICMSConstant.HOST_STATUS_DELETE);
				theEjb.setValue(map);
			}
		}
	}

	/**
	 * Helper method to delete all the limit charges
	 */
	private void removeAllLimitCharges() {
		Collection c = getLimitChargesCMR();
		Iterator iterator = c.iterator();

		while (iterator.hasNext()) {
			EBLimitChargeLocal theEjb = (EBLimitChargeLocal) iterator.next();
			deleteLimitCharge(theEjb);
		}
	}

	/**
	 * Helper method to delete limit charges in chargesCol that are not
	 * contained in chargeList.
	 * 
	 * @param chargesCol a list of old limit charges
	 * @param chargeList a list of newly updated limit charges
	 */
	private void removeLimitCharges(Collection chargesCol, ILimitCharge[] chargeList) {
		Iterator iterator = chargesCol.iterator();

		while (iterator.hasNext()) {
			EBLimitChargeLocal theEjb = (EBLimitChargeLocal) iterator.next();
			ILimitCharge charge = theEjb.getValue();
			if ((charge.getStatus() != null) && charge.getStatus().equals(ICMSConstant.STATE_DELETED)) {
				continue;
			}

			boolean found = false;

			for (int i = 0; i < chargeList.length; i++) {
				if (chargeList[i].getRefID() == charge.getRefID()) {
					found = true;
					break;
				}
			}
			if (!found) {
				deleteLimitCharge(theEjb);
			}
		}
	}

	/**
	 * Helper method to delete limit charge.
	 * 
	 * @param theEjb of type EBLimitChargeLocal
	 */
	private void deleteLimitCharge(EBLimitChargeLocal theEjb) {
		theEjb.delete();
	}

	/**
	 * Helper method to delete all the insurance policies
	 */
	private void removeAllInsurancePolicies() {
		Collection c = getInsurancePolicyCMR();
		Iterator iterator = c.iterator();

		while (iterator.hasNext()) {
			EBInsurancePolicyLocal theEjb = (EBInsurancePolicyLocal) iterator.next();
			deleteInsurancePolicy(theEjb);
		}
	}
	
	private void removeAllAddtionalDocumentFacilityDetails() {
		Collection c = getAddtionalDocumentFacilityDetailsCMR();
		Iterator iterator = c.iterator();

		while (iterator.hasNext()) {
			EBAddtionalDocumentFacilityDetailsLocal theEjb = (EBAddtionalDocumentFacilityDetailsLocal) iterator.next();
			deleteAddtionalDocumentFacilityDetails(theEjb);
		}
	}

	/**
	 * Helper method to delete insurance policies in policyCol that are not
	 * contained in policyList.
	 * 
	 * @param policyCol a list of old insurance policies
	 * @param policyList a list of newly updated insurance policies
	 */
	private void removeInsurancePolicies(Collection policyCol, IInsurancePolicy[] policyList, int policyCount) {
		Iterator iterator = policyCol.iterator();

		while (iterator.hasNext()) {
			EBInsurancePolicyLocal theEjb = (EBInsurancePolicyLocal) iterator.next();
			IInsurancePolicy policy = theEjb.getValue();
			if ((policy.getStatus() != null) && policy.getStatus().equals(ICMSConstant.STATE_DELETED)) {
				continue;
			}

			boolean found = false;

			for (int i = 0; i < policyCount; i++) {
				if (policy.getRefID().equals(policyList[i].getRefID())) {
					found = true;
					break;
				}
			}
			if (!found) {
				deleteInsurancePolicy(theEjb);
			}
		}
	}
	
	
	private void removeAddtionalDocumentFacilityDetails(Collection policyCol, IAddtionalDocumentFacilityDetails[] policyList, int policyCount) {
		Iterator iterator = policyCol.iterator();

		while (iterator.hasNext()) {
			EBAddtionalDocumentFacilityDetailsLocal theEjb = (EBAddtionalDocumentFacilityDetailsLocal) iterator.next();
			IAddtionalDocumentFacilityDetails policy = theEjb.getValue();
			if ((policy.getStatus() != null) && policy.getStatus().equals(ICMSConstant.STATE_DELETED)) {
				continue;
			}

			boolean found = false;

			for (int i = 0; i < policyCount; i++) {
				if (policy.getRefID().equals(policyList[i].getRefID())) {
					found = true;
					break;
				}
			}
			if (!found) {
				deleteAddtionalDocumentFacilityDetails(theEjb);
			}
		}
	}

	/**
	 * Helper method to delete limit charge.
	 * 
	 * @param theEjb of type EBInsurancePolicyLocal
	 */
	private void deleteInsurancePolicy(EBInsurancePolicyLocal theEjb) {
		theEjb.delete();
	}
	
	private void deleteAddtionalDocumentFacilityDetails(EBAddtionalDocumentFacilityDetailsLocal theEjb) {
		theEjb.delete();
	}

	/**
	 * Set valuation of the collateral.
	 * 
	 * @param valuation of type IValuation
	 * @throws CreateException on error creating reference to valuation entity
	 */
	protected void setValuationRef(IValuation valuation) throws CreateException {
		if (valuation == null) {
			return;
		}

		EBValuationLocalHome ejbHome = getEBValuationLocalHome();
		getValuationCMR().add(ejbHome.create(valuation));
	}

	
	public abstract Collection getSecurityCoverageCMR();
	
	public abstract void setSecurityCoverageCMR(Collection securityCoverage);
	
	public List<ISecurityCoverage> getSecurityCoverage() {
		Collection<?> cmr = getSecurityCoverageCMR();
		if (CollectionUtils.isEmpty(cmr))
			return null;
		List<ISecurityCoverage> list = new ArrayList<ISecurityCoverage>();
		
		try {
			Iterator<?> iter = cmr.iterator();
			while (iter.hasNext()) {
				EBSecurityCoverageLocal local = (EBSecurityCoverageLocal) iter.next();
				ISecurityCoverage stockAndDate = local.getValue();
				list.add(stockAndDate);
			}
		} catch (Exception e) {
			DefaultLogger.error(this, "Exception retrieving Security Coverage data", e);
			//TODO throw Exception
		}
		return list;
	}
	
	public void setSecurityCoverage(List<ISecurityCoverage> securityCoverage) {
		
	}
	
	protected EBSecurityCoverageLocalHome getEBSecurityCoverageLocalHome() throws CollateralException {
		EBSecurityCoverageLocalHome home = (EBSecurityCoverageLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_SECURITY_COVERAGE_LOCAL_JNDI, EBSecurityCoverageLocalHome.class.getName());

		if (null != home) {
			return home;
		} else {
			throw new CollateralException("EBSecurityCoverageLocalHome is null!");
		}
	}
	
	protected void updateSecurityCoverage(List<ISecurityCoverage> securityCoverageList, long generalChargeDetailsID,
			boolean isAdd) throws CollateralException {
		try {
			Collection cmr = getSecurityCoverageCMR();

			if (securityCoverageList == null) {
				if (CollectionUtils.isEmpty(cmr)) {
					return;
				} else {
					deleteSecurityCoverage(new ArrayList(cmr));
				}
			} else if (isAdd || CollectionUtils.isEmpty(cmr)) {
				createSecurityCoverage(securityCoverageList, generalChargeDetailsID);
			} else {
				Iterator iterator = cmr.iterator();
				List<ISecurityCoverage> createList = new ArrayList<ISecurityCoverage>();
				List<EBSecurityCoverageLocal> deleteList = new ArrayList<EBSecurityCoverageLocal>();

				while (iterator.hasNext()) {
					EBSecurityCoverageLocal local = (EBSecurityCoverageLocal) iterator.next();
					boolean update = false;

					for (ISecurityCoverage securityCoverage : securityCoverageList) {
						if (securityCoverage.getId() == local.getId()) {
							local.setValue(securityCoverage);
							update = true;
							break;
						}
					}
					if (!update) {
						deleteList.add(local);
					}
				}

				for (ISecurityCoverage securityCoverage : securityCoverageList) {
					iterator = cmr.iterator();
					boolean found = false;

					while (iterator.hasNext()) {
						EBSecurityCoverageLocal local = (EBSecurityCoverageLocal) iterator.next();
						if (securityCoverage.getId() == local.getId()) {
							found = true;
							break;
						}
					}
					if (!found) {
						createList.add(securityCoverage);
					}
				}
				deleteSecurityCoverage(deleteList);
				createSecurityCoverage(createList, generalChargeDetailsID);
			}
		} catch (CollateralException e) {
			DefaultLogger.error(this, "Exception while updating Security Coverage", e);
			throw e;
		} catch (Exception e) {
			DefaultLogger.error(this, "Exception while updating Security Coverage", e);
			throw new CollateralException("Caught Exception: " + e.toString());
		}
	}

	protected void deleteSecurityCoverage(List<EBSecurityCoverageLocal> securityCoverageList) throws CollateralException {
		if (CollectionUtils.isEmpty(securityCoverageList)) {
			return;
		}

		Collection cmr = getSecurityCoverageCMR();
		Iterator<EBSecurityCoverageLocal> iterator = securityCoverageList.iterator();
		while (iterator.hasNext()) {
			EBSecurityCoverageLocal local = iterator.next();
			cmr.remove(local);
			try {
				local.remove();
			} catch (EJBException e) {
				DefaultLogger.error(this, "Exception while deleting Security Coverage item(s)", e);
				throw new CollateralException(e.getMessage(), e);
			} catch (RemoveException e) {
				DefaultLogger.error(this, "Exception while deleting Security Coverage item(s)", e);
				throw new CollateralException(e.getMessage(), e);
			}
		}

	}

	protected void createSecurityCoverage(List<ISecurityCoverage> securityCoverageList, long collateralId) throws CollateralException {
		if (CollectionUtils.isEmpty(securityCoverageList)) {
			return;
		}
		Collection cmr = getSecurityCoverageCMR();
		Iterator<ISecurityCoverage> iterator = securityCoverageList.iterator();
		EBSecurityCoverageLocalHome home = getEBSecurityCoverageLocalHome();
		while (iterator.hasNext()) {
			ISecurityCoverage securityCoverage = iterator.next();
			if (securityCoverage != null) {
				String serverType = (new BatchResourceFactory()).getAppServerType();
				DefaultLogger.debug(this, "Creating Security Coverage ID: " + securityCoverage.getId()
						+ ", Application Server Type : " + serverType);

				if (serverType.equals(ICMSConstant.WEBSPHERE)) {
					securityCoverage.setCollateralId(collateralId);
				}

				EBSecurityCoverageLocal local;
				try {
					local = home.create(securityCoverage);
				} catch (CreateException e) {
					DefaultLogger.error(this, "Exception while creating Security Coverage item(s)", e);
					throw new CollateralException(e.getMessage(), e);
				}
				cmr.add(local);
			}
		}

	}
	
	/**
	 * @return Returns the sourceSecIdAliases.
	 */
	public List getSourceSecIdAliases() {
		return null;
	}

	/**
	 * @param sourceSecIdAliases The sourceSecIdAliases to set.
	 */
	public void setSourceSecIdAliases(List sourceSecIdAliases) {
	}

	public boolean getIsCGCPledged() {
		if (getIsCGCPledgedStr() != null && getIsCGCPledgedStr().equals(ICMSConstant.TRUE_VALUE))
			return true;
		return false;
	}

	public void setIsCGCPledged(boolean isCGCPledged) {
		if (isCGCPledged)
			setIsCGCPledgedStr(ICMSConstant.TRUE_VALUE);
		else
			setIsCGCPledgedStr(ICMSConstant.FALSE_VALUE);
	}

	/**
	 * EJB callback method to set the context of the bean.
	 * 
	 * @param context the entity context.
	 */
	public void setEntityContext(EntityContext context) {
		this.context = context;
	}

	/**
	 * EJB callback method to clears the context of the bean.
	 */
	public void unsetEntityContext() {
		this.context = null;
	}

	/**
	 * This method is called when the container picks this entity object and
	 * assigns it to a specific entity object. No implementation currently
	 * acquires any additional resources that it needs when it is in the ready
	 * state.
	 */
	public void ejbActivate() {
	}

	/**
	 * This method is called when the container diassociates the bean from the
	 * entity object identity and puts the instance back into the pool of
	 * available instances. No implementation is currently provided to release
	 * resources that should not be held while the instance is in the pool.
	 */
	public void ejbPassivate() {
	}

	/**
	 * The container invokes this method on the bean whenever it becomes
	 * necessary to synchronize the bean's state with the state in the database.
	 * This method is called after the container has loaded the bean's state
	 * from the database.
	 */
	public void ejbLoad() {
	}

	/**
	 * The container invokes this method on the bean whenever it becomes
	 * necessary to synchronize the state in the database with the state of the
	 * bean. This method is called before the container extracts the fields and
	 * writes them into the database.
	 */
	public void ejbStore() {
	}

	/**
	 * The container invokes this method in response to a client-invoked remove
	 * request. No implementation is currently provided for taking actions
	 * before the bean is removed from the database.
	 */
	public void ejbRemove() {
	}

	/**
	 * User for getting source Name of the security
	 * @return String
	 */

	public List getSecSystemName() {
		List list = new ArrayList();
		ICollateralDAO dao = CollateralDAOFactory.getDAO();
		try {
			list = dao.getSecSystemName(getCollateralID());
		}
		catch (SearchDAOException ex) {
			// ignore
		}
		return list;
	}

	public abstract Date getLEDate();

	public abstract void setLEDate(Date lEDate);

	public abstract Date getLEDateByChargeRanking();

	public abstract void setLEDateByChargeRanking(Date lEDateByChargeRanking);

	public abstract Date getLEDateByJurisdiction();

	public abstract void setLEDateByJurisdiction(Date lEDateByJurisdiction);

	public abstract Date getLEDateByGovernLaws();

	public abstract void setLEDateByGovernLaws(Date lEDateByGovernLaws);

	public abstract String getCollateralLocation();

	public abstract void setCollateralLocation(String collateralLocation);

	public abstract String getStatus();

	public abstract void setStatus(String status);

	public abstract void setVersionTime(long versionTime);

	public abstract Date getCollateralMaturityDate();

	public abstract void setCollateralMaturityDate(Date collateralMaturityDate);

	public abstract String getCollateralCustodian();

	public abstract void setCollateralCustodian(String collateralCustodian);

	public abstract String getCollateralCustodianType();

	public abstract void setCollateralCustodianType(String collateralCustodianType);

	public abstract String getFSVCcyCode();

	public abstract void setFSVCcyCode(String fsvCcyCode);

	public abstract String getCMVCcyCode();

	public abstract void setCMVCcyCode(String cmvCcyCode);

	public abstract String getIsExchangeCtrlObtained();

	public abstract void setIsExchangeCtrlObtained(String isExchangeCtrlObtained);

	public abstract Date getExchangeCtrlDate();

	public abstract void setExchangeCtrlDate(Date exchangeCtrlDate);

	public abstract Date getPerfectionDate();

	public abstract void setPerfectionDate(Date perfectionDate);

	public abstract Date getLastValuationDate();

	public abstract void setLastValuationDate(Date lastValuationDate);

	public abstract String getLastValuer();

	public abstract void setLastValuer(String lastValuer);

	public abstract String getCurrencyCode();

	public abstract void setCurrencyCode(String currencyCode);

	public abstract String getSCICurrencyCode();

	public abstract void setSCICurrencyCode(String sciCurrencyCode);

	public abstract Date getSCIFSVDate();

	public abstract void setSCIFSVDate(Date sciFSVDate);

	public abstract String getSCISubTypeValue();

	public abstract void setSCISubTypeValue(String sciSubTypeValue);

	public abstract String getSCITypeValue();

	public abstract void setSCITypeValue(String sciTypeValue);

	public abstract String getSCIReferenceNote();

	public abstract void setSCIReferenceNote(String sciReferenceNote);

	public abstract long getSCIBookingLocationID();

	public abstract void setSCIBookingLocationID(long sciBookingLocationID);

	public abstract String getSCISecurityID();

	public abstract void setSCISecurityID(String sciSecurityID);

	public abstract String getRemargin();

	public abstract void setRemargin(String remargin);

	public abstract String getIsCGCPledgedStr();

	public abstract void setIsCGCPledgedStr(String isPledgedStr);

	public abstract Date getLastRemarginDate();

	public abstract void setLastRemarginDate(Date lastRemarginDate);

	public abstract Date getNextRemarginDate();

	public abstract void setNextRemarginDate(Date nextRemarginDate);

	public abstract String getRiskMitigationCategory();

	public abstract void setRiskMitigationCategory(String riskMitigationCategory);

	public abstract String getExtSeniorLien();

	public abstract void setExtSeniorLien(String extSeniorLien);

	public abstract String getComment();

	public abstract void setComment(String comment);

	public abstract void setSourceId(String sourceId);

	public abstract String getSourceId();

	public abstract String getValuationType();

	public abstract void setValuationType(String valuationType);

	public abstract String getValuer();

	public abstract void setValuer(String valuer);

	public abstract String getCollateralStatus();

	public abstract String getSourceSecuritySubType();

	public abstract void setCollateralStatus(String collateralStatus);

	public abstract void setSourceSecuritySubType(String sourceSecuritySubType);

	public abstract Character getChargeInfoDrawAmountUsageIndicator();

	public abstract Character getChargeInfoPledgeAmountUsageIndicator();

	public abstract void setChargeInfoDrawAmountUsageIndicator(Character chargeInfoDrawAmountUsageIndicator);

	public abstract void setChargeInfoPledgeAmountUsageIndicator(Character chargeInfoPledgeAmountUsageIndicator);

	public abstract String getLosCollateralRef();

	public abstract void setLosCollateralRef(String losCollateralRef);

	public abstract Date getCreateDate();

	public abstract void setCreateDate(Date createDate);

	public abstract Date getReservePriceDate();

	public abstract void setReservePriceDate(Date reservePriceDate);

	public abstract String getToBeDischargedInd();

	public abstract void setToBeDischargedInd(String toBeDischargedInd);
	
	public abstract String getCollateralCode();

	public abstract void setCollateralCode(String collateralCode);
	
	public abstract String getSecPriority();

	public abstract void setSecPriority(String secPriority);
	
	
	public abstract String getLoanableAmount();

	public abstract void setLoanableAmount(String loanableAmount);
	
	public abstract String getLmtSecurityCoverage();
	public abstract void setLmtSecurityCoverage(String lmtSecurityCoverage);
	
	//Added by Pramod Katkar for New Filed CR on 13-08-2013
	public abstract String getCommonRevalFreq();
	public abstract void setCommonRevalFreq(String commonRevalFreq);
	public abstract String getCommonRevalFreqNo();
	public abstract void setCommonRevalFreqNo(String commonRevalFreqNo) ;
	public abstract Date getValuationDate();
	public abstract void setValuationDate(Date valuationDate);
	public abstract String getValuationAmount();
	public abstract void setValuationAmount(String valuationAmount);
	public abstract Date getNextValDate();
	public abstract void setNextValDate(Date nextValDate);
	public abstract String getTypeOfChange();
	public abstract void setTypeOfChange(String typeOfChange);
	public abstract String getOtherBankCharge();
	public abstract void setOtherBankCharge(String otherBankCharge);
	public abstract String getMonitorProcess();
	public abstract void setMonitorProcess(String monitorProcess);
	public abstract String getMonitorFrequency();
	public abstract void setMonitorFrequency(String monitorFrequency);
	
	//End by Pramod Katkar
	
	public abstract String getTypeOfCharge();
	public abstract void setTypeOfCharge(String typeOfCharge);
	
	//Stock DP Calculation
	public abstract String getBankingArrangement();
	public abstract void setBankingArrangement(String bankingArrangement);

	
	/* CERSAI CR */
	public abstract String getOwnerOfProperty();
	public abstract void setOwnerOfProperty(String ownerOfProperty);
	public abstract String getSecurityOwnership();
	public abstract void setSecurityOwnership(String securityOwnership);
	public abstract String getThirdPartyEntity();
	public abstract void setThirdPartyEntity(String thirdPartyEntity);
	public abstract String getCinForThirdParty();
	public abstract void setCinForThirdParty(String cinForThirdParty);
	public abstract String getCersaiTransactionRefNumber();
	public abstract void setCersaiTransactionRefNumber(String cersaiTransactionRefNumber);
	public abstract String getCersaiSecurityInterestId();
	public abstract void setCersaiSecurityInterestId(String cersaiSecurityInterestId);
	public abstract String getCersaiAssetId();
	public abstract void setCersaiAssetId(String cersaiAssetId);
	public abstract Date getDateOfCersaiRegisteration();
	public abstract void setDateOfCersaiRegisteration(Date dateOfCersaiRegisteration);
	public abstract String getCersaiId();
	public abstract void setCersaiId(String cersaiId);
	public abstract Date getSaleDeedPurchaseDate();
	public abstract void setSaleDeedPurchaseDate(Date saleDeedPurchaseDate);
	public abstract String getThirdPartyAddress();
	public abstract void setThirdPartyAddress(String thirdPartyAddress);
	public abstract String getThirdPartyState();
	public abstract void setThirdPartyState(String thirdPartyState);
	public abstract String getThirdPartyCity();
	public abstract void setThirdPartyCity(String thirdPartyCity);
	public abstract String getThirdPartyPincode();
	public abstract void setThirdPartyPincode(String thirdPartyPincode);
	

	public abstract String getFdRebooking();
	public abstract void setFdRebooking(String fdRebooking);
	
	//New General Fields Added
	public abstract String getPrimarySecurityAddress();
	public abstract void setPrimarySecurityAddress(String primarySecurityAddress);

	public abstract Date getSecurityValueAsPerCAM();
	public abstract void setSecurityValueAsPerCAM(Date securityValueAsPerCAM);

	public abstract String getSecondarySecurityAddress();
	public abstract void setSecondarySecurityAddress(String secondarySecurityAddress);

	public abstract String getSecurityMargin();
	public abstract void setSecurityMargin(String securityMargin);

	public abstract String getChargePriority();
	public abstract void setChargePriority(String chargePriority);
	
		
	public abstract String getTermLoanOutstdAmt();
	public abstract void setTermLoanOutstdAmt(String termLoanOutstdAmt);
	
	public abstract String getMarginAssetCover();
	public abstract void setMarginAssetCover(String marginAssetCover);
	
	public abstract String getRecvGivenByClient();
	public abstract void setRecvGivenByClient(String recvGivenByClient);

}