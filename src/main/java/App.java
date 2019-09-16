import java.util.Map;
//import java.util.ArrayList;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import static spark.Spark.*;


public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");
        ProcessBuilder process = new ProcessBuilder();
        Integer port;
        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = ;
        }

        setPort(port);

        get("/", (request, response) -> "Hero Squad");{
        Map<String, Object> model = new HashMap<String, Object>();
        return new ModelAndView(new HashMap(),"home.hbs");
    }, new HandlebarsTemplateEngine());

        get("/heroo",(request, response) ->{
            Map<String, Object> model = new HashMap<String, Object>();
            return new ModelAndView(new HashMap(), "heroo.hbs");
        }, new HandlebarsTemplateEngine());

        get("/hero",(req, res) ->{
            Map<String, Object> model = new HashMap<String, Object>();
            return new ModelAndView(new HashMap(), "hero.hbs");
        }, new HandlebarsTemplateEngine());


    }
}
