
package com.integrosys.cms.app.directorMaster.bus;

import java.util.List;

/**
 * Description:
 * @author $Author: Venkat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-05-04 15:13:16 +0800 (Tue, 03 May 2011) $
 */
public interface IDirectorMasterJdbc {
	List getAllDirectorMaster (String searchBy,String searchText)throws DirectorMasterException;
	List getAllDirectorMaster()throws DirectorMasterException;
	List getAllDirectorMasterSearch(String login)throws DirectorMasterException;
	IDirectorMaster listDirectorMaster(long branchCode)throws DirectorMasterException;

}
