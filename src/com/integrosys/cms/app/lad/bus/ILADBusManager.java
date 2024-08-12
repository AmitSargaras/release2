package com.integrosys.cms.app.lad.bus;

import java.util.List;

/**
@author $Author: Abhijit R $
*/

public interface ILADBusManager {
	
	

	List getLAD(long limit_profile_id,String stage)throws Exception;
	
	List getLADItem(long lad_id)throws Exception;
	
	List getLADSubItem(long lad_id)throws Exception;
	
	ILAD createLAD( ILAD ilad) 	throws Exception;
	
	ILADItem createLADItem( ILADItem ilad) 	throws Exception;
	
	ILADSubItem createLADSubItem( ILADSubItem ilad) 	throws Exception;
	
	ILAD updateLAD(ILAD ilad)throws Exception;
	
	public void deleteLADItem( long ilad) 	throws Exception;
	
	public void deleteLADSubItem( long ilad) 	throws Exception;
	
	public List getLADSubItemById(long id) throws Exception ;
}
