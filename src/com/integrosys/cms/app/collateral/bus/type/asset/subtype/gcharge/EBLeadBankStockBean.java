package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import java.math.BigDecimal;
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
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.vo.ILeadBankStock;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.vo.OBLeadBankStock;

public abstract class EBLeadBankStockBean implements EntityBean, ILeadBankStock {

	private static final long serialVersionUID = 1L;
	private static final String SEQUENCE = ICMSConstant.SEQUENCE_LEAD_BANK_STOCK;
	private static final String[] EXCLUDE_METHODS = new String[] {"getId", "getLeadBankStockId", "getGeneralChargeDetailId"};
	
	protected EntityContext _context = null;
	
	public long getLeadBankStockId() {
		return (getId() == 0) ? ICMSConstant.LONG_INVALID_VALUE : getId();
	}
	
	public void setLeadBankStockId(long id) {
		setId(Long.valueOf(id));
	}

	public EBLeadBankStockBean(){
		
	}
	
	public abstract Long getId();

	public abstract void setId(Long id);

	public long getGeneralChargeDetailId() {
		return (getGeneralChargeDetailIdFK()!= null) ? getGeneralChargeDetailIdFK().longValue() : ICMSConstant.LONG_INVALID_VALUE;
	}
	
	public void setGeneralChargeDetailId(long generalChargeDetailId) {
		setGeneralChargeDetailIdFK(Long.valueOf(generalChargeDetailId));
	}

	public abstract Long getGeneralChargeDetailIdFK();
	
	public abstract void setGeneralChargeDetailIdFK(Long generalChargeDetailId);

	public abstract Date getCreationDate();

	public abstract void setCreationDate(Date creationDate);

	public abstract String getCreateBy();

	public abstract void setCreateBy(String createBy);

	public abstract Date getLastUpdateDate();

	public abstract void setLastUpdateDate(Date lastUpdateDate);

	public abstract String getLastUpdateBy();

	public abstract void setLastUpdateBy(String lastUpdateBy);
	
	public abstract BigDecimal getDrawingPowerAsPerLeadBank();

	public abstract void setDrawingPowerAsPerLeadBank(BigDecimal drawingPowerAsPerLeadBank);

	public abstract Double getBankSharePercentage();

	public abstract void setBankSharePercentage(Double bankSharePercentage);

	public abstract BigDecimal getStockAmount();

	public abstract void setStockAmount(BigDecimal stockAmount);

	public abstract BigDecimal getCreditorsAmount();

	public abstract void setCreditorsAmount(BigDecimal creditorsAmount);
		
	public abstract BigDecimal getDebtorsAmount();

	public abstract void setDebtorsAmount(BigDecimal debtorsAmount);

	public abstract BigDecimal getMarginOnStock();

	public abstract void setMarginOnStock(BigDecimal marginOnStock);

	public abstract BigDecimal getMarginOnCreditors();

	public abstract void setMarginOnCreditors(BigDecimal marginOnCreditors);

	public abstract BigDecimal getMarginOnDebtors();

	public abstract void setMarginOnDebtors(BigDecimal marginOnDebtors);
	
	public ILeadBankStock getValue() {
		ILeadBankStock value = new OBLeadBankStock();
		AccessorUtil.copyValue(this, value);
		return value;
	}
	
	public void setValue(ILeadBankStock value) {
		AccessorUtil.copyValue(value, this, EXCLUDE_METHODS);
	}
	
	public Long ejbCreate(ILeadBankStock stock) throws CreateException {
		if(stock == null)
			throw new CreateException("ILeadBankStock is null");
		long primayKey = 0;
		try {
			primayKey = Long.parseLong((new SequenceManager()).getSeqNum(SEQUENCE));
			AccessorUtil.copyValue(stock, this, EXCLUDE_METHODS);
			setLeadBankStockId(primayKey);
		} catch (NumberFormatException e) {
			DefaultLogger.error(this, "Exception while converting sequence", e);
		} catch (Exception e) {
			DefaultLogger.error(this, "Exception on create", e);
			e.printStackTrace();
		}
		DefaultLogger.info(this, "Object created with id: "+primayKey);
		
		return Long.valueOf(primayKey);
	}

	public void ejbPostCreate(ILeadBankStock stock) throws CreateException {
		
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
