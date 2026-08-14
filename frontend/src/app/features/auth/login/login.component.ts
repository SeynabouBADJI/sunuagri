import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { Router,RouterLink  } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, IonicModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent {
  email = '';
  motDePasse = '';
  chargement = false;

  constructor(private auth: AuthService, private router: Router) {}

  seConnecter() {
    this.chargement = true;
    this.auth.login(this.email, this.motDePasse).subscribe(() => {
      this.chargement = false;
      this.router.navigateByUrl('/tabs/diagnostic');
    });
  }
}