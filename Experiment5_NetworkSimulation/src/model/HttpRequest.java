package model;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private String url;
    private String method; // GET, POST
    private String ipAddress;
    private Map<String, String> headers;

    public HttpRequest(String url, String method) {
        this.url = url;
        this.method = method;
        this.headers = new HashMap<>();
    }

    public String getUrl() { return url; }
    public String getMethod() { return method; }
    public String getIpAddress() { return ipAddress; }
    
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
    
    public void addHeader(String key, String value) {
        headers.put(key, value);
    }
    
    public String getHost() {
        // Simple host extraction from URL (e.g., https://www.google.com/path -> www.google.com)
        return url.replace("https://", "").replace("http://", "").split("/")[0];
    }

    @Override
    public String toString() {
        return method + " " + url + " HTTP/1.1\nHost: " + getHost();
    }
}
