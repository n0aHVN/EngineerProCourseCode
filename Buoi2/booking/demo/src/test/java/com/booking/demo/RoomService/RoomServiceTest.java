package com.booking.demo.RoomService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.booking.demo.model.Room;
import com.booking.demo.repo.RoomRepository;
import com.booking.demo.service.RoomService;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {
    @Mock
    private RoomRepository roomRepo;

    RoomService roomService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        roomService = new RoomService(roomRepo);
    }

    @Test
    void testOptismisticBooking_whenUpdatedRoomRow(){
        String user = "testUser";
        int roomId = 1;
        int version = 0;

        Room mockAvailableRoom = Room.builder()
                .id(1)
                .name("room1")
                .user(null)
                .version(version)
                .build();

        when(roomRepo.findOneByIdAndUserIsNull(1)).thenReturn(mockAvailableRoom);
        
    }
}
