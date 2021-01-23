package tech.nikant.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import tech.nikant.model.JohnData;

public interface JohnRepo extends JpaRepository<JohnData, String> {

}
