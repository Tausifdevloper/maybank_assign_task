package com.batch.config;


import org.springframework.batch.item.ItemProcessor;

import com.batch.model.Customer;

public class CustomItemProcessor implements ItemProcessor<Customer, Customer> {
    @Override
    public Customer process(Customer item) throws Exception {


        try {
            //Write some business  logic 
        } catch (
                NumberFormatException ex
        ) {
            ex.printStackTrace();
        }

        return item;
    }
}
