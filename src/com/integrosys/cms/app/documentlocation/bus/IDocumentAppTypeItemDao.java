package com.integrosys.cms.app.documentlocation.bus;

import java.io.Serializable;
import java.util.List;

public interface IDocumentAppTypeItemDao {

	public IDocumentAppTypeItem getDocAppTypeItem(Serializable key);
	
	public List getDocAppTypeItemByDocId(String key , String status);

	public IDocumentAppTypeItem createDocAppTypeItem(String entityName, IDocumentAppTypeItem item);

	public IDocumentAppTypeItem updateDocAppTypeItem(String entityName, IDocumentAppTypeItem item);
	
	public void saveOrUpdateDocAppTypeItemByDocId(IDocumentAppTypeItem item);
	
	public void deleteDocAppTypeItemByDocId(String key , final String status);
	
	public void updateDocAppTypeItemStatus(String key , String status);
	
	public List getDocIdByMasterListId(final String key);
	
	public void updateDocAppTypeItemId(final String key , final String itemId);

}
