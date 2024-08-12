/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * EBEarkMarkGroupBean
 *
 * Created on 5:45:40 PM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.app.predeal.bus;

import java.rmi.RemoteException;
import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Apr 2, 2007 Time: 5:45:40 PM
 */
public abstract class EBEarMarkGroupBean implements EntityBean, IEarMarkGroup {

	protected EntityContext context = null;

	public void setEntityContext(EntityContext entityContext) throws EJBException, RemoteException {
		context = entityContext;
	}

	public void unsetEntityContext() throws EJBException, RemoteException {
		context = null;
	}

	public void ejbStore() throws EJBException, RemoteException {
	}

	public void ejbRemove() throws RemoveException, EJBException, RemoteException {
	}

	public void ejbPassivate() throws EJBException, RemoteException {
	}

	public void ejbLoad() throws EJBException, RemoteException {
	}

	public void ejbActivate() throws EJBException, RemoteException {
	}

	public abstract void setCMPbreachInd(String flag);

	public abstract String getCMPbreachInd();

	public void setBreachInd(boolean flag) {
		setCMPbreachInd(flag ? ICMSConstant.TRUE_VALUE : ICMSConstant.FALSE_VALUE);
		// setBreachInd ( ICMSConstant.TRUE_VALUE.equals ( flag ) ) ;
	}

	public boolean getBreachInd() {
		return (getCMPbreachInd() != null) && ICMSConstant.TRUE_VALUE.equals(getCMPbreachInd());
		// return getBreachInd () ? ICMSConstant.TRUE_VALUE :
		// ICMSConstant.FALSE_VALUE ;
	}

	public OBEarMarkGroup getOBEarMarkGroup() {
		OBEarMarkGroup ob = new OBEarMarkGroup();

		AccessorUtil.copyValue(this, ob);

		return ob;
	}

	private static final String[] EXCLUDE_METHOD_UPDATE = new String[] { "getEarMarkGroupId", "getFeedId" };

	public void updateEBEarMarkGroup(OBEarMarkGroup ob) {
		AccessorUtil.copyValue(ob, this, EXCLUDE_METHOD_UPDATE);
	}

	public Long ejbCreate(OBEarMarkGroup ob) throws CreateException {
		// AccessorUtil.printMethodValue ( ob ) ;

		try {
			if ((ob.getEarMarkGroupId() == null)
					|| (ob.getEarMarkGroupId().longValue() == ICMSConstant.LONG_INVALID_VALUE)) {
				Long pk = new Long((new SequenceManager())
						.getSeqNum(ICMSConstant.SEQUENCE_CMS_EAR_MARK_GROUP_SEQ, true));

				ob.setEarMarkGroupId(pk);
			}
		}
		catch (Exception e) {
			throw new CreateException(e.getMessage());
		}

		// System.out.println ( AccessorUtil.printMethodValue( ob ) ) ;

		// System.out.println ( "Creating object " ) ;

		AccessorUtil.copyValue(ob, this);

		// System.out.println ( "Object created" ) ;

		return ob.getEarMarkGroupId();
	}

	public void ejbPostCreate(OBEarMarkGroup ob) {

	}

	public Long ejbCreate() throws CreateException {
		return null;
	}

	public void ejbPostCreate() {

	}

    public abstract long getCmsActualHolding();

    public abstract void setCmsActualHolding(long cmsActualHolding);

    public abstract Date getDateMaxCapBreach();

    public abstract void setDateMaxCapBreach(Date dateMaxCapBreach);

    public abstract Date getDateQuotaBreach();

    public abstract void setDateQuotaBreach(Date dateQuotaBreach);

    public abstract long getEarMarkCurrent();

    public abstract void setEarMarkCurrent(long earMarkCurrent);

    public abstract Long getEarMarkGroupId();

    public abstract void setEarMarkGroupId(Long earMarkGroupId);

    public abstract long getEarMarkHolding();

    public abstract void setEarMarkHolding(long earMarkHolding);

    public abstract long getFeedId();

    public abstract void setFeedId(long feedId);

    public abstract Date getLastBatchUpdate();

    public abstract void setLastBatchUpdate(Date lastNomineeUpdate);

    public abstract String getSourceSystemId();

    public abstract void setSourceSystemId(String sourceSystemId);

    public abstract Date getLastDateQuotaBreach();

    public abstract void setLastDateQuotaBreach(Date lastDateQuotaBreach);

    public abstract Date getLastDateMaxCapBreach();

    public abstract void setLastDateMaxCapBreach(Date lastDateMaxCapBreach);

    public abstract String getStatus();

    public abstract void setStatus(String status);

    public abstract long getTotalOfUnits();

    public abstract void setTotalOfUnits(long totalOfUnits);

    public abstract long getVersionTime();

    public abstract void setVersionTime(long l);
}
