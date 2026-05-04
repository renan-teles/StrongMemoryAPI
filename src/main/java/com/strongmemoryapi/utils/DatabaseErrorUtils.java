package com.strongmemoryapi.utils;

import com.strongmemoryapi.domain.exception.local.ResourceAlreadyExistsException;
import com.strongmemoryapi.domain.exception.local.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;

public class DatabaseErrorUtils {

    private static final String
            FOREIGN_KEY_VIOLATION = "foreign key constraint fails",
            DUPLICATE_ENTRY = "duplicate entry";

    public static boolean isForeignKeyViolation(DataIntegrityViolationException ex) {
        return getErrorMessage(ex)
                .contains(FOREIGN_KEY_VIOLATION);
    }

    public static void checkForeignKeyViolationIfThrowResourceNotFoundException(
            DataIntegrityViolationException ex,
            String exceptionMessage
    ){
        if(isForeignKeyViolation(ex))
            throw new ResourceNotFoundException(exceptionMessage);
    }

    public static void checkForeignKeyViolationIfThrowResourceNotFoundException(
            DataIntegrityViolationException ex,
            String exceptionMessage,
            String referencesTableName
    ){
        if(isForeignKeyViolation(ex, referencesTableName))
            throw new ResourceNotFoundException(exceptionMessage);
    }

    public static boolean isForeignKeyViolation(
            DataIntegrityViolationException ex,
            String referencesTableName
    ) {
        String message = getErrorMessage(ex);
        return message.contains(referencesTableName) && message.contains(FOREIGN_KEY_VIOLATION);
    }

    public static void checkUniqueConstraintViolationIfThrowResourceAlreadyExistsException(
            DataIntegrityViolationException ex,
            String exceptionMessage
    ){
        if (isUniqueConstraintViolation(ex))
            throw new ResourceAlreadyExistsException(exceptionMessage);
    }

    public static boolean isUniqueConstraintViolation(DataIntegrityViolationException ex) {
        return getErrorMessage(ex)
                .contains(DUPLICATE_ENTRY);
    }

    public static String getErrorMessage(DataIntegrityViolationException e) {
        return e.getMostSpecificCause().getMessage().toLowerCase();
    }

}
