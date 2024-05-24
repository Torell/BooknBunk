package com.example.booknbunk.services.implementations;

import com.example.booknbunk.dtos.*;
import com.example.booknbunk.models.Booking;
import com.example.booknbunk.models.Customer;
import com.example.booknbunk.models.Room;
import com.example.booknbunk.repositories.BookingRepository;
import com.example.booknbunk.repositories.CustomerRepository;
import com.example.booknbunk.repositories.RoomRepository;
import com.example.booknbunk.services.interfaces.BlacklistService;
import com.example.booknbunk.services.interfaces.BookingService;
import com.example.booknbunk.services.interfaces.EmailService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class BookingServiceImplementation implements BookingService {

    public static final int SUCCESS = 1;
    public static final int FAILURE = -1;

    private final BookingRepository bookingRepository;

    private final RoomRepository roomRepository;

    private final CustomerRepository customerRepository;

    private final BlacklistService blacklistService;
    private final DiscountServiceImplementation discountService;
    private final EmailService emailService;



    public BookingServiceImplementation(BookingRepository bookingRepository, RoomRepository roomRepository, CustomerRepository customerRepository, BlacklistService blacklistService, DiscountServiceImplementation discountService, EmailService emailService) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.customerRepository = customerRepository;
        this.blacklistService = blacklistService;
        this.discountService = discountService;
        this.emailService = emailService;
    }

    @Override
    public BookingDetailedDto bookingToBookingDetailedDto(Booking booking) {
        return BookingDetailedDto.builder()
                .id(booking.getId()).extraBed(booking.getExtraBed())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .customerMiniDto(customerToCustomerMiniDto(booking.getCustomer()))
                .roomMiniDto(roomToRoomMiniDto(booking.getRoom()))
                .totalPrice(booking.getTotalPrice())
                .build();

    }

    @Override
    public BookingMiniDto bookingToBookingMiniDto(Booking booking) {
        return BookingMiniDto.builder()
                .id(booking.getId())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .build();
    }


    @Override
    public Booking bookingDetailedDtoToBooking(BookingDetailedDto bookingDetailedDto) {
        return Booking.builder()
                .startDate(bookingDetailedDto.getStartDate())
                .endDate(bookingDetailedDto.getEndDate())
                .customer(customerMiniDtoToCustomer(bookingDetailedDto.getCustomerMiniDto()))
                .room(roomMiniDtoRoom(bookingDetailedDto.getRoomMiniDto()))
                .extraBed(bookingDetailedDto.getExtraBed())
                .id(bookingDetailedDto.getId())
                .totalPrice(bookingDetailedDto.getTotalPrice())
                .build();
    }

    @Override
    public Room roomMiniDtoRoom(RoomMiniDto roomMiniDto) {
        return Room.builder()
                .id(roomMiniDto.getId())
                .roomSize(roomMiniDto.getRoomSize())
                .pricePerNight(roomMiniDto.getPricePerNight())
                .build();
    }



    @Override
    public CustomerMiniDto customerToCustomerMiniDto(Customer customer) {
        return CustomerMiniDto.builder()
                .id(customer.getId())
                .name(customer.getName())
                .build();
    }


    @Override
    public RoomMiniDto roomToRoomMiniDto(Room room) {
        return RoomMiniDto.builder()
                .id(room.getId())
                .roomSize(room.getRoomSize())
                .pricePerNight(room.getPricePerNight())
                .build();
    }

    @Override
    public Customer customerMiniDtoToCustomer(CustomerMiniDto customerMiniDto) {
        return Customer.builder()
                .name(customerMiniDto.getName())
                .id(customerMiniDto.getId())
                .build();
    }

    @Override
    public BookingDetailedDto findBookingById(long id) {
        return bookingToBookingDetailedDto(bookingRepository.getReferenceById(id));
    }

    @Override
    public StringBuilder createOrChangeBooking(BookingDetailedDto bookingDetailedDto,RoomDetailedDto roomDetailedDto) {
        StringBuilder returnMessage = new StringBuilder();
        boolean allConditionsMet = true;
        if (!extraBedSpaceAvailable(bookingDetailedDto)) {
            returnMessage.append("Not enough space for extra beds. ");
            allConditionsMet = false;
        } if (!startDateIsBeforeEndDate(bookingDetailedDto)) {
            returnMessage.append("End date must be after start date. ");
            allConditionsMet = false;
        } if (!checkRoomForAvailability(bookingDetailedDto, roomDetailedDto)) {
            returnMessage.append("The room is not available the chosen dates.");
            allConditionsMet = false;
        } if (!blacklistService.checkBlacklist(customerRepository.getReferenceById(bookingDetailedDto.getCustomerMiniDto().getId()).getEmail())){
            returnMessage.append("Customer is on Blacklist.");
            allConditionsMet = false;
        } if (allConditionsMet) {
            returnMessage.append("Booking successfully saved");

            bookingDetailedDto.setRoomMiniDto(roomToRoomMiniDto(roomRepository.getReferenceById(bookingDetailedDto.getRoomMiniDto().getId())));
            bookingDetailedDto.setCustomerMiniDto(customerToCustomerMiniDto(customerRepository.getReferenceById(bookingDetailedDto.getCustomerMiniDto().getId())));
            bookingDetailedDto.setTotalPrice(calculateTotalPrice(bookingDetailedDto));
            bookingRepository.save(bookingDetailedDtoToBooking(bookingDetailedDto));
            sendConfirmationEmail(bookingDetailedDto);
        }

        return returnMessage;
    }

    private void sendConfirmationEmail(BookingDetailedDto bookingDetailedDto) {
        Map<String, Object> variables = Map.of(
                "customerName", bookingDetailedDto.getCustomerMiniDto().getName(),
                "roomSize", bookingDetailedDto.getRoomMiniDto().getRoomSize(),
                "checkInDate", bookingDetailedDto.getStartDate().toString(),
                "checkOutDate", bookingDetailedDto.getEndDate().toString()
        );

        emailService.sendEmailWithTemplate(customerRepository.getReferenceById(bookingDetailedDto.getCustomerMiniDto().getId()).getEmail(), "Booking confirmation", "emailTemplate", variables);
    }




    @Override
    public void modifyBooking(BookingDetailedDto bookingDetailedDto) {
            bookingRepository.save(bookingDetailedDtoToBooking(bookingDetailedDto));
    }
    @Override
    public List<RoomDetailedDto> getAllAvailabileRoomsBasedOnRoomSizeAndDateIntervall(int occupants, BookingDetailedDto bookingDetailedDto) {

        List<RoomDetailedDto> allRoomsWithEnoughSpace = getAllRooms().stream()
                .filter(roomDetailedDto -> roomDetailedDto.getRoomSize() >= occupants-1)
                .toList();

        List<RoomDetailedDto> availableRooms = new ArrayList<>();
        BookingDetailedDto mockBooking = new BookingDetailedDto();

        mockBooking.setStartDate(bookingDetailedDto.getStartDate());
        mockBooking.setEndDate(bookingDetailedDto.getEndDate());

        allRoomsWithEnoughSpace.forEach(room -> {
            if (checkRoomForAvailability(mockBooking, room)) {
                availableRooms.add(room);
            }
        });

        return availableRooms;
    }



    @Override
    public boolean extraBedSpaceAvailable(BookingDetailedDto bookingDetailedDto) {

        int numberOfBeds = bookingDetailedDto.getExtraBed();
        int availableSpace = bookingDetailedDto.getRoomMiniDto().getRoomSize();
        return availableSpace >= numberOfBeds;
    }

    @Override
    public void cancelBooking(long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public List<BookingDetailedDto> getAllBookingDetailedDto() {
        return bookingRepository.findAll()
                .stream()
                .map(booking -> bookingToBookingDetailedDto(booking))
                .toList();
    }

    @Override
    public List<RoomDetailedDto> getAllRooms() {
        return roomRepository.findAll()
                .stream()
                .map(room -> roomToRoomDetailedDto(room))
                .toList();
    }

    @Override
    public RoomDetailedDto roomToRoomDetailedDto(Room room){
        return RoomDetailedDto.builder()
                .id(room.getId())
                .roomSize(room.getRoomSize())
                .bookingMiniDtoList(room.getBookings()
                        .stream().map(booking -> bookingToBookingMiniDto(booking))
                        .toList()).build();
    }

    @Override
    public List<LocalDate> getAllDatesBetweenStartAndEndDate(LocalDate startDate, LocalDate endDate) {

        List<LocalDate> allBookedDates = new ArrayList<>();

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            allBookedDates.add(date);
        }
        return allBookedDates;
    }

    @Override
    public boolean checkRoomForAvailability(BookingDetailedDto booking, RoomDetailedDto room) {
        LocalDate currentDate = LocalDate.now();
        if (booking.getStartDate().isBefore(currentDate)) {
            return false;
        }
        List<LocalDate> desiredDates = getAllDatesBetweenStartAndEndDate(booking.getStartDate(), booking.getEndDate());
        List<LocalDate> bookedDates = room.getBookingMiniDtoList()
                .stream()
                .filter(bookingMiniDto -> !bookingMiniDto.getId().equals(booking.getId()))
                .flatMap(bookingMiniDto -> getAllDatesBetweenStartAndEndDate(bookingMiniDto.getStartDate(), bookingMiniDto.getEndDate()).stream())
                .toList();

        boolean hasConflict = desiredDates.stream()
                .anyMatch(bookedDates::contains);

        return !hasConflict;

    }

    @Override
    public boolean startDateIsBeforeEndDate(BookingDetailedDto booking) {

        return booking.getStartDate().isBefore(booking.getEndDate());
    }
    @Override
    public double calculateTotalPrice(BookingDetailedDto bookingDetailedDto) {
        double pricePerNight = bookingDetailedDto.getRoomMiniDto().getPricePerNight();
        double totalNightsBooked = ChronoUnit.DAYS.between(bookingDetailedDto.getStartDate(),bookingDetailedDto.getEndDate());

        return ((pricePerNight * totalNightsBooked) - discountService.discountSundayToMonday(bookingDetailedDto)) * discountService.discount(bookingDetailedDto);
    }


}
