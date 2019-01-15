/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.EntityExample;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class EntityFacade<T> {

    private EntityManagerFactory emf;
    private String className;
    private Class c;

    public EntityFacade(Class c) {
        this(Persistence.createEntityManagerFactory("pu"), c);
    }

    public EntityFacade(EntityManagerFactory emf, Class c) {
        this.emf = emf;
        String[] arr = c.getName().split("\\.");
        this.className = arr[arr.length - 1];
        this.c = c;
    }

    private EntityManager getEm() {
        return emf.createEntityManager();
    }

    public List<T> getAllEntity() {
        EntityManager em = getEm();

        try {
            return em.createQuery("SELECT t FROM " + className + " t").getResultList();
        } finally {
            em.close();
        }
    }

    public T createEntity(T t) {
        EntityManager em = getEm();

        try {
            em.getTransaction().begin();
            em.persist(t);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return t;
    }

    public T findEntityByValue(String search, String value) {
        EntityManager em = getEm();

        try {
            TypedQuery<T> q = em.createQuery("SELECT t FROM " + className + " t WHERE t." + search + " = :value", c);
            q.setParameter("value", value);
            return q.getSingleResult();
        } finally {
            em.close();
        }
    }

    public T deleteEntity(int id) {
        EntityManager em = getEm();

        try {
            em.getTransaction().begin();
            T t = (T) em.find(c, id);
            em.remove(t);
            em.getTransaction().commit();
            return t;
        } finally {
            em.close();
        }
    }

    public T editEntity(T t) {
        EntityManager em = getEm();

        try {
            em.getTransaction().begin();
            em.merge(t);
            em.getTransaction().commit();
            return t;
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) {
//        EntityExample ex1 = new EntityExample("name1", "email1");
//        EntityExample ex2 = new EntityExample("name2", "email2");
//        EntityExample ex3 = new EntityExample("name3", "email3");
//        EntityExample ex4 = new EntityExample("name4", "email4");
//        EntityExample ex5 = new EntityExample("name5", "email5");
//
//        EntityFacade<EntityExample> facade = new EntityFacade<>(EntityExample.class);
//        facade.createResource(ex1);
//        facade.createResource(ex2);
//        facade.createResource(ex3);
//        facade.createResource(ex4);
//        facade.createResource(ex5);
//        System.out.println(facade.findByValue("name", "name4"));
//        System.out.println(facade.deleteEntity(3));
//        EntityExample ee = facade.findByValue("email", "email5");
//        ee.setEmail("ViTesterLige");
//        facade.editEntity(ee);
//        System.out.println(facade.getAllEntity());
    }
}
