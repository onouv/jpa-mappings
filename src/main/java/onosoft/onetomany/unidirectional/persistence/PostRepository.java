package onosoft.onetomany.unidirectional.persistence;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import onosoft.onetomany.unidirectional.model.Post;

@ApplicationScoped
public class PostRepository implements PanacheRepository<Post> {
}
