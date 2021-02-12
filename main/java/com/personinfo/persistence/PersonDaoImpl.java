package com.personinfo.persistence;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.personinfo.entity.PersonEntity;

@Singleton
public class PersonDaoImpl implements PersonDao {

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("persist-unit");
	
	public PersonEntity retrieve(Long id) {
		EntityManager em = emf.createEntityManager();

		PersonEntity entity = null;

		try {
			entity = em.find(PersonEntity.class, id);
		} finally {
			em.close();
		}
		
		return entity;
	}

    public void delete(Long id) {
        EntityManager em = emf.createEntityManager();

        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            PersonEntity entity = em.find(PersonEntity.class, id);

            if(entity == null) {
                System.out.println("Error Deleting Customer: Customer not found");
            }
            else {
                em.remove(entity);
            }

            transaction.commit();
        } catch (Exception e) {
            System.out.println("Error Deleting Customer: " + e.getMessage());

            transaction.rollback();
        } finally {
            em.close();
        }
    }

	public void save(PersonEntity customer) {
		EntityManager em = emf.createEntityManager();

		EntityTransaction transaction = em.getTransaction();

		try {
			transaction.begin();

            em.merge(customer);

			transaction.commit();
		} catch (Exception e) {
			System.out.println("Error Saving Customer: " + e.getMessage());

			transaction.rollback();
		} finally {
			em.close();
		}
	}
}
