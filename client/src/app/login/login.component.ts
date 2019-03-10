import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { UserService } from '../user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private Auth: AuthService, private router: Router, private user: UserService) { }
  active: boolean = false;

  ngOnInit() {
    this.user.getUserData().subscribe( data => {
      this.active = data.isActive
      this.Auth.logout();
      this.Auth.emitState(false)
      console.log("User logged out")
    })
  }

  loginUser(event) {
    event.preventDefault()
    const username = event.target.querySelector("#username").value
    const password = event.target.querySelector("#password").value
    console.log(event)  
    this.Auth.getUserDetails(username, password).subscribe(data => {
      console.log (data, " from the web")
      if (data.isActive) {
        console.log("User logged in")
        this.Auth.setLoggedIn(true)
        this.Auth.emitState(true)
        this.router.navigate(["receipts"])
      }
    })
  }
}
