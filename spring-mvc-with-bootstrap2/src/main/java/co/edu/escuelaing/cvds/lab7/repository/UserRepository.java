package co.edu.escuelaing.cvds.lab7.repository;

import co.edu.escuelaing.cvds.lab7.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);
}
