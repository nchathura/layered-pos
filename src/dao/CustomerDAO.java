package dao;

import db.DBConnection;
import entity.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    public static List<Customer> findAllCustomers(){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet resultSet = stm.executeQuery("SELECT * FROM Customer");
            ArrayList<Customer> customers = new ArrayList<>();
            while (resultSet.next()){
                customers.add(new Customer(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3)
                        ));
            }
            return customers;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    public static Customer findCustomer(String custId){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from Customer where id = ?");
            preparedStatement.setObject(1,custId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return new Customer(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
        return null;
    }

    public static boolean saveCustomer(Customer customer){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO Customer VALUES (?,?,?)");
            pstm.setObject(1, customer.getId());
            pstm.setObject(2, customer.getName());
            pstm.setObject(3, customer.getAddress());
            return pstm.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static boolean deleteCustomer(String custId){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM Customer WHERE id=?");
            pstm.setObject(1, custId);
            return pstm.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateCustomer(Customer customer){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("UPDATE Customer SET name=?, address=? WHERE id=?");
            pstm.setObject(3, customer.getId());
            pstm.setObject(1, customer.getName());
            pstm.setObject(2, customer.getAddress());
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getLastCustomer(){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM Customer ORDER BY DESC LIMIT 1");

            if (rst.next()){
                return rst.getString(1);
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
