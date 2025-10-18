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

    public Integer findOrderCountByPartnerId(String partnerId){
        // your code here
        if(!partnerMap.containsKey(partnerId)){
            return 0;
        }
        return partnerMap.get(partnerId).getNumberOfOrders();
    }

    public List<String> findOrdersByPartnerId(String partnerId){
        // your code here
        HashSet<String> set=partnerToOrderMap.get(partnerId);
        List<String> list=new ArrayList<>(set);
        return list;
    }

    public List<String> findAllOrders(){
        // your code here
        // return list of all orders
        List<String> list=new ArrayList<>(orderMap.keySet());
        return list;
    }

    public void deletePartner(String partnerId){
        // your code here
        // delete partner by ID
        if(partnerToOrderMap.containsKey(partnerId)){
            HashSet<String> orders=partnerToOrderMap.get(partnerId);
            if(orders!=null){
                for(String orderID:orders){
                    orderToPartnerMap.remove(orderID);
                }
            }
        }
        partnerMap.remove(partnerId);
        partnerToOrderMap.remove(partnerId);
    }

    public void deleteOrder(String orderId){
        // your code here
        // delete order by ID
        String partnerId=null;
        if(orderToPartnerMap.containsKey(orderId)){
            partnerId=orderToPartnerMap.get(orderId);

            partnerToOrderMap.get(partnerId).remove(orderId);

            DeliveryPartner partner=partnerMap.get(partnerId);
            partner.setNumberOfOrders(partner.getNumberOfOrders()-1);
        }
        orderToPartnerMap.remove(orderId);
        orderMap.remove(orderId);
    }

    public Integer findCountOfUnassignedOrders(){
        // your code here
        int count=0;
        for(String order:orderMap.keySet()){
            if(!orderToPartnerMap.containsKey(order)){
                count++;
            }
        }
        return count;
    }

    public Integer findOrdersLeftAfterGivenTimeByPartnerId(String timeString, String partnerId){
        // your code here
        int count=0;
        String[] arr=timeString.split(":");
        int delTime=(Integer.parseInt(arr[0])*60)+(Integer.parseInt(arr[1]));
        for(String orderId:partnerToOrderMap.get(partnerId)){
            Order order=orderMap.get(orderId);
            if(delTime<order.getDeliveryTime()){
                count++;
            }
        }
        return count;
    }

    public String findLastDeliveryTimeByPartnerId(String partnerId){
        // your code here
        // code should return string in format HH:MM
        int time=0;
        if(partnerToOrderMap.containsKey(partnerId)){
            HashSet<String> set=partnerToOrderMap.get(partnerId);
            for(String order:set){
                if(time<orderMap.get(order).getDeliveryTime()){
                    time=orderMap.get(order).getDeliveryTime();
                }
            }
        }
        int hours=time/60;
        int min=time%60;
        String lastTime=String.format("%02d:%02d",hours,min);
//        (OR)
//        String lastTime=(hours<10?"0"+hours:hours)+":"+(min<10?"0"+min:min);
        return lastTime;
    }
}