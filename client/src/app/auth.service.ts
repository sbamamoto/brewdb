import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';


interface activeUser {
  firstname: string,
  lastname: string,
  userId: string,
  isActive: boolean
}

@Injectable()
export class AuthService {

  private loggedInStatus:activeUser;
  private loginState = new BehaviorSubject<boolean>(false);
  stateEmitter = this.loginState.asObservable();
  
  constructor(private http: HttpClient) {}  

  emitState(state) {
    this.loginState.next(state)
  }

  setLoggedIn (value:boolean) {
    this.loggedInStatus.isActive = value
  }
  
  get isLoggedIn() {
    this.http.get<activeUser>('/api/auth').subscribe(data => {
      console.log ("Updating user state to:" + data.isActive)
      this.loggedInStatus = data
    })

    return this.loggedInStatus
  }

  getUserDetails(username, password) {
    return this.http.post<activeUser>("/api/login", {
      username, password
    })
  }

  logout () {
    return this.http.get<activeUser>("/api/logout", {}).subscribe(data => {
      this.loggedInStatus = data
    })
  }

}
