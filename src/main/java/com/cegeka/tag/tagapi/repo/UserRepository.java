package com.cegeka.tag.tagapi.repo;

import com.cegeka.tag.tagapi.model.User;
import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, String> {
  Optional<User> findByEmail(String email);
  Optional<Boolean> existsByEmail(String email);
}
