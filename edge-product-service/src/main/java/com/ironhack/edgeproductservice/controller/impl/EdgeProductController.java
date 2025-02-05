package com.ironhack.edgeproductservice.controller.impl;

import com.ironhack.edgeproductservice.classes.Price;
import com.ironhack.edgeproductservice.classes.StoreProduct;
import com.ironhack.edgeproductservice.client.ExchangeClient;
import com.ironhack.edgeproductservice.client.StoreProductClient;
import com.ironhack.edgeproductservice.controller.interfaces.IEdgeProductController;
import com.ironhack.edgeproductservice.dto.PriceDto;
import com.ironhack.edgeproductservice.service.interfaces.IEdgeProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Currency;

@RestController
public class EdgeProductController implements IEdgeProductController {

    @Autowired
    private IEdgeProductService edgeProductService;

    @GetMapping("/product/{productId}/{currency}")
    @ResponseStatus(HttpStatus.OK)
    public StoreProduct productInOtherCurrency(@PathVariable("productId") Integer productId, @PathVariable("currency")  String currency){

        return edgeProductService.productInOtherCurrency(productId, currency);
    }

}
