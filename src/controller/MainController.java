package controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Kevin Nascimento
 */
public class MainController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    public void handleAnalisar() {
        JOptionPane.showMessageDialog(null, "Analisar");
    }
    
    @FXML
    public void handleLimpar() {
        JOptionPane.showMessageDialog(null, "Limpar");
    }
    
    @FXML
    public void handleEquipe() {
        JOptionPane.showMessageDialog(null, "Kevin Eduardo do Nascimento");
    }
}
