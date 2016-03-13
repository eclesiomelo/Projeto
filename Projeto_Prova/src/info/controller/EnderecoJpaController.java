/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.controller;

import info.controller.exceptions.NonexistentEntityException;
import info.controller.exceptions.PreexistingEntityException;
import info.modal.Endereco;
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
public class EnderecoJpaController implements Serializable {

    public EnderecoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EnderecoJpaController() {
        String up = "InfoPU";
        emf = Persistence.createEntityManagerFactory(up);
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Endereco endereco) throws PreexistingEntityException, Exception {
        if (endereco.getPessoaCollection() == null) {
            endereco.setPessoaCollection(new ArrayList<Pessoa>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Pessoa> attachedPessoaCollection = new ArrayList<Pessoa>();
            for (Pessoa pessoaCollectionPessoaToAttach : endereco.getPessoaCollection()) {
                pessoaCollectionPessoaToAttach = em.getReference(pessoaCollectionPessoaToAttach.getClass(), pessoaCollectionPessoaToAttach.getId());
                attachedPessoaCollection.add(pessoaCollectionPessoaToAttach);
            }
            endereco.setPessoaCollection(attachedPessoaCollection);
            em.persist(endereco);
            for (Pessoa pessoaCollectionPessoa : endereco.getPessoaCollection()) {
                Endereco oldEndIdOfPessoaCollectionPessoa = pessoaCollectionPessoa.getEndId();
                pessoaCollectionPessoa.setEndId(endereco);
                pessoaCollectionPessoa = em.merge(pessoaCollectionPessoa);
                if (oldEndIdOfPessoaCollectionPessoa != null) {
                    oldEndIdOfPessoaCollectionPessoa.getPessoaCollection().remove(pessoaCollectionPessoa);
                    oldEndIdOfPessoaCollectionPessoa = em.merge(oldEndIdOfPessoaCollectionPessoa);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEndereco(endereco.getId()) != null) {
                throw new PreexistingEntityException("Endereco " + endereco + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Endereco endereco) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Endereco persistentEndereco = em.find(Endereco.class, endereco.getId());
            Collection<Pessoa> pessoaCollectionOld = persistentEndereco.getPessoaCollection();
            Collection<Pessoa> pessoaCollectionNew = endereco.getPessoaCollection();
            Collection<Pessoa> attachedPessoaCollectionNew = new ArrayList<Pessoa>();
            for (Pessoa pessoaCollectionNewPessoaToAttach : pessoaCollectionNew) {
                pessoaCollectionNewPessoaToAttach = em.getReference(pessoaCollectionNewPessoaToAttach.getClass(), pessoaCollectionNewPessoaToAttach.getId());
                attachedPessoaCollectionNew.add(pessoaCollectionNewPessoaToAttach);
            }
            pessoaCollectionNew = attachedPessoaCollectionNew;
            endereco.setPessoaCollection(pessoaCollectionNew);
            endereco = em.merge(endereco);
            for (Pessoa pessoaCollectionOldPessoa : pessoaCollectionOld) {
                if (!pessoaCollectionNew.contains(pessoaCollectionOldPessoa)) {
                    pessoaCollectionOldPessoa.setEndId(null);
                    pessoaCollectionOldPessoa = em.merge(pessoaCollectionOldPessoa);
                }
            }
            for (Pessoa pessoaCollectionNewPessoa : pessoaCollectionNew) {
                if (!pessoaCollectionOld.contains(pessoaCollectionNewPessoa)) {
                    Endereco oldEndIdOfPessoaCollectionNewPessoa = pessoaCollectionNewPessoa.getEndId();
                    pessoaCollectionNewPessoa.setEndId(endereco);
                    pessoaCollectionNewPessoa = em.merge(pessoaCollectionNewPessoa);
                    if (oldEndIdOfPessoaCollectionNewPessoa != null && !oldEndIdOfPessoaCollectionNewPessoa.equals(endereco)) {
                        oldEndIdOfPessoaCollectionNewPessoa.getPessoaCollection().remove(pessoaCollectionNewPessoa);
                        oldEndIdOfPessoaCollectionNewPessoa = em.merge(oldEndIdOfPessoaCollectionNewPessoa);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = endereco.getId();
                if (findEndereco(id) == null) {
                    throw new NonexistentEntityException("The endereco with id " + id + " no longer exists.");
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
            Endereco endereco;
            try {
                endereco = em.getReference(Endereco.class, id);
                endereco.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The endereco with id " + id + " no longer exists.", enfe);
            }
            Collection<Pessoa> pessoaCollection = endereco.getPessoaCollection();
            for (Pessoa pessoaCollectionPessoa : pessoaCollection) {
                pessoaCollectionPessoa.setEndId(null);
                pessoaCollectionPessoa = em.merge(pessoaCollectionPessoa);
            }
            em.remove(endereco);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Endereco> findEnderecoEntities() {
        return findEnderecoEntities(true, -1, -1);
    }

    public List<Endereco> findEnderecoEntities(int maxResults, int firstResult) {
        return findEnderecoEntities(false, maxResults, firstResult);
    }

    private List<Endereco> findEnderecoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Endereco.class));
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

    public Endereco findEndereco(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Endereco.class, id);
        } finally {
            em.close();
        }
    }

    public int getEnderecoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Endereco> rt = cq.from(Endereco.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
