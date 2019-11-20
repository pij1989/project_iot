package ru.pij.dimon.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.pij.dimon.model.DateForm;

import java.util.regex.Pattern;
@Component
public class DateFormValidator implements Validator {

    private static final String REG_EX_DATE_TIME_FORMAT = "^(\\d{2})(\\.)(\\d{2})(\\.)(\\d{4}) (0[0-9]|[1][0-9]|[2][0-3])[\\:](0[0-9]|[1-5][0-9])[\\:](0[0-9]|[1-5][0-9])$";
    private static final String REG_EX_DATE_FORMAT = "(^(((0[1-9]|[12][0-8])[\\.](0[1-9]|1[012]))|((29|30|31)[\\.](0[13578]|1[02]))|((29|30)[\\.](0[4,6,9]|11)))[\\.]" +
            "(19|[2-9][0-9])\\d\\d$)|(^29[\\.]02[\\.]" +
            "(19|[2-9][0-9])(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)$)";
    @Override
    public boolean supports(Class<?> aClass) {
        return DateForm.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(target == null){
            errors.reject("object.null", "Date form is null");
            return;
        }

        DateForm dateForm = (DateForm)target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"fromDate","fromdate.empty","The from date is empty. Please choose a from date.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"toDate","todate.empty","The to date is empty. Please choose a to date.");

        Pattern patternDateTime = Pattern.compile(REG_EX_DATE_TIME_FORMAT);
        Pattern patternDate = Pattern.compile(REG_EX_DATE_FORMAT);
        if(!patternDateTime.matcher(dateForm.getFromDate().trim()).matches()){
            errors.rejectValue("fromDate","fromdate.invalidformat","From date should be in the format of dd.mm.yyyy hh:mm:ss");
        } else {
            if(!patternDate.matcher(dateForm.getFromDate().trim().split(" ")[0]).matches()){
                errors.rejectValue("fromDate","fromdate.invaliddate","From date invalid date");
            }
        }
        if (!patternDateTime.matcher(dateForm.getToDate().trim()).matches()){
            errors.rejectValue("toDate","fromdate.invalidformat","From date should be in the format of dd.mm.yyyy hh:mm:ss");
        }else {
            if (!patternDate.matcher(dateForm.getToDate().trim().split(" ")[0]).matches()) {
                errors.rejectValue("fromDate", "todate.invaliddate", "To date invalid date");
            }
        }
    }
}
