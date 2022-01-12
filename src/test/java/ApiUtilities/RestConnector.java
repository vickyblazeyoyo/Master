package ApiUtilities;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import Utilities.Utilities;

public class RestConnector {
	 protected static Map<String, String> cookies;
	    /**
	     * This is the URL to the ALM application.
	     * For example: http://myhost:8080/qcbin.
	     * Make sure that there is no slash at the end.
	     */
	    protected String serverUrl;
	    protected String domain;
	    protected String project;

	    public RestConnector init(
	            Map<String, String> cookies,
	            String serverUrl,
	            String domain,
	            String project) {

	        this.cookies = cookies;
	        this.serverUrl = serverUrl;
	        this.domain = domain;
	        this.project = project;

	        return this;
	    }

	    private RestConnector() {}

	    private static RestConnector instance = new RestConnector();

	    public static RestConnector getInstance() {
	        return instance;
	    }

	    public String buildEntityCollectionUrl(String entityType) {
	        return buildUrl("rest/domains/"
	                        + domain
	                        + "/projects/"
	                        + project
	                        + "/"
	                        + entityType
	                        + "");
	    }

	    /**
	     * @param path
	     *            on the server to use
	     * @return a url on the server for the path parameter
	     */
	    public String buildUrl(String path) {

	        return String.format("%1$s/%2$s", serverUrl, path);
	    }

	    /**
	     * @return the cookies
	     */
	    public Map<String, String> getCookies() {
	        return cookies;
	    }

	    /**
	     * @param cookies
	     *            the cookies to set
	     */
	    public void setCookies(Map<String, String> cookies) {
	        this.cookies = cookies;
	    }

	    public Response httpPut(String url, byte[] data, Map<String,
	           String> headers) throws Throwable {

	        return doHttp("PUT", url, null, data, headers, cookies);
	    }

	    public Response httpPost(String url, byte[] data, Map<String,
	           String> headers) throws Throwable {

	        return doHttp("POST", url, null, data, headers, cookies);
	    }
	    

	    public Response httpDelete(String url, Map<String, String> headers)
	           throws Throwable {

	        return doHttp("DELETE", url, null, null, headers, cookies);
	    }

	    public Response httpGet(String url, String queryString, Map<String,
	           String> headers)throws Throwable {
	        return doHttp("GET", url, queryString, null, headers, cookies);
	        
	        
	    }
	
	public static  Response doHttp(
			String type,
			String url,
			String queryString,
			byte[] data,
			 Map<String, String> headers,
			  Map<String, String> cookies) throws NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException, Throwable {
		
		//Adding Quary parameter with Normal URL
		if ((queryString != null) && !queryString.isEmpty()) {
			url += "?" + queryString;
		}
		
		HttpURLConnection con = null;
		//This Method will add certificate and SSL for the request if needed
		con = createTrustManager(con);
		
		//Setting up Proxy if  needed
		if (url.contains("https://api-gw-uat.fisglobal.com")) {
			 Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.fisdev.local", 8080));
			 con = (HttpURLConnection) new URL(url).openConnection(proxy);
		}else {
			con = (HttpURLConnection) new URL(url).openConnection();
		}
		//setting up request method
		con.setRequestMethod(type);
		//In this method we are setting up headers,requestpayload
		prepareHttpRequest(con, headers, data);
		con.connect();
		//retriving response and giving it to response class
		Response ret = retrieveHtmlResponse(con);
		updateCookies(ret);
		
		
				return ret;
		

	}
	
	public static  HttpURLConnection createTrustManager(HttpURLConnection con) throws NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException, Throwable {
		String ENV, certPassword = null, certfilename = null;
		KeyManager[] kms = null;
		ENV=Utilities.ReadFromPropertyFile("Environment");
		if (ENV.equals("PROD")) {
			//Taking password certificate password from the Property file
			String certVar =  "PROD_Cert_Passwd";
			certPassword = Utilities.ReadFromPropertyFile(certVar);
			certfilename = "ProdCert.jks";
			System.out.println("certPasswd : " + certPassword );
			kms = trustCertificate(certfilename, certPassword);
			
		}
		 // Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
	                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	                	return null;
	                }
	                public void checkClientTrusted(X509Certificate[] certs, String authType) {
	                }
	                public void checkServerTrusted(X509Certificate[] certs, String authType) {
	                }
	            }
	        };
		
		// Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(kms, trustAllCerts, new java.security.SecureRandom());
        ((HttpsURLConnection) con).setDefaultSSLSocketFactory(sc.getSocketFactory());
       
     // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
     // Install the all-trusting host verifier
        ((HttpsURLConnection) con).setDefaultHostnameVerifier(allHostsValid);	
        return con;
		
		

	}
		
	
	//This method will load the certificate
	public static  KeyManager[] trustCertificate(String certfilename, String certPassword) throws NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException, Throwable  {
		KeyManager[] kms = null;
	    
			KeyStore ks = KeyStore.getInstance("JKS");
			//here we are taking certificate location and decrypting password and we are loading it
			 ks.load(new FileInputStream("src/test/resources/" + certfilename), Utilities.dstr(certPassword).toCharArray());
			 KeyManagerFactory kmf = KeyManagerFactory.getInstance("NewSunX509");
			 kmf.init(ks, Utilities.dstr(certPassword).toCharArray());
			  kmf.getKeyManagers();
			 
			  return kms ;

	}

	public static  void prepareHttpRequest(
			HttpURLConnection con,
			 Map<String, String> headers,
			 byte[] bytes
			
			) throws IOException {
		String contentType = null;
		if (headers != null) {
			//we are removing the content type if there is no payload eg:GET,DELETE services
			contentType = headers.remove("Content-Type");
	Iterator<Entry<String, String>> headersIterator=headers.entrySet().iterator();
	//taking header and sending for request 
	while (headersIterator.hasNext()) {
		Entry<String, String> header = headersIterator.next();
		con.setRequestProperty(header.getKey(), header.getValue());
		
		 // If there's data to attach to the request, it's handled here.
        // Note that if data exists, we take into account previously removed
        // content-type.
		if ((bytes != null) && (bytes.length > 0)) {
			 con.setDoOutput(true);
			//warning: if you add content-type header then you MUST send
	            // information or receive error.
	            //so only do so if you're writing information...
			 if (contentType != null) {
	                con.setRequestProperty("Content-Type", contentType);
	            }
			
			 OutputStream out = con.getOutputStream();
	            out.write(bytes);
	            out.flush();
	            out.close();
			
			
		}
		
		
	}
		}
		

	}
	
	public static Response retrieveHtmlResponse(HttpURLConnection con) throws IOException {
		  Response ret = new Response();
		  //taking response code and headers from con and setting up to response
		  ret.setStatusCode(con.getResponseCode());
		  ret.setResponseHeaders(con.getHeaderFields());
		  InputStream inputStream;
		  
		  //select the source of the input bytes, first try 'regular' input
	        try {
	            inputStream = con.getInputStream();
	        }

	        /*
	         If the connection to the server somehow failed, for example 404 or 500,
	         con.getInputStream() will throw an exception, which we'll keep.
	         We'll also store the body of the exception page, in the response data.
	         */
	        catch (Exception e) {

	            inputStream = con.getErrorStream();
	            ret.setFailure(e);
	        }
	        // This actually takes the data from the previously set stream
	        // (error or input) and stores it in a byte[] inside the response
	        ByteArrayOutputStream container = new ByteArrayOutputStream();
	        byte[] buf = new byte[1024];
	        int read;
	        //Take the response and put it in a container
	        while ((read = inputStream.read(buf, 0, 1024)) > 0) {
	            container.write(buf, 0, read);
	        }
           //Putting the response in to response class
	        ret.setResponseData(container.toByteArray());
	        return ret;
	}
	
	public static void updateCookies(Response response) {
		Iterable<String> newCookies =
	            response.getResponseHeaders().get("Set-Cookie");

	}
	public String getCookieString() {

        StringBuilder sb = new StringBuilder();

        if (!cookies.isEmpty()) {

            Set<Entry<String, String>> cookieEntries =
                cookies.entrySet();
            for (Entry<String, String> entry : cookieEntries) {
            	sb.append(entry.getKey()).append("=").append(entry.getValue()).append(";");
            }
        }

        String ret = sb.toString();

        return ret;
    }

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

