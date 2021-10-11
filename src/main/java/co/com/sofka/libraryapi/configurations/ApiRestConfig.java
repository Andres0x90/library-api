package co.com.sofka.libraryapi.configurations;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

@SpringBootConfiguration
public class ApiRestConfig {
    @Bean
    public ModelMapper getModelMapper()
    {
        return new ModelMapper();
    }

    @Bean
    public DateTimeFormatter getDateTimeFormatter()
    {
        return DateTimeFormatter.ofPattern("dd-MMM-yyyy'T'HH:mm:ss.SSSX", Locale.ENGLISH);
    }
}
