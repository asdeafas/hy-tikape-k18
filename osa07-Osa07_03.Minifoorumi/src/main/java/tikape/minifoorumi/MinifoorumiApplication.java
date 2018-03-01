package tikape.minifoorumi;

import spark.Spark;

public class MinifoorumiApplication {

    public static void main(String[] args) throws Exception {

        Spark.get("*", (req, res) -> {
            return "Hei maailma!";
        });
    }
}
