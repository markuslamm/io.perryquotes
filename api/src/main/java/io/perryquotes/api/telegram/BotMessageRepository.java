package io.perryquotes.api.telegram;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BotMessageRepository extends JpaRepository<BotMessage, UUID> {

  Optional<BotMessage> findByUuid(UUID uuid);

  @Query("select bm from BotMessage bm where bm.processingStatus = :status")
  List<BotMessage> findByProcessingStatus(ProcessingStatus status);
}
