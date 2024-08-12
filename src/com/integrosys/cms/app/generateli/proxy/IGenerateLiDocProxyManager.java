package com.integrosys.cms.app.generateli.proxy;

import java.util.List;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.diary.bus.DiaryItemException;
import com.integrosys.cms.app.diary.bus.DiaryItemSearchCriteria;
import com.integrosys.cms.app.diary.bus.IDiaryItem;
import com.integrosys.cms.app.documentlocation.bus.IDocumentAppTypeItem;
import com.integrosys.component.bizstructure.app.bus.ITeam;

/**
 * This interface defines the list of attributes that will be available to the
 * generation of a diary item
 * 
 * @author $Author: jtan $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2004/06/29 10:03:55 $ Tag: $Name: $
 */
public interface IGenerateLiDocProxyManager {
	
	public List getAllLiDocList();
	public void saveOrUpdateDocItem(String checklistId, String fileLocation , String liTemplateName , String generatedBy);

	
}
