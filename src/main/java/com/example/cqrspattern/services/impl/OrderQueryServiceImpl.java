package com.example.cqrspattern.services.impl;

import com.example.cqrspattern.domain.PurchaseOrderSummary;
import com.example.cqrspattern.dtos.PurchaseOrderSummaryDto;
import com.example.cqrspattern.repositories.PurchaseOrderSummaryRepository;
import com.example.cqrspattern.services.OrderQueryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderQueryServiceImpl implements OrderQueryService {

  private final PurchaseOrderSummaryRepository purchaseOrderSummaryRepository;

  public OrderQueryServiceImpl(final PurchaseOrderSummaryRepository purchaseOrderSummaryRepository) {
    this.purchaseOrderSummaryRepository = purchaseOrderSummaryRepository;
  }

  @Override
  public List<PurchaseOrderSummaryDto> getSaleSummaryGroupByState() {
    return this.purchaseOrderSummaryRepository.findAll()
        .stream()
        .map(this::entityToDto)
        .collect(Collectors.toList());
  }

  @Override
  public PurchaseOrderSummaryDto getSaleSummaryByState(String state) {
    return this.purchaseOrderSummaryRepository.findByState(state)
        .map(this::entityToDto)
        .orElseGet(() -> new PurchaseOrderSummaryDto(state, 0));
  }

  @Override
  public double getTotalSale() {
    return this.purchaseOrderSummaryRepository.findAll()
        .stream()
        .mapToDouble(PurchaseOrderSummary::getTotalSale)
        .sum();
  }

  private PurchaseOrderSummaryDto entityToDto(PurchaseOrderSummary purchaseOrderSummary) {
    PurchaseOrderSummaryDto dto = new PurchaseOrderSummaryDto();
    dto.setState(purchaseOrderSummary.getState());
    dto.setTotalSale(purchaseOrderSummary.getTotalSale());
    return dto;
  }
}
