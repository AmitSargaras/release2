package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.vo.ILeadBankStock;

public interface EBLeadBankStock extends EJBObject {
	
	public ILeadBankStock getValue() throws RemoteException;
	
	public void setValue(ILeadBankStock stock) throws ConcurrentUpdateException, RemoteException;

}
