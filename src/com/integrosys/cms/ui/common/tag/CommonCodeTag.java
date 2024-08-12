package com.integrosys.cms.ui.common.tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.struts.taglib.TagUtils;

import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.component.commondata.app.CommonDataSearchContext;

public class CommonCodeTag extends CommonOptionsHandlerTag {

	private static final long serialVersionUID = 8278967849256558200L;

	private CommonCodeList commonCodeList;

	private String categoryCode;

	private String entryCode;

	/**
	 * default the usage of common code tag to include to show code with
	 * description
	 **/
	private boolean descWithCode = true;

	private String country;

	private String refEntryCode;

	private String source;

	private boolean onlyInitial;

	private boolean onlyMatchCountry;

	private boolean onlyMatchRefEntry;

	private Character initial;

	public CommonCodeList getCommonCodeList() {
		return commonCodeList;
	}

	public void setCommonCodeList(CommonCodeList commonCodeList) {
		this.commonCodeList = commonCodeList;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getEntryCode() {
		return entryCode;
	}

	public void setEntryCode(String entryCode) {
		this.entryCode = entryCode;
	}

	public boolean isDescWithCode() {
		return descWithCode;
	}

	public void setDescWithCode(boolean descWithCode) {
		this.descWithCode = descWithCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRefEntryCode() {
		return refEntryCode;
	}

	public void setRefEntryCode(String refEntryCode) {
		this.refEntryCode = refEntryCode;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public boolean getOnlyInitial() {
		return onlyInitial;
	}

	public void setOnlyInitial(boolean onlyInitial) {
		this.onlyInitial = onlyInitial;
	}

	public boolean isOnlyMatchCountry() {
		return onlyMatchCountry;
	}

	public void setOnlyMatchCountry(boolean onlyMatchCountry) {
		this.onlyMatchCountry = onlyMatchCountry;
	}

	public boolean isOnlyMatchRefEntry() {
		return onlyMatchRefEntry;
	}

	public void setOnlyMatchRefEntry(boolean onlyMatchRefEntry) {
		this.onlyMatchRefEntry = onlyMatchRefEntry;
	}

	public Character getInitial() {
		return initial;
	}

	public void setInitial(Character initial) {
		this.initial = initial;
	}

	/**
	 * Process the start of this tag.
	 * 
	 * @throws JspException if a JSP exception has occurred
	 */
	public int doStartTag() throws JspException {
		CommonDataSearchContext context = new CommonDataSearchContext(categoryCode, source, country, refEntryCode);
		context.setOnlyMatchingCountry(isOnlyMatchCountry());
		context.setOnlyMatchingParentEntry(isOnlyMatchRefEntry());

		commonCodeList = CommonCodeList.getInstance(context, descWithCode, excludeList);

		if (display) {
			try {
				String code = StringEscapeUtils.escapeHtml(commonCodeList.getCommonCodeLabel(entryCode));
				pageContext.getOut().print((code == null) ? "-" : (code.trim().length() == 0 ? "-" : code));
			}
			catch (IOException e) {
				throw new JspException("failed to print common code [" + entryCode + "]", e);
			}
		}
		else if (onlyInitial) {
			Collection initialList = commonCodeList.getCommonCodeInitials();

			StringBuffer sb = new StringBuffer();
			listOptions(initialList, initialList, sb);
			TagUtils.getInstance().write(pageContext, sb.toString());
		}
		else {
			List valueList = new ArrayList();
			valueList.addAll(commonCodeList.getCommonCodeValues());

			List labelList = new ArrayList();
			labelList.addAll(commonCodeList.getCommonCodeLabels());

			StringBuffer sb = new StringBuffer();

			if (initial != null) {
				Iterator itrValue = valueList.iterator();
				for (Iterator itr = labelList.iterator(); itr.hasNext();) {
					String label = (String) itr.next();
					itrValue.next();
					if (initial.compareTo(new Character(label.charAt(0))) != 0) {
						itr.remove();
						itrValue.remove();
					}
				}
			}

			listOptions(valueList, labelList, sb);

			TagUtils.getInstance().write(pageContext, sb.toString());
		}
		return SKIP_BODY;
	}
}
