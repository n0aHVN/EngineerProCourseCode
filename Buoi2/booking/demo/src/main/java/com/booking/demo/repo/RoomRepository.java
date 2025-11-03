package com.booking.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.booking.demo.model.Room;

import jakarta.persistence.LockModeType;


public interface RoomRepository extends JpaRepository<Room, Integer> {    

    Room findByName(String name);

    boolean existsByName(String name);

    // Get a Room by id where user is null
    Room findOneByIdAndUserIsNull(int id);

    // Get a room by id where user is null Pessimistic
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select r from rooms r where r.id = :id and r.user is null")
    Room findOneByIdAndUserIsNullPessimistic(@Param("id") int id);

    @Modifying
    @Query("update rooms set user = :user, version = version + 1 where id = :id and version = :version and user is null")
    int bookRoom(@Param("id") int id, @Param("version") int version, @Param("user") String user);

    @Modifying
    @Query("update rooms set user = :user where id = :id and user is null")
    int bookRoomPessimistic(@Param("id") int id, @Param("user") String user);
}
