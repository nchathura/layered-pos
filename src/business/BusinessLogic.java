package business;

import dao.DataLayer;
import db.DBConnection;
import util.CustomerTM;
import util.ItemTM;
import util.OrderDetailTM;
import util.OrderTM;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BusinessLogic {

    public static List<CustomerTM> getAllCustomers(){
        return DataLayer.getAllCustomers();
    }

    public static boolean saveCustomer(String id, String name, String address){
        return DataLayer.saveCustomer(new CustomerTM(id,name,address));
    }

    public static boolean deleteCustomer(String customerId){
        return DataLayer.deleteCustomer(customerId);
    }

    public static boolean updateCustomer(String name, String address, String customerId){
        return DataLayer.updateCustomer(new CustomerTM(name, address, customerId));
    }

    public static List<ItemTM> getAllItems(){
        return DataLayer.getAllItems();
    }

    public static boolean saveItem(String code, String description, int qtyOnHand, double unitPrice){
        return DataLayer.saveItem(new ItemTM(code, description, qtyOnHand, unitPrice));
    }

    public static boolean deleteItem(String itemCode){
        return DataLayer.deleteItem(itemCode);
    }

    public static boolean updateItem(String description, int qtyOnHand, double unitPrice, String itemCode){
        return DataLayer.updateItem(new ItemTM(itemCode, description, qtyOnHand, unitPrice));
    }

    public static boolean placeOrder(OrderTM order, List<OrderDetailTM> orderDetails){
        return DataLayer.placeOrder(order, orderDetails);
    }


}
