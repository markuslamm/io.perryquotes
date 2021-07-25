package io.perryquotes.api.processor;

import io.perryquotes.api.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional //necessary for automatic rollback after each test
public class MessageProcessorIT extends AbstractIntegrationTest {

  @Autowired
  private MessageProcessor processor;

  @Test
  public void testProcessMessage() {
    //TODO
  }

  //TODO validation tests
}
