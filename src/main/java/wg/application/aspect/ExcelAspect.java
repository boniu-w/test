package wg.application.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.FieldSignature;
import org.springframework.stereotype.Component;
import wg.application.excel.annotation.Excel;

import java.lang.reflect.Field;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 16:56 2022/3/7
 * @updateTime: 16:56 2022/3/7
 ************************************************************************/
@Aspect
@Component
public class ExcelAspect {

    @Pointcut("@annotation(wg.application.excel.annotation.Excel)")
    public void excelPoint() {
    }

    @Around("excelPoint()")
    public Object excelAspect(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("--------excelPoint()-------");
        boolean b = false;
        String value = "";
        Signature signature = joinPoint.getSignature();
        FieldSignature fieldSignature = (FieldSignature) signature;
        Field field = fieldSignature.getField();
        Excel excelAnnotation = field.getAnnotation(Excel.class);
        String[] replace = excelAnnotation.replace();
        if (replace.length > 0) {

        }
        return joinPoint.proceed();
    }
}
