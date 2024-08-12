package com.integrosys.cms.app.email.notification.bus;

import java.util.List;

import org.hibernate.Query;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;

public class EmailNotificationDaoImpl extends HibernateDaoSupport  implements IEmailNotificationDao {

	public IEmailNotification createEmailNotification(String entityName,
			IEmailNotification emailNotification) {
		if(!(entityName==null|| emailNotification==null)){
			Long key = (Long) getHibernateTemplate().save(entityName, emailNotification);
			emailNotification.setNotifcationId(key.longValue());
			return emailNotification;
			}else{
				throw new EmailNotificationException("ERROR- Entity name or key is null ");
			}
	}

	public IEmailNotification updateEmailNotification(String entityName,
			IEmailNotification emailNotification) {
		if(!(entityName==null|| emailNotification==null)){
			getHibernateTemplate().update(entityName, emailNotification);
			return  (IEmailNotification) getHibernateTemplate().load(entityName, new Long(emailNotification.getNotifcationId()));
		}else{
			throw new EmailNotificationException("ERROR-- Entity Name Or Key is null");
		}
	}


	public List getAllPendingNotificationList() throws EmailNotificationException {
		List list=null;
		try {
			String fromServer=PropertyManager.getValue("integrosys.server.identification");
			DefaultLogger.debug(this, "inside getAllPendingNotificationList ----->" + fromServer);
			System.out.println("inside getAllPendingNotificationList ----->" + fromServer);
			Query query = currentSession().createQuery("from actualEmailNotification as notification where notification.isSent = 'N' and notification.fromServer = '"+fromServer+"' and notification.noticationTypeCode in('NOT00023','NOT00024','NOT00025','NOT00026') ");
			DefaultLogger.debug(this, "-------->" + query);
			System.out.println( "-------->" + query);
			list=query.list();
			System.out.println( "-------->" + list);
		} catch (Exception e) {
			throw new EmailNotificationException("Error while retriving pending Email notification List",e);
		}
		return list;
	}
	

}
