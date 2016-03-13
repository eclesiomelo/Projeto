/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.controller;

import info.modal.*;

import info.controller.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Clayton Ferraz
 */
public class Sistema {

    public Sistema() {
    }

    //Pessoa
    public void CadastrarPessoa(Pessoa_1 pe) {
        Pessoa_1JpaController pjpa = new Pessoa_1JpaController();
        pjpa.create(pe);
    }

    public void AlterarPessoa(Pessoa_1 pe) {
        Pessoa_1JpaController pjpa = new Pessoa_1JpaController();
        try {
            pjpa.edit(pe);
        } catch (Exception ex) {
            Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Pessoa_1 BuscarPessoa(Integer id) {
        Pessoa_1 pe = new Pessoa_1();
        Pessoa_1JpaController pejpa = new Pessoa_1JpaController();
        pe = pejpa.findPessoa_1(id);
        return pe;
    }

    public void DeletarPessoa(Integer id) throws info.controller.exceptions.NonexistentEntityException {
        Pessoa_1JpaController pejpa = new Pessoa_1JpaController();
        pejpa.destroy(id);
    }

    //Endereco
    public void CadastrarEndereco(Endereco_1 end) throws Exception {
        Endereco_1JpaController endjpa = new Endereco_1JpaController();
        endjpa.create(end);
    }

    public void AlterarEndereco(Endereco_1 end) {
        Endereco_1JpaController endjpa = new Endereco_1JpaController();
        try {
            endjpa.edit(end);
        } catch (Exception ex) {
            Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Endereco_1 BuscarEndereco(Integer id) {
        Endereco_1 end = new Endereco_1();
        Endereco_1JpaController endjpa = new Endereco_1JpaController();
        end = endjpa.findEndereco_1(id);
        return end;
    }

    public void DeletarEndereco(Integer id) throws info.controller.exceptions.NonexistentEntityException {
        Endereco_1JpaController endjpa = new Endereco_1JpaController();
        endjpa.destroy(id);
    }

}