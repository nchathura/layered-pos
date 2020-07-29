package dao.impl;

import dao.OrderDAO;
import db.DBConnection;
import entity.Order;
import util.OrderTM;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    public List<Order> findAllOrders(){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet resultSet = stm.executeQuery("SELECT * FROM Order");
            ArrayList<Order> orders = new ArrayList<>();
            while (resultSet.next()){
                orders.add(new Order(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getBigDecimal(3),
                        resultSet.getInt(4)
                ));
            }
            return orders;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Order findOrder(String OrderId){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from `Order` where id = ?");
            preparedStatement.setObject(1, OrderId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return new Order(resultSet.getString(1),
                        resultSet.getDate(2),
                        resultSet.getString(3));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
        return null;
    }

    public boolean saveOrder(OrderTM order){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO `Order` VALUES (?,?,?)");
            pstm.setObject(1, order.getOrderId());
            pstm.setObject(2, order.getOrderDate());
            pstm.setObject(3, order.getCustomerId());
            return pstm.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteOrder(String orderId){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM `Order` WHERE orderId=?");
            pstm.setObject(1, orderId);
            return pstm.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateOrder(Order order){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("UPDATE `Order` SET `date`=?, customerId=? WHERE id=?");
            pstm.setObject(3, order.getId());
            pstm.setObject(1, order.getDate());
            pstm.setObject(2, order.getCustomerId());
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
