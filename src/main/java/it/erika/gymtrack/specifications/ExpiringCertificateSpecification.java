package it.erika.gymtrack.specifications;

import it.erika.gymtrack.entities.Certificate;
import it.erika.gymtrack.entities.Certificate_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.time.Duration;
import java.time.Instant;

@Data
public class ExpiringCertificateSpecification implements Specification<Certificate> {

    private final Duration duration;

    @Override
    public Predicate toPredicate(Root<Certificate> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return getExpiringSoonCertificate().toPredicate(root, query, criteriaBuilder);
    }

    private Specification<Certificate> getExpiringSoonCertificate() {
        var expire = Instant.now().plus(duration);
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get(Certificate_.expiryDate), expire);
    }
}
