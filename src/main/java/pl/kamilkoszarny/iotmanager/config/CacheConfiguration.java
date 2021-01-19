package pl.kamilkoszarny.iotmanager.config;

import com.github.benmanes.caffeine.jcache.configuration.CaffeineConfiguration;
import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.config.cache.PrefixedKeyGenerator;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.OptionalLong;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfiguration {
    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Caffeine caffeine = jHipsterProperties.getCache().getCaffeine();

        CaffeineConfiguration<Object, Object> caffeineConfiguration = new CaffeineConfiguration<>();
        caffeineConfiguration.setMaximumSize(OptionalLong.of(caffeine.getMaxEntries()));
        caffeineConfiguration.setExpireAfterWrite(OptionalLong.of(TimeUnit.SECONDS.toNanos(caffeine.getTimeToLiveSeconds())));
        caffeineConfiguration.setStatisticsEnabled(true);
        jcacheConfiguration = caffeineConfiguration;
    }

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, pl.kamilkoszarny.iotmanager.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, pl.kamilkoszarny.iotmanager.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, pl.kamilkoszarny.iotmanager.domain.User.class.getName());
            createCache(cm, pl.kamilkoszarny.iotmanager.domain.Authority.class.getName());
            createCache(cm, pl.kamilkoszarny.iotmanager.domain.User.class.getName() + ".authorities");
            createCache(cm, pl.kamilkoszarny.iotmanager.domain.Device.class.getName());
            createCache(cm, pl.kamilkoszarny.iotmanager.domain.DeviceType.class.getName());
            createCache(cm, pl.kamilkoszarny.iotmanager.domain.DeviceType.class.getName() + ".deviceModels");
            createCache(cm, pl.kamilkoszarny.iotmanager.domain.DeviceModel.class.getName());
            createCache(cm, pl.kamilkoszarny.iotmanager.domain.DeviceModel.class.getName() + ".devices");
            createCache(cm, pl.kamilkoszarny.iotmanager.domain.DeviceProducer.class.getName());
            createCache(cm, pl.kamilkoszarny.iotmanager.domain.DeviceProducer.class.getName() + ".deviceModels");
            createCache(cm, pl.kamilkoszarny.iotmanager.domain.Site.class.getName());
            createCache(cm, pl.kamilkoszarny.iotmanager.domain.Site.class.getName() + ".devices");
            // jhipster-needle-caffeine-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
