/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.controller;

import info.controller.exceptions.NonexistentEntityException;
import info.modal.Endereco_1;
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
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(endereco_1);
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
            endereco_1 = em.merge(endereco_1);
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
