package com.booking.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;

import com.booking.demo.model.Room;
import com.booking.demo.service.RoomService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @GetMapping("/api/room/all")
    @ResponseBody
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }
}
