package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.exception.SeatLockedException;
import org.example.service.PriceCalculaterService;
import org.example.service.SeatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class TicketController {
    private final SeatService seatService;
    private final PriceCalculaterService priceService;

    @PostMapping("/seats/{seatId}/hold")
    public ResponseEntity<String> hold(@PathVariable Long seatId, @RequestParam Long userId){
        try{
            seatService.holdSeat(seatId, userId);
            return ResponseEntity.ok("Seat held for 10 minutes");
        }catch (SeatLockedException e){
            return ResponseEntity.status(409).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/price")
    public ResponseEntity<PriceCalculaterService.PriceResult> getPrice(
            @RequestParam Long userId,
            @RequestParam Long eventId){
        try{
            return ResponseEntity.ok(priceService.calculatePrice(userId, eventId));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
