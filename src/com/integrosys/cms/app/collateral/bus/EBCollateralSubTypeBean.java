/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBCollateralSubTypeBean.java,v 1.7 2003/08/15 06:00:31 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Entity bean implementation for collateral subtype entity.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2003/08/15 06:00:31 $ Tag: $Name: $
 */
public abstract class EBCollateralSubTypeBean implements ICollateralSubType, EntityBean {
	// ************** Abstract methods ************
	public abstract String getSubTypeStandardisedApproachStr();

	public abstract String getSubTypeFoundationIRBStr();

	public abstract String getSubTypeAdvancedIRBStr();

	public abstract void setSubTypeStandardisedApproachStr(String subTypeStandardisedApproachStr);

	public abstract void setSubTypeFoundationIRBStr(String subTypeFoundationIRBStr);

	public abstract void setSubTypeAdvancedIRBStr(String subTypeAdvancedIRBStr);

	/** The container assigned reference to the entity. */
	private EntityContext context;

	/**
	 * A list of methods to be excluded during update to the security parameter.
	 */
	private static final String[] EXCLUDE_METHOD = new String[] { "getSubTypeCode" };

	/**
	 * Helper method to get the invault indicator
	 * @return boolean
	 */
	public boolean getSubTypeStandardisedApproach() {
		if ((getSubTypeStandardisedApproachStr() != null)
				&& getSubTypeStandardisedApproachStr().equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	/**
	 * Helper method to set invault indicator
	 * @param subTypeStandardisedApproach - boolean
	 */
	public void setSubTypeStandardisedApproach(boolean subTypeStandardisedApproach) {
		if (subTypeStandardisedApproach) {
			setSubTypeStandardisedApproachStr(ICMSConstant.TRUE_VALUE);
			return;
		}
		setSubTypeStandardisedApproachStr(ICMSConstant.FALSE_VALUE);
	}

	/**
	 * Helper method to get the invault indicator
	 * @return boolean
	 */
	public boolean getSubTypeFoundationIRB() {
		if ((getSubTypeFoundationIRBStr() != null) && getSubTypeFoundationIRBStr().equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	/**
	 * Helper method to set invault indicator
	 * @param subTypeFoundationIRB - boolean
	 */
	public void setSubTypeFoundationIRB(boolean subTypeFoundationIRB) {
		if (subTypeFoundationIRB) {
			setSubTypeFoundationIRBStr(ICMSConstant.TRUE_VALUE);
			return;
		}
		setSubTypeFoundationIRBStr(ICMSConstant.FALSE_VALUE);
	}

	/**
	 * Helper method to get the invault indicator
	 * @return boolean
	 */
	public boolean getSubTypeAdvancedIRB() {
		if ((getSubTypeAdvancedIRBStr() != null) && getSubTypeAdvancedIRBStr().equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	/**
	 * Helper method to set invault indicator
	 * @param subTypeAdvancedIRB - boolean
	 */
	public void setSubTypeAdvancedIRB(boolean subTypeAdvancedIRB) {
		if (subTypeAdvancedIRB) {
			setSubTypeAdvancedIRBStr(ICMSConstant.TRUE_VALUE);
			return;
		}
		setSubTypeAdvancedIRBStr(ICMSConstant.FALSE_VALUE);
	}

	/**
	 * Get the collateral type business object.
	 * 
	 * @return collateral type object
	 */
	public ICollateralSubType getValue() {
		OBCollateralSubType colType = new OBCollateralSubType();
		AccessorUtil.copyValue(this, colType);
		return colType;
	}

	/**
	 * Set the collateral subtype to this entity.
	 * 
	 * @param colType is of type ICollateralSubType
	 * @throws VersionMismatchException if the subtype's version is invalid
	 */
	public void setValue(ICollateralSubType colType) throws VersionMismatchException {
		checkVersionMismatch(colType);
		AccessorUtil.copyValue(colType, this, EXCLUDE_METHOD);
		setVersionTime(VersionGenerator.getVersionNumber());
	}

	/**
	 * Set the max value, standardised approach, foundation IRB, advanced IRB
	 * for security subtype.
	 * 
	 * @param colType is of type ICollateralSubType
	 * @throws VersionMismatchException if the subtype's version is invalid
	 */
	public void setSubTypeValue(ICollateralSubType colType) throws VersionMismatchException {
		checkVersionMismatch(colType);
		setMaxValue(colType.getMaxValue());
		setSubTypeStandardisedApproach(colType.getSubTypeStandardisedApproach());
		setSubTypeFoundationIRB(colType.getSubTypeFoundationIRB());
		setSubTypeAdvancedIRB(colType.getSubTypeAdvancedIRB());
		setStatus(colType.getStatus());
		setVersionTime(VersionGenerator.getVersionNumber());
	}

	/**
	 * Search security sub type based on its type code.
	 * 
	 * @param typeCode of type String
	 * @return a list of security subtypes
	 * @throws SearchDAOException on errror searching the security subtypes
	 */
	public ICollateralSubType[] ejbHomeSearchByTypeCode(String typeCode) throws SearchDAOException {
		ICollateralDAO dao = getCollateralDAO();
		return dao.getCollateralSubTypesByTypeCode(typeCode);
	}

	/**
	 * Get DAO implementation for collateral dao.
	 * 
	 * @return ICollateralDAO
	 */
	protected ICollateralDAO getCollateralDAO() {
		return CollateralDAOFactory.getDAO();
	}

	/**
	 * Check the version of this subtype.
	 * 
	 * @param subType collateral subtype
	 * @throws VersionMismatchException if the entity version is invalid
	 */
	private void checkVersionMismatch(ICollateralSubType subType) throws VersionMismatchException {
		if (getVersionTime() != subType.getVersionTime()) {
			throw new VersionMismatchException("Mismatch timestamp! " + subType.getVersionTime());
		}
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param colType of type ICollateralSubType
	 * @throws CreateException on error creating the entity object
	 */
	public String ejbCreate(ICollateralSubType colType) throws CreateException {
		try {
			String id = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_COL_SUBTYPE, true);
			AccessorUtil.copyValue(colType, this);
			if (colType.getGroupID() == ICMSConstant.LONG_MIN_VALUE) {
				setGroupID(Long.parseLong(id));
			}
			setVersionTime(VersionGenerator.getVersionNumber());
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
	 * @param colType of type ICollateralSubType
	 */
	public void ejbPostCreate(ICollateralSubType colType) {
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

    public abstract String getSubTypeCode();

    public abstract void setSubTypeCode(String subTypeCode);

    public abstract String getSubTypeName();

    public abstract void setSubTypeName(String subTypeName);

    public abstract String getSubTypeDesc();

    public abstract void setSubTypeDesc(String subTypeDesc);

    public abstract double getMaxValue();

    public abstract void setMaxValue(double maxValue);

    public abstract long getGroupID();

    public abstract void setGroupID(long groupID);

    public abstract String getStatus();

    public abstract void setStatus(String status);

    public abstract long getVersionTime();

    public abstract void setVersionTime(long versionTime);

    public abstract String getTypeCode();

    public abstract void setTypeCode(String typeCode);

    public abstract String getTypeName();

    public abstract void setTypeName(String typeName);
}