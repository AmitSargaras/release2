package com.integrosys.cms.app.documentlocation.bus;

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

import com.integrosys.cms.app.securityenvelope.bus.ISecEnvelope;


public class DocumentAppTypeItemDaoImpl extends HibernateDaoSupport implements IDocumentAppTypeItemDao{
	
	public IDocumentAppTypeItem getDocAppTypeItem(Serializable key){
		
		return (IDocumentAppTypeItem) getHibernateTemplate().load(OBDocumentAppTypeItem.class, key);
		
	}
	
	public void updateDocAppTypeItemStatus(final String key , final String status){
		
		getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException {
				Query q = session.getNamedQuery("clims.doc.app.type.update");
				q.setString("applicationRefNo", key);
				q.setString("status", status);
				q.executeUpdate();
				return null;
			}
		});
		
		return;
		
	}
	
	public void deleteDocAppTypeItemByDocId(final String key , final String status){
		
		getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException {
				Query q = session.getNamedQuery("clims.doc.app.type.delete");
				q.setString("applicationRefNo", key);
				q.setString("status", status);
				q.executeUpdate();
				return null;
			}
		});
		
		return;
		
	}
	
	public void updateDocAppTypeItemId(final String key , final String itemId){
		
		getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException {
				Query q = session.getNamedQuery("clims.doc.item.id.update");
				q.setString("itemId", itemId);
				q.setString("applicationRefNo", key);
				q.executeUpdate();
				return null;
			}
		});
		
		return;
		
	}
	
	public List getDocAppTypeItemByDocId(final String key , final String status){
		
		List curList = (List) getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException {
				Query q = session.getNamedQuery("clims.doc.app.type");
				q.setString("applicationRefNo", key);
				q.setString("status", status);
				q.list();
				
				return q.list();
			}
		});
		
		return curList;
		
	}
	
	public List getDocIdByMasterListId(final String key){
		
		List curList = (List) getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException {
				Query q = session.getNamedQuery("clims.get.docid.by.masterid");
				q.setString("masterid", key);
				q.list();
				
				return q.list();
			}
		});
		
		return curList;
		
	}
	
	public void saveOrUpdateDocAppTypeItemByDocId(final IDocumentAppTypeItem item){
		
		List curList = (List) getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException {
				
				session.delete(item);
				session.saveOrUpdate(item);
				
				return null;
			}
		});
		
		return;
		
	}
	
	
	public IDocumentAppTypeItem createDocAppTypeItem(String entityName, IDocumentAppTypeItem item){
		Long key = (Long) getHibernateTemplate().save(item);

		item.setDocumentLoanId(key);
		return item;
	}	
	
	public IDocumentAppTypeItem updateDocAppTypeItem(String entityName, IDocumentAppTypeItem item){
		getHibernateTemplate().update(item);

		return (IDocumentAppTypeItem) getHibernateTemplate().load(entityName, item.getDocumentLoanId());
		
	}

}
