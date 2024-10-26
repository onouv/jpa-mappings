package onosoft.onetomany.unidirectional.rest;

import lombok.Data;
import lombok.NonNull;
import onosoft.onetomany.unidirectional.model.Post;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class PostDTO {
    @NonNull
    private String title;

    @NonNull
    private List<String> comments;

    public static PostDTO of(Post post) {

        List<String> comments = post
                .getComments()
                .stream()
                .map(comment -> {
                    return comment.getReview();
                })
                .collect(Collectors.toList());

        return new PostDTO(
                post.getTitle(),
                comments
        );
    }
}