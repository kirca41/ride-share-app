package mk.ukim.finki.rideshare.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

public class SpecificationFactory {

    public static <T> Specification<T> chainAndSpecifications(List<Specification<T>> specifications) {
        return specifications.stream().reduce(Specification::and)
                .orElseGet(() -> Specification.where(null));
    }

    public static <T, U> Specification<T> equalsValue(String path, U value) {
        return (root, query, criteriaBuilder) -> {
            if (value == null) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.equal(root.get(path), value);
        };
    }

    public static <T> Specification<T> dateEquals(String path, LocalDate date, ZoneId zoneId) {
        return (root, query, criteriaBuilder) -> {
            if (date == null) {
                return criteriaBuilder.conjunction();
            }

            ZonedDateTime startOfDay = date.atStartOfDay(zoneId);
            ZonedDateTime endOfDay = date.atTime(23, 59, 59, 999999).atZone(zoneId);

            return criteriaBuilder.between(root.get(path), startOfDay, endOfDay);
        };
    }

    public static <T> Specification<T> dateGreaterThan(String path, LocalDate date, ZoneId zoneId) {
        return (root, query, criteriaBuilder) -> {
            if (date == null) {
                return criteriaBuilder.conjunction();
            }

            ZonedDateTime referenceDate = date.atStartOfDay(zoneId);

            return criteriaBuilder.greaterThan(root.get(path), referenceDate);
        };
    }

    public static <T> Specification<T> like(String path, String value) {
        return (root, query, criteriaBuilder) -> {
            if (value == null) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.like(root.get(path), "%" + value + "%");
        };
    }
}
