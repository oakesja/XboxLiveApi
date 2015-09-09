package login;

import login.models.LoginResponse;
import login.models.LoginPostBody;
import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;

interface LoginService {
    @POST("/user/authenticate")
    Observable<LoginResponse> authorize(@Body LoginPostBody body);

    @POST("/xsts/authorize")
    Observable<LoginResponse> authenticate(@Body LoginPostBody body);
}
