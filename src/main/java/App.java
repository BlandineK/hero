import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
//import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import static spark.Spark.*;


public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");
        String layout = "templates/layout.vtl";
        ProcessBuilder process = new ProcessBuilder();
        Integer port;
        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port =4567;
        }

        setPort(port);

        get("/", (request, response) -> {
           // System.out.println(Hero.all());
            Map<String, Object> model = new HashMap<String, Object>();
            //model.put("hero", Hero.all());
            model.put("template", "templates/categories.vtl");
            return new ModelAndView(new HashMap(),"home.hbs");
        }, new HandlebarsTemplateEngine());

get("/heroo",(request, response) ->{
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("template", "templates/hero-form.vtl");
            return new ModelAndView(new HashMap(), "heroo.hbs");
        }, new HandlebarsTemplateEngine());

        get("/hero",(request, response) ->{
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("hero", Hero.all());
            model.put("template", "templates/hero.vtl");
            return new ModelAndView(new HashMap(), "hero.hbs");
        }, new HandlebarsTemplateEngine());

        get("/squad-form",(request, response) ->{
            Map<String, Object> model = new HashMap<String,Object>();
            model.put("template", "templates/squad-form.vtl");
            return new ModelAndView(model, "squad-form.hbs");
        }, new HandlebarsTemplateEngine());

        get("/squads",(request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("squads", Squad.all());
            model.put("template", "templates/squads.vtl");
            return new ModelAndView(new HashMap(), "squad.hbs");
        }, new HandlebarsTemplateEngine());

        get("/squads/:id", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            Squad squad = Squad.find(Integer.parseInt(request.params(":id")));
            model.put("squad", squad);
            model.put("template", "templates/squad.vtl");
            return new ModelAndView(model, layout);
        }, new HandlebarsTemplateEngine());

        get("squads/:id/heroes/new", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            Squad squad = Squad.find(Integer.parseInt(request.params(":id")));
            model.put("squad", squad);
            model.put("template", "templates/squad-heroes-form.vtl");
            return new ModelAndView(model, layout);
        }, new HandlebarsTemplateEngine());

        post("/new/heroes",(request, response) ->{
            Map<String, Object> model = new HashMap<String, Object>();
            ArrayList<Hero> heroes = request.session().attribute("heroes");
            if (heroes == null) {
                heroes = new ArrayList<Hero>();
                request.session().attribute("heroes", heroes);
            }

            String name = request.queryParams("name");
            int age = Integer.parseInt(request.queryParams("age"));
            String power = request.queryParams("power");
            String weakness = request.queryParams("weakness");
            Hero newHero = new Hero(name, age, power, weakness);
            heroes.add(newHero);
            request.session().attribute("heroes", heroes);

            model.put("template", "templates/heroList.vtl");
            return new ModelAndView(model, layout);
        }, new HandlebarsTemplateEngine());


        post("/squads", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            String name = request.queryParams("name");
            int size = Integer.parseInt(request.queryParams("size"));
            String cause = request.queryParams("cause");
            Squad newSquad = new Squad(name, size, cause);
            model.put("template", "templates/squad-List.vtl");
            return new ModelAndView(model, layout);
        }, new HandlebarsTemplateEngine());

        post("/heroes", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();

            Squad squad = Squad.find(Integer.parseInt(request.queryParams("squadId")));

            String name = request.queryParams("name");
            int age = Integer.parseInt(request.queryParams("age"));
            String power = request.queryParams("power");
            String weakness = request.queryParams("weakness");
            Hero newHero = new Hero(name, age, power, weakness);

            squad.addHero(newHero);

            model.put("squad", squad);
            model.put("template", "templates/squad-heroes-List.vtl");
            return new ModelAndView(model, layout);
        }, new HandlebarsTemplateEngine());

    }
}

