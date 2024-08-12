/**
 * EBCommonCodeEntryBean.java
 *
 * Created on January 30, 2007, 11:01 AM
 *
 * Purpose: Bean that talk to the db
 * Description: Bean class used to retrieve, search , update and insert to table COMMON_CODE_CATEGORY_ENTRY
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */

package com.integrosys.cms.app.commoncodeentry.bus;

import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public abstract class EBCommonCodeEntryBean implements EntityBean, ICommonCodeEntry {

	protected EntityContext context = null;

	public void setEntityContext(EntityContext entityContext) throws EJBException {
		context = entityContext;
	}

	public void unsetEntityContext() throws EJBException {
		context = null;
	}

	public void ejbStore() throws EJBException {
	}

	public void ejbRemove() throws RemoveException, EJBException {
	}

	public void ejbPassivate() throws EJBException {
	}

	public void ejbLoad() throws EJBException {
	}

	public void ejbActivate() throws EJBException {
	}

	private static final String[] EXCLUDE_METHOD = new String[] { "setEntryId", "getEntryId" };

	public abstract Long getCmpEntryId();

	public abstract void setCmpEntryId(Long entryId);

	public long getEntryId() {
		return getCmpEntryId().longValue();
	}

	public void setEntryId(long entryId) {
		setCmpEntryId(new Long(entryId));
	}

	public Long ejbCreate(OBCommonCodeEntry ob) throws CreateException {
		try {
			ob.setVersionTime(VersionGenerator.getVersionNumber());
			AccessorUtil.copyValue(ob, this, EXCLUDE_METHOD);
			if (ob.getEntryId() == ICMSConstant.LONG_INVALID_VALUE) {
				SequenceManager manager = new SequenceManager();
				long entryId = Long.parseLong(manager.getSeqNum(ICMSConstant.SEQUENCE_COMMON_CODE_ENTRY, true));
				setEntryId(entryId);
			}
			else {
				setEntryId(ob.getEntryId());
			}
			return null;
		}
		catch (Exception ex) {
			DefaultLogger.error(this, "Error in ejbCreate.", ex);
			throw new CreateException(ex.getMessage());
		}
	}

	public void ejbPostCreate(OBCommonCodeEntry ob) {

	}

	public void updateCommonCodeEntry(OBCommonCodeEntry ob) throws ConcurrentUpdateException {
		// checkVersionMismatch(ob);
		ob.setVersionTime(VersionGenerator.getVersionNumber());
		AccessorUtil.copyValue(ob, this, EXCLUDE_METHOD);
	}

	public OBCommonCodeEntry getOBCommonCodeEntry() {
		OBCommonCodeEntry ob = new OBCommonCodeEntry();
		AccessorUtil.copyValue(this, ob);
		return ob;
	}

	// private void checkVersionMismatch(ICommonCodeEntry ob)
	// throws ConcurrentUpdateException {
	// if (getVersionTime() != ob.getVersionTime()) {
	// ConcurrentUpdateException exp = new ConcurrentUpdateException(
	// "Mismatch timestamp");
	// exp.setErrorCode(ICMSErrorCodes.CONCURRENT_UPDATE);
	// throw exp;
	// }
	// }

    public abstract String getEntryCode();

    public abstract void setEntryCode(String entryCode);

    public abstract String getEntryName();

    public abstract void setEntryName(String entryName);

    public abstract boolean getActiveStatus();

    public abstract void setActiveStatus(boolean activeStatus);

    public abstract String getEntrySource();

    public abstract void setEntrySource(String entrySource);

    public abstract String getCountry();

    public abstract void setCountry(String country);

    public abstract Integer getGroupId();

    public abstract void setGroupId(Integer groupId);

    public abstract String getCategoryCode();

    public abstract void setCategoryCode(String categoryCode);

    public abstract long getCategoryCodeId();

    public abstract void setCategoryCodeId(long categoryCodeId);

    public abstract String getRefEntryCode();

    public abstract void setRefEntryCode(String refEntryCode);

    public abstract String getStatus();

    public abstract void setStatus(String status);

    public abstract long getVersionTime();

    public abstract void setVersionTime(long l);

    public abstract Date getCreationDate() ;
	
	public abstract void setCreationDate(Date creationDate);

	public abstract Date getLastUpdateDate(); 
	
	public abstract void setLastUpdateDate(Date lastUpdateDate) ;
	
	public abstract String getCpsId();

	public abstract void setCpsId(String cpsId);
}
