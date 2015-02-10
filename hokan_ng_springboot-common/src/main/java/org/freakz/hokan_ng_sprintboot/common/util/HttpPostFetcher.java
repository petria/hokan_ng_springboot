package org.freakz.hokan_ng_sprintboot.common.util;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.PropertyName;
import org.freakz.hokan_ng_sprintboot.common.service.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import static org.freakz.hokan_ng_sprintboot.common.util.StaticStrings.HTTP_USER_AGENT;

/**
 * <br>
 * <p/>
 * User: petria<br>
 * Date: 29-Jun-2007<br>
 * Time: 12:53:37<br>
 * <p/>
 */
@Slf4j
@Component
@Scope("prototype")
public class HttpPostFetcher {

  @Autowired
  private Properties properties;

  private StringBuilder _htmlBuffer;

  public HttpPostFetcher() {
  }

  public void fetch(String urlStr, String encoding, String... params) throws IOException {

    URL url = new URL(urlStr);
    String proxyHost = properties.getPropertyAsString(PropertyName.PROP_SYS_HTTP_PROXY_HOST, null);
    int proxyPort = properties.getPropertyAsInt(PropertyName.PROP_SYS_HTTP_PROXY_PORT, -1);

    URLConnection conn;
    if (proxyHost != null && proxyPort != -1) {
      SocketAddress address = new
          InetSocketAddress(proxyHost, proxyPort);
      Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
      conn = url.openConnection(proxy);
    } else {
      conn = url.openConnection();
    }

    HttpURLConnection connection = (HttpURLConnection) conn;
    connection.setRequestProperty("User-Agent", properties.getPropertyAsString(PropertyName.PROP_SYS_HTTP_USER_AGENT, HTTP_USER_AGENT));
    connection.setRequestMethod("POST");
    connection.setDoOutput(true);

    PrintWriter out = new PrintWriter(connection.getOutputStream());

    // encode parameters and concate
    StringBuilder sb = new StringBuilder();
    for (String param : params) {
      sb.append(URLEncoder.encode(param, encoding));
//      sb.append(param);
      sb.append("&");
    }
    out.println(sb.toString());
    out.close();


    String headerEncoding;
    if (encoding != null) {
      headerEncoding = encoding;
    } else {
      headerEncoding = HttpPageFetcher.getEncodingFromHeaders(connection);
    }

    InputStream in = connection.getInputStream();
    InputStreamReader isr = new InputStreamReader(in, headerEncoding);

    BufferedReader br =
        new BufferedReader(isr);

    _htmlBuffer = new StringBuilder();
    String line;
    while ((line = br.readLine()) != null) {
      _htmlBuffer.append(line);
    }
    in.close();

    log.info("HttpPost fetched: " + urlStr);

  }

  /**
   * @return the String containing returned HTML-page from the URL
   */
  public String getHtmlBuffer() {
    return _htmlBuffer.toString();
  }


}
