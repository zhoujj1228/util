package example;

import java.util.List;

import util.PatternUtil;

public class PatternExample {

	public static void main(String[] args) {
		/*
		^:开始
		$:结束
		。:任何字符
		*:0或是多个匹配
		{2}:匹配固定两次
		{2,}:匹配至少两次
		{2,5}:匹配2-5次
		?:等价于{0,1}
		():表示子表达式
		\\d:代表数字，等同与[0-9]
		\\D:代表非数字,等同于[^0-9]
		+:等价于{1,}
		[abc]:匹配abc任意一个
		(aa|bb|cc):匹配aa或是bb或是cc的字符串
		*/
		String patternString = "http.*w(ww){1}.*(song){2,3}.*mp3";
		String source = "123http://www.userusersongsongsongsong123123123.mp3asdfsdf";
		List<String> list = PatternUtil.getStringByPattern(patternString, source);
		//System.out.println(list.size()>0?list.get(0):0);
		
		//patternString = ".*\\D{4}.*";
		//patternString = ".*\\d{3}.*";
		//patternString = ".*[^0-9]{5}.*";
		patternString = ".*[0-9]{4}.*";
		source = "111aaaa";
		list = PatternUtil.getStringByPattern(patternString, source);
		//System.out.println(list.size()>0?list.get(0):0);
		
		patternString = ".*[abc]{4}.*";
		source = "aacbaa";
		list = PatternUtil.getStringByPattern(patternString, source);
		//System.out.println(list.size() > 0 ? list.get(0) : 0);
		
		patternString = ".*(1000332019302|1000332019304|1000332019306|1000332019310|1000332019502).*";
		source = "1000332019305aaa";
		list = PatternUtil.getStringByPattern(patternString, source);
		System.out.println(list.size() > 0 ? list.get(0) : 0); 
	}

}
