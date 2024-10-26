package onosoft.onetomany.unidirectional.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity(name = "Comment")
@Table(name = "onetomany-unidirectional-comments")
@Data
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    private String review;
}
