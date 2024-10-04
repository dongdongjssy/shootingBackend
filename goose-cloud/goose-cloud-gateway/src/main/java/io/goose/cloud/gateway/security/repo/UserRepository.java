package io.goose.cloud.gateway.security.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import io.goose.cloud.gateway.security.domain.auth.User;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends CrudRepository<User, Long> {

	User findByUserName(String userName);

	User findByPhone(String mobile);

	User save(User user);
}
