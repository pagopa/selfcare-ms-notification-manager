package it.pagopa.selfcare.notification_manager.web.model;

import it.pagopa.selfcare.commons.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotBlank;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CreateMessageToCustomerCareDtoTest {
    private Validator validator;
    private static final CreateMessageToCustomerCareDto CREATE_MESSAGE_DTO = TestUtils.mockInstance(new CreateMessageToCustomerCareDto());

    @BeforeEach
    void setUp() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    void validateNullFields() {
        //given
        HashMap<String, Class<? extends Annotation>> toCheckMap = new HashMap<>();
        toCheckMap.put("content", NotBlank.class);
        toCheckMap.put("subject", NotBlank.class);

        CreateMessageToCustomerCareDto messageDto = new CreateMessageToCustomerCareDto();
        messageDto.setContent(null);
        messageDto.setSubject(null);
        //when
        Set<ConstraintViolation<Object>> violations = validator.validate(messageDto);
        //then
        assertFalse(violations.isEmpty());
        List<ConstraintViolation<Object>> filteredViolations = violations.stream()
                .filter(violation -> {
                    Class<? extends Annotation> annotationToCheck = toCheckMap.get(violation.getPropertyPath().toString());
                    return !violation.getConstraintDescriptor().getAnnotation().annotationType().equals(annotationToCheck);
                })
                .collect(Collectors.toList());
        assertTrue(filteredViolations.isEmpty());
    }

    @Test
    void validateNotNullFields() {
        //when
        Set<ConstraintViolation<Object>> violations = validator.validate(CREATE_MESSAGE_DTO);
        // then
        assertTrue(violations.isEmpty());
    }
}
