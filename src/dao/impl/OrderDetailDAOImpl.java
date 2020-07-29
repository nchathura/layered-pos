package dao.impl;

import dao.OrderDetailDAO;
import db.DBConnection;
import entity.Customer;
import entity.Order;
import entity.OrderDetail;
import entity.OrderDetailPK;

import java.sql.*;
import java.util.ArrayList;

public class OrderDetailDAOImpl implements OrderDetailDAO {
    public ArrayList<Order> findAllOrderDetail(){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet resultSet = stm.executeQuery("SELECT * FROM OrderDetail");
            ArrayList<Order> orderDetails = new ArrayList<>();
            while (resultSet.next()){
                orderDetails.add(new Order(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getBigDecimal(3),
                        resultSet.getInt(4)
                ));
            }
            return orderDetails;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public OrderDetail findOrderDetail(OrderDetailPK orderDetailPK){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM OrderDetail WHERE orderId = ? AND itemCode = ?");
            preparedStatement.setString(1,orderDetailPK.getOrderId());
            preparedStatement.setObject(2,orderDetailPK.getItemCode());
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return new OrderDetail(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getBigDecimal(4));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
        return null;
    }

    public boolean saveOrder(OrderDetail orderDetail){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO OrderDetail VALUES (?,?,?,?)");
            preparedStatement.setObject(1,orderDetail.getOrderDetailPK().getOrderId());
            preparedStatement.setObject(2,orderDetail.getOrderDetailPK().getItemCode());
            preparedStatement.setObject(3,orderDetail.getQty());
            preparedStatement.setObject(4,orderDetail.getUnitPrice());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public boolean deleteOrder(OrderDetailPK orderDetailPK){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE OrderDetail WHERE orderId = ? AND itemCode = ?");
            preparedStatement.setObject(1,orderDetailPK.getOrderId());
            preparedStatement.setObject(2,orderDetailPK.getItemCode());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public boolean updateOrder(OrderDetail orderDetail){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE OrderDetail SET qty =  ?,unitPrice = ? WHERE orderId = ? AND itemCode = ?");
            preparedStatement.setObject(1,orderDetail.getQty());
            preparedStatement.setObject(2,orderDetail.getUnitPrice());
            preparedStatement.setObject(3,orderDetail.getOrderDetailPK().getOrderId());
            preparedStatement.setObject(3,orderDetail.getOrderDetailPK().getItemCode());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public String getLastOrderDetailID() {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id FROM `Order` ORDER BY id DESC limit 1");
            resultSet.next();
            return resultSet.getString(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
