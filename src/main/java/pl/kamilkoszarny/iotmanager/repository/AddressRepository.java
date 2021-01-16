package pl.kamilkoszarny.iotmanager.repository;

import pl.kamilkoszarny.iotmanager.domain.Address;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Address entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
