package com.weakwater.framework.core.config;

import com.weakwater.framework.core.props.WeakWaterProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@AutoConfiguration
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableConfigurationProperties(WeakWaterProperties.class)
public class WeakWaterPropertyConfiguration {
}
