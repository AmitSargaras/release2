package com.integrosys.cms.app.rbicategory.bus;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * Replication utility used for replicating stock to interact with persistent
 *
 * @author Andy Wong
 * @since 16 Sep 2008
 */
public abstract class RbiCategoryReplicationUtils {

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
	public static IRbiCategory replicateRbiCategoryForCreateStagingCopy(IRbiCategory rbiCategory) {
		IRbiCategory replicatedIdx = new OBRbiCategory();
        replicatedIdx.setIndustryNameId(rbiCategory.getIndustryNameId());
		replicatedIdx.setDeprecated(rbiCategory.getDeprecated());
		replicatedIdx.setId(rbiCategory.getId());
		replicatedIdx.setCreateBy(rbiCategory.getCreateBy());
		replicatedIdx.setLastUpdateBy(rbiCategory.getLastUpdateBy());
		replicatedIdx.setLastUpdateDate(rbiCategory.getLastUpdateDate());
		replicatedIdx.setCreationDate(rbiCategory.getCreationDate());
		replicatedIdx.setStatus(rbiCategory.getStatus());
		replicatedIdx.setStageIndustryNameSet(rbiCategory.getStageIndustryNameSet());
		Set replicatedIndNameSet = new HashSet();
		Set stagingIndNameSet = rbiCategory.getStageIndustryNameSet();
		Iterator it = stagingIndNameSet.iterator();
		OBIndustryCodeCategory industryNameStageObj = new OBIndustryCodeCategory();
		while(it.hasNext())
		{
			OBIndustryCodeCategory repIndustryNameStageObj = new OBIndustryCodeCategory();
			industryNameStageObj = (OBIndustryCodeCategory)it.next();
			repIndustryNameStageObj.setRbiCategoryId(industryNameStageObj.getRbiCategoryId());
		    repIndustryNameStageObj.setRbiCodeCategoryId(industryNameStageObj.getRbiCodeCategoryId());
			replicatedIndNameSet.add(repIndustryNameStageObj);
			
		}
		replicatedIdx.setStageIndustryNameSet(replicatedIndNameSet);

		

        return replicatedIdx;
	}
}