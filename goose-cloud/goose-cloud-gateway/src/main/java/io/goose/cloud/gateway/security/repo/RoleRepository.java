package io.goose.cloud.gateway.security.repo;

import org.springframework.data.repository.CrudRepository;

import io.goose.cloud.gateway.security.domain.auth.Role;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Integer> {
	public Optional<Role> findById(Integer id);
	
	public void deleteByName(String name);

}
