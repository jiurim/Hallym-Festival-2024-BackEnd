package com.hallymfestival.HallymFestival2024BackEnd.reservation.service;

import com.hallymfestival.HallymFestival2024BackEnd.domain.reservation.service.RerservationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RequiredArgsConstructor
@Service
public class LockReservationFacade {
    private ConcurrentHashMap<String , Lock> locks = new ConcurrentHashMap<>();

    private final RerservationServiceImpl rerservationService;

    public void decrease(Long id) throws InterruptedException{
        Lock lock = locks.computeIfAbsent(String.valueOf(id) , key -> new ReentrantLock());
        boolean acquiredLock = lock.tryLock(3, TimeUnit.SECONDS);
        if(!acquiredLock){
            throw new RuntimeException("Lock 획득 실패");
        }
        try{
            rerservationService.decrease(id);
        }finally {
            lock.unlock();
        }
    }
}
