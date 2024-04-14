package api.dto.in;

import java.util.Optional;

import domain.Candidate;

public record CreateCandidate(
        Optional<String> photo,
        String givenName,
        String familyName,
        String email,
        Optional<String> phone,
        Optional<String> jobtitle) {
    
        public Candidate toDomain() {
            return Candidate.create(
                    photo().orElse(null),
                    givenName(),
                    familyName(),
                    email(),
                    phone().orElse(null),
                    jobtitle().orElse(null));
        }
}
