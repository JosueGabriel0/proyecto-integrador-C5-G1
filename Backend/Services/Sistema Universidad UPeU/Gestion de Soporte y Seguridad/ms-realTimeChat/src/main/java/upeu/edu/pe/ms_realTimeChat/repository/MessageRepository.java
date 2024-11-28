package upeu.edu.pe.ms_realTimeChat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upeu.edu.pe.ms_realTimeChat.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}