import { Component } from '@angular/core';
import { AuthService } from './auth.service';
import { UserService } from './user.service';

@Component({
  selector: 'app',
  templateUrl: './app.component.html'
})
export class AppComponent {
  
  authorizedUser : boolean = false;

  constructor(private auth: AuthService, private user: UserService) { 
    this.auth.isLoggedIn
  }

  ngOnInit() {
    this.user.getUserData().subscribe( data => {
      this.authorizedUser = data.isActive
      console.log("Greetings from Main: "+this.authorizedUser)
    })
  }
}
