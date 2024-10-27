package onosoft.onetomany.unidirectional.rest;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import onosoft.onetomany.unidirectional.model.Post;
import onosoft.onetomany.unidirectional.persistence.PostRepository;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;
import java.util.stream.Collectors;

@Path("comment/uni")
public class CommentEndpoint {

    @Inject
    PostRepository postRepository;

    @POST
    @Path("/{postId}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public RestResponse<PostDTO> addComment(Long postId, String comment) {
        Post post = postRepository.findById(postId);
        post.addComment(comment);
        postRepository.persist(post);
        PostDTO payload = PostDTO.of(post);

        return RestResponse.ok(payload);
    }

    @GET
    @Path("/{postId}")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<List<String>> getComments(Long postId) {
        Post post = postRepository.findById(postId);

        List<String> comments = post
                .getComments()
                .stream()
                .map(comment -> comment.getReview())
                .collect(Collectors.toList());

        return RestResponse.ok(comments);
    }
}
