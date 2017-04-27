package com.example.atakanyenel.myapplication.util;

/**
 * Created by eralpsahin on 24.02.2017.
 */

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpHelper {


    public static String getData(RequestPackage p) {
        InputStream is = null;
        String uri = p.getEndPoint();

        //TODO:: to be deleted
//        if (p.getMethod() == "GET")
//            uri += "?" + p.getEncodedParams();
        try {
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(p.getMethod());
            con.setRequestProperty("Content-Type", "application/json");
            con.setConnectTimeout(100000);
            con.setReadTimeout(100000);
            if (p.getMethod() == "POST") {
                con.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
                writer.write(p.getJsonObject().toString());
                writer.flush();
            }
            con.connect();

            int responseCode = con.getResponseCode();
            if (responseCode != 200) {
                throw new IOException("Got error code " + responseCode);
            }
            is = con.getInputStream();
            return readStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * Reads an InputStream and converts it to a String.
     *
     * @param stream
     * @return
     * @throws IOException
     */
    private static String readStream(InputStream stream) throws IOException {

        byte[] buffer = new byte[1024];
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        BufferedOutputStream out = null;
        try {
            int length = 0;
            out = new BufferedOutputStream(byteArray);
            while ((length = stream.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            out.flush();
            return byteArray.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}