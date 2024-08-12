package com.integrosys.cms.app.propertyindex.bus;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Sep 11, 2008
 * Time: 3:11:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyIdxBusManagerStagingImpl extends AbstractPropertyIdxBusManager {

    public String getPropertyIdxEntityName() {
        return IPropertyIdxDao.STAGE_PROPERTY_IDX_ENTITY_NAME;
    }

    public IPropertyIdx updateToWorkingCopy(IPropertyIdx workingCopy, IPropertyIdx imageCopy)
            throws PropertyIdxException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }

}