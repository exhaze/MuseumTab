package com.jes.museumtab.backend;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public enum Dao {
	INSTANCE;
	
	public List<ExhibitData> listExhibits() {
		EntityManager em = EMFService.get().createEntityManager();
		
		// read the existing entries
		Query q = em.createQuery("select m from ExhibitData m");
		List<ExhibitData> exhibits = q.getResultList();
		return exhibits;
	}
	
	public void add(String name, String desc) {
		synchronized (this) {
			EntityManager em = EMFService.get().createEntityManager();
			ExhibitData exhibit = new ExhibitData(name, desc);
			em.persist(exhibit);
			em.close();
		}
	}
	
	public void remove(long id) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			ExhibitData exhibit = em.find(ExhibitData.class, id);
			em.remove(exhibit);
		} finally {
			em.close();
		}
	}
}
