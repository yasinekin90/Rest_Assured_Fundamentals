package ecommerceapitest.pojo;

public class LoginResponse {

    private String token;
    private String userId;
    private String message;

    public LoginResponse(String token, String userId, String message) {
        this.token = token;
        this.userId = userId;
        this.message = message;
    }

    public LoginResponse() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
