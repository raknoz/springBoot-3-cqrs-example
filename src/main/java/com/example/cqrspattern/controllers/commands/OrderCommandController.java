package com.example.cqrspattern.controllers.commands;

import com.example.cqrspattern.dtos.OrderCommandDto;
import com.example.cqrspattern.services.OrderCommandService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
@ConditionalOnProperty(name = "app.write.enabled", havingValue = "true")
public class OrderCommandController {
  private final OrderCommandService orderCommandService;

  public OrderCommandController(final OrderCommandService orderCommandService) {
    this.orderCommandService = orderCommandService;
  }

  @PostMapping("/sale")
  public void placeOrder(@RequestBody OrderCommandDto dto) {
    this.orderCommandService.createOrder(dto.getUserIndex(), dto.getProductIndex());
  }

  @PutMapping("/cancel-order/{orderId}")
  public void cancelOrder(@PathVariable long orderId) {
    this.orderCommandService.cancelOrder(orderId);
  }
}
