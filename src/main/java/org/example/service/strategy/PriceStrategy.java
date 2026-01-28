package org.example.service.strategy;

import org.example.model.entity.Event;
import org.example.model.entity.User;
import org.springframework.stereotype.Component;

public interface PriceStrategy {
    User.Tier supports();
    double calculate(User user, Event event);
    boolean hasPriorityAccess();
}

@Component
class RegularStrategy implements PriceStrategy{

    @Override
    public User.Tier supports(){
        return User.Tier.REGULAR;
    }

    public double calculate(User user, Event event) { return event.getBasePrice(); }
    public boolean hasPriorityAccess() { return false; }
}

@Component
class VipStrategy implements PriceStrategy{

    @Override
    public User.Tier supports(){
        return User.Tier.VIP;
    }

    public double calculate(User user, Event event){
        return event.isHighDemand() ? event.getBasePrice() : event.getBasePrice() * 0.9;
    }
    public boolean hasPriorityAccess(){
        return false;
    }
}

@Component
class PlatinumStrategy implements PriceStrategy{

    @Override
    public User.Tier supports(){
        return User.Tier.PLATINUM;
    }

    public double calculate(User user,Event event){
        return event.getBasePrice();
    }
    public boolean hasPriorityAccess(){
        return true;
    }
}
