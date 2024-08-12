package com.integrosys.cms.batch.sibs.parameter.obj;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Oct 2, 2008
 * Time: 3:51:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class OBHostLoanProductType extends OBHostProductType implements IHostProductType, Serializable {

    public void defaultSource() {
        // default reference entry code
        setSource("LNPAR2");
    }


}