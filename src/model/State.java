package model;

/**
 *
 * @author Kevin Nascimento
 */
public class State {
    
    private int id;
    private String name;

    public State(int id) {
        this.id = id;
        this.name = String.valueOf("q" + id);
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    
}
