package com.integrosys.cms.batch.bhavcopy;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.cms.batch.IncompleteBatchJobException;

public class BhavcopyDaoImpl extends HibernateDaoSupport implements
		IBhavcopyDao {

	public int insertData(ArrayList data) throws IncompleteBatchJobException {
		int noOfRecInserted = 0;
		if (data == null || data.size() <= 0) {
			throw new IncompleteBatchJobException(
					"Data to be instered to DB is null or empty");
		}
		try {

			for (int index = 0; index < data.size(); index++) {
				HashMap eachDataMap = (HashMap) data.get(index);
				OBBhavcopy bhavcopy = new OBBhavcopy();
				bhavcopy.setScCode(Integer.parseInt((String) eachDataMap
						.get("SC_CODE")));
				bhavcopy.setScName((String) eachDataMap.get("SC_NAME"));
				bhavcopy.setScGroup((String) eachDataMap.get("SC_GROUP"));
				bhavcopy.setOpenValue(Double.parseDouble((String) eachDataMap
						.get("OPEN")));
				bhavcopy.setCloseValue(Double.parseDouble((String) eachDataMap
						.get("CLOSE")));
				bhavcopy.setLastValue(Double.parseDouble((String) eachDataMap
						.get("LAST")));

				getHibernateTemplate().saveOrUpdate(bhavcopy);
				noOfRecInserted++;
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new IncompleteBatchJobException(
					"Unable to update/insert dad retrived form CSV file");

		}
		return noOfRecInserted;
	}

}
