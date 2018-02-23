import java.util.List;
import java.time.LocalDate;
public class LuokkakaaviostaLuokiksi {


    public class Kirja {
        String nimi;
        String kirjoittaja;
        int julkaisuvuosi;
        List<Nide> niteet;
}
    
    public class Nide {
        int tunnus;
        List<Laina> lainat;
    }
    
    public class Hylly {
        String sijainti;
        List<Nide> sisalto;
    }
    
    public class Laina {
        LocalDate alku;
        LocalDate loppu;
        Boolean palautettu;
        Henkilo lainaaja;
    }
    
    public class Henkilo {
        List<Laina> lainatutKirjat;
    }
    
    public static void main(String[] args) {
        // Voit halutessasi kokeilla luokkiesi toimintaa täällä
        
    }

}
