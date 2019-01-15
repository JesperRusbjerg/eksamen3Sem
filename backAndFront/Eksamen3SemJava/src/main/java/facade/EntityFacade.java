/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Jesper
 * @param <T>
 */
public class EntityFacade <T> {
    
    private final EntityManagerFactory emf;
    
    public EntityFacade() {
        this.emf = Persistence.createEntityManagerFactory("pu");
    }
    
    private EntityManager getEntityManager(){
     return emf.createEntityManager();
    }

    public T persistEntity(T t) {
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(t);
            em.getTransaction().commit();
        }catch(Exception e){
        }finally {
            em.close();
        }
        return t;
    }
    
    public void removeEntity(int id, Class<T> whatClass){
        EntityManager em = getEntityManager();
        try{
           T t = em.find(whatClass, id);
           em.getTransaction().begin();
           em.remove(t);
           em.getTransaction().commit();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }finally {
            em.close();
        }
    }
    
    public T findEntity(int id, Class<T> whatClass){
        EntityManager em = getEntityManager();
       T t = null;
        try{ 
         t = em.find(whatClass, id);
         if(t == null){
             throw new Exception("Could not find Entity");
         }
       }catch(Exception e){
           System.out.println(e.getMessage());
       }finally {
            em.close();
        }
        return t;
    }
    
    public void editEntity(T t){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.merge(t);
            em.getTransaction().commit();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }finally {
            em.close();
        }
    }

    
}
