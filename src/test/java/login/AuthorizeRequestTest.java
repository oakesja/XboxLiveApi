package login;

import helpers.AnswerWithSelf;
import login.models.AuthorizeProperties;
import login.models.LoginPostBody;
import org.junit.Before;
import org.junit.Test;
import request_utils.CustomInterceptor;
import request_utils.Header;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

import static org.mockito.Mockito.*;

public class AuthorizeRequestTest {

    private RestAdapter.Builder builder;
    private LoginService service;
    private String accessToken;

    @Before
    public void setup() {
        accessToken = "token";
        Class<RestAdapter.Builder> classToMock = RestAdapter.Builder.class;
        builder = mock(classToMock, new AnswerWithSelf(classToMock));
        RestAdapter adapter = mock(RestAdapter.class);
        service = mock(LoginService.class);
        when(builder.build()).thenReturn(adapter);
        when(adapter.create(LoginService.class)).thenReturn(service);
    }

    @Test
    public void authorize() {
        AuthorizeRequest adapter = new AuthorizeRequest(accessToken, builder);
        adapter.execute();
        verify(builder, times(1)).setEndpoint("https://user.auth.xboxlive.com");
        verify(builder, times(1)).setRequestInterceptor(getInterceptor());
        verify(service, times(1)).authorize(expectedPostBody());
    }

    private RequestInterceptor getInterceptor() {
        Header header = new LoginHeader();
        return new CustomInterceptor.Builder()
                .headerKeys(header.keys())
                .headerValues(header.values())
                .build();
    }

    private LoginPostBody expectedPostBody() {
        AuthorizeProperties properties = new AuthorizeProperties(accessToken);
        return new LoginPostBody(properties);
    }
}