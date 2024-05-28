package com.example.booknbunk.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "event_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = EventRoomCleaningStarted.class, name = "RoomCleaningStarted"),
        @JsonSubTypes.Type(value = EventRoomCleaningFinished.class, name = "RoomCleaningFinished"),
        @JsonSubTypes.Type(value = EventRoomOpened.class, name = "RoomOpened"),
        @JsonSubTypes.Type(value = EventRoomClosed.class, name = "RoomClosed")
})
public abstract class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("TimeStamp")
    private LocalDateTime timeStamp;


    @JoinColumn(name = "room_id")
    @NonNull
    @ManyToOne
    private Room room;

    @JsonProperty("RoomNo")
    public Long getRoomNo() {
        return room != null ? room.getId() : null;
    }

    @JsonProperty("RoomNo")
    public void setRoomNo(Long roomNo) {
        if (this.room == null) {
            this.room = new Room();
        }
        this.room.setId(roomNo);
    }

    public Event(LocalDateTime timeStamp, Room room, String eventDetail) {
        this.timeStamp = timeStamp;
        this.room = room;

    }

    public Event(Room room, LocalDateTime timeStamp) {
    }
}
