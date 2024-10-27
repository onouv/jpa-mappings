package onosoft.onetomany.bidirectional.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;
import java.util.stream.Collectors;

@Entity(name = "CommentBidirect")
@Table(name = "onetomany_bidirect_comments")
@Data
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    private String review;

    @ManyToOne(fetch = FetchType.LAZY)
    @NonNull
    private Post parentPost;

    public Comment(String review) {
        this.review = review;
    }

    public List<Comment> getSiblings() {
        return this.parentPost
                .getComments()
                .stream()
                .filter(comment -> comment.id != this.id)
                .collect(Collectors.toList());
    }
}
