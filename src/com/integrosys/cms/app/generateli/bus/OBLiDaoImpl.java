package com.integrosys.cms.app.generateli.bus;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.orm.hibernate4.HibernateCallback;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.securityenvelope.bus.ISecEnvelope;


public class OBLiDaoImpl extends HibernateDaoSupport implements ILiDocDao{
	
	public List getAllLiDocList(){
		
		List curList = (List) getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException{
				Query q = session.getNamedQuery("clims.gen.li");
				q.list();
				
				return q.list();
			}
		});
		
		return curList;
		
	}
	
	public void saveOrUpdateDocItem(final OBLiDoc anOBLiDoc)
	{
		try
		{
		
		getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException {
				session.saveOrUpdate(anOBLiDoc);
				return null;
			}
		});
		}catch (Exception e)
		{
			DefaultLogger.debug(this,"Database error : " + e.getCause());
		}
	}
	
}
