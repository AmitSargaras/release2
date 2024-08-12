package com.integrosys.cms.app.commoncode.bus;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.component.commondata.app.Constants;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public abstract class EBCommonCodeTypeBean implements EntityBean {
	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	private static final String[] EXCLUDE_METHOD_CREATE = new String[] { "getCommonCategoryId" };

	private static final String[] EXCLUDE_METHOD_UPDATE = new String[] { "getCommonCategoryId" };

	/**
	 * Default Constructor
	 */
	public EBCommonCodeTypeBean() {
	}

	public abstract Long getCMPCommonCodeTypeId();

	public abstract String getCMPCommonCodeTypeCode();

	public abstract String getCMPCommonCodeTypeName();

	public abstract int getCMPCommonCodeType();

	public abstract String getCMPActiveStatus();

	public abstract Long getCMPVersionTime();

	public abstract String getCMPStatus();

	public abstract String getRefCategoryCode();

	public abstract void setRefCategoryCode(String refCategoryCode);

	/**
	 * Helper method to get the common code type ID
	 * @return long - the long value of the common code type ID
	 */
	public long getCommonCodeTypeId() {
		if (getCMPCommonCodeTypeId() != null) {
			return getCMPCommonCodeTypeId().longValue();
		}
		return ICMSConstant.LONG_INVALID_VALUE;
	}

	// setters
	public abstract void setCMPCommonCodeTypeId(Long aCommonCodeTypeID);

	public abstract void setCMPCommonCodeTypeCode(String aCommonCodeTypeCode);

	public abstract void setCMPCommonCodeTypeName(String aCommonCodeTypeName);

	public abstract void setCMPCommonCodeType(int aCommonCodeType);

	public abstract void setCMPActiveStatus(String anActiveStatus);

	public abstract void setCMPVersionTime(Long versionTime);

	public abstract void setCMPStatus(String status);

	/**
	 * Helper method to set the CommonCodeType ID
	 * @param aCommonCodeTypeId - long
	 */
	public void setCommonCodeTypeId(long aCommonCodeTypeId) {
		setCMPCommonCodeTypeId(new Long(aCommonCodeTypeId));
	}

	/**
	 * Return a common code type object
	 * @return ICommonCodeType - the object containing the common code type
	 *         object
	 * @throws CommonCodeTypeException on errors
	 */
	public ICommonCodeType getValue() throws CommonCodeTypeException {
		ICommonCodeType value = new OBCommonCodeType();
		long id;
		if (getCMPCommonCodeTypeId() != null) {
			id = getCMPCommonCodeTypeId().longValue();
		}
		else {
			id = ICMSConstant.LONG_INVALID_VALUE;
		}
		value.setCommonCategoryId(id);
		value.setCommonCategoryCode(getCMPCommonCodeTypeCode());
		value.setCommonCategoryName(getCMPCommonCodeTypeName());
		value.setCommonCategoryType(getCMPCommonCodeType());
		value.setActiveStatus(getCMPActiveStatus());
		if (getCMPVersionTime() != null) {
			value.setVersionTime(getCMPVersionTime().longValue());
		}

		value.setRefCategoryCode(getRefCategoryCode());
		return value;
	}

	/**
	 * Set the CommonCodeType object.
	 * @param anICommonCodeType of ICommonCodeType type
	 * @throws CommonCodeTypeException on error
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 */
	public void setValue(ICommonCodeType anICommonCodeType) throws CommonCodeTypeException, ConcurrentUpdateException {
		try {
			if ((getCMPVersionTime() != null)
					&& (getCMPVersionTime().longValue() != anICommonCodeType.getVersionTime())) {
				throw new ConcurrentUpdateException("Mismatch timestamp");
			}
			AccessorUtil.copyValue(anICommonCodeType, this, EXCLUDE_METHOD_UPDATE);
			setCMPCommonCodeTypeName(anICommonCodeType.getCommonCategoryName());
			if (anICommonCodeType.getActiveStatus() == null) {
				anICommonCodeType.setActiveStatus(Constants.STATUS_INACTIVE);
			}
			setCMPActiveStatus(anICommonCodeType.getActiveStatus());
			setCMPVersionTime(new Long(VersionGenerator.getVersionNumber()));
			setRefCategoryCode(anICommonCodeType.getRefCategoryCode());
		}
		catch (ConcurrentUpdateException ex) {
			_context.setRollbackOnly();
			throw ex;
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			throw new CommonCodeTypeException("Exception in setValue: " + ex.toString());
		}
	}

	/**
	 * Create a CommonCodeType.
	 * @param anICommonCodeType of ICommonCodeType typef
	 * @return Long - the CommonCodeType ID
	 * @throws CreateException on error
	 */
	public Long ejbCreate(ICommonCodeType anICommonCodeType) throws CreateException {
		if (anICommonCodeType == null) {
			throw new CreateException("anICommonCodeType is null!");
		}
		try {
			long pk = ICMSConstant.LONG_INVALID_VALUE;
			pk = Long.parseLong((new SequenceManager()).getSeqNum(getSequenceName(), true));
			setCommonCodeTypeId(pk);
			setCMPCommonCodeTypeCode(anICommonCodeType.getCommonCategoryCode());
			setCMPCommonCodeTypeName(anICommonCodeType.getCommonCategoryName());
			setCMPCommonCodeType(com.integrosys.cms.app.commoncode.Constants.CMS_DATA);
			if (anICommonCodeType.getActiveStatus() == null) {
				anICommonCodeType.setActiveStatus(Constants.STATUS_INACTIVE);
			}
			setCMPActiveStatus(anICommonCodeType.getActiveStatus());
			AccessorUtil.copyValue(anICommonCodeType, this, EXCLUDE_METHOD_CREATE);
			setCMPVersionTime(new Long(VersionGenerator.getVersionNumber()));
			setRefCategoryCode(anICommonCodeType.getRefCategoryCode());
			return new Long(pk);
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			throw new CreateException("Exception in ejbCreate : " + ex.toString());
		}
	}

	/**
	 * EJB Post Create Method
	 * @param anICommonCodeType - ICommonCodeType
	 */
	public void ejbPostCreate(ICommonCodeType anICommonCodeType) throws CreateException {
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

	/**
	 * A container invokes this method when the instance is taken out of the
	 * pool of available instances to become associated with a specific EJB
	 * object. This method transitions the instance to the ready state. This
	 * method executes in an unspecified transaction context.
	 */
	public void ejbActivate() {
	}

	/**
	 * A container invokes this method on an instance before the instance
	 * becomes disassociated with a specific EJB object. After this method
	 * completes, the container will place the instance into the pool of
	 * available instances. This method executes in an unspecified transaction
	 * context.
	 */
	public void ejbPassivate() {
	}

	/**
	 * A container invokes this method to instruct the instance to synchronize
	 * its state by loading it from the underlying database. This method always
	 * executes in the transaction context determined by the value of the
	 * transaction attribute in the deployment descriptor.
	 */
	public void ejbLoad() {
	}

	/**
	 * A container invokes this method to instruct the instance to synchronize
	 * its state by storing it to the underlying database. This method always
	 * executes in the transaction context determined by the value of the
	 * transaction attribute in the deployment descriptor.
	 */
	public void ejbStore() {
	}

	/**
	 * A container invokes this method before it removes the EJB object that is
	 * currently associated with the instance. It is invoked when a client
	 * invokes a remove operation on the enterprise Bean's home or remote
	 * interface. It transitions the instance from the ready state to the pool
	 * of available instances. It is called in the transaction context of the
	 * remove operation.
	 */
	public void ejbRemove() throws RemoveException {
	}

	/**
	 * Get the name of the sequence to be used for key generation
	 * @return String - the sequence to be used
	 */
	protected String getSequenceName() {
		return ICMSConstant.SEQUENCE_COMMON_CODE_TYPE;
	}

}
