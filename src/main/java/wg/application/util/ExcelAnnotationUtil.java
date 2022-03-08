package wg.application.util;

import wg.application.annotation.Excel;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 17:33 2022/3/7
 * @updateTime: 17:33 2022/3/7
 ************************************************************************/
public class ExcelAnnotationUtil implements ConstraintValidator<Excel, Object> {

    private String[] replace;

    @Override
    public void initialize(Excel excelAnnotation) {
        replace = excelAnnotation.replace();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        if (replace.length > 0) {
            System.out.println("replace.length : " + replace.length);
            return true;
        }
        return false;
    }
}
