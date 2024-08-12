package com.integrosys.cms.app.lad.bus;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.limit.bus.ILimit;


/**
 @author $Author: Abhijit R $
 */

public interface ILADDao {

	List getLAD(long limit_profile_id,String stage)throws Exception;
	
	List getLADNormal(long limit_profile_id)throws Exception;
	
	List getAllLAD()throws Exception;
	
	List getAllLADNotGenerated()throws Exception;
	
	List getLADItem(long lad_id)throws Exception;
	
	List getLADSubItem(long lad_id)throws Exception;
	
	ILAD createLAD( ILAD ilad) 	throws Exception;
	
	ILADItem createLADItem( ILADItem ilad) 	throws Exception;
	
	ILADSubItem createLADSubItem( ILADSubItem ilad) 	throws Exception;
	
	ILAD updateLAD(ILAD ilad)throws Exception;
	
	public boolean isUniqueCode(String coloumn ,String value)throws Exception;
	
	public void deleteLADItem( long ilad) 	throws Exception;
	
	public void deleteLADSubItem( long ilad) 	throws Exception;
	
	public void updateLADOperation(String operation, long limitProfileId)throws Exception;
	
	public List getLADSubItemSorted(long lad_id)throws Exception ;
	
	//public ICheckListItem getCheckListItemLad(long limit_profile_id);
	
	public List getLADSubItemById(long id)throws Exception;
	
	public long getReceivedLAD(long ladID)throws Exception;
	
	public String getFacilityInfo(long id);
	
	public String getSecurityInfo(long id);
	
	public List checkPendingLAD(long id);
	
	public List getTraxID(long id);
	
	public List checkPendingLADAtferCreate(long id);
	
	public List getUpdateLad(long id);
	
	
	public long getLadid(long id);


	
}
