package com.integrosys.cms.app.component.proxy;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.holiday.bus.HolidayException;
import com.integrosys.cms.app.holiday.bus.IHoliday;
import com.integrosys.cms.app.holiday.trx.IHolidayTrxValue;
import com.integrosys.cms.app.component.bus.IComponent;
import com.integrosys.cms.app.component.trx.IComponentTrxValue;
import com.integrosys.cms.app.otherbank.bus.OtherBankException;
import com.integrosys.cms.app.transaction.ITrxContext;

public interface IComponentProxyManager {
	
	public SearchResult getAllActualComponent() throws ComponentException,TrxParameterException,TransactionException;
	public IComponentTrxValue makerCreateComponent(ITrxContext anITrxContext, IComponent anICCComponent)throws ComponentException,TrxParameterException,TransactionException;
	public IComponentTrxValue getComponentTrxValue(long aComponentId) throws ComponentException,TrxParameterException,TransactionException;
	public IComponentTrxValue getComponentByTrxID(String aTrxID) throws ComponentException,TransactionException,CommandProcessingException;
	public IComponentTrxValue checkerApproveComponent(ITrxContext anITrxContext, IComponentTrxValue anIComponentTrxValue) throws ComponentException,TrxParameterException,TransactionException;
	public IComponentTrxValue checkerRejectComponent(ITrxContext anITrxContext, IComponentTrxValue anIComponentTrxValue) throws ComponentException,TrxParameterException,TransactionException;
	public IComponentTrxValue makerDeleteComponent(ITrxContext anITrxContext, IComponentTrxValue anICCComponentTrxValue, IComponent anICCComponent)throws ComponentException,TrxParameterException,TransactionException;
	public IComponentTrxValue makerEditRejectedComponent(ITrxContext anITrxContext, IComponentTrxValue anIComponentTrxValue, IComponent anComponent) throws ComponentException,TrxParameterException,TransactionException;
	public IComponentTrxValue makerUpdateComponent(ITrxContext anITrxContext, IComponentTrxValue anICCComponentTrxValue, IComponent anICCComponent)
	throws ComponentException,TrxParameterException,TransactionException;
	public IComponentTrxValue makerUpdateSaveCreateComponent(ITrxContext anITrxContext, IComponentTrxValue anICCComponentTrxValue, IComponent anICCComponent)
	throws ComponentException,TrxParameterException,TransactionException;
	public IComponentTrxValue makerCloseRejectedComponent(ITrxContext anITrxContext, IComponentTrxValue anIComponentTrxValue) throws ComponentException,TrxParameterException,TransactionException;
	public IComponentTrxValue makerCloseDraftComponent(ITrxContext anITrxContext, IComponentTrxValue anIComponentTrxValue) throws ComponentException,TrxParameterException,TransactionException;
	public boolean isUniqueCode(String componentName)throws ComponentException, TrxParameterException,
	TransactionException;
	public SearchResult getSearchComponentList(String componentName) throws ComponentException;
	
}
