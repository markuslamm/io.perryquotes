package io.perryquotes.api.telegram;

import io.perryquotes.api.base.BaseEntityService;
import io.perryquotes.api.error.EntityNotFoundException;
import io.perryquotes.api.events.BotMessageCreatedEvent;
import org.slf4j.Logger;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class BotMessageService extends BaseEntityService<BotMessage> {

  private final BotMessageRepository repository;

  //TODO get rid of this tech details, check DDD
  private final ApplicationEventPublisher eventPublisher;


  BotMessageService(final Logger log,
                    final BotMessageRepository repository,
                    final ApplicationEventPublisher eventPublisher) {
    super(log);
    this.repository = repository;
    this.eventPublisher = eventPublisher;
  }

  @Transactional
  BotMessage storeMessage(final IncomingBotMessage update) {
    var message = update.message();
    var created = repository.save(
      new BotMessage(
        update.updateId(),
        message.messageId(),
        message.messageDate(),
        message.text()));
    log.debug("BotMessage created: {}, publish BotMessageCreatedEvent", created);
    eventPublisher.publishEvent(new BotMessageCreatedEvent(this, created));
    return created;
  }

  @Transactional
  public BotMessage setProcessingStatus(final UUID uuid, final ProcessingStatus status) {
    return repository.findByUuid(uuid).map(msg -> {
      msg.setProcessingStatus(status);
      return repository.save(msg);
    }).orElseThrow(() -> new EntityNotFoundException(BotMessage.class, "uuid", uuid.toString()));
  }

  @Override
  protected BotMessageRepository getRepository() {
    return repository;
  }
}
