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
        @JsonSubTypes.Type(value = EventRoomOpened.class, name = "RoomOpened"),
        @JsonSubTypes.Type(value = EventRoomClosed.class, name = "RoomClosed"),
        @JsonSubTypes.Type(value = EventRoomCleaningFinished.class, name = "RoomCleaningFinished"),
        @JsonSubTypes.Type(value = EventRoomCleaningStarted.class, name = "RoomCleaningStarted")
})
public abstract class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("TimeStamp")
    private LocalDateTime timeStamp;

    @JoinColumn(name = "room_id", nullable = false)
    @NonNull
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonProperty("RoomNo")
    private Room room;

    public Event(LocalDateTime timeStamp, Room room) {
        this.timeStamp = timeStamp;
        this.room = room;
    }


}
