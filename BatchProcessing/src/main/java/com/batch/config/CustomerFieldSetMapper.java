package com.batch.config;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.batch.model.Customer;

public class CustomerFieldSetMapper  implements FieldSetMapper<Customer>{
	
	@Override
    public Customer mapFieldSet(FieldSet fieldSet) throws BindException {
        return new Customer(
            fieldSet.readString("accountNumber"),
            fieldSet.readString("trxAmount"),
            fieldSet.readString("description"),
            fieldSet.readString("trxDate"),
            fieldSet.readString("trx_time"),
            fieldSet.readString("customerId")
        );
    }

}
