package dao;

import entity.Order;
import entity.OrderDetail;
import entity.OrderDetailPK;

import java.util.ArrayList;

public interface OrderDetailDAO {
    public ArrayList<Order> findAllOrderDetail();
    public OrderDetail findOrderDetail(OrderDetailPK orderDetailPK);
    public boolean saveOrder(OrderDetail orderDetail);
    public boolean deleteOrder(OrderDetailPK orderDetailPK);
    public boolean updateOrder(OrderDetail orderDetail);
    public String getLastOrderDetailID();
}
