/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info;
import com.sun.glass.ui.SystemClipboard;
import info.controller.*;
import info.controller.exceptions.NonexistentEntityException;
import info.modal.*;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author Eclesio
 */
public class CadPessoa {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NonexistentEntityException {
        
        Pessoa_1 pe = new Pessoa_1();
        Sistema si = new Sistema();
        Endereco_1 end = new Endereco_1();
        
        end.setId(1);
        
        end.setBairro("asdasd");
        end.setNumerocasa("qqqqqqq");
        end.setRua("aaaaaaaaaa");
        end.setLogradouro("uma ai");
        
        si.AlterarEndereco(end);
        
    }
    
}
