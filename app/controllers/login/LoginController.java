package controllers.login;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import controllers.login.dtos.LoginDTO;
import models.user.User;
import models.user.UserRepository;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import utils.secure.SecureUtil;

import java.util.concurrent.CompletionStage;

/**
 * Controller for login services.
 * Created by Luis Talero on 12 Jun 2020
 */
public class LoginController extends Controller {

    private final SecureUtil secureUtil;
    private final UserRepository userRepository;
    private final HttpExecutionContext httpExecutionContext;

    @Inject
    public LoginController(SecureUtil secureUtil, UserRepository userRepository, HttpExecutionContext httpExecutionContext) {
        this.secureUtil = secureUtil;
        this.userRepository = userRepository;
        this.httpExecutionContext = httpExecutionContext;
    }

    /**
     * Authenticate the user
     * @param request JSON request
     * @return 200 If authentication success - 401 if authentication fails
     */
    public CompletionStage<Result> login(Http.Request request) {
        JsonNode json = request.body().asJson();
        LoginDTO loginDTO = Json.fromJson(json, LoginDTO.class);
        CompletionStage<User> userCompletionStage = userRepository.authenticate(loginDTO.getEmail(), secureUtil.encrypt(loginDTO.getEmail(), loginDTO.getPassword()));
        return userCompletionStage.thenApplyAsync(user -> {
            if(user != null){
                return ok(Json.toJson(user.getProfile()));
            } else {
                return unauthorized();
            }
        }, httpExecutionContext.current());
    }

    /**
     * Return hash of the password
     * @param email
     * @param password
     * @return JSON {"hash" : "hash"}
     */
    public Result getHash(String email, String password) {
        String hash = secureUtil.encrypt(email, password);
        ObjectNode result = Json.newObject();
        result.put("hash", hash);
        return ok(result);
    }

}
