package com.integrosys.cms.app.securityenvelope.bus;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;


/**
 * Created by IntelliJ IDEA.
 * User: Erene Wong
 * Date: Jan 25, 2010
 * Time: 10:45:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class SecEnvelopeDaoImpl extends HibernateDaoSupport implements ISecEnvelopeDao {

    public ISecEnvelope createSecEnvelope(String entityName, ISecEnvelope secEnvelope) {
        Long key = (Long) getHibernateTemplate().save(entityName, secEnvelope);
        secEnvelope.setSecEnvelopeId(key.longValue());
		return secEnvelope;
	}

	public ISecEnvelope getSecEnvelope(String entityName, long key) {
        return (ISecEnvelope) getHibernateTemplate().get(entityName, new Long(key));
	}

	public ISecEnvelope updateSecEnvelope(String entityName, ISecEnvelope secEnvelope) {
		getHibernateTemplate().update(entityName, secEnvelope);
		return (ISecEnvelope) getHibernateTemplate().load(entityName, new Long(secEnvelope.getSecEnvelopeId()));
	}
	
	public List getEnvelopeItemByLimitProfileId(final long limitProfileid){
		
		List curList = (List) getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException {
				Query q = session.getNamedQuery("sec.env.item.by.limit.id");
				q.setLong("limitId", limitProfileid);
				return q.list();
			}
		});
		
		return curList;
		
	}
}
