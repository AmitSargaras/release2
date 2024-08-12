package com.integrosys.cms.app.generateli.bus;

import java.util.Date;
import java.util.List;

import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.component.bizstructure.app.bus.ITeam;

public class GenerateLiDocBusManagerImpl implements IGenerateLiDocBusManager {

	private ILiDocDao liDocDao;
	
	public List getAllLiDocList()
	{
		return getLiDocDao().getAllLiDocList();
	}
	
	public void saveOrUpdateDocItem(String checklistId, String fileLocation , String liTemplateName , String generatedBy)
	{
		
		OBLiDoc anOBLiDoc = new OBLiDoc();
		anOBLiDoc.setChecklistId(new Long(checklistId));
		anOBLiDoc.setFileLocation(fileLocation);
		anOBLiDoc.setLiTemplateName(liTemplateName);
		anOBLiDoc.setGeneratedDate(new Date());
		anOBLiDoc.setGeneratedBy(generatedBy);
		
		getLiDocDao().saveOrUpdateDocItem(anOBLiDoc);
		
		
		
		return;
		
		
	}


	public ILiDocDao getLiDocDao() {
		return liDocDao;
	}


	public void setLiDocDao(ILiDocDao liDocDao) {
		this.liDocDao = liDocDao;
	}






}
