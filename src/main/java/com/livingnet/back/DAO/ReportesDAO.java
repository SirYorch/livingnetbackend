package com.livingnet.back.DAO;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.livingnet.back.Model.ReporteModel;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

// clase de acceso a datos, maneja la persistencia de reportes
@Repository
public class ReportesDAO {
    
    @PersistenceContext
    private EntityManager em;

    public List<ReporteModel> findAll() {
         try {
            return em.createQuery("SELECT r FROM ReporteModel r", ReporteModel.class)
                    .getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    public ReporteModel addReporte(ReporteModel reporte) {
        System.out.println("Se llega aquí");
        em.persist(reporte);
        return reporte;
    }

    @Transactional
    public boolean deleteReporte(Long id) {
        System.out.println("Se llega aquí");
        return em.createQuery("DELETE FROM ReporteModel r WHERE r.id = :id")
                .setParameter("id", id)
                .executeUpdate() > 0;
    }

    @Transactional
    public ReporteModel updateReporte(ReporteModel reporte) {
        System.out.println("Se llega aquí");
        return em.merge(reporte);
    }
}
