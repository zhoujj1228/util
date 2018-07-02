package example;

import java.util.List;

import util.PatternUtil;

public class PatternExample {

	public static void main(String[] args) {
		/*
		^:��ʼ
		$:����
		��:�κ��ַ�
		*:0���Ƕ��ƥ��
		{2}:ƥ��̶�����
		{2,}:ƥ����������
		{2,5}:ƥ��2-5��
		?:�ȼ���{0,1}
		():��ʾ�ӱ��ʽ
		\\d:�������֣���ͬ��[0-9]
		\\D:���������,��ͬ��[^0-9]
		+:�ȼ���{1,}
		[abc]:ƥ��abc����һ��
		(aa|bb|cc):ƥ��aa����bb����cc���ַ���
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
