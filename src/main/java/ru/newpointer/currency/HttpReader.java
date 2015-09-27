package ru.newpointer.currency;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

/**
 * Created by isavin on 27.09.15.
 */
public class HttpReader {

    private URL url;


    public HttpReader(String url) throws MalformedURLException {
        this.url = new URL(url);
    }

    public String getPageContent() throws IOException {
        StringBuffer sb = new StringBuffer();
        URLConnection connection = url.openConnection();
        try (BufferedReader br = new BufferedReader(
                     new InputStreamReader(connection.getInputStream(), Charset.forName("CP1251")))) {
            String inputLine;

            while ((inputLine = br.readLine()) != null) {
                System.out.println(inputLine);
                sb.append(inputLine);
            }
            return sb.toString();
        }
    }
}
