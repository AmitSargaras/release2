package com.integrosys.cms.app.generateli.bus;

import java.util.List;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.component.bizstructure.app.bus.ITeam;

public interface IGenerateLiDocBusManager {
	
	public List getAllLiDocList();
	public void saveOrUpdateDocItem(String checklistId, String fileLocation , String liTemplateName , String generatedBy);
	
}
