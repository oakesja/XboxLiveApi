package login;

import helpers.MockedDynamicRequestAdapterBuilder;
import org.junit.Before;
import org.junit.Test;
import rx.Observable;

import static org.mockito.Mockito.*;
import static uk.co.ribot.assertjrx.api.Assertions.assertThat;

public class LoginInfoRequestTest {

    private DynamicRequestAdapter.Builder builder;
    private DynamicRequestAdapter adapter;
    private String[] keys;
    private String[] values;
    private LoginInformation loginInfo;
    private String url;

    @Before
    public void setup() {
        adapter = mock(DynamicRequestAdapter.class);
        url = "https://login.live.com/oauth20_authorize.srf";
        keys = new String[]{"client_id", "redirect_uri", "response_type", "display", "scope", "locale"};
        values = new String[]{"0000000048093EE3",
                "https://login.live.com/oauth20_desktop.srf",
                "token",
                "touch",
                "service::user.auth.xboxlive.com::MBI_SSL",
                "en"};
    }

    @Test
    public void getLoginInformation() {
        mockResponse(mockedResponse());
        assertThat(createLoginRequest().execute().toBlocking())
                .completes()
                .emitsSingleValue(loginInfo);
        verify(builder).queryKeys(keys);
        verify(builder).queryValues(values);
        verify(builder).url(url);
    }

    //TODO create failsWithMessage for assertj-rx lib
    @Test
    public void getLoginInformationFailed() {
        mockResponse("test");
        String msg = "Could not parse login info from: test";
        assertThat(createLoginRequest().execute().toBlocking())
                .fails();
    }

    private LoginInfoRequest createLoginRequest() {
        return new LoginInfoRequest(builder);
    }

    private void mockResponse(String response) {
        when(adapter.getString()).thenReturn(Observable.just(response));
        builder = new MockedDynamicRequestAdapterBuilder(adapter).getMockedBuilder();
        when(builder.build()).thenReturn(adapter);
    }

    private String mockedResponse() {
        String tag = "SOMETAG";
        String postUrl = "http://some.url.com";
        loginInfo = new LoginInformation(tag, postUrl);
        return "sFTTag: '<input type=\"hidden\" name=\"PPFT\" id=\"i0327\" value=\"" + tag + "\"/>'," + "urlPost: '" + postUrl + "',";
    }
}
