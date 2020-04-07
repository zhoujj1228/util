package test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import java.util.Map.Entry;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import util.HttpUtil;
import util.ImageCompareUtil;

public class HttpClientTest2{
	private static List<String> imagePaths;
	public static void main(String[] args) throws Exception {
		init();
		call();
//		String reqStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <SDOROOT>   <SYS_HEAD><CONSUMER_SEQ_NO>030009204009110000001</CONSUMER_SEQ_NO>     <TRAN_TIMESTAMP>091253091</TRAN_TIMESTAMP>          <USER_LANG>CHINESE</USER_LANG>     <SERVICE_SCENE>01</SERVICE_SCENE>     <SERVICE_CODE>11002000081</SERVICE_CODE>     <CONSUMER_ID>010106</CONSUMER_ID>     <TRAN_DATE>20400911</TRAN_DATE>     <ORG_SYS_ID>8888888888</ORG_SYS_ID>   </SYS_HEAD>   <APP_HEAD>     <BIZ_SEQ_NO>59431056</BIZ_SEQ_NO>     <USER_ID>X0300</USER_ID>     <BRANCH_ID>0300</BRANCH_ID>   </APP_HEAD>   <LOCAL_HEAD>     <RURAL_BRANCH_ID>0000</RURAL_BRANCH_ID>     <CHANNEL_CODE>000001</CHANNEL_CODE>     <BUS_SEQ_NO>0300092040091159000001</BUS_SEQ_NO>   </LOCAL_HEAD>   <BODY>     <EXT_BUS_CODE>1120080</EXT_BUS_CODE>   </BODY> </SDOROOT>";
//		HttpUtil.callHttpXml("http://11.8.129.120:30056/eone", reqStr);
		//HttpUtil.callHttpXml("http://11.13.2.66:8081/ectip/httpaccess", reqStr);
		
		
	}
	
	private static void init() {
		imagePaths = new ArrayList<String>();
		imagePaths.add("D:\\Test\\20200213\\1.png");
	}

	private static void call() throws IOException {
		int i = 0;
		while(true) {
			try {
				Thread.currentThread().sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
			String rsp = callHttpParamGet("https://list.tmall.com/search_product.htm?q=%D5%F1%B5%C2+%BF%DA%D5%D6", null, null, "GBK");
			i++;
			System.out.println("检查"+i);
			if(!rsp.contains("口罩")) {
				System.out.println("!html.contains(\"口罩\")");
				System.exit(0);
			}
			//System.out.println(rsp);
			if(rsp.contains("ZD振德旗舰店")) {
				System.out.println("获取到口罩信息更新");
				ImageCompareUtil.findImagesAndMouseLeftClick(imagePaths);
				break;
			}
		}		
	}

	public static String callHttpParamGet(String url, HashMap<String,String> paramMap, String data, String encode){
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
			//设置通用的请求属性
			url_con.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
			url_con.setRequestProperty("Accept-Encoding", "gzip, deflate");
			url_con.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
			url_con.setRequestProperty("Cache-Control", "max-age=0");
			url_con.setRequestProperty("connection", "keep-alive");
			url_con.setRequestProperty("Cookie", "lid=jingots; cna=nrD4FVAF13QCAXWITxlrcPQ8; hng=CN%7Czh-CN%7CCNY%7C156; _m_h5_tk=7e9533ffd0f3c06591fdaa163f687dac_1581480288612; _m_h5_tk_enc=41367c74ddce86ff4221135e9724586e; _med=dw:1366&dh:768&pw:1366&ph:768&ist:0; cq=ccp%3D1; sm4=440600; t=b5411c4c46a109015639cf0ca13c2ff3; tracknick=jingots; _tb_token_=7356e57b17e35; cookie2=1aae58cfdbd1e27ad8272d861ab481d7; res=scroll%3A1349*5461-client%3A1349*625-offset%3A1349*5461-screen%3A1366*768; pnm_cku822=098%23E1hvOpvUvbpvUvCkvvvvvjiPn2zptjnCPsM9QjljPmPw0jYnn2svAjYjRLzp0jEViQhvChCvCCpCvpvVphhvvvvvmphvLhW7wvmFr2Bc8vmYib01UxUDCw2IRfU6pLEw9E7re169wx0DYEufJhY%2BVd0DW3CQoAnmsXZpejIUExjxALwpEctl8PoxdXIaneUjFaVivpvUphvhQBsB%2BzAEvpvVpyUUCEKwKphv8hCvvvvvvhCvphvZ7pvvpFnvpCBXvvC2p6CvHHyvvh84phvZ7pvvpiIPvpvhvv2MMsyCvvBvpvvv; l=cBICmAocqlGdxQk9BOCanurza77OSIRYYuPzaNbMi_5IP6T_Ni_OoSYpkF96VjWd_3LB4GjXta99-etkZijABplHtBUV.; isg=BG1tOTENz48k06gaSsU4A5g6fAnnyqGc_fSp0q9yqYRzJo3YdxqxbLv0FPrAprlU");
			url_con.setRequestProperty("Host", "list.tmall.com");
			url_con.setRequestProperty("Upgrade-Insecure-Requests", "1");
			url_con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36");
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
				
				
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				int len = 1024;
				byte[] buffer = new byte[1024];
				int readlen = 0;
				while((readlen = in.read(buffer, 0, len)) > 0){
					bos.write(buffer, 0, readlen);
				}
				byte[] bytes = bos.toByteArray();
				byte[] uncompress = uncompress(bytes);
				result = new String(uncompress, "GBK");
				
				/*isr = new InputStreamReader(in, encode);
				br = new BufferedReader(isr);
				String s = br.readLine();
				StringBuffer sbRsp = new StringBuffer();
				while(s != null){
					sbRsp.append(s);
					s = br.readLine();
				}
				result = sbRsp.toString();*/
				//System.out.println(result);
				//System.out.println(url_con.getHeaderFields());
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
	
	public static byte[] uncompress(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            GZIPInputStream ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

}
