package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

    private HashMap<String, Order> orderMap;
    private HashMap<String, DeliveryPartner> partnerMap;
    private HashMap<String, HashSet<String>> partnerToOrderMap;
    private HashMap<String, String> orderToPartnerMap;

    public OrderRepository(){
        this.orderMap = new HashMap<String, Order>();
        this.partnerMap = new HashMap<String, DeliveryPartner>();
        this.partnerToOrderMap = new HashMap<String, HashSet<String>>();
        this.orderToPartnerMap = new HashMap<String, String>();
    }

    public void saveOrder(Order order){
        // your code here
        orderMap.put(order.getId(),order);
    }

    public void savePartner(String partnerId){
        // your code here
        // create a new partner with given partnerId and save it
        partnerMap.put(partnerId,new DeliveryPartner(partnerId));
    }

    public void saveOrderPartnerMap(String orderId, String partnerId){
        if(orderMap.containsKey(orderId) && partnerMap.containsKey(partnerId)){
            // your code here
            //add order to given partner's order list
            if(!partnerToOrderMap.containsKey(partnerId)){
                partnerToOrderMap.put(partnerId,new HashSet<>());
            }
            partnerToOrderMap.get(partnerId).add(orderId);

            //increase order count of partner
            int count=partnerMap.get(partnerId).getNumberOfOrders();
            partnerMap.get(partnerId).setNumberOfOrders(count+1);

            //assign partner to this order
            orderToPartnerMap.put(orderId,partnerId);
        }
    }

    public Order findOrderById(String orderId){
        // your code here
        if(!orderMap.containsKey(orderId)){
            return null;
        }
        return orderMap.get(orderId);
    }

    public DeliveryPartner findPartnerById(String partnerId){
        // your code here
        if(!partnerMap.containsKey(partnerId)){
            return null;
        }
        return partnerMap.get(partnerId);
    }
    /*

    public Integer findOrderCountByPartnerId(String partnerId){
        // your code here
    }

    public List<String> findOrdersByPartnerId(String partnerId){
        // your code here
    }

    public List<String> findAllOrders(){
        // your code here
        // return list of all orders
    }

    public void deletePartner(String partnerId){
        // your code here
        // delete partner by ID
    }

    public void deleteOrder(String orderId){
        // your code here
        // delete order by ID
    }

    public Integer findCountOfUnassignedOrders(){
        // your code here
    }

    public Integer findOrdersLeftAfterGivenTimeByPartnerId(String timeString, String partnerId){
        // your code here
    }

    public String findLastDeliveryTimeByPartnerId(String partnerId){
        // your code here
        // code should return string in format HH:MM
    }

     */
}