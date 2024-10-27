package onosoft.onetomany.bidirectional.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Entity(name="Postbidirect")
@Table(name="onetomany_bidirect_posts")
@Data
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Comment> comments = new ArrayList<>();

    public Post(@NonNull String title) {
        this.title = title;
    }

    public Comment addComment(@NonNull String review) {
        Comment comment = new Comment(review);
        this.comments.add(comment);
        comment.setPost(this);

        return comment;
    }
}
