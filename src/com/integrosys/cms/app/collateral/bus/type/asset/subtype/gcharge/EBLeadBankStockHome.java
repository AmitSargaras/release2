package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.vo.ILeadBankStock;

public interface EBLeadBankStockHome extends EJBHome {
	
	public EBLeadBankStockLocal create(ILeadBankStock stock) throws CreateException;

	public EBLeadBankStockLocal findByPrimaryKey(Long id) throws FinderException;

}
