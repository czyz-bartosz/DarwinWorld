package darwinWorld.model;

import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;

public class BoundaryConverter extends SimpleArgumentConverter {

    @Override
    protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {

        if (source instanceof String) {

            String[] array = ((String) source).split("\\s*,\\s*");

            if(array.length != 4){
                throw new ArgumentConversionException("Cannot convert input to boundary: Argument count not equal to four");
            }

            int[] intArray = new int[4];

            for (int i=0; i<4; i++) {

                try{
                    int argument = Integer.parseInt(array[i]);
                    intArray[i] = argument;
                }catch (NumberFormatException e){
                    throw new ArgumentConversionException("Cannot convert input to boundary: Argument not a number");
                }
            }
            Vector2d lowerLeft = new Vector2d(intArray[0], intArray[1]);
            Vector2d upperRight = new Vector2d(intArray[2], intArray[3]);

            return new Boundary(lowerLeft, upperRight);

        } else {
            throw new IllegalArgumentException("Conversion from " + source.getClass() + " to "
                    + targetType + " not supported.");
        }
    }

}
