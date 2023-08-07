package com.booking.response;

public enum ResponseMessage {
    COIN_INSERTED_SUCCESSFULLY("Coin inserted successfully."),
    COIN_REFUNDED_SUCCESSFULLY("Coin(s) refunded successfully."),
    PRODUCT_LIST_RETURNED_SUCCESSFULLY("Available products listed successfully."),
    PRODUCT_BOUGHT_SUCCESSFULLY("Product bought successfully.");
    
    private String message;
    
    ResponseMessage(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}
