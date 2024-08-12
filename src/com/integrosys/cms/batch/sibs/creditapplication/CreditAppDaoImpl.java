package com.integrosys.cms.batch.sibs.creditapplication;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * 
 * who: gek phuang
 * Date: 29th Sep 2008
 * Time: 1635hr
 * 
 */

public class CreditAppDaoImpl extends HibernateDaoSupport implements ICreditAppDao {

    public ICreditApplication saveCreditAppODTL(String entityName, ICreditApplicationODTL obCAODTL) {
    	String key = null;
		try {
			if ( obCAODTL.getRecordType().equalsIgnoreCase("D") ) {
				key = (String)getHibernateTemplate().save(entityName, obCAODTL);
			}
		} catch (Exception e) {
			DefaultLogger.error(this,e);
		}
		//return (ICreditApplication)getHibernateTemplate().load(entityName, key);
		return obCAODTL;
    }

    public ICreditApplication saveCreditAppNplClosedAcc(String entityName, ICreditApplicationNplClosedAcc obNplClosedAcc) {
    	String key = null;
		try {
			if ( obNplClosedAcc.getRecordType().equalsIgnoreCase("D") ) {
				key = (String)getHibernateTemplate().save(entityName, obNplClosedAcc);
				
			}
		} catch (Exception e) {
			DefaultLogger.error(this,e);
		}
		//return (ICreditApplication) getHibernateTemplate().load(entityName, key);
		return obNplClosedAcc;
    }


	public void saveCreditAppODTLList(final String entityName, final List creditAppODTLList) {
		try {
			getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {

				public Object doInHibernate(Session session) throws HibernateException {
					checkWriteOperationAllowed(session);
					Iterator it = creditAppODTLList.iterator();
					//for (Iterator it = creditAppODTLList.iterator(); it.hasNext();) {
					while ( it.hasNext() ) {
						session.saveOrUpdate(entityName, it.next() );
					}
					return null;
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.error(" failed to save credit app ODTL info ", e);
		}
	}


	public void saveCreditAppNplClosedAccList(final String entityName, final List creditAppNplClosedAccList) {
		try {
			getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException {
				checkWriteOperationAllowed(session);
				Iterator it = creditAppNplClosedAccList.iterator();
				//for (Iterator it = creditAppNplClosedAccList.iterator(); it.hasNext();) {
				while ( it.hasNext() ) {
					session.saveOrUpdate(entityName, it.next() );
				}
				return null;
			}
		});
		} catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.error(" failed to save credit app ODTL info ", e);
		}
	}


	private void checkWriteOperationAllowed(Session session) throws InvalidDataAccessApiUsageException {
		if (getHibernateTemplate().isCheckWriteOperations()
			//	&& getHibernateTemplate().getFlushMode() != FlushMode.FLUSH_EAGER
				&& session.getFlushMode().lessThan(FlushMode.COMMIT)) {
			throw new InvalidDataAccessApiUsageException(
					"Write operations are not allowed in read-only mode (FlushMode.NEVER/MANUAL): "
							+ "Turn your Session into FlushMode.COMMIT/AUTO or remove 'readOnly' marker from transaction definition.");
		}
	}

}
