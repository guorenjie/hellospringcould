package com.grj.eurekauser.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Description Swagger配置类
 *   swagger2访问地址：http://localhost:8080/swagger-ui.html
 *   swagger2使用说明：
 *          @Api：用在类上，说明该类的作用
 *          @ApiOperation：用在方法上，说明方法的作用
 *          @ApiIgnore：使用该注解忽略这个API
 *          @ApiImplicitParams：用在方法上包含一组参数说明
 *          @ApiImplicitParam：用在@ApiImplicitParams注解中，指定一个请求参数的各个方面
 *             paramType：参数放在哪个地方
 *                  header-->请求参数的获取：@RequestHeader
 *                  query-->请求参数的获取：@RequestParam
 *                  path（用于restful接口）-->请求参数的获取：@PathVariable
 *                  body（不常用）
 *                  form（不常用）
 *              name：参数名
 *              dataType：参数类型
 *              required：参数是否必须传
 *              value：参数的意思
 *              defaultValue：参数的默认值
 *          @ApiResponses：用于表示一组响应
 *          @ApiResponse：用在@ApiResponses中，一般用于表达一个错误的响应信息
 *              code：数字，例如400
 *              message：信息，例如"请求参数没填好"
 *              response：抛出异常的类
 *          @ApiModel：描述一个Model的信息（这种一般用在post创建的时候，使用@RequestBody这样的场景，请求参数无法使用@ApiImplicitParam注解进行描述的时候）
 *             @ApiModelProperty：描述一个model的属性
 *
 * @Author guorenjie
 * @Date 2019/9/21 23:12
 **/

@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                //groupName设置了中文会导致Can't read swagger JSON from http://localhost:8080/v2/api-docs?group=groupName
                .groupName("userService")
                .apiInfo(apiInfo())
                // 选择那些路径和api会生成document
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.grj.eurekauser.controller"))
                .paths(PathSelectors.any()).build();
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("接口文档标题")
                .description("接口文档描述")
                //.termsOfServiceUrl("http://blog.52itstyle.com")
                //.contact(new Contact("grj", "https://www.baidu.com", "guorenjie1994@qq.com"))
                .version("1.0").build();
    }
}
