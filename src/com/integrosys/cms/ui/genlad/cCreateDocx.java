/**
 * Main class.
 * @author 2mdc
 */
package com.integrosys.cms.ui.genlad;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.activation.MimetypesFileTypeMap;
import javax.swing.ImageIcon;

import com.integrosys.base.techinfra.logger.DefaultLogger;

import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.poi.report.xml.schema.IReportConstants;

public class cCreateDocx {

  private static final String NAMESPACE = "w";
  private static final String SCHEMA_IMAGEDOCUMENT = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/image";
  private static final String SCHEMA_OFFICEDOCUMENT = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument";
  private HashMap arrIdRels;
  private HashMap arrIdWords;
  private Object objDebug;
  private int intIdImgHeader;
  private int intIdRels;
  private int intIdWord;
  private ZipOutputStream objZipDocx;
  private boolean boolMarco;
  private String xml_ContentType__Content;
  private String xml_ContentType__Template;
  private String xml_Rels_Rels__Content;
  private String xml_Rels_Rels__Template;
  private String xml_DocProps_App__Content;
  private String xml_DocProps_App__Template;
  private String xml_DocProps_Core__Content;
  private String xml_DocProps_Core__Template;
  private String xml_Word_Document__Template;
  private String xml_Word_Document__Content;
  private String xml_Word_Endnotes__Content;
  private String xml_Word_Endnotes__Template;
  private String xml_Word_FontTable__Template;
  private String xml_Word_FontTable__Content;
  private String xml_Word_Footer__Content;
  private String xml_Word_Footer__Template;
  private String xml_Word_Footnotes__Content;
  private String xml_Word_Footnotes__Template;
  private String xml_Word_Header__Content;
  private String xml_Word_Header__Template;
  private String xml_Word_Numbering__Content;
  private String xml_Word_Numbering__Template;
  private String xml_Word_Rels_DocumentRels__Content;
  private String xml_Word_Rels_DocumentRels__Template;
  private String xml_Word_Rels_HeaderRels__Content;
  private String xml_Word_Rels_HeaderRels__Template;
  private String xml_Word_Rels_FooterRels__Content;
  private String xml_Word_Rels_FooterRels__Template;
  private String xml_Word_Settings__Content;
  private String xml_Word_Settings__Template;
  private String xml_Word_Styles__Content;
  private String xml_Word_Styles__Template;
  private String xml_Word_Theme_Theme1__Template;
  private String xml_Word_Theme_Theme1__Content;
  private String xml_Word_WebSettings__Template;
  private String xml_Word_WebSettings__Content;
  private String extension;
  private String defaultFont;

  public cCreateDocx(String title,String extension,String basePath) {

    this.arrIdWords = new HashMap();
    this.intIdImgHeader = 1;
    this.intIdRels = 1;
    this.intIdWord = 0;
    if ("docm".equals(extension)) {
      this.extension = "docm";
    } else {
      this.extension = "docx";
    }
    try {
    	//String filePath=PropertyManager.getValue("contextPath");
		//String pdfFilePath=basePath+"/dmsImages/"+pID+".pdf";
    	//String filePath = PropertyManager.getValue(IReportConstants.BASE_PATH);
    	System.out.println("basePath :"+basePath);
    	DefaultLogger.debug(this,"There is a basePath  to generating a zip archive: " + basePath);
    	if(title==null)
    		this.objZipDocx = new ZipOutputStream(new FileOutputStream(basePath+"/result2." + this.extension));
    	else
    		this.objZipDocx = new ZipOutputStream(new FileOutputStream(basePath+"/"+title+"."+ this.extension));		
    } catch (java.io.FileNotFoundException e) {
    	DefaultLogger.debug(this,"There is a problem generating a zip archive: " + e.getMessage());
    }
    this.xml_ContentType__Content = "";
    this.xml_ContentType__Template = "";
    this.xml_Rels_Rels__Content = "";
    this.xml_Rels_Rels__Template = "";
    this.xml_DocProps_App__Content = "";
    this.xml_DocProps_App__Template = "";
    this.xml_DocProps_Core__Content = "";
    this.xml_DocProps_Core__Template = "";
    this.xml_Word_Document__Template = "";
    this.xml_Word_Document__Content = "";
    this.xml_Word_Endnotes__Content = "";
    this.xml_Word_Endnotes__Template = "";
    this.xml_Word_FontTable__Template = "";
    this.xml_Word_FontTable__Content = "";
    this.xml_Word_Footer__Content = "";
    this.xml_Word_Footer__Template = "";
    this.xml_Word_Footnotes__Content = "";
    this.xml_Word_Footnotes__Template = "";
    this.xml_Word_Header__Content = "";
    this.xml_Word_Header__Template = "";
    this.xml_Word_Numbering__Content = "";
    this.xml_Word_Numbering__Template = "";
    this.xml_Word_Rels_DocumentRels__Content = "";
    this.xml_Word_Rels_DocumentRels__Template = "";
    this.xml_Word_Rels_HeaderRels__Content = "";
    this.xml_Word_Rels_HeaderRels__Template = "";
    this.xml_Word_Rels_FooterRels__Content = "";
    this.xml_Word_Rels_FooterRels__Template = "";
    this.xml_Word_Settings__Content = "";
    this.xml_Word_Settings__Template = "";
    this.xml_Word_Styles__Content = "";
    this.xml_Word_Styles__Template = "";

    this.xml_Word_Theme_Theme1__Template = "";
    this.xml_Word_Theme_Theme1__Content = "";
    this.boolMarco = false;
    this.xml_Word_WebSettings__Template = "";
    this.xml_Word_WebSettings__Content = "";
    this.fGenerateContentType();
	this.defaultFont = "";
  }

  public void setXml_ContentTypes(String xml_ContentTypes) {
    this.xml_ContentType__Content += xml_ContentTypes;
  }

  public String getXml_ContentTypes() {
    return this.xml_ContentType__Content;
  }

  public void setXml_Rels_Rels(String xml_Rels_Rels) {
    this.xml_Rels_Rels__Content += xml_Rels_Rels;
  }

  public String getXml_Rels_Rels() {
    return this.xml_Rels_Rels__Content;
  }

  public void setXml_DocProps_App(String xml_DocProps_App) {
    this.xml_DocProps_App__Content += xml_DocProps_App;
  }

  public String getXml_DocProps_App() {
    return this.xml_DocProps_App__Content;
  }

  public void setXml_DocProps_Core(String xml_DocProps_Core) {
    this.xml_DocProps_Core__Content += xml_DocProps_Core;
  }

  public String getXml_DocProps_Core() {
    return this.xml_DocProps_Core__Content;
  }

  public void setXml_Word_Document(String xml_Word_Document) {
    this.xml_Word_Document__Content += xml_Word_Document;
  }

  public String getXml_Word_Document() {
    return this.xml_Word_Document__Content;
  }

  public void setXml_Word_Endnotes(String xml_Word_Endnotes) {
    this.xml_Word_Endnotes__Content += xml_Word_Endnotes;
  }

  public String getXml_Word_Endnotes() {
    return this.xml_Word_Endnotes__Content;
  }

  public void setXml_Word_FontTable(String xml_Word_FontTable) {
    this.xml_Word_FontTable__Content += xml_Word_FontTable;
  }

  public String getXml_Word_FontTable() {
    return this.xml_Word_FontTable__Content;
  }

  public void setXml_Word_Footer(String xml_Word_Footer) {
    this.xml_Word_Footer__Content += xml_Word_Footer;
  }

  public String getXml_Word_Footer() {
    return this.xml_Word_Footer__Content;
  }

  public void setXml_Word_Header(String xml_Word_Header) {
    this.xml_Word_Header__Content += xml_Word_Header;
  }

  public String getXml_Word_Header() {
    return this.xml_Word_Header__Content;
  }

  public void setXml_Word_Rels_DocumentRels(String xml_Word_Rels_DocumentRels) {
    this.xml_Word_Rels_DocumentRels__Content += xml_Word_Rels_DocumentRels;
  }

  public String getXml_Word_Rels_DocumentRels() {
    return this.xml_Word_Rels_DocumentRels__Content;
  }

  public void setXml_Word_Settings(String xml_Word_Settings) {
    this.xml_Word_Settings__Content += xml_Word_Settings;
  }

  public String getXml_Word_Settings() {
    return this.xml_Word_Settings__Content;
  }

  public void setXml_Word_Styles(String xml_Word_Styles) {
    this.xml_Word_Styles__Content += xml_Word_Styles;
  }

  public String getXml_Word_Styles() {
    return this.xml_Word_Styles__Content;
  }

  public void setXml_Word_Theme_Theme1(String xml_Word_Theme_Theme1) {
    this.xml_Word_Theme_Theme1__Content += xml_Word_Theme_Theme1;
  }

  public String getXml_Word_Theme_Theme1() {
    return this.xml_Word_Theme_Theme1__Content;
  }

  public void setXml_Word_WebSettings(String xml_Word_WebSettings) {
    this.xml_Word_WebSettings__Content += xml_Word_WebSettings;
  }

  public String getXml_Word_WebSettings() {
    return this.xml_Word_WebSettings__Content;
  }

  protected void fCleanTemplate() {
    this.xml_Word_Document__Template = this.xml_Word_Document__Template.replaceAll("__[A-Z]+__", "");
  }

  protected String fGenerateRELATIONSHIP(String strId, String strType, String strTraget) {
    if (strType.equals("vbaProject")) {
      strType = "http://schemas.microsoft.com/office/2006/relationships/vbaProject";
    } else {
      strType = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/" + strType;
    }
    return "<Relationship Id='" + strId + "' Type='" + strType + "' Target='" + strTraget + "'></Relationship>";
  }

  protected void fGenerateSECTPR(HashMap arrArgs) {
    cCreatePage objPage = cCreatePage.getInstance();
    objPage.fCreateSECTPR(arrArgs);
    this.xml_Word_Document__Content += objPage.toString();
  }

  public void fGenerateContentType() {
    this.fGenerateDEFAULT("rels", "application/vnd.openxmlformats-package.relationships+xml");
    this.fGenerateDEFAULT("xml", "application/xml");
    this.fGenerateOVERRIDE("/word/numbering.xml", "application/vnd.openxmlformats-officedocument.wordprocessingml.numbering+xml");
    this.fGenerateOVERRIDE("/word/styles.xml", "application/vnd.openxmlformats-officedocument.wordprocessingml.styles+xml");
    this.fGenerateOVERRIDE("/docProps/app.xml", "application/vnd.openxmlformats-officedocument.extended-properties+xml");
    this.fGenerateOVERRIDE("/word/settings.xml", "application/vnd.openxmlformats-officedocument.wordprocessingml.settings+xml");
    this.fGenerateOVERRIDE("/word/theme/theme1.xml", "application/vnd.openxmlformats-officedocument.theme+xml");
    this.fGenerateOVERRIDE("/word/fontTable.xml", "application/vnd.openxmlformats-officedocument.wordprocessingml.fontTable+xml");
    this.fGenerateOVERRIDE("/word/webSettings.xml", "application/vnd.openxmlformats-officedocument.wordprocessingml.webSettings+xml");
    if (this.xml_Word_Footer__Content != "" || this.xml_Word_Header__Content != "") {
      this.fGenerateOVERRIDE("/word/header.xml", "application/vnd.openxmlformats-officedocument.wordprocessingml.header+xml");
      this.fGenerateOVERRIDE("/word/footer.xml", "application/vnd.openxmlformats-officedocument.wordprocessingml.footer+xml");
      this.fGenerateOVERRIDE("/word/footnotes.xml", "application/vnd.openxmlformats-officedocument.wordprocessingml.footnotes+xml");
      this.fGenerateOVERRIDE("/word/endnotes.xml", "application/vnd.openxmlformats-officedocument.wordprocessingml.endnotes+xml");
    }
    this.fGenerateOVERRIDE("/docProps/core.xml", "application/vnd.openxmlformats-package.core-properties+xml");
  }

  private void fGenerateTemplate_DocProps_App() {
    this.xml_DocProps_App__Template = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><Properties xmlns=\"http://schemas.openxmlformats.org/officeDocument/2006/extended-properties\" xmlns:vt=\"http://schemas.openxmlformats.org/officeDocument/2006/docPropsVTypes\"><Template>Normal.dotm</Template><TotalTime>0</TotalTime><Pages>1</Pages><Words>1</Words><Characters>1</Characters><Application>Microsoft Office Word</Application><DocSecurity>4</DocSecurity><Lines>1</Lines><Paragraphs>1</Paragraphs><ScaleCrop>false</ScaleCrop><Company>Company</Company><LinksUpToDate>false</LinksUpToDate><CharactersWithSpaces>1</CharactersWithSpaces><SharedDoc>false</SharedDoc><HyperlinksChanged>false</HyperlinksChanged><AppVersion>12.0000</AppVersion></Properties>";
  }

  private void fGenerateTemplate_DocProps_Core() {
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
    String fecha = sdf.format(cal.getTime());
    fecha = fecha.replace(" ", "T");
    fecha = fecha.substring(0, fecha.length() - 2) + ":" + fecha.substring(fecha.length() - 2, fecha.length());
    this.xml_DocProps_Core__Template = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><cp:coreProperties xmlns:cp=\"http://schemas.openxmlformats.org/package/2006/metadata/core-properties\" xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xmlns:dcterms=\"http://purl.org/dc/terms/\" xmlns:dcmitype=\"http://purl.org/dc/dcmitype/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><dc:title>Title</dc:title><dc:subject>Subject</dc:subject><dc:creator>2mdc</dc:creator><dc:description>Description</dc:description><cp:lastModifiedBy>user</cp:lastModifiedBy><cp:revision>1</cp:revision><dcterms:created xsi:type=\"dcterms:W3CDTF\">" + fecha + "</dcterms:created><dcterms:modified xsi:type=\"dcterms:W3CDTF\">" + fecha + "</dcterms:modified></cp:coreProperties>";
  }

  private void fGenerateTemplate_Rels_Rels() {
    this.xml_Rels_Rels__Template = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><Relationships xmlns=\"http://schemas.openxmlformats.org/package/2006/relationships\">" + this.fGenerateRELATIONSHIP("rId3", "extended-properties", "docProps/app.xml") + "<Relationship Id=\"rId2\" Type=\"http://schemas.openxmlformats.org/package/2006/relationships/metadata/core-properties\" Target=\"docProps/core.xml\"/>" + this.fGenerateRELATIONSHIP("rId1", "officeDocument", "word/document.xml") + "</Relationships>";
  }

  private void fGenerateOVERRIDE(String PartName, String ContentType) {
    if (this.xml_ContentType__Content.indexOf("PartName='" + PartName + "'") == -1) {
      this.xml_ContentType__Content += "<Override PartName='" + PartName + "' ContentType='" + ContentType + "'> </Override>";
    }
  }

  private void fGenerateDEFAULT(String Extension, String ContentType) {
    if (this.xml_ContentType__Content.indexOf("Extension='" + Extension) == -1) {
      this.xml_ContentType__Content += "<Default Extension='" + Extension + "' ContentType='" + ContentType + "'> </Default>";
    }
  }

  private void fGenerateTemplate_Word_Document(HashMap arrArgs) {
    this.fGenerateSECTPR(arrArgs);
    if (!this.xml_Word_Header__Content.equals("")) {
      this.xml_Word_Document__Content = this.xml_Word_Document__Content.replace("__GENERATEHEADERREFERENCE__", "<" + cCreateDocx.NAMESPACE + ":headerReference " + cCreateDocx.NAMESPACE + ":type=\"default\" r:id=\"rId" + this.arrIdWords.get("header").toString() + "\"></" + cCreateDocx.NAMESPACE + ":headerReference>");
    }
    if (!this.xml_Word_Footer__Content.equals("")) {
      this.xml_Word_Document__Content = this.xml_Word_Document__Content.replace("__GENERATEFOOTERREFERENCE__", "<" + cCreateDocx.NAMESPACE + ":footerReference " + cCreateDocx.NAMESPACE + ":type=\"default\" r:id=\"rId" + this.arrIdWords.get("footer").toString() + "\"></" + cCreateDocx.NAMESPACE + ":footerReference>");
    }
    this.xml_Word_Document__Template = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
            + "<" + cCreateDocx.NAMESPACE + ":wordDocument xmlns:ve=\"http://schemas.openxmlformats.org/markup-compatibility/2006\" xmlns:o=\"urn:schemas-microsoft-com:office:office\""
            + " xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" xmlns:m=\"http://schemas.openxmlformats.org/officeDocument/2006/math\" xmlns:v=\"urn:schemas-microsoft-com:vml\""
            + " xmlns:wp=\"http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing\" xmlns:w10=\"urn:schemas-microsoft-com:office:word\""
            + " xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" xmlns:wne=\"http://schemas.microsoft.com/office/word/2006/wordml\">"
            + "<" + cCreateDocx.NAMESPACE + ":body>"
            + this.xml_Word_Document__Content
            + "</" + cCreateDocx.NAMESPACE + ":body>"
            + "</" + cCreateDocx.NAMESPACE + ":wordDocument>";
    this.fCleanTemplate();
  }

  private void fGenerateTemplate_Word_Endnotes() {
    this.xml_Word_Endnotes__Template = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
            + "<" + cCreateDocx.NAMESPACE + ":endnotes xmlns:ve=\"http://schemas.openxmlformats.org/markup-compatibility/2006\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" xmlns:m=\"http://schemas.openxmlformats.org/officeDocument/2006/math\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:wp=\"http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing\" xmlns:w10=\"urn:schemas-microsoft-com:office:word\" xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" xmlns:wne=\"http://schemas.microsoft.com/office/word/2006/wordml\">"
            + this.xml_Word_Endnotes__Content
            + "</" + cCreateDocx.NAMESPACE + ":endnotes>";
    this.intIdWord++;
    this.xml_Word_Rels_DocumentRels__Content += this.fGenerateRELATIONSHIP("rId" + this.intIdWord, "endnotes", "endnotes.xml");
    this.fGenerateOVERRIDE("/word/endnotes.xml", "application/vnd.openxmlformats-officedocument.wordprocessingml.endnotes+xml");
  }

  private void fGenerateTemplate_Word_FontTable() {
    this.xml_Word_FontTable__Template = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>"
            + "<" + cCreateDocx.NAMESPACE + ":fonts xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\">"
            + this.xml_Word_FontTable__Content
            + "</" + cCreateDocx.NAMESPACE + ":fonts>";
  }

  private void fGenerateTemplate_Word_Footer() {
    this.intIdWord++;
    this.arrIdWords.put("footer", String.valueOf(this.intIdWord));
    this.xml_Word_Footer__Template = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><" + cCreateDocx.NAMESPACE + ":ftr xmlns:ve=\"http://schemas.openxmlformats.org/markup-compatibility/2006\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" xmlns:m=\"http://schemas.openxmlformats+org/officeDocument/2006/math\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:wp=\"http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing\" xmlns:w10=\"urn:schemas-microsoft-com:office:word\" xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" xmlns:wne=\"http://schemas.microsoft.com/office/word/2006/wordml\">"
            + this.xml_Word_Footer__Content
            + "</" + cCreateDocx.NAMESPACE + ":ftr>";
    this.xml_Word_Rels_DocumentRels__Content += this.fGenerateRELATIONSHIP("rId" + this.intIdWord, "footer", "footer.xml");
  }

  private void fGenerateTemplate_Word_Footnotes() {
    this.xml_Word_Footnotes__Template = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
            + "<" + cCreateDocx.NAMESPACE + ":footnotes xmlns:ve=\"http://schemas.openxmlformats.org/markup-compatibility/2006\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" xmlns:m=\"http://schemas.openxmlformats.org/officeDocument/2006/math\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:wp=\"http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing\" xmlns:w10=\"urn:schemas-microsoft-com:office:word\" xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" xmlns:wne=\"http://schemas.microsoft.com/office/word/2006/wordml\">"
            + this.xml_Word_Footnotes__Content
            + "</" + cCreateDocx.NAMESPACE + ":footnotes>";
    this.intIdWord++;
    this.xml_Word_Rels_DocumentRels__Content += this.fGenerateRELATIONSHIP("rId" + this.intIdWord, "footnotes", "footnotes.xml");
    this.fGenerateOVERRIDE("/word/footnotes.xml", "application/vnd.openxmlformats-officedocument.wordprocessingml.footnotes+xml");
  }

  private void fGenerateTemplate_Word_Header() {
    this.intIdWord++;
    this.arrIdWords.put("header", String.valueOf(this.intIdWord));
    this.xml_Word_Header__Template = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
            + "<" + cCreateDocx.NAMESPACE + ":hdr xmlns:ve=\"http://schemas.openxmlformats.org/markup-compatibility/2006\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" xmlns:m=\"http://schemas.openxmlformats.org/officeDocument/2006/math\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:wp=\"http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing\" xmlns:w10=\"urn:schemas-microsoft-com:office:word\" xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" xmlns:wne=\"http://schemas.microsoft.com/office/word/2006/wordml\"> "
            + this.xml_Word_Header__Content
            + "</" + cCreateDocx.NAMESPACE + ":hdr>";
    this.xml_Word_Rels_DocumentRels__Content += this.fGenerateRELATIONSHIP("rId" + this.intIdWord, "header", "header.xml");
  }

  private void fGenerateTemplate_Word_Numbering() {
    this.xml_Word_Numbering__Template = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?> "
            + "<w:numbering xmlns:ve=\"http://schemas.openxmlformats.org/markup-compatibility/2006\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" xmlns:m=\"http://schemas.openxmlformats.org/officeDocument/2006/math\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:wp=\"http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing\" xmlns:w10=\"urn:schemas-microsoft-com:office:word\" xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" xmlns:wne=\"http://schemas.microsoft.com/office/word/2006/wordml\"><w:abstractNum w:abstractNumId=\"0\"><w:nsid w:val=\"713727AE\"/><w:multiLevelType w:val=\"hybridMultilevel\"/><w:tmpl w:val=\"F0B4B6B8\"/><w:lvl w:ilvl=\"0\" w:tplc=\"0C0A0001\"><w:start w:val=\"1\"/><w:numFmt w:val=\"bullet\"/><w:lvlText w:val=\"\"/><w:lvlJc w:val=\"left\"/><w:pPr><w:ind w:left=\"720\" w:hanging=\"360\"/></w:pPr><w:rPr><w:rFonts w:ascii=\"Symbol\" w:hAnsi=\"Symbol\" w:hint=\"default\"/></w:rPr></w:lvl><w:lvl w:ilvl=\"1\" w:tplc=\"0C0A0003\" w:tentative=\"1\"><w:start w:val=\"1\"/><w:numFmt w:val=\"bullet\"/><w:lvlText w:val=\"o\"/><w:lvlJc w:val=\"left\"/><w:pPr><w:ind w:left=\"1440\" w:hanging=\"360\"/></w:pPr><w:rPr><w:rFonts w:ascii=\"Courier New\" w:hAnsi=\"Courier New\" w:cs=\"Courier New\" w:hint=\"default\"/></w:rPr></w:lvl><w:lvl w:ilvl=\"2\" w:tplc=\"0C0A0005\" w:tentative=\"1\"><w:start w:val=\"1\"/><w:numFmt w:val=\"bullet\"/><w:lvlText w:val=\"\"/><w:lvlJc w:val=\"left\"/><w:pPr><w:ind w:left=\"2160\" w:hanging=\"360\"/></w:pPr><w:rPr><w:rFonts w:ascii=\"Wingdings\" w:hAnsi=\"Wingdings\" w:hint=\"default\"/></w:rPr></w:lvl><w:lvl w:ilvl=\"3\" w:tplc=\"0C0A0001\" w:tentative=\"1\"><w:start w:val=\"1\"/><w:numFmt w:val=\"bullet\"/><w:lvlText w:val=\"\"/><w:lvlJc w:val=\"left\"/><w:pPr><w:ind w:left=\"2880\" w:hanging=\"360\"/></w:pPr><w:rPr><w:rFonts w:ascii=\"Symbol\" w:hAnsi=\"Symbol\" w:hint=\"default\"/></w:rPr></w:lvl><w:lvl w:ilvl=\"4\" w:tplc=\"0C0A0003\" w:tentative=\"1\"><w:start w:val=\"1\"/><w:numFmt w:val=\"bullet\"/><w:lvlText w:val=\"o\"/><w:lvlJc w:val=\"left\"/><w:pPr><w:ind w:left=\"3600\" w:hanging=\"360\"/></w:pPr><w:rPr><w:rFonts w:ascii=\"Courier New\" w:hAnsi=\"Courier New\" w:cs=\"Courier New\" w:hint=\"default\"/></w:rPr></w:lvl><w:lvl w:ilvl=\"5\" w:tplc=\"0C0A0005\" w:tentative=\"1\"><w:start w:val=\"1\"/><w:numFmt w:val=\"bullet\"/><w:lvlText w:val=\"\"/><w:lvlJc w:val=\"left\"/><w:pPr><w:ind w:left=\"4320\" w:hanging=\"360\"/></w:pPr><w:rPr><w:rFonts w:ascii=\"Wingdings\" w:hAnsi=\"Wingdings\" w:hint=\"default\"/></w:rPr></w:lvl><w:lvl w:ilvl=\"6\" w:tplc=\"0C0A0001\" w:tentative=\"1\"><w:start w:val=\"1\"/><w:numFmt w:val=\"bullet\"/><w:lvlText w:val=\"\"/><w:lvlJc w:val=\"left\"/><w:pPr><w:ind w:left=\"5040\" w:hanging=\"360\"/></w:pPr><w:rPr><w:rFonts w:ascii=\"Symbol\" w:hAnsi=\"Symbol\" w:hint=\"default\"/></w:rPr></w:lvl><w:lvl w:ilvl=\"7\" w:tplc=\"0C0A0003\" w:tentative=\"1\"><w:start w:val=\"1\"/><w:numFmt w:val=\"bullet\"/><w:lvlText w:val=\"o\"/><w:lvlJc w:val=\"left\"/><w:pPr><w:ind w:left=\"5760\" w:hanging=\"360\"/></w:pPr><w:rPr><w:rFonts w:ascii=\"Courier New\" w:hAnsi=\"Courier New\" w:cs=\"Courier New\" w:hint=\"default\"/></w:rPr></w:lvl><w:lvl w:ilvl=\"8\" w:tplc=\"0C0A0005\" w:tentative=\"1\"><w:start w:val=\"1\"/><w:numFmt w:val=\"bullet\"/><w:lvlText w:val=\"\"/><w:lvlJc w:val=\"left\"/><w:pPr><w:ind w:left=\"6480\" w:hanging=\"360\"/></w:pPr><w:rPr><w:rFonts w:ascii=\"Wingdings\" w:hAnsi=\"Wingdings\" w:hint=\"default\"/></w:rPr></w:lvl></w:abstractNum><w:num w:numId=\"1\"><w:abstractNumId w:val=\"0\"/></w:num></w:numbering>";
  }

  private void fGenerateTemplate_Word_Rels_DocumentRels() {
    this.xml_Word_Rels_DocumentRels__Template = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><Relationships xmlns=\"http://schemas.openxmlformats.org/package/2006/relationships\">"
            + this.xml_Word_Rels_DocumentRels__Content
            + "</Relationships>";
  }

  private void fGenerateTemplate_Word_Settings() {
    this.xml_Word_Settings__Template = this.xml_Word_Settings__Content;
  }

  private void fGenerateTemplate_Word_Styles() {
    this.xml_Word_Styles__Template =
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><" + cCreateDocx.NAMESPACE + ":styles xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\">"
            + this.xml_Word_Styles__Content
            + "</" + cCreateDocx.NAMESPACE + ":styles>";
  }

  private void fGenerateTemplate_Word_Theme_Theme1() {
    this.fAddTheme(this.defaultFont);
    this.xml_Word_Theme_Theme1__Template = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?><" + cCreateTheme1.NAMESPACE + ":theme xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\" name=\"Tema de Office\">"
            + this.xml_Word_Theme_Theme1__Content
            + "</" + cCreateTheme1.NAMESPACE + ":theme>";
  }

  private void fGenerateTemplate_ContentType() {
    this.xml_ContentType__Template = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?><Types xmlns=\"http://schemas.openxmlformats.org/package/2006/content-types\">"
            + this.xml_ContentType__Content
            + "</Types>";
  }

  private void fGenerateTemplate_Word_WebSettings() {
    this.xml_Word_WebSettings__Template = this.xml_Word_WebSettings__Content;
  }

  private void fGenerateDefaultFonts() {
    HashMap arrFont = new HashMap();
    arrFont.put("name", "Calibri");
    arrFont.put("pitch", "variable");
    arrFont.put("usb0", "A00002EF");
    arrFont.put("usb1", "4000207B");
    arrFont.put("usb2", "00000000");
    arrFont.put("usb3", "00000000");
    arrFont.put("csb0", "0000009F");
    arrFont.put("csb1", "00000000");
    arrFont.put("family", "swiss");
    arrFont.put("charset", "00");
    arrFont.put("panose1", "020F0502020204030204");
    this.fAddFont(arrFont);
    arrFont = new HashMap();
    arrFont.put("name", "Times New Roman");
    arrFont.put("pitch", "variable");
    arrFont.put("usb0", "E0002AEF");
    arrFont.put("usb1", "C0007841");
    arrFont.put("usb2", "00000009");
    arrFont.put("usb3", "00000000");
    arrFont.put("csb0", "000001FF");
    arrFont.put("csb1", "00000000");
    arrFont.put("family", "roman");
    arrFont.put("charset", "00");
    arrFont.put("panose1", "02020603050405020304");
    this.fAddFont(arrFont);
    arrFont = new HashMap();
    arrFont.put("name", "Cambria");
    arrFont.put("pitch", "variable");
    arrFont.put("usb0", "A00002EF");
    arrFont.put("usb1", "4000004B");
    arrFont.put("usb2", "00000000");
    arrFont.put("usb3", "00000000");
    arrFont.put("csb0", "0000009F");
    arrFont.put("csb1", "00000000");
    arrFont.put("family", "roman");
    arrFont.put("charset", "00");
    arrFont.put("panose1", "02040503050406030204");
    this.fAddFont(arrFont);
  }

  private void fGenerateDefault_Word_Rels() {
    this.intIdWord++;
    this.xml_Word_Rels_DocumentRels__Content += this.fGenerateRELATIONSHIP("rId" + this.intIdWord, "numbering", "numbering.xml");
    this.intIdWord++;
    this.xml_Word_Rels_DocumentRels__Content += this.fGenerateRELATIONSHIP("rId" + this.intIdWord, "theme", "theme/theme1.xml");
    this.intIdWord++;
    this.xml_Word_Rels_DocumentRels__Content += this.fGenerateRELATIONSHIP("rId" + this.intIdWord, "webSettings", "webSettings.xml");
    this.intIdWord++;
    this.xml_Word_Rels_DocumentRels__Content += this.fGenerateRELATIONSHIP("rId" + this.intIdWord, "fontTable", "fontTable.xml");
    this.intIdWord++;
    this.xml_Word_Rels_DocumentRels__Content += this.fGenerateRELATIONSHIP("rId" + this.intIdWord, "settings", "settings.xml");
    this.intIdWord++;
    this.xml_Word_Rels_DocumentRels__Content += this.fGenerateRELATIONSHIP("rId" + this.intIdWord, "styles", "styles.xml");

  }

  private HashMap parsePath(String strDir) {
    int barra = 0;
    String path = "";
    try {
      if ((barra = strDir.lastIndexOf("/")) != -1) {
        barra += 1;
        path = strDir.substring(0, barra);
      }
      int punto;
      if ((punto = strDir.substring(barra).lastIndexOf(".")) == -1) {
        DefaultLogger.debug(this,"There is a error when get the image included. The image has not extension\n");
      }
      String nombre = strDir.substring(barra, barra + punto);
      String extension = strDir.substring(punto + barra + 1);
      HashMap dev = new HashMap();
      dev.put("path", path);
      dev.put("nombre", nombre);
      dev.put("extension", extension);
      return dev;
    } catch (Exception e) {
      DefaultLogger.debug(this,"There is a exception when parse the path of the image" + e.toString());
      return null;
    }
  }

  public void fCreateDocx(String title, HashMap arrArgsPage) {

    String strFileName = "";
    if (!title.equals("")) {
      strFileName = title;
    } else {
      strFileName = "document";
    }
    try {

      String outFilename = "document." + this.extension;
      this.fGenerateTemplate_Rels_Rels();
      this.objZipDocx.putNextEntry(new ZipEntry("_rels/.rels"));
      byte[] buf = this.xml_Rels_Rels__Template.getBytes();
      this.objZipDocx.write(buf, 0, buf.length);
      this.objZipDocx.closeEntry();
      this.fGenerateTemplate_DocProps_App();
      this.objZipDocx.putNextEntry(new ZipEntry("docProps/app.xml"));
      buf = this.xml_DocProps_App__Template.getBytes();
      this.objZipDocx.write(buf, 0, buf.length);
      this.objZipDocx.closeEntry();
      this.fGenerateTemplate_DocProps_Core();
      this.objZipDocx.putNextEntry(new ZipEntry("docProps/core.xml"));
      buf = this.xml_DocProps_Core__Template.getBytes();
      this.objZipDocx.write(buf, 0, buf.length);
      this.objZipDocx.closeEntry();
      this.fAddStyle();
      this.fGenerateTemplate_Word_Styles();
      this.objZipDocx.putNextEntry(new ZipEntry("word/styles.xml"));
      buf = this.xml_Word_Styles__Template.getBytes();
      this.objZipDocx.write(buf, 0, buf.length);
      this.objZipDocx.closeEntry();
      this.fAddSettings();
      this.fGenerateTemplate_Word_Settings();
      this.objZipDocx.putNextEntry(new ZipEntry("word/settings.xml"));
      buf = this.xml_Word_Settings__Template.getBytes();
      this.objZipDocx.write(buf, 0, buf.length);
      this.objZipDocx.closeEntry();
      this.fAddWebSettings();
      this.fGenerateTemplate_Word_WebSettings();
      this.objZipDocx.putNextEntry(new ZipEntry("word/webSettings.xml"));
      buf = this.xml_Word_WebSettings__Template.getBytes();
      this.objZipDocx.write(buf, 0, buf.length);
      this.objZipDocx.closeEntry();
      if (!this.xml_Word_Footer__Content.equals("")) {
        this.fGenerateTemplate_Word_Footer();
        this.objZipDocx.putNextEntry(new ZipEntry("word/footer.xml"));
        buf = this.xml_Word_Footer__Template.getBytes();
        this.objZipDocx.write(buf, 0, buf.length);
        this.objZipDocx.closeEntry();
      }
      if (!this.xml_Word_Header__Content.equals("")) {
        this.fGenerateTemplate_Word_Header();
        this.objZipDocx.putNextEntry(new ZipEntry("word/header.xml"));
        buf = this.xml_Word_Header__Template.getBytes();
        this.objZipDocx.write(buf, 0, buf.length);
        this.objZipDocx.closeEntry();
      }
      if (this.extension.equals("docm")) {
        this.fGenerateOVERRIDE("/word/document.xml", "application/vnd.ms-word.document.macroEnabled.main+xml");
      } else {
        this.fGenerateOVERRIDE("/word/document.xml", "application/vnd.openxmlformats-officedocument.wordprocessingml.document.main+xml");
      }
      this.fGenerateTemplate_ContentType();
      this.objZipDocx.putNextEntry(new ZipEntry("[Content_Types].xml"));
      buf = this.xml_ContentType__Template.getBytes();
      this.objZipDocx.write(buf, 0, buf.length);
      this.objZipDocx.closeEntry();
      this.fGenerateTemplate_Word_Numbering();
      this.objZipDocx.putNextEntry(new ZipEntry("word/numbering.xml"));
      buf = this.xml_Word_Numbering__Template.getBytes();
      this.objZipDocx.write(buf, 0, buf.length);
      this.objZipDocx.closeEntry();
      this.fGenerateDefault_Word_Rels();
      if (!this.xml_Word_Rels_DocumentRels__Content.equals("")) {
        this.fGenerateTemplate_Word_Rels_DocumentRels();
        this.objZipDocx.putNextEntry(new ZipEntry("word/_rels/document.xml.rels"));
        buf = this.xml_Word_Rels_DocumentRels__Template.getBytes();
        this.objZipDocx.write(buf, 0, buf.length);
        this.objZipDocx.closeEntry();
      }
      this.fGenerateTemplate_Word_Document(arrArgsPage);      
      this.objZipDocx.putNextEntry(new ZipEntry("word/document.xml"));
      buf = this.xml_Word_Document__Template.getBytes();
      this.objZipDocx.write(buf, 0, buf.length);
      this.objZipDocx.closeEntry();

      this.fGenerateDefaultFonts();
      this.fGenerateTemplate_Word_FontTable();
      this.objZipDocx.putNextEntry(new ZipEntry("word/fontTable.xml"));
      buf = this.xml_Word_FontTable__Template.getBytes();
      this.objZipDocx.write(buf, 0, buf.length);
      this.objZipDocx.closeEntry();

      this.fGenerateTemplate_Word_Theme_Theme1();
      this.objZipDocx.putNextEntry(new ZipEntry("word/theme/theme1.xml"));
      buf = this.xml_Word_Theme_Theme1__Template.getBytes();
      this.objZipDocx.write(buf, 0, buf.length);
      this.objZipDocx.closeEntry();

      this.objZipDocx.close();
      File file = new File("document." + this.extension);
      // File (or directory) with new name
      File file2 = new File(strFileName + "." + this.extension);
      // Rename file (or directory)
     /* boolean success = file.renameTo(file2);
      if (!success) {
        DefaultLogger.debug(this,"There is a problem when rename the document");
      }*/

    } catch (Exception e) {
      DefaultLogger.debug(this,"There is a exception when generating the document " + e.toString());
    }
  }

  public void fAddFont(HashMap arrFont) {
    cCreateFontTable objFont = cCreateFontTable.getInstance();
    objFont.fCreateFont(arrFont);
    this.xml_Word_FontTable__Content += objFont.toString();
  }

  public void fAddFooter(String strDat, HashMap arrParamsFooter) {
    if (!strDat.equals("")) {
      cCreateFooter objFooter = cCreateFooter.getInstance();
      objFooter.fCreateFooter(strDat, arrParamsFooter);
      this.xml_Word_Footer__Content += objFooter.toString();
    }
    this.fGenerateOVERRIDE("/word/footer.xml", "application/vnd.openxmlformats-officedocument.wordprocessingml.footer+xml");
  }

  public void fAddHeader(String strDat, HashMap arrParamsHeader) {
    if (!strDat.equals("")) {
      cCreateHeader objHeader = cCreateHeader.getInstance();
      objHeader.fCreateHeader(strDat, arrParamsHeader);////strDat,
      this.xml_Word_Header__Content += objHeader.toString();
    }    
    this.fGenerateOVERRIDE("/word/header.xml", "application/vnd.openxmlformats-officedocument.wordprocessingml.header+xml");
  }

  public boolean fAddImage(HashMap arrDat) {
    File file;
    if (arrDat.get("name") != null) {
      try {
        file = new File(arrDat.get("name").toString());
      } catch (NullPointerException e) {
        DefaultLogger.debug(this,"The image " + arrDat.get("name").toString() + " does not exist.");
        return false;
      }

      Image img = new ImageIcon(arrDat.get("name").toString()).getImage();
      int width = img.getWidth(null);
      int height = img.getHeight(null);
      String mimeType = new MimetypesFileTypeMap().getContentType(file);
      if (mimeType.equals("image/jpg") || mimeType.equals("image/jpeg") || mimeType.equals("image/png") || mimeType.equals("application/octet-stream") || mimeType.equals("image/gif")) {
        this.intIdWord++;
        cCreateImage objImg = cCreateImage.getInstance();
        arrDat.put("rId", String.valueOf(this.intIdWord));
        objImg.fCreateImage(arrDat);
        this.xml_Word_Document__Content += objImg.toString();
        HashMap arrDir = this.parsePath(arrDat.get("name").toString());
        try {
          this.objZipDocx.putNextEntry(new ZipEntry("word/media/image" + this.intIdWord + "." + arrDir.get("extension").toString()));
          File myImageFile = new File(arrDat.get("name").toString());
          FileInputStream fis = new FileInputStream(myImageFile);
          byte[] data = new byte[1024];
          byte[] tmp = new byte[0];
          byte[] myArrayImage = new byte[0];
          int len = 0;
          int total = 0;
          while ((len = fis.read(data)) != -1) {
            total += len;
            tmp = myArrayImage;
            myArrayImage = new byte[total];
            System.arraycopy(tmp, 0, myArrayImage, 0, tmp.length);
            System.arraycopy(data, 0, myArrayImage, tmp.length, len);
          }
          fis.close();
          this.objZipDocx.write(myArrayImage, 0, myArrayImage.length);
          this.objZipDocx.closeEntry();
        } catch (Exception e) {
          DefaultLogger.debug(this,"There is a exception including the image: " + e.toString());
        }
        this.fGenerateDEFAULT(arrDir.get("extension").toString(), mimeType);
        if (!objImg.toString().equals("")) {
          this.xml_Word_Rels_DocumentRels__Content += this.fGenerateRELATIONSHIP("rId" + this.intIdWord, "image", "media/image" + this.intIdWord + "." + arrDir.get("extension").toString());
        }
      } else {
        DefaultLogger.debug(this,"The type of the image that you try to insert, is not suported " + mimeType + "\n");
        return false;
      }
    } else {
      DefaultLogger.debug(this,"The image that you try to insert does not exist\n");
      return false;
    }
    return true;
  }

  public boolean fAddGraphic(HashMap arrDatos) {
    if (arrDatos.containsKey("data") && arrDatos.containsKey("type")) {
      this.intIdWord++;
      cCreateGraphic objGraphic = cCreateGraphic.getInstance();
      if (objGraphic.fCreateGraphic(this.intIdWord, arrDatos) != false) {
        try {
          this.objZipDocx.putNextEntry(new ZipEntry("word/charts/chart" + this.intIdWord + ".xml"));
          byte[] buf = objGraphic.getXmlChart().getBytes();
          this.objZipDocx.write(buf, 0, buf.length);
          this.objZipDocx.closeEntry();
        } catch (Exception e) {
          DefaultLogger.debug(this,"There is a exception adding the chart file inside the document: " + e.toString());
          return false;
        }
        this.xml_Word_Rels_DocumentRels__Content += this.fGenerateRELATIONSHIP("rId" + this.intIdWord, "chart", "charts/chart" + this.intIdWord + ".xml");
        this.xml_Word_Document__Content += objGraphic.toString();
        this.fGenerateDEFAULT("xlsx", "application/octet-stream");
        this.fGenerateOVERRIDE("/word/charts/chart" + this.intIdWord + ".xml", "application/vnd.openxmlformats-officedocument.drawingml.chart+xml");
      } else {
        DefaultLogger.debug(this,"There is a error when generate the chart file\n");
        return false;
      }
      //cCreateXlsx objExcel = cCreateXlsx.getInstance();
      arrDatos.put("fileName", "datos" + this.intIdWord);
      arrDatos.put("id",String.valueOf( this.intIdWord));
/*      if (objExcel.fCreateXlsx(arrDatos) != false) {
        try {
          int num = this.intIdWord;
          this.objZipDocx.putNextEntry(new ZipEntry("word/embeddings/datos" + num + ".xlsx"));
          File myImageFile = new File("datos" + num + ".xlsx");
          FileInputStream fis = new FileInputStream(myImageFile);
          byte[] data = new byte[1024];
          byte[] tmp = new byte[0];
          byte[] myArrayImage = new byte[0];
          int len = 0;
          int total = 0;
          while ((len = fis.read(data)) != -1) {
            total += len;
            tmp = myArrayImage;
            myArrayImage = new byte[total];
            System.arraycopy(tmp, 0, myArrayImage, 0, tmp.length);
            System.arraycopy(data, 0, myArrayImage, tmp.length, len);
          }
          fis.close();
          this.objZipDocx.write(myArrayImage, 0, myArrayImage.length);
          this.objZipDocx.closeEntry();
        } catch (Exception e) {
          DefaultLogger.debug(this,"There is a exception adding the excel file inside the document: " + e.toString());
        }
        int num = this.intIdWord;
        File borrar = new File("datos" + num + ".xlsx");
        borrar.delete();
        cCreateChartRels objChartRels = cCreateChartRels.getInstance();
        objChartRels.fCreateRelationship("" + num);

        try {
          this.objZipDocx.putNextEntry(new ZipEntry("word/charts/_rels/chart" + this.intIdWord + ".xml.rels"));
          byte[] buf = objChartRels.toString().getBytes();
          this.objZipDocx.write(buf, 0, buf.length);
          this.objZipDocx.closeEntry();
        } catch (Exception e) {
          DefaultLogger.debug(this,"There is a exception adding the chart file inside the document: " + e.toString());
        }
        return true;
      } else {
        DefaultLogger.debug(this,"There is a problem when generate the excel file\n");
        return false;
      }*/
      return false;
    } else {
      DefaultLogger.debug(this,"There is not type or data of the graphic\n");
      return false;
    }
  }

  public void fAddLink(String strText, String strLink) {
    cCreateLink objLink = cCreateLink.getInstance();
    objLink.fCreateLink(strText, strLink);
    this.xml_Word_Document__Content += objLink.toString();
  }

  public void fAddList(HashMap arrParamsList) {
    cCreateList objList = cCreateList.getInstance();
    objList.fCreateList(arrParamsList);
    this.xml_Word_Document__Content += objList.toString();
  }

  public void fAddMathDocx(String strPath) {
    try {
      String rscRelations = "";
      ZipFile fZip = new ZipFile(strPath);
      Enumeration zipEntries = fZip.entries();
      while (zipEntries.hasMoreElements()) {
        ZipEntry zZip = (ZipEntry) zipEntries.nextElement();
        if (zZip.getName().equals("word/document.xml")) {
          BufferedReader br = new BufferedReader(new InputStreamReader(fZip.getInputStream(zZip)));
          String line = new String();
          while ((line = br.readLine()) != null) {
            rscRelations = rscRelations + line;
          }
          br.close();
        }
      }
      String strMath = rscRelations.substring(rscRelations.indexOf("<m:oMathPara>"), rscRelations.indexOf("</m:oMathPara>") + "</m:oMathPara>".length());
      this.fAddMathEq(strMath);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public void fAddMathEq(String strDat) {
    this.xml_Word_Document__Content += "<" + cCreateDocx.NAMESPACE + ":p>" + strDat + "</" + cCreateDocx.NAMESPACE + ":p>";
  }

  public void fAddTable(HashMap arrDat) {
    cCreateTable objTable = cCreateTable.getInstance();
    objTable.fCreateTable(arrDat);
    this.xml_Word_Document__Content += objTable.toString();
  }

  public void fAddText(HashMap strDat) {
    try {
      cCreateText objText = cCreateText.getInstance();
      objText.fCreateText(strDat);
      this.xml_Word_Document__Content += objText.toString();
    } catch (Exception e) {
      DefaultLogger.debug(this,"There is a exception adding text inside the document: " + e.toString());
    }
  }

  public void fAddTitle(HashMap strDat) {
    cCreateText objText = cCreateText.getInstance();
    objText.fCreateTitle(strDat);
    this.xml_Word_Document__Content += objText.toString();
  }

  public void fAddTheme(String strFont) {
    cCreateTheme1 objTheme = cCreateTheme1.getInstance();
    objTheme.fCreateTheme(strFont);
    this.xml_Word_Theme_Theme1__Content += objTheme.toString();
  }

  public void fAddSettings() {
    cCreateSettings objSettings = cCreateSettings.getInstance();
    objSettings.fGenerateSettings();
    this.xml_Word_Settings__Content += objSettings.toString();
  }

  public void fAddStyle() {
    cCreateStyle objStyle = cCreateStyle.getInstance();
    objStyle.fCreateStyle("");
    this.xml_Word_Styles__Content += objStyle.toString();
  }

  public void fAddWebSettings() {
    cCreateWebSettings objWebSettings = cCreateWebSettings.getInstance();
    objWebSettings.fGenerateWebSettings();
    this.xml_Word_WebSettings__Content += objWebSettings.toString();
  }

  public void fAddBreak(String strType) {
    cCreatePage objPage = cCreatePage.getInstance();
    objPage.fGeneratePageBreak(strType);
    this.xml_Word_Document__Content += objPage.toString();
  }

  public void fSetDefaultFont(String strFont){
	this.defaultFont = strFont;
   }
}
