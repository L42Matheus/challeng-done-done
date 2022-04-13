package net.intelie.challenges.repository;

import net.intelie.challenges.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    void deleteAllByType(String type);

    List<Event> findAllByType(String type);
}
