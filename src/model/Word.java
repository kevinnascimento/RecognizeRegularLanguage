package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Kevin Nascimento
 */
public class Word {

    private final List<Symbol> word;
    private int line;

    public Word(String w, int line) {
        this.word = new ArrayList<>();
        char[] words = w.toCharArray();
        for(int i = 0; i < words.length; i++) {
            addSymbol(new Symbol(words[i]));
        }
        this.line = line;
    }

    public void addSymbol(Symbol s) {
        this.word.add(s);
    }

    public void removeSymbol(Symbol s) {
        this.word.remove(s);
    }
    
    public List<Symbol> getSymbols() {
        return Collections.unmodifiableList(word);
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }
    
    @Override
    public String toString() {
        StringBuilder r = new StringBuilder();
        word.stream().forEach((Symbol x) -> {
            r.append(x.getValue());
        });
        return r.toString();
    }
}
