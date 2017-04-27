package com.artecoconsulting.compra.mysql;

import com.artecoconsulting.compra.model.Item;
import com.artecoconsulting.compra.model.Order;
import com.artecoconsulting.compra.model.Shop;
import com.artecoconsulting.compra.model.ShoppingCart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arteco1 on 27/04/2017.
 */
public class MysqlShoppingCart implements ShoppingCart {

    private static long count = 0;

    private Long cartId = count++;

    private final Database database;

    public MysqlShoppingCart(Database database) {
        this.database = database;
    }


    @Override
    public boolean addItem(Item item) {
        if (item != null) {
            database.saveCartItem(cartId, item);
            return true;
        }
        return false;
    }

    @Override
    public List<Item> getItems() {
        return database.getCartItems(cartId);
    }

    @Override
    public int getProductCount() {
        int totalCantidad = 0;
        for (Item item : getItems()) {
            totalCantidad += item.getCantidad();
        }
        return totalCantidad;
    }

    @Override
    public Order checkout(Shop shop) {
        Order order = new Order(new ArrayList<>(getItems()));
        database.removeAllCartItems(cartId);
        shop.getOrders().add(order);
        return order;
    }

    @Override
    public void removeItem(Item item, Shop shop) {
        database.removeCartItem(cartId, item);
        Item dbItem = shop.getItem(item.getId());
        if (dbItem != null) {
            dbItem.setCantidad(item.getCantidad() + dbItem.getCantidad());
            database.saveItem(dbItem);
        }
    }

    @Override
    public void removeAll(Shop shop) {
        List<Item> items = new ArrayList<>(getItems());
        for (Item item : items) {
            removeItem(item, shop);
        }
    }

    @Override
    public BigDecimal getTotalCartPrice() {
        BigDecimal costeTotal = new BigDecimal(0);
        for (Item item : getItems()) {
            costeTotal = costeTotal.add(item.getPrecio())
                    .multiply(BigDecimal.valueOf(item.getCantidad()));
        }
        return costeTotal;
    }
}