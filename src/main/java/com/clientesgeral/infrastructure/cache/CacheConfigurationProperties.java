package com.clientesgeral.infrastructure.cache;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;

@ConfigurationProperties("cache.cliente")
public class CacheConfigurationProperties {

	public static final String BUSCAR_POR_ID = "clientesgeral.buscarporid";
	public static final String BUSCAR_POR_EMAIL = "clientesgeral.buscarporemail";
	public static final String BUSCAR_PAGINADO = "clientesgeral.buscarpaginado";

	private Long padrao;
	private Long buscarPorId;
	private Long buscarPorEmail;
	private Long buscarPaginado;

	@Bean
	public Map<String, RedisCacheConfiguration> cacheConfiguration() {
		final Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
		cacheConfigurations.put(BUSCAR_POR_ID, getConfig(this.buscarPorId));
		cacheConfigurations.put(BUSCAR_POR_EMAIL, getConfig(this.buscarPorEmail));
		cacheConfigurations.put(BUSCAR_PAGINADO, getConfig(this.buscarPaginado));

		return cacheConfigurations;
	}

	private RedisCacheConfiguration getConfig(final Long timeToLive) {
		return RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(Duration.ofSeconds(timeToLive == null ? this.padrao : timeToLive));
	}

	public Long getPadrao() {
		return padrao;
	}

	public void setPadrao(Long padrao) {
		this.padrao = padrao;
	}

	public Long getBuscarPorId() {
		return buscarPorId;
	}

	public void setBuscarPorId(Long buscarPorId) {
		this.buscarPorId = buscarPorId;
	}

	public Long getBuscarPorEmail() {
		return buscarPorEmail;
	}

	public void setBuscarPorEmail(Long buscarPorEmail) {
		this.buscarPorEmail = buscarPorEmail;
	}

	public Long getBuscarPaginado() {
		return buscarPaginado;
	}

	public void setBuscarPaginado(Long buscarPaginado) {
		this.buscarPaginado = buscarPaginado;
	}

	@Override
	public String toString() {
		return "CacheConfigurationProperties [padrao=" + padrao + ", buscarPorId=" + buscarPorId + ", buscarPorEmail="
				+ buscarPorEmail + ", buscarPaginado=" + buscarPaginado + "]";
	}

}
