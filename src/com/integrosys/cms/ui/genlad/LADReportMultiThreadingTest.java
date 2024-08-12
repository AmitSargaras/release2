package com.integrosys.cms.ui.genlad;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class LADReportMultiThreadingTest extends JdbcDaoSupport  {

	public Map getData() {

		String query = "SELECT D.Limit_Id,d.customer_id FROM CMS_LAD_FILTER a LEFT JOIN CMS_LAD_DETAIL d ON a.id=d.filter_ID WHERE d.status  ='PENDING' AND a.rm_id     ='19' AND a.party_id  ='ALL' AND A.Due_Month ='10' AND A.Due_Year  ='2016' AND a.segment_id='12'";
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		jdbcTemplate.setFetchSize(100);

		return jdbcTemplate.queryForMap(query);

	}


}
