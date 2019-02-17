import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

interface activeUser {
  isActive: boolean
}

@Injectable()
export class AuthService {



  private loggedInStatus = false;

  constructor(private http: HttpClient) { 

  }

  setLoggedIn (value:boolean) {
    this.loggedInStatus = value
  }

  get isLoggedIn() {
    return this.loggedInStatus
  }

  getUserDetails(username, password) {
    return this.http.post<activeUser>("/api/login", {
      username, password
    })
  }

}
