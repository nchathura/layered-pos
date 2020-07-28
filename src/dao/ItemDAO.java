package dao;

import db.DBConnection;
import entity.Item;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {
    public static List<Item> findAllItem(){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet resultSet = stm.executeQuery("SELECT * FROM Item");
            ArrayList<Item> item = new ArrayList<>();
            while (resultSet.next()){
                item.add(new Item(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getBigDecimal(3),
                        resultSet.getInt(4)
                ));
            }
            return item;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Item findItem(String itemCode){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from Item where code = ?");
            preparedStatement.setObject(1,itemCode);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return new Item(resultSet.getString(1),
                        resultSet.getString(2),
                        new BigDecimal(resultSet.getString(3)),
                        Integer.parseInt(resultSet.getString(4)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
        return null;
    }

    public static boolean saveItem(Item item){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO Item VALUES (?,?,?,?)");
            pstm.setObject(1, item.getCode());
            pstm.setObject(2, item.getDescription());
            pstm.setObject(3, item.getUnitPrice());
            pstm.setObject(4, item.getQtyOnHand());
            return pstm.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteItem(String itemCode){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM Item WHERE itemCode=?");
            pstm.setObject(1, itemCode);
            return pstm.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateItem(Item item){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?");
            pstm.setObject(3, item.getCode());
            pstm.setObject(1, item.getDescription());
            pstm.setObject(2, item.getUnitPrice());
            pstm.setObject(3, item.getQtyOnHand());
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getLastCustomerID() {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from Item order by code desc limit 1");
            if(resultSet.next()){
                return resultSet.getString(1);
            }else{
                return null;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
}
