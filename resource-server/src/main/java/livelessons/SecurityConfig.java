package livelessons;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Value("${spring.security.oauth2.resource-server.jwk-set-uri}")
	String jwkSetUril;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.oauth2()
				.resourceServer()
					.jwt().jwkSetUri(this.jwkSetUril)
					.and()
			.authorizeRequests()
				.anyRequest().access("principal?.claims['email'] == 'user@example.com'");
	}
}
