
package tikape.minifoorumi.domain;

public class Viesti {
    private int id;
    private int viestiketju_id;
    private String sanoma;
    private int jarjestys;
    
    public Viesti(String sanoma) {
        this.sanoma = sanoma;
    }
    
    public int getId() {
        return this.id;
    }
    
    public int getViestiketjuId() {
        return this.viestiketju_id;
    }
    
    public String getSanoma() {
        return this.sanoma;
    }
}
