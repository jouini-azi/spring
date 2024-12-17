package com.library.mapper;

import com.library.dto.request.ReservationRequest;
import com.library.dto.response.ReservationResponse;
import com.library.entity.Book;
import com.library.entity.Reservation;
import com.library.entity.User;
import com.library.repository.BookRepository;
import com.library.repository.UserRepository;
import com.library.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Autowired
    public ReservationMapper(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public Reservation toEntity(ReservationRequest request) {
        if (request == null) {
            return null;
        }
        
        Reservation reservation = new Reservation();
        
        Book book = bookRepository.findById(request.getBookId())
            .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + request.getBookId()));
        User user = userRepository.findById(request.getUserId())
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getUserId()));
            
        reservation.setBook(book);
        reservation.setUser(user);
        return reservation;
    }

    public ReservationResponse toResponse(Reservation reservation) {
        if (reservation == null) {
            return null;
        }
        
        ReservationResponse response = new ReservationResponse();
        response.setId(reservation.getId());
        response.setBookId(reservation.getBook().getId());
        response.setUserId(reservation.getUser().getId());
        response.setReservationDate(reservation.getReservationDate());
        return response;
    }
}