package oasip.us1.backend.repository;

import oasip.us1.backend.entity.Eventcategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventcategoryRepository extends JpaRepository<Eventcategory, Integer> {
}