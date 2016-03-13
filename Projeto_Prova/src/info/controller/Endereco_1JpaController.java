/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.controller;

import info.controller.exceptions.NonexistentEntityException;
import info.modal.Endereco_1;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import info.modal.Pessoa_1;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Eclesio
 */
public class Endereco_1JpaController implements Serializable {

    public Endereco_1JpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public Endereco_1JpaController() {
        String up = "InfoPU";
        emf = Persistence.createEntityManagerFactory(up);
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Endereco_1 endereco_1) {
        if (endereco_1.getPessoaCollection() == null) {
            endereco_1.setPessoaCollection(new ArrayList<Pessoa_1>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Pessoa_1> attachedPessoaCollection = new ArrayList<Pessoa_1>();
            for (Pessoa_1 pessoaCollectionPessoa_1ToAttach : endereco_1.getPessoaCollection()) {
                pessoaCollectionPessoa_1ToAttach = em.getReference(pessoaCollectionPessoa_1ToAttach.getClass(), pessoaCollectionPessoa_1ToAttach.getId());
                attachedPessoaCollection.add(pessoaCollectionPessoa_1ToAttach);
            }
            endereco_1.setPessoaCollection(attachedPessoaCollection);
            em.persist(endereco_1);
            for (Pessoa_1 pessoaCollectionPessoa_1 : endereco_1.getPessoaCollection()) {
                Endereco_1 oldEndIdOfPessoaCollectionPessoa_1 = pessoaCollectionPessoa_1.getEndId();
                pessoaCollectionPessoa_1.setEndId(endereco_1);
                pessoaCollectionPessoa_1 = em.merge(pessoaCollectionPessoa_1);
                if (oldEndIdOfPessoaCollectionPessoa_1 != null) {
                    oldEndIdOfPessoaCollectionPessoa_1.getPessoaCollection().remove(pessoaCollectionPessoa_1);
                    oldEndIdOfPessoaCollectionPessoa_1 = em.merge(oldEndIdOfPessoaCollectionPessoa_1);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Endereco_1 endereco_1) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Endereco_1 persistentEndereco_1 = em.find(Endereco_1.class, endereco_1.getId());
            Collection<Pessoa_1> pessoaCollectionOld = persistentEndereco_1.getPessoaCollection();
            Collection<Pessoa_1> pessoaCollectionNew = endereco_1.getPessoaCollection();
            Collection<Pessoa_1> attachedPessoaCollectionNew = new ArrayList<Pessoa_1>();
            for (Pessoa_1 pessoaCollectionNewPessoa_1ToAttach : pessoaCollectionNew) {
                pessoaCollectionNewPessoa_1ToAttach = em.getReference(pessoaCollectionNewPessoa_1ToAttach.getClass(), pessoaCollectionNewPessoa_1ToAttach.getId());
                attachedPessoaCollectionNew.add(pessoaCollectionNewPessoa_1ToAttach);
            }
            pessoaCollectionNew = attachedPessoaCollectionNew;
            endereco_1.setPessoaCollection(pessoaCollectionNew);
            endereco_1 = em.merge(endereco_1);
            for (Pessoa_1 pessoaCollectionOldPessoa_1 : pessoaCollectionOld) {
                if (!pessoaCollectionNew.contains(pessoaCollectionOldPessoa_1)) {
                    pessoaCollectionOldPessoa_1.setEndId(null);
                    pessoaCollectionOldPessoa_1 = em.merge(pessoaCollectionOldPessoa_1);
                }
            }
            for (Pessoa_1 pessoaCollectionNewPessoa_1 : pessoaCollectionNew) {
                if (!pessoaCollectionOld.contains(pessoaCollectionNewPessoa_1)) {
                    Endereco_1 oldEndIdOfPessoaCollectionNewPessoa_1 = pessoaCollectionNewPessoa_1.getEndId();
                    pessoaCollectionNewPessoa_1.setEndId(endereco_1);
                    pessoaCollectionNewPessoa_1 = em.merge(pessoaCollectionNewPessoa_1);
                    if (oldEndIdOfPessoaCollectionNewPessoa_1 != null && !oldEndIdOfPessoaCollectionNewPessoa_1.equals(endereco_1)) {
                        oldEndIdOfPessoaCollectionNewPessoa_1.getPessoaCollection().remove(pessoaCollectionNewPessoa_1);
                        oldEndIdOfPessoaCollectionNewPessoa_1 = em.merge(oldEndIdOfPessoaCollectionNewPessoa_1);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = endereco_1.getId();
                if (findEndereco_1(id) == null) {
                    throw new NonexistentEntityException("The endereco_1 with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Endereco_1 endereco_1;
            try {
                endereco_1 = em.getReference(Endereco_1.class, id);
                endereco_1.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The endereco_1 with id " + id + " no longer exists.", enfe);
            }
            Collection<Pessoa_1> pessoaCollection = endereco_1.getPessoaCollection();
            for (Pessoa_1 pessoaCollectionPessoa_1 : pessoaCollection) {
                pessoaCollectionPessoa_1.setEndId(null);
                pessoaCollectionPessoa_1 = em.merge(pessoaCollectionPessoa_1);
            }
            em.remove(endereco_1);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Endereco_1> findEndereco_1Entities() {
        return findEndereco_1Entities(true, -1, -1);
    }

    public List<Endereco_1> findEndereco_1Entities(int maxResults, int firstResult) {
        return findEndereco_1Entities(false, maxResults, firstResult);
    }

    private List<Endereco_1> findEndereco_1Entities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Endereco_1.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Endereco_1 findEndereco_1(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Endereco_1.class, id);
        } finally {
            em.close();
        }
    }

    public int getEndereco_1Count() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Endereco_1> rt = cq.from(Endereco_1.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
