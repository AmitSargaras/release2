package com.integrosys.cms.app.newtatmaster.bus;

import java.io.Serializable;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.component.bus.IComponent;


public class TatMasterDaoImpl extends HibernateDaoSupport implements ITatMasterDao{
	
	private ITatMasterDao tatMasterDao;

	public ITatMasterDao getTatMasterDao() {
		return tatMasterDao;
	}

	public void setTatMasterDao(ITatMasterDao tatMasterDao) {
		this.tatMasterDao = tatMasterDao;
	}
	
	public INewTatMaster getTatMaster(String entityName, Serializable key)throws TatMasterException {
		
		if(!(entityName==null|| key==null)){
		
		return (INewTatMaster) getHibernateTemplate().get(entityName, key);
		}else{
			throw new TatMasterException("ERROR-- Entity Name Or Key is null in DAO");
		}
	}
	
	public INewTatMaster createTatMaster(String entityName,INewTatMaster tatMaster)
			throws TatMasterException {
		if(!(entityName==null|| tatMaster==null)){
			
					
			
			Long key = (Long) getHibernateTemplate().save(entityName, tatMaster);
			tatMaster.setId(key.longValue());
			
			return tatMaster;
			}else{
				throw new ComponentException("ERROR- Entity name or key is null ");
			}
	}
	
	public INewTatMaster updateTatMaster(String entityName, INewTatMaster item)throws ComponentException{
		if(!(entityName==null|| item==null)){
			getHibernateTemplate().update(entityName, item);
			return  (INewTatMaster) getHibernateTemplate().load(entityName, new Long(item.getId()));
		}else{
			throw new ComponentException("ERROR-- Entity Name Or Key is null");
		}
	}

}
