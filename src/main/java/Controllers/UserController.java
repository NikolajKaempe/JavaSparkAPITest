package Controllers;

import ErrorHandling.ResponseError;
import Models.User;
import Services.UserService;
import static JsonUtil.JsonUtil.*;
import static spark.Spark.*;

public class UserController {

    public UserController(final UserService userService) {

        userService.createUser("Thomas","thomas@mh.dk");
        userService.createUser("Mark","Mark@mark.kram");

        get("/users", (req, res) -> userService.getAllUsers(), json());

        get("/users/:id", (req, res) -> {
            String id = req.params(":id");
            User user = userService.getUser(id);
            if (user != null) {
                return user;
            }
            res.status(400);
            return new ResponseError("No user with id '%s' found", id);
        }, json());

        post("/users", (req, res) -> userService.createUser(
                req.queryParams("name"),
                req.queryParams("email")
        ), json());

        put("/users/:id", (req, res) -> userService.updateUser(
                req.params(":id"),
                req.queryParams("name"),
                req.queryParams("email")
        ), json());

        after((req, res) -> {
            res.type("application/json");
        });

        exception(IllegalArgumentException.class, (e, req, res) -> {
            res.status(400);
            res.body(toJson(new ResponseError(e)));
        });
    }
}