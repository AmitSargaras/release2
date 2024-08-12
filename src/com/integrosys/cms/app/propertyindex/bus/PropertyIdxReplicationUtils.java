package com.integrosys.cms.app.propertyindex.bus;

import java.util.Set;
import java.util.Iterator;
import java.util.HashSet;

import com.integrosys.base.techinfra.util.ReplicateUtils;
import com.integrosys.cms.app.feed.bus.stock.IStockFeedGroup;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA.
 * Replication utility used for replicating stock to interact with persistent
 *
 * @author Andy Wong
 * @since 16 Sep 2008
 */
public abstract class PropertyIdxReplicationUtils {

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
	public static IPropertyIdx replicatePropertyIdxForCreateStagingCopy(IPropertyIdx propertyIdx) {
        Set tempItemSet = new HashSet();

        for (Iterator iterator = propertyIdx.getPropertyIdxItemList().iterator(); iterator.hasNext();) {
            OBPropertyIdxItem propertyIdxItem = (OBPropertyIdxItem) iterator.next();

            Set replicatedDistrictSet = (Set) ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(
				propertyIdxItem.getDistrictList(), new String[] { "propertyIdxDistrictCodeId" });
            Set replicatedMukimSet = (Set) ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(
				propertyIdxItem.getMukimList(), new String[] { "propertyIdxMukimCodeId" });
            Set replicatedPropertyTypeSet = (Set) ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(
				propertyIdxItem.getPropertyTypeList(), new String[] { "propertyIdxPropertyTypeId" });

            propertyIdxItem.setPropertyIdxItemId(0);
            propertyIdxItem.setDistrictList(replicatedDistrictSet);
            propertyIdxItem.setMukimList(replicatedMukimSet);
            propertyIdxItem.setPropertyTypeList(replicatedPropertyTypeSet);
            tempItemSet.add(propertyIdxItem);
        }

        Set replicatedSecSubTypesSet = (Set) ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(
                propertyIdx.getPropertyIdxSecSubTypeList(), new String[] { "propertyIdxSecSubTypeId" });

        IPropertyIdx replicatedIdx = (IPropertyIdx) ReplicateUtils.replicateObject(propertyIdx,
				new String[] { "propertyIdxId"});

		replicatedIdx.setPropertyIdxItemList(tempItemSet);
        replicatedIdx.setPropertyIdxSecSubTypeList(replicatedSecSubTypesSet);

        return replicatedIdx;
	}
}