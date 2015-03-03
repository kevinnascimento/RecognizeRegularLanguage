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
import model.Automaton;
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
    private Automaton automaton;
    private List<State> currentsStates;
    private List<String> recognize;
    private ObservableList<Results> data = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadAutomaton();
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

    private void loadAutomaton() {
        automaton = new Automaton();

        automaton.setAlphabet("abc");

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

        automaton.setState(q0);
        automaton.setState(q1);
        automaton.setState(q2);
        automaton.setState(q3);
        automaton.setState(q4);
        automaton.setState(q5);
        automaton.setState(q6);

        automaton.setStartState(q0);
        automaton.setFinalState(q2);
        automaton.setFinalState(q3);
        automaton.setFinalState(q5);

        automaton.setTransition(q0, q1, a);
        automaton.setTransition(q0, q2, a);
        automaton.setTransition(q0, q3, b);
        automaton.setTransition(q0, q3, c);
        automaton.setTransition(q1, q0, a);
        automaton.setTransition(q2, q2, a);
        automaton.setTransition(q2, q4, b);
        automaton.setTransition(q3, q6, b);
        automaton.setTransition(q3, q6, c);
        automaton.setTransition(q4, q5, a);
        automaton.setTransition(q5, q4, a);
        automaton.setTransition(q5, q4, b);
        automaton.setTransition(q6, q3, b);
        automaton.setTransition(q6, q3, c);
    }

    private Results testWord(Word word) {
        List<Symbol> symbols = word.getSymbols();
        recognize = new ArrayList<>();
        currentsStates = new ArrayList<>();
        currentsStates.add(automaton.getStartState());
        recognize.add(automaton.getStartState().getName());
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
            if (automaton.isFinalState(recognize.get(recognize.size() - 1))) {
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
            Set<Transition> transitions = automaton.getTransitions(s, currentSymbol);
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
        return symbols.stream().allMatch((Symbol s) -> (automaton.getAlphabet().contains(String.valueOf(s.getValue()).toUpperCase())));
    }

}
