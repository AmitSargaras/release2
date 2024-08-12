package com.integrosys.cms.app.customer.bus;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public abstract class EBCoBorrowerDetailsBean implements EntityBean, ICoBorrowerDetails {

	private static final long serialVersionUID = 1L;
	private static final String SEQUENCE = ICMSConstant.SEQUENCE_CO_BORROWER;
	private static final String[] EXCLUDE_METHODS = new String[] {"getCoBorrowerId", "getMainProfileId"};
		
	protected EntityContext _context = null;
	
	public long getCoBorrowerId() {
		return (getId() == 0) ? ICMSConstant.LONG_INVALID_VALUE : getId();
	}
	public void setCoBorrowerId(long id) {
		setId(Long.valueOf(id));
	}
	
	public abstract Long getId();
	public abstract void setId(Long id);
	
	public long getMainProfileId() {
		return (getMainProfileIdFk()!= null) ? getMainProfileIdFk().longValue() : ICMSConstant.LONG_INVALID_VALUE;
	}
	
	public void setMainProfileId(long mainProfileId) {
		setMainProfileIdFk(Long.valueOf(mainProfileId));
	}
	
	public abstract Long getMainProfileIdFk();
	public abstract void setMainProfileIdFk(Long mainProfileId);
	
	public abstract String getCoBorrowerLiabId();
	public abstract void setCoBorrowerLiabId(String coBorrowerLiabId);
	
	public abstract String getCoBorrowerName();
	public abstract void setCoBorrowerName(String coBorrowerName);
	
	public abstract String getIsInterfaced();
	public abstract void setIsInterfaced(String isInyerfaced);
	
	public ICoBorrowerDetails getValue() {
		ICoBorrowerDetails value = new OBCoBorrowerDetails();
		AccessorUtil.copyValue(this, value);
		return value;
	}
	
	public void setValue(ICoBorrowerDetails value) {
		AccessorUtil.copyValue(value, this);
	}
	
	public Long ejbCreate(ICoBorrowerDetails coBorrower) throws CreateException {
		if(coBorrower == null)
			throw new CreateException("ICoBorrowerDetails is null");
		long primayKey = 0;
		try {
			primayKey = Long.parseLong((new SequenceManager()).getSeqNum(SEQUENCE));
			AccessorUtil.copyValue(coBorrower, this, EXCLUDE_METHODS);
			setId(primayKey);
		} catch (NumberFormatException e) {
			DefaultLogger.error(this, "Exception while converting sequence", e);
		} catch (Exception e) {
			DefaultLogger.error(this, "Exception on create", e);
		}
		DefaultLogger.info(this, "Object created with id: "+primayKey);
		
		return Long.valueOf(primayKey);
	}

	public void ejbPostCreate(ICoBorrowerDetails stock) throws CreateException {
		
	}
	
	public void ejbActivate() throws EJBException, RemoteException {
		
	}

	public void ejbLoad() throws EJBException, RemoteException{
		
	}

	public void ejbPassivate() throws EJBException, RemoteException{
		
	}

	public void ejbRemove() throws RemoveException, EJBException, RemoteException {
		
	}

	public void ejbStore() throws EJBException, RemoteException {
		
	}

	public void setEntityContext(EntityContext ctx) throws EJBException, RemoteException {
		_context = ctx;
	}

	public void unsetEntityContext() throws EJBException, RemoteException {
		_context = null;
	}
}
