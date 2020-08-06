import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.*;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {

        port(getHerokuAssignedPort());
        staticFiles.location("/public");

        get("/greet", (req, res) -> "Hello");
        get("/greet/:username", (req, res) -> "Hello " + req.params(":username"));
        get("/greet/:username/language/:language", (request, response) -> {
            String name = request.params("username");
            String language = request.params("language");
            return greeting(name, language);
        });

        post("/greet", (req, res) -> {
            String name = req.queryParams("username");
            String language = req.queryParams("language");
            return greeting(name, language);
        });

        get("/hello", (req, res) -> {

            Map<String, Object> map = new HashMap<>();
            return new ModelAndView(map, "hello.handlebars");

        }, new HandlebarsTemplateEngine());


        post("/hello", (req, res) -> {

            Map<String, Object> map = new HashMap<>();

            // create the greeting message
            String greeting = "Hello, " + req.queryParams("username");
//            if(req.queryParams("username").exists()){

//                return "";
//            }
            /*i*//*f(req.queryParams("username").Exists == false) {
                username.add(username);
            } else {
                System.out.println("You already added that username");
            }*/

            // put it in the map which is passed to the template - the value will be merged into the template
            map.put("greeting", greeting);

            return new ModelAndView(map, "hello.handlebars");

        }, new HandlebarsTemplateEngine());
    }

    public ArrayList<String> username = new ArrayList();

    public static String greeting(String personName, String selectedLanguage) {
        switch (selectedLanguage) {
            case "english":
                return "Hello, " + personName;
            case "isixhosa":
                return "Molo, " + personName;
            case "french":
                return "Bonjour, " + personName;
            case "dutch":
                return "Goeie Dag, " + personName;
            default:
                return "Hello, " + personName;
        }
    }


    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}