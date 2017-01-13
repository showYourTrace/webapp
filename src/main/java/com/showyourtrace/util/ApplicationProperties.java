package com.showyourtrace.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource(value = { "classpath:application.properties" })
public class ApplicationProperties {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Value("${mailer.username}")
    public String mailerUserName;

    @Value("${mailer.passwd}")
    public String mailerPwd;

    @Value("${mail.smtp.auth}")
    public String mailSmtpAuthEnabled;

    @Value("${mail.smtp.starttls.enable}")
    public String mailSmtpTlsEnabled;

    @Value("${mail.smtp.host}")
    public String mailSmtpHost;

    @Value("${mail.smtp.port}")
    public String mailSmtpPort;

    @Value("${subscription.confirm.url}")
    public String subscriptionConfirmUrl;

    @Value("${subscription.confirm.msg.follow_the_link}")
    public String msgFollowTheLink;

    @Value("${homepage.url}")
    public String homepageUrl;

    @Value("${filestore.path}")
    public String fileStorePath;
}
