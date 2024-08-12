/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/marketable/EBMarketableEquityBean.java,v 1.18 2003/11/06 11:43:05 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.marketable;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.type.marketable.linedetail.EBMarketableEquityLineDetailLocal;
import com.integrosys.cms.app.collateral.bus.type.marketable.linedetail.EBMarketableEquityLineDetailLocalHome;
import com.integrosys.cms.app.collateral.bus.type.marketable.linedetail.IMarketableEquityLineDetail;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Entity bean implementation for marketable equity.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.18 $
 * @since $Date: 2003/11/06 11:43:05 $ Tag: $Name: $
 */
public abstract class EBMarketableEquityBean implements IMarketableEquity, EntityBean {
	/** The container assigned reference to the entity. */
	private EntityContext context;

	/** A list of methods to be excluded during update to the equity. */
	private static final String[] EXCLUDE_METHOD = new String[] { "getEquityID", "getRefID" };

	/**
	 * Get marketable equity id.
	 * 
	 * @return long
	 */
	public long getEquityID() {
		return getEBEquityID().longValue();
	}

	/**
	 * Set marketable equity id.
	 * 
	 * @param equityID of type long
	 */
	public void setEquityID(long equityID) {
		setEBEquityID(new Long(equityID));
	}

	/**
	 * Get unit price/ current market value.
	 * 
	 * @return Amount
	 */
	public Amount getCMV() {
		if (getEBCMV() != null) {
			return new Amount(getEBCMV().doubleValue(), getCMVCcyCode());
		}
		else {
			return null;
		}
	}

	/**
	 * Set unit price/ current market value.
	 * 
	 * @param cmv of type Amount
	 */
	public void setCMV(Amount cmv) {
		if (cmv != null) {
			setEBCMV(new Double(cmv.getAmountAsDouble()));
		}
		else {
			setEBCMV(null);
		}
	}

	/**
	 * Get minimal forced sale value.
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
	 * Set minimal forced sale value.
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
	 * Get nominal value.
	 * 
	 * @return Amount
	 */
	public Amount getNominalValue() {
		if (getEBNominalValue() != null) {
			return new Amount(getEBNominalValue().doubleValue(), getItemCurrencyCode());
		}
		else {
			return null;
		}
	}

	/**
	 * Set nominal value.
	 * 
	 * @param nominalValue of type Amount
	 */
	public void setNominalValue(Amount nominalValue) {
		if (nominalValue != null) {
			setEBNominalValue(new Double(nominalValue.getAmountAsDouble()));
		}
		else {
			setEBNominalValue(null);
		}
	}

	public boolean getIsCollateralBlacklisted() {
		String isBlacklisted = getEBIsCollateralBlacklisted();
		if ((isBlacklisted != null) && isBlacklisted.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	/**
	 * Get if the security is blacklisted.
	 * 
	 * @return boolean
	 */
	public boolean getIsLocalStockExchange() {
		if (ICMSConstant.TRUE_VALUE.equals(getEBIsLocalStockExchange())) {
			return true;
		}
		return false;
	}

	public void setIsLocalStockExchange(boolean isLocalStockExchange) {
		if (isLocalStockExchange) {
			setEBIsLocalStockExchange(ICMSConstant.TRUE_VALUE);
		}
		else {
			setEBIsLocalStockExchange(ICMSConstant.FALSE_VALUE);
		}
	}

	/**
	 * Set if the security is blacklisted.
	 * 
	 * @param isCollateralBlacklisted of type boolean
	 */
	public void setIsCollateralBlacklisted(boolean isCollateralBlacklisted) {
		if (isCollateralBlacklisted) {
			setEBIsCollateralBlacklisted(ICMSConstant.TRUE_VALUE);
		}
		else {
			setEBIsCollateralBlacklisted(ICMSConstant.FALSE_VALUE);
		}
	}

	/**
	 * Get if guarantee is by government.
	 * 
	 * @return boolean
	 */
	public boolean getIsGuaranteeByGovt() {
		String isByGovt = getEBIsGuaranteeByGovt();
		if ((isByGovt != null) && isByGovt.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	/**
	 * Set if guarantee is by government.
	 * 
	 * @param isGuaranteeByGovt of type boolean
	 */
	public void setIsGuaranteeByGovt(boolean isGuaranteeByGovt) {
		if (isGuaranteeByGovt) {
			setEBIsGuaranteeByGovt(ICMSConstant.TRUE_VALUE);
		}
		else {
			setEBIsGuaranteeByGovt(ICMSConstant.FALSE_VALUE);
		}
	}

	/**
	 * Get unit price.
	 * 
	 * @return Amount
	 */
	public Amount getValuationUnitPrice() {
		return new Amount(getEBValuationUnitPrice(), getValuationUnitPriceCcyCode());
	}

	/**
	 * Set unit price used for valuation.
	 * 
	 * @param valUnitPrice of type Amount
	 */
	public void setValuationUnitPrice(Amount valUnitPrice) {
		if (valUnitPrice != null) {
			setEBValuationUnitPrice(valUnitPrice.getAmountAsDouble());
		}
	}


    public Amount getUnitPrice() {
        return null;  //Do nothing - read only field
    }

    public void setUnitPrice(Amount unitPrice) {
        //Do nothing
    }

    public String getUnitPriceCcyCode() {
        return null;  //Do nothing - read only field
    }

    public void setUnitPriceCcyCode(String unitPriceCcyCode) {
        //Do nothing
    }

    public Amount getExercisePrice() {
		if (getEBExercisePrice() != null)
			return new Amount(getEBExercisePrice().doubleValue(), IGlobalConstant.DEFAULT_CURRENCY);
		return null;
	}

	public void setExercisePrice(Amount exercisePrice) {
		if (exercisePrice != null)
			setEBExercisePrice(new Double(exercisePrice.getAmountAsDouble()));
		else
			setEBExercisePrice(null);
	}

	public abstract Long getEBEquityID();

	public abstract void setEBEquityID(Long eBEquityID);

	public abstract Double getEBCMV();

	public abstract void setEBCMV(Double eBCMV);

	public abstract Double getEBFSV();

	public abstract void setEBFSV(Double eBFSV);

	public abstract Double getEBNominalValue();

	public abstract void setEBNominalValue(Double eBNominalValue);

	public abstract String getEBIsCollateralBlacklisted();

	public abstract void setEBIsCollateralBlacklisted(String eBIsCollateralBlacklisted);

	public abstract String getEBIsLocalStockExchange();

	public abstract void setEBIsLocalStockExchange(String eBIsLocalStockExchange);

	public abstract String getEBIsGuaranteeByGovt();

	public abstract void setEBIsGuaranteeByGovt(String eBIsGuaranteeByGovt);

	public abstract double getEBValuationUnitPrice();

	public abstract void setEBValuationUnitPrice(double eBValUnitPrice);

	public abstract void setStatus(String status);

	public abstract void setEquityDetailCMR(Collection EquityDetails);

	public abstract Collection getEquityDetailCMR();

	/**
	 * Get the marketable equity.
	 * 
	 * @return IMarketableEquity
	 */
	public IMarketableEquity getValue() {
		OBMarketableEquity ob = new OBMarketableEquity();
		AccessorUtil.copyValue(this, ob);
		if ((ICMSConstant.COLTYPE_MAR_NONLISTED_LOCAL).equals(getSubTypeByRefId(ob.getRefID()))) {
		}
		else {
			// Retrieve unit price from feed if subtype is not
			// "Non - Listed Local"
			ob.setUnitPrice(getUnitPriceFromFeed(getStockCode(), getIsinCode(), getStockExchange()));
			ob.setUnitPriceCcyCode(ob.getUnitPrice().getCurrencyCode());
        }
		
		try {
			ob.setLineDetails(retrieveLineDetails());
		} catch (RuntimeException e) {
			throw new EJBException("fail to retrieve Equity detail for equity id ["
					+ ob.getEquityID() + "] ", e);
		}
		
		return ob;
	}

	/**
	 * Set the marketable equity.
	 * 
	 * @param equity is of type IMarketableEquity
	 */
	public void setValue(IMarketableEquity equity) {
		AccessorUtil.copyValue(equity, this, EXCLUDE_METHOD);
		
		try {
			updateLineDetail(((IMarketableEquity) equity).getLineDetails(), equity.getEquityID());
		} catch (Exception e) {
			throw new EJBException("fail to create line details, equity id [" + equity.getEquityID() + "]",
					e);
		}
	}

	/**
	 * Get Array of EquityDetail
	 * @return Array of IMarketableEquityDetail
	 */
	public IMarketableEquityDetail[] getEquityDetailArray() {
		try {

			if (getEquityDetailCMR() != null) {
				Iterator i = getEquityDetailCMR().iterator();
				ArrayList list = new ArrayList();

				while (i.hasNext()) {
					try {
						list.add(((EBMarketableEquityDetailLocal) i.next()).getValue());
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}

				return (OBMarketableEquityDetail[]) list.toArray(new OBMarketableEquityDetail[0]);
			}
			else {
				return null;
			}
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
		}
		return null;
	}

	public void setEquityDetailArray(IMarketableEquityDetail[] equityDetails) {
		//
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param equity of type IMarketableEquity
	 * @return primary key
	 * @throws CreateException on error creating the entity object
	 */
	public Long ejbCreate(IMarketableEquity equity) throws CreateException {
		try {
			String equityID = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_MARKETABLE_EQUITY, true);
			AccessorUtil.copyValue(equity, this, EXCLUDE_METHOD);
			this.setEBEquityID(new Long(equityID));
			if (equity.getRefID() == ICMSConstant.LONG_MIN_VALUE) {
				setRefID(getEquityID());
			}
			else {
				// else maintain this reference id.
				setRefID(equity.getRefID());
			}
			return null;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CreateException(e.toString());
		}
	}

	/**
	 * The container invokes this method after invoking the ejbCreate method
	 * with the same arguments.
	 * 
	 * @param equity of type IMarketableEquity
	 * @throws CreateException 
	 */
	public void ejbPostCreate(IMarketableEquity equity) throws CreateException {
		
		try {
			updateLineDetail(((IMarketableEquity) equity).getLineDetails(), equity.getEquityID());
		} catch (Exception e) {
			DefaultLogger.error(this, "Exception caught: ", e);
			throw new CreateException(e.toString());
		}
	}

	public IMarketableEquityLineDetail[] getLineDetails() {
		return null;
	}
	
	public void setLineDetails(IMarketableEquityLineDetail[] lLineDetails) {
		
	}
	
	public static Comparator<IMarketableEquityLineDetail> orderByLineDetailID = new Comparator<IMarketableEquityLineDetail>() {

		public int compare(IMarketableEquityLineDetail o1, IMarketableEquityLineDetail o2) {
			return Long.valueOf(o1.getLineDetailId()).compareTo(o2.getLineDetailId());
		}
	}; 
	
	private IMarketableEquityLineDetail[] retrieveLineDetails(){

		try {
			Collection c = getCMRLineDetails();
			if ((null == c) || (c.size() == 0)) {
				return null;
			}
			else {
				ArrayList<IMarketableEquityLineDetail> aList = new ArrayList<IMarketableEquityLineDetail>();
				Iterator i = c.iterator();
				while (i.hasNext()) {
					EBMarketableEquityLineDetailLocal local = (EBMarketableEquityLineDetailLocal) i.next();
					IMarketableEquityLineDetail ob = local.getValue();
					aList.add(ob);
				}
				IMarketableEquityLineDetail[] lineDetails = aList.toArray(new IMarketableEquityLineDetail[0]);
				Arrays.sort(lineDetails, orderByLineDetailID);
				return lineDetails;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
				throw new RuntimeException("Caught Exception: " + e.toString());
		}
	
	}
	
	private void deleteLineDetails(List deleteList) {
		if ((null == deleteList) || (deleteList.size() == 0)) {
			
		}
		
		try {
			Collection c = getCMRLineDetails();
			Iterator i = deleteList.iterator();
			while (i.hasNext()) {
				EBMarketableEquityLineDetailLocal local = (EBMarketableEquityLineDetailLocal) i.next();
				c.remove(local);
				local.remove();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Caught Exception: " + e.toString());
		}
		
	}
	
	private void createLineDetails(List createList,long marketableEquityId) {
		if ((null == createList) || (createList.size() == 0)) {
			return; // do nothing
		}
		Collection c = getCMRLineDetails();
		Iterator i = createList.iterator();
		try {
			EBMarketableEquityLineDetailLocalHome home = getEBLocalLineDetail();
			while (i.hasNext()) {
				IMarketableEquityLineDetail ob = (IMarketableEquityLineDetail) i.next();
				if(ob!=null){
					DefaultLogger.debug(this, "Creating LineItem ID: " + ob.getLineDetailId());
					ob.setMarketableEquityId(marketableEquityId);
					EBMarketableEquityLineDetailLocal local = home.create(ob);
					c.add(local);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Caught Exception: " + e.toString());
		}
	}
	
	private void updateLineDetail(IMarketableEquityLineDetail[] lineDetails,long marketableEquityId){
		try {
			Collection c = getCMRLineDetails();

			if (null == lineDetails) {
				if ((null == c) || (c.size() == 0)) {
					return; // nothing to do
				}
				else {
					// delete all records
					deleteLineDetails(new ArrayList(c));
				}
			}
			else if ((null == c) || (c.size() == 0)) {
				List lineDetailList = Arrays.asList(lineDetails);
				createLineDetails(lineDetailList, marketableEquityId);
			}
			else {
				Iterator i = c.iterator();
				ArrayList createList = new ArrayList(); // contains list of OBs
				ArrayList deleteList = new ArrayList(); // contains list of
														// local interfaces

				// identify identify records for delete or udpate first
				while (i.hasNext()) {
					EBMarketableEquityLineDetailLocal local = (EBMarketableEquityLineDetailLocal) i.next();

					long lineDetailId = local.getLineDetailId();
					boolean update = false;

					for (int j = 0; j < lineDetails.length; j++) {
						IMarketableEquityLineDetail newOB = lineDetails[j];

						if (newOB.getLineDetailId() == lineDetailId) {
							// perform update
							local.setValue(newOB);
							update = true;
							break;
						}
					}
					if (!update) {
						// add for delete
						deleteList.add(local);
					}
				}
				// next identify records for add
				for (int j = 0; j < lineDetails.length; j++) {
					i = c.iterator();
					IMarketableEquityLineDetail newOB = lineDetails[j];
					boolean found = false;

					while (i.hasNext()) {
						EBMarketableEquityLineDetailLocal local = (EBMarketableEquityLineDetailLocal) i.next();
						long id = local.getLineDetailId();

						if (newOB.getLineDetailId() == id) {
							found = true;
							break;
						}
					}
					if (!found) {
						// add for adding
						createList.add(newOB);
					}
				}
				deleteLineDetails(deleteList);
				createLineDetails(createList, marketableEquityId);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Caught Exception: " + e.toString());
		}
	}
	
	
	public abstract Collection getCMRLineDetails();
	
	public abstract void setCMRLineDetails(Collection value);
	
	protected EBMarketableEquityLineDetailLocalHome getEBLocalLineDetail()  {
		EBMarketableEquityLineDetailLocalHome home = (EBMarketableEquityLineDetailLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_MARKETABLE_EQUITY_LINE_DETAIL_LOCAL_JNDI, EBMarketableEquityLineDetailLocalHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new RuntimeException("EBMarketableEquityLineDetailLocalHome is null!");
		}
	}
	

	/**
	 * EJB callback method to set the context of the bean.
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

	private Amount getUnitPriceFromFeed(String stockCode, String isinCode, String stockExchange) {
//		if ((stockExchange == null) || "".equals(stockExchange.trim())) {
//			return new Amount(0, "");
//		}
		DBUtil dbUtil = null;
		ResultSet rs = null;
		String sql = null;
		Amount unitPrice = null;
		try {
			if ((stockCode != null) && !"".equals(stockCode.trim())) {
				dbUtil = new DBUtil();
				sql = "select UNIT_PRICE,CURRENCY from cms_price_feed where TICKER=? ";
				dbUtil.setSQL(sql);
				dbUtil.setString(1, stockCode.trim());
				// dbUtil.setString(2, stockExchange);
				rs = dbUtil.executeQuery();
				unitPrice = processResultSet(rs);
				if (unitPrice != null) {
                    DefaultLogger.debug(this, " >>>>>>>>>> unitprice for stockcode [" + stockCode + "] =" + unitPrice);
					return unitPrice;
				}
				else {
					DefaultLogger.debug(this, " Can't find unitprice via stockcode [" + stockCode + "]");
				}
			}
			finalize(dbUtil, rs);
			if ((isinCode != null) && !"".equals(isinCode.trim())) {
				dbUtil = new DBUtil();
				sql = "select UNIT_PRICE,CURRENCY from cms_price_feed where ISIN_CODE=? ";
				dbUtil.setSQL(sql);
				dbUtil.setString(1, isinCode.trim());
				// dbUtil.setString(2, stockExchange);
				rs = dbUtil.executeQuery();
				unitPrice = processResultSet(rs);
				if (unitPrice != null) {
					return unitPrice;
				}
				else {
					DefaultLogger.debug(this, " Can't find unitprice via isincode [" + isinCode + "]");
				}
			}
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "Error: " + e.getMessage());
		}
		finally {
			finalize(dbUtil, rs);
		}
		return new Amount(0, "");
	}

	private Amount processResultSet(ResultSet rs) {
		try {
			if ((rs != null) && rs.next()) {
				return new Amount(Double.parseDouble(rs.getString("UNIT_PRICE")), rs.getString("CURRENCY"));
			}
		}
		catch (Exception e) {
		}
		return null;
	}

	private String getSubTypeByRefId(long refId) {
		DefaultLogger.debug(this, "Ref Id : " + refId);
		String subtypeCode = "";

		DBUtil dbUtil = null;
		ResultSet rs = null;
		String sql = null;
		try {
			dbUtil = new DBUtil();

			// retrieve subtype from actual table
			sql = "select sec.SCI_SECURITY_SUBTYPE_VALUE from CMS_SECURITY sec, CMS_PORTFOLIO_ITEM port "
					+ "where sec.CMS_COLLATERAL_ID = port.CMS_COLLATERAL_ID " + "and port.CMS_REF_ID=? ";
			dbUtil.setSQL(sql);
			dbUtil.setLong(1, refId);
			rs = dbUtil.executeQuery();

			if ((rs != null) && rs.next()) {
				subtypeCode = rs.getString("SCI_SECURITY_SUBTYPE_VALUE");
			}

			// if subtype not found from actual, retrieve from staging table
			if ("".equals(subtypeCode.trim())) {
				sql = "select sec.SCI_SECURITY_SUBTYPE_VALUE from CMS_STAGE_SECURITY sec, CMS_STAGE_PORTFOLIO_ITEM port "
						+ "where sec.CMS_COLLATERAL_ID = port.CMS_COLLATERAL_ID " + "and port.CMS_REF_ID=? ";
				dbUtil.setSQL(sql);
				dbUtil.setLong(1, refId);
				rs = dbUtil.executeQuery();

				if ((rs != null) && rs.next()) {
					subtypeCode = rs.getString("SCI_SECURITY_SUBTYPE_VALUE");
				}
			}

			finalize(dbUtil, rs);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "Error: " + e.getMessage());
		}
		finally {
			finalize(dbUtil, rs);
		}

		DefaultLogger.debug(this, "SubType Value retrieved : " + subtypeCode);
		return subtypeCode;
	}

	private void finalize(DBUtil dbUtil, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		}
		catch (Exception e) {
		}
		try {
			if (dbUtil != null) {
				dbUtil.close();
			}
		}
		catch (Exception e) {
		}
	}

	public abstract long getRefID();

	public abstract void setRefID(long refID);

	public abstract String getEquityType();

	public abstract void setEquityType(String equityType);

	public abstract String getCertificateNo();

	public abstract void setCertificateNo(String certificateNo);

	public abstract String getRegisteredName();

	public abstract void setRegisteredName(String registeredName);
	//Start- Added by Uma Khot- CAM QUARTER ACTIVITY CR: allow decimal in NoofUnits for Security Sub-type ‘Mutual Funds’.
	public abstract double getNoOfUnits();

	public abstract void setNoOfUnits(double noOfUnits);
	//End- Added by Uma Khot- CAM QUARTER ACTIVITY CR: allow decimal in NoofUnits for Security Sub-type ‘Mutual Funds’.
	public abstract String getCMVCcyCode();

	public abstract void setCMVCcyCode(String cmvCcyCode);

	public abstract String getFSVCcyCode();

	public abstract void setFSVCcyCode(String fsvCcyCode);

	public abstract String getAgentName();

	public abstract void setAgentName(String agentName);

	public abstract Date getAgentConfirmDate();

	public abstract void setAgentConfirmDate(Date agentConfirmDate);

	public abstract String getSettlementOrganisation();

	public abstract void setSettlementOrganisation(String settlementOrganisation);

	public abstract Date getBondIssueDate();

	public abstract void setBondIssueDate(Date bondIssueDate);

	public abstract Date getBondMaturityDate();

	public abstract void setBondMaturityDate(Date bondMaturityDate);

	public abstract String getItemCurrencyCode();

	public abstract void setItemCurrencyCode(String itemCurrencyCode);

	public abstract String getIsExchangeCtrlObtained();

	public abstract void setIsExchangeCtrlObtained(String isExchangeCtrlObtained);

	public abstract String getBaselCompliant();

	public abstract void setBaselCompliant(String baselCompliant);

	public abstract String getGovernmentName();

	public abstract void setGovernmentName(String governmentName);

	public abstract String getRIC();

	public abstract void setRIC(String ric);

	public abstract String getNameOfIndex();

	public abstract void setNameOfIndex(String nameOfIndex);

	public abstract String getStockExchange();

	public abstract void setStockExchange(String stockExchange);

	public abstract String getStockExchangeCountry();

	public abstract void setStockExchangeCountry(String stockExchangeCountry);

	public abstract String getLeadManager();

	public abstract void setLeadManager(String leadManager);

	public abstract String getIssuerName();

	public abstract void setIssuerName(String issuerName);

	public abstract String getIssuerIdType();

	public abstract void setIssuerIdType(String issuerIdType);

	public abstract Date getCollateralMaturityDate();

	public abstract void setCollateralMaturityDate(Date collateralMaturityDate);

	public abstract String getCollateralCustodian();

	public abstract void setCollateralCustodian(String collateralCustodian);

	public abstract String getCollateralCustodianType();

	public abstract void setCollateralCustodianType(String collateralCustodianType);

	public abstract String getValuationUnitPriceCcyCode();

	public abstract void setValuationUnitPriceCcyCode(String unitPriceCcyCode);

	public abstract String getBondRating();

	public abstract void setBondRating(String bondRating);

	public abstract String getStatus();

	public abstract String getIsinCode();

	public abstract void setIsinCode(String isinCode);

	public abstract String getStockCode();

	public abstract void setStockCode(String stockCode);

	public abstract String getRecognizeExchange();

	public abstract void setRecognizeExchange(String recognizeExchange);

	public abstract Double getEBExercisePrice();

	public abstract void setEBExercisePrice(Double eBExercisePrice);

	public abstract String getCdsNumber();

	public abstract void setCdsNumber(String cdsNumber);

	public abstract String getClientCode();

	public abstract void setClientCode(String clientCode);

	public abstract String getBrokerName();

	public abstract void setBrokerName(String brokerName);

	public abstract Date getExchangeCtrlDate();

	public abstract void setExchangeCtrlDate(Date exchangeCtrlDate);

	public abstract Date getLeDate();

	public abstract void setLeDate(Date leDate);
	
	
	public abstract String getEquityUniqueID();

	public abstract void setEquityUniqueID(String equityUniqueID);
	
	

}
