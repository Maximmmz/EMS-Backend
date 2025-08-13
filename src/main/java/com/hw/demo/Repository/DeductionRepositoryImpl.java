package com.hw.demo.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class DeductionRepositoryImpl implements DeductionRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public int insertUnexcusedAbsences(int month, int year) {
        return entityManager.createNativeQuery("EXEC dbo.InsertUnexcusedAbsences :month, :year")
                .setParameter("month", month)
                .setParameter("year", year)
                .executeUpdate();
    }
}
