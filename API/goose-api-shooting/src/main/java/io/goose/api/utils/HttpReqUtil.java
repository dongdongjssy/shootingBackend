package io.goose.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

public class HttpReqUtil {
	private static int DEFAULT_CONNTIME = 5000;
	private static int DEFAULT_READTIME = 5000;

	/**
	 * 默认的https执行方法,返回
	 * 
	 * @param method 请求的方法 POST/GET
	 * @param path   请求path 路径
	 * @param map    请求参数集合
	 * @param data   输入的数据 允许为空
	 * @return
	 */
	public static <T> T HttpsDefaultExecute(String method, String path, Map<String, String> map, String data,
			String encoding, Class<T> clazz) {
		String result = "";
		try {
			String url = setParmas((TreeMap<String, String>) map, path, "");
			result = defaultConnection(method, url, DEFAULT_CONNTIME, DEFAULT_READTIME, data, encoding);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SerializeUtil.jsonToBean(result, clazz) ;
	}

	/**
	 * 设置参数
	 * 
	 * @param map     参数map
	 * @param path    需要赋值的path
	 * @param charset 编码格式 默认编码为utf-8(取消默认)
	 * @return 已经赋值好的url 只需要访问即可
	 */
	public static String setParmas(Map<String, String> map, String path, String charset) throws Exception {
		String result = "";
		boolean hasParams = false;
		if (path != null && !"".equals(path)) {
			if (MapUtils.isNotEmpty(map)) {
				StringBuilder builder = new StringBuilder();
				Set<Entry<String, String>> params = map.entrySet();
				for (Entry<String, String> entry : params) {
					String key = entry.getKey().trim();
					String value = entry.getValue().trim();
					if (hasParams) {
						builder.append("&");
					} else {
						hasParams = true;
					}
					if (charset != null && !"".equals(charset)) {
						// builder.append(key).append("=").append(URLDecoder.(value,charset));
						builder.append(key).append("=").append(IOUtil.urlEncode(value, charset));
					} else {
						builder.append(key).append("=").append(value);
					}
				}
				result = builder.toString();
			}
		}
		return doUrlPath(path, result).toString();
	}

	/**
	 * 设置连接参数
	 * 
	 * @param path 路径
	 * @return
	 */
	private static URL doUrlPath(String path, String query) throws Exception {
		URL url = new URL(path);
		if (StringUtils.isEmpty(path)) {
			return url;
		}
		if (StringUtils.isEmpty(url.getQuery())) {
			if (path.endsWith("?")) {
				path += query;
			} else {
				path = path + "?" + query;
			}
		} else {
			if (path.endsWith("&")) {
				path += query;
			} else {
				path = path + "&" + query;
			}
		}
		return new URL(path);
	}

	/**
	 * http请求
	 * 
	 * @param method      请求方法GET/POST
	 * @param path        请求路径
	 * @param timeout     连接超时时间 默认为5000
	 * @param readTimeout 读取超时时间 默认为5000
	 * @param data        数据
	 * @return
	 */
	private static String defaultConnection(String method, String path, int timeout, int readTimeout, String data,
			String encoding) throws Exception {
		String result = "";
		URL url = new URL(path);
		if (url != null) {
			HttpURLConnection conn = getConnection(method, url);
			conn.setConnectTimeout(timeout == 0 ? DEFAULT_CONNTIME : timeout);
			conn.setReadTimeout(readTimeout == 0 ? DEFAULT_READTIME : readTimeout);
			if (data != null && !"".equals(data)) {
				OutputStream output = conn.getOutputStream();
				output.write(data.getBytes("UTF-8"));
				output.flush();
			}
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream input = conn.getInputStream();
				result = IOUtil.inputStreamToString(input, encoding);
				conn.disconnect();
			}
		}
		return result;
	}

	/**
	 * 根据url的协议选择对应的请求方式
	 * 
	 * @param method 请求的方法
	 * @return
	 * @throws IOException
	 */
	private static HttpURLConnection getConnection(String method, URL url) throws IOException {
		HttpURLConnection conn = null;
		if ("https".equals(url.getProtocol())) {
			SSLContext context = null;
			try {
				context = SSLContext.getInstance("SSL", "SunJSSE");
				context.init(new KeyManager[0], new TrustManager[] { new MyX509TrustManager() },
						new java.security.SecureRandom());
			} catch (Exception e) {
				throw new IOException(e);
			}
			HttpsURLConnection connHttps = (HttpsURLConnection) url.openConnection();
			connHttps.setSSLSocketFactory(context.getSocketFactory());
			connHttps.setHostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}
			});
			conn = connHttps;
		} else {
			conn = (HttpURLConnection) url.openConnection();
		}
		conn.setRequestMethod(method);
		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		return conn;
	}
}

/**
 * 自定义信任管理
 * 
 * @author
 * @date
 */
class MyX509TrustManager implements X509TrustManager {

	public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
	}

	public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
	}

	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}

}