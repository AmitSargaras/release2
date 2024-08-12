package com.integrosys.cms.app.generateli.proxy;

import java.util.List;

import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.generateli.bus.IGenerateLiDocBusManager;
import com.integrosys.component.bizstructure.app.bus.ITeam;

public class GenerateLiDocProxyManagerImpl implements IGenerateLiDocProxyManager {

	private IGenerateLiDocBusManager generateLiDocBusManager;
	
	public IGenerateLiDocBusManager getGenerateLiDocBusManager() {
		return generateLiDocBusManager;
	}


	public void setGenerateLiDocBusManager(
			IGenerateLiDocBusManager generateLiDocBusManager) {
		this.generateLiDocBusManager = generateLiDocBusManager;
	}
	
	
	
	public List getAllLiDocList()
	{
		return getGenerateLiDocBusManager().getAllLiDocList();
	}
	
	public void saveOrUpdateDocItem(String checklistId, String fileLocation , String liTemplateName , String generatedBy)
	{
		getGenerateLiDocBusManager().saveOrUpdateDocItem(checklistId, fileLocation, liTemplateName, generatedBy);
	}







}
