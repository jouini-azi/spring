package com.library.dto.request;

import com.library.validation.ValidationMessages;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequest {
    @NotNull(message = ValidationMessages.BOOK_ID_REQUIRED)
    private Long bookId;
    
    @NotNull(message = ValidationMessages.USER_ID_REQUIRED)
    private Long userId;
}