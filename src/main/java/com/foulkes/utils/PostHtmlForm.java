package com.foulkes.utils;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.*;

/**
 * Created by danfoulkes on 29/03/2016.
 */
public class PostHtmlForm {

    private String proxy;
    private  int proxyPort;
    HttpURLConnection httpCon;
    URL url;

    public PostHtmlForm(final String proxy, final int proxyPort){
        this.proxy = proxy;
        this.proxyPort = proxyPort;
    }


    /**
     * Send Html Post Request to server
     * @param postUrl
     * @return
     */
    public Response sendPost(String postUrl) throws IOException {
        url= new URL(postUrl);
        if(proxy != null && !"".equals(proxy)){
            httpCon = (HttpURLConnection) url.openConnection(getProxy());
        }
        else {
            httpCon = (HttpURLConnection) url.openConnection();
        }
        httpCon.setDoOutput(true);
        httpCon.setRequestMethod("POST");
        OutputStreamWriter out = new OutputStreamWriter(
                httpCon.getOutputStream());
        Response response  = new Response(httpCon.getResponseCode(), httpCon.getResponseMessage());
        out.close();
        return response;
    }



    private Proxy getProxy(){
        return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxy, proxyPort));
    }
}
