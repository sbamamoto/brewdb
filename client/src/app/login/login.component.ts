import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth.service';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { connectableObservableDescriptor } from 'rxjs/observable/ConnectableObservable';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private Auth: AuthService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit() {
    console.log("hallo, ich war hier ....")
  }

  loginUser(event) {
    event.preventDefault()
    const username = event.target.querySelector("#username").value
    const password = event.target.querySelector("#password").value
    console.log(event)  
    this.Auth.getUserDetails(username, password).subscribe(data => {
      console.log (data, " from the web")
      if (!data.isActive) {
        window.alert ("Went  Wrong :(")
      }
      else {
        console.log("User logged in")
        this.Auth.setLoggedIn(true)
        this.router.navigate(["receipts"])
      }
    })
  }
}
