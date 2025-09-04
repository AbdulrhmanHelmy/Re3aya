//package com.re3aya.re3aya.Config;
//
//import io.swagger.v3.oas.models.Components;
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.security.SecurityRequirement;
//import io.swagger.v3.oas.models.security.SecurityScheme;
//import org.springdoc.core.customizers.OpenApiCustomizer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.List;
//import java.util.Map;
//import java.util.regex.Pattern;
//
//@Configuration
//public class OpenApiConfig {
//
//    @Bean
//    public OpenAPI customizeOpenAPI() {
//        return new OpenAPI()
//                .components(new Components()
//                        .addSecuritySchemes("bearerAuth",
//                                new SecurityScheme()
//                                        .type(SecurityScheme.Type.HTTP)
//                                        .scheme("bearer")
//                                        .bearerFormat("JWT")));
//    }
//
//    // Mapping بين Path patterns والـ Roles
//    private final Map<String, List<String>> roleMappings = Map.of(
//            "/auth/.*", List.of("PUBLIC"),
//            "/api/auth/verify", List.of("PUBLIC"),
//            "/categories/admin/.*", List.of("ADMIN"),
//            "/product/admin/.*", List.of("ADMIN"),
//            "/categories/.*", List.of("PUBLIC"),
//            "/product/.*", List.of("PUBLIC"),
//            "/v3/.*", List.of("PUBLIC"),
//            "/swagger-ui/.*", List.of("PUBLIC"),
//                "/admin/.*",List.of("ADMIN")
//    );
//
//    @Bean
//    public OpenApiCustomizer roleBasedCustomizer() {
//        return openApi -> {
//            openApi.getPaths().forEach((path, pathItem) -> {
//                pathItem.readOperations().forEach(operation -> {
//                    roleMappings.forEach((pattern, roles) -> {
//                        if (Pattern.matches(pattern, path)) {
//                            // لو مش Public، اضف Authorization header
//                            if (!roles.contains("PUBLIC")) {
//                                operation.addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
//                            }
//                            // اضف وصف بالـ Roles
//                            operation.setDescription("Roles: " + String.join(", ", roles));
//                        }
//                    });
//                });
//            });
//        };
//    }
//}
