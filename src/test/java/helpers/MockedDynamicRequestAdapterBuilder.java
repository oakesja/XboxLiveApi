package helpers;

import login.DynamicRequestAdapter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockedDynamicRequestAdapterBuilder {
    private DynamicRequestAdapter.Builder builder;

    public MockedDynamicRequestAdapterBuilder(DynamicRequestAdapter adapter) {
        builder = mock(DynamicRequestAdapter.Builder.class,
                new AnswerWithSelf(DynamicRequestAdapter.Builder.class));
        when(builder.build()).thenReturn(adapter);
    }

    public DynamicRequestAdapter.Builder getMockedBuilder() {
        return builder;
    }

}

