package onosoft.onetomany.unidirectional.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


import java.util.ArrayList;
import java.util.List;

@Entity(name="Post")
@Table(name="onetomany-unidirectional-posts")
@Data
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "post_id")
    private List<Comment> comments = new ArrayList<>();

    public Post(@NonNull String title) {
        this.title = title;
    }
}
