package login;

public class LoginAuthenticateRequest {
    private String accessToken;
    private DynamicRequestAdapter.Builder builder;

    public LoginAuthenticateRequest(String accessToken) {
        this.accessToken = accessToken;
    }

    public LoginAuthenticateRequest(String accessToken, DynamicRequestAdapter.Builder builder) {
        this.accessToken = accessToken;
        this.builder = builder;
    }
}
