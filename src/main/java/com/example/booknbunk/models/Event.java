package com.example.booknbunk.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
        @JsonSubTypes.Type(value = EventRoomDoor.class, name = "RoomDoor"),
        @JsonSubTypes.Type(value = EventRoomCleaning.class, name = "RoomCleaning"),

})

 /*
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "event_type")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = EventRoomCleaning.class, name = "RoomCleaning"),
        @JsonSubTypes.Type(value = EventRoomDoor.class, name = "RoomDoor")
})*/

public abstract class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("TimeStamp")
    private LocalDateTime timeStamp;

    @JsonProperty("RoomNo")
    @JoinColumn(name = "room_id")
    @NonNull
    @ManyToOne
   // @JsonIgnore
    private Room room;

    private String eventDetail;

    public Event(LocalDateTime timeStamp, Room room, String eventDetail) {
        this.timeStamp = timeStamp;
        this.room = room;
        this.eventDetail = eventDetail;
    }


}
