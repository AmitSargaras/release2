package com.integrosys.cms.app.collateral.bus;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface EBInstrumentLocalHome extends EJBLocalHome {
	public EBInstrumentLocal create(IInstrument aInstrument) throws CreateException;

	public EBInstrumentLocal findByPrimaryKey(Long instrumentID) throws FinderException;

	public Collection findByCollateralID(Long collateralID) throws FinderException;
}