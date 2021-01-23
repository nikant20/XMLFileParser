package tech.nikant.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import tech.nikant.model.JohnData;
import tech.nikant.model.MichelData;

public interface MichelRepo extends JpaRepository<MichelData, String> {

}
