/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.controller;

import info.controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import info.modal.Endereco_1;
import info.modal.Pessoa_1;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Eclesio
 */
public class Pessoa_1JpaController implements Serializable {

    public Pessoa_1JpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
     public Pessoa_1JpaController() {
        String up = "InfoPU";
        emf = Persistence.createEntityManagerFactory(up);
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pessoa_1 pessoa_1) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Endereco_1 endId = pessoa_1.getEndId();
            if (endId != null) {
                endId = em.getReference(endId.getClass(), endId.getId());
                pessoa_1.setEndId(endId);
            }
            em.persist(pessoa_1);
            if (endId != null) {
                endId.getPessoaCollection().add(pessoa_1);
                endId = em.merge(endId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pessoa_1 pessoa_1) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pessoa_1 persistentPessoa_1 = em.find(Pessoa_1.class, pessoa_1.getId());
            Endereco_1 endIdOld = persistentPessoa_1.getEndId();
            Endereco_1 endIdNew = pessoa_1.getEndId();
            if (endIdNew != null) {
                endIdNew = em.getReference(endIdNew.getClass(), endIdNew.getId());
                pessoa_1.setEndId(endIdNew);
            }
            pessoa_1 = em.merge(pessoa_1);
            if (endIdOld != null && !endIdOld.equals(endIdNew)) {
                endIdOld.getPessoaCollection().remove(pessoa_1);
                endIdOld = em.merge(endIdOld);
            }
            if (endIdNew != null && !endIdNew.equals(endIdOld)) {
                endIdNew.getPessoaCollection().add(pessoa_1);
                endIdNew = em.merge(endIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pessoa_1.getId();
                if (findPessoa_1(id) == null) {
                    throw new NonexistentEntityException("The pessoa_1 with id " + id + " no longer exists.");
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
            Pessoa_1 pessoa_1;
            try {
                pessoa_1 = em.getReference(Pessoa_1.class, id);
                pessoa_1.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pessoa_1 with id " + id + " no longer exists.", enfe);
            }
            Endereco_1 endId = pessoa_1.getEndId();
            if (endId != null) {
                endId.getPessoaCollection().remove(pessoa_1);
                endId = em.merge(endId);
            }
            em.remove(pessoa_1);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pessoa_1> findPessoa_1Entities() {
        return findPessoa_1Entities(true, -1, -1);
    }

    public List<Pessoa_1> findPessoa_1Entities(int maxResults, int firstResult) {
        return findPessoa_1Entities(false, maxResults, firstResult);
    }

    private List<Pessoa_1> findPessoa_1Entities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pessoa_1.class));
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

    public Pessoa_1 findPessoa_1(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pessoa_1.class, id);
        } finally {
            em.close();
        }
    }

    public int getPessoa_1Count() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pessoa_1> rt = cq.from(Pessoa_1.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
