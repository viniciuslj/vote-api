package viniciuslj.vote.api.repository.constraints;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
public class RelationshipViolation {
    public boolean check(DataIntegrityViolationException exception, Class classA, Class classB) {
        String nameA = extractClassName(classA).toLowerCase();
        String nameB = extractClassName(classB).toLowerCase();
        String constraintName = ((ConstraintViolationException) exception.getCause()).getConstraintName();

        return constraintName.contains(nameA) && constraintName.contains(nameB);
    }

    private String extractClassName(Class c) {
        return c.getName().replace(c.getPackageName() + ".", "");
    }
}
