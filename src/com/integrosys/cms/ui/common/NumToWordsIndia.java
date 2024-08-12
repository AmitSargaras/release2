package com.integrosys.cms.ui.common;



public class NumToWordsIndia   {
	
	String rupee=null;
	
	String paise=null;
	
	
	
	
	public NumToWordsIndia(String number){
		if(!number.contains(".")){
			number=number+".00";
			this.rupee  = number.substring(0, number.indexOf('.'));
		}else{
	
		this. paise = number.substring(number.indexOf('.') + 1);
		
		this.rupee  = number.substring(0, number.indexOf('.')); 
		}// get rupees
		
	}

	

	/**
	 * @param myValue
	 * @return 
	 */
	protected String formatNumber(String myvalue) {
		String fin = myvalue ;
		String finArr1 = "";
		String finArr2 = "";

		if (fin.indexOf('.') != -1){
			finArr1 =  fin.substring(0,fin.indexOf('.'));
			finArr2 =  fin.substring(fin.indexOf('.')+1) ;
			if (finArr2.length()==1){
				finArr2 = finArr2 +"0";
			}
		}else{
			finArr1 = fin;
		}

		if(finArr1.length()==1){
			fin="00"+finArr1;
		}else{
			if(finArr1.length()==0){
				finArr1="00";
			}
			fin = ( finArr1.length() % 2 ==1) ? finArr1 : ("0"+finArr1) ;
		}
		
		fin = fin + ( ( finArr2 != "" ) ? ("."+ finArr2.substring(0,2) ) : ".00" ) ;
		return fin;
	}

	
	/**
	 * @param number
	 * @return 
	 */
	protected String getVerbose(String number)
	{
		switch(Integer.parseInt(number)) 
		{
		case 1 : return "One" ;
		case 2 : return "Two" ;
		case 3 : return "Three" ;
		case 4 : return "Four" ;
		case 5 : return "Five" ;
		case 6 : return "Six" ;
		case 7 : return "Seven" ;
		case 8 : return "Eight" ;
		case 9 : return "Nine" ;
		case 10 : return "Ten" ;
		case 11 : return "Eleven" ;
		case 12 : return "Twelve" ;
		case 13 : return "Thirteen" ;
		case 14 : return "Fourteen" ;
		case 15 : return "Fifteen" ;
		case 16 : return "Sixteen" ;
		case 17 : return "Seventeen";
		case 18 : return "Eighteen" ;
		case 19 : return "Nineteen" ;
		case 20 : return "Twenty" ;
		case 30 : return "Thirty" ;
		case 40: return "Forty" ;
		case 50 : return "Fifty" ;
		case 60 : return "Sixty" ;
		case 70 : return "Seventy" ;
		case 80 : return "Eighty" ;
		case 90 : return "Ninety" ;
		default : return "" ;
		}
	}

	
	/**
	 * @param tensip
	 * @return 
	 */
	protected String RdTwoDigit(String tensip)
	{
		tensip=tensip.trim();
		if(tensip.equals(""))
			return tensip;

		int tens = Integer.parseInt(tensip);
		String output ="";

		if (tens !=0){
			if ( tens > 20 && tens < 100 ){
				output =  getVerbose(  (tens - tens % 10) +"" );
				output = output+" "+getVerbose( ( tens % 10 ) +"" );
			}else if ( tens > 0 && tens <=20 ){
				output = getVerbose( tens +"" );
			}
		}
		return output;
	}
	
	public String getWords(String number,
			String[] currencyDetailsForPaymentRequest,
			boolean suffixCurrencyDetails) {
		if(number.length()==0){
			return"";
		}
		String c = "" + number;
		String rsnum = "", paisenum = "";
		String output = "";

		if (c.indexOf('.') != -1) {
			paisenum = c.substring(c.indexOf('.') + 1); // get paise
			rsnum = c.substring(0, c.indexOf('.')); // get rupees
			c = rsnum + "." + paisenum;
		}

		if (c != "") {
			Double d = new Double(c);

			if (!d.isNaN()) {
				if (d.doubleValue() != 0.00) {
					String myvalue = formatNumber(c);
					String rupees = "";
					int ruplen = 0;

					int length = 0;
					int hund = 0;
					int tens = 0;
					if (d >= 1) {
						rupees = myvalue.substring(0, myvalue.indexOf('.'));
						ruplen = rupees.length();
						hund = Integer.parseInt(rupees.charAt(ruplen - 3) + "");
						tens = Integer.parseInt(rupees.substring(ruplen - 2));
					}

					String paise = myvalue.substring(myvalue.indexOf('.') + 1);
					length = myvalue.length();

					if (hund >= 1 && hund <= 9)
						output = output + " " + getVerbose(hund + "")
								+ " Hundred ";
					output = output + RdTwoDigit(tens + "");

					int count = 3;
					String temp = "";
					while (count < ruplen) {
						switch (count) {
						case 3:
							temp = RdTwoDigit(rupees.substring(ruplen - 5,
									ruplen - 3));
							output = ((temp != "") ? (temp + " Thousand") : "")
									+ output;
							break;
						case 5:
							temp = RdTwoDigit(rupees.substring(ruplen - 7,
									ruplen - 5));
							String lacs;
							if (temp == "One")
								lacs = " Lac ";
							else
								lacs = " Lacs ";
							output = ((temp != "") ? (temp + lacs) : "")
									+ output;
							break;
						case 7:
							String croresAsString = rupees.substring(0,
									ruplen - 7);
							Integer croresAsInteger = Integer
									.parseInt(croresAsString);
							if (croresAsInteger > 0) {
								output = getWords(
										rupees.substring(0, ruplen - 7),
										currencyDetailsForPaymentRequest, false)
										+ " Crores " + output;
								count = length;
							}
							break;
						}
						count = count + 2;
						temp = "";
					}

				}

			}
		}
		return output;
	}

/*	public static void main(String args[]) {
		
		NumToWordsIndia converter = new NumToWordsIndia("333333333.33");
		
		System.out.println(converter.getWords(converter.rupee, null, false)+" Rupees and "+converter.getWords(converter.paise, null, false)+" paise" );
	}*/
	
	

}
