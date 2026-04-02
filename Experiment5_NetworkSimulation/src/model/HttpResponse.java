package model;

public class HttpResponse {
    private int statusCode;
    private String serverHeader;
    private String body;

    public HttpResponse(int statusCode, String serverHeader, String body) {
        this.statusCode = statusCode;
        this.serverHeader = serverHeader;
        this.body = body;
    }

    public int getStatusCode() { return statusCode; }

    @Override
    public String toString() {
        return "HTTP/1.1 " + statusCode + (statusCode == 200 ? " OK" : " Error") + 
               "\nServer: " + serverHeader + 
               "\n\n" + body;
    }
}
