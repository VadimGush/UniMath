#include <stdio.h>
#include "gmath.h"

int main(void) {
        // Test function
        printf("Result: %f\n", function(-2.0).value);
        // Test linear interpolation
        printf("Linear: %f, %f, %f\n", function(1).value, linear_interpolation(1.5, function(1), function(2)), function(2).value);

        // Test lan interpolation
        struct function_value data[10];
        for (int i = 0; i < 10; i++) {
                data[i] = function(i / 5.0);
                printf("%f: %f\n", data[i].argument, data[i].value);
        }
        lan_interpolation(0.1, data, 10);
        return 0;
}
