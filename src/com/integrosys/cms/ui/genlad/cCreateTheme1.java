/**
 * Generate themes.
 * @author 2mdc
 */
package com.integrosys.cms.ui.genlad;

public class cCreateTheme1 extends cCreateElement {

  private static cCreateTheme1 rscInstance = null;
  public static final String NAMESPACE = "a";

  public static cCreateTheme1 getInstance() {
    if (cCreateTheme1.rscInstance == null) {
      cCreateTheme1.rscInstance = new cCreateTheme1();
    }
    return cCreateTheme1.rscInstance;
  }

   
  public String toString() {
    return this.xml;
  }

  protected void fGenerateTHEMEELEMENTS() {
    this.xml = "<" + cCreateTheme1.NAMESPACE + ":themeElements>__GENERATETHEMEELEMENTS1____GENERATETHEMEELEMENTS2____GENERATETHEMEELEMENTS3__</" + cCreateTheme1.NAMESPACE + ":themeElements>__GENERATETHEMEELEMENTS4__";
  }

  protected void fGenerateCLRSCHEME(String name) {
    if ("".equals(name)) {
      name = "Office";
    }
    this.xml = this.xml.replace("__GENERATETHEMEELEMENTS1__", "<" + cCreateTheme1.NAMESPACE + ":clrScheme name='" + name + "'>__GENERATECLRSCHEME__</" + cCreateTheme1.NAMESPACE + ":clrScheme>");
  }

  protected void fGenerateDK1() {
    this.xml = this.xml.replace("__GENERATECLRSCHEME__", "<" + cCreateTheme1.NAMESPACE + ":dk1>__GENERATESYSCLR__</" + cCreateTheme1.NAMESPACE + ":dk1>__GENERATEDK1__");
  }

  protected void fGenerateSYSCLR(String val, String lastClr) {
    if ("".equals(val)) {
      val = "windowText";
    }
    if ("".equals(lastClr)) {
      lastClr = "000000";
    }
    this.xml = this.xml.replace("__GENERATESYSCLR__", "<" + cCreateTheme1.NAMESPACE + ":sysClr val='" + val + "' lastClr='" + lastClr + "'></" + cCreateTheme1.NAMESPACE + ":sysClr>");
  }

  protected void fGenerateLT1() {
    this.xml = this.xml.replace("__GENERATEDK1__", "<" + cCreateTheme1.NAMESPACE + ":lt1>__GENERATESYSCLR__</" + cCreateTheme1.NAMESPACE + ":lt1>__GENERATELT1__");
  }

  protected void fGenerateDK2() {
    this.xml = this.xml.replace("__GENERATELT1__", "<" + cCreateTheme1.NAMESPACE + ":dk2>__GENERATESRGBCLR__</" + cCreateTheme1.NAMESPACE + ":dk2>__GENERATEDK2__");
  }

  protected void fGenerateSRGBCLR(String val) {
    if ("".equals(val)) {
      val = "windowText";
    }
    this.xml = this.xml.replace("__GENERATESRGBCLR__", "<" + cCreateTheme1.NAMESPACE + ":srgbClr val='" + val + "'></" + cCreateTheme1.NAMESPACE + ":srgbClr>");
  }

  protected void fGenerateLT2() {
    this.xml = this.xml.replace("__GENERATEDK2__", "<" + cCreateTheme1.NAMESPACE + ":lt2>__GENERATESRGBCLR__</" + cCreateTheme1.NAMESPACE + ":lt2>__GENERATELT2__");
  }

  protected void fGenerateACCENT1() {
    this.xml = this.xml.replace("__GENERATELT2__", "<" + cCreateTheme1.NAMESPACE + ":accent1>__GENERATESRGBCLR__</" + cCreateTheme1.NAMESPACE + ":accent1>__GENERATEACCENT1__");
  }

  protected void fGenerateACCENT2() {
    this.xml = this.xml.replace("__GENERATEACCENT1__", "<" + cCreateTheme1.NAMESPACE + ":accent2>__GENERATESRGBCLR__</" + cCreateTheme1.NAMESPACE + ":accent2>__GENERATEACCENT2__");
  }

  protected void fGenerateACCENT3() {
    this.xml = this.xml.replace("__GENERATEACCENT2__", "<" + cCreateTheme1.NAMESPACE + ":accent3>__GENERATESRGBCLR__</" + cCreateTheme1.NAMESPACE + ":accent3>__GENERATEACCENT3__");
  }

  protected void fGenerateACCENT4() {
    this.xml = this.xml.replace("__GENERATEACCENT3__", "<" + cCreateTheme1.NAMESPACE + ":accent4>__GENERATESRGBCLR__</" + cCreateTheme1.NAMESPACE + ":accent4>__GENERATEACCENT4__");
  }

  protected void fGenerateACCENT5() {
    this.xml = this.xml.replace("__GENERATEACCENT4__", "<" + cCreateTheme1.NAMESPACE + ":accent5>__GENERATESRGBCLR__</" + cCreateTheme1.NAMESPACE + ":accent5>__GENERATEACCENT5__");
  }

  protected void fGenerateACCENT6() {
    this.xml = this.xml.replace("__GENERATEACCENT5__", "<" + cCreateTheme1.NAMESPACE + ":accent6>__GENERATESRGBCLR__</" + cCreateTheme1.NAMESPACE + ":accent6>__GENERATEACCENT6__");
  }

  protected void fGenerateHLINK() {
    this.xml = this.xml.replace("__GENERATEACCENT6__", "<" + cCreateTheme1.NAMESPACE + ":hlink>__GENERATESRGBCLR__</" + cCreateTheme1.NAMESPACE + ":hlink>__GENERATEHLINK__");
  }

  protected void fGenerateFOLHLINK() {
    this.xml = this.xml.replace("__GENERATEHLINK__", "<" + cCreateTheme1.NAMESPACE + ":folHlink>__GENERATESRGBCLR__</" + cCreateTheme1.NAMESPACE + ":folHlink>");
  }

  protected void fGenerateFONTSCHEMA(String name) {
    if ("".equals(name)) {
      name = "Office";
    }
    this.xml = this.xml.replace("__GENERATETHEMEELEMENTS2__", "<" + cCreateTheme1.NAMESPACE + ":fontScheme name='" + name + "'>__GENERATEFONTSCHEMA1____GENERATEFONTSCHEMA2__</" + cCreateTheme1.NAMESPACE + ":fontScheme>");
  }

  protected void fGenerateMAYORFONT() {
    this.xml = this.xml.replace("__GENERATEFONTSCHEMA1__", "<" + cCreateTheme1.NAMESPACE + ":majorFont>__GENERATEMAYORFONT__</" + cCreateTheme1.NAMESPACE + ":majorFont>");
  }

  protected void fGenerateLATIN(String typeface) {
    if ("".equals(typeface)) {
      typeface = "cambia";
    }
    this.xml = this.xml.replace("__GENERATEMAYORFONT__", "<" + cCreateTheme1.NAMESPACE + ":latin typeface='" + typeface + "'></" + cCreateTheme1.NAMESPACE + ":latin>__GENERATELATIN__");
  }

  protected void fGenerateEA(String typeface) {
    this.xml = this.xml.replace("__GENERATELATIN__", "<" + cCreateTheme1.NAMESPACE + ":ea typeface='" + typeface + "'></" + cCreateTheme1.NAMESPACE + ":ea>__GENERATEEA__");
  }

  protected void fGenerateCS(String typeface) {
    this.xml = this.xml.replace("__GENERATEEA__", "<" + cCreateTheme1.NAMESPACE + ":cs typeface='" + typeface + "'></" + cCreateTheme1.NAMESPACE + ":cs>__GENERATECS__");
  }

  protected void fGenerateFONT(String script, String typeface) {
    this.xml = this.xml.replace("__GENERATECS__", "<" + cCreateTheme1.NAMESPACE + ":font script='" + script + "' typeface='" + typeface + "'></" + cCreateTheme1.NAMESPACE + ":font>__GENERATEFONT__");
  }

  protected void fGenerateFONT2(String script, String typeface) {
    this.xml = this.xml.replace("__GENERATEFONT__", "<" + cCreateTheme1.NAMESPACE + ":font script='" + script + "' typeface='" + typeface + "'></" + cCreateTheme1.NAMESPACE + ":font>__GENERATEFONT__");
  }

  protected void fGenerateFONT3(String script, String typeface) {
    this.xml = this.xml.replace("__GENERATEFONT__", "<" + cCreateTheme1.NAMESPACE + ":font script='" + script + "' typeface='" + typeface + "'></" + cCreateTheme1.NAMESPACE + ":font>");
  }

  protected void fGenerateMINORFONT() {
    this.xml = this.xml.replace("__GENERATEFONTSCHEMA2__", "<" + cCreateTheme1.NAMESPACE + ":minorFont>__GENERATEMAYORFONT__</" + cCreateTheme1.NAMESPACE + ":minorFont>");
  }

  protected void fGenerateFMTSCHEME(String name) {
    this.xml = this.xml.replace("__GENERATETHEMEELEMENTS3__", "<" + cCreateTheme1.NAMESPACE + ":fmtScheme name='" + name + "'>__GENERATEFMTSCHEME__</" + cCreateTheme1.NAMESPACE + ":fmtScheme>");
  }

  protected void fGenerateFILLSTYLELST() {
    this.xml = this.xml.replace("__GENERATEFMTSCHEME__", "<" + cCreateTheme1.NAMESPACE + ":fillStyleLst>__GENERATEFILLSTYLELST__</" + cCreateTheme1.NAMESPACE + ":fillStyleLst>__GENERATEFILLSTYLELST2__");
  }

   
  protected void fGenerateSOLIDFILL() {
    this.xml = this.xml.replace("__GENERATEFILLSTYLELST__", "<" + cCreateTheme1.NAMESPACE + ":solidFill>__GENERATESOLIDFILL__</" + cCreateTheme1.NAMESPACE + ":solidFill>__GENERATESOLIDFILL2__");
  }

   
  protected void fGenerateSCHEMECLR(String val) {
    this.xml = this.xml.replace("__GENERATESOLIDFILL__", "<" + cCreateTheme1.NAMESPACE + ":schemeClr val='" + val + "'></" + cCreateTheme1.NAMESPACE + ":schemeClr>");
  }

  protected void fGenerateGRADFILL(String rotWithShape) {
    this.xml = this.xml.replace("__GENERATESOLIDFILL2__", "<" + cCreateTheme1.NAMESPACE + ":gradFill rotWithShape='" + rotWithShape + "'>__GENERATEGRADFILL__</" + cCreateTheme1.NAMESPACE + ":gradFill>__GENERATESOLIDFILL2__");
  }

  protected void fGenerateGRADFILL2(String rotWithShape) {
    this.xml = this.xml.replace("__GENERATESOLIDFILL2__", "<" + cCreateTheme1.NAMESPACE + ":gradFill rotWithShape='" + rotWithShape + "'>__GENERATEGRADFILL__</" + cCreateTheme1.NAMESPACE + ":gradFill>");
  }

  protected void fGenerateGSLST() {
    this.xml = this.xml.replace("__GENERATEGRADFILL__", "<" + cCreateTheme1.NAMESPACE + ":gsLst>__GENERATEGSLST__</" + cCreateTheme1.NAMESPACE + ":gsLst>__GENERATEGSLST2__");
  }

  protected void fGenerateGS(String pos) {
    this.xml = this.xml.replace("__GENERATEGSLST__", "<" + cCreateTheme1.NAMESPACE + ":gs pos='" + pos + "'>__GENERATEPOS__</" + cCreateTheme1.NAMESPACE + ":gs>__GENERATEGSLST__");
  }

  protected void fGenerateGS2(String pos) {
    this.xml = this.xml.replace("__GENERATEGSLST__", "<" + cCreateTheme1.NAMESPACE + ":gs pos='" + pos + "'>__GENERATEPOS__</" + cCreateTheme1.NAMESPACE + ":gs>");
  }

  protected void fGenerateSCHEMECLR2(String val) {
    this.xml = this.xml.replace("__GENERATEPOS__", "<" + cCreateTheme1.NAMESPACE + ":schemeClr val='" + val + "'>__GENERATESCHEMECLR2__</" + cCreateTheme1.NAMESPACE + ":schemeClr>");
  }

  protected void fGenerateTINT(String val) {
    this.xml = this.xml.replace("__GENERATESCHEMECLR2__", "<" + cCreateTheme1.NAMESPACE + ":tint val='" + val + "'></" + cCreateTheme1.NAMESPACE + ":tint>__GENERATETINT__");
  }

  protected void fGenerateSHADE(String val) {
    this.xml = this.xml.replace("__GENERATESCHEMECLR2__", "<" + cCreateTheme1.NAMESPACE + ":shade val='" + val + "'></" + cCreateTheme1.NAMESPACE + ":shade>__GENERATETINT__");
  }

  protected void fGenerateSATMOD(String val) {
    this.xml = this.xml.replace("__GENERATETINT__", "<" + cCreateTheme1.NAMESPACE + ":satMod val='" + val + "'></" + cCreateTheme1.NAMESPACE + ":satMod>");
  }

  protected void fGenerateLIN(String ang, String scaled) {
    this.xml = this.xml.replace("__GENERATEGSLST2__", "<" + cCreateTheme1.NAMESPACE + ":lin ang='" + ang + "' scaled='" + scaled + "'></" + cCreateTheme1.NAMESPACE + ":lin>");
  }

  protected void fGenerateLNSTYLELST() {
    this.xml = this.xml.replace("__GENERATEFILLSTYLELST2__", "<" + cCreateTheme1.NAMESPACE + ":lnStyleLst>__GENERATELNSTYLELST__</" + cCreateTheme1.NAMESPACE + ":lnStyleLst>__GENERATELNSTYLELST2__");
  }

  protected void fGenerateLN(String w, String cap, String cmpd, String algn) {
    this.xml = this.xml.replace("__GENERATELNSTYLELST__", "<" + cCreateTheme1.NAMESPACE + ":ln w='" + w + "' cap='" + cap + "' cmpd='" + cmpd + "' algn='" + algn + "'>__GENERATEFILLSTYLELST__</" + cCreateTheme1.NAMESPACE + ":ln>__GENERATELNSTYLELST__");
  }

  protected void fGenerateLN2(String w, String cap, String cmpd, String algn) {
    this.xml = this.xml.replace("__GENERATELNSTYLELST__", "<" + cCreateTheme1.NAMESPACE + ":ln w='" + w + "' cap='" + cap + "' cmpd='" + cmpd + "' algn='" + algn + "'>__GENERATEFILLSTYLELST__</" + cCreateTheme1.NAMESPACE + ":ln>");
  }

  protected void fGenerateSCHEMECLR3(String val) {
    this.xml = this.xml.replace("__GENERATESOLIDFILL__", "<" + cCreateTheme1.NAMESPACE + ":schemeClr val='" + val + "'>__GENERATESCHEMECLR2__</" + cCreateTheme1.NAMESPACE + ":schemeClr>");
  }

   
  protected void fGeneratePRSTDASH(String val) {
    this.xml = this.xml.replace("__GENERATESOLIDFILL2__", "<" + cCreateTheme1.NAMESPACE + ":prstDash val='" + val + "'></" + cCreateTheme1.NAMESPACE + ":prstDash>");
  }

  protected void fGenerateEFFECTSTYLELST() {
    this.xml = this.xml.replace("__GENERATELNSTYLELST2__", "<" + cCreateTheme1.NAMESPACE + ":effectStyleLst>__GENERATEEFFECTSTYLELST__</" + cCreateTheme1.NAMESPACE + ":effectStyleLst>__GENERATEEFFECTSTYLELST2__");
  }

  protected void fGenerateEFFECTSTYLE() {
    this.xml = this.xml.replace("__GENERATEEFFECTSTYLELST__", "<" + cCreateTheme1.NAMESPACE + ":effectStyle>__GENERATEEFFECTSTYLE__</" + cCreateTheme1.NAMESPACE + ":effectStyle>__GENERATEEFFECTSTYLELST__");
  }

  protected void fGenerateEFFECTSTYLE2() {
    this.xml = this.xml.replace("__GENERATEEFFECTSTYLELST__", "<" + cCreateTheme1.NAMESPACE + ":effectStyle>__GENERATEEFFECTSTYLE__</" + cCreateTheme1.NAMESPACE + ":effectStyle>");
  }

  protected void fGenerateEFFECTLST() {
    this.xml = this.xml.replace("__GENERATEEFFECTSTYLE__", "<" + cCreateTheme1.NAMESPACE + ":effectLst>__GENERATEEFFECTLST__</" + cCreateTheme1.NAMESPACE + ":effectLst>");
  }

  protected void fGenerateOUTERSHDW(String blurRad, String dist, String dir, String rotWithShape) {
    this.xml = this.xml.replace("__GENERATEEFFECTLST__", "<" + cCreateTheme1.NAMESPACE + ":outerShdw blurRad='" + blurRad + "' dist='" + dist + "' dir='" + dir + "' rotWithShape='" + rotWithShape + "'>__GENERATEOUTERSHDW__</" + cCreateTheme1.NAMESPACE + ":outerShdw>");
  }

  protected void fGenerateSRGBCLR2(String val) {
    this.xml = this.xml.replace("__GENERATEOUTERSHDW__", "<" + cCreateTheme1.NAMESPACE + ":srgbClr val='" + val + "'>__GENERATESRGBCLR__</" + cCreateTheme1.NAMESPACE + ":srgbClr>");
  }

  protected void fGenerateALPHA(String val) {
    this.xml = this.xml.replace("__GENERATESRGBCLR__", "<" + cCreateTheme1.NAMESPACE + ":alpha val='" + val + "'></" + cCreateTheme1.NAMESPACE + ":alpha>");
  }

  protected void fGenerateEFFECTLST2() {
    this.xml = this.xml.replace("__GENERATEEFFECTSTYLE__", "<" + cCreateTheme1.NAMESPACE + ":effectLst>__GENERATEEFFECTLST__</" + cCreateTheme1.NAMESPACE + ":effectLst>__GENERATEEFFECTLST2__");
  }

  protected void fGenerateSCENE3D() {
    this.xml = this.xml.replace("__GENERATEEFFECTLST2__", "<" + cCreateTheme1.NAMESPACE + ":scene3d>__GENERATESCENE3D__</" + cCreateTheme1.NAMESPACE + ":scene3d>__GENERATESCENE3D2__");
  }

  protected void fGenerateCAMERA(String prst) {
    this.xml = this.xml.replace("__GENERATESCENE3D__", "<" + cCreateTheme1.NAMESPACE + ":camera prst='" + prst + "'>__GENERATECAMERA__</" + cCreateTheme1.NAMESPACE + ":camera>__GENERATECAMERA2__");
  }

  protected void fGenerateROT(String lat, String lon, String rev) {
    this.xml = this.xml.replace("__GENERATECAMERA__", "<" + cCreateTheme1.NAMESPACE + ":rot lat='" + lat + "' lon='" + lon + "' rev='" + rev + "'></" + cCreateTheme1.NAMESPACE + ":rot>");
  }

  protected void fGenerateLIGHTRIG(String rig, String dir) {
    this.xml = this.xml.replace("__GENERATECAMERA2__", "<" + cCreateTheme1.NAMESPACE + ":lightRig rig='" + rig + "' dir='" + dir + "'>__GENERATECAMERA__</" + cCreateTheme1.NAMESPACE + ":lightRig>");
  }

  protected void fGenerateSP3D() {
    this.xml = this.xml.replace("__GENERATESCENE3D2__", "<" + cCreateTheme1.NAMESPACE + ":sp3d>__GENERATESP3D__</" + cCreateTheme1.NAMESPACE + ":sp3d>");
  }

  protected void fGenerateBEVELT(String w, String h) {
    this.xml = this.xml.replace("__GENERATESP3D__", "<" + cCreateTheme1.NAMESPACE + ":bevelT w='" + w + "' h='" + h + "'></" + cCreateTheme1.NAMESPACE + ":bevelT>");
  }

  protected void fGenerateBGFILLSTYLELST() {
    this.xml = this.xml.replace("__GENERATEEFFECTSTYLELST2__", "<" + cCreateTheme1.NAMESPACE + ":bgFillStyleLst>__GENERATEFILLSTYLELST__</" + cCreateTheme1.NAMESPACE + ":bgFillStyleLst>");
  }

  protected void fGenerateTINT2(String val) {
    this.xml = this.xml.replace("__GENERATESCHEMECLR2__", "<" + cCreateTheme1.NAMESPACE + ":tint val='" + val + "'></" + cCreateTheme1.NAMESPACE + ":tint>__GENERATESCHEMECLR2__");
  }

  protected void fGeneratePATH(String path) {
    this.xml = this.xml.replace("__GENERATEGSLST2__", "<" + cCreateTheme1.NAMESPACE + ":path path='" + path + "'>__GENERATEPATH__</" + cCreateTheme1.NAMESPACE + ":path>");
  }

  protected void fGenerateFILLTORECT(String l, String t, String r, String b) {
    this.xml = this.xml.replace("__GENERATEPATH__", "<" + cCreateTheme1.NAMESPACE + ":fillToRect l='" + l + "' t='" + t + "' r='" + r + "' b='" + b + "'></" + cCreateTheme1.NAMESPACE + ":fillToRect>");
  }

  protected void fGenerateOBJECTDEFAULTS() {
    this.xml = this.xml.replace("__GENERATETHEMEELEMENTS4__", "<" + cCreateTheme1.NAMESPACE + ":objectDefaults></" + cCreateTheme1.NAMESPACE + ":objectDefaults>__GENERATETHEMEELEMENTS4__");
  }

  protected void fGenerateEXTRACLRSCHEMELST() {
    this.xml = this.xml.replace("__GENERATETHEMEELEMENTS4__", "<" + cCreateTheme1.NAMESPACE + ":extraClrSchemeLst></" + cCreateTheme1.NAMESPACE + ":extraClrSchemeLst>");
  }

  public void fCreateTheme(String strFont) {
    this.xml = "";
    this.fGenerateTHEMEELEMENTS();
    this.fGenerateCLRSCHEME("Office");
    this.fGenerateDK1();
    this.fGenerateSYSCLR("windowText", "000000");
    this.fGenerateLT1();
    this.fGenerateSYSCLR("window", "FFFFFF");
    this.fGenerateDK2();
    this.fGenerateSRGBCLR("1F497D");
    this.fGenerateLT2();
    this.fGenerateSRGBCLR("EEECE1");
    this.fGenerateACCENT1();
    this.fGenerateSRGBCLR("4F81BD");
    this.fGenerateACCENT2();
    this.fGenerateSRGBCLR("C0504D");
    this.fGenerateACCENT3();
    this.fGenerateSRGBCLR("9BBB59");
    this.fGenerateACCENT4();
    this.fGenerateSRGBCLR("8064A2");
    this.fGenerateACCENT5();
    this.fGenerateSRGBCLR("4BACC6");
    this.fGenerateACCENT6();
    this.fGenerateSRGBCLR("F79646");
    this.fGenerateHLINK();
    this.fGenerateSRGBCLR("0000FF");
    this.fGenerateFOLHLINK();
    this.fGenerateSRGBCLR("800080");
    if(strFont.equals("")){
    	this.fGenerateFONTSCHEMA("Office");
        this.fGenerateMAYORFONT();
        this.fGenerateLATIN("Cambria");
        this.fGenerateEA("");
        this.fGenerateCS("");
        this.fGenerateFONT("Jpan", "Times New Roman");
        this.fGenerateFONT2("Hang", "Times New Roman");
        this.fGenerateFONT2("Hans", "Times New Roman");
        this.fGenerateFONT2("Hant", "Times New Roman");
        this.fGenerateFONT2("Arab", "Times New Roman");
        this.fGenerateFONT2("Hebr", "Times New Roman");
        this.fGenerateFONT2("Thai", "Angsana New");
        this.fGenerateFONT2("Ethi", "Nyala");
        this.fGenerateFONT2("Beng", "Vrinda");
        this.fGenerateFONT2("Gujr", "Shruti");
        this.fGenerateFONT2("Khmr", "MoolBoran");
        this.fGenerateFONT2("Knda", "Tunga");
        this.fGenerateFONT2("Guru", "Raavi");
        this.fGenerateFONT2("Cans", "Euphemia");
        this.fGenerateFONT2("Cher", "Plantagenet Cherokee");
        this.fGenerateFONT2("Yiii", "Microsoft Yi Baiti");
        this.fGenerateFONT2("Tibt", "Microsoft Himalaya");
        this.fGenerateFONT2("Thaa", "MV Boli");
        this.fGenerateFONT2("Deva", "Mangal");
        this.fGenerateFONT2("Telu", "Gautami");
        this.fGenerateFONT2("Taml", "Latha");
        this.fGenerateFONT2("Syrc", "Estrangelo Edessa");
        this.fGenerateFONT2("Orya", "Kalinga");
        this.fGenerateFONT2("Mlym", "Kartika");
        this.fGenerateFONT2("Laoo", "DokChampa");
        this.fGenerateFONT2("Sinh", "Iskoola Pota");
        this.fGenerateFONT2("Mong", "Mongolian Baiti");
        this.fGenerateFONT2("Viet", "Times New Roman");
        this.fGenerateFONT3("Uigh", "Microsoft Uighur");
        this.fGenerateMINORFONT();
        this.fGenerateLATIN("Calibri");
        this.fGenerateEA("");
        this.fGenerateCS("");
        this.fGenerateFONT("Jpan", "Arial");
        this.fGenerateFONT2("Hang", "Arial");
        this.fGenerateFONT2("Hans", "Arial");
        this.fGenerateFONT2("Hant", "Arial");
        this.fGenerateFONT2("Arab", "Arial");
        this.fGenerateFONT2("Hebr", "Arial");
        this.fGenerateFONT2("Thai", "Cordia New");
        this.fGenerateFONT2("Ethi", "Nyala");
        this.fGenerateFONT2("Beng", "Vrinda");
        this.fGenerateFONT2("Gujr", "Shruti");
        this.fGenerateFONT2("Khmr", "DaunPenh");
        this.fGenerateFONT2("Knda", "Tunga");
        this.fGenerateFONT2("Guru", "Raavi");
        this.fGenerateFONT2("Cans", "Euphemia");
        this.fGenerateFONT2("Cher", "Plantagenet Cherokee");
        this.fGenerateFONT2("Yiii", "Microsoft Yi Baiti");
        this.fGenerateFONT2("Tibt", "Microsoft Himalaya");
        this.fGenerateFONT2("Thaa", "MV Boli");
        this.fGenerateFONT2("Deva", "Mangal");
        this.fGenerateFONT2("Telu", "Gautami");
        this.fGenerateFONT2("Taml", "Latha");
        this.fGenerateFONT2("Syrc", "Estrangelo Edessa");
        this.fGenerateFONT2("Orya", "Kalinga");
        this.fGenerateFONT2("Mlym", "Kartika");
        this.fGenerateFONT2("Laoo", "DokChampa");
        this.fGenerateFONT2("Sinh", "Iskoola Pota");
        this.fGenerateFONT2("Mong", "Mongolian Baiti");
        this.fGenerateFONT2("Viet", "Arial");
        this.fGenerateFONT3("Uigh", "Microsoft Uighur");    	
    }else{
    	this.fGenerateFONTSCHEMA("Office");
    	this.fGenerateMAYORFONT();
    	this.fGenerateLATIN(strFont);
        this.fGenerateEA("");
        this.fGenerateCS("");
    	this.fGenerateMINORFONT();
    	this.fGenerateLATIN(strFont);
    	this.fGenerateEA("");
    	this.fGenerateCS("");    	
    }
    this.fGenerateFMTSCHEME("Office");
    this.fGenerateFILLSTYLELST();
    this.fGenerateSOLIDFILL();
    this.fGenerateSCHEMECLR("phClr");
    this.fGenerateGRADFILL("1");
    this.fGenerateGSLST();
    this.fGenerateGS("0");
    this.fGenerateSCHEMECLR2("phClr");
    this.fGenerateTINT("50000");
    this.fGenerateSATMOD("300000");
    this.fGenerateGS("35000");
    this.fGenerateSCHEMECLR2("phClr");
    this.fGenerateTINT("37000");
    this.fGenerateSATMOD("300000");
    this.fGenerateGS2("100000");
    this.fGenerateSCHEMECLR2("phClr");
    this.fGenerateTINT("15000");
    this.fGenerateSATMOD("350000");
    this.fGenerateLIN("16200000", "1");
    this.fGenerateGRADFILL2("1");
    this.fGenerateGSLST();
    this.fGenerateGS("0");
    this.fGenerateSCHEMECLR2("phClr");
    this.fGenerateSHADE("51000");
    this.fGenerateSATMOD("130000");
    this.fGenerateGS("80000");
    this.fGenerateSCHEMECLR2("phClr");
    this.fGenerateSHADE("93000");
    this.fGenerateSATMOD("130000");
    this.fGenerateGS2("100000");
    this.fGenerateSCHEMECLR2("phClr");
    this.fGenerateSHADE("94000");
    this.fGenerateSATMOD("135000");
    this.fGenerateLIN("16200000", "0");
    this.fGenerateLNSTYLELST();
    this.fGenerateLN("9525", "flat", "sng", "ctr");
    this.fGenerateSOLIDFILL();
    this.fGenerateSCHEMECLR3("phClr");
    this.fGenerateSHADE("95000");
    this.fGenerateSATMOD("105000");
    this.fGeneratePRSTDASH("solid");
    this.fGenerateLN("25400", "flat", "sng", "ctr");
    this.fGenerateSOLIDFILL();
    this.fGenerateSCHEMECLR("phClr");
    this.fGeneratePRSTDASH("solid");
    this.fGenerateLN2("38100", "flat", "sng", "ctr");
    this.fGenerateSOLIDFILL();
    this.fGenerateSCHEMECLR("phClr");
    this.fGeneratePRSTDASH("solid");
    this.fGenerateEFFECTSTYLELST();
    this.fGenerateEFFECTSTYLE();
    this.fGenerateEFFECTLST();
    this.fGenerateOUTERSHDW("40000", "20000", "5400000", "0");
    this.fGenerateSRGBCLR2("000000");
    this.fGenerateALPHA("38000");
    this.fGenerateEFFECTSTYLE();
    this.fGenerateEFFECTLST();
    this.fGenerateOUTERSHDW("40000", "23000", "5400000", "0");
    this.fGenerateSRGBCLR2("000000");
    this.fGenerateALPHA("35000");
    this.fGenerateEFFECTSTYLE2();
    this.fGenerateEFFECTLST2();
    this.fGenerateOUTERSHDW("40000", "23000", "5400000", "0");
    this.fGenerateSRGBCLR2("000000");
    this.fGenerateALPHA("35000");
    this.fGenerateSCENE3D();
    this.fGenerateCAMERA("orthographicFront");
    this.fGenerateROT("0", "0", "0");
    this.fGenerateLIGHTRIG("threePt", "t");
    this.fGenerateROT("0", "0", "1200000");
    this.fGenerateSP3D();
    this.fGenerateBEVELT("63500", "25400");
    this.fGenerateBGFILLSTYLELST();
    this.fGenerateSOLIDFILL();
    this.fGenerateSCHEMECLR("phClr");
    this.fGenerateGRADFILL("1");
    this.fGenerateGSLST();
    this.fGenerateGS("0");
    this.fGenerateSCHEMECLR2("phClr");
    this.fGenerateTINT("40000");
    this.fGenerateSATMOD("350000");
    this.fGenerateGS("40000");
    this.fGenerateSCHEMECLR2("phClr");
    this.fGenerateTINT2("45000");
    this.fGenerateSHADE("99000");
    this.fGenerateSATMOD("350000");
    this.fGenerateGS2("100000");
    this.fGenerateSCHEMECLR2("phClr");
    this.fGenerateSHADE("20000");
    this.fGenerateSATMOD("255000");
    this.fGeneratePATH("circle");
    this.fGenerateFILLTORECT("50000", "-80000", "50000", "180000");
    this.fGenerateGRADFILL2("1");
    this.fGenerateGSLST();
    this.fGenerateGS("0");
    this.fGenerateSCHEMECLR2("phClr");
    this.fGenerateTINT("80000");
    this.fGenerateSATMOD("300000");
    this.fGenerateGS2("100000");
    this.fGenerateSCHEMECLR2("phClr");
    this.fGenerateSHADE("30000");
    this.fGenerateSATMOD("200000");
    this.fGeneratePATH("circle");
    this.fGenerateFILLTORECT("50000", "50000", "50000", "50000");
    this.fGenerateOBJECTDEFAULTS();
    this.fGenerateEXTRACLRSCHEMELST();
    this.fCleanTemplate();
    
  }
}
