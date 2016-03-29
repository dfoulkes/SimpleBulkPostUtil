package com.foulkes.utils;

import java.io.*;

/**
 * Created by danfoulkes on 29/03/2016.
 */
public class ProcessLinkFile {


    File urls;
    PostHtmlForm postHtmlForm;
    String proxy;
    int port;

    public ProcessLinkFile(String filePath) {
        setUrls(new File(filePath));
    }

    public Boolean fileExists() {
        return urls.exists();
    }

    public File getUrls() {
        return urls;
    }

    public void setUrls(File urls) {
        this.urls = urls;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setProxy(String proxy) {
        this.proxy = proxy;
    }

    public void start() {
        postHtmlForm = new PostHtmlForm(proxy, port);
        BufferedReader br = null;

        try {

            String currentUrl;

            br = new BufferedReader(new FileReader(urls));
            Response response;
            while ((currentUrl = br.readLine()) != null) {
                response = postHtmlForm.sendPost(currentUrl);
                System.out.println("Response for "+currentUrl+" Response Code:"+response.getResponseCode());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    /**
     * @param args url, proxy, proxy port
     */

    public static void main(String[] args) {
        System.out.println("Application starting....");
        if (args.length < 1) {
            System.out.println("Please supply a path to file");
        }

        ProcessLinkFile processLinkFile = new ProcessLinkFile(args[0]);

        if (processLinkFile.fileExists()) {
            if (args.length == 3) {
                processLinkFile.setProxy(args[1]);
                processLinkFile.setPort(Integer.valueOf(args[2]));
            }

            processLinkFile.start();
        } else {
            System.out.println("cannot find file with a path of:" + processLinkFile.getUrls().getAbsolutePath());
        }
    }
}
