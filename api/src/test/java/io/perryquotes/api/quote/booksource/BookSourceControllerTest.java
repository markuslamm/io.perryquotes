//package io.perryquotes.api.quote.booksource; TODO
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.slf4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.UUID;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(SpringExtension.class)
//@WebMvcTest(BookSourceController.class)
//public class BookSourceControllerTest {
//
//  @Autowired
//  private MockMvc mockMvc;
//
//  @MockBean
//  private BookSourceService bookSourceService;
//
//  @MockBean
//  private Logger logger;
//
//  @Test
//  public void testCreate() throws Exception {
//    var payload = "{\"name\": \"Book source name 1\", \"shortcut\": \"BSN1\"}";
//    var result = new BookSource("Book source name 1", "BSN1");
//    result.setUuid(UUID.randomUUID());
//    when(bookSourceService.create(new BookSourceRecord( "Book source name 1", "BSN1")))
//      .thenReturn(result);
//
//    this.mockMvc.perform(post("/books")
//      .content(payload)
//      .contentType("application/json"))
//      .andDo(print())
//      .andExpect(status().isCreated());
//  }
//
//  @Test
//  public void testCreateMissingName() throws Exception {
//    var payload = "{\"shortcut\": \"BSN1\"}";
//
//    this.mockMvc.perform(post("/books")
//      .content(payload)
//      .contentType("application/json"))
//      .andDo(print())
//      .andExpect(status().isBadRequest());
//  }
//
//  @Test
//  public void testCreateNullName() throws Exception {
//    var payload = "{\"name\": null, \"shortcut\": \"BSN1\"}";
//
//    this.mockMvc.perform(post("/books")
//      .content(payload)
//      .contentType("application/json"))
//      .andDo(print())
//      .andExpect(status().isBadRequest());
//  }
//
//  @Test
//  public void testCreateEmptyName() throws Exception {
//    var payload = "{\"name\": \"\", \"shortcut\": \"BSN1\"}";
//
//    this.mockMvc.perform(post("/books")
//      .content(payload)
//      .contentType("application/json"))
//      .andDo(print())
//      .andExpect(status().isBadRequest());
//  }
//
//  @Test
//  public void testCreateMissingShortcut() throws Exception {
//    var payload = "{\"name\": \"Book source name 1\"}";
//
//    this.mockMvc.perform(post("/books")
//      .content(payload)
//      .contentType("application/json"))
//      .andDo(print())
//      .andExpect(status().isBadRequest());
//
//  }
//
//  @Test
//  public void testCreateNullShortcut() throws Exception {
//
//    var payload = "{\"name\": \"Book source name 1\", \"shortcut\": null}";
//
//    this.mockMvc.perform(post("/books")
//      .content(payload)
//      .contentType("application/json"))
//      .andDo(print())
//      .andExpect(status().isBadRequest());
//  }
//
//  @Test
//  public void testCreateEmptyShortcut() throws Exception {
//    var payload = "{\"name\": \"Book source name 1\", \"shortcut\": \"\"}";
//
//    this.mockMvc.perform(post("/books")
//      .content(payload)
//      .contentType("application/json"))
//      .andDo(print())
//      .andExpect(status().isBadRequest());
//
//  }
//
//  @Test
//  public void testCreateShortcutWrongPattern() throws Exception {
//    var payload = "{\"name\": \"Book source name 1\", \"shortcut\": \"BSN252121\"}";
//
//    this.mockMvc.perform(post("/books")
//      .content(payload)
//      .contentType("application/json"))
//      .andDo(print())
//      .andExpect(status().isBadRequest());
//  }
//
//  @Test
//  public void testUpdate() throws Exception {
//    //TODO
//  }
//
//  @Test
//  public void testUpdateMissingName() throws Exception {
//    //TODO
//  }
//
//  @Test
//  public void testUpdateNullName() throws Exception {
//    //TODO
//  }
//
//  @Test
//  public void testUpdateEmptyName() throws Exception {
//    //TODO
//  }
//
//  @Test
//  public void testUpdateMissingShortcut() throws Exception {
//    //TODO
//  }
//
//  @Test
//  public void testUpdateNullShortcut() throws Exception {
//    //TODO
//  }
//
//  @Test
//  public void testUpdateEmptyShortcut() throws Exception {
//    //TODO
//  }
//
//  @Test
//  public void testUpdateShortcutWrongPattern() throws Exception {
//    //TODO
//  }
//}
