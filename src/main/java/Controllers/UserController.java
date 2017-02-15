package Controllers;

import ErrorHandling.ResponseError;
import Models.User;
import Services.UserService;

import java.util.List;

import static JsonUtil.JsonUtil.*;
import static spark.Spark.*;

public class UserController {

    public UserController(final UserService userService) {

        for (int i = 0; i < 100;i++) {
            userService.createUser("Thomas"+i, "thomas@mh.dk");
            userService.createUser("Mark"+i, "Mark@mark.kram");
        }

        get("/users", (req, res) ->
        {
            List<User> users = userService.getAllUsers();
            if (users.size() != 0){
                res.status(200);
                return  users;
            }
            res.status(404);
            return new ResponseError("No user found in the database");
        }, json());


        get("/users/:id", (req, res) -> {
            int id ;
            try{
                id = Integer.parseInt(req.params(":id"));
            }catch (Exception e)
            {
                res.status(404);
                return new ResponseError("the id must be an integer");
            }
            User user = userService.getUser(id);
            if (user != null) {
                res.status(200);
                return user;
            }
            res.status(404);
            return new ResponseError("No user with id "+ id +" found");
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