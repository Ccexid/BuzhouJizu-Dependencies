package com.buzhou.jizu.core.config;

import com.buzhou.jizu.core.props.BuzhouProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@AutoConfiguration
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableConfigurationProperties(BuzhouProperties.class)
public class BuzhouPropertyConfiguration {
}
