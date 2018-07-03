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
	public static void callHttpParamPost(String url, HashMap<String,String> paramMap, String data){
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
		//��ʽ��http://11.8.128.140:8765/test?username=123&age=12
		try {
			if(paramString != null){
				urls = new URL(url+"?"+paramString);
			}else{
				urls = new URL(url);
			}
			//urls = new URL("http://11.8.129.121:8081/esbconsole");
			//������
			url_con = (HttpURLConnection) urls.openConnection();
			//����ͨ�õ���������
			url_con.setRequestProperty("accept", "*/*");
			url_con.setRequestProperty("connection", "Keep-Alive");
			url_con.setRequestMethod("POST");
			//post�������������
			url_con.setDoOutput(true);
			url_con.setDoInput(true);
			//���Ͳ���
			if(data != null){
				out = new PrintWriter(url_con.getOutputStream());
				out.print(data);
				out.flush();
			}else{
				url_con.connect();
			}
			
			//����ʵ����,�����δ�򿪣���Ϊ�Ѿ�����output���Բ��ô�������
			//url_con.connect();
			
			int code = url_con.getResponseCode();
			if(code == HttpURLConnection.HTTP_OK){
				in = url_con.getInputStream();
				isr = new InputStreamReader(in);
				br = new BufferedReader(isr);
				String s = br.readLine();
				StringBuffer sbRsp = new StringBuffer();
				while(s != null){
					sbRsp.append(s);
					s = br.readLine();
				}
				result = sbRsp.toString();
				System.out.println(result);
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
					br.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
			if(in != null){
				try {
					br.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public static void callHttpParamGet(String url, HashMap<String,String> paramMap, String data){
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
		//��ʽ��http://11.8.128.140:8765/test?username=123&age=12
		try {
			if(paramString != null){
				urls = new URL(url+"?"+paramString);
			}else{
				urls = new URL(url);
			}
			//urls = new URL("http://11.8.129.121:8081/esbconsole");
			//������
			url_con = (HttpURLConnection) urls.openConnection();
			//����ͨ�õ���������
			url_con.setRequestProperty("accept", "*/*");
			url_con.setRequestProperty("connection", "Keep-Alive");
			url_con.setRequestMethod("GET");
			//post�������������
			/*url_con.setDoOutput(true);
			url_con.setDoInput(true);*/
			//���Ͳ���
			if(data != null){
				out = new PrintWriter(url_con.getOutputStream());
				out.print(data);
				out.flush();
			}else{
				url_con.connect();
			}
			
			//����ʵ����,�����δ�򿪣���Ϊ�Ѿ�����output���Բ��ô�������
			//url_con.connect();
			
			int code = url_con.getResponseCode();
			if(code == HttpURLConnection.HTTP_OK){
				in = url_con.getInputStream();
				isr = new InputStreamReader(in);
				br = new BufferedReader(isr);
				String s = br.readLine();
				StringBuffer sbRsp = new StringBuffer();
				while(s != null){
					sbRsp.append(s);
					s = br.readLine();
				}
				result = sbRsp.toString();
				System.out.println(result);
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
					br.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
			if(in != null){
				try {
					br.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		}
		
	}
	
	
	public static void callHttpXml(String url,String data){
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
			
			//post�������
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
				System.out.println("���յ�������Ϊ��"+sb.toString());
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
	 * @param connectNum ���������
	 * @param rspData ��������
	 * @param context �¼�Ŀ¼
	 * @param port �����˿�
	 */
	public static void createHttpServer(int connectNum, String rspData, String context, int port){
		InetSocketAddress inetSock = new InetSocketAddress(port);
		HttpServer httpServer =null;
		try {
			httpServer = HttpServer.create(inetSock, connectNum);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//���ö�ӦĿ¼������
		httpServer.createContext(context, new HanderTestA(rspData));
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
				throw new Throwable("�ļ�����ʧ��......", e);
			}
		}
		return dest_file + File.separator + fileName;
	}
	
	private static String getFileName(String src_file) {
		// TODO �Զ����ɵķ������
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
			// TODO �Զ����ɵ� catch ��
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
				// TODO �Զ����ɵ� catch ��
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
		System.out.println("����ʽΪ"+requestMethod);
		
		Headers headers = httpExchange.getRequestHeaders();
		Set<String> set = headers.keySet();
		Iterator<String> it = set.iterator();
		while(it.hasNext()){
			String key = it.next();
			List<String> values = headers.get(key);
			String s = key + "=" + values.toString();
			System.out.println(s);
		}
		System.out.println("urlΪ"+httpExchange.getRequestURI());
		
		InputStream input = httpExchange.getRequestBody();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input,"UTF-8"));
		String temp = null;
		System.out.println("request-begin");
		while((temp = reader.readLine()) != null){
			System.out.println(temp);
		}
		System.out.println("request-end");
		httpExchange.sendResponseHeaders(200, responseString.length());
		OutputStream os = httpExchange.getResponseBody();
		os.write(responseString.getBytes());
		os.close();
		httpExchange.close();
	}
	
	
	
}