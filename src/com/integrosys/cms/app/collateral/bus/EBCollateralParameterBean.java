/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBCollateralParameterBean.java,v 1.5 2003/08/15 10:16:39 lyng Exp $
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
 * Entity bean implementation for security parameter.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2003/08/15 10:16:39 $ Tag: $Name: $
 */
public abstract class EBCollateralParameterBean extends OBCollateralSubType implements ICollateralParameter, EntityBean {
	/** The container assigned reference to the entity. */
	private EntityContext context;

	/**
	 * A list of methods to be excluded during update to the security parameter.
	 */
	private static final String[] EXCLUDE_METHOD = new String[] { "getId" };

	/**
	 * Gets the id which is the primary key to identify the parameter.
	 * 
	 * @return collateral parameter id
	 */
	public long getId() {
		return getEBId().longValue();
	}

	/**
	 * Sets the id.
	 * 
	 * @param id collateral parameter id
	 */
	public void setId(long id) {
		setEBId(new Long(id));
	}

	public abstract Long getEBId();

	public abstract void setEBId(Long eBId);

	public abstract long getVersionTime();

	public abstract void setVersionTime(long versionTime);

	public abstract String getStatus();

	public abstract void setStatus(String status);

	/**
	 * Gets the business object of security parameter.
	 * 
	 * @return security parameter
	 */
	public ICollateralParameter getValue() {
		return new OBCollateralParameter(this);
	}

	/**
	 * Sets the business object of security parameter.
	 * 
	 * @param colParam of type ICollateralParameter
	 * @throws VersionMismatchException if the security parameter's version is
	 *         invalid
	 */
	public void setValue(ICollateralParameter colParam) throws VersionMismatchException {
		checkVersionMismatch(colParam);
		AccessorUtil.copyValue(colParam, this, EXCLUDE_METHOD);
		setVersionTime(VersionGenerator.getVersionNumber());
	}

	/**
	 * Search security parameter based on the country code and security type
	 * code.
	 * 
	 * @param countryCode of type String
	 * @param colType of type String
	 * @return a list of security parameters
	 * @throws SearchDAOException on errror searching the security parameter
	 */
	public ICollateralParameter[] ejbHomeSearchByCountryAndColType(String countryCode, String colType)
			throws SearchDAOException {
		ICollateralParameterDAO dao = getCollateralParameterDAO();
		return dao.getCollateralParameters(countryCode, colType);
	}

	/**
	 * Search security parameter based on the group id.
	 * 
	 * @param groupID group id
	 * @return a list of security parameters
	 * @throws SearchDAOException on error searching the security parameter
	 */
	public ICollateralParameter[] ejbHomeSearchByGroupID(long groupID) throws SearchDAOException {
		ICollateralParameterDAO dao = getCollateralParameterDAO();
		return dao.getCollateralParameters(groupID);
	}

	/**
	 * Get DAO implementation for collateral parameter.
	 * 
	 * @return ICollateralParameterDAO
	 */
	protected ICollateralParameterDAO getCollateralParameterDAO() {
		return CollateralParameterDAOFactory.getDAO();
	}

	/**
	 * Check the version of this security parameter
	 * 
	 * @param colParam security parameter
	 * @throws VersionMismatchException if the entity version is invalid
	 */
	private void checkVersionMismatch(ICollateralParameter colParam) throws VersionMismatchException {
		if (getVersionTime() != colParam.getVersionTime()) {
			throw new VersionMismatchException("Mismatch timestamp! " + colParam.getVersionTime());
		}
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param colParam of type ICollateralParameter
	 * @throws CreateException on error creating the entity object
	 */
	public Long ejbCreate(ICollateralParameter colParam) throws CreateException {
		try {
			String id = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_COL_PARAMETER, true);
			AccessorUtil.copyValue(colParam, this, EXCLUDE_METHOD);
			setEBId(new Long(id));
			if (colParam.getGroupId() == ICMSConstant.LONG_MIN_VALUE) {
				setGroupId(Long.parseLong(id));
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
	 * 
	 * @param colParam of type ICollateralParameter
	 */
	public void ejbPostCreate(ICollateralParameter colParam) {
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

    public abstract long getGroupId();

    public abstract void setGroupId(long groupId);

    public abstract String getCountryIsoCode();

    public abstract void setCountryIsoCode(String countryIsoCode);

    public abstract String getSecuritySubTypeId();

    public abstract void setSecuritySubTypeId(String securitySubTypeId);

    public abstract double getThresholdPercent();

    public abstract void setThresholdPercent(double thresholdPercent);

    public abstract String getValuationFrequencyUnit();

    public abstract void setValuationFrequencyUnit(String valuationUnit);

    public abstract int getValuationFrequency();

    public abstract void setValuationFrequency(int valuationFrequency);
}
