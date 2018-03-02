
package tikape.minifoorumi.domain;
import java.util.ArrayList;
import java.util.List;


public class Viestiketju {
    private int id;
    private List<Viesti> viestit = new ArrayList<>();
    
    public Viestiketju() {
        
    }
    
    public int getId() {
        return this.id;
    }
    
    public List<Viesti> getViestit() {
        return viestit;
    }
}
