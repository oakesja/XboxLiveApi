package login;

import helpers.MockedDynamicRequestAdapterBuilder;
import org.junit.Before;
import org.junit.Test;
import retrofit.client.Header;
import retrofit.client.Response;
import rx.Observable;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static uk.co.ribot.assertjrx.api.Assertions.assertThat;

public class LoginTokenRequestTest {

    private DynamicRequestAdapter adapter;
    private LoginInformation loginInfo;
    private String username;
    private String password;
    private String[] keys;
    private String[] values;
    private DynamicRequestAdapter.Builder builder;
    private String token;

    @Before
    public void setup() {
        adapter = mock(DynamicRequestAdapter.class);
        loginInfo = new LoginInformation("ppft", "example.com");
        username = "username";
        password = "password";
        keys = new String[]{"login", "passwd", "PPFT", "SI", "type", "NewUser", "LoginOptions", "i3", "m1", "m3", "i12",
                "i17", "i17"};
        values = new String[]{username, password, loginInfo.getPpft(), "Passpor", "Sign in", "11", "1", "1",
                "36728", "768", "1184", "0", "1", "0", "__Login_Host|1"};
        token = "token";
    }

    @Test
    public void getToken() {
        mockValidResponse();
        assertThat(createRequest().execute().toBlocking())
                .completes()
                .emitsSingleValue(token);
        verify(builder).queryKeys(keys);
        verify(builder).queryValues(values);
        verify(builder).url(loginInfo.getUrl());
    }

    private void mockValidResponse() {
        List<Header> headers = new ArrayList<>();
        String urlToken = "https://login.live.com/oauth20_desktop.srf?lc=1033#access_token=" + token;
        headers.add(new Header("Location", urlToken));
        Response response = new Response(loginInfo.getUrl(), 400, "reason", headers, null);
        when(adapter.getResponse()).thenReturn(Observable.just(response));
        builder = new MockedDynamicRequestAdapterBuilder(adapter).getMockedBuilder();
        when(builder.build()).thenReturn(adapter);
    }

    private LoginTokenRequest createRequest() {
        return new LoginTokenRequest(username, password, loginInfo, builder);
    }
}
