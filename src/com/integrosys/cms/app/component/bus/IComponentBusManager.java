package com.integrosys.cms.app.component.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.holiday.bus.HolidayException;
import com.integrosys.cms.app.holiday.bus.IHoliday;

public interface IComponentBusManager {
	
	SearchResult getAllComponent()throws ComponentException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	SearchResult getAllComponent(String searchBy,String searchText)throws ComponentException,TrxParameterException,TransactionException;
	
	IComponent updateToWorkingCopy(IComponent workingCopy, IComponent imageCopy) throws ComponentException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	IComponent updateComponent(IComponent item) throws ComponentException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	IComponent getComponentById(long id) throws ComponentException,TrxParameterException,TransactionException;
	
	IComponent createComponent(IComponent component)throws ComponentException;
	IComponent insertComponent(IComponent component)throws ComponentException;
	
	IFileMapperId createFileId(IFileMapperId obFileMapperID)throws ComponentException;
	
	public boolean isUniqueCode(String componentName)throws ComponentException, TrxParameterException,
	TransactionException;
	public SearchResult getSearchComponentList(String componentName) throws ComponentException;

}
