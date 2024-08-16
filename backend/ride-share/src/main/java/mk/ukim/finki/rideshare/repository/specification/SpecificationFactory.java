package mk.ukim.finki.rideshare.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;

public class SpecificationFactory {

    public static <T> Specification<T> chainAndSpecifications(Specification<T> ...specifications) {
        return Arrays.stream(specifications).reduce(Specification::and)
                .orElseGet(() -> Specification.where(null));
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
