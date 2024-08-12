package com.integrosys.cms.app.cersaiMapping.bus;

import java.io.Serializable;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

public class CersaiMappingDaoImpl extends HibernateDaoSupport implements ICersaiMappingDao{

	@Override
	public ICersaiMapping createCersaiMapping(String cersaiMappingName, ICersaiMapping cersaiMapping)
			throws CersaiMappingException {
		if(!(cersaiMappingName==null|| cersaiMapping==null)){
			
			Long key = (Long) getHibernateTemplate().save(cersaiMappingName, cersaiMapping);
			cersaiMapping.setId(key.longValue());
			return cersaiMapping;
			}else{
				throw new CersaiMappingException("ERROR- Entity name or key is null ");
			}
	}

	@Override
	public ICersaiMapping getCersaiMapping(String entityName, Serializable key) {
		// TODO Auto-generated method stub
		if(!(entityName==null|| key==null)){
			
			return (ICersaiMapping) getHibernateTemplate().get(entityName, key);
			}else{
				throw new CersaiMappingException("ERROR-- Entity Name Or Key is null");
			}
	}
	
	@Override
	public ICersaiMapping updateCersaiMapping(String entityName, ICersaiMapping item)throws CersaiMappingException{
		if(!(entityName==null|| item==null)){
			getHibernateTemplate().update(entityName, item);
			return  (ICersaiMapping) getHibernateTemplate().load(entityName, new Long(item.getId()));
		}else{
			throw new CersaiMappingException("ERROR-- Entity Name Or Key is null");
		}
	}
	
}

