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

@Path("post/uni")
public class PostEndpoint {

    @Inject
    PostRepository postRepository;

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public RestResponse<PostDTO> createPost(String title) {
        Post post = new Post(title);
        postRepository.persist(post);
        PostDTO payload = PostDTO.of(post);

        return RestResponse.ok(payload);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<List<PostDTO>> getPosts() {
        List<Post> posts = postRepository
                .findAll()
                .stream()
                .toList();

        List<PostDTO> payload = posts
                .stream()
                .map(post -> PostDTO.of(post))
                .collect(Collectors.toList());

        return RestResponse.ok(payload);
    }
}
