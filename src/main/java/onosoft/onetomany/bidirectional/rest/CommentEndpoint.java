package onosoft.onetomany.bidirectional.rest;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import onosoft.onetomany.bidirectional.model.Comment;
import onosoft.onetomany.bidirectional.model.Post;
import onosoft.onetomany.bidirectional.persistence.PostRepository;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;
import java.util.stream.Collectors;

@Path("comment/bidirect")
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

    /**
     * Retrieve all sibling comments of a given comment. This is a
     * contrived example to exercise the child -> parent relationship.
     *
     * @param postId id of the parent post
     * @param commentId id of comment under parent post
     * @return
     */
    @GET
    @Path("/siblings/{postId}/{commentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<List<String>> getSiblings(Long postId, Long commentId) {
        Post post = postRepository.findById(postId);
        Comment comment = post.getComments()
                .stream()
                .filter(cmt -> cmt.getId() == commentId)
                .findAny()
                .orElse(null);

        if(comment == null) {
            return RestResponse.status(Response.Status.NOT_FOUND);
        }

        List<String> payload = comment.getSiblings()
                .stream()
                .map(cm -> cm.getReview())
                .collect(Collectors.toList());

        return RestResponse.ok(payload);
    }
}
