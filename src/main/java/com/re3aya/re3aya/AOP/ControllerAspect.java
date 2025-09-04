package com.re3aya.re3aya.AOP;

import com.re3aya.re3aya.Response.API_Response;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
public class ControllerAspect {

    @Around("execution(* com.re3aya.re3aya.Controller..*(..))")
    public Object logControllerMethods(ProceedingJoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        System.out.println("Invoking method: " + methodName);
        System.out.println("Parameters: " + Arrays.toString(args));

        try {
            Object result = joinPoint.proceed();

            System.out.println("Method executed successfully: " + methodName);
            System.out.println("Returned value: " + result);

            return result;
        } catch (Throwable ex) {
            System.err.println("Exception in method: " + methodName);
            System.err.println("Exception message: " + ex.getMessage());
            API_Response apiResponse = new API_Response();
            apiResponse.setMessage("Error: " + ex.getMessage());
            apiResponse.setData(null);

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @Before("within(@org.springframework.web.bind.annotation.RestController *)")
    public void logRequest(JoinPoint joinPoint) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            String username = authentication.getName();
            String roles = authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(", "));

            System.out.println("Request to: " + joinPoint.getSignature());
            System.out.println("User: " + username + " | Roles: " + roles);
        } else {
            System.out.println("Request to: " + joinPoint.getSignature());
            System.out.println("User: ANONYMOUS");
        }
    }
}
