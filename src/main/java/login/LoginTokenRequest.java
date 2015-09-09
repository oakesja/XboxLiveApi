package login;

import request_utils.DynamicRequestAdapter;
import retrofit.client.Header;
import retrofit.client.Response;
import rx.Observable;
import rx.exceptions.OnErrorThrowable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class LoginTokenRequest {
    private DynamicRequestAdapter.Builder builder;
    private String username;
    private String password;
    private LoginInformation loginInfo;

    public LoginTokenRequest(String username, String password, LoginInformation loginInfo){
        this.username = username;
        this.password = password;
        this.loginInfo = loginInfo;
        this.builder = new DynamicRequestAdapter.Builder();
    }

    public LoginTokenRequest(String username, String password, LoginInformation loginInfo, DynamicRequestAdapter.Builder builder) {
        this.username = username;
        this.loginInfo = loginInfo;
        this.password = password;
        this.builder = builder;
    }

    public Observable<String> execute() {
        DynamicRequestAdapter adapter = builder.url(loginInfo.getUrl())
                .queryKeys(getQueryKeys())
                .queryValues(getQueryValues())
                .build();
        return adapter.getResponse().map(this::getToken);
    }

    private String getToken(Response response) {
        Header header = getHeader(response);
        Pattern pattern = Pattern.compile(".*access_token=(\\S*)");
        Matcher matcher = pattern.matcher(header.getValue());
        if(matcher.matches())
            return matcher.group(1);
        throw OnErrorThrowable.from(new Throwable("Could not parse access token from: " + header.getValue()));
    }

    private Header getHeader(Response response) {
        Header header = null;
        for (int i = 0; i < response.getHeaders().size(); i++) {
            if(response.getHeaders().get(i).getName().equals("Location"))
                header = response.getHeaders().get(i);
        }
        return header;
    }

    private String[] getQueryKeys() {
        return new String[]{"login", "passwd", "PPFT", "SI", "type", "NewUser", "LoginOptions", "i3", "m1", "m3", "i12",
                "i17", "i17"};
    }

    private String[] getQueryValues() {
        return new String[]{username, password, loginInfo.getPpft(), "Passpor", "Sign in", "11", "1", "1",
                "36728", "768", "1184", "0", "1", "0", "__Login_Host|1"};
    }
}
