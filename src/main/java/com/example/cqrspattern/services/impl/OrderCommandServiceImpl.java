package com.example.cqrspattern.services.impl;

import com.example.cqrspattern.domain.Product;
import com.example.cqrspattern.domain.PurchaseOrder;
import com.example.cqrspattern.domain.User;
import com.example.cqrspattern.repositories.ProductRepository;
import com.example.cqrspattern.repositories.PurchaseOrderRepository;
import com.example.cqrspattern.repositories.UserRepository;
import com.example.cqrspattern.services.OrderCommandService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
public class OrderCommandServiceImpl implements OrderCommandService {
  private static final long ORDER_CANCELLATION_WINDOW = 30;

  private final UserRepository userRepository;
  private final ProductRepository productRepository;
  private final PurchaseOrderRepository purchaseOrderRepository;

  private List<User> users;
  private List<Product> products;

  public OrderCommandServiceImpl(
      final UserRepository userRepository, final ProductRepository productRepository, final PurchaseOrderRepository purchaseOrderRepository) {
    this.userRepository = userRepository;
    this.productRepository = productRepository;
    this.purchaseOrderRepository = purchaseOrderRepository;
  }

  @PostConstruct
  private void init() {
    this.users = this.userRepository.findAll();
    this.products = this.productRepository.findAll();
  }

  @Override
  public void createOrder(int userIndex, int productIndex) {
    PurchaseOrder purchaseOrder = new PurchaseOrder();
    purchaseOrder.setProductId(this.products.get(productIndex).getId());
    purchaseOrder.setUserId(this.users.get(userIndex).getId());
    this.purchaseOrderRepository.save(purchaseOrder);
  }

  @Override
  public void cancelOrder(long orderId) {
    this.purchaseOrderRepository.findById(orderId)
        .ifPresent(purchaseOrder -> {
          LocalDate orderDate = LocalDate.ofInstant(purchaseOrder.getOrderDate().toInstant(), ZoneId.systemDefault());
          if (Duration.between(orderDate, LocalDate.now()).toDays() <= ORDER_CANCELLATION_WINDOW) {
            this.purchaseOrderRepository.deleteById(orderId);
            //additional logic to issue refund
          }
        });
  }
}
