package entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderDetailPK implements Serializable {
    private String orderId;
    private String itemCode;

    @Override
    public String toString() {
        return "OrderDetailPK{" +
                "orderId='" + getOrderId() + '\'' +
                ", itemCode='" + getItemCode() + '\'' +
                '}';
    }

    public OrderDetailPK(String orderId, String itemCode) {
        this.setOrderId(orderId);
        this.setItemCode(itemCode);
    }

    public OrderDetailPK() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }


}
