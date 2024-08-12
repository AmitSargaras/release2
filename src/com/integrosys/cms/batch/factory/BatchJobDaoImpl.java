package com.integrosys.cms.batch.factory;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

/**
 * Implementation of BatchJobDao using hibernate routine
 * 
 * @author Chong Jun Yong
 * 
 */
public class BatchJobDaoImpl extends HibernateDaoSupport implements BatchJobDao {

	public OBBatchJobStatus createBatchJobStatus(OBBatchJobStatus status) {
		Long key = (Long) getHibernateTemplate().save(status);

		return (OBBatchJobStatus) getHibernateTemplate().load(OBBatchJobStatus.class, key);
	}

	public OBBatchJobStatus retrieveBatchJobStatus(Long key) {
		return (OBBatchJobStatus) getHibernateTemplate().load(OBBatchJobStatus.class, key);
	}

	public OBBatchJobStatus updateBatchJobStatus(OBBatchJobStatus status) {
		getHibernateTemplate().update(status);

		return (OBBatchJobStatus) getHibernateTemplate().load(OBBatchJobStatus.class, status.getBatchId());
	}

}
