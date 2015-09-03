package login;

import org.junit.Before;
import org.junit.Test;
import request_utils.CustomInterceptor;
import request_utils.StringConverter;
import retrofit.RestAdapter;
import retrofit.converter.Converter;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class DynamicRequestAdapterTest {

    private String[] headerKeys;
    private String[] headerValues;
    private String[] queryKeys;
    private String[] queryValues;
    private RestAdapter.Builder restAdapterBuilder;
    private RestAdapter adapter;
    private DynamicService service;
    private String url;
    private DynamicRequestAdapter.Builder builder;

    @Before
    public void setup() {
        headerKeys = new String[]{"hkey1", "hkey2"};
        headerValues = new String[]{"hvalue1", "hvalue2"};
        queryKeys = new String[]{"qkey1", "qkey2"};
        queryValues = new String[]{"qvalue1", "qvalue2"};
        restAdapterBuilder = mock(RestAdapter.Builder.class);
        adapter = mock(RestAdapter.class);
        service = mock(DynamicService.class);
        builder = new DynamicRequestAdapter.Builder();
        url = "http://www.google.com";
    }

    @Test
    public void getString() throws InterruptedException {
        stubMethods();
        getAdapter().getString();
        verify(restAdapterBuilder, times(1)).setEndpoint(url);
        verify(restAdapterBuilder, times(1)).setConverter(any(StringConverter.class));
        verify(restAdapterBuilder, times(1)).setRequestInterceptor(getCustomInterceptor());
        verify(service, times(1)).getString();
    }

    @Test
    public void getResponse() throws InterruptedException {
        stubMethods();
        getAdapter().getResponse();
        verify(restAdapterBuilder, times(1)).setEndpoint(url);
        verify(restAdapterBuilder, times(0)).setConverter(any(Converter.class));
        verify(restAdapterBuilder, times(1)).setRequestInterceptor(getCustomInterceptor());
        verify(service, times(1)).getResponse();
    }

    @Test
    public void post() throws InterruptedException {
        stubMethods();
        getAdapter().post(new HashMap<>());
        verify(restAdapterBuilder, times(1)).setEndpoint(url);
        verify(restAdapterBuilder, times(0)).setConverter(any(Converter.class));
        verify(restAdapterBuilder, times(1)).setRequestInterceptor(getCustomInterceptor());
        verify(service, times(1)).post(any(Map.class));
    }

    private void stubMethods() {
        when(restAdapterBuilder.setEndpoint(url)).thenReturn(restAdapterBuilder);
        when(restAdapterBuilder.setConverter(any(StringConverter.class))).thenReturn(restAdapterBuilder);
        when(restAdapterBuilder.setRequestInterceptor(any(CustomInterceptor.class))).thenReturn(restAdapterBuilder);
        when(restAdapterBuilder.build()).thenReturn(adapter);
        when(adapter.create(DynamicService.class)).thenReturn(service);
    }

    private DynamicRequestAdapter getAdapter() {
        return builder.headerKeys(headerKeys)
                .headerValues(headerValues)
                .queryKeys(queryKeys)
                .queryValues(queryValues)
                .restAdapterBuilder(restAdapterBuilder)
                .url(url)
                .build();
    }

    private CustomInterceptor getCustomInterceptor() {
        return new CustomInterceptor.Builder()
                .headerValues(headerValues)
                .headerKeys(headerKeys)
                .queryValues(queryValues)
                .queryKeys(queryKeys)
                .build();
    }
}