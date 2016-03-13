/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.controller;

import info.controller.exceptions.NonexistentEntityException;
import info.modal.Pessoa_1;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
            em.persist(pessoa_1);
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
            pessoa_1 = em.merge(pessoa_1);
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
