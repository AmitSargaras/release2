package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import javax.ejb.EJBLocalObject;

import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.vo.ILeadBankStock;

public interface EBLeadBankStockLocal extends EJBLocalObject {
	
	public long getLeadBankStockId();
	
	public ILeadBankStock getValue();

	public void setValue(ILeadBankStock value);
	
}
