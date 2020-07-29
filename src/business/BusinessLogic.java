package business;

import dao.*;
import dao.impl.CustomerDAOImpl;
import dao.impl.ItemDAOImpl;
import dao.impl.OrderDAOImpl;
import dao.impl.OrderDetailDAOImpl;
import db.DBConnection;
import entity.*;
import util.CustomerTM;
import util.ItemTM;
import util.OrderDetailTM;
import util.OrderTM;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BusinessLogic {

    public static String getNewCustomerId() {
        String lastCustomerId = new CustomerDAOImpl().getLastCustomer();
        if (lastCustomerId == null) {
            return "C001";
        } else {
            int maxId = Integer.parseInt(lastCustomerId.replace("C", ""));
            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "C00" + maxId;
            } else if (maxId < 100) {
                id = "C0" + maxId;
            } else {
                id = "C" + maxId;
            }
            return id;
        }
    }

    public static String getNewItemId() {


        String lastItemId = DataLayer.getLastItemID();
        if (lastItemId == null) {
            return "I001";
        } else {
            int maxCode = Integer.parseInt(lastItemId.replace("I", ""));
            maxCode = maxCode + 1;
            String code = "";
            if (maxCode < 10) {
                code = "I00" + maxCode;
            } else if (maxCode < 100) {
                code = "I0" + maxCode;
            } else {
                code = "I" + maxCode;
            }
            return code;
        }
    }

    public static String getNewOrderId() {
        OrderDetailDAO orderDetailDAO = new OrderDetailDAOImpl();
        String oldID = orderDetailDAO.getLastOrderDetailID();
        if (oldID == null) {
            return "OD001";
        } else {
            int maxId = Integer.parseInt(oldID.replace("OD", ""));
            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "OD00" + maxId;
            } else if (maxId < 100) {
                id = "OD0" + maxId;
            } else {
                id = "OD" + maxId;
            }
            return id;
        }
    }

    public static List<CustomerTM> getAllCustomers() {
        CustomerDAO customerDAO = new CustomerDAOImpl();

        ArrayList<CustomerTM> allCustomerTMs = new ArrayList<>();

        for (Customer customer : customerDAO.findAllCustomers()) {
            allCustomerTMs.add(new CustomerTM(customer.getId(), customer.getName(), customer.getAddress()));


        }
        return allCustomerTMs;
    }

    public static boolean saveCustomer(String id, String name, String address) {
        CustomerDAOImpl customerDAO = new CustomerDAOImpl();
        return customerDAO.saveCustomer(new Customer(id, name, address));
    }

    public static boolean deleteCustomer(String customerId) {
        CustomerDAOImpl customerDAO = new CustomerDAOImpl();
        return customerDAO.deleteCustomer(new Customer(customerId));
    }

    public static boolean updateCustomer(String name, String address, String customerId) {
        CustomerDAO customerDAO = new CustomerDAOImpl();
        return customerDAO.updateCustomer(new Customer(customerId, name, address));
    }

    public static List<ItemTM> getAllItems() {
        ItemDAO itemDAO = new ItemDAOImpl();
        List<Item> allItems = itemDAO.findAllItem();
        List<ItemTM> itemTMS = new ArrayList<>();

        for (Item allItem : allItems) {
            itemTMS.add(new ItemTM(allItem.getCode(), allItem.getDescription(), allItem.getQtyOnHand(), allItem.getUnitPrice()));
        }
        return itemTMS;
    }

    public static boolean saveItem(String code, String description, int qtyOnHand, double unitPrice) {
        ItemDAO itemDAO = new ItemDAOImpl();
        return itemDAO.saveItem(
                new Item(code, description, unitPrice, qtyOnHand));
    }

    public static boolean deleteItem(String itemCode) {
        ItemDAO itemDAO = new ItemDAOImpl();
        return itemDAO.deleteItem(itemCode);
    }

    public static boolean updateItem(String description, int qtyOnHand, double unitPrice, String itemCode) {
        ItemDAO itemDAO = new ItemDAOImpl();
        return itemDAO.updateItem(new Item(itemCode, description, unitPrice, qtyOnHand));
    }


    public static boolean placeOrder(OrderTM order, List<OrderDetailTM> orderDetails) {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            OrderDAO orderDAO = new OrderDAOImpl();
            connection.setAutoCommit(false);

            boolean result = orderDAO.saveOrder(order);
            if (!result) {
                connection.rollback();
                return false;
            }
            for (OrderDetailTM orderDetail : orderDetails) {
                OrderDetailDAO orderDetailDAO = new OrderDetailDAOImpl();
                result = orderDetailDAO.saveOrder(new OrderDetail(new OrderDetailPK(order.getOrderId(), orderDetail.getCode()), orderDetail.getQty(), BigDecimal.valueOf(orderDetail.getUnitPrice())));
                if (!result) {
                    connection.rollback();
                    return false;
                }

                ItemDAO itemDAO = new ItemDAOImpl();
                result = itemDAO.updateItem(new Item(orderDetail.getCode(), orderDetail.getDescription(), Double.valueOf(orderDetail.getUnitPrice()), orderDetail.getQty()));
                if (!result) {
                    connection.rollback();
                    return false;
                }

                connection.commit();

            }
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }


}
