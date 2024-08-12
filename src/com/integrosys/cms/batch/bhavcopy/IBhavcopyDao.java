package com.integrosys.cms.batch.bhavcopy;

import java.util.ArrayList;

import com.integrosys.cms.batch.IncompleteBatchJobException;

public interface IBhavcopyDao {
	public int insertData(ArrayList data)throws IncompleteBatchJobException;
}
