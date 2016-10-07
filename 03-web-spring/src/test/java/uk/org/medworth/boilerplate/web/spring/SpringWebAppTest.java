package uk.org.medworth.boilerplate.web.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringWebAppTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testStaticResponse() {
        ResponseEntity<String> response = restTemplate.getForEntity("/", String.class);
        assertSuccessfulResponse(response, "Listen to a speech");
    }

    @Test
    public void testTemplateResponseDefault() {
        ResponseEntity<String> response = restTemplate.getForEntity("/speech", String.class);
        assertSuccessfulResponse(response, "Hong Kong Code Conf");
    }

    @Test
    public void testTemplateResponseWithParam() {
        String occasion = "JavaOne";
        ResponseEntity<String> response = restTemplate.getForEntity("/speech?occasion=" + occasion, String.class);
        assertSuccessfulResponse(response, occasion);
    }

    private void assertSuccessfulResponse(ResponseEntity<String> response, String content) {
        assertEquals(200, response.getStatusCodeValue());
        assertThat(response.getHeaders().getFirst("content-type"), containsString("text/html"));
        assertThat(response.getBody(), containsString(content));
    }
}
