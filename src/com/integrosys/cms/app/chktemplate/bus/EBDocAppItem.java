package com.integrosys.cms.app.chktemplate.bus;

import com.integrosys.cms.app.documentlocation.bus.IDocumentAppTypeItem;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Apr 9, 2010
 * Time: 11:32:37 AM
 * To change this template use File | Settings | File Templates.
 */
public interface EBDocAppItem extends EJBObject {
    /**
     * Return an object representation of the item information.
     *
     * @return IItem
     */

    public Long getDocumentId() throws RemoteException;


    public IDocumentAppTypeItem getValue() throws CheckListTemplateException, RemoteException;

    /**
     * Persist a item information
     *
     * @param value is of type IItem
     */
    public void setValue(IDocumentAppTypeItem value) throws RemoteException;
}