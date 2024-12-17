package com.library.service;

import com.library.entity.Reservation;
import com.library.entity.Book;
import com.library.repository.ReservationRepository;
import com.library.repository.BookRepository;
import com.library.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final BookRepository bookRepository;
    
    @Autowired
    public ReservationService(ReservationRepository reservationRepository, BookRepository bookRepository) {
        this.reservationRepository = reservationRepository;
        this.bookRepository = bookRepository;
    }
    
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }
    
    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }
    
    public Reservation createReservation(Reservation reservation) {
        Book book = reservation.getBook();
        if (book == null) {
            throw new IllegalStateException("Book cannot be null");
        }
        
        if (!book.isAvailable()) {
            throw new IllegalStateException("Book is not available for reservation");
        }
        
        book.setAvailable(false);
        bookRepository.save(book);
        reservation.setReservationDate(LocalDate.now());
        return reservationRepository.save(reservation);
    }
    
    public void deleteReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: " + id));
            
        Book book = reservation.getBook();
        if (book != null) {
            book.setAvailable(true);
            bookRepository.save(book);
        }
        
        reservationRepository.deleteById(id);
    }
}