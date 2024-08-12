package com.integrosys.cms.batch.contentManager.schedular;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.ibm.mm.sdk.common.DKException;
import com.ibm.mm.sdk.server.DKDatastoreICM;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.contentManager.exception.ContentManagerInitializationException;
import com.integrosys.cms.app.contentManager.factory.ContentManagerFactory;
import com.integrosys.cms.app.contentManager.ibmDb2Cm.utilities.ContentManagerHelper;
import com.integrosys.cms.app.contentManager.service.ContentManagerService;
import com.integrosys.cms.app.image.bus.IImageUploadDao;
import com.integrosys.cms.app.image.bus.ImageUploadDaoImpl;
import com.integrosys.cms.app.image.bus.OBImageUploadAdd;
import com.integrosys.cms.app.image.proxy.IImageUploadProxyManager;

public class ContentManagerThread implements Runnable {

	private String userId;
	private String password;
	List imageList = new ArrayList();
	DKDatastoreICM connection;
	IImageUploadProxyManager proxyManager;
	
	public ContentManagerThread(String userId, String password, IImageUploadProxyManager proxyManager) throws Exception {
		this.userId = userId;
		this.password = password;
		this.proxyManager = proxyManager;
		DefaultLogger.debug(this,"ContentManagerThread ------- "+ this.userId);
		DefaultLogger.debug(this,"ContentManagerThread ------- "+ this.password);
		DefaultLogger.debug(this,"ContentManagerThread ------- "+ this.proxyManager);
		if (userId == null || password == null) {
			throw new ContentManagerInitializationException("Error connecting to content manager datasource : No Credentials Configured.");
		}
	}

	public void run() {
		IImageUploadDao dao = new ImageUploadDaoImpl();
		DefaultLogger.debug(this,"Starting Thread with userId ------- "+ userId +"\nNo of Images to Process: ------- "+imageList.size());
		try {
			this.connection = ContentManagerHelper.initializeDatastore(userId, password);
			DefaultLogger.debug(this,"Initializing connection --" + userId+"@"+password+"------"+this.connection);
			ContentManagerService contentManagerService = ContentManagerFactory.getContentManagerService();
			//Session session = new ImageUploadDaoImpl().getHibernateTemplate().getSessionFactory().openSession();
		//	Transaction transaction = session.beginTransaction();
			OBImageUploadAdd aOBImageUploadAdd;
			OBImageUploadAdd aOBImageUploadAddNS;
			Object[] params;
			if (imageList != null) {
				Iterator iter = imageList.iterator();
				while (iter.hasNext()) {
					aOBImageUploadAddNS = (OBImageUploadAdd) iter.next();

					aOBImageUploadAdd = null;
				//	aOBImageUploadAdd = (OBImageUploadAdd)session.load(aOBImageUploadAddNS.getClass(), aOBImageUploadAddNS.getImgId());
					try {
						DefaultLogger.debug(this,"Proxy ------" + proxyManager);
						aOBImageUploadAdd = (OBImageUploadAdd) proxyManager.getTempImageUploadById(aOBImageUploadAddNS.getImgId());
						DefaultLogger.debug(this,"run() == Inserting ---- "+aOBImageUploadAdd.getImageFilePath());
						params = new Object[2];
						params[0] = connection;
						params[1] = aOBImageUploadAdd;
						String pid = (String) contentManagerService.insertDocuments(params);
						aOBImageUploadAdd.setImageFilePath(pid);
						aOBImageUploadAdd.setStatus(3);
						proxyManager.updateTempImageUpload(aOBImageUploadAdd);
						//dao.updateTempImageUpload(aOBImageUploadAdd);
				//		session.update(aOBImageUploadAdd);
					} 
					catch (Exception e) {
						e.printStackTrace();
						if (aOBImageUploadAdd != null) {
							aOBImageUploadAdd.setStatus(4);
							aOBImageUploadAdd.setError(e.getMessage());
							proxyManager.updateTempImageUpload(aOBImageUploadAdd);
						}
					//	dao.updateTempImageUpload(aOBImageUploadAdd);
				//		session.update(aOBImageUploadAdd);
					}
				}
			}
			//transaction.commit();
			//session.close();
		} 
		catch (ContentManagerInitializationException e) {
			e.printStackTrace();
			if (imageList != null) {
				Iterator iter = imageList.iterator();
				OBImageUploadAdd aOBImageUploadAdd;
				while (iter.hasNext()) {
					aOBImageUploadAdd = (OBImageUploadAdd ) iter.next();
					aOBImageUploadAdd.setStatus(1);
					dao.updateTempImageUpload(aOBImageUploadAdd);
				}
			}
		} 
		finally {
			try {
				this.connection.disconnect();
				this.connection.destroy();
			} 
			catch (DKException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void addImageToList(OBImageUploadAdd thread) {
		imageList.add(thread);
	}
}

