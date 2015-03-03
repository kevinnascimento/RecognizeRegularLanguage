package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author Kevin Nascimento
 */
public class Automaton {

    private String alphabet;
    private State startState;
    private final HashMap<Integer, State> finalStates;
    private final HashMap<Integer, State> states;
    private final HashSet<Transition> transitions;

    public Automaton() {
        this.finalStates = new HashMap<>();
        this.states = new HashMap<>();
        this.transitions = new HashSet<>();
    }
    
    public String getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(String alphabet) {
        this.alphabet = alphabet.toUpperCase();
    }
    
    public State getStartState() {
        return startState;
    }

    public void setStartState(State startState) {
        this.startState = startState;
    }

    public State getFinalState(int id) {
        return finalStates.get(id);
    }

    public void setFinalState(State state) {
        this.finalStates.put(state.getId(), state);
    }

    public void setTransition(State origin, State destiny, Symbol symbol) {
        transitions.add(new Transition(origin, destiny, symbol));
    }
    
    public Set<Transition> getTransitions(State origin, Symbol symbol) {
        return transitions.stream().filter((Transition t) -> (t.getSymbol().getValue() == symbol.getValue()) && (t.getOrigin().getId() == origin.getId())).collect(Collectors.toSet());
    }

    public void setState(State state) {
        this.states.put(state.getId(), state);
    }

    public State getState(int id) {
        return states.get(id);
    }
    
    public boolean isFinalState(String states) {
        String[] split = states.split("q");
        boolean result = false;
        for(String s : split) {
            if((!s.isEmpty()) && (!result)) {
                result = finalStates.containsKey(Integer.valueOf(s));
            }
        }
        return result;
    }
}
