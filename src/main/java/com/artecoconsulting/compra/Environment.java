package com.artecoconsulting.compra;

import com.artecoconsulting.compra.model.Shop;
import com.artecoconsulting.compra.model.ShoppingCart;

/**
 * Created by arteco1 on 20/04/2017.
 */
public interface Environment {

    /**
     * Devuelve la instancia de la tienda sobre la que se va a trabajar.
     * @return
     */
    Shop getShop();

    /**
     * Devuelve la instancia del un carrito sobre la que se va a trabajar.
     * Se crea un carrito nuevo por cada compra
     * @return
     */
    ShoppingCart newShoppingCart();

}
