package com.integrosys.cms.batch.sema;

import java.util.List;

/*updating the code to support hibernate 4 jars
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;*/
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
/**
 * Implementation of {@link IFacilityFileDAO} using hibernate persistence
 * framework
 * 
 * @author Forbys Wei
 * @author Chong Jun Yong
 * 
 */
public class FacilityFileDAO extends HibernateDaoSupport implements IFacilityFileDAO {

	public IFacilityFile createFacilityFileItem(IFacilityFile facilityFile) {
		Long key = (Long) getHibernateTemplate().save(facilityFile);
		facilityFile.setTempID(key.longValue());
		return facilityFile;
	}

	/*updating the code to support hibernate 4 jars
	public void createFacilityFileItems(List facilityFileList) {
		getHibernateTemplate().saveOrUpdateAll(facilityFileList);
	}*/
	public void createFacilityFileItems(List facilityFileList) {
		getHibernateTemplate().saveOrUpdate(facilityFileList);
	}
}
