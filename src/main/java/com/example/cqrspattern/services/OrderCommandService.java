package com.example.cqrspattern.services;

public interface OrderCommandService {
  void createOrder(int userIndex, int productIndex);

  void cancelOrder(long orderId);
}
