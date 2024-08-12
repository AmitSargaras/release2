package com.integrosys.cms.app.limit.bus;

import java.rmi.RemoteException;
import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public abstract class EBFacilityCoBorrowerDetailsBean implements EntityBean, IFacilityCoBorrowerDetails {

	private static final long serialVersionUID = 1L;
	private static final String SEQUENCE = ICMSConstant.SEQUENCE_FACILITY_CO_BORROWER;
	private static final String[] EXCLUDE_METHODS = new String[] {"getCoBorrowerId", "getLimitId"};
		
	protected EntityContext _context = null;
	
	public long getCoBorrowerId() {
		return (getId() == 0) ? ICMSConstant.LONG_INVALID_VALUE : getId();
	}
	public void setCoBorrowerId(long id) {
		setId(Long.valueOf(id));
	}
	
	public abstract Long getId();
	public abstract void setId(Long id);
	
	public abstract String getCreateBy();
	public abstract void setCreateBy(String createBy);
	
	public abstract Date getCreationDate();
	public abstract void setCreationDate(Date creationDate);

	public long getLimitId() {
		return (getLimitIdFk()!= null) ? getLimitIdFk().longValue() : ICMSConstant.LONG_INVALID_VALUE;
	}
	
	public void setLimitId(long limitId) {
		setLimitIdFk(Long.valueOf(limitId));
	}
	
	public abstract Long getLimitIdFk();
	public abstract void setLimitIdFk(Long limitId);
	
	public abstract Long getMainProfileId();
	public abstract void setMainProfileId(Long mainProfileId);
	
	public abstract String getCoBorrowerLiabId();
	public abstract void setCoBorrowerLiabId(String coBorrowerLiabId);
	
	public abstract String getCoBorrowerName();
	public abstract void setCoBorrowerName(String coBorrowerName);
	
	public IFacilityCoBorrowerDetails getValue() {
		IFacilityCoBorrowerDetails value = new OBFacilityCoBorrowerDetails();
		AccessorUtil.copyValue(this, value);
		return value;
	}
	
	public void setValue(IFacilityCoBorrowerDetails value) {
		AccessorUtil.copyValue(value, this);
	}
	
	public Long ejbCreate(IFacilityCoBorrowerDetails coBorrower) throws CreateException {
		if(coBorrower == null)
			throw new CreateException("IFacilityCoBorrowerDetails is null");
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

	public void ejbPostCreate(IFacilityCoBorrowerDetails stock) throws CreateException {
		
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
