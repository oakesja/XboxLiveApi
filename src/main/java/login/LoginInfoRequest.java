package login;

import request_utils.DynamicRequestAdapter;
import rx.Observable;
import rx.exceptions.OnErrorThrowable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class LoginInfoRequest {
    private static final String URL = "https://login.live.com/oauth20_authorize.srf";

    private DynamicRequestAdapter.Builder restAdapterBuilder;

    public LoginInfoRequest() {
        restAdapterBuilder = new DynamicRequestAdapter.Builder();
    }

    public LoginInfoRequest(DynamicRequestAdapter.Builder restAdapterBuilder) {
        this.restAdapterBuilder = restAdapterBuilder;
    }

    public Observable<LoginInformation> execute() {
        DynamicRequestAdapter adapter = restAdapterBuilder.queryKeys(getQueryKeys())
                .queryValues(getQueryValues())
                .url(URL)
                .build();
        return adapter.getString().map(this::parseLoginInfo);
    }

    private LoginInformation parseLoginInfo(String s) {
        Pattern pattern = Pattern.compile(".*sFTTag.*value=\"(.*)\"/>.*urlPost: '(\\S*)'.*");
        Matcher matcher = pattern.matcher(s);
        if (matcher.matches()) {
            return new LoginInformation(matcher.group(1), matcher.group(2));
        }
        throw OnErrorThrowable.from(new Throwable("Could not parse login info from: " + s));
    }

    private String[] getQueryKeys() {
        return new String[]{"client_id", "redirect_uri", "response_type", "display", "scope", "locale"};
    }

    private String[] getQueryValues() {
        return new String[]{"0000000048093EE3",
                "https://login.live.com/oauth20_desktop.srf",
                "token",
                "touch",
                "service::user.auth.xboxlive.com::MBI_SSL",
                "en"};
    }
}


