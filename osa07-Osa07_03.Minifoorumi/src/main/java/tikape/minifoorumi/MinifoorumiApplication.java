package tikape.minifoorumi;

import spark.Spark;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class MinifoorumiApplication {

    public static void main(String[] args) throws Exception {

        Spark.get("*", (req, res) -> {
            HashMap map = new HashMap<>();
            
            return new ModelAndView(map,"messages");
        },new ThymeleafTemplateEngine());
    }
}
