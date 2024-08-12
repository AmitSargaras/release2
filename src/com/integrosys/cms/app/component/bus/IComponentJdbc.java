package com.integrosys.cms.app.component.bus;

import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.holiday.bus.HolidayException;

public interface IComponentJdbc {

	SearchResult getAllComponent (String searchBy,String searchText)throws ComponentException;
	SearchResult getAllComponent()throws ComponentException;
	List getAllComponentSearch(String login)throws ComponentException;
}
