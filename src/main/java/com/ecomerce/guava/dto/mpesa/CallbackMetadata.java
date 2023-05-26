package com.ecomerce.guava.dto.mpesa;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CallbackMetadata {
    @JsonProperty("Item")
    private List<ItemItem> item;

}
