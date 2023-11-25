package com.example.cqrspattern.services;

import com.example.cqrspattern.dtos.PurchaseOrderSummaryDto;

import java.util.List;

public interface OrderQueryService {
  List<PurchaseOrderSummaryDto> getSaleSummaryGroupByState();

  PurchaseOrderSummaryDto getSaleSummaryByState(String state);

  double getTotalSale();
}
