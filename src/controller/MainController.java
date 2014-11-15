package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import model.Automata;
import model.Results;
import model.State;
import model.Symbol;
import model.Transition;
import model.Word;

/**
 * FXML Controller class
 *
 * @author Kevin Nascimento
 */
public class MainController implements Initializable {

    @FXML
    private TextArea textArea;

    @FXML
    private TableView tvResult;

    @FXML
    private TableColumn tcLine, tcResult, tcSequence, tcRecognize;

    private List<String> texts;
    private Automata automata;
    private List<State> currentsStates;
    private List<String> recognize;
    private ObservableList<Results> data = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadAutomata();
        tcLine.setCellValueFactory(new PropertyValueFactory<>("line"));
        tcResult.setCellValueFactory(new PropertyValueFactory<>("result"));
        tcSequence.setCellValueFactory(new PropertyValueFactory<>("sequence"));
        tcRecognize.setCellValueFactory(new PropertyValueFactory<>("recognize"));
        tvResult.setItems(data);
    }

    @FXML
    public void handleAnalisar() {
        List<String> textToRemove = new ArrayList<>();
        List<String> textLine = Arrays.asList(textArea.getText().split("\n"));
        data = null;

        textLine.stream().forEach((String text) -> {
            texts = new ArrayList<>(Arrays.asList(text.split(" ")));
            texts.forEach((t) -> {
                if (t.trim().isEmpty()) {
                    textToRemove.add(t);
                } else {
                    texts.set(texts.indexOf(t), t.trim());
                    if (data == null) {
                        data = FXCollections.observableArrayList(testWord(new Word(t.trim(), textLine.indexOf(text) + 1)));
                    } else {
                        data.add(testWord(new Word(t.trim(), textLine.indexOf(text) + 1)));
                    }
                }
            });
        });
        texts.removeAll(textToRemove);
        tvResult.setItems(data);
    }

    @FXML
    public void handleLimpar() {
        data = null;
        tvResult.setItems(data);
    }

    @FXML
    public void handleEquipe() {
        JOptionPane.showMessageDialog(null, "Made By Kevin Nascimento");
    }

    private void loadAutomata() {
        automata = new Automata();

        automata.setAlphabet("abc");

        State q0 = new State(0);
        State q1 = new State(1);
        State q2 = new State(2);
        State q3 = new State(3);
        State q4 = new State(4);
        State q5 = new State(5);
        State q6 = new State(6);

        Symbol a = new Symbol('a');
        Symbol b = new Symbol('b');
        Symbol c = new Symbol('c');

        automata.setState(q0);
        automata.setState(q1);
        automata.setState(q2);
        automata.setState(q3);
        automata.setState(q4);
        automata.setState(q5);
        automata.setState(q6);

        automata.setStartState(q0);
        automata.setFinalState(q2);
        automata.setFinalState(q3);
        automata.setFinalState(q5);

        automata.setTransition(q0, q1, a);
        automata.setTransition(q0, q2, a);
        automata.setTransition(q0, q3, b);
        automata.setTransition(q0, q3, c);
        automata.setTransition(q1, q0, a);
        automata.setTransition(q2, q2, a);
        automata.setTransition(q2, q4, b);
        automata.setTransition(q3, q6, b);
        automata.setTransition(q3, q6, c);
        automata.setTransition(q4, q5, a);
        automata.setTransition(q5, q4, a);
        automata.setTransition(q5, q4, b);
        automata.setTransition(q6, q3, b);
        automata.setTransition(q6, q3, c);
    }

    private Results testWord(Word word) {
        List<Symbol> symbols = word.getSymbols();
        recognize = new ArrayList<>();
        currentsStates = new ArrayList<>();
        currentsStates.add(automata.getStartState());
        recognize.add(automata.getStartState().getName());
        symbols.stream().forEach((Symbol s) -> {
            if (currentsStates.isEmpty()) {
                if (!recognize.contains("error")) {
                    recognize.add("error");
                }
            } else {
                testTransition(s);
            }

        });
        
        if (!isValidWord(word)) {
            return new Results(word.getLine(), "error: symbol is not valid", word.toString(), recognize.toString());
        } else if (currentsStates.isEmpty()) {
            return new Results(word.getLine(), "error: word is not valid", word.toString(), recognize.toString());
        } else {
            if (automata.isFinalState(recognize.get(recognize.size() - 1))) {
                return new Results(word.getLine(), "valid word", word.toString(), recognize.toString());
            } else {
                recognize.add("error");
                return new Results(word.getLine(), "error: word is not valid", word.toString(), recognize.toString());
            }
        }
    }

    private void testTransition(Symbol currentSymbol) {
        List<State> statesToAdd = new ArrayList<>();
        List<State> statesToRemove = new ArrayList<>();
        String stageNames = "";
        for (State s : currentsStates) {
            statesToRemove.add(s);
            Set<Transition> transitions = automata.getTransitions(s, currentSymbol);
            for (Transition t : transitions) {
                statesToAdd.add(t.getDestiny());
                stageNames += t.getDestiny().getName();
            }
        }
        if (!stageNames.equals("")) {
            recognize.add(stageNames);
        }
        currentsStates.removeAll(statesToRemove);
        currentsStates.addAll(statesToAdd);
    }

    private boolean isValidWord(Word word) {
        List<Symbol> symbols = word.getSymbols();
        return symbols.stream().allMatch((Symbol s) -> (automata.getAlphabet().contains(String.valueOf(s.getValue()).toUpperCase())));
    }

}
