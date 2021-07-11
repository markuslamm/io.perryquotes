package io.perryquotes.api.telegram;

import io.perryquotes.api.base.BaseEntityService;
import io.perryquotes.api.error.EntityNotFoundException;
import io.perryquotes.api.events.BotMessageCreatedEvent;
import org.slf4j.Logger;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.UUID;

@Service
@Validated
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
  BotMessage storeMessage(@Valid final IncomingBotMessage update) {
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
  public BotMessage setFailureState(final UUID messageUuid) {
    return repository.findByUuid(messageUuid).map(msg -> repository.save(
      msg.setProcessingStatus(ProcessingStatus.FAILURE)))
      .orElseThrow(() -> new EntityNotFoundException(BotMessage.class, "uuid", messageUuid.toString()));
  }


  @Transactional
  public BotMessage setSuccessState(final UUID messageUuid, final UUID quoteUuid) {
    return repository.findByUuid(messageUuid).map(msg -> repository.save(
      msg
        .setProcessingStatus(ProcessingStatus.SUCCESS)
        .setQuoteUuid(quoteUuid)))
      .orElseThrow(() -> new EntityNotFoundException(BotMessage.class, "uuid", messageUuid.toString()));
  }

  @Override
  protected BotMessageRepository getRepository() {
    return repository;
  }
}
