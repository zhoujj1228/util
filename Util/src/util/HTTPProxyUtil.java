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
	 * @param acct �����˻�
	 * @param pass ��������
	 * @param encode 
	 * @return
	 */
	public static String proxyHttp(String httpUrl, String proxyAddr, int proxyPort, String acct, String pass, String encode) {
		String result = null;
		// TODO Auto-generated method stub
		try {
			URL url = new URL(httpUrl);
			// �������������
			InetSocketAddress addr = new InetSocketAddress(proxyAddr, proxyPort);
			Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http ����
			URLConnection conn = url.openConnection(proxy);

			// ��������������Ҫ��֤ʱ�������ʺ�������Ϣ
			String headerkey = "Proxy-Authorization";
			// String headerValue = "Basic
			// "+Base64.encodeToString("atco:atco".getBytes(), false);
			// //�ʺ�������:������base64���ܷ�ʽ
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
	 * ��У����Ϣд��httpͷ�����û����������base64����֮������Proxy-Authorizationͷ
	 * �÷�ʽ��https�����󳡾����п����޷���������
	 * ������ʵ��Authenticator�ӿڣ���ע��Ϊȫ����֤��
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
		
		// ����ϵͳ����

		System.setProperty("http.proxySet", "true");
		System.setProperty("http.proxyHost", proxyHost);
		System.setProperty("http.proxyPort", "" + proxyPort);
		// ���httpsҲ��������
		System.setProperty("https.proxyHost", proxyHost);
		System.setProperty("https.proxyPort", "" + proxyPort);
		// ����Ĭ��У����
		setDefaultAuthentication(proxyUser, proxyPass);

		// ��ʼ����
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
	 * ����ȫ��У��������
	 * @param proxyUser 
	 * @param proxyPass 
	 */
	public static void setDefaultAuthentication(String proxyUser, String proxyPass) {
		BasicAuthenticator auth = new BasicAuthenticator(proxyUser, proxyPass);
		Authenticator.setDefault(auth);
	}
}



/**
 * У����
 * ʵ��sun.net�Ĵ�����֤
 * @author Jay
 * @date 2018��11��2��
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
