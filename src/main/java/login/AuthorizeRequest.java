package login;

import login.models.AuthorizeProperties;
import login.models.LoginResponse;
import login.models.LoginPostBody;
import request_utils.CustomInterceptor;
import request_utils.Header;
import retrofit.RestAdapter;
import rx.Observable;

class AuthorizeRequest {

    private static final String AUTHORIZE_URL = "https://user.auth.xboxlive.com";
    private RestAdapter.Builder builder;
    private String accessToken;

    public AuthorizeRequest(String accessToken) {
        this.accessToken = accessToken;
        this.builder = new RestAdapter.Builder();
    }

    public AuthorizeRequest(String accessToken, RestAdapter.Builder builder) {
        this.accessToken = accessToken;
        this.builder = builder;
    }

    public Observable<LoginResponse> execute() {
        RestAdapter adapter = getBaseBuilder().setEndpoint(AUTHORIZE_URL).build();
        LoginService service = adapter.create(LoginService.class);
        AuthorizeProperties properties = new AuthorizeProperties(accessToken);
        LoginPostBody postBody = new LoginPostBody(properties);
        return service.authorize(postBody);
    }

    public RestAdapter.Builder getBaseBuilder() {
        Header header = new LoginHeader();
        CustomInterceptor interceptor = new CustomInterceptor.Builder()
                .headerKeys(header.keys())
                .headerValues(header.values())
                .build();
        return builder.setRequestInterceptor(interceptor);
    }
}
