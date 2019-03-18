package com.xha.mangotv.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.text.TextUtils;

/**
 * 对一字符串进行验证，包括�? 身份证号，手机号，银行卡，是否数字�?�中文，邮箱�?
 * 
 * 以及判断是否是数字，还有dp转px，px转dp�?
 * 
 * @author ls
 * 
 */
public class NumYzUtils {

	/**
	 * 判断是不是手机号�?
	 * 
	 * @author ls
	 **/

	public static boolean isPhoneNumberValid(String phoneNumber) {
		/*
		 * 移动�?134�?135�?136�?137�?138�?139�?150�?151�?157(TD)�?158�?159�?187�?188
		 * 新加�?147�?170（虚拟运营商）�??178�?182�?183�?184 联�?�：130�?131�?132�?152�?155�?156�?185�?186
		 * 新加�?145�?176 电信�?133�?153�?180�?189、（1349卫�?�） 新加�?177�?181
		 * 总结起来就是第一位必定为1，第二位必定�?3�?5�?8�?7,4，其他位置的可以�?0-9 用正则表达式进行判断
		 */
		String telRegex = "[1][34578]\\d{9}";// "[1]"代表�?1位为数字1�?"[358]"代表第二位可以为3�?5�?8中的�?个，"\\d{9}"代表后面是可以是0�?9的数字，�?9位�??
		if (TextUtils.isEmpty(phoneNumber))
			return false;
		else
			return phoneNumber.matches(telRegex);
	}

	/**
	 * 功能：身份证的有效验�?
	 * 
	 * @author ls
	 * @param IDStr
	 * @return 有效：返�?"" 无效：返回String信息
	 * @throws ParseException
	 */
	public static boolean IDCardValidate(String IDStr) {
		String errorInfo = "";// 记录错误信息
		String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4",
				"3", "2" };
		String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
				"9", "10", "5", "8", "4", "2" };
		String Ai = "";
		// ================ 号码的长�? 15位或18�? ================
		if (IDStr.length() != 15 && IDStr.length() != 18) {
			errorInfo = "身份证号码长度应该为15位或18位�??";
			return false;
		}
		// =======================(end)========================

		// ================ 数字 除最后以为都为数�? ================
		if (IDStr.length() == 18) {
			Ai = IDStr.substring(0, 17);
		} else if (IDStr.length() == 15) {
			Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
		}
		if (isNumeric(Ai) == false) {
			errorInfo = "身份�?15位号码都应为数字 ; 18位号码除�?后一位外，都应为数字�?";
			return false;
		}
		// =======================(end)========================

		// ================ 出生年月是否有效 ================
		String strYear = Ai.substring(6, 10);// 年份
		String strMonth = Ai.substring(10, 12);// 月份
		String strDay = Ai.substring(12, 14);// 月份
		if (isDataFormat(strYear + "-" + strMonth + "-" + strDay) == false) {
			errorInfo = "身份证生日无效�??";
			return false;
		}
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
					|| (gc.getTime().getTime() - s.parse(
							strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
				errorInfo = "身份证生日不在有效范围�??";
				return false;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
			errorInfo = "身份证月份无�?";
			return false;
		}
		if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
			errorInfo = "身份证日期无�?";
			return false;
		}
		// =====================(end)=====================

		// ================ 地区码时候有�? ================
		Hashtable h = GetAreaCode();
		if (h.get(Ai.substring(0, 2)) == null) {
			errorInfo = "身份证地区编码错误�??";
			return false;
		}
		// ==============================================

		// ================ 判断�?后一位的�? ================
		int TotalmulAiWi = 0;
		for (int i = 0; i < 17; i++) {
			TotalmulAiWi = TotalmulAiWi
					+ Integer.parseInt(String.valueOf(Ai.charAt(i)))
					* Integer.parseInt(Wi[i]);
		}
		int modValue = TotalmulAiWi % 11;
		String strVerifyCode = ValCodeArr[modValue];
		Ai = Ai + strVerifyCode;

		if (IDStr.length() == 18) {
			if (Ai.equals(IDStr) == false) {
				errorInfo = "身份证无效，不是合法的身份证号码";
				return false;
			}
		} else {
			return true;
		}
		// =====================(end)=====================
		return true;
	}

	/**
	 * 功能：判断字符串是否为数�?
	 * 
	 * @param str
	 * @return
	 */
	private static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (isNum.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 功能：设置地区编�?
	 * 
	 * @return Hashtable 对象
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Hashtable GetAreaCode() {
		Hashtable hashtable = new Hashtable();
		hashtable.put("11", "北京");
		hashtable.put("12", "天津");
		hashtable.put("13", "河北");
		hashtable.put("14", "山西");
		hashtable.put("15", "内蒙�?");
		hashtable.put("21", "辽宁");
		hashtable.put("22", "吉林");
		hashtable.put("23", "黑龙�?");
		hashtable.put("31", "上海");
		hashtable.put("32", "江苏");
		hashtable.put("33", "浙江");
		hashtable.put("34", "安徽");
		hashtable.put("35", "福建");
		hashtable.put("36", "江西");
		hashtable.put("37", "山东");
		hashtable.put("41", "河南");
		hashtable.put("42", "湖北");
		hashtable.put("43", "湖南");
		hashtable.put("44", "广东");
		hashtable.put("45", "广西");
		hashtable.put("46", "海南");
		hashtable.put("50", "重庆");
		hashtable.put("51", "四川");
		hashtable.put("52", "贵州");
		hashtable.put("53", "云南");
		hashtable.put("54", "西藏");
		hashtable.put("61", "陕西");
		hashtable.put("62", "甘肃");
		hashtable.put("63", "青海");
		hashtable.put("64", "宁夏");
		hashtable.put("65", "新疆");
		hashtable.put("71", "台湾");
		hashtable.put("81", "香港");
		hashtable.put("82", "澳门");
		hashtable.put("91", "国外");
		return hashtable;
	}

	/**
	 * 验证日期字符串是否是YYYY-MM-DD格式
	 * 
	 * @param str
	 * @return
	 */
	private static boolean isDataFormat(String str) {
		boolean flag = false;
		// String
		// regxStr="[1-9][0-9]{3}-[0-1][0-2]-((0[1-9])|([12][0-9])|(3[01]))";
		String regxStr = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
		Pattern pattern1 = Pattern.compile(regxStr);
		Matcher isNo = pattern1.matcher(str);
		if (isNo.matches()) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 功能：是否是邮箱.
	 * 
	 * @author ls
	 * @param str
	 *            指定的字符串
	 * @return 是否是邮�?:是为true，否则false
	 */
	public static Boolean isEmail(String str) {
		Boolean isEmail = false;
		String expr = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		if (str.matches(expr)) {
			isEmail = true;
		}
		return isEmail;
	}

	/**
	 * 判断是否是银行卡�?
	 * 
	 * @author ls
	 * @param cardId
	 * @return
	 */
	public static boolean checkBankCard(String cardId) {
		char bit = getBankCardCheckCode(cardId
				.substring(0, cardId.length() - 1));
		if (bit == 'N') {
			return false;
		}
		return cardId.charAt(cardId.length() - 1) == bit;

	}

	private static char getBankCardCheckCode(String nonCheckCodeCardId) {
		if (nonCheckCodeCardId == null
				|| nonCheckCodeCardId.trim().length() == 0
				|| !nonCheckCodeCardId.matches("\\d+")) {
			// 如果传的不是数据返回N
			return 'N';
		}
		char[] chs = nonCheckCodeCardId.trim().toCharArray();
		int luhmSum = 0;
		for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
			int k = chs[i] - '0';
			if (j % 2 == 0) {
				k *= 2;
				k = k / 10 + k % 10;
			}
			luhmSum += k;
		}
		return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
	}

	/**
	 * 描述：判断一个字符串是否为null或空�?.
	 * 
	 * @author ls
	 * @param str
	 *            指定的字符串
	 * @return true or false
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	/**
	 * 描述：是否是中文.
	 * 
	 * @author ls
	 * @param str
	 *            指定的字符串
	 * @return 是否是中�?:是为true，否则false
	 */
	public static Boolean isChinese(String str) {
		Boolean isChinese = true;
		String chinese = "[\u0391-\uFFE5]";
		if (!isEmpty(str)) {
			// 获取字段值的长度，如果含中文字符，则每个中文字符长度�?2，否则为1
			for (int i = 0; i < str.length(); i++) {
				// 获取�?个字�?
				String temp = str.substring(i, i + 1);
				// 判断是否为中文字�?
				if (temp.matches(chinese)) {
				} else {
					isChinese = false;
				}
			}
		}
		return isChinese;
	}

	/**
	 * 描述：是否包含中�?.
	 * 
	 * @author ls
	 * 
	 * @param str
	 *            指定的字符串
	 * @return 是否包含中文:是为true，否则false
	 */
	public static Boolean isContainChinese(String str) {
		Boolean isChinese = false;
		String chinese = "[\u0391-\uFFE5]";
		if (!isEmpty(str)) {
			// 获取字段值的长度，如果含中文字符，则每个中文字符长度�?2，否则为1
			for (int i = 0; i < str.length(); i++) {
				// 获取�?个字�?
				String temp = str.substring(i, i + 1);
				// 判断是否为中文字�?
				if (temp.matches(chinese)) {
					isChinese = true;
				} else {

				}
			}
		}
		return isChinese;
	}

	/**
	 * 正则表达式可能不严谨（写的比较唐突）
	 * 
	 * @param source
	 * @return 返回0表示非数值，返回1表示是int型，返回2表示是double�?
	 */
	public static int type(String source) {
		int result = 0;
		String regex1 = "^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$";
		boolean flag1 = source.matches(regex1);
		if (flag1) {
			String regex2 = "^[-+]?([0-9]+)$";
			boolean flag2 = source.matches(regex2);
			if (flag2) {
				result = 1;
			} else {
				result = 2;
			}
		}
		return result;
	}

	//判断是否是纯英文，纯数字
	
	public static boolean ispsd(String psd) {
        Pattern p = Pattern
                .compile("^[a-zA-Z].*[0-9]|.*[0-9].*[a-zA-Z]");
        Matcher m = p.matcher(psd);

        return m.matches();
    }


	
	/**
	 * dp转px
	 * 
	 * @param dp
	 * @param context
	 * @return
	 */
	public static float DpzhuanPx(int dp, Context context) {

		float density = context.getResources().getDisplayMetrics().density;

		float px;
		px = dp * density;
		return px;
	}

	/**
	 * px转dp
	 * 
	 * @param dp
	 * @param context
	 * @return
	 */
	public static float PxzhuanDp(int px, Context context) {
		float density = context.getResources().getDisplayMetrics().density;
		float dp;
		dp = px / density;
		return dp;
	}

}
