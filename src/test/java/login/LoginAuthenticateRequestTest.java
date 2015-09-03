package login;

import helpers.MockedDynamicRequestAdapterBuilder;
import org.junit.Before;
import request_utils.Header;
import rx.Observable;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginAuthenticateRequestTest {
    private DynamicRequestAdapter.Builder builder;
    private DynamicRequestAdapter adapter;
    private String accessToken;
    private String url;
    private Header header;
    private Map<String, Object> postBody;

    @Before
    public void setup() {
        adapter = mock(DynamicRequestAdapter.class);
        url = "https://user.auth.xboxlive.com/user/authenticate";
        accessToken = "token";
        header = new LoginHeader();
        setUpPostBody();
    }

    private void setUpPostBody() {
        HashMap<String, String> postBodyProperties = new HashMap<>();
        postBodyProperties.put("AuthMethod", "RPS");
        postBodyProperties.put("SiteName", "user.auth.xboxlive.com");
        postBodyProperties.put("RpsTicket", accessToken);
        postBody = new HashMap<>();
        postBody.put("RelyingParty", "http://auth.xboxlive.com");
        postBody.put("TokenType", "JWT");
        postBody.put("Properties", postBodyProperties);
    }

    private LoginAuthenticateRequest createLoginRequest() {
        return new LoginAuthenticateRequest(accessToken, builder);
    }

    private void mockResponse(String response) {
        when(adapter.getString()).thenReturn(Observable.just(response));
        builder = new MockedDynamicRequestAdapterBuilder(adapter).getMockedBuilder();
        when(builder.build()).thenReturn(adapter);
    }
}