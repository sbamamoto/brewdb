import { Component } from '@angular/core';
import { AuthService } from './auth.service';

@Component({
  selector: 'app',
  templateUrl: './app.component.html'
})
export class AppComponent {
  
  constructor(private auth: AuthService) { 
    this.auth.isLoggedIn
  }
}
