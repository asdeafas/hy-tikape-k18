
package tikape.minifoorumi.domain;
import java.util.ArrayList;
import java.util.List;


public class Viestiketju {
    private Integer id;
    private String nimi;
    private List<Viesti> viestit = new ArrayList<>();
    
    public Viestiketju() {
        this.id = null;
    }
    
    public Viestiketju(List<Viesti> viestit) {
        this.id = null;
        this.viestit=viestit;
    }
    
    public Viestiketju(String nimi, List<Viesti> viestit) {
        this.id = null;
        this.nimi = nimi;
        this.viestit=viestit;
    }
    
    public Viestiketju(Integer id, String nimi, List<Viesti> viestit) {
        this.id = id;
        this.nimi = nimi;
        this.viestit=viestit;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public String getNimi() {
        return this.nimi;
    }
    
    public List<Viesti> getViestit() {
        return viestit;
    }
    
    public void setId(Integer id) {
        this.id=id;
    }
    
    public void setNimi(String nimi) {
        this.nimi = nimi;
    }
    
    public void setViestit(List<Viesti> viestit) {
        this.viestit = viestit;
    }
    
    public void addViestit(Viesti viesti) {
        this.viestit.add(viesti);
    }
}
