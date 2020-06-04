package io.aetherit.ats.ws.application;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

import io.aetherit.ats.ws.application.support.DatabaseProperties;
import io.aetherit.ats.ws.repository.mapper.AdvertisingMapper;
import io.aetherit.ats.ws.repository.mapper.ChannelMapper;
import io.aetherit.ats.ws.repository.mapper.LiveRoomMapper;
import io.aetherit.ats.ws.repository.mapper.MovieMapper;
import io.aetherit.ats.ws.repository.mapper.RankingMapper;
import io.aetherit.ats.ws.repository.mapper.UserMapper;
import io.aetherit.ats.ws.repository.mapper.VerifyMapper;
import io.aetherit.ats.ws.util.SystemEnvUtil;

@Configuration
@EnableTransactionManagement
public class DatabaseConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfiguration.class);
    private static final String DB_URL_ENV_KEY = "DB_URL";
    private static final String DB_USER_ENV_KEY = "DB_USERNAME";
    private static final String DB_PASS_ENV_KEY = "DB_PASSWORD";

    private SystemEnvUtil systemEnvUtil;
    private DatabaseProperties databaseProperties;

    @Autowired
    public DatabaseConfiguration(SystemEnvUtil systemEnvUtil, DatabaseProperties databaseProperties) {
        this.systemEnvUtil = systemEnvUtil;
        this.databaseProperties = databaseProperties;
    }

    @Bean(name="psDataSource")
    @Primary
    public DataSource dataSource() {
        String dbURL = systemEnvUtil.getValue(DB_URL_ENV_KEY, databaseProperties.getUrl());
        String dbUser = systemEnvUtil.getValue(DB_USER_ENV_KEY, databaseProperties.getUsername());
        String dbPass = systemEnvUtil.getValue(DB_PASS_ENV_KEY, databaseProperties.getPassword());

        logger.debug("Creating DB DataSource : {}, {}, {}", databaseProperties.getDriverClassName(), dbURL, dbUser);
        
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(databaseProperties.getDriverClassName());
        dataSource.setJdbcUrl(dbURL);
        dataSource.setUsername(dbUser);
        dataSource.setPassword(dbPass);
        dataSource.setMinimumIdle(databaseProperties.getMinIdle());
        dataSource.setMaximumPoolSize(databaseProperties.getMaxPoolSize());

        return dataSource;
    }

    @Bean(name="psSqlSessionFactory")
    @Primary
    public SqlSessionFactoryBean sqlSessionFactory(@Qualifier("psDataSource") DataSource dataSource, ApplicationContext applicationContext) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();

        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource(databaseProperties.getConfigLocation()));

        return sqlSessionFactoryBean;
    }

    @Bean(name="psSqlSession")
    @Primary
    public SqlSession sqlSession(@Qualifier("psSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name="psTransactionManager")
    @Primary
    public DataSourceTransactionManager transactionManager(@Qualifier("psDataSource") DataSource dataSource) {
    	
    	DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        transactionManager.setGlobalRollbackOnParticipationFailure(false);
    	
        return transactionManager;
    }
    
    
    
 // Mappers
    @Bean
    public MapperFactoryBean<UserMapper> UserMapper(@Qualifier("psSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<UserMapper> bean = new MapperFactoryBean<>(UserMapper.class);
        bean.setSqlSessionFactory(sqlSessionFactory);
        return bean;
    }
    
    @Bean
    public MapperFactoryBean<AdvertisingMapper> AdvertisingMapper(@Qualifier("psSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<AdvertisingMapper> bean = new MapperFactoryBean<>(AdvertisingMapper.class);
        bean.setSqlSessionFactory(sqlSessionFactory);
        return bean;
    }
    
    @Bean
    public MapperFactoryBean<ChannelMapper> ChannelMapper(@Qualifier("psSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
    	MapperFactoryBean<ChannelMapper> bean = new MapperFactoryBean<>(ChannelMapper.class);
    	bean.setSqlSessionFactory(sqlSessionFactory);
    	return bean;
    }
    
    @Bean
    public MapperFactoryBean<MovieMapper> MovieMapper(@Qualifier("psSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
    	MapperFactoryBean<MovieMapper> bean = new MapperFactoryBean<>(MovieMapper.class);
    	bean.setSqlSessionFactory(sqlSessionFactory);
    	return bean;
    }
    
    @Bean
    public MapperFactoryBean<RankingMapper> RankingMapper(@Qualifier("psSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
    	MapperFactoryBean<RankingMapper> bean = new MapperFactoryBean<>(RankingMapper.class);
    	bean.setSqlSessionFactory(sqlSessionFactory);
    	return bean;
    }
    
    @Bean
    public MapperFactoryBean<LiveRoomMapper> LiveRoomMapper(@Qualifier("psSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
    	MapperFactoryBean<LiveRoomMapper> bean = new MapperFactoryBean<>(LiveRoomMapper.class);
    	bean.setSqlSessionFactory(sqlSessionFactory);
    	return bean;
    }
    
    @Bean
    public MapperFactoryBean<VerifyMapper> VerifyMapper(@Qualifier("psSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
    	MapperFactoryBean<VerifyMapper> bean = new MapperFactoryBean<>(VerifyMapper.class);
    	bean.setSqlSessionFactory(sqlSessionFactory);
    	return bean;
    }
    
}
