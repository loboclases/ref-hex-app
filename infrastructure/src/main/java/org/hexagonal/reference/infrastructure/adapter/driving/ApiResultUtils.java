package org.hexagonal.reference.infrastructure.adapter.driving;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.*;

import java.net.URI;
import java.util.List;
/**
 * Utility class to create the {@link ResponseEntity} based on an {@link ApiResponse} for successful responses
 * or a {@link ProblemDetail} for failure responses.
 *
 * @author Enrique Medina Montenegro (em54029)
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class ApiResultUtils {
  static ResponseEntity<Object> createSuccessResponse(final HttpStatusCode status,
      final Object response) {
    return ResponseEntity.status(status).body(response);
  }

  static ResponseEntity<List<Object>> createSuccessListResponse(final HttpStatusCode status,
      final List<Object> response) {
    return ResponseEntity.status(status).body(response);
  }

  /**
   * Creates a failure response with a list of {@link ProblemDetail}, setting the originating {@link URI}.
   *
   * @param problemDetails the list of problems
   * @param uri            the originating URI
   * @return the failure response
   */
  static ResponseEntity<List<ProblemDetail>> createFailureResponse(final List<ProblemDetail> problemDetails, final URI uri) {
    HttpStatus status=HttpStatus.I_AM_A_TEAPOT;
    if(problemDetails.size()==1){
      status=HttpStatus.valueOf(problemDetails.get(0).getStatus());
    }
    return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON)
        .body(problemDetails.stream().map(detail -> {
          detail.setType(uri);
          return detail;
        }).toList());
  }
}
