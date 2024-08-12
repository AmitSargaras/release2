package com.integrosys.cms.batch.feeds.property;

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

import com.integrosys.cms.app.collateral.bus.IValuation;

/**
 * Implementation of {@link IPropertyFileDAO} using hibernate persistence
 * framework.
 * 
 * @author Forbys Wei
 * @author Chong Jun Yong
 * 
 */
public class PropertyFileDAO extends HibernateDaoSupport implements IPropertyFileDAO {

	public IValuation createPropertyItem(String entityName, IValuation valuationItem) {
		Long key = (Long) getHibernateTemplate().save(entityName, valuationItem);
		valuationItem.setValuationID(key.longValue());

		return valuationItem;
	}

	public void createPropertyItems(final String entityName, final List valuationFeedsList) {
		getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException {
				checkWriteOperationAllowed(session);
				for (Iterator it = valuationFeedsList.iterator(); it.hasNext();) {
					session.saveOrUpdate(entityName, it.next());
				}
				return null;
			}
		});

	}


	public void saveOrUpdatePropertyFeeds(final List propertyFeedList) {
		getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException {
				checkWriteOperationAllowed(session);
				for (Iterator it = propertyFeedList.iterator(); it.hasNext();) {
					session.saveOrUpdate(it.next());
				}
				return null;
			}
		});
    }

    /**
	 * <p>
	 * <b>NOTE</b> port over from {@link HibernateTemplate}, because this method
	 * only visible within itself or subclasses.
	 * 
	 * <p>
	 * Check whether write operations are allowed on the given Session.
	 * 
	 * <p>
	 * Default implementation throws an InvalidDataAccessApiUsageException in
	 * case of <code>FlushMode.NEVER/MANUAL</code>. Can be overridden in
	 * subclasses.
	 * 
	 * @param session current Hibernate Session
	 * @throws InvalidDataAccessApiUsageException if write operations are not
	 *         allowed
	 * @see #setCheckWriteOperations
	 * @see #getFlushMode()
	 * @see #FLUSH_EAGER
	 * @see org.hibernate.Session#getFlushMode()
	 * @see org.hibernate.FlushMode#NEVER
	 * @see org.hibernate.FlushMode#MANUAL
	 */
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
