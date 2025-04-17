public class Order {
    String id;
    Customer customer;
    Integer shippingFee;
    List<OrderStatus> orderStatuses;
    OrderStatus currentStatus;
    List<OrderItem> orderItems;
    Integer paymentMethod;
    Double totalAmount;
    Double totalDiscount;

    public Order(Customer customer, Integer shippingFee, Integer paymentMethod) { … }

    public void makeOrder(List<OrderItemDetails> orderItemList) { … }
}
