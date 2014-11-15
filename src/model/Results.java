package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Kevin Nascimento
 */
public class Results {
    
    private SimpleIntegerProperty line;
    private SimpleStringProperty result;
    private SimpleStringProperty sequence;
    private SimpleStringProperty recognize;
    
    public Results(int line, String result, String sequence, String recogsize) {
        this.line = new SimpleIntegerProperty(line);
        this.result = new SimpleStringProperty(result);
        this.sequence = new SimpleStringProperty(sequence);
        this.recognize = new SimpleStringProperty(recogsize);
    }

    public int getLine() {
        return line.get();
    }

    public void setLine(int line) {
        this.line.set(line);
    }

    public String getResult() {
        return result.get();
    }

    public void setResult(String result) {
        this.result.set(result);
    }

    public String getSequence() {
        return sequence.get();
    }

    public void setSequence(String sequence) {
        this.sequence.set(sequence);
    }

    public String getRecognize() {
        return recognize.get();
    }

    public void setRecognize(String recognize) {
        this.recognize.get();
    }
}
