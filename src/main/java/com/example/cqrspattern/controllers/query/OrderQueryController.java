package com.example.cqrspattern.controllers.query;

import com.example.cqrspattern.dtos.PurchaseOrderSummaryDto;
import com.example.cqrspattern.services.OrderQueryService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@ConditionalOnProperty(name = "app.write.enabled", havingValue = "false")
public class OrderQueryController {
  private final OrderQueryService orderQueryService;

  public OrderQueryController(final OrderQueryService orderQueryService) {
    this.orderQueryService = orderQueryService;
  }

  @GetMapping("/summary")
  public List<PurchaseOrderSummaryDto> getSummary() {
    return this.orderQueryService.getSaleSummaryGroupByState();
  }

  @GetMapping("/summary/{state}")
  public PurchaseOrderSummaryDto getStateSummary(@PathVariable String state) {
    return this.orderQueryService.getSaleSummaryByState(state);
  }

  @GetMapping("/total-sale")
  public Double getTotalSale() {
    return this.orderQueryService.getTotalSale();
  }
}
