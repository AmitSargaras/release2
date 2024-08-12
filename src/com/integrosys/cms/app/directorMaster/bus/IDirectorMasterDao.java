package com.integrosys.cms.app.directorMaster.bus;

import java.io.Serializable;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
/**
 * Description:
 * @author $Author: Venkat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-05-04 15:13:16 +0800 (Tue, 03 May 2011) $
 */
public interface IDirectorMasterDao {

	static final String ACTUAL_DIRECTOR_MASTER_NAME = "actualOBDirectorMaster";
	static final String STAGE_DIRECTOR_MASTER_NAME = "stageOBDirectorMaster";

	IDirectorMaster getDirectorMaster(String entityName, Serializable key)throws DirectorMasterException;
	IDirectorMaster updateDirectorMaster(String entityName, IDirectorMaster item)throws DirectorMasterException;
	IDirectorMaster disableDirectorMaster(String entityName, IDirectorMaster item);
	IDirectorMaster enableDirectorMaster(String entityName, IDirectorMaster item);
	IDirectorMaster saveDirectorMaster(String entityName, IDirectorMaster item);
	IDirectorMaster load(String entityName,long id)throws DirectorMasterException;
	
	IDirectorMaster createDirectorMaster(String entityName, IDirectorMaster directorMaster)
	throws DirectorMasterException;

	public boolean isDinNumberUnique(String dinNumber);
	
	public boolean isDirectorNameUnique(String directorName);
	
	SearchResult getAllDirectorMaster()throws DirectorMasterException;
	SearchResult getAllDirectorMaster (String searchBy,String searchText)throws DirectorMasterException;
}
