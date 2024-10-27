package onosoft.onetomany.bidirectional.persistence;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import onosoft.onetomany.bidirectional.model.Post;

@ApplicationScoped
public class PostRepository implements PanacheRepository<Post> {
}
