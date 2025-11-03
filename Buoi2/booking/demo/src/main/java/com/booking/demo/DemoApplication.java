package com.booking.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.booking.demo.model.Room;
import com.booking.demo.service.RoomService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class DemoApplication {
	private static int TRANSACTION_NUMBER = 5;

	private static class RoomRunnable implements Runnable {
		private final int roomId;
		private final String user;
		private RoomService roomService;

		public RoomRunnable(int roomId, String user, RoomService roomService) {
			this.roomId = roomId;
			this.user = user;
			this.roomService = roomService;
		}

		@Override
		public void run() {
			// roomService.bookRoomOptimistic(roomId, user);
			roomService.bookRoomPessimistic(roomId, user);
		}
	}

	public static void main(String[] args) throws InterruptedException {
		ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
		
		RoomService roomService = context.getBean(RoomService.class);

		Room createdRoom = roomService.createRoom("Room1", null);

		// Run multiple transactions to book the same room
		List<Thread> allThreads = new ArrayList<>();
		for (int i = 1; i <= TRANSACTION_NUMBER; i++) {
			Thread t = new Thread(new RoomRunnable(createdRoom.getId(), "User" + i, roomService));
			log.info("start thread userId={}", i);
			t.start();
			allThreads.add(t);
		}
		for (Thread t : allThreads) {
			t.join();
		}
	}
}
