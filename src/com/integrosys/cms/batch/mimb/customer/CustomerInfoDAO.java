package com.integrosys.cms.batch.mimb.customer;

import java.util.List;

/*updating the code to support hibernate 4 jars
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;*/
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
/**
 * Implementation of {@link ICustomerInfoDAO} using hibernate persistence
 * framework.
 * 
 * @author Forbys Wei
 * @author Chong Jun Yong
 * 
 */
public class CustomerInfoDAO extends HibernateDaoSupport implements ICustomerInfoDAO {

	public ICustomerInfo createCustomerInfoItem(ICustomerInfo customerInfoItem) {
		Long key = (Long) getHibernateTemplate().save(customerInfoItem);
		customerInfoItem.setTempID(key.longValue());
		return customerInfoItem;
	}

	/*updating the code to support hibernate 4 jars
	public void createCustomerInfoItems(List customerInfoItemFeedList) {
		getHibernateTemplate().saveOrUpdateAll(customerInfoItemFeedList);
	}*/
	
	public void createCustomerInfoItems(List customerInfoItemFeedList) {
		getHibernateTemplate().saveOrUpdate(customerInfoItemFeedList);
	}

}
