package dao;

import entity.Order;
import util.OrderTM;

import java.util.List;

public interface OrderDAO {
    public List<Order> findAllOrders();
    public Order findOrder(String OrderId);
    public boolean saveOrder(OrderTM order);
    public boolean deleteOrder(String orderId);
    public boolean updateOrder(Order order);

}
