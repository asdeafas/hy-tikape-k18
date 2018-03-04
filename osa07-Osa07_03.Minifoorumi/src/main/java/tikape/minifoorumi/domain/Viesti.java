
package tikape.minifoorumi.domain;

public class Viesti {
    private Integer id;
    private Integer viestiketju_id;
    private String sanoma;
    private Integer jarjestys;
    
    public Viesti() {
        this.id = null;
        this.viestiketju_id = null;
        this.sanoma = null;
        this.jarjestys = null;
    }
    
    public Viesti(String sanoma) {
        this.id = null;
        this.viestiketju_id = null;
        this.sanoma = sanoma;
        this.jarjestys = null;
    }
    
        public Viesti(String sanoma,Integer viestiketju_id) {
        this.id = null;
        this.viestiketju_id = viestiketju_id;
        this.sanoma = sanoma;
        this.jarjestys = null;
    }
    
    public Viesti(Integer id, Integer viestiketju_id, String sanoma, Integer jarjestys) {
        this.id = id;
        this.viestiketju_id = viestiketju_id;
        this.sanoma = sanoma;
        this.jarjestys = jarjestys;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public Integer getViestiketjuId() {
        return this.viestiketju_id;
    }
    
    public String getSanoma() {
        return this.sanoma;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public void setJarjestys(Integer jarjestys) {
        this.jarjestys = jarjestys;
    }
    
}
