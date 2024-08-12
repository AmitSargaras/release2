/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * EBEarMarkBean
 *
 * Created on 9:59:31 AM
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
 * Created by IntelliJ IDEA. User: Eric Date: Mar 23, 2007 Time: 9:59:31 AM
 */
public abstract class EBEarMarkBean implements EntityBean, IEarMark {
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

	public abstract void setCMPholdingInd(String ind);

	public abstract String getCMPholdingInd();

	public abstract void setCMPinfoCorrectInd(String ind);

	public abstract String getCMPinfoCorrectInd();

	public abstract void setCMPwaiveApproveInd(String ind);

	public abstract String getCMPwaiveApproveInd();

	public abstract void setCMPstatus(String status);

	public abstract String getCMPstatus();

	public void setHoldingInd(boolean ind) {
		setCMPholdingInd(ind ? ICMSConstant.TRUE_VALUE : ICMSConstant.FALSE_VALUE);
	}

	public boolean getHoldingInd() {
		return (getCMPholdingInd() != null) && ICMSConstant.TRUE_VALUE.equals(getCMPholdingInd().trim());
	}

	public void setInfoCorrectInd(boolean ind) {
		setCMPinfoCorrectInd(ind ? ICMSConstant.TRUE_VALUE : ICMSConstant.FALSE_VALUE);
	}

	public boolean getInfoCorrectInd() {
		return (getCMPinfoCorrectInd() != null) && ICMSConstant.TRUE_VALUE.equals(getCMPinfoCorrectInd().trim());
	}

	public void setWaiveApproveInd(boolean ind) {
		setCMPwaiveApproveInd(ind ? ICMSConstant.TRUE_VALUE : ICMSConstant.FALSE_VALUE);
	}

	public boolean getWaiveApproveInd() {
		return (getCMPwaiveApproveInd() != null) && ICMSConstant.TRUE_VALUE.equals(getCMPwaiveApproveInd().trim());
	}

	public void setStatus(boolean status) {
		setCMPstatus(status ? ICMSConstant.STATE_ACTIVE : ICMSConstant.STATE_DELETED);
	}

	public boolean getStatus() {
		return (getCMPstatus() != null) && ICMSConstant.STATE_ACTIVE.equals(getCMPstatus().trim());
	}

	private static final String[] EXCLUDE_METHOD_UPDATE = new String[] { "getEarMarkId", "getEarMarkGroupId",
			"getFeedId" };

	private static final String[] EXCLUDE_METHOD_CREATE = new String[] { "getEarMarkId" };

	public OBEarMark getOBEarMark() throws RemoteException {
		OBEarMark ob = new OBEarMark();

		AccessorUtil.copyValue(this, ob);

		return ob;
	}

	public void updateEBEarMark(OBEarMark ob) throws RemoteException {
		AccessorUtil.copyValue(ob, this, EXCLUDE_METHOD_UPDATE);
	}

	public Long ejbCreate() throws CreateException {
		return null;
	}

	public void ejbPostCreate() {

	}

	public Long ejbCreate(OBEarMark ob) throws CreateException {
		// AccessorUtil.printMethodValue ( ob ) ;

		try {
			if ((ob.getEarMarkId() == null) || (ob.getEarMarkId().longValue() == ICMSConstant.LONG_INVALID_VALUE)) {
				Long pk = new Long((new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_CMS_EAR_MARK_SEQ, true));

				ob.setEarMarkId(pk);
			}
		}
		catch (Exception e) {
			throw new CreateException(e.getMessage());
		}

		// System.out.println ( AccessorUtil.printMethodValue( ob ) ) ;

		// System.out.println ( "Creating object " ) ;

		AccessorUtil.copyValue(ob, this);

		// System.out.println ( "Object created" ) ;

		return ob.getEarMarkId();
	}

	public void ejbPostCreate(OBEarMark ob) {

	}

    public abstract Long getEarMarkId();

    public abstract void setEarMarkId(Long id);

    public abstract long getEarMarkGroupId();

    public abstract void setEarMarkGroupId(long id);

    public abstract long getFeedId();

    public abstract void setFeedId(long feedId);

    public abstract String getCustomerName();

    public abstract void setCustomerName(String name);

    public abstract String getSourceSystem();

    public abstract void setSourceSystem(String source);

    public abstract String getSecurityId();

    public abstract void setSecurityId(String securityId);

    public abstract String getAaNumber();

    public abstract void setAaNumber(String aANumber);

    public abstract String getBranchName();

    public abstract void setBranchName(String branch);

    public abstract String getBranchCode();

    public abstract void setBranchCode(String code);

    public abstract String getCifNo();

    public abstract void setCifNo(String cifNo);

    public abstract String getAccountNo();

    public abstract void setAccountNo(String accountNo);

    public abstract long getEarMarkUnits();

    public abstract void setEarMarkUnits(long earMarkUnit);

    public abstract void setEarMarkingDate(Date earMarkingDate);

    public abstract Date getEarMarkingDate();

    public abstract String getEarMarkStatus();

    public abstract void setEarMarkStatus(String earMarkStatus);

    public abstract String getReleaseStatus();

    public abstract void setReleaseStatus(String releaseStatus);

    public abstract String getInfoIncorrectDetails();

    public abstract void setInfoIncorrectDetails(String details);

    public abstract Date getDateMaxCapBreach();

    public abstract void setDateMaxCapBreach(Date dateMaxCapBreach);

    public abstract void setPurposeOfEarmarking(String purposeOfEarmarking);

    public abstract String getPurposeOfEarmarking();

    public abstract long getVersionTime();

    public abstract void setVersionTime(long l);
}
