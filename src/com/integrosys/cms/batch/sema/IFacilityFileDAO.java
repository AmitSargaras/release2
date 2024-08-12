package com.integrosys.cms.batch.sema;

import java.util.List;

/**
 * <p>
 * Data Access Object interface to be used by SEMA feeds module.
 * 
 * <p>
 * Use {@link #createFacilityFileItems(List)} for bulk insert of facility fees
 * to gain performance
 * 
 * @author Forbys Wei
 * @author Chong Jun Yong
 * 
 */
public interface IFacilityFileDAO {

	/**
	 * Create facility feed into persistent storage, return back persisted info.
	 * 
	 * @param facilityFile credit card facility feed info
	 * @return facility feed info persisted
	 */
	public IFacilityFile createFacilityFileItem(IFacilityFile facilityFile);

	/**
	 * Create all facility feed in the list supplied
	 * 
	 * @param facilityFileList list of facility feed to be persisted
	 */
	public void createFacilityFileItems(List facilityFileList);

}
