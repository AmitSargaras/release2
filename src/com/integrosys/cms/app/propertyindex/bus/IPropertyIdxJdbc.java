package com.integrosys.cms.app.propertyindex.bus;

import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: USER
 * Date: Sep 11, 2008
 * Time: 7:16:19 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IPropertyIdxJdbc {
    List getAllPropertyIdx ();

    boolean isSecSubTypeValTypeExist (long propertyIndexID, Set secSubTypeList, String valDesc);
}
