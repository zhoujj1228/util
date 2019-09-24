package util;

import java.io.InputStream;
import java.net.Authenticator;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;

import javax.net.ssl.HttpsURLConnection;

public class HTTPProxyUtil {
	public static void main(String[] args) {
		String httpsurl = "https://www.cnblogs.com/littleatp/p/4729781.html";
        String httpsrsp = proxyHttps(httpsurl, "183.236.234.44", 3807, "user", "pass");
        System.out.println("----httpsrsp:" + httpsrsp);
		
		String httpurl = "https://www.cnblogs.com/littleatp/p/4729781.html";
		String httprsp = proxyHttp(httpurl, "180.210.205.198", 8888, "acct", "pass", "utf-8");
        System.out.println("----httprsp:" + httprsp);
        
	}

	/**
	 * 
	 * @param httpUrl
	 * @param proxyAddr
	 * @param proxyPort
	 * @param acct 代理账户
	 * @param pass 代理密码
	 * @param encode 
	 * @return
	 */
	public static String proxyHttp(String httpUrl, String proxyAddr, int proxyPort, String acct, String pass, String encode) {
		String result = null;
		// TODO Auto-generated method stub
		try {
			URL url = new URL(httpUrl);
			// 创建代理服务器
			InetSocketAddress addr = new InetSocketAddress(proxyAddr, proxyPort);
			Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http 代理
			URLConnection conn = url.openConnection(proxy);

			// 以下三行是在需要验证时，输入帐号密码信息
			String headerkey = "Proxy-Authorization";
			// String headerValue = "Basic
			// "+Base64.encodeToString("atco:atco".getBytes(), false);
			// //帐号密码用:隔开，base64加密方式
			String headerValue = "Basic " + Base64.getEncoder().encodeToString((acct + ":" + pass).getBytes());
			conn.setRequestProperty(headerkey, headerValue);

			InputStream in = conn.getInputStream();
			// InputStream in = url.openStream();
			result = IOUtil.toString(in, encode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	/**
	 * 将校验信息写入http头，将用户名密码进行base64编码之后设置Proxy-Authorization头
	 * 该方式在https的需求场景下有可能无法正常工作
	 * 下面是实现Authenticator接口，并注入为全局验证器
	 * @param url
	 * @param proxyHost 
	 * @param proxyPort 
	 * @param proxyUser 
	 * @param proxyPass 
	 * @return
	 */
	public static String proxyHttps(String url, String proxyHost, int proxyPort, String proxyUser, String proxyPass) {
		//String proxyHost = "183.236.234.44";
	    //int proxyPort = 3807;
	    //String proxyUser = "user";
	    //String proxyPass = "pass";
		
		// 设置系统变量

		System.setProperty("http.proxySet", "true");
		System.setProperty("http.proxyHost", proxyHost);
		System.setProperty("http.proxyPort", "" + proxyPort);
		// 针对https也开启代理
		System.setProperty("https.proxyHost", proxyHost);
		System.setProperty("https.proxyPort", "" + proxyPort);
		// 设置默认校验器
		setDefaultAuthentication(proxyUser, proxyPass);

		// 开始请求
		try {
			URL u = new URL(url);
			URLConnection conn = u.openConnection();
			HttpsURLConnection httpsCon = (HttpsURLConnection) conn;
			httpsCon.setFollowRedirects(true);

			String encoding = conn.getContentEncoding();
			if (encoding == null || "".equals(encoding)) {
				encoding = "UTF-8";
			}
			InputStream is = conn.getInputStream();
			String content = IOUtil.toString(is, encoding);
			return content;
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	/**
	 * 设置全局校验器对象
	 * @param proxyUser 
	 * @param proxyPass 
	 */
	public static void setDefaultAuthentication(String proxyUser, String proxyPass) {
		BasicAuthenticator auth = new BasicAuthenticator(proxyUser, proxyPass);
		Authenticator.setDefault(auth);
	}
}



/**
 * 校验器
 * 实现sun.net的代理验证
 * @author Jay
 * @date 2018年11月2日
 */
class BasicAuthenticator extends Authenticator {
    String userName;
    String password;
    public BasicAuthenticator(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
    /**
     * Called when password authorization is needed. Subclasses should override the default implementation, which returns null.
     * 
     * @return The PasswordAuthentication collected from the user, or null if none is provided.
     */
    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        //System.out.println("DEBUG === use global authentication of password");
        return new PasswordAuthentication(userName, password.toCharArray());
    }
}
