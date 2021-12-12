package hrbeu.courseDesign.yxd.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.ContentVersionStrategy;
import org.springframework.web.servlet.resource.VersionResourceResolver;

import java.util.concurrent.TimeUnit;

@Configuration
public class ApplicationConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /**
         * 如果我们将/xxxx/** 修改为 /** 与默认的相同时，则会覆盖系统的配置，可以多次使用 addResourceLocations 添加目录，
         * 优先级先添加的高于后添加的。
         *
         * 如果是/xxxx/** 引用静态资源 加不加/xxxx/ 均可，因为系统默认配置（/**）也会作用
         * 如果是/** 会覆盖默认配置，应用addResourceLocations添加所有会用到的静态资源地址，系统默认不会再起作用
         */
        VersionResourceResolver versionResourceResolver = new VersionResourceResolver()
                .addVersionStrategy(new ContentVersionStrategy(), "/**");
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/webapp/")
                .addResourceLocations("classpath:/META-INF/resources/")
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/public/")
                .addResourceLocations("file:D:\\imgs\\")
//                .addResourceLocations("file:/Users/kokorozashiguohuang/Desktop/images/")
//                .addResourceLocations("classpath:/**")
//                .addResourceLocations("classpath:/webapp/pagesForDrugs/")
//                .addResourceLocations("classpath:/webapp/page/")
                .setCacheControl(CacheControl.maxAge(5, TimeUnit.SECONDS))
                .setCachePeriod(2592000).resourceChain(true).addResolver(versionResourceResolver);

//        registry.addResourceHandler("/we/**").addResourceLocations("file:F:/preview/");

        super.addResourceHandlers(registry);
    }

//    @Bean
//    public ResourceUrlEncodingFilter resourceUrlEncodingFilter() {
//        return new ResourceUrlEncodingFilter();
//    }
}