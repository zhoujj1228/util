package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import util.HttpUtil;

public class HttpClientTest{
	String responseString = "123";
	public static void main(String[] args) throws Exception {
		
		String reqStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <SDOROOT>   <SYS_HEAD><CONSUMER_SEQ_NO>030009204009110000001</CONSUMER_SEQ_NO>     <TRAN_TIMESTAMP>091253091</TRAN_TIMESTAMP>          <USER_LANG>CHINESE</USER_LANG>     <SERVICE_SCENE>01</SERVICE_SCENE>     <SERVICE_CODE>11002000081</SERVICE_CODE>     <CONSUMER_ID>010106</CONSUMER_ID>     <TRAN_DATE>20400911</TRAN_DATE>     <ORG_SYS_ID>8888888888</ORG_SYS_ID>   </SYS_HEAD>   <APP_HEAD>     <BIZ_SEQ_NO>59431056</BIZ_SEQ_NO>     <USER_ID>X0300</USER_ID>     <BRANCH_ID>0300</BRANCH_ID>   </APP_HEAD>   <LOCAL_HEAD>     <RURAL_BRANCH_ID>0000</RURAL_BRANCH_ID>     <CHANNEL_CODE>000001</CHANNEL_CODE>     <BUS_SEQ_NO>0300092040091159000001</BUS_SEQ_NO>   </LOCAL_HEAD>   <BODY>     <EXT_BUS_CODE>1120080</EXT_BUS_CODE>   </BODY> </SDOROOT>";
		HttpUtil.callHttpXml("http://11.8.129.120:30056/eone", reqStr);
		//HttpUtil.callHttpXml("http://11.13.2.66:8081/ectip/httpaccess", reqStr);
	}

}
