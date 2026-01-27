package org.example.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.model.entity.Event;
import org.example.model.entity.User;
import org.example.repository.EventRepository;
import org.example.repository.UserRepository;
import org.example.service.strategy.PlatinumStrategy;
import org.example.service.strategy.PriceStrategy;
import org.example.service.strategy.RegularStrategy;
import org.example.service.strategy.VipStrategy;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PriceCalculaterService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    private final Map<User.Tier, PriceStrategy> strategies = Map.of(
      User.Tier.REGULAR, new RegularStrategy(),
      User.Tier.VIP, new VipStrategy(),
      User.Tier.PLATINUM, new PlatinumStrategy()
    );

    public PriceResult calculatePrice(Long userId, Long eventId){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));

        PriceStrategy strategy = strategies.get(user.getTier());
        if(strategy == null) throw new IllegalArgumentException("Unknown user tier");

        double price = strategy.calculate(user, event);
        boolean priority = strategy.hasPriorityAccess();

        return new PriceResult(price, priority);
    }

    @Data
    public static class PriceResult{
        private final double price;
        private final boolean priorityAccess;
    }
}
