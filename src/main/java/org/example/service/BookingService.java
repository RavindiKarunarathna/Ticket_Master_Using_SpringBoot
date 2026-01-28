package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.entity.AuditLog;
import org.example.repository.AuditLogRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final AuditLogRepository auditLogRepository;

    public void confirmBooking(Long userId, Long seatId){
        try{
            throw new RuntimeException("Payment failed - bot suspected");
        }catch (Exception ex){
            AuditLog log = new AuditLog();
            log.setUserID(userId);
            log.setAction("Confirm Booking");
            log.setDetails(ex.getMessage());
            auditLogRepository.save(log);

            throw ex;
        }
    }
}
