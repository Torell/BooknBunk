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
        @JsonSubTypes.Type(value = EventRoomDoor.class, name = "RoomOpened"),
        @JsonSubTypes.Type(value = EventRoomDoor.class, name = "RoomClosed"),
        @JsonSubTypes.Type(value = EventRoomCleaning.class, name = "RoomCleaningStarted"),
        @JsonSubTypes.Type(value = EventRoomCleaning.class, name = "RoomCleaningFinished"),
})
public abstract class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("TimeStamp")
    private LocalDateTime timeStamp;

    @Transient // Används bara temporärt för deserialisering
    @JsonProperty("RoomNo")
    private String roomNo;

    @JsonProperty("RoomNo")
    @JoinColumn(name = "room_id")
    @NonNull
    @ManyToOne
   // @JsonIgnore
    private Room room;

    private String eventType;

    public Event(LocalDateTime timeStamp, Room room, String eventDetail) {
        this.timeStamp = timeStamp;
        this.room = room;
        this.eventType = eventDetail;
    }


}
