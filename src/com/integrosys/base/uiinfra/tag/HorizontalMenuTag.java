package com.integrosys.base.uiinfra.tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.MessageResourceUtils;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class HorizontalMenuTag extends TagSupport {
	private final static String MENU_LINK = "link";

	private final static String MENU_NAME = "name";

	private final static String MENU_STYLE = "style";

	private final static String MENU_ROLE = "roleid";

	private final static String MENU = "menu";

	private final static String MENU_SEPARATOR = "|";

	protected int roleID = ICMSConstant.INT_INVALID_VALUE;

	public int getRoleID() {
		return roleID;
	}

	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}

	public int doStartTag() throws JspException {
		try {
			String returnValue = "";

			if (this.getRoleID() != ICMSConstant.INT_INVALID_VALUE) {

				String menuListStr = PropertyManager.getValue(MENU + "." + MENU_ROLE + "."
						+ String.valueOf(this.getRoleID()));
				List menuList = splitList(menuListStr);
				Iterator menuItr = menuList.iterator();
				String subMenuStr = "";
				returnValue = "<ul>";
				while (menuItr.hasNext()) {
					String topMenuStr = (String) menuItr.next();
					returnValue += generateMenuLI(topMenuStr);

					subMenuStr += generateSubMenu(topMenuStr);
				}
				returnValue += "</ul></div>" + subMenuStr;
			}

			pageContext.getOut().print(returnValue);
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new JspException("IOException");
		}
		return SKIP_BODY;
	}

	private List splitList(String listStr) {
		List menu = new ArrayList();

		StringTokenizer st = new StringTokenizer(listStr, MENU_SEPARATOR);
		while (st.hasMoreTokens()) {
			menu.add(st.nextToken());
		}

		return menu;
	}

	private String generateSubMenu(String tab) throws JspException {
		String returnStr = "";
		String subMenuStr = PropertyManager.getValue(MENU + "." + MENU_ROLE + "." + String.valueOf(getRoleID()) + "."
				+ tab);
		if (subMenuStr != null) {
			returnStr += "<div id=\"" + tab + "\" class=\"dropmenudiv\">";
			if("manual_feeds".equals(tab))
				returnStr += "<div style=\"width: 204px; height: calc(100vh - 40px);overflow-y: auto;\">";

			List subMenuList = splitList(subMenuStr);
			Iterator subMenuItr = subMenuList.iterator();
			while (subMenuItr.hasNext()) {
				returnStr += generateLink(tab + "." + (String) subMenuItr.next());
			}

			returnStr += "</div>";
			if("manual_feeds".equals(tab))
				returnStr += "</div>";
		}

		return returnStr;
	}

	private String generateMenuLI(String item) throws JspException {
		String returnStr = "";

		returnStr += "<li>";

		returnStr += generateLink(item);

		returnStr += "</li>";

		return returnStr;
	}

	private String generateLink(String item) throws JspException {
		String styleStr = PropertyManager.getValue(MENU + "." + item + "." + MENU_STYLE);
		String linkStr = PropertyManager.getValue(MENU + "." + item + "." + MENU_LINK);
		//String nameStr = PropertyManager.getValue(MENU + "." + item + "." + MENU_NAME);
		
        String bundle = PropertyManager.getValue("integro.message.resource.menu");
		
		String nameStr = MessageResourceUtils.getInstance().getMessage(
				null, MENU + "." + item + "." + MENU_NAME, bundle, pageContext, null);
		
		String returnStr = "<a ";
		if (styleStr != null) {
			returnStr += "style=\"" + styleStr + "\" ";
		}

		if (linkStr == null) {
			returnStr += "href=\"#\" rel=\"" + item + "\">";
		}
		else {
			returnStr += "href=\"" + linkStr + "\">";
		}
		returnStr += nameStr;
		returnStr += "</a>";

		return returnStr;
	}
}