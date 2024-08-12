package com.integrosys.cms.ui.ubsupload.proxy;

import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.holiday.bus.HolidayException;
import com.integrosys.cms.batch.ubs.IUbsErrDetLog;
import com.integrosys.cms.batch.ubs.IUbsErrorLog;

/**
 * This interface defines the list of attributes that will be available to the
 * generation of a diary item
 * 
 * @author $Author: jtan $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2004/06/29 10:03:55 $ Tag: $Name: $
 */
public interface IUbsUploadProxyManager {


	public IUbsErrorLog insertUbsfile(ArrayList resultList,String fileName,String uploadId,IUbsErrDetLog[] obUbsErrDetLog) throws HolidayException,TrxParameterException,TransactionException;


}
