package com.integrosys.cms.app.directorMaster.bus;

import com.integrosys.base.techinfra.util.ReplicateUtils;
/**
 * Description:
 * @author $Author: Venkat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-05-04 15:13:16 +0800 (Tue, 04 May 2011) $
 */
public abstract class DirectorMasterReplicationUtils {

	/**
	 * <p>
	 * Replicate Property Index which is ready to create a entity into
	 * persistent storage. Normally to create a staging copy will use this.
	 *
	 * <p>
	 * <b>note</b> must know which field(s) need to be filtered when doing
	 * replication
	 *
	 */
	public static IDirectorMaster replicateDirectorMasterForCreateStagingCopy(IDirectorMaster directorMaster) {

          

        IDirectorMaster replicatedIdx = (IDirectorMaster) ReplicateUtils.replicateObject(directorMaster,
				new String[] { "dinNo"});
         if (replicatedIdx.getDinNo() == null)        	
         replicatedIdx.setDinNo((directorMaster.getDinNo()));
         return replicatedIdx;
	}
}