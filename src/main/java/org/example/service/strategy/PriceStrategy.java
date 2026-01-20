package org.example.service.strategy;

import org.example.model.entity.Event;
import org.example.model.entity.User;
import org.springframework.stereotype.Component;

public interface PriceStrategy {
    double calculate(User user, Event event);
    boolean hasPriorityAccess();
}

@Component
class RegularStrategy implements PriceStrategy{
    public double calculate(User user, Event event) { return event.getBasePrice(); }
    public boolean hasPriorityAccess() { return false; }
}

@Component
class VipStrategy implements PriceStrategy{
    public double calculate(User user, Event event){
        return event.isHighDemand() ? event.getBasePrice() : event.getBasePrice() * 0.9;
    }
    public boolean hasPriorityAccess(){
        return false;
    }
}

@Component
class PlatinumStrategy implements PriceStrategy{
    public double calculate(User user,Event event){
        return event.getBasePrice();
    }
    public boolean hasPriorityAccess(){
        return true;
    }
}
