package zoo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import zoo.model.Animal;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long>,
        JpaSpecificationExecutor<Animal> {
}
