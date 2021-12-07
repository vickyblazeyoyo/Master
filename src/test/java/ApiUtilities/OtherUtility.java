package ApiUtilities;

import Pages.Constant;
import Utilities.Utilities;

public class OtherUtility {

	
	public static  void updateTestdata(String key) throws Throwable {
		String date;
		if (Constant.Api_Testdata.get(key).contains("Today(")) {
			String dateformat = Constant.Api_Testdata.get(key).trim().substring(Constant.Api_Testdata.get(key).indexOf('(')+1,Constant.Api_Testdata.get(key).indexOf(')'));
            if (Constant.Api_Testdata.get(key).startsWith("Today(") && Constant.Api_Testdata.get(key).endsWith(")")) {
	        date=Utilities.getCurrentCSTDateWithArgFormat(dateformat, 0);
	        Constant.Api_Testdata.put(key, date);
            }else {
					String days = Constant.Api_Testdata.get(key).toString().trim().substring(Constant.Api_Testdata.get(key).indexOf(')')+1, Constant.Api_Testdata.get(key).length());
					date = Utilities.getCurrentCSTDateWithArgFormat(dateformat,Integer.parseInt(days));
					  Constant.Api_Testdata.put(key, date);
			}
		}else if (Constant.Api_Testdata.get(key).contains("CurMonth()")) {
			Constant.Api_Testdata.put(key, Utilities.getCSTSpecificComponentInDate("MM").trim());
			
		}else if (Constant.Api_Testdata.get(key).contains("CurYear()")) {
			Constant.Api_Testdata.put(key, Utilities.getCSTSpecificComponentInDate("yyyy").trim());
		}else if (Constant.Api_Testdata.get(key).contains("CurDay()")) {
			Constant.Api_Testdata.put(key, Utilities.getCSTSpecificComponentInDate("dd").trim());
		}else if (Constant.Api_Testdata.get(key).contains("TimeNow()")) {
			Constant.Api_Testdata.put(key, Utilities.getTellerTimeStamp().trim());		
		}else if (Constant.Api_Testdata.get(key).contains("5digitrandNum#")){
			String fivedigrepl = Utilities.fiveDigitRandomNum().trim();
			Constant.Api_Testdata.put(key, fivedigrepl);
		}else if (Constant.Api_Testdata.get(key).contains("3digitrandNum#")){
			String fivedigrepl = Utilities.threeDigitRandomNum().trim();
			Constant.Api_Testdata.put(key, fivedigrepl);
		} else if (Constant.Api_Testdata.get(key).contains("9digitrandNum#")){
			String fivedigrepl = Utilities.get9Digit().trim();
			Constant.Api_Testdata.put(key, fivedigrepl);
		} else if (Constant.Api_Testdata.get(key).contains("ranSSN#")){
			String fivedigrepl = Utilities.genSSN().trim();
			Constant.Api_Testdata.put(key, fivedigrepl);
		} else if (Constant.Api_Testdata.get(key).contains("10digitrandNum#")){
			String tendigitnum = Utilities.get10Digit().trim();
			Constant.Api_Testdata.put(key, tendigitnum);
		} 
		
		
		
		
		
		
		
		
	}
}
