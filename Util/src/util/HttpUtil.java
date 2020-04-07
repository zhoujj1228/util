package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class HttpUtil {
	
	public static void main(String[] args) throws InterruptedException{
		String url = "https://rsshub.app/telegram/channel/jdkzjk";
		String result = callHttpParamGet(url, null, null, "UTF-8", null);
		System.out.println(result);
	}
	
	
	public static String callHttpParamPost(String url, HashMap<String,String> paramMap, String data, String encode, HashMap<String, String> headerMap){
		StringBuffer sb = new StringBuffer();
		String paramString = null;
		if(paramMap != null){
			Iterator<Entry<String, String>> it = paramMap.entrySet().iterator();
			while(it.hasNext()){
				Entry<String, String> entry = it.next();
				String key = entry.getKey();
				String value = entry.getValue();
				sb.append(key+"="+value+"&");
			}
			paramString = sb.substring(0, sb.length()-1);
		}
		
		URL urls = null;
		HttpURLConnection url_con = null;
		PrintWriter out = null;
		String result = null;
		InputStream in = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		//格式如http://11.8.128.140:8765/test?username=123&age=12
		try {
			if(paramString != null){
				urls = new URL(url+"?"+paramString);
			}else{
				urls = new URL(url);
			}
			//urls = new URL("http://11.8.129.121:8081/esbconsole");
			//打开连接
			url_con = (HttpURLConnection) urls.openConnection();
			
			if(headerMap == null) {
				//设置通用的请求属性
				url_con.setRequestProperty("accept", "*/*");
				url_con.setRequestProperty("connection", "Keep-Alive");
			}else {
				for(String key : headerMap.keySet()) {
					String value = headerMap.get(key);
					url_con.setRequestProperty(key, value);
				}
			}
			
			
			url_con.setRequestMethod("POST");
			//post请求必须这两行
			url_con.setDoOutput(true);
			url_con.setDoInput(true);
			//发送参数
			if(data != null){
				out = new PrintWriter(url_con.getOutputStream());
				out.print(data);
				out.flush();
			}else{
				url_con.connect();
			}
			
			//打开真实连接,如果尚未打开，因为已经建立output所以不用打开连接了
			//url_con.connect();
			
			int code = url_con.getResponseCode();
			if(code == HttpURLConnection.HTTP_OK){
				in = url_con.getInputStream();
				isr = new InputStreamReader(in, encode);
				br = new BufferedReader(isr);
				String s = br.readLine();
				StringBuffer sbRsp = new StringBuffer();
				while(s != null){
					sbRsp.append(s);
					s = br.readLine();
				}
				result = sbRsp.toString();
//				System.out.println(result);
				br.close();
				isr.close();
				in.close();
			}
			else{
				System.out.println("errCode="+code);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
			if(isr != null){
				try {
					isr.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		}
		return result;
		
	}
	
	public static String callHttpParamGet(String url, HashMap<String,String> paramMap, String data, String encode, HashMap<String,String> headerMap){
		StringBuffer sb = new StringBuffer();
		String paramString = null;
		if(paramMap != null){
			Iterator<Entry<String, String>> it = paramMap.entrySet().iterator();
			while(it.hasNext()){
				Entry<String, String> entry = it.next();
				String key = entry.getKey();
				String value = entry.getValue();
				sb.append(key+"="+value+"&");
			}
			paramString = sb.substring(0, sb.length()-1);
		}
		
		URL urls = null;
		HttpURLConnection url_con = null;
		PrintWriter out = null;
		String result = null;
		InputStream in = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		//格式如http://11.8.128.140:8765/test?username=123&age=12
		try {
			if(paramString != null){
				urls = new URL(url+"?"+paramString);
			}else{
				urls = new URL(url);
			}
			//urls = new URL("http://11.8.129.121:8081/esbconsole");
			//打开连接
			url_con = (HttpURLConnection) urls.openConnection();
			
			if(headerMap == null) {
				url_con.setRequestProperty("accept", "*/*");
				url_con.setRequestProperty("connection", "Keep-Alive");
			}else {
				for(String key : headerMap.keySet()) {
					String value = headerMap.get(key);
					url_con.setRequestProperty(key, value);
				}
			}
			
			//设置通用的请求属性
			//url_con.setRequestProperty("accept", "*/*");
			//url_con.setRequestProperty("connection", "Keep-Alive");
			//url_con.setRequestProperty("Cookie", "Hm_lvt_428625cbc2b4a0c40698357ed55cdba9=1522650616; Hm_lvt_1ebcfb94d6c4595ccc209b35019cd05d=1529032835; _pk_id.3.ec3a=27f0bdf36521d25b.1518059675.2.1522651850.1518059719.; Hm_lvt_e723284c7c986858e4979c37cfecef34=1530170143; PD-H-SESSION-ID=4_lynrb8uFU7tGA9JEo1tNo3RHsxiHMRG+PufzpiXJO8F9e7NW; SESSION_COOKIE=c_webseal_31; AMWEBJCT!%2Fitsweb!JSESSIONID=1E55D3325BA7D0842C46EA81396A43D7; IV_JCT=%2Fdcie; AMWEBJCT!%2Fdcie!JSESSIONID=05BECA3C8E97E2AC02188C08DEE9C4ED; PD-ID=nIHTOpTOFg39NQsDA2IWPPPjRslFYwFHFHMlkA9NRrYwfQcxrwkQXrUuNaNh4yRwpfUNjJmL7CXEjiUcB/IK0A9PdqtgO24ymDjo7bDPSnHPY7F1p/y/X0PPdghVg4zbXfl1Ty5RGZl34C5bDMMBO0wrrDqVAHQeWH1CyOQbc4uc2D+m9c0Bwkvzr4LwMNoiQyuDye1+Oo8T+8jo5l/jZE6QV3qm7V74eGQtiijE1f8Op6YYmWCxLkiE/fV3Q/VgdxUZ+SVnFWM=; PD-ECC=BAGs3DB0AgEBAgIBmgIBAAIBAAQABGMEAazcMF0CAQECBFs9qQIEGWhvbWUuaXRzcG9ydGFsLmRjaXRzLmNvbQAEBmRjaXRzAAQOL3BrbXN2b3VjaGZvcgAwHwIBATAaMBgCAQEEDHBkLWVjYy1odHRwADAFBAM4MAA=!.dcits.com");
			url_con.setRequestMethod("GET");
			url_con.setInstanceFollowRedirects(false); 
			//post请求必须这两行
			/*url_con.setDoOutput(true);
			url_con.setDoInput(true);*/
			//发送参数
			if(data != null){
				out = new PrintWriter(url_con.getOutputStream());
				out.print(data);
				out.flush();
			}else{
				url_con.connect();
			}
			
			//打开真实连接,如果尚未打开，因为已经建立output所以不用打开连接了
			//url_con.connect();
			
			int code = url_con.getResponseCode();
			if(code == HttpURLConnection.HTTP_OK){
				in = url_con.getInputStream();
				isr = new InputStreamReader(in, encode);
				br = new BufferedReader(isr);
				String s = br.readLine();
				StringBuffer sbRsp = new StringBuffer();
				while(s != null){
					sbRsp.append(s);
					s = br.readLine();
				}
				result = sbRsp.toString();
				System.out.println(result);
				System.out.println(url_con.getHeaderFields());
				br.close();
				isr.close();
				in.close();
			}
			else{
				if(code == 302){
					System.out.println();
				}
				System.out.println("errCode="+code);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
			if(isr != null){
				try {
					isr.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		}
		return result;
		
	}
	
	
	public static void callHttpXml(String url,String data) throws Exception{
		HttpURLConnection connection = null;
		OutputStream os = null;
		URL urls = null;
		BufferedReader br = null;
		OutputStreamWriter out = null;
		try {
			urls = new URL(url);
			HostnameVerifier hv = new HostnameVerifier() {
				@Override
				public boolean verify(String urlHostName, SSLSession session) {
					return true;
				}
			};
			//HttpURLConnection.set(hv);
			System.setProperty("java.protocol.handler.pkgs", "sun.net.www.protocol");
			connection = (HttpURLConnection) urls.openConnection();
			connection.setRequestProperty("Content-Type", "text/xml");
			
			//post请求必须
			connection.setDoOutput(true);
			connection.setDoInput(true);
			
			
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setReadTimeout(120000);
			connection.connect();
			os = connection.getOutputStream();
			out = new OutputStreamWriter(os);
			out.write(data);
			out.flush();
			out.close();
			os.close();
			if(connection.getResponseCode() == 200){
				//System.out.println("ok200");
				br = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
				StringBuffer sb = new StringBuffer();
				String readData = br.readLine();
				while(readData != null || "".equals(readData)){
					sb.append(readData);
					readData = br.readLine();
				}
				System.out.println("接收到的数据为："+sb.toString());
			}else{
				br = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
				StringBuffer sb = new StringBuffer();
				String readData = br.readLine();
				while(readData != null || "".equals(readData)){
					sb.append(readData);
					readData = br.readLine();
				}
			}
			br.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally{
			if(os != null){
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
			connection.disconnect();
			
		}
	
	}
	/**
	 * 
	 * @param connectNum 最大连接数
	 * @param rspData 返回数据
	 * @param context 下级目录
	 * @param port 监听端口
	 */
	public static void createHttpServer(int connectNum, String rspData, String context, int port){
		InetSocketAddress inetSock = new InetSocketAddress(port);
		HttpServer httpServer =null;
		try {
			httpServer = HttpServer.create(inetSock, connectNum);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//设置对应目录处理类
		httpServer.createContext(context, new HanderTestA(rspData));
		//httpServer.createContext("/test",new HanderTestB());
		httpServer.setExecutor(null);
		httpServer.start();
		System.out.println("httpServer...start...");
	}
	
	
	public static void createHttpServer(int connectNum, String rspData, String context, int port, HttpHandler handler){
		InetSocketAddress inetSock = new InetSocketAddress(port);
		HttpServer httpServer =null;
		try {
			httpServer = HttpServer.create(inetSock, connectNum);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//设置对应目录处理类
		httpServer.createContext(context, handler);
		//httpServer.createContext("/test",new HanderTestB());
		httpServer.setExecutor(null);
		httpServer.start();
		System.out.println("httpServer...start...");
	}
	
	
	
	public static String downloadFile(String src_file, String dest_file, String createName) throws Throwable {

		/*if(src_file.indexOf("awesome") > -1) {
			System.out.println(src_file);
		}*/
		String fileName = getFileName(src_file);
		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
			HttpGet httpget = new HttpGet(src_file);
			httpget.setConfig(RequestConfig.custom() //
					.setConnectionRequestTimeout(3000) //
					.setConnectTimeout(3000) //
					.setSocketTimeout(3000) //
					.build());
			//httpget.setHeader("X-Forwarded-For", "1.1.1.1, 2.2.2.2, 3.3.3.3");
			try (CloseableHttpResponse response = httpclient.execute(httpget)) {
				org.apache.http.HttpEntity entity = response.getEntity();
				if (createName != null && !createName.equals("")) {
					fileName = createName;
				}
				File desc = new File(dest_file + File.separator + fileName);
				File folder = desc.getParentFile();
				folder.mkdirs();
				try (InputStream is = entity.getContent(); //
						OutputStream os = new FileOutputStream(desc)) {
					copy(is, os);
				}
			} catch (Throwable e) {
				//e.printStackTrace();
				throw new Throwable("文件下载失败......", e);
			}
		}
		return dest_file + File.separator + fileName;
	}
	
	private static String getFileName(String src_file) {
		// TODO 自动生成的方法存根
		String name = src_file;
		if(name.indexOf("?") > -1) {
			name = name.substring(0, name.lastIndexOf("?"));
		}
		try {
			name = name.substring(name.lastIndexOf("/") + 1);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(name);
		}
		return name;
	}
	
	private static void copy(InputStream is, OutputStream os) throws IOException {
		byte[] buffer = new byte[1024];
		int len = 0;
		try {
			boolean isFile = false;
			while ((len = is.read(buffer)) > -1) {
				isFile = true;
				os.write(buffer, 0, len);
				os.flush();
			}
			// System.out.println(isFile);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			//e.printStackTrace();
			throw e;
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}
}

class HanderTestA implements HttpHandler{
	String responseString = null;
	HanderTestA(String s){
		super();
		responseString = s;
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
		BufferedReader reader = new BufferedReader(new InputStreamReader(input,"UTF-8"));
		String temp = null;
		System.out.println("request-begin");
		while((temp = reader.readLine()) != null){
			System.out.println(temp);
		}
		System.out.println("request-end");
		httpExchange.sendResponseHeaders(200, responseString.getBytes("UTF-8").length);
		OutputStream os = httpExchange.getResponseBody();
		os.write(responseString.getBytes("UTF-8"));
		os.close();
		httpExchange.close();
	}
	
	
	
}
