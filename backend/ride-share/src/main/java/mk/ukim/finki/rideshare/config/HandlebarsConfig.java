package mk.ukim.finki.rideshare.config;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class HandlebarsConfig {

    private final TemplateLoader loader = new ClassPathTemplateLoader("/templates/", ".hbs");

    @Bean
    public Handlebars handlebars() {
        return new Handlebars(loader);
    }
}
