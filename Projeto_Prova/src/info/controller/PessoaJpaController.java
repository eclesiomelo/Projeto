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
import info.modal.Contato;
import info.modal.Endereco;
import info.modal.Pessoa;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Eclesio
 */
public class PessoaJpaController implements Serializable {

    public PessoaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public PessoaJpaController() {
        String up = "InfoPU";
        emf = Persistence.createEntityManagerFactory(up);
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pessoa pessoa) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Contato contId = pessoa.getContId();
            if (contId != null) {
                contId = em.getReference(contId.getClass(), contId.getId());
                pessoa.setContId(contId);
            }
            Endereco endId = pessoa.getEndId();
            if (endId != null) {
                endId = em.getReference(endId.getClass(), endId.getId());
                pessoa.setEndId(endId);
            }
            em.persist(pessoa);
            if (contId != null) {
                contId.getPessoaCollection().add(pessoa);
                contId = em.merge(contId);
            }
            if (endId != null) {
                endId.getPessoaCollection().add(pessoa);
                endId = em.merge(endId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pessoa pessoa) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pessoa persistentPessoa = em.find(Pessoa.class, pessoa.getId());
            Contato contIdOld = persistentPessoa.getContId();
            Contato contIdNew = pessoa.getContId();
            Endereco endIdOld = persistentPessoa.getEndId();
            Endereco endIdNew = pessoa.getEndId();
            if (contIdNew != null) {
                contIdNew = em.getReference(contIdNew.getClass(), contIdNew.getId());
                pessoa.setContId(contIdNew);
            }
            if (endIdNew != null) {
                endIdNew = em.getReference(endIdNew.getClass(), endIdNew.getId());
                pessoa.setEndId(endIdNew);
            }
            pessoa = em.merge(pessoa);
            if (contIdOld != null && !contIdOld.equals(contIdNew)) {
                contIdOld.getPessoaCollection().remove(pessoa);
                contIdOld = em.merge(contIdOld);
            }
            if (contIdNew != null && !contIdNew.equals(contIdOld)) {
                contIdNew.getPessoaCollection().add(pessoa);
                contIdNew = em.merge(contIdNew);
            }
            if (endIdOld != null && !endIdOld.equals(endIdNew)) {
                endIdOld.getPessoaCollection().remove(pessoa);
                endIdOld = em.merge(endIdOld);
            }
            if (endIdNew != null && !endIdNew.equals(endIdOld)) {
                endIdNew.getPessoaCollection().add(pessoa);
                endIdNew = em.merge(endIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pessoa.getId();
                if (findPessoa(id) == null) {
                    throw new NonexistentEntityException("The pessoa with id " + id + " no longer exists.");
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
            Pessoa pessoa;
            try {
                pessoa = em.getReference(Pessoa.class, id);
                pessoa.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pessoa with id " + id + " no longer exists.", enfe);
            }
            Contato contId = pessoa.getContId();
            if (contId != null) {
                contId.getPessoaCollection().remove(pessoa);
                contId = em.merge(contId);
            }
            Endereco endId = pessoa.getEndId();
            if (endId != null) {
                endId.getPessoaCollection().remove(pessoa);
                endId = em.merge(endId);
            }
            em.remove(pessoa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pessoa> findPessoaEntities() {
        return findPessoaEntities(true, -1, -1);
    }

    public List<Pessoa> findPessoaEntities(int maxResults, int firstResult) {
        return findPessoaEntities(false, maxResults, firstResult);
    }

    private List<Pessoa> findPessoaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pessoa.class));
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

    public Pessoa findPessoa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pessoa.class, id);
        } finally {
            em.close();
        }
    }

    public int getPessoaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pessoa> rt = cq.from(Pessoa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
