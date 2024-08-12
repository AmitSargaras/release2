package com.integrosys.cms.batch.finwarefd;

import java.util.ArrayList;

import com.integrosys.cms.batch.ubs.IUbsErrDetLog;
import com.integrosys.cms.batch.ubs.IUbsErrorLog;

public interface IFinwarefdcopyDao {
	public IUbsErrorLog compareFinwareFdfile(ArrayList result,String fileName,String uploadId,IUbsErrDetLog obUbsErrDetLog[]);
}
