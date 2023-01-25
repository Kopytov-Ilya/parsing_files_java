package guru.qa;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.ED807toED101;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonParsing {

    ClassLoader cl = JsonParsing.class.getClassLoader();
    ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("Парсинг json файла с помощью Jackson")
    void parseJson() throws Exception {

        try (InputStream resource = cl.getResourceAsStream("PacketEPD.json"); InputStreamReader reader = new InputStreamReader(resource);) {
            ED807toED101 ed807toED101 = mapper.readValue(reader, ED807toED101.class);
            assertThat(ed807toED101.EDAuthor).isEqualTo(4583001999L);
            assertThat(ed807toED101.EDNo).isEqualTo(707545534L);
            assertThat(ed807toED101.Sum).isEqualTo(31071);
        }
    }
}
