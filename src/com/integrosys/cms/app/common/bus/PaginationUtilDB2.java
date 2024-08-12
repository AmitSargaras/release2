package com.integrosys.cms.app.common.bus;

public class PaginationUtilDB2 implements PaginationUtil {

	public String formCountQuery(String queryOrg) {
		return "select count(1) from (" + queryOrg + ") as pagingTemp1";
	}

	public String formPagingQuery(String queryOrg, PaginationBean pgBean) {
		String query = queryOrg.toUpperCase();
		int fromInd = getFromIndex(query);
		String orderByPart = getOrderbyPart(query, fromInd);
		StringBuffer buf = new StringBuffer();

		buf.append("select pagingTemp2.* from (select pagingTemp1.*, rownumber() over ( ");
		buf.append(orderByPart);
		buf.append(") as row_next from ( ");
		buf.append(query);
		buf.append(") as pagingTemp1) as pagingTemp2 ");

		if (pgBean != null && pgBean.getStartIndex() > 0) {
			buf.append("where pagingTemp2.row_next between ");
			buf.append(pgBean.getStartIndex());
			buf.append(" and ");
			buf.append(pgBean.getEndIndex());
		}

		return buf.toString();
	}

	private int getFromIndex(String query) {
		int countSel = 1;
		int cursorPos = 6;
		int nextSelInd = -1;
		int nextFromInd = -1;

		do {
			nextSelInd = query.indexOf("SELECT", cursorPos);
			nextFromInd = query.indexOf("FROM", cursorPos);
			if ((nextSelInd >= 0) && (nextFromInd >= 0)) {
				if (nextSelInd > nextFromInd) {
					cursorPos = nextFromInd + 4;
					countSel = countSel - 1;
				}
				else {
					cursorPos = nextSelInd + 6;
					countSel = countSel + 1;
				}
			}
			else if (nextFromInd >= 0) {
				// no more select, this is the matching from
				countSel = countSel - 1;
			}
		} while (countSel > 0);

		return nextFromInd;
	}

	private String getOrderbyPart(String query, int fromInd) {
		int orderByInd = query.lastIndexOf(" ORDER ");

		if ((orderByInd >= 0) && (orderByInd > fromInd)) {
			return query.substring(orderByInd + 1);
		}
		else {
			return "";
		}
	}
}
