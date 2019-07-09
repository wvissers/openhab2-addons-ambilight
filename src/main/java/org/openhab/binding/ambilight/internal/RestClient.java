/**
 * Copyright (c) 2010-2017 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.ambilight.internal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;

/**
 * @author Wim Vissers - Initial contribution
 */
public class RestClient {

    private static final int CONNECTION_TIMEOUT = 20000;

    private String _endPoint;

    /**
     * Create a new client with the given server and port address.
     *
     * @param endPoint
     */
    public RestClient(String server, int port, int version) {
        _endPoint = "http://" + server + ":" + port + "/" + version;
    }

    private String getResponse(String resourcePath) {
        StringBuilder output = new StringBuilder();
        try {
            URL url = new URL(_endPoint + resourcePath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setConnectTimeout(CONNECTION_TIMEOUT);
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String line;
            System.out.println("Output from Server .... \n");
            while ((line = br.readLine()) != null) {
                output.append(line);
            }
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    private String postData(String resourcePath, String data) {
        StringBuilder output = new StringBuilder();
        try {
            URL url = new URL(_endPoint + resourcePath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-type", "application/json");
            conn.setRequestProperty("Content-length", "" + data.length());
            conn.setDoOutput(true);
            conn.setConnectTimeout(CONNECTION_TIMEOUT);
            OutputStream out = conn.getOutputStream();
            out.write(data.getBytes());
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String line;
            System.out.println("Output from Server .... \n");
            while ((line = br.readLine()) != null) {
                output.append(line);
            }
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    /**
     * The jointSpace mode resource.
     *
     * @param json
     */
    public void setMode(ModeBean modeBean) {
        Gson gson = new Gson();
        postData("/ambilight/mode", gson.toJson(modeBean));
    }

    /**
     * The jointSpace mode resource.
     *
     * @return
     */
    public ModeBean getMode() {
        Gson gson = new Gson();
        return gson.fromJson(getResponse("/ambilight/mode"), ModeBean.class);
    }

    /**
     * The jointSpace topology resource.
     *
     * @return
     */
    public TopologyBean getTopology() {
        Gson gson = new Gson();
        return gson.fromJson(getResponse("/ambilight/topology"), TopologyBean.class);
    }

    public LayerBean getCached() {
        Gson gson = new Gson();
        return gson.fromJson(getResponse("/ambilight/cached"), LayerBean.class);
    }

    public void setCached(LayerBean layerBean) {
        Gson gson = new Gson();
        postData("/ambilight/cached", gson.toJson(layerBean));
    }

}
