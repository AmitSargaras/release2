package com.integrosys.cms.app.generateli.bus;

import java.io.Serializable;
import java.util.List;

public interface ILiDocDao {
	
	public List getAllLiDocList();
	public void saveOrUpdateDocItem(final OBLiDoc anOBLiDoc);
	

}
