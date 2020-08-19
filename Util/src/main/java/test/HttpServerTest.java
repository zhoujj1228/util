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

public class HttpServerTest implements HttpHandler{
	String responseString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <SDOROOT>   <SYS_HEAD>     <RET>         <SDO>           <RET_MSG>交易成功完成</RET_MSG>           <RET_CODE>I030006000000</RET_CODE>         </SDO>     </RET>     <CONSUMER_SVR_ID>2D0298806C326687</CONSUMER_SVR_ID>     <TRAN_TIMESTAMP>094524608</TRAN_TIMESTAMP>     <CONSUMER_SEQ_NO>0300092040091159431056</CONSUMER_SEQ_NO>     <WS_ID>0000000141</WS_ID>     <RET_STATUS>S</RET_STATUS>     <SERVICE_SCENE>05</SERVICE_SCENE>     <CONSUMER_ID>030009</CONSUMER_ID>     <SERVICE_CODE>01001000001</SERVICE_CODE>     <TRAN_DATE>20400911</TRAN_DATE>     <ORG_SYS_ID>8888888888</ORG_SYS_ID>   </SYS_HEAD>   <APP_HEAD>     <BIZ_SEQ_NO>59431056</BIZ_SEQ_NO>     <USER_ID>X0300</USER_ID>   </APP_HEAD>   <LOCAL_HEAD>     <RURAL_BRANCH_ID>0000</RURAL_BRANCH_ID>   </LOCAL_HEAD>   <BODY> 	<EXT_BUS_TIME>0</EXT_BUS_TIME>   </BODY> </SDOROOT>";
	public static int sleepTimeout = 1;
	public static void main(String[] args) throws Exception {
		if(args.length > 0){
			sleepTimeout = Integer.parseInt(args[0]);
		}
		HttpServerTest hut = new HttpServerTest();
		HttpUtil.createHttpServer(20, "", "/ectip/httpaccess", 8888, hut);
		
	}

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {

		//String responseString = "<SDOROOT package_type=\"xml\"> 	<SYS_HEAD> 		<TRAN_TIMESTAMP>162625284</TRAN_TIMESTAMP> 		<TRAN_DATE>20160302</TRAN_DATE> 		<SERVICE_SCENE>01</SERVICE_SCENE> 		<CONSUMER_ID>030006</CONSUMER_ID> 		<SERVICE_CODE>11002000066</SERVICE_CODE> 		<ORG_SYS_ID>030006    </ORG_SYS_ID> 		<ESB_SEQ_NO>C1-20160308162625-001167</ESB_SEQ_NO> 		<CONSUMER_SEQ_NO>0300062016030200649129</CONSUMER_SEQ_NO> 	</SYS_HEAD> 	<APP_HEAD> 		<USER_ID>E9990   </USER_ID> 		<REVERSAL_DATE></REVERSAL_DATE> 		<AUTH_USER_ID_ARRAY> 			<SDO> 				<AUTH_PASSWORD>                </AUTH_PASSWORD> 				<AUTH_USER_ID>        </AUTH_USER_ID> 			</SDO> 			<SDO> 				<AUTH_PASSWORD>                </AUTH_PASSWORD> 				<AUTH_USER_ID>        </AUTH_USER_ID> 			</SDO> 		</AUTH_USER_ID_ARRAY> 		<REVERSAL_SEQ_NO>00000000</REVERSAL_SEQ_NO> 		<BRANCH_ID>9990      </BRANCH_ID> 		<BIZ_SEQ_NO>00007370</BIZ_SEQ_NO> 	</APP_HEAD> 	<LOCAL_HEAD> 		<RURAL_BRANCH_ID>0000</RURAL_BRANCH_ID> 		<CHANNEL_CODE>000001</CHANNEL_CODE> 		<BUS_SEQ_NO>0300062016030200649129</BUS_SEQ_NO> 	</LOCAL_HEAD> 	<BODY> 		<ACCT_BAL>431.50</ACCT_BAL> 		<TRANS_AMT>6.00</TRANS_AMT> 		<TRANS_TIME>131948</TRANS_TIME> 		<TRANS_DATE>20160307</TRANS_DATE> 		<ACCT_TYPE>1</ACCT_TYPE> 		<DR_CR_FLAG>2</DR_CR_FLAG> 		<ACCT_NO>6231288400001952761</ACCT_NO> 		<REMARK>bbb</REMARK> 	</BODY> </SDOROOT>";
		String requestMethod = httpExchange.getRequestMethod();
		System.out.println("请求方式为"+requestMethod);
		
		Headers headers = httpExchange.getRequestHeaders();
		Set<String> set = headers.keySet();
		Iterator<String> it = set.iterator();
		while(it.hasNext()){
			String key = it.next();
			List<String> values = headers.get(key);
			String s = key + "=" + values.toString();
			System.out.println(s);
		}
		System.out.println("url为"+httpExchange.getRequestURI());
		
		InputStream input = httpExchange.getRequestBody();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
		String temp = null;
		System.out.println("request-begin");
		while((temp = reader.readLine()) != null){
			System.out.println(temp);
		}
		System.out.println("request-end");
		try {
			Thread.currentThread().sleep(sleepTimeout);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		httpExchange.sendResponseHeaders(200, responseString.length());
		OutputStream os = httpExchange.getResponseBody();
		os.write(responseString.getBytes("UTF-8"));
		os.close();
		httpExchange.close();
	
	}
}
