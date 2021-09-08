package com.github.zjjfly;

import com.github.zjjfly.readinglist.model.Book;
import com.github.zjjfly.readinglist.model.Reader;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Created by zjjfly on 2017/7/15.
 */
public class HomePageTest extends BaseMockWebTest {
    @Test
    @WithMockUser(username = "jjzi",password = "123456",roles = "READER")
    public void homePage() throws Exception {
        mvc.perform(get("/readinglist/sss"))
           .andExpect(status().isOk())
           .andExpect(view().name("readingList"))
           .andExpect(model().attributeExists("books", "reader", "amazonID"))
           .andExpect(model().attribute("books", is(empty())));
    }

    @Test
    @WithMockUser(username = "jjzi",password = "123456",roles = "READER")//@WithMockUser会构造一个假的用户，用于简单的测试，如果要使用现有的UserDetailsService，使用@WithUserDetails
    public void postBook() throws Exception {
        mvc.perform(post("/readinglist/jjzi")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("title", "Book Title")
                                .param("author", "Book Author")
                                .param("isbn", "1234567890")
                                .param("description", "DESCRIPTION"))
           .andExpect(status().is3xxRedirection())
           .andExpect(header().string("Location", "/readinglist/jjzi"));

        Book expectedBook = new Book();
        expectedBook.setAuthor("Book Author");
        expectedBook.setId(14L);
        expectedBook.setReader("jjzi");
        expectedBook.setTitle("Book Title");
        expectedBook.setDescription("DESCRIPTION");
        expectedBook.setIsbn("1234567890");

        mvc.perform(get("/readinglist/jjzi"))
           .andExpect(status().isOk())
           .andExpect(view().name("readingList"))
           .andExpect(model().attributeExists("books", "reader", "amazonID"))
           .andExpect(model().attribute("amazonID", is("zjjblue")))
           .andExpect(model().attribute("reader", is("jjzi")))
           .andExpect(model().attribute("books", hasSize(1)))
           .andExpect(model().attribute("books",contains(samePropertyValuesAs(expectedBook))));

    }

    @Test
    @WithUserDetails("jjzi")
    public void login() throws Exception {
        Reader expectedReader = new Reader();
        expectedReader.setFullname("Zi JunJie");
        expectedReader.setPassword("123456");
        expectedReader.setUsername("jjzi");
        mvc.perform(get("/readinglist/jjzi"))
           .andExpect(status().isOk())
           .andExpect(view().name("readingList"))
           .andExpect(model().attribute("reader",samePropertyValuesAs(expectedReader)));
    }

}
