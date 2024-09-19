package mk.ukim.finki.rideshare.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import mk.ukim.finki.rideshare.model.base.BaseEntity;
import mk.ukim.finki.rideshare.model.enums.NotificationType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "notification")
public class Notification extends BaseEntity {

    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    private NotificationType notificationType;

    @Column(name = "subject")
    private String subject;

    @Column(name = "recipient")
    private String recipient;

    @Column(name = "template_name")
    private String templateName;

    @Column(name = "context")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> context;

    @Column(name = "is_processed")
    private Boolean isProcessed;
}
