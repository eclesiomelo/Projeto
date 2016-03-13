/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.controller;

import info.controller.exceptions.NonexistentEntityException;
import info.controller.exceptions.PreexistingEntityException;
import info.modal.Sequence_1;
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
public class Sequence_1JpaController implements Serializable {

    public Sequence_1JpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public Sequence_1JpaController() {
        String up = "InfoPU";
        emf = Persistence.createEntityManagerFactory(up);
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sequence_1 sequence_1) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(sequence_1);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSequence_1(sequence_1.getSeqName()) != null) {
                throw new PreexistingEntityException("Sequence_1 " + sequence_1 + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sequence_1 sequence_1) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            sequence_1 = em.merge(sequence_1);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = sequence_1.getSeqName();
                if (findSequence_1(id) == null) {
                    throw new NonexistentEntityException("The sequence_1 with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sequence_1 sequence_1;
            try {
                sequence_1 = em.getReference(Sequence_1.class, id);
                sequence_1.getSeqName();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sequence_1 with id " + id + " no longer exists.", enfe);
            }
            em.remove(sequence_1);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sequence_1> findSequence_1Entities() {
        return findSequence_1Entities(true, -1, -1);
    }

    public List<Sequence_1> findSequence_1Entities(int maxResults, int firstResult) {
        return findSequence_1Entities(false, maxResults, firstResult);
    }

    private List<Sequence_1> findSequence_1Entities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sequence_1.class));
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

    public Sequence_1 findSequence_1(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sequence_1.class, id);
        } finally {
            em.close();
        }
    }

    public int getSequence_1Count() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sequence_1> rt = cq.from(Sequence_1.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
