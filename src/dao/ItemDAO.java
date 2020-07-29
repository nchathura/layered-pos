package dao;

import entity.Item;
import entity.Order;

import java.util.List;

public interface ItemDAO {
    public List<Item> findAllItem();
    public Item findItem(String itemCode);
    public boolean saveItem(Item item);
    public boolean deleteItem(String itemCode);
    public boolean updateItem(Item item);
}
