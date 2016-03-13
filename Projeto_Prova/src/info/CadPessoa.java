/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info;
import com.sun.glass.ui.SystemClipboard;
import info.controller.*;
import info.modal.*;

import java.util.Scanner;
import javax.swing.JOptionPane;
/**
 *
 * @author Eclesio
 */
public class CadPessoa {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Pessoa pe = new Pessoa();
        Sistema si = new Sistema();
        pe.setNome("Joãozinho");
        pe.setIdade(24);
       
        si.CadastrarPessoa(pe);
        
        //si.DeletarPessoa(1);
        
        
       
        
    }
    
}
