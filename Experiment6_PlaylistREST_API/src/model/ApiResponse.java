package model;

public class ApiResponse<T> {
    private int statusCode;
    private String message;
    private T data;

    public ApiResponse(int statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public int getStatusCode() { return statusCode; }
    public String getMessage() { return message; }
    public T getData() { return data; }

    @Override
    public String toString() {
        return "HTTP/1.1 " + statusCode + " " + message + 
               "\n{ \"status\": " + statusCode + ", \"message\": \"" + message + "\"" + 
               (data != null ? ", \"data\": " + data.toString() : "") + " }";
    }
}
