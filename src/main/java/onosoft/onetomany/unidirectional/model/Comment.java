package onosoft.onetomany.unidirectional.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Comment")
@Table(name = "onetomany-unidirectional-comments")
@Data
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    private String review;

    public Comment(String review) {
        this.review = review;
    }
}
