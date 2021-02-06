import { AbstractControl, ValidatorFn } from '@angular/forms';

export const conditionalValidator = (condition: () => boolean, validator: ValidatorFn): ValidatorFn => {
  return (control: AbstractControl): { [key: string]: any } => {
    if (!condition()) {
      // @ts-ignore
      return null;
    }
    // @ts-ignore
    return validator(control);
  };
};
