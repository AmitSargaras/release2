package com.integrosys.cms.batch.sibs.customer;

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

/**
 * Implementation of {@link ICustomerDao} using hibernate routine
 * 
 * @author Loh Gek Phuang
 * @author Chong Jun Yong
 * 
 */
public class CustomerDaoImpl extends HibernateDaoSupport implements ICustomerDao {

	public ICustomerInfo saveCustomer(String entityName, ICustomerInfo obUpdate) {
		if (obUpdate.getRecordType().equalsIgnoreCase("D")) {
			String key = (String) getHibernateTemplate().save(entityName, obUpdate);
			return (ICustomerInfo) getHibernateTemplate().load(entityName, key);
		}

		return obUpdate;

	}

	public ICustomer fuseCustomer(String entityName, ICustomer obFusion) {
		if (obFusion.getRecordType().equalsIgnoreCase("D")) {
			return (ICustomer) getHibernateTemplate().load(entityName, obFusion.getCustomerID());
		}

		return obFusion;
	}

	public void saveCustomerInfo(final String entityName, final List customerUpdateList) {
		getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException {
				checkWriteOperationAllowed(session);
				for (Iterator it = customerUpdateList.iterator(); it.hasNext();) {
					session.saveOrUpdate(entityName, it.next());
				}
				return null;
			}
		});

	}

	public void fuseCustomerInfo(final String entityName, final List customerFusionList) {
		getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException {
				checkWriteOperationAllowed(session);
				for (Iterator it = customerFusionList.iterator(); it.hasNext();) {
//					System.out.println(" fuse customer info : session.saveOrUpdate(it.next()); ");
//					OBCustomerUpdate obInfo = (OBCustomerUpdate) it.next();
					session.saveOrUpdate(entityName, it.next());
				}
				return null;
			}
		});

	}

	private void checkWriteOperationAllowed(Session session) throws InvalidDataAccessApiUsageException {
		if (getHibernateTemplate().isCheckWriteOperations()
			//	&& getHibernateTemplate().getFlushMode() != HibernateTemplate.FLUSH_EAGER
				&& session.getFlushMode().lessThan(FlushMode.COMMIT)) {
			throw new InvalidDataAccessApiUsageException(
					"Write operations are not allowed in read-only mode (FlushMode.NEVER/MANUAL): "
							+ "Turn your Session into FlushMode.COMMIT/AUTO or remove 'readOnly' marker from transaction definition.");
		}
	}
}
