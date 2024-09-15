package mk.ukim.finki.rideshare.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import mk.ukim.finki.rideshare.model.base.BaseEntity;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "authority")
public class Authority extends BaseEntity implements GrantedAuthority {

    public String authority;
}
