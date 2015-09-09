package request_utils;

import org.junit.Before;
import org.junit.Test;
import request_utils.CustomInterceptor;
import retrofit.RequestInterceptor;

import static org.assertj.core.api.StrictAssertions.assertThat;
import static org.mockito.Mockito.*;

public class CustomInterceptorTest {

    private RequestInterceptor.RequestFacade request;
    private String[] headerKeys;
    private String[] headerValues;
    private String[] queryKeys;
    private String[] queryValues;
    private CustomInterceptor.Builder builder;

    @Before
    public void setUp() throws Exception {
        request = mock(RequestInterceptor.RequestFacade.class);
        headerKeys = new String[]{"hkey1", "hkey2"};
        headerValues = new String[]{"hvalue1", "hvalue2"};
        queryKeys = new String[]{"qkey1", "qkey2"};
        queryValues = new String[]{"qvalue1", "qvalue2"};
        builder = new CustomInterceptor.Builder();
    }

    @Test
    public void testHeaders() {
        builder.headerKeys(headerKeys)
                .headerValues(headerValues);
        CustomInterceptor interceptor = builder.build();
        interceptor.intercept(request);
        verify(request, times(1)).addHeader(headerKeys[0], headerValues[0]);
        verify(request, times(1)).addHeader(headerKeys[1], headerValues[1]);
    }

    @Test
    public void testQueryParams(){
        builder.queryKeys(queryKeys)
                .queryValues(queryValues);
        CustomInterceptor interceptor = builder.build();
        interceptor.intercept(request);
        verify(request, times(1)).addQueryParam(queryKeys[0], queryValues[0]);
        verify(request, times(1)).addQueryParam(queryKeys[1], queryValues[1]);
    }

}