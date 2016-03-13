/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.controller;

import info.controller.exceptions.NonexistentEntityException;
import info.controller.exceptions.PreexistingEntityException;
import info.modal.Contato;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import info.modal.Pessoa;
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
public class ContatoJpaController implements Serializable {

    public ContatoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public ContatoJpaController() {
        
        String up = "InfoPU";
        emf = Persistence.createEntityManagerFactory(up);
        
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Contato contato) throws PreexistingEntityException, Exception {
        if (contato.getPessoaCollection() == null) {
            contato.setPessoaCollection(new ArrayList<Pessoa>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Pessoa> attachedPessoaCollection = new ArrayList<Pessoa>();
            for (Pessoa pessoaCollectionPessoaToAttach : contato.getPessoaCollection()) {
                pessoaCollectionPessoaToAttach = em.getReference(pessoaCollectionPessoaToAttach.getClass(), pessoaCollectionPessoaToAttach.getId());
                attachedPessoaCollection.add(pessoaCollectionPessoaToAttach);
            }
            contato.setPessoaCollection(attachedPessoaCollection);
            em.persist(contato);
            for (Pessoa pessoaCollectionPessoa : contato.getPessoaCollection()) {
                Contato oldContIdOfPessoaCollectionPessoa = pessoaCollectionPessoa.getContId();
                pessoaCollectionPessoa.setContId(contato);
                pessoaCollectionPessoa = em.merge(pessoaCollectionPessoa);
                if (oldContIdOfPessoaCollectionPessoa != null) {
                    oldContIdOfPessoaCollectionPessoa.getPessoaCollection().remove(pessoaCollectionPessoa);
                    oldContIdOfPessoaCollectionPessoa = em.merge(oldContIdOfPessoaCollectionPessoa);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findContato(contato.getId()) != null) {
                throw new PreexistingEntityException("Contato " + contato + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Contato contato) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Contato persistentContato = em.find(Contato.class, contato.getId());
            Collection<Pessoa> pessoaCollectionOld = persistentContato.getPessoaCollection();
            Collection<Pessoa> pessoaCollectionNew = contato.getPessoaCollection();
            Collection<Pessoa> attachedPessoaCollectionNew = new ArrayList<Pessoa>();
            for (Pessoa pessoaCollectionNewPessoaToAttach : pessoaCollectionNew) {
                pessoaCollectionNewPessoaToAttach = em.getReference(pessoaCollectionNewPessoaToAttach.getClass(), pessoaCollectionNewPessoaToAttach.getId());
                attachedPessoaCollectionNew.add(pessoaCollectionNewPessoaToAttach);
            }
            pessoaCollectionNew = attachedPessoaCollectionNew;
            contato.setPessoaCollection(pessoaCollectionNew);
            contato = em.merge(contato);
            for (Pessoa pessoaCollectionOldPessoa : pessoaCollectionOld) {
                if (!pessoaCollectionNew.contains(pessoaCollectionOldPessoa)) {
                    pessoaCollectionOldPessoa.setContId(null);
                    pessoaCollectionOldPessoa = em.merge(pessoaCollectionOldPessoa);
                }
            }
            for (Pessoa pessoaCollectionNewPessoa : pessoaCollectionNew) {
                if (!pessoaCollectionOld.contains(pessoaCollectionNewPessoa)) {
                    Contato oldContIdOfPessoaCollectionNewPessoa = pessoaCollectionNewPessoa.getContId();
                    pessoaCollectionNewPessoa.setContId(contato);
                    pessoaCollectionNewPessoa = em.merge(pessoaCollectionNewPessoa);
                    if (oldContIdOfPessoaCollectionNewPessoa != null && !oldContIdOfPessoaCollectionNewPessoa.equals(contato)) {
                        oldContIdOfPessoaCollectionNewPessoa.getPessoaCollection().remove(pessoaCollectionNewPessoa);
                        oldContIdOfPessoaCollectionNewPessoa = em.merge(oldContIdOfPessoaCollectionNewPessoa);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = contato.getId();
                if (findContato(id) == null) {
                    throw new NonexistentEntityException("The contato with id " + id + " no longer exists.");
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
            Contato contato;
            try {
                contato = em.getReference(Contato.class, id);
                contato.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contato with id " + id + " no longer exists.", enfe);
            }
            Collection<Pessoa> pessoaCollection = contato.getPessoaCollection();
            for (Pessoa pessoaCollectionPessoa : pessoaCollection) {
                pessoaCollectionPessoa.setContId(null);
                pessoaCollectionPessoa = em.merge(pessoaCollectionPessoa);
            }
            em.remove(contato);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Contato> findContatoEntities() {
        return findContatoEntities(true, -1, -1);
    }

    public List<Contato> findContatoEntities(int maxResults, int firstResult) {
        return findContatoEntities(false, maxResults, firstResult);
    }

    private List<Contato> findContatoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Contato.class));
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

    public Contato findContato(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Contato.class, id);
        } finally {
            em.close();
        }
    }

    public int getContatoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Contato> rt = cq.from(Contato.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
