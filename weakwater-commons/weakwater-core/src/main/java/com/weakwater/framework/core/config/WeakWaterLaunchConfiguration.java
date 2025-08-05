package com.weakwater.framework.core.config;

import com.weakwater.framework.core.props.WeakWaterProperties;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * Buzhou启动配置类
 * <p>
 * 该类负责Buzhou框架的自动配置启动，通过Spring Boot的自动配置机制
 * 来初始化相关的配置属性和组件。此类具有最高优先级的执行顺序，确保
 * 在其他配置之前加载。
 * <p>
 * 配置属性来源：
 * - BuzhouProperties：Buzhou框架的核心配置属性类
 *
 * @author [作者名]
 * @since [版本号]
 */
@AutoConfiguration
@AllArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableConfigurationProperties({
        WeakWaterProperties.class
})
public class WeakWaterLaunchConfiguration {
}

