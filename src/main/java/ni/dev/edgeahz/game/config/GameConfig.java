package ni.dev.edgeahz.game.config;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.reactive.config.EnableWebFlux;

import java.util.Locale;

@Configuration
@EnableWebFlux
@EnableR2dbcAuditing
@EnableR2dbcRepositories
public class GameConfig {

    @Bean
    public MessageSource messageSource(){
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setDefaultLocale(Locale.getDefault());
        return messageSource;
    }

    @Bean
    @Primary
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

    @Bean
    public GroupedOpenApi prisonBreakGameOpenApi(@Value("${springdoc.version}") String appVersion) {
        String[] paths = { "/**" };
        return GroupedOpenApi.builder().group("develop")
                .addOpenApiCustomizer(openApi ->
                        openApi.info(new Info()
                                .title("Prison Break Game - Challenge")
                                .version(appVersion)
                                .description("Prison Break Game is a Spring Boot Webflux project, which tests the " +
                                        "knowledge of search algorithms to others to implement new technologies, for " +
                                        "this reason, said this API documentation is added for easy use and efficient " +
                                        "testing.")))
                .pathsToMatch(paths)
                .build();
    }

}
