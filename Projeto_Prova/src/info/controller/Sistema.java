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
    public void CadastrarPessoa(Pessoa pe) {
        PessoaJpaController pjpa = new PessoaJpaController();
        pjpa.create(pe);
    }

    public void AlterarPessoa(Pessoa pe) {
        PessoaJpaController pjpa = new PessoaJpaController();
        try {
            pjpa.edit(pe);
        } catch (Exception ex) {
            Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Pessoa BuscarPessoa(Integer id) {
        Pessoa pe = new Pessoa();
        PessoaJpaController pejpa = new PessoaJpaController();
        pe = pejpa.findPessoa(id);
        return pe;
    }

    public void DeletarPessoa(Integer id) throws info.controller.exceptions.NonexistentEntityException {
        PessoaJpaController pejpa = new PessoaJpaController();
        pejpa.destroy(id);
    }

    //Endereco
    public void CadastrarEndereco(Endereco end) throws Exception {
        EnderecoJpaController endjpa = new EnderecoJpaController();
        endjpa.create(end);
    }

    public void AlterarEndereco(Endereco end) {
        EnderecoJpaController endjpa = new EnderecoJpaController();
        try {
            endjpa.edit(end);
        } catch (Exception ex) {
            Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Endereco BuscarEndereco(Integer id) {
        Endereco end = new Endereco();
        EnderecoJpaController endjpa = new EnderecoJpaController();
        end = endjpa.findEndereco(id);
        return end;
    }

    public void DeletarEndereco(Integer id) throws info.controller.exceptions.NonexistentEntityException {
        EnderecoJpaController endjpa = new EnderecoJpaController();
        endjpa.destroy(id);
    }

    //Contato
    public void CadastrarContato(Contato con) throws Exception {
        ContatoJpaController conjpa = new ContatoJpaController();
        conjpa.create(con);
    }

    public void AlterarContato(Contato con) {
       ContatoJpaController conjpa = new ContatoJpaController();
        try {
            conjpa.edit(con);
        } catch (Exception ex) {
            Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Contato BuscarContato(Integer id) {
       Contato con = new Contato();
       ContatoJpaController conjpa = new ContatoJpaController();
        con = conjpa.findContato(id);
        return con;
    }

    public void DeletarContato(Integer id) throws info.controller.exceptions.NonexistentEntityException {
        ContatoJpaController conjpa = new ContatoJpaController();
        conjpa.destroy(id);
    }

  
}
