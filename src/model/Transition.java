package model;

/**
 *
 * @author Kevin Nascimento
 */
public class Transition {

    private State origin;
    private State destiny;
    private Symbol symbol;
    
    public Transition(State origin, State destiny, Symbol symbol) {
        this.origin = origin;
        this.destiny = destiny;
        this.symbol = symbol;
    }

    public State getOrigin() {
        return origin;
    }

    public void setOrigin(State origin) {
        this.origin = origin;
    }

    public State getDestiny() {
        return destiny;
    }

    public void setDestiny(State destiny) {
        this.destiny = destiny;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }
}
