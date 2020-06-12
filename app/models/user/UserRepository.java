package models.user;

import com.google.inject.ImplementedBy;

import java.util.concurrent.CompletionStage;

/**
 * Interface that exposes all database actions
 * Created by Luis Talero on 12 Jun 2020
 */
@ImplementedBy(JPAUserRepository.class)
public interface UserRepository {

    /**
     * Get an User that matches with the given parameters email and credential.
     *
     * @param email   given email
     * @param credential given credential
     * @return Found user or null.
     */
    CompletionStage<User> authenticate(String email, String credential);
}
