package tehokkuus;


import java.sql.Date;
import java.util.Random;

public class Generaattori {

    private final Random random;

    public Generaattori() {
        this.random = new Random();
    }

    public String ip() {
        return "127.0.0." + random.nextInt(256);
    }

    public Date date() {
        return new Date(new java.util.Date().getTime() - random.nextInt(10000000));
    }

    public String address() {
        return "sivu-" + random.nextInt(50);
    }

    public String operation() {
        return "operaatio-" + random.nextInt(10);
    }

    public String device() {
        return "laite-" + random.nextInt(50);
    }

    public String user() {
        return "kayttaja-" + random.nextInt(50);
    }

}
