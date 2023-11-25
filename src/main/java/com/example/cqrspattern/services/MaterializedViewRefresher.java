package com.example.cqrspattern.services;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MaterializedViewRefresher {
  private final EntityManager entityManager;

  public MaterializedViewRefresher(final EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Transactional
  @Scheduled(fixedRate = 5000L)
  public void refresh() {
    this.entityManager.createNativeQuery("call refresh_mat_view();").executeUpdate();
  }

}
