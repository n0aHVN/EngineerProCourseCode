package com.booking.demo.service;

import org.springframework.stereotype.Service;

import com.booking.demo.model.Room;
import com.booking.demo.repo.RoomRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepo;

    public Room createRoom(String name, String user) {
        boolean isExist = roomRepo.existsByName(name);
        if (isExist) {
            log.error("Room with name {} already exists", name);
            return roomRepo.findByName(name);
        }
        Room room = Room.builder().name(name).user(user).build();
        return roomRepo.save(room);
    }

    public List<Room> getAllRooms() {
        List<Room> rooms = roomRepo.findAll();
        return rooms;
    }

    @Transactional
    public int bookRoomOptimistic(int roomId, String user) {
        log.info("Start booking roomId={} for user={}", roomId, user);
        Room room = roomRepo.findOneByIdAndUserIsNull(roomId);
        if (room == null) {
            log.error("Room with id: {} is already booked or does not exist", roomId);
            return 0;
        }

        log.info("Booking room with id: {} and version: {}", roomId, room.getVersion());

        // Simulate the delaying
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
        }

        int changeRows = roomRepo.bookRoom(roomId, room.getVersion(), user);
        if (changeRows == 0) {
            log.error("Failed to book room with id: {} due to version conflict", roomId);
            throw new ConcurrentModificationException("Failed to book room with id: " + roomId + " due to version conflict");
        }
        log.info("User {} successfully booked room {}", user, roomId);
        return changeRows;
    }

    @Transactional
    public int bookRoomPessimistic(int roomId, String user) {
        log.info("User {} is booking room with id: {} using pessimistic locking", user, roomId);
        // Simulate the delaying
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
        }

        int changeRows = roomRepo.bookRoomPessimistic(roomId, user);
        if (changeRows == 0) {
            log.error("User {}: Room with id: {} is already booked or does not exist", user, roomId);
            return 0;
        }
        log.info("User {} successfully booked room {}", user, roomId);
        return changeRows;
    }
}
