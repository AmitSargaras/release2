package com.integrosys.cms.batch.mimb.limit;

import java.util.List;

/*updating the code to support hibernate 4 jars
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;*/
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

/**
 * Implementation of {@link ILimitInformationFileDAO} using hibernate
 * persistence framework
 * 
 * @author Forbys Wei
 * @author Chong Jun Yong
 * 
 */
public class LimitInformationFileDAO extends HibernateDaoSupport implements ILimitInformationFileDAO {

	public ILimitInformation createLimitInfoItem(ILimitInformation limitInformationItem) {

		Long key = (Long) getHibernateTemplate().save(limitInformationItem);
		limitInformationItem.setTempID(key.longValue());
		return limitInformationItem;
	}
	
/*updating the code to support hibernate 4 jars
	public void createLimitInfoItems(List limitInfoFeedList) {
		getHibernateTemplate().saveOrUpdateAll(limitInfoFeedList);

	}
	*/
	public void createLimitInfoItems(List limitInfoFeedList) {
		getHibernateTemplate().saveOrUpdate(limitInfoFeedList);

	}
}
