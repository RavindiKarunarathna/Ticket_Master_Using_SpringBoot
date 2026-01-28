package org.example.service;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.model.entity.Event;
import org.example.model.entity.User;
import org.example.repository.EventRepository;
import org.example.repository.UserRepository;
//import org.example.service.strategy.PlatinumStrategy;
import org.example.service.strategy.PriceStrategy;
//import org.example.service.strategy.RegularStrategy;
//import org.example.service.strategy.VipStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PriceCalculaterService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;


    private final Map<User.Tier, PriceStrategy> strategies;

    @Autowired
    public PriceCalculaterService(
            UserRepository userRepository,
            EventRepository eventRepository,
            List<PriceStrategy> strategyList
    ) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;

        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(
                        PriceStrategy::supports,
                        Function.identity()
                ));
    }

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
