package ru.newpointer.currency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

/**
 * Получение строкового представления страницы по указанному адресу
 */
public class HttpReader {

    private final static Logger logger = LoggerFactory.getLogger(HttpReader.class);
    private URL url;


    public HttpReader(String url) throws MalformedURLException {
        this.url = new URL(url);
        logger.info("HTTP reader created for URL: [{}]", this.url);
    }

    public String getPageContent() throws IOException {
        StringBuffer sb = new StringBuffer();
        URLConnection connection = url.openConnection();
        try (BufferedReader br = new BufferedReader(
                     new InputStreamReader(connection.getInputStream(), Charset.forName("CP1251")))) {
            String inputLine;

            while ((inputLine = br.readLine()) != null) {
//                logger.info("Line read: {}", inputLine);
                sb.append(inputLine);
            }
            return sb.toString();
        }
    }
}
