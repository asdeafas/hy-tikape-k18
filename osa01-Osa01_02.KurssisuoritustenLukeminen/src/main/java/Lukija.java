
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Lukija {

    public List<Opiskelija> lueOpiskelijat(String opiskelijatiedosto) throws Throwable {
        List<Opiskelija> opiskelijat = new ArrayList<>();

        Files.lines(Paths.get(opiskelijatiedosto)).forEach(rivi -> {
            String[] palat = rivi.split("\t");
            opiskelijat.add(new Opiskelija(palat[0], palat[1], Integer.parseInt(palat[2])));
        });

        return opiskelijat;
    }

    public List<Kurssisuoritus> lueKurssisuoritukset(String suoritustiedosto, String opiskelijatiedosto) throws Throwable {

        // lisää toiminnallisuus tänne
        return new ArrayList<>();
    }
}
