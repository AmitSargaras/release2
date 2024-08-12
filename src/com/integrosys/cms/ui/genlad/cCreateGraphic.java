/**
 * Generate graphics.
 * @author 2mdc
 */
package com.integrosys.cms.ui.genlad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.integrosys.base.techinfra.logger.DefaultLogger;

public class cCreateGraphic extends cCreateElement {

  private static final String NAMESPACE = "c";
  private cCreateImage objImage = new cCreateImage();
  private static cCreateGraphic rscInstance = null;
  private int rId;
  private String sizeX;
  private String sizeY;
  private String type;
  private HashMap datos;
  private String title;
  private String name;
  protected String xmlChart;

  public void cCreateGraphic() {
    this.rId = 0;
    this.sizeX = new String();
    this.sizeY = new String();
    this.type = new String();
    this.datos = new HashMap();
    this.title = new String();
    this.xml = new String();
    this.name = new String();
    this.xmlChart = "";
  }

  
  public String toString() {
    return this.xml;
  }

  static public cCreateGraphic getInstance() {
    if (rscInstance == null) {
      rscInstance = new cCreateGraphic();
    }
    return rscInstance;
  }

  public void setRId(int rId) {
    this.rId = rId;
  }

  public int getRId() {
    return this.rId;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public void setXmlChart(String xmlChart) {
    this.xmlChart = xmlChart;
  }

  public String getXmlChart() {
    return this.xmlChart;
  }

  protected void fGenerateCHARTSPACE() {
    this.xmlChart = "<?xml version='1.0' encoding='UTF-8' standalone='yes' ?><" + cCreateGraphic.NAMESPACE + ":chartSpace xmlns:c='http://schemas.openxmlformats.org/drawingml/2006/chart' xmlns:a='http://schemas.openxmlformats.org/drawingml/2006/main' xmlns:r='http://schemas.openxmlformats.org/officeDocument/2006/relationships'>__GENERATECHARTSPACE__</" + cCreateGraphic.NAMESPACE + ":chartSpace>";
  }

  protected void fGenerateDATE1904(String val) {
    if (val.equals("")) {
      val = "1";
    }
    this.xmlChart = this.xmlChart.replaceAll("__GENERATECHARTSPACE__", "<" + cCreateGraphic.NAMESPACE + ":date1904 val='" + val + "'></" + cCreateGraphic.NAMESPACE + ":date1904>__GENERATECHARTSPACE__");
  }

  protected void fGenerateLANG(String val) {
    if (val.equals("")) {
      val = "es-ES";
    }
    this.xmlChart = this.xmlChart.replaceAll("__GENERATECHARTSPACE__", "<" + cCreateGraphic.NAMESPACE + ":lang val='" + val + "'></" + cCreateGraphic.NAMESPACE + ":lang>__GENERATECHARTSPACE__");
  }

  protected void fGenerateSTYLE(String val) {
    if (val.equals("")) {
      val = "2";
    }
    this.xmlChart = this.xmlChart.replaceAll("__GENERATECHARTSPACE__", "<" + cCreateGraphic.NAMESPACE + ":style val='" + val + "'></" + cCreateGraphic.NAMESPACE + ":style>__GENERATECHARTSPACE__");
  }

  protected void fGenerateTITLE() {
    this.xmlChart = this.xmlChart.replaceAll("__GENERATECHART__", "<" + cCreateGraphic.NAMESPACE + ":title>__GENERATETITLE__</" + cCreateGraphic.NAMESPACE + ":title>__GENERATECHART__");
  }

  protected void fGenerateTITLETX() {
    this.xmlChart = this.xmlChart.replaceAll("__GENERATETITLE__", "<" + cCreateGraphic.NAMESPACE + ":tx>__GENERATETITLETX__</" + cCreateGraphic.NAMESPACE + ":tx>__GENERATETITLE__");
  }

  protected void fGenerateRICH() {
    this.xmlChart = this.xmlChart.replaceAll("__GENERATETITLETX__", "<" + cCreateGraphic.NAMESPACE + ":rich>__GENERATERICH__</" + cCreateGraphic.NAMESPACE + ":rich>__GENERATETITLETX__");
  }

  protected void fGenerateBODYPR() {
    this.xmlChart = this.xmlChart.replaceAll("__GENERATERICH__", "<" + objImage.NAMESPACE1 + ":bodyPr></" + objImage.NAMESPACE1 + ":bodyPr>__GENERATERICH__");
  }

  protected void fGenerateLSTSTYLE() {
    this.xmlChart = this.xmlChart.replaceAll("__GENERATERICH__", "<" + objImage.NAMESPACE1 + ":lstStyle></" + objImage.NAMESPACE1 + ":lstStyle>__GENERATERICH__");
  }

  protected void fGenerateTITLEP() {
    this.xmlChart = this.xmlChart.replaceAll("__GENERATERICH__", "<" + objImage.NAMESPACE1 + ":p>__GENERATETITLEP__</" + objImage.NAMESPACE1 + ":p>__GENERATERICH__");
  }

  protected void fGenerateTITLEPPR() {
    this.xmlChart = this.xmlChart.replaceAll("__GENERATETITLEP__", "<" + objImage.NAMESPACE1 + ":pPr>__GENERATETITLEPPR__</" + objImage.NAMESPACE1 + ":pPr>__GENERATETITLEP__");
  }

  protected void fGenerateDEFRPR() {
    this.xmlChart = this.xmlChart.replaceAll("__GENERATETITLEPPR__", "<" + objImage.NAMESPACE1 + ":defRPr></" + objImage.NAMESPACE1 + ":defRPr>__GENERATETITLEPPR__");
  }

  protected void fGenerateTITLER() {
    this.xmlChart = this.xmlChart.replaceAll("__GENERATETITLEP__", "<" + objImage.NAMESPACE1 + ":r>__GENERATETITLER__</" + objImage.NAMESPACE1 + ":r>__GENERATETITLEP__");
  }

  protected void fGenerateTITLERPR(String lang) {
    if (lang.equals("")) {
      lang = "es-ES";
    }
    this.xmlChart = this.xmlChart.replaceAll("__GENERATETITLER__", "<" + objImage.NAMESPACE1 + ":rPr lang='" + lang + "'></" + objImage.NAMESPACE1 + ":rPr>__GENERATETITLER__");
  }

  protected void fGenerateTITLET(String nombre) {
    this.xmlChart = this.xmlChart.replaceAll("__GENERATETITLER__", "<" + objImage.NAMESPACE1 + ":t>" + nombre + "</" + objImage.NAMESPACE1 + ":t>__GENERATETITLER__");
  }

  protected void fGenerateTITLELAYOUT(String nombre) {
    this.xmlChart = this.xmlChart.replaceAll("__GENERATETITLE__", "<" + objImage.NAMESPACE1 + ":layout></" + objImage.NAMESPACE1 + ":layout>");
  }

  protected void fGenerateAUTOTITLEDELETED(String val) {
    if (val.equals("")) {
      val = "1";
    }
    this.xmlChart = this.xmlChart.replaceAll("__GENERATECHART__", "<" + cCreateGraphic.NAMESPACE + ":autoTitleDeleted val='" + val + "'></" + cCreateGraphic.NAMESPACE + ":autoTitleDeleted>__GENERATECHART__");
  }

  protected void fGenerateVIEW3D() {
    this.xmlChart = this.xmlChart.replaceAll("__GENERATECHART__", "<" + cCreateGraphic.NAMESPACE + ":view3D>__GENERATEVIEW3D__</" + cCreateGraphic.NAMESPACE + ":view3D>__GENERATECHART__");
  }

  protected void fGenerateROTX(String val) {
    if (val.equals("")) {
      val = "30";
    }
    this.xmlChart = this.xmlChart.replaceAll("__GENERATEVIEW3D__", "<" + cCreateGraphic.NAMESPACE + ":rotX val='" + val + "'></" + cCreateGraphic.NAMESPACE + ":rotX>__GENERATEVIEW3D__");
  }

  protected void fGenerateROTY(String val) {
    if (val.equals("")) {
      val = "30";
    }
    this.xmlChart = this.xmlChart.replaceAll("__GENERATEVIEW3D__", "<" + cCreateGraphic.NAMESPACE + ":rotY val='" + val + "'></" + cCreateGraphic.NAMESPACE + ":rotY>__GENERATEVIEW3D__");
  }

  protected void fGeneratePERSPECTIVE(String val) {
    if (val.equals("")) {
      val = "30";
    }
    this.xmlChart = this.xmlChart.replaceAll("__GENERATEVIEW3D__", "<" + cCreateGraphic.NAMESPACE + ":perspective val='" + val + "'></" + cCreateGraphic.NAMESPACE + ":perspective>");
  }

  protected void fGenerateCHART() {
    this.xmlChart = this.xmlChart.replaceAll("__GENERATECHARTSPACE__", "<" + cCreateGraphic.NAMESPACE + ":chart>__GENERATECHART__</" + cCreateGraphic.NAMESPACE + ":chart>__GENERATECHARTSPACE__");
  }

  protected void fGeneratePLOTAREA() {
    this.xmlChart = this.xmlChart.replaceAll("__GENERATECHART__", "<" + cCreateGraphic.NAMESPACE + ":plotArea>__GENERATEPLOTAREA__</" + cCreateGraphic.NAMESPACE + ":plotArea>__GENERATECHART__");
  }

  protected void fGenerateLAYOUT() {
    this.xmlChart = this.xmlChart.replaceAll("__GENERATEPLOTAREA__", "<" + cCreateGraphic.NAMESPACE + ":layout></" + cCreateGraphic.NAMESPACE + ":layout>__GENERATEPLOTAREA__");
  }

  protected void fGeneratePIECHART() {
    this.xmlChart = this.xmlChart.replaceAll("__GENERATEPLOTAREA__", "<" + cCreateGraphic.NAMESPACE + ":pieChart>__GENERATETYPECHART__</" + cCreateGraphic.NAMESPACE + ":pieChart>__GENERATEPLOTAREA__");
  }

  protected void fGeneratePIE3DCHART() {
    this.xmlChart = this.xmlChart.replaceAll("__GENERATEPLOTAREA__", "<" + cCreateGraphic.NAMESPACE + ":pie3DChart>__GENERATETYPECHART__</" + cCreateGraphic.NAMESPACE + ":pie3DChart>__GENERATEPLOTAREA__");
  }

  protected void fGenerateBARCHART() {
    this.xmlChart = this.xmlChart.replaceAll("__GENERATEPLOTAREA__", "<" + cCreateGraphic.NAMESPACE + ":barChart>__GENERATETYPECHART__</" + cCreateGraphic.NAMESPACE + ":barChart>__GENERATEPLOTAREA__");
  }

  protected void fGenerateLINECHART() {
    this.xmlChart = this.xmlChart.replaceAll("__GENERATEPLOTAREA__", "<" + cCreateGraphic.NAMESPACE + ":lineChart>__GENERATETYPECHART__</" + cCreateGraphic.NAMESPACE + ":lineChart>__GENERATEPLOTAREA__");
  }

  protected void fGenerateLINE3DCHART() {
    this.xmlChart = this.xmlChart.replaceAll("__GENERATEPLOTAREA__", "<" + cCreateGraphic.NAMESPACE + ":line3DChart>__GENERATETYPECHART__</" + cCreateGraphic.NAMESPACE + ":line3DChart>__GENERATEPLOTAREA__");
  }

  protected void fGenerateBAR3DCHART() {
    this.xmlChart = this.xmlChart.replaceAll("__GENERATEPLOTAREA__", "<" + cCreateGraphic.NAMESPACE + ":bar3DChart>__GENERATETYPECHART__</" + cCreateGraphic.NAMESPACE + ":bar3DChart>__GENERATEPLOTAREA__");
  }

  protected void fGenerateVARYCOLORS(String val) {
    if (val.equals("")) {
      val = "1";
    }
    this.xmlChart = this.xmlChart.replaceAll("__GENERATETYPECHART__", "<" + cCreateGraphic.NAMESPACE + ":varyColors val='" + val + "'></" + cCreateGraphic.NAMESPACE + ":varyColors>__GENERATETYPECHART__");
  }

  protected void fGenerateBARDIR(String val) {
    if (val.equals("")) {
      val = "bar";
    }
    this.xmlChart = this.xmlChart.replaceAll("__GENERATETYPECHART__", "<" + cCreateGraphic.NAMESPACE + ":barDir val='" + val + "'></" + cCreateGraphic.NAMESPACE + ":barDir>__GENERATETYPECHART__");
  }

  protected void fGenerateGROUPING(String val) {
    if (val.equals("")) {
      val = "stacked";
    }
    this.xmlChart = this.xmlChart.replaceAll("__GENERATETYPECHART__", "<" + cCreateGraphic.NAMESPACE + ":grouping val='" + val + "'></" + cCreateGraphic.NAMESPACE + ":grouping>__GENERATETYPECHART__");
  }

  protected void fGenerateSER() {
    this.xmlChart = this.xmlChart.replaceAll("__GENERATETYPECHART__", "<" + cCreateGraphic.NAMESPACE + ":ser>__GENERATESER__</" + cCreateGraphic.NAMESPACE + ":ser>__GENERATETYPECHART__");
  }

  protected void fGenerateIDX(String val) {
    if (val.equals("")) {
      val = "0";
    }
    this.xmlChart = this.xmlChart.replaceAll("__GENERATESER__", "<" + cCreateGraphic.NAMESPACE + ":idx val='" + val + "'></" + cCreateGraphic.NAMESPACE + ":idx>__GENERATESER__");
  }

  protected void fGenerateORDER(String val) {
    if (val.equals("")) {
      val = "0";
    }
    this.xmlChart = this.xmlChart.replaceAll("__GENERATESER__", "<" + cCreateGraphic.NAMESPACE + ":order val='" + val + "'></" + cCreateGraphic.NAMESPACE + ":order>__GENERATESER__");
  }

  protected void fGenerateTX() {
    this.xmlChart = this.xmlChart.replaceAll("__GENERATESER__", "<" + cCreateGraphic.NAMESPACE + ":tx>__GENERATETX__</" + cCreateGraphic.NAMESPACE + ":tx>__GENERATESER__");
  }

  protected void fGenerateSTRREF() {
    this.xmlChart = this.xmlChart.replaceAll("__GENERATETX__", "<" + cCreateGraphic.NAMESPACE + ":strRef>__GENERATESTRREF__</" + cCreateGraphic.NAMESPACE + ":strRef>__GENERATETX__");
  }

  protected void fGenerateF(String val) {
    if (val.equals("")) {
      val = "Hoja1!$B$1";
    }
    this.xmlChart = this.xmlChart.replace("__GENERATESTRREF__", "<" + cCreateGraphic.NAMESPACE + ":f>" + val + "</" + cCreateGraphic.NAMESPACE + ":f>__GENERATESTRREF__");
  }

  protected void fGenerateSTRCACHE() {
    this.xmlChart = this.xmlChart.replaceAll("__GENERATESTRREF__", "<" + cCreateGraphic.NAMESPACE + ":strCache>__GENERATESTRCACHE__</" + cCreateGraphic.NAMESPACE + ":strCache>__GENERATESTRREF__");
  }

  protected void fGeneratePTCOUNT(String val) {
    if (val.equals("")) {
      val = "1";
    }
    this.xmlChart = this.xmlChart.replaceAll("__GENERATESTRCACHE__", "<" + cCreateGraphic.NAMESPACE + ":ptCount val='" + val + "'></" + cCreateGraphic.NAMESPACE + ":ptCount>__GENERATESTRCACHE__");
  }

  protected void fGeneratePT(String idx) {
    if (idx.equals("")) {
      idx = "0";
    }
    this.xmlChart = this.xmlChart.replaceAll("__GENERATESTRCACHE__", "<" + cCreateGraphic.NAMESPACE + ":pt idx='" + idx + "'>__GENERATEPT__</" + cCreateGraphic.NAMESPACE + ":pt>__GENERATESTRCACHE__");
  }

  protected void fGenerateV(String idx) {
    if (idx.equals("")) {
      idx = "Ventas";
    }
    this.xmlChart = this.xmlChart.replaceAll("__GENERATEPT__", "<" + cCreateGraphic.NAMESPACE + ":v>" + idx + "</" + cCreateGraphic.NAMESPACE + ":v>");
  }

  protected void fGenerateCAT() {
    this.xmlChart = this.xmlChart.replaceAll("__GENERATESER__", "<" + cCreateGraphic.NAMESPACE + ":cat>__GENERATETX__</" + cCreateGraphic.NAMESPACE + ":cat>__GENERATESER__");
  }

  protected void fGenerateVAL() {
    this.xmlChart = this.xmlChart.replaceAll("__GENERATESER__", "<" + cCreateGraphic.NAMESPACE + ":val>__GENERATETX__</" + cCreateGraphic.NAMESPACE + ":val>__GENERATESER__");
  }

  protected void fGenerateNUMCACHE() {
    this.xmlChart = this.xmlChart.replaceAll("__GENERATESTRREF__", "<" + cCreateGraphic.NAMESPACE + ":numCache>__GENERATESTRCACHE__</" + cCreateGraphic.NAMESPACE + ":numCache>__GENERATESTRREF__");
  }

  protected void fGenerateNUMREF() {
    this.xmlChart = this.xmlChart.replaceAll("__GENERATETX__", "<" + cCreateGraphic.NAMESPACE + ":numRef>__GENERATESTRREF__</" + cCreateGraphic.NAMESPACE + ":numRef>__GENERATETX__");
  }

  protected void fGenerateFORMATCODE(String val) {
    if (val.equals("")) {
      val = "General";
    }
    this.xmlChart = this.xmlChart.replaceAll("__GENERATESTRCACHE__", "<" + cCreateGraphic.NAMESPACE + ":formatCode>" + val + "</" + cCreateGraphic.NAMESPACE + ":formatCode>__GENERATESTRCACHE__");
  }

  protected void fGenerateLEGEND() {
    this.xmlChart = this.xmlChart.replaceAll("__GENERATECHART__", "<" + cCreateGraphic.NAMESPACE + ":legend>__GENERATELEGEND__</" + cCreateGraphic.NAMESPACE + ":legend>__GENERATECHART__");
  }

  protected void fGenerateLEGENDPOS(String val) {
    if (val.equals("")) {
      val = "r";
    }
    this.xmlChart = this.xmlChart.replaceAll("__GENERATELEGEND__", "<" + cCreateGraphic.NAMESPACE + ":legendPos val='" + val + "'></" + cCreateGraphic.NAMESPACE + ":legendPos>");
  }

  protected void fGeneratePLOTVISONLY(String val) {
    if (val.equals("")) {
      val = "1";
    }
    this.xmlChart = this.xmlChart.replaceAll("__GENERATECHART__", "<" + cCreateGraphic.NAMESPACE + ":plotVisOnly val='" + val + "'></" + cCreateGraphic.NAMESPACE + ":plotVisOnly>__GENERATECHART__");
  }

  protected void fGenerateEXTERNALDATA(String val) {
    if (val.equals("")) {
      val = "rId1";
    }
    this.xmlChart = this.xmlChart.replaceAll("__GENERATECHARTSPACE__", "<" + cCreateGraphic.NAMESPACE + ":externalData r:id='" + val + "'></" + cCreateGraphic.NAMESPACE + ":externalData>");
  }

  protected void fGenerateSPPR() {
    this.xmlChart = this.xmlChart.replaceAll("__GENERATECHARTSPACE__", "<" + cCreateGraphic.NAMESPACE + ":spPr>__GENERATESPPR__</" + cCreateGraphic.NAMESPACE + ":spPr>__GENERATECHARTSPACE__");
  }

  protected void fGenerateLN() {
    this.xmlChart = this.xmlChart.replaceAll("__GENERATESPPR__", "<" + objImage.NAMESPACE1 + ":ln>__GENERATELN__</" + objImage.NAMESPACE1 + ":ln>");
  }

  protected void fGenerateNOFILL() {
    this.xmlChart = this.xmlChart.replaceAll("__GENERATELN__", "<" + objImage.NAMESPACE1 + ":noFill></" + objImage.NAMESPACE1 + ":noFill>");
  }

  protected void fGenerateOVERLAP(String val) {
    if (val.equals("")) {
      val = "100";
    }
    this.xmlChart = this.xmlChart.replaceAll("__GENERATETYPECHART__", "<" + cCreateGraphic.NAMESPACE + ":overlap val='" + val + "'></" + cCreateGraphic.NAMESPACE + ":overlap>__GENERATETYPECHART__");
  }

  protected void fGenerateSHAPE(String val) {
    if (val.equals("")) {
      val = "box";
    }
    this.xmlChart = this.xmlChart.replaceAll("__GENERATETYPECHART__", "<" + cCreateGraphic.NAMESPACE + ":shape val='" + val + "'></" + cCreateGraphic.NAMESPACE + ":shape>__GENERATETYPECHART__");
  }

  protected void fGenerateAXID(String val) {
    if (val.equals("")) {
      val = "59034624";
    }
    this.xmlChart = this.xmlChart.replaceAll("__GENERATETYPECHART__", "<" + cCreateGraphic.NAMESPACE + ":axId val='" + val + "'></" + cCreateGraphic.NAMESPACE + ":axId>__GENERATETYPECHART__");
  }

  protected void fGenerateFIRSTSLICEANG(String val) {
    if (val.equals("")) {
      val = "0";
    }
    this.xmlChart = this.xmlChart.replaceAll("__GENERATETYPECHART__", "<" + cCreateGraphic.NAMESPACE + ":firstSliceAng val='" + val + "'></" + cCreateGraphic.NAMESPACE + ":firstSliceAng>");
  }

  protected void fGenerateDLBLS() {
    this.xmlChart = this.xmlChart.replaceAll("__GENERATETYPECHART__", "<" + cCreateGraphic.NAMESPACE + ":dLbls>__GENERATEDLBLS__</" + cCreateGraphic.NAMESPACE + ":dLbls>__GENERATETYPECHART__");
  }

  protected void fGenerateSHOWPERCENT(String val) {
    if (val.equals("")) {
      val = "0";
    }
    this.xmlChart = this.xmlChart.replaceAll("__GENERATEDLBLS__", "<" + cCreateGraphic.NAMESPACE + ":showPercent val='" + val + "'></" + cCreateGraphic.NAMESPACE + ":showPercent>");
  }

  protected void fGenerateDOCUMENTCHART() {
    this.xml = this.xml.replaceAll("__GENERATEGRAPHICDATA__", "<" + cCreateGraphic.NAMESPACE + ":chart xmlns:c='http://schemas.openxmlformats.org/drawingml/2006/chart' xmlns:r='http://schemas.openxmlformats.org/officeDocument/2006/relationships' r:id='rId" + this.getRId() + "'></" + cCreateGraphic.NAMESPACE + ":chart>");
  }

  protected void fGenerateCATAX() {
    this.xmlChart = this.xmlChart.replaceAll("__GENERATEPLOTAREA__", "<" + cCreateGraphic.NAMESPACE + ":catAx>__GENERATEAX__</" + cCreateGraphic.NAMESPACE + ":catAx>__GENERATEPLOTAREA__");
  }

  protected void fGenerateVALAX() {
    this.xmlChart = this.xmlChart.replaceAll("__GENERATEPLOTAREA__", "<" + cCreateGraphic.NAMESPACE + ":valAx>__GENERATEAX__</" + cCreateGraphic.NAMESPACE + ":valAx>");
  }

  protected void fGenerateAXAXID(String val) {
    if (val.equals("")) {
      val = "59034624";
    }
    this.xmlChart = this.xmlChart.replaceAll("__GENERATEAX__", "<" + cCreateGraphic.NAMESPACE + ":axId val='" + val + "'></" + cCreateGraphic.NAMESPACE + ":axId>__GENERATEAX__");
  }

  protected void fGenerateSCALING() {
    this.xmlChart = this.xmlChart.replaceAll("__GENERATEAX__", "<" + cCreateGraphic.NAMESPACE + ":scaling>__GENERATESCALING__</" + cCreateGraphic.NAMESPACE + ":scaling>__GENERATEAX__");
  }

  protected void fGenerateORIENTATION(String val) {
    if (val.equals("")) {
      val = "minMax";
    }
    this.xmlChart = this.xmlChart.replaceAll("__GENERATESCALING__", "<" + cCreateGraphic.NAMESPACE + ":orientation val='" + val + "'></" + cCreateGraphic.NAMESPACE + ":orientation>");
  }

  protected void fGenerateAXPOS(String val) {
    if (val.equals("")) {
      val = "b";
    }
    this.xmlChart = this.xmlChart.replaceAll("__GENERATEAX__", "<" + cCreateGraphic.NAMESPACE + ":axPos val='" + val + "'></" + cCreateGraphic.NAMESPACE + ":axPos>__GENERATEAX__");
  }

  protected void fGenerateTICKLBLPOS(String val) {
    if (val.equals("")) {
      val = "nextTo";
    }
    this.xmlChart = this.xmlChart.replaceAll("__GENERATEAX__", "<" + cCreateGraphic.NAMESPACE + ":tickLblPos val='" + val + "'></" + cCreateGraphic.NAMESPACE + ":tickLblPos>__GENERATEAX__");
  }

  protected void fGenerateCROSSAX(String val) {
    if (val.equals("")) {
      val = "59040512";
    }
    this.xmlChart = this.xmlChart.replaceAll("__GENERATEAX__", "<" + cCreateGraphic.NAMESPACE + ":crossAx  val='" + val + "'></" + cCreateGraphic.NAMESPACE + ":crossAx >__GENERATEAX__");
  }

  protected void fGenerateCROSSES(String val) {
    if (val.equals("")) {
      val = "autoZero";
    }
    this.xmlChart = this.xmlChart.replaceAll("__GENERATEAX__", "<" + cCreateGraphic.NAMESPACE + ":crosses val='" + val + "'></" + cCreateGraphic.NAMESPACE + ":crosses>__GENERATEAX__");
  }

  protected void fGenerateAUTO(String val) {
    if (val.equals("")) {
      val = "1";
    }
    this.xmlChart = this.xmlChart.replaceAll("__GENERATEAX__", "<" + cCreateGraphic.NAMESPACE + ":auto val='" + val + "'></" + cCreateGraphic.NAMESPACE + ":auto>__GENERATEAX__");
  }

  protected void fGenerateLBLALGN(String val) {
    if (val.equals("")) {
      val = "ctr";
    }
    this.xmlChart = this.xmlChart.replaceAll("__GENERATEAX__", "<" + cCreateGraphic.NAMESPACE + ":lblAlgn val='" + val + "'></" + cCreateGraphic.NAMESPACE + ":lblAlgn>__GENERATEAX__");
  }

  protected void fGenerateLBLOFFSET(String val) {
    if (val.equals("")) {
      val = "100";
    }
    this.xmlChart = this.xmlChart.replaceAll("__GENERATEAX__", "<" + cCreateGraphic.NAMESPACE + ":lblOffset val='" + val + "'></" + cCreateGraphic.NAMESPACE + ":lblOffset>");
  }

  protected void fGenerateMAJORGRIDLINES() {
    this.xmlChart = this.xmlChart.replaceAll("__GENERATEAX__", "<" + cCreateGraphic.NAMESPACE + ":majorGridlines></" + cCreateGraphic.NAMESPACE + ":majorGridlines>__GENERATEAX__");
  }

  protected void fGenerateNUMFMT(String formatCode, String sourceLinked) {
    if (formatCode.equals("")) {
      formatCode = "General";
    }
    if (sourceLinked.equals("")) {
      sourceLinked = "1";
    }
    this.xmlChart = this.xmlChart.replaceAll("__GENERATEAX__", "<" + cCreateGraphic.NAMESPACE + ":numFmt formatCode='" + formatCode + "' sourceLinked='" + sourceLinked + "'></" + cCreateGraphic.NAMESPACE + ":numFmt>__GENERATEAX__");
  }

  protected void fGenerateCROSSBETWEEN(String val) {
    if (val.equals("")) {
      val = "between";
    }
    this.xmlChart = this.xmlChart.replaceAll("__GENERATEAX__", "<" + cCreateGraphic.NAMESPACE + ":crossBetween val='" + val + "'></" + cCreateGraphic.NAMESPACE + ":crossBetween>");
  }

  private void fCleanTemplateDocument() {
    this.xmlChart = this.xmlChart.replaceAll("__[A-Z]+__", "");
  }

  private void fCleanTemplate2() {
    this.xmlChart = this.xmlChart.replaceAll("__GENERATE[A-B,D-O,Q-R,U-Z][A-Z]+__", "");
    this.xmlChart = this.xmlChart.replaceAll("__GENERATES[A-D,F-Z][A-Z]+__", "");
    this.xmlChart = this.xmlChart.replaceAll("__GENERATETX__", "");
  }

  private void fCleanTemplate3() {
    this.xmlChart = this.xmlChart.replaceAll("__GENERATE[A-B,D-O,Q-S,U-Z][A-Z]+__", "");
    this.xmlChart = this.xmlChart.replaceAll("__GENERATES[A-D,F-Z][A-Z]+__", "");
    this.xmlChart = this.xmlChart.replaceAll("__GENERATETX__", "");
  }

  protected boolean fCreateCHARTXML(int id, HashMap arrArgs) {
    this.xmlChart = "";
    String type = arrArgs.get("type").toString();
    this.setRId(id);
    this.fGenerateCHARTSPACE();
    this.fGenerateDATE1904("1");
    this.fGenerateLANG("");
    this.fGenerateSTYLE("2");
    this.fGenerateCHART();
    String title;
    if (arrArgs.containsKey("title")) {
      this.fGenerateTITLE();
      title = arrArgs.get("title").toString();
      this.fGenerateTITLETX();
      this.fGenerateRICH();
      this.fGenerateBODYPR();
      this.fGenerateLSTSTYLE();
      this.fGenerateTITLEP();
      this.fGenerateTITLEPPR();
      this.fGenerateDEFRPR();
      this.fGenerateTITLER();
      this.fGenerateTITLERPR("");
      this.fGenerateTITLET(title);
      this.fGenerateTITLELAYOUT("");
    } else {
      this.fGenerateAUTOTITLEDELETED("");
      title = "";
    }
    String cornerX;
    String cornerY;
    String cornerP;
    if (!arrArgs.containsKey("data")) {
      DefaultLogger.debug(this,"There is not data for the graphic.");
      return false;
    }
    HashMap datos = (HashMap) arrArgs.get("data");
    this.fGeneratePLOTAREA();
    this.fGenerateLAYOUT();
    ArrayList leyends;
    int numDatos;
    String strTypeBar;
    String strGroupBar;
    if (type.indexOf("pie") != -1) {
      this.fGeneratePIECHART();
      this.fGenerateVARYCOLORS("");
      if (datos.containsKey("0")) {
        leyends = (ArrayList) datos.get("0");
      } else {
        leyends = new ArrayList();
        leyends.add(title);
      }
      numDatos = datos.size();
    } else if ((type.indexOf("bar") != -1) || (type.indexOf("col") != -1)) {
      this.fGenerateBARCHART();
      strTypeBar = "bar";
      if (type.indexOf("col") != -1) {
        strTypeBar = "col";
      }
      this.fGenerateBARDIR(strTypeBar);
      this.fGenerateGROUPING("clustered");
      if (datos.containsKey("0")) {
        leyends = (ArrayList) datos.get("0");
      } else {
        DefaultLogger.debug(this,"There is not legends.");
        return false;
      }
      numDatos = datos.size() - 1;
    } else if (type.indexOf("line") != -1) {
      this.fGenerateLINECHART();
      this.fGenerateGROUPING("standard");
      if (datos.containsKey("0")) {
        leyends = (ArrayList) datos.get("0");
      } else {
        DefaultLogger.debug(this,"There is not legends.");
        return false;
      }
      numDatos = datos.size() - 1;

    } else {
      DefaultLogger.debug(this,"The type of the graphic is undefined o is not suported.");
      return false;
    }
    int num;
    char letra = 'A';
    for (int i = 0; i < leyends.size(); i++) {
      this.fGenerateSER();
      this.fGenerateIDX("" + i);
      this.fGenerateORDER("" + i);
      letra += 1;

      this.fGenerateTX();
      this.fGenerateSTRREF();
      this.fGenerateF(" Hoja1!$" + letra + "$1");//deberia introducir la celda donde se encuetra el titulo  Hoja1!B1
      this.fGenerateSTRCACHE();
      this.fGeneratePTCOUNT("");
      this.fGeneratePT("");
      if (type.indexOf("pie") != -1) {
        this.fGenerateV(title);
      } else {
        this.fGenerateV(leyends.get(i).toString());
      }

      this.fCleanTemplate2();
      this.fGenerateCAT();
      this.fGenerateSTRREF();
      this.fGenerateF("Hoja1!$A$2:$A$" + (numDatos + 1));
      this.fGenerateSTRCACHE();
      this.fGeneratePTCOUNT("" + numDatos);//introducir el numero de elementos

      num = 0;
      Iterator it = datos.entrySet().iterator();
      while (it.hasNext()) {
        Map.Entry e = (Map.Entry) it.next();
        if (e.getKey().equals("0")) {
          continue;
        }
        this.fGeneratePT("" + num);
        this.fGenerateV(e.getKey().toString());
        num++;
      }
      this.fCleanTemplate2();
      this.fGenerateVAL();
      this.fGenerateNUMREF();
      this.fGenerateF("Hoja1!$" + letra + "$2:$B$" + (numDatos + 1));
      this.fGenerateNUMCACHE();
      this.fGenerateFORMATCODE("");
      this.fGeneratePTCOUNT("" + numDatos);
      num = 0;
      it = datos.entrySet().iterator();
      while (it.hasNext()) {
        Map.Entry e = (Map.Entry) it.next();
        if (e.getKey().equals("0")) {
          continue;
        }
        this.fGeneratePT("" + num);
        this.fGenerateV(((ArrayList) e.getValue()).get(i).toString());
        num++;
      }
      this.fCleanTemplate3();
    }
    if (type.indexOf("pie") != -1) {
      this.fGenerateDLBLS();
      this.fGenerateSHOWPERCENT("0");
      this.fGenerateFIRSTSLICEANG("");
    } else {
      this.fGenerateSHAPE("");
      this.fGenerateAXID("");
      this.fGenerateAXID("59040512");
      this.fGenerateCATAX();
      this.fGenerateAXAXID("59034624");
      this.fGenerateSCALING();
      this.fGenerateORIENTATION("");
      this.fGenerateAXPOS("");
      this.fGenerateTICKLBLPOS("");
      this.fGenerateCROSSAX("");
      this.fGenerateCROSSES("");
      this.fGenerateAUTO("");
      this.fGenerateLBLALGN("");
      this.fGenerateLBLOFFSET("");
      this.fGenerateVALAX();
      this.fGenerateAXAXID("59040512");
      this.fGenerateSCALING();
      this.fGenerateORIENTATION("");
      this.fGenerateAXPOS("l");
      this.fGenerateMAJORGRIDLINES();
      this.fGenerateNUMFMT("", "");
      this.fGenerateTICKLBLPOS("");
      this.fGenerateCROSSAX("59034624");
      this.fGenerateCROSSES("");
      this.fGenerateCROSSBETWEEN("");
    }
    this.fGenerateLEGEND();
    this.fGenerateLEGENDPOS("");
    this.fGeneratePLOTVISONLY("");
    if (type.indexOf("pieChart") == -1) {
      this.fGenerateSPPR();
      this.fGenerateLN();
      this.fGenerateNOFILL();
    }
    this.fGenerateEXTERNALDATA("");
    this.fCleanTemplateDocument();
    return true;
  }

  protected void fCreateDOCUEMNTXML(HashMap arrArgs) {
    String intAjusteTexto;
    int sizeX;
    int sizeY;
    int tamPageX;
    int tamPageY;
    intAjusteTexto = "0";
    if (arrArgs.get("sizeX") != null) {
      sizeX = Integer.parseInt(arrArgs.get("sizeX").toString()) * cCreateImage.CONSTWORD;
    } else {
      sizeX = 2993296;
    }
    if (arrArgs.get("sizeY") != null) {
      sizeY = Integer.parseInt(arrArgs.get("sizeY").toString()) * cCreateImage.CONSTWORD;
    } else {
      sizeY = 2238233;
    }


    this.xml = "";
    this.fGenerateP();
    this.fGenerateR();
    this.fGenerateRPR();
    this.fGenerateNOPROOF();
    this.fGenerateDRAWING();
    this.fGenerateINLINE("", "", "", "");
    this.fGenerateEXTENT("" + sizeX, "" + sizeY);
    this.fGenerateEFFECTEXTENT("", "", "", "");
    this.fGenerateDOCPR("", "");
    this.fGenerateCNVGRAPHICFRAMEPR();
    this.fGenerateGRAPHIC("");
    this.fGenerateGRAPHICDATA("http://schemas.openxmlformats.org/drawingml/2006/chart");
    this.fGenerateDOCUMENTCHART();
    this.fCleanTemplate();
  }

  public boolean fCreateGraphic(int id, HashMap arrArgs) {
    this.xmlChart = "";
    if (arrArgs.get("type") != null) {
      if (this.fCreateCHARTXML(id, arrArgs) == false) {
        DefaultLogger.debug(this,"There is not legends.");
        return false;
      }
      this.fCreateDOCUEMNTXML(arrArgs);
      return true;
    } else {
      DefaultLogger.debug(this,"The type of the graphic is undefined.");
      return false;
    }
  }
}
