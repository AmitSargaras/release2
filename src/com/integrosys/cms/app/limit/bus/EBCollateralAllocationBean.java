/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/EBCollateralAllocationBean.java,v 1.10 2006/08/01 12:50:25 czhou Exp $
 */
package com.integrosys.cms.app.limit.bus;

import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.OBCollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;

/**
 * This entity bean represents the persistence for Collateral Allocation.
 * 
 * @author $Author: czhou $
 * @version $Revision: 1.10 $
 * @since $Date: 2006/08/01 12:50:25 $ Tag: $Name: $
 */
public abstract class EBCollateralAllocationBean implements EntityBean, ICollateralAllocation {
	private static final String[] EXCLUDE_METHOD = new String[] { "getChargeID" };

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	/**
	 * Default Constructor
	 */
	public EBCollateralAllocationBean() {
	}

	// ************ Non-persistence method *************
	// Getters
	/**
	 * Get the charge ID
	 * 
	 * @return long
	 */
	public long getChargeID() {
		Long id = getChargePK();
		if (null != id) {
			return id.longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * Get the collateral This method will only create an empty collateral
	 * object which contains the primary key. The object is to be populated
	 * later in proxy.
	 * 
	 * @return ICollateral
	 */
	public ICollateral getCollateral() {
		if (null != getCollateralPK()) {
			OBCollateral collateral = new OBCollateral();
			collateral.setCollateralID(getCollateralPK().longValue());
			return collateral;
		}
		else {
			return null;
		}
	}

	public long getCoborrowerLimitID() {
		Long id = getCoBorrowerLimitPK();
		return (id == null) ? ICMSConstant.LONG_INVALID_VALUE : id.longValue();
	}

	public long getLimitID() {
		Long id = getLimitPK();
		return (id == null) ? ICMSConstant.LONG_INVALID_VALUE : id.longValue();
	}

	// Setters
	/**
	 * Set the charge ID
	 * 
	 * @param value is of type long
	 */
	public void setChargeID(long value) {
		setChargePK(new Long(value));
	}

	/**
	 * Set collateral
	 * 
	 * @param value is of type ICollateral
	 */
	public void setCollateral(ICollateral value) {
		if (null != value) {
			setCollateralPK(new Long(value.getCollateralID()));
		}
	}

	public void setCoborrowerLimitID(long value) {
		setCoBorrowerLimitPK(new Long(value));
	}

	public void setLimitID(long value) {
		setLimitPK(new Long(value));
	}

	/**
	 * Get Limit Profile ID
	 * 
	 * @return long
	 */
	public long getLimitProfileID() {
		if (null != getEBLimitProfileID()) {
			return getEBLimitProfileID().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * Set Limit Profile ID
	 * 
	 * @param value is of type long
	 */
	public void setLimitProfileID(long value) {
		setEBLimitProfileID(new Long(value));
	}

	// ********************** Abstract Methods **********************

	// Getters
	/**
	 * Get the Charge PK
	 * 
	 * @return Long
	 */
	public abstract Long getChargePK();

	/**
	 * Get Collateral ID
	 * 
	 * @return Long
	 */
	public abstract Long getCollateralPK();

	/**
	 * Get Limit ID
	 * 
	 * @return Long
	 */
	public abstract Long getLimitPK();

	/**
	 * Get Co-Borrower Limit ID.
	 * @return coborrower limit id
	 */
	public abstract Long getCoBorrowerLimitPK();

	// Setters
	/**
	 * Set the Charge PK
	 * 
	 * @param value is of type Long
	 */
	public abstract void setChargePK(Long value);

	/**
	 * Set Collateral PK
	 * 
	 * @param value is of type Long
	 */
	public abstract void setCollateralPK(Long value);

	/**
	 * Set Limit PK
	 * 
	 * @param value is of type Long
	 */
	public abstract void setLimitPK(Long value);

	/**
	 * Set Co-Borrower Limit PK
	 * 
	 * @param value is of type Long
	 */
	public abstract void setCoBorrowerLimitPK(Long value);

	public abstract String getHostStatus();

	public abstract void setHostStatus(String value);

	public abstract Date getCreateDate();

	public abstract void setCreateDate(Date value);

	public abstract Date getDeletionDate();

	public abstract void setDeletionDate(Date value);

	public abstract Long getEBLimitProfileID();

	public abstract void setEBLimitProfileID(Long value);

	public abstract String getSourceID();

	public abstract void setSourceID(String value);

	// ************************ ejbCreate methods ********************

	/**
	 * Create a collateral allocation object
	 * 
	 * @param value is the ICollateralAllocation object
	 * @return Long the primary key
	 */
	public Long ejbCreate(ICollateralAllocation value) throws CreateException {
		try {
			String chargeID = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_COL_LIMIT_MAP, true);
			DefaultLogger.debug(this, "Creating Collateral Allocation with charge ID: " + chargeID);

			Long chargePK = new Long(chargeID);
			// AccessorUtil.copyValue (value, this, EXCLUDE_METHOD);

			setChargePK(new Long(chargeID));
			// setChargeDetailFK(null); //intentional. This is to be set when
			// collateral updates charges
			setHostStatus(value.getHostStatus());
			setSourceLmtId(value.getSourceLmtId());
			setCustomerCategory(value.getCustomerCategory());
			setCreateDate(CommonUtil.getCurrentDate());
			setLimitProfileID(value.getLimitProfileID());
			setSourceID(value.getSourceID());
			setLmtSecurityCoverage(value.getLmtSecurityCoverage());
			System.out.print("====EBCollateralAllocationBean==246=======value.getLmtSecurityCoverage() : "+value.getLmtSecurityCoverage());
			setCpsSecurityId(value.getCpsSecurityId());

			ICollateral col = value.getCollateral();
			if (null != col) {
				long collateralID = col.getCollateralID();
				if (collateralID == ICMSConstant.LONG_INVALID_VALUE) {
					throw new CreateException("CollateralID is not initialised!");
				}
				else {
					setCollateralPK(new Long(collateralID));
				}
			}
			else {
				throw new CreateException("ICollateral is null!");
			}

			return chargePK;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CreateException(e.toString());
		}
	}

	/**
	 * Create a collateral allocation object
	 * 
	 * @param value is the ICollateralAllocation object
	 */
	public void ejbPostCreate(ICollateralAllocation value) throws CreateException {
		// do nothing
	}

	/**
	 * Method to get an object representation from persistance
	 * 
	 * @return ICollateralAllocation
	 */
	public ICollateralAllocation getValue() {
		OBCollateralAllocation value = new OBCollateralAllocation();
		AccessorUtil.copyValue(this, value);

		return value;
	}

	/**
	 * Method to set an object representation into persistance
	 * 
	 * @param value is of type ICollateralAllocation
	 */
	public void setValue(ICollateralAllocation value) {
		// throw new
		// LimitException("This bean is read only and cannot update the table."
		// );
		// do nothing
		AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
	}

	/**
	 * Method to soft delete collateral allocation
	 */
	public void delete() {
		setHostStatus(ICMSConstant.HOST_STATUS_DELETE);
		setDeletionDate(CommonUtil.getCurrentDate());
	}

	// ************************************************************************
	/**
	 * EJB callback method
	 */
	public void ejbActivate() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbPassivate() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbLoad() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbStore() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbRemove() throws RemoveException, EJBException {
	}

	/**
	 * EJB Callback Method
	 */
	public void setEntityContext(EntityContext ctx) {
		_context = ctx;
	}

	/**
	 * EJB Callback Method
	 */
	public void unsetEntityContext() {
		_context = null;
	}

    public abstract long getSCILimitSecMapID();

    public abstract void setSCILimitSecMapID(long sciLimitMapID);

    public abstract String getSourceLmtId();

    public abstract void setSourceLmtId(String sourceLmtId);

    public abstract String getCustomerCategory();

    public abstract void setCustomerCategory(String customerCategory);
    
    public abstract String getLmtSecurityCoverage();

    public abstract void setLmtSecurityCoverage(String lmtSecurityCoverage);
    
    public abstract String getCpsSecurityId();

    public abstract void setCpsSecurityId(String cpsSecurityId);
}