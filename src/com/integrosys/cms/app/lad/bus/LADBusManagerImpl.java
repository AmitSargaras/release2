package com.integrosys.cms.app.lad.bus;

import java.util.List;

import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.integrosys.cms.app.systemBank.bus.SystemBankException;

/**
 * @author $Author: Abhijit R $<br>
 * 
 * Bus Manager Imlication  declares the methods used by Dao and Jdbc
 */

public class LADBusManagerImpl  implements ILADBusManager {

	private ILADDao ladDao;
	
	
	public ILADDao getLadDao() {
		return ladDao;
	}

	public void setLadDao(ILADDao ladDao) {
		this.ladDao = ladDao;
	}


	public List getLAD(long limit_profile_id,String stage) throws Exception {
		try {
			return getLadDao().getLAD(limit_profile_id,stage);
		}
		catch (HibernateOptimisticLockingFailureException ex) {
			throw new Exception("ERROR---While Retriving LAD");
		}
	}
	
	public List getLADSubItemById(long id) throws Exception {
		try {
			return getLadDao().getLADSubItemById(id);
		}
		catch (HibernateOptimisticLockingFailureException ex) {
			throw new Exception("ERROR---While Retriving LAD");
		}
	}
	
	
	public List getLADItem(long lad_id) throws Exception {
		try {
			return getLadDao().getLADItem(lad_id);
		}
		catch (HibernateOptimisticLockingFailureException ex) {
			throw new Exception("ERROR---While Retriving LAD");
		}
	}
	public List getLADSubItem(long lad_id) throws Exception {
		try {
			return getLadDao().getLADSubItem(lad_id);
		}
		catch (HibernateOptimisticLockingFailureException ex) {
			throw new Exception("ERROR---While Retriving LAD");
		}
	}
	
			public ILAD createLAD(ILAD ilad)
			throws Exception {
					if(ilad!=null){
				
				return getLadDao().createLAD(ilad);
				}else{
					throw new Exception("ERROR- Entity name or key is null ");
				}
			}
			
			public ILADItem createLADItem(ILADItem ilad)
			throws Exception {
			if(ilad!=null){
				
				return getLadDao().createLADItem(ilad);
				}else{
					throw new Exception("ERROR- Entity name or key is null ");
				}
			}
			
			public ILADSubItem createLADSubItem(ILADSubItem ilad)
			throws Exception {
			if(ilad!=null){
				
				return getLadDao().createLADSubItem(ilad);
				}else{
					throw new Exception("ERROR- Entity name or key is null ");
				}
			}

			public ILAD updateLAD(ILAD ilad)
			throws Exception {
				if(ilad!=null){
					
					 getLadDao().updateLAD(ilad);
					return ilad;
					}else{
						throw new Exception("ERROR- Entity name or key is null ");
					}
			}
			public void deleteLADItem(long ilad)
			throws Exception {
			if(ilad!=0){
				
				getLadDao().deleteLADItem(ilad);
				}else{
					throw new Exception("ERROR- Entity name or key is null ");
				}
			}
			
			public void deleteLADSubItem(long ilad)
			throws Exception {
			if(ilad!=0){
				
				getLadDao().deleteLADSubItem(ilad);
				}else{
					throw new Exception("ERROR- Entity name or key is null ");
				}
			}
	
}
