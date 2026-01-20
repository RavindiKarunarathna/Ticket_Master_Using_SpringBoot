package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.entity.Seat;
import org.example.repository.SeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SeatService {
    private final SeatRepository seatRepository;

    @Transactional
    public void holdSeat(Long seatId, Long userId){
        Seat seat = seatRepository.findByIdLocked(seatId);
        if(seat == null) throw new RuntimeException("Seat not found");

        LocalDateTime now = LocalDateTime.now();

        if(seat.getStatus() == Seat.Status.AVAILABLE ||  (seat.getStatus() == Seat.Status.HELD && seat.getHoldExpiry().isBefore(now))){

            seat.setStatus(Seat.Status.HELD);
            seat.setHeldByUserId(userId);
            seat.setHoldExpiry(now.plusMinutes(10));
            seatRepository.save(seat);

        } else if (seat.getStatus() == Seat.Status.HELD) {
            long secondsLeft = Duration.between(now, seat.getHoldExpiry()).getSeconds();
            throw new SeatLockedException("Seat locked for "+secondsLeft+" seconds");
        }else {
            throw new RuntimeException("Seat already sold");
        }
    }
}
