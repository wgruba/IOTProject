import { Component } from '@angular/core';

@Component({
  selector: 'app-register-site',
  templateUrl: './register-site.component.html',
  styleUrls: ['./register-site.component.scss']
})



export class RegisterSiteComponent {

  password: string = "";
  password_repeated: string = "";
  warning: string = "";

  validatePassword(p: string, p2: string): string[] {
    const errors: string[] = [];
  
    if (p.length < 8) {
      errors.push("Hasło musi mieć co najmniej 8 znaków.");
    }
    if (!/[a-z]/.test(p)) {
      errors.push("Hasło musi zawierać co najmniej jedną małą literę.");
    }
    if (!/[A-Z]/.test(p)) {
      errors.push("Hasło musi zawierać co najmniej jedną dużą literę.");
    }
    if (!/\d/.test(p)) {
      errors.push("Hasło musi zawierać co najmniej jedną cyfrę.");
    }
    if (!/[ !"#$%&'()*+,-./:;<=>?@[\]^_`{|}~]/.test(p)) {
      errors.push("Hasło musi zawierać co najmniej jeden znak specjalny.");
    }
    if (p!==p2) {
      errors.push("Hasła muszą być takie same.");
    }

    return errors;
  }
  
  updateWarning() {
    const errors = this.validatePassword(this.password, this.password_repeated);
    this.warning = errors.join("\r\n");
  }
}

