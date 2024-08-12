package com.integrosys.cms.app.propertyindex.proxy;

import java.util.List;
import java.util.Set;

import com.integrosys.cms.app.propertyindex.bus.IPropertyIdx;
import com.integrosys.cms.app.propertyindex.bus.PropertyIdxException;
import com.integrosys.cms.app.propertyindex.trx.IPropertyIdxTrxValue;
import com.integrosys.cms.app.propertyparameters.bus.PropertyParametersException;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * Title: CLIMS 
 * Description: 
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Andy Wong 
 * Date: Jan 17, 2008
 */

public interface IPropertyIdxProxyManager {

	public List getAllActual() throws PropertyIdxException;

    public boolean isSecSubTypeValTypeExist (long propertyIndexID, Set secSubTypeList, String valDesc);

    public IPropertyIdxTrxValue getPropertyIdxByTrxID(String aTrxID) throws PropertyIdxException;

	public IPropertyIdxTrxValue getPropertyIdxTrxValue(long aPropertyIdxId) throws PropertyIdxException;

	public IPropertyIdxTrxValue makerCreatePropertyIdx(ITrxContext anITrxContext, IPropertyIdx anICCPropertyIdx)
        throws PropertyIdxException;

	public IPropertyIdxTrxValue makerUpdatePropertyIdx(ITrxContext anITrxContext, IPropertyIdxTrxValue anICCPropertyIdxTrxValue, IPropertyIdx anICCPropertyIdx)
		throws PropertyIdxException;

	public IPropertyIdxTrxValue makerDeletePropertyIdx(ITrxContext anITrxContext, IPropertyIdxTrxValue anICCPropertyIdxTrxValue, IPropertyIdx anICCPropertyIdx)
		throws PropertyIdxException;

	public IPropertyIdxTrxValue checkerApprovePropertyIdx(ITrxContext anITrxContext, IPropertyIdxTrxValue anIPropertyIdxTrxValue) throws PropertyIdxException;

	public IPropertyIdxTrxValue checkerRejectPropertyIdx(ITrxContext anITrxContext, IPropertyIdxTrxValue anIPropertyIdxTrxValue) throws PropertyIdxException;

	public IPropertyIdxTrxValue makerEditRejectedPropertyIdx(ITrxContext anITrxContext, IPropertyIdxTrxValue anIPropertyIdxTrxValue, IPropertyIdx anPropertyIdx) throws PropertyIdxException;

	public IPropertyIdxTrxValue makerCloseRejectedPropertyIdx(ITrxContext anITrxContext, IPropertyIdxTrxValue anIPropertyIdxTrxValue) throws PropertyIdxException;

}
