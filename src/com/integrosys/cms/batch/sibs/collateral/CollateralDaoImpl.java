package com.integrosys.cms.batch.sibs.collateral;

/**
 * @author User
 * @date 02 oct 08 1908hr
 *
 */

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

public class CollateralDaoImpl extends HibernateDaoSupport implements ICollateralDao {

	public ICollateralSMF saveSharesMarginFinancing(String entityName, ICollateralSMF obCollSMF) {
		// TODO Auto-generated method stub
    	String key = null;
		try {
			if ( obCollSMF.getRecordType().equalsIgnoreCase("D") ) {
				//System.out.println("here in SMF dao ");
				key = (String)getHibernateTemplate().save(entityName, obCollSMF);
			}
		} catch (Exception e) {
			DefaultLogger.error(this,e);
		}
		//return (ICollateralSMF) getHibernateTemplate().load(entityName, key);
		//System.out.println("\n in collateral dao impl : save SMF : before return of obejct SMF");
		return obCollSMF;
    }

	public ICollateralFD saveFdMaturity(String entityName, ICollateralFD obCollFD) {
		// TODO Auto-generated method stub
    	String key = null;
		try {
			if ( obCollFD.getRecordType().equalsIgnoreCase("D") ) {
				key = (String)getHibernateTemplate().save(entityName, obCollFD);
			}
		} catch (Exception e) {
			DefaultLogger.error(this,e);
		}
		//return (ICollateralFD) getHibernateTemplate().load(entityName, key);
		//System.out.println("\n in collateral dao impl : save FD : before return of obejct FD");
		return obCollFD;
    }

	public void saveCollateralFDList(final String entityName, final List fdList) {
		try {
			getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {

				public Object doInHibernate(Session session) throws HibernateException {
					checkWriteOperationAllowed(session);
					Iterator it = fdList.iterator();
					//for (Iterator it = creditAppODTLList.iterator(); it.hasNext();) {
					while ( it.hasNext() ) {
						session.saveOrUpdate(entityName, it.next() );
					}
					return null;
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.error(" failed to save coll FD info ", e);
		}
	}


	public void saveCollateralSMFList(final String entityName, final List smfList) {
		try {
			getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {

				public Object doInHibernate(Session session) throws HibernateException {
					checkWriteOperationAllowed(session);
					Iterator it = smfList.iterator();
					//for (Iterator it = creditAppODTLList.iterator(); it.hasNext();) {
					while ( it.hasNext() ) {
						session.saveOrUpdate(entityName, it.next() );
					}
					return null;
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.error(" failed to save SMF info ", e);
		}
	}


	private void checkWriteOperationAllowed(Session session) throws InvalidDataAccessApiUsageException {
		if (getHibernateTemplate().isCheckWriteOperations()
				//&& getHibernateTemplate().getFlushMode() != HibernateTemplate.FLUSH_EAGER
				&& session.getFlushMode().lessThan(FlushMode.COMMIT)) {
			throw new InvalidDataAccessApiUsageException(
					"Write operations are not allowed in read-only mode (FlushMode.NEVER/MANUAL): "
							+ "Turn your Session into FlushMode.COMMIT/AUTO or remove 'readOnly' marker from transaction definition.");
		}
	}

}
