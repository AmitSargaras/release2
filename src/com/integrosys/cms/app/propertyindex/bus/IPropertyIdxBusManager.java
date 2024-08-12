package com.integrosys.cms.app.propertyindex.bus;

import com.integrosys.cms.app.feed.bus.forex.IForexFeedGroup;
import com.integrosys.cms.app.feed.bus.forex.ForexFeedGroupException;

import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: USER
 * Date: Sep 11, 2008
 * Time: 7:41:05 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IPropertyIdxBusManager {
    IPropertyIdxDao getPropertyIdxDao();

    void setPropertyIdxDao(IPropertyIdxDao propertyIdxDao);

    IPropertyIdxJdbc getPropertyIdxJdbc();

    void setPropertyIdxJdbc(IPropertyIdxJdbc propertyIdxJdbc);

    IPropertyIdx createPropertyIdx(IPropertyIdx propertyIdx);

    IPropertyIdx getPropertyIdx(long key);

    IPropertyIdx updatePropertyIdx(IPropertyIdx propertyIdx);

    List getAllPropertyIdx ();

    IPropertyIdx updateToWorkingCopy(IPropertyIdx workingCopy, IPropertyIdx imageCopy) throws PropertyIdxException;

    boolean isSecSubTypeValTypeExist (long propertyIndexID, Set secSubTypeList, String valDesc);
}
