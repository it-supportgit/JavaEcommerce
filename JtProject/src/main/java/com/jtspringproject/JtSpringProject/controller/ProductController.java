@Autowired
private OrderService orderService;

public String testWrongServiceUsage() {
    orderService.getAllOrders();
    return "products"; 
}
