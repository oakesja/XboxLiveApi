package login;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class LoginHeaderTest {

    private LoginHeader header;

    @Before
    public void setup() {
        header = new LoginHeader();
    }
    @Test
    public void testKeys() {
        assertThat(header.keys()).containsExactly("Content-Type");
    }

    @Test
    public void testValues() {
        assertThat(header.values()).containsExactly("application/json");
    }
}