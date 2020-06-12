package models.user;

import com.google.inject.Inject;
import models.DatabaseExecutionContext;
import play.Logger;
import play.db.jpa.JPAApi;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

import static java.util.concurrent.CompletableFuture.supplyAsync;

/**
 * JPA repository that handles all database interactions.
 * Created by Luis Talero on 12 Jun 2020
 */
@Singleton
public class JPAUserRepository implements UserRepository {

    private final JPAApi jpaApi;
    private final DatabaseExecutionContext executionContext;

    @Inject
    public JPAUserRepository(JPAApi jpaApi, DatabaseExecutionContext executionContext) {

        this.jpaApi = jpaApi;
        this.executionContext = executionContext;
    }

    @Override
    public CompletionStage<User> authenticate(String email, String credential) {
        return supplyAsync(() -> wrap(em -> getUserByEmailAndPassword(em, email, credential)), executionContext);
    }

    /**
     * Wraps the query and the Entity Manager.
     *
     * @param function given function.
     * @param <T>      T param
     * @return transactional context.
     */
    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }


    /**
     * Execute the named query User.findByLoginAndCredential, given the username and the credential. If there is no results
     * it tries to return an existing user, if the user doesnt exist.
     *
     * @param em         current entity manager.
     * @param email      given username.
     * @param credential given credentials.
     * @return The user or null.
     */
    private User getUserByEmailAndPassword(EntityManager em, String email, String credential) {
        try {
            return (User) em.createNamedQuery("User.findByLoginAndCredential").setParameter("email", email).
                    setParameter("credential", credential).getSingleResult();
        } catch (NoResultException nre) {
            Logger.warn("No se encontraron resultados para el query --> User.findByEmailAndCredential, auth error: " + nre);
            return null;
        }
    }
}
